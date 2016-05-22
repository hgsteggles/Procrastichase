package hgs.tombstone.components;

import com.badlogic.ashley.core.Component;
import hgs.tombstone.elements.GameMusic;
import hgs.tombstone.elements.Enums.*;

/**
 * Created by harry on 07/04/16.
 */
public class MusicComponent implements Component {
	public GameMusic music;
	public MusicControlType control = MusicControlType.NORMAL;
	public MusicType type = MusicType.RUNNING;
}
