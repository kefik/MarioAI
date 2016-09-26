/*
 * Copyright (c) 2009-2010, Sergey Karakovskiy and Julian Togelius
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Mario AI nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package ch.idsia.agents.controllers.keyboard;

import java.awt.event.KeyEvent;

import ch.idsia.benchmark.mario.engine.SimulatorOptions;
import ch.idsia.benchmark.mario.engine.SimulatorOptions.ReceptiveFieldMode;
import ch.idsia.benchmark.mario.engine.input.MarioCheaterKeyboard;

/**
 * Created by IntelliJ IDEA. 
 * User: Sergey Karakovskiy 
 * Date: Apr 8, 2009 
 * Time: 3:36:16 AM 
 * Package: ch.idsia.controllers.agents.controllers;
 * 
 * @author Sergey Karakovskiy
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public class CheaterKeyboardAgent extends KeyboardAgent {

	public CheaterKeyboardAgent() {
		super("HumanCheatKeyboard", new MarioCheaterKeyboard());
	}
	
	@Override
	protected void toggleKey(KeyEvent e, boolean isPressed) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		// CHEATS!
		case KeyEvent.VK_D:
			if (isPressed) SimulatorOptions.gameViewerTick();
			return;
		case KeyEvent.VK_V:
			if (isPressed) SimulatorOptions.isVisualization = !SimulatorOptions.isVisualization;
			return;
		case KeyEvent.VK_O:
			if (isPressed) {
				SimulatorOptions.areFrozenCreatures = !SimulatorOptions.areFrozenCreatures;
			}
			return;
		case KeyEvent.VK_L:
			if (isPressed) SimulatorOptions.areLabels = !SimulatorOptions.areLabels;
			return;
		case KeyEvent.VK_C:
			if (isPressed) SimulatorOptions.isCameraCenteredOnMario = !SimulatorOptions.isCameraCenteredOnMario;
			return;
		case 61:
			if (isPressed) {
				++SimulatorOptions.FPS;
				SimulatorOptions.FPS = (SimulatorOptions.FPS > SimulatorOptions.MaxFPS ? SimulatorOptions.MaxFPS : SimulatorOptions.FPS);
				SimulatorOptions.AdjustMarioVisualComponentFPS();
			}
			return;
		case 45:
			if (isPressed) {
				--SimulatorOptions.FPS;
				SimulatorOptions.FPS = (SimulatorOptions.FPS < 1 ? 1 : SimulatorOptions.FPS);
				SimulatorOptions.AdjustMarioVisualComponentFPS();
			}
			return;
		case KeyEvent.VK_G:
			if (isPressed) {
				SimulatorOptions.receptiveFieldMode = ReceptiveFieldMode.getForCode(SimulatorOptions.receptiveFieldMode.getCode()+1);
			}
			return;
		case KeyEvent.VK_SPACE:
			if (isPressed) {
				SimulatorOptions.isGameplayStopped = !SimulatorOptions.isGameplayStopped;
			}
			return;
		case KeyEvent.VK_F:
			if (isPressed) {
				SimulatorOptions.isFly = !SimulatorOptions.isFly;
			}
			return;
		case KeyEvent.VK_Z:
			if (isPressed) {
				SimulatorOptions.changeScale2x();
			}
			return;
		case KeyEvent.VK_R:
			if (isPressed) {
				SimulatorOptions.isRecording = !SimulatorOptions.isRecording;
			}
			return;
		case KeyEvent.VK_N:
			if (isPressed) {
				SimulatorOptions.nextFrameIfPaused = true;
			}
			return;
		}
		// NOT HANDLED YET
		// => ask parent
		super.toggleKey(e, isPressed);
	}
}
