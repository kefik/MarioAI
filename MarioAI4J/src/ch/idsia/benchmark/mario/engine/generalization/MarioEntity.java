package ch.idsia.benchmark.mario.engine.generalization;

import ch.idsia.benchmark.mario.engine.sprites.Mario;
import ch.idsia.benchmark.mario.engine.sprites.Mario.MarioMode;

public class MarioEntity extends Entity<Mario> {
	
	/**
	 * READ-ONLY
	 */
	public int receptiveFieldWidth;
	
	/**
	 * READ-ONLY
	 */
	public int receptiveFieldHeight;
		
	/**
	 * READ-ONLY
	 */
	public int zLevelTiles;
	
	/**
	 * READ-ONLY
	 */
	public int zLevelEntities;

	/**
	 * READ-ONLY
	 */
	public int egoRow;
	
	/**
	 * READ-ONLY
	 */
	public int egoCol;
		
	/**
	 * Array filled with data about Mario:
	 *         marioState[0]  = this.status; 
	 *         marioState[1]  = this.mode.getCode();
	 *         marioState[2]  = this.onGround ? 1 : 0; 
	 *         marioState[3]  = this.mayJump ? 1 : 0; 
	 *         marioState[4]  = this.mayShoot ? 1 : 0; 
	 *         marioState[5]  = this.carrying ? 1 : 0; 
	 *         marioState[6]  = this.killsTotal; 
	 *         marioState[7]  = this.killsByFire;
	 *         marioState[8]  = this.killsByStomp;
	 *         marioState[9]  = this.getKillsByStomp(); 
	 *         marioState[10] = this.killsByShell;
	 *         marioState[11] = this.timeLeft;
	 *         
	 * READ-ONLY
	 */
	public int[] state = new int[11];
	
	public int status;
	
	public MarioMode mode;
	
	public boolean onGround;
	
	/**
	 * Note that if you press JUMP key while !mayJump, Mario won't jump
	 * and the flag will remain dropped so it will stuck Mario!
	 */
	public boolean mayJump;
	public boolean carrying;
	public boolean mayShoot;
		
	public int killsTotal;
	public int killsByFire;
	public int killsByStomp;
	public int killsByShell;	
	
	public int timeLeft;
	public int timeSpent;

	public MarioEntity() {
		super(null, EntityType.MARIO, 0, 0, 0, 0, 0);
	}

}
