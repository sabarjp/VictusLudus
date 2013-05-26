package com.teamderpy.victusludus.game.renderer;

import org.newdawn.slick.SpriteSheet;

import com.teamderpy.victusludus.game.cosmos.Galaxy;
import com.teamderpy.victusludus.game.cosmos.Universe;

/**
 * Renders the universe
 * 
 * @author Josh
 *
 */
public class UniverseRenderer {
	/** The sprite sheet that contains... sprites */
	private SpriteSheet spriteSheet;

	/** The parent of this object */
	private CosmosRenderer cosmosRenderer;

	/**
	 * Instantiates a new universe renderer
	 * 
	 * @param cosmosRenderer the cosmos renderer that is the parent
	 */
	public UniverseRenderer(final CosmosRenderer cosmosRenderer){
		this.spriteSheet = BitmapHandler.LoadSpriteSheet("res/sprites/galaxies.png", 16, 1);

		this.cosmosRenderer = cosmosRenderer;
	}

	/**
	 * Renders all the galaxies in a universe to the screen
	 */
	public void render(final Universe universe){
		//now to render!

		int spriteWidth = this.spriteSheet.getWidth() / this.spriteSheet.getHorizontalCount();
		int spriteHeight = this.spriteSheet.getHeight() / this.spriteSheet.getVerticalCount();

		spriteWidth *= 2;
		spriteHeight *= 2;

		this.spriteSheet.startUse();

		for(Galaxy galaxy:universe.getGalaxies()){
			int x = (int) ((this.cosmosRenderer.cosmos.getGameDimensions().getWidth() - spriteWidth) / universe.getDiameter() * (galaxy.getxPosition() - galaxy.getRadius()));
			int y = (int) ((this.cosmosRenderer.cosmos.getGameDimensions().getHeight() - spriteHeight) / universe.getDiameter() * (galaxy.getyPosition() - galaxy.getRadius()));

			this.spriteSheet.renderInUse(x, y,
					spriteWidth,
					spriteHeight,
					(float) galaxy.getRotation(),
					//0,
					BitmapHandler.getSpriteSheetCol(galaxy.getGalaxyType().getSpriteIndex(), this.spriteSheet),
					BitmapHandler.getSpriteSheetRow(galaxy.getGalaxyType().getSpriteIndex(), this.spriteSheet));
		}

		this.spriteSheet.endUse();
	}

}
