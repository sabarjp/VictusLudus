package com.teamderpy.victusludus.data.resources;

import org.newdawn.slick.UnicodeFont;

// TODO: Auto-generated Javadoc
/**
 * The Class FontFile.
 */
public class FontFile {
	
	/** The id. */
	private String id;
	
	/** The name. */
	private String name = "";
	
	/** The path. */
	private String path = "";
	
	/** The small size. */
	private int smallSize = 8;
	
	/** The default size. */
	private int defaultSize = 12;
	
	/** The large size. */
	private int largeSize = 16;
	
	/** The font normal. */
	private UnicodeFont fontNormal;
	
	/** The font small. */
	private UnicodeFont fontSmall;
	
	/** The font large. */
	private UnicodeFont fontLarge;
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Sets the path.
	 *
	 * @param path the new path
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * Gets the small size.
	 *
	 * @return the small size
	 */
	public int getSmallSize() {
		return smallSize;
	}
	
	/**
	 * Sets the small size.
	 *
	 * @param smallSize the new small size
	 */
	public void setSmallSize(int smallSize) {
		this.smallSize = smallSize;
	}
	
	/**
	 * Gets the default size.
	 *
	 * @return the default size
	 */
	public int getDefaultSize() {
		return defaultSize;
	}
	
	/**
	 * Sets the default size.
	 *
	 * @param defaultSize the new default size
	 */
	public void setDefaultSize(int defaultSize) {
		this.defaultSize = defaultSize;
	}
	
	/**
	 * Gets the large size.
	 *
	 * @return the large size
	 */
	public int getLargeSize() {
		return largeSize;
	}
	
	/**
	 * Sets the large size.
	 *
	 * @param largeSize the new large size
	 */
	public void setLargeSize(int largeSize) {
		this.largeSize = largeSize;
	}

	/**
	 * Gets the font normal.
	 *
	 * @return the font normal
	 */
	public UnicodeFont getFontNormal() {
		return fontNormal;
	}

	/**
	 * Sets the font normal.
	 *
	 * @param fontNormal the new font normal
	 */
	public void setFontNormal(UnicodeFont fontNormal) {
		this.fontNormal = fontNormal;
	}

	/**
	 * Gets the font small.
	 *
	 * @return the font small
	 */
	public UnicodeFont getFontSmall() {
		return fontSmall;
	}

	/**
	 * Sets the font small.
	 *
	 * @param fontSmall the new font small
	 */
	public void setFontSmall(UnicodeFont fontSmall) {
		this.fontSmall = fontSmall;
	}

	/**
	 * Gets the font large.
	 *
	 * @return the font large
	 */
	public UnicodeFont getFontLarge() {
		return fontLarge;
	}

	/**
	 * Sets the font large.
	 *
	 * @param fontLarge the new font large
	 */
	public void setFontLarge(UnicodeFont fontLarge) {
		this.fontLarge = fontLarge;
	}
}
