
package com.teamderpy.victusludus.game.entity.behavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.Vector3;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.game.EnumFlags;
import com.teamderpy.victusludus.game.entity.GameEntity;
import com.teamderpy.victusludus.game.entity.GameEntityInstance;
import com.teamderpy.victusludus.game.map.Map;

/* This behavior creates an entity on an adjacent tile */
/** The Class CreateAdjacentBehavior. */
public class CreateAdjacentBehavior extends EntityBehavior {
	/* the entity to create */
	/** The object id. */
	private String objectID;

	/* the number of entities to create */
	/** The count. */
	private int count;

	/* a list of matching ids where the entity can be made */
	/** The restriction list. */
	private ArrayList<String> restrictionList;

	/* the tick rarity in which to make the entity */
	/** The rarity. */
	private int rarity;

	/** Instantiates a new creates the adjacent behavior. */
	public CreateAdjacentBehavior () {
		this.restrictionList = new ArrayList<String>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teamderpy.victusludus.game.entity.behavior.EntityBehavior#tick(com
	 * .teamderpy.victusludus.game.entity.GameEntity)
	 */
	@Override
	public void tick (final GameEntityInstance ge, final Map map) {
		if (VictusLudusGame.rand.nextInt(this.rarity) != 0) {
			return;
		}

		List<Vector3> adjacentCoords = new LinkedList<Vector3>();

		Vector3 pos = ge.getPos();

		adjacentCoords.add(new Vector3(pos.x + 1, pos.y, pos.z));
		adjacentCoords.add(new Vector3(pos.x - 1, pos.y, pos.z));
		adjacentCoords.add(new Vector3(pos.x, pos.y - 1, pos.z));
		adjacentCoords.add(new Vector3(pos.x, pos.y + 1, pos.z));

		for (int i = 0; i < this.count; i++) {
			Collections.shuffle(adjacentCoords);

			for (Vector3 actionCoord : adjacentCoords) {
				List<GameEntityInstance> entityList = map.getEntityManager().getEntityListAtPos(actionCoord);

				if (entityList != null) {
					// skip this tile if we are not stackable and there is a
// non-walkable entity on the tile
					GameEntity ce = VictusLudusGame.resources.getEntityHash().get(this.objectID);
					if (!ce.getFlagSet().contains(EnumFlags.STACKABLE)) {
						boolean isSpawnBlocked = false;

						for (GameEntityInstance entity : entityList) {
							if (!entity.getEntity().getFlagSet().contains(EnumFlags.WALKABLE)) {
								isSpawnBlocked = true;
								break;
							}
						}

						if (isSpawnBlocked) {
							continue;
						}
					}

					boolean didSpawnEntity = false;

					for (GameEntityInstance entity : entityList) {
						if (this.restrictionList.contains(entity.getId()) || this.restrictionList.contains("any")) {
							map.addEntity(new GameEntityInstance(this.objectID, (int)actionCoord.x, (int)actionCoord.y,
								(int)actionCoord.z, map));
							didSpawnEntity = true;
							break;
						}
					}

					if (didSpawnEntity) {
						break;
					}
				}
			}
		}
	}

	/**
	 * Gets the object id.
	 * 
	 * @return the object id
	 */
	public String getObjectID () {
		return this.objectID;
	}

	/**
	 * Sets the object id.
	 * 
	 * @param objectID the new object id
	 */
	public void setObjectID (final String objectID) {
		this.objectID = objectID;
	}

	/**
	 * Gets the count.
	 * 
	 * @return the count
	 */
	public int getCount () {
		return this.count;
	}

	/**
	 * Sets the count.
	 * 
	 * @param count the new count
	 */
	public void setCount (final int count) {
		this.count = count;
	}

	/**
	 * Gets the restriction list.
	 * 
	 * @return the restriction list
	 */
	public ArrayList<String> getRestrictionList () {
		return this.restrictionList;
	}

	/**
	 * Sets the restriction list.
	 * 
	 * @param restrictionList the new restriction list
	 */
	public void setRestrictionList (final ArrayList<String> restrictionList) {
		this.restrictionList = restrictionList;
	}

	/**
	 * Gets the rarity.
	 * 
	 * @return the rarity
	 */
	public int getRarity () {
		return this.rarity;
	}

	/**
	 * Sets the rarity.
	 * 
	 * @param rarity the new rarity
	 */
	public void setRarity (final int rarity) {
		this.rarity = rarity;
	}
}
