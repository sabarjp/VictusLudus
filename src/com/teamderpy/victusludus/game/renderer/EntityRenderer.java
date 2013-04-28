package com.teamderpy.victusludus.game.renderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;

import org.newdawn.slick.Animation;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.game.EnumFlags;
import com.teamderpy.victusludus.game.ScreenCoord;
import com.teamderpy.victusludus.game.WorldCoord;
import com.teamderpy.victusludus.game.entity.GameEntity;
import com.teamderpy.victusludus.game.tile.GameTile;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityRenderer.
 */
public class EntityRenderer {

	/** The game renderer. */
	public GameRenderer gameRenderer;

	/** The list of entites that have been culled, ready for rendering */
	ArrayList<GameEntity> culledEntityList;

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

		//check if a ray of light can hit this entity
		int[] sc = RenderUtil.worldCoordToRawUnscaledScreenCoord(gameEntity.getPos().getX(), gameEntity.getPos().getY(), gameEntity.getPos().getZ());

		GameTile[][][] tileArray = this.gameRenderer.game.getMap().getMap();

		int z = gameEntity.getPos().getZ();

		for(int k=layer; k>z; k--){
			int[] wc = RenderUtil.screenCoordToRawUnscaledWorldCoord(sc[0], sc[1], k);
			GameTile t = null;

			if(wc[0] >= 0 && wc[1] >= 0 && wc[0] < tileArray[k].length && wc[1] < tileArray[k][0].length){
				t = tileArray[k][wc[0]][wc[1]];

				if(t != null){
					//we are being blocked
					if(this.culledEntityList.contains(gameEntity)){
						this.culledEntityList.remove(gameEntity);
					}
					return;
				}
			}
		}

		//we must be visible
		if(!this.culledEntityList.contains(gameEntity)){
			this.culledEntityList.add(gameEntity);
		}
	}

	/**
	 * Render.
	 *
	 * @param entityList the entity list
	 * @param layer the layer
	 */
	public void render(final Vector<GameEntity> entityList, final int layer){
		this.calculateCulledEntities(entityList, layer);

		//beware of concurrent modification
		//get the entity render list then sort it to obtain correct order
		GameEntity[] gelist = new GameEntity[this.culledEntityList.size()];
		this.culledEntityList.toArray(gelist);
		Arrays.sort(gelist, new RenderComparator());

		for (GameEntity gameEntity : gelist) {
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

	/**
	 * The Class RenderComparator.
	 */
	private static class RenderComparator implements Comparator<GameEntity>{

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(final GameEntity e1, final GameEntity e2) {
			WorldCoord ep1 = e1.getPos();
			WorldCoord ep2 = e2.getPos();

			int mod1 = 0;
			int mod2 = 0;

			if(e1.getEntity().getFlagSet().contains(EnumFlags.WALKABLE)){
				mod1--;
			}

			if(e2.getEntity().getFlagSet().contains(EnumFlags.WALKABLE)){
				mod2--;
			}

			int sum1 = ep1.getX() + ep1.getY() + ep1.getZ() + mod1;
			int sum2 = ep2.getX() + ep2.getY() + ep2.getZ() + mod2;

			if(sum1 < sum2){
				return -1;
			} else if (sum1 > sum2){
				return 1;
			}

			//it is a perfect tie
			return 0;
		}
	}
}
