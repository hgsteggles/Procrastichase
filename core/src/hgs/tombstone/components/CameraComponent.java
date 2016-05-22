package hgs.tombstone.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by harry on 02/04/16.
 */
public class CameraComponent implements Component {
	public OrthographicCamera camera;
	public float endX = 0;
}
