package hgs.tombstone.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import hgs.tombstone.TombstoneGame;
import hgs.tombstone.assets.GameArt;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Colors;
import hgs.tombstone.elements.Enums;
import hgs.tombstone.systems.RenderingSystem;

/**
 * Created by harry on 07/04/16.
 */
public class ParticleFactory {
	public static EmitterComponent createTrailEmitter(final float hue, float speed) {
		EmitterComponent emitComp = new EmitterComponent();

		emitComp.velocity.x = speed;
		emitComp.rotationalVelocity = 0.0f;

		emitComp.texregion = GameArt.diamond;
		Colors.setHue(emitComp.texcolor, hue, 1.0f, 1.0f);
		final float texW = 2.0f * emitComp.texregion.getRegionWidth() * RenderingSystem.PIXELS_TO_WORLD;
		final float texH = 2.0f * emitComp.texregion.getRegionHeight() * RenderingSystem.PIXELS_TO_WORLD;
		emitComp.texsize.set(texW, texH);

		emitComp.intermittency = 0.01f;
		emitComp.lifetime = 1.0f;
		float height = 2.0f * emitComp.texregion.getRegionHeight() * RenderingSystem.PIXELS_TO_WORLD;
		float width = 0.01f;
		emitComp.sourceRect.setSize(width, height);
		emitComp.sourceRect.setCenter(0.0f, 0.0f);
		emitComp.rate = 10f;

		emitComp.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				TextureComponent txc = ComponentMappers.texture.get(e);
				txc.color = Colors.setHue(txc.color, a + hue, 1.0f, 1.0f);
				txc.color.a = a;
				txc.size.x = a * texW;
				txc.size.y = a * texH;
			}

