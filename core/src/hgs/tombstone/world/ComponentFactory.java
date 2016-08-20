package hgs.tombstone.world;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import hgs.tombstone.components.*;
import hgs.tombstone.elements.Enums;
import hgs.tombstone.elements.GameParameters;

/**
 * Created by harry on 07/04/16.
 */
public class ComponentFactory {
	public static GunComponent createEndlessGun() {
		GunComponent gunComp = new GunComponent();
		gunComp.fireRate = 0.8f;

		gunComp.shootInterface = new GunInterface() {
			@Override
			public void shoot(Entity e, Engine eng) {
				TransformComponent tc = ComponentMappers.transform.get(e);
				GunComponent gc = ComponentMappers.gun.get(e);
				BossComponent bc = ComponentMappers.boss.get(e);
				EndlessComponent ec = ComponentMappers.endless.get(e);

				float bulletVelocityX = - gc.speed;

				Entity bullet = BulletFactory.createEndlessProjectile(ec.time,
						tc.body.getPosition().x, tc.body.getPosition().y, bulletVelocityX);

				eng.addEntity(bullet);
			}
		};

		return gunComp;
	}

	public static GunComponent createBossGun(Enums.BossType who) {
		GunComponent gunComp = new GunComponent();
		if (who == Enums.BossType.MELVIN)
			gunComp.fireRate = 1.0f;
		if (who == Enums.BossType.JULIAN)
			gunComp.fireRate = 1.4f;
		if (who == Enums.BossType.RENE)
			gunComp.fireRate = 1.5f;
		if (who == Enums.BossType.SVEN)
			gunComp.fireRate = 1.5f;

		gunComp.shootInterface = new GunInterface() {
			@Override
			public void shoot(Entity e, Engine eng) {
				TransformComponent tc = ComponentMappers.transform.get(e);
				GunComponent gc = ComponentMappers.gun.get(e);
				BossComponent bc = ComponentMappers.boss.get(e);

				float bulletVelocityX = - gc.speed;

				Entity bullet = BulletFactory.createBossProjectile(bc.who, tc.body.getPosition().x,
						tc.body.getPosition().y, bulletVelocityX);

				eng.addEntity(bullet);
			}
		};

		return gunComp;
	}

	public static GunComponent createPlayerGun() {
		GunComponent gunComp = new GunComponent();
		gunComp.fireRate = 1.0f;
		gunComp.shootInterface = new GunInterface() {
			@Override
			public void shoot(Entity e, Engine eng) {
				MovementComponent mc = ComponentMappers.movement.get(e);
				TransformComponent tc = ComponentMappers.transform.get(e);
				GunComponent gc = ComponentMappers.gun.get(e);
				PlayerComponent pc = ComponentMappers.player.get(e);
				StateComponent sc = ComponentMappers.state.get(e);

				float dir = Math.signum(mc.linearVelocity.x);
				if (dir == 0)
					dir = 1;
				float bulletVelocityX = mc.linearVelocity.x + dir * gc.speed;

				float y = tc.body.getPosition().y;
				if (sc.get() == Enums.PlayerState.SLIDE.value())
					y -= 0.25;

				eng.addEntity(BulletFactory.createPlayerProjectile(pc.character, tc.body.getPosition().x,
						y, bulletVelocityX));
			}
		};

		return gunComp;
	}

	public static GunComponent createMiniBossGun(Enums.MiniBossType who) {
		GunComponent gunComp = new GunComponent();
		if (who == Enums.MiniBossType.IVA)
			gunComp.fireRate = 0.8f;
		else if (who == Enums.MiniBossType.GREG)
			gunComp.fireRate = 1.0f;
		else if (who == Enums.MiniBossType.ROB)
			gunComp.fireRate = 1.0f;
		else
			gunComp.fireRate = 0.8f;

		gunComp.shootInterface = new GunInterface() {
			@Override
			public void shoot(Entity e, Engine eng) {
				MovementComponent mc = ComponentMappers.movement.get(e);
				TransformComponent tc = ComponentMappers.transform.get(e);
				GunComponent gc = ComponentMappers.gun.get(e);
				MiniBossComponent mbc = ComponentMappers.miniboss.get(e);

				float bulletVelocityX = 2.0f * (mc.linearVelocity.x - gc.speed);

				Entity bullet = BulletFactory.createMiniBossProjectile(mbc.who, tc.body.getPosition().x,
						tc.body.getPosition().y, bulletVelocityX);

				eng.addEntity(bullet);
			}
		};

		return gunComp;
	}
}
