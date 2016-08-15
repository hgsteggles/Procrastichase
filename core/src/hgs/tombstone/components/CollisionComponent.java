package hgs.tombstone.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import hgs.tombstone.elements.Enums;

/**
 * Created by harry on 03/04/16.
 */
public class CollisionComponent implements Component {
	public Enums.CollisionType type = Enums.CollisionType.PLAYER;
	public final Rectangle rect = new Rectangle();
	public final Vector2 offset = new Vector2();
}
