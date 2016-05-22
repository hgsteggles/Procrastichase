package hgs.tombstone.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Enums;
import hgs.tombstone.world.EntityFactory;

/**
 * Created by harry on 03/04/16.
 */
public class BossSystem extends IteratingSystem {
	public BossSystem() {
		super(Family.all(BossComponent.class, TransformComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		BossComponent bc = ComponentMappers.boss.get(entity);
		TransformComponent tc = ComponentMappers.transform.get(entity);
		GunComponent gunComp = ComponentMappers.gun.get(entity);
		StateComponent stateComp = ComponentMappers.state.get(entity);

		if (bc.health <= 0) {
			getEngine().addEntity(EntityFactory.createGameState(Enums.GameState.CHAPTER_COMPLETE));
		}

		if (gunComp != null) {
			GunSystem.shoot(gunComp);

			if (gunComp.heat < 1.0f / (2.0f * gunComp.fireRate))
				stateComp.set(Enums.BossState.PRIME.value());
			else
				stateComp.set(Enums.BossState.THROW.value());
		}
		else {
			if (stateComp.time > bc.intro_time) {
				getEngine().addEntity(EntityFactory.createGameState(Enums.GameState.BOSS_FIGHT));
			}
		}
	}
}