			@Override
			public void endTween(Entity e) {
				e.add(new RemovalComponent());
			}
		};

		return emitComp;
	}

	public static EmitterComponent createFireballEmitter() {
		EmitterComponent emitComp = new EmitterComponent();

		//emitComp.velocity.x = 0.8f;
		emitComp.velScale = 0.8f;
		emitComp.rotationalVelocity = 0.0f;

		TextureRegion fireballRegion = GameArt.bulletAnim.get(Enums.BulletType.FIREBALL).get(0);
		float height = 2.0f * fireballRegion.getRegionHeight() * RenderingSystem.PIXELS_TO_WORLD;
		float width = 2.0f * fireballRegion.getRegionWidth() * RenderingSystem.PIXELS_TO_WORLD;
		emitComp.sourceRect.setSize(0.5f*width, 0.5f*height);
		emitComp.sourceRect.setCenter(0.0f, 0.0f);

		emitComp.intermittency = 0.01f;
		emitComp.lifetime = 1.0f;
		emitComp.rate = 60f;
		emitComp.nextDrop = 0.1f;

		emitComp.texregion = GameArt.flame;
		emitComp.texcolor.set(Color.RED);
		final float texW = 2.0f * emitComp.texregion.getRegionWidth() * RenderingSystem.PIXELS_TO_WORLD;
		final float texH = 2.0f * emitComp.texregion.getRegionHeight() * RenderingSystem.PIXELS_TO_WORLD;
		emitComp.texsize.set(texW, texH);

		emitComp.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				TextureComponent txc = ComponentMappers.texture.get(e);
				Colors.lerp(txc.color, Color.RED, Color.YELLOW, a);
				//txc.color.set(1.0f, 0.0f, 0.0f, a);
				txc.color.a = a;
				txc.size.x = a * texW;
				txc.size.y = a * texH;
			}

			@Override
			public void endTween(Entity e) {
				e.add(new RemovalComponent());
			}
		};

		return emitComp;
	}

	public static EmitterComponent createMilkEmitter() {
		EmitterComponent emitComp = new EmitterComponent();

		emitComp.velocity.y = 2.0f;
		emitComp.acceleration.y = -4.0f;
		//emitComp.velScale = 0.8f;
		emitComp.rotationalVelocity = 90.0f;

		emitComp.sourceRect.setSize(0.02f, 0.02f);
		emitComp.sourceRect.setCenter(0.0f, 0.0f);
		emitComp.sourceRotation = 0.0f;

		emitComp.intermittency = 0.01f;
		emitComp.lifetime = 2f;
		emitComp.rate = 40f;
		emitComp.nextDrop = 0.1f;

		emitComp.texregion = GameArt.whitePixel;
		emitComp.texcolor.set(1.0f, 1.0f, 1.0f, 1.0f);
		final float texW = 0.1f;
		final float texH = 0.1f;
		emitComp.texsize.set(texW, texH);

		emitComp.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				TextureComponent txc = ComponentMappers.texture.get(e);
				txc.color.a = a;
				txc.size.x = a * texW;
				txc.size.y = a * texH;
			}

			@Override
			public void endTween(Entity e) {
				e.add(new RemovalComponent());
			}
		};

		return emitComp;
	}

	public static EmitterComponent createBeerEmitter() {
		EmitterComponent emitComp = new EmitterComponent();

		emitComp.velocity.y = 2.0f;
		emitComp.acceleration.y = -4.0f;
		//emitComp.velScale = 0.8f;
		emitComp.rotationalVelocity = 90.0f;

		emitComp.sourceRect.setSize(0.02f, 0.02f);
		emitComp.sourceRect.setCenter(0.0f, 0.0f);
		emitComp.sourceRotation = 0.0f;
		emitComp.angularRange = 0f;

		emitComp.intermittency = 0.01f;
		emitComp.lifetime = 2.0f;
		emitComp.rate = 40f;
		emitComp.nextDrop = 0.1f;

		emitComp.texregion = GameArt.whitePixel;
		emitComp.texcolor.set(1.0f, 204.0f/255.0f, 0.0f, 1.0f);
		emitComp.texsize.set(0.1f, 0.1f);

		emitComp.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				TextureComponent txc = ComponentMappers.texture.get(e);
				txc.color.a = a;
				txc.size.x = a * 0.1f;
				txc.size.y = a * 0.1f;
			}

			@Override
			public void endTween(Entity e) {
				e.add(new RemovalComponent());
			}
		};

		return emitComp;
	}

	public static EmitterComponent createPeeEmitter() {
		EmitterComponent emitComp = new EmitterComponent();

		emitComp.texregion = GameArt.whitePixel;
		emitComp.texcolor.set(Color.YELLOW);
		final float texW = 0.05f;
		final float texH = 0.05f;
		emitComp.texsize.set(texW, texH);

		emitComp.velocity.set(-1.0f, 2.0f);
		emitComp.angularRange = 1f;
		emitComp.rotationalVelocity = 0.0f;
		emitComp.acceleration.y = -10f;

		float height = 0.001f;
		float width = 0.001f;
		emitComp.sourceRect.setSize(width, height);
		emitComp.sourceRect.setCenter(0.0f, -0.2f);

		emitComp.collisionType = Enums.CollisionType.PEE;
		emitComp.collisionRect.setSize(texW, texH);
		emitComp.collisionRect.setCenter(0.0f, 0.0f);

		emitComp.lifetime = 1.0f;
		emitComp.intermittency = 0.05f;
		emitComp.rate = 20f;

		return emitComp;
	}

	public static EmitterComponent createMusicNoteEmitter() {
		EmitterComponent emitComp = new EmitterComponent();

		emitComp.texregion = GameArt.musicNote;
		emitComp.texcolor.set(Color.WHITE);
		final float texW = 2.0f * emitComp.texregion.getRegionWidth() * RenderingSystem.PIXELS_TO_WORLD;
		final float texH = 2.0f * emitComp.texregion.getRegionWidth() * RenderingSystem.PIXELS_TO_WORLD;
		emitComp.texsize.set(texW, texH);

		emitComp.velocity.set(-2.0f, 0.0f);

		float height = 0.001f;
		float width = 0.001f;
		emitComp.sourceRect.setSize(width, height);
		emitComp.sourceRect.setCenter(0.0f, 0.0f);

		emitComp.collisionType = Enums.CollisionType.ENEMY_BULLET;
		emitComp.collisionRect.setSize(texW, texH);
		emitComp.collisionRect.setCenter(0.0f, 0.0f);

		emitComp.lifetime = 2.0f;
		emitComp.lifetimeRange = 0.0f;
		emitComp.intermittency = 0;
		emitComp.rate = 0.25f;

		emitComp.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				TextureComponent txc = ComponentMappers.texture.get(e);
				txc.color.a = 0.5f + a*0.5f;
				TransformComponent tc = ComponentMappers.transform.get(e);
				CollisionComponent cc = ComponentMappers.collision.get(e);
				float nexty = 2.5f + 0.1f * (float)Math.sin(20f * a);
				tc.body.updatePosY(nexty);
				cc.rect.y = nexty - 0.5f * cc.rect.height;
			}

			@Override
			public void endTween(Entity e) {
				e.add(new RemovalComponent());
			}
		};

		return emitComp;
	}

	public static EmitterComponent createSleepEmitter(final float posX) {
		EmitterComponent emitComp = new EmitterComponent();

		emitComp.texregion = GameArt.sleepZ;
		emitComp.texcolor.set(Color.WHITE);
		final float texW = 0.5f * 2.0f * emitComp.texregion.getRegionWidth() * RenderingSystem.PIXELS_TO_WORLD;
		final float texH = 0.5f * 2.0f * emitComp.texregion.getRegionWidth() * RenderingSystem.PIXELS_TO_WORLD;
		emitComp.texsize.set(texW, texH);

		emitComp.velocity.set(0.0f, 1.0f);

		float height = 0.001f;
		float width = 0.001f;
		emitComp.sourceRect.setSize(width, height);
		emitComp.sourceRect.setCenter(0.0f, 0.0f);

		emitComp.collisionType = Enums.CollisionType.NONE;
		emitComp.collisionRect.setSize(texW, texH);
		emitComp.collisionRect.setCenter(0.0f, 0.0f);

		emitComp.lifetime = 4.0f;
		emitComp.lifetimeRange = 0.0f;
		emitComp.intermittency = 0;
		emitComp.rate = 1.8f;

		emitComp.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				TextureComponent txc = ComponentMappers.texture.get(e);
				txc.color.a = a;
				txc.size.set(a * texW, a * texH);
				TransformComponent tc = ComponentMappers.transform.get(e);
				//CollisionComponent cc = ComponentMappers.collision.get(e);
				float nextx = posX + 0.1f * (float)Math.sin(20f * a);
				tc.body.updatePosX(nextx);
				//cc.rect.x = nextx - 0.5f * cc.rect.height;
			}

			@Override
			public void endTween(Entity e) {
				e.add(new RemovalComponent());
			}
		};

		return emitComp;
	}

	public static EmitterComponent createHighScoreEmitter(final float hue, float speed) {
		EmitterComponent emitComp = new EmitterComponent();

		emitComp.velocity.x = speed;
		emitComp.rotationalVelocity = 0.0f;

		emitComp.texregion = GameArt.diamond;
		Colors.setHue(emitComp.texcolor, hue, 1.0f, 1.0f);
		final float texW = 0.5f * 2.0f * emitComp.texregion.getRegionWidth() * RenderingSystem.PIXELS_TO_WORLD;
		final float texH = 0.5f * 2.0f * emitComp.texregion.getRegionHeight() * RenderingSystem.PIXELS_TO_WORLD;
		emitComp.texsize.set(texW, texH);

		emitComp.intermittency = 0.01f;
		emitComp.lifetime = 2f;
		float height = 1.5f;
		float width = 0.1f;
		emitComp.sourceRect.setSize(width, height);
		emitComp.sourceRect.setCenter(0.0f, 0.0f);
		emitComp.rate = 10;

		emitComp.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				TextureComponent txc = ComponentMappers.texture.get(e);
				txc.color = Colors.setHue(txc.color, a + hue, 1.0f, 1.0f);
				txc.color.a = a;
				txc.size.x = a * texW;
				txc.size.y = a * texH;
			}

			@Override
			public void endTween(Entity e) {
				e.add(new RemovalComponent());
			}
		};

		return emitComp;
	}

	public static EmitterComponent createHoverEmitter(float speed) {
		EmitterComponent emitComp = new EmitterComponent();

		emitComp.velocity.x = -speed;
		emitComp.rotationalVelocity = 0.0f;

		emitComp.texregion = GameArt.diamond;
		emitComp.texcolor.set(159f/255f, 248f/255f, 1.0f, 1.0f);
		final float texW = 0.75f * 2.0f * emitComp.texregion.getRegionWidth() * RenderingSystem.PIXELS_TO_WORLD;
		final float texH = 0.75f * 2.0f * emitComp.texregion.getRegionHeight() * RenderingSystem.PIXELS_TO_WORLD;
		emitComp.texsize.set(texW, texH);

		emitComp.intermittency = 0.01f;
		emitComp.lifetime = 1.0f;
		float height = 0.04f;
		float width = 0.01f;
		emitComp.sourceRect.setSize(width, height);
		emitComp.sourceRect.setCenter(0.0f, -0.4f);
		emitComp.rate = 15f;

		emitComp.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				TextureComponent txc = ComponentMappers.texture.get(e);
				txc.size.x = a * texW;
				txc.size.y = a * texH;
			}

			@Override
			public void endTween(Entity e) {
				e.add(new RemovalComponent());
			}
		};

		return emitComp;
	}

	public static EmitterComponent createPepperoniEmitter() {
		EmitterComponent emitComp = new EmitterComponent();

		emitComp.texregion = GameArt.pepperoni;
		final float texW = 2.0f * emitComp.texregion.getRegionWidth() * RenderingSystem.PIXELS_TO_WORLD;
		final float texH = 2.0f * emitComp.texregion.getRegionHeight() * RenderingSystem.PIXELS_TO_WORLD;
		emitComp.texsize.set(texW, texH);

		emitComp.velocity.set(-2.0f, 0.0f);
		emitComp.angularRange = 2f;
		emitComp.rotationalVelocity = 0.0f;
		//emitComp.acceleration.y = -10f;

		float height = 0.2f;
		float width = 0.1f;
		emitComp.sourceRect.setSize(width, height);
		emitComp.sourceRect.setCenter(0.0f, 0.0f);

		emitComp.lifetime = 0.5f;
		emitComp.intermittency = 0.05f;
		emitComp.rate = 10f;

		emitComp.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				TextureComponent txc = ComponentMappers.texture.get(e);
				txc.color.a = a;
			}

			@Override
			public void endTween(Entity e) {
				e.add(new RemovalComponent());
			}
		};

		return emitComp;
	}

	public static EmitterComponent createWetHairEmitter() {
		EmitterComponent emitComp = new EmitterComponent();

		emitComp.texregion = GameArt.whitePixel;
		emitComp.texcolor.set(Color.YELLOW);
		final float texW = 0.05f;
		final float texH = 0.05f;
		emitComp.texsize.set(texW, texH);

		emitComp.velocity.set(0.0f, -1.0f);
		emitComp.angularRange = 2f;
		emitComp.rotationalVelocity = 0.0f;
		//emitComp.acceleration.y = -10f;

		float height = 0.1f;
		float width = 0.2f;
		emitComp.sourceRect.setSize(width, height);
		emitComp.sourceRect.setCenter(0.0f, 0.08f);

		emitComp.lifetime = 0.7f;
		emitComp.intermittency = 0.05f;
		emitComp.rate = 1f;

		emitComp.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				TextureComponent txc = ComponentMappers.texture.get(e);
				txc.color.a = a;
			}

			@Override
			public void endTween(Entity e) {
				e.add(new RemovalComponent());
			}
		};

		return emitComp;
	}
}
