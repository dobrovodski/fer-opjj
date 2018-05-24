package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Listener class for {@link SingleDocumentModel}. It provides methods for tracking the modification status and the path
 * of the model
 *
 * @author matej
 */
public interface SingleDocumentListener {
    /**
     * Called when the status of the document has been updated.
     *
     * @param model model which the listener listens to
     */
    void documentModifyStatusUpdated(SingleDocumentModel model);

    /**
     * Called when the path of the document has been updated.
     *
     * @param model model which the listener listens to
     */
    void documentFilePathUpdated(SingleDocumentModel model);
}
