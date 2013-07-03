
package com.teamderpy.victusludus.math.heightmap;

import java.util.Random;

import com.teamderpy.victusludus.math.VMath;

/** The Class LayeredGenerator. */
public class LayeredGenerator implements INoiseGenerator {

	/** The seed. */
	private int seed;

	/** The rand. */
	private Random rand;

	/** The passes. */
	private int passes;

	/** The persistence. */
	private float persistence;

	/** Instantiates a new layered generator. */
	public LayeredGenerator () {
		this.passes = 6;
		this.persistence = 0.25F;

		this.rand = new Random();
		this.seed();
	}

	/**
	 * Instantiates a new layered generator.
	 * 
	 * @param passes the passes
	 * @param persistence the persistence
	 */
	public LayeredGenerator (final int passes, final float persistence) {
		this.passes = passes;
		this.persistence = persistence;

		this.rand = new Random();
		this.seed();
	}

	/** Seed. */
	private void seed () {
		// this instance will have a new random seed
		this.seed = this.rand.nextInt() / 2;
	}

	/**
	 * Smooth noise using basic blur function.
	 * 
	 * @param x the x coord
	 * @param y the y coord
	 * @return the float
	 */
	private float smoothNoise (final int x, final int y) {
		float corners = this.randomNoise(x - 1, y - 1) + this.randomNoise(x - 1, y + 1) + this.randomNoise(x + 1, y - 1)
			+ this.randomNoise(x + 1, y + 1);
		float sides = this.randomNoise(x, y - 1) + this.randomNoise(x, y + 1) + this.randomNoise(x + 1, y)
			+ this.randomNoise(x - 1, y);
		float center = this.randomNoise(x, y);

		return corners / 16 + sides / 8 + center / 4;
	}

	/**
	 * Smooth noise using simple gaussian blur function.
	 * 
	 * @param x the x coord
	 * @param y the y coord
	 * @return the float
	 */
	private float simpleGaussianNoise (final int x, final int y) {
		float corners = this.randomNoise(x - 1, y - 1) + this.randomNoise(x - 1, y + 1) + this.randomNoise(x + 1, y - 1)
			+ this.randomNoise(x + 1, y + 1);
		float sides = this.randomNoise(x, y - 1) + this.randomNoise(x, y + 1) + this.randomNoise(x + 1, y)
			+ this.randomNoise(x - 1, y);
		float center = this.randomNoise(x, y);

		return 0.09470416F * corners + 0.118318F * sides + 0.147761F * center;
	}

	/**
	 * Smooth noise using gaussian blur function.
	 * 
	 * @param x the x coord
	 * @param y the y coord
	 * @return the float
	 */
	private float gaussianNoise (final int x, final int y, final GaussianBlurMachine blurMachine) {
		float[][] blurMatrix = blurMachine.getGaussianMatrix();

		float sum = 0.0F;

		for (int i = 0; i < blurMatrix.length; i++) {
			for (int j = 0; j < blurMatrix[i].length; j++) {
				sum += this.randomNoise(x + i - blurMachine.getPixelRadius(), y + j - blurMachine.getPixelRadius())
					* blurMatrix[i][j];
			}
		}

		return sum;
	}

	/**
	 * Random noise.
	 * 
	 * @param x the x coord
	 * @param y the y coord
	 * @return the float
	 */
	private float randomNoise (final int x, final int y) {
		int h = x * 29 + y * 113;
		h += this.seed;
		h = h << 13 ^ h;
		return 1.0F - (h * (h * h * 15731 + 789221) + 1376312589 & 0x7fffffff) / 1073741824.0F;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teamderpy.victusludus.game.map.INoiseGenerator#generate(int, int)
	 */
	@Override
	public int[][] generateInt (final int width, final int height, final boolean normalize) {
		int[][] array = new int[width][height];
		int highestPoint = 0;
		int lowestPoint = 9999;

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				array[i][j] = 1;
			}
		}

		// generate terrain
		for (int p = 1; p < this.passes + 1; p++) {
			GaussianBlurMachine blurMachine = new GaussianBlurMachine(p, 1.5F);

			for (int i = 0; i < array.length; i++) {
				for (int j = 0; j < array[i].length; j++) {
					if (normalize) {
						array[i][j] += 5 + 5 * Math.pow(this.persistence, this.passes - p)
							* this.gaussianNoise(i / p, j / p, blurMachine);
					} else {
						array[i][j] += Math.pow(this.persistence, this.passes - p) * this.gaussianNoise(i / p, j / p, blurMachine);
					}

					if (p == this.passes) {
						if (array[i][j] > highestPoint) {
							highestPoint = array[i][j];
						}
						if (array[i][j] < lowestPoint) {
							lowestPoint = array[i][j];
						}
					}
				}
			}

			this.seed();
		}

		// normalize terrain
		if (normalize) {
			for (int i = 0; i < array.length; i++) {
				for (int j = 0; j < array[i].length; j++) {
					array[i][j] -= lowestPoint - 15;
				}
			}
		}

		return array;
	}

	@Override
	public float[][] generateFloat (final int width, final int height, final float minValue, final float maxValue,
		final boolean blur) {
		float[][] array = new float[width][height];
		float highestPoint = 0;
		float lowestPoint = 9999;

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				array[i][j] = 0;
			}
		}

		// generate terrain
		for (int p = 1; p < this.passes + 1; p++) {
			GaussianBlurMachine blurMachine = new GaussianBlurMachine(p, 1.5F);

			for (int i = 0; i < array.length; i++) {
				for (int j = 0; j < array[i].length; j++) {
					if (blur) {
						array[i][j] += Math.pow(this.persistence, this.passes - p) * this.gaussianNoise(i / p, j / p, blurMachine);
					} else {
						array[i][j] += Math.pow(this.persistence, this.passes - p) * this.smoothNoise(i / p, j / p);
					}

					if (p == this.passes) {
						if (array[i][j] > highestPoint) {
							highestPoint = array[i][j];
						}
						if (array[i][j] < lowestPoint) {
							lowestPoint = array[i][j];
						}
					}
				}
			}

			this.seed();
		}

		// normalize
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				array[i][j] = VMath.linearInterpolation(lowestPoint, highestPoint, minValue, maxValue, array[i][j]);
			}
		}

		return array;
	}

	/**
	 * Gets the passes.
	 * 
	 * @return the passes
	 */
	public int getPasses () {
		return this.passes;
	}

	/**
	 * Sets the passes.
	 * 
	 * @param passes the new passes
	 */
	public void setPasses (final int passes) {
		this.passes = passes;
	}

	/**
	 * Gets the persistence.
	 * 
	 * @return the persistence
	 */
	public float getPersistence () {
		return this.persistence;
	}

	/**
	 * Sets the persistence.
	 * 
	 * @param persistence the new persistence
	 */
	public void setPersistence (final float persistence) {
		this.persistence = persistence;
	}
}
