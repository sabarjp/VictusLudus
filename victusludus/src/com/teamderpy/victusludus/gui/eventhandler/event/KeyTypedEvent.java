package com.teamderpy.victusludus.gui.eventhandler.event;

import java.util.EventObject;

/**
 * Called when a key is typed
 */
public class KeyTypedEvent extends EventObject{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1735684502434779938L;
	
	/** The character. */
	private char character;

	/**
	 * Instantiates a new keyboard event.
	 *
	 * @param source the source
	 * @param keyboardCharacter the keyboard character
	 */
	public KeyTypedEvent(Object source, char keyboardCharacter) {
		super(source);
		
		this.character = keyboardCharacter;
	}

	/**
	 * Gets the character.
	 *
	 * @return the character
	 */
	public char getCharacter() {
		return this.character;
	}
	
	@Override
	public String toString(){
		return "KeyTypedEvent" + "  Key " + this.character;
	}
}
