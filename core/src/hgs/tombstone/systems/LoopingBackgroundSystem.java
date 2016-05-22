package hgs.tombstone.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Enums.PlayerState;
import hgs.tombstone.screens.BasicScreen;

/**
 * Created by harry on 02/04/16.
 */
public class LoopingBackgroundSystem extends IteratingSystem {
	private ImmutableArray<Entity> cameras;

	public LoopingBackgroundSystem() {
		super(Family.all(LoopingBackgroundComponent.class).get());
	}

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		cameras = engine.getEntitiesFor(Family.all(CameraComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		for (Entity camera : cameras) {
			TransformComponent camTrans = ComponentMappers.transform.get(camera);
			TransformComponent bgTrans = ComponentMappers.transform.get(entity);
			CameraComponent camComp = ComponentMappers.camera.get(camera);
			TextureComponent bgTex = ComponentMappers.texture.get(entity);

			if (bgTrans.body.getPosition().x + 0.5f * bgTex.size.x < camTrans.body.getPosition().x - 0.5f * camComp.camera.viewportWidth) {
				bgTrans.body.initPosX(bgTrans.body.getPosition().x + 2f * bgTex.size.x);
			}
		}
	}
}
