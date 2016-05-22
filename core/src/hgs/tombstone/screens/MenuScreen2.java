package hgs.tombstone.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import hgs.tombstone.TombstoneGame;
import hgs.tombstone.assets.Assets;
import hgs.tombstone.assets.GameArt;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Depths;
import hgs.tombstone.elements.GameMenuButton;
import hgs.tombstone.elements.GameMenuIconButton;
import hgs.tombstone.elements.GameSettings;
import hgs.tombstone.systems.*;
import hgs.tombstone.world.EntityFactory;
import hgs.tombstone.world.MenuWorld;
import hgs.tombstone.world.World;
import hgs.tombstone.world.WorldUI;
import org.w3c.dom.css.Rect;

public class MenuScreen2 extends BasicScreen {
	private static final float BUTTON_WIDTH = 2.8f;
	private static final float BUTTON_HEIGHT = 0.6f;
	private static final float BUTTONS_SPACING = (2.0f - 2.0f * BUTTON_HEIGHT) / 3.0f;

	private static final float BUTTONS_X = BUTTONS_SPACING + 0.5f * BUTTON_WIDTH;
	private static final float BUTTONS_Y = BUTTONS_SPACING + 0.5f * BUTTON_HEIGHT;

	private static final float BUTTONS2_X = BUTTONS_SPACING + BUTTON_WIDTH + (WORLD_WIDTH - BUTTON_WIDTH - BUTTONS_SPACING) / 2.0f;


	private MenuWorld world;
	private SoundComponent clockSoundComp;
	private Entity tournamentEntity;

	public MenuScreen2(TombstoneGame game) {
		super(game);

		add(new ClickSystem());
		add(new TweenSystem());
		add(new SoundSystem());
		add(new TickerSystem());
		add(new RemovalSystem());
		add(new LoopingBackgroundSystem());
		add(new CameraSystem());
		add(new AnimationSystem());
		add(new MovementSystem());

		world = new MenuWorld();
		world.populateLevel(engine, worldCamera);

		addClockSound();
		add(WorldUI.createFullBlackBG());
		if (GameSettings.getHighScore() > 0) {
			addHighScore();
		}

		addMenuItem(BUTTONS_X, 2.0f - BUTTONS_Y, BUTTON_WIDTH, BUTTON_HEIGHT, "STORY", new ClickInterface() {
			@Override
			public void onClick(Entity entity) {
				startGame();
			}
		});
		addMenuItem(BUTTONS_X, BUTTONS_Y, BUTTON_WIDTH, BUTTON_HEIGHT, "ENDLESS", new ClickInterface() {
			@Override
			public void onClick(Entity entity) {
				endlessScreen();
			}
		});
		addMenuItem(BUTTONS2_X + 0.8f, 1f, 1.0f, 1.0f, "?", new ClickInterface() {
			@Override
			public void onClick(Entity entity) {
				aboutScreen();
			}
		});

		addMenuIconItem(BUTTONS2_X - 0.8f, 1f, 1.0f, 1.0f, GameArt.tournament, new ClickInterface() {
			@Override
			public void onClick(Entity entity) {
				tournamentButtonDown();
			}
		});

		if (!game.actionResolver.getSignedInGPGS())
			game.actionResolver.loginGPGS();

		tournamentEntity = createTournamentEntity();
		add(tournamentEntity);
	}

	private Entity createTournamentEntity() {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.tournamentUp;
		texComp.size.set(1.0f, 1.0f);
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		transComp.body.initPosition(BasicScreen.WORLD_WIDTH - 0.6f, 0.9f, Depths.screenbgZ + 1);
		entity.add(transComp);

		ClickComponent clickComp = new ClickComponent();
		Rectangle rect = new Rectangle(0f, 0f, texComp.size.x, texComp.size.y);
		rect.setCenter(0f, 0f);
		clickComp.shape = rect;
		clickComp.clicker = new ClickInterface() {
			@Override
			public void onClick(Entity entity) {
				tournamentButtonDown();
			}
		};
		entity.add(clickComp);

		return entity;
	}

	private void tournamentButtonDown() {
		final Entity entity = new Entity();
		TickerComponent tickComp = new TickerComponent();
		tickComp.finishActive = true;
		tickComp.duration = 0.2f;
		tickComp.finish = new EventInterface() {
			@Override
			public void dispatchEvent(Entity e) {
				if (game.actionResolver.getSignedInGPGS())
					game.actionResolver.getLeaderboardGPGS();
				else
					game.actionResolver.loginGPGS();

				e.add(new RemovalComponent());
			}
		};
		entity.add(tickComp);

		engine.addEntity(entity);
	}

	private void startGame() {
		stopClock();
		game.setScreen(new GameScreen(game, 1, 0));
	}

	private void aboutScreen() {
		stopClock();
		game.setScreen(new AboutScreen(game));
	}

	private void bonusScreen() {
		stopClock();
		game.setScreen(new GameScreen(game, 4, 0));
	}

	private void endlessScreen() {
		stopClock();
		game.setScreen(new GameScreen(game, 5, 0));
	}

	private void addMenuItem(float x, float y, float w, float h, String text, ClickInterface clickInterface) {
		GameMenuButton menuButton = new GameMenuButton(x, y, Depths.hudZ + 1, w, h, text, clickInterface, false);
		menuButton.addToEngine(engine);
	}

	private void addMenuIconItem(float x, float y, float w, float h, TextureRegion region, ClickInterface clickInterface) {
		GameMenuIconButton menuButton = new GameMenuIconButton(x, y, Depths.hudZ + 1, w, h, region, clickInterface);
		menuButton.addToEngine(engine);
	}

	private void addHighScore() {
		Entity entity = new Entity();

		BitmapFontComponent fontComp = new BitmapFontComponent();
		fontComp.font = "retro";
		fontComp.string = "High score: " + WorldUI.createTimerString(GameSettings.getHighScore());
		fontComp.color.set(0.2f, 1.0f, 0.2f, 1f);
		fontComp.scale = 0.5f;
		fontComp.centering = false;
		entity.add(fontComp);

		Assets.fonts.get(fontComp.font).getData().setScale(fontComp.scale);
		GlyphLayout layout = new GlyphLayout(Assets.fonts.get(fontComp.font), fontComp.string);

		TransformComponent transComp = new TransformComponent();
		float x = BasicScreen.WORLD_WIDTH - layout.width * RenderingSystem.PIXELS_TO_WORLD - 0.1f;
		float y = 0.1f;
		float z = 10f;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		add(entity);
	}

	private void stopClock() {
		clockSoundComp.sound.stop(clockSoundComp.soundID);
	}

	private void addClockSound() {
		Entity entity = new Entity();

		SoundComponent soundComp = new SoundComponent();
		soundComp.sound = Assets.sounds.get("clock");
		soundComp.loop = true;
		soundComp.duration = -10f;
		entity.add(soundComp);

		clockSoundComp = soundComp;

		add(entity);
	}

	@Override
	public void backPressed() {
		//engine.getSystem(MusicSystem.class).stopMusic();
		game.exit();
	}
}