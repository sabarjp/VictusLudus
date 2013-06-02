package com.teamderpy.victusludus.game;


/**
 * The Class WorldCoord.
 */
public class WorldCoord {
	
	/** The x. */
	private int x;
	
	/** The y. */
	private int y;
	
	/** The z. */
	private int z;

	/**
	 * Instantiates a new world coord.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public WorldCoord(final int x, final int y, final int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX(){
		return this.x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY(){
		return this.y;
	}

	/**
	 * Gets the z.
	 *
	 * @return the z
	 */
	public int getZ(){
		return this.z;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return this.x + "," + this.y + "," + this.z;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		return (this.x * 13 ^ this.y) * 29 ^ this.z;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if(o instanceof WorldCoord){
			WorldCoord wc = (WorldCoord)o;
			return this.x == wc.x && this.y == wc.y && this.z == wc.z;
		}

		return false;
	}
}
