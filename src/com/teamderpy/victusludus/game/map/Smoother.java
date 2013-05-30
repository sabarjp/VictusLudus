package com.teamderpy.victusludus.game.map;

// TODO: Auto-generated Javadoc
/**
 * The Class Smoother.
 */
public class Smoother {

	/**
	 * Smooth.
	 *
	 * @param array the array
	 * @param iterations the iterations
	 */
	public static void smooth(final int[][] array, final int iterations) {
		for(int c=0; c<iterations; c++){
			for(int i=0; i<array.length; i++){
				for(int j=0; j<array[i].length; j++){
					float sum = array[i][j];
					int count = 1;

					if(i-1 >= 0){
						sum += array[i-1][j];
						count++;
					}

					if(j-1 >= 0){
						sum += array[i][j-1];
						count++;
					}

					if(i+1 < array.length){
						sum += array[i+1][j];
						count++;
					}

					if(j+1 < array[i].length){
						sum += array[i][j+1];
						count++;
					}

					array[i][j] = (int)(sum / count);
				}
			}
		}
	}

	public static void smooth(final float[][] array, final int iterations) {
		for(int c=0; c<iterations; c++){
			for(int i=0; i<array.length; i++){
				for(int j=0; j<array[i].length; j++){
					float sum = array[i][j];
					int count = 1;

					if(i-1 >= 0){
						sum += array[i-1][j];
						count++;
					}

					if(j-1 >= 0){
						sum += array[i][j-1];
						count++;
					}

					if(i+1 < array.length){
						sum += array[i+1][j];
						count++;
					}

					if(j+1 < array[i].length){
						sum += array[i][j+1];
						count++;
					}

					array[i][j] = sum / count;
				}
			}
		}
	}
}
