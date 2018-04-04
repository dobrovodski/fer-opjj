package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Models command to pop the current state from the context.
 * @author matej
 */
public class PopCommand implements Command{
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}
}
