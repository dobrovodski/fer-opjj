package hr.fer.zemris.java.hw16.jvdraw.swing;

import hr.fer.zemris.java.hw16.jvdraw.geometrical.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.objects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingObjectListModel;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JObjectList extends JList<GeometricalObject> {
    private final static String PROMPT = "Change shape values";

    public JObjectList(DrawingModel model) {
        setModel(new DrawingObjectListModel(model));

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    int[] selectedIndices = getSelectedIndices();
                    if (selectedIndices.length == 0) {
                        return;
                    }

                    int prevIndex = selectedIndices[0] - 1;

                    for (int i = selectedIndices.length - 1; i >= 0; i--) {
                        model.remove(model.getObject(selectedIndices[i]));
                    }

                    if (prevIndex <= 0) {
                        prevIndex = 0;
                    }

                    if (getModel().getSize() > prevIndex) {
                        setSelectedIndex(prevIndex);
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_PLUS) {
                    model.changeOrder(getSelectedValue(), 1);
                    if (getSelectedIndex() + 1 < getModel().getSize()) {
                        setSelectedIndex(getSelectedIndex() + 1);
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_MINUS) {
                    model.changeOrder(getSelectedValue(), -1);
                    if (getSelectedIndex() - 1 >= 0) {
                        setSelectedIndex(getSelectedIndex() - 1);
                    }
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() < 2) {
                    return;
                }

                GeometricalObject obj = getSelectedValue();
                GeometricalObjectEditor editor = obj.createGeometricalObjectEditor();

                JPanel p = new JPanel();
                p.add(editor);
                int result = JOptionPane.showConfirmDialog(JObjectList.this, p, PROMPT, JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        editor.checkEditing();
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(null,
                                ex.getMessage(),
                                "Edit failed",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    editor.acceptEditing();

                    //Force update
                    if (model.getSize() >= 1) {
                        model.changeOrder(model.getObject(0), 0);
                    }
                }
            }
        });
    }
}
