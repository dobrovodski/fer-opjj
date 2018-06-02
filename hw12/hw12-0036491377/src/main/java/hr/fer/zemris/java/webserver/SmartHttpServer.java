package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

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
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * HTTP Server program which expects a single argument: the path to a valid server configuration file. The server starts
 * running on the provided domain and port and listens for oncoming requests and processes them.
 *
 * @author matej
 */
public class SmartHttpServer {
    /**
     * Address of server.
     */
    private String address;
    /**
     * Domain of server.
     */
    private String domainName;
    /**
     * Port to listen on.
     */
    private int port;
    /**
     * Number of threads of the server.
     */
    private int workerThreads;
    /**
     * How long a session is.
     */
    private int sessionTimeout;
    /**
     * List of valid mime types.
     */
    private Map<String, String> mimeTypes = new HashMap<>();
    /**
     * Server thread.
     */
    private ServerThread serverThread;
    /**
     * Thread pool.
     */
    private ExecutorService threadPool;
    /**
     * Document root.
     */
    private Path documentRoot;
    /**
     * Map of {@link IWebWorker}s extracted from workers properties file.
     */
    private Map<String, IWebWorker> workersMap;
    /**
     * Map of valid {@link SessionMapEntry}s.
     */
    private Map<String, SessionMapEntry> sessions = new HashMap<>();
    /**
     * Used to randomize a SID.
     */
    private Random sessionRandom = new Random();

    /**
     * Main entry point.
     *
     * @param args valid server configuration path
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please specify a valid config path.");
            return;
        }

        new SmartHttpServer(args[0]);
    }

    /**
     * Constructor.
     *
     * @param configFileName valid server configuration path
     */
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

        workersMap = new HashMap<>();
        parseWorkers(Paths.get(prop.getProperty("server.workers")));

        startSessionCleanupThread();

