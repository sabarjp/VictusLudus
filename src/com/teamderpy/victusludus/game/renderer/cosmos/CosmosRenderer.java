package com.teamderpy.victusludus.game.renderer.cosmos;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.SpriteSheet;

import com.teamderpy.victusludus.engine.graphics.BitmapHandler;
import com.teamderpy.victusludus.game.cosmos.Cosmos;
import com.teamderpy.victusludus.game.cosmos.EnumCosmosMode;
import com.teamderpy.victusludus.game.cosmos.Galaxy;
import com.teamderpy.victusludus.game.cosmos.Star;
import com.teamderpy.victusludus.game.renderer.BackgroundRenderer;
import com.teamderpy.victusludus.game.renderer.DebugRenderer;

// TODO: Auto-generated Javadoc
/**
 * The Class CosmosRenderer.
 */
public class CosmosRenderer{
	/** The bg renderer. */
	private BackgroundRenderer bgRenderer;

	/** The debug overlay renderer. */
	private DebugRenderer debugRenderer;

	/** Renders aspects of the universe */
	private UniverseRenderer universeRenderer;

	/** A list of galaxies */
	protected ArrayList<GalaxyImage> galaxyList;

	/** A list of stars */
	protected ArrayList<StarImage> starList;

	/** The sprite sheet for galaxies */
	protected SpriteSheet spriteSheetGalaxy;

	/** The sprite sheet for stars */
	protected SpriteSheet spriteSheetStar;

	/** The game. */
	public Cosmos cosmos;

	/**
	 * Instantiates a new game renderer.
	 *
	 * @param game the game
	 */
	public CosmosRenderer(final Cosmos cosmos){
		this.cosmos = cosmos;

		this.spriteSheetGalaxy = BitmapHandler.LoadSpriteSheet("res/sprites/galaxies.png", 4, 4);
		this.spriteSheetStar = BitmapHandler.LoadSpriteSheet("res/sprites/stars_small.png", 8, 4);

		this.bgRenderer = new BackgroundRenderer(this.cosmos.getGameDimensions(), Color.black);
		this.debugRenderer = new DebugRenderer(cosmos.getGameDimensions());
		this.universeRenderer = new UniverseRenderer();

		this.changePerspective(EnumCosmosMode.UNIVERSE_PERSPECTIVE);
	}


	/**
	 * Render the cosmos
	 */
	public void render(final float deltaT){
		if(this.bgRenderer != null){
			this.bgRenderer.render();
		}

		if(this.debugRenderer != null){
			this.debugRenderer.render();
		}

		switch (this.cosmos.getCurrentPerspective()){
			case UNIVERSE_PERSPECTIVE:
				this.universeRenderer.renderGalaxies(this.galaxyList, deltaT);
				break;
			case GALAXY_PERSPECTIVE:
				this.universeRenderer.renderStars(this.starList, deltaT);
				break;
			case STAR_PERSPECTIVE:
				break;
			case PLANET_PERSPECTIVE:
				break;
		}
	}

	/**
	 * Changes the current perspective
	 * 
	 * @param newPerspective the new perspective to render
	 */
	public void changePerspective(final EnumCosmosMode newPerspective){
		//clear out obsolete objects
		if(this.galaxyList != null){
			for(GalaxyImage g:this.galaxyList){
				g.getActionArea().unregisterListeners();
			}

			this.galaxyList = null;
		}

		if(this.starList != null){
			for(StarImage s:this.starList){
				s.getActionArea().unregisterListeners();
			}

			this.galaxyList = null;
		}

		//change perspectives
		switch (newPerspective){
			case UNIVERSE_PERSPECTIVE:
				this.galaxyList = new ArrayList<GalaxyImage>();

				for(Galaxy g:this.cosmos.getUniverse().getGalaxies()){
					this.galaxyList.add(new GalaxyImage(g, this));
				}

				this.bgRenderer.setBgImage("res/sprites/universe.png");
				this.bgRenderer.setFlipTiling(false);
				this.bgRenderer.setStretchingImage(false);

				break;
			case GALAXY_PERSPECTIVE:
				this.starList = new ArrayList<StarImage>();

				for(Star s:this.cosmos.getGalaxy().getStars()){
					this.starList.add(new StarImage(s, this));
				}

				//this.bgRenderer.destroy();
				//this.bgRenderer = null;
				//this.bgRenderer.setBgImage("res/sprites/nebulae.png");
				//this.bgRenderer.setFlipTiling(true);
				this.bgRenderer.createBackgroundNebula();
				this.bgRenderer.setStretchingImage(true);
				//this.bgRenderer = new BackgroundRenderer(this.cosmos.getGameDimensions(), new Color(100,100,100));

				break;
			case STAR_PERSPECTIVE:
				break;
			case PLANET_PERSPECTIVE:
				break;
		}

		this.cosmos.setCurrentPerspective(newPerspective);
	}


	/**
	 * Unregister listeners.
	 */
	public void unregisterListeners() {

	}

	public BackgroundRenderer getBgRenderer() {
		return this.bgRenderer;
	}
}
