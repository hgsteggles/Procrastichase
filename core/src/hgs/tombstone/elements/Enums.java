package hgs.tombstone.elements;

/**
 * Created by harry on 05/04/16.
 */
public class Enums {
	public enum PlayerState {
		IDLE(0), RUN(1), JUMP(2), SLIDE(3), DEAD(4);
		private final int val;
		PlayerState(int val) {
			this.val = val;
		}
		public int value() {
			return val;
		}
	}

	public enum PlayerType {
		FERNANDO, HARRY, JAKE, KARIM;
	}

	public enum CollisionType {
		NONE, PLAYER, OBJECT, PICKUP, ENEMY, PLAYER_BULLET, ENEMY_BULLET, BOSS_AREA,
		MINI_BOSS_AREA, PEE;
	}

	public enum BossState {
		PRIME(0), THROW(1), ENTER(2);
		private final int val;
		private BossState(int val) {
			this.val = val;
		}
		public int value() {
			return val;
		}
	}

	public enum BossType {
		MELVIN, JULIAN, RENE, SVEN, ENDLESS, STUART, TOM;
	}

	public enum MiniBossState {
		PRIME(0), THROW(1);
		private final int val;
		private MiniBossState(int val) {
			this.val = val;
		}
		public int value() {
			return val;
		}
	}

	public enum MiniBossType {
		IVA, ROB, GREG, MARC;
	}

	public enum GameState {
		RUNNING, BOSS_CUTSCENE, BOSS_FIGHT, MINI_BOSS,
		CHAPTER_COMPLETE, GAME_COMPLETE, DEAD;
	}

	public enum ScreenType {

	}

	public enum BulletDirection {
		STRAIGHT, UP, DOWN;
	}

	public enum BulletType {
		RAINBOW, PASTY, SCIMITAR, PIZZA,
		SAR, JUGGLINGBALL, SPEECH_UP, SPEECH_DOWN,
		BEER, MILK, FIREBALL, JUGGLINGPIN,
		TRON_UP, TRON_DOWN,
		TRON_SQUARE, TRON_WAVE, TRON_SAWTOOTH,
		TRON_DELAY,
		TRON_CONNECT, TRON_BREAK, TRON_DISABLE,
	}

	public enum BulletCreateType {
		RAINBOW, SAR, SPEECH, JUGGLINGBALL, MILK, BEER, FIREBALL, JUGGLINGPIN, PASTY, LIGHTNING, SCIMITAR, TRON, RANDOM, ENDLESS;
	}

	public enum BulletDirectionStyle {
		ALL(3), LOWER(2), SPLIT(1), UPPER(0);
		private final int val;
		BulletDirectionStyle(int val) {
			this.val = val;
		}
		public int value() {
			return val;
		}
	}

	public enum MusicType {
		RUNNING, BOSS;
	}

	public enum MusicControlType {
		NORMAL, FADEIN, FADEOUT
	}

	public enum ControlType {
		JUMP, SLIDE, FIRE
	}
}
