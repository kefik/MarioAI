package ch.idsia.benchmark.mario.engine.input;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Used by {@link IAgent} to represent the state of pressed key by the agent.
 * 
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public class MarioInput {

	private Set<MarioKey> justPressed = new TreeSet<MarioKey>(new Comparator<MarioKey>() {
		@Override
		public int compare(MarioKey o1, MarioKey o2) {
			return o1.getCode() - o2.getCode();
		}		
	});
	
	private Set<MarioKey> pressed = new TreeSet<MarioKey>(new Comparator<MarioKey>() {
		@Override
		public int compare(MarioKey o1, MarioKey o2) {
			return o1.getCode() - o2.getCode();
		}		
	});
	
	private Set<MarioKey> justReleased = new TreeSet<MarioKey>(new Comparator<MarioKey>() {
		@Override
		public int compare(MarioKey o1, MarioKey o2) {
			return o1.getCode() - o2.getCode();
		}		
	});
	
	/**
	 * New frame begins. Used to clear {@link #justPressed} and {@link #justReleased}.
	 */
	public void tick() {
		justPressed.clear();
		justReleased.clear();
	}
	
	public Set<MarioKey> getPressed() {
		return pressed;
	}
	
	/**
	 * Press or release given 'key'.
	 * @param key
	 * @param pressed true == PRESS, false == RELEASE
	 */
	public void set(MarioKey key, boolean pressed) {
		if (pressed) press(key);
		else release(key);
	}
	
	/**
	 * Change state of given 'key'.
	 * @param key
	 */
	public void toggle(MarioKey key) {
		if (pressed.contains(key)) pressed.remove(key);
		else pressed.add(key);
	}
	
	/**
	 * PRESS given 'key' or keep pressed if already pressed.
	 * @param key
	 */
	public void press(MarioKey key) {
		if (!pressed.contains(key)) {
			if (justReleased.contains(key)) {
				justReleased.remove(key);
			} else {
				justPressed.add(key);
			}
		}
		pressed.add(key);		
	}
	
	/**
	 * RELEASE given 'key'.
	 * @param key
	 */
	public void release(MarioKey key) {
		if (!pressed.contains(key)) return;
		pressed.remove(key);
		if (justPressed.contains(key)) {
			justPressed.remove(key);
		} else {
			justReleased.add(key);
		}
	}
	
	/**
	 * Whether 'key' is PRESSED.
	 * @param key
	 * @return
	 */
	public boolean isPressed(MarioKey key) {
		return pressed.contains(key);
	}
	
	/**
	 * Whether 'key' was NEWLY pressed THIS FRAME.
	 * @param key
	 * @return
	 */
	public boolean isJustPressed(MarioKey key) {
		return justPressed.contains(key);
	}
	
	/**
	 * Whether 'key' was RELEASED THIS FRAME.
	 * @param key
	 * @return
	 */
	public boolean isJustReleased(MarioKey key) {
		return justReleased.contains(key);
	}

	/**
	 * Completely resets the instance.
	 */
	public void reset() {
		justPressed.clear();
		justReleased.clear();
		pressed.clear();
	}
	
}
