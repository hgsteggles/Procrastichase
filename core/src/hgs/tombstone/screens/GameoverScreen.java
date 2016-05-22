package hgs.tombstone.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import hgs.tombstone.TombstoneGame;
import hgs.tombstone.assets.Assets;
import hgs.tombstone.assets.GameArt;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.GameMenuButton;
import hgs.tombstone.elements.GameSettings;
import hgs.tombstone.input.InputManager;
import hgs.tombstone.systems.ClickSystem;
import hgs.tombstone.systems.GameStateSystem;
import hgs.tombstone.systems.RenderingSystem;
import hgs.tombstone.systems.TweenSystem;
import hgs.tombstone.world.EntityFactory;
import hgs.tombstone.world.WorldUI;

public class GameoverScreen extends BasicScreen {
	private static final float BUTTONS_X = WORLD_WIDTH / 2.0f;
	private static final float BUTTONS_Y = WORLD_HEIGHT / 4.0f + 0.5f;
	private static final float BUTTONS_SPACING = 0.7f;

	private int npages;
	private int level;
	private final float pageMinusTime = 1.0f;
	private final float pageChangeTime = 2.0f;
	private float time = 0.0f;
	private boolean done = false;

	private boolean doublespeed = false;

	private Entity pageText;

	public GameoverScreen(TombstoneGame game, final int level, final int npages) {
		super(game);

		this.npages = npages;
		this.level = level;

		add(new ClickSystem());
		add(new TweenSystem());

		add(WorldUI.createFullBlackBG());

		add(createPageCounterIcon());
		pageText = createPageText();
		add(pageText);
	}

	@Override
	public void update(float delta) {
		if (doublespeed)
			delta *= 4;

		super.update(delta);

		if (!done) {
			if (time > pageChangeTime) {
				BitmapFontComponent bfc = ComponentMappers.bitmapfont.get(pageText);
				bfc.string = Integer.toString(Math.max(npages - 2, 0));

				addMenuItem(BUTTONS_X, BUTTONS_Y, "CONTINUE", new ClickInterface() {
					@Override
					public void onClick(Entity entity) {
						continueGame(level, npages);
					}
				}, 0);
				addMenuItem(BUTTONS_X, BUTTONS_Y - 1 * BUTTONS_SPACING, "MENU", new ClickInterface() {
					@Override
					public void onClick(Entity entity) {
						menuScreen();
					}
				}, 1);

				done = true;
			} else if (time > pageMinusTime) {
				BitmapFontComponent bfc = ComponentMappers.bitmapfont.get(pageText);
				bfc.string = Integer.toString(npages) + "-2";
			}

			time += delta;
		}

		if (InputManager.screenInput.get(0).isPointerDown() && InputManager.screenInput.get(0).isPointerDownLast()) {
			doublespeed = true;
		}
	}

	private void continueGame(int level, int npages) {
		game.setScreen(new GameScreen(game, level, Math.max(npages - 2, 0)));
	}

	private void menuScreen() {
		game.setScreen(new MenuScreen2(game));
	}

	private void addMenuItem(float x, float y, String text, ClickInterface clickInterface, int n) {
		GameMenuButton menuButton = new GameMenuButton(x, y, 3.7f, text, clickInterface);
		menuButton.addToEngine(engine);
	}

	private Entity createPageCounterIcon() {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.page;
		texComp.centre = false;
		texComp.size.x = 2.0f * texComp.region.getRegionWidth() * RenderingSystem.PIXELS_TO_WORLD;
		texComp.size.y = 2.0f * texComp.region.getRegionHeight() * RenderingSystem.PIXELS_TO_WORLD;
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float x = 0.5f * BasicScreen.WORLD_WIDTH - 0.5f;
		float y = 0.5f * BasicScreen.WORLD_HEIGHT + 1f;
		float z = 11.0f;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		return entity;
	}

	private Entity createPageText() {
		Entity entity = new Entity();

		BitmapFontComponent fontComp = new BitmapFontComponent();
		fontComp.font = "retro";
		fontComp.string = Integer.toString(npages);
		fontComp.color.set(Color.WHITE);
		fontComp.centering = false;
		entity.add(fontComp);

		TransformComponent transComp = new TransformComponent();
		float x = 0.5f * BasicScreen.WORLD_WIDTH + 0.1f;
		float y = 0.5f * BasicScreen.WORLD_HEIGHT + 1f;
		float z = 11.0f;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		return entity;
	}

	@Override
	public void backPressed() {
		game.setScreen(new MenuScreen2(game));
	}
}