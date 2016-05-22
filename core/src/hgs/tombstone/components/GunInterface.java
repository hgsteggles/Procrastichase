package hgs.tombstone.components;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

/**
 * Created by harry on 05/04/16.
 */
public abstract class GunInterface {
	public abstract void shoot(Entity e, Engine eng);
}
