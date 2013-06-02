package com.teamderpy.victusludus.game.map;


/**
 * The Interface INoiseGenerator.
 */
public interface INoiseGenerator {

	/**
	 * Generate.
	 *
	 * @param height the height
	 * @param width the width
	 * @param normalize whether or not to normalize result
	 * @return int array
	 */
	public int[][] generateInt(int height, int width, boolean normalize);

	/**
	 * Generate.
	 *
	 * @param height the height
	 * @param width the width
	 * @param minValue the lowest a number can be
	 * @param maxValye the highest a number can be
	 * @param blur whether or not to blur
	 * @return float array
	 */
	public float[][] generateFloat(int height, int width, float minValue, float maxValue, boolean blur);
}
