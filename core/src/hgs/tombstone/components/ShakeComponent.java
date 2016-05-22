package hgs.tombstone.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by harry on 05/04/16.
 */
public class ShakeComponent implements Component {
	public float maxRadius = 0.0f;
	public float maxTime = 10f;
	public float currRadius = 0.0f;
	public float currTime = 0.0f;
	public float posX = 0;
	public float posY = 0;
}
