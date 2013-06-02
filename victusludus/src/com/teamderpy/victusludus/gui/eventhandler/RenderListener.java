package com.teamderpy.victusludus.gui.eventhandler;

import java.util.EventListener;

import com.teamderpy.victusludus.gui.eventhandler.event.RenderEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent;



/**
 * The listener interface for receiving resize events.
 * The class that is interested in processing a resize
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addResizeListener<code> method. When
 * the resize event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ResizeEvent
 */
public interface RenderListener extends EventListener{

	/**
	 * On resize.
	 *
	 * @param resizeEvent the resize event
	 */
	public void onRenderChangeEvent(RenderEvent renderEvent);
}
