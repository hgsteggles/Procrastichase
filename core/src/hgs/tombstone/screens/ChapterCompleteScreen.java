package hgs.tombstone.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import hgs.tombstone.TombstoneGame;
import hgs.tombstone.assets.GameArt;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Enums;
import hgs.tombstone.elements.GameSettings;
import hgs.tombstone.systems.*;
import hgs.tombstone.world.EntityFactory;
import hgs.tombstone.world.TransitionFactory;
import hgs.tombstone.world.WorldUI;

/**
 * Created by harry on 03/04/16.
 */
public class ChapterCompleteScreen extends BasicScreen {
	private Enums.PlayerType playerType;

	public ChapterCompleteScreen(TombstoneGame game, int levelComplete, int npages, Enums.PlayerType playerType) {
		super(game);
		this.playerType = playerType;

		add(new ClickSystem());
		add(new TweenSystem());
		add(new ScrollSystem());
		add(new TickerSystem());
		add(new AnimationSystem());
		add(new SoundSystem());
		add(new CameraSystem());

		add(WorldUI.createFullBlackBG());
		add(EntityFactory.createBook());

		int maxPages = Math.min(levelComplete * 10, 30);

		add(createChapterMessage(2.66f, BasicScreen.WORLD_HEIGHT / 2.0f + 1.0f, levelComplete));
		if (levelComplete != 4)
			add(createChapterMessage(5.36f, BasicScreen.WORLD_HEIGHT / 2.0f + 1.0f, levelComplete + 1));
		add(WorldUI.createBlackPageIcon(2.66f, BasicScreen.WORLD_HEIGHT / 2.0f - 0.0f));
		add(createPageCount(2.66f, BasicScreen.WORLD_HEIGHT / 2.0f - 0.5f, npages, maxPages));
		if (levelComplete < 3 || ((levelComplete != 4) && (npages == maxPages))) {
			System.out.println(npages + " " + maxPages);
			add(createContinueText("Press", 5.32f, BasicScreen.WORLD_HEIGHT / 2.0f + 0.0f));
			add(createContinueText("to", 5.32f, BasicScreen.WORLD_HEIGHT / 2.0f - 0.25f));
			add(createContinueText("Continue", 5.32f, BasicScreen.WORLD_HEIGHT / 2.0f - 0.5f));
			add(createClickArea(levelComplete + 1, npages));
		}
		else if (levelComplete == 3){
			add(createThanksMessage());
			add(createContinueText("Locked", 5.32f, BasicScreen.WORLD_HEIGHT / 2.0f - 0.25f));
			add(createClickArea(-1, npages));
		}
		else if (levelComplete == 4) {
			add(createThanksMessage());
			add(createClickArea(-1, npages));
		}

		if (game.actionResolver.getSignedInGPGS()) {
			if (levelComplete == 1)
				game.actionResolver.unlockAchievementGPGS("CgkI4qLb0q0bEAIQAQ");
			else if (levelComplete == 2)
				game.actionResolver.unlockAchievementGPGS("CgkI4qLb0q0bEAIQAg");
			else if (levelComplete == 3)
				game.actionResolver.unlockAchievementGPGS("CgkI4qLb0q0bEAIQAw");
			else if (levelComplete == 4)
				game.actionResolver.unlockAchievementGPGS("CgkI4qLb0q0bEAIQBA");
		}

		if (GameSettings.getLevelsComplete() < levelComplete) {
			GameSettings.setLevelsComplete(levelComplete);
		}

		if (levelComplete == 4 && npages == 30) {
			GameSettings.setCollectedAllPages(true);
		}
	}

	@Override
	public void backPressed() {
		game.setScreen(new MenuScreen2(game));
	}

