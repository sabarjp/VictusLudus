package com.teamderpy.victusludus.game.renderer;

import com.teamderpy.victusludus.game.cosmos.Cosmos;

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

	/** The game. */
	public Cosmos cosmos;

	/**
	 * Instantiates a new game renderer.
	 *
	 * @param game the game
	 */
	public CosmosRenderer(final Cosmos cosmos){
		this.cosmos = cosmos;

		this.bgRenderer = new BackgroundRenderer(cosmos.getGameDimensions(), "res/sprites/universe.png");
		this.debugRenderer = new DebugRenderer(cosmos.getGameDimensions());
		this.universeRenderer = new UniverseRenderer(this);
	}


	/**
	 * Render the cosmos
	 */
	public void render(final float deltaT){
		this.bgRenderer.render();
		this.debugRenderer.render();

		this.universeRenderer.render(this.cosmos.getUniverse(), deltaT);
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
