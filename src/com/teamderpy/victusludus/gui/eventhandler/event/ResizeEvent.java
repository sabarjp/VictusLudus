package com.teamderpy.victusludus.gui.eventhandler.event;

import java.util.EventObject;

// TODO: Auto-generated Javadoc
/**
 * The Class ResizeEvent.
 */
public class ResizeEvent extends EventObject{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3965247445490684232L;
	
	/** The width. */
	private int width;
	
	/** The height. */
	private int height;

	/**
	 * Instantiates a new resize event.
	 *
	 * @param source the source
	 * @param width the width
	 * @param height the height
	 */
	public ResizeEvent(Object source, int width, int height) {
		super(source);
		
		this.width = width;
		this.height = height;
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
