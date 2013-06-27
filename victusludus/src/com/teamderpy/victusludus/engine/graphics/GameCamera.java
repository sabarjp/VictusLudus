package com.teamderpy.victusludus.engine.graphics;


/**
 * The Class GameCamera.
 */
public class GameCamera {

	/** The offset x. */
	private int offsetX = 0;

	/** The offset y. */
	private int offsetY = 0;

	/** The zoom. */
	private float zoom = 0.50F;

	/**
	 * Gets the offset x.
	 *
	 * @return the offset x
	 */
	public int getOffsetX() {
		return this.offsetX;
	}

	/**
	 * Sets the offset x.
	 *
	 * @param offsetX the new offset x
	 */
	public void setOffsetX(final int offsetX) {
		this.offsetX = offsetX;
	}

	/**
	 * Gets the offset y.
	 *
	 * @return the offset y
	 */
	public int getOffsetY() {
		return this.offsetY;
	}

	/**
	 * Sets the offset y.
	 *
	 * @param offsetY the new offset y
	 */
	public void setOffsetY(final int offsetY) {
		this.offsetY = offsetY;
	}

	/**
	 * Gets the zoom.
	 *
	 * @return the zoom
	 */
	public float getZoom() {
		return this.zoom;
	}

	/**
	 * Sets the zoom.
	 *
	 * @param zoom the new zoom
	 */
	public void setZoom(final float zoom) {
		this.zoom = zoom;
	}

	/**
	 * Move camera up.
	 */
	public void moveCameraUp(){
		this.setOffsetY((int) (this.getOffsetY() + 20 / (this.getZoom()/2)));
	}

	/**
	 * Move camera down.
	 */
	public void moveCameraDown(){
		this.setOffsetY((int) (this.getOffsetY() - 20 / (this.getZoom()/2)));
	}

	/**
	 * Move camera left.
	 */
	public void moveCameraLeft(){
		this.setOffsetX((int) (this.getOffsetX() + 20 / (this.getZoom()/2)));
	}

	/**
	 * Move camera right.
	 */
	public void moveCameraRight(){
		this.setOffsetX((int) (this.getOffsetX() - 20 / (this.getZoom()/2)));
	}
}
