package hgs.tombstone.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import hgs.tombstone.assets.Assets;
import hgs.tombstone.assets.GameArt;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Depths;
import hgs.tombstone.elements.Enums;
import hgs.tombstone.screens.BasicScreen;
import hgs.tombstone.systems.RenderingSystem;

/**
 * Created by harry on 05/04/16.
 */
public class WorldUI {
	public static Entity createControlButton(float x, float y, float w, float h, Enums.ControlType type) {
		Entity entity = new Entity();

		//Color offColor = new Color(111f/255f, 1.0f, 170f/255f, 1.0f);
		//Color onColor = new Color(1.0f, 111f/255f, 196f/255f, 1.0f);
		Color offColor = new Color(113f/255f, 95f/255f, 45f/255f, 1.0f);
		Color onColor = new Color(26f/255f, 193f/255f, 73f/255f, 1.0f);

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.buttonPixelart;
		texComp.size.x = 2.0f * RenderingSystem.PIXELS_TO_WORLD * texComp.region.getRegionWidth();
		texComp.size.y = 2.0f * RenderingSystem.PIXELS_TO_WORLD * texComp.region.getRegionHeight();
		texComp.color.set(offColor);
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		transComp.body.initPosition(x, y, Depths.hudZ);
		entity.add(transComp);

		ControlButtonComponent controlComp = new ControlButtonComponent();
		controlComp.controlType = type;
		controlComp.offColor.set(offColor);
		controlComp.onColor.set(onColor);
		controlComp.shape = new Rectangle().setSize(w, h).setCenter(0.0f, 0.0f);
		entity.add(controlComp);

		entity.add(new HeadsUpDisplayComponent());

		return entity;
	}

	public static Entity createText(float x, float y, String text) {
		Entity entity = new Entity();

		BitmapFontComponent fontComp = new BitmapFontComponent();
		fontComp.font = "retro";
		fontComp.string = text;
		fontComp.color = new Color(Color.BLACK);
		fontComp.centering = true;
		entity.add(fontComp);

		TransformComponent transComp = new TransformComponent();
		transComp.body.initPosition(x, y, Depths.hudZ + 1);
		entity.add(transComp);

		return entity;
	}

	public static Entity createInstructions() {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.instructions;
		texComp.size.x = 2.0f;
		texComp.size.y = 1.0f;
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float x = 1.5f;
		float y = BasicScreen.WORLD_HEIGHT / 2.0f - 2.5f + texComp.size.y / 2.0f;
		float z = Depths.hudZ;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		entity.add(new HeadsUpDisplayComponent());

		return entity;
	}

	public static Entity createTopBar() {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.whitePixel;
		texComp.size.x = BasicScreen.WORLD_WIDTH;
		texComp.size.y = 2.0f;
		texComp.color.set(Color.BLACK);
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float x = BasicScreen.WORLD_WIDTH / 2.0f;
		float y = 5.0f;
		float z = Depths.hudZ - 1;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		entity.add(new HeadsUpDisplayComponent());

		return entity;
	}

	public static Entity createBottomBar() {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.whitePixel;
		texComp.size.x = BasicScreen.WORLD_WIDTH;
		texComp.size.y = 2.0f;
		texComp.color.set(Color.BLACK);
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float x = BasicScreen.WORLD_WIDTH / 2.0f;
		float y = 1.0f;
		float z = Depths.hudZ - 1;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		entity.add(new HeadsUpDisplayComponent());

		return entity;
	}

	public static Entity createPageCounterIcon(float x, float y, boolean centering) {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.page;
		texComp.centre = centering;
		texComp.size.x = 2.0f * texComp.region.getRegionWidth() * RenderingSystem.PIXELS_TO_WORLD;
		texComp.size.y = 2.0f * texComp.region.getRegionHeight() * RenderingSystem.PIXELS_TO_WORLD;
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float z = Depths.hudZ;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		entity.add(new HeadsUpDisplayComponent());

		return entity;
	}

	public static Entity createBlackPageIcon(float x, float y) {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.blackPage;
		texComp.centre = true;
		texComp.size.x = 2.0f * texComp.region.getRegionWidth() * RenderingSystem.PIXELS_TO_WORLD;
		texComp.size.y = 2.0f * texComp.region.getRegionHeight() * RenderingSystem.PIXELS_TO_WORLD;
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float z = Depths.screentextZ;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		entity.add(new HeadsUpDisplayComponent());

		return entity;
	}

	public static Entity createPageCounter() {
		Entity entity = new Entity();

		BitmapFontComponent fontComp = new BitmapFontComponent();
		fontComp.font = "retro";
		fontComp.string = Integer.toString(0);
		fontComp.color.set(Color.WHITE);
		fontComp.centering = false;
		entity.add(fontComp);

		TransformComponent transComp = new TransformComponent();
		float x = 1.0f;
		float y = 4.5f;
		float z = Depths.hudZ;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		entity.add(new TweenComponent());
		entity.add(new PageCountComponent());
		entity.add(new HeadsUpDisplayComponent());

		return entity;
	}

