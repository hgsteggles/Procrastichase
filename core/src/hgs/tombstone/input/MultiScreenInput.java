package hgs.tombstone.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by harry on 09/04/16.
 */
public class MultiScreenInput implements InputProcessor {
	public ObjectSet<Integer> keyDownSet = new ObjectSet<Integer>(); //True if pointer is down
	public ObjectSet<Integer> keyDownLastSet = new ObjectSet<Integer>(); //True if point was moved down last frame
	public ObjectSet<Integer> keyUpLastSet = new ObjectSet<Integer>(); //True if pointer was moved up last frame
	private IntMap<InputData> inputDataMap = new IntMap<InputData>();
	private int npointers = 2;
	protected Viewport viewport;

	private boolean backPressedLast = false;

	public MultiScreenInput() {
		for (int i = 0; i < npointers; ++i)
			inputDataMap.put(i, new InputData());
	}

	public int getNumPointers() {
		return npointers;
	}

	public void setViewport(Viewport viewport) {
		this.viewport = viewport;
	}

	public void backPressed() {
		backPressedLast = true;
	}

	public boolean isBackPressedLast() {
		return backPressedLast;
	}

	public void reset() {
		for (int i = 0; i < npointers; ++i)
			inputDataMap.get(i).reset();

		keyDownSet.clear();
		keyDownLastSet.clear();
		keyUpLastSet.clear();

		backPressedLast = false;
	}

	public InputData get(int pointer) {
		return inputDataMap.get(pointer);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {
			backPressed();
			return true;
		}
		else {
			keyDownLastSet.add(keycode);
			keyDownSet.add(keycode);
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		keyDownSet.remove(keycode);
		keyUpLastSet.add(keycode);

		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (pointer < npointers)
			inputDataMap.get(pointer).pointerDown(unprojectPointer(screenX, screenY));
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (pointer < npointers)
			inputDataMap.get(pointer).pointerUp(unprojectPointer(screenX, screenY));
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (pointer < npointers)
			inputDataMap.get(pointer).pointerMoved(unprojectPointer(screenX, screenY), true);
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		inputDataMap.get(0).pointerMoved(unprojectPointer(screenX, screenY), false);
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	//Converts pixel coordinates to world coordinates
	protected Vector2 unprojectPointer(Vector2 screen) {
		Vector3 pointer = new Vector3(screen.x, screen.y, 0);
		viewport.unproject(pointer);
		return new Vector2(pointer.x, pointer.y);
	}

	//Converts pixel coordinates to world coordinates
	private Vector2 unprojectPointer(int screenX, int screenY) {
		Vector3 pointer = new Vector3(screenX, screenY, 0);
		viewport.unproject(pointer);
		return new Vector2(pointer.x, pointer.y);
	}
}