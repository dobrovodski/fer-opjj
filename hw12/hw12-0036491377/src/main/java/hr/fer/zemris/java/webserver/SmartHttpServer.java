package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SmartHttpServer {
    private String address;
    private String domainName;
    private int port;
    private int workerThreads;
    private int sessionTimeout;
    private Map<String, String> mimeTypes = new HashMap<>();
    private ServerThread serverThread;
    private ExecutorService threadPool;
    private Path documentRoot;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please specify a valid config path.");
            return;
        }

        new SmartHttpServer(args[0]);
    }

    public SmartHttpServer(String configFileName) {
        Properties prop = new Properties();

        try {
            prop.load(new FileInputStream(configFileName));
        } catch (IOException e) {
            System.out.println("Could not read given config file.");
            return;
        }

        address = prop.getProperty("server.address");
        domainName = prop.getProperty("server.domainName");
        port = Integer.parseInt(prop.getProperty("server.port"));
        workerThreads = Integer.parseInt(prop.getProperty("server.workerThreads"));
        documentRoot = Paths.get(prop.getProperty("server.documentRoot"));
        sessionTimeout = Integer.parseInt(prop.getProperty("session.timeout"));

        Path mimePropertiesPath = Paths.get(prop.getProperty("server.mimeConfig"));
        initMimeTypes(mimePropertiesPath);

        serverThread = new ServerThread();
        start();
    }

    private void initMimeTypes(Path path) {
        try {
            String[] allTypes = new String(Files.readAllBytes(path), StandardCharsets.UTF_8).split("\n");
            for (String type : allTypes) {
                String[] parts = type.split("=");
                mimeTypes.put(parts[0].trim(), parts[1].trim());
            }

        } catch (IOException e) {
            System.out.println("Could not open mine type file.");
        }
    }

    protected synchronized void start() {
        if (!serverThread.isAlive()) {
            serverThread.start();
        }
        if (threadPool == null) {
            threadPool = Executors.newFixedThreadPool(workerThreads);
        }
    }

    protected synchronized void stop() {
        threadPool.shutdown();
        serverThread.stopThread();
    }

    protected class ServerThread extends Thread {
        private boolean stop = false;

        @Override
        public void run() {
            ServerSocket socket;
            try {
                socket = new ServerSocket();
                socket.bind(new InetSocketAddress((InetAddress) null, port));
            } catch (IOException e) {
                System.out.println("Could not create socket.");
                return;
            }

            while (!stop) {
                try {
                    Socket client = socket.accept();
                    ClientWorker cw = new ClientWorker(client);
                    threadPool.submit(cw);
                } catch (IOException e) {
                    System.out.println("Socket couldn't accept connection");
                    return;
                }
            }
        }

        void stopThread() {
            stop = true;
        }
    }

    private class ClientWorker implements Runnable, IDispatcher {
        private Socket csocket;
        private PushbackInputStream istream;
        private OutputStream ostream;
        private String version;
        private String method;
        private String host;
        private Map<String, String> params = new HashMap<>();
        private Map<String, String> tempParams = new HashMap<>();
        private Map<String, String> permPrams = new HashMap<>();
        private List<RequestContext.RCCookie> outputCookies = new ArrayList<RCCookie>();
        private String SID;

        public ClientWorker(Socket csocket) {
            super();
            this.csocket = csocket;
        }

        @Override
        public void run() {
            List<String> request;
            try {
                istream = new PushbackInputStream(csocket.getInputStream());
                ostream = csocket.getOutputStream();
                request = readRequest();

                if (request == null || request.size() < 1) {
                    sendError(400, "Bad request");
                    return;
                }

                String[] firstLine = request.get(0).split(" ");
                if (firstLine.length != 3) {
                    sendError(400, "Bad request");
                    return;
                }

                String method = firstLine[0].toUpperCase();
                if (!method.equals("GET")) {
                    sendError(405, "Method Not Allowed");
                    return;
                }

                String version = firstLine[2].toUpperCase();
                if (!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
                    sendError(505, "HTTP Version Not Supported");
                    return;
                }

                for (String header : request) {
                    if (header.startsWith("Host:")) {
                        String[] host = header.substring(5).trim().split(":");
                        this.host = host[0];
                    }
                }

                String path;
                String paramString;
                String[] split = firstLine[1].split("\\?");
                path = split[0];
                if (split.length > 1) {
                    paramString = split[1];
                    parseParameters(paramString);
                }

                internalDispatchRequest(path, true);
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            } finally {
                try {
                    csocket.close();
                } catch (IOException ignored) {
                }
            }
        }

        private List<String> readRequest() throws IOException {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int state = 0;
            l:
            while (true) {
                int b = istream.read();
                if (b == -1)
                    return null;
                if (b != 13) {
                    bos.write(b);
                }
                switch (state) {
                    case 0:
                        if (b == 13) {
                            state = 1;
                        } else if (b == 10)
                            state = 4;
                        break;
                    case 1:
                        if (b == 10) {
                            state = 2;
                        } else
                            state = 0;
                        break;
                    case 2:
                        if (b == 13) {
                            state = 3;
                        } else
                            state = 0;
                        break;
                    case 3:
                        if (b == 10) {
                            break l;
                        } else
                            state = 0;
                        break;
                    case 4:
                        if (b == 10) {
                            break l;
                        } else
                            state = 0;
                        break;
                }
            }

            String requestHeader = new String(bos.toByteArray(), StandardCharsets.US_ASCII);

            List<String> headers = new ArrayList<>();
            StringBuilder currentLine = null;

            for (String s : requestHeader.split("\n")) {
                if (s.isEmpty()) {
                    break;
                }
                char c = s.charAt(0);
                if ((c == 9 || c == 32) && currentLine != null) {
                    currentLine.append(s);
                } else {
                    if (currentLine != null) {
                        headers.add(currentLine.toString());
                    }
                    currentLine = new StringBuilder(s);
                }
            }

            if (currentLine != null && currentLine.length() > 0) {
                headers.add(currentLine.toString());
            }

            return headers;
        }

        private void parseParameters(String paramString) {
            String[] params = paramString.split("&");
            for (String p : params) {
                String[] parts = p.split("=");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Could not parse parameter.");
                }
                this.params.put(parts[0], parts[1]);
            }
        }

        private void sendError(int statusCode, String statusText) {
            try {
                ostream.write((
                        "HTTP/1.1 " + statusCode + " " + statusText + "\r\n" +
                        "Server: simple java server\r\n" +
                        "Content-Type: text/plain;charset=UTF-8\r\n" +
                        "Content-Length: 0\r\n" +
                        "Connection: close\r\n" +
                        "\r\n"
                ).getBytes(StandardCharsets.US_ASCII));
                ostream.flush();
            } catch (IOException e) {
                System.out.println("Could not write error to output stream.");
            }
        }

        @Override
        public void dispatchRequest(String urlPath) throws Exception {

        }

        public void internalDispatchRequest(String urlPath, boolean directCall) throws IOException {
            Path root = documentRoot.toAbsolutePath();
            Path requestedFile = root.resolve(urlPath.substring(1)).toAbsolutePath();

            if (!requestedFile.startsWith(documentRoot)) {
                sendError(403, "Forbidden");
                return;
            }

            if (!Files.isRegularFile(requestedFile) || !Files.isReadable(requestedFile)) {
                sendError(404, "File not found");
                return;
            }

            String fileName = requestedFile.toAbsolutePath().toString();
            String extension = "";
            int i = fileName.lastIndexOf('.');
            int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
            if (i > p) {
                extension = fileName.substring(i + 1);
            }
            String mimeType = mimeTypes.getOrDefault(extension, "application/octet-stream");

            RequestContext rc = new RequestContext(ostream, params, permPrams, outputCookies);
            rc.setMimeType(mimeType);
            rc.setStatusCode(200);
            byte[] fileData = Files.readAllBytes(requestedFile);
            rc.setContentLength((long) fileData.length);
            rc.write(fileData);
            ostream.flush();
        }

    }

}
