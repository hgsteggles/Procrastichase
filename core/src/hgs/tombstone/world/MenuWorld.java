package hgs.tombstone.world;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;
import hgs.tombstone.assets.Assets;
import hgs.tombstone.assets.GameArt;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Enums.*;
import hgs.tombstone.elements.GameMusic;
import hgs.tombstone.elements.GameParameters;
import hgs.tombstone.screens.BasicScreen;
import hgs.tombstone.systems.MovementSystem;
import hgs.tombstone.systems.RenderingSystem;

/**
 * Created by harry on 01/04/16.
 */
public class MenuWorld {
	private int sectionWidth = 20;
	private float playerStartX = 1.5f;
	private PlayerType player;
	private float levelSpeed;

	public MenuWorld() {
		levelSpeed = GameParameters.baseLevelSpeed;
		player = PlayerType.FERNANDO;
	}

	public void populateLevel(Engine engine, OrthographicCamera worldCamera) {
		engine.addEntity(WorldUI.createBottomBar());
		engine.addEntity(WorldUI.createTopBar());
		engine.addEntity(WorldUI.createFullBlackBG());
		engine.addEntity(createCamera(worldCamera));

		for (int i = 0; i < 2; ++i) {
			Entity bg = EntityFactory.createBackground((i + 0.5f) * sectionWidth);
			bg.add(new LoopingBackgroundComponent());
			engine.addEntity(bg);
		}
		engine.addEntity(createPlayer());
	}

	public Entity createPlayer() {
		Entity entity = new Entity();

		StateComponent stateComp = new StateComponent();
		stateComp.set(PlayerState.RUN.value());
		entity.add(stateComp);

		AnimationComponent animComp = new AnimationComponent();
		animComp.animations.put(PlayerState.RUN.value(), new Animation(0.1f, GameArt.playerRunning, Animation.PlayMode.LOOP));
		entity.add(animComp);

		TextureComponent texComp = new TextureComponent();
		TextureRegion region = animComp.animations.get(PlayerState.RUN.value()).getKeyFrame(0);
		texComp.region = region;
		texComp.size.x = (region.getRegionWidth()/(float)(region.getRegionHeight()));
		texComp.size.y = 1.0f;
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		float x = playerStartX;
		float y = BasicScreen.WORLD_HEIGHT / 2.0f - 0.5f;
		float z = 1.0f;
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		PlayerComponent playerComp = new PlayerComponent();
		playerComp.character = player;
		entity.add(playerComp);

		MovementComponent moveComp = new MovementComponent();
		moveComp.linearVelocity.x = levelSpeed;
		entity.add(moveComp);

		return entity;
	}

	public Entity createCamera(OrthographicCamera camera) {
		Entity entity = new Entity();

		TransformComponent transComp = new TransformComponent();
		transComp.body.initPosition(camera.position.x, camera.position.y, camera.position.z);
		entity.add(transComp);

		MovementComponent moveComp = new MovementComponent();
		moveComp.linearVelocity.x = levelSpeed;
		entity.add(moveComp);

		CameraComponent camComp = new CameraComponent();
		camComp.camera = camera;
		entity.add(camComp);

		return entity;
	}
}