        serverThread = new ServerThread();
        start();
    }

    /**
     * Fills workersMap with workers by looking at the provided workers.properties file.
     *
     * @param path path of workers.properties file
     */
    private void parseWorkers(Path path) {
        String workers;
        try {
            workers = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Could not read workers file.");
            return;
        }

        String[] lines = workers.split("\n");
        for (String worker : lines) {
            String[] split = worker.split("=");
            String pathToWorker = split[0].trim();
            String fqcn = split[1].trim();
            Class<?> referenceToClass = null;
            try {
                referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
                Object newObject = referenceToClass.newInstance();
                IWebWorker iww = (IWebWorker) newObject;
                workersMap.put(pathToWorker, iww);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                System.out.println("Could not create web worker.");
                return;
            }
        }
    }

    /**
     * Initializes the mimeTypes map through a valid mime.properties file.
     *
     * @param path path to mime.properties
     */
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

    /**
     * Starts the server.
     */
    protected synchronized void start() {
        if (!serverThread.isAlive()) {
            serverThread.start();
        }
        if (threadPool == null) {
            threadPool = Executors.newFixedThreadPool(workerThreads);
        }
    }

    /**
     * Stops the server.
     */
    protected synchronized void stop() {
        threadPool.shutdown();
        serverThread.stopThread();
    }

    /**
     * Starts the daemon thread which cleans up expired sessions from the sessions map.
     */
    private void startSessionCleanupThread() {
        // Every 5 mins
        final int PERIOD = 5 * 60000;
        Thread t = new Thread(() -> {
            while (true) {
                // Don't touch sessions unless it's synchronized
                synchronized (SmartHttpServer.this) {
                    for (Map.Entry<String, SessionMapEntry> kv : sessions.entrySet()) {
                        SessionMapEntry entry = kv.getValue();
                        if (entry.validUntil < System.currentTimeMillis()) {
                            sessions.remove(entry.sid);
                        }
                    }
                }
                try {
                    Thread.sleep(PERIOD);
                } catch (InterruptedException ignored) {
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }

    /**
     * A single thread which runs on the server and forwards requests to be processed.
     *
     * @author matej
     */
    protected class ServerThread extends Thread {
        /**
         * Used to stop the thread.
         */
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

    /**
     * A worker class which is used by {@link ServerThread}s to respond to server requests.
     *
     * @author matej
     */
    private class ClientWorker implements Runnable, IDispatcher {
        /**
         * Client socket.
         */
        private Socket csocket;
        /**
         * Input stream.
         */
        private PushbackInputStream istream;
        /**
         * Output stream.
         */
        private OutputStream ostream;
        /**
         * HTTP version.
         */
        private String version;
        /**
         * HTTP method.
         */
        private String method;
        /**
         * Host which the request came from.
         */
        private String host;
        /**
         * Map of request parameters.
         */
        private Map<String, String> params = new HashMap<>();
        /**
         * Map of temporary parameters.
         */
        private Map<String, String> tempParams = new HashMap<>();
        /**
         * Map of permanent parameters.
         */
        private Map<String, String> permPrams = new HashMap<>();
        /**
         * List of output cookies.
         */
        private List<RequestContext.RCCookie> outputCookies = new ArrayList<>();
        /**
         * Context of the request.
         */
        private RequestContext context;

        /**
         * Constructor for {@link ClientWorker}.
         *
         * @param csocket client socket
         */
        public ClientWorker(Socket csocket) {
            super();
            this.csocket = csocket;
        }

        @Override
        public void run() {
            try {
                istream = new PushbackInputStream(csocket.getInputStream());
                ostream = csocket.getOutputStream();
                List<String> request = readRequest();

                // This part checks for malformed requests
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
                        break;
                    }
                }
                if (host == null) {
                    host = domainName;
                }

                checkSession(request);

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

        /**
         * Checks for cookies with the name "sid" and updates them or creates a new cookie with a randomly generated
         * sid.
         *
         * @param headers list of headers
         */
        private void checkSession(List<String> headers) {
            // It has to be SmartHttpServer.this because otherwise 2 clients could access the map at the same time
            synchronized (SmartHttpServer.this) {
                String sidCandidate = null;
                for (String header : headers) {
                    if (!header.startsWith("Cookie:")) {
                        continue;
                    }

                    String[] cookies = header.substring(7).split(";");
                    for (String cookie : cookies) {
                        String[] parts = cookie.split("=", 2);
                        String name = parts[0].trim();
                        String value = parts[1].replace("\"", "");
                        if (name.equals("sid")) {
                            sidCandidate = value;
                        }
                    }
                }

                SessionMapEntry entry = createSessionEntry();
                if (sidCandidate == null) {
                    createCookie(entry);
                }

                if (sidCandidate != null) {
                    entry = sessions.get(sidCandidate);
                    if (entry == null || !entry.host.equals(this.host)) {
                        entry = createSessionEntry();
                        createCookie(entry);
                    } else if (entry.validUntil < System.currentTimeMillis()) {
                        sessions.remove(sidCandidate);
                        entry = createSessionEntry();
                        createCookie(entry);
                    } else {
                        entry.validUntil = System.currentTimeMillis() + sessionTimeout * 1000;
                    }
                }

                permPrams = entry.map;
            }
        }

        /**
         * Adds provided entry to the sessions map and creates a new
         * {@link hr.fer.zemris.java.webserver.RequestContext.RCCookie}
         * which is added to the outputCookies list.
         *
         * @param entry entry
         */
        private void createCookie(SessionMapEntry entry) {
            sessions.put(entry.sid, entry);
            outputCookies.add(new RequestContext.RCCookie("sid", entry.sid, null, host, "/"));
        }

        /**
         * Creates a new {@link SessionMapEntry} with a randomly generated sid.
         *
         * @return newly generated session entry
         */
        private SessionMapEntry createSessionEntry() {
            SessionMapEntry entry = new SessionMapEntry();

            StringBuilder randomSID = new StringBuilder();
            for (int i = 0; i < 20; i++) {
                randomSID.append((char) sessionRandom.nextInt(26) + 'A');
            }

            entry.validUntil = System.currentTimeMillis() + sessionTimeout * 1000;
            entry.map = new ConcurrentHashMap<>();
            entry.sid = randomSID.toString();
            entry.host = host;

            return entry;
        }

        /**
         * Simple state machine which reads the HTTP request.
         *
         * @return list of headers
         *
         * @throws IOException if couldn't create a list of headers
         */
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

        /**
         * Parses the parameters given and stores them in the params map.
         *
         * @param paramString string of parameters in the form of p1=v1&p2=v2...
         */
        private void parseParameters(String paramString) {
            String[] params = paramString.split("&");
            for (String p : params) {
                if (p.startsWith("=")) {
                    continue;
                }
                String[] parts = p.split("=");
                String left = parts[0];
                String right;
                if (parts.length == 1) {
                    if (p.contains("=")) {
                        right = "";
                    } else {
                        right = null;
                    }
                } else {
                    right = parts[1];
                }

                this.params.put(left, right);
            }
        }

        /**
         * Sends an error with the given status code and message to the context.
         *
         * @param statusCode status code
         * @param statusText status message
         */
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
            internalDispatchRequest(urlPath, false);
        }

        /**
         * This method analyzes the given url path and determines how to process it.
         *
         * @param urlPath url path
         * @param directCall boolean flag which indicates if this method was called directly
         *
         * @throws IOException if couldn't write to output stream
         */
        public void internalDispatchRequest(String urlPath, boolean directCall) throws IOException {
            Path root = documentRoot.toAbsolutePath();
            Path requestedFile = root.resolve(urlPath.substring(1)).toAbsolutePath();

            if (!requestedFile.startsWith(documentRoot)) {
                sendError(403, "Forbidden");
                return;
            }

            if (urlPath.equals("/private") || ((urlPath.startsWith("/private/")) && directCall)) {
                sendError(404, "File not found");
                return;
            }

            if (context == null) {
                context = new RequestContext(ostream, params, permPrams, outputCookies, this, tempParams);
            }

            if (urlPath.startsWith("/ext/")) {
                runWorker(urlPath, context);
                return;
            }

            if (workersMap.containsKey(urlPath)) {
                try {
                    workersMap.get(urlPath).processRequest(context);
                } catch (Exception e) {
                    System.out.println("Could not process request with worker.");
                }
                return;
            }

            if (!Files.isRegularFile(requestedFile) || !Files.isReadable(requestedFile)) {
                sendError(404, "File not found");
                return;
            }

            String extension = getExtension(requestedFile);
            String mimeType = mimeTypes.getOrDefault(extension, "application/octet-stream");

            // Special case when it is a script file
            if (extension.equals("smscr")) {
                runScript(requestedFile, context);
                return;
            }

            context.setMimeType(mimeType);
            byte[] fileData = Files.readAllBytes(requestedFile);
            context.setContentLength((long) fileData.length);
            context.write(fileData);
            ostream.flush();
        }
    }

    /**
     * Run worker specified by urlPath.
     *
     * @param urlPath path of worker
     * @param context context
     */
    private void runWorker(String urlPath, RequestContext context) {
        try {
            String fqcn = "hr.fer.zemris.java.webserver.workers." + urlPath.substring(5);
            Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
            Object newObject = null;
            newObject = referenceToClass.newInstance();
            IWebWorker iww = (IWebWorker) newObject;
            iww.processRequest(context);
        } catch (Exception e) {
            System.out.println("Could not create web worker.");
        }
    }

    /**
     * Returns the file extension of the given path or empty string if extension couldn't be determined.
     *
     * @param path path of file to check
     *
     * @return extension of file
     */
    private String getExtension(Path path) {
        String fileName = path.toAbsolutePath().toString();
        String extension = "";
        int i = fileName.lastIndexOf('.');
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
        if (i > p) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }

    /**
     * Runs the smart script determined by given path.
     *
     * @param path path to smart script file
     * @param context context
     *
     * @throws IOException if couldn't write to output stream
     */
    private void runScript(Path path, RequestContext context) throws IOException {
        String documentBody = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        context.setMimeType("text/html");
        new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), context).execute();
    }

    /**
     * Represents a single session.
     *
     * @author matej
     */
    private static class SessionMapEntry {
        /**
         * Randomly generated session id.
         */
        String sid;
        /**
         * Host of the session.
         */
        String host;
        /**
         * Time until the session is valid.
         */
        long validUntil;
        /**
         * Session map.
         */
        Map<String, String> map;
    }
}
