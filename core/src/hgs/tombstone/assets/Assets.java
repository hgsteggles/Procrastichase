package hgs.tombstone.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ObjectMap;
import hgs.tombstone.components.MusicComponent;

import java.nio.file.Paths;

/**
 * Created by harry on 01/04/16.
 */
public class Assets {
	public static ObjectMap<String, BitmapFont> fonts = new ObjectMap<String, BitmapFont>();
	public static ObjectMap<String, Sound> sounds = new ObjectMap<String, Sound>();
	public static ObjectMap<String, Music> music = new ObjectMap<String, Music>();

	public static void load(AssetManager manager) {
		manager.load("atlas/art.atlas", TextureAtlas.class);
		manager.load("fonts/retro3.fnt", BitmapFont.class);
		manager.load("music/gameover.wav", Sound.class);
		manager.load("music/clock.mp3", Sound.class);
		manager.load("music/page-turn.wav", Sound.class);

		for (int i = 1; i <= 3; ++i)
			loadMusic(manager, i);
	}

	public static void done(AssetManager manager) {
		TextureAtlas atlas = manager.get("atlas/art.atlas", TextureAtlas.class);

		fonts.put("retro", manager.get("fonts/retro3.fnt", BitmapFont.class));

		sounds.put("gameover", manager.get("music/gameover.wav", Sound.class));
		sounds.put("clock", manager.get("music/clock.mp3", Sound.class));
		sounds.put("page-turn", manager.get("music/page-turn.wav", Sound.class));

		for (int i = 1; i <= 3; ++i) {
			getMusic(manager, i);
		}
	}

	private static void loadMusic(AssetManager manager, int level) {
		String ext = ".mp3";
		String lstr = Integer.toString(level);
		manager.load("music/running-" + lstr + ext, Music.class);
		manager.load("music/boss-" + lstr + ext, Music.class);
	}

	private static void getMusic(AssetManager manager, int level) {
		String ext = ".mp3";
		String lstr = Integer.toString(level);
		music.put("running-" + lstr, manager.get("music/running-" + lstr + ext, Music.class));
		music.put("boss-" + lstr, manager.get("music/boss-" + lstr + ext, Music.class));
	}
}
