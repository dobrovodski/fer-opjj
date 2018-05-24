package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * This class contains a collection of {@link SingleDocumentModel} objects, a reference to the current {@link
 * SingleDocumentModel}. It provides methods for managing listeners and it also provides an interface for loading,
 * saving and closing documents.
 *
 * @author matej
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
    /**
     * Creates a new document.
     *
     * @return reference to new document
     */
    SingleDocumentModel createNewDocument();

    /**
     * Returns the current document.
     *
     * @return reference to current document
     */
    SingleDocumentModel getCurrentDocument();

    /**
     * Loads a document.
     *
     * @param path path to the document
     *
     * @return loaded document
     */
    SingleDocumentModel loadDocument(Path path);

    /**
     * Saves the document to the given path
     *
     * @param model document to save
     * @param newPath path to save to
     */
    void saveDocument(SingleDocumentModel model, Path newPath);

    /**
     * Closes the document without doing any additional checks.
     *
     * @param model document to be closed
     */
    void closeDocument(SingleDocumentModel model);

    /**
     * Adds a listener to the model.
     *
     * @param l listener to add
     */
    void addMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Removes a listener from the model
     *
     * @param l listener to remove
     */
    void removeMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Returns the number of currently open documents.
     *
     * @return number of currently open documents
     */
    int getNumberOfDocuments();

    /**
     * Returns document at given index.
     *
     * @param index index of document
     *
     * @return document at the given index
     */
    SingleDocumentModel getDocument(int index);
}
