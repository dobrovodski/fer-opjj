package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Interface which models a single command to be executed to modify the state of an L-System.
 * @author matej
 * @see <a href="https://en.wikipedia.org/wiki/L-system">L-system</a>
 */
public interface Command {

	/**
	 * Method to be called when the command is executed.
	 * @param ctx context on which the command operates
	 * @param painter used to draw on the screen
	 */
	void execute(Context ctx, Painter painter);
}
