
package com.teamderpy.victusludus.game.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.engine.graphics.EasyGL;
import com.teamderpy.victusludus.game.GameDimensions;

/** The Class BackgroundRenderer. */
public class BackgroundRenderer {
	private GameDimensions dimensions;

	/** The bg color. */
	private Color bgColor = null;

	/** The background image */
	private Texture bgImage = null;

	/** whether or not the background tiles flip as they are laid out */
	private boolean isFlipTiling = true;
	private boolean isStretchingImage = true;

	/** whether the texture was generated on the fly or not */
	private boolean isTextureTemporary = false;

	/**
	 * Instantiates a new background renderer using a background color
	 * 
	 * @param gameRenderer the game renderer
	 * @param bgColor the bg color
	 */
	public BackgroundRenderer (final GameDimensions dimensions, final Color bgColor) {
		this.dimensions = dimensions;
		this.bgColor = bgColor;
	}

	/**
	 * Instantiates a new background renderer using a background image
	 * 
	 * @param gameRenderer the game renderer
	 * @param path the path to the background image
	 */
	public BackgroundRenderer (final GameDimensions dimensions, final String path) {
		this.dimensions = dimensions;

		this.bgImage = VictusLudusGame.resources.getTextureAtlasGUI().findRegion(path).getTexture();
	}

	/** Render. */
	public void render (final SpriteBatch batch, final float deltaT) {
		if (this.bgColor != null) {
			EasyGL.drawRect(batch, this.bgColor, 0, 0, this.dimensions.getWidth(), this.dimensions.getHeight());
			EasyGL.freePoolResources();
		}

		if (this.bgImage != null) {
			EasyGL.drawRect(batch, Color.BLACK, 0, 0, this.dimensions.getWidth(), this.dimensions.getHeight());
			EasyGL.freePoolResources();

			if (this.isStretchingImage) {
				batch.draw(this.bgImage, 0, 0, this.dimensions.getWidth(), this.dimensions.getHeight());
			} else {
				for (int i = 0; i < this.dimensions.getWidth(); i += this.bgImage.getWidth()) {
					for (int j = 0; j < this.dimensions.getHeight(); j += this.bgImage.getHeight()) {
						if (this.isFlipTiling) {
							int imod = i / this.bgImage.getWidth() % 2;
							int jmod = j / this.bgImage.getHeight() % 2;

							if (imod == 1 && jmod == 1) {
								// both
								batch.draw(this.bgImage, i, j, this.bgImage.getWidth(), this.bgImage.getHeight(), 0, 0,
									this.bgImage.getWidth(), this.bgImage.getHeight(), true, true);
							} else if (imod == 1 && jmod == 0) {
								// horizontal
								batch.draw(this.bgImage, i, j, this.bgImage.getWidth(), this.bgImage.getHeight(), 0, 0,
									this.bgImage.getWidth(), this.bgImage.getHeight(), true, false);
							} else if (imod == 0 && jmod == 1) {
								// vertical
								batch.draw(this.bgImage, i, j, this.bgImage.getWidth(), this.bgImage.getHeight(), 0, 0,
									this.bgImage.getWidth(), this.bgImage.getHeight(), false, true);
							} else if (imod == 0 && jmod == 0) {
								// normal
								batch.draw(this.bgImage, i, j);
							}
						} else {
							batch.draw(this.bgImage, i, j);
						}
					}
				}
			}
		}
	}

	/**
	 * Gets the bg color.
	 * 
	 * @return the bg color
	 */
	public Color getBgColor () {
		return this.bgColor;
	}

	/**
	 * Sets the bg color.
	 * 
	 * @param bgColor the new bg color
	 */
	public void setBgColor (final Color bgColor) {
		this.bgColor = bgColor;
		this.bgImage = null;
	}

	public void setBgImage (final String path, final boolean isTextureTemporary) {
		if (this.isTextureTemporary && this.bgImage != null) {
			this.bgImage.dispose();
		}

		this.bgImage = VictusLudusGame.resources.getTextureAtlasCosmos().findRegion(path).getTexture();
		this.bgColor = null;

		this.isTextureTemporary = isTextureTemporary;
	}

	public void setBgImage (final Texture bgImage, final boolean isTextureTemporary) {
		if (this.isTextureTemporary && this.bgImage != null) {
			this.bgImage.dispose();
		}

		this.bgImage = bgImage;

		this.isTextureTemporary = isTextureTemporary;
	}

	public void dispose () {
		if (this.isTextureTemporary && this.bgImage != null) {
			this.bgImage.dispose();
		}
	}

	@Override
	public void finalize () {
		this.dispose();
	}

	public boolean isFlipTiling () {
		return this.isFlipTiling;
	}

	public void setFlipTiling (final boolean isFlipTiling) {
		this.isFlipTiling = isFlipTiling;
	}

	public boolean isStretchingImage () {
		return this.isStretchingImage;
	}

	public void setStretchingImage (final boolean isStretchingImage) {
		this.isStretchingImage = isStretchingImage;
	}

	public boolean isTextureTemporary () {
		return this.isTextureTemporary;
	}

	public void setTextureTemporary (final boolean isTextureTemporary) {
		this.isTextureTemporary = isTextureTemporary;
	}
}
