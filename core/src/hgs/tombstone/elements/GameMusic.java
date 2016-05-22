package hgs.tombstone.elements;

import com.badlogic.gdx.audio.Music;

/**
 * Created by harry on 07/04/16.
 */
public class GameMusic {
	private Music music;
	private float volume = 1.0f;
	private boolean isMute = false;
	private float scale = 1.0f;

	public GameMusic(Music music) {
		setMusic(music);
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	public void setVolumeScale(float scale) {
		this.scale = scale;
		setVolume(volume);
	}

	public void setVolume(float v) {
		volume = v;
		if (!isMute) {
			music.setVolume(v * scale);
		}
	}

	public float getVolume() {
		return volume;
	}

	public void play(boolean isLooping) {
		setVolume(volume);
		music.play();
		music.setLooping(isLooping);
	}

	public void stop() {
		music.stop();
	}

	public void mute() {
		music.setVolume(0);
		isMute = true;
	}

	public void unmute() {
		isMute = false;
		setVolume(volume);
	}
}
