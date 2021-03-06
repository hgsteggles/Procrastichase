package hgs.tombstone.elements;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import hgs.tombstone.assets.GameArt;
import hgs.tombstone.components.*;

public class GameMenuIconSwitch {
	private Entity ninepatch, icon;

	public GameMenuIconSwitch(float x, float y, float z, float w, float h, TextureRegion region, ClickInterface clickInterface) {
		ninepatch = createButton(x, y, z, w, h);
		icon = createIcon(x, y, z, w, h, region, clickInterface);
	}

	public Entity getBorderEntity() {
		return ninepatch;
	}

	public Entity getIconEntity() {
		return icon;
	}

	private Entity createIcon(float x, float y, float z, float w, float h, TextureRegion region, ClickInterface clickInterface) {
		Entity entity = new Entity();

		TextureComponent texComp = new TextureComponent();
		texComp.region = region;
		texComp.size.x = w;
		texComp.size.y = h;
		entity.add(texComp);

		TransformComponent transComp = new TransformComponent();
		transComp.body.initPosition(x, y, z + 1);
		entity.add(transComp);

		ClickComponent clickComponent = new ClickComponent();
		clickComponent.clicker = clickInterface;
		clickComponent.active = true;
		clickComponent.shape = new Rectangle().setSize(w, h).setCenter(0.0f, 0.0f);
		entity.add(clickComponent);

		entity.add(new HeadsUpDisplayComponent());

		return entity;
	}

	static public Entity createButton(float x, float y, float z, float w, float h) {
		Entity entity = new Entity();

		NinepatchComponent nineComp = new NinepatchComponent();
		nineComp.patch = GameArt.whiteButtonBorder;
		nineComp.size.set(w, h);
		entity.add(nineComp);

		TransformComponent transComp = new TransformComponent();
		transComp.body.initPosition(x, y, z);
		entity.add(transComp);

		TextButtonComponent textButtonComponent = new TextButtonComponent();
		textButtonComponent.base.set(nineComp.color);
		textButtonComponent.pressed.set(Color.GREEN);
		entity.add(textButtonComponent);

		return entity;
	}

	public void addToEngine(Engine engine) {
		engine.addEntity(ninepatch);
		engine.addEntity(icon);
	}

	public void removeFromEngine(Engine engine) {
		engine.removeEntity(ninepatch);
		engine.removeEntity(icon);
	}
}