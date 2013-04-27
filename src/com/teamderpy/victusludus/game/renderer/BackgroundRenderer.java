package com.teamderpy.victusludus.game.renderer;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

import com.teamderpy.victusludus.data.VictusLudus;

// TODO: Auto-generated Javadoc
/**
 * The Class BackgroundRenderer.
 */
public class BackgroundRenderer {

	/** The game renderer. */
	public GameRenderer gameRenderer;

	/** The bg color. */
	private Color bgColor;

	/**
	 * Instantiates a new background renderer.
	 *
	 * @param gameRenderer the game renderer
	 * @param bgColor the bg color
	 */
	public BackgroundRenderer(final GameRenderer gameRenderer, final Color bgColor){
		this.gameRenderer = gameRenderer;
		this.bgColor = bgColor;
	}

	/**
	 * Render.
	 */
	public void render(){
		VictusLudus.e.graphics.setColor(this.bgColor);
		VictusLudus.e.graphics.fill(new Rectangle(0, 0, VictusLudus.e.X_RESOLUTION(), VictusLudus.e.Y_RESOLUTION()));
	}

	/**
	 * Gets the bg color.
	 *
	 * @return the bg color
	 */
	public Color getBgColor() {
		return this.bgColor;
	}

	/**
	 * Sets the bg color.
	 *
	 * @param bgColor the new bg color
	 */
	public void setBgColor(final Color bgColor) {
		this.bgColor = bgColor;
	}
}
