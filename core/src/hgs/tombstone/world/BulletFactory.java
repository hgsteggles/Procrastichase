package hgs.tombstone.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import hgs.tombstone.assets.GameArt;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Colors;
import hgs.tombstone.elements.Depths;
import hgs.tombstone.elements.Enums.*;
import hgs.tombstone.elements.RNG;
import hgs.tombstone.screens.BasicScreen;
import hgs.tombstone.systems.RenderingSystem;

/**
 * Created by harry on 05/04/16.
 */
public class BulletFactory {
	public static ObjectMap<MiniBossType, BulletCreateType> miniBossBulletMap = createMiniBossBulletMap();
	public static ObjectMap<BossType, BulletCreateType> bossBulletMap = createBossBulletMap();
	public static ObjectMap<PlayerType, BulletCreateType> playerBulletMap = createPlayerBulletMap();
	public static ObjectMap<MiniBossType, BulletDirectionStyle> miniBossBulletDirectionMap = createMiniBossBulletDirectionMap();
	public static ObjectMap<BossType, BulletDirectionStyle> bossBulletDirectionMap = createBossBulletDirectionMap();
	public static ObjectSet<BulletType> rotatingBullets = createRotatingBulletSet();

	private static ObjectMap<MiniBossType, BulletCreateType> createMiniBossBulletMap() {
		ObjectMap<MiniBossType, BulletCreateType> map = new ObjectMap<MiniBossType, BulletCreateType>();
		map.put(MiniBossType.IVA, BulletCreateType.SAR);
		map.put(MiniBossType.GREG, BulletCreateType.JUGGLINGBALL);
		map.put(MiniBossType.ROB, BulletCreateType.SPEECH);
		map.put(MiniBossType.MARC, BulletCreateType.TRON);

		return map;
	}

	private static ObjectMap<BossType, BulletCreateType> createBossBulletMap() {
		ObjectMap<BossType, BulletCreateType> map = new ObjectMap<BossType, BulletCreateType>();
		map.put(BossType.MELVIN, BulletCreateType.BEER);
		map.put(BossType.JULIAN, BulletCreateType.MILK);
		map.put(BossType.RENE, BulletCreateType.JUGGLINGPIN);
		map.put(BossType.SVEN, BulletCreateType.RANDOM);
		map.put(BossType.ENDLESS, BulletCreateType.ENDLESS);

		return map;
	}

	private static ObjectMap<PlayerType, BulletCreateType> createPlayerBulletMap() {
		ObjectMap<PlayerType, BulletCreateType> map = new ObjectMap<PlayerType, BulletCreateType>();
		map.put(PlayerType.FERNANDO, BulletCreateType.RAINBOW);
		map.put(PlayerType.HARRY, BulletCreateType.PASTY);
		map.put(PlayerType.JAKE, BulletCreateType.LIGHTNING);
		map.put(PlayerType.KARIM, BulletCreateType.SCIMITAR);

		return map;
	}

	private static ObjectMap<MiniBossType, BulletDirectionStyle> createMiniBossBulletDirectionMap() {
		ObjectMap<MiniBossType, BulletDirectionStyle> map = new ObjectMap<MiniBossType, BulletDirectionStyle>();
		map.put(MiniBossType.IVA, BulletDirectionStyle.ALL);
		map.put(MiniBossType.GREG, BulletDirectionStyle.LOWER);
		map.put(MiniBossType.ROB, BulletDirectionStyle.LOWER);
		map.put(MiniBossType.MARC, BulletDirectionStyle.LOWER);

		return map;
	}

	private static ObjectMap<BossType, BulletDirectionStyle> createBossBulletDirectionMap() {
		ObjectMap<BossType, BulletDirectionStyle> map = new ObjectMap<BossType, BulletDirectionStyle>();
		map.put(BossType.MELVIN, BulletDirectionStyle.ALL);
		map.put(BossType.JULIAN, BulletDirectionStyle.LOWER);
		map.put(BossType.RENE, BulletDirectionStyle.LOWER);
		map.put(BossType.SVEN, BulletDirectionStyle.LOWER);
		map.put(BossType.ENDLESS, BulletDirectionStyle.LOWER);

		return map;
	}

