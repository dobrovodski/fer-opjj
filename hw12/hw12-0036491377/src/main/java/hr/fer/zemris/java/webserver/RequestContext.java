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

/**
 * This class represents a single HTTP request and keeps track of all the necessary information about the request as
 * well as provides methods for changing its parameters. Once the request has been set up, it can be written to the
 * provided {@link OutputStream}.
 *
 * @author matej
 */
public class RequestContext {
    /**
     * Stream to write to.
     */
    private OutputStream outputStream;
    /**
     * Charset being used.
     */
    private Charset charset;
    /**
     * Encoding (defaults to UTF-8).
     */
    private String encoding = "UTF-8";
    /**
     * Status code of the request.
     */
    private int statusCode = 200;
    /**
     * Status text.
     */
    private String statusText = "OK";
    /**
     * Mime type.
     */
    private String mimeType = "text/html";
    /**
     * Content length.
     */
    private Long contentLength;
    /**
     * Map of request's parameters.
     */
    private Map<String, String> parameters;
    /**
     * Map of request's temporary parameters.
     */
    private Map<String, String> temporaryParameters;
    /**
     * Map of request's persistent parametes.
     */
    private Map<String, String> persistentParameters;
    /**
     * List of output cookies.
     */
    private List<RCCookie> outputCookies;
    /**
     * Keeps track of header generation.
     */
    private boolean headerGenerated = false;
    /**
     * Dispatcher.
     */
    private IDispatcher dispatcher;

