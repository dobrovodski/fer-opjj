package hr.fer.zemris.java.webserver;

/**
 * Interface used to dispatch requests.
 *
 * @author matej
 */
public interface IDispatcher {
    /**
     * Called when the request is to be dispatched.
     *
     * @param urlPath url path
     *
     * @throws Exception if error occurred while dispatching the request
     */
    void dispatchRequest(String urlPath) throws Exception;
}