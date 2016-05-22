package hgs.tombstone.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import hgs.tombstone.elements.Enums.*;

/**
 * Created by harry on 01/04/16.
 */
public class GameArt {
	public static TextureRegion sheeptext;
	public static TextureRegion sheepstare;
	public static TextureRegion sheepeye;
	public static TextureRegion sheepaura;
	public static TextureRegion sheeplight;
	public static TextureRegion lightline;

	public static TextureRegion tournamentUp;
	public static TextureRegion tournamentDown;
	public static TextureRegion tournament;

	public static TextureRegion mainBackgroundScrollable;
	public static TextureRegion tronBackground;
	public static TextureRegion battleBackground;
	public static TextureRegion book;
	public static TextureRegion blackPage;

	public static Array<TextureAtlas.AtlasRegion> playerRunning;
	public static Array<TextureAtlas.AtlasRegion> playerJumping;
	public static Array<TextureAtlas.AtlasRegion> playerSliding;
	public static Array<TextureAtlas.AtlasRegion> playerIdle;

	public static Array<TextureAtlas.AtlasRegion> cat;

	public static ObjectMap<MiniBossType, Array<TextureAtlas.AtlasRegion>> miniBossPrime;
	public static ObjectMap<MiniBossType, Array<TextureAtlas.AtlasRegion>> miniBossThrow;
	public static ObjectMap<BossType, Array<TextureAtlas.AtlasRegion>> bossPrime;
	public static ObjectMap<BossType, Array<TextureAtlas.AtlasRegion>> bossThrow;
	public static ObjectMap<BossType, Array<TextureAtlas.AtlasRegion>> bossEnter;

	public static ObjectMap<BulletType, Array<TextureAtlas.AtlasRegion>> bulletAnim;

	public static TextureRegion hangStar;
	public static TextureRegion hangEarth;
	public static TextureRegion stackedTable;
	public static TextureRegion ball;
	public static TextureRegion pin;
	public static TextureRegion tableSimon;
	public static TextureRegion table;
	public static TextureRegion page;
	public static TextureRegion instructions;
	public static TextureRegion whitePixel;
	public static TextureRegion bulletBox;
	public static TextureRegion health;
	public static TextureRegion healthBar;
	public static TextureRegion diamond;
	public static TextureRegion flame;
	public static TextureRegion musicNote;
	public static TextureRegion tonye;
	public static TextureRegion sleepZ;
	public static TextureRegion abi;
	public static TextureRegion kathryn;
	public static TextureRegion mo;
	public static TextureRegion tombstone;
	public static Array<TextureAtlas.AtlasRegion> boltAnim;

	public static TextureRegion buttonPixelart;
	public static NinePatchDrawable buttonBorder;
	public static NinePatchDrawable whiteButtonBorder;

	private static Skin skin = new Skin();

