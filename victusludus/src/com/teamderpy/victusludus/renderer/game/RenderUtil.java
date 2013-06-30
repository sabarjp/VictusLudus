package com.teamderpy.victusludus.renderer.game;

import com.teamderpy.victusludus.game.EnumWallFacing;
import com.teamderpy.victusludus.game.Game;
import com.teamderpy.victusludus.game.ScreenCoord;
import com.teamderpy.victusludus.game.WorldCoordSelection;


/**
 * The Class RenderUtil.
 */
public final class RenderUtil {
	/**
	 * Screen coord offset for wall.
	 *
	 * @param game the game
	 * @param x the x
	 * @param y the y
	 * @param facing the facing
	 * @return the screen coord
	 */
	public static ScreenCoord screenCoordOffsetForWall(final Game game, final int x, final int y, final EnumWallFacing facing){
		ScreenCoord c = new ScreenCoord(x,y);

		if(facing == EnumWallFacing.NORTH_EAST){
			c.x = c.x + game.getWallWidthS();
			c.y = c.y - (int)(game.getWallHeightS()-game.getWallWidthS()/2+1*game.getGameCamera().getZoom());
		} else if(facing == EnumWallFacing.NORTH_WEST){
			c.y = c.y - (int)(game.getWallHeightS()-game.getWallWidthS()/2+1*game.getGameCamera().getZoom());
		} else if(facing == EnumWallFacing.SOUTH_EAST){
			c.x = c.x + game.getWallWidthS();
			c.y = c.y - (int)(game.getWallHeightS()-game.getWallWidthS()+1*game.getGameCamera().getZoom());
		} else if(facing == EnumWallFacing.SOUTH_WEST){
			c.y = c.y - (int)(game.getWallHeightS()-game.getWallWidthS()+1*game.getGameCamera().getZoom());
		}

		return c;
	}

	/**
	 * Gets a world coordinate for a tile at x,y and returns the starting position on the screen of where to start the render
	 * This is scaled and correctly offset
	 *
	 * @param game the game
	 * @param x the x
	 * @param y the y
	 * @param layer the layer
	 * @return the screen coord
	 */
	public static ScreenCoord worldCoordToScreenCoord(final Game game, final int x, final int y, final int layer){
		float actualX = x*(game.getTileWidthS()/2)-y*(game.getTileWidthS()/2)+game.getGameCamera().getOffsetX();
		float actualY = y*(game.getTileHeightS()/2)+x*(game.getTileHeightS()/2)+game.getGameCamera().getOffsetY()-layer*game.getLayerHeightS();

		return new ScreenCoord((int)actualX, (int)actualY);
	}

	/**
	 * Gets a world coordinate for a tile at x,y and returns the starting position on the screen of where to start the render
	 * This returns an array with the x and y positions to avoid object overhead
	 *
	 * @param game the game
	 * @param x the x
	 * @param y the y
	 * @param layer the layer
	 * @return the screen coord as an array with 2 elements, x and y
	 */
	public static int[] worldCoordToRawScreenCoord(final Game game, final int x, final int y, final int layer){
		float actualX = x*(game.getTileWidthS()/2)-y*(game.getTileWidthS()/2)+game.getGameCamera().getOffsetX();
		float actualY = y*(game.getTileHeightS()/2)+x*(game.getTileHeightS()/2)+game.getGameCamera().getOffsetY()-layer*game.getLayerHeightS();

		return new int[]{(int)actualX, (int)actualY, layer};
	}

	/**
	 * Gets a world coordinate for a tile at x,y and returns the starting position on the screen of where to start the render
	 * This returns an array with the x and y positions to avoid object overhead
	 * This function has no camera offset or scaling applied!
	 *
	 * @param game the game
	 * @param x the x
	 * @param y the y
	 * @param layer the layer
	 * @return the screen coord as an array with 2 elements, x and y
	 */
	public static int[] worldCoordToRawUnscaledScreenCoord(final Game game, final int x, final int y, final int layer){
		float actualX = x*(game.getGameDimensions().getTileWidth()/2)-y*(game.getGameDimensions().getTileWidth()/2);
		float actualY = y*(game.getGameDimensions().getTileHeight()/2)+x*(game.getGameDimensions().getTileHeight()/2)-layer*game.getGameDimensions().getLayerHeight();

		return new int[]{(int)actualX, (int)actualY, layer};
	}

