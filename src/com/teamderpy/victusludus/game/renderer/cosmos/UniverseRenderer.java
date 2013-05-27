package com.teamderpy.victusludus.game.renderer.cosmos;

import java.util.ArrayList;

/**
 * Renders the universe
 * 
 * @author Josh
 *
 */
public class UniverseRenderer {

	/**
	 * Renders all the galaxies in a universe to the screen
	 */
	public void renderGalaxies(final ArrayList<GalaxyImage> galaxyList, final float deltaT){
		for(GalaxyImage galaxyImage:galaxyList){
			galaxyImage.render(deltaT);
		}
	}

	/**
	 * Renders all the stars in a universe to the screen
	 */
	public void renderStars(final ArrayList<StarImage> starList, final float deltaT){
		for(StarImage starImage:starList){
			starImage.render(deltaT);
		}
	}


}
