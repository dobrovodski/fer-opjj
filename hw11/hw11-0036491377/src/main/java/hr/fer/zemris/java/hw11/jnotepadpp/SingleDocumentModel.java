package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import java.nio.file.Path;

/**
 * SingleDocumentModel represents a model of a single document. It contains information about the file path, document
 * modification status and it holds a reference to the Swing component which is used for editing.
 *
 * @author matej
 */
public interface SingleDocumentModel {
    /**
     * Returns text area which is used for editing.
     *
     * @return text area used for editing
     */
    JTextArea getTextComponent();

    /**
     * Returns the file path of the model. Can be null for new documents.
     *
     * @return file path of model
     */
    Path getFilePath();

    /**
     * Sets file path and notifies listeners.
     *
     * @param path path to set
     */
    void setFilePath(Path path);

    /**
     * Returns true if the document has been modified since it was last saved, false otherwise.
     *
     * @return true if the document has been modified since it was last saved, false otherwise
     */
    boolean isModified();

    /**
     * Sets the modified flag
     *
     * @param modified flag to set
     */
    void setModified(boolean modified);

    /**
     * Adds listener to the model.
     *
     * @param l listener to add
     */
    void addSingleDocumentListener(SingleDocumentListener l);

    /**
     * Removes listener from the model.
     *
     * @param l listener to remove
     */
    void removeSingleDocumentListener(SingleDocumentListener l);

}
