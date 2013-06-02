package com.teamderpy.victusludus.game.entity.behavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.game.EnumFlags;
import com.teamderpy.victusludus.game.Vec2i;
import com.teamderpy.victusludus.game.WorldCoord;
import com.teamderpy.victusludus.game.entity.GameEntity;
import com.teamderpy.victusludus.game.map.Map;


/* This behavior creates an entity on an adjacent tile */
/**
 * The Class MoveBehavior.
 */
public class MoveBehavior extends EntityBehavior{
	/* a list of matching ids where the entity may move */
	/** The restriction list. */
	private ArrayList<String> restrictionList;

	/* the tick rarity in which to make the entity */
	/** The rarity. */
	private int rarity;

	/* determines whether to move on random ticks or after a delta */
	/** The is random. */
	private boolean isRandom;

	/**
	 * Instantiates a new move behavior.
	 */
	public MoveBehavior(){
		this.restrictionList = new ArrayList<String>();
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.game.entity.behavior.EntityBehavior#tick(com.teamderpy.victusludus.game.entity.GameEntity)
	 */
	@Override
	public void tick(final GameEntity ge, final Map map){
		if(this.isRandom){
			if(VictusLudus.rand.nextInt(this.rarity) != 0){
				return;
			}
		} else {
			if((VictusLudusGame.engine.getTickCount() + ge.getCreationTime()) % this.rarity != 0){
				return;
			}
		}

		List<WorldCoord> adjacentCoords = new LinkedList<WorldCoord>();

		Collections.shuffle(adjacentCoords);

		//strongly prefer direction of movement and try not to move backwards
		if(ge.getEntity().getFlagSet().contains(EnumFlags.KEEPS_MOMENTUM) && !ge.getMovementVector().equals(Vec2i.getNullVector())){
			//we like going forward
			WorldCoord fc = new WorldCoord(ge.getPos().getX()+ge.getMovementVector().x, ge.getPos().getY()+ge.getMovementVector().y, ge.getPos().getZ());
			WorldCoord bc = new WorldCoord(ge.getPos().getX()+ge.getMovementVector().scale(-1).x, ge.getPos().getY()+ge.getMovementVector().scale(-1).y, ge.getPos().getZ());
			WorldCoord rc = new WorldCoord(ge.getPos().getX()+ge.getMovementVector().y, ge.getPos().getY()+ge.getMovementVector().x, ge.getPos().getZ());
			WorldCoord lc = new WorldCoord(ge.getPos().getX()+ge.getMovementVector().scale(-1).y, ge.getPos().getY()+ge.getMovementVector().scale(-1).x, ge.getPos().getZ());
			adjacentCoords.add(rc);
			adjacentCoords.add(lc);
			adjacentCoords.add(0, fc);

			if(ge.getEntity().getFlagSet().contains(EnumFlags.RANDOMLY_TURNS)){
				Collections.shuffle(adjacentCoords);
			}

			adjacentCoords.add(bc);
		} else {
			adjacentCoords.add(new WorldCoord(ge.getPos().getX()+1, ge.getPos().getY(), ge.getPos().getZ()));
			adjacentCoords.add(new WorldCoord(ge.getPos().getX()-1, ge.getPos().getY(), ge.getPos().getZ()));
			adjacentCoords.add(new WorldCoord(ge.getPos().getX(), ge.getPos().getY()-1, ge.getPos().getZ()));
			adjacentCoords.add(new WorldCoord(ge.getPos().getX(), ge.getPos().getY()+1, ge.getPos().getZ()));

			Collections.shuffle(adjacentCoords);
		}

		for(WorldCoord actionCoord:adjacentCoords){
			List<GameEntity> entityList = map.getEntityManager().getEntityListAtPos(actionCoord);

			if(entityList != null){
				//skip this tile if we are not stackable and there is a non-walkable entity on the tile
				if(!ge.getEntity().getFlagSet().contains(EnumFlags.STACKABLE)){
					boolean isPathBlocked = false;

					for(GameEntity entity:entityList){
						if(!entity.getEntity().getFlagSet().contains(EnumFlags.WALKABLE)){
							isPathBlocked = true;
							break;
						}
					}

					if(isPathBlocked) { continue; }
				}

				boolean didMoveEntity = false;

				for(GameEntity entity:entityList){
					if(entity.getEntity().getFlagSet().contains(EnumFlags.WALKABLE) &&
							(this.restrictionList.contains(entity.getId()) || this.restrictionList.contains("any"))){
						ge.setMovementVector(new Vec2i(actionCoord.getX()-ge.getPos().getX(), actionCoord.getY()-ge.getPos().getY()));
						map.getEntityManager().move(actionCoord, ge);
						didMoveEntity = true;
						break;
					}
				}

				if(didMoveEntity) { break; }
			}
		}
	}

	/**
	 * Gets the restriction list.
	 *
	 * @return the restriction list
	 */
	public ArrayList<String> getRestrictionList() {
		return this.restrictionList;
	}

	/**
	 * Sets the restriction list.
	 *
	 * @param restrictionList the new restriction list
	 */
	public void setRestrictionList(final ArrayList<String> restrictionList) {
		this.restrictionList = restrictionList;
	}

	/**
	 * Gets the rarity.
	 *
	 * @return the rarity
	 */
	public int getRarity() {
		return this.rarity;
	}

	/**
	 * Sets the rarity.
	 *
	 * @param rarity the new rarity
	 */
	public void setRarity(final int rarity) {
		this.rarity = rarity;
	}

	/**
	 * Checks if is random.
	 *
	 * @return true, if is random
	 */
	public boolean isRandom() {
		return this.isRandom;
	}

	/**
	 * Sets the random.
	 *
	 * @param isRandom the new random
	 */
	public void setRandom(final boolean isRandom) {
		this.isRandom = isRandom;
	}
}
