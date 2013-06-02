package com.teamderpy.victusludus.game;

import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.gui.eventhandler.ResizeListener;
import com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent;


/**
 * The Class GameDimensions.
 */
public class GameDimensions implements ResizeListener{

	/** The grid width. */
	private int gridWidth = 16;

	/** The grid height. */
	private int gridHeight = 16;

	/** The tile height. */
	private int tileHeight = 32;

	/** The tile width. */
	private int tileWidth = 32;

	/** The wall width. */
	private int wallWidth = 16;

	/** The wall height. */
	private int wallHeight = 32;

	/** The layer height. */
	private int layerHeight = 16;

	/** The width. */
	private int width;

	/** The height. */
	private int height;

	/**
	 * Instantiates a new game dimensions.
	 */
	public GameDimensions(){
		this.registerListeners();
	}

	/**
	 * Gets the grid width.
	 *
	 * @return the grid width
	 */
	public int getGridWidth() {
		return this.gridWidth;
	}

	/**
	 * Sets the grid width.
	 *
	 * @param gridWidth the new grid width
	 */
	public void setGridWidth(final int gridWidth) {
		this.gridWidth = gridWidth;
	}

	/**
	 * Gets the grid height.
	 *
	 * @return the grid height
	 */
	public int getGridHeight() {
		return this.gridHeight;
	}

	/**
	 * Sets the grid height.
	 *
	 * @param gridHeight the new grid height
	 */
	public void setGridHeight(final int gridHeight) {
		this.gridHeight = gridHeight;
	}

	/**
	 * Gets the tile height.
	 *
	 * @return the tile height
	 */
	public int getTileHeight() {
		return this.tileHeight;
	}

	/**
	 * Sets the tile height.
	 *
	 * @param tileHeight the new tile height
	 */
	public void setTileHeight(final int tileHeight) {
		this.tileHeight = tileHeight;
	}

	/**
	 * Gets the tile width.
	 *
	 * @return the tile width
	 */
	public int getTileWidth() {
		return this.tileWidth;
	}

	/**
	 * Sets the tile width.
	 *
	 * @param tileWidth the new tile width
	 */
	public void setTileWidth(final int tileWidth) {
		this.tileWidth = tileWidth;
	}

	/**
	 * Gets the wall width.
	 *
	 * @return the wall width
	 */
	public int getWallWidth() {
		return this.wallWidth;
	}

	/**
	 * Sets the wall width.
	 *
	 * @param wallWidth the new wall width
	 */
	public void setWallWidth(final int wallWidth) {
		this.wallWidth = wallWidth;
	}

	/**
	 * Gets the wall height.
	 *
	 * @return the wall height
	 */
	public int getWallHeight() {
		return this.wallHeight;
	}

	/**
	 * Sets the wall height.
	 *
	 * @param wallHeight the new wall height
	 */
	public void setWallHeight(final int wallHeight) {
		this.wallHeight = wallHeight;
	}

	/**
	 * Gets the layer height.
	 *
	 * @return the layer height
	 */
	public int getLayerHeight() {
		return this.layerHeight;
	}

	/**
	 * Sets the layer height.
	 *
	 * @param layerHeight the new layer height
	 */
	public void setLayerHeight(final int layerHeight) {
		this.layerHeight = layerHeight;
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Sets the width.
	 *
	 * @param width the new width
	 */
	public void setWidth(final int width) {
		this.width = width;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Sets the height.
	 *
	 * @param height the new height
	 */
	public void setHeight(final int height) {
		this.height = height;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.ResizeListener#onResize(com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent)
	 */
	@Override
	public void onResize(final ResizeEvent resizeEvent) {
		this.setWidth(resizeEvent.getWidth());
		this.setHeight(resizeEvent.getHeight());
	}

	/**
	 * Register listeners.
	 */
	public void registerListeners() {
		VictusLudusGame.engine.eventHandler.resizeHandler.registerPlease(this);
	}

	/**
	 * Unregister listeners.
	 */
	public void unregisterListeners() {
		VictusLudusGame.engine.eventHandler.resizeHandler.unregisterPlease(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() {
		this.unregisterListeners();
	}
}
