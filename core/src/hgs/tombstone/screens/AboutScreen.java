package hgs.tombstone.screens;

import com.badlogic.ashley.core.Entity;

import com.badlogic.gdx.Gdx;
import hgs.tombstone.TombstoneGame;
import hgs.tombstone.components.ClickInterface;
import hgs.tombstone.elements.GameMenuButton;
import hgs.tombstone.systems.ClickSystem;
import hgs.tombstone.systems.TweenSystem;
import hgs.tombstone.world.EntityFactory;
import hgs.tombstone.world.WorldUI;

import java.util.ArrayList;

public class AboutScreen extends BasicScreen {

	private static class Credit {
		public String name = "";
		public String credit = "";
		public String linkText = "";
		public String linkUrl = "";

		public Credit(String name, String credit, String linkText, String linkUrl) {
			this.name = name;
			this.credit = credit;
			this.linkText = linkText;
			this.linkUrl = linkUrl;
		}
	}

	private static final float TITLE_Y = WORLD_HEIGHT * 5.0f / 6.0f;
	private static final float TITLE_TIME = 1.0f;

	private static final float CREDITS_X = WORLD_WIDTH / 2.0f - 1.0f;
	private static final float CREDITS_Y = WORLD_HEIGHT / 2.0f - 1.65f;
	private static final float CREDITS_SPACING = 0.3f;
	private static final float LINK_X = WORLD_WIDTH / 2.0f + 1.8f;
	private static final float BACK_X = 1.5f;
	private static final float BACK_Y = WORLD_HEIGHT - 0.5f;

	private static final String TEXTMADEFOR = "Made using LibGDX";
	private static final String TEXTSRC = "Source code:";
	private static final String TEXTGITHUB = "GITHUB";
	private static final String URLGITHUB = "https://github.com/hgsteggles/tombstone";
	private static final String TEXTMADEBY = "Credits:";

	private static final ArrayList<Credit> CREDITS = new ArrayList<Credit>();
	static {
		CREDITS.add(new Credit("Abigail Frost", "Art", "", ""));
		CREDITS.add(new Credit("Harry Steggles", "Code", "GITHUB", "https://github.com/hgsteggles"));
	}

	public AboutScreen(TombstoneGame game) {
		super(game);

		add(new ClickSystem());
		add(new TweenSystem());

		add(WorldUI.createFullBlackBG());

		add(createText(WORLD_WIDTH / 2.0f, WORLD_HEIGHT * 2.0f / 3.0f, TEXTMADEFOR, 0));
		add(createText(WORLD_WIDTH / 2.0f - 1.5f, WORLD_HEIGHT * 2.0f / 3.0f - 1.0f, TEXTSRC, 1));

		GameMenuButton butSrc = new GameMenuButton(WORLD_WIDTH / 2.0f + 2.0f, WORLD_HEIGHT * 2.0f / 3.0f - 1.0f, TEXTGITHUB, new ClickInterface() {
			@Override
			public void onClick(Entity entity) {
				Gdx.net.openURI(URLGITHUB);
			}
		});
		butSrc.addToEngine(engine);

		add(createText(WORLD_WIDTH / 2.0f, WORLD_HEIGHT * 2.0f / 3.0f - 2.0f, TEXTMADEBY, 2));

		for (int i = 0; i < CREDITS.size(); i++) {
			addCredit(CREDITS.get(i), i);
		}

		GameMenuButton butBack = new GameMenuButton(BACK_X, BACK_Y, 2.0f, "BACK", new ClickInterface() {
			@Override
			public void onClick(Entity entity) {
				backPressed();
			}
		});
		butBack.addToEngine(engine);
	}

	@Override
	public void backPressed() {
		game.setScreen(new MenuScreen2(game));
	}

	public void addCredit(final Credit credit, int n) {
		String fullText = credit.name + " - " + credit.credit;
		float offset = (fullText.length() - (credit.name.length() * 2.0f)) / 10.0f;
		Entity text = createText(CREDITS_X + offset, CREDITS_Y - (n * CREDITS_SPACING), fullText, true);
		add(text);
		if (credit.linkUrl != "") {
			GameMenuButton link = new GameMenuButton(LINK_X, CREDITS_Y - (n * CREDITS_SPACING), credit.linkText, new ClickInterface() {
				@Override
				public void onClick(Entity entity) {
					Gdx.net.openURI(credit.linkUrl);
				}
			}, true);
			link.addToEngine(engine);
		}
	}

	private Entity createText(float x, float y, String text, int n) {
		Entity entity = createText(x, y, text);

		return entity;
	}
}