package com.teamderpy.victusludus.gui.eventhandler.event;

import java.util.EventObject;


/**
 * The Class TooltipEvent.
 */
public class TooltipEvent extends EventObject{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6849564719529457968L;

	/** The tooltip. */
	private final String tooltip;

	/**
	 * Instantiates a new tooltip event.
	 *
	 * @param source the source
	 * @param tooltip the tooltip
	 */
	public TooltipEvent(final Object source, final String tooltip) {
		super(source);

		this.tooltip = tooltip;
	}

	/**
	 * Gets the tooltip.
	 *
	 * @return the tooltip
	 */
	public String getTooltip(){
		return this.tooltip;
	}
}
