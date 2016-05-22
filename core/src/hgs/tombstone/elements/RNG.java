package hgs.tombstone.elements;

import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by harry on 09/04/16.
 */
public class RNG {
	static private RandomXS128 rng = new RandomXS128(0);

	public static float genRange(float a, float b) {
		return a + (b - a)*genFloat();
	}

	public static float genFloat() {
		return rng.nextLong(10000) / 10000.0f;
	}

	public static int genInt(int n) {
		return rng.nextInt(n);
	}
}
