package hr.fer.zemris.java.gui.calc;

/**
 * Interface for a listener which listens for changes in the given {@link CalcModel}.
 *
 * @author matej
 */
public interface CalcValueListener {
    /**
     * This method is called when the model is changed.
     *
     * @param model reference to the model
     */
    void valueChanged(CalcModel model);
}