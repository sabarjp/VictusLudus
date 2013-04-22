package com.teamderpy.victusludus.gui.eventhandler.event;

import java.util.EventObject;

// TODO: Auto-generated Javadoc
/**
 * The Class ResizeEvent.
 */
public class RenderEvent extends EventObject{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3965247445490684232L;

	/** The type of render event. */
	private EnumRenderEventType eventType;

	/**
	 * Instantiates a new resize event.
	 *
	 * @param source the source
	 * @param width the width
	 * @param height the height
	 */
	public RenderEvent(final Object source, final EnumRenderEventType eventType) {
		super(source);

		this.setEventType(eventType);
	}

	public EnumRenderEventType getEventType() {
		return this.eventType;
	}

	/**
	 * Sets the event type.
	 *
	 * @param eventType the new event type
	 */
	public void setEventType(final EnumRenderEventType eventType) {
		this.eventType = eventType;
	}

}
