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

import ch.idsia.benchmark.mario.engine.sprites.Sprite;
import ch.idsia.utils.MarioLog;

/**
 * Created by IntelliJ IDEA. 
 * User: Sergey Karakovskiy, sergey@idsia.ch 
 * Date: Aug 5, 2009 
 * Time: 7:04:19 PM 
 * Package: ch.idsia.benchmark.mario.engine
 * 
 * Provides generalization of entities that can be placed over {@link Tile}.
 * 
 * TODO: investigate, seems like ad-hoc stuff for some competition or experiment, reconsider to cut it out completely...
 * 
 * @author Sergey Karakovskiy, sergey@idsia.ch
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public class EntityGeneralizer {

	public static EntityType generalize(byte el, int zLevel) {
		switch (zLevel) {
		case (0):
			switch (el) {
			// cancel irrelevant sprite codes
			case Sprite.KIND_COIN_ANIM:
			case Sprite.KIND_PARTICLE:
			case Sprite.KIND_SPARCLE:
			case Sprite.KIND_MARIO:
				return EntityType.NOTHING;
			case Sprite.KIND_FIRE_FLOWER:
				return EntityType.FIRE_FLOWER;
			case Sprite.KIND_MUSHROOM:
				return EntityType.MUSHROOM;
			case Sprite.KIND_FIREBALL:
				return EntityType.FIREBALL;
			case Sprite.KIND_BULLET_BILL:
				return EntityType.BULLET_BILL;
			case Sprite.KIND_GOOMBA:
				return EntityType.GOOMBA;
			case Sprite.KIND_GOOMBA_WINGED:
				return EntityType.GOOMBA_WINGED;
			case Sprite.KIND_GREEN_KOOPA:
				return EntityType.GREEN_KOOPA;
			case Sprite.KIND_GREEN_KOOPA_WINGED:
				return EntityType.GREEN_KOOPA_WINGED;
			case Sprite.KIND_RED_KOOPA:
				return EntityType.RED_KOOPA;
			case Sprite.KIND_RED_KOOPA_WINGED:
				return EntityType.RED_KOOPA_WINGED;
			case Sprite.KIND_SHELL:
				return EntityType.SHELL_STILL;
			case Sprite.KIND_WAVE_GOOMBA:
				return EntityType.WAVE_GOOMBA;
			case Sprite.KIND_SPIKY:
				return EntityType.SPIKY;
			case Sprite.KIND_ENEMY_FLOWER:
				return EntityType.ENEMY_FLOWER;				
			case Sprite.KIND_SPIKY_WINGED:
				return EntityType.SPIKY_WINGED;
			case Sprite.KIND_PRINCESS:
				return EntityType.PRINCESS;
			}
			MarioLog.error("EntityGeneralizer.generalize(el=" + el + ", zLevel=" + zLevel + "): Failed to interpret el = " + el + "! Returning SOMETHING!");
			return EntityType.SOMETHING;
		case (1):
			switch (el) {
			case Sprite.KIND_COIN_ANIM:
			case Sprite.KIND_PARTICLE:
			case Sprite.KIND_SPARCLE:
			case Sprite.KIND_MARIO:
				return EntityType.NOTHING;
			case Sprite.KIND_FIRE_FLOWER:
				return EntityType.FIRE_FLOWER;
			case Sprite.KIND_MUSHROOM:
				return EntityType.MUSHROOM;
			case Sprite.KIND_FIREBALL:
				return EntityType.FIREBALL;
			case Sprite.KIND_BULLET_BILL:
			case Sprite.KIND_GOOMBA:
			case Sprite.KIND_GOOMBA_WINGED:
			case Sprite.KIND_GREEN_KOOPA:
			case Sprite.KIND_GREEN_KOOPA_WINGED:
			case Sprite.KIND_RED_KOOPA:
			case Sprite.KIND_RED_KOOPA_WINGED:
			case Sprite.KIND_SHELL:
			case Sprite.KIND_WAVE_GOOMBA:
				return EntityType.DANGER;
			case Sprite.KIND_SPIKY:
			case Sprite.KIND_ENEMY_FLOWER:
			case Sprite.KIND_SPIKY_WINGED:
				return EntityType.SPIKY;
			case Sprite.KIND_PRINCESS:
				return EntityType.PRINCESS;
			}
			MarioLog.error("EntityGeneralizer.generalize(el=" + el + ", zLevel=" + zLevel + "): Failed to interpret el = " + el + "! Returning SOMETHING!");
			return EntityType.SOMETHING;
		case (2):
			switch (el) {
			case (Sprite.KIND_COIN_ANIM):
			case (Sprite.KIND_PARTICLE):
			case (Sprite.KIND_SPARCLE):
			case (Sprite.KIND_FIREBALL):
			case (Sprite.KIND_MARIO):
			case (Sprite.KIND_FIRE_FLOWER):
			case (Sprite.KIND_MUSHROOM):
				return EntityType.NOTHING;
			case (Sprite.KIND_BULLET_BILL):
			case (Sprite.KIND_GOOMBA):
			case (Sprite.KIND_GOOMBA_WINGED):
			case (Sprite.KIND_GREEN_KOOPA):
			case (Sprite.KIND_GREEN_KOOPA_WINGED):
			case (Sprite.KIND_RED_KOOPA):
			case (Sprite.KIND_RED_KOOPA_WINGED):
			case (Sprite.KIND_SHELL):
			case (Sprite.KIND_SPIKY):
			case (Sprite.KIND_ENEMY_FLOWER):
			case (Sprite.KIND_SPIKY_WINGED):
			case (Sprite.KIND_WAVE_GOOMBA):
				return EntityType.DANGER;
			case (Sprite.KIND_PRINCESS): 
				return EntityType.DANGER;
			
			}
			MarioLog.error("EntityGeneralizer.generalize(el=" + el + ", zLevel=" + zLevel + "): Failed to interpret el = " + el + "! Returning SOMETHING!");
			return EntityType.SOMETHING;
		}
		
		throw new RuntimeException("Invalid ZLevel[" + zLevel + "], an entity cannot be interpreted. Have you correctly set up your zLevel for entities in your agent?");
	}
}
