package com.teamderpy.victusludus.gui.eventhandler.event;

import java.util.EventObject;


/**
 * Called when a key goes up
 */
public class KeyUpEvent extends EventObject{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1735684502434779938L;
	
	/** The key. */
	private int key;

	/**
	 * Instantiates a new keyboard event.
	 *
	 * @param source the source
	 * @param keyboardKey the keyboard key
	 */
	public KeyUpEvent(Object source, int keyboardKey) {
		super(source);
		
		this.key = keyboardKey;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public int getKey() {
		return this.key;
	}
}