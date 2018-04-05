package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Models command to rotate the direction of the next draw/skip command.
 *
 * @author matej
 */
public class RotateCommand implements Command {
	// Angle to rotate by
	private double angle;

	public RotateCommand(double angle) {
		this.angle = angle;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState current = ctx.getCurrentState();
		Vector2D dir = current.getDirection();
		Vector2D nextDir = dir.rotated(angle);
		current.setDirection(nextDir);
	}
}
