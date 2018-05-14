package hr.fer.zemris.java.gui.layouts;

import java.awt.*;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CalcLayout implements LayoutManager2 {

    private static final int ROW_COUNT = 5;
    private static final int COLUMN_COUNT = 7;
    private static final int FIRST_ELEMENT_WIDTH = 5;

    private Component[][] components = new Component[ROW_COUNT][COLUMN_COUNT];
    private int spacing;

    private Dimension preferredSize = new Dimension();

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

    private void updatePreferredSize(Component newest, RCPosition constraints) {
        Dimension newestDim = newest.getPreferredSize();

        preferredSize.height = Math.max(preferredSize.height, newestDim.height);
        if (!(constraints.getColumn() == 1 && constraints.getColumn() == 1)) {
            preferredSize.width = Math.max(preferredSize.width, newestDim.width);
        }
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

        if (components[row - 1][column - 1] != null) {
            throw new CalcLayoutException("There cannot be two components with the same constraints.");
        }

        updatePreferredSize(comp, pos);
        components[row - 1][column - 1] = comp;
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
        components = new Component[ROW_COUNT][COLUMN_COUNT];
        preferredSize = new Dimension();
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {
        for (int i = 0; i < components.length; i++) {
            for (int j = 0; j < components[i].length; j++) {
                if (components[i][j] == comp) {
                    components[i][j] = null;
                }
            }
        }
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return layoutSize(parent, Math::max);
        /*Insets insets = parent.getInsets();
        int insetWidth = insets.left + insets.right;
        int insetHeight = insets.top + insets.bottom;
        int gapWidth = (COLUMN_COUNT - 1) * spacing;
        int gapHeight = (ROW_COUNT - 1) * spacing;
        int componentHeight = ROW_COUNT * preferredSize.height;

        Component first = components[0][0];
        int firstWidth = 0;
        if (first != null) {
            firstWidth = first.getPreferredSize().width;
        }

        int componentWidth;
        if (firstWidth > FIRST_ELEMENT_WIDTH * preferredSize.width) {
            componentWidth = firstWidth + (COLUMN_COUNT - FIRST_ELEMENT_WIDTH) * preferredSize.width;
        } else {
            componentWidth = COLUMN_COUNT * preferredSize.width;
        }

        int totalWidth = insetWidth + gapWidth + componentWidth;
        int totalHeight = insetHeight + gapHeight + componentHeight;

        return new Dimension(totalWidth, totalHeight);*/
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        Insets insets = parent.getInsets();
        int insetWidth = insets.left + insets.right;
        int insetHeight = insets.top + insets.bottom;
        int gapWidth = (COLUMN_COUNT - 1) * spacing;
        int gapHeight = (ROW_COUNT - 1) * spacing;


        Component first = components[0][0];
        int firstWidth = 0;
        if (first != null) {
            firstWidth = first.getPreferredSize().width;
        }

        int w = -1;
        int h = -1;
        for (Component[] componentRow : components) {
            for (Component component : componentRow) {
                if (component == null) {
                    continue;
                }
                Dimension dim = component.getPreferredSize();
                if (dim == null) {
                    continue;
                }

                if (w == -1) {
                    w = dim.width;
                } else {
                    w = Math.max(dim.width, w);
                }

                if (h == -1) {
                    h = dim.height;
                } else {
                    h = Math.max(dim.height, h);
                }
            }
        }

        int componentHeight = ROW_COUNT * h;
        int componentWidth;
        if (firstWidth > FIRST_ELEMENT_WIDTH * w) {
            componentWidth = firstWidth + (COLUMN_COUNT - FIRST_ELEMENT_WIDTH) * w;
        } else {
            componentWidth = COLUMN_COUNT * w;
        }

        int totalWidth = insetWidth + gapWidth + componentWidth;
        int totalHeight = insetHeight + gapHeight + componentHeight;

        return new Dimension(totalWidth, totalHeight);
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public Dimension layoutSize(Container container, BiFunction<Integer, Integer, Integer> predicate) {
        Insets insets = container.getInsets();
        int insetWidth = insets.left + insets.right;
        int insetHeight = insets.top + insets.bottom;
        int gapWidth = (COLUMN_COUNT - 1) * spacing;
        int gapHeight = (ROW_COUNT - 1) * spacing;

        Component first = components[0][0];
        int firstWidth = 0;
        if (first != null) {
            firstWidth = first.getPreferredSize().width;
        }

        int w = -1;
        int h = -1;

        for (Component[] componentRow : components) {
            for (Component component : componentRow) {
                if (component == null || component == first) {
                    continue;
                }

                Dimension dim = component.getPreferredSize();
                if (dim == null) {
                    continue;
                }

                if (w == -1) {
                    w = dim.width;
                } else {
                    w = predicate.apply(dim.width, w);
                }

                if (h == -1) {
                    h = dim.height;
                } else {
                    h = predicate.apply(dim.height, h);
                }
            }
        }

        int componentHeight = ROW_COUNT * h;
        int componentWidth;
        if (firstWidth >= FIRST_ELEMENT_WIDTH * w) {
            componentWidth = firstWidth + (COLUMN_COUNT - FIRST_ELEMENT_WIDTH) * w;
        } else {
            componentWidth = COLUMN_COUNT * w;
        }

        if (w == -1) {
            componentWidth = 0;
        }

        if (h == -1) {
            componentHeight = 0;
        }

        int totalWidth = insetWidth + gapWidth + componentWidth;
        int totalHeight = insetHeight + gapHeight + componentHeight;

        return new Dimension(totalWidth, totalHeight);
    }

    @Override
    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();
    }
}
