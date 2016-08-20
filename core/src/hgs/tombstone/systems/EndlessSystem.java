package hgs.tombstone.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Enums;
import hgs.tombstone.elements.GameParameters;
import hgs.tombstone.world.EntityFactory;

/**
 * Created by harry on 03/04/16.
 */
public class EndlessSystem extends IteratingSystem {
	public EndlessSystem() {
		super(Family.all(EndlessComponent.class, GunComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		GunComponent gunComp = ComponentMappers.gun.get(entity);
		EndlessComponent endComp = ComponentMappers.endless.get(entity);

		float delay = 0.28f;

		if (endComp.time >= 210)
			delay = 0.08f;
		else if (endComp.time >= 180)
			delay = 0.10f;
		else if (endComp.time >= 150)
			delay = 0.12f;
		else if (endComp.time >= 120)
			delay = 0.14f;
		else if (endComp.time >= 90)
			delay = 0.17f;
		else if (endComp.time >= 70)
			delay = 0.20f;
		else if (endComp.time >= 50)
			delay = 0.23f;
		else if (endComp.time >= 30)
			delay = 0.26f;

		gunComp.fireRate = 1.0f / (GameParameters.baseMaxJumpTime + delay);

		endComp.time += deltaTime;
	}
}
