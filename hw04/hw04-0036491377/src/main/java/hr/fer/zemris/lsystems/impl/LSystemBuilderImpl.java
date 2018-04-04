package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.*;
import hr.fer.zemris.math.Vector2D;

import java.awt.*;

public class LSystemBuilderImpl implements LSystemBuilder {
	private final Color DEFAULT_COLOR = new Color(0, 0, 0);
	private final double DEFAULT_UNIT = 0.1;
	private final double DEFAULT_SCALAR = 1;
	private final double DEFAULT_ANGLE = 0;
	private final Vector2D DEFAULT_ORIGIN = new Vector2D(0, 0);
	private final String DEFAULT_AXIOM = "";

	private Dictionary commands;
	private Dictionary actions;
	private double unitLength;
	private double unitLengthScalar;
	private Vector2D origin;
	private double angle;
	private String axiom;
	private Color color;

	public LSystemBuilderImpl() {
		commands = new Dictionary();
		actions = new Dictionary();

		unitLength = DEFAULT_UNIT;
		unitLengthScalar = DEFAULT_SCALAR;
		origin = DEFAULT_ORIGIN;
		angle = DEFAULT_ANGLE;
		axiom = DEFAULT_AXIOM;
		color = DEFAULT_COLOR;
	}

	@Override
	public LSystemBuilder setUnitLength(double length) {
		this.unitLength = length;
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}

	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double scalar) {
		this.unitLengthScalar = scalar;
		return this;
	}

	@Override
	public LSystemBuilder registerProduction(char c, String s) {
		actions.put(c, s);
		return this;
	}

	@Override
	public LSystemBuilder registerCommand(char c, String s) {
		commands.put(c, s);
		return this;
	}

	@Override
	public LSystemBuilder configureFromText(String[] strings) {
		return null;
		//return this;
	}

	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	private class LSystemImpl implements LSystem {

		@Override
		public String generate(int n) {
			String generated = axiom;

			for (int i = 0; i < n; i++) {
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < generated.length(); j++) {
					char c = generated.charAt(j);
					String prod = (String) actions.get(c);

					if (prod == null) {
						sb.append(c);
						continue;
					}

					sb.append(prod);
				}

				generated = sb.toString();
			}

			return generated;
		}

		@Override
		public void draw(int i, Painter painter) {
			String generated = generate(i);
			Context ctx = new Context();
			TurtleState ts = new TurtleState(origin, new Vector2D(1, 0).rotated(angle), color, unitLength);
			ctx.pushState(ts);

			for (int pos = 0; pos < generated.length(); pos++) {
				char c = generated.charAt(pos);
				String command = (String) commands.get(c);

				if (command == null) {
					continue;
				}
				//TODO: strings to enum
				String[] split = command.split(" ");
				String type = split[0].toLowerCase();
				switch (type) {
					case "draw": {
						double step = Double.parseDouble(split[1]) * Math.pow(unitLengthScalar, i);
						new DrawCommand(step).execute(ctx, painter);
						break;
					}
					case "skip": {
						double step = Double.parseDouble(split[1]) * Math.pow(unitLengthScalar, i);
						new SkipCommand(step).execute(ctx, painter);
						break;
					}
					case "scale": {
						double step = Double.parseDouble(split[1]) * Math.pow(unitLengthScalar, i);
						new ScaleCommand(step).execute(ctx, painter);
						break;
					}
					case "rotate": {
						double angle = Double.parseDouble(split[1]);
						new RotateCommand(angle).execute(ctx, painter);
						break;
					}
					case "push": {
						new PushCommand().execute(ctx, painter);
						break;
					}
					case "pop": {
						new PopCommand().execute(ctx, painter);
						break;
					}
					case "color": {
						String color = split[1];
						new ColorCommand(Color.decode("#" + color)).execute(ctx, painter);
						break;
					}
				}
			}


		}
	}
}
