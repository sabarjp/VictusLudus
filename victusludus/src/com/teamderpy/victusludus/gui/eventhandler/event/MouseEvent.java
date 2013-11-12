
package com.teamderpy.victusludus.gui.eventhandler.event;

import java.util.EventObject;

/**
 * The Class MouseEvent.
 */
public class MouseEvent extends EventObject {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4751970392667326635L;

	public static final byte EVENT_MOVE = 0x0;
	public static final byte EVENT_CLICK = 0x1;
	public static final byte EVENT_DRAGGED = 0x2;

	/** The specific event. */
	private byte specificEvent;

	/** The button. */
	private int button;

	/** The x. */
	private int x;

	/** The y. */
	private int y;

	/** The is button pressed. */
	private boolean isButtonPressed;

	/**
	 * Instantiates a new mouse event.
	 * 
	 * @param source the source
	 * @param specificEvent the specific event
	 * @param x the x
	 * @param y the y
	 * @param button the button
	 * @param isButtonPressed the is button pressed
	 */
	public MouseEvent (final Object source, final byte specificEvent, final int x, final int y, final int button,
		final boolean isButtonPressed) {
		super(source);

		this.specificEvent = specificEvent;
		this.x = x;
		this.y = y;
		this.button = button;
		this.isButtonPressed = isButtonPressed;
	}

	/**
	 * Gets the specific event.
	 * 
	 * @return the specific event
	 */
	public byte getSpecificEvent () {
		return this.specificEvent;
	}

	/**
	 * Gets the x.
	 * 
	 * @return the x
	 */
	public int getX () {
		return this.x;
	}

	/**
	 * Gets the y.
	 * 
	 * @return the y
	 */
	public int getY () {
		return this.y;
	}

	/**
	 * Gets the button.
	 * 
	 * @return the button
	 */
	public int getButton () {
		return this.button;
	}

	/**
	 * Checks if is button pressed.
	 * 
	 * @return true, if is button pressed
	 */
	public boolean isButtonPressed () {
		return this.isButtonPressed;
	}

	@Override
	public String toString () {
		return "Mouse event" + " evt " + this.specificEvent + "  " + this.x + "," + this.y + "  btn " + this.button + " "
			+ this.isButtonPressed;
	}
}
