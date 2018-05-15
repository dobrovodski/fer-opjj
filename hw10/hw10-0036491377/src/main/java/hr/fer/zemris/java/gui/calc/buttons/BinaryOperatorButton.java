package hr.fer.zemris.java.gui.calc.buttons;

import hr.fer.zemris.java.gui.calc.CalcModel;

import java.util.function.DoubleBinaryOperator;

public class BinaryOperatorButton extends CalcButton {
    private DoubleBinaryOperator op;

    public BinaryOperatorButton(String text, DoubleBinaryOperator op) {
        super(text);
        this.op = op;
    }

    @Override
    public void action(CalcModel model) {
        model.setActiveOperand(model.getValue());
        model.clear();
        model.setPendingBinaryOperation(op);
    }
}
