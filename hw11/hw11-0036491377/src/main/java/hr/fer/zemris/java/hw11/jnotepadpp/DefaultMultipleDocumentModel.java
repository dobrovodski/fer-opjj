package hr.fer.zemris.java.hw11.jnotepadpp;

import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Default implementation for {@link MultipleDocumentModel}.
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
    /**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
    /**
     * Saved file icon.
     */
    private static final Icon SAVED = Util.loadIcon("icons/saved.png");
    /**
     * Unsaved file icon.
     */
    private static final Icon UNSAVED = Util.loadIcon("icons/unsaved.png");
    /**
     * List of all single document models.
     */
    private List<SingleDocumentModel> singleDocuments;
    /**
     * Current document.
     */
    private SingleDocumentModel currentDocument;
    /**
     * Listeners.
     */
    private List<MultipleDocumentListener> listeners;

    /**
     * Constructor.
     */
    public DefaultMultipleDocumentModel() {
        singleDocuments = new ArrayList<>();
        listeners = new ArrayList<>();

        addChangeListener(e -> {
            int index = getSelectedIndex();
            SingleDocumentModel prev = getCurrentDocument();
            if (index < 0 || singleDocuments.size() == 0) {
                currentDocument = null;
            } else {
                currentDocument = singleDocuments.get(index);
                notifyCurrentDocumentChanged(prev, currentDocument);
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
        String newDocumentName = LocalizationProvider.getInstance().getString("newFileName");
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

    /**
     * Sets attributes of tab at given index.
     *
     * @param index index of tab
     * @param name title of tab
     * @param tooltip tooltip
     * @param icon icon of tab
     */
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
                    String.format(LocalizationProvider.getInstance().getString("saveFail"),
                            newPath.getFileName().toAbsolutePath()),
                    LocalizationProvider.getInstance().getString("error"),
                    JOptionPane.ERROR_MESSAGE);
        }

        if (!newPath.equals(model.getFilePath())) {
            model.setFilePath(newPath);
        }

        notifyCurrentDocumentChanged(currentDocument, currentDocument);
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

    /**
     * Notifies listeners when the current document changes.
     */
    private void notifyCurrentDocumentChanged(SingleDocumentModel prev, SingleDocumentModel current) {
        for (MultipleDocumentListener l : listeners) {
            l.currentDocumentChanged(prev, current);
        }
    }

    /**
     * Notifies listeners when a document has been added.
     */
    private void notifyDocumentAdded(SingleDocumentModel document) {
        for (MultipleDocumentListener l : listeners) {
            l.documentAdded(document);
        }
    }

    /**
     * Notifies listeners when a document has been removed.
     */
    private void notifyDocumentRemoved(SingleDocumentModel document) {
        for (MultipleDocumentListener l : listeners) {
            l.documentRemoved(document);
        }
    }

    /**
     * Adds a listener for when the status of the model has been changed so it can update the tab's icon and title.
     *
     * @param model model
     */
    private void addAttributesListener(SingleDocumentModel model) {
        model.addSingleDocumentListener(new SingleDocumentListener() {
            @Override
            public void documentModifyStatusUpdated(SingleDocumentModel model) {
                int index = singleDocuments.indexOf(model);
                Path path = model.getFilePath();
                Icon icon = model.isModified() ? UNSAVED : SAVED;
                String newDocumentName = LocalizationProvider.getInstance().getString("newFileName");
                if (path != null) {
                    setTabAttributes(index, path.getFileName().toString(), path.toAbsolutePath().toString(), icon);
                } else {
                    setTabAttributes(index, newDocumentName, newDocumentName, icon);
                }
            }

            @Override
            public void documentFilePathUpdated(SingleDocumentModel model) {
                documentModifyStatusUpdated(model);
            }
        });
    }
}
