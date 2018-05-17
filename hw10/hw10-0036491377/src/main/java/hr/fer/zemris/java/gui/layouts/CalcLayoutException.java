package hr.fer.zemris.java.gui.layouts;

/**
 * Exception to indicate when the {@link CalcLayout} is put into an illegal state.
 *
 * @author matej
 */
public class CalcLayoutException extends RuntimeException {
    /**
     * Constructor.
     * @param message message to display
     */
    public CalcLayoutException(String message) {
        super(message);
    }
}
