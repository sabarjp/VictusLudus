
package com.teamderpy.victusludus.game.cosmos;

import com.teamderpy.victusludus.math.MatrixMath;
import com.teamderpy.victusludus.math.heightmap.MidpointGenerator;

/**
 * The planet as a large map
 * @author Josh
 * 
 */
public class World {
	private Planet parentPlanet;
	private long seed;

	private int width;
	private int height;

	private float[][] worldHeightData;

	/**
	 * Instantiates a new World using a seed, which will generate the height map
	 * @param seed the seed used for generating a height map
	 */
	public World (final Planet parentPlanet, final long seed) {
		this.parentPlanet = parentPlanet;
		this.seed = seed;

		this.width = 256;
		this.height = 256;

		MidpointGenerator noiseGenerator = new MidpointGenerator(0.66F, this.seed + 234983249L);
		this.worldHeightData = noiseGenerator.generateFloat(this.width, this.height, 0F, 1F, false);

		// iterate a bit
		if (!this.parentPlanet.getPlanetType().isGasGiant()) {
			this.iterateTerrain(3);
		} else {
			MatrixMath.smooth(this.worldHeightData, 1);
		}
	}

	/**
	 * The world height map
	 * @return the height map as a 2d array of floats
	 */
	public float[][] getWorldHeightData () {
		return this.worldHeightData;
	}

	/**
	 * Generates random terrain and then runs a difference function against the current world data
	 * @param iterations the number of iterations to perform
	 */
	private void iterateTerrain (final int iterations) {
		MidpointGenerator noiseGenerator = new MidpointGenerator(0.66F, this.seed + 1379123L);

		for (int i = 0; i < iterations + 1; i++) {
			noiseGenerator.seed(this.seed + (1379123L * 49223));

			float[][] tempData = noiseGenerator.generateFloat(this.width, this.height, 0F, 1F, false);

			MatrixMath.difference(this.worldHeightData, tempData);
		}

		// normalize the terrain
		MatrixMath.normalize(this.worldHeightData, 0F, 1F);
	}
}
