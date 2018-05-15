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
        DoubleBinaryOperator currentOperator = model.getPendingBinaryOperation();
        if (currentOperator != null && model.isActiveOperandSet()) {
            double operand = model.getActiveOperand();
            double result  = currentOperator.applyAsDouble(operand, model.getValue());

            model.setValue(result);
        }

        model.setActiveOperand(model.getValue());
        model.setPendingBinaryOperation(op);
    }
}
