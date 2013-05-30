package com.teamderpy.victusludus.gui.eventhandler.event;

import java.util.EventObject;

// TODO: Auto-generated Javadoc
/**
 * The Class SelectEvent.
 */
public class SelectEvent extends EventObject{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8056286231121047180L;
	
	/** The is selecting. */
	private boolean isSelecting;

	/**
	 * Instantiates a new select event.
	 *
	 * @param source the source
	 * @param isSelecting the is selecting
	 */
	public SelectEvent(Object source, boolean isSelecting) {
		super(source);
		
		this.isSelecting = isSelecting;
	}

	/**
	 * Checks if is selecting.
	 *
	 * @return true, if is selecting
	 */
	public boolean isSelecting() {
		return isSelecting;
	}
}
