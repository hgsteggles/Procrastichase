package hgs.tombstone.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import hgs.tombstone.assets.GameArt;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Depths;
import hgs.tombstone.screens.BasicScreen;

/**
 * Created by harry on 15/04/16.
 */
public class TransitionFactory {
	public static Entity createBlackFadeOverlay(float fadein) {
		Entity entity = new Entity();

		final TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.whitePixel;
		texComp.size.x = BasicScreen.WORLD_WIDTH;
		texComp.size.y = BasicScreen.WORLD_HEIGHT;
		texComp.color.set(Color.BLACK);
		texComp.color.a = 0.0f;
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float x = BasicScreen.WORLD_WIDTH / 2.0f;
		float y = BasicScreen.WORLD_HEIGHT / 2.0f;
		float z = Depths.overlayZ;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		TweenComponent tweenComp = new TweenComponent();
		TweenSpec tweenSpec = new TweenSpec();
		tweenSpec.period = fadein;
		tweenSpec.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				TextureComponent txc = ComponentMappers.texture.get(e);
				txc.color.a = a;
			}
		};
		tweenComp.tweenSpecs.add(tweenSpec);
		entity.add(tweenComp);

		entity.add(new HeadsUpDisplayComponent());

		return entity;
	}

	public static Entity createGameoverBar(float period, boolean shiftLeft) {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.whitePixel;
		texComp.size.x = BasicScreen.WORLD_WIDTH;
		texComp.size.y = BasicScreen.WORLD_HEIGHT;
		texComp.color.set(Color.BLACK);
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float x = shiftLeft ? 1.5f * BasicScreen.WORLD_WIDTH : -0.5f * BasicScreen.WORLD_WIDTH;
		float y = BasicScreen.WORLD_HEIGHT / 2.0f;
		float z = Depths.hudZ - 1;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		TweenComponent tweenComp = new TweenComponent();

		TweenSpec tweenSpec = new TweenSpec();
		tweenSpec.cycle = TweenSpec.Cycle.ONCE;
		tweenSpec.period = period;
		tweenSpec.start = transComp.body.getPosition().x;
		if (shiftLeft)
			tweenSpec.end = BasicScreen.WORLD_WIDTH / 2.0f + 0.9f;
		else
			tweenSpec.end = - 0.5f * BasicScreen.WORLD_WIDTH + 1.1f;
		tweenSpec.interp = Interpolation.linear;
		tweenSpec.tweenInterface = new TweenInterface() {
			@Override
			public void applyTween(Entity e, float a) {
				TransformComponent tc = ComponentMappers.transform.get(e);
				tc.body.updatePosX(a);
			}
		};
		tweenComp.tweenSpecs.add(tweenSpec);
		entity.add(tweenComp);

		entity.add(new HeadsUpDisplayComponent());

		return entity;
	}
}
