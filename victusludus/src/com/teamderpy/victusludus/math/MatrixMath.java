
package com.teamderpy.victusludus.math;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Utilities for performing operations on matricies represented as a
 * 2-dimensional array. Uses light threading to compute faster.
 */
public class MatrixMath {
	/** multithreading worker pool for matrix math */
	private static ExecutorService workerPool = Executors.newFixedThreadPool(8, Executors.defaultThreadFactory());

	/**
	 * Takes the difference of both arrays. If the arrays are not the same size,
	 * then only the areas that overlap will be affected. If the arrays are not
	 * rectangular, then behavior is unpredictable.
	 * 
	 * @param array1 an array of values
	 * @param array2 another array of values to subtract from the first
	 */
	public static void difference (final float[][] array1, final float[][] array2) {
		if (array1 == null || array2 == null || array1.length == 0 || array2.length == 0) {
			return;
		}

		int iMax = 0;
		int jMax = 0;

		if (array1.length < array2.length) {
			iMax = array1.length;
		} else {
			iMax = array2.length;
		}

		ArrayList<Callable<Boolean>> workers = new ArrayList<Callable<Boolean>>();

		for (int i = 0; i < iMax; i++) {
			if (array1[i].length < array2[i].length) {
				jMax = array1[i].length;
			} else {
				jMax = array2[i].length;
			}

			workers.add(new DifferenceThread(array1[i], array2[i], jMax));
		}

		try {
			MatrixMath.workerPool.invokeAll(workers);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static class DifferenceThread implements Callable<Boolean> {
		private float[] array1;
		private float[] array2;
		private int jMax;

		public DifferenceThread (final float[] array1, final float[] array2, final int jMax) {
			this.array1 = array1;
			this.array2 = array2;
			this.jMax = jMax;
		}

		@Override
		public Boolean call () throws Exception {
			for (int j = 0; j < this.jMax; j++) {
				this.array1[j] = Math.abs(this.array1[j] - this.array2[j]);
			}

			return true;
		}
	}

	/**
	 * Subtracts the second array from the first array. If the arrays are not the
	 * same size, then only the areas that overlap will be affected. If the
	 * arrays are not rectangular, then behavior is unpredictable.
	 * 
	 * @param array1 an array of values
	 * @param array2 another array of values to subtract from the first
	 * @param minValue the result of the addition cannot go above this value.
	 *           Specify Float.MAX_VALUE to remove the limit.
	 */
	public static void subtract (final float[][] array1, final float[][] array2, final float minValue) {
		if (array1 == null || array2 == null || array1.length == 0 || array2.length == 0) {
			return;
		}

		int iMax = 0;
		int jMax = 0;

		if (array1.length < array2.length) {
			iMax = array1.length;
		} else {
			iMax = array2.length;
		}

		ArrayList<Callable<Boolean>> workers = new ArrayList<Callable<Boolean>>();

		for (int i = 0; i < iMax; i++) {
			if (array1[i].length < array2[i].length) {
				jMax = array1[i].length;
			} else {
				jMax = array2[i].length;
			}

			workers.add(new SubtractThread(array1[i], array2[i], jMax, minValue));
		}

		try {
			MatrixMath.workerPool.invokeAll(workers);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static class SubtractThread implements Callable<Boolean> {
		private float[] array1;
		private float[] array2;
		private int jMax;
		private float minValue;

		public SubtractThread (final float[] array1, final float[] array2, final int jMax, final float minValue) {
			this.array1 = array1;
			this.array2 = array2;
			this.jMax = jMax;
			this.minValue = minValue;
		}

		@Override
		public Boolean call () throws Exception {
			for (int j = 0; j < this.jMax; j++) {
				this.array1[j] = this.array1[j] - this.array2[j];

				if (this.array1[j] < this.minValue) {
					this.array1[j] = this.minValue;
				}
			}

			return true;
		}
	}

	/**
	 * Takes the first array and adds the second one to it. If the arrays are not
	 * the same size, then only the areas that overlap will be affected. If the
	 * arrays are not rectangular, then behavior is unpredictable.
	 * 
	 * @param array1 the array of base values
	 * @param array2 the array to add to the first
	 * @param maxValue the result of the addition cannot go above this value.
	 *           Specify Float.MAX_VALUE to remove the limit.
	 */
	public static void sum (final float[][] array1, final float[][] array2, final float maxValue) {
		if (array1 == null || array2 == null || array1.length == 0 || array2.length == 0) {
			return;
		}

		int iMax = 0;
		int jMax = 0;

		if (array1.length < array2.length) {
			iMax = array1.length;
		} else {
			iMax = array2.length;
		}

		ArrayList<Callable<Boolean>> workers = new ArrayList<Callable<Boolean>>();

		for (int i = 0; i < iMax; i++) {
			if (array1[i].length < array2[i].length) {
				jMax = array1[i].length;
			} else {
				jMax = array2[i].length;
			}

			workers.add(new SumThread(array1[i], array2[i], jMax, maxValue));
		}

		try {
			MatrixMath.workerPool.invokeAll(workers);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static class SumThread implements Callable<Boolean> {
		private float[] array1;
		private float[] array2;
		private int jMax;
		private float maxValue;

		public SumThread (final float[] array1, final float[] array2, final int jMax, final float maxValue) {
			this.array1 = array1;
			this.array2 = array2;
			this.jMax = jMax;
			this.maxValue = maxValue;
		}

		@Override
		public Boolean call () throws Exception {
			for (int j = 0; j < this.jMax; j++) {
				this.array1[j] = this.array1[j] + this.array2[j];

				if (this.array1[j] > this.maxValue) {
					this.array1[j] = this.maxValue;
				}
			}

			return true;
		}
	}

	/**
	 * Caps the max of the array at an upper value
	 * 
	 * @param array the array
	 * @param height the height to cap at
	 */
	public static void capMax (final int[][] array, final int height) {
		ArrayList<Callable<Boolean>> workers = new ArrayList<Callable<Boolean>>();

		for (int i = 0; i < array.length; i++) {
			workers.add(new CapMaxThread(array[i], height));
		}

		try {
			MatrixMath.workerPool.invokeAll(workers);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static class CapMaxThread implements Callable<Boolean> {
		private int[] array;
		private int height;

		public CapMaxThread (final int[] array, final int height) {
			this.array = array;
			this.height = height;
		}

		@Override
		public Boolean call () throws Exception {
			for (int j = 0; j < this.array.length; j++) {
				if (this.array[j] > this.height) {
					this.array[j] = this.height;
				}
			}

			return true;
		}
	}

	/**
	 * Caps the min of the array at a lower value
	 * 
	 * @param array the array
	 * @param height the height to cap at
	 */
	public static void capMin (final int[][] array, final int height) {
		ArrayList<Callable<Boolean>> workers = new ArrayList<Callable<Boolean>>();

		for (int i = 0; i < array.length; i++) {
			workers.add(new CapMinThread(array[i], height));
		}

		try {
			MatrixMath.workerPool.invokeAll(workers);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static class CapMinThread implements Callable<Boolean> {
		private int[] array;
		private int height;

		public CapMinThread (final int[] array, final int height) {
			this.array = array;
			this.height = height;
		}

		@Override
		public Boolean call () throws Exception {
			for (int j = 0; j < this.array.length; j++) {
				if (this.array[j] < this.height) {
					this.array[j] = this.height;
				}
			}

			return true;
		}
	}

	/**
	 * Smooth a 2d array by averaging neighbors
	 * 
	 * @param array the array of ints
	 * @param iterations the iterations
	 */
	public static void smooth (int[][] array, final int iterations) {
		ArrayList<Callable<Boolean>> workers = new ArrayList<Callable<Boolean>>();
		int[][] nextIteration = array.clone();

		for (int c = 0; c < iterations; c++) {
			for (int i = 0; i < array.length; i++) {
				workers.add(new SmoothIntThread(array, nextIteration, i));
			}

			try {
				MatrixMath.workerPool.invokeAll(workers);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			workers.clear();

			array = nextIteration.clone();
		}
	}

	private static class SmoothIntThread implements Callable<Boolean> {
		private int[][] array;
		private int[][] nextIteration;
		private int i;

		public SmoothIntThread (final int[][] array, final int[][] nextIteration, final int i) {
			this.array = array;
			this.nextIteration = nextIteration;
			this.i = i;
		}

		@Override
		public Boolean call () throws Exception {
			for (int j = 0; j < this.array[this.i].length; j++) {
				float sum = this.array[this.i][j];
				int count = 1;

				if (this.i - 1 >= 0) {
					sum += this.array[this.i - 1][j];
					count++;
				}

				if (j - 1 >= 0) {
					sum += this.array[this.i][j - 1];
					count++;
				}

				if (this.i + 1 < this.array.length) {
					sum += this.array[this.i + 1][j];
					count++;
				}

				if (j + 1 < this.array[this.i].length) {
					sum += this.array[this.i][j + 1];
					count++;
				}

				this.nextIteration[this.i][j] = (int)(sum / count);
			}

			return true;
		}
	}

	/**
	 * Smooth a 2d array by averaging neighbors
	 * 
	 * @param array the array of floats
	 * @param iterations the iterations
	 */
	public static void smooth (float[][] array, final int iterations) {
		ArrayList<Callable<Boolean>> workers = new ArrayList<Callable<Boolean>>();
		float[][] nextIteration = array.clone();

		for (int c = 0; c < iterations; c++) {
			for (int i = 0; i < array.length; i++) {
				workers.add(new SmoothFloatThread(array, nextIteration, i));
			}

			try {
				MatrixMath.workerPool.invokeAll(workers);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			workers.clear();

			array = nextIteration.clone();
		}
	}

	private static class SmoothFloatThread implements Callable<Boolean> {
		private float[][] array;
		private float[][] nextIteration;
		private int i;

		public SmoothFloatThread (final float[][] array, final float[][] nextIteration, final int i) {
			this.array = array;
			this.nextIteration = nextIteration;
			this.i = i;
		}

		@Override
		public Boolean call () throws Exception {
			for (int j = 0; j < this.array[this.i].length; j++) {
				float sum = this.array[this.i][j];
				int count = 1;

				if (this.i - 1 >= 0) {
					sum += this.array[this.i - 1][j];
					count++;
				}

				if (j - 1 >= 0) {
					sum += this.array[this.i][j - 1];
					count++;
				}

				if (this.i + 1 < this.array.length) {
					sum += this.array[this.i + 1][j];
					count++;
				}

				if (j + 1 < this.array[this.i].length) {
					sum += this.array[this.i][j + 1];
					count++;
				}

				this.nextIteration[this.i][j] = (sum / count);
			}

			return true;
		}
	}

	/**
	 * Normalize an array between two values
	 * 
	 * @param array the array to normalize
	 * @param min the lowest possible value
	 * @param max the highest possible value
	 */
	public static void normalize (final float[][] array, final float min, final float max) {
		float highestPoint = Float.MIN_VALUE;
		float lowestPoint = Float.MAX_VALUE;

		ArrayList<Callable<Boolean>> workers = new ArrayList<Callable<Boolean>>();

		// find min and max values
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (array[i][j] > highestPoint) {
					highestPoint = array[i][j];
				}
				if (array[i][j] < lowestPoint) {
					lowestPoint = array[i][j];
				}
			}
		}

		// normalize
		for (int i = 0; i < array.length; i++) {
			workers.add(new NormalizeThread(array[i], lowestPoint, highestPoint, min, max));
		}

		try {
			MatrixMath.workerPool.invokeAll(workers);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static class NormalizeThread implements Callable<Boolean> {
		private float[] array;
		private float lowestPoint;
		private float highestPoint;
		private float min;
		private float max;

		public NormalizeThread (final float[] array, final float lowestPoint, final float highestPoint, final float min,
			final float max) {
			this.array = array;
			this.lowestPoint = lowestPoint;
			this.highestPoint = highestPoint;
			this.min = min;
			this.max = max;
		}

		@Override
		public Boolean call () throws Exception {
			for (int j = 0; j < this.array.length; j++) {
				this.array[j] = VMath.linearInterpolation(this.lowestPoint, this.highestPoint, this.min, this.max, this.array[j]);
			}

			return true;
		}
	}
}
