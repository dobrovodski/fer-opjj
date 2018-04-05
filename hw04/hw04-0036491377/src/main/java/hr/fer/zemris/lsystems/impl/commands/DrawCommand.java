package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

import java.awt.Color;

/**
 * Models command to draw the current state on the screen.
 *
 * @author matej
 */
public class DrawCommand implements Command {
	// Distance to draw
	private double step;
	// Thickness of lines drawn
	private final static float DEFAULT_SIZE = 1.0f;

	public DrawCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState current = ctx.getCurrentState();
		Color color = current.getColor();
		Vector2D pos = current.getPosition();
		Vector2D d = current.getDirection().scaled(step).scaled(current.getLength());
		Vector2D nextPos = pos.translated(d);

		painter.drawLine(pos.getX(), pos.getY(), nextPos.getX(), nextPos.getY(), color, DEFAULT_SIZE);
		current.setPosition(nextPos);
	}
}
