package com.teamderpy.victusludus.game.renderer;

import org.newdawn.slick.SpriteSheet;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.game.EnumWallFacing;
import com.teamderpy.victusludus.game.ScreenCoord;
import com.teamderpy.victusludus.game.tile.GameTile;

// TODO: Auto-generated Javadoc
/**
 * The Class WallRenderer.
 */
public class WallRenderer {

	/** The wall sheet. */
	private SpriteSheet wallSheet;

	/** The game renderer. */
	public GameRenderer gameRenderer;

	/**
	 * Instantiates a new wall renderer.
	 *
	 * @param gameRenderer the game renderer
	 */
	public WallRenderer(final GameRenderer gameRenderer){
		this.wallSheet = BitmapHandler.LoadSpriteSheet("res/sprites/walls.png");

		this.gameRenderer = gameRenderer;

		gameRenderer.game.getGameDimensions().setWallHeight(this.wallSheet.getHeight() / this.wallSheet.getVerticalCount());
		gameRenderer.game.getGameDimensions().setWallWidth(this.wallSheet.getWidth() / this.wallSheet.getHorizontalCount());
		//gameRenderer.game.getGameDimensions().setLayerHeight(gameRenderer.game.getWallHeightS());
	}

	/**
	 * Render.
	 *
	 * @param tileArray the tile array
	 * @param layer the layer
	 */
	public void render(final GameTile[][][] tileArray, final int layer){
		int x = 0;
		int y = 0;

		this.wallSheet.startUse();

		while(x < tileArray[layer].length){
			y = 0;

			while(y < tileArray[layer][x].length){
				ScreenCoord c = RenderUtil.worldCoordToScreenCoord(x, y, VictusLudus.e.currentGame.getCurrentDepth());

				if(c.x < 0 || c.y < 0 || c.x > VictusLudus.e.currentGame.getGameDimensions().getWidth() || c.y > VictusLudus.e.currentGame.getGameDimensions().getHeight()){
					y++;
					continue;  //do not render walls off the screen!
				}

				GameTile t = tileArray[layer][x][y];

				if(t != null){
					if(t.getNorthEastWall() != null){
						ScreenCoord wallCoord = RenderUtil.screenCoordOffsetForWall(c.x, c.y, EnumWallFacing.NORTH_EAST);
						this.wallSheet.renderInUse(wallCoord.x,  wallCoord.y,
								VictusLudus.e.currentGame.getWallWidthS(), VictusLudus.e.currentGame.getWallHeightS(),
								t.getNorthEastWall().getSpriteSheetCol(), t.getNorthEastWall().getSpriteSheetRow());
					}


					if(t.getNorthWestWall() != null){
						ScreenCoord wallCoord = RenderUtil.screenCoordOffsetForWall(c.x, c.y, EnumWallFacing.NORTH_WEST);
						this.wallSheet.renderInUse(wallCoord.x,  wallCoord.y,
								VictusLudus.e.currentGame.getWallWidthS(), VictusLudus.e.currentGame.getWallHeightS(),
								t.getNorthWestWall().getSpriteSheetCol(), t.getNorthWestWall().getSpriteSheetRow());
					}
				}

				y++;
			}

			x++;
		}

		this.wallSheet.endUse();
	}
}
