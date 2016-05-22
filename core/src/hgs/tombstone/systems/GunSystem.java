package hgs.tombstone.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import hgs.tombstone.components.ClickComponent;
import hgs.tombstone.components.ComponentMappers;
import hgs.tombstone.components.GunComponent;
import hgs.tombstone.world.EntityFactory;

/**
 * Created by harry on 02/04/16.
 */
public class GunSystem  extends IteratingSystem {
	public GunSystem() {
		super(Family.all(GunComponent.class).get());
	}


	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		GunComponent gunComp = ComponentMappers.gun.get(entity);

		gunComp.heat -= deltaTime;

		if (gunComp.shoot) {
			gunComp.heat = 1.0f / gunComp.fireRate;
			gunComp.shoot = false;

			if (gunComp.shootInterface != null)
				gunComp.shootInterface.shoot(entity, getEngine());
		}
	}

	public static void shoot(GunComponent gunComp) {
		if (gunComp != null && gunComp.heat < 0)
			gunComp.shoot = true;
	}
}
