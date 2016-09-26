package ch.idsia.benchmark.mario.engine.input;

import java.util.HashMap;
import java.util.Map;

import ch.idsia.benchmark.mario.engine.generalization.MarioEntity;

/**
 * Represents keys that {@link IAgent} may use via {@link MarioInput}.
 *  
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public class MarioKey {
	
	private static Map<Integer, MarioKey> marioKeys = new HashMap<Integer, MarioKey>();

	public static final int numberOfKeys = 6;

	/**
	 * Move LEFT
	 */
	public static final MarioKey LEFT  = new MarioKey(0, "LEFT", "<<");
	
	/**
	 * Move RIGHT
	 */
	public static final MarioKey RIGHT = new MarioKey(1, "RIGHT", ">>");
	
	/**
	 * Duck
	 */
	public static final MarioKey DOWN  = new MarioKey(2, "DOWN", "vv");
	
	/**
	 * Perform a JUMP. Beware that Mario must have flag {@link MarioEntity#mayJump} set to true.
	 * If it is false and you hold JUMP, you will never jump.
	 */
	public static final MarioKey JUMP  = new MarioKey(3, "JUMP", "JJ");
	
	/**
	 * Shoot or speed depending on the use. If you press SPEED form the first time,
	 * Mario will shoot fireball (iff {@link MarioEntity#mayShoot}). If you continue
	 * holding SPEED together with LEFT or RIGHT you will start sprinting. To shoot
	 * again you have to first release SPEED and then press it again (whenever {@link MarioEntity#mayShoot}).
	 * 
	 * Note that there can be 2 fireballs in game at once at max! If you shoot twice, you
	 * have to wait when 1 of fireballs die of (either killing something or getting
	 * out of camera viewport).
	 */
	public static final MarioKey SPEED = new MarioKey(4, "SPEED", "SS");

	/**
	 * Fly UP - Works only if you're flying (which is a cheat).
	 */
	public static final MarioKey UP    = new MarioKey(5, "UP", "^^");
	
	private int code;
	private String name;
	private String debug;

	public MarioKey(int code, String name, String debug) {
		marioKeys.put(code, this);
		this.name = name;
		this.code = code;
		this.debug = debug;
	}

	public String getName() {
		return name;
	}

	public int getCode() {
		return code;
	}

	public String getDebug() {
		return debug;
	}

	public static MarioKey getMarioKey(int keyCode) {
		return marioKeys.get(keyCode);
	}
	
	public String toString() {
		return name;
	}
	
}
