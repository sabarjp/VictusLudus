package com.teamderpy.victusludus.game.map;

// TODO: Auto-generated Javadoc
/**
 * The Class GaussianBlur.
 */
public class GaussianBlur {

	/** The gaussian matrix. */
	private float[][] matrix;

	/** The center pixel's location. */
	private int centerPoint;

	/** The pixel radius to blur. */
	private int pixelRadius;

	/** The sigma value used in blur calcs. */
	private float sigma;

	/**
	 * Instantiates a new gaussian blur machine.
	 *
	 * @param pixelRadius the pixel radius to blur
	 * @param sigma the sigma value used in the gaussian std dev
	 */
	public GaussianBlur(int pixelRadius, final float sigma){
		if(pixelRadius < 1){
			pixelRadius = 1;
		}

		this.matrix = new float[pixelRadius + pixelRadius + 1][pixelRadius + pixelRadius + 1];
		this.centerPoint = pixelRadius;
		this.pixelRadius = pixelRadius;
		this.sigma = sigma;

		this.calculateMatrix();
		this.normalizeMatrix();
	}

	/**
	 * Gets the gaussian matrix (that should be calculated)
	 *
	 * @return the gaussian matrix
	 */
	public float[][] getGaussianMatrix(){
		return this.matrix;
	}

	/**
	 * Make sure the matrix can be used for weighted summation
	 */
	private void normalizeMatrix(){
		float sum = 0.0F;

		//find sum
		for (float[] row : this.matrix) {
			for (float element : row) {
				sum += element;
			}
		}

		//modify weights
		for(int i=0; i<this.matrix.length; i++){
			for(int j=0; j<this.matrix[i].length;j++){
				this.matrix[i][j] /= sum;
			}
		}
	}

	/**
	 * Calculate matrix using the gauss function
	 */
	private void calculateMatrix(){
		for(int i=0; i<this.matrix.length; i++){
			for(int j=0; j<this.matrix[i].length;j++){
				this.matrix[i][j] = this.gauss(i-this.centerPoint,j-this.centerPoint);
			}
		}
	}

	/**
	 * The gaussian function
	 *
	 * @param x the x coordinate offset from the origin
	 * @param y the y coordinate offset from the origin
	 * @return the float of the gaussian function calculation
	 */
	private float gauss(final int x, final int y){
		float sigma = this.sigma;

		return (float) (1.0F / (2.0F * Math.PI * (sigma * sigma)) * Math.pow(Math.E, - (x * x + y * y) / (2.0F * (sigma * sigma))));
	}

	public int getPixelRadius() {
		return this.pixelRadius;
	}
}