	public Entity createThanksMessage() {
		Entity entity = new Entity();

		BitmapFontComponent fontComp = new BitmapFontComponent();
		fontComp.font = "retro";
		fontComp.string = "Thanks for Playing!";
		fontComp.color.set(Color.WHITE);
		fontComp.centering = true;
		entity.add(fontComp);

		TransformComponent transComp = new TransformComponent();
		float x = BasicScreen.WORLD_WIDTH / 2.0f;
		float y = BasicScreen.WORLD_HEIGHT / 2.0f + 2.5f;
		float z = 11.0f;
		transComp.body.initPosition(x, y, z);

		entity.add(transComp);

		entity.add(new TweenComponent());

		return entity;
	}

	public Entity createContinueText(String text, float x, float y) {
		Entity entity = new Entity();

		BitmapFontComponent fontComp = new BitmapFontComponent();
		fontComp.font = "retro";
		fontComp.string = text;
		fontComp.color.set(40.0f/255.0f, 40.0f/255.0f, 40.0f/255.0f, 1.0f);
		fontComp.centering = true;
		fontComp.scale = 0.5f;
		entity.add(fontComp);

		TransformComponent transComp = new TransformComponent();
		float z = 11.0f;
		transComp.body.initPosition(x, y, z);

		entity.add(transComp);

		return entity;
	}

	public Entity createChapterMessage(float x, float y, int chpt) {
		Entity entity = new Entity();

		BitmapFontComponent fontComp = new BitmapFontComponent();
		fontComp.font = "retro";
		if (chpt != 4)
			fontComp.string = "Chapter " + Integer.toString(chpt);
		else
			fontComp.string = "Appendix";
		fontComp.color.set(40.0f/255.0f, 40.0f/255.0f, 40.0f/255.0f, 1.0f);
		fontComp.centering = true;
		fontComp.scale = 0.75f;
		entity.add(fontComp);

		TransformComponent transComp = new TransformComponent();
		float z = 11.0f;
		transComp.body.initPosition(x, y, z);

		entity.add(transComp);

		return entity;
	}

	public Entity createPageCount(float x, float y, int npages, int totpages) {
		Entity entity = new Entity();

		BitmapFontComponent fontComp = new BitmapFontComponent();
		fontComp.font = "retro";
		fontComp.string = Integer.toString(npages) + "/" + Integer.toString(totpages);
		if (npages != totpages)
			fontComp.color.set(40.0f/255.0f, 40.0f/255.0f, 40.0f/255.0f, 1.0f);
		else
			fontComp.color.set(0.2f, 1.0f, 0.2f, 1.0f);

		fontComp.centering = true;
		entity.add(fontComp);

		TransformComponent transComp = new TransformComponent();
		float z = 11.0f;
		transComp.body.initPosition(x, y, z);

		entity.add(transComp);

		return entity;
	}

	public Entity createClickArea(final int nextLevel, final int npages) {
		Entity entity = new Entity();

		ClickComponent clickComp = new ClickComponent();
		clickComp.shape = new Rectangle()
				.setSize(GameScreen.WORLD_WIDTH, GameScreen.WORLD_HEIGHT)
				.setCenter(GameScreen.WORLD_WIDTH / 2.0f, GameScreen.WORLD_HEIGHT / 2.0f);
		clickComp.clicker = new ClickInterface() {
			@Override
			public void onClick(Entity e) {
				TickerComponent tickComp = new TickerComponent();
				tickComp.finishActive = true;
				tickComp.duration = 2.0f;
				if (nextLevel != -1)
					tickComp.finish = new EventInterface() {
					@Override
					public void dispatchEvent(Entity e) {
						game.setScreen(new GameScreen(game, nextLevel, npages, playerType));
					}
				};
				else
					tickComp.finish = new EventInterface() {
						@Override
						public void dispatchEvent(Entity e) {
							game.setScreen(new MenuScreen2(game));
						}
					};
				e.add(tickComp);

				e.remove(ClickComponent.class);

				add(TransitionFactory.createBlackFadeOverlay(2.0f));
			}
		};
		entity.add(clickComp);

		return entity;
	}


}
