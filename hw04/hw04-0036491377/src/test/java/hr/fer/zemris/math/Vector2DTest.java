package hr.fer.zemris.math;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit tests for Vector2D.
 *
 * @author matej
 * @see <a href=
 * "http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html">
 * Naming standards for unit tests </a>
 */
public class Vector2DTest {
	private static final double EPS = 1E-6;

	@Test
	public void GetX_SimpleValue_Retrieved() {
		double x = 485.33;
		Assert.assertEquals(x, new Vector2D(x, 0).getX(), EPS);
	}

	@Test
	public void GetY_SimpleValue_Retrieved() {
		double y = -348.3333;
		Assert.assertEquals(y, new Vector2D(0, y).getY(), EPS);
	}

	@Test
	public void Translate_TranslateByZero_Unchanged() {
		double x = 3.4;
		double y = 4;
		Vector2D v = new Vector2D(x, y);
		Vector2D translate = new Vector2D(0, 0);
		v.translate(translate);
		Assert.assertEquals(v, new Vector2D(x, y));
	}

	@Test
	public void Translate_TranslateByValues_Translated() {
		double x = 3.4;
		double y = 4;
		double tx = 4;
		double ty = -3;
		Vector2D v = new Vector2D(x, y);
		Vector2D translate = new Vector2D(tx, ty);
		v.translate(translate);
		Assert.assertEquals(v, new Vector2D(x + tx, y + ty));
	}

	@Test
	public void Translated_TranslateByZero_Unchanged() {
		Vector2D v = new Vector2D(3.4, 4);
		Vector2D translate = new Vector2D(0, 0);
		Vector2D vTranslated = v.translated(translate);
		Assert.assertEquals(v, vTranslated);
	}

	@Test
	public void Translated_TranslateByValues_Translated() {
		double x = 3.4;
		double y = 4;
		double tx = 7.2;
		double ty = 1.3;
		Vector2D v = new Vector2D(x, y);
		Vector2D translate = new Vector2D(tx, ty);
		Vector2D vTranslated = v.translated(translate);

		Assert.assertEquals(vTranslated.getX(), x + tx, EPS);
		Assert.assertEquals(vTranslated.getY(), y + ty, EPS);
	}

	@Test
	public void Rotate_RotateBy60_Rotated() {
		double x = 3;
		double y = 7;
		double angle = 60.0;

		double rx = -4.56217782649;
		double ry = 6.098076211353;
		Vector2D v = new Vector2D(x, y);
		v.rotate(angle);

		Assert.assertEquals(rx, v.getX(), EPS);
		Assert.assertEquals(ry, v.getY(), EPS);
	}

	@Test
	public void Rotate_RotateBy0_Unchanged() {
		double x = 3;
		double y = 7;
		double angle = 0;

		Vector2D v = new Vector2D(x, y);
		v.rotate(angle);

		Assert.assertEquals(x, v.getX(), EPS);
		Assert.assertEquals(y, v.getY(), EPS);
	}

	@Test
	public void Rotated_RotateBy130_Rotated() {
		double x = 3;
		double y = 7;
		double angle = 130.0;

		double rx = -7.29067393089;
		double ry = -2.20137993844;
		Vector2D v = new Vector2D(x, y);
		Vector2D vr = v.rotated(angle);

		Assert.assertEquals(rx, vr.getX(), EPS);
		Assert.assertEquals(ry, vr.getY(), EPS);
	}


	@Test
	public void Scale_ScaleBy3_Scaled() {
		double x = 3;
		double y = 7;
		double scalar = 3;
		Vector2D v = new Vector2D(x, y);

		v.scale(scalar);
		Assert.assertEquals(x * scalar, v.getX(), EPS);
		Assert.assertEquals(y * scalar, v.getY(), EPS);
	}

	@Test
	public void Scale_ScaleBy0_ZeroVector() {
		double x = 3;
		double y = 7;
		double scalar = 0;
		Vector2D v = new Vector2D(x, y);

		v.scale(scalar);
		Assert.assertEquals(0, v.getX(), EPS);
		Assert.assertEquals(0, v.getY(), EPS);
	}

	@Test
	public void Scaled_ScaledBy2_Scaled() {
		double x = 3;
		double y = 7;
		double scalar = 2;

		Vector2D v = new Vector2D(x, y);
		Vector2D vs = v.scaled(scalar);

		Assert.assertEquals(x * scalar, vs.getX(), EPS);
		Assert.assertEquals(y * scalar, vs.getY(), EPS);
	}

	@Test
	public void Copy_RandomValues_Copied() {
		double x = 11;
		double y = -3.6;
		Vector2D v = new Vector2D(x, y);
		Vector2D vc = v.copy();

		Assert.assertEquals(v.getX(), vc.getX(), EPS);
		Assert.assertEquals(v.getY(), vc.getY(), EPS);
	}

	@Test
	public void Normalize_SimpleValues_Normalized() {
		double x = -34;
		double y = 55;
		double nx = -0.525822;
		double ny = 0.850595;
		Vector2D v = new Vector2D(x, y);
		v.normalize();

		Assert.assertEquals(nx, v.getX(), EPS);
		Assert.assertEquals(ny, v.getY(), EPS);
	}

	@Test
	public void Normalized_SimpleValues_Normalized() {
		double x = 1.1;
		double y = 1000;
		double nx = 0.0011;
		double ny = 0.999999;
		Vector2D v = new Vector2D(x, y);
		Vector2D vn = v.normalized();

		Assert.assertEquals(nx, vn.getX(), EPS);
		Assert.assertEquals(ny, vn.getY(), EPS);
	}
}
