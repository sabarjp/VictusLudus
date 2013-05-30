package com.teamderpy.victusludus.game;

import com.teamderpy.victusludus.engine.ISettings;

// TODO: Auto-generated Javadoc
/**
 * The Class GameSettings.
 */
public class GameSettings implements ISettings{

	/** The requested map height. */
	private int requestedMapHeight;

	/** The requested map width. */
	private int requestedMapWidth;

	/** The requested map smoothness. */
	private float requestedMapSmoothness;

	/** The requested map scale. */
	private float requestedMapScale;

	/** The requested map randomness. */
	private float requestedMapRandomness;

	/** The requested map plateau-ness. */
	private float requestedMapPlateauFactor;

	/**
	 * Gets the requested map height.
	 *
	 * @return the requested map height
	 */
	public int getRequestedMapHeight() {
		return this.requestedMapHeight;
	}

	/**
	 * Sets the requested map height.
	 *
	 * @param requestedMapHeight the new requested map height
	 */
	public void setRequestedMapHeight(final int requestedMapHeight) {
		this.requestedMapHeight = requestedMapHeight;
	}

	/**
	 * Gets the requested map width.
	 *
	 * @return the requested map width
	 */
	public int getRequestedMapWidth() {
		return this.requestedMapWidth;
	}

	/**
	 * Sets the requested map width.
	 *
	 * @param requestedMapWidth the new requested map width
	 */
	public void setRequestedMapWidth(final int requestedMapWidth) {
		this.requestedMapWidth = requestedMapWidth;
	}

	/**
	 * Gets the requested map smoothness.
	 *
	 * @return the requested map smoothness
	 */
	public float getRequestedMapSmoothness() {
		return this.requestedMapSmoothness;
	}

	/**
	 * Sets the requested map smoothness.
	 *
	 * @param requestedMapSmoothness the new requested map smoothness
	 */
	public void setRequestedMapSmoothness(final float requestedMapSmoothness) {
		this.requestedMapSmoothness = requestedMapSmoothness;
	}

	/**
	 * Gets the requested map scale.
	 *
	 * @return the requested map scale
	 */
	public float getRequestedMapScale() {
		return this.requestedMapScale;
	}

	/**
	 * Sets the requested map scale.
	 *
	 * @param requestedMapScale the new requested map scale
	 */
	public void setRequestedMapScale(final float requestedMapScale) {
		this.requestedMapScale = requestedMapScale;
	}

	/**
	 * Gets the requested map randomness.
	 *
	 * @return the requested map randomness
	 */
	public float getRequestedMapRandomness() {
		return this.requestedMapRandomness;
	}

	/**
	 * Sets the requested map randomness.
	 *
	 * @param requestedMapRandomness the new requested map randomness
	 */
	public void setRequestedMapRandomness(final float requestedMapRandomness) {
		this.requestedMapRandomness = requestedMapRandomness;
	}

	/**
	 * Gets the requested map plateau factor.
	 *
	 * @return the requested map plateau factor
	 */
	public float getRequestedMapPlateauFactor() {
		return this.requestedMapPlateauFactor;
	}

	/**
	 * Sets the requested map plateau factor.
	 *
	 * @param requestedMapPlateauFactor the new requested map plateau factor
	 */
	public void setRequestedMapPlateauFactor(final float requestedMapPlateauFactor) {
		this.requestedMapPlateauFactor = requestedMapPlateauFactor;
	}
}
