package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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

        SwingUtilities.invokeLater(() -> {
            new JNotepadPP().setVisible(true);
        });
    }

    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(multipleDocumentModel, BorderLayout.CENTER);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        createActions();
        createMenus();
        createToolbars();
    }

    private void createActions() {
        setActionAttributes(newDocumentAction, "New", "control N", KeyEvent.VK_N, "New file");
        setActionAttributes(openDocumentAction, "Open", "control O", KeyEvent.VK_O, "Open file");
        setActionAttributes(saveDocumentAction, "Save", "control S", KeyEvent.VK_S, "Save");
        setActionAttributes(saveAsDocumentAction, "Save As", "control alt S", KeyEvent.VK_A, "Save As");
        setActionAttributes(closeDocumentAction, "Close", "control W", KeyEvent.VK_W, "Close");
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
        tb.add(createActionButton(exitAction, "icons/cut.png"));
        tb.add(createActionButton(closeDocumentAction, "icons/copy.png"));
        tb.add(createActionButton(closeDocumentAction, "icons/paste.png"));

        getContentPane().add(tb, BorderLayout.PAGE_START);

    }

    private JButton createActionButton(Action action, String location) {
        JButton button = new JButton(action);
        button.setIcon(Util.loadIcon(location));
        button.setText("");
        button.setFocusPainted(false);

        return button;
    }

    private Action openDocumentAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Open file");

            // Cancel
            if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            Path filePath = fc.getSelectedFile().toPath();
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

            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Save as");

            // Cancel
            if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            Path filePath = fc.getSelectedFile().toPath();

            int choice = 1;
            if (Files.exists(filePath)) {
                choice = JOptionPane.showConfirmDialog(JNotepadPP.this,
                        filePath.getFileName() + " already exists.\nDo you want to replace it?",
                        "Confirm Save As",
                        JOptionPane.YES_NO_OPTION);
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
            multipleDocumentModel.closeDocument(multipleDocumentModel.getCurrentDocument());
        }
    };

    private Action exitAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (SingleDocumentModel m : multipleDocumentModel) {
                if (m.isModified()) {
                    System.out.println("not saved");
                }
            }

            dispose();
        }
    };
}
