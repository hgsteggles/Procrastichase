package hgs.tombstone.screens;

import hgs.tombstone.TombstoneGame;
import hgs.tombstone.elements.Enums;
import hgs.tombstone.input.InputManager;
import hgs.tombstone.systems.*;
import hgs.tombstone.world.World;

/**
 * Created by harry on 01/04/16.
 */
public class GameScreen extends BasicScreen {
	private World world;
	private int level;
	private boolean doublespeed = false;
	private int old_npages;
	private Enums.PlayerType playerType;

	public GameScreen(TombstoneGame game, int level, int npages, Enums.PlayerType playerType) {
		super(game);

		this.old_npages = npages;
		this.level = level;
		this.playerType = playerType;
		world = new World(level, npages, playerType);

		add(new ClickSystem());
		add(new TweenSystem());
		add(new MovementSystem());
		add(new ScrollSystem());
		add(new TickerSystem());
		add(new AnimationSystem());
		add(new SoundSystem());
		add(new JumpSystem());
		add(new SlideSystem());
		add(new PlayerControlSystem());
		add(new GunSystem());
		add(new CameraSystem());
		add(new CollisionSystem(world));
		add(new GameStateSystem(world));
		add(new PageCountSystem(world));
		add(new BoundsSystem(worldCamera));
		add(new RemovalSystem());
		add(new EmitterSystem());
		add(new MiniBossSystem());
		add(new BossSystem());
		//add(new ShakeSystem());
		add(new StopSystem());
		add(new BarrierSystem());
		add(new HealthBarSystem());
		add(new MusicSystem());
		add(new EndlessSystem());
		add(new DelaySystem());
		//add(new DebugSystem(worldCamera));

		world.populateLevel(engine, worldCamera);
	}

	@Override
	public void update(float delta) {
		if (doublespeed)
			delta *= 4;
		super.update(delta);

		if (engine.getSystem(GameStateSystem.class).screenChange == GameStateSystem.SCREEN_GAMEOVER) {
			if (level != 5)
				game.setScreen(new GameoverScreen(game, level, old_npages, playerType));
			else
				game.setScreen(new EndlessFinishScreen(game, world.timeCount.count, playerType));
		}
		else if (engine.getSystem(GameStateSystem.class).screenChange == GameStateSystem.SCREEN_NEXTCHAPTER) {
			game.setScreen(new ChapterCompleteScreen(game, level, world.getNumberPages(), playerType));
		}

		if (engine.getSystem(GameStateSystem.class).nextScreenChange != GameStateSystem.SCREEN_NONE
				&& InputManager.screenInput.get(0).isPointerDown() && InputManager.screenInput.get(0).isPointerDownLast()) {
			doublespeed = true;
		}
	}

	@Override
	public void render(float interp) {
		engine.getSystem(CameraSystem.class).update(interp);
		super.render(interp);
		//engine.getSystem(DebugSystem.class).render();
	}

	@Override
	public void backPressed() {
		engine.getSystem(MusicSystem.class).stopMusic();
		game.setScreen(new MenuScreen2(game));
	}
}
