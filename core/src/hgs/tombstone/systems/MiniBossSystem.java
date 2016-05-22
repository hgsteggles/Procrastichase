package hgs.tombstone.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Enums;
import hgs.tombstone.world.EntityFactory;

/**
 * Created by harry on 03/04/16.
 */
public class MiniBossSystem extends IteratingSystem {
	public MiniBossSystem() {
		super(Family.all(MiniBossComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		MiniBossComponent mbc = ComponentMappers.miniboss.get(entity);
		TransformComponent tc = ComponentMappers.transform.get(entity);
		MovementComponent moveComp = ComponentMappers.movement.get(entity);
		GunComponent gunComp = ComponentMappers.gun.get(entity);
		StateComponent stateComp = ComponentMappers.state.get(entity);

		if (moveComp.linearVelocity.x > mbc.maxSpeed) {
			moveComp.linearVelocity.x = mbc.maxSpeed;
			moveComp.linearAcceleration.x = 0;
		}

		if (gunComp != null) {
			GunSystem.shoot(gunComp);

			if (gunComp.heat < 1.0f / (2.0f * gunComp.fireRate))
				stateComp.set(Enums.MiniBossState.PRIME.value());
			else
				stateComp.set(Enums.MiniBossState.THROW.value());
		}
		else if (stateComp.get() != Enums.MiniBossState.PRIME.value()) {
			stateComp.set(Enums.MiniBossState.PRIME.value());
		}
	}
}
