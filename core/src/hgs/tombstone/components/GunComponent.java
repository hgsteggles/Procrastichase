package hgs.tombstone.components;

import com.badlogic.ashley.core.Component;
import hgs.tombstone.elements.GameParameters;
import hgs.tombstone.elements.Enums.*;

/**
 * Created by harry on 02/04/16.
 */
public class GunComponent implements Component {
	public float heat = 0;
	public float fireRate = 1.0f;
	public float speed = GameParameters.baseProjectileSpeed;
	public BulletDirection direction = BulletDirection.STRAIGHT;
	public boolean shoot = false;

	public GunInterface shootInterface;
}
