package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 *
 */
public interface Command {

	/**
	 *
	 * @param ctx
	 * @param painter
	 */
	void execute(Context ctx, Painter painter);
}
