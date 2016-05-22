package hgs.tombstone.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by harry on 04/04/16.
 */
public class GameBody {
	private final Vector3 position = new Vector3();
	private final Vector2 scale = new Vector2(1.0f, 1.0f);
	private float rotation = 0.0f;

	private final Vector3 prevPosition = new Vector3();
	private final Vector2 prevScale = new Vector2(1.0f, 1.0f);
	private float prevRotation = 0.0f;

	public Vector3 getPosition() {
		return position;
	}

	public Vector3 getPrevPosition() {
		return prevPosition;
	}

	public Vector2 getScale() {
		return scale;
	}

	public Vector2 getPrevScale() {
		return prevScale;
	}

	public float getRotation() {
		return rotation;
	}

	public float getPrevRotation() {
		return prevRotation;
	}

	public void initPosition(float x, float y, float z) {
		prevPosition.set(x, y, z);
		position.set(x, y, z);
	}

	public void initPosition(Vector3 pos) {
		prevPosition.set(pos);
		position.set(pos);
	}

	public void initPosX(float x) {
		prevPosition.x = x;
		position.x = x;
	}

	public void initPosY(float y) {
		prevPosition.y = y;
		position.y = y;
	}

	public void initPosZ(float z) {
		prevPosition.z = z;
		position.z = z;
	}

	public void initScale(float sX, float sY) {
		prevScale.set(sX, sY);
		scale.set(sX, sY);
	}

	public void initRotation(float rot) {
		prevRotation = rot;
		rotation = rot;
	}

	public void updatePosition(float x, float y, float z) {
		prevPosition.set(position);
		position.set(x, y, z);
	}

	public void updatePosX(float x) {
		prevPosition.x = position.x;
		position.x = x;
	}

	public void updatePosY(float y) {
		prevPosition.y = position.y;
		position.y = y;
	}

	public void updatePosZ(float z) {
		prevPosition.z = position.z;
		position.z = z;
	}

	public void updateScale(float sX, float sY) {
		prevScale.set(scale);
		scale.set(sX, sY);
	}

	public void updateRotation(float rot) {
		prevRotation = rotation;
		rotation = rot;
	}
}
