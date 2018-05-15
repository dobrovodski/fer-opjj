package hr.fer.zemris.java.gui.calc.buttons;

import hr.fer.zemris.java.gui.calc.CalcModel;

import java.util.function.DoubleUnaryOperator;

public class UnaryOperatorButton extends CalcButton {
    private DoubleUnaryOperator op;

    public UnaryOperatorButton(String text, DoubleUnaryOperator op) {
        super(text);
        this.op = op;
    }

    @Override
    public void action(CalcModel model) {
        model.setValue(op.applyAsDouble(model.getValue()));
    }
}
