package com.teamderpy.victusludus.gui.element;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.teamderpy.victusludus.data.VictusLudus;


// TODO: Auto-generated Javadoc
/**
 * The Class SitchedGUIImage.
 */
public class SitchedGUIImage {
	
	/** The button image. */
	private Image buttonImage;

	/** The width. */
	private int width;
	
	/** The height. */
	private int height;

	/**
	 * Instantiates a new sitched gui image.
	 *
	 * @param path the path
	 * @param width the width
	 * @param height the height
	 * @param border the border
	 * @throws SlickException the slick exception
	 * @throws ImageSizeException the image size exception
	 */
	public SitchedGUIImage(final String path, final int width, final int height, final int border) throws SlickException, ImageSizeException{
		this.load(path, width, height, border);
	}

	/**
	 * Instantiates a new sitched gui image.
	 *
	 * @param path the path
	 * @param width the width
	 * @param height the height
	 * @throws SlickException the slick exception
	 * @throws ImageSizeException the image size exception
	 */
	public SitchedGUIImage(final String path, final int width, final int height) throws SlickException, ImageSizeException{
		Image loadedImage = new Image(path, false, Image.FILTER_NEAREST, Color.magenta);

		final float scale = (float)height / (float)loadedImage.getHeight();

		loadedImage = loadedImage.getScaledCopy(scale);

		int borderSize = (int)(Math.min(loadedImage.getWidth(), loadedImage.getHeight())/3.0F);

		if(borderSize <= 0) {
			borderSize = 1;
		}

		this.load(path, width, height, borderSize);
	}

	/**
	 * Load.
	 *
	 * @param path the path
	 * @param width the width
	 * @param height the height
	 * @param borderSize the border size
	 * @throws SlickException the slick exception
	 * @throws ImageSizeException the image size exception
	 */
	private void load(final String path, final int width, final int height, int borderSize) throws SlickException, ImageSizeException{
		Image loadedImage = new Image(path, false, Image.FILTER_NEAREST, Color.magenta);

		final float scale = (float)height / (float)loadedImage.getHeight();

		if(borderSize <= 0) {
			borderSize = 1;
		}else if(borderSize >= Math.min(loadedImage.getWidth(), loadedImage.getHeight())/2-1){
			borderSize = Math.min(loadedImage.getWidth(), loadedImage.getHeight())/2-1;
		}

		loadedImage = loadedImage.getScaledCopy(scale);

		borderSize *= scale;

		final Image left   = loadedImage.getSubImage(0, 0, borderSize, loadedImage.getHeight());
		final Image center = loadedImage.getSubImage(borderSize, 0, loadedImage.getWidth()-borderSize*2, loadedImage.getHeight());
		final Image right  = loadedImage.getSubImage(loadedImage.getWidth()-borderSize, 0, borderSize, loadedImage.getHeight());

		//stitch images together
		this.buttonImage = new Image(width, height, Image.FILTER_NEAREST);
		final Graphics g = VictusLudus.e.gbuf.getBufferGraphicsContext(width, height);

		g.clear();

		if(center.getWidth() <= 0 || center.getHeight() <= 0){
			throw new ImageSizeException();
		}

		for(int i=0; i<this.buttonImage.getWidth(); i += center.getWidth()){
			g.drawImage(center, i, 0);
		}

		g.drawImage(left, 0, 0);
		g.drawImage(right, this.buttonImage.getWidth()-right.getWidth(), 0);

		g.copyArea(this.buttonImage, 0, 0);

		g.flush();

		this.width = width;
		this.height = height;
	}

	/**
	 * Draw.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void draw(final float x, final float y){
		this.buttonImage.draw(x, y);
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return this.height;
	}
}
