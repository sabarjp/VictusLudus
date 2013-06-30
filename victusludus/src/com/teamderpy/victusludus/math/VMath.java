
package com.teamderpy.victusludus.math;

public class VMath {
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
	static public float linearInterpolation (final float x1, final float x2, final float y1, final float y2, final float desiredX) {
		return y1 + ((y2 - y1) * ((desiredX - x1) / (x2 - x1)));
	}
}
