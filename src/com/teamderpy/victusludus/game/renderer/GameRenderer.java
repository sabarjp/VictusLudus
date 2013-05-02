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

	/** The euclidean object renderer. */
	private EuclideanObjectRenderer objectRenderer;

	/** The bg renderer. */
	private BackgroundRenderer bgRenderer;

	/** The debug overlay renderer. */
	private DebugRenderer debugRenderer;

	/** The game. */
	public Game game;

	/** The deepest that we can see */
	private static final int MAX_VISIBLE_DEPTH = 10;

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
		this.objectRenderer = new EuclideanObjectRenderer(this);
		this.bgRenderer = new BackgroundRenderer(this, new Color(13,5,28));
		this.debugRenderer = new DebugRenderer(this);
	}

	/**
	 * Render game layer and some layers below it
	 *
	 * @param map the map to render
	 * @param layer the layer to render up to
	 */
	public void renderGameLayer(final Map map, final int layer){
		this.bgRenderer.render();
		//this.tileRenderer.render(map.getTileOverlayList(), layer);
		//this.wallRenderer.render(map.getMap(), layer);
		//this.entityRenderer.render(map.getEntities(), layer);
		this.entityRenderer.calculateCulledEntities(map.getEntities(), layer);
		this.objectRenderer.render(this.tileRenderer.getCulledTileList(), this.entityRenderer.getCulledEntityList(), map.getTileOverlayList(), layer);
		map.getLightMap().renderLightMap(layer);
		this.debugRenderer.render();

		//this.renderBuffer.draw(0, 0);
	}

	/**
	 * Unregister listeners.
	 */
	public void unregisterListeners() {
		this.tileRenderer.unregisterListeners();
	}

	public static int getMaxVisibleDepth() {
		return GameRenderer.MAX_VISIBLE_DEPTH;
	}

	public TileRenderer getTileRenderer() {
		return this.tileRenderer;
	}

	public WallRenderer getWallRenderer() {
		return this.wallRenderer;
	}

	public EntityRenderer getEntityRenderer() {
		return this.entityRenderer;
	}

	public EuclideanObjectRenderer getObjectRenderer() {
		return this.objectRenderer;
	}

	public BackgroundRenderer getBgRenderer() {
		return this.bgRenderer;
	}
}
