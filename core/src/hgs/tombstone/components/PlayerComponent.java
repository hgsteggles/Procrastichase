package hgs.tombstone.components;

import com.badlogic.ashley.core.Component;
import hgs.tombstone.elements.Enums;

/**
 * Created by harry on 02/04/16.
 */

public class PlayerComponent implements Component {
	public Enums.PlayerType character = Enums.PlayerType.FERNANDO;
	public float endX = 0;
}
