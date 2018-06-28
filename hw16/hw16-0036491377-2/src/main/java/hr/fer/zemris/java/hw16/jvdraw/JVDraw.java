package hr.fer.zemris.java.hw16.jvdraw;

import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.objects.GeometricalObject;
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
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Womp womp :( stisli ispiti, bit će bolje idući put :)
 * (zadaća nije dovršena)
 */
public class JVDraw extends JFrame {
    private Path currentPath;
    private DrawingModel model;
    /**
     * Loads a document through a file chooser.
     */
    private Action openDocumentAction = new AbstractAction("Open") {
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
                JOptionPane.showMessageDialog(JVDraw.this,
                        String.format("File does not exist %s", filePath.getFileName().toAbsolutePath()),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            for (int i = model.getSize() - 1; i >= 0; i--) {
                model.remove(model.getObject(i));
            }

            try {
                String[] f = new String(Files.readAllBytes(filePath)).split("\n");
                for (String s : f) {
                    GeometricalObject o = GeometricalObject.fromString(s.trim());
                    model.add(o);
                }
            } catch (IOException ignored) {
            }

            //Force update
            model.changeOrder(model.getObject(0), 0);
        }
    };
    /**
     * Saves document through file chooser.
     */
    private Action saveAsDocumentAction = new AbstractAction("Save As") {
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

            currentPath = filePath;
            int choice = 1;
            if (Files.exists(filePath)) {
                choice = JOptionPane.showConfirmDialog(JVDraw.this,
                        String.format("Replace file query %s", filePath
                                .getFileName()),
                        "Confirm Save As",
                        JOptionPane.YES_NO_OPTION);
            } else {
                // If target file doesn't exist, create and save it
                StringBuilder sb = new StringBuilder();
                for (int i = 0, n = model.getSize(); i < n; i++) {
                    sb.append(model.getObject(i).saveFormat());
                    sb.append("\n");
                }

                try {
                    Files.write(filePath, sb.toString().getBytes(StandardCharsets.UTF_8));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(JVDraw.this,
                            String.format("Save Failed: %s",
                                    currentPath.toAbsolutePath()),
                            "Error ",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            if (choice == 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0, n = model.getSize(); i < n; i++) {
                    sb.append(model.getObject(i).saveFormat());
                    sb.append("\n");
                }

                try {
                    Files.write(filePath, sb.toString().getBytes(StandardCharsets.UTF_8));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(JVDraw.this,
                            String.format("Save Failed: %s",
                                    currentPath.toAbsolutePath()),
                            "Error ",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    };
    /**
     * Saves document using associated path. If there is no path, it defers to saveAsDocumentAction.
     */
    private Action saveDocumentAction = new AbstractAction("Save") {
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

            StringBuilder sb = new StringBuilder();
            for (int i = 0, n = model.getSize(); i < n; i++) {
                sb.append(model.getObject(i).saveFormat());
                sb.append("\n");
            }

            try {
                Files.write(currentPath, sb.toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(JVDraw.this,
                        String.format("Save Failed: %s",
                                currentPath.toAbsolutePath()),
                        "Error ",
                        JOptionPane.ERROR_MESSAGE);
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

        model = new DrawingModelImpl();
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
        setJMenuBar(mb);

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
