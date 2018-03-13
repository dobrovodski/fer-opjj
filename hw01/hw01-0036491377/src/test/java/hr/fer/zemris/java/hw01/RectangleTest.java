package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit tests for Rectangle.
 * 
 * @see <a href=
 *      "http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html">
 *      Naming standards for unit tests </a>
 * @author matej
 *
 */
public class RectangleTest {

	private static final double EPSILON = 1e-15;

	@Test
	public void RectangleArea_NormalParemeters_Calculated() {
		Assert.assertEquals(8.8, Rectangle.rectangleArea(4, 2.2), EPSILON);
	}

	@Test
	public void RectanglePerimeterr_NormalParemeters_Calculated() {
		Assert.assertEquals(12.4, Rectangle.rectanglePerimeter(4, 2.2), EPSILON);
	}

	@Test
	public void RectangleArea_Square_Calculated() {
		Assert.assertEquals(25, Rectangle.rectangleArea(5, 5), EPSILON);
	}

	@Test
	public void RectanglePerimeter_Square_Calculated() {
		Assert.assertEquals(20, Rectangle.rectanglePerimeter(5, 5), EPSILON);
	}

	@Test(expected = IllegalArgumentException.class)
	public void RectangleArea_NegativeValueParam_ExceptionThrown() {
		Rectangle.rectangleArea(-6.3, 3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void RectanglePerimeter_NegativeValueParam_ExceptionThrown() {
		Rectangle.rectanglePerimeter(-6.3, 3);
	}
}
