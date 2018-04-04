package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

import java.awt.Color;

/**
 * Models command to change the position of the state by {@code step} without drawing a line.
 * @author matej
 */
public class SkipCommand implements Command {
	// Distance to skip
	private double step;

	public SkipCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState current = ctx.getCurrentState();
		Vector2D pos = current.getPosition();
		Vector2D d = current.getDirection().scaled(step).scaled(current.getLength());
		Vector2D nextPos = pos.translated(d);

		current.setPosition(nextPos);
	}
}
