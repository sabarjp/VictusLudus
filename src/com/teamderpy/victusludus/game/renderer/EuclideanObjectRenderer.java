package com.teamderpy.victusludus.game.renderer;

import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.Animation;
import com.teamderpy.victusludus.game.EuclideanObject;
import com.teamderpy.victusludus.game.entity.GameEntity;
import com.teamderpy.victusludus.game.tile.GameTile;

/**
 * Renders tiles and entities
 * @author Joshua
 *
 */
public class EuclideanObjectRenderer {
	/** The game renderer. */
	private GameRenderer gameRenderer;

	/** The list of euclidean objects that have been culled, ready for rendering */
	private ArrayList<EuclideanObject> culledObjectList;

	public EuclideanObjectRenderer(final GameRenderer gameRenderer){
		this.gameRenderer = gameRenderer;
		this.culledObjectList = new ArrayList<EuclideanObject>();
	}

	/**
	 * @param tiles
	 * @param entities
	 * @param overlayTiles
	 * @param layer
	 */
	public void render(final ArrayList<GameTile> tiles, final ArrayList<GameEntity> entities, final ArrayList<GameTile> overlayTiles, final int layer){
		int rightBound =  this.gameRenderer.game.getGameDimensions().getWidth() - this.gameRenderer.game.getTileWidthS();
		int bottomBound = this.gameRenderer.game.getGameDimensions().getHeight() - this.gameRenderer.game.getTileHeightS();

		//master list
		this.culledObjectList.clear();
		this.culledObjectList.addAll(tiles);
		this.culledObjectList.addAll(entities);
		this.culledObjectList.addAll(overlayTiles);

		Collections.sort(this.culledObjectList, new EuclideanComparator());

		boolean isTileSheetInUse = false;

		//render everything
		for (EuclideanObject o : this.culledObjectList) {
			int[] sc = RenderUtil.worldCoordToRawScreenCoord(this.gameRenderer.game, o.getWorldCoord().getX(), o.getWorldCoord().getY(), o.getWorldCoord().getZ());

			//skip objects not on the screen
			if(sc[0] < 0 || sc[1] < 0 || sc[0] > rightBound || sc[1] > bottomBound){
				continue;  //skip
			}

			boolean showHidden = false; //whether or not the object is hidden

			//color by depth -- this may be too slow to keep
			//float colorModDepth = (o.getWorldCoord().getZ() - (layer-zdeep)) / (float)zdeep;
			//Color colorDepth = new Color(colorModDepth,colorModDepth,colorModDepth);
			//colorDepth.bind();

			//calculate if an object is hidden
			//will need to change this to check if the object is smothered instead of above
			if(o.getWorldCoord().getZ() < this.gameRenderer.game.getMap().getHighestPoint()){
				if(this.gameRenderer.game.getMap().getMap()[o.getWorldCoord().getZ()+1][o.getWorldCoord().getX()][o.getWorldCoord().getY()] != null){
					showHidden = true;
				}
			}

			if (showHidden){
				if(!isTileSheetInUse){
					this.gameRenderer.getTileRenderer().getTileSheet().startUse();
					isTileSheetInUse = true;
				}

				this.gameRenderer.getTileRenderer().getTileSheet().renderInUse(sc[0], sc[1],
						this.gameRenderer.game.getTileWidthS(), this.gameRenderer.game.getTileHeightS()*2,
						BitmapHandler.getSpriteSheetCol(GameTile.ID_HIDDEN, this.gameRenderer.getTileRenderer().getTileSheet()), BitmapHandler.getSpriteSheetRow(GameTile.ID_HIDDEN, this.gameRenderer.getTileRenderer().getTileSheet()));
			} else {
				if(o instanceof GameTile){
					/**
					 * 
					 * RENDER GAME TILE
					 * 
					 * */

					GameTile t = (GameTile) o;

					if(!isTileSheetInUse){
						this.gameRenderer.getTileRenderer().getTileSheet().startUse();
						isTileSheetInUse = true;
					}

					t.getTileLight().getColor().bind();

					this.gameRenderer.getTileRenderer().getTileSheet().renderInUse(sc[0], sc[1],
							this.gameRenderer.game.getTileWidthS(), this.gameRenderer.game.getTileHeightS()*2,
							BitmapHandler.getSpriteSheetCol(t.getId(), this.gameRenderer.getTileRenderer().getTileSheet()), BitmapHandler.getSpriteSheetRow(t.getId(), this.gameRenderer.getTileRenderer().getTileSheet()));
				}else if(o instanceof GameEntity){
					/**
					 * 
					 * RENDER GAME ENTITY
					 * 
					 * */

					GameEntity ge = (GameEntity) o;

					if(isTileSheetInUse){
						this.gameRenderer.getTileRenderer().getTileSheet().endUse();
						isTileSheetInUse = false;
					}

					Animation a = ge.getCurrentAnimation();
					if(a != null){
						sc[1] -= (a.getHeight()+1)*this.gameRenderer.game.getGameCamera().getZoom();
						sc[1] += this.gameRenderer.game.getTileHeightS();

						a.draw(sc[0], sc[1],
								a.getWidth()*this.gameRenderer.game.getGameCamera().getZoom(), a.getHeight()*this.gameRenderer.game.getGameCamera().getZoom());
					}
				}
			}
		}

		if(isTileSheetInUse){
			this.gameRenderer.getTileRenderer().getTileSheet().endUse();
			isTileSheetInUse = false;
		}
	}
}
