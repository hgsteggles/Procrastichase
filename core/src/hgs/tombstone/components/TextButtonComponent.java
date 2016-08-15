package hgs.tombstone.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

public class TextButtonComponent implements Component {
	public final Color base = new Color(Color.WHITE);
	public final Color pressed = new Color(Color.RED);
}
