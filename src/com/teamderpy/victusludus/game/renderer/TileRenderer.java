package com.teamderpy.victusludus.game.renderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.newdawn.slick.Color;
import org.newdawn.slick.SpriteSheet;
import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.game.WorldCoord;
import com.teamderpy.victusludus.game.tile.GameTile;
import com.teamderpy.victusludus.gui.eventhandler.RenderListener;
import com.teamderpy.victusludus.gui.eventhandler.event.EnumRenderEventType;
import com.teamderpy.victusludus.gui.eventhandler.event.RenderEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class TileRenderer.
 */
/**
 * @author Joshua
 *
 */
public class TileRenderer implements RenderListener{

	/** The tile sheet. */
	private SpriteSheet tileSheet;

	/** The game renderer. */
	public GameRenderer gameRenderer;

	/** The culled tile list.  This should always be up-to-date with the tiles clear for drawing. */
	private GameTile[] culledTileArray;

	/**
	 * Instantiates a new tile renderer.
	 *
	 * @param gameRenderer the game renderer
	 */
	public TileRenderer(final GameRenderer gameRenderer){
		this.tileSheet = BitmapHandler.LoadSpriteSheet("res/sprites/tiles.png");

		this.gameRenderer = gameRenderer;

		gameRenderer.game.getGameDimensions().setTileHeight(this.tileSheet.getHeight() / this.tileSheet.getVerticalCount() / 2);
		gameRenderer.game.getGameDimensions().setTileWidth(this.tileSheet.getWidth() / this.tileSheet.getHorizontalCount());
		gameRenderer.game.getGameDimensions().setGridHeight(gameRenderer.game.getGameDimensions().getTileHeight() / 2);
		gameRenderer.game.getGameDimensions().setGridWidth(gameRenderer.game.getGameDimensions().getTileWidth() / 2);
		gameRenderer.game.getGameDimensions().setLayerHeight(gameRenderer.game.getGameDimensions().getTileHeight());

		this.registerListeners();
		this.culledTileArray = new GameTile[0];

		//initial render
		this.calculateCulledTiles();
	}

	/**
	 * Render.
	 *
	 * @param tileArray the tile array
	 * @param overlayList the overlay list
	 * @param layer the layer
	 */
	public void render(final GameTile[][][] tileArray, final ArrayList<GameTile> overlayList, final int layer){
		this.cullRender(overlayList, layer);

		return;

		/*

		int x = 0;
		int y = 0;
		int z = layer - 15;

		Color baseColor = new Color(255,255,255);

		if(z < 0){
			z = 0;
		}

		this.tileSheet.startUse();

		int bottomLayer = z;

		//render layers
		while (z <= layer){
			float colorModDepth = (float)(z-bottomLayer) / (float)(layer-bottomLayer);
			Color colorDepth = new Color(colorModDepth,colorModDepth,colorModDepth);

			while(x < tileArray[layer].length){
				y = 0;

				while(y < tileArray[layer][x].length){
					ScreenCoord c = RenderUtil.worldCoordToScreenCoord(x, y, z);
					boolean showHidden = false;

					if(c.x < 0 || c.y < 0 || c.x > VictusLudus.e.currentGame.getGameDimensions().getWidth() || c.y > VictusLudus.e.currentGame.getGameDimensions().getHeight()){
						y++;
						continue;  //do not render tiles off the screen!
					}

					GameTile t = tileArray[z][x][y];

					if(z < tileArray.length){
						if(tileArray[z+1][x][y] != null){
							showHidden = true;
						}
					}

					if(t != null){
						if(showHidden){
							//baseColor.bind();

							this.tileSheet.renderInUse(c.x, c.y,
									VictusLudus.e.currentGame.getTileWidthS(), VictusLudus.e.currentGame.getTileHeightS()*2,
									GameTile.getSpriteSheetCol(GameTile.ID_HIDDEN), GameTile.getSpriteSheetRow(GameTile.ID_HIDDEN));
						} else {
							//colorDepth.bind();

							this.tileSheet.renderInUse(c.x, c.y,
									VictusLudus.e.currentGame.getTileWidthS(), VictusLudus.e.currentGame.getTileHeightS()*2,
									t.getSpriteSheetCol(), t.getSpriteSheetRow());
						}
					}

					y++;
				}

				x++;
			}

			z++;
			x = 0;
			y = 0;
		}

		//render tile overlays
		for(GameTile gt:overlayList){
			if(gt.getPos().getZ() == layer){
				ScreenCoord c = RenderUtil.worldCoordToScreenCoord(gt.getPos().getX(), gt.getPos().getY(), layer);

				this.tileSheet.renderInUse(c.x, c.y,
						VictusLudus.e.currentGame.getTileWidthS(), VictusLudus.e.currentGame.getTileHeightS()*2,
						gt.getSpriteSheetCol(), gt.getSpriteSheetRow());
			}
		}

		this.tileSheet.endUse();
		 */
	}

