package com.teamderpy.victusludus.engine;


import org.lwjgl.input.Keyboard;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.gui.eventhandler.KeyboardListener;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyboardEvent;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving global events.
 * The class that is interested in processing a global
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addGlobalListener<code> method. When
 * the global event occurs, that object's appropriate
 * method is invoked.
 *
 * @see GlobalEvent
 */
public class GlobalListener implements KeyboardListener{

	/**
	 * Instantiates a new global listener.
	 */
	public GlobalListener(){
		this.registerListeners();
	}

	/**
	 * Register listeners.
	 */
	public void registerListeners() {
		VictusLudus.e.eventHandler.keyboardHandler.registerPlease(this);
	}

	/**
	 * Unregister listeners.
	 */
	public void unregisterListeners() {
		VictusLudus.e.eventHandler.keyboardHandler.unregisterPlease(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize(){
		this.unregisterListeners();
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.KeyboardListener#onKeyPress(com.teamderpy.victusludus.gui.eventhandler.event.KeyboardEvent)
	 */
	@Override
	public void onKeyPress(final KeyboardEvent keyboardEvent) {
		if (keyboardEvent.getKey() == Keyboard.KEY_F3) {
			VictusLudus.e.IS_DEBUGGING = !VictusLudus.e.IS_DEBUGGING;
		}
	}
}
