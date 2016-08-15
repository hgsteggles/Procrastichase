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
		BoundsComponent bc = ComponentMappers.bounds.get(entity);

		if (!within(tc.body.getPosition().x, tc.body.getPosition().y)) {
			if (bc.entered)
				getEngine().removeEntity(entity);
		}
		else if (!bc.entered) {
			bc.entered = true;

			if (bc.onEnter != null)
				bc.onEnter.dispatchEvent(entity);
		}
	}

	private boolean within(float x, float y) {
		return !(x < camera.position.x - camera.viewportWidth / 2.0f - 2
				|| x > camera.position.x + camera.viewportWidth / 2.0f + 2
				|| y < 1.8
				|| y > 4.2);
	}
}
