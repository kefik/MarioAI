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
import ch.idsia.benchmark.mario.options.MarioOptions.IntOption;
import ch.idsia.benchmark.mario.options.MarioOptions.StringOption;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. 
 * User: Sergey Karakovskiy 
 * Date: Apr 25, 2009 
 * Time: 9:05:20 AM 
 * Package: ch.idsia.tools
 * 
 * @author Sergey Karakovskiy
 * @version 1.0, Apr 25, 2009
 * @see ch.idsia.utils.ParameterContainer
 * @see ch.idsia.benchmark.mario.options.SimulationOptions
 * @since MarioAI0.1
 * 
 * Read AI values from {@link MarioOptions}.
 * 
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */

public final class AIOptions extends SimulationOptions {

	final private Point marioInitialPos = new Point();
	
	/**
	 * Auto-adjusts certain AI options.
	 * <br/><br/>
	 * Currently do not do anything, reserved for future use.
	 */
	public static void reset() {		
		SimulatorOptions.receptiveFieldWidth = getReceptiveFieldWidth();
		SimulatorOptions.receptiveFieldHeight = getReceptiveFieldHeight();
		if (getMarioEgoCol() == 9 && SimulatorOptions.receptiveFieldWidth != 19)
			SimulatorOptions.marioEgoCol = SimulatorOptions.receptiveFieldWidth / 2;
		else
			SimulatorOptions.marioEgoCol = getMarioEgoCol();
		if (getMarioEgoRow() == 9
				&& SimulatorOptions.receptiveFieldHeight != 19)
			SimulatorOptions.marioEgoRow = SimulatorOptions.receptiveFieldHeight / 2;
		else
			SimulatorOptions.marioEgoRow = getMarioEgoRow();


	}

	public static int getReceptiveFieldWidth() {
		return MarioOptions.getInstance().getInt(IntOption.AI_RECEPTIVE_FIELD_WIDTH);
	}
	
	public static int getReceptiveFieldHeight() {
		return MarioOptions.getInstance().getInt(IntOption.AI_RECEPTIVE_FIELD_HEIGHT);
	}
	
	public static int getMarioEgoCol() {
		return MarioOptions.getInstance().getInt(IntOption.AI_MARIO_EGO_COLUMN);
	}
	
	public static int getMarioEgoRow() {
		return MarioOptions.getInstance().getInt(IntOption.AI_MARIO_EGO_ROW);
	}
	
	public static int getTileGeneralizationZLevel() {
		return MarioOptions.getInstance().getInt(IntOption.AI_TILE_GENERALIZATION_ZLEVEL);
	}
	
	public static int getEntityGeneralizationZLevel() {
		return MarioOptions.getInstance().getInt(IntOption.AI_ENTITY_GENERALIZATION_ZLEVEL);
	}
	
