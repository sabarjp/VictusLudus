package com.teamderpy.victusludus.game.renderer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import com.teamderpy.victusludus.game.GameDimensions;

// TODO: Auto-generated Javadoc
/**
 * The Class DebugRenderer.
 */
public class DebugRenderer {
	private GameDimensions dimensions;

	/** The overlay image */
	private Image debugImage = null;

	/**
	 * Instantiates a new DebugRenderer.
	 *
	 * @param gameRenderer the game renderer
	 */
	public DebugRenderer(final GameDimensions dimensions){
		this.dimensions = dimensions;

		try {
			this.debugImage = Image.createOffscreenImage(this.dimensions.getWidth(), this.dimensions.getHeight());
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Render.
	 */
	public void render(){
		this.debugImage.draw(0, 0);

		try {
			Graphics g = this.debugImage.getGraphics();
			g.setColor(Color.transparent);
			g.fill(new Rectangle(0, 0, this.debugImage.getWidth(), this.debugImage.getHeight()));
			g.flush();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	public Image getDebugImage() {
		return this.debugImage;
	}
}
