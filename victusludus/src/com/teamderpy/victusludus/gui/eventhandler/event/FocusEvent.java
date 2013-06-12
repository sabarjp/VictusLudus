package com.teamderpy.victusludus.gui.eventhandler.event;

import java.util.EventObject;


/**
 * The Class FocusEvent.
 */
public class FocusEvent extends EventObject{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6751236541677212141L;
	
	/** The is gaining focus. */
	private boolean isGainingFocus;

	/**
	 * Instantiates a new focus event.
	 *
	 * @param source the source
	 * @param isGainingFocus the is gaining focus
	 */
	public FocusEvent(Object source, boolean isGainingFocus) {
		super(source);
		
		this.isGainingFocus = isGainingFocus;
	}

	/**
	 * Checks if is gaining focus.
	 *
	 * @return true, if is gaining focus
	 */
	public boolean isGainingFocus() {
		return isGainingFocus;
	}
	
	@Override
	public String toString(){
		return "FocusEvent" + "  Gaining? " + this.isGainingFocus;
	}
}
