package mario;

import ch.idsia.benchmark.mario.engine.generalization.Enemy;
import ch.idsia.benchmark.mario.options.FastOpts;

public enum LevelConfig {
	
	/**
	 * Level with no threats + no jumps.
	 */
	LEVEL_0_FLAT(FastOpts.VIS_ON_2X + FastOpts.LEVEL_01_FLAT),
	
	/**
	 * Level where you have to jump.
	 */
	LEVEL_1_JUMPING(FastOpts.VIS_ON_2X + FastOpts.LEVEL_02_JUMPING),
	
	/**
	 * And here you must mind malicious GOOMBAs!
	 */
	LEVEL_2_GOOMBAS(FastOpts.VIS_ON_2X + FastOpts.LEVEL_02_JUMPING + FastOpts.L_ENEMY(Enemy.GOOMBA)),
	
	/**
	 * + Tubes with dangerous flowers. 
	 */
	LEVEL_3_TUBES(FastOpts.VIS_ON_2X + FastOpts.LEVEL_02_JUMPING + FastOpts.L_ENEMY(Enemy.GOOMBA) + FastOpts.L_TUBES_ON),
	
	/**
	 * Here we're adding SPIKIES! (Cannot be killed by fireballs...)
	 */
	LEVEL_4_SPIKIES(FastOpts.VIS_ON_2X + FastOpts.LEVEL_02_JUMPING + FastOpts.L_ENEMY(Enemy.GOOMBA, Enemy.SPIKY) + FastOpts.L_TUBES_ON),
	
	/**
	 * Finally, level with green tourtles (so called KOOPAs).
	 */
	LEVEL_5_SPIKIES(FastOpts.VIS_ON_2X + FastOpts.LEVEL_02_JUMPING + FastOpts.L_ENEMY(Enemy.GOOMBA, Enemy.SPIKY, Enemy.GREEN_KOOPA) + FastOpts.L_TUBES_ON);

	private String options;
	
	private LevelConfig(String options) {
		this.options = options;
	}
	
	public String getOptions() {
		return options;
	}
	
	public String getOptionsRandomized() {
		return options + FastOpts.L_RANDOMIZE;
	}
	
	public String getOptionsVisualizationOff() {
		return options + FastOpts.VIS_OFF;
	}
	
	public String getOptionsRndVissOff() {
		return options + FastOpts.VIS_OFF + FastOpts.L_RANDOMIZE;
	}
	
}
