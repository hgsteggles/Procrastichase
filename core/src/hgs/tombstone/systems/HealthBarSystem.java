package hgs.tombstone.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Enums.PlayerState;
import hgs.tombstone.screens.BasicScreen;

/**
 * Created by harry on 02/04/16.
 */
public class HealthBarSystem extends IteratingSystem {
	private ImmutableArray<Entity> bosses;

	public HealthBarSystem() {
		super(Family.all(HealthBarComponent.class, TransformComponent.class, TextureComponent.class).get());
	}

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		bosses = engine.getEntitiesFor(Family.all(BossComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		HealthBarComponent healthComp = ComponentMappers.healthbar.get(entity);
		TextureComponent texComp = ComponentMappers.texture.get(entity);
		TransformComponent transComp = ComponentMappers.transform.get(entity);

		for (Entity boss : bosses) {
			BossComponent bossComp = ComponentMappers.boss.get(boss);
			float scale = bossComp.health / (float)healthComp.maxHealth;
			scale = MathUtils.clamp(scale, 0.0f, 1.0f);
			float oldTexX = texComp.size.x;
			float newTexX = scale * healthComp.maxTexSizeX;

			float ds = 1.0f * deltaTime;

			if (newTexX > oldTexX)
				texComp.size.x = Math.min(newTexX, texComp.size.x + ds);
			else if (newTexX < oldTexX)
				texComp.size.x = Math.max(newTexX, texComp.size.x - ds);

			transComp.body.updatePosX(healthComp.x + healthComp.maxTexSizeX - texComp.size.x);
		}
	}
}
