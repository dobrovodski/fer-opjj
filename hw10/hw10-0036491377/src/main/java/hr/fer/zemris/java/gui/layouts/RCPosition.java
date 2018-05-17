package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

/**
 * Represents an unmodifiable single (row, column) location.
 *
 * @author matej
 */
public class RCPosition {
    /**
     * Row.
     */
    private int row;
    /**
     * Column.
     */
    private int column;

    /**
     * Constructor
     *
     * @param row row to store
     * @param column column to store
     */
    public RCPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Returns the row.
     *
     * @return row
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column
     *
     * @return column
     */
    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RCPosition that = (RCPosition) o;
        return row == that.row &&
               column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
