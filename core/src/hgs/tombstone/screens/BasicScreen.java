package hgs.tombstone.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import hgs.tombstone.TombstoneGame;
import hgs.tombstone.components.*;
import hgs.tombstone.input.InputManager;
import hgs.tombstone.systems.RenderingSystem;

/**
 * Created by harry on 01/04/16.
 */


public abstract class BasicScreen implements Screen {
	public static final float WORLD_WIDTH = 8.0f;
	public static final float WORLD_HEIGHT = 6.0f;
	protected final TombstoneGame game;
	protected int width;
	protected int height;

	protected Viewport staticWorldViewport;
	protected Viewport worldViewport;
	protected Viewport pixelViewport;
	protected OrthographicCamera staticWorldCam;
	protected OrthographicCamera worldCamera;
	protected OrthographicCamera pixelCamera;
	protected SpriteBatch spriteBatch;

	protected Engine engine;

	public BasicScreen(TombstoneGame game) {
		this.game = game;
		worldCamera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		worldCamera.position.set(WORLD_WIDTH / 2.0f, WORLD_HEIGHT / 2.0f, 0);
		worldCamera.update();

		staticWorldCam = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		staticWorldCam.position.set(WORLD_WIDTH / 2.0f, WORLD_HEIGHT / 2.0f, 0);
		staticWorldCam.update();

		pixelCamera = new OrthographicCamera(TombstoneGame.WIDTH, TombstoneGame.HEIGHT);
		pixelCamera.position.set(TombstoneGame.WIDTH / 2.0f, TombstoneGame.HEIGHT / 2.0f, 0);
		pixelCamera.update();

		staticWorldViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, staticWorldCam);
		worldViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, worldCamera);
		pixelViewport = new FitViewport(TombstoneGame.WIDTH, TombstoneGame.HEIGHT, pixelCamera);

		InputManager.screenInput.setViewport(staticWorldViewport);

		engine = new Engine();
		spriteBatch = new SpriteBatch();
		engine.addSystem(new RenderingSystem(spriteBatch, worldCamera, staticWorldCam, pixelCamera));
	}

	public void add(Entity e) {
		engine.addEntity(e);
	}

	public void add(EntitySystem es) {
		engine.addSystem(es);
	}

	public void update(float delta) {
		engine.update(delta);
	}

	public void render(float interp) {
		engine.getSystem(RenderingSystem.class).render(interp);
	}

	@Override
	public void show() {
		worldCamera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		worldCamera.position.set(WORLD_WIDTH / 2.0f, WORLD_HEIGHT / 2.0f, 0);
		worldCamera.update();

		spriteBatch.setProjectionMatrix(worldCamera.combined);

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;

		staticWorldViewport.update(width, height);
		worldViewport.update(width, height);
		pixelViewport.update(width, height);
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	public boolean isOverlay() {
		return false;
	}

	public abstract void backPressed();

	public Entity createText(float x, float y, String text) {
		return createText(x, y, text, false);
	}

	public Entity createText(float x, float y, String text, boolean small) {
		Entity entity = new Entity();

		BitmapFontComponent fontComp = new BitmapFontComponent();
		fontComp.font = "retro";
		if (small) {
			fontComp.scale = 0.5f;
		}
		fontComp.string = text;
		fontComp.color.set(Color.WHITE);
		fontComp.centering = true;

		TransformComponent transComp = new TransformComponent();
		transComp.body.initPosition(x, y, 0);

		entity.add(fontComp);
		entity.add(transComp);

		return entity;
	}

	public void addFadeTween(Entity entity) {
		TweenComponent tweenComp = ComponentMappers.tween.get(entity);

		TweenSpec tweenSpec = new TweenSpec();
		tweenSpec.cycle = TweenSpec.Cycle.ONCE;
		tweenSpec.period = 0.5f;
		tweenSpec.start = 1.0f;
		tweenSpec.end = 0.0f;
		tweenSpec.reverse = true;
		tweenSpec.interp = Interpolation.sine;
		tweenSpec.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				BitmapFontComponent fc = ComponentMappers.bitmapfont.get(e);
				fc.color.a = a;
			}
		};
		tweenComp.tweenSpecs.add(tweenSpec);
	}

	public void addFadeEndTween(Entity entity, final int level, final int npages) {
		TweenComponent tweenComp = ComponentMappers.tween.get(entity);

		TweenSpec tweenSpec = new TweenSpec();
		tweenSpec.cycle = TweenSpec.Cycle.ONCE;
		tweenSpec.period = 0.5f;
		tweenSpec.start = 1.0f;
		tweenSpec.end = 0.0f;
		tweenSpec.reverse = true;
		tweenSpec.interp = Interpolation.sine;
		tweenSpec.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				BitmapFontComponent fc = ComponentMappers.bitmapfont.get(e);
				fc.color.a = a;
			}

			@Override
			public void endTween(Entity e) {
				game.setScreen(new GameScreen(game, level, npages));
			}
		};
		tweenComp.tweenSpecs.add(tweenSpec);
	}
}