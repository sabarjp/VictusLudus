
package com.teamderpy.victusludus.math.heightmap;

/**
 * Generates random noise on a 2d matrix
 */
public interface INoiseGenerator {

	/**
	 * Generate noise and return an integer matrix
	 * 
	 * @param height the height
	 * @param width the width
	 * @param normalize whether or not to normalize result
	 * @return int array
	 */
	public int[][] generateInt (int height, int width, boolean normalize);

	/**
	 * Generate noise and return a float matrix
	 * 
	 * @param height the height
	 * @param width the width
	 * @param minValue the lowest a number can be
	 * @param maxValue the highest a number can be
	 * @param blur whether or not to blur
	 * @return float array
	 */
	public float[][] generateFloat (int height, int width, float minValue, float maxValue, boolean blur);
}
