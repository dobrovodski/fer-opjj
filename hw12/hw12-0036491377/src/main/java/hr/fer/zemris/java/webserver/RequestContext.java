package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class RequestContext {
    private OutputStream outputStream;
    private Charset charset;
    private String encoding = "UTF-8";
    private int statusCode = 200;
    private String statusText = "OK";
    private String mimeType = "text/html";
    private Map<String, String> parameters;
    private Map<String, String> temporaryParameters;
    private Map<String, String> persistentParameters;
    private List<RCCookie> outputCookies;
    private boolean headerGenerated = false;

    public RequestContext(OutputStream outputStream, Map<String, String> parameters, Map<String, String>
            persistentParameters, List<RCCookie> outputCookies) {
        Objects.requireNonNull(outputStream, "Output stream must not be null");
        if (parameters == null) {
            parameters = new HashMap<>();
        }
        if (persistentParameters == null) {
            persistentParameters = new HashMap<>();
        }
        if (outputCookies == null) {
            outputCookies = new ArrayList<>();
        }
        this.outputStream = outputStream;
        this.parameters = parameters;
        this.persistentParameters = persistentParameters;
        this.outputCookies = outputCookies;
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public Set<String> getParameterNames() {
        return parameters.keySet();
    }

    public String getPersistentParameter(String name) {
        return persistentParameters.get(name);
    }

    public Set<String> getPersistentParameterNames() {
        return persistentParameters.keySet();
    }

    public void setPersistentParameter(String name, String value) {
        persistentParameters.put(name, value);
    }

    public void removePersistentParameter(String name) {
        persistentParameters.remove(name);
    }

    public String getTempoeraryParameter(String name) {
        return temporaryParameters.get(name);
    }

    public Set<String> getTemporaryParameterNames() {
        return temporaryParameters.keySet();
    }

    public void setTemporaryParameter(String name, String value) {
        temporaryParameters.put(name, value);
    }

    public void removeTemporaryParameter(String name) {
        temporaryParameters.remove(name);
    }

    public RequestContext write(byte[] data) throws IOException {
        createHeader();
        outputStream.write(data);
        return null;
    }

    public RequestContext write(String text) throws IOException {
        byte[] data = text.getBytes(charset);
        return write(data);
    }

    public void setEncoding(String encoding) {
        if (headerGenerated) {
            throw new RuntimeException("Header has already been generated.");
        }
        this.encoding = encoding;
    }

    public void setStatusCode(int statusCode) {
        if (headerGenerated) {
            throw new RuntimeException("Header has already been generated.");
        }
        this.statusCode = statusCode;
    }

    public void setStatusText(String statusText) {
        if (headerGenerated) {
            throw new RuntimeException("Header has already been generated.");
        }
        this.statusText = statusText;
    }

    public void setMimeType(String mimeType) {
        if (headerGenerated) {
            throw new RuntimeException("Header has already been generated.");
        }
        this.mimeType = mimeType;
    }

    public void addRCCookie(RCCookie cookie) {
        if (headerGenerated) {
            throw new RuntimeException("Header has already been generated.");
        }
        outputCookies.add(cookie);
    }

    private void createHeader() throws IOException {
        if (headerGenerated) {
            return;
        }
        headerGenerated = true;
        charset = Charset.forName(encoding);
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("HTTP/1.1 %s %s\r\n", statusCode, statusText));

        if (mimeType.startsWith("text/")) {
            mimeType += "; charset=" + encoding;
        }
        sb.append(String.format("Content-Type: %s\r\n", mimeType));

        if (outputCookies.size() > 0) {
            for (RCCookie cookie : outputCookies) {
                sb.append(cookie.toString());
                sb.append("\r\n");
            }
        }

        byte[] headerBytes = sb.toString().getBytes(StandardCharsets.ISO_8859_1);
        outputStream.write(headerBytes);
    }

    public static class RCCookie {
        private String name;
        private String value;
        private String domain;
        private String path;
        private Integer maxAge;

        public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
            this.name = name;
            this.value = value;
            this.domain = domain;
            this.path = path;
            this.maxAge = maxAge;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public String getDomain() {
            return domain;
        }

        public String getPath() {
            return path;
        }

        public Integer getMaxAge() {
            return maxAge;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            String s = String.format("Set-Cookie: %s=\"%s\"", name, value);
            sb.append(s);

            if (domain != null) {
                sb.append(String.format("; Domain=%s", domain));
            }
            if (path != null) {
                sb.append(String.format("; Path=%s", path));
            }
            if (maxAge != null) {
                sb.append(String.format("; Max-Age=%s", maxAge));
            }

            return sb.toString();
        }
    }
}
