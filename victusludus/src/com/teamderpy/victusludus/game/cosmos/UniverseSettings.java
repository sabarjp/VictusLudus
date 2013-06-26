
package com.teamderpy.victusludus.game.cosmos;

import com.teamderpy.victusludus.engine.ISettings;

/** The Class UniverseSettings. */
public class UniverseSettings implements ISettings {

	/** The requested age. */
	private float requestedUniverseAge;

	/** The requested random seed */
	private long requestedSeed;

	/** Requested star mass distribution */
	private float requestedStarMassDistribution;

	/**
	 * Gets the requested age
	 * 
	 * @return the requested age
	 */
	public float getRequestedUniAge () {
		return this.requestedUniverseAge;
	}

	/**
	 * Sets the requested age.
	 * 
	 * @param age the new requested age
	 */
	public void setRequestedUniAge (final float age) {
		this.requestedUniverseAge = age;
	}

	/**
	 * Gets the requested seed
	 * @return the requested seed
	 */
	public long getRequestedSeed () {
		return this.requestedSeed;
	}

	/**
	 * Sets the requested seed
	 * @param requestedSeed the new requested seed
	 */
	public void setRequestedSeed (final long requestedSeed) {
		this.requestedSeed = requestedSeed;
	}

	/** Gets the requested star mass distribution */
	public float getRequestedStarMassDistribution () {
		return this.requestedStarMassDistribution;
	}

	/**
	 * Sets the star mass distribution
	 * @param requestedStarMassDistribution
	 */
	public void setRequestedStarMassDistribution (final float requestedStarMassDistribution) {
		this.requestedStarMassDistribution = requestedStarMassDistribution;
	}

}
