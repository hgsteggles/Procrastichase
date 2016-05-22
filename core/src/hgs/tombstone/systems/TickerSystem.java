package hgs.tombstone.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import hgs.tombstone.components.ComponentMappers;
import hgs.tombstone.components.TickerComponent;

public class TickerSystem extends IteratingSystem {
	public TickerSystem() {
		super(Family.all(TickerComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		TickerComponent tc = ComponentMappers.ticker.get(entity);

		if (tc.tickerActive) {
			tc.tickerTime += deltaTime;

			while (tc.tickerTime >= tc.interval) {
				tc.tickerTime -= tc.interval;

				if (tc.ticker != null) {
					tc.ticker.dispatchEvent(entity);
				}
			}

		}

		if (tc.finishActive) {
			tc.totalTime += deltaTime;

			if (tc.totalTime >= tc.duration) {
				tc.tickerActive = false;
				tc.finishActive = false;

				if (tc.finish != null) {
					tc.finish.dispatchEvent(entity);
				}
			}
		}
	}
}
