package hgs.tombstone.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import hgs.tombstone.elements.GameBody;

public class TransformComponent implements Component {
	public final GameBody body = new GameBody();
}