	/**
	 * gets screen coordinates and current z layer and returns the world coordinate for a tile at x,y
	 * This returns an array with the x, y, and z positions to avoid object overhead
	 *
	 * @param game the game
	 * @param x the x
	 * @param y the y
	 * @param layer the layer
	 * @param calculateWallFacing the calculate wall facing
	 * @return the world coord as an array with 3 elements, x y and z
	 */
	public static int[] screenCoordToRawWorldCoord(final Game game, final float x, final float y, final float layer){
		float virtualTileX = (x - game.getGameCamera().getOffsetX()) / game.getTileWidthS();
		float virtualTileY = (y - (game.getGameCamera().getOffsetY() - layer*game.getLayerHeightS())) / game.getTileHeightS();
		int isoTileX, isoTileY;

		isoTileX = (int)Math.floor(virtualTileY + virtualTileX - 0.5);
		isoTileY = (int)Math.floor(virtualTileY - virtualTileX + 0.5);

		return new int[]{isoTileX, isoTileY, (int) layer};
	}

	/**
	 * gets screen coordinates and current z layer and returns the world coordinate for a tile at x,y
	 * This returns an array with the x, y, and z positions to avoid object overhead
	 * This function has no camera offset or scaling applied!
	 *
	 * @param game the game
	 * @param x the x
	 * @param y the y
	 * @param layer the layer
	 * @param calculateWallFacing the calculate wall facing
	 * @return the world coord as an array with 3 elements, x y and z
	 */
	public static int[] screenCoordToRawUnscaledWorldCoord(final Game game, final float x, final float y, final float layer){
		float virtualTileX = x / game.getGameDimensions().getTileWidth();
		float virtualTileY = (y + layer*game.getGameDimensions().getLayerHeight()) / game.getGameDimensions().getTileHeight();

		int isoTileX, isoTileY;

		isoTileX = (int)Math.floor(virtualTileY + virtualTileX);
		isoTileY = (int)Math.floor(virtualTileY - virtualTileX);

		return new int[]{isoTileX, isoTileY, (int) layer};
	}

	/**
	 * gets screen coordinates and current z layer and returns the world coordinate for a tile at x,y
	 * along with the wall that is being faced
	 *
	 * @param game the game
	 * @param x the x
	 * @param y the y
	 * @param layer the layer
	 * @param calculateWallFacing the calculate wall facing
	 * @return the world coord selection
	 */
	public static WorldCoordSelection screenCoordToWorldCoord(final Game game, final float x, final float y, final int layer, final boolean calculateWallFacing){
		WorldCoordSelection w = new WorldCoordSelection(0,0);

		float virtualTileX = (x - game.getGameCamera().getOffsetX()) / game.getTileWidthS();
		float virtualTileY = (y - (game.getGameCamera().getOffsetY() - layer*game.getLayerHeightS())) / game.getTileHeightS();

		int isoTileX, isoTileY;

		isoTileX = (int)Math.floor(virtualTileY + virtualTileX - 0.5);
		isoTileY = (int)Math.floor(virtualTileY - virtualTileX + 0.5);

		if(calculateWallFacing){
			float segmentX = virtualTileX;
			float segmentY = virtualTileY;

			if((isoTileX + isoTileY) % 2 == 1){
				segmentX -= 0.5;
				segmentY -= 0.5;
			}

			segmentX -= (int)segmentX;
			segmentY -= (int)segmentY;

			if(segmentX < 0) {
				segmentX += 1.0;
			}

			if(segmentY < 0) {
				segmentY += 1.0;
			}

			if(segmentX >= 0.5){
				if(segmentY >= 0.5){
					w.wallSubSectionFacing = EnumWallFacing.SOUTH_EAST;
				} else {
					w.wallSubSectionFacing = EnumWallFacing.NORTH_EAST;
				}
			} else {
				if(segmentY >= 0.5){
					w.wallSubSectionFacing = EnumWallFacing.SOUTH_WEST;
				} else {
					w.wallSubSectionFacing = EnumWallFacing.NORTH_WEST;
				}
			}
		}

		w.x = isoTileX;
		w.y = isoTileY;

		return w;
	}
}