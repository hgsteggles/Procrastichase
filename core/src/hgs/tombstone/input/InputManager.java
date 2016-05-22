package hgs.tombstone.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

public class InputManager {
	//public static ScreenInput screenInput;
	public static MultiScreenInput screenInput;
	private static InputMultiplexer input;

	private InputManager() {
	}

	public static void create() {
		input = new InputMultiplexer();
		Gdx.input.setInputProcessor(input);

		screenInput = new MultiScreenInput();
		addInputProcessor(screenInput);
	}

	public static void reset() {
		screenInput.reset();
	}

	public static void addInputProcessor(InputProcessor ip) {
		input.addProcessor(ip);
	}

	public static void removeInputProcessor(InputProcessor ip) {
		input.removeProcessor(ip);
	}
}
