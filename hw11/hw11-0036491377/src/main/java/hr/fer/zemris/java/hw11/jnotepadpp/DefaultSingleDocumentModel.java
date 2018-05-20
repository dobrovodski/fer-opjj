package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DefaultSingleDocumentModel implements SingleDocumentModel {
    private JTextArea textComponent;
    private Path filePath;
    private boolean modified;
    private List<SingleDocumentListener> listeners;

    public DefaultSingleDocumentModel(Path filePath, String content) {
        this.textComponent = new JTextArea(content);
        this.filePath = filePath;
        this.listeners = new ArrayList<>();
    }

    @Override
    public JTextArea getTextComponent() {
        return textComponent;
    }

    @Override
    public Path getFilePath() {
        return filePath;
    }

    @Override
    public void setFilePath(Path path) {
        this.filePath = path;
        notifyFilePathChanged();
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void setModified(boolean modified) {
        this.modified = modified;
        notifyModifiedChanged();
    }

    @Override
    public void addSingleDocumentListener(SingleDocumentListener l) {
        listeners.add(l);
    }

    @Override
    public void removeSingleDocumentListener(SingleDocumentListener l) {
        // TODO: concurrent modification
        listeners.remove(l);
    }

    private void notifyModifiedChanged() {
        for (SingleDocumentListener l : listeners) {
            l.documentModifyStatusUpdated(this);
        }
    }

    private void notifyFilePathChanged() {
        for (SingleDocumentListener l : listeners) {
            l.documentFilePathUpdated(this);
        }
    }
}