    /**
     * Constructor for {@link RequestContext}.
     *
     * @param outputStream output stream to write to
     * @param parameters parameters map - created if null
     * @param persistentParameters persistentParameters map - created if null
     * @param outputCookies outputCookies list - created if null
     *
     * @throws NullPointerException if outputStream is null
     */
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
        this.temporaryParameters = new HashMap<>();
    }

    /**
     * Constructor for {@link RequestContext}.
     *
     * @param outputStream output stream to write to
     * @param parameters parameters map - created if null
     * @param persistentParameters persistentParameters map - created if null
     * @param outputCookies outputCookies list - created if null
     * @param dispatcher dispatcher to use
     * @param temporaryParameters temporary parameters map
     *
     * @throws NullPointerException if outputStream, dispatcher or temporaryParameters are null
     */
    public RequestContext(OutputStream outputStream, Map<String, String> parameters, Map<String, String>
            persistentParameters, List<RCCookie> outputCookies, IDispatcher dispatcher, Map<String, String>
            temporaryParameters) {
        this(outputStream, parameters, persistentParameters, outputCookies);
        Objects.requireNonNull(dispatcher, "Dispatcher cannot be null.");
        Objects.requireNonNull(temporaryParameters, "Temporary parameters cannot be null.");
        this.dispatcher = dispatcher;
        this.temporaryParameters = temporaryParameters;
    }

    /**
     * Returns parameter with given name
     *
     * @param name name of parameter
     *
     * @return parameter
     */
    public String getParameter(String name) {
        return parameters.get(name);
    }

    /**
     * Gets all parameter names.
     *
     * @return set of parameter names
     */
    public Set<String> getParameterNames() {
        return parameters.keySet();
    }

    /**
     * Gets persistent parameter with given name.
     *
     * @param name name of parameter
     *
     * @return parameter
     */
    public String getPersistentParameter(String name) {
        return persistentParameters.get(name);
    }

    /**
     * Gets all persistent parameter names.
     *
     * @return set of persistent parameter names
     */
    public Set<String> getPersistentParameterNames() {
        return persistentParameters.keySet();
    }

    /**
     * Sets persistent parameter to the given value
     *
     * @param name name of parameter
     * @param value value to store
     */
    public void setPersistentParameter(String name, String value) {
        persistentParameters.put(name, value);
    }

    /**
     * Removes persistent parameter.
     *
     * @param name name of parameter
     */
    public void removePersistentParameter(String name) {
        persistentParameters.remove(name);
    }

    /**
     * Returns temporary parameter with given name.
     *
     * @param name name of parameter
     *
     * @return temporary parameter with given name
     */
    public String getTemporaryParameter(String name) {
        return temporaryParameters.get(name);
    }

    /**
     * Gets all temporary parameter names.
     *
     * @return set of temporary parameter names
     */
    public Set<String> getTemporaryParameterNames() {
        return temporaryParameters.keySet();
    }

    /**
     * Sets temporary parameter to the given value.
     *
     * @param name name of temporary parameter
     * @param value value to set it to
     */
    public void setTemporaryParameter(String name, String value) {
        temporaryParameters.put(name, value);
    }

    /**
     * Removes temporary parameter.
     *
     * @param name name of parameter to remove
     */
    public void removeTemporaryParameter(String name) {
        temporaryParameters.remove(name);
    }

    /**
     * Writes given data to the output stream.
     *
     * @param data data to write
     *
     * @return this RequestContext
     *
     * @throws IOException if couldn't write to output stream
     */
    public RequestContext write(byte[] data) throws IOException {
        write(data, 0, data.length);
        return this;
    }

    /**
     * Writes given data to the output stream.
     *
     * @param text text to write
     *
     * @return this RequestContext
     *
     * @throws IOException if couldn't write to output stream
     */
    public RequestContext write(String text) throws IOException {
        // This is called here so that it can set the charset property before decoding the data
        createHeader();
        byte[] data = text.getBytes(charset);
        return write(data);
    }

    /**
     * Writes given data to the output stream.
     *
     * @param data data to write
     * @param offset offset
     * @param len len of data
     *
     * @return this RequestContext
     *
     * @throws IOException if couldn't write to output stream
     */
    public RequestContext write(byte[] data, int offset, int len) throws IOException {
        createHeader();
        outputStream.write(data, offset, len);
        return this;
    }

    /**
     * Sets the encoding.
     *
     * @param encoding encoding to set to
     */
    public void setEncoding(String encoding) {
        if (headerGenerated) {
            throw new RuntimeException("Header has already been generated.");
        }
        this.encoding = encoding;
    }

    /**
     * Sets the request's status code.
     *
     * @param statusCode status code
     */
    public void setStatusCode(int statusCode) {
        if (headerGenerated) {
            throw new RuntimeException("Header has already been generated.");
        }
        this.statusCode = statusCode;
    }

    /**
     * Sets status text.
     *
     * @param statusText status text
     */
    public void setStatusText(String statusText) {
        if (headerGenerated) {
            throw new RuntimeException("Header has already been generated.");
        }
        this.statusText = statusText;
    }

    /**
     * Sets the mime-type of the request.
     *
     * @param mimeType mime type
     */
    public void setMimeType(String mimeType) {
        if (headerGenerated) {
            throw new RuntimeException("Header has already been generated.");
        }
        this.mimeType = mimeType;
    }

    /**
     * Sets the value of the contentLength header.
     *
     * @param contentLength length of content
     */
    public void setContentLength(Long contentLength) {
        if (headerGenerated) {
            throw new RuntimeException("Header has already been generated.");
        }
        this.contentLength = contentLength;
    }

    /**
     * Adds given cookie to outputCookies.
     *
     * @param cookie cookie to add
     */
    public void addRCCookie(RCCookie cookie) {
        if (headerGenerated) {
            throw new RuntimeException("Header has already been generated.");
        }
        outputCookies.add(cookie);
    }

    /**
     * Generates and writes header using the current fields. Header is only generated and written once.
     *
     * @throws IOException if couldn't write to outputStream
     */
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

        if (contentLength != null) {
            sb.append(String.format("Content-Length: %d\r\n", contentLength));
        }

        if (outputCookies.size() > 0) {
            for (RCCookie cookie : outputCookies) {
                sb.append(cookie.toString());
                sb.append("\r\n");
            }
        }

        sb.append("\r\n");

        byte[] headerBytes = sb.toString().getBytes(StandardCharsets.US_ASCII);
        write(headerBytes);
    }

    /**
     * Returns the stored dispatcher.
     *
     * @return IDispatcher dispatcher
     */
    public IDispatcher getDispatcher() {
        return dispatcher;
    }

    /**
     * Represents a {@link RequestContext} cookie and holds data about it.
     *
     * @author matej
     */
    public static class RCCookie {
        /**
         * Name of cookie.
         */
        private String name;
        /**
         * Value.
         */
        private String value;
        /**
         * Domain.
         */
        private String domain;
        /**
         * Path.
         */
        private String path;
        /**
         * Maximum age of cookie.
         */
        private Integer maxAge;

        /**
         * Constructor for {@link RCCookie}.
         *
         * @param name name
         * @param value value
         * @param maxAge maximum age
         * @param domain domain
         * @param path path
         */
        public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
            this.name = name;
            this.value = value;
            this.domain = domain;
            this.path = path;
            this.maxAge = maxAge;
        }

        /**
         * Returns the name of the cookie.
         *
         * @return name
         */
        public String getName() {
            return name;
        }

        /**
         * Returns value va of the cookie.
         *
         * @return value
         */
        public String getValue() {
            return value;
        }

        /**
         * Returns the domain of the cookie.
         *
         * @return domain
         */
        public String getDomain() {
            return domain;
        }

        /**
         * Returns the path of the cookie.
         *
         * @return path
         */
        public String getPath() {
            return path;
        }

        /**
         * Returns the maximum age of the cookie.
         *
         * @return maximum age
         */
        public Integer getMaxAge() {
            return maxAge;
        }

        @Override
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
            sb.append("; HttpOnly");

            return sb.toString();
        }
    }
}
