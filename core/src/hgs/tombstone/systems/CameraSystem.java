package hgs.tombstone.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import hgs.tombstone.components.CameraComponent;
import hgs.tombstone.components.ComponentMappers;
import hgs.tombstone.components.MovementComponent;
import hgs.tombstone.components.TransformComponent;

/**
 * Created by harry on 02/04/16.
 */
public class CameraSystem extends IteratingSystem {
	public CameraSystem() {
		super(Family.all(CameraComponent.class, TransformComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {

	}

	public void update(float interp) {
		for (Entity entity : getEntities()) {
			TransformComponent tc = ComponentMappers.transform.get(entity);
			CameraComponent cc = ComponentMappers.camera.get(entity);

			float x = interp * tc.body.getPosition().x + (1.0f - interp) * tc.body.getPrevPosition().x;
			float y = interp * tc.body.getPosition().y + (1.0f - interp) * tc.body.getPrevPosition().y;

			cc.camera.position.x = x;
			cc.camera.position.y = y;
		}
	}
}