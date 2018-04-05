package demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Demo class for L-Systems.
 */
public class Glavni3 {
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}
}
