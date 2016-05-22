package hgs.tombstone.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import hgs.tombstone.TombstoneGame;
import hgs.tombstone.assets.GameArt;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Colors;
import hgs.tombstone.elements.GameMenuButton;
import hgs.tombstone.elements.GameSettings;
import hgs.tombstone.input.InputManager;
import hgs.tombstone.systems.*;
import hgs.tombstone.world.EntityFactory;
import hgs.tombstone.world.ParticleFactory;
import hgs.tombstone.world.WorldUI;

public class EndlessFinishScreen extends BasicScreen {
	private static final float BUTTONS_X = WORLD_WIDTH / 2.0f;
	private static final float BUTTONS_Y = WORLD_HEIGHT / 4.0f + 0.5f;
	private static final float BUTTONS_SPACING = 0.7f;

	public EndlessFinishScreen(TombstoneGame game, int time) {
		super(game);

		add(new ClickSystem());
		add(new TweenSystem());
		add(new MovementSystem());
		add(new TickerSystem());
		add(new AnimationSystem());
		add(new SoundSystem());
		add(new RemovalSystem());
		add(new EmitterSystem());

		add(WorldUI.createFullBlackBG());

		addScore(time);

		if (!game.actionResolver.getSignedInGPGS())
			game.actionResolver.loginGPGS();

		if (game.actionResolver.getSignedInGPGS()) {
			game.actionResolver.submitScoreGPGS(1000*time);
			if (time >= 300)
				game.actionResolver.unlockAchievementGPGS("CgkI4qLb0q0bEAIQBQ");
		}

		boolean newHighscore = time > GameSettings.getHighScore();
		if (newHighscore) {
			GameSettings.setHighScore(time);
		}
		add(createHighScoreMessage(newHighscore));

		addMenuItem(BUTTONS_X, BUTTONS_Y, "CONTINUE", new ClickInterface() {
			@Override
			public void onClick(Entity entity) {
				continueGame();
			}
		}, 0);
		addMenuItem(BUTTONS_X, BUTTONS_Y - 1 * BUTTONS_SPACING, "MENU", new ClickInterface() {
			@Override
			public void onClick(Entity entity) {
				menuScreen();
			}
		}, 1);
	}

	private void continueGame() {
		game.setScreen(new GameScreen(game, 5, 0));
	}

	private void menuScreen() {
		game.setScreen(new MenuScreen2(game));
	}

	private void addMenuItem(float x, float y, String text, ClickInterface clickInterface, int n) {
		GameMenuButton menuButton = new GameMenuButton(x, y, 3.7f, text, clickInterface);
		menuButton.addToEngine(engine);
	}

	private void addScore(final int time) {
		Entity entity = new Entity();

		BitmapFontComponent fontComp = new BitmapFontComponent();
		fontComp.font = "retro";
		fontComp.string = WorldUI.createTimerString(time);
		fontComp.color.set(Color.WHITE);
		fontComp.centering = true;
		entity.add(fontComp);

		TransformComponent transComp = new TransformComponent();
		float x = BasicScreen.WORLD_WIDTH / 2.0f;
		float y = 5;
		float z = 12.0f;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		add(entity);
	}

	private Entity createHighScoreMessage(boolean newHighScore) {
		Entity entity = new Entity();

		BitmapFontComponent fontComp = new BitmapFontComponent();
		fontComp.font = "retro";
		fontComp.string = "High Score: " + WorldUI.createTimerString(GameSettings.getHighScore());
		fontComp.color.set(1.0f, 0.2f, 0.2f, 1.0f);
		fontComp.centering = true;
		fontComp.scale = 0.8f;
		entity.add(fontComp);

		TransformComponent transComp = new TransformComponent();
		float x = BasicScreen.WORLD_WIDTH / 2f;
		float y = BasicScreen.WORLD_HEIGHT / 2f + 1.0f;
		float z = 11f;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		return entity;
	}

	private Entity createEmitter(boolean isLeft) {
		Entity entity = new Entity();
		TransformComponent transComp = new TransformComponent();
		float x = isLeft ? 0 : BasicScreen.WORLD_WIDTH;
		float y = BasicScreen.WORLD_HEIGHT / 2f + 1.0f;
		float z = 11f;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		entity.add(ParticleFactory.createHighScoreEmitter(0, isLeft ? 4.0f : -4.0f));

		return entity;
	}

	@Override
	public void backPressed() {
		game.setScreen(new MenuScreen2(game));
	}
}