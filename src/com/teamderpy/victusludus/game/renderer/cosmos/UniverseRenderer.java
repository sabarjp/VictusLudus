package com.teamderpy.victusludus.game.renderer.cosmos;

import java.util.ArrayList;

/**
 * Renders the universe
 * 
 * @author Josh
 *
 */
public class UniverseRenderer {
	/** The parent of this object */
	private CosmosRenderer cosmosRenderer;

	/**
	 * Instantiates a new universe renderer
	 * 
	 * @param cosmosRenderer the cosmos renderer that is the parent
	 */
	public UniverseRenderer(final CosmosRenderer cosmosRenderer){
		this.cosmosRenderer = cosmosRenderer;
	}

	/**
	 * Renders all the galaxies in a universe to the screen
	 */
	public void render(final ArrayList<GalaxyImage> galaxyList, final float deltaT){
		for(GalaxyImage galaxyImage:galaxyList){
			galaxyImage.render(this.cosmosRenderer, deltaT);
		}
	}

}
