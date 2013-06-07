package com.teamderpy.victusludus.gui.eventhandler;


import java.util.EventListener;

import com.teamderpy.victusludus.gui.eventhandler.event.TooltipEvent;


/**
 * The listener interface for receiving tooltip events.
 * The class that is interested in processing a tooltip
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addTooltipListener<code> method. When
 * the tooltip event occurs, that object's appropriate
 * method is invoked.
 *
 * @see TooltipEvent
 */
public interface TooltipListener extends EventListener{
	
	/**
	 * On change tooltip.
	 *
	 * @param tooltipEvent the tooltip event
	 * @return whether or not the event was handled
	 */
	public boolean onChangeTooltip(TooltipEvent tooltipEvent);
}
