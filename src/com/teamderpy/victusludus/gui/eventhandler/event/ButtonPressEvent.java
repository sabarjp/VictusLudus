package com.teamderpy.victusludus.gui.eventhandler.event;

import java.util.EventObject;

// TODO: Auto-generated Javadoc
/**
 * The Class ButtonPressEvent.
 */
public class ButtonPressEvent extends EventObject{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1150302508647403536L;
	
	/** The value. */
	private String value;

	/**
	 * Instantiates a new button press event.
	 *
	 * @param source the source
	 * @param value the value
	 */
	public ButtonPressEvent(Object source, String value) {
		super(source);
		
		this.value = value;
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue(){
		return this.value;
	}
}
