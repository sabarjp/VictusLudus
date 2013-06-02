package com.teamderpy.victusludus.game.map;


/**
 * The Class HeightCapper, whose purpose is to place upper and lower bounds on 2d arrays
 */
public class HeightCapper {

	/**
	 * Caps the height of the array at an upper value
	 *
	 * @param array the array
	 * @param height the height to cap at
	 */
	public static void capMaxHeight(final int[][] array, final int height) {
		for(int i=0; i<array.length; i++){
			for(int j=0; j<array[i].length; j++){
				if(array[i][j] > height){
					array[i][j] = height;
				}
			}
		}
	}


	/**
	 * Caps the height of the array at a lower value
	 *
	 * @param array the array
	 * @param height the height to cap at
	 */
	public static void capMinHeight(final int[][] array, final int height) {
		for(int i=0; i<array.length; i++){
			for(int j=0; j<array[i].length; j++){
				if(array[i][j] < height){
					array[i][j] = height;
				}
			}
		}
	}
}
