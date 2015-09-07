package ch.idsia.benchmark.mario.engine.generalization;

import ch.idsia.tools.RandomCreatureGenerator;

/**
 * Matches {@link RandomCreatureGenerator} implementation.
 * 
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public enum Enemy {
	
	GOOMBA("g"), 
	GOOMBA_WINGED("gw"), 
	RED_KOOPA("rk"), 
	RED_KOOPA_WINGED("rkw"), 
	GREEN_KOOPA("gk"), 
	GREEN_KOOPA_WINGED("gkw"), 
	SPIKY("s"),
	SPIKY_WINGED("sw"),
	WAVE_GOOMBA("gww");
	
	private String shorthand;
	
	private Enemy(String shorthand) {
		this.shorthand = shorthand;
	}

	public String getShorthand() {
		return shorthand;
	}
	
	public static Enemy getEnemy(String shorthand) {
		for (Enemy enemy : Enemy.values()) {
			if (enemy.getShorthand().equals(shorthand)) return enemy;
 		}
		return null;
	}
	
}
