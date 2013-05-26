package com.teamderpy.victusludus.game.renderer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.game.GameDimensions;

// TODO: Auto-generated Javadoc
/**
 * The Class BackgroundRenderer.
 */
public class BackgroundRenderer {
	private GameDimensions dimensions;

	/** The bg color. */
	private Color bgColor;

	/** The background image */
	private Image bgImage = null;

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
		if(this.bgImage == null){
			VictusLudus.e.graphics.setColor(this.bgColor);
			VictusLudus.e.graphics.fill(new Rectangle(0, 0, this.dimensions.getWidth(), this.dimensions.getHeight()));
		} else {
			for(int i=0; i<this.dimensions.getWidth(); i+= this.bgImage.getWidth()){
				for(int j=0; j<this.dimensions.getHeight(); j+= this.bgImage.getHeight()){
					this.bgImage.draw(i, j);
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
	}
}
