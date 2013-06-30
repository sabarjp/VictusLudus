
package com.teamderpy.victusludus.game.cosmos;

import com.teamderpy.victusludus.math.MidpointGenerator;
import com.teamderpy.victusludus.math.Smoother;

/**
 * The planet as a large map
 * @author Josh
 * 
 */
public class World {
	private long seed;

	private int width;
	private int height;

	private float[][] worldHeightData;

	/**
	 * Instantiates a new World using a seed, which will generate the height map
	 * @param seed the seed used for generating a height map
	 */
	public World (final long seed) {
		this.seed = seed;

		this.width = 256;
		this.height = 256;

		MidpointGenerator noiseGenerator = new MidpointGenerator(0.66F, this.seed + 234983249L);
		this.worldHeightData = noiseGenerator.generateFloat(this.width, this.height, 0F, 255F, false);

		Smoother.smooth(this.worldHeightData, 1);
	}

	/**
	 * The world height map
	 * @return the height map as a 2d array of floats
	 */
	public float[][] getWorldHeightData () {
		return this.worldHeightData;
	}
}
