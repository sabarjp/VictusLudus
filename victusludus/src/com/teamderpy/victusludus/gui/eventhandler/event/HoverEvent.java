package com.teamderpy.victusludus.gui.eventhandler.event;

import java.util.EventObject;


/**
 * The Class HoverEvent.
 */
public class HoverEvent extends EventObject{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6884682446095946653L;

	/** The is entering. */
	private boolean isEntering;

	/**
	 * Instantiates a new hover event.
	 *
	 * @param source the source
	 * @param isEntering the is entering
	 */
	public HoverEvent(Object source, boolean isEntering) {
		super(source);
		
		this.isEntering = isEntering;
	}

	/**
	 * Checks if is entering.
	 *
	 * @return true, if is entering
	 */
	public boolean isEntering() {
		return isEntering;
	}
}
