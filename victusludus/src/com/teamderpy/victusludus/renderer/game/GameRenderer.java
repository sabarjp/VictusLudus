
package com.teamderpy.victusludus.renderer.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.teamderpy.victusludus.game.Game;
import com.teamderpy.victusludus.renderer.common.BackgroundRenderer;
import com.teamderpy.victusludus.renderer.common.DebugRenderer;

/**
 * The Class GameRenderer.
 */
public class GameRenderer {
	/** The background renderer. */
	private BackgroundRenderer bgRenderer;

	/** The debug overlay renderer. */
	private DebugRenderer debugRenderer;

	/** The map renderer */
	private MapRenderer mapRenderer;

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

		this.bgRenderer = new BackgroundRenderer(game.getGameDimensions(), new Color(0.0589F, 0.0198F, 0.11F, 1));
		this.debugRenderer = new DebugRenderer(game.getGameDimensions());
		this.mapRenderer = new MapRenderer(game, game.getMap());
	}

	public void render (final SpriteBatch spriteBatch, final ModelBatch modelBatch, final float deltaT) {
		this.mapRenderer.render(modelBatch, deltaT);
	}

	/**
	 * Unregister listeners.
	 */
	public void unregisterListeners () {

	}

	public static int getMaxVisibleDepth () {
		return GameRenderer.MAX_VISIBLE_DEPTH;
	}

	public BackgroundRenderer getBgRenderer () {
		return this.bgRenderer;
	}
}
