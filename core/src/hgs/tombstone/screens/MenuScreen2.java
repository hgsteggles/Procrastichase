package hgs.tombstone.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import hgs.tombstone.TombstoneGame;
import hgs.tombstone.assets.Assets;
import hgs.tombstone.assets.GameArt;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.*;
import hgs.tombstone.systems.*;
import hgs.tombstone.world.MenuWorld;
import hgs.tombstone.world.WorldUI;

public class MenuScreen2 extends BasicScreen {
	private static final float BUTTON_WIDTH = 2.8f;
	private static final float BUTTON_HEIGHT = 0.6f;
	private static final float BUTTONS_SPACING = (2.0f - 2.0f * BUTTON_HEIGHT) / 3.0f;

	private static final float BUTTONS_X = BUTTONS_SPACING + 0.5f * BUTTON_WIDTH;
	private static final float BUTTONS_Y = BUTTONS_SPACING + 0.5f * BUTTON_HEIGHT;

	private static final float BUTTONS2_X = BUTTONS_SPACING + BUTTON_WIDTH + (WORLD_WIDTH - BUTTON_WIDTH - BUTTONS_SPACING) / 2.0f;

	final static Array<Enums.PlayerType> characters = new Array<Enums.PlayerType>();
	private int charIndex = 0;

	private MenuWorld world;
	private SoundComponent clockSoundComp;

	private Entity soundIconEntity;

	public MenuScreen2(TombstoneGame game) {
		super(game);

		characters.add(Enums.PlayerType.FERNANDO);
		characters.add(Enums.PlayerType.HARRY);
		characters.add(Enums.PlayerType.JAKE);
		characters.add(Enums.PlayerType.KARIM);

		add(new ClickSystem());
		add(new TweenSystem());
		add(new SoundSystem());
		add(new TickerSystem());
		add(new RemovalSystem());
		add(new LoopingBackgroundSystem());
		add(new CameraSystem());
		add(new AnimationSystem());
		add(new MovementSystem());
		add(new CharacterSelectSystem());

		world = new MenuWorld();
		world.populateLevel(engine, worldCamera);

		addClockSound();
		add(WorldUI.createFullBlackBG());
		if (GameSettings.getHighScore() > 0) {
			addHighScore();
		}
		addPercentageComplete();
		addTitle();

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

		addMenuItem(BUTTONS2_X + 1.4f, 1f, 1.0f, 1.0f, "?", new ClickInterface() {
			@Override
			public void onClick(Entity entity) {
				aboutScreen();
			}
		});
		addMenuIconSwitchItem(BUTTONS2_X, 1f, 1.0f, 1.0f, GameSettings.isSoundOn() ? GameArt.soundOn : GameArt.soundOff, new ClickInterface() {
			@Override
			public void onClick(Entity entity) {
				GameSettings.setSoundOn(!GameSettings.isSoundOn());
				ComponentMappers.texture.get(entity).region = GameSettings.isSoundOn() ? GameArt.soundOn : GameArt.soundOff;
			}
		});
		addMenuIconItem(BUTTONS2_X - 1.4f, 1f, 1.0f, 1.0f, GameArt.tournament, new ClickInterface() {
			@Override
			public void onClick(Entity entity) {
				tournamentButtonDown();
			}
		});

		//tournamentEntity = createTournamentEntity();
		//add(tournamentEntity);

		add(createSelectCharButton(true));
		add(createSelectCharButton(false));
	}

	@Override
	public void render(float interp) {
		engine.getSystem(CameraSystem.class).update(interp);
		super.render(interp);
		//engine.getSystem(DebugSystem.class).render();
	}

	private void charChange(boolean isLeft) {
		if (isLeft)
			charIndex = (charIndex + 1) % characters.size;
		else
			charIndex = (charIndex - 1 + characters.size) % characters.size;

		CharacterChangeEvent changeEvent = new CharacterChangeEvent();
		changeEvent.player = characters.get(charIndex);

		Entity e = new Entity();
		e.add(changeEvent);
		add(e);
	}

