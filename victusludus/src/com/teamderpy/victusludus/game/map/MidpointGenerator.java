package com.teamderpy.victusludus.game.map;

import java.util.Random;


/**
 * The Class MidpointGenerator.
 */
public class MidpointGenerator implements INoiseGenerator {

	/** The seed. */
	private int seed;

	/** The rand. */
	private Random rand;

	/** The persistence. */
	private float persistence;

	/**
	 * Instantiates a new MidpointGenerator generator.
	 */
	public MidpointGenerator(){
		this.persistence = 0.25F;
		this.rand = new Random();
		this.seed();
	}

	/**
	 * Instantiates a new MidpointGenerator generator.
	 */
	public MidpointGenerator(final float persistence){
		this.persistence = persistence;
		this.rand = new Random();
		this.seed();
	}

	/**
	 * Instantiates a new MidpointGenerator generator.
	 */
	public MidpointGenerator(final float persistence, final float seed){
		this.persistence = persistence;
		this.rand = new Random();
		this.seed = (int) seed;
	}

	/**
	 * Seed.
	 */
	public void seed(){
		//this instance will have a new random seed
		this.seed = this.rand.nextInt()/2;
	}

	/**
	 * Seed.
	 */
	public void seed(final float seed){
		this.seed = (int) seed;
	}

	/**
	 * Smooth noise using basic blur function.
	 *
	 * @param x the x coord
	 * @param y the y coord
	 * @return the float
	 */
	private float smoothNoise(final int x, final int y){
		float corners = this.randomNoise(x-1, y-1) + this.randomNoise(x-1, y+1) + this.randomNoise(x+1, y-1) + this.randomNoise(x+1, y+1);
		float sides = this.randomNoise(x, y-1) + this.randomNoise(x, y+1) + this.randomNoise(x+1, y) + this.randomNoise(x-1, y);
		float center = this.randomNoise(x, y);

		return corners/16 + sides/8 + center/4;
	}

