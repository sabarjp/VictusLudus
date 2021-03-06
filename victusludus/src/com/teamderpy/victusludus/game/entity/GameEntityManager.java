
package com.teamderpy.victusludus.game.entity;

import java.util.Vector;

import com.badlogic.gdx.math.Vector3;
import com.teamderpy.victusludus.data.MultiMap;
import com.teamderpy.victusludus.game.map.Map;

/**
 * The Class GameEntityManager.
 */
public class GameEntityManager {
	/** The game entity list. */
	MultiMap<Vector3, GameEntityInstance> gameEntityList;
	MultiMap<Vector3, GameEntityInstance> overlayList;

	/** The map this belongs to */
	Map map;

	/**
	 * Instantiates a new game entity manager.
	 * @param map
	 */
	public GameEntityManager (final Map map) {
		this.map = map;
		this.gameEntityList = new MultiMap<Vector3, GameEntityInstance>();
		this.overlayList = new MultiMap<Vector3, GameEntityInstance>();
	}

	/**
	 * Adds the game entity to the list
	 * 
	 * @param ge the GameEntity
	 * @param map the map the entity belongs to
	 */
	public void add (final GameEntityInstance ge) {
		this.gameEntityList.add(ge.getPos(), ge);
	}

	/**
	 * Adds the game entity to the list
	 * 
	 * @param ge the GameEntity
	 * @param map the map the entity belongs to
	 */
	public void addOverlay (final GameEntityInstance ge) {
		this.overlayList.add(ge.getPos(), ge);
	}

	/**
	 * Gets the entities.
	 * 
	 * @return the entities
	 */
	public Vector<GameEntityInstance> getEntities () {
		return this.gameEntityList.getAllValues();
	}

	/**
	 * Gets the overlay entities.
	 * 
	 * @return the entities
	 */
	public Vector<GameEntityInstance> getOverlayEntities () {
		return this.overlayList.getAllValues();
	}

	/**
	 * Clears all overlay entities.
	 * 
	 * @return the entities
	 */
	public void clearOverlayEntities () {
		this.overlayList.clear();
	}

	/**
	 * Gets all entities.
	 * 
	 * @return the entities
	 */
	public Vector<GameEntityInstance> getAllEntities () {
		Vector<GameEntityInstance> combinedList = new Vector<GameEntityInstance>();

		combinedList.addAll(this.gameEntityList.getAllValues());
		combinedList.addAll(this.overlayList.getAllValues());

		return combinedList;
	}

	/**
	 * Gets the entity list at pos.
	 * 
	 * @param coord the coord
	 * @return the entity list at pos
	 */
	public Vector<GameEntityInstance> getEntityListAtPos (final Vector3 coord) {
		return this.gameEntityList.getValueList(coord);
	}

	/**
	 * Move.
	 * 
	 * @param coord the coord the object moves to
	 * @param ge the game entity to move
	 */
	public void move (final Vector3 coord, final GameEntityInstance ge) {
		/* re-index then set position */
		this.gameEntityList.remove(ge.getPos(), ge);
		ge.setPos(coord);
		this.gameEntityList.add(coord, ge);
	}
}
