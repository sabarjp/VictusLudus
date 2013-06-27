
package com.teamderpy.victusludus.renderer.common;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamderpy.victusludus.engine.graphics.GameDimensions;

/** The Class DebugRenderer. */
public class DebugRenderer {
	private GameDimensions dimensions;

	/** The overlay image */
	private Texture debugImage = null;

	/**
	 * Instantiates a new DebugRenderer.
	 * 
	 * @param gameRenderer the game renderer
	 */
	public DebugRenderer (final GameDimensions dimensions) {
		this.dimensions = dimensions;

		this.debugImage = new Texture(new Pixmap(this.dimensions.getRenderWidth(), this.dimensions.getRenderHeight(),
			Format.RGBA8888));
	}

	/** Render. */
	public void render (final SpriteBatch batch, final float deltaT) {
		batch.draw(this.debugImage, 0, 0);
	}

	public Texture getDebugImage () {
		return this.debugImage;
	}
}
