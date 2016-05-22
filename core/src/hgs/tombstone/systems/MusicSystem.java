package hgs.tombstone.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Game;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Enums;
import hgs.tombstone.elements.GameParameters;
import hgs.tombstone.world.ComponentFactory;
import hgs.tombstone.world.World;

/**
 * Created by harry on 03/04/16.
 */
public class MusicSystem extends IteratingSystem implements EntityListener {
	public MusicSystem() {
		super(Family.all(MusicComponent.class).get());
	}

	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);

		stopMusic();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		MusicComponent musicComp = ComponentMappers.music.get(entity);

		if (musicComp.control == Enums.MusicControlType.FADEIN) {
			float vol = musicComp.music.getVolume() + 1.0f * deltaTime;
			if (vol > 1.0f) {
				vol = 1.0f;
				musicComp.control = Enums.MusicControlType.NORMAL;
			}
			musicComp.music.setVolume(vol);
		}
		else if (musicComp.control == Enums.MusicControlType.FADEOUT) {
			float vol = musicComp.music.getVolume() - 1.0f * deltaTime;
			if (vol < 0) {
				musicComp.music.stop();
				getEngine().removeEntity(entity);
			}
			else
				musicComp.music.setVolume(vol);
		}
	}

	public void stopMusic() {
		for (Entity entity: getEntities()) {
			MusicComponent musicComp = ComponentMappers.music.get(entity);

			musicComp.music.stop();
		}
	}

	@Override
	public void entityAdded(Entity entity) {

	}

	@Override
	public void entityRemoved(Entity entity) {
		MusicComponent musicComp = ComponentMappers.music.get(entity);
		musicComp.music.stop();
	}
}
