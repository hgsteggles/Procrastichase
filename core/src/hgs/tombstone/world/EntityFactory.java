package hgs.tombstone.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import hgs.tombstone.assets.Assets;
import hgs.tombstone.assets.GameArt;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Depths;
import hgs.tombstone.elements.Enums;
import hgs.tombstone.elements.GameParameters;
import hgs.tombstone.screens.BasicScreen;
import hgs.tombstone.systems.RenderingSystem;

/**
 * Created by harry on 05/04/16.
 */
public class EntityFactory {
	public static Entity createRIP(float posX) {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.rip;
		texComp.size.x = 2.0f * RenderingSystem.PIXELS_TO_WORLD * texComp.region.getRegionWidth();
		texComp.size.y = 2.0f * RenderingSystem.PIXELS_TO_WORLD * texComp.region.getRegionHeight();
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		transComp.body.initPosition(posX + 3.8f, 0.32f + 0.5f * BasicScreen.WORLD_HEIGHT, Depths.worldbgZ + 1);
		entity.add(transComp);

		return entity;
	}

	public static Entity createFramedPicture(float posX, int level) {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		if (level == 1)
			texComp.region = GameArt.frameCrab;
		else if (level == 2)
			texComp.region = GameArt.frameHorsehead;
		else if (level == 3)
			texComp.region = GameArt.frameRing;
		else if (level == 4)
			texComp.region = GameArt.frameSombrero;
		texComp.size.x = 2.0f * RenderingSystem.PIXELS_TO_WORLD * texComp.region.getRegionWidth();
		texComp.size.y = 2.0f * RenderingSystem.PIXELS_TO_WORLD * texComp.region.getRegionHeight();
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		transComp.body.initPosition(posX + 6.1f, 0.32f + 0.5f * BasicScreen.WORLD_HEIGHT, Depths.worldbgZ + 1);
		entity.add(transComp);

		return entity;
	}

	public static Entity createStarryBackground() {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = new TextureRegion(GameArt.backgroundStars);
		texComp.size.x = 1.0f * RenderingSystem.PIXELS_TO_WORLD * texComp.region.getRegionWidth();
		texComp.size.y = 1.0f * RenderingSystem.PIXELS_TO_WORLD * texComp.region.getRegionHeight();
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		transComp.body.initPosition(0.5f * BasicScreen.WORLD_WIDTH, 0.5f * BasicScreen.WORLD_HEIGHT, Depths.worldbgZ);
		entity.add(transComp);

		entity.add(new HeadsUpDisplayComponent());

		ScrollComponent scrollComp = new ScrollComponent();
		scrollComp.speed.set(-0.2f, 0.0f);
		scrollComp.rotation = 0f;
		entity.add(scrollComp);

		return entity;
	}
	public static Entity createTombstone(float posX) {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.tombstone;
		texComp.size.x = 2.0f * texComp.region.getRegionWidth() * RenderingSystem.PIXELS_TO_WORLD;
		texComp.size.y = 2.0f * texComp.region.getRegionHeight() * RenderingSystem.PIXELS_TO_WORLD;
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float x = posX;
		float y = BasicScreen.WORLD_HEIGHT / 2.0f;
		float z = Depths.playerZ - 1;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		entity.add(new KillObjectComponent());

		return entity;
	}

	public static Entity createLightning(float posX) {
		Entity entity = new Entity();

		StateComponent stateComp = new StateComponent();
		stateComp.set(0);
		entity.add(stateComp);

		float strikeTime = 0.25f;
		float lifetime = 0.5f;

		AnimationComponent animComp = new AnimationComponent();
		animComp.animations.put(stateComp.get(), new Animation(strikeTime / GameArt.boltAnim.size, GameArt.boltAnim, Animation.PlayMode.NORMAL));
		entity.add(animComp);

		TextureComponent texComp = new TextureComponent();
		texComp.region = animComp.animations.get(stateComp.get()).getKeyFrame(0);
		texComp.size.x = 2.0f * texComp.region.getRegionWidth() / (float)texComp.region.getRegionHeight();
		texComp.size.y = 2.0f;
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float x = posX;
		float y = BasicScreen.WORLD_HEIGHT / 2.0f;
		float z = Depths.playerZ + 1;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		TickerComponent tickComp = new TickerComponent();
		tickComp.finishActive = true;
		tickComp.duration = lifetime;
		tickComp.finish = new EventInterface() {
			@Override
			public void dispatchEvent(Entity e) {
				e.add(new RemovalComponent());
			}
		};
		entity.add(tickComp);

		return entity;
	}

