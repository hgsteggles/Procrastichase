package hgs.tombstone.elements;

import com.badlogic.gdx.graphics.Color;

public class Colors {
	static public void lerp(Color target, Color start, Color finish, float t) {
		target.r = start.r + t * (finish.r - start.r);
		target.g = start.g + t * (finish.g - start.g);
		target.b = start.b + t * (finish.b - start.b);
		target.a = start.a + t * (finish.a - start.a);
	}

	public static Color setHue (Color target, float hue, float sat, float val){
		float saturation = sat;
		while (hue < 0) hue++;
		while (hue >= 1) hue--;
		float value = val;

		float red = 0.0f;
		float green = 0.0f;
		float blue = 0.0f;

		final float hf = (hue - (int) hue) * 6.0f;
		final int ihf = (int) hf;
		final float f = hf - ihf;
		final float pv = value * (1.0f - saturation);
		final float qv = value * (1.0f - saturation * f);
		final float tv = value * (1.0f - saturation * (1.0f - f));

		switch (ihf) {
			case 0:         // Red is the dominant color
				red = value;
				green = tv;
				blue = pv;
				break;
			case 1:         // Green is the dominant color
				red = qv;
				green = value;
				blue = pv;
				break;
			case 2:
				red = pv;
				green = value;
				blue = tv;
				break;
			case 3:         // Blue is the dominant color
				red = pv;
				green = qv;
				blue = value;
				break;
			case 4:
				red = tv;
				green = pv;
				blue = value;
				break;
			case 5:         // Red is the dominant color
				red = value;
				green = pv;
				blue = qv;
				break;
		}

		return target.set(red, green, blue, target.a);
	}

	public static float calcSaturation(float r, float g, float b) {
		float min = r < g ? r : g;
		min = min  < b ? min  : b;

		float max = r > g ? r : g;
		max = max  > b ? max  : b;

		float delta = max - min;

		if ( max > 0.0 )
			return (delta / max);
		else
			return 0;
	}

	public static float calcBrightness(float r, float g, float b) {
		float max = r > g ? r : g;
		max = max  > b ? max  : b;

		return max;
	}

	public static float calcHue(float r, float g, float b) {
		float hue;

		float min = r < g ? r : g;
		min = min  < b ? min  : b;

		float max = r > g ? r : g;
		max = max  > b ? max  : b;
		// v
		float delta = max - min;
		if ( max <= 0.0) // NOTE: if Max is == 0, this divide would cause a crash
			return 0;

		if (delta == 0)
			return 0;
		else if ( r >= max )                           // > is bogus, just keeps compilor happy
			hue = ( g - b ) / delta;        // between yellow & magenta
		else {
			if ( g >= max )
				hue = 2.0f + ( b - r ) / delta;  // between cyan & yellow
			else
				hue = 4.0f + ( r - g ) / delta;  // between magenta & cyan
		}
		hue *= 60.0;                              // degrees

		if( hue < 0.0 )
			hue += 360.0;

		return hue / 360.0f;
	}
}
