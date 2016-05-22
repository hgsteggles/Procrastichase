package hgs.tombstone.components;

import com.badlogic.ashley.core.Component;
import hgs.tombstone.elements.Enums;

/**
 * Created by harry on 05/04/16.
 */
public class MiniBossComponent implements Component {
	public Enums.MiniBossType who = Enums.MiniBossType.IVA;
	public float maxSpeed = 0;
}
