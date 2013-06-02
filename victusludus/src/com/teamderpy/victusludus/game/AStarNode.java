package com.teamderpy.victusludus.game;


/**
 * The Class AStarNode.
 */
public class AStarNode implements Comparable<AStarNode>{
	
	/** The f. */
	private int f;
	
	/** The g. */
	private int g;
	
	/** The h. */
	private int h;

	/**
	 * Instantiates a new a star node.
	 */
	public AStarNode(){
		this.f = 0;
		this.g = 0;
		this.h = 0;
	}

	/**
	 * Gets the f.
	 *
	 * @return the f
	 */
	public int getF() {
		return this.f;
	}

	/**
	 * Sets the f.
	 *
	 * @param f the new f
	 */
	public void setF(final int f) {
		this.f = f;
	}

	/**
	 * Gets the g.
	 *
	 * @return the g
	 */
	public int getG() {
		return this.g;
	}

	/**
	 * Sets the g.
	 *
	 * @param g the new g
	 */
	public void setG(final int g) {
		this.g = g;
	}

	/**
	 * Gets the h.
	 *
	 * @return the h
	 */
	public int getH() {
		return this.h;
	}

	/**
	 * Sets the h.
	 *
	 * @param h the new h
	 */
	public void setH(final int h) {
		this.h = h;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(final AStarNode o) {
		if(this.f < o.f){
			return -1;
		}

		if(this.f > o.f){
			return 1;
		}

		return 0;
	}
}
