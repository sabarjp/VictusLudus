
package com.teamderpy.victusludus.renderer.game;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.engine.graphics.BitmapHandler;
import com.teamderpy.victusludus.engine.graphics.SpriteSheet;
import com.teamderpy.victusludus.game.tile.GameTile;
import com.teamderpy.victusludus.gui.eventhandler.RenderListener;
import com.teamderpy.victusludus.gui.eventhandler.event.EnumRenderEventType;
import com.teamderpy.victusludus.gui.eventhandler.event.RenderEvent;

/**
 * The Class TileRenderer.
 */
/**
 * @author Joshua
 * 
 */
public class TileRenderer implements RenderListener {

	/** The tile sheet. */
	private SpriteSheet tileSheet;

	/** The game renderer. */
	private GameRenderer gameRenderer;

	/**
	 * The culled tile list. This should always be up-to-date with the tiles
	 * cleared for drawing.
	 */
	private ArrayList<GameTile> culledTileList;

	/**
	 * Instantiates a new tile renderer.
	 * 
	 * @param gameRenderer the game renderer
	 */
	public TileRenderer (final GameRenderer gameRenderer) {
		this.tileSheet = BitmapHandler.LoadSpriteSheet(VictusLudusGame.resources.getTextureAtlasTiles(), "tiles");

		this.gameRenderer = gameRenderer;

		gameRenderer.game.getGameDimensions().setTileHeight(this.tileSheet.getHeight() / this.tileSheet.getVerticalCount() / 2);
		gameRenderer.game.getGameDimensions().setTileWidth(this.tileSheet.getWidth() / this.tileSheet.getHorizontalCount());
		gameRenderer.game.getGameDimensions().setGridHeight(gameRenderer.game.getGameDimensions().getTileHeight() / 2);
		gameRenderer.game.getGameDimensions().setGridWidth(gameRenderer.game.getGameDimensions().getTileWidth() / 2);
		gameRenderer.game.getGameDimensions().setLayerHeight(gameRenderer.game.getGameDimensions().getTileHeight());

		this.registerListeners();
		this.culledTileList = new ArrayList<GameTile>();

		// initial render
		this.calculateCulledTiles();
	}

	/**
	 * Render (just a pass-through to cullRender)
	 * 
	 * @param overlayList the overlay list
	 * @param layer the layer
	 */
	@Deprecated
	public void render (final SpriteBatch batch, final float deltaT, final ArrayList<GameTile> overlayList, final int layer) {
		this.cullRender(batch, deltaT, overlayList, layer);
	}

	/**
	 * Calculate culled tiles. Hopefully this does not need to be called too
	 * often.
	 */
	private void calculateCulledTiles () {
		GameTile[][][] tileArray = this.gameRenderer.game.getMap().getMap();
		int layer = this.gameRenderer.game.getCurrentDepth();

		this.culledTileList = new ArrayList<GameTile>();

		int zdeep = GameRenderer.getMaxVisibleDepth();
		int z = layer - zdeep;

		if (z < 0) {
			z = 0;
		}

		int X1 = RenderUtil.worldCoordToRawUnscaledScreenCoord(this.gameRenderer.game, 0, this.gameRenderer.game.getMap()
			.getHeight() + zdeep, layer)[0];
		int Y1 = RenderUtil.worldCoordToRawUnscaledScreenCoord(this.gameRenderer.game, 0, 0, layer)[1];

		int X2 = this.gameRenderer.game.getGameDimensions().getTileWidth()
			+ RenderUtil.worldCoordToRawUnscaledScreenCoord(this.gameRenderer.game, this.gameRenderer.game.getMap().getWidth()
				+ zdeep, 0, layer)[0];
		int Y2 = this.gameRenderer.game.getGameDimensions().getTileHeight()
			+ RenderUtil.worldCoordToRawUnscaledScreenCoord(this.gameRenderer.game, this.gameRenderer.game.getMap().getWidth()
				+ zdeep, this.gameRenderer.game.getMap().getHeight() + zdeep, layer)[1];

		int xStep = this.gameRenderer.game.getGameDimensions().getTileWidth() / 2;
		int yStep = this.gameRenderer.game.getGameDimensions().getTileHeight() / 2;

		// System.err.println(X1 + "," + Y1 + " thru " + X2 + "," + Y2 +
// "   step:" + xStep + "," + yStep);

		// cull tiles by depth
		// split the map into a grid
		for (int j = Y1; j < Y2 - yStep; j += yStep) {
			for (int i = X1; i < X2 - xStep; i += xStep) {
				// locate the first tile that covers this spot
				// System.err.println("check " + i + "," + j);
				for (int k = layer; k > z; k--) {
					int[] wc = RenderUtil.screenCoordToRawUnscaledWorldCoord(this.gameRenderer.game, i, j, k);
					GameTile t = null;

					if (wc[0] >= 0 && wc[1] >= 0 && wc[0] < tileArray[k].length && wc[1] < tileArray[k][0].length) {
						t = tileArray[k][wc[0]][wc[1]];

						if (t != null) {
							// toss onto the render list
							if (!this.culledTileList.contains(t)) {
								// System.err.println("matched " + wc[0] + "," + wc[1] +
// "," + wc[2]);
								this.culledTileList.add(t);
							}

							break; // found a tile
						}
					}
				}
			}
		}

		// sort for proper rendering order
		Collections.sort(this.culledTileList, new EuclideanComparator());
	}

