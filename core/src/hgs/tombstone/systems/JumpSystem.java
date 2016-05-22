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
public class JumpSystem extends IteratingSystem {
	public JumpSystem() {
		super(Family.all(JumpComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		JumpComponent jumpComp = ComponentMappers.jump.get(entity);
		StateComponent stateComp = ComponentMappers.state.get(entity);

		if (stateComp.get() == PlayerState.JUMP.value()) {
			if (stateComp.time > jumpComp.maxJumpTime
					|| (!jumpComp.jumpHeld && stateComp.time > jumpComp.minJumpTime)) {
				fall(entity);
			}
		}
	}

	static void jump(Entity entity, int pointer) {
		JumpComponent jumpComp = ComponentMappers.jump.get(entity);
		jumpComp.jumpReleased = false;
		jumpComp.jumpHeld = true;
		jumpComp.pointer = pointer;

		TransformComponent transComp = ComponentMappers.transform.get(entity);
		StateComponent stateComp = ComponentMappers.state.get(entity);

		transComp.body.updatePosY(BasicScreen.WORLD_HEIGHT / 2.0f + 0.5f);
		stateComp.set(PlayerState.JUMP.value());

		CollisionComponent collComp = ComponentMappers.collision.get(entity);
		collComp.rect.setY(collComp.rect.getY() + 1.0f);
	}

	static void fall(Entity entity) {
		TransformComponent transComp = ComponentMappers.transform.get(entity);
		StateComponent stateComp = ComponentMappers.state.get(entity);

		transComp.body.updatePosY(BasicScreen.WORLD_HEIGHT / 2f - 0.5f);
		stateComp.set(stateComp.prevState);

		CollisionComponent collComp = ComponentMappers.collision.get(entity);
		if (collComp != null)
			collComp.rect.setY(collComp.rect.getY() - 1.0f);
	}
}