	public static Entity createBossName(Enums.BossType who) {
		Entity entity = new Entity();

		BitmapFontComponent fontComp = new BitmapFontComponent();
		fontComp.font = "retro";
		if (who == Enums.BossType.MELVIN)
			fontComp.string = "Melvin";
		else if (who == Enums.BossType.JULIAN)
			fontComp.string = "Julian";
		else if (who == Enums.BossType.RENE)
			fontComp.string = "Rene";
		else if (who == Enums.BossType.SVEN)
			fontComp.string = "Sven";
		fontComp.color.set(Color.WHITE);
		fontComp.centering = false;
		fontComp.scale = 1.0f;
		BitmapFont font = Assets.fonts.get(fontComp.font);
		font.getData().setScale(fontComp.scale);
		GlyphLayout layout = new GlyphLayout(font, fontComp.string);
		entity.add(fontComp);

		TransformComponent transComp = new TransformComponent();
		float x = BasicScreen.WORLD_WIDTH - 0.5f - layout.width * RenderingSystem.PIXELS_TO_WORLD;
		float y = 4.75f;
		float z = Depths.hudZ;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		entity.add(new TweenComponent());
		entity.add(new HeadsUpDisplayComponent());

		return entity;
	}

	public static Entity createHealthBar() {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.healthBar;
		texComp.centre = false;
		texComp.size.x = 2.0f * texComp.region.getRegionWidth() * RenderingSystem.PIXELS_TO_WORLD;
		texComp.size.y = 2.0f * texComp.region.getRegionHeight() * RenderingSystem.PIXELS_TO_WORLD;
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float x = BasicScreen.WORLD_WIDTH - texComp.size.x - 0.5f;
		float y = 4.35f;
		float z = Depths.hudZ;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		entity.add(new TweenComponent());
		entity.add(new HeadsUpDisplayComponent());

		return entity;
	}

	public static Entity createHealth() {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.health;
		texComp.centre = false;
		texComp.size.x = 2.0f * texComp.region.getRegionWidth() * RenderingSystem.PIXELS_TO_WORLD;
		texComp.size.y = 2.0f * texComp.region.getRegionHeight() * RenderingSystem.PIXELS_TO_WORLD;
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float offX = - 8.0f * RenderingSystem.PIXELS_TO_WORLD;
		float offY = 12.0f * RenderingSystem.PIXELS_TO_WORLD;
		float x = BasicScreen.WORLD_WIDTH - texComp.size.x - 0.5f + offX;
		float y = 4.35f + offY;
		float z = Depths.hudZ;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		HealthBarComponent healthComp = new HealthBarComponent();
		healthComp.maxTexSizeX = texComp.size.x;
		healthComp.maxHealth = 10;
		healthComp.x = x;
		entity.add(healthComp);

		entity.add(new TweenComponent());
		entity.add(new HeadsUpDisplayComponent());

		return entity;
	}

	public static Entity createChapter(int level) {
		Entity entity = new Entity();

		BitmapFontComponent fontComp = new BitmapFontComponent();
		fontComp.font = "retro";
		fontComp.string = "ch. " + Integer.toString(level);
		fontComp.color.set(Color.WHITE);
		fontComp.centering = false;
		entity.add(fontComp);

		TransformComponent transComp = new TransformComponent();
		float x = 0.25f;
		float y = 5.5f;
		float z = Depths.hudZ;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		entity.add(new TweenComponent());
		entity.add(new PageCountComponent());
		entity.add(new HeadsUpDisplayComponent());

		return entity;
	}

	public static Entity createTimer() {
		Entity entity = new Entity();

		BitmapFontComponent fontComp = new BitmapFontComponent();
		fontComp.font = "retro";
		fontComp.string = createTimerString(0);
		fontComp.color.set(Color.WHITE);
		fontComp.centering = true;
		entity.add(fontComp);

		TransformComponent transComp = new TransformComponent();
		float x = BasicScreen.WORLD_WIDTH / 2.0f;
		float y = 5;
		float z = Depths.hudZ;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		TickerComponent tickComp = new TickerComponent();
		tickComp.tickerActive = true;
		tickComp.interval = 1;
		tickComp.ticker = new EventInterface() {
			@Override
			public void dispatchEvent(Entity entity) {
				BitmapFontComponent bfc = ComponentMappers.bitmapfont.get(entity);
				TickerComponent tc = ComponentMappers.ticker.get(entity);
				CountComponent cc = ComponentMappers.count.get(entity);
				cc.count += 1;
				bfc.string = createTimerString((int) (cc.count));
			}
		};
		entity.add(tickComp);

		entity.add(new CountComponent());
		entity.add(new HeadsUpDisplayComponent());

		return entity;
	}

	static public String createTimerString(int t) {
		int nminutes = (int) (t / 60f);
		int nseconds = (int) (t - nminutes * 60);

		String timestring = "";
		if (nminutes < 10) {
			timestring += "0";
		}
		timestring += Integer.toString(nminutes);

		timestring += ":";

		if (nseconds < 10) {
			timestring += "0";
		}
		timestring += Integer.toString(nseconds);

		return timestring;
	}

	public static Entity createFullBlackBG() {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = GameArt.whitePixel;
		texComp.size.x = BasicScreen.WORLD_WIDTH;
		texComp.size.y = BasicScreen.WORLD_HEIGHT;
		texComp.color.set(Color.BLACK);
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float x = BasicScreen.WORLD_WIDTH / 2.0f;
		float y = BasicScreen.WORLD_HEIGHT / 2.0f;
		float z = Depths.underlayZ;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		entity.add(new HeadsUpDisplayComponent());

		return entity;
	}


}