	public Entity createSelectCharButton(final boolean isLeft) {
		Entity entity = new Entity();

		Color offColor = new Color(113f/255f, 95f/255f, 45f/255f, 1.0f);
		Color onColor = new Color(26f/255f, 193f/255f, 73f/255f, 1.0f);

		TextureComponent texComp = new TextureComponent();
		texComp.region = isLeft ? GameArt.selectCharButtonLeft : GameArt.selectCharButtonRight;
		texComp.size.x = 2.0f * RenderingSystem.PIXELS_TO_WORLD * texComp.region.getRegionWidth();
		texComp.size.y = 2.0f * RenderingSystem.PIXELS_TO_WORLD * texComp.region.getRegionHeight();
		texComp.color.set(offColor);
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float x = isLeft ? 0.5f * texComp.size.x : BasicScreen.WORLD_WIDTH - 0.5f * texComp.size.x;
		float y = 0.5f * BasicScreen.WORLD_HEIGHT;
		float z = Depths.worldoverlayZ;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		TextButtonComponent textButtonComponent = new TextButtonComponent();
		textButtonComponent.base.set(offColor);
		textButtonComponent.pressed.set(onColor);
		entity.add(textButtonComponent);

		ClickComponent clickComp = new ClickComponent();
		Rectangle rect = new Rectangle(0f, 0f, texComp.size.x, texComp.size.y);
		rect.setCenter(0f, 0f);
		clickComp.shape = rect;
		clickComp.clicker = new ClickInterface() {
			@Override
			public void onClick(Entity entity) {
				charChange(isLeft);
			}
		};
		entity.add(clickComp);

		entity.add(new HeadsUpDisplayComponent());

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
				if (!game.actionResolver.getSignedInGPGS())
					game.actionResolver.loginGPGS();
				if (game.actionResolver.getSignedInGPGS())
					game.actionResolver.getLeaderboardGPGS();

				e.add(new RemovalComponent());
			}
		};
		entity.add(tickComp);

		engine.addEntity(entity);
	}

	private void startGame() {
		stopClock();
		game.setScreen(new GameScreen(game, 1, 0, characters.get(charIndex)));
	}

	private void aboutScreen() {
		stopClock();
		game.setScreen(new AboutScreen(game));
	}

	private void bonusScreen() {
		stopClock();
		game.setScreen(new GameScreen(game, 4, 0, characters.get(charIndex)));
	}

	private void endlessScreen() {
		stopClock();
		game.setScreen(new GameScreen(game, 5, 0, characters.get(charIndex)));
	}

	private void addMenuItem(float x, float y, float w, float h, String text, ClickInterface clickInterface) {
		GameMenuButton menuButton = new GameMenuButton(x, y, Depths.hudZ + 1, w, h, text, clickInterface, false);
		menuButton.addToEngine(engine);
	}

	private void addMenuIconItem(float x, float y, float w, float h, TextureRegion region, ClickInterface clickInterface) {
		GameMenuIconButton menuButton = new GameMenuIconButton(x, y, Depths.hudZ + 1, w, h, region, clickInterface);
		menuButton.addToEngine(engine);
	}

	private void addMenuIconSwitchItem(float x, float y, float w, float h, TextureRegion region, ClickInterface clickInterface) {
		GameMenuIconSwitch menuSwitch = new GameMenuIconSwitch(x, y, Depths.hudZ + 1, w, h, region, clickInterface);
		menuSwitch.addToEngine(engine);
	}

	private void addPercentageComplete() {
		int percent = GameSettings.getLevelsComplete() * 20;
		if (GameSettings.collectedAllPages())
			percent += 10;
		if (GameSettings.endlessRank() < 4)
			percent += 5;
		if (GameSettings.endlessRank() < 3)
			percent += 4;
		if (GameSettings.endlessRank() < 2)
			percent += 1;

		Entity entity = new Entity();

		BitmapFontComponent fontComp = new BitmapFontComponent();
		fontComp.font = "retro";
		fontComp.string = Integer.toString(percent) + "%";
		fontComp.color.set(0.4f, 1.0f, 0.4f, 1f);
		fontComp.scale = 1.0f;
		fontComp.centering = true;
		entity.add(fontComp);

		TransformComponent transComp = new TransformComponent();
		float x = 0.5f * BasicScreen.WORLD_WIDTH;
		float y = 0.5f * BasicScreen.WORLD_HEIGHT + 1.5f - 0.125f;
		float z = Depths.hudZ;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		add(entity);
	}

	private void addTitle() {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.title;
		texComp.size.x = 1.0f * RenderingSystem.PIXELS_TO_WORLD * texComp.region.getRegionWidth();
		texComp.size.y = 1.0f * RenderingSystem.PIXELS_TO_WORLD * texComp.region.getRegionHeight();
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float x = 0.5f * BasicScreen.WORLD_WIDTH;
		float y = 5.0f;
		float z = Depths.hudZ;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		entity.add(new HeadsUpDisplayComponent());

		add(entity);
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
		float z = Depths.screentextZ;
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