	/**
	 * Renders the tiles that we culled for occlusion We will also only render
	 * tiles that are on the screen here and we will make sure that hidden tiles
	 * are replaced with the hidden placeholder
	 * 
	 * @param overlayList
	 * @param layer
	 */
	@Deprecated
	public void cullRender (final SpriteBatch batch, final float deltaT, final ArrayList<GameTile> overlayList, final int layer) {
		int rightBound = this.gameRenderer.game.getGameDimensions().getWidth() - this.gameRenderer.game.getTileWidthScaled();
		int bottomBound = this.gameRenderer.game.getGameDimensions().getHeight() - this.gameRenderer.game.getTileHeightScaled();
		int zdeep = GameRenderer.getMaxVisibleDepth();

		// render tile list
		for (GameTile t : this.culledTileList) {
			boolean showHidden = false; // whether or not the tile is hidden

			int[] sc = RenderUtil.worldCoordToRawScreenCoord(this.gameRenderer.game, t.getPos().getX(), t.getPos().getY(), t
				.getPos().getZ());

			// skip tiles not on the screen
			if (sc[0] < 0 || sc[1] < 0 || sc[0] > rightBound || sc[1] > bottomBound) {
				continue; // skip
			}

			/*
			 * color by depth -- this may be too slow to keep float colorModDepth =
			 * (t.getPos().getZ() - (layer - zdeep)) / (float)zdeep; Color
			 * colorDepth = new Color(colorModDepth, colorModDepth, colorModDepth,
			 * 1); colorDepth.bind();
			 */

			/*
			 * calculate if a tile is hidden will need to change this to check if
			 * the tile is smothered instead of above
			 */
			if (t.getPos().getZ() < this.gameRenderer.game.getMap().getHighestPoint()) {
				if (this.gameRenderer.game.getMap().getMap()[t.getPos().getZ() + 1][t.getPos().getX()][t.getPos().getY()] != null) {
					showHidden = true;
				}
			}

			if (showHidden) {
				this.tileSheet.render(batch, sc[0], sc[1], this.gameRenderer.game.getTileWidthScaled(),
					this.gameRenderer.game.getTileHeightScaled() * 2, BitmapHandler.getSpriteSheetCol(GameTile.ID_HIDDEN, this.tileSheet),
					BitmapHandler.getSpriteSheetRow(GameTile.ID_HIDDEN, this.tileSheet));
			} else {
				this.tileSheet.render(batch, sc[0], sc[1], this.gameRenderer.game.getTileWidthScaled(),
					this.gameRenderer.game.getTileHeightScaled() * 2, BitmapHandler.getSpriteSheetCol(t.getId(), this.tileSheet),
					BitmapHandler.getSpriteSheetRow(t.getId(), this.tileSheet));
			}
		}

		// render tile overlays
		for (GameTile gt : overlayList) {
			if (gt.getPos().getZ() == layer) {
				int[] c = RenderUtil
					.worldCoordToRawScreenCoord(this.gameRenderer.game, gt.getPos().getX(), gt.getPos().getY(), layer);

				this.tileSheet.render(batch, c[0], c[1], this.gameRenderer.game.getTileWidthScaled(),
					this.gameRenderer.game.getTileHeightScaled() * 2, BitmapHandler.getSpriteSheetCol(gt.getId(), this.tileSheet),
					BitmapHandler.getSpriteSheetRow(gt.getId(), this.tileSheet));
			}
		}
	}

	/**
	 * Register listeners.
	 */
	public void registerListeners () {
		VictusLudusGame.engine.eventHandler.renderHandler.registerPlease(this);
	}

	/**
	 * Unregister listeners.
	 */
	public void unregisterListeners () {
		VictusLudusGame.engine.eventHandler.renderHandler.unregisterPlease(this);
	}

	@Override
	protected void finalize () {
		this.unregisterListeners();
	}

	@Override
	public void onRenderChangeEvent (final RenderEvent renderEvent) {
		if (renderEvent.getEventType() == EnumRenderEventType.CHANGE_DEPTH) {
			this.calculateCulledTiles();
		}
	}

	public ArrayList<GameTile> getCulledTileList () {
		return this.culledTileList;
	}

	public SpriteSheet getTileSheet () {
		return this.tileSheet;
	}
}
