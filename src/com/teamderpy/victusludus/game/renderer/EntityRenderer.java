package com.teamderpy.victusludus.game.renderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import org.newdawn.slick.Animation;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.game.ScreenCoord;
import com.teamderpy.victusludus.game.entity.GameEntity;
import com.teamderpy.victusludus.game.tile.GameTile;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityRenderer.
 */
public class EntityRenderer {

	/** The game renderer. */
	private GameRenderer gameRenderer;

	/** The list of entities that have been culled, ready for rendering */
	private ArrayList<GameEntity> culledEntityList;

	/**
	 * Instantiates a new entity renderer.
	 *
	 * @param gameRenderer the game renderer
	 */
	public EntityRenderer(final GameRenderer gameRenderer){
		this.gameRenderer = gameRenderer;
		this.culledEntityList = new ArrayList<GameEntity>();
	}

	public void calculateCulledEntities(final Vector<GameEntity> entityList, final int layer){
		for(GameEntity gameEntity:entityList){
			this.calculateCulledEntity(gameEntity, layer);
		}
	}

	/**
	 * Check if the entity is renderable or not
	 * 
	 * @param gameEntity the game entity to check
	 */
	public void calculateCulledEntity(final GameEntity gameEntity, final int layer){
		//do not render things that cannot be seen at all
		if(gameEntity.getPos().getZ() > layer){
			if(this.culledEntityList.contains(gameEntity)){
				this.culledEntityList.remove(gameEntity);
			}
			return;
		}

		//check if a ray of light can hit the base of this entity
		int[] sc = RenderUtil.worldCoordToRawUnscaledScreenCoord(gameEntity.getPos().getX(), gameEntity.getPos().getY(), gameEntity.getPos().getZ());
		//System.err.println("base of entity is " + gameEntity.getPos() + "  " + sc[0] + "," + sc[1]);

		GameTile[][][] tileArray = this.gameRenderer.game.getMap().getMap();

		int zdeep = GameRenderer.getMaxVisibleDepth();
		int z = gameEntity.getPos().getZ();

		//set the offsets to see if each part of the entity can be hit by light
		int[] yOffsets = new int[gameEntity.getEntity().getHeight()];
		for(int i=0; i<gameEntity.getEntity().getHeight(); i++){
			yOffsets[i] = sc[1] + (i+1) * this.gameRenderer.game.getGameDimensions().getLayerHeight();
			//System.err.println("  offset " + i + "  at " + yOffsets[i]);
		}

		boolean isBlocked = false;
		//start from the top of the entity and go down
		for(int i=yOffsets.length-1; i>=0; i--){
			//System.err.println("  checking against offset " + i + " at " + yOffsets[i]);
			isBlocked = false;

			//System.err.println("  making sure that offset " + (i+1+z) + " isnt over current layer " + layer);
			if(i+1 + z > layer){
				//this entity must be visible since nothing could break sight of its top
				break;
			}

			for(int k=layer; k>z+i+1; k--){
				//a certain offset can only be blocked by certain tiles
				int[] wc = RenderUtil.screenCoordToRawUnscaledWorldCoord(sc[0], yOffsets[i], k);
				//System.err.println("    for the layer " + k + " the potential tile is " + wc[0] + "," + wc[1] + "," + wc[2]);

				GameTile t = null;

				if(wc[0] >= 0 && wc[1] >= 0 && wc[0] < tileArray[k].length && wc[1] < tileArray[k][0].length){
					t = tileArray[k][wc[0]][wc[1]];

					if(t != null){
						//System.err.println("       a blocking tile exists");
						//this is blocked, so go to the next spot
						isBlocked = true;
						break;
					}

					//System.err.println("       no blocking tile exists");
				}

				//if the top of the entity if visible then add it
				if(!isBlocked){
					//System.err.println("       the offset " + i + " is visible");
					if(!this.culledEntityList.contains(gameEntity)){
						this.culledEntityList.add(gameEntity);
					}
					return;
				}
			}
		}

		//if we got all the way through then the entity is fully blocked off, so remove it
		if(isBlocked){
			//System.err.println("  blocked all the way through");
			if(this.culledEntityList.contains(gameEntity)){
				this.culledEntityList.remove(gameEntity);
			}
		} else {
			//System.err.println("  visible at the last second");
			if(!this.culledEntityList.contains(gameEntity)){
				this.culledEntityList.add(gameEntity);
			}
		}

		return;
	}

	/**
	 * Render.
	 *
	 * @param entityList the entity list
	 * @param layer the layer
	 */
	@Deprecated
	public void render(final Vector<GameEntity> entityList, final int layer){
		this.calculateCulledEntities(entityList, layer);

		//beware of concurrent modification
		//get the entity render list then sort it to obtain correct order
		Collections.sort(this.culledEntityList, new EuclideanComparator());

		for (GameEntity gameEntity : this.culledEntityList) {
			ScreenCoord c = RenderUtil.worldCoordToScreenCoord(gameEntity.getPos().getX(), gameEntity.getPos().getY(), gameEntity.getPos().getZ());

			if(c.x < 0 || c.y < 0 || c.x > VictusLudus.e.currentGame.getGameDimensions().getWidth() || c.y > VictusLudus.e.currentGame.getGameDimensions().getHeight()){
				continue;  //do not render entities off the screen!
			}

			Animation a = gameEntity.getCurrentAnimation();
			if(a != null){
				c.y -= a.getHeight()*VictusLudus.e.currentGame.getGameCamera().getZoom();
				c.y += VictusLudus.e.currentGame.getTileHeightS();

				a.draw(c.x, c.y,
						a.getWidth()*VictusLudus.e.currentGame.getGameCamera().getZoom(), a.getHeight()*VictusLudus.e.currentGame.getGameCamera().getZoom());
			}

			//gameEntity.getEntity().getEntitySpriteSheet().endUse();
		}
	}

	public ArrayList<GameEntity> getCulledEntityList() {
		return this.culledEntityList;
	}
}
