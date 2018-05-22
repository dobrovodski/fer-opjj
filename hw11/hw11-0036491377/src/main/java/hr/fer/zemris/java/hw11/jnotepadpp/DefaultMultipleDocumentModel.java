package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
    private List<SingleDocumentModel> singleDocuments;
    private SingleDocumentModel currentDocument;
    private List<MultipleDocumentListener> listeners;
    private static final Icon SAVED = Util.loadIcon("icons/saved.png");
    private static final Icon UNSAVED = Util.loadIcon("icons/unsaved.png");
    private static final String newDocumentName = "new document";

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
        int index = singleDocuments.indexOf(currentDocument);

        addTab(newDocumentName, sp);
        setSelectedIndex(index);
        setTabAttributes(index, newDocumentName, newDocumentName, SAVED);
        addAttributesListener(newDocument);

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
                currentDocument = m;

                int index = singleDocuments.indexOf(currentDocument);
                setSelectedIndex(index);
                notifyCurrentDocumentChanged(prev, currentDocument);
                return currentDocument;
            }
        }

        String text = Util.readFile(path.toAbsolutePath(), this);

        SingleDocumentModel model = new DefaultSingleDocumentModel(path, text);
        SingleDocumentModel prev = currentDocument;
        currentDocument = model;
        singleDocuments.add(model);

        String name = path.getFileName().toString();
        addTab(name, new JScrollPane(model.getTextComponent()));

        int index = singleDocuments.indexOf(currentDocument);
        setSelectedIndex(index);
        setTabAttributes(index, name, path.toAbsolutePath().toString(), SAVED);
        addAttributesListener(model);

        notifyCurrentDocumentChanged(prev, currentDocument);
        notifyDocumentAdded(model);

        return model;
    }

    private void setTabAttributes(int index, String name, String tooltip, Icon icon) {
        setTitleAt(index, name);
        setToolTipTextAt(index, tooltip);
        setIconAt(index, icon);
    }

    @Override
    public void saveDocument(SingleDocumentModel model, Path newPath) {
        if (model == null) {
            return;
        }

        if (newPath == null) {
            newPath = model.getFilePath();
        }

        try {
            Files.write(newPath, model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Could not save " + newPath.getFileName().toAbsolutePath() + ".",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        if (!newPath.equals(model.getFilePath())) {
            model.setFilePath(newPath);
        }

        model.setModified(false);
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

    private void addAttributesListener(SingleDocumentModel model) {
        model.addSingleDocumentListener(new SingleDocumentListener() {
            @Override
            public void documentModifyStatusUpdated(SingleDocumentModel model) {
                int index = singleDocuments.indexOf(model);
                Path path = model.getFilePath();
                Icon icon = model.isModified() ? UNSAVED : SAVED;

                if (path != null) {
                    setTabAttributes(index, path.getFileName().toString(), path.toAbsolutePath().toString(), icon);
                } else {
                    setTabAttributes(index, newDocumentName, newDocumentName, icon);
                }
            }

            @Override
            public void documentFilePathUpdated(SingleDocumentModel model) {
                System.out.println("file path updated");
            }
        });
    }
}
