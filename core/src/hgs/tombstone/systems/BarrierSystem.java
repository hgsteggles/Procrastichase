package hgs.tombstone.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Interpolation;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Enums;
import hgs.tombstone.elements.Enums.GameState;
import hgs.tombstone.screens.BasicScreen;
import hgs.tombstone.world.World;

/**
 * Created by harry on 03/04/16.
 */
public class BarrierSystem extends EntitySystem {
	private ImmutableArray<Entity> barriers;
	private ImmutableArray<Entity> collideables;

	public BarrierSystem() {

	}

	@Override
	public void addedToEngine(Engine engine) {
		barriers = engine.getEntitiesFor(Family.all(BarrierComponent.class).get());
		collideables = engine.getEntitiesFor(Family.all(CollisionComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		for (Entity barrier : barriers) {
			TransformComponent tc = ComponentMappers.transform.get(barrier);
			BarrierComponent bc = ComponentMappers.barrier.get(barrier);

			bc.rect.setCenter(tc.body.getPosition().x, tc.body.getPosition().y);

			for (Entity collideable : collideables) {
				CollisionComponent cc = ComponentMappers.collision.get(collideable);
				if (ComponentMappers.shift.has(collideable)) {
					if (cc.type == Enums.CollisionType.ENEMY_BULLET && cc.rect.overlaps(bc.rect)) {
						ShiftComponent shiftComp = ComponentMappers.shift.get(collideable);
						TransformComponent transComp = ComponentMappers.transform.get(collideable);

						if (shiftComp.up) {
							transComp.body.updatePosY(transComp.body.getPosition().y + 0.5f);
						}
						else {
							transComp.body.updatePosY(transComp.body.getPosition().y - 0.5f);
						}
						cc.rect.setCenter(transComp.body.getPosition().x, transComp.body.getPosition().y);

						collideable.remove(ShiftComponent.class);
					}
				}
			}
		}
	}
}
