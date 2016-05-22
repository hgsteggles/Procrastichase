package hgs.tombstone.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.IntMap;
import hgs.tombstone.elements.Enums;

public class ControlButtonComponent implements Component {
	public Shape2D shape;
	public Enums.ControlType controlType = Enums.ControlType.SLIDE;
	public Color offColor = new Color();
	public Color onColor = new Color();
	public int pointer = -1;
}
