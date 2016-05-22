package hgs.tombstone.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import hgs.tombstone.TombstoneGame;
import hgs.tombstone.assets.Assets;
import hgs.tombstone.components.*;
import hgs.tombstone.screens.BasicScreen;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public class DebugSystem extends EntitySystem {
	private Camera worldCamera, pixelcamera;
	private ShapeRenderer renderer;

	private ImmutableArray<Entity> boxes;

	public DebugSystem(Camera worldCamera) {
		super();

		renderer = new ShapeRenderer();

		this.worldCamera = worldCamera;
	}

	@Override
	public void addedToEngine(Engine engine) {
		boxes = engine.getEntitiesFor(Family.all(CollisionComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {

	}

	public void render() {
		// Update cameras.
		worldCamera.update();

		// Render to screen.
		renderer.setProjectionMatrix(worldCamera.combined);
		renderer.begin(ShapeRenderer.ShapeType.Line);
		for (Entity entity : boxes) {
			CollisionComponent collComp = ComponentMappers.collision.get(entity);

			renderer.rect(collComp.rect.x, collComp.rect.y, collComp.rect.width, collComp.rect.height);
		}
		renderer.end();
	}
}
