package com.teamderpy.victusludus.game.renderer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.game.GameDimensions;


/**
 * The Class BackgroundRenderer.
 */
public class BackgroundRenderer {
	private GameDimensions dimensions;

	/** The bg color. */
	private Color bgColor = null;

	/** The background image */
	private Image bgImage = null;

	/** whether or not the background tiles flip as they are laid out */
	private boolean isFlipTiling = true;
	private boolean isStretchingImage = true;

	/**
	 * Instantiates a new background renderer using a background color
	 *
	 * @param gameRenderer the game renderer
	 * @param bgColor the bg color
	 */
	public BackgroundRenderer(final GameDimensions dimensions, final Color bgColor){
		this.dimensions = dimensions;
		this.bgColor = bgColor;
	}

	/**
	 * Instantiates a new background renderer using a background image
	 *
	 * @param gameRenderer the game renderer
	 * @param path the path to the background image
	 */
	public BackgroundRenderer(final GameDimensions dimensions, final String path){
		this.dimensions = dimensions;

		try {
			this.bgImage = new Image(path, false, Image.FILTER_NEAREST, Color.magenta);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Render.
	 */
	public void render(){
		if(this.bgColor != null){
			VictusLudusGame.engine.graphics.setColor(this.bgColor);
			VictusLudusGame.engine.graphics.fill(new Rectangle(0, 0, this.dimensions.getWidth(), this.dimensions.getHeight()));
		}

		if(this.bgImage != null){
			VictusLudusGame.engine.graphics.setColor(Color.black);
			VictusLudusGame.engine.graphics.fill(new Rectangle(0, 0, this.dimensions.getWidth(), this.dimensions.getHeight()));

			if(this.isStretchingImage){
				this.bgImage.draw(0, 0, this.dimensions.getWidth(), this.dimensions.getHeight());
			}else{
				for(int i=0; i<this.dimensions.getWidth(); i+= this.bgImage.getWidth()){
					for(int j=0; j<this.dimensions.getHeight(); j+= this.bgImage.getHeight()){
						if(this.isFlipTiling){
							int imod = i/this.bgImage.getWidth() % 2;
							int jmod = j/this.bgImage.getHeight() % 2;

							if(imod == 1 && jmod == 1){
								//both
								this.bgImage.getFlippedCopy(true, true).draw(i, j);
							} else if(imod == 1 && jmod == 0){
								//horizontal
								this.bgImage.getFlippedCopy(true, false).draw(i, j);
							} else if(imod == 0 && jmod == 1){
								//vertical
								this.bgImage.getFlippedCopy(false, true).draw(i, j);
							} else if(imod == 0 && jmod == 0){
								//normal
								this.bgImage.draw(i, j);
							}
						} else {
							this.bgImage.draw(i, j);
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
		this.bgImage = null;
	}

	public void setBgImage(final String path){
		try {
			this.bgImage = new Image(path, false, Image.FILTER_NEAREST, Color.magenta);
			this.bgColor = null;
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void setBgImage(final Image bgImage) {
		this.bgImage = bgImage;
	}

	public void destroy(){
		try {
			this.bgImage.destroy();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isFlipTiling() {
		return this.isFlipTiling;
	}

	public void setFlipTiling(final boolean isFlipTiling) {
		this.isFlipTiling = isFlipTiling;
	}

	public boolean isStretchingImage() {
		return this.isStretchingImage;
	}

	public void setStretchingImage(final boolean isStretchingImage) {
		this.isStretchingImage = isStretchingImage;
	}
}