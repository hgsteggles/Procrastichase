package hgs.tombstone.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameSettings {

	private static Preferences options = Gdx.app.getPreferences("tombstone.options");

	private GameSettings() {
	}

	public static boolean isSoundOn() {
		return options.getBoolean("sound", true);
	}

	public static void setSoundOn(boolean value) {
		options.putBoolean("sound", value);
		options.flush();
	}

	public static boolean isBonusLevelUnlocked() {
		return options.getBoolean("bonus", true);
	}
	public static void setBonusLevelUnlocked() {
		options.putBoolean("bonus", true);
		options.flush();
	}

	public static boolean isEndlessLevelUnlocked() {
		return options.getBoolean("bonus", true);
	}
	public static void setEndlessLevelUnlocked() {
		options.putBoolean("endless", true);
		options.flush();
	}
	
	public static int getHighScore() {
		return options.getInteger("highscore", 0);
	}
	
	public static void setHighScore(int score) {
		options.putInteger("highscore", score);
		options.flush();
	}
	
	public static boolean getIntroSkippable() {
		return options.getBoolean("introskip", false);
	}
	
	public static void setIntroSkippable(boolean skippable) {
		options.putBoolean("introskip", skippable);
		options.flush();
	}
}
