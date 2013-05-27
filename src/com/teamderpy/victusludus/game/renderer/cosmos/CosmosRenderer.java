package com.teamderpy.victusludus.game.renderer.cosmos;

import java.util.ArrayList;

import org.newdawn.slick.SpriteSheet;

import com.teamderpy.victusludus.engine.graphics.BitmapHandler;
import com.teamderpy.victusludus.game.cosmos.Cosmos;
import com.teamderpy.victusludus.game.cosmos.Galaxy;
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

	/** The sprite sheet for galaxies */
	protected SpriteSheet spriteSheetGalaxy;

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
		this.bgRenderer = new BackgroundRenderer(cosmos.getGameDimensions(), "res/sprites/universe.png");
		this.debugRenderer = new DebugRenderer(cosmos.getGameDimensions());
		this.universeRenderer = new UniverseRenderer(this);

		this.galaxyList = new ArrayList<GalaxyImage>();

		for(Galaxy g:cosmos.getUniverse().getGalaxies()){
			this.galaxyList.add(new GalaxyImage(g, this));
		}
	}


	/**
	 * Render the cosmos
	 */
	public void render(final float deltaT){
		this.bgRenderer.render();
		this.debugRenderer.render();

		this.universeRenderer.render(this.galaxyList, deltaT);
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
