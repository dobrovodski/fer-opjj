package hr.fer.zemris.java.hw16.jvdraw;

import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelImpl;
import hr.fer.zemris.java.hw16.jvdraw.swing.JObjectList;
import hr.fer.zemris.java.hw16.jvdraw.swing.JStatusBar;
import hr.fer.zemris.java.hw16.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.LineTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;

public class JVDraw extends JFrame {
    private Path currentPath;
    /**
     * Loads a document through a file chooser.
     */
    private Action openDocumentAction = new AbstractAction("open") {
        /**
         * Default serial version UID
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            Path filePath = chooseFile("Open");
            if (filePath == null) {
                return;
            }

            if (!Files.isReadable(filePath)) {
                JOptionPane.showMessageDialog(JNotepadPP.this,
                        String.format(lp.getString("notExist")
                                , filePath.getFileName().toAbsolutePath()),
                        lp.getString("error"),
                        JOptionPane.ERROR_MESSAGE);
            }

            multipleDocumentModel.loadDocument(filePath);
        }
    };
    /**
     * Saves document through file chooser.
     */
    private Action saveAsDocumentAction = new AbstractAction("saveAs") {
        /**
         * Default serial version UID
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            Path filePath = chooseFile("Save As");
            if (filePath == null) {
                return;
            }

            int choice = 1;
            if (Files.exists(filePath)) {
                choice = JOptionPane.showConfirmDialog(JVDraw.this,
                        String.format("Replace file query %s", filePath
                                .getFileName()),
                        "Confirm Save As",
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
    private Action saveDocumentAction = new AbstractAction("save") {
        /**
         * Default serial version UID
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentPath == null) {
                saveAsDocumentAction.actionPerformed(e);
                return;
            }


        }
    };

    public JVDraw() {
        setLocation(50, 50);
        setSize(600, 600);
        setTitle("JVDraw");
        initGUI();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                UnsupportedLookAndFeelException ignored) {
        }

        SwingUtilities.invokeLater(() -> new JVDraw().setVisible(true));
    }

    private void initGUI() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        DrawingModel model = new DrawingModelImpl();
        JDrawingCanvas canvas = new JDrawingCanvas(model);
        model.addDrawingModelListener(canvas);
        cp.add(canvas, BorderLayout.CENTER);

        JToolBar tb = new JToolBar();
        tb.setFloatable(false);

        JColorArea bg = new JColorArea(Color.WHITE);
        JColorArea fg = new JColorArea(Color.BLACK);
        tb.add(bg);
        tb.addSeparator(new Dimension(5, 0));
        tb.add(fg);

        tb.addSeparator();

        ButtonGroup buttonGroup = new ButtonGroup();
        JToggleButton lineButton = new JToggleButton("Line");
        JToggleButton circleButton = new JToggleButton("Circle");
        JToggleButton fillCircleButton = new JToggleButton("Fill Circle");

        lineButton.addActionListener(e -> {
            JToggleButton b = (JToggleButton) e.getSource();
            if (b.isSelected()) {
                canvas.setCurrentState(new LineTool(model, fg));
            }
        });
        circleButton.addActionListener(e -> {
            JToggleButton b = (JToggleButton) e.getSource();
            if (b.isSelected()) {
                canvas.setCurrentState(new CircleTool(model, fg));
            }
        });
        fillCircleButton.addActionListener(e -> {
            JToggleButton b = (JToggleButton) e.getSource();
            if (b.isSelected()) {
                canvas.setCurrentState(new FilledCircleTool(model, fg, bg));
            }
        });

        buttonGroup.add(lineButton);
        buttonGroup.add(circleButton);
        buttonGroup.add(fillCircleButton);
        tb.add(lineButton);
        tb.add(circleButton);
        tb.add(fillCircleButton);

        cp.add(tb, BorderLayout.NORTH);

        cp.add(new JScrollPane(new JObjectList(model)), BorderLayout.EAST);

        cp.add(new JStatusBar(fg, bg), BorderLayout.SOUTH);

        JMenuBar mb = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        mb.add(fileMenu);
        fileMenu.add(new JMenuItem(openDocumentAction));
        fileMenu.add(new JMenuItem(saveDocumentAction));
        fileMenu.add(new JMenuItem(saveAsDocumentAction));

        canvas.repaint();
    }

    private Path chooseFile(String dialogTitle) {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle(dialogTitle);

        // Cancel
        if (fc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        return fc.getSelectedFile().toPath();
    }
}
