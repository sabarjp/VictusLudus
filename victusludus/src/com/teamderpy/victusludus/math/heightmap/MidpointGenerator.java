
package com.teamderpy.victusludus.math.heightmap;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.badlogic.gdx.math.MathUtils;
import com.teamderpy.victusludus.math.MatrixMath;

/**
 * Uses midpoint displacement to calculate random noise. Would most likely be MUCH faster using GPU compute or multiple threads.
 */
public class MidpointGenerator implements INoiseGenerator {
	/** The seed. */
	private long seed;

	/** The rand. */
	private Random rand;

	/** The persistence. */
	private float persistence;

	/** multithreading worker pool for matrix math */
	private ExecutorService workerPool = Executors.newFixedThreadPool(8, Executors.defaultThreadFactory());
	private ArrayList<Callable<Boolean>> workers = new ArrayList<Callable<Boolean>>();

	/** Instantiates a new MidpointGenerator generator. */
	public MidpointGenerator () {
		this.persistence = 0.25F;
		this.rand = new Random();
		this.seed();
	}

	/** Instantiates a new MidpointGenerator generator. */
	public MidpointGenerator (final float persistence) {
		this.persistence = persistence;
		this.rand = new Random();
		this.seed();
	}

	/** Instantiates a new MidpointGenerator generator. */
	public MidpointGenerator (final float persistence, final long seed) {
		this.persistence = persistence;
		this.rand = new Random();
		this.seed = seed;
	}

	/** Seeds the generator with a new random seed */
	public void seed () {
		// this instance will have a new random seed
		this.seed = this.rand.nextLong();
	}

	/** Seeds the generator with a specified seed */
	public void seed (final long seed) {
		this.seed = seed;
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

	@Override
	public int[][] generateInt (final int width, final int height, final boolean normalize) {
		int[][] array;
		int actualSize;

		// array must be a multiple of 2^x + 1
		int maxSide = Math.max(width, height);

		if (MathUtils.isPowerOfTwo(maxSide)) {
			array = new int[maxSide + 1][maxSide + 1];
			actualSize = maxSide;
		} else {
			int num = MathUtils.nextPowerOfTwo(Math.max(width, height));
			array = new int[num + 1][num + 1];
			actualSize = num;
		}

		int highestPoint = 0;
		int lowestPoint = 9999;

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				array[i][j] = 1;
			}
		}

		// seed corners
		array[0][0] = (int)(5 * this.randomNoise(0, 0));
		array[actualSize][0] = (int)(5 * this.randomNoise(actualSize, 0));
		array[0][actualSize] = (int)(5 * this.randomNoise(0, actualSize));
		array[actualSize][actualSize] = (int)(5 * this.randomNoise(actualSize, actualSize));

		// find midpoints
		int squareSize = actualSize;
		int iteration = 1;
		while (squareSize > 1) {
			for (int x = 0; x < actualSize; x += squareSize) {
				for (int y = 0; y < actualSize; y += squareSize) {
					// create middle point
					int midX = x + squareSize / 2;
					int midY = y + squareSize / 2;

					int cornerNWValue = array[x][y];
					int cornerNEValue = array[x + squareSize][y];
					int cornerSEValue = array[x + squareSize][y + squareSize];
					int cornerSWValue = array[x][y + squareSize];

					int averageCenter = (int)((cornerNWValue + cornerNEValue + cornerSEValue + cornerSWValue) / 4.0F);
					int averageNorth = (int)((cornerNWValue + cornerNEValue) / 2.0F);
					int averageEast = (int)((cornerNEValue + cornerSEValue) / 2.0F);
					int averageSouth = (int)((cornerSEValue + cornerSWValue) / 2.0F);
					int averageWest = (int)((cornerNWValue + cornerSWValue) / 2.0F);

					array[midX][midY] = (int)(averageCenter + 5 * Math.pow(this.persistence, iteration) * this.randomNoise(midX, midY));

					array[midX][y] = (int)(averageNorth + 5 * Math.pow(this.persistence, iteration) * this.randomNoise(midX, midY));
					array[midX][y + squareSize] = (int)(averageSouth + 5 * Math.pow(this.persistence, iteration)
						* this.randomNoise(midX, y + squareSize));

					array[x][midY] = (int)(averageWest + 5 * Math.pow(this.persistence, iteration) * this.randomNoise(x, midY));
					array[x + squareSize][midY] = (int)(averageEast + 5 * Math.pow(this.persistence, iteration)
						* this.randomNoise(x + squareSize, midY));
				}
			}

			// downsize square
			squareSize /= 2;
			iteration++;
		}

		// high and low points
		for (int[] element : array) {
			for (int element2 : element) {
				if (element2 > highestPoint) {
					highestPoint = element2;
				}
				if (element2 < lowestPoint) {
					lowestPoint = element2;
				}
			}
		}

		// normalize terrain
		if (normalize) {
			for (int i = 0; i < array.length; i++) {
				for (int j = 0; j < array[i].length; j++) {
					array[i][j] -= lowestPoint - 15;
				}
			}
		}

		// resize
		int[][] returnArray = new int[width][height];
		for (int i = 0; i < returnArray.length; i++) {
			for (int j = 0; j < returnArray[i].length; j++) {
				returnArray[i][j] = array[i][j];
			}
		}

