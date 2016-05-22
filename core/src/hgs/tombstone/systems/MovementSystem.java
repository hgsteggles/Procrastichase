package hgs.tombstone.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Enums.*;
import hgs.tombstone.screens.BasicScreen;

public class MovementSystem extends IteratingSystem {
	public MovementSystem() {
		super(Family.all(MovementComponent.class, TransformComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		TransformComponent tc = ComponentMappers.transform.get(entity);
		MovementComponent mc = ComponentMappers.movement.get(entity);

		float dx = (mc.linearVelocity.x + deltaTime * mc.linearAcceleration.x / 2.0f) * deltaTime;
		float dy = (mc.linearVelocity.y + deltaTime * mc.linearAcceleration.y / 2.0f) * deltaTime;
		float dz = (mc.linearVelocity.z + deltaTime * mc.linearAcceleration.z / 2.0f) * deltaTime;

		float x = tc.body.getPosition().x;
		float y = tc.body.getPosition().y;
		float z = tc.body.getPosition().z;

		/*
		if (ComponentMappers.player.has(entity)) {
			PlayerComponent playerComp = ComponentMappers.player.get(entity);
			if (x + dx > playerComp.endX) {
				dx = playerComp.endX - x;
				mc.linearVelocity.x = 0;
				idle(entity);
			}
		}

		if (ComponentMappers.camera.has(entity)) {
			CameraComponent camComp = ComponentMappers.camera.get(entity);
			if (x + dx > camComp.endX) {
				dx = camComp.endX - x;
				mc.linearVelocity.x = 0;
			}
		}
		*/

		tc.body.updatePosition(x + dx, y + dy, z + dz);
		tc.body.updateRotation(tc.body.getRotation() + mc.rotationalVelocity * deltaTime);

		mc.linearVelocity.x += mc.linearAcceleration.x * deltaTime;
		mc.linearVelocity.y += mc.linearAcceleration.y * deltaTime;
		mc.linearVelocity.z += mc.linearAcceleration.z * deltaTime;

		if (ComponentMappers.collision.has(entity)) {
			CollisionComponent collComp = ComponentMappers.collision.get(entity);

			float rectX = collComp.rect.getX();
			float rectY = collComp.rect.getY();

			collComp.rect.setPosition(rectX + dx, rectY + dy);
		}
	}

	public static void idle(Entity entity) {
		StateComponent stateComp = ComponentMappers.state.get(entity);
		if (stateComp.get() != PlayerState.RUN.value())
			stateComp.prevState = PlayerState.IDLE.value();
		else
			stateComp.set(PlayerState.IDLE.value());

		CollisionComponent collComp = ComponentMappers.collision.get(entity);
		TextureComponent texComp = ComponentMappers.texture.get(entity);
		collComp.rect.setHeight(0.9f * texComp.size.y);
	}

	public static void walkoff(Entity entity) {
		StateComponent stateComp = ComponentMappers.state.get(entity);
		stateComp.set(PlayerState.RUN.value());

		TransformComponent transComp = ComponentMappers.transform.get(entity);
		transComp.body.updatePosY(BasicScreen.WORLD_HEIGHT / 2f - 0.5f);

		ComponentMappers.movement.get(entity).linearVelocity.x = 2.0f;
	}
}
