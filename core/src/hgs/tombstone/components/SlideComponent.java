package hgs.tombstone.components;

import com.badlogic.ashley.core.Component;
import hgs.tombstone.elements.GameParameters;

/**
 * Created by harry on 03/04/16.
 */
public class SlideComponent implements Component {
	public float maxSlideTime = GameParameters.baseMaxSlideTime;
	public float minSlideTime = GameParameters.baseMinSlideTime;
	public boolean slideHeld = false;
	public boolean slideReleased = true;
	public int pointer = 0;
}
