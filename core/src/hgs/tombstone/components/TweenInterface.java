package hgs.tombstone.components;

import com.badlogic.ashley.core.Entity;

public abstract class TweenInterface {
	public abstract void applyTween(Entity e, float a);

	public void endTween(Entity e) {
	}
}
