package ch.idsia.benchmark.mario.engine.input;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class MarioInput {

	private Set<MarioKey> pressed = new TreeSet<MarioKey>(new Comparator<MarioKey>() {
		@Override
		public int compare(MarioKey o1, MarioKey o2) {
			return o1.getCode() - o2.getCode();
		}		
	});
	
	public Set<MarioKey> getPressed() {
		return pressed;
	}
	
	public void set(MarioKey key, boolean pressed) {
		if (pressed) press(key);
		else release(key);
	}
	
	public void toggle(MarioKey key) {
		if (pressed.contains(key)) pressed.remove(key);
		else pressed.add(key);
	}
	
	public void press(MarioKey key) {
		pressed.add(key);
	}
	
	public void release(MarioKey key) {
		pressed.remove(key);		
	}
	
	public boolean isPressed(MarioKey key) {
		return pressed.contains(key);
	}

	public void reset() {
		pressed.clear();
	}
	
}