	/**
	 * Smooth noise using simple gaussian blur function.
	 *
	 * @param x the x coord
	 * @param y the y coord
	 * @return the float
	 */
	private float simpleGaussianNoise(final int x, final int y){
		float corners = this.randomNoise(x-1, y-1) + this.randomNoise(x-1, y+1) + this.randomNoise(x+1, y-1) + this.randomNoise(x+1, y+1);
		float sides = this.randomNoise(x, y-1) + this.randomNoise(x, y+1) + this.randomNoise(x+1, y) + this.randomNoise(x-1, y);
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
	private float gaussianNoise(final int x, final int y, final GaussianBlur blurMachine){
		float[][] blurMatrix = blurMachine.getGaussianMatrix();

		float sum = 0.0F;

		for(int i=0; i<blurMatrix.length; i++){
			for(int j=0; j<blurMatrix[i].length; j++){
				sum += this.randomNoise(x+i-blurMachine.getPixelRadius(), y+j-blurMachine.getPixelRadius()) * blurMatrix[i][j];
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
	private float randomNoise(final int x, final int y){
		int h = x * 29 + y * 113;
		h += this.seed;
		h = h << 13 ^ h;
		return 1.0F - (h * (h * h * 15731 + 789221) + 1376312589 & 0x7fffffff) / 1073741824.0F;
	}

	private boolean isPowerOfTwo(final int number){
		if ((number & -number) == number){
			return true;
		}

		return false;
	}

	private int getNextPowerOfTwo(final int number){
		int nextPowerOfTwo = 2;

		while(number > nextPowerOfTwo){
			nextPowerOfTwo *= 2;
		}

		return nextPowerOfTwo;
	}


	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.game.map.INoiseGenerator#generate(int, int)
	 */
	@Override
	public int[][] generateInt(final int width, final int height, final boolean normalize) {
		int[][] array;
		int actualSize;

		//array must be a multiple of 2^x + 1
		int maxSide = Math.max(width,height);

		if(this.isPowerOfTwo(maxSide)){
			array = new int[maxSide+1][maxSide+1];
			actualSize = maxSide;
		} else {
			int num = this.getNextPowerOfTwo(Math.max(width,height));
			array = new int[num+1][num+1];
			actualSize = num;
		}

		int highestPoint = 0;
		int lowestPoint = 9999;

		for(int i=0; i<array.length; i++){
			for(int j=0; j<array[i].length; j++){
				array[i][j] = 1;
			}
		}

		//seed corners
		array[0][0] = (int) (5 * this.randomNoise(0, 0));
		array[actualSize][0] = (int) (5 * this.randomNoise(actualSize, 0));
		array[0][actualSize] = (int) (5 * this.randomNoise(0, actualSize));
		array[actualSize][actualSize] = (int) (5 * this.randomNoise(actualSize, actualSize));

		//find midpoints
		int squareSize = actualSize;
		int iteration = 1;
		while(squareSize > 1){
			for(int x=0; x<actualSize; x+=squareSize){
				for(int y=0; y<actualSize; y+=squareSize){
					//create middle point
					int midX = x + squareSize/2;
					int midY = y + squareSize/2;

					int cornerNWValue = array[x][y];
					int cornerNEValue = array[x + squareSize][y];
					int cornerSEValue = array[x + squareSize][y + squareSize];
					int cornerSWValue = array[x][y + squareSize];

					int averageCenter = (int) ((cornerNWValue + cornerNEValue + cornerSEValue + cornerSWValue) / 4.0F);
					int averageNorth = (int) ((cornerNWValue + cornerNEValue) / 2.0F);
					int averageEast = (int) ((cornerNEValue + cornerSEValue) / 2.0F);
					int averageSouth = (int) ((cornerSEValue + cornerSWValue) / 2.0F);
					int averageWest = (int) ((cornerNWValue + cornerSWValue) / 2.0F);

					array[midX][midY] = (int) (averageCenter + 5 * Math.pow(this.persistence, iteration) *  this.randomNoise(midX, midY));

					array[midX][y] = (int) (averageNorth + 5 * Math.pow(this.persistence, iteration) *  this.randomNoise(midX, midY));
					array[midX][y+squareSize] = (int) (averageSouth + 5 * Math.pow(this.persistence, iteration) *  this.randomNoise(midX, y+squareSize));

					array[x][midY] = (int) (averageWest + 5 * Math.pow(this.persistence, iteration) *  this.randomNoise(x, midY));
					array[x+squareSize][midY] = (int) (averageEast + 5 * Math.pow(this.persistence, iteration) *  this.randomNoise(x+squareSize, midY));
				}
			}

			//downsize square
			squareSize /= 2;
			iteration++;
		}

		//high and low points
		for (int[] element : array) {
			for (int element2 : element) {
				if(element2 > highestPoint) { highestPoint = element2; }
				if(element2 < lowestPoint) { lowestPoint = element2; }
			}
		}

		//normalize terrain
		if(normalize){
			for(int i=0; i<array.length; i++){
				for(int j=0; j<array[i].length; j++){
					array[i][j] -= lowestPoint - 15;
				}
			}
		}

		//resize
		int[][] returnArray = new int[width][height];
		for(int i=0; i<returnArray.length; i++){
			for(int j=0; j<returnArray[i].length; j++){
				returnArray[i][j] = array[i][j];
			}
		}

		return returnArray;
	}

	@Override
	public float[][] generateFloat(final int width, final int height, final float minValue, final float maxValue, final boolean blur) {
		float[][] array;
		int actualSize;

		//array must be a multiple of 2^x + 1
		int maxSide = Math.max(width,height);

		if(this.isPowerOfTwo(maxSide)){
			array = new float[maxSide+1][maxSide+1];
			actualSize = maxSide;
		} else {
			int num = this.getNextPowerOfTwo(Math.max(width,height));
			array = new float[num+1][num+1];
			actualSize = num;
		}

		System.err.println("requested area " + width + "x" + height + "  and created " + actualSize + "x" + actualSize);

		float highestPoint = 0;
		float lowestPoint = 9999;

		for(int i=0; i<array.length; i++){
			for(int j=0; j<array[i].length; j++){
				array[i][j] = 0;
			}
		}

		//seed corners
		array[0][0] =  this.randomNoise(0, 0);
		array[actualSize][0] =  this.randomNoise(actualSize, 0);
		array[0][actualSize] =  this.randomNoise(0, actualSize);
		array[actualSize][actualSize] = this.randomNoise(actualSize, actualSize);

		//find midpoints
		int squareSize = actualSize;
		int iteration = 1;
		while(squareSize >= 1){
			for(int x=0; x<actualSize; x+=squareSize){
				for(int y=0; y<actualSize; y+=squareSize){
					//create middle point
					int midX = x + squareSize/2;
					int midY = y + squareSize/2;

					float cornerNWValue = array[x][y];
					float cornerNEValue = array[x + squareSize][y];
					float cornerSEValue = array[x + squareSize][y + squareSize];
					float cornerSWValue = array[x][y + squareSize];

					float averageCenter = (cornerNWValue + cornerNEValue + cornerSEValue + cornerSWValue) / 4.0F;
					float averageNorth =  (cornerNWValue + cornerNEValue) / 2.0F;
					float averageEast =  (cornerNEValue + cornerSEValue) / 2.0F;
					float averageSouth =  (cornerSEValue + cornerSWValue) / 2.0F;
					float averageWest =  (cornerNWValue + cornerSWValue) / 2.0F;

					array[midX][midY] =  (float) (averageCenter + Math.pow(this.persistence, iteration) * this.randomNoise(midX, midY));

					array[midX][y] =  (float) (averageNorth + Math.pow(this.persistence, iteration) *  this.randomNoise(midX, midY));
					array[midX][y+squareSize] =  (float) (averageSouth + Math.pow(this.persistence, iteration) *  this.randomNoise(midX, y+squareSize));

					array[x][midY] =  (float) (averageWest + Math.pow(this.persistence, iteration) *  this.randomNoise(x, midY));
					array[x+squareSize][midY] =  (float) (averageEast + Math.pow(this.persistence, iteration) *  this.randomNoise(x+squareSize, midY));
				}
			}

			//downsize square
			squareSize /= 2;
			iteration++;
		}

		//high and low points
		for (float[] element : array) {
			for (float element2 : element) {
				if(element2 > highestPoint) { highestPoint = element2; }
				if(element2 < lowestPoint) { lowestPoint = element2; }
			}
		}

		System.err.println("high: " + highestPoint + "  low: " + lowestPoint);

		//normalize
		for(int i=0; i<array.length; i++){
			for(int j=0; j<array[i].length; j++){
				array[i][j] = MidpointGenerator.linearInterpolation(lowestPoint, highestPoint, minValue, maxValue, array[i][j]);
			}
		}

		//resize
		float[][] returnArray = new float[width][height];
		for(int i=0; i<returnArray.length; i++){
			for(int j=0; j<returnArray[i].length; j++){
				returnArray[i][j] = array[i][j];
			}
		}

		System.err.println("returning size of " + width + "x" + height);

		return returnArray;
	}


	/**
	 * Linear interpolation between two known points
	 * 
	 * @param x1
	 * @param x2
	 * @param y1
	 * @param y2
	 * @param desiredX the x at which we desire an interpolated Y
	 * @return the interpolated Y
	 */
	private static float linearInterpolation(final float x1, final float x2, final float y1, final float y2, final float desiredX){
		float result = y1 + (y2 - y1) * (desiredX - x1 / (x2 - x1));

		if(result < y1){
			result = y1;
		} else if(result > y2){
			result = y2;
		}

		return result;
	}

	/**
	 * Gets the persistence.
	 *
	 * @return the persistence
	 */
	public float getPersistence() {
		return this.persistence;
	}

	/**
	 * Sets the persistence.
	 *
	 * @param persistence the new persistence
	 */
	public void setPersistence(final float persistence) {
		this.persistence = persistence;
	}

}
