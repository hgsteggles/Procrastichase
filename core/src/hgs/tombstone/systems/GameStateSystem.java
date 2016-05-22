package hgs.tombstone.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Interpolation;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Enums.BossState;
import hgs.tombstone.elements.Enums.CollisionType;
import hgs.tombstone.elements.Enums.GameState;
import hgs.tombstone.elements.Enums.MusicControlType;
import hgs.tombstone.screens.BasicScreen;
import hgs.tombstone.world.ComponentFactory;
import hgs.tombstone.world.TransitionFactory;
import hgs.tombstone.world.World;
import hgs.tombstone.world.WorldUI;

/**
 * Created by harry on 03/04/16.
 */
public class GameStateSystem extends EntitySystem {
	private World world;

	private ImmutableArray<Entity> stateChanges;
	private ImmutableArray<Entity> players;
	private ImmutableArray<Entity> bosses;
	private ImmutableArray<Entity> bossEvents;
	private ImmutableArray<Entity> miniBossEvents;
	private ImmutableArray<Entity> music;
	private ImmutableArray<Entity> removeWhenDead;
	private ImmutableArray<Entity> moving;
	private ImmutableArray<Entity> emitting;
	private ImmutableArray<Entity> ticking;

	public static final int SCREEN_NONE = 0;
	public static final int SCREEN_MENU = 1;
	public static final int SCREEN_GAMEOVER = 2;
	public static final int SCREEN_NEXTCHAPTER = 3;
	public static final int SCREEN_COMPLETE = 4;

	public int screenChange = SCREEN_NONE;
	private float currChangeTime = 0.0f;
	private float maxChangeTime = 2.0f;
	public int nextScreenChange = SCREEN_NONE;

	public GameStateSystem(World world) {
		this.world = world;
	}

