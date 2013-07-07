
package com.teamderpy.victusludus.renderer.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamderpy.victusludus.game.Game;
import com.teamderpy.victusludus.game.map.Map;
import com.teamderpy.victusludus.renderer.common.BackgroundRenderer;
import com.teamderpy.victusludus.renderer.common.DebugRenderer;

/**
 * The Class GameRenderer.
 */
public class GameRenderer {
	/** The tile renderer. */
	private TileRenderer tileRenderer;

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
	public GameRenderer (final Game game) {
		this.game = game;

		this.tileRenderer = new TileRenderer(this);
		this.entityRenderer = new EntityRenderer(this);
		this.objectRenderer = new EuclideanObjectRenderer(this);
		this.bgRenderer = new BackgroundRenderer(game.getGameDimensions(), new Color(0.0589F, 0.0198F, 0.11F, 1));
		this.debugRenderer = new DebugRenderer(game.getGameDimensions());
	}

	/**
	 * Render game layer and some layers below it
	 * 
	 * @param map the map to render
	 * @param layer the layer to render up to
	 */
	public void renderGameLayer (final SpriteBatch batch, final float deltaT, final Map map, final int layer) {
		this.bgRenderer.render(batch, deltaT);

		// this.tileRenderer.render(batch, deltaT, map.getTileOverlayList(),
// layer);
		// this.wallRenderer.render(batch, deltaT, map.getMap(), layer);
		// this.entityRenderer.render(batch, deltaT, map.getEntities(), layer);

		// NO, NOT ON EVERY FRAME
		// this.entityRenderer.calculateCulledEntities(map.getEntities(), layer);

		batch.enableBlending();
		this.objectRenderer.render(batch, deltaT, this.tileRenderer.getCulledTileList(), this.entityRenderer.getCulledEntityList(),
			map.getTileOverlayList(), layer);
		batch.disableBlending();
		// map.getLightMap().renderLightMap(batch, deltaT, layer);
		// this.debugRenderer.render(batch, deltaT);

		// this.renderBuffer.draw(0, 0);
	}

	/**
	 * Unregister listeners.
	 */
	public void unregisterListeners () {
		this.tileRenderer.unregisterListeners();
	}

	public static int getMaxVisibleDepth () {
		return GameRenderer.MAX_VISIBLE_DEPTH;
	}

	public TileRenderer getTileRenderer () {
		return this.tileRenderer;
	}

	public EntityRenderer getEntityRenderer () {
		return this.entityRenderer;
	}

	public EuclideanObjectRenderer getObjectRenderer () {
		return this.objectRenderer;
	}

	public BackgroundRenderer getBgRenderer () {
		return this.bgRenderer;
	}
}
