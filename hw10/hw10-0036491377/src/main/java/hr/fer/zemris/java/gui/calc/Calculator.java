package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.buttons.ActionButton;
import hr.fer.zemris.java.gui.calc.buttons.BinaryOperatorButton;
import hr.fer.zemris.java.gui.calc.buttons.CalcButton;
import hr.fer.zemris.java.gui.calc.buttons.DigitButton;
import hr.fer.zemris.java.gui.calc.buttons.UnaryOperatorButton;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

public class Calculator extends JFrame {
    private final static int X = 20;
    private final static int Y = 50;
    private final static int WIDTH = 500;
    private final static int HEIGHT = 500;
    private final static int GAP = 5;
    private CalcModel model = new CalcModelImpl();
    private boolean uninverted = true;
    private Stack<String> stack = new Stack<>();

    public static void main(String[] args) {
        new Calculator();
    }

    public Calculator() {
        setLocation(X, Y);
        setSize(WIDTH, HEIGHT);
        setTitle("Calculator");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        initGUI();
    }

    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new CalcLayout(GAP));

        JLabel l = new JLabel("", SwingConstants.RIGHT);
        cp.add(l, new RCPosition(1, 1));
        model.addCalcValueListener(m -> l.setText(m.toString()));

        // Digit buttons
        addButton(cp, new DigitButton("7"), 2, 3);
        addButton(cp, new DigitButton("4"), 3, 3);
        addButton(cp, new DigitButton("1"), 4, 3);
        addButton(cp, new DigitButton("0"), 5, 3);

        addButton(cp, new DigitButton("8"), 2, 4);
        addButton(cp, new DigitButton("5"), 3, 4);
        addButton(cp, new DigitButton("2"), 4, 4);

        addButton(cp, new DigitButton("9"), 2, 5);
        addButton(cp, new DigitButton("6"), 3, 5);
        addButton(cp, new DigitButton("3"), 4, 5);

        // Unary operator buttons
        addButton(cp, new UnaryOperatorButton("1/x", x -> 1 / x), 2, 1);
        addButton(cp, new UnaryOperatorButton("log", x -> uninverted ? Math.log10(x) : Math.pow(10, x)), 3, 1);
        addButton(cp, new UnaryOperatorButton("ln", x -> uninverted ? Math.log(x) : Math.pow(Math.E, x)), 4, 1);
        addButton(cp, new UnaryOperatorButton("sin", x -> uninverted ? Math.sin(x) : Math.asin(x)), 2, 2);
        addButton(cp, new UnaryOperatorButton("cos", x -> uninverted ? Math.cos(x) : Math.acos(x)), 3, 2);
        addButton(cp, new UnaryOperatorButton("tan", x -> uninverted ? Math.tan(x) : Math.atan(x)), 4, 2);
        addButton(cp, new UnaryOperatorButton("ctg", x -> uninverted ? Math.cos(x) / Math.sin(x) : Math.tan(x)), 5, 2);

        // Binary operator buttons
        addButton(cp, new BinaryOperatorButton("+", (x, y) -> x + y), 5, 6);
        addButton(cp, new BinaryOperatorButton("-", (x, y) -> x - y), 4, 6);
        addButton(cp, new BinaryOperatorButton("*", (x, y) -> x * y), 3, 6);
        addButton(cp, new BinaryOperatorButton("/", (x, y) -> x / y), 2, 6);
        addButton(cp, new BinaryOperatorButton("x^n", (x, y) -> uninverted ? Math.pow(x, y) : Math.pow(x, -y)), 5, 1);

        // Special action buttons
        addButton(cp, new ActionButton(".", CalcModel::insertDecimalPoint), 5, 5);
        addButton(cp, new ActionButton("clr", CalcModel::clear), 1, 7);
        addButton(cp, new ActionButton("res", CalcModel::clearAll), 2, 7);
        addButton(cp, new ActionButton("+/-", CalcModel::swapSign), 5, 4);
        addButton(cp, new ActionButton("=", m -> {
            DoubleBinaryOperator op = m.getPendingBinaryOperation();
            if (op == null) {
                return;
            }

            double operand = m.getActiveOperand();
            double result  = op.applyAsDouble(operand, m.getValue());

            m.setValue(result);
            m.clearActiveOperand();
            m.setPendingBinaryOperation(null);
        }), 1, 6);

        addButton(cp, new ActionButton("push", x -> stack.push(x.toString())), 3, 7);
        addButton(cp, new ActionButton("pop", x -> {
            if (stack.empty()) {
                JOptionPane.showMessageDialog(cp, "The stack is empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String s = stack.pop();
            x.setValue(Double.parseDouble(s));
        }), 4, 7);

        // Inverting checkbox
        JCheckBox invBtn = new JCheckBox("Inv");
        cp.add(invBtn, new RCPosition(5, 7));
        invBtn.addActionListener(e -> {
            uninverted = !uninverted;
        });

        setVisible(true);
    }

    private void addButton(Container pane, CalcButton btn, int row, int col) {
        btn.addActionListener(e -> {
            try {
                btn.action(model);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(pane, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        pane.add(btn, new RCPosition(row, col));
    }
}