	public static boolean isPunctualJudge() {
		return MarioOptions.getInstance().getBool(BoolOption.AI_PUNCTUAL_JUDGE);
	}
	
//	
//
//	public void setArgs(String argString) {
//		if (!"".equals(argString))
//			this.setArgs(argString.trim().split("\\s+"));
//		else
//			this.setArgs((String[]) null);
//	}
//
//	public String asString() {
//		return optionsAsString;
//	}
//
//	public void setArgs(String[] args) {
//		if (args != null) {
//			for (String s : args)
//				optionsAsString += s + " ";
//		}
//
//		this.setUpOptions(args);
//
//		if (isEcho()) {
//			this.printOptions(false);
//		}
//		SimulatorOptions.receptiveFieldWidth = getReceptiveFieldWidth();
//		SimulatorOptions.receptiveFieldHeight = getReceptiveFieldHeight();
//		if (getMarioEgoPosCol() == 9 && SimulatorOptions.receptiveFieldWidth != 19)
//			SimulatorOptions.marioEgoCol = SimulatorOptions.receptiveFieldWidth / 2;
//		else
//			SimulatorOptions.marioEgoCol = getMarioEgoPosCol();
//		if (getMarioEgoPosRow() == 9
//				&& SimulatorOptions.receptiveFieldHeight != 19)
//			SimulatorOptions.marioEgoRow = SimulatorOptions.receptiveFieldHeight / 2;
//		else
//			SimulatorOptions.marioEgoRow = getMarioEgoPosRow();
//
//		SimulatorOptions.VISUAL_COMPONENT_HEIGHT = getViewHeight();
//		SimulatorOptions.VISUAL_COMPONENT_WIDTH = getViewWidth();
//		SimulatorOptions.isShowReceptiveField = isReceptiveFieldVisualized();
//		SimulatorOptions.isGameplayStopped = isStopGamePlay();
//	}
//
//	public float getMarioGravity() {
//		// TODO: getMarioGravity, doublecheck if unit test is present and remove
//		// if fixed
//		return asFloat(getParameterValue("-mgr"));
//	}
//
//	public void setMarioGravity(float mgr) {
//		setParameterValue("-mgr", asString(mgr));
//	}
//
//	public float getWind() {
//		return asFloat(getParameterValue("-w"));
//	}
//
//	public void setWind(float wind) {
//		setParameterValue("-w", asString(wind));
//	}
//
//	public float getIce() {
//		return asFloat(getParameterValue("-ice"));
//	}
//
//	public void setIce(float ice) {
//		setParameterValue("-ice", asString(ice));
//	}
//
//	public float getCreaturesGravity() {
//		// TODO: getCreaturesGravity, same as for mgr
//		return asFloat(getParameterValue("-cgr"));
//	}
//
//	public int getViewWidth() {
//		return asInt(getParameterValue("-vw"));
//	}
//
//	public void setViewWidth(int width) {
//		setParameterValue("-vw", asString(width));
//	}
//
//	public int getViewHeight() {
//		return asInt(getParameterValue("-vh"));
//	}
//
//	public void setViewHeight(int height) {
//		setParameterValue("-vh", asString(height));
//	}
//
//	public void printOptions(boolean singleLine) {
//		System.out.println("\n[MarioAI] : Options have been set to:");
//		for (Map.Entry<String, String> el : optionsHashMap.entrySet())
//			if (singleLine)
//				System.out.print(el.getKey() + " " + el.getValue() + " ");
//			else
//				System.out.println(el.getKey() + " " + el.getValue() + " ");
//	}
//
//	public static AIOptions getOptionsByString(String argString) {
//		// TODO: verify validity of this method, add unit tests
//		if (CmdLineOptionsMapString.get(argString) == null) {
//			final AIOptions value = new AIOptions(argString.trim().split("\\s+"));
//			CmdLineOptionsMapString.put(argString, value);
//			return value;
//		}
//		return CmdLineOptionsMapString.get(argString);
//	}
//
//	public static AIOptions getDefaultOptions() {
//		return getOptionsByString("");
//	}
//
//	public boolean isToolsConfigurator() {
//		return asBoolean(getParameterValue("-tc"));
//	}
//
//	public boolean isGameViewer() {
//		return asBoolean(getParameterValue("-gv"));
//	}
//
//	public void setGameViewer(boolean gv) {
//		setParameterValue("-gv", asString(gv));
//	}
//
//	public boolean isGameViewerContinuousUpdates() {
//		return asBoolean(getParameterValue("-gvc"));
//	}
//
//	public void setGameViewerContinuousUpdates(boolean gvc) {
//		setParameterValue("-gvc", asString(gvc));
//	}
//
//	public boolean isEcho() {
//		return asBoolean(getParameterValue("-echo"));
//	}
//
//	public void setEcho(boolean echo) {
//		setParameterValue("-echo", asString(echo));
//	}
//
//	public boolean isStopGamePlay() {
//		return asBoolean(getParameterValue("-stop"));
//	}
//
//	public void setStopGamePlay(boolean stop) {
//		setParameterValue("-stop", asString(stop));
//	}
//
//	public float getJumpPower() {
//		return asFloat(getParameterValue("-jp"));
//	}
//
//	public void setJumpPower(float jp) {
//		setParameterValue("-jp", asString(jp));
//	}
//
//	public int getReceptiveFieldWidth() {
//		int ret = asInt(getParameterValue("-rfw"));
//		//
//		// if (ret % 2 == 0)
//		// {
//		// System.err.println("\n[MarioAI WARNING] : Wrong value for receptive field width: "
//		// + ret++ +
//		// " ; receptive field width set to " + ret);
//		// setParameterValue("-rfw", s(ret));
//		// }
//		return ret;
//	}
//
//	public void setReceptiveFieldWidth(int rfw) {
//		setParameterValue("-rfw", asString(rfw));
//	}
//
//	public int getReceptiveFieldHeight() {
//		int ret = asInt(getParameterValue("-rfh"));
//		// if (ret % 2 == 0)
//		// {
//		// System.err.println("\n[MarioAI WARNING] : Wrong value for receptive field height: "
//		// + ret++ +
//		// " ; receptive field height set to " + ret);
//		// setParameterValue("-rfh", s(ret));
//		// }
//		return ret;
//	}
//
//	public void setReceptiveFieldHeight(int rfh) {
//		setParameterValue("-rfh", asString(rfh));
//	}
//
//	public boolean isReceptiveFieldVisualized() {
//		return asBoolean(getParameterValue("-srf"));
//	}
//
//	public void setReceptiveFieldVisualized(boolean srf) {
//		setParameterValue("-srf", asString(srf));
//	}
//
//	public Point getMarioInitialPos() {
//		marioInitialPos.x = asInt(getParameterValue("-mix"));
//		marioInitialPos.y = asInt(getParameterValue("-miy"));
//		return marioInitialPos;
//	}
//
//	public void reset() {
//		optionsHashMap.clear();
//	}
//
//	public int getMarioEgoPosRow() {
//		return asInt(getParameterValue("-mer"));
//	}
//
//	public int getMarioEgoPosCol() {
//		return asInt(getParameterValue("-mec"));
//	}
//
//	public int getExitX() {
//		return asInt(getParameterValue("-ex"));
//	}
//
//	public int getExitY() {
//		return asInt(getParameterValue("-ey"));
//	}
//
//	public void setExitX(int x) {
//		setParameterValue("-ex", asString(x));
//	}
//
//	public void setExitY(int y) {
//		setParameterValue("-ey", asString(y));
//	}
}