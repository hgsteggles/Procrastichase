package hgs.tombstone.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import hgs.tombstone.components.*;
import hgs.tombstone.world.World;

/**
 * Created by harry on 03/04/16.
 */
public class PageCountSystem extends EntitySystem {
	private World world;
	private ImmutableArray<Entity> pagefonts;

	public PageCountSystem(World world) {
		this.world = world;
	}

	@Override
	public void addedToEngine(Engine engine) {
		pagefonts = engine.getEntitiesFor(Family.all(PageCountComponent.class, BitmapFontComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		if (pagefonts.size() > 0) {
			Entity pageFont = pagefonts.get(0);

			BitmapFontComponent fontComp = ComponentMappers.bitmapfont.get(pageFont);
			fontComp.string = Integer.toString(world.getNumberPages());
		}
	}

}
