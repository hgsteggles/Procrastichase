package hgs.tombstone.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import hgs.tombstone.elements.Enums;
import hgs.tombstone.systems.CollisionSystem;

/**
 * Created by harry on 07/04/16.
 */
public class EmitterComponent implements Component {
	public final Vector2 velocity = new Vector2();
	public final Vector2 acceleration = new Vector2();
	public float velScale = 1.0f;
	public float rotationalVelocity = 0;
	public float angularRange = 0;

	public final Rectangle sourceRect = new Rectangle();
	public float sourceRotation = 0f;

	public float lifetime = 1.0f;
	public float lifetimeRange = 0.01f;
	public float rate = 1.0f;
	public float intermittency = 0.0f;

	public TextureRegion texregion;
	public final Color texcolor = new Color(Color.WHITE);
	public final Vector2 texsize = new Vector2(1.0f, 1.0f);


	public Enums.CollisionType collisionType = Enums.CollisionType.NONE;
	public final Rectangle collisionRect = new Rectangle();

	public TweenInterface tweenInterface;

	public float time = 0.0f;
	public float nextDrop = 0.0f;
}
