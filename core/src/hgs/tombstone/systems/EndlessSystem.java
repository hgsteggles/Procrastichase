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
public class EndlessSystem extends IteratingSystem {
	public EndlessSystem() {
		super(Family.all(EndlessComponent.class, GunComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		GunComponent gunComp = ComponentMappers.gun.get(entity);
		EndlessComponent endComp = ComponentMappers.endless.get(entity);

		if (endComp.time >= 240)
			gunComp.fireRate = 2.1f;
		else if (endComp.time >= 210)
			gunComp.fireRate = 2.0f;
		else if (endComp.time >= 180)
			gunComp.fireRate = 1.9f;
		else if (endComp.time >= 150)
			gunComp.fireRate = 1.8f;
		else if (endComp.time >= 120)
			gunComp.fireRate = 1.7f;
		else if (endComp.time >= 90)
			gunComp.fireRate = 1.6f;
		else if (endComp.time >= 70)
			gunComp.fireRate = 1.4f;
		else if (endComp.time >= 50)
			gunComp.fireRate = 1.3f;
		else if (endComp.time >= 30)
			gunComp.fireRate = 1.2f;
		else
			gunComp.fireRate = 1.0f;


		endComp.time += deltaTime;
	}
}
