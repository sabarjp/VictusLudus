
package com.teamderpy.victusludus.game.renderer.cosmos;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.VictusRuntimeException;
import com.teamderpy.victusludus.game.GameDimensions;
import com.teamderpy.victusludus.game.cosmos.Star;

/**
 * Renders the universe
 * 
 * @author Josh
 */
public class UniverseRenderer {
	GameDimensions gameDimensions;

	public UniverseRenderer (final GameDimensions gameDimensions) {
		this.gameDimensions = gameDimensions;
	}

	/** Renders all the galaxies in a universe to the screen */
	public void renderGalaxies (final ArrayList<GalaxyImage> galaxyList, final SpriteBatch batch, final float deltaT) {
		for (GalaxyImage galaxyImage : galaxyList) {
			galaxyImage.render(batch, deltaT);
		}
	}

	/** Renders all the stars in a universe to the screen */
	public void renderStars (final ArrayList<StarImage> starList, final SpriteBatch batch, final float deltaT) {
		for (StarImage starImage : starList) {
			starImage.render(batch, deltaT);
		}
	}

	/** Renders all the planets in a galaxy to the screen */
	public void renderPlanets (final ArrayList<PlanetImage> planetList, final SpriteBatch batch, final float deltaT) {
		for (PlanetImage planetImage : planetList) {
			planetImage.render(batch, deltaT);
		}
	}

	/** Renders a large star to the screen in the solar system view */
	public void renderBigStar (final Star star, final SpriteBatch batch, final float deltaT) {
		Sprite sprite = VictusLudusGame.resources.getTextureAtlasCosmos().createSprite(Star.BIG_STAR_IMAGE_PATH);
		if (sprite == null) {
			throw new VictusRuntimeException("Failed to load sprite: " + Star.BIG_STAR_IMAGE_PATH);
		}

		double scale = star.getRelativeScale();

		int spriteWidth = (int)(sprite.getWidth() * scale);
		int spriteHeight = (int)(sprite.getHeight() * scale);
		int offset = Math.max(spriteWidth - 128, 0);

		System.err.println(scale + " " + offset);

		sprite.setColor(star.getStarColor());

		sprite.setBounds(0 - offset, this.gameDimensions.getHeight() / 2 - (spriteHeight / 2), spriteWidth, spriteHeight);
		sprite.draw(batch);
	}
}
