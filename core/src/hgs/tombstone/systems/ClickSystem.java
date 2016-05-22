package hgs.tombstone.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import hgs.tombstone.components.*;
import hgs.tombstone.input.InputManager;
import org.w3c.dom.css.Rect;

public class ClickSystem extends IteratingSystem {
	public ClickSystem() {
		super(Family.all(ClickComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		ClickComponent bc = ComponentMappers.click.get(entity);

		if (!bc.active) {
			return;
		}

		bc.pointerOver = false;
		bc.clickLast = false;

		Vector2 pos = new Vector2(InputManager.screenInput.get(0).getPointerLocation());
		boolean inside = false;
		if (ComponentMappers.transform.has(entity)) {
			TransformComponent tc = ComponentMappers.transform.get(entity);
			inside = isInside(bc, pos.x - tc.body.getPosition().x, pos.y - tc.body.getPosition().y);
		} else {
			inside = isInside(bc, pos.x, pos.y);
		}
		if (inside) {
			if (InputManager.screenInput.get(0).isPointerDown()) {
				bc.pointerOver = true;
			} else if (InputManager.screenInput.get(0).isPointerUpLast()) {
				bc.clickLast = true;
				if (bc.clicker != null)
					bc.clicker.onClick(entity);
			}
		}

		if (ComponentMappers.textbutton.has(entity)) {
			TextButtonComponent tc = ComponentMappers.textbutton.get(entity);
			NinepatchComponent npc = ComponentMappers.ninepatch.get(entity);
			if (InputManager.screenInput.get(0).isPointerDown() && inside) {
				npc.color = tc.pressed;
			} else {
				npc.color = tc.base;
			}
		}
	}

	private boolean isInside(ClickComponent bc, float x, float y) {
		if (bc.shape == null) {
			return true;
		} else {
			return bc.shape.contains(x, y);
		}
	}
}
