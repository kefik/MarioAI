package ch.idsia.benchmark.mario.engine.generalization;

import ch.idsia.benchmark.mario.engine.sprites.Sprite;

public enum EntityType {

	/**
	 * ZLevels: 0, 1, 2
	 * 
	 * No entity at this spot.
	 */
	NOTHING("", Sprite.KIND_NONE, 0, 1, 2),
	
	/**
	 * ZLevels: 0, 1
	 * 
	 * Collectible fire flower.
	 */
	FIRE_FLOWER("FF", Sprite.KIND_FIRE_FLOWER, 0, 1),
	
	/**
	 * ZLevels: 0, 1
	 * 
	 * Collectible mushroom!
	 */
	MUSHROOM("M", Sprite.KIND_MUSHROOM, 0, 1),
	
	/**
	 * ZLevels: 0, 1
	 * 
	 * Something with spikes! Don't touch this!
	 * 
	 * {@link Sprite#KIND_SPIKY} or {@link Sprite#KIND_ENEMY_FLOWER} or {@link Sprite#KIND_SPIKY_WINGED}
	 */
	SPIKES("S", Sprite.KIND_SPIKY, 0, 1), 
	
	/**
	 * ZLevels: 0, 1
	 * 
	 * {@link Sprite#KIND_FIREBALL}
	 */
	FIREBALL("FB", Sprite.KIND_FIREBALL, 0, 1),
	
	/**
	 * ZLevels: 0
	 * 
	 * {@link Sprite#KIND_BULLET_BILL}
	 */
	BULLET_BILL("BB", Sprite.KIND_BULLET_BILL, 0),
	
	/**
	 * ZLevels: 0
	 * 
	 * {@link Sprite#KIND_GOOMBA}
	 */
	GOOMBA("G", Sprite.KIND_GOOMBA, 0),
	
	/**
	 * ZLevels: 0
	 * 
	 * {@link Sprite#KIND_GOOMBA_WINGED
	 */
	GOOMBA_WINGED("GW", Sprite.KIND_GOOMBA_WINGED, 0),
	
	/**
	 * ZLevels: 0
	 * 
	 * {@link Sprite#KIND_GREEN_KOOPA
	 */
	GREEN_KOOPA("GK", Sprite.KIND_GREEN_KOOPA, 0),
	
	/**
	 * ZLevels: 0
	 * 
	 * {@link Sprite#KIND_GREEN_KOOPA_WINGED
	 */
	GREEN_KOOPA_WINGED("KW", Sprite.KIND_GREEN_KOOPA_WINGED, 0),
	
	/**
	 * ZLevels: 0
	 * 
	 * {@link Sprite#KIND_RED_KOOPA
	 */
	RED_KOOPA("RK", Sprite.KIND_RED_KOOPA, 0),
	
	/**
	 * ZLevels: 0
	 * 
	 * {@link Sprite#KIND_RED_KOOPA_WINGED
	 */
	RED_KOOPA_WINGED("RW", Sprite.KIND_RED_KOOPA_WINGED, 0),
	
	/**
	 * ZLevels: 0
	 * 
	 * {@link Sprite#KIND_SHELL}
	 */
	SHELL("SH", Sprite.KIND_SHELL, 0),
	
	/**
	 * ZLevels: 0
	 * 
	 * {@link Sprite#KIND_WAVE_GOOMBA}
	 */
	WAVE_GOOMBA("WG", Sprite.KIND_WAVE_GOOMBA, 0),
	
	/**
	 * ZLevels: 1
	 * 
	 * Something dangerous!
	 * 
	 * {@link Sprite#KIND_BULLET_BILL},
	 * {@link Sprite#KIND_GOOMBA}, {@link Sprite#KIND_GOOMBA_WINGED}, 
	 * {@link Sprite#KIND_GREEN_KOOPA}, {@link Sprite#KIND_GREEN_KOOPA_WINGED},
	 * {@link Sprite#KIND_RED_KOOPA}, {@link Sprite#KIND_RED_KOOPA_WINGED},
	 * {@link Sprite#KIND_SHELL}, {@link Sprite#KIND_WAVE_GOOMBA}
	 */
	DANGER("D!", Sprite.KIND_GOOMBA, 1),
	
	/**
	 * ZLevels: 0
	 * 
	 * {@link Sprite#KIND_SPIKY}.
	 */
	SPIKY("SP", Sprite.KIND_SPIKY, 0),
	
	/**
	 * ZLevels: 0
	 * 
	 * {@link Sprite#KIND_ENEMY_FLOWER}
	 */
	ENEMY_FLOWER("EF", Sprite.KIND_ENEMY_FLOWER, 0),
	
	/**
	 * ZLevels: 0
	 * 
	 * {@link Sprite#KIND_SPIKY_WINGED}
	 */
	SPIKY_WINGED("SW", Sprite.KIND_SPIKY_WINGED, 0),
	
	/**
	 * ZLevels: 0, 1, 2
	 * 
	 * {@link Sprite#KIND_PRINCESS}
	 */
	PRINCESS("P", Sprite.KIND_PRINCESS, 0, 1, 2),
	
	/**
	 * ZLevels: 0, 1, 2
	 * 
	 * {@link Sprite#KIND_MARIO}
	 */
	MARIO("M", Sprite.KIND_MARIO, 0, 1, 2),
	
	/**
	 * ZLevels: 0 (received in this level means BUG), 1 (received in this level means BUG), 2
	 * 
	 * Some entity... ???
	 */
	SOMETHING("?", 1, 0, 1, 2);
		
	private String debug;
	
	private int code;

	private int[] zLevels;
	
	private EntityType(String debug, int code, int... zLevels) {
		this.debug = debug;
		this.code = code;
		this.zLevels = zLevels;		
	}
	
	public String getDebug() {
		return debug;
	}

	public int getCode() {
		return code;
	}
	
	public int[] getZLevels() {
		return zLevels;
	}
	
	public boolean isZLevel(int zLevel) {
		for (int level : zLevels) {
			if (zLevel == level) return true;
		}
		return false;
	}
	
}