	public static Entity createBackground(float posX) {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.mainBackgroundScrollable;
		texComp.size.x = 20.0f;
		texComp.size.y = 2.0f;
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float x = posX;
		float y = BasicScreen.WORLD_HEIGHT / 2.0f;
		float z = Depths.worldbgZ;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		entity.add(new KillObjectComponent());

		return entity;
	}

	public static Entity createBattleBackground(float posX) {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.battleBackground;
		texComp.size.x = 2.0f * texComp.region.getRegionWidth() * RenderingSystem.PIXELS_TO_WORLD;
		texComp.size.y = 2.0f * texComp.region.getRegionHeight() * RenderingSystem.PIXELS_TO_WORLD;
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float x = posX + BasicScreen.WORLD_WIDTH - texComp.size.x / 2f;
		float y = BasicScreen.WORLD_HEIGHT / 2.0f;
		float z = Depths.worldbgZ + 1;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		entity.add(new KillObjectComponent());

		return entity;
	}

	public static Entity createTronBackground(float posX, float levelSpeed) {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.tronBackground;
		texComp.size.x = 2.0f;
		texComp.size.y = 2.0f;
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float x = posX;
		float y = BasicScreen.WORLD_HEIGHT / 2.0f;
		float z = Depths.worldbgZ;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		MovementComponent moveComp = new MovementComponent();
		moveComp.linearVelocity.x = 0.92f * levelSpeed;
		entity.add(moveComp);

		entity.add(new KillObjectComponent());

		return entity;
	}

	public static Entity createBossEventBox(float posX) {
		Entity entity = new Entity();

		CollisionComponent collComp = new CollisionComponent();
		collComp.type = Enums.CollisionType.BOSS_AREA;
		TextureRegion region = GameArt.playerRunning.get(Enums.PlayerType.FERNANDO).first();
		collComp.rect.setSize(10f, 2.0f);
		collComp.rect.setCenter(5.0f + posX + 0 + region.getRegionWidth() / (float)(region.getRegionHeight()), BasicScreen.WORLD_HEIGHT / 2.0f);
		entity.add(collComp);

		entity.add(new BossEventComponent());

		return entity;
	}

	public static Entity createMiniBossEventBox(float posX) {
		Entity entity = new Entity();

		CollisionComponent collComp = new CollisionComponent();
		collComp.type = Enums.CollisionType.MINI_BOSS_AREA;
		collComp.rect.setSize(10f, 2.0f);
		collComp.rect.setCenter(5.0f + posX, BasicScreen.WORLD_HEIGHT / 2.0f);
		entity.add(collComp);

		entity.add(new MiniBossEventComponent());

		return entity;
	}

	public static Entity createFloorObject(float posX, TextureRegion region) {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = region;
		texComp.size.x = 2.0f * region.getRegionWidth() * RenderingSystem.PIXELS_TO_WORLD;
		texComp.size.y = 2.0f * region.getRegionHeight() * RenderingSystem.PIXELS_TO_WORLD;
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float x = posX;
		float y = BasicScreen.WORLD_HEIGHT / 2.0f - 1.0f + texComp.size.y / 2.0f;
		float z = Depths.objectZ;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		CollisionComponent collComp = new CollisionComponent();
		collComp.type = Enums.CollisionType.OBJECT;
		collComp.rect.setSize(texComp.size.x, texComp.size.y);
		collComp.rect.setCenter(x, y);
		entity.add(collComp);

		return entity;
	}

	public static Entity createCeilingObject(float posX, TextureRegion region) {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = region;
		texComp.size.x = 2.0f * region.getRegionWidth() * RenderingSystem.PIXELS_TO_WORLD;
		texComp.size.y = 2.0f * region.getRegionHeight() * RenderingSystem.PIXELS_TO_WORLD;
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float x = posX;
		float y = BasicScreen.WORLD_HEIGHT / 2.0f + 1.0f - texComp.size.y / 2.0f;
		float z = Depths.objectZ;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		CollisionComponent collComp = new CollisionComponent();
		collComp.type = Enums.CollisionType.OBJECT;
		collComp.rect.setSize(texComp.size.x, texComp.size.y);
		collComp.rect.setCenter(x, y);
		entity.add(collComp);

		return entity;
	}

