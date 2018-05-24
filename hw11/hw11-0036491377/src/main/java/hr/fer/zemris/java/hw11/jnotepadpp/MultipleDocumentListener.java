package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Listener class for {@link MultipleDocumentModel}. It provides methods for tracking the creation and removal of
 * documents as well as the current document.
 *
 * @author matej
 */
public interface MultipleDocumentListener {
    /**
     * Called when the current document has been changed.
     * @param previousModel previous current document
     * @param currentModel new current document
     */
    void currentDocumentChanged(SingleDocumentModel previousModel,
            SingleDocumentModel currentModel);

    /**
     * Called when a new document has been added.
     * @param model document model that has been added
     */
    void documentAdded(SingleDocumentModel model);

    /**
     * Called when a document has been removed.
     * @param model document which has been removed
     */
    void documentRemoved(SingleDocumentModel model);
}
