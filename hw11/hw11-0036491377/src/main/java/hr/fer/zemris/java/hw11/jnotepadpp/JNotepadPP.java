package hr.fer.zemris.java.hw11.jnotepadpp;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LJStatusBar;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Function;

/**
 * A simplified Notepad++ clone. It provides semi-useful functions for text manipulation as well as i18n for 3 English,
 * German and Croatian.
 *
 * @author matej
 */
public class JNotepadPP extends JFrame {
    /**
     * Name of the window.
     */
    private final static String nameOfWindow = "JNotepad++";
    /**
     * Reference to the model which this frame tracks.
     */
    private DefaultMultipleDocumentModel multipleDocumentModel;
    /**
     * Form localization provider.
     */
    private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
    /**
     * Reference to status bar.
     */
    private LJStatusBar statusBar;

    /**
     * Loads a document through a file chooser.
     */
    private Action openDocumentAction = new LocalizableAction("open", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            Path filePath = chooseFile(LocalizationProvider.getInstance().getString("open"));
            if (filePath == null) {
                return;
            }

            if (!Files.isReadable(filePath)) {
                JOptionPane.showMessageDialog(JNotepadPP.this,
                        String.format(LocalizationProvider.getInstance().getString("notExist")
                                , filePath.getFileName().toAbsolutePath()),
                        LocalizationProvider.getInstance().getString("error"),
                        JOptionPane.ERROR_MESSAGE);
            }

            multipleDocumentModel.loadDocument(filePath);
        }
    };
    /**
     * Creates a brand new document (not initially saved anywhere).
     */
    private Action newDocumentAction = new LocalizableAction("new", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            multipleDocumentModel.createNewDocument();
        }
    };
    /**
     * Saves document through file chooser.
     */
    private Action saveAsDocumentAction = new LocalizableAction("saveAs", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            SingleDocumentModel doc = multipleDocumentModel.getCurrentDocument();
            if (doc == null) {
                return;
            }

            Path filePath = chooseFile(LocalizationProvider.getInstance().getString("saveAs"));
            if (filePath == null) {
                return;
            }

            int choice = 1;
            if (Files.exists(filePath)) {
                choice = JOptionPane.showConfirmDialog(JNotepadPP.this,
                        String.format(LocalizationProvider.getInstance().getString("replaceFileQuery"), filePath
                                .getFileName()),
                        LocalizationProvider.getInstance().getString("confirmSaveAs"),
                        JOptionPane.YES_NO_OPTION);
            } else {
                // If target file doesn't exist, create and save it
                multipleDocumentModel.saveDocument(doc, filePath);
            }

            if (choice == 0) {
                multipleDocumentModel.saveDocument(doc, filePath);
            }
        }
    };
    /**
     * Saves document using associated path. If there is no path, it defers to saveAsDocumentAction.
     */
    private Action saveDocumentAction = new LocalizableAction("save", flp) {
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
    /**
     * Closes the current document.
     */
    private Action closeDocumentAction = new LocalizableAction("close", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            SingleDocumentModel current = multipleDocumentModel.getCurrentDocument();
            if (current == null) {
                return;
            }

            if (!current.isModified()) {
                multipleDocumentModel.closeDocument(current);
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
    /**
     * Exits the program after checking for any unsaved modifications.
     */
    private Action exitAction = new LocalizableAction("exit", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (checkUnsavedDocuments()) {
                dispose();
            }
        }
    };
    /**
     * Copies selected area of text to clipboard.
     */
    private Action copyAction = new LocalizableAction("copy", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            SingleDocumentModel doc = multipleDocumentModel.getCurrentDocument();
            if (doc == null) {
                return;
            }

            doc.getTextComponent().copy();
        }
    };
    /**
     * Pastes the clipboard onto the position of the caret.
     */
    private Action pasteAction = new LocalizableAction("paste", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            SingleDocumentModel doc = multipleDocumentModel.getCurrentDocument();
            if (doc == null) {
                return;
            }

            doc.getTextComponent().paste();
        }
    };
    /**
     * Cuts selected text and copies it into the clipboard.
     */
    private Action cutAction = new LocalizableAction("cut", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            SingleDocumentModel doc = multipleDocumentModel.getCurrentDocument();
            if (doc == null) {
                return;
            }

            doc.getTextComponent().cut();
        }
    };
    /**
     * Displays information box containing the summary of the current file.
     */
    private Action statsAction = new LocalizableAction("stats", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            SingleDocumentModel current = multipleDocumentModel.getCurrentDocument();
            if (current == null) {
                return;
            }

            JTextArea textArea = current.getTextComponent();
            int lineCount = textArea.getLineCount();
            int characterCount = textArea.getText().length();
            int nonBlankCharacterCount = textArea.getText().replaceAll("\\s+", "").length();

            JOptionPane.showMessageDialog(JNotepadPP.this,
                    String.format(LocalizationProvider.getInstance().getString("statsSummary"),
                            nonBlankCharacterCount, lineCount, characterCount),
                    "Summary", JOptionPane.INFORMATION_MESSAGE);
        }
    };
    /**
     * Switches the language to Croatian.
     */
    private Action languageHrAction = new LocalizableAction("languageHr", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            LocalizationProvider.getInstance().setLanguage("hr");
        }
    };
    /**
     * Switches the language to German.
     */
    private Action languageDeAction = new LocalizableAction("languageDe", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            LocalizationProvider.getInstance().setLanguage("de");
        }
    };
    /**
     * Switches the language to English.
     */
    private Action languageEnAction = new LocalizableAction("languageEn", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            LocalizationProvider.getInstance().setLanguage("en");
        }
    };
    /**
     * Changes the case of the selected area to uppercase.
     */
    private Action upperCaseAction = new LocalizableAction("uppercase", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            changeSelectedCase(String::toUpperCase);
        }
    };
    /**
     * Changes the case of the selected area to lowercase.
     */
    private Action lowerCaseAction = new LocalizableAction("lowercase", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            changeSelectedCase(String::toLowerCase);
        }
    };
    /**
     * Inverts the case of the selected area.
     */
    private Action invertCaseAction = new LocalizableAction("invertcase", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            changeSelectedCase(this::invertCase);
        }

        private String invertCase(String str) {
            char[] chars = str.toCharArray();

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
    /**
     * Sorts selected lines in an ascending order.
     */
    private Action sortAscendingAction = new LocalizableAction("ascending", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            changeSelectedText(s ->
                    Arrays.stream(s)
                          .sorted((s1, s2) -> LocalizationProvider.getCollator().compare(s1, s2))
                          .toArray(String[]::new));
        }
    };
    /**
     * Sorts selected lines in a descending order.
     */
    private Action sortDescendingAction = new LocalizableAction("descending", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            changeSelectedText(s ->
                    Arrays.stream(s)
                          .sorted((s1, s2) -> LocalizationProvider.getCollator().compare(s2, s1))
                          .toArray(String[]::new));
        }
    };
    /**
     * Removes non-unique lines from the selected ones.
     */
    private Action uniqueLinesAction = new LocalizableAction("unique", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            changeSelectedText(s -> Arrays.stream(s).distinct().toArray(String[]::new));
        }
    };

    /**
     * Used to manipulate selected rows of text using the given strategy, eg. sorting and removing non-unique lines.
     *
     * @param changeStrategy strategy used to manipulate selected rows
     */
    private void changeSelectedText(Function<String[], String[]> changeStrategy) {
        SingleDocumentModel document = multipleDocumentModel.getCurrentDocument();
        if (document == null) {
            return;
        }

        JTextArea ta = document.getTextComponent();
        try {
            int startPos = ta.getSelectionStart();
            int endPos = ta.getSelectionEnd();

            int startLine = ta.getLineOfOffset(startPos);
            int endLine = ta.getLineOfOffset(endPos);

            int start = ta.getLineStartOffset(startLine);
            int end = ta.getLineEndOffset(endLine);
            int len = Math.abs(end - start);

            String text = ta.getText(start, len);
            ta.getDocument().remove(start, len);

            String[] rows = text.split("\\r?\\n");
            rows = changeStrategy.apply(rows);
            text = String.join("\n", rows) + "\n";

            ta.getDocument().insertString(start, text, null);
        } catch (BadLocationException ignored) {
        }
    }

    /**
     * Constructor.
     */
    public JNotepadPP() {
        multipleDocumentModel = new DefaultMultipleDocumentModel();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(50, 50);
        setSize(600, 600);
        setTitle(nameOfWindow);
        initGUI();
    }

    /**
     * Entry point of the program.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                UnsupportedLookAndFeelException ignored) {
        }

        SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
    }

    /**
     * Initializes the GUI.
     */
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

        multipleDocumentModel.addMultipleDocumentListener(new MultipleDocumentListener() {
            @Override
            public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
                if (previousModel != null) {
                    previousModel.getTextComponent().removeCaretListener(statusBar.getCaretListener());
                }

                if (currentModel != null) {
                    updateCaret(currentModel);
                    updateTitle(currentModel);
                }

                if (currentModel == null) {
                    updateTitle(null);
                }
            }

            @Override
            public void documentAdded(SingleDocumentModel model) {
                model.getTextComponent().addCaretListener(statusBar.getCaretListener());
            }

            @Override
            public void documentRemoved(SingleDocumentModel model) {
                model.getTextComponent().removeCaretListener(statusBar.getCaretListener());
            }
        });

        statusBar = new LJStatusBar(flp);
        cp.add(statusBar, BorderLayout.SOUTH);

        createToolbar();
        createActions();
        createMenus();
    }

    /**
     * Sets all action attributes (keyStroke and mnemonic).
     */
    private void createActions() {
        // It is possible to localize the mnemonics as well, but we skip doing that here
        setActionAttributes(newDocumentAction, "control N", KeyEvent.VK_N);
        setActionAttributes(openDocumentAction, "control O", KeyEvent.VK_O);
        setActionAttributes(saveDocumentAction, "control S", KeyEvent.VK_S);
        setActionAttributes(saveAsDocumentAction, "control alt S", KeyEvent.VK_A);
        setActionAttributes(closeDocumentAction, "control W", KeyEvent.VK_C);
        setActionAttributes(exitAction, "alt F4", KeyEvent.VK_X);

        setActionAttributes(cutAction, "control X", KeyEvent.VK_T);
        setActionAttributes(copyAction, "control C", KeyEvent.VK_C);
        setActionAttributes(pasteAction, "control V", KeyEvent.VK_P);

        setActionAttributes(statsAction, "control alt T", KeyEvent.VK_U);

        setActionAttributes(languageEnAction, "control alt E", KeyEvent.VK_E);
        setActionAttributes(languageDeAction, "control alt D", KeyEvent.VK_D);
        setActionAttributes(languageHrAction, "control alt H", KeyEvent.VK_H);

        setActionAttributes(upperCaseAction, "control shift U", KeyEvent.VK_D);
        setActionAttributes(lowerCaseAction, "control U", KeyEvent.VK_H);
    }

    /**
     * Sets action's attributes.
     *
     * @param action action
     * @param keyStroke keyStroke to use
     * @param mnemonic mnemonic to use
     */
    private void setActionAttributes(Action action, String keyStroke, int mnemonic) {
        action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(keyStroke));
        action.putValue(Action.MNEMONIC_KEY, mnemonic);
    }

    /**
     * Creates the menus for the GUI.
     */
    private void createMenus() {
        JMenuBar mb = new JMenuBar();

        JMenu fileMenu = new LJMenu("file", flp);
        mb.add(fileMenu);
        fileMenu.add(new JMenuItem(openDocumentAction));
        fileMenu.add(new JMenuItem(newDocumentAction));
        fileMenu.add(new JMenuItem(saveDocumentAction));
        fileMenu.add(new JMenuItem(saveAsDocumentAction));
        fileMenu.add(new JMenuItem(closeDocumentAction));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem(exitAction));

        JMenu editMenu = new LJMenu("edit", flp);
        mb.add(editMenu);
        editMenu.add(new JMenuItem(cutAction));
        editMenu.add(new JMenuItem(copyAction));
        editMenu.add(new JMenuItem(pasteAction));

        JMenu viewMenu = new LJMenu("view", flp);
        mb.add(viewMenu);
        viewMenu.add(new JMenuItem(statsAction));


        JMenu languageMenu = new LJMenu("languages", flp);
        mb.add(languageMenu);
        languageMenu.add(new JMenuItem(languageEnAction));
        languageMenu.add(new JMenuItem(languageHrAction));
        languageMenu.add(new JMenuItem(languageDeAction));

        JMenu toolMenu = new LJMenu("tools", flp);
        mb.add(toolMenu);
        JMenu caseMenu = new LJMenu("changeCase", flp);
        caseMenu.add(new JMenuItem(upperCaseAction));
        caseMenu.add(new JMenuItem(lowerCaseAction));
        caseMenu.add(new JMenuItem(invertCaseAction));
        toolMenu.add(caseMenu);
        JMenu sortMenu = new LJMenu("sort", flp);
        toolMenu.add(sortMenu);
        sortMenu.add(new JMenuItem(sortAscendingAction));
        sortMenu.add(new JMenuItem(sortDescendingAction));
        toolMenu.add(new JMenuItem(uniqueLinesAction));

        this.setJMenuBar(mb);
    }

    /**
     * Creates the toolbar.
     */
    private void createToolbar() {
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
        tb.addSeparator();
        tb.add(createActionButton(statsAction, "icons/summary.png"));

        getContentPane().add(tb, BorderLayout.PAGE_START);
    }

    /**
     * Queries the user about an unsaved document.
     *
     * @param doc document
     *
     * @return user's choice (yes - 0, no - 1, cancel - 2)
     */
    private int queryForUnsavedDocument(SingleDocumentModel doc) {
        Path path = doc.getFilePath();
        String name = path == null ? LocalizationProvider.getInstance().getString("newFileName")
                : path.getFileName().toString();

        return JOptionPane.showConfirmDialog(
                this,
                String.format(LocalizationProvider.getInstance().getString("saveFileQuery"), name),
                LocalizationProvider.getInstance().getString("save"),
                JOptionPane.YES_NO_CANCEL_OPTION);
    }

    /**
     * Checks for unsaved documents and returns true if user hasn't cancelled the action.
     *
     * @return true if user hasn't aborted
     */
    private boolean checkUnsavedDocuments() {
        for (SingleDocumentModel m : multipleDocumentModel) {
            if (m.isModified()) {
                int n = queryForUnsavedDocument(m);

                if (n == 0) {
                    Path path = m.getFilePath();
                    if (path == null) {
                        path = chooseFile(LocalizationProvider.getInstance().getString("saveAs"));
                        if (path == null)
                            return false;
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

    /**
     * Prompts user to select a file via a file chooser.
     *
     * @param dialogTitle title of the dialog
     *
     * @return path of the selected file or null if none have been selected
     */
    private Path chooseFile(String dialogTitle) {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle(dialogTitle);

        // Cancel
        if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        return fc.getSelectedFile().toPath();
    }

    /**
     * Creates a new action button.
     *
     * @param action action
     * @param location location of the icon
     *
     * @return JButton which represents an action
     */
    private JButton createActionButton(Action action, String location) {
        JButton button = new JButton(action);
        button.setHideActionText(true);
        button.setIcon(Util.loadIcon(location));
        button.setFocusPainted(false);

        return button;
    }

    /**
     * Changes the case of the selected text using the given strategy (uppercase, lowercase...).
     *
     * @param caseStrategy strategy to use on each letter of the selected text
     */
    private void changeSelectedCase(Function<String, String> caseStrategy) {
        SingleDocumentModel current = multipleDocumentModel.getCurrentDocument();
        if (current == null) {
            return;
        }
        JTextArea editor = current.getTextComponent();
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
            text = caseStrategy.apply(text);
            doc.remove(offset, len);
            doc.insertString(offset, text, null);
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Force updates the caret. Used when the current document changes.
     *
     * @param model model
     */
    private void updateCaret(SingleDocumentModel model) {
        model.getTextComponent().addCaretListener(statusBar.getCaretListener());
        statusBar.getCaretListener().caretUpdate(new CaretEvent(model.getTextComponent()) {
            @Override
            public int getDot() {
                return model.getTextComponent().getCaret().getDot();
            }

            @Override
            public int getMark() {
                return model.getTextComponent().getCaret().getMark();
            }
        });
    }

    /**
     * Updates the window title using the given model.
     *
     * @param model model whose path will be used for the title
     */
    private void updateTitle(SingleDocumentModel model) {
        if (model == null) {
            setTitle(nameOfWindow);
            return;
        }

        Path path = model.getFilePath();
        String name = path == null ? "new" : path.toString();
        setTitle(name + " - " + nameOfWindow);
    }
}
