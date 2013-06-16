
package com.teamderpy.victusludus.engine;

import com.badlogic.gdx.Graphics.DisplayMode;

public class FlexibleDisplayMode implements Comparable<FlexibleDisplayMode> {
	public final int width;
	public final int height;
	public final boolean isFullscreen;

	public DisplayMode displayMode;

	public FlexibleDisplayMode (final int width, final int height, final boolean isFullscreen) {
		this.displayMode = null;
		this.width = width;
		this.height = height;
		this.isFullscreen = isFullscreen;
	}

	@Override
	public boolean equals (final Object obj) {
		FlexibleDisplayMode fdm = (FlexibleDisplayMode)obj;

		return this.width == fdm.width
			&& this.height == fdm.height
			&& this.isFullscreen == fdm.isFullscreen
			&& ((this.displayMode == null && fdm.displayMode == null) || ((this.displayMode != null && fdm.displayMode != null) && this.displayMode
				.equals(fdm.displayMode)));
	}

	/** Only checks for equality on the width and height of the display
	 * 
	 * @param obj the other object to compare to
	 * @return true if equals, false otherwise */
	public boolean softEquals (final Object obj) {
		FlexibleDisplayMode fdm = (FlexibleDisplayMode)obj;

		return this.width == fdm.width && this.height == fdm.height;
	}

	@Override
	public int hashCode () {
		return 31 * this.width + 13 * this.height + 81 * (this.isFullscreen ? 1 : 0) + 43 * this.displayMode.hashCode();
	}

	@Override
	public int compareTo (final FlexibleDisplayMode o) {
		long cmp0, cmp1;

		cmp0 = this.height * this.width;
		cmp1 = o.height * o.width;

		if (cmp0 < cmp1) {
			return -1;
		}

		if (cmp0 > cmp1) {
			return 1;
		}

		return 0;
	}
}
