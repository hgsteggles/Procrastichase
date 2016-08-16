package hgs.tombstone.screens;

import hgs.tombstone.TombstoneGame;
import hgs.tombstone.screens.BasicScreen;

public class BufferScreen extends BasicScreen {
	//First frame on android was a few seconds of deltatime for some reason
	//This screen eats up the first frame so that SplashScreen works correctly
	public BufferScreen(TombstoneGame app) {
		super(app);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		game.setScreen(new SplashScreen(game));
	}

	@Override
	public void backPressed() {

	}
}
