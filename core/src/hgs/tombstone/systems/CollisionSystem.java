package hgs.tombstone.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Enums.*;
import hgs.tombstone.world.EntityFactory;
import hgs.tombstone.world.SoundFactory;
import hgs.tombstone.world.World;

/**
 * Created by harry on 03/04/16.
 */
public class CollisionSystem extends IteratingSystem {
	private World world;

	public CollisionSystem(World world) {
		super(Family.all(CollisionComponent.class).get());
		this.world = world;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CollisionComponent cc1 = ComponentMappers.collision.get(entity);

		for (Entity other : getEntities()) {
			CollisionComponent cc2 = ComponentMappers.collision.get(other);

			if (cc1.type == CollisionType.PLAYER) {
				if ((cc2.type == CollisionType.OBJECT
						|| cc2.type == CollisionType.ENEMY
						|| cc2.type == CollisionType.ENEMY_BULLET)
						&& cc1.rect.overlaps(cc2.rect)) {
					other.add(new KillObjectComponent());
					getEngine().addEntity(EntityFactory.createGameState(GameState.DEAD));
				}
				else if (cc2.type == CollisionType.PICKUP
						&& cc1.rect.overlaps(cc2.rect)) {
					world.addPage(1);

					getEngine().addEntity(SoundFactory.createPageTurnSound());

					getEngine().removeEntity(other);
				}
				else if (cc2.type == CollisionType.BOSS_AREA
						&& cc1.rect.overlaps(cc2.rect)) {
					getEngine().addEntity(EntityFactory.createGameState(GameState.BOSS_CUTSCENE));
				}
				else if (cc2.type == CollisionType.MINI_BOSS_AREA
						&& cc1.rect.overlaps(cc2.rect)) {
					getEngine().addEntity(EntityFactory.createGameState(GameState.MINI_BOSS));
				}
			}
			else if (cc1.type == CollisionType.PLAYER_BULLET
					&& cc2.type == CollisionType.ENEMY
					&& cc1.rect.overlaps(cc2.rect)) {
				if (ComponentMappers.disable.has(other)) {
					if (ComponentMappers.emitter.has(other)) {
						other.remove(EmitterComponent.class);
					}
				}
				if (ComponentMappers.boss.has(other)) {
					BossComponent bossComp = ComponentMappers.boss.get(other);
					bossComp.health -= 1;
				}

				getEngine().removeEntity(entity);
			}
			else if (cc1.type == CollisionType.ENEMY
					&& cc2.type == CollisionType.BOSS_AREA
					&& cc1.rect.overlaps(cc2.rect)) {
				entity.remove(GunComponent.class);
			}
		}
	}
}