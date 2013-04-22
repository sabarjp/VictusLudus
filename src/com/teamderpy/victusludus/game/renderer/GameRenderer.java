package com.teamderpy.victusludus.game.renderer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.game.Game;
import com.teamderpy.victusludus.game.map.Map;
import com.teamderpy.victusludus.gui.eventhandler.ResizeListener;
import com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class GameRenderer.
 */
public class GameRenderer implements ResizeListener{

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

	/** The render buffer, before it is actually rendered */
	private Image renderBuffer = null;

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

		try {
			this.setRenderBuffer(Image.createOffscreenImage(VictusLudus.e.X_RESOLUTION(), VictusLudus.e.Y_RESOLUTION(), Image.FILTER_NEAREST));
		} catch (SlickException e) {
			e.printStackTrace();
		}

		this.registerListeners();
	}

	/**
	 * Render game layer and some layers below it
	 *
	 * @param map the map to render
	 * @param layer the layer to render up to
	 */
	public void renderGameLayer(final Map map, final int layer){
		this.bgRenderer.render();
		this.tileRenderer.render(map.getMap(), map.getTileOverlayList(), layer);
		//this.wallRenderer.render(map.getMap(), layer);
		//this.entityRenderer.render(map.getEntities(), layer);
		//map.getLightMap().renderLightMap(layer);

		//this.renderBuffer.draw(0, 0);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.ResizeListener#onResize(com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent)
	 */
	@Override
	public void onResize(final ResizeEvent resizeEvent) {
		try {
			this.setRenderBuffer(Image.createOffscreenImage(VictusLudus.e.X_RESOLUTION(), VictusLudus.e.Y_RESOLUTION(), Image.FILTER_NEAREST));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Register listeners.
	 */
	public void registerListeners() {
		VictusLudus.e.eventHandler.resizeHandler.registerPlease(this);
	}

	/**
	 * Unregister listeners.
	 */
	public void unregisterListeners() {
		VictusLudus.e.eventHandler.resizeHandler.unregisterPlease(this);
		this.tileRenderer.unregisterListeners();
	}

	/**
	 * Gets the render buffer.
	 *
	 * @return the render buffer
	 */
	public Image getRenderBuffer() {
		return this.renderBuffer;
	}

	private void setRenderBuffer(final Image renderBuffer) {
		this.renderBuffer = renderBuffer;
	}
}
