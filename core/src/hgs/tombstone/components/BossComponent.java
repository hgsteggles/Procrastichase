package hgs.tombstone.components;

import com.badlogic.ashley.core.Component;
import hgs.tombstone.elements.Enums;

/**
 * Created by harry on 05/04/16.
 */
public class BossComponent implements Component {
	public Enums.BossType who = Enums.BossType.MELVIN;
	public float intro_time = 1f;
	public float health = 1f;
}
