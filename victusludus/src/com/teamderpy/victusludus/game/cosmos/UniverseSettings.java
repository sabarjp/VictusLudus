
package com.teamderpy.victusludus.game.cosmos;

import com.teamderpy.victusludus.engine.ISettings;

/** The Class UniverseSettings. */
public class UniverseSettings implements ISettings {

	/** The requested age. */
	private float requestedUniverseAge;

	/** The requested density. */
	private float requestedUniverseDensity;

	/** The requested random seed */
	private long requestedSeed;

	/** Gets the requested age
	 * 
	 * @return the requested age */
	public float getRequestedUniAge () {
		return this.requestedUniverseAge;
	}

	/** Sets the requested age.
	 * 
	 * @param age the new requested age */
	public void setRequestedUniAge (final float age) {
		this.requestedUniverseAge = age;
	}

	/** Gets the requested density
	 * 
	 * @return the requested desnity */
	public float getRequestedUniDensity () {
		return this.requestedUniverseDensity;
	}

	/** Sets the requested density
	 * 
	 * @param density the new requested density */
	public void setRequestedUniDensity (final float density) {
		this.requestedUniverseDensity = density;
	}

	/** Gets the requested seed
	 * @return the requested seed */
	public long getRequestedSeed () {
		return this.requestedSeed;
	}

	/** Sets the requested seed
	 * @param requestedSeed the new requested seed */
	public void setRequestedSeed (final long requestedSeed) {
		this.requestedSeed = requestedSeed;
	}

}