	public static Entity createThrownObject(Enums.BulletType bulletType, float posX, float speedX) {
		Entity entity = BulletFactory.createProjectile(bulletType, posX, 0.5f * BasicScreen.WORLD_HEIGHT, speedX);

		CollisionComponent collComp = ComponentMappers.collision.get(entity);
		collComp.type = Enums.CollisionType.ENEMY_BULLET;

		return entity;
	}

	public static Entity createStackedTable(float posX) {
		Entity entity = createFloorObject(posX, GameArt.stackedTable);

		CollisionComponent collComp = ComponentMappers.collision.get(entity);
		collComp.rect.setY(collComp.rect.getY() + 0.34f * collComp.rect.getHeight());
		collComp.rect.setHeight(0.66f * collComp.rect.getHeight());

		return entity;
	}

	public static Entity createTable(float posX) {
		Entity entity = createFloorObject(posX, GameArt.table);

		CollisionComponent collComp = ComponentMappers.collision.get(entity);
		collComp.rect.setY(collComp.rect.getY() + 0.34f * collComp.rect.getHeight());
		collComp.rect.setHeight(0.1f);

		return entity;
	}

	public static Entity createHangingStar(float posX) {
		return createCeilingObject(posX, GameArt.hangStar);
	}

	public static Entity createHangingEarth(float posX) {
		return createCeilingObject(posX, GameArt.hangEarth);
	}

	public static Entity createTableSimon(float posX) {
		final Entity entity = createFloorObject(posX, GameArt.tableSimon);

		CollisionComponent collComp = ComponentMappers.collision.get(entity);
		collComp.rect.setY(collComp.rect.getY() + 0.40f * collComp.rect.getHeight());
		collComp.rect.setHeight(0.60f * collComp.rect.getHeight());
		collComp.type = Enums.CollisionType.ENEMY;

		//entity.add(new PeeComponent());
		entity.add(ParticleFactory.createPeeEmitter());
		entity.add(new DisableComponent());

		BoundsComponent boundsComp = new BoundsComponent();
		boundsComp.onEnter = new EventInterface() {
			@Override
			public void dispatchEvent(Entity e) {
				e.add(ParticleFactory.createPeeEmitter());
			}
		};
		entity.add(boundsComp);

		return entity;
	}

	public static Entity createTonye(float posX) {
		Entity entity = createFloorObject(posX, GameArt.tonye);

		CollisionComponent collComp = ComponentMappers.collision.get(entity);
		collComp.rect.height *= 2.0f / 3.0f;
		collComp.type = Enums.CollisionType.ENEMY;

		//entity.add(new PeeComponent());
		entity.add(ParticleFactory.createMusicNoteEmitter());

		return entity;
	}

	public static Entity createCat(float posX, float levelSpeed) {
		StateComponent stateComp = new StateComponent();
		stateComp.set(0);

		AnimationComponent animComp = new AnimationComponent();
		animComp.animations.put(0, new Animation(0.5f, GameArt.cat, Animation.PlayMode.LOOP));

		Entity entity = createFloorObject(posX, animComp.animations.get(0).getKeyFrame(0));

		entity.add(stateComp);
		entity.add(animComp);

		CollisionComponent collComp = ComponentMappers.collision.get(entity);
		collComp.rect.height *= 1.0f / 3.0f;
		collComp.type = Enums.CollisionType.ENEMY;

		TickerComponent tickComp = new TickerComponent();
		tickComp.finishActive = true;
		tickComp.duration = (posX - BasicScreen.WORLD_WIDTH - 2) / levelSpeed;
		tickComp.finish = new EventInterface() {
			@Override
			public void dispatchEvent(Entity e) {
				MovementComponent moveComp = new MovementComponent();
				moveComp.linearVelocity.x = -1.0f;
				e.add(moveComp);
			}
		};
		entity.add(tickComp);

		return entity;
	}

	public static Entity createKathryn(float posX) {
		Entity entity = createFloorObject(posX, GameArt.kathryn);

		CollisionComponent collComp = ComponentMappers.collision.get(entity);
		collComp.rect.height *= 2.0f / 3.0f;
		collComp.type = Enums.CollisionType.ENEMY;

		return entity;
	}

