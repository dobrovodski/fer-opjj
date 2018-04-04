package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.*;
import hr.fer.zemris.math.Vector2D;

import java.awt.*;
import java.text.ParseException;

/**
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	private final Color DEFAULT_COLOR = new Color(0, 0, 0);
	private final double DEFAULT_UNIT = 0.1;
	private final double DEFAULT_SCALAR = 1;
	private final double DEFAULT_ANGLE = 0;
	private final Vector2D DEFAULT_ORIGIN = new Vector2D(0, 0);
	private final String DEFAULT_AXIOM = "";

	//
	private Dictionary commands;
	//
	private Dictionary actions;
	//
	private double unitLength;
	//
	private double unitLengthScalar;
	//
	private Vector2D origin;
	//
	private double angle;
	//
	private String axiom;
	//
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

	/**
	 *
	 * @param length
	 * @return
	 */
	@Override
	public LSystemBuilder setUnitLength(double length) {
		this.unitLength = length;
		return this;
	}

	/**
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}

	/**
	 *
	 * @param angle
	 * @return
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	/**
	 *
	 * @param axiom
	 * @return
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	/**
	 *
	 * @param scalar
	 * @return
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double scalar) {
		this.unitLengthScalar = scalar;
		return this;
	}

	/**
	 *
	 * @param c
	 * @param s
	 * @return
	 */
	@Override
	public LSystemBuilder registerProduction(char c, String s) {
		actions.put(c, s);
		return this;
	}

	/**
	 *
	 * @param c
	 * @param s
	 * @return
	 */
	@Override
	public LSystemBuilder registerCommand(char c, String s) {
		commands.put(c, s);
		return this;
	}

	/**
	 *
	 * @param strings
	 * @return
	 */
	@Override
	public LSystemBuilder configureFromText(String[] strings) {
		for (String line : strings) {
			line = line.trim().replaceAll("\\s+", " ");
			if (line.length() == 0) {
				continue;
			}

			String[] tokens = line.split(" ", 2);
			DirectiveType type = DirectiveType.getType(tokens[0]);
			switch (type) {
				case ANGLE: {
					if (tokens.length < 2) throw new LSystemBuilderException("Angle directive requires a parameter");
					double angle = parseNumber(tokens[1]);
					this.setAngle(angle);
					break;
				}
				case AXIOM: {
					if (tokens.length < 2) throw new LSystemBuilderException("Axiom directive requires a parameter");
					String axiom = tokens[1];
					this.setAxiom(axiom);
					break;
				}
				case ORIGIN: {
					// Assuming that the origin points can't be fractional
					if (tokens.length < 2) throw new LSystemBuilderException("Origin directive requires two numbers");
					String[] numbers = tokens[1].split(" ");
					if (numbers.length < 2) throw new LSystemBuilderException("Origin directive requires two numbers");
					double x = Double.parseDouble(numbers[0]);
					double y = Double.parseDouble(numbers[1]);
					this.setOrigin(x, y);
					break;
				}
				case SCALAR: {
					if (tokens.length < 2) throw new LSystemBuilderException("Scalar directive requires a parameter");
					double scalar = parseNumber(tokens[1]);
					this.setUnitLengthDegreeScaler(scalar);
					break;
				}
				case COMMAND: {
					if (tokens.length < 2)
						throw new LSystemBuilderException("Command directive requires at least two parameters");
					String[] commandTokens = tokens[1].split(" ", 2);
					char name = commandTokens[0].charAt(0);
					String command = commandTokens[1];
					this.registerCommand(name, command);
					break;
				}
				case PRODUCTION: {
					if (tokens.length < 2)
						throw new LSystemBuilderException("Production directive requires at least two parameters");
					String[] productionTokens = tokens[1].split(" ", 2);
					char name = productionTokens[0].charAt(0);
					String production = productionTokens[1];
					this.registerProduction(name, production);
					break;
				}
				case UNIT_LENGTH: {
					if (tokens.length < 2) throw new LSystemBuilderException("Angle directive requires a parameter");
					double step = parseNumber(tokens[1]);
					this.setUnitLength(step);
					break;
				}
			}
		}

		return this;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	/**
	 *
	 */
	private class LSystemImpl implements LSystem {
		/**
		 *
		 * @param n
		 * @return
		 */
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

		/**
		 *
		 * @param i
		 * @param painter
		 */
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

				String[] tokens = command.split(" ", 2);
				CommandType type = CommandType.getType(tokens[0]);
				switch (type) {
					case DRAW: {
						double step = parseNumber(tokens[1]) * Math.pow(unitLengthScalar, i);
						new DrawCommand(step).execute(ctx, painter);
						break;
					}
					case SKIP: {
						double step = parseNumber(tokens[1]) * Math.pow(unitLengthScalar, i);
						new SkipCommand(step).execute(ctx, painter);
						break;
					}
					case SCALE: {
						double scale = parseNumber(tokens[1]) * Math.pow(unitLengthScalar, i);
						new ScaleCommand(scale).execute(ctx, painter);
						break;
					}
					case ROTATE: {
						double angle = parseNumber(tokens[1]);
						new RotateCommand(angle).execute(ctx, painter);
						break;
					}
					case PUSH: {
						new PushCommand().execute(ctx, painter);
						break;
					}
					case POP: {
						new PopCommand().execute(ctx, painter);
						break;
					}
					case COLOR: {
						String color = tokens[1];
						new ColorCommand(Color.decode("#" + color)).execute(ctx, painter);
						break;
					}
				}
			}
		}
	}

	/**
	 *
	 * @param num
	 * @return
	 */
	double parseNumber(String num) {
		if (num.contains("/")) {
			String[] digits = num.split("/");
			return Double.parseDouble(digits[0]) / Double.parseDouble(digits[1]);
		}
		return Double.parseDouble(num);
	}
}
