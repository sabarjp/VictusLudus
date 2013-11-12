
package com.teamderpy.victusludus.renderer.game;

import com.badlogic.gdx.math.Vector3;
import com.teamderpy.victusludus.game.Game;
import com.teamderpy.victusludus.game.ScreenCoord;

/**
 * The Class RenderUtil.
 */
public final class RenderUtil {
	/**
	 * Gets a world coordinate for a tile at x,y and returns the starting
	 * position on the screen of where to start the render This is scaled and
	 * correctly offset
	 * 
	 * @param game the game
	 * @param x the x
	 * @param y the y
	 * @param layer the layer
	 * @return the screen coord
	 */
	public static ScreenCoord worldCoordToScreenCoord (final Game game, final int x, final int y, final int layer) {
		float actualX = x * (game.getTileWidthScaled() / 2) - y * (game.getTileWidthScaled() / 2);
		float actualY = y * (game.getTileHeightScaled() / 2) + x * (game.getTileHeightScaled() / 2) - layer
			* game.getLayerHeightScaled();

		return new ScreenCoord((int)actualX, (int)actualY);
	}

	/**
	 * Gets a world coordinate for a tile at x,y and returns the starting
	 * position on the screen of where to start the render This returns an array
	 * with the x and y positions to avoid object overhead
	 * 
	 * @param game the game
	 * @param x the x
	 * @param y the y
	 * @param layer the layer
	 * @return the screen coord as an array with 2 elements, x and y
	 */
	public static int[] worldCoordToRawScreenCoord (final Game game, final int x, final int y, final int layer) {
		float actualX = x * (game.getTileWidthScaled() / 2) - y * (game.getTileWidthScaled() / 2);
		float actualY = y * (game.getTileHeightScaled() / 2) + x * (game.getTileHeightScaled() / 2) - layer
			* game.getLayerHeightScaled();

		return new int[] {(int)actualX, (int)actualY, layer};
	}

	/**
	 * Gets a world coordinate for a tile at x,y and returns the starting
	 * position on the screen of where to start the render This returns an array
	 * with the x and y positions to avoid object overhead This function has no
	 * camera offset or scaling applied!
	 * 
	 * @param game the game
	 * @param x the x
	 * @param y the y
	 * @param layer the layer
	 * @return the screen coord as an array with 2 elements, x and y
	 */
	public static int[] worldCoordToRawUnscaledScreenCoord (final Game game, final int x, final int y, final int layer) {
		float actualX = x * (game.getGameDimensions().getTileWidth() / 2) - y * (game.getGameDimensions().getTileWidth() / 2);
		float actualY = y * (game.getGameDimensions().getTileHeight() / 2) + x * (game.getGameDimensions().getTileHeight() / 2)
			- layer * game.getGameDimensions().getLayerHeight();

		return new int[] {(int)actualX, (int)actualY, layer};
	}

	/**
	 * gets screen coordinates and current z layer and returns the world
	 * coordinate for a tile at x,y This returns an array with the x, y, and z
	 * positions to avoid object overhead
	 * 
	 * @param game the game
	 * @param x the x
	 * @param y the y
	 * @param layer the layer
	 * @param calculateWallFacing the calculate wall facing
	 * @return the world coord as an array with 3 elements, x y and z
	 */
	public static int[] screenCoordToRawWorldCoord (final Game game, final float x, final float y, final float layer) {
		float virtualTileX = (x) / game.getTileWidthScaled();
		float virtualTileY = (y - (layer * game.getLayerHeightScaled())) / game.getTileHeightScaled();
		int isoTileX, isoTileY;

		isoTileX = (int)Math.floor(virtualTileY + virtualTileX - 0.5);
		isoTileY = (int)Math.floor(virtualTileY - virtualTileX + 0.5);

		return new int[] {isoTileX, isoTileY, (int)layer};
	}

	/**
	 * gets screen coordinates and current z layer and returns the world
	 * coordinate for a tile at x,y This returns an array with the x, y, and z
	 * positions to avoid object overhead This function has no camera offset or
	 * scaling applied!
	 * 
	 * @param game the game
	 * @param x the x
	 * @param y the y
	 * @param layer the layer
	 * @param calculateWallFacing the calculate wall facing
	 * @return the world coord as an array with 3 elements, x y and z
	 */
	public static int[] screenCoordToRawUnscaledWorldCoord (final Game game, final float x, final float y, final float layer) {
		float virtualTileX = x / game.getGameDimensions().getTileWidth();
		float virtualTileY = (y + layer * game.getGameDimensions().getLayerHeight()) / game.getGameDimensions().getTileHeight();

		int isoTileX, isoTileY;

		isoTileX = (int)Math.floor(virtualTileY + virtualTileX);
		isoTileY = (int)Math.floor(virtualTileY - virtualTileX);

		return new int[] {isoTileX, isoTileY, (int)layer};
	}

	/**
	 * gets screen coordinates and current z layer and returns the world
	 * coordinate for a tile at x,y along with the wall that is being faced
	 * 
	 * @param game the game
	 * @param x the x
	 * @param y the y
	 * @param layer the layer
	 * @param calculateWallFacing the calculate wall facing
	 * @return the world coord selection
	 */
	public static Vector3 screenCoordToWorldCoord (final Game game, final float x, final float y, final int layer,
		final boolean calculateWallFacing) {
		Vector3 w = new Vector3(0, 0, 0);

		float virtualTileX = (x) / game.getTileWidthScaled();
		float virtualTileY = (y - (layer * game.getLayerHeightScaled())) / game.getTileHeightScaled();

		int isoTileX, isoTileY;

		isoTileX = (int)Math.floor(virtualTileY + virtualTileX - 0.5);
		isoTileY = (int)Math.floor(virtualTileY - virtualTileX + 0.5);

		if (calculateWallFacing) {
			float segmentX = virtualTileX;
			float segmentY = virtualTileY;

			if ((isoTileX + isoTileY) % 2 == 1) {
				segmentX -= 0.5;
				segmentY -= 0.5;
			}

			segmentX -= (int)segmentX;
			segmentY -= (int)segmentY;

			if (segmentX < 0) {
				segmentX += 1.0;
			}

			if (segmentY < 0) {
				segmentY += 1.0;
			}

		}

		w.x = isoTileX;
		w.y = isoTileY;

		return w;
	}
}
