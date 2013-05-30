package com.teamderpy.victusludus.gui.eventhandler;

import java.util.EventListener;

import com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent;


// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving mouse events.
 * The class that is interested in processing a mouse
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addMouseListener<code> method. When
 * the mouse event occurs, that object's appropriate
 * method is invoked.
 *
 * @see MouseEvent
 */
public interface MouseListener extends EventListener{
	
	/**
	 * On mouse click.
	 *
	 * @param mouseEvent the mouse event
	 */
	public void onMouseClick(MouseEvent mouseEvent);
	
	/**
	 * On mouse move.
	 *
	 * @param mouseEvent the mouse event
	 */
	public void onMouseMove(MouseEvent mouseEvent);
}
