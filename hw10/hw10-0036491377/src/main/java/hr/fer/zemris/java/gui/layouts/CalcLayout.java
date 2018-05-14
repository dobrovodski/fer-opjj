package hr.fer.zemris.java.gui.layouts;

import java.awt.*;
import java.util.Hashtable;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CalcLayout implements LayoutManager2 {

    private static final int ROW_COUNT = 5;
    private static final int COLUMN_COUNT = 7;
    private static final int FIRST_ELEMENT_WIDTH = 5;

    //private Component[][] components = new Component[ROW_COUNT][COLUMN_COUNT];
    private Hashtable<Component, RCPosition> components = new Hashtable<>();
    Component first;
    private int spacing;

    public CalcLayout(int spacing) {
        if (spacing < 0) {
            throw new IllegalArgumentException("The gap between elements must be a positive number");
        }

        this.spacing = spacing;
    }

    public CalcLayout() {
        this(0);
    }

    private RCPosition toRCPosition(String constraints) {
        String[] posString = constraints.split(",");
        int[] pos = new int[2];

        try {
            pos[0] = Integer.parseInt(posString[0]);
            pos[1] = Integer.parseInt(posString[1]);
        } catch (NumberFormatException ex) {
            throw new CalcLayoutException("Could not convert to RCPosition: " + constraints);
        }

        return new RCPosition(pos[0], pos[1]);
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        Objects.requireNonNull(comp, "Component cannot be null.");

        if (constraints instanceof String) {
            constraints = toRCPosition((String) constraints);
        }

        if (!(constraints instanceof RCPosition)) {
            throw new CalcLayoutException("Constraints must be of type RCPosition.");
        }

        RCPosition pos = (RCPosition) constraints;
        int row = pos.getRow();
        int column = pos.getColumn();

        if (row < 1 || row > 5 || column < 1 || column > 7) {
            throw new CalcLayoutException("Constraints must be within the boundaries [1, 5]x[1, 7].");
        }

        if (row == 1 && (column > 1 && column < 6)) {
            throw new CalcLayoutException("The first row only accepts columns 1, 6 and 7.");
        }

        if (components.containsValue(pos)) {
            throw new CalcLayoutException("There cannot be two components with the same constraints.");
        }

        if (row == 1 && column == 1) {
            first = comp;
        }

        components.put(comp, pos);
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0.5f;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0.5f;
    }

    @Override
    public void invalidateLayout(Container target) {
        components = new Hashtable<>();
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {
        components.remove(comp);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return layoutSize(parent, Math::max, Component::getPreferredSize);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return layoutSize(parent, Math::max, Component::getMinimumSize);
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return layoutSize(target, Math::min, Component::getMaximumSize);
    }

    public Dimension layoutSize(Container container, BiFunction<Integer, Integer, Integer> predicate,
            Function<Component, Dimension> getSize) {
        Insets insets = container.getInsets();
        int insetWidth = insets.left + insets.right;
        int insetHeight = insets.top + insets.bottom;
        int gapWidth = (COLUMN_COUNT - 1) * spacing;
        int gapHeight = (ROW_COUNT - 1) * spacing;

        int w = -1;
        int h = -1;
        Component firstComponent = first;
        for (Component component : components.keySet()) {
            if (component == null || component == firstComponent) {
                continue;
            }

            Dimension dimension = getSize.apply(component);
            // Some components might not care about its size and will return null
            if (dimension == null) {
                continue;
            }

            if (w == -1) { // Forcibly set width to the first width you see
                w = dimension.width;
            } else {
                w = predicate.apply(dimension.width, w);
            }

            if (h == -1) { // Forcibly set height to the first height you see
                h = dimension.height;
            } else {
                h = predicate.apply(dimension.height, h);
            }
        }

        int componentHeight = ROW_COUNT * h;
        int componentWidth;

        // In case the (1, 1) component exists, get its width to check total component width
        // Total component width will either be (first width) + (number of leftover components * size)
        // or it can be
        int firstWidth = 0;
        if (firstComponent != null) {
            firstWidth = getSize.apply(firstComponent).width;
        }

        if (firstWidth >= FIRST_ELEMENT_WIDTH * w) {
            componentWidth = firstWidth + (COLUMN_COUNT - FIRST_ELEMENT_WIDTH) * w;
        } else {
            componentWidth = COLUMN_COUNT * w;
        }

        if (w == -1 && h == -1) {
            return null;
        }

        int totalWidth = insetWidth + gapWidth + componentWidth;
        int totalHeight = insetHeight + gapHeight + componentHeight;
        return new Dimension(totalWidth, totalHeight);
    }

    @Override
    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();
        Hashtable<Component, RCPosition> ht = new Hashtable<>();
        for (Component component : components.keySet()) {

        }
    }
}
