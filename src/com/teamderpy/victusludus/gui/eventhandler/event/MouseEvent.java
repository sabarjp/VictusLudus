package com.teamderpy.victusludus.gui.eventhandler.event;

import java.util.EventObject;

// TODO: Auto-generated Javadoc
/**
 * The Class MouseEvent.
 */
public class MouseEvent extends EventObject{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4751970392667326635L;
	
	/** The Constant EVENT_MOVE. */
	public static final byte EVENT_MOVE  = 0x0;
	
	/** The Constant EVENT_CLICK. */
	public static final byte EVENT_CLICK = 0x1;
	
	/** The Constant BUTTON_NONE. */
	public static final byte BUTTON_NONE = 0x0;
	
	/** The Constant BUTTON_1. */
	public static final byte BUTTON_1 = 0x1;
	
	/** The Constant BUTTON_2. */
	public static final byte BUTTON_2 = 0x2;
	
	/** The specific event. */
	private byte specificEvent;
	
	/** The button. */
	private byte button;
	
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
	public MouseEvent(Object source, byte specificEvent, int x, int y, byte button, boolean isButtonPressed) {
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
	public byte getSpecificEvent() {
		return specificEvent;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Gets the button.
	 *
	 * @return the button
	 */
	public byte getButton() {
		return button;
	}

	/**
	 * Checks if is button pressed.
	 *
	 * @return true, if is button pressed
	 */
	public boolean isButtonPressed() {
		return isButtonPressed;
	}
}
