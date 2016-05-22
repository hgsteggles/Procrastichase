package hgs.tombstone.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import hgs.tombstone.components.BoundsComponent;
import hgs.tombstone.components.ComponentMappers;
import hgs.tombstone.components.RemovalComponent;
import hgs.tombstone.components.TransformComponent;

/**
 * Created by harry on 03/04/16.
 */
public class RemovalSystem extends IteratingSystem {
	public RemovalSystem() {
		super(Family.all(RemovalComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		getEngine().removeEntity(entity);
	}
}
