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

package ch.idsia.benchmark.mario.environments;

import java.util.List;

import ch.idsia.agents.IAgent;
import ch.idsia.benchmark.mario.engine.LevelScene;
import ch.idsia.benchmark.mario.engine.VisualizationComponent;
import ch.idsia.benchmark.mario.engine.Replayer;
import ch.idsia.benchmark.mario.engine.generalization.Entity;
import ch.idsia.benchmark.mario.engine.generalization.Tile;
import ch.idsia.benchmark.mario.engine.input.MarioInput;
import ch.idsia.benchmark.mario.engine.sprites.Mario;
import ch.idsia.benchmark.mario.engine.sprites.Mario.MarioMode;
import ch.idsia.benchmark.mario.options.MarioOptions;
import ch.idsia.tools.EvaluationInfo;

/**
 * Created by IntelliJ IDEA. 
 * User: Sergey Karakovskiy 
 * Date: Mar 28, 2009 
 * Time:
 * 8:51:57 PM Package: .Environments
 * 
 * @author Sergey Karakovskiy
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public interface IEnvironment {

	public static final int MARIO_STATUS_WIN = Mario.STATUS_WIN;
	public static final int MARIO_STATUS_DEAD = Mario.STATUS_DEAD;
	public static final int MARIO_STATUS_RUNNING = Mario.STATUS_RUNNING;

	/**
	 * Configures the {@link IEnvironment} with actual values stored within {@link MarioOptions},
	 * binds the environment with {@link IAgent} and {@link IAgent#reset(ch.idsia.agents.AgentOptions)}s it. 
	 * @param agent
	 */
	public void reset(IAgent agent);
	
	/**
	 * Reconfigures the {@link IEnvironment} with actual values stored within {@link MarioOptions} 
	 * while maintaining link to previously bound {@link IAgent} from previous {@link #reset(IAgent)} call.
	 * I.e., you cannot call this method if you haven't previously called {@link #reset(IAgent)} to bind environment
	 * with concrete agent.
	 */
	public void reset();

	/**
	 * Perform another simulator step.
	 */
	public void tick();
	
	/**
	 * Inform about actions an agent wants to perform.
	 * @param keys
	 */
	public void performAction(MarioInput keys);
	
	/**
	 * Current reward for the play so far.
	 * @return
	 */
	public int getIntermediateReward();

	/**
	 * Mario's body interface, contains egocentric information about Mario's body and current kill-counts.
	 * @return
	 */
	public ch.idsia.benchmark.mario.engine.generalization.MarioEntity getMario();
	
	// OBSERVATION

	public Tile[][] getTileField();

	public List<Entity>[][] getEntityField();	

	public List<Entity> getEntities();

	

	boolean isLevelFinished();

	EvaluationInfo getEvaluationInfo();

	IAgent getAgent();

	void setAgent(IAgent agent);

	

	

	public void closeRecorder();

	public void setReplayer(Replayer recorder);

	public int getTimeSpent();

	public byte[][] getScreenCapture();

	void saveLastRun(String filename);

	/**
	 * May be NULL in case we're running HEADLESS.
	 */
	VisualizationComponent getVisualization();
	
	LevelScene getLevelScene();
}