	private static ObjectSet<BulletType> createRotatingBulletSet() {
		ObjectSet<BulletType> set = new ObjectSet<BulletType>();
		set.add(BulletType.RAINBOW);
		set.add(BulletType.SCIMITAR);
		set.add(BulletType.BEER);
		set.add(BulletType.PASTY);
		set.add(BulletType.SAR);
		set.add(BulletType.MILK);
		set.add(BulletType.JUGGLINGBALL);
		set.add(BulletType.JUGGLINGPIN);

		return set;
	}

	public static Entity createProjectile(BulletType type, float posX, float posY, float speedX) {
		Entity entity = new Entity();

		AnimationComponent animComp = new AnimationComponent();
		animComp.animations.put(0, new Animation(0.2f, GameArt.bulletAnim.get(type), Animation.PlayMode.NORMAL));
		entity.add(animComp);

		StateComponent stateComp = new StateComponent();
		stateComp.set(0);
		entity.add(stateComp);

		TextureComponent texComp = new TextureComponent();
		TextureRegion region = animComp.animations.get(stateComp.get()).getKeyFrame(stateComp.time);
		texComp.region = region;
		if (type == BulletType.TRON_DELAY
				|| type == BulletType.TRON_UP
				|| type == BulletType.TRON_DOWN
				|| type == BulletType.TRON_SQUARE
				|| type == BulletType.TRON_WAVE
				|| type == BulletType.TRON_SAWTOOTH) {
			texComp.size.x = 40f * RenderingSystem.PIXELS_TO_WORLD;
			texComp.size.y = 40f * RenderingSystem.PIXELS_TO_WORLD;
		}
		else if (type == BulletType.SPEECH_DOWN
				|| type == BulletType.SPEECH_UP) {
			texComp.size.y = 40f * RenderingSystem.PIXELS_TO_WORLD;
			texComp.size.x = texComp.size.y * texComp.region.getRegionWidth() / (float)texComp.region.getRegionHeight();
		}
		else {
			texComp.size.x = 2.0f * texComp.region.getRegionWidth() * RenderingSystem.PIXELS_TO_WORLD;
			texComp.size.y = 2.0f * texComp.region.getRegionHeight() * RenderingSystem.PIXELS_TO_WORLD;
		}
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float z = Depths.bulletZ;
		transComp.body.initPosition(posX, posY, z);
		entity.add(transComp);

		CollisionComponent collComp = new CollisionComponent();
		collComp.type = CollisionType.OBJECT;
		collComp.rect.setSize(texComp.size.x, texComp.size.y);
		collComp.rect.setCenter(posX, posY);
		entity.add(collComp);

		MovementComponent moveComp = new MovementComponent();
		moveComp.linearVelocity.x = speedX;
		if (rotatingBullets.contains(type)) {
			moveComp.rotationalVelocity = speedX < 0 ? 90f : -90f;
		}
		entity.add(moveComp);

		entity.add(new BoundsComponent());

		if (type == BulletType.TRON_DELAY
				|| type == BulletType.TRON_UP
				|| type == BulletType.TRON_DOWN
				|| type == BulletType.TRON_SQUARE
				|| type == BulletType.TRON_WAVE
				|| type == BulletType.TRON_SAWTOOTH) {
			float hue = RNG.genFloat();
			Colors.setHue(texComp.color, hue, 1.0f, 1.0f);
			entity.add(ParticleFactory.createTrailEmitter(hue, 1.0f));
		}

		if (type == BulletType.RAINBOW) {
			texComp.color.set(1.0f, 0.0f, 0.0f, 1.0f);
			entity.add(ParticleFactory.createTrailEmitter(0, -1.0f));
		}
		else if (type == BulletType.MILK) {
			entity.add(ParticleFactory.createMilkEmitter());
		}
		else if (type == BulletType.BEER) {
			entity.add(ParticleFactory.createBeerEmitter());
		}
		else if (type == BulletType.FIREBALL) {
			entity.add(ParticleFactory.createFireballEmitter());
		}
		else if (type == BulletType.PIZZA) {
			entity.add(ParticleFactory.createPepperoniEmitter());
		}
		else if (type == BulletType.SPEECH_UP || type == BulletType.SPEECH_DOWN
				|| type == BulletType.TRON_UP || type == BulletType.TRON_DOWN) {
			ShiftComponent shiftComp = new ShiftComponent();
			shiftComp.up = (type == BulletType.SPEECH_UP || type == BulletType.TRON_UP);
			entity.add(shiftComp);
		}
		else if (type == BulletType.TRON_WAVE
				|| type == BulletType.JUGGLINGPIN) {
			entity.add(new WaveComponent());
		}
		else if (type == BulletType.TRON_SQUARE) {
			entity.add(new SquareComponent());
		}
		else if (type == BulletType.TRON_DELAY) {
			moveComp.linearVelocity.x /= 2;
			DelayComponent delayComp = new DelayComponent();
			delayComp.speed = 4.0f * moveComp.linearVelocity.x;
			delayComp.startdelay = 0.5f;
			delayComp.stopdelay = 1.5f - (5.0f / 8.0f);
			entity.add(delayComp);
		}
		else if (type == BulletType.TRON_SAWTOOTH) {
			entity.add(new SawtoothComponent());
		}

		return entity;
	}

