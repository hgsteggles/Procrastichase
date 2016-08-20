package hgs.tombstone.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import hgs.tombstone.components.*;
import hgs.tombstone.screens.BasicScreen;
import hgs.tombstone.elements.Enums.*;

/**
 * Created by harry on 02/04/16.
 */
public class SlideSystem extends IteratingSystem {
	public SlideSystem() {
		super(Family.all(SlideComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		SlideComponent slideComp = ComponentMappers.slide.get(entity);
		TransformComponent transComp = ComponentMappers.transform.get(entity);
		StateComponent stateComp = ComponentMappers.state.get(entity);

		if (stateComp.get() == PlayerState.SLIDE.value()) {
			if (stateComp.time > slideComp.slideTime) {
				getUp(entity);
			}
		}
	}

	static void slide(Entity entity, int pointer) {
		SlideComponent slideComp = ComponentMappers.slide.get(entity);
		slideComp.slideReleased = false;
		slideComp.pointer = pointer;

		StateComponent stateComp = ComponentMappers.state.get(entity);
		stateComp.set(PlayerState.SLIDE.value());

		CollisionComponent collComp = ComponentMappers.collision.get(entity);
		collComp.rect.height /= 1.5f;
	}

	static void getUp(Entity entity) {
		StateComponent stateComp = ComponentMappers.state.get(entity);
		stateComp.set(stateComp.prevState);
		CollisionComponent collComp = ComponentMappers.collision.get(entity);
		if (collComp != null)
			collComp.rect.height *= 1.5f;
	}
}
