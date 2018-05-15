package hr.fer.zemris.java.gui.calc.buttons;

import hr.fer.zemris.java.gui.calc.CalcModel;

import java.util.function.Consumer;

public class ActionButton extends CalcButton {
    private Consumer<CalcModel> action;

    public ActionButton(String text, Consumer<CalcModel> action) {
        super(text);
        this.action = action;
    }

    @Override
    public void action(CalcModel model) {
        action.accept(model);
    }
}
