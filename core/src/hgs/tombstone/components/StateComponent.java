package hgs.tombstone.components;

import com.badlogic.ashley.core.Component;

public class StateComponent implements Component {
	private int state = 0;
	public int prevState = 0;

	public float time = 0.0f;
	public float timescale = 1.0f;

	public int get() {
		return state;
	}

	public void set(int newState) {
		prevState = state;
		state = newState;
		time = 0.0f;
	}
}
