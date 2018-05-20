package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import java.nio.file.Path;
import java.util.Iterator;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
    @Override
    public SingleDocumentModel createNewDocument() {
        return null;
    }

    @Override
    public SingleDocumentModel getCurrentDocument() {
        return null;
    }

    @Override
    public SingleDocumentModel loadDocument(Path path) {
        return null;
    }

    @Override
    public void saveDocument(SingleDocumentModel model, Path newPath) {

    }

    @Override
    public void closeDocument(SingleDocumentModel model) {

    }

    @Override
    public void addMultipleDocumentListener(MultipleDocumentListener l) {

    }

    @Override
    public void removeMultipleDocumentListener(MultipleDocumentListener l) {

    }

    @Override
    public int getNumberOfDocuments() {
        return 0;
    }

    @Override
    public SingleDocumentModel getDocument(int index) {
        return null;
    }

    @Override
    public Iterator<SingleDocumentModel> iterator() {
        return null;
    }
}
