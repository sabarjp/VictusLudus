package com.teamderpy.victusludus.game;

// TODO: Auto-generated Javadoc
/**
 * The Class EuclideanObject represents an object that exists in Euclidean space.
 */
public class EuclideanObject{

	/** The world coordinate of the euclidean object. */
	private WorldCoord worldCoord;

	/**
	 * Whether or not this object totally blocks sight of other objects that exist
	 * in the screen coordinates where this object lies.  This is used for the render
	 * of the object */
	private boolean isTotallyBlockingLOS;

	/**
	 * Whether or not to render on the next pass
	 */
	private boolean canRenderNextPass;

	/**
	 * Instantiates a new euclidean object.
	 */
	public EuclideanObject(){
		this.setWorldCoord(new WorldCoord(-1,-1,-1));
		this.setTotallyBlockingLOS(false);
		this.setRenderNextPass(false);
	}

	/**
	 * Instantiates a new euclidean object.
	 *
	 * @param x the x position of the object
	 * @param y the y position of the object
	 * @param z the z position of the object
	 */
	public EuclideanObject(final int x, final int y, final int z){
		this.setWorldCoord(new WorldCoord(x,y,z));
		this.setTotallyBlockingLOS(false);
		this.setRenderNextPass(false);
	}

	/**
	 * Instantiates a new euclidean object.
	 *
	 * @param pos the position of the object
	 */
	public EuclideanObject(final WorldCoord pos){
		this.setWorldCoord(pos);
		this.setTotallyBlockingLOS(false);
	}

	/**
	 * Gets the world coord of this object
	 *
	 * @return the world coord of this object
	 */
	public WorldCoord getWorldCoord() {
		return this.worldCoord;
	}

	/**
	 * Sets the world coord of this object
	 *
	 * @param worldCoord the new world coord of this object
	 */
	public void setWorldCoord(final WorldCoord worldCoord) {
		this.worldCoord = worldCoord;
	}

	/**
	 * Whether or not this object totally blocks sight of other objects that exist in the
	 * screen coordinates where this object lies.
	 *
	 * @return true, if is totally blocking line of sight
	 */
	public boolean isTotallyBlockingLOS() {
		return this.isTotallyBlockingLOS;
	}

	/**
	 * Sets whether or not this object totally blocks sight of other objects that exist in the
	 * screen coordinates where this object lies.
	 *
	 * @param isTotallyBlockingLOS whether line of sight blocks or not
	 */
	public void setTotallyBlockingLOS(final boolean isTotallyBlockingLOS) {
		this.isTotallyBlockingLOS = isTotallyBlockingLOS;
	}

	public boolean canRenderNextPAss() {
		return this.canRenderNextPass;
	}

	public void setRenderNextPass(final boolean canRenderNextPass) {
		this.canRenderNextPass = canRenderNextPass;
	}
}
