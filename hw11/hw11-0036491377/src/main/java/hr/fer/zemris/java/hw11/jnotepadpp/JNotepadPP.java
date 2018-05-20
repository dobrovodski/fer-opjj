package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Path;

public class JNotepadPP extends JFrame {
    private DefaultMultipleDocumentModel model;
    private JTabbedPane tabbedPane;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JNotepadPP().setVisible(true);
        });
    }

    public JNotepadPP() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(50, 50);
        setSize(600, 600);
        model = new DefaultMultipleDocumentModel();
        tabbedPane = new JTabbedPane();
        initGUI();
    }

    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(tabbedPane, BorderLayout.CENTER);
        createActions();
        createMenus();
    }

    private void createActions() {
        setActionAttributes(openDocumentAction, "Open", "control O", KeyEvent.VK_O, "Owo");
    }

    private void setActionAttributes(Action action, String name, String keyStroke, int mnemomic, String description) {
        action.putValue(Action.NAME, name);
        action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(keyStroke));
        action.putValue(Action.MNEMONIC_KEY, mnemomic);
        action.putValue(Action.SHORT_DESCRIPTION, description);
    }

    private void createMenus() {
        JMenuBar mb = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        mb.add(fileMenu);
        fileMenu.add(new JMenuItem(openDocumentAction));

        this.setJMenuBar(mb);
    }

    private void createToolbars() {

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
                        "File " + filePath.getFileName().toAbsolutePath() + " ne postoji!",
                        "Pogreška",
                        JOptionPane.ERROR_MESSAGE);
            }

            model.addMultipleDocumentListener(new MultipleDocumentListener() {
                @Override
                public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
                    System.out.println("Here");
                }

                @Override
                public void documentAdded(SingleDocumentModel model) {
                    System.out.println("Here2");
                    tabbedPane.addTab("yeet", model.getTextComponent());
                }

                @Override
                public void documentRemoved(SingleDocumentModel model) {
                    System.out.println("Here3");
                }
            });

            model.loadDocument(filePath);
        }
    };
}