	public static void load(AssetManager manager) {
		TextureAtlas atlas = manager.get("atlas/art.atlas", TextureAtlas.class);

		sheeptext = atlas.findRegion("sheepstaretext");
		sheepstare = atlas.findRegion("sheepstare");
		sheepeye = atlas.findRegion("sheepeye");
		sheepaura = atlas.findRegion("sheepaura");
		sheeplight = atlas.findRegion("sheeplight");
		lightline = atlas.findRegion("lightline");

		tournamentUp = atlas.findRegion("tournament-button-off");
		tournamentDown = atlas.findRegion("tournament-button-on");
		tournament = atlas.findRegion("tournament-button");

		mainBackgroundScrollable = atlas.findRegion("background");
		battleBackground = atlas.findRegion("endbattle-bg");
		tronBackground = atlas.findRegion("tron-bg");

		playerRunning = atlas.findRegions("fernando-run");
		playerJumping = atlas.findRegions("fernando-jump");
		playerSliding = atlas.findRegions("fernando-slide");
		playerIdle = atlas.findRegions("fernando-idle");

		miniBossPrime = new ObjectMap<MiniBossType, Array<TextureAtlas.AtlasRegion>>();
		miniBossPrime.put(MiniBossType.IVA, atlas.findRegions("iva-sit-prime"));
		miniBossPrime.put(MiniBossType.ROB, atlas.findRegions("rob-sit-prime"));
		miniBossPrime.put(MiniBossType.GREG, atlas.findRegions("greg-sit-prime"));
		miniBossPrime.put(MiniBossType.MARC, atlas.findRegions("marc-stand-prime"));

		miniBossThrow = new ObjectMap<MiniBossType, Array<TextureAtlas.AtlasRegion>>();
		miniBossThrow.put(MiniBossType.IVA, atlas.findRegions("iva-sit-throw"));
		miniBossThrow.put(MiniBossType.ROB, atlas.findRegions("rob-sit-throw"));
		miniBossThrow.put(MiniBossType.GREG, atlas.findRegions("greg-sit-throw"));
		miniBossThrow.put(MiniBossType.MARC, atlas.findRegions("marc-stand-throw"));

		bossThrow = new ObjectMap<BossType, Array<TextureAtlas.AtlasRegion>>();
		bossThrow.put(BossType.MELVIN, atlas.findRegions("melvin-stand-throw"));
		bossThrow.put(BossType.JULIAN, atlas.findRegions("julian-stand-throw"));
		bossThrow.put(BossType.RENE, atlas.findRegions("rene-stand-throw"));
		bossThrow.put(BossType.SVEN, atlas.findRegions("sven-stand-throw"));
		bossThrow.put(BossType.ENDLESS, atlas.findRegions("stuart-stand-throw"));

		bossPrime = new ObjectMap<BossType, Array<TextureAtlas.AtlasRegion>>();
		bossPrime.put(BossType.MELVIN, atlas.findRegions("melvin-stand-prime"));
		bossPrime.put(BossType.JULIAN, atlas.findRegions("julian-stand-prime"));
		bossPrime.put(BossType.RENE, atlas.findRegions("rene-stand-prime"));
		bossPrime.put(BossType.SVEN, atlas.findRegions("sven-stand-prime"));
		bossPrime.put(BossType.ENDLESS, atlas.findRegions("stuart-stand-prime"));

		bossEnter = new ObjectMap<BossType, Array<TextureAtlas.AtlasRegion>>();
		bossEnter.put(BossType.MELVIN, atlas.findRegions("melvin-stand"));
		bossEnter.put(BossType.JULIAN, atlas.findRegions("julian-stand"));
		bossEnter.put(BossType.RENE, atlas.findRegions("rene-stand"));
		bossEnter.put(BossType.SVEN, atlas.findRegions("sven-stand"));
		bossEnter.put(BossType.ENDLESS, atlas.findRegions("stuart-walk"));

		bulletAnim = new ObjectMap<BulletType, Array<TextureAtlas.AtlasRegion>>();
		bulletAnim.put(BulletType.RAINBOW, atlas.findRegions("bullet-box"));
		bulletAnim.put(BulletType.SPEECH_UP, atlas.findRegions("speech-up"));
		bulletAnim.put(BulletType.SPEECH_DOWN, atlas.findRegions("speech-down"));
		bulletAnim.put(BulletType.BEER, atlas.findRegions("beer"));
		bulletAnim.put(BulletType.MILK, atlas.findRegions("milk"));
		bulletAnim.put(BulletType.FIREBALL_UP, atlas.findRegions("fireball-up"));
		bulletAnim.put(BulletType.FIREBALL_DOWN, atlas.findRegions("fireball-down"));
		bulletAnim.put(BulletType.FIREBALL, atlas.findRegions("fireball"));
		bulletAnim.put(BulletType.SAR, atlas.findRegions("sai"));
		bulletAnim.put(BulletType.JUGGLINGBALL, atlas.findRegions("jugglingball"));
		bulletAnim.put(BulletType.LIGHTNING, atlas.findRegions("bullet-box"));
		bulletAnim.put(BulletType.PASTY, atlas.findRegions("pasty"));
		bulletAnim.put(BulletType.SCIMITAR, atlas.findRegions("bullet-box"));
		bulletAnim.put(BulletType.TRON_DELAY, atlas.findRegions("tron-delay"));
		bulletAnim.put(BulletType.TRON_DOWN, atlas.findRegions("tron-down"));
		bulletAnim.put(BulletType.TRON_SQUARE, atlas.findRegions("tron-square"));
		bulletAnim.put(BulletType.TRON_UP, atlas.findRegions("tron-up"));
		bulletAnim.put(BulletType.TRON_WAVE, atlas.findRegions("tron-wave"));
		bulletAnim.put(BulletType.TRON_SAWTOOTH, atlas.findRegions("tron-sawtooth"));

		boltAnim = atlas.findRegions("bolt");

		cat = atlas.findRegions("cat");

		hangStar = atlas.findRegion("hang-star");
		hangEarth = atlas.findRegion("hang-earth");
		stackedTable = atlas.findRegion("stacked-table");
		table = atlas.findRegion("table");
		ball = atlas.findRegion("ball");
		pin = atlas.findRegion("pin");
		tableSimon = atlas.findRegion("table-simon");

		tonye = atlas.findRegion("tonye-sit");
		abi = atlas.findRegion(("abi"));
		mo = atlas.findRegion(("mo-sit-rose"));
		kathryn = atlas.findRegion("kathryn-sit");

		page = atlas.findRegion("thesis-page");

		instructions = atlas.findRegion("instructions");

		whitePixel = atlas.findRegion("white-pixel");
		bulletBox = atlas.findRegion("bullet-box");
		diamond = atlas.findRegion("diamond");
		flame = atlas.findRegion("flame");
		health = atlas.findRegion("health");
		healthBar = atlas.findRegion("health-bar");
		musicNote = atlas.findRegion("music-note");
		sleepZ = atlas.findRegion("sleepZ");
		tombstone = atlas.findRegion("tombstone");
		book = atlas.findRegion("book");
		blackPage = atlas.findRegion("thesis-page-black");

		buttonPixelart = atlas.findRegion("button-pixelart");

		skin.addRegions(atlas);
		buttonBorder = (NinePatchDrawable) skin.getDrawable("button-border");
		whiteButtonBorder = (NinePatchDrawable) skin.getDrawable("white-button-border");
	}
}
