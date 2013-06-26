
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

	/** The background image */
	private Texture bgImage = null;
	private Texture bgColor = null;

	/** whether or not the background tiles flip as they are laid out */
	private boolean isFlipTiling = true;
	private boolean isStretchingImage = true;

	/** whether or not to dispose the background image when done using it */
	private boolean isDisposeBGImgWhenDone = false;

	/**
	 * Instantiates a new background renderer using a background color
	 * 
	 * @param gameRenderer the game renderer
	 * @param bgColor the bg color
	 */
	public BackgroundRenderer (final GameDimensions dimensions, final Color bgColor) {
		this.dimensions = dimensions;
		this.bgColor = EasyGL.getPixelTexture(bgColor, 1, 1);
	}

	/**
	 * Instantiates a new background renderer using a background image
	 * 
	 * @param gameRenderer the game renderer
	 * @param path the path to the background image
	 */
	public BackgroundRenderer (final GameDimensions dimensions, final String path, final boolean isDisposeBGImgWhenDone) {
		this.dimensions = dimensions;
		this.isDisposeBGImgWhenDone = isDisposeBGImgWhenDone;
		this.bgImage = VictusLudusGame.resources.getTextureAtlasGUI().findRegion(path).getTexture();
		this.bgColor = EasyGL.getPixelTexture(Color.BLACK, 1, 1);
	}

	/** Render. */
	public void render (final SpriteBatch batch, final float deltaT) {
		if (this.bgColor != null) {
			batch.draw(this.bgColor, 0, 0, this.dimensions.getWidth(), this.dimensions.getHeight());
		}

		if (this.bgImage != null) {
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
	public Texture getBgColor () {
		return this.bgColor;
	}

	/**
	 * Sets the background color
	 * 
	 * @param bgColor the new background color
	 */
	public void setBgColor (final Color bgColor) {
		if (this.isDisposeBGImgWhenDone && this.bgImage != null) {
			this.bgImage.dispose();
		}

		if (this.bgColor != null) {
			this.bgColor.dispose();
		}

		this.bgColor = EasyGL.getPixelTexture(bgColor, 1, 1);
		this.bgImage = null;
	}

	/**
	 * Sets the background image to a texture at a path and sets the background color to black
	 * 
	 * @param path the path of the texture to load
	 */
	public void setBgImage (final String path, final boolean isDisposeBGImgWhenDone) {
		if (this.isDisposeBGImgWhenDone && this.bgImage != null) {
			this.bgImage.dispose();
		}

		if (this.bgColor != null) {
			this.bgColor.dispose();
		}

		this.bgImage = VictusLudusGame.resources.getTextureAtlasCosmos().findRegion(path).getTexture();
		this.bgColor = EasyGL.getPixelTexture(Color.BLACK, 1, 1);
		this.isDisposeBGImgWhenDone = isDisposeBGImgWhenDone;
	}

	/**
	 * Sets the background image to a texture at a path and sets the background color to black
	 * 
	 * @param bgImage the background image to load
	 */
	public void setBgImage (final Texture bgImage, final boolean isDisposeBGImgWhenDone) {
		if (this.isDisposeBGImgWhenDone && this.bgImage != null) {
			this.bgImage.dispose();
		}

		if (this.bgColor != null) {
			this.bgColor.dispose();
		}

		this.bgImage = bgImage;
		this.bgColor = EasyGL.getPixelTexture(Color.BLACK, 1, 1);

		this.isDisposeBGImgWhenDone = isDisposeBGImgWhenDone;
	}

	/**
	 * Dispose of textures
	 */
	public void dispose () {
		if (this.isDisposeBGImgWhenDone && this.bgImage != null) {
			this.bgImage.dispose();
		}

		if (this.bgColor != null) {
			this.bgColor.dispose();
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

	/**
	 * Whether or not the texture will be disposed
	 * @return
	 */
	public boolean isDisposeBGImgWhenDone () {
		return this.isDisposeBGImgWhenDone;
	}

	/**
	 * Sets whether or not the texture will be disposed
	 * @param disposeBackgroundImageWhenDone
	 */
	public void setDisposeBGImgWhenDone (final boolean isDisposeBGImgWhenDone) {
		this.isDisposeBGImgWhenDone = isDisposeBGImgWhenDone;
	}
}
