package hgs.tombstone.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import hgs.tombstone.components.*;

/**
 * Created by harry on 03/04/16.
 */
public class BoundsSystem extends IteratingSystem {
	Rectangle boundsRect;
	OrthographicCamera camera;

	public BoundsSystem(OrthographicCamera camera) {
		super(Family.all(TransformComponent.class, BoundsComponent.class).get());

		this.camera = camera;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		TransformComponent tc = ComponentMappers.transform.get(entity);

		if (tc.body.getPosition().x < camera.position.x - camera.viewportWidth / 2.0f - 2
			|| tc.body.getPosition().x > camera.position.x + camera.viewportWidth / 2.0f + 2
			|| tc.body.getPosition().y < 1.8
			|| tc.body.getPosition().y > 4.2) {

			getEngine().removeEntity(entity);
		}
	}
}
