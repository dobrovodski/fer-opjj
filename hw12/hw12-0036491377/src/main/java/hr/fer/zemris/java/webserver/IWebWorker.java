package hr.fer.zemris.java.webserver;

/**
 * This interface allows the processing of a request using the provided {@link RequestContext} via the processRequest
 * method.
 *
 * @author matej
 */
public interface IWebWorker {
    /**
     * Called when a request is received.
     *
     * @param context the context of the request
     *
     * @throws Exception if error occurred while processing the request
     */
    void processRequest(RequestContext context) throws Exception;
}