	public static Entity createPlayerProjectile(PlayerType character, float posX, float posY, float speedX) {
		BulletCreateType createType = playerBulletMap.get(character);
		BulletType bulletType = getBulletType(createType);
		Entity entity = createProjectile(bulletType, posX, posY, speedX);

		CollisionComponent collComp = ComponentMappers.collision.get(entity);
		collComp.type = CollisionType.PLAYER_BULLET;

		return entity;
	}

	public static Entity createEnemyProjectile(BulletType bulletType, float posX, float posY, float speedX) {
		Entity entity = createProjectile(bulletType, posX, posY, speedX);

		CollisionComponent collComp = ComponentMappers.collision.get(entity);
		collComp.type = CollisionType.ENEMY_BULLET;

		BulletDirectionStyle style = getBulletDirectionStyle(bulletType);
		entity.add(createBulletTween(bulletType, getDirection(style), posY));

		return entity;
	}

	public static Entity createMiniBossProjectile(MiniBossType who, float posX, float posY, float speedX) {
		BulletCreateType createType = miniBossBulletMap.get(who);
		BulletType bulletType = getBulletType(createType);

		return createEnemyProjectile(bulletType, posX, posY, speedX);
	}

	public static Entity createBossProjectile(BossType who, float posX, float posY, float speedX) {
		BulletCreateType createType = bossBulletMap.get(who);
		BulletType bulletType = getBulletType(createType);

		return createEnemyProjectile(bulletType, posX, posY, speedX);
	}

	public static Entity createEndlessProjectile(float time, float posX, float posY, float speedX) {
		BulletType bulletType = getEndlessBulletType(time);
		//int r = RNG.genInt(2);
		//BulletType bulletType = BulletType.TRON_WAVE;

		return createEnemyProjectile(bulletType, posX, posY, speedX);
	}

	public static TweenComponent createBulletTween(BulletType type, final BulletDirection dir, float initY) {
		final TweenComponent tweenComp = new TweenComponent();

		final TweenSpec tweenSpec = new TweenSpec();
		tweenSpec.cycle = TweenSpec.Cycle.ONCE;
		tweenSpec.period = 0.5f;
		tweenSpec.start = initY;

		if (dir == BulletDirection.STRAIGHT)
			tweenSpec.end = 0.5f * BasicScreen.WORLD_HEIGHT;
		else if (dir == BulletDirection.DOWN)
			tweenSpec.end = 0.5f * BasicScreen.WORLD_HEIGHT - 0.5f;
		else if (dir == BulletDirection.UP)
			tweenSpec.end = 0.5f * BasicScreen.WORLD_HEIGHT + 0.5f;

		if (tweenSpec.end - initY < 0)
			tweenSpec.interp = Interpolation.circleIn;
		else
			tweenSpec.interp = Interpolation.circleOut;

		tweenSpec.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				TransformComponent tc = ComponentMappers.transform.get(e);
				CollisionComponent cc = ComponentMappers.collision.get(e);
				cc.rect.setY(a - 0.5f * cc.rect.getHeight());
				tc.body.updatePosY(a);
			}

