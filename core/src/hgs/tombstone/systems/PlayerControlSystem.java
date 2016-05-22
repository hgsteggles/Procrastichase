package hgs.tombstone.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import hgs.tombstone.components.*;
import hgs.tombstone.input.InputManager;
import hgs.tombstone.screens.BasicScreen;
import hgs.tombstone.world.EntityFactory;
import hgs.tombstone.world.World;
import hgs.tombstone.elements.Enums.*;

/**
 * Created by harry on 02/04/16.
 */
public class PlayerControlSystem extends EntitySystem {
	private ImmutableArray<Entity> players;
	private ImmutableArray<Entity> buttons;

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		players = engine.getEntitiesFor(Family.all(ControlComponent.class).get());
		buttons = engine.getEntitiesFor(Family.all(ControlButtonComponent.class, TextureComponent.class, TransformComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		if (InputManager.screenInput.keyUpLastSet.contains(Input.Keys.W)) {
			jumpReleased();
		}
		else if (InputManager.screenInput.keyDownSet.contains(Input.Keys.W)) {
			if (InputManager.screenInput.keyDownLastSet.contains(Input.Keys.W))
				attemptJump(-1);
			else
				holdJump();
		}

		if (InputManager.screenInput.keyUpLastSet.contains(Input.Keys.S)) {
			slideReleased();
		}
		else if (InputManager.screenInput.keyDownSet.contains(Input.Keys.S)) {
			if (InputManager.screenInput.keyDownLastSet.contains(Input.Keys.S))
				attemptSlide(-1);
			else
				holdSlide();
		}

		for (int iptr = 0; iptr != InputManager.screenInput.getNumPointers(); ++iptr) {
			if (InputManager.screenInput.get(iptr).isPointerUpLast())
				pointerReleased(iptr);

			if (InputManager.screenInput.get(iptr).isPointerDownLast()) {
				Vector2 clickPos = InputManager.screenInput.get(iptr).getPointerDownLocation();

				for (Entity button : buttons) {
					ControlButtonComponent controlComp = ComponentMappers.controlbutton.get(button);
					TransformComponent tc = ComponentMappers.transform.get(button);

					boolean inside = isInside(controlComp.shape, clickPos.x - tc.body.getPosition().x, clickPos.y - tc.body.getPosition().y);
					if (inside) {
						switch (controlComp.controlType) {
							case FIRE:
								for (Entity player : players)
									GunSystem.shoot(ComponentMappers.gun.get(player));
								break;
							case SLIDE:
								for (Entity player : players) {
									StateComponent stateComp = ComponentMappers.state.get(player);
									if ((stateComp.get() == PlayerState.RUN.value()
											|| stateComp.get() == PlayerState.IDLE.value()))
										SlideSystem.slide(player, iptr);
								}
								break;
							case JUMP:
								for (Entity player : players) {
									StateComponent stateComp = ComponentMappers.state.get(player);
									if ((stateComp.get() == PlayerState.RUN.value()
											|| stateComp.get() == PlayerState.IDLE.value()))
										JumpSystem.jump(player, iptr);
								}
								break;
						}
					}
				}
			}
		}

		for (Entity button : buttons) {
			ControlButtonComponent controlComp = ComponentMappers.controlbutton.get(button);
			TextureComponent texComp = ComponentMappers.texture.get(button);

			switch (controlComp.controlType) {
				case FIRE:
					for (Entity player : players) {
						if (ComponentMappers.gun.has(player) && ComponentMappers.gun.get(player).heat > 0)
							texComp.color.set(controlComp.onColor);
						else
							texComp.color.set(controlComp.offColor);
					}
					break;
				case SLIDE:
					for (Entity player : players) {
						if (ComponentMappers.state.get(player).get() == PlayerState.SLIDE.value())
							texComp.color.set(controlComp.onColor);
						else
							texComp.color.set(controlComp.offColor);
					}
					break;
				case JUMP:
					for (Entity player : players) {
						if (ComponentMappers.state.get(player).get() == PlayerState.JUMP.value())
							texComp.color.set(controlComp.onColor);
						else
							texComp.color.set(controlComp.offColor);
					}
					break;
			}
		}
	}

	private void jumpReleased() {
		for (Entity player : players) {
			JumpComponent jumpComp = ComponentMappers.jump.get(player);
			jumpComp.jumpReleased = true;
			jumpComp.jumpHeld = false;
		}
	}

	private void slideReleased() {
		for (Entity player : players) {
			SlideComponent slideComp = ComponentMappers.slide.get(player);
			slideComp.slideReleased = true;
			slideComp.slideHeld = false;
		}
	}

	private void attemptJump(int pointer) {
		for (Entity player : players) {
			StateComponent stateComp = ComponentMappers.state.get(player);
			if ((stateComp.get() == PlayerState.RUN.value()
					|| stateComp.get() == PlayerState.IDLE.value())) {
				JumpSystem.jump(player, pointer);
			}
		}
	}

	private void attemptSlide(int pointer) {
		for (Entity player : players) {
			StateComponent stateComp = ComponentMappers.state.get(player);
			if ((stateComp.get() == PlayerState.RUN.value()
					|| stateComp.get() == PlayerState.IDLE.value())) {
				SlideSystem.slide(player, pointer);
			}
		}
	}

	private void holdJump() {
		for (Entity player : players) {
			JumpComponent jumpComp = ComponentMappers.jump.get(player);
			StateComponent stateComp = ComponentMappers.state.get(player);

			if (stateComp.get() == PlayerState.JUMP.value()
					&& !jumpComp.jumpReleased) {
				jumpComp.jumpHeld = true;
			}
		}
	}

	private void holdSlide() {
		for (Entity player : players) {
			SlideComponent slideComp = ComponentMappers.slide.get(player);
			StateComponent stateComp = ComponentMappers.state.get(player);

			if (stateComp.get() == PlayerState.SLIDE.value()
					&& !slideComp.slideReleased) {
				slideComp.slideHeld = true;
			}
		}
	}

	private void pointerReleased(int iptr) {
		for (Entity player : players) {
			JumpComponent jumpComp = ComponentMappers.jump.get(player);
			SlideComponent slideComp = ComponentMappers.slide.get(player);

			if (jumpComp.pointer == iptr) {
				jumpComp.jumpReleased = true;
				jumpComp.jumpHeld = false;
				jumpComp.pointer = -1;
			}
			if (slideComp.pointer == iptr) {
				slideComp.slideReleased = true;
				slideComp.slideHeld = false;
				slideComp.pointer = -1;
			}
		}
	}

	private boolean isInside(Shape2D shape, float x, float y) {
		if (shape == null) {
			return true;
		} else {
			return shape.contains(x, y);
		}
	}
}
