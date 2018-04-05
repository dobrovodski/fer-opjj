package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Models command to push the provided state onto the context.
 *
 * @author matej
 */
public class PushCommand implements Command {
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState current = ctx.getCurrentState().copy();
		ctx.pushState(current);
	}
}
