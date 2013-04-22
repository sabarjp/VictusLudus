package com.teamderpy.victusludus.game.renderer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.game.EnumFlags;
import com.teamderpy.victusludus.game.ScreenCoord;
import com.teamderpy.victusludus.game.WorldCoord;
import com.teamderpy.victusludus.game.entity.GameEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityRenderer.
 */
public class EntityRenderer {
	
	/** The game renderer. */
	public GameRenderer gameRenderer;

	/**
	 * Instantiates a new entity renderer.
	 *
	 * @param gameRenderer the game renderer
	 */
	public EntityRenderer(final GameRenderer gameRenderer){
		this.gameRenderer = gameRenderer;
	}

	/**
	 * Render.
	 *
	 * @param entityList the entity list
	 * @param layer the layer
	 */
	public void render(final Vector<GameEntity> entityList, final int layer){
		//beware of concurrent modification

		//get the entity render list then sort it to obtain correct order
		GameEntity[] gelist = new GameEntity[entityList.size()];
		entityList.toArray(gelist);
		Arrays.sort(gelist, new RenderComparator());

		for (GameEntity gameEntity : gelist) {
			if(gameEntity.getPos().getZ() != layer){
				continue;
			}

			ScreenCoord c = RenderUtil.worldCoordToScreenCoord(gameEntity.getPos().getX(), gameEntity.getPos().getY(), VictusLudus.e.currentGame.getCurrentDepth());

			if(c.x < 0 || c.y < 0 || c.x > VictusLudus.e.currentGame.getGameDimensions().getWidth() || c.y > VictusLudus.e.currentGame.getGameDimensions().getHeight()){
				continue;  //do not render entities off the screen!
			}

			c.y -= gameEntity.getEntity().getAnimationHash().get("idle").getHeight()*VictusLudus.e.currentGame.getGameCamera().getZoom();
			c.y += VictusLudus.e.currentGame.getTileHeightS();

			gameEntity.getEntity().getAnimationHash().get("idle").draw(c.x, c.y,
					gameEntity.getEntity().getAnimationHash().get("idle").getWidth()*VictusLudus.e.currentGame.getGameCamera().getZoom(), gameEntity.getEntity().getAnimationHash().get("idle").getHeight()*VictusLudus.e.currentGame.getGameCamera().getZoom());

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
