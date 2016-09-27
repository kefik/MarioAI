package ch.idsia.benchmark.mario.engine.input;

import ch.idsia.benchmark.mario.engine.generalization.MarioEntity;

/**
 * {@link MarioInput} wrapper that abstracts Mario movement into callable methods. It hides or articulates 
 * some intricacies of Mario controls (e.g. sprint is bound to the same key as shooting, etc.). 
 * 
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public class MarioControl {

	private MarioEntity mario;
	
	private MarioInput input;
	
	private int frame;
	
	private boolean shooting;
	
	private int lastFrameShoot;

	public MarioControl(MarioInput input) {
		this.input = input;
	}
	
	public void setMario(MarioEntity mario) {
		this.mario = mario;
	}
	
	public void tick() {
		++frame;
		if (shooting) {
			if (lastFrameShoot + 1 == frame) {
				// WE STILL WANT TO SHOOT
				if (input.isPressed(MarioKey.SPEED)) input.release(MarioKey.SPEED);
				else input.press(MarioKey.SPEED);
			} else {
				// shoot() WAS NOT CALLED LAST FRAME
				shooting = false;
				input.release(MarioKey.SPEED);
			}
		}
		input.release(MarioKey.LEFT);
		input.release(MarioKey.RIGHT);
		input.release(MarioKey.JUMP);
	}
	
	public void reset() {
		frame = 0;
		lastFrameShoot = 0;
		shooting = false;
		input.reset();
	}
	
	public void shoot() {
		lastFrameShoot = frame;
		if (shooting) return;
		shooting = true;
		input.press(MarioKey.SPEED);
	}
	
	public void sprint() {
		if (shooting) {
			// CANNOT SPRINT IF SHOOTING
			// => we would not be shooting at all!
			return; 
		}
		shooting = false;
		input.press(MarioKey.SPEED);
	}
	
	public void jump() {
		if (!mario.mayJump && mario.speed.y >= 0) return;
		input.press(MarioKey.JUMP);
	}
	
	public void runLeft() {
		input.press(MarioKey.LEFT);
		input.release(MarioKey.RIGHT);
	}
	
	public void runRight() {
		input.press(MarioKey.RIGHT);
		input.release(MarioKey.LEFT);
	}
	
}
