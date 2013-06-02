package com.teamderpy.victusludus.gui.eventhandler.event;

import java.util.EventObject;

/**
 * Called when the mouse wheel is scrolled
 */
public class ScrollEvent extends EventObject{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1735684502434779938L;
	
	/** The amount scrolled, , -1 or 1 depending on direction */
	private int amountScrolled;

	/**
	 * Instantiates a new keyboard event.
	 *
	 * @param source the source
	 * @param amountScrolled the amount scrolled, , -1 or 1 depending on direction
	 */
	public ScrollEvent(Object source, int amountScrolled) {
		super(source);
		
		this.amountScrolled = amountScrolled;
	}

	/**
	 * Gets the amount scrolled.
	 *
	 * @return the amount scrolled, -1 or 1 depending on direction
	 */
	public int getScrollAmount() {
		return this.amountScrolled;
	}
}
