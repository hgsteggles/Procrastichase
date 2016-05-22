package hgs.tombstone.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import hgs.tombstone.TombstoneGame;
import hgs.tombstone.components.*;
import hgs.tombstone.systems.*;
import hgs.tombstone.world.EntityFactory;
import hgs.tombstone.world.WorldUI;

/**
 * Created by harry on 03/04/16.
 */
public class GameCompleteScreen extends BasicScreen {
	private Entity message;
	private Entity start;

	public GameCompleteScreen(TombstoneGame game) {
		super(game);

		add(new ClickSystem());
		add(new TweenSystem());
		add(new ScrollSystem());
		add(new TickerSystem());
		add(new AnimationSystem());
		add(new SoundSystem());
		add(new CameraSystem());

		message = createMessage();
		start = createStart();

		add(WorldUI.createFullBlackBG());
		add(EntityFactory.createBook());
		add(message);
		add(start);
	}

	@Override
	public void backPressed() {

	}

	public Entity createMessage() {
		Entity entity = new Entity();

		BitmapFontComponent fontComp = new BitmapFontComponent();
		fontComp.font = "retro";
		fontComp.string = "Thanks for Playing!";
		fontComp.color.set(Color.WHITE);
		fontComp.centering = true;
		entity.add(fontComp);

		TransformComponent transComp = new TransformComponent();
		float x = BasicScreen.WORLD_WIDTH / 2.0f;
		float y = BasicScreen.WORLD_HEIGHT / 2.0f + 0.25f;
		float z = 11.0f;
		transComp.body.initPosition(x, y, z);

		entity.add(transComp);

		entity.add(new TweenComponent());

		return entity;
	}

	public Entity createStart() {
		Entity entity = new Entity();

		BitmapFontComponent fontComp = new BitmapFontComponent();
		fontComp.font = "retro";
		fontComp.string = "Press to Restart";
		fontComp.color.set(Color.WHITE);
		fontComp.centering = true;
		fontComp.scale = 0.5f;
		entity.add(fontComp);

		TransformComponent transComp = new TransformComponent();
		float x = BasicScreen.WORLD_WIDTH / 2.0f;
		float y = BasicScreen.WORLD_HEIGHT / 2.0f - 0.25f;
		float z = 11.0f;
		transComp.body.initPosition(x, y, z);

		entity.add(transComp);

		ClickComponent clickComp = new ClickComponent();
		clickComp.shape = new Rectangle()
				.setSize(GameScreen.WORLD_WIDTH, GameScreen.WORLD_HEIGHT)
				.setCenter(0, 0);
		clickComp.clicker = new ClickInterface() {
			@Override
			public void onClick(Entity e) {
				game.setScreen(new MenuScreen2(game));
			}
		};
		entity.add(clickComp);

		entity.add(new TweenComponent());

		return entity;
	}
}
