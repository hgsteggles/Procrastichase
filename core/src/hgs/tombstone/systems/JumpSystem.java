package hgs.tombstone.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Interpolation;
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

		if (stateComp.get() == PlayerState.JUMP.value() && !jumpComp.falling) {
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

		StateComponent stateComp = ComponentMappers.state.get(entity);
		stateComp.set(PlayerState.JUMP.value());

		final float T = 0.75f * jumpComp.minJumpTime;
		final float y0 = 0.5f * BasicScreen.WORLD_HEIGHT - 0.5f;
		final float H = 0.5f * BasicScreen.WORLD_HEIGHT + 0.5f;
		final float u = 2.0f * (H - y0) / T;
		final float acc = - u * u / (2.0f * (H - y0));

		TweenComponent tweenComp = ComponentMappers.tween.get(entity);
		if (tweenComp == null) {
			tweenComp = new TweenComponent();
			entity.add(tweenComp);
		}
		TweenSpec tweenSpec = new TweenSpec();
		tweenSpec.cycle = TweenSpec.Cycle.ONCE;
		tweenSpec.interp = Interpolation.linear;
		tweenSpec.start = 0;
		tweenSpec.end = T;
		tweenSpec.period = T;
		tweenSpec.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				float posY = y0 + u * a + 0.5f * acc * a * a;
				posY = Math.min(posY, H);

				TransformComponent transComp = ComponentMappers.transform.get(e);
				transComp.body.updatePosY(posY);

				CollisionComponent collComp = ComponentMappers.collision.get(e);
				if (collComp != null)
					collComp.rect.setY(posY - 0.5f * collComp.rect.height);
			}
		};
		tweenComp.tweenSpecs.add(tweenSpec);
	}

	static void fall(Entity entity) {
		JumpComponent jumpComp = ComponentMappers.jump.get(entity);
		jumpComp.falling = true;

		final float T = 0.3f * jumpComp.minJumpTime;
		final float y0 = 0.5f * BasicScreen.WORLD_HEIGHT - 0.5f;
		final float H = 0.5f * BasicScreen.WORLD_HEIGHT + 0.5f;
		final float u = 2.0f * (H - y0) / T;
		final float acc = - u * u / (2.0f * (H - y0));

		TweenComponent tweenComp = ComponentMappers.tween.get(entity);
		if (tweenComp == null) {
			tweenComp = new TweenComponent();
			entity.add(tweenComp);
		}
		TweenSpec tweenSpec = new TweenSpec();
		tweenSpec.cycle = TweenSpec.Cycle.ONCE;
		tweenSpec.interp = Interpolation.linear;
		tweenSpec.start = T;
		tweenSpec.end = 2.0f * T;
		tweenSpec.period = T;
		tweenSpec.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				float posY = y0 + u * a + 0.5f * acc * a * a;
				posY = Math.max(posY, y0);

				TransformComponent transComp = ComponentMappers.transform.get(e);
				transComp.body.updatePosY(posY);

				CollisionComponent collComp = ComponentMappers.collision.get(e);
				if (collComp != null)
					collComp.rect.setY(posY - 0.5f * collComp.rect.height);
			}

			@Override
			public void endTween(Entity e) {
				StateComponent stateComp = ComponentMappers.state.get(e);
				stateComp.set(stateComp.prevState);

				if (ComponentMappers.jump.has(e))
					ComponentMappers.jump.get(e).falling = false;
			}
		};
		tweenComp.tweenSpecs.add(tweenSpec);
	}
}
