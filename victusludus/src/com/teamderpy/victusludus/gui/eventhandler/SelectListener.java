package com.teamderpy.victusludus.gui.eventhandler;

import java.util.EventListener;

import com.teamderpy.victusludus.gui.eventhandler.event.SelectEvent;



/**
 * The listener interface for receiving select events.
 * The class that is interested in processing a select
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addSelectListener<code> method. When
 * the select event occurs, that object's appropriate
 * method is invoked.
 *
 * @see SelectEvent
 */
public interface SelectListener extends EventListener{
	
	/**
	 * On select.
	 *
	 * @param selectEvent the select event
	 * @return whether or not the event was handled
	 */
	public boolean onSelect(SelectEvent selectEvent);
	
	/**
	 * On unselect.
	 *
	 * @param selectEvent the select event
	 * @return whether or not the event was handled
	 */
	public boolean onUnselect(SelectEvent selectEvent);
}