			@Override
			public void endTween(Entity e) {
				if (ComponentMappers.wave.has(e)) {
					TweenComponent tc = ComponentMappers.tween.get(e);
					tc.tweenSpecs.add(createWaveTween(dir));
				}
				else if (ComponentMappers.square.has(e)) {
					TweenComponent tc = ComponentMappers.tween.get(e);
					tc.tweenSpecs.add(createSquareTween(dir));
				}
				else if (ComponentMappers.sawtooth.has(e)) {
					TweenComponent tc = ComponentMappers.tween.get(e);
					tc.tweenSpecs.add(createSawtoothTween(dir));
				}
			}
		};
		tweenComp.tweenSpecs.add(tweenSpec);

		return tweenComp;
	}

	public static BulletType getBulletType(BulletCreateType createType) {
		if (createType == BulletCreateType.BEER) {
			return BulletType.BEER;
		}
		else if (createType == BulletCreateType.SAR) {
			return BulletType.SAR;
		}
		else if (createType == BulletCreateType.LIGHTNING) {
			return BulletType.PIZZA;
		}
		else if (createType == BulletCreateType.MILK) {
			return BulletType.MILK;
		}
		else if (createType == BulletCreateType.PASTY) {
			return BulletType.PASTY;
		}
		else if (createType == BulletCreateType.JUGGLINGBALL) {
			return BulletType.JUGGLINGBALL;
		}
		else if (createType == BulletCreateType.RAINBOW) {
			return BulletType.RAINBOW;
		}
		else if (createType == BulletCreateType.SCIMITAR) {
			return BulletType.SCIMITAR;
		}
		else if (createType == BulletCreateType.SPEECH) {
			int r = RNG.genInt(2);
			return r == 1 ? BulletType.SPEECH_DOWN : BulletType.SPEECH_UP;
		}
		else if (createType == BulletCreateType.FIREBALL) {
			return BulletType.FIREBALL;
		}
		else if (createType == BulletCreateType.JUGGLINGPIN) {
			return BulletType.JUGGLINGPIN;
		}
		else if (createType == BulletCreateType.TRON) {
			int r = RNG.genInt(5);
			if (r == 0)
				return BulletType.TRON_DELAY;
			else if (r == 1)
				return BulletType.TRON_DOWN;
			else if (r == 2)
				return BulletType.TRON_SQUARE;
			else if (r == 3)
				return BulletType.TRON_WAVE;
			else
				return BulletType.TRON_UP;
		}
		else if (createType == BulletCreateType.RANDOM) {
			int r = RNG.genInt(21);
			if (r == 0)
				return BulletType.BEER;
			else if (r == 1)
				return BulletType.FIREBALL;
			else if (r == 2)
				return BulletType.JUGGLINGBALL;
			else if (r == 3)
				return BulletType.MILK;
			else if (r == 4)
				return BulletType.SAR;
			else if (r == 5)
				return BulletType.SPEECH_DOWN;
			else if (r == 6)
				return BulletType.SPEECH_UP;
			else if (r < 8)
				return BulletType.TRON_DELAY;
			else if (r < 11)
				return BulletType.TRON_DOWN;
			else if (r < 14)
				return BulletType.TRON_SQUARE;
			else if (r < 17)
				return BulletType.TRON_WAVE;
			else if (r < 20)
				return BulletType.TRON_UP;
			else
				return BulletType.JUGGLINGPIN;
		}
		else
			return BulletType.RAINBOW;
	}

	public static BulletType getEndlessBulletType(float time) {
		float tmax = 280;
		float mintronw = 0.5f;
		float tron_weight = Math.min(mintronw + (1.0f - mintronw) * (tmax - time) / tmax, 1.0f);
		float tron_r = RNG.genFloat();

		if (tron_r >= tron_weight) {
			return BulletType.FIREBALL;
		}
		else {
			int rmax = 4;
			if (time >= 120)
				rmax = 6;
			else if (time >= 60)
				rmax = 5;
			int r = RNG.genInt(rmax);
			if (r == 0)
				return BulletType.TRON_DOWN;
			else if (r == 1)
				return BulletType.TRON_UP;
			else if (r == 2)
				return BulletType.TRON_SQUARE;
			else if (r == 3)
				return BulletType.TRON_WAVE;
			else if (r == 4)
				return BulletType.TRON_SAWTOOTH;
			else if (r == 5)
				return BulletType.TRON_DELAY;
			else
				return BulletType.RAINBOW;
		}
	}

	public static BulletDirection getDirection(BulletDirectionStyle style) {
		BulletDirection dir = null;

		if (style == BulletDirectionStyle.ALL) {
			int d = RNG.genInt(3);
			if (d == 0)
				dir = BulletDirection.STRAIGHT;
			else if (d == 1)
				dir = BulletDirection.DOWN;
			else
				dir = BulletDirection.UP;
		}
		else if (style == BulletDirectionStyle.LOWER) {
			int d = RNG.genInt(2);
			if (d == 0)
				dir = BulletDirection.STRAIGHT;
			else
				dir = BulletDirection.DOWN;
		}
		else if (style == BulletDirectionStyle.UPPER) {
			int d = RNG.genInt(2);
			if (d == 0)
				dir = BulletDirection.UP;
			else
				dir = BulletDirection.STRAIGHT;
		}
		else if (style == BulletDirectionStyle.SPLIT) {
			int d = RNG.genInt(2);
			if (d == 0)
				dir = BulletDirection.UP;
			else
				dir = BulletDirection.DOWN;
		}

		return dir;
	}

	public static BulletDirectionStyle getBulletDirectionStyle(BulletType bulletType) {
		BulletDirectionStyle style;
		if (bulletType == BulletType.TRON_SQUARE
				|| bulletType == BulletType.TRON_WAVE
				|| bulletType == BulletType.TRON_SAWTOOTH
				|| bulletType == BulletType.JUGGLINGPIN)
			style = BulletDirectionStyle.SPLIT;
		else if (bulletType == BulletType.TRON_UP
				|| bulletType == BulletType.SPEECH_UP) {
			style = BulletDirectionStyle.LOWER;
		}
		else if (bulletType == BulletType.TRON_DOWN
				|| bulletType == BulletType.SPEECH_DOWN) {
			style = BulletDirectionStyle.UPPER;
		}
		else {
			style = BulletDirectionStyle.ALL;
		}

		return style;
	}

	public static TweenSpec createWaveTween(BulletDirection dir) {
		TweenSpec ts = new TweenSpec();

		if (dir == BulletDirection.DOWN) {
			ts.start = 0.5f * BasicScreen.WORLD_HEIGHT - 0.5f;
			ts.end = 0.5f * BasicScreen.WORLD_HEIGHT + 0.5f;
		}
		else {
			ts.start = 0.5f * BasicScreen.WORLD_HEIGHT + 0.5f;
			ts.end = 0.5f * BasicScreen.WORLD_HEIGHT - 0.5f;
		}
		ts.period = 1.0f;
		ts.interp = Interpolation.sine;
		ts.reverse = true;
		ts.cycle = TweenSpec.Cycle.INFLOOP;
		ts.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				TransformComponent tc = ComponentMappers.transform.get(e);
				CollisionComponent cc = ComponentMappers.collision.get(e);
				cc.rect.setY(a - 0.5f * cc.rect.getHeight());
				tc.body.updatePosY(a);
			}
		};

		return ts;
	}

	public static TweenSpec createSquareTween(BulletDirection dir) {
		TweenSpec ts = new TweenSpec();

		final float start, end;
		if (dir == BulletDirection.DOWN) {
			start = 0.5f * BasicScreen.WORLD_HEIGHT - 0.5f;
			end = 0.5f * BasicScreen.WORLD_HEIGHT + 0.5f;
		}
		else {
			start = 0.5f * BasicScreen.WORLD_HEIGHT + 0.5f;
			end = 0.5f * BasicScreen.WORLD_HEIGHT - 0.5f;
		}
		ts.period = 1.0f;
		ts.interp = Interpolation.linear;
		ts.reverse = true;
		ts.cycle = TweenSpec.Cycle.INFLOOP;
		ts.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				TransformComponent tc = ComponentMappers.transform.get(e);
				CollisionComponent cc = ComponentMappers.collision.get(e);

				float y = a < 0.5 ? start : end;
				cc.rect.setY(y - 0.5f * cc.rect.getHeight());
				tc.body.updatePosY(y);
			}
		};

		return ts;
	}

	public static TweenSpec createSawtoothTween(BulletDirection dir) {
		TweenSpec ts = new TweenSpec();

		final float start, end;
		if (dir == BulletDirection.DOWN) {
			start = 0.5f * BasicScreen.WORLD_HEIGHT - 0.5f;
			end = 0.5f * BasicScreen.WORLD_HEIGHT + 0.5f;
		}
		else {
			start = 0.5f * BasicScreen.WORLD_HEIGHT + 0.5f;
			end = 0.5f * BasicScreen.WORLD_HEIGHT - 0.5f;
		}
		ts.start = start;
		ts.end = end;
		ts.period = 1.0f;
		ts.interp = Interpolation.linear;
		ts.reverse = true;
		ts.cycle = TweenSpec.Cycle.INFLOOP;
		ts.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				TransformComponent tc = ComponentMappers.transform.get(e);
				CollisionComponent cc = ComponentMappers.collision.get(e);

				cc.rect.setY(a - 0.5f * cc.rect.getHeight());
				tc.body.updatePosY(a);
			}
		};

		return ts;
	}
}
