package ch.idsia.agents.controllers.modules;

import ch.idsia.agents.AgentOptions;
import ch.idsia.benchmark.mario.engine.generalization.MarioEntity;
import ch.idsia.benchmark.mario.engine.generalization.Tile;

public class Tiles {

	private MarioEntity mario;
	
	/**
	 * 2-dimensional array representing the scene terrain+coins.
	 * 
	 * Tiles are stored in [row][col] manner.
	 * 
	 * Mario is at [marioEgoRow][marioEgoCol].
	 *  
	 * The array is {@link #receptiveFieldHeight} x {@link #receptiveFieldWidth} long.
	 */
	public Tile[][] tileField;
	
	public void reset(AgentOptions options) {
		this.mario = options.mario;
	}
	
	public Tile getTile(int mapX, int mapY) {
		if (mapY < 0 || mapY >= tileField.length || mapX < 0 || mapX >= tileField[0].length) return Tile.NOTHING;
		return tileField[mapY][mapX];
	}
	
	public Tile tile(int relMapX, int relMapY) {
		return getTile(mario.egoCol + relMapX, mario.egoRow + relMapY);
	}
	
	public boolean emptyTile(int relMapX, int relMapY) {
		return tile(relMapX, relMapY) == Tile.NOTHING;
	}
	
	public boolean anyTile(int relMapX, int relMapY) {
		return !emptyTile(relMapX, relMapY);
	}
	
	public boolean brick(int relMapX, int relMapY) {
		Tile tile = tile(relMapX, relMapY); 
		switch (tile) {
		case BORDER_CANNOT_PASS_THROUGH:
		case BREAKABLE_BRICK:
		case CANNON_MUZZLE:
		case CANNON_TRUNK:
		case FLOWER_POT:
		case FLOWER_POT_OR_CANNON:
		case QUESTION_BRICK:
			return true;
		}
		return false;
	}
	
}
