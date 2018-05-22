package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Files;
import java.nio.file.Path;

public class JNotepadPP extends JFrame {
    private DefaultMultipleDocumentModel multipleDocumentModel;

    public JNotepadPP() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(50, 50);
        setSize(600, 600);
        multipleDocumentModel = new DefaultMultipleDocumentModel();
        initGUI();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
    }

    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(multipleDocumentModel, BorderLayout.CENTER);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (checkUnsavedDocuments()) {
                    dispose();
                }
            }
        });

        createActions();
        createMenus();
        createToolbars();
    }

    private void createActions() {
        setActionAttributes(newDocumentAction, "New", "control N", KeyEvent.VK_N, "New file");
        setActionAttributes(openDocumentAction, "Open", "control O", KeyEvent.VK_O, "Open file");
        setActionAttributes(saveDocumentAction, "Save", "control S", KeyEvent.VK_S, "Save");
        setActionAttributes(saveAsDocumentAction, "Save As", "control alt S", KeyEvent.VK_A, "Save As");
        setActionAttributes(closeDocumentAction, "Close", "control W", KeyEvent.VK_C, "Close");
        setActionAttributes(exitAction, "Exit", "alt F4", KeyEvent.VK_X, "Exit");

        setActionAttributes(cutAction, "Cut", "control X", KeyEvent.VK_T, "Cut");
        setActionAttributes(copyAction, "Copy", "control C", KeyEvent.VK_C, "Copy");
        setActionAttributes(pasteAction, "Paste", "control V", KeyEvent.VK_P, "Paste");
    }

    private void setActionAttributes(Action action, String name, String keyStroke, int mnemonic, String description) {
        action.putValue(Action.NAME, name);
        action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(keyStroke));
        action.putValue(Action.MNEMONIC_KEY, mnemonic);
        action.putValue(Action.SHORT_DESCRIPTION, description);
    }

    private void createMenus() {
        JMenuBar mb = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        mb.add(fileMenu);
        fileMenu.add(new JMenuItem(openDocumentAction));
        fileMenu.add(new JMenuItem(newDocumentAction));
        fileMenu.add(new JMenuItem(saveDocumentAction));
        fileMenu.add(new JMenuItem(saveAsDocumentAction));
        fileMenu.add(new JMenuItem(closeDocumentAction));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem(exitAction));

        JMenu editMenu = new JMenu("Edit");
        mb.add(editMenu);
        editMenu.add(new JMenuItem(cutAction));
        editMenu.add(new JMenuItem(copyAction));
        editMenu.add(new JMenuItem(pasteAction));

        this.setJMenuBar(mb);
    }

    private void createToolbars() {
        JToolBar tb = new JToolBar();
        tb.setFloatable(true);

        tb.add(createActionButton(newDocumentAction, "icons/newFile.png"));
        tb.add(createActionButton(openDocumentAction, "icons/openFile.png"));
        tb.add(createActionButton(saveDocumentAction, "icons/saveFile.png"));
        tb.add(createActionButton(saveAsDocumentAction, "icons/saveAsFile.png"));
        tb.add(createActionButton(closeDocumentAction, "icons/closeFile.png"));
        tb.addSeparator();
        tb.add(createActionButton(cutAction, "icons/cut.png"));
        tb.add(createActionButton(copyAction, "icons/copy.png"));
        tb.add(createActionButton(pasteAction, "icons/paste.png"));

        getContentPane().add(tb, BorderLayout.PAGE_START);

    }

    private Action openDocumentAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Path filePath = chooseFile("Open file");
            if (filePath == null) {
                return;
            }

            if (!Files.isReadable(filePath)) {
                JOptionPane.showMessageDialog(JNotepadPP.this,
                        "File " + filePath.getFileName().toAbsolutePath() + " does not exist.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            multipleDocumentModel.loadDocument(filePath);
        }
    };

    private Action newDocumentAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            multipleDocumentModel.createNewDocument();
        }
    };

    private Action saveAsDocumentAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            SingleDocumentModel doc = multipleDocumentModel.getCurrentDocument();
            if (doc == null) {
                return;
            }

            Path filePath = chooseFile("Save As");
            if (filePath == null) {
                return;
            }

            int choice = 1;
            if (Files.exists(filePath)) {
                choice = JOptionPane.showConfirmDialog(JNotepadPP.this,
                        filePath.getFileName() + " already exists.\nDo you want to replace it?",
                        "Confirm Save As",
                        JOptionPane.YES_NO_OPTION);
            } else {
                multipleDocumentModel.saveDocument(doc, filePath);
            }

            if (choice == 0) {
                multipleDocumentModel.saveDocument(doc, filePath);
            }
        }
    };

    private Action saveDocumentAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            SingleDocumentModel doc = multipleDocumentModel.getCurrentDocument();
            if (doc == null) {
                return;
            }

            Path filePath = doc.getFilePath();
            if (filePath == null) {
                saveAsDocumentAction.actionPerformed(e);
                return;
            }

            multipleDocumentModel.saveDocument(doc, filePath);
        }
    };

    private Action closeDocumentAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            SingleDocumentModel current = multipleDocumentModel.getCurrentDocument();
            if (!current.isModified()) {
                return;
            }
            int n = queryForUnsavedDocument(current);
            if (n == 0) {
                saveAsDocumentAction.actionPerformed(null);
                multipleDocumentModel.closeDocument(current);
            }
            if (n == 1) {
                multipleDocumentModel.closeDocument(current);
            }
        }
    };

    private Action exitAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (checkUnsavedDocuments()) {
                dispose();
            }
        }
    };

    private Action copyAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            SingleDocumentModel doc = multipleDocumentModel.getCurrentDocument();
            if (doc == null) {
                return;
            }

            doc.getTextComponent().copy();
        }
    };

    private Action pasteAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            SingleDocumentModel doc = multipleDocumentModel.getCurrentDocument();
            if (doc == null) {
                return;
            }

            doc.getTextComponent().paste();
        }
    };

    private Action cutAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            SingleDocumentModel doc = multipleDocumentModel.getCurrentDocument();
            if (doc == null) {
                return;
            }

            doc.getTextComponent().cut();
        }
    };

    private int queryForUnsavedDocument(SingleDocumentModel doc) {
        Path path = doc.getFilePath();
        String name = path == null ? "new document" : path.getFileName().toString();

        return JOptionPane.showConfirmDialog(
                this,
                "Save file " + name + "?",
                "Save",
                JOptionPane.YES_NO_CANCEL_OPTION);
    }

    private boolean checkUnsavedDocuments() {
        for (SingleDocumentModel m : multipleDocumentModel) {
            if (m.isModified()) {
                int n = queryForUnsavedDocument(m);

                if (n == 0) {
                    Path path = m.getFilePath();
                    if (path == null) {
                        path = chooseFile("Save As");
                        if (path == null) return false;
                    }

                    multipleDocumentModel.saveDocument(m, path);
                }
                if (n == 1) {
                    continue;
                }
                if (n == 2) {
                    return false;
                }
            }
        }

        return true;
    }

    private Path chooseFile(String dialogTitle) {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle(dialogTitle);

        // Cancel
        if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        return fc.getSelectedFile().toPath();
    }

    private JButton createActionButton(Action action, String location) {
        JButton button = new JButton(action);
        button.setIcon(Util.loadIcon(location));
        button.setText("");
        button.setFocusPainted(false);

        return button;
    }
}
