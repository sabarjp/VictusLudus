
package com.teamderpy.victusludus.game.renderer.cosmos;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** Renders the universe
 * 
 * @author Josh */
public class UniverseRenderer {

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

}
