package hgs.tombstone.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import hgs.tombstone.TombstoneGame;
import hgs.tombstone.assets.GameArt;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Depths;
import hgs.tombstone.elements.GameMenuButton;
import hgs.tombstone.systems.ClickSystem;
import hgs.tombstone.systems.MovementSystem;
import hgs.tombstone.systems.RemovalSystem;
import hgs.tombstone.systems.TweenSystem;
import hgs.tombstone.world.WorldUI;

import java.util.ArrayList;

public class SplashScreen extends BasicScreen {
	private final float SHEEP_LOC_Y = WORLD_HEIGHT / 2f;
	float accum = 0;

	public SplashScreen(TombstoneGame game) {
		super(game);

		add(new TweenSystem());
		add(new MovementSystem());
		add(new RemovalSystem());

		add(WorldUI.createFullBlackBG());

		add(createSheep());
		add(createEyeLine(true, true));
		add(createEyeLine(true, false));
		add(createEyeLine(false, true));
		add(createEyeLine(false, false));
		//add(createEye(true));
		//add(createEye(false));
		add(createEyeAura(true));
		add(createEyeAura(false));
		add(createEyeLight(true));
		add(createEyeLight(false));
	}

	@Override
	public void update(float delta) {
		super.update(delta);

		if (accum > 2) {
			game.setScreen(new MenuScreen2(game));
		}

		accum += delta;
	}

	private Entity createEye(boolean isLeft) {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.sheepeye;
		texComp.size.x = 0.0f;
		texComp.size.y = 0.0f;
		texComp.color.set(Color.WHITE);
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float offsetX = isLeft ? -1.0f : 1.0f;
		float x = BasicScreen.WORLD_WIDTH / 2.0f + offsetX;
		float y = SHEEP_LOC_Y + 0.5f;
		float z = Depths.screenbgZ + 3;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		float period = 0.5f;

		TweenComponent tweenComp = new TweenComponent();
		TweenSpec tweenSpec = new TweenSpec();
		tweenSpec.period = period;
		tweenSpec.cycle = TweenSpec.Cycle.ONCE;
		tweenSpec.interp = Interpolation.sine;
		tweenSpec.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				TextureComponent txc = ComponentMappers.texture.get(e);
				txc.size.x = 0.2f * a;
				txc.size.y = 0.2f * a;
			}
		};
		tweenComp.tweenSpecs.add(tweenSpec);
		entity.add(tweenComp);

		entity.add(new HeadsUpDisplayComponent());

		return entity;
	}

	private Entity createEyeAura(boolean isLeft) {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.sheepaura;
		texComp.size.x = 0.0f;
		texComp.size.y = 0.0f;
		texComp.color.set(Color.WHITE);
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float offsetX = isLeft ? -1.0f : 1.0f;
		float x = BasicScreen.WORLD_WIDTH / 2.0f + offsetX;
		float y = SHEEP_LOC_Y + 0.5f;
		float z = Depths.screenbgZ + 1;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		float period = 1.0f;

		TweenComponent tweenComp = new TweenComponent();

		TweenSpec tweenSpec = new TweenSpec();
		tweenSpec.period = 0.5f * period;
		tweenSpec.cycle = TweenSpec.Cycle.LOOP;
		tweenSpec.interp = Interpolation.sine;
		tweenSpec.loops = 2;
		tweenSpec.reverse = true;
		tweenSpec.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				TextureComponent txc = ComponentMappers.texture.get(e);
				txc.size.x = 1.6f * a;
				txc.size.y = 1.6f * a;
			}
		};
		tweenComp.tweenSpecs.add(tweenSpec);

		entity.add(tweenComp);

		entity.add(new HeadsUpDisplayComponent());

		return entity;
	}

	private Entity createEyeLight(boolean isLeft) {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.sheeplight;
		texComp.size.x = 0.0f;
		texComp.size.y = 0.0f;
		texComp.color.set(Color.WHITE);
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float offsetX = isLeft ? -1.0f : 1.0f;
		float x = BasicScreen.WORLD_WIDTH / 2.0f + offsetX;
		float y = SHEEP_LOC_Y + 0.5f;
		float z = Depths.screenbgZ + 4;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		float period = 1.0f;

		TweenComponent tweenComp = new TweenComponent();

		TweenSpec tweenSpec = new TweenSpec();
		tweenSpec.period = 0.5f * period;
		tweenSpec.cycle = TweenSpec.Cycle.LOOP;
		tweenSpec.interp = Interpolation.sine;
		tweenSpec.loops = 2;
		tweenSpec.reverse = true;
		tweenSpec.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				TextureComponent txc = ComponentMappers.texture.get(e);
				txc.size.x = 0.8f * a;
				txc.size.y = 0.8f * a;
			}
		};
		tweenComp.tweenSpecs.add(tweenSpec);

		entity.add(tweenComp);

		entity.add(new HeadsUpDisplayComponent());

		return entity;
	}

	private Entity createEyeLine(boolean isLeft, boolean isRotated) {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.lightline;
		texComp.size.x = 0.0f;
		texComp.size.y = 0.3f;
		texComp.color.set(Color.WHITE);
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float offsetX = isLeft ? -1.0f : 1.0f;
		float x = BasicScreen.WORLD_WIDTH / 2.0f + offsetX;
		float y = SHEEP_LOC_Y + 0.5f;
		float z = Depths.screenbgZ + 1;
		transComp.body.initPosition(x, y, z);
		transComp.body.initRotation(isRotated ? 90f : 0f);
		entity.add(transComp);

		float period = 1.0f;

		TweenComponent tweenComp = new TweenComponent();

		TweenSpec tweenSpec = new TweenSpec();
		tweenSpec.period = 0.5f * period;
		tweenSpec.cycle = TweenSpec.Cycle.LOOP;
		tweenSpec.interp = Interpolation.sine;
		tweenSpec.loops = 2;
		tweenSpec.reverse = true;
		tweenSpec.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				TextureComponent txc = ComponentMappers.texture.get(e);
				txc.size.x = 2.0f * a;
			}
		};
		tweenComp.tweenSpecs.add(tweenSpec);

		TweenSpec tweenSpec2 = new TweenSpec();
		tweenSpec2.period = period;
		tweenSpec2.cycle = TweenSpec.Cycle.ONCE;
		tweenSpec2.interp = Interpolation.sine;
		tweenSpec2.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				MovementComponent mc = ComponentMappers.movement.get(e);
				mc.rotationalVelocity = 360f * a;
			}

			@Override
			public void endTween(Entity e) {
				e.add(new RemovalComponent());
			}
		};
		tweenComp.tweenSpecs.add(tweenSpec2);

		entity.add(tweenComp);

		entity.add(new MovementComponent());

		entity.add(new HeadsUpDisplayComponent());

		return entity;
	}

	private Entity createSheep() {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.sheepstare;
		texComp.size.x = BasicScreen.WORLD_HEIGHT * texComp.region.getRegionWidth() / (float)texComp.region.getRegionHeight();
		texComp.size.y = BasicScreen.WORLD_HEIGHT;
		texComp.color.set(Color.WHITE);
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float x = BasicScreen.WORLD_WIDTH / 2.0f;
		float y = SHEEP_LOC_Y;
		float z = Depths.screenbgZ + 0;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		entity.add(new HeadsUpDisplayComponent());

		return entity;
	}

	@Override
	public void backPressed() {}

}