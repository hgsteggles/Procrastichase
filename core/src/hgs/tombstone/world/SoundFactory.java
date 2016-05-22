package hgs.tombstone.world;

import com.badlogic.ashley.core.Entity;
import hgs.tombstone.assets.Assets;
import hgs.tombstone.components.SoundComponent;

/**
 * Created by harry on 13/04/16.
 */
public class SoundFactory {
	public static Entity createGameOverSound() {
		Entity entity = new Entity();

		SoundComponent soundComp = new SoundComponent();
		soundComp.sound = Assets.sounds.get("gameover");
		entity.add(soundComp);

		return entity;
	}

	public static Entity createPageTurnSound() {
		Entity entity = new Entity();

		SoundComponent soundComp = new SoundComponent();
		soundComp.sound = Assets.sounds.get("page-turn");
		soundComp.volume = 0.5f;
		entity.add(soundComp);

		return entity;
	}
}
