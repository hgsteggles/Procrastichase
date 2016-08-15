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

	public static boolean collectedAllPages() {
		return options.getBoolean("pages", false);
	}

	public static void setCollectedAllPages(boolean value) {
		options.putBoolean("pages", value);
		options.flush();
	}

	public static int endlessRank() {
		return options.getInteger("endless-rank", 4);
	}

	public static void setEndlessRank(int rank) {
		options.putInteger("endless-rank", rank);
		options.flush();
	}

	public static int getLevelsComplete() {
		return options.getInteger("levels-complete", 0);
	}

	public static void setLevelsComplete(int nlevels) {
		options.putInteger("levels-complete", nlevels);
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
