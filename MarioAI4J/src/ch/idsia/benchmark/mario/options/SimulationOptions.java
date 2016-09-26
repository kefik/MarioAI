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

package ch.idsia.benchmark.mario.options;

import ch.idsia.benchmark.mario.engine.SimulatorOptions;
import ch.idsia.benchmark.mario.options.MarioOptions.BoolOption;
import ch.idsia.benchmark.mario.options.MarioOptions.FloatOption;
import ch.idsia.benchmark.mario.options.MarioOptions.IntOption;

/**
 * Created by IntelliJ IDEA. 
 * User: Sergey Karakovskiy 
 * Date: Apr 12, 2009 
 * Time: 9:55:56 PM 
 * Package: .Simulation
 * 
 * Read Simulation values from {@link MarioOptions}.
 * 
 * @author Sergey Karakovskiy
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public class SimulationOptions {
	//final Point viewLocation = new Point(42, 42);
	
	/**
	 * Auto-adjusts certain SIMULATION options.
	 * <br/><br/>
	 * Currently do not do anything, reserved for future use.
	 */
	public static void reset() {
		SimulatorOptions.isPowerRestoration = isPowerRestoration();
		SimulatorOptions.isGameplayStopped = isGameplayStopped();
		SimulatorOptions.nextFrameIfPaused = false;
	}
	
	public static boolean isCreaturesFrozen() {
		return MarioOptions.getInstance().getBool(BoolOption.SIMULATION_FROZEN_CREATURES);
	}
	
	public static boolean isMarioInvulnerable() {
		return MarioOptions.getInstance().getBool(BoolOption.SIMULATION_MARIO_INVULNERABLE);
	}
	
	public static boolean isPowerRestoration() {
		return MarioOptions.getInstance().getBool(BoolOption.SIMULATION_POWER_RESTORATION);
	}
	
	public static boolean isGameplayStopped() {
		return MarioOptions.getInstance().getBool(BoolOption.SIMULATION_GAMEPLAY_STOPPED);
	}
	
	public static float getMarioJumpPower() {
		return MarioOptions.getInstance().getFloat(FloatOption.SIMULATION_MARIO_JUMP_POWER);
	}
	
	public static int getMarioStartMode() {
		return MarioOptions.getInstance().getInt(IntOption.SIMULATION_MARIO_START_MODE);
	}
	
	public static int getTimeLimit() {
		return MarioOptions.getInstance().getInt(IntOption.SIMULATION_TIME_LIMIT);
	}
	
	public static float getGravityCreatures() {
		return MarioOptions.getInstance().getFloat(FloatOption.SIMULATION_GRAVITY_CREATURES);
	}
	
	public static float getGravityMario() {
		return MarioOptions.getInstance().getFloat(FloatOption.SIMULATION_GRAVITY_MARIO);
	}
	
	public static float getWindCreatures() {
		return MarioOptions.getInstance().getFloat(FloatOption.SIMULATION_WIND_CREATURES);
	}
	
	public static float getWindMario() {
		return MarioOptions.getInstance().getFloat(FloatOption.SIMULATION_WIND_MARIO);
	}
	
}
