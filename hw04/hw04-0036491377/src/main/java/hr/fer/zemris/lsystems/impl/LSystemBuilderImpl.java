package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.math.Vector2D;

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
		return null;
	}

	private class LSystemImpl implements LSystem {
		@Override
		public String generate(int n) {
			StringBuilder sb = new StringBuilder();
			sb.append(axiom);

			for (int i = 0; i < n; i++) {
				String current = sb.toString();
				sb.
			}

			return sb.toString();
		}

		@Override
		public void draw(int i, Painter painter) {

		}
	}
}
