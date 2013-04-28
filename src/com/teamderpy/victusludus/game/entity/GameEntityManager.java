package com.teamderpy.victusludus.game.entity;

import java.util.Vector;

import com.teamderpy.victusludus.data.MultiMap;
import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.game.WorldCoord;

// TODO: Auto-generated Javadoc
/**
 * The Class GameEntityManager.
 */
public class GameEntityManager {
	/* all world entities indexed on position */
	/** The game entity list. */
	MultiMap<WorldCoord, GameEntity> gameEntityList;

	/**
	 * Instantiates a new game entity manager.
	 */
	public GameEntityManager(){
		this.gameEntityList = new MultiMap<WorldCoord, GameEntity>();
	}

	/**
	 * Adds the.
	 *
	 * @param ge the ge
	 */
	public void add(final GameEntity ge){
		this.gameEntityList.add(ge.getPos(), ge);
	}

	/**
	 * Gets the entities.
	 *
	 * @return the entities
	 */
	public Vector<GameEntity> getEntities(){
		return this.gameEntityList.getAllValues();
	}

	/**
	 * Gets the entity list at pos.
	 *
	 * @param coord the coord
	 * @return the entity list at pos
	 */
	public Vector<GameEntity> getEntityListAtPos(final WorldCoord coord){
		return this.gameEntityList.getValueList(coord);
	}

	/**
	 * Move.
	 *
	 * @param coord the coord the object moves to
	 * @param ge the game entity to move
	 */
	public void move(final WorldCoord coord, final GameEntity ge){
		/* re-index then set position */
		this.gameEntityList.remove(ge.getPos(), ge);
		ge.setPos(coord);
		this.gameEntityList.add(coord, ge);

		if(ge.getEntityLight() != null){
			ge.getEntityLight().setPos(coord);
			//we will need to re-calculate the light map
			VictusLudus.e.currentGame.getMap().getLightMap().setDirty(true);
		}
	}
}
