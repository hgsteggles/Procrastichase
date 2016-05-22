package hgs.tombstone.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import hgs.tombstone.components.AnimationComponent;
import hgs.tombstone.components.ComponentMappers;
import hgs.tombstone.components.StateComponent;
import hgs.tombstone.components.TextureComponent;

public class AnimationSystem extends IteratingSystem {
	public AnimationSystem() {
		super(Family.all(TextureComponent.class, AnimationComponent.class, StateComponent.class).get());
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		TextureComponent tex = ComponentMappers.texture.get(entity);
		AnimationComponent anim = ComponentMappers.animation.get(entity);
		StateComponent state = ComponentMappers.state.get(entity);

		Animation animation = anim.animations.get(state.get());

		if (animation != null) {
			tex.region = animation.getKeyFrame(state.time);
		}

		state.time += state.timescale * deltaTime;
	}
}