	/**
	 * Calculate culled tiles.  Hopefully this does not need to be called too often.
	 */
	private void calculateCulledTiles(){
		GameTile[][][] tileArray = this.gameRenderer.game.getMap().getMap();
		int layer = this.gameRenderer.game.getCurrentDepth();
		ArrayList<GameTile> culledTileList = new ArrayList<GameTile>();

		int z = layer - 15;

		if(z < 0){
			z = 0;
		}

		int X1 = RenderUtil.worldCoordToRawUnscaledScreenCoord(0, this.gameRenderer.game.getMap().getHeight()+15, layer)[0];
		int Y1 = RenderUtil.worldCoordToRawUnscaledScreenCoord(0, 0, layer)[1];

		int X2 = this.gameRenderer.game.getGameDimensions().getTileWidth() +
				RenderUtil.worldCoordToRawUnscaledScreenCoord(this.gameRenderer.game.getMap().getWidth()+15, 0, layer)[0];
		int Y2 = this.gameRenderer.game.getGameDimensions().getTileHeight() +
				RenderUtil.worldCoordToRawUnscaledScreenCoord(this.gameRenderer.game.getMap().getWidth()+15, this.gameRenderer.game.getMap().getHeight()+15, layer)[1];

		int xStep = this.gameRenderer.game.getGameDimensions().getTileWidth()/2;
		int yStep = this.gameRenderer.game.getGameDimensions().getTileHeight()/2;

		//System.err.println(X1 + "," + Y1 + " thru " + X2 + "," + Y2 + "   step:" + xStep + "," + yStep);

		//cull tiles by depth
		//split the screen into a grid
		for(int j=Y1; j<Y2-yStep; j += yStep){
			for(int i=X1; i<X2-xStep; i += xStep){
				//locate the first tile that covers this spot
				//System.err.println("check " + i + "," + j);
				for(int k=layer; k>z; k--){
					int[] wc = RenderUtil.screenCoordToRawUnscaledWorldCoord(i, j, k);
					GameTile t = null;

					if(wc[0] >= 0 && wc[1] >= 0 && wc[0] < tileArray[k].length && wc[1] < tileArray[k][0].length){
						t = tileArray[k][wc[0]][wc[1]];

						if(t != null){
							//toss onto the render list
							if(!culledTileList.contains(t)){
								//System.err.println("matched " + wc[0] + "," + wc[1] + "," + wc[2]);
								culledTileList.add(t);
							}

							break; //found a tile
						}
					}
				}
			}
		}

		//sort for proper rendering order
		this.culledTileArray = new GameTile[culledTileList.size()];
		culledTileList.toArray(this.culledTileArray);
		Arrays.sort(this.culledTileArray, new RenderComparator());
	}

	/**
	 * Renders the tiles that we culled for occlusion
	 * We will also only render tiles that are on the screen here and
	 * we will make sure that hidden tiles are replaced with the hidden placeholder
	 * 
	 * @param overlayList
	 * @param layer
	 */
	public void cullRender(final ArrayList<GameTile> overlayList, final int layer){
		this.tileSheet.startUse();

		int rightBound =  VictusLudus.e.currentGame.getGameDimensions().getWidth() - this.gameRenderer.game.getTileWidthS();
		int bottomBound = VictusLudus.e.currentGame.getGameDimensions().getHeight() - this.gameRenderer.game.getTileHeightS();;

		//render tile list
		for (GameTile t : this.culledTileArray) {
			int[] sc = RenderUtil.worldCoordToRawScreenCoord(t.getPos().getX(), t.getPos().getY(), t.getPos().getZ());

			//cull tiles not on the screen
			if(sc[0] < 0 || sc[1] < 0 || sc[0] > rightBound || sc[1] > bottomBound){
				continue;  //skip
			}

			//color by depth -- this is too slow to keep
			float colorModDepth = (t.getPos().getZ() - (layer-15)) / 15.0F;
			Color colorDepth = new Color(colorModDepth,colorModDepth,colorModDepth);
			colorDepth.bind();

			this.tileSheet.renderInUse(sc[0], sc[1],
					VictusLudus.e.currentGame.getTileWidthS(), VictusLudus.e.currentGame.getTileHeightS()*2,
					t.getSpriteSheetCol(), t.getSpriteSheetRow());
		}

		//render tile overlays
		for(GameTile gt:overlayList){
			if(gt.getPos().getZ() == layer){
				int[] c = RenderUtil.worldCoordToRawScreenCoord(gt.getPos().getX(), gt.getPos().getY(), layer);

				this.tileSheet.renderInUse(c[0], c[1],
						VictusLudus.e.currentGame.getTileWidthS(), VictusLudus.e.currentGame.getTileHeightS()*2,
						gt.getSpriteSheetCol(), gt.getSpriteSheetRow());
			}
		}

		this.tileSheet.endUse();
	}

	/**
	 * The Class RenderComparator.
	 */
	private static class RenderComparator implements Comparator<GameTile>{

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(final GameTile t1, final GameTile t2) {
			WorldCoord tp1 = t1.getPos();
			WorldCoord tp2 = t2.getPos();

			int sum1 = tp1.getX() + tp1.getY() + tp1.getZ();
			int sum2 = tp2.getX() + tp2.getY() + tp2.getZ();

			if(sum1 < sum2){
				return -1;
			} else if (sum1 > sum2){
				return 1;
			}

			//it is a perfect tie
			return 0;
		}
	}

	/**
	 * Register listeners.
	 */
	public void registerListeners() {
		VictusLudus.e.eventHandler.renderHandler.registerPlease(this);
	}

	/**
	 * Unregister listeners.
	 */
	public void unregisterListeners() {
		VictusLudus.e.eventHandler.renderHandler.unregisterPlease(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() {
		this.unregisterListeners();
	}

	@Override
	public void onRenderChangeEvent(final RenderEvent renderEvent) {
		if(renderEvent.getEventType() == EnumRenderEventType.CHANGE_DEPTH){
			this.calculateCulledTiles();
		}
	}
}
