package hgs.tombstone.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
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

	public static TextureRegion soundOn;
	public static TextureRegion soundOff;

	public static TextureRegion frameCrab;
	public static TextureRegion frameHorsehead;
	public static TextureRegion frameSombrero;
	public static TextureRegion frameRing;
	public static TextureRegion rip;

	public static Texture backgroundStars;
	public static TextureRegion sheeptext;
	public static TextureRegion sheepstare;
	public static TextureRegion sheepeye;
	public static TextureRegion sheepaura;
	public static TextureRegion sheeplight;
	public static TextureRegion lightline;


	public static TextureRegion title;
	public static TextureRegion tournamentUp;
	public static TextureRegion tournamentDown;
	public static TextureRegion tournament;

	public static TextureRegion mainBackgroundScrollable;
	public static TextureRegion tronBackground;
	public static TextureRegion battleBackground;
	public static TextureRegion book;
	public static TextureRegion blackPage;

	public static ObjectMap<PlayerType, Array<TextureAtlas.AtlasRegion>> playerRunning;
	public static ObjectMap<PlayerType, Array<TextureAtlas.AtlasRegion>> playerJumping;
	public static ObjectMap<PlayerType, Array<TextureAtlas.AtlasRegion>> playerSliding;
	public static ObjectMap<PlayerType, Array<TextureAtlas.AtlasRegion>> playerIdle;

	public static Array<TextureAtlas.AtlasRegion> cat;

	public static ObjectMap<MiniBossType, Array<TextureAtlas.AtlasRegion>> miniBossPrime;
	public static ObjectMap<MiniBossType, Array<TextureAtlas.AtlasRegion>> miniBossThrow;
	public static ObjectMap<BossType, Array<TextureAtlas.AtlasRegion>> bossPrime;
	public static ObjectMap<BossType, Array<TextureAtlas.AtlasRegion>> bossThrow;
	public static ObjectMap<BossType, Array<TextureAtlas.AtlasRegion>> bossEnter;

	public static ObjectMap<BulletType, Array<TextureAtlas.AtlasRegion>> bulletAnim;

	public static TextureRegion chairLeft;
	public static TextureRegion chairRight;
	public static TextureRegion computerDesk;
	public static TextureRegion computerDeskDouble;
	public static TextureRegion baubles;
	public static TextureRegion drawers;
	public static TextureRegion minifridge;

	public static TextureRegion hangStar;
	public static TextureRegion hangEarth;
	public static TextureRegion stackedTable;
	public static TextureRegion ball;
	public static TextureRegion pin;
	public static TextureRegion tableSimon;
	public static TextureRegion table;
	public static TextureRegion page;
	public static TextureRegion whitePixel;
	public static TextureRegion bulletBox;
	public static TextureRegion health;
	public static TextureRegion healthBar;
	public static TextureRegion diamond;
	public static TextureRegion flame;
	public static TextureRegion pepperoni;
	public static TextureRegion musicNote;
	public static TextureRegion tonye;
	public static TextureRegion sleepZ;
	public static TextureRegion abi;
	public static TextureRegion kathryn;
	public static TextureRegion mo;
	public static TextureRegion tombstone;
	public static Array<TextureAtlas.AtlasRegion> boltAnim;

	public static TextureRegion selectCharButtonLeft, selectCharButtonRight;
	public static TextureRegion buttonPixelart;
	public static NinePatchDrawable buttonBorder;
	public static NinePatchDrawable whiteButtonBorder;

	private static Skin skin = new Skin();

	public static void load(AssetManager manager) {
		TextureAtlas atlas = manager.get("atlas/art.atlas", TextureAtlas.class);

		backgroundStars = manager.get("bg-stars.png", Texture.class);

		soundOn = atlas.findRegion("sound-on");
		soundOff = atlas.findRegion("sound-off");

		frameCrab = atlas.findRegion("frame-crab");
		frameHorsehead = atlas.findRegion("frame-horsehead");
		frameRing = atlas.findRegion("frame-ring");
		frameSombrero = atlas.findRegion("frame-sombrero");
		rip = atlas.findRegion("rip");

		sheeptext = atlas.findRegion("sheepstaretext");
		sheepstare = atlas.findRegion("sheepstare");
		sheepeye = atlas.findRegion("sheepeye");
		sheepaura = atlas.findRegion("sheepaura");
		sheeplight = atlas.findRegion("sheeplight");
		lightline = atlas.findRegion("lightline");

		title = atlas.findRegion("procrastichase");

		tournamentUp = atlas.findRegion("tournament-button-off");
		tournamentDown = atlas.findRegion("tournament-button-on");
		tournament = atlas.findRegion("tournament-button");

		mainBackgroundScrollable = atlas.findRegion("background");
		battleBackground = atlas.findRegion("endbattle-bg");
		tronBackground = atlas.findRegion("tron-bg");

		playerRunning = new ObjectMap<PlayerType, Array<TextureAtlas.AtlasRegion>>();
		playerRunning.put(PlayerType.FERNANDO, atlas.findRegions("fernando-run"));
		playerRunning.put(PlayerType.HARRY, atlas.findRegions("harry-run"));
		playerRunning.put(PlayerType.KARIM, atlas.findRegions("karim-run"));
		playerRunning.put(PlayerType.JAKE, atlas.findRegions("jake-run"));

		playerJumping = new ObjectMap<PlayerType, Array<TextureAtlas.AtlasRegion>>();
		playerJumping.put(PlayerType.FERNANDO, atlas.findRegions("fernando-jump"));
		playerJumping.put(PlayerType.HARRY, atlas.findRegions("harry-jump"));
		playerJumping.put(PlayerType.KARIM, atlas.findRegions("karim-jump"));
		playerJumping.put(PlayerType.JAKE, atlas.findRegions("jake-jump"));

		playerSliding = new ObjectMap<PlayerType, Array<TextureAtlas.AtlasRegion>>();
		playerSliding.put(PlayerType.FERNANDO, atlas.findRegions("fernando-slide"));
		playerSliding.put(PlayerType.HARRY, atlas.findRegions("harry-slide"));
		playerSliding.put(PlayerType.KARIM, atlas.findRegions("karim-slide"));
		playerSliding.put(PlayerType.JAKE, atlas.findRegions("jake-slide"));

		playerIdle= new ObjectMap<PlayerType, Array<TextureAtlas.AtlasRegion>>();
		playerIdle.put(PlayerType.FERNANDO, atlas.findRegions("fernando-idle"));
		playerIdle.put(PlayerType.HARRY, atlas.findRegions("harry-idle"));
		playerIdle.put(PlayerType.KARIM, atlas.findRegions("karim-idle"));
		playerIdle.put(PlayerType.JAKE, atlas.findRegions("jake-idle"));

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
		bossThrow.put(BossType.STUART, atlas.findRegions("stuart-stand-throw"));
		bossThrow.put(BossType.TOM, atlas.findRegions("tom-stand-throw"));

		bossPrime = new ObjectMap<BossType, Array<TextureAtlas.AtlasRegion>>();
		bossPrime.put(BossType.MELVIN, atlas.findRegions("melvin-stand-prime"));
		bossPrime.put(BossType.JULIAN, atlas.findRegions("julian-stand-prime"));
		bossPrime.put(BossType.RENE, atlas.findRegions("rene-stand-prime"));
		bossPrime.put(BossType.SVEN, atlas.findRegions("sven-stand-prime"));
		bossPrime.put(BossType.STUART, atlas.findRegions("stuart-stand-prime"));
		bossPrime.put(BossType.TOM, atlas.findRegions("tom-stand-prime"));

		bossEnter = new ObjectMap<BossType, Array<TextureAtlas.AtlasRegion>>();
		bossEnter.put(BossType.MELVIN, atlas.findRegions("melvin-stand"));
		bossEnter.put(BossType.JULIAN, atlas.findRegions("julian-stand"));
		bossEnter.put(BossType.RENE, atlas.findRegions("rene-stand"));
		bossEnter.put(BossType.SVEN, atlas.findRegions("sven-stand"));
		bossEnter.put(BossType.STUART, atlas.findRegions("stuart-walk"));
		bossEnter.put(BossType.TOM, atlas.findRegions("tom-walk"));

		bulletAnim = new ObjectMap<BulletType, Array<TextureAtlas.AtlasRegion>>();
		bulletAnim.put(BulletType.RAINBOW, atlas.findRegions("bullet-box"));
		bulletAnim.put(BulletType.SPEECH_UP, atlas.findRegions("speech-up"));
		bulletAnim.put(BulletType.SPEECH_DOWN, atlas.findRegions("speech-down"));
		bulletAnim.put(BulletType.BEER, atlas.findRegions("beer"));
		bulletAnim.put(BulletType.MILK, atlas.findRegions("milk"));
		bulletAnim.put(BulletType.FIREBALL, atlas.findRegions("fireball"));
		bulletAnim.put(BulletType.SAR, atlas.findRegions("sai"));
		bulletAnim.put(BulletType.JUGGLINGBALL, atlas.findRegions("jugglingball"));
		bulletAnim.put(BulletType.PIZZA, atlas.findRegions("pizza"));
		bulletAnim.put(BulletType.PASTY, atlas.findRegions("pasty"));
		bulletAnim.put(BulletType.SCIMITAR, atlas.findRegions("knife"));
		bulletAnim.put(BulletType.TRON_DELAY, atlas.findRegions("tron-delay"));
		bulletAnim.put(BulletType.TRON_DOWN, atlas.findRegions("tron-down"));
		bulletAnim.put(BulletType.TRON_SQUARE, atlas.findRegions("tron-square"));
		bulletAnim.put(BulletType.TRON_UP, atlas.findRegions("tron-up"));
		bulletAnim.put(BulletType.TRON_WAVE, atlas.findRegions("tron-wave"));
		bulletAnim.put(BulletType.TRON_SAWTOOTH, atlas.findRegions("tron-sawtooth"));
		bulletAnim.put(BulletType.JUGGLINGPIN, atlas.findRegions("pin"));

		boltAnim = atlas.findRegions("bolt");

		cat = atlas.findRegions("cat");

		chairLeft = atlas.findRegion("chair-left");
		chairRight = new TextureRegion(chairLeft);
		chairRight.flip(true, false);
		computerDesk = atlas.findRegion("computer-desk");
		computerDeskDouble = atlas.findRegion("computer-desk-double");
		baubles = atlas.findRegion("baubles");
		drawers = atlas.findRegion("drawers");
		minifridge = atlas.findRegion("minifridge");

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
		pepperoni = atlas.findRegion("pepperoni");

		selectCharButtonLeft = atlas.findRegion("select-char-button");
		selectCharButtonRight = new TextureRegion(selectCharButtonLeft);
		selectCharButtonRight.flip(true, false);
		buttonPixelart = atlas.findRegion("button-pixelart");

		skin.addRegions(atlas);
		buttonBorder = (NinePatchDrawable) skin.getDrawable("button-border");
		whiteButtonBorder = (NinePatchDrawable) skin.getDrawable("white-button-border");
	}
}
