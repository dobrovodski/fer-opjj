package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author matej
 *
 */
public class RectangleTest {

	private static final double EPSILON = 1e-15;

	@Test
	public void normalRectangleArea() {
		Assert.assertEquals(8.8, Rectangle.rectangleArea(4, 2.2), EPSILON);
	}

	@Test
	public void normalRectanglePerimeter() {
		Assert.assertEquals(12.4, Rectangle.rectanglePerimeter(4, 2.2), EPSILON);
	}

	@Test
	public void squareArea() {
		Assert.assertEquals(25, Rectangle.rectangleArea(5, 5), EPSILON);
	}

	@Test
	public void squarePerimeter() {
		Assert.assertEquals(20, Rectangle.rectanglePerimeter(5, 5), EPSILON);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeArguments() {
		Rectangle.rectangleArea(-6.3, 3);
	}
}