	@Override
	public void addedToEngine(Engine engine) {
		stateChanges = engine.getEntitiesFor(Family.all(GameStateComponent.class).get());
		players = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
		bosses = engine.getEntitiesFor(Family.all(BossComponent.class).get());
		bossEvents = engine.getEntitiesFor(Family.all(BossEventComponent.class).get());
		miniBossEvents = engine.getEntitiesFor(Family.all(MiniBossEventComponent.class).get());
		music = engine.getEntitiesFor(Family.all(MusicComponent.class).get());
		removeWhenDead = engine.getEntitiesFor(Family.exclude(KillObjectComponent.class, HeadsUpDisplayComponent.class, MusicComponent.class, SoundComponent.class).get());
		moving = engine.getEntitiesFor(Family.all(MovementComponent.class).get());
		emitting = engine.getEntitiesFor(Family.all(EmitterComponent.class).get());
		ticking = engine.getEntitiesFor(Family.all(TickerComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		if (nextScreenChange != SCREEN_NONE) {
			currChangeTime += deltaTime;
			if (currChangeTime > maxChangeTime) {
				screenChange = nextScreenChange;
				nextScreenChange = SCREEN_NONE;
			}

			return;
		}

		if (stateChanges.size() > 0) {
			Entity stateChangeEntity = stateChanges.get(0);
			GameStateComponent gameState = ComponentMappers.gamestate.get(stateChangeEntity);

			if (gameState.state == GameState.DEAD) {
				startDeadState();
			}
			else if (gameState.state == GameState.BOSS_CUTSCENE) {
				startBossCutSceneState();
			}
			else if (gameState.state == GameState.BOSS_FIGHT) {
				startBossFightState();
			}
			else if (gameState.state == GameState.MINI_BOSS) {
				startMiniBossState();
			}
			else if (gameState.state == GameState.CHAPTER_COMPLETE) {
				startChapterCompleteState();
			}
		}

		for (Entity stateChange : stateChanges) {
			getEngine().removeEntity(stateChange);
		}
	}

	private void startBossCutSceneState() {
		for (Entity bossEvent : bossEvents) {
			getEngine().removeEntity(bossEvent);
		}
		for (Entity player : players) {
			player.remove(GunComponent.class);
		}

		if (world.getLevel() != 5) {
			getEngine().addEntity(WorldUI.createBossName(world.boss));
			getEngine().addEntity(WorldUI.createHealthBar());
			getEngine().addEntity(WorldUI.createHealth());
		}
		getEngine().addEntity(world.createBoss());

		if (world.getLevel() == 3 || world.getLevel() == 5) {
			getEngine().addEntity(world.createLightning());
		}
	}

	private void startBossFightState() {
		for (Entity boss : bosses) {
			BossComponent bossComp = ComponentMappers.boss.get(boss);
			if (world.getLevel() != 5)
				boss.add(ComponentFactory.createBossGun(bossComp.who));
			else
				boss.add(ComponentFactory.createEndlessGun());
			StateComponent sc = ComponentMappers.state.get(boss);
			sc.set(BossState.PRIME.value());
			CollisionComponent cc = ComponentMappers.collision.get(boss);
			cc.type = CollisionType.ENEMY;
		}

		for (Entity player : players) {
			if (world.getLevel() != 5)
				player.add(ComponentFactory.createPlayerGun());
		}

		for (Entity m : music) {
			MusicComponent mc = ComponentMappers.music.get(m);
			mc.control = MusicControlType.FADEOUT;
		}

		getEngine().addEntity(world.createBossMusic());
	}

	private void startMiniBossState() {
		for (Entity bossEvent : miniBossEvents) {
			getEngine().removeEntity(bossEvent);
		}

		getEngine().addEntity(world.createMiniBoss());
	}

	private void startChapterCompleteState() {
		nextScreenChange = SCREEN_NEXTCHAPTER;

		for (Entity player : players) {
			MovementSystem.walkoff(player);
			player.remove(JumpComponent.class);
			player.remove(SlideComponent.class);

			player.remove(CollisionComponent.class);
			player.remove(ControlComponent.class);
			player.remove(CollisionComponent.class);
		}
		for (Entity boss : bosses) {
			boss.remove(GunComponent.class);
			boss.remove(CollisionComponent.class);
			boss.remove(BossComponent.class);
		}

		for (Entity m : music) {
			MusicComponent mc = ComponentMappers.music.get(m);
			mc.control = MusicControlType.FADEOUT;
		}

		getEngine().addEntity(TransitionFactory.createBlackFadeOverlay(maxChangeTime));
	}

	private void startDeadState() {
		nextScreenChange = SCREEN_GAMEOVER;

		for (Entity remove : removeWhenDead) {
			getEngine().removeEntity(remove);
		}

		for (Entity mover : moving) {
			if (!ComponentMappers.player.has(mover))
				mover.remove(MovementComponent.class);
		}

		for (Entity emitter : emitting) {
			emitter.remove(EmitterComponent.class);
		}

		for (Entity ticker : ticking) {
			ticker.remove(TickerComponent.class);
		}

		for (Entity player : players) {
			MovementComponent mc = ComponentMappers.movement.get(player);
			mc.linearVelocity.x = 0;

			player.remove(AnimationComponent.class);
			player.remove(CollisionComponent.class);
			player.remove(ControlComponent.class);
			player.remove(SlideComponent.class);
			player.remove(CollisionComponent.class);
		}

		//getEngine().addEntity(EntityFactory.createGameOverSound());

		for (Entity m : music) {
			MusicComponent mc = ComponentMappers.music.get(m);
			mc.control = MusicControlType.FADEOUT;
		}

		//closeBars();
		getEngine().addEntity(TransitionFactory.createGameoverBar(maxChangeTime, true));
		getEngine().addEntity(TransitionFactory.createGameoverBar(maxChangeTime, false));
		getEngine().addEntity(TransitionFactory.createBlackFadeOverlay(maxChangeTime));
	}
}
