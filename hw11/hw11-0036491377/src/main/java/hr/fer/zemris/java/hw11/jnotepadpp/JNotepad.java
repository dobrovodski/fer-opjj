package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class JNotepad extends JFrame {
    private JTextArea editor;
    private Path openedFilePath;
    private Action openDocumentAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Open file");
            // Cancel
            if (fc.showOpenDialog(JNotepad.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            Path filePath = fc.getSelectedFile().toPath();
            if (!Files.isReadable(filePath)) {
                JOptionPane.showMessageDialog(JNotepad.this,
                        "File " + filePath.getFileName().toAbsolutePath() + " ne postoji!",
                        "Pogreška",
                        JOptionPane.ERROR_MESSAGE);
            }

            String text = Util.readFile(filePath.toAbsolutePath(), JNotepad.this);

            editor.setText(text);
            openedFilePath = filePath;
        }
    };
    private Action saveDocumentAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (openedFilePath == null) {
                JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Save document");

                if (fc.showOpenDialog(JNotepad.this) != JFileChooser.APPROVE_OPTION) {
                    // Why?
                    JOptionPane.showMessageDialog(JNotepad.this,
                            "Ništa nije snimljeno",
                            "Upozorenje",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                openedFilePath = fc.getSelectedFile().toPath();
            }

            byte[] bytes = editor.getText().getBytes(StandardCharsets.UTF_8);
            try {
                Files.write(openedFilePath, bytes);
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(JNotepad.this,
                        "Pogreška prilikom zapisivanja datoteke: " + openedFilePath,
                        "Pogreška",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }


            JOptionPane.showMessageDialog(JNotepad.this,
                    "Datoteka snimljena.",
                    "Informacija",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    };
    private Action deleteSelectedPartAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Document doc = editor.getDocument();
            int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
            if (len == 0) {
                return;
            }

            int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
            try {
                doc.remove(offset, len);
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
        }
    };
    private Action toggleCaseAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Document doc = editor.getDocument();

            int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());

            int offset = 0;
            if (len != 0) {
                offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
            } else {
                len = doc.getLength();
            }

            try {
                String text = doc.getText(offset, len);
                text = changeCase(text);
                doc.remove(offset, len);
                doc.insertString(offset, text, null);
            } catch (BadLocationException e1) {
                e1.printStackTrace();
                return;
            }
        }

        private String changeCase(String text) {
            char[] chars = text.toCharArray();

            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                if (Character.isLowerCase(c)) {
                    chars[i] = Character.toUpperCase(c);
                } else if (Character.isUpperCase(c)) {
                    chars[i] = Character.toLowerCase(c);
                }
            }

            return new String(chars);
        }
    };
    private Action exitAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    };

    public JNotepad() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(50, 50);
        setSize(600, 600);

        initGUI();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            new JNotepad().setVisible(true);
        });
    }

    private void initGUI() {
        editor = new JTextArea("owo");
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(new JScrollPane(editor), BorderLayout.CENTER);

        createActions();
        createMenus();
        createToolbars();
    }

    private void createActions() {
        openDocumentAction.putValue(Action.NAME, "Open");
        openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control 0"));
        openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_0);
        openDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to open existing file from disk");

        saveDocumentAction.putValue(Action.NAME, "Save");
        saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        saveDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to save current file to disk");

        deleteSelectedPartAction.putValue(Action.NAME, "Delete");
        deleteSelectedPartAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F2"));
        deleteSelectedPartAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
        deleteSelectedPartAction.putValue(Action.SHORT_DESCRIPTION, "Used to delete the selected part of text");

        toggleCaseAction.putValue(Action.NAME, "Toggle");
        toggleCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F3"));
        toggleCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
        toggleCaseAction.putValue(Action.SHORT_DESCRIPTION, "Used to toggle the case of the selected part of text");

        exitAction.putValue(Action.NAME, "Exit");
        exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
        exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
        exitAction.putValue(Action.SHORT_DESCRIPTION, "Exit application");
    }

    private void createMenus() {
        JMenuBar mb = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        mb.add(fileMenu);
        fileMenu.add(new JMenuItem(openDocumentAction));
        fileMenu.add(new JMenuItem(saveDocumentAction));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem(exitAction));

        JMenu editMenu = new JMenu("Edit");
        mb.add(editMenu);

        editMenu.add(new JMenuItem(deleteSelectedPartAction));
        editMenu.add(new JMenuItem(toggleCaseAction));

        this.setJMenuBar(mb);
    }

    private void createToolbars() {
        JToolBar tb = new JToolBar("Alati");
        tb.setFloatable(true);

        tb.add(new JButton(openDocumentAction));
        tb.add(new JButton(saveDocumentAction));
        tb.addSeparator();
        tb.add(new JButton(deleteSelectedPartAction));
        tb.add(new JButton(toggleCaseAction));

        getContentPane().add(tb, BorderLayout.PAGE_START);
    }
}
