package com.teamderpy.victusludus.game.cosmos;

import com.teamderpy.victusludus.engine.ISettings;

// TODO: Auto-generated Javadoc
/**
 * The Class UniverseSettings.
 */
public class UniverseSettings implements ISettings{

	/** The requested age. */
	private float requestedUniverseAge;

	/** The requested density. */
	private float requestedUniverseDensity;


	/**
	 * Gets the requested age
	 *
	 * @return the requested age
	 */
	public float getRequestedUniAge() {
		return this.requestedUniverseAge;
	}

	/**
	 * Sets the requested age.
	 *
	 * @param age the new requested age
	 */
	public void setRequestedUniAge(final float age) {
		this.requestedUniverseAge = age;
	}

	/**
	 * Gets the requested density
	 *
	 * @return the requested desnity
	 */
	public float getRequestedUniDensity() {
		return this.requestedUniverseDensity;
	}

	/**
	 * Sets the requested density
	 *
	 * @param density the new requested density
	 */
	public void setRequestedUniDensity(final float density) {
		this.requestedUniverseDensity = density;
	}

}
