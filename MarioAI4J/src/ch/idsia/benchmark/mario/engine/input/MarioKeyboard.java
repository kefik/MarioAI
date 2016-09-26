package ch.idsia.benchmark.mario.engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Represents configurable keyboard. Keyboard is configured via {@link #register(int, MarioKey)},
 * than it acts as {@link KeyListener}.
 *  
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public class MarioKeyboard implements KeyListener {

	private Map<Integer, MarioKey> mapping = new HashMap<Integer, MarioKey>();
	
	private Set<MarioKey> keys = new TreeSet<MarioKey>(new Comparator<MarioKey>() {
		@Override
		public int compare(MarioKey o1, MarioKey o2) {
			return o1.getCode() - o2.getCode();
		}		
	});
	
	private MarioInput input = new MarioInput();
	
	public void register(int keyboardCode, MarioKey key) {
		mapping.put(keyboardCode, key);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		MarioKey key = mapping.get(e.getKeyCode());
		if (key != null) input.press(key);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		MarioKey key = mapping.get(e.getKeyCode());
		if (key != null) input.release(key);
	}

	public void reset() {
		input.reset();		
	}

	public MarioInput getInput() {
		return input;
	}
	
}
