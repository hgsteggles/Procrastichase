package hgs.tombstone.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import hgs.tombstone.assets.GameArt;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Enums.PlayerState;
import hgs.tombstone.input.InputManager;

/**
 * Created by harry on 02/04/16.
 */
public class CharacterSelectSystem extends EntitySystem {
	private ImmutableArray<Entity> players;
	private ImmutableArray<Entity> events;

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		players = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
		events = engine.getEntitiesFor(Family.all(CharacterChangeEvent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		for (Entity event : events) {
			for (Entity player : players) {
				CharacterChangeEvent changeEvent = ComponentMappers.charchange.get(event);

				ComponentMappers.player.get(player).character = changeEvent.player;

				AnimationComponent animComp = ComponentMappers.animation.get(player);
				animComp.animations.clear();
				animComp.animations.put(PlayerState.RUN.value(), new Animation(0.1f, GameArt.playerRunning.get(changeEvent.player), Animation.PlayMode.LOOP));
			}

			getEngine().removeEntity(event);
		}
	}
}
