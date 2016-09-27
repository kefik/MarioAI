package ch.idsia.agents.controllers.modules;

import java.util.ArrayList;
import java.util.List;

import ch.idsia.agents.AgentOptions;
import ch.idsia.benchmark.mario.engine.generalization.Entity;
import ch.idsia.benchmark.mario.engine.generalization.EntityKind;
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
	
	/**
	 * Gets the most dangerous (lowest {@link EntityKind#getThreatLevel()}) entity type within receptive field on [mapX, mapY].
	 * @param mapX absolute receptive field tile x-coordinate
	 * @param mapY absolute receptive field tile y-coordinate
	 * @return
	 */
	protected EntityType getEntityType(int mapX, int mapY) {
		if (mapY < 0 || mapY >= entityField.length || mapX < 0 || mapX >= entityField[0].length) return EntityType.NOTHING;
		if (entityField[mapY][mapX].size() > 0) {
			// SEARCH FOR THE MOST DANGEROUS ONE
			Entity result = entityField[mapY][mapX].get(0);
			for (Entity entity : entityField[mapY][mapX]) {
				if (result.type.getKind().getThreatLevel() > entity.type.getKind().getThreatLevel()) result = entity;
			}
			return result.type;
		} else {
			return EntityType.NOTHING;
		}
	}
	
	/**
	 * Returns all entities within receptive field on [mapX, mapY].
	 * @param mapX absolute receptive field tile x-coordinate
	 * @param mapY absolute receptive field tile y-coordinate
	 * @return
	 */
	protected List<Entity> getEntities(int mapX, int mapY) {
		if (entityField == null) return new ArrayList<Entity>();
		if (mapY < 0 || mapY >= entityField.length || mapX < 0 || mapX >= entityField[0].length) return new ArrayList<Entity>();
		return entityField[mapY][mapX];
	}
	
	/**
	 * Gets the most dangerous (lowest {@link EntityKind#getThreatLevel()}) entity type within receptive field relatively to Mario.
	 * @param relMapX
	 * @param relMapY
	 * @return
	 */
	public EntityType entityType(int relMapX, int relMapY) {
		return getEntityType(mario.egoCol + relMapX,mario.egoRow + relMapY);
	}
	
	/**
	 * Returns all entities within receptive field relatively to Mario.
	 * @param relMapX
	 * @param relMapY
	 * @return
	 */
	public List<Entity> entities(int relMapX, int relMapY) {
		return getEntities(mario.egoCol + relMapX, mario.egoRow + relMapY);
	}
	
	/**
	 * Is there something on the tile? (Relatively to Mario.)
	 * @param relMapX
	 * @param relMapY
	 * @return
	 */
	public boolean anything(int relMapX, int relMapY) {
		return entities(relMapX, relMapY).size() > 0;
	}
	
	/**
	 * Is there no entities there? (Relatively to Mario.)
	 * @param relMapX
	 * @param relMapY
	 * @return
	 */
	public boolean nothing(int relMapX, int relMapY) {
		return entities(relMapX, relMapY).size() == 0;
	}
	
	/**
	 * Is there any danger, something that can hurt me? (Relatively to Mario.)
	 * @param relMapX
	 * @param relMapY
	 * @return
	 */
	public boolean danger(int relMapX, int relMapY) {
		if (nothing(relMapX, relMapY)) return false;
		for (Entity entity : entities(relMapX, relMapY)) {
			if (isDanger(entity)) return true;
		}
		return false;
	}
	
	/**
	 * Is this 'entity' dangerous (i.e. it can hurt Mario if Mario touches it)?
	 * @param entity
	 * @return
	 */
	public boolean isDanger(Entity entity) {
		return entity.type.getKind().isDangerous();
	}
	
	/**
	 * Can I squish something there? (Relative to Mario). Note that this will return FALSE if there is non-squishable dangerous stuff there.
	 * if the same tile is 
	 * @param relMapX
	 * @param relMapY
	 * @return
	 */
	public boolean squishy(int relMapX, int relMapY) {
		if (nothing(relMapX, relMapY)) return false;
		boolean result = false;
		for (Entity entity : entities(relMapX, relMapY)) {
			if (!isSquishy(entity) && isDanger(entity)) return false;
			if (isSquishy(entity)) {
				result = true;
			}
		}	
		return result;
	}
	
	/**
	 * Is this 'entity' squishable by jumping on it (== destroyable)?
	 * @param entity
	 * @return
	 */
	public boolean isSquishy(Entity entity) {
		return entity.type.getKind().isSquishy();
	}
	
	/**
	 * Can I shoot out something there? If the tile is occupied by both shootable and non-shootable things, this will report FALSE (play safe).
	 * @param relMapX
	 * @param relMapY
	 * @return
	 */
	public boolean shootable(int relMapX, int relMapY) {
		if (nothing(relMapX, relMapY)) return false;		
		boolean result = false;
		for (Entity entity : entities(relMapX, relMapY)) {
			if (!isShootable(entity)) return false;
			result = true;
		}	
		return result;
	}
	
	/**
	 * Is this 'entity' shootable by fireball?
	 * @param entity
	 * @return
	 */
	public boolean isShootable(Entity entity) {
		return entity.type.getKind().isShootable();
	}
	
	/**
	 * Can I collect something by going to that tile? (TRUE == collectible & !danger)
	 * @param relMapX
	 * @param relMapY
	 * @return
	 */
	public boolean collectible(int relMapX, int relMapY) {
		if (nothing(relMapX, relMapY)) return false;
		if (danger(relMapX, relMapY)) return false;
		for (Entity entity : entities(relMapX, relMapY)) {
			if (isCollectible(entity)) return true;
		}		
		return false;
	}
	
	/**
	 * Is this entity 'collectible'?
	 * @param entity
	 * @return
	 */
	public boolean isCollectible(Entity entity) {
		return entity.type.getKind().isCollectible();
	}
	
}
