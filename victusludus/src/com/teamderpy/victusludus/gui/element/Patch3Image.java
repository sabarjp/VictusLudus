package com.teamderpy.victusludus.gui.element;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamderpy.victusludus.VictusLudusGame;

/**
 * The Class Patch3Image is an image split into three parts (left, middle, right)
 * that can be dynamically resized by filling empty space with the middle section
 * of the image.
 */
public class Patch3Image {
	/** The button image. */
	private Sprite buttonImage;

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
	 * @throws InvalidDimensionsException exception signifying invalid dimensions
	 */
	public Patch3Image(final String path, final int width, final int height, final int border) throws InvalidDimensionsException{
		this.load(path, width, height, border);
	}

	/**
	 * Instantiates a new sitched gui image.
	 *
	 * @param path the path
	 * @param width the width
	 * @param height the height
	 * @throws InvalidDimensionsException exception signifying invalid dimensions
	 */
	public Patch3Image(final String path, final int width, final int height) throws InvalidDimensionsException{
		Sprite loadedImage = VictusLudusGame.resources.getTextureAtlasGUI().createSprite(path);

		final float scale = (float)height / (float)loadedImage.getHeight();

		loadedImage.scale(scale);

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
	 * @throws InvalidDimensionsException exception signifying invalid dimensions
	 */
	private void load(final String path, final int width, final int height, int borderSize) throws InvalidDimensionsException{
		Sprite loadedImage = VictusLudusGame.resources.getTextureAtlasGUI().createSprite(path);
		
		System.err.println("loading image: " + path);
		System.err.println("size " + loadedImage.getWidth() + "x" + loadedImage.getHeight());
		
		System.err.println("desired size: " + width + "x" + height);
		
		final float scale = (float)height / (float)loadedImage.getHeight();
		
		System.err.println("scaled size: " + (int)(width/scale) + "x" + (int)(height/scale));

		if(borderSize <= 0) {
			borderSize = 1;
		}else if(borderSize >= Math.min(loadedImage.getWidth(), loadedImage.getHeight())/2-1){
			borderSize = (int)(Math.min(loadedImage.getWidth(), loadedImage.getHeight())/2-1);
		}
		
		Pixmap leftPixmap = new Pixmap(borderSize, (int)(loadedImage.getHeight()), Format.RGBA8888);
		Pixmap centerPixmap = new Pixmap((int)(loadedImage.getWidth())-borderSize*2, (int)(loadedImage.getHeight()), Format.RGBA8888);
		Pixmap rightPixmap = new Pixmap(borderSize, (int)(loadedImage.getHeight()), Format.RGBA8888);
		
		System.err.println("left " + leftPixmap.getWidth() + "x" + leftPixmap.getHeight());
		System.err.println("cent " + centerPixmap.getWidth() + "x" + centerPixmap.getHeight());
		System.err.println("rigt " + rightPixmap.getWidth() + "x" + rightPixmap.getHeight());
		
		loadedImage.getTexture().getTextureData().prepare();
		Pixmap imagePixmap = loadedImage.getTexture().getTextureData().consumePixmap();
		
		leftPixmap.drawPixmap(imagePixmap, 0, 0, 0, 0, leftPixmap.getWidth(), leftPixmap.getHeight());
		centerPixmap.drawPixmap(imagePixmap, 0, 0, borderSize, 0, leftPixmap.getWidth(), leftPixmap.getHeight());
		rightPixmap.drawPixmap(imagePixmap, 0, 0, (int)(loadedImage.getWidth()*loadedImage.getScaleX()-borderSize), 0, leftPixmap.getWidth(), leftPixmap.getHeight());
		
		loadedImage.getTexture().getTextureData().disposePixmap();
		imagePixmap.dispose();
		
		//stitch images together
		this.buttonImage = new Sprite(new Texture((int)(width/scale), (int)(height/scale), Format.RGBA8888), (int)(width/scale), (int)(height/scale));

		if(centerPixmap.getWidth() <= 0 || centerPixmap.getHeight() <= 0){
			throw new InvalidDimensionsException();
		}

		for(int i=0; i<this.buttonImage.getWidth(); i += centerPixmap.getWidth()){
			this.buttonImage.getTexture().draw(centerPixmap, i, 0);
		}

		this.buttonImage.getTexture().draw(leftPixmap, 0, 0);
		this.buttonImage.getTexture().draw(rightPixmap, (int)(this.buttonImage.getWidth()-rightPixmap.getWidth()), 0);
		
		this.buttonImage.scale(scale - this.buttonImage.getScaleX());

		this.width = width;
		this.height = height;
		
		leftPixmap.dispose();
		centerPixmap.dispose();
		rightPixmap.dispose();
	}

	/**
	 * Draw.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void draw(SpriteBatch batch, final float x, final float y){
		batch.enableBlending();
		
		this.buttonImage.setPosition(x, y);
		this.buttonImage.draw(batch);
		
		batch.disableBlending();
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
