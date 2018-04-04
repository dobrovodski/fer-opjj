package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.math.Vector2D;

import java.awt.*;

public class LSystemBuilderImpl implements LSystemBuilder {
	private Dictionary commands;
	private Dictionary actions;
	private double unitLength;
	private double unitLengthScalar;
	private Vector2D origin;
	private double angle;
	protected String axiom;

	public LSystemBuilderImpl() {
		commands = new Dictionary();
		actions = new Dictionary();
		unitLength = 0.1;
		unitLengthScalar = 1;
		origin = new Vector2D(0, 0);
		angle = 0;
		axiom = "";
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
			TurtleState ts = new TurtleState(origin, new Vector2D(1, 0).rotated(angle), new Color(0, 0, 0), unitLength);
			ctx.pushState(ts);

			for (int pos = 0; pos < generated.length(); pos++) {
				char c = generated.charAt(pos);
				String command = (String) commands.get(c);

				if (command != null) {
					continue;
				}
				//TODO: strings to enum
				String[] split = command.split(" ");
				String type = split[0].toLowerCase();
				switch (type) {
					case "draw":
						double step = Double.parseDouble(split[1]);
						new DrawCommand(step);
						break;
					case "skip":
						break;
					case "scale":
						break;
					case "rotate":
						break;
					case "push":
						break;
					case "pop":
						break;
					case "color":
						break;
				}
				System.out.println("AA");
			}


		}
	}
}
