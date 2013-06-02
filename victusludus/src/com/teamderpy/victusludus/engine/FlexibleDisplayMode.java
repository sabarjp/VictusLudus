package com.teamderpy.victusludus.engine;

import com.badlogic.gdx.Graphics.DisplayMode;

public class FlexibleDisplayMode {
	public final int width;
	public final int height;
	public final boolean isFullscreen;
	
	public DisplayMode displayMode;
	
	public FlexibleDisplayMode (int width, int height, boolean isFullscreen) {
		this.displayMode = null;
		this.width = width;
		this.height = height;
		this.isFullscreen = isFullscreen;
	}
}
