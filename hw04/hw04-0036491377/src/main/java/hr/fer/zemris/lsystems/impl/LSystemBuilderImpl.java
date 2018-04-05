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
 * Models an L-system builder and provides method to create and configure an L-system.
 *
 * @author matej
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	private final Color DEFAULT_COLOR = new Color(0, 0, 0);
	private final double DEFAULT_UNIT = 0.1;
	private final double DEFAULT_SCALAR = 1;
	private final double DEFAULT_ANGLE = 0;
	private final Vector2D DEFAULT_ORIGIN = new Vector2D(0, 0);
	private final String DEFAULT_AXIOM = "";

	// Maps commands
	private Dictionary commands;
	// Maps actions
	private Dictionary actions;
	// Length of a unit distance
	private double unitLength;
	// Scalar to scale length by in each iteration
	private double unitLengthScalar;
	// Origin point of system
	private Vector2D origin;
	// Direction of system
	private double angle;
	// Axiom of system
	private String axiom;
	// Color of lines to be drawn
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
	 * Sets unit length.
	 *
	 * @param length length to set it to
	 * @return configured builder
	 */
	@Override
	public LSystemBuilder setUnitLength(double length) {
		this.unitLength = length;
		return this;
	}

	/**
	 * Sets origin point.
	 *
	 * @param x x coordinate of origin
	 * @param y y coordinate of origin
	 * @return configured builder
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}

	/**
	 * Sets angle of rotation.
	 *
	 * @param angle angle of rotation
	 * @return configured builder
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	/**
	 * Sets axiom (starting point of L-system).
	 *
	 * @param axiom axiom
	 * @return configured builder
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	/**
	 * Sets scalar to scale by in each iteration.
	 *
	 * @param scalar scalar to scale by
	 * @return configured builder
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double scalar) {
		this.unitLengthScalar = scalar;
		return this;
	}

	/**
	 * Maps string representation of production to character in the production dictionary.
	 *
	 * @param c left-hand side of production
	 * @param s right-hand side of production
	 * @return configured builder
	 */
	@Override
	public LSystemBuilder registerProduction(char c, String s) {
		actions.put(c, s);
		return this;
	}

	/**
	 * Maps string representation of command to character in the command dictionary.
	 *
	 * @param c representation of command
	 * @param s operation the command is to perform
	 * @return configured builder
	 */
	@Override
	public LSystemBuilder registerCommand(char c, String s) {
		commands.put(c, s);
		return this;
	}

	/**
	 * Configures the L-system builder from configuration provided as an array of strings.
	 *
	 * @param strings array of configuration parameters
	 * @return configured L-system builder
	 * @throws LSystemBuilderException if the configuration parameters are not parsable
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
					if (tokens.length < 2)
						throw new LSystemBuilderException("Angle directive requires a parameter");

					double angle = parseNumber(tokens[1]);
					this.setAngle(angle);
					break;
				}
				case AXIOM: {
					if (tokens.length < 2)
						throw new LSystemBuilderException("Axiom directive requires a parameter");

					String axiom = tokens[1];
					this.setAxiom(axiom);
					break;
				}
				case ORIGIN: {
					// Assuming that the origin points can't be fractional
					if (tokens.length < 2)
						throw new LSystemBuilderException("Origin directive has no parameters, but requires two");

					String[] numbers = tokens[1].split(" ");
					if (numbers.length < 2)
						throw new LSystemBuilderException("Origin directive requires two numbers");

					double x = Double.parseDouble(numbers[0]);
					double y = Double.parseDouble(numbers[1]);
					this.setOrigin(x, y);
					break;
				}
				case SCALAR: {
					if (tokens.length < 2)
						throw new LSystemBuilderException("Scalar directive requires a parameter");

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
					if (tokens.length < 2)
						throw new LSystemBuilderException("Angle directive requires a parameter");

					double step = parseNumber(tokens[1]);
					this.setUnitLength(step);
					break;
				}
			}
		}

		return this;
	}

	/**
	 * Creates the system object and returns it.
	 *
	 * @return L-system object
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	/**
	 * Models L-system used to generate a fractal to be displayed.
	 *
	 * @author matej
	 */
	private class LSystemImpl implements LSystem {
		/**
		 * Generates string representation of the system created by applying production rules n times.
		 *
		 * @param n amount of times to apply productions
		 * @return string representation of fractal
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
		 * Draws L-system after i iterations using the provided painter object.
		 *
		 * @param i       number of iterations
		 * @param painter object used to display system
		 * @throws LSystemBuilderException if the action parameters are not parsable
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
						if (tokens.length < 2)
							throw new LSystemBuilderException("Draw length not specified.");

						double step = parseNumber(tokens[1]) * Math.pow(unitLengthScalar, i);
						new DrawCommand(step).execute(ctx, painter);
						break;
					}
					case SKIP: {
						if (tokens.length < 2)
							throw new LSystemBuilderException("Skip length not specified.");

						double step = parseNumber(tokens[1]) * Math.pow(unitLengthScalar, i);
						new SkipCommand(step).execute(ctx, painter);
						break;
					}
					case SCALE: {
						if (tokens.length < 2)
							throw new LSystemBuilderException("Scale factor not specified.");

						double scale = parseNumber(tokens[1]) * Math.pow(unitLengthScalar, i);
						new ScaleCommand(scale).execute(ctx, painter);
						break;
					}
					case ROTATE: {
						if (tokens.length < 2)
							throw new LSystemBuilderException("Rotation angle not specified.");

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
						if (tokens.length < 2)
							throw new LSystemBuilderException("Color not specified.");

						String color = tokens[1];
						new ColorCommand(Color.decode("#" + color)).execute(ctx, painter);
						break;
					}
				}
			}
		}
	}

	/**
	 * Utility method to parse string to a double. The string can be a fraction.
	 *
	 * @param num string representation of a fraction or double number
	 * @return parsed value
	 * @throws LSystemBuilderException if the string couldn't be parsed as a number
	 */
	private double parseNumber(String num) {
		// Ideally, this method would be in Util, but since it's the only one I might as well not pollute the package.
		if (num.contains("/")) {
			String[] digits = num.split("/");
			if (digits.length != 2) {
				throw new LSystemBuilderException("Could not parse this as a number: " + num);
			}
			
			return Double.parseDouble(digits[0]) / Double.parseDouble(digits[1]);
		}
		return Double.parseDouble(num);
	}
}
