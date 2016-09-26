package ch.idsia.benchmark.mario.engine.input;

import java.awt.event.KeyEvent;

public class MarioCheaterKeyboard extends MarioHumanKeyboard {
	
	public MarioCheaterKeyboard() {
		register(KeyEvent.VK_W, MarioCheatKey.CHEAT_KEY_WIN);
	}

}
