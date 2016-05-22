package hgs.tombstone.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import hgs.tombstone.TombstoneGame;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Enums.PlayerState;
import hgs.tombstone.elements.RNG;

public class ShakeSystem extends IteratingSystem {
	public ShakeSystem() {
		super(Family.all(ShakeComponent.class, TransformComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		TransformComponent transComp = ComponentMappers.transform.get(entity);
		ShakeComponent shakeComp = ComponentMappers.shake.get(entity);
		MovementComponent moveComp = ComponentMappers.movement.get(entity);

		if (moveComp.linearVelocity.x == 0 && moveComp.linearVelocity.y == 0) {
			shakeComp.currRadius = shakeComp.maxRadius * (1.0f - shakeComp.currTime / shakeComp.maxTime);
			float shakeX = shakeComp.posX + shakeComp.currRadius * RNG.genRange(-1f, 1f);
			float shakeY = shakeComp.posY + shakeComp.currRadius * RNG.genRange(-1f, 1f);

			transComp.body.updatePosX(shakeX);
			transComp.body.updatePosY(shakeY);
		}

		shakeComp.currTime += deltaTime;

		if (shakeComp.currTime > shakeComp.maxTime)
			entity.remove(ShakeComponent.class);
	}
}
