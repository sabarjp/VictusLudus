
package com.teamderpy.victusludus.game.entity.behavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.Vector3;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.game.EnumFlags;
import com.teamderpy.victusludus.game.entity.GameEntityInstance;
import com.teamderpy.victusludus.game.map.Map;

/* This behavior creates an entity on an adjacent tile */
/** The Class MoveBehavior. */
public class MoveBehavior extends EntityBehavior {
	/* a list of matching ids where the entity may move */
	/** The restriction list. */
	private ArrayList<String> restrictionList;

	/* the tick rarity in which to make the entity */
	/** The rarity. */
	private int rarity;

	/* determines whether to move on random ticks or after a delta */
	/** The is random. */
	private boolean isRandom;

	/** Instantiates a new move behavior. */
	public MoveBehavior () {
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
		if (this.isRandom) {
			if (VictusLudusGame.rand.nextInt(this.rarity) != 0) {
				return;
			}
		} else {
			if ((VictusLudusGame.engine.getTickCount() + ge.getCreationTime()) % this.rarity != 0) {
				return;
			}
		}

		List<Vector3> adjacentCoords = new LinkedList<Vector3>();

		Collections.shuffle(adjacentCoords);

		// strongly prefer direction of movement and try not to move backwards
		if (ge.getEntity().getFlagSet().contains(EnumFlags.KEEPS_MOMENTUM) && !ge.getMovementVector().equals(Vector3.Zero)) {
			// we like going forward
			Vector3 pos = ge.getPos();

			Vector3 fc = new Vector3(pos.x + ge.getMovementVector().x, pos.y + ge.getMovementVector().y, pos.z);
			Vector3 bc = new Vector3(pos.x + ge.getMovementVector().scl(-1).x, pos.y + ge.getMovementVector().scl(-1).y, pos.z);
			Vector3 rc = new Vector3(pos.x + ge.getMovementVector().y, pos.y + ge.getMovementVector().x, pos.z);
			Vector3 lc = new Vector3(pos.x + ge.getMovementVector().scl(-1).y, pos.y + ge.getMovementVector().scl(-1).x, pos.z);
			adjacentCoords.add(rc);
			adjacentCoords.add(lc);
			adjacentCoords.add(0, fc);

			if (ge.getEntity().getFlagSet().contains(EnumFlags.RANDOMLY_TURNS)) {
				Collections.shuffle(adjacentCoords);
			}

			adjacentCoords.add(bc);
		} else {
			Vector3 pos = ge.getPos();

			adjacentCoords.add(new Vector3(pos.x + 1, pos.y, pos.z));
			adjacentCoords.add(new Vector3(pos.x - 1, pos.y, pos.z));
			adjacentCoords.add(new Vector3(pos.x, pos.y - 1, pos.z));
			adjacentCoords.add(new Vector3(pos.x, pos.y + 1, pos.z));

			Collections.shuffle(adjacentCoords);
		}

		for (Vector3 actionCoord : adjacentCoords) {
			List<GameEntityInstance> entityList = map.getEntityManager().getEntityListAtPos(actionCoord);

			if (entityList != null) {
				// skip this tile if we are not stackable and there is a
// non-walkable entity on the tile
				if (!ge.getEntity().getFlagSet().contains(EnumFlags.STACKABLE)) {
					boolean isPathBlocked = false;

					for (GameEntityInstance entity : entityList) {
						if (!entity.getEntity().getFlagSet().contains(EnumFlags.WALKABLE)) {
							isPathBlocked = true;
							break;
						}
					}

					if (isPathBlocked) {
						continue;
					}
				}

				boolean didMoveEntity = false;

				for (GameEntityInstance entity : entityList) {
					if (entity.getEntity().getFlagSet().contains(EnumFlags.WALKABLE)
						&& (this.restrictionList.contains(entity.getId()) || this.restrictionList.contains("any"))) {
						ge.setMovementVector(new Vector3((int)(actionCoord.x - ge.getPos().x), 0, (int)(actionCoord.y - ge.getPos().y)));
						map.getEntityManager().move(actionCoord, ge);
						didMoveEntity = true;
						break;
					}
				}

				if (didMoveEntity) {
					break;
				}
			}
		}
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

	/**
	 * Checks if is random.
	 * 
	 * @return true, if is random
	 */
	public boolean isRandom () {
		return this.isRandom;
	}

	/**
	 * Sets the random.
	 * 
	 * @param isRandom the new random
	 */
	public void setRandom (final boolean isRandom) {
		this.isRandom = isRandom;
	}
}
