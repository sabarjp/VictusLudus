package com.teamderpy.victusludus.gui.eventhandler;

import java.util.EventListener;

import com.teamderpy.victusludus.gui.eventhandler.event.KeyDownEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyTypedEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyUpEvent;



/**
 * The listener interface for receiving keyboard events.
 * The class that is interested in processing a keyboard
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addKeyboardListener<code> method. When
 * the keyboard event occurs, that object's appropriate
 * method is invoked.
 *
 * @see KeyDownEvent
 */
public interface KeyboardListener extends EventListener{
	
	/**
	 * On key pressed down
	 *
	 * @param keyboardEvent the keyboard event
	 */
	public void onKeyDown(KeyDownEvent keyboardEvent);
	
	/**
	 * On key release
	 *
	 * @param keyboardEvent the keyboard event
	 */
	public void onKeyUp(KeyUpEvent keyboardEvent);
	
	/**
	 * On key typed
	 *
	 * @param keyboardEvent the keyboard event
	 */
	public void onKeyTyped(KeyTypedEvent keyboardEvent);
}
