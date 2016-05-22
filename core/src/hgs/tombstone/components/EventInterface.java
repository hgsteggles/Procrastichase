package hgs.tombstone.components;

import com.badlogic.ashley.core.Entity;

public interface EventInterface {
	void dispatchEvent(Entity e);
}
