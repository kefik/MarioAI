package ch.idsia.benchmark.mario.engine.input;

import java.awt.event.KeyEvent;

public class MarioHumanKeyboard extends MarioKeyboard {

	public MarioHumanKeyboard() {
		register(KeyEvent.VK_LEFT, MarioKey.LEFT);
		register(KeyEvent.VK_RIGHT, MarioKey.RIGHT);
		register(KeyEvent.VK_DOWN, MarioKey.DOWN);
		register(KeyEvent.VK_UP, MarioKey.UP);
		register(KeyEvent.VK_A, MarioKey.JUMP);
		register(KeyEvent.VK_S, MarioKey.SPEED);
	}
	
}
