package com.teamderpy.victusludus.gui.eventhandler;

import java.util.EventListener;

import com.teamderpy.victusludus.gui.eventhandler.event.HoverEvent;


// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving hover events.
 * The class that is interested in processing a hover
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addHoverListener<code> method. When
 * the hover event occurs, that object's appropriate
 * method is invoked.
 *
 * @see HoverEvent
 */
public interface HoverListener extends EventListener{
	
	/**
	 * On enter.
	 *
	 * @param hoverEvent the hover event
	 */
	public void onEnter(HoverEvent hoverEvent);
	
	/**
	 * On leave.
	 *
	 * @param hoverEvent the hover event
	 */
	public void onLeave(HoverEvent hoverEvent);
}