		return returnArray;
	}

	@Override
	public float[][] generateFloat (final int width, final int height, final float minValue, final float maxValue,
		final boolean blur) {
		float[][] array;
		int actualSize;

		// array must be a multiple of 2^x + 1
		int maxSide = Math.max(width, height);

		if (MathUtils.isPowerOfTwo(maxSide)) {
			array = new float[maxSide + 1][maxSide + 1];
			actualSize = maxSide;
		} else {
			int num = MathUtils.nextPowerOfTwo(Math.max(width, height));
			array = new float[num + 1][num + 1];
			actualSize = num;
		}

		// System.err.println("requested area " + width + "x" + height + "  and created " + actualSize + "x" + actualSize);

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				array[i][j] = 0;
			}
		}

		// seed corners
		if (blur) {
			array[0][0] = this.simpleGaussianNoise(0, 0);
			array[actualSize][0] = this.simpleGaussianNoise(actualSize, 0);
			array[0][actualSize] = this.simpleGaussianNoise(0, actualSize);
			array[actualSize][actualSize] = this.simpleGaussianNoise(actualSize, actualSize);
		} else {
			array[0][0] = this.randomNoise(0, 0);
			array[actualSize][0] = this.randomNoise(actualSize, 0);
			array[0][actualSize] = this.randomNoise(0, actualSize);
			array[actualSize][actualSize] = this.randomNoise(actualSize, actualSize);
		}

		// find midpoints
		int squareSize = actualSize;
		int iteration = 1;

		this.workers.clear();

		while (squareSize > 1) {
			for (int x = 0; x < actualSize; x += squareSize) {
				for (int y = 0; y < actualSize; y += squareSize) {
					this.workers.add(new MidpointThread(array, x, y, squareSize, blur, iteration));
				}
			}

			try {
				this.workerPool.invokeAll(this.workers);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// downsize square
			squareSize /= 2;
			iteration++;

			this.workers.clear();
		}

		MatrixMath.normalize(array, minValue, maxValue);

		// resize
		float[][] returnArray = new float[width][height];
		for (int i = 0; i < returnArray.length; i++) {
			for (int j = 0; j < returnArray[i].length; j++) {
				returnArray[i][j] = array[i][j];
			}
		}

		// System.err.println("returning size of " + width + "x" + height);

		return returnArray;
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

	private class MidpointThread implements Callable<Boolean> {
		private float[][] array;
		private int x;
		private int y;
		private int squareSize;
		private boolean blur;
		private int iteration;

		public MidpointThread (final float[][] array, final int x, final int y, final int squareSize, final boolean blur,
			final int iteration) {
			this.array = array;
			this.x = x;
			this.y = y;
			this.squareSize = squareSize;
			this.blur = blur;
			this.iteration = iteration;
		}

		@Override
		public Boolean call () throws Exception {
			// create middle point
			int midX = this.x + this.squareSize / 2;
			int midY = this.y + this.squareSize / 2;

			float cornerNWValue = this.array[this.x][this.y];
			float cornerNEValue = this.array[this.x + this.squareSize][this.y];
			float cornerSEValue = this.array[this.x + this.squareSize][this.y + this.squareSize];
			float cornerSWValue = this.array[this.x][this.y + this.squareSize];

			float averageCenter = (cornerNWValue + cornerNEValue + cornerSEValue + cornerSWValue) / 4.0F;
			float averageNorth = (cornerNWValue + cornerNEValue) / 2.0F;
			float averageEast = (cornerNEValue + cornerSEValue) / 2.0F;
			float averageSouth = (cornerSEValue + cornerSWValue) / 2.0F;
			float averageWest = (cornerNWValue + cornerSWValue) / 2.0F;

			float finalCenter, finalNorth, finalEast, finalSouth, finalWest;

			if (this.blur) {
				finalCenter = MidpointGenerator.this.simpleGaussianNoise(midX, midY);
				finalNorth = MidpointGenerator.this.simpleGaussianNoise(midX, midY);
				finalEast = MidpointGenerator.this.simpleGaussianNoise(this.x + this.squareSize, midY);
				finalSouth = MidpointGenerator.this.simpleGaussianNoise(midX, this.y + this.squareSize);
				finalWest = MidpointGenerator.this.simpleGaussianNoise(this.x, midY);
			} else {
				finalCenter = MidpointGenerator.this.randomNoise(midX, midY);
				finalNorth = MidpointGenerator.this.randomNoise(midX, midY);
				finalEast = MidpointGenerator.this.randomNoise(this.x + this.squareSize, midY);
				finalSouth = MidpointGenerator.this.randomNoise(midX, this.y + this.squareSize);
				finalWest = MidpointGenerator.this.randomNoise(this.x, midY);
			}

			// calculate the side points
			this.array[midX][midY] = (float)(averageCenter + Math.pow(MidpointGenerator.this.persistence, this.iteration)
				* finalCenter);

			this.array[midX][this.y] = (float)(averageNorth + Math.pow(MidpointGenerator.this.persistence, this.iteration)
				* finalNorth);
			this.array[midX][this.y + this.squareSize] = (float)(averageSouth + Math.pow(MidpointGenerator.this.persistence,
				this.iteration) * finalSouth);

			this.array[this.x][midY] = (float)(averageWest + Math.pow(MidpointGenerator.this.persistence, this.iteration)
				* finalWest);
			this.array[this.x + this.squareSize][midY] = (float)(averageEast + Math.pow(MidpointGenerator.this.persistence,
				this.iteration) * finalEast);

			return true;
		}
	}

}
