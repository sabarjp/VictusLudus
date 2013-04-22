package com.teamderpy.victusludus.gui.eventhandler;

import java.util.EventListener;

import com.teamderpy.victusludus.gui.eventhandler.event.KeyboardEvent;


// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving keyboard events.
 * The class that is interested in processing a keyboard
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addKeyboardListener<code> method. When
 * the keyboard event occurs, that object's appropriate
 * method is invoked.
 *
 * @see KeyboardEvent
 */
public interface KeyboardListener extends EventListener{
	
	/**
	 * On key press.
	 *
	 * @param keyboardEvent the keyboard event
	 */
	public void onKeyPress(KeyboardEvent keyboardEvent);
}
