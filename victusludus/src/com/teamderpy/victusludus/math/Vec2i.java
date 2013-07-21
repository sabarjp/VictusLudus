package com.teamderpy.victusludus.math;


/**
 * The Class Vec2i.
 */
public class Vec2i{
	
	/** The x. */
	public int x;
	
	/** The y. */
	public int y;

	/**
	 * Instantiates a new vec2i.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Vec2i(final int x, final int y){
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the null vector.
	 *
	 * @return the null vector
	 */
	public static Vec2i getNullVector(){
		return new Vec2i(0, 0);
	}

	/**
	 * Equals.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	public boolean equals(final Vec2i o) {
		return this.x == o.x && this.y == o.y;
	}

	/**
	 * Adds the.
	 *
	 * @param v the v
	 * @return the vec2i
	 */
	public Vec2i add(final Vec2i v){
		return new Vec2i(this.x + v.x, this.y + v.y);
	}

	/**
	 * Scale.
	 *
	 * @param i the i
	 * @return the vec2i
	 */
	public Vec2i scale(final int i){
		return new Vec2i(this.x * i, this.y * i);
	}

	/**
	 * Length.
	 *
	 * @return the double
	 */
	public double length(){
		return Math.sqrt(this.x*this.x + this.y*this.y);
	}

	/**
	 * Normalize.
	 *
	 * @return the vec2i
	 */
	public Vec2i normalize(){
		double len = this.length();

		return new Vec2i((int)(this.x/len), (int)(this.y/len));
	}

	/**
	 * Dot product.
	 *
	 * @param v the v
	 * @return the int
	 */
	public int dotProduct(final Vec2i v){
		return this.x * this.y + v.x * v.y;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		return this.x * 31 + this.y * 97;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return Integer.toHexString(this.hashCode()) + " (" + this.x + "," + this.y + ")";
	}
}
