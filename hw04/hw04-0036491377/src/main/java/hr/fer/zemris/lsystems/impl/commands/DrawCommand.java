package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

import java.awt.Color;

public class DrawCommand implements Command {
	private double step;

	public DrawCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState current = ctx.getCurrentState();
		Color color = current.getColor();
		Vector2D pos = current.getPosition();
		Vector2D d = current.getDirection().scaled(step);
		Vector2D nextPos = pos.translated(d);

		Vector2D truePos = pos.scaled(current.getLength());
		Vector2D trueNextPos = nextPos.scaled(current.getLength());
		//TODO: size 1.0f
		painter.drawLine(truePos.getX(), truePos.getY(), trueNextPos.getX(), trueNextPos.getY(), color, 1.0f);

		current.setPosition(nextPos);
	}
}
