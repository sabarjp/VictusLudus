package com.teamderpy.victusludus.gui.eventhandler;

import java.util.EventListener;

import com.teamderpy.victusludus.gui.eventhandler.event.FocusEvent;


// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving focus events.
 * The class that is interested in processing a focus
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addFocusListener<code> method. When
 * the focus event occurs, that object's appropriate
 * method is invoked.
 *
 * @see FocusEvent
 */
public interface FocusListener extends EventListener{
	
	/**
	 * On gain focus.
	 *
	 * @param focusEvent the focus event
	 */
	public void onGainFocus(FocusEvent focusEvent);
	
	/**
	 * On lose focus.
	 *
	 * @param focusEvent the focus event
	 */
	public void onLoseFocus(FocusEvent focusEvent);
}
