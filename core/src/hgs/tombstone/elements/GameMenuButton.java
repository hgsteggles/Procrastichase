package hgs.tombstone.elements;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import hgs.tombstone.assets.GameArt;
import hgs.tombstone.components.*;

public class GameMenuButton {
	private Entity ninepatch, bitmapfont;

	public GameMenuButton(float x, float y, String text, ClickInterface clickInterface) {
		this(x, y, Depths.hudZ + 1, text.length() / 2.3f, 0.6f, text, clickInterface, false); //2.5 gives exactly text width, 2.3 allows for a little padding
	}

	public GameMenuButton(float x, float y, String text, ClickInterface clickInterface, boolean small) {
		this(x, y, Depths.hudZ + 1, text.length() / 4.6f, 0.3f, text, clickInterface, small);
	}

	public GameMenuButton(float x, float y, float width, String text, ClickInterface clickInterface) {
		this(x, y, Depths.hudZ + 1, width, 0.6f, text, clickInterface, false);
	}

	public GameMenuButton(float x, float y, float z, float w, float h, String text, ClickInterface clickInterface, boolean small) {
		ninepatch = createButton(x, y, z, w, h, clickInterface);
		bitmapfont = createText(x, y, z, w, h, text, small);
	}

	public Entity getBorderEntity() {
		return ninepatch;
	}

	public Entity getTextEntity() {
		return bitmapfont;
	}

	private Entity createText(float x, float y, float z, float w, float h, String text, boolean small) {
		Entity entity = new Entity();

		BitmapFontComponent fontComp = new BitmapFontComponent();
		fontComp.font = "retro";
		if (small) {
			fontComp.scale = 0.5f;
		}
		fontComp.string = text;
		fontComp.color = new Color(Color.BLACK);
		fontComp.centering = true;
		entity.add(fontComp);

		TransformComponent transComp = new TransformComponent();
		transComp.body.initPosition(x, y, z + 1);
		entity.add(transComp);

		return entity;
	}

	static public Entity createButton(float x, float y, float z, float w, float h, ClickInterface clickInterface) {
		Entity entity = new Entity();

		NinepatchComponent nineComp = new NinepatchComponent();
		nineComp.patch = GameArt.whiteButtonBorder;
		nineComp.size.set(w, h);
		entity.add(nineComp);

		TransformComponent transComp = new TransformComponent();
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		ClickComponent clickComponent = new ClickComponent();
		clickComponent.clicker = clickInterface;
		clickComponent.active = true;
		clickComponent.shape = new Rectangle().setSize(w, h).setCenter(0.0f, 0.0f);
		entity.add(clickComponent);

		TextButtonComponent textButtonComponent = new TextButtonComponent();
		textButtonComponent.base.set(nineComp.color);
		textButtonComponent.pressed.set(Color.GREEN);
		entity.add(textButtonComponent);

		return entity;
	}

	public void addToEngine(Engine engine) {
		engine.addEntity(ninepatch);
		engine.addEntity(bitmapfont);
	}

	public void removeFromEngine(Engine engine) {
		engine.removeEntity(ninepatch);
		engine.removeEntity(bitmapfont);
	}
}