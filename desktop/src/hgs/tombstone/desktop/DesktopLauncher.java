package hgs.tombstone.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import hgs.tombstone.TombstoneGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		TexturePacker.process("../../images", "atlas", "art");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Tombstone";
		config.width = TombstoneGame.WIDTH;
		config.height = TombstoneGame.HEIGHT;

		new LwjglApplication(new TombstoneGame(new DesktopActionResolver()), config);
	}
}
