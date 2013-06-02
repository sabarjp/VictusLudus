package com.teamderpy.victusludus.gui.element.imagebutton;

import org.newdawn.slick.SpriteSheet;


/**
 * The Class ImageButtonImage.
 */
public class ImageButtonImage {
	
	/** The Constant OFFSET_NONE. */
	public static final byte OFFSET_NONE     = 0x0;
	
	/** The Constant OFFSET_DISABLE. */
	public static final byte OFFSET_DISABLE  = 0x1;
	
	/** The Constant OFFSET_SELECT. */
	public static final byte OFFSET_SELECT   = 0x2;
	
	/** The Constant OFFSET_HOVER. */
	public static final byte OFFSET_HOVER    = 0x2;
	
	/** The Constant OFFSET_PRESS. */
	public static final byte OFFSET_PRESS    = 0x3;

	/** The id. */
	private int id;

	/** The width. */
	private int width;
	
	/** The height. */
	private int height;
	
	/** The scale. */
	private float scale;

	/** The sprite sheet. */
	private SpriteSheet spriteSheet;

	/**
	 * Instantiates a new image button image.
	 *
	 * @param id the id
	 * @param width the width
	 * @param height the height
	 * @param spriteSheet the sprite sheet
	 */
	public ImageButtonImage(final byte id, final int width, final int height, final SpriteSheet spriteSheet){
		this.spriteSheet = spriteSheet;

		this.scale = (float)height / (float)(spriteSheet.getWidth() / spriteSheet.getHorizontalCount());

		this.id = id;
		this.width = width;
		this.height = height;
	}

	/**
	 * Draw.
	 *
	 * @param x the x
	 * @param y the y
	 * @param offset the offset
	 */
	public void draw(final float x, final float y, final byte offset){
		this.spriteSheet.startUse();

		this.spriteSheet.renderInUse((int)x,  (int)y,
				this.width, this.height,
				this.getSpriteSheetCol(offset), this.getSpriteSheetRow(offset));

		this.spriteSheet.endUse();
	}

	/**
	 * Draw.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void draw(final float x, final float y){
		this.draw(x, y, ImageButtonImage.OFFSET_NONE);
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

	/**
	 * Gets the scale.
	 *
	 * @return the scale
	 */
	public float getScale() {
		return this.scale;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * Gets the sprite sheet col.
	 *
	 * @param offset the offset
	 * @return the sprite sheet col
	 */
	public int getSpriteSheetCol(final byte offset){
		return (this.getId()+offset) % this.spriteSheet.getHorizontalCount();
	}

	/**
	 * Gets the sprite sheet row.
	 *
	 * @param offset the offset
	 * @return the sprite sheet row
	 */
	public int getSpriteSheetRow(final byte offset){
		return (this.getId()+offset) / this.spriteSheet.getHorizontalCount();
	}
}
