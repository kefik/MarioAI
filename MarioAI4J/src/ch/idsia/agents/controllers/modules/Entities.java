package ch.idsia.agents.controllers.modules;

import java.util.ArrayList;
import java.util.List;

import ch.idsia.agents.AgentOptions;
import ch.idsia.benchmark.mario.engine.generalization.Entity;
import ch.idsia.benchmark.mario.engine.generalization.EntityType;
import ch.idsia.benchmark.mario.engine.generalization.MarioEntity;

public class Entities {

	private MarioEntity mario;
	
	/**
	 * 2-dimensional array representing entities.
	 * 
	 * Entities are stored in [row][col] manner.
	 * 
	 * Mario is at [marioEgoRow][marioEgoCol].
	 * 
	 * The array is {@link #receptiveFieldHeight} x {@link #receptiveFieldWidth} long.
	 */
	public List<Entity>[][] entityField;
	
	/**
	 * List of all {@link Entity}ies that are within receptive field of Mario.
	 */
	public List<Entity> entities;
	
	public void reset(AgentOptions options) {
		this.mario = options.mario;
	}
	
	protected EntityType getEntityType(int mapX, int mapY) {
		if (mapY < 0 || mapY >= entityField.length || mapX < 0 || mapX >= entityField[0].length) return EntityType.NOTHING;
		if (entityField[mapY][mapX].size() > 0) {
			return entityField[mapY][mapX].get(0).type;
		} else {
			return EntityType.NOTHING;
		}
	}
	
	protected List<Entity> getEntities(int mapX, int mapY) {
		if (mapY < 0 || mapY >= entityField.length || mapX < 0 || mapX >= entityField[0].length) return new ArrayList<Entity>();
		return entityField[mapY][mapX];
	}
	
	public EntityType entityType(int relMapX, int relMapY) {
		return getEntityType(mario.egoCol + relMapX,mario.egoRow + relMapY);
	}
	
	public List<Entity> entities(int relMapX, int relMapY) {
		return getEntities(mario.egoCol + relMapX, mario.egoRow + relMapY);
	}
	
	public boolean anything(int relMapX, int relMapY) {
		return entities(relMapX, relMapY).size() > 0;
	}
	
	public boolean nothing(int relMapX, int relMapY) {
		return entities(relMapX, relMapY).size() == 0;
	}
	
	public boolean danger(int relMapX, int relMapY) {
		if (nothing(relMapX, relMapY)) return false;
		for (Entity entity : entities(relMapX, relMapY)) {
			switch (entity.type) {
			case DANGER:
			case BULLET_BILL:
			case ENEMY_FLOWER:
			case GOOMBA:
			case GOOMBA_WINGED:
			case RED_KOOPA:
			case RED_KOOPA_WINGED:
			case SPIKES:
			case SPIKY:
			case SPIKY_WINGED:
			case WAVE_GOOMBA:
				return true;
			case SHELL:
				return !entity.speed.isZero();
			}
		}
		return false;
	}
	
	public boolean squishy(int relMapX, int relMapY) {
		if (nothing(relMapX, relMapY)) return false;
		for (Entity entity : entities(relMapX, relMapY)) {
			switch (entity.type) {
			case GOOMBA:
			case RED_KOOPA:
			case RED_KOOPA_WINGED:
			case GREEN_KOOPA_WINGED:
			case GREEN_KOOPA:
			case WAVE_GOOMBA:
				return true;
			}
		}	
		return false;
	}
	
	public boolean creature(int relMapX, int relMapY) {
		if (nothing(relMapX, relMapY)) return false;
		for (Entity entity : entities(relMapX, relMapY)) {
			switch (entity.type) {
			case GOOMBA:
			case RED_KOOPA:
			case RED_KOOPA_WINGED:
			case GREEN_KOOPA_WINGED:
			case GREEN_KOOPA:
			case SPIKY:
			case SPIKY_WINGED:
			case WAVE_GOOMBA:
				return true;
			}
		}		
		return false;
	}
	
	public boolean shellStill(int relMapX, int relMapY) {
		if (nothing(relMapX, relMapY)) return false;
		for (Entity entity : entities(relMapX, relMapY)) {
			switch (entity.type) {
			case SHELL:
				return entity.speed.isZero();
			}
		}		
		return false;
	}
	
	public boolean shellMoving(int relMapX, int relMapY) {
		if (nothing(relMapX, relMapY)) return false;
		for (Entity entity : entities(relMapX, relMapY)) {
			switch (entity.type) {
			case SHELL:
				return !entity.speed.isZero();
			}
		}		
		return false;
	}
	
}
