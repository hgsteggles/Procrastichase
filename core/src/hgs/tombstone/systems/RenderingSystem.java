package hgs.tombstone.systems;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import hgs.tombstone.TombstoneGame;
import hgs.tombstone.assets.Assets;
import hgs.tombstone.components.*;
import hgs.tombstone.screens.BasicScreen;
import hgs.tombstone.elements.SortedEntityList;

public class RenderingSystem extends EntitySystem {
	public static final float PIXELS_TO_WORLD = BasicScreen.WORLD_WIDTH / TombstoneGame.WIDTH;
	public static final float WORLD_TO_PIXELS = 1.0f / PIXELS_TO_WORLD;

	private Camera worldCamera, pixelCamera;
	private Camera staticWorldCamera;
	private SpriteBatch batch;

	private SortedEntityList screenRenderQueue;

	public RenderingSystem(SpriteBatch batch, Camera worldCamera, Camera staticWorldCamera, Camera pixelCamera) {
		super();
		
		screenRenderQueue = new SortedEntityList(Family.all(TransformComponent.class)
				.one(TextureComponent.class, BitmapFontComponent.class, NinepatchComponent.class)
				.get(), new DepthComparator());

		this.batch = batch;

		this.worldCamera = worldCamera;
		this.staticWorldCamera = staticWorldCamera;
		this.pixelCamera = pixelCamera;
	}

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);

		screenRenderQueue.addedToEngine(engine);
	}

	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);

		screenRenderQueue.removedFromEngine(engine);
	}

	@Override
	public void update(float deltaTime) {

	}

	public void render(float interp) {
		// Update cameras.
		worldCamera.update();
		pixelCamera.update();
		staticWorldCamera.update();

		// Render to screen.
		batch.setProjectionMatrix(worldCamera.combined);
		batch.begin();
		Array<Entity> screenEntities = screenRenderQueue.getSortedEntities();
		for (Entity entity : screenEntities) {
			render(entity, batch, interp);
		}
		batch.end();
	}

	private void render(Entity entity, SpriteBatch spriteBatch, float interp) {
		TransformComponent t = ComponentMappers.transform.get(entity);

		float renderX = interp * t.body.getPosition().x + (1.0f - interp) * t.body.getPrevPosition().x;
		float renderY = interp * t.body.getPosition().y + (1.0f - interp) * t.body.getPrevPosition().y;
		float renderScaleX = interp * t.body.getScale().x + (1.0f - interp) * t.body.getPrevScale().x;
		float renderScaleY = interp * t.body.getScale().y + (1.0f - interp) * t.body.getPrevScale().y;
		float renderRotation = interp * t.body.getRotation() + (1.0f - interp) * t.body.getPrevRotation();

		if (ComponentMappers.shader.has(entity)) {
			ShaderComponent shaderComp = ComponentMappers.shader.get(entity);

			spriteBatch.setShader(shaderComp.shader);

			if (ComponentMappers.shadertime.has(entity)) {
				ShaderTimeComponent shaderTimeComp = ComponentMappers.shadertime.get(entity);
				shaderComp.shader.setUniformf("time", shaderTimeComp.time);
			}
		}

		if (ComponentMappers.texture.has(entity)) {
			if (ComponentMappers.hud.has(entity)) {
				spriteBatch.setProjectionMatrix(staticWorldCamera.combined);
			}

			TextureComponent tex = ComponentMappers.texture.get(entity);

			if (tex.region == null || tex.region.getTexture() == null) {
				return;
			}

			float width = tex.size.x;
			float height = tex.size.y;
			float originX = 0.0f;
			float originY = 0.0f;

			if (tex.centre) {
				originX = width * 0.5f;
				originY = height * 0.5f;
			}

			spriteBatch.setColor(tex.color);

			spriteBatch.draw(tex.region, renderX - originX,
					renderY - originY, originX, originY,
					width, height, renderScaleX, renderScaleY,
					renderRotation);

			spriteBatch.setColor(Color.WHITE);

			if (ComponentMappers.hud.has(entity)) {
				spriteBatch.setProjectionMatrix(worldCamera.combined);
			}

		} else if (ComponentMappers.bitmapfont.has(entity)) {
			spriteBatch.setProjectionMatrix(pixelCamera.combined);

			BitmapFontComponent bitmap = ComponentMappers.bitmapfont.get(entity);
			BitmapFont font = Assets.fonts.get(bitmap.font);

			font.setColor(bitmap.color);
			font.getData().setScale(bitmap.scale);
			
			Vector2 pospixel = new Vector2(renderX * WORLD_TO_PIXELS, renderY * WORLD_TO_PIXELS);
			GlyphLayout layout = new GlyphLayout(font, bitmap.string);
			if (!bitmap.centering) {
				font.draw(spriteBatch, bitmap.string, pospixel.x, pospixel.y + layout.height);
			} else {
				font.draw(spriteBatch, bitmap.string, pospixel.x - layout.width / 2.0f, pospixel.y + layout.height / 2.0f);
			}

			spriteBatch.setProjectionMatrix(worldCamera.combined);
		} else if (ComponentMappers.ninepatch.has(entity)) {
			spriteBatch.setProjectionMatrix(pixelCamera.combined);

			NinepatchComponent nine = ComponentMappers.ninepatch.get(entity);

			if (nine.patch == null) {
				return;
			}

			Vector2 pospixel = new Vector2(renderX * WORLD_TO_PIXELS, renderY * WORLD_TO_PIXELS);

			float width = nine.size.x * WORLD_TO_PIXELS;
			float height = nine.size.y * WORLD_TO_PIXELS;
			float originX = 0.0f;
			float originY = 0.0f;

			if (nine.centre) {
				originX = width * 0.5f;
				originY = height * 0.5f;
			}

			spriteBatch.setColor(nine.color);

			nine.patch.draw(spriteBatch, pospixel.x - originX, pospixel.y - originY, width, height);

			spriteBatch.setColor(Color.WHITE);
			spriteBatch.setProjectionMatrix(worldCamera.combined);
		}

		if (ComponentMappers.shader.has(entity)) {
			spriteBatch.setShader(null);
		}
	}

	private static class DepthComparator implements Comparator<Entity> {
		@Override
		public int compare(Entity e1, Entity e2) {
			return (int) Math.signum(ComponentMappers.transform.get(e1).body.getPosition().z - ComponentMappers.transform.get(e2).body.getPosition().z);
		}

		@Override
		public Comparator<Entity> reversed() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Comparator<Entity> thenComparing(Comparator<? super Entity> other) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <U> Comparator<Entity> thenComparing(
				Function<? super Entity, ? extends U> keyExtractor,
				Comparator<? super U> keyComparator) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <U extends Comparable<? super U>> Comparator<Entity> thenComparing(
				Function<? super Entity, ? extends U> keyExtractor) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Comparator<Entity> thenComparingInt(
				ToIntFunction<? super Entity> keyExtractor) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Comparator<Entity> thenComparingLong(
				ToLongFunction<? super Entity> keyExtractor) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Comparator<Entity> thenComparingDouble(
				ToDoubleFunction<? super Entity> keyExtractor) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
