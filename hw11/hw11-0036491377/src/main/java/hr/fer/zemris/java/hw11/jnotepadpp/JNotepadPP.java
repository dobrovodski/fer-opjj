package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class JNotepadPP extends JFrame {
    private DefaultMultipleDocumentModel multipleDocumentModel;

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

    public JNotepadPP() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(50, 50);
        setSize(600, 600);
        multipleDocumentModel = new DefaultMultipleDocumentModel();
        initGUI();
    }

    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(multipleDocumentModel, BorderLayout.CENTER);

        createActions();
        createMenus();
        createToolbars();
    }

    private void createActions() {
        setActionAttributes(openDocumentAction, "Open", "control O", KeyEvent.VK_O, "TODO");
        setActionAttributes(newDocumentAction, "New", "control N", KeyEvent.VK_N, "TODO");
        setActionAttributes(saveDocumentAction, "Save", "control S", KeyEvent.VK_S, "TODO");
        setActionAttributes(saveAsDocumentAction, "Save As", "control alt S", KeyEvent.VK_A, "TODO");
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

        this.setJMenuBar(mb);
    }

    private void createToolbars() {
        JToolBar tb = new JToolBar("Alati");
        tb.setFloatable(true);
        tb.setDefaultB

        tb.add(createActionButton(openDocumentAction, "icons/newFile.png"));
        tb.add(createActionButton(saveDocumentAction, "icons/saveFile.png"));

        getContentPane().add(tb, BorderLayout.PAGE_START);
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
                multipleDocumentModel.saveDocument(multipleDocumentModel.getCurrentDocument(), filePath);
            }
        }
    };

    private Action saveDocumentAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Path filePath = multipleDocumentModel.getCurrentDocument().getFilePath();
            if (filePath == null) {
                saveAsDocumentAction.actionPerformed(e);
                return;
            }

            multipleDocumentModel.saveDocument(multipleDocumentModel.getCurrentDocument(), filePath);
        }
    };

    private JButton createActionButton(Action action, String location) {
        JButton button = new JButton(action);
        InputStream is = this.getClass().getResourceAsStream(location);
        byte[] bytes;

        try {
            bytes = is.readAllBytes();
        } catch (IOException e) {
            return null;
        }

        try {
            is.close();
        } catch (IOException e) {
            return null;
        }

        button.setIcon(new ImageIcon(bytes));
        button.setText("");
        button.setFocusPainted(false);

        return button;
    }
}