	public static Entity createMo(float posX) {
		Entity entity = createFloorObject(posX, GameArt.mo);

		CollisionComponent collComp = ComponentMappers.collision.get(entity);
		collComp.rect.height *= 2.0f / 3.0f;
		collComp.type = Enums.CollisionType.ENEMY;

		return entity;
	}

	public static Entity createAbi(final float posX) {
		Entity entity = createFloorObject(posX, GameArt.abi);

		CollisionComponent collComp = ComponentMappers.collision.get(entity);
		collComp.rect.height *= 1.0f / 3.0f;
		collComp.type = Enums.CollisionType.ENEMY;

		//entity.add(new PeeComponent());
		BoundsComponent boundsComp = new BoundsComponent();
		boundsComp.onEnter = new EventInterface() {
			@Override
			public void dispatchEvent(Entity e) {
				e.add(ParticleFactory.createSleepEmitter(posX));
			}
		};

		entity.add(new DisableComponent());

		return entity;
	}

	public static Entity createBall(float posX) {
		return createFloorObject(posX, GameArt.ball);
	}

	public static Entity createPin(float posX) {
		return createFloorObject(posX, GameArt.pin);
	}

	public static Entity createJugglingBall(float posX) {
		return createFloorObject(posX, GameArt.bulletAnim.get(Enums.BulletType.JUGGLINGBALL).first());
	}

	public static Entity createComputerDesk(float posX) {
		return createFloorObject(posX, GameArt.computerDesk);
	}

	public static Entity createComputerDeskDouble(float posX) {
		return createFloorObject(posX, GameArt.computerDeskDouble);
	}

	public static Entity createBaubles(float posX) {
		return createCeilingObject(posX, GameArt.baubles);
	}

	public static Entity createDrawers(float posX) {
		return createFloorObject(posX, GameArt.drawers);
	}

	public static Entity createMinifridge(float posX) {
		return createFloorObject(posX, GameArt.minifridge);
	}

	public static Entity createChairRight(float posX) {
		return createFloorObject(posX, GameArt.chairRight);
	}

	public static Entity createChairLeft(float posX) {
		return createFloorObject(posX, GameArt.chairLeft);
	}

	public static Entity createGameState(Enums.GameState state) {
		Entity entity = new Entity();

		GameStateComponent gsc = new GameStateComponent();
		gsc.state = state;
		entity.add(gsc);

		return entity;
	}

	public static Entity createPage(float posX, boolean isGrounded) {
		Entity entity = isGrounded ? createFloorObject(posX, GameArt.page) : createCeilingObject(posX, GameArt.page);

		TweenComponent tweenComp = new TweenComponent();

		TweenSpec tweenSpec = new TweenSpec();
		tweenSpec.cycle = TweenSpec.Cycle.INFLOOP;
		tweenSpec.period = 1.0f;
		tweenSpec.start = 0.0f;
		tweenSpec.end = 0.1f;
		tweenSpec.reverse = true;
		tweenSpec.interp = Interpolation.sine;
		TextureComponent texComp = ComponentMappers.texture.get(entity);
		final float offsetY = isGrounded ? -1.0f + texComp.size.y / 2.0f : 1.0f - texComp.size.y;
		tweenSpec.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				TransformComponent tc = ComponentMappers.transform.get(e);
				TextureComponent txc = ComponentMappers.texture.get(e);

				tc.body.updatePosY(a + BasicScreen.WORLD_HEIGHT / 2.0f + offsetY);
			}
		};
		tweenComp.tweenSpecs.add(tweenSpec);
		entity.add(tweenComp);

		CollisionComponent collComp = ComponentMappers.collision.get(entity);
		collComp.type = Enums.CollisionType.PICKUP;

		return entity;
	}

	public static Entity createBook() {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.book;
		texComp.size.x = BasicScreen.WORLD_WIDTH - 2;
		texComp.size.y = BasicScreen.WORLD_HEIGHT - 2;
		texComp.color.set(Color.WHITE);
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float x = BasicScreen.WORLD_WIDTH / 2.0f;
		float y = BasicScreen.WORLD_HEIGHT / 2.0f;
		float z = 0.0f;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		return entity;
	}
}
