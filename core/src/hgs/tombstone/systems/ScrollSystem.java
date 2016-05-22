package hgs.tombstone.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import hgs.tombstone.components.ComponentMappers;
import hgs.tombstone.components.ScrollComponent;
import hgs.tombstone.components.TextureComponent;

public class ScrollSystem extends IteratingSystem {
	public ScrollSystem() {
		super(Family.all(ScrollComponent.class, TextureComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		ScrollComponent sc = ComponentMappers.scroll.get(entity);
		TextureComponent tc = ComponentMappers.texture.get(entity);

		float dx = deltaTime * sc.speed.x * RenderingSystem.WORLD_TO_PIXELS;
		float dy = deltaTime * sc.speed.y * RenderingSystem.WORLD_TO_PIXELS;

		if (sc.rotation != 0) {
			float dx_rot = dx * MathUtils.cosDeg(sc.rotation) + dy * MathUtils.sinDeg(sc.rotation);
			float dy_rot = dy * MathUtils.cosDeg(sc.rotation) - dx * MathUtils.sinDeg(sc.rotation);
			dx = dx_rot;
			dy = dy_rot;
		}

		//x has to be negative to get scroll correct
		tc.region.scroll(-dx / tc.region.getTexture().getWidth(), dy / tc.region.getTexture().getHeight());
	}
}
