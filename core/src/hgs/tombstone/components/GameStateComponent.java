package hgs.tombstone.components;

import com.badlogic.ashley.core.Component;
import hgs.tombstone.elements.Enums;

/**
 * Created by harry on 03/04/16.
 */
public class GameStateComponent implements Component {
	public Enums.GameState state = Enums.GameState.RUNNING;
}
