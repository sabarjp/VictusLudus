package com.teamderpy.victusludus.gui.eventhandler;


import java.util.EventListener;

import com.teamderpy.victusludus.gui.eventhandler.event.ButtonPressEvent;



/**
 * The listener interface for receiving buttonPress events.
 * The class that is interested in processing a buttonPress
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addButtonPressListener<code> method. When
 * the buttonPress event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ButtonPressEvent
 */
public interface ButtonPressListener extends EventListener{
	
	/**
	 * On button press.
	 *
	 * @param buttonPressEvent the button press event
	 */
	public void onButtonPress(ButtonPressEvent buttonPressEvent);
}
