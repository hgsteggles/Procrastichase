package hgs.tombstone.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import hgs.tombstone.TombstoneGame;
import hgs.tombstone.assets.GameArt;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Colors;
import hgs.tombstone.elements.Depths;
import hgs.tombstone.elements.Enums;
import hgs.tombstone.elements.RNG;

/**
 * Created by harry on 04/04/16.
 */
public class EmitterSystem extends IteratingSystem {
	int i = 0;

	public EmitterSystem() {
		super(Family.all(EmitterComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		EmitterComponent emitComp = ComponentMappers.emitter.get(entity);
		TransformComponent transComp = ComponentMappers.transform.get(entity);

		emitComp.velocity.rotate(emitComp.rotationalVelocity * deltaTime);

		if (emitComp.time > emitComp.nextDrop) {
			Entity particle = new Entity();

			particle.add(createParticleMovement(emitComp, ComponentMappers.movement.get(entity)));
			particle.add(createParticleTransform(emitComp, transComp));
			particle.add(createParticleTexture(emitComp));
			particle.add(createParticleTween(emitComp));
			if (emitComp.collisionType != Enums.CollisionType.NONE)
				particle.add(createParticleCollision(emitComp, ComponentMappers.transform.get(particle)));
			particle.add(new BoundsComponent());

			getEngine().addEntity(particle);

			emitComp.time = 0;
			emitComp.nextDrop = 1.0f / (emitComp.rate * (1.0f + emitComp.intermittency * RNG.genRange(-1f, 1f)));
		}

		emitComp.time += deltaTime;
	}

	public static TweenComponent createParticleTween(EmitterComponent emitComp) {
		TweenComponent tweenComp = new TweenComponent();

		TweenSpec tweenSpec = new TweenSpec();
		tweenSpec.cycle = TweenSpec.Cycle.ONCE;
		tweenSpec.period = emitComp.lifetime * (1.0f + emitComp.lifetimeRange * RNG.genRange(1.0f, 1.0f));
		tweenSpec.start = 1.0f;
		tweenSpec.end = 0.0f;
		tweenSpec.interp = Interpolation.linear;
		if (emitComp.tweenInterface != null) {
			tweenSpec.tweenInterface = emitComp.tweenInterface;
		}
		else {
			tweenSpec.tweenInterface = new TweenInterface() {
				@Override
				public void applyTween(Entity e, float a) {}

				@Override
				public void endTween(Entity e) {
					e.add(new RemovalComponent());
				}
			};
		}
		tweenComp.tweenSpecs.add(tweenSpec);

		return tweenComp;
	}

	public static MovementComponent createParticleMovement(EmitterComponent emitComp, MovementComponent sourceMove) {
		MovementComponent moveComp = new MovementComponent();

		moveComp.linearVelocity.x = emitComp.velocity.x;
		moveComp.linearVelocity.y = emitComp.velocity.y;

		if (emitComp.angularRange >= 0) {
			float degrees = emitComp.angularRange * RNG.genRange(-1f, 1f);
			moveComp.linearVelocity.rotate(Vector3.Z, degrees);
		}

		moveComp.linearAcceleration.x = emitComp.acceleration.x;
		moveComp.linearAcceleration.y = emitComp.acceleration.y;

		if (sourceMove != null) {
			moveComp.linearVelocity.x += emitComp.velScale * sourceMove.linearVelocity.x;
			moveComp.linearVelocity.y += emitComp.velScale * sourceMove.linearVelocity.y;
		}

		//emitComp.sourceRotation = (180.0f / (float)Math.PI) * (float)Math.atan2(moveComp.linearVelocity.y, moveComp.linearVelocity.x) - 180.0f;

		return moveComp;
	}

	public static TransformComponent createParticleTransform(EmitterComponent emitComp, TransformComponent sourceTrans) {
		TransformComponent transComp = new TransformComponent();

		float rx = RNG.genFloat();
		float ry = RNG.genFloat();

		float x = sourceTrans.body.getPosition().x + emitComp.sourceRect.getX() + rx * emitComp.sourceRect.getWidth();
		float y = sourceTrans.body.getPosition().y + emitComp.sourceRect.getY() + ry * emitComp.sourceRect.getHeight();

		transComp.body.initPosition(x, y, Depths.particleZ);
		transComp.body.initRotation(emitComp.sourceRotation);

		return transComp;
	}

	public static TextureComponent createParticleTexture(EmitterComponent emitComp) {
		TextureComponent texComp = new TextureComponent();
		texComp.region = emitComp.texregion;
		texComp.size.x = emitComp.texsize.x;
		texComp.size.y = emitComp.texsize.y;
		texComp.color.set(emitComp.texcolor);

		return texComp;
	}

	public static CollisionComponent createParticleCollision(EmitterComponent emitComp, TransformComponent particleTrans) {
		CollisionComponent cc = new CollisionComponent();
		cc.type = emitComp.collisionType;
		cc.rect.set(emitComp.collisionRect);

		float cx = particleTrans.body.getPosition().x + emitComp.collisionRect.x + 0.5f * emitComp.collisionRect.width;
		float cy = particleTrans.body.getPosition().y + emitComp.collisionRect.y + 0.5f * emitComp.collisionRect.height;
		cc.rect.setCenter(cx, cy);

		return cc;
	}
}
