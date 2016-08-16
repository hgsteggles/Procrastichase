package hgs.tombstone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import hgs.tombstone.assets.Assets;
import hgs.tombstone.assets.GameArt;
import hgs.tombstone.elements.FontHolder;
import hgs.tombstone.elements.GameSettings;
import hgs.tombstone.elements.ActionResolver;
import hgs.tombstone.input.InputManager;
import hgs.tombstone.screens.*;

public class TombstoneGame extends ApplicationAdapter {
	static public final int WIDTH = 800;
	static public final int HEIGHT = 600;

	static public ActionResolver actionResolver;

	private AssetManager manager;

	private BasicScreen screen;
	private Music soundtrack;

	double dt = 0.01;
	double accumulator = 0.0;

	public TombstoneGame(ActionResolver actionResolver) {
		this.actionResolver = actionResolver;
	}
	
	@Override
	public void create () {
		Gdx.input.setCatchBackKey(true);
		InputManager.create();

		manager = new AssetManager();
		Assets.load(manager);
		manager.finishLoading();
		Assets.done(manager);

		GameArt.load(manager);

		setScreen(new BufferScreen(this));
	}

	@Override
	public void render () {
		Gdx.graphics.getGL20().glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		if (screen != null) {
			if (InputManager.screenInput.isBackPressedLast()) {
				screen.backPressed();
			}

			double frameTime = Gdx.graphics.getDeltaTime();

			if (frameTime > 0.25)
				frameTime = 0.25;

			accumulator += frameTime;

			while (accumulator >= dt) {
				screen.update((float)dt);
				InputManager.reset();
				accumulator -= dt;
			}

			screen.render((float)(accumulator / dt));


			if (soundtrack != null) {
				soundtrack.setVolume(GameSettings.isSoundOn() ? 1.0f : 0.0f);
			}
		}
	}

	@Override
	public void dispose() {
		if (screen != null) {
			screen.dispose();
		}
		manager.dispose();
	}

	@Override
	public void pause() {
		if (screen != null) {
			screen.pause();
		}
	}

	@Override
	public void resume() {

		if (screen != null) {
			screen.resume();
		}
	}

	@Override
	public void resize(int width, int height) {
		if (screen != null) {
			screen.resize(width, height);
		}
	}

	public void setScreen(BasicScreen newScreen) {
		if (newScreen != null) {
			if (!(newScreen.isOverlay()) && this.screen != null) { // New screen will replace current
				this.screen.dispose();
			}
			this.screen = newScreen;
			this.screen.show();
			this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	}

	public void exit() {
		Gdx.app.exit();
	}

	public void startSoundtrack() {
		soundtrack = manager.get("sounds/soundtrack.ogg", Music.class);
		soundtrack.setLooping(true);
		soundtrack.play();
		if (!GameSettings.isSoundOn()) {
			soundtrack.setVolume(0.0f);
		}
	}

	public AssetManager getAssetManager() {
		return manager;
	}
}
