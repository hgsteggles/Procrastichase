package hgs.tombstone.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import hgs.tombstone.components.ComponentMappers;
import hgs.tombstone.components.DelayComponent;
import hgs.tombstone.components.MovementComponent;

/**
 * Created by harry on 12/04/16.
 */
public class DelaySystem extends IteratingSystem {
	public DelaySystem() {
		super(Family.all(DelayComponent.class, MovementComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		DelayComponent delayComp = ComponentMappers.delay.get(entity);
		MovementComponent moveComp = ComponentMappers.movement.get(entity);

		if (delayComp.time > delayComp.startdelay) {
			if (delayComp.time < delayComp.stopdelay) {
				moveComp.linearVelocity.x = 0;
			}
			else {
				moveComp.linearVelocity.x = delayComp.speed;
			}
		}

		delayComp.time += deltaTime;
	}
}
