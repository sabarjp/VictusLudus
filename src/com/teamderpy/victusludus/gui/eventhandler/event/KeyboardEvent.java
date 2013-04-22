package com.teamderpy.victusludus.gui.eventhandler.event;

import java.util.EventObject;

// TODO: Auto-generated Javadoc
/**
 * The Class KeyboardEvent.
 */
public class KeyboardEvent extends EventObject{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1735684502434779938L;
	
	/** The key. */
	private int key;
	
	/** The character. */
	private char character;

	/**
	 * Instantiates a new keyboard event.
	 *
	 * @param source the source
	 * @param keyboardKey the keyboard key
	 * @param keyboardCharacter the keyboard character
	 */
	public KeyboardEvent(Object source, int keyboardKey, char keyboardCharacter) {
		super(source);
		
		this.key = keyboardKey;
		this.character = keyboardCharacter;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public int getKey() {
		return this.key;
	}

	/**
	 * Gets the character.
	 *
	 * @return the character
	 */
	public char getCharacter() {
		return this.character;
	}
}
