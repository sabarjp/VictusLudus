package com.teamderpy.victusludus.game.renderer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import com.teamderpy.victusludus.data.VictusLudus;

// TODO: Auto-generated Javadoc
/**
 * The Class DebugRenderer.
 */
public class DebugRenderer {

	/** The game renderer. */
	@SuppressWarnings("unused")
	private GameRenderer gameRenderer;

	/** The overlay image */
	private Image debugImage = null;

	/**
	 * Instantiates a new DebugRenderer.
	 *
	 * @param gameRenderer the game renderer
	 */
	public DebugRenderer(final GameRenderer gameRenderer){
		this.gameRenderer = gameRenderer;

		try {
			this.debugImage = Image.createOffscreenImage(VictusLudus.e.X_RESOLUTION(), VictusLudus.e.Y_RESOLUTION());
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
