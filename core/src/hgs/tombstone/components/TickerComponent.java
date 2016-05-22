package hgs.tombstone.components;

import com.badlogic.ashley.core.Component;

public class TickerComponent implements Component {
	public boolean tickerActive = false;
	public boolean finishActive = false;

	public float duration = 0f;
	public float interval = 1f;

	public float tickerTime = 0.0f;
	public float totalTime = 0.0f;

	public EventInterface ticker;
	public EventInterface finish;
}
