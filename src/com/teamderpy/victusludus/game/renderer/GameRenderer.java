package com.teamderpy.victusludus.game.renderer;

import org.newdawn.slick.Color;
import com.teamderpy.victusludus.game.Game;
import com.teamderpy.victusludus.game.map.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class GameRenderer.
 */
public class GameRenderer{

	/** The tile renderer. */
	private TileRenderer tileRenderer;

	/** The wall renderer. */
	private WallRenderer wallRenderer;

	/** The entity renderer. */
	private EntityRenderer entityRenderer;

	/** The bg renderer. */
	private BackgroundRenderer bgRenderer;

	/** The game. */
	public Game game;

	/** The deepest that we can see */
	private static final int MAX_VISIBLE_DEPTH = 15;

	/**
	 * Instantiates a new game renderer.
	 *
	 * @param game the game
	 */
	public GameRenderer(final Game game){
		this.game = game;

		this.tileRenderer = new TileRenderer(this);
		this.wallRenderer = new WallRenderer(this);
		this.entityRenderer = new EntityRenderer(this);
		this.bgRenderer = new BackgroundRenderer(this, new Color(13,5,28));
	}

	/**
	 * Render game layer and some layers below it
	 *
	 * @param map the map to render
	 * @param layer the layer to render up to
	 */
	public void renderGameLayer(final Map map, final int layer){
		this.bgRenderer.render();
		this.tileRenderer.render(map.getTileOverlayList(), layer);
		//this.wallRenderer.render(map.getMap(), layer);
		this.entityRenderer.render(map.getEntities(), layer);
		map.getLightMap().renderLightMap(layer);

		//this.renderBuffer.draw(0, 0);
	}

	/**
	 * Unregister listeners.
	 */
	public void unregisterListeners() {
		this.tileRenderer.unregisterListeners();
	}

	public static int getMaxVisibleDepth() {
		return MAX_VISIBLE_DEPTH;
	}
}
