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

package ch.idsia.benchmark.mario.engine.generalization;

import ch.idsia.utils.MarioLog;

/**
 * Created by IntelliJ IDEA. 
 * User: Sergey Karakovskiy, sergey@idsia.ch 
 * Date: Aug 5, 2009 
 * Time: 7:05:46 PM 
 * Package: ch.idsia.benchmark.mario.engine
 * 
 * Generalizer that provides abstracts over "background tile", this includes {@link Tile#COIN_ANIM} as well because there can be an enemy in front of it!
 * 
 * @author Sergey Karakovskiy, sergey@idsia.ch
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public class TileGeneralizer {

	/**
	 * Generalize tile type 'el' according to the generalization 'zLevel'. 
	 * 
	 * @param el tile type as used by the renderer
	 * @param zLevel required generalization level, possible values 0 (slight generalization), 1 (some generalization), 2 (over-generalized)
	 * @return
	 */
	public static Tile generalize(byte el, int zLevel) {
		if (el == 0)
			return Tile.NOTHING;
		switch (zLevel) {
		case (0):
			switch (el) {
			case 16: // brick, simple, without any surprise.
			case 17: // brick with a hidden coin
			case 18: // brick with a hidden friendly flower
				return Tile.BREAKABLE_BRICK;
			case 21: // question brick, contains coin
			case 22: // question brick, contains flower/mushroom
			case 23: // question brick, N coins inside. prevents cheating
				return Tile.QUESTION_BRICK; // question brick, contains something
			case 34:
				return Tile.COIN_ANIM;
			case 4:
			case -109:
			case -110:
			case -111:
			case -112:
			case -125:
			case -126:
			case -127:	
			case -128:
				return Tile.BORDER_CANNOT_PASS_THROUGH;
			case 14:
				return Tile.CANNON_MUZZLE;
			case 30:
			case 46:
				return Tile.CANNON_TRUNK;
			case 10:
			case 11:
			case 26:
			case 27:
				return Tile.FLOWER_POT;
			case 1:
				return Tile.NOTHING; // hidden block
			case -124:
			case -123:
			case -122:
			case -76:
			case -74:
				return Tile.BORDER_HILL;
			case -108:
			case -107:
			case -106:
				return Tile.NOTHING; // background of the hill. empty space
			case (61):
				return Tile.LADDER;
			case (93):
				return Tile.TOP_OF_LADDER;
			case (-1):
				return Tile.PRINCESS;
			}
			return Tile.SOMETHING; // everything else is "something", so it is 1
		case (1):
			switch (el) {
			case 16: // brick, simple, without any surprise.
			case 17: // brick with a hidden coin
			case 18: // brick with a hidden flower
			case 21: // question brick, contains coin
			case 22: // question brick, contains flower/mushroom
			case 23: // question brick, N coins inside. prevents cheating
				return Tile.BRICK; // some brick
			case 1: // hidden block
			case (-108):
			case (-107):
			case (-106):
			case (15): // Sparkle, irrelevant
				return Tile.NOTHING;
			case (34):
				return Tile.COIN_ANIM;
			case (-128):
			case (-127):
			case (-126):
			case (-125):
			case (-120):
			case (-119):
			case (-118):
			case (-117):
			case (-116):
			case (-115):
			case (-114):
			case (-113):
			case (-112):
			case (-110):
			case (-109):
			case (-104):
			case (-103):
			case (-102):
			case (-101):
			case (-100):
			case (-99):
			case (-98):
			case (-97):
			case (-96):
			case (-95):
			case (-94):
			case (-93):
			case (-69):
			case (-65):
			case (-88):
			case (-87):
			case (-86):
			case (-85):
			case (-84):
			case (-83):
			case (-82):
			case (-81):
			case (-77):
			case (-111):
			case (4): // kicked hidden brick
			case (9):
				return Tile.BORDER_CANNOT_PASS_THROUGH; // border, cannot pass through, can stand on
			case (-124):
			case (-123):
			case (-122):
			case (-76):
			case (-74):
				return Tile.BORDER_HILL; // half-border, can jump through from bottom and can stand on
			case (10):
			case (11):
			case (26):
			case (27): // flower pot
			case (14):
			case (30):
			case (46): // canon
				return Tile.FLOWER_POT_OR_CANNON; // angry flower pot or cannon
			case (61):
				return Tile.LADDER;
			case (93):
				return Tile.TOP_OF_LADDER;
			case (-1):
				return Tile.PRINCESS;
			}
			MarioLog.error("GeneralizerLevelScene.generalize(el=" + el + ", zLevel=" + zLevel + "): Unknown value el = " + el + ". Possible Level tiles bug!");
			return Tile.SOMETHING; // everything else is "something", so it is 1
		case (2):
			switch (el) {
			// cancel out half-borders, that could be passed through
			case (0):
			case (-108):
			case (-107):
			case (-106):
			case 1: // hidden block
			case (15): // Sparcle, irrelevant
				return Tile.NOTHING;
			case (34): // coins
				return Tile.COIN_ANIM;
			case 16: // brick, simple, without any surprise.
			case 17: // brick with a hidden coin
			case 18: // brick with a hidden flower
			case 21: // question brick, contains coin
			case 22: // question brick, contains flower/mushroom
				// here bricks are any objects cannot jump through and can stand
				// on
			case 4: // kicked hidden block
			case 9:
			case (10):
			case (11):
			case (26):
			case (27): // flower pot
			case (14):
			case (30):
			case (46): // canon
				return Tile.BORDER_CANNOT_PASS_THROUGH;
			case (-1):
				return Tile.PRINCESS;
			}
			return Tile.SOMETHING; // everything else is "something", so it is 1
		}
		MarioLog.error("Unkown ZLevel Z" + zLevel);
		
		throw new RuntimeException("Invalid ZLevel[" + zLevel + "], a tile cannot be interpreted. Have you correctly set up your zLevel for the scene in your agent?");
	}
}
