package hgs.tombstone.components;

import com.badlogic.ashley.core.Component;
import hgs.tombstone.elements.GameParameters;

/**
 * Created by harry on 02/04/16.
 */
public class JumpComponent implements Component {
	public float maxJumpTime = GameParameters.baseMaxJumpTime;
	public float minJumpTime = GameParameters.baseMinJumpTime;
	public boolean falling = false;
	public boolean jumpReleased = true;
	public int pointer = 0;
}
