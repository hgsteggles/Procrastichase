package hgs.tombstone.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import hgs.tombstone.components.*;

/**
 * Created by harry on 05/04/16.
 */
public class StopSystem extends IteratingSystem {
	public StopSystem() {
		super(Family.all(StopComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		TransformComponent tc = ComponentMappers.transform.get(entity);
		MovementComponent mc = ComponentMappers.movement.get(entity);
		StopComponent sc = ComponentMappers.stop.get(entity);

		if (tc.body.getPosition().x > sc.endX) {
			tc.body.initPosX(tc.body.getPrevPosition().x);
			tc.body.updatePosX(sc.endX);
			if (sc.stopInterface != null)
				sc.stopInterface.dispatchEvent(entity);
			mc.linearVelocity.x = 0;
		}
	}
}
