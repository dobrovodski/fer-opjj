package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
    private List<SingleDocumentModel> singleDocuments;
    private SingleDocumentModel currentDocument;
    private List<MultipleDocumentListener> listeners;

    public DefaultMultipleDocumentModel() {
        singleDocuments = new ArrayList<>();
        listeners = new ArrayList<>();

        addChangeListener(e -> {
            int index = getSelectedIndex();
            if (index < 0 || singleDocuments.size() == 0) {
                currentDocument = null;
            } else {
                currentDocument = singleDocuments.get(index);
            }
        });
    }

    @Override
    public SingleDocumentModel createNewDocument() {
        SingleDocumentModel newDocument = new DefaultSingleDocumentModel(null, "");
        SingleDocumentModel prevDocument = currentDocument;

        currentDocument = newDocument;
        singleDocuments.add(newDocument);

        JScrollPane sp = new JScrollPane(newDocument.getTextComponent());
        String name = "new document";
        Path path = Paths.get("").toAbsolutePath().resolve(name);
        newDocument.setFilePath(path);
        addTab(path.getFileName().toString(), sp);

        int index = singleDocuments.indexOf(currentDocument);
        setSelectedIndex(index);
        setTabAttributes(index, name, path.toString(), "icons/paste.png");

        notifyCurrentDocumentChanged(prevDocument, newDocument);
        notifyDocumentAdded(newDocument);
        return newDocument;
    }

    @Override
    public SingleDocumentModel getCurrentDocument() {
        return currentDocument;
    }

    @Override
    public SingleDocumentModel loadDocument(Path path) {
        Objects.requireNonNull(path, "Path may not be null.");

        // Check if already opened
        for (SingleDocumentModel m : singleDocuments) {
            Path mPath = m.getFilePath();

            if (mPath == null) {
                continue;
            }

            if (mPath.equals(path)) {
                SingleDocumentModel prev = currentDocument;
                setEnabledAt(singleDocuments.indexOf(prev), false);
                currentDocument = m;

                int index = singleDocuments.indexOf(currentDocument);
                setSelectedIndex(index);
                setEnabledAt(index, false);
                notifyCurrentDocumentChanged(prev, currentDocument);
                return currentDocument;
            }
        }

        byte[] bytes;
        try {
            bytes = Files.readAllBytes(path);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Could not read file: " + path.getFileName().toAbsolutePath(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        String text = new String(bytes, StandardCharsets.UTF_8);

        SingleDocumentModel model = new DefaultSingleDocumentModel(path, text);
        SingleDocumentModel prev = currentDocument;
        currentDocument = model;
        singleDocuments.add(model);

        String name = path.getFileName().toString();
        addTab(name, new JScrollPane(model.getTextComponent()));

        int index = singleDocuments.indexOf(currentDocument);
        setSelectedIndex(index);
        setTabAttributes(index, name, path.toAbsolutePath().toString(), "icons/paste.png");

        notifyCurrentDocumentChanged(prev, currentDocument);
        notifyDocumentAdded(model);

        return model;
    }

    private void setTabAttributes(int index, String name, String tooltip, String icon) {
        setTitleAt(index, name);
        setToolTipTextAt(index, tooltip);
        setIconAt(index, Util.loadIcon(icon));
    }

    @Override
    public void saveDocument(SingleDocumentModel model, Path newPath) {
        if (model == null) {
            return;
        }

        Objects.requireNonNull(newPath, "Path may not be null.");
        try {
            Files.write(newPath, model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Could not save " + newPath.getFileName().toAbsolutePath() + ".",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void closeDocument(SingleDocumentModel model) {
        if (model == null) {
            return;
        }

        int size = singleDocuments.size();
        int index = singleDocuments.indexOf(model);

        if (index == -1) {
            return;
        }

        if (size == 1) {
            currentDocument = null;
        } else {
            if (index == size - 1) {
                currentDocument = singleDocuments.get(index - 1);
            } else {
                currentDocument = singleDocuments.get(index + 1);
            }
        }


        if (currentDocument != null) {
            int currentIndex = singleDocuments.indexOf(currentDocument);
            setSelectedIndex(currentIndex);
            Path path = currentDocument.getFilePath();
            String name = path.getFileName().toString();
            setTabAttributes(currentIndex, name, path.toAbsolutePath().toString(), "icons/paste.png");
        }

        singleDocuments.remove(model);
        removeTabAt(index);
        notifyCurrentDocumentChanged(model, currentDocument);
        notifyDocumentRemoved(model);
    }

    @Override
    public void addMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.add(l);
    }

    @Override
    public void removeMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.remove(l);
    }

    @Override
    public int getNumberOfDocuments() {
        return singleDocuments.size();
    }

    @Override
    public SingleDocumentModel getDocument(int index) {
        return singleDocuments.get(index);
    }

    @Override
    public Iterator<SingleDocumentModel> iterator() {
        return singleDocuments.iterator();
    }

    private void notifyCurrentDocumentChanged(SingleDocumentModel prev, SingleDocumentModel current) {
        for (MultipleDocumentListener l : listeners) {
            l.currentDocumentChanged(prev, current);
        }
    }

    private void notifyDocumentAdded(SingleDocumentModel document) {
        for (MultipleDocumentListener l : listeners) {
            l.documentAdded(document);
        }
    }

    private void notifyDocumentRemoved(SingleDocumentModel document) {
        for (MultipleDocumentListener l : listeners) {
            l.documentRemoved(document);
        }
    }

    private void setIconModified() {
        //setIconAt(, );
    }

    private void setIconSaved() {

    }
}
