
package com.teamderpy.victusludus.engine;

import com.badlogic.gdx.Input.Keys;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.gui.eventhandler.KeyboardListener;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyDownEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyTypedEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyUpEvent;
import com.teamderpy.victusludus.math.heightmap.MidpointGenerator;

/**
 * The listener interface for receiving global events. The class that is interested in processing a global event implements this
 * interface, and the object created with that class is registered with a component using the component's
 * <code>addGlobalListener<code> method. When
 * the global event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see GlobalEvent
 */
public class GlobalListener implements KeyboardListener {

	/**
	 * Instantiates a new global listener.
	 */
	public GlobalListener () {
		this.registerListeners();
	}

	/**
	 * Register listeners.
	 */
	public void registerListeners () {
		VictusLudusGame.engine.eventHandler.keyboardHandler.registerPlease(this);
	}

	/**
	 * Unregister listeners.
	 */
	public void unregisterListeners () {
		VictusLudusGame.engine.eventHandler.keyboardHandler.unregisterPlease(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize () {
		this.unregisterListeners();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teamderpy.victusludus.gui.eventhandler.KeyboardListener#onKeyPress(com.teamderpy.victusludus.gui.eventhandler.event.
	 * KeyboardEvent)
	 */
	@Override
	public boolean onKeyDown (final KeyDownEvent keyboardEvent) {
		if (keyboardEvent.getKey() == Keys.F3) {
			VictusLudusGame.engine.IS_DEBUGGING = !VictusLudusGame.engine.IS_DEBUGGING;
			return true;
		} else if (keyboardEvent.getKey() == Keys.F4) {
			VictusLudusGame.engine.IS_SHADERS_ENABLED = !VictusLudusGame.engine.IS_SHADERS_ENABLED;
			return true;
		} else if (keyboardEvent.getKey() == Keys.F5) {
			MidpointGenerator gen = new MidpointGenerator(0.66F, 203498230498L);

			float[][] test = gen.generateFloat(64, 64, 0, 255, false);

			for (int i = 0; i < test.length; i++) {
				for (int j = 0; j < test[i].length; j++) {
					System.err.print(test[i][j] + " ");
				}
				System.err.println();
			}

			return true;
		}

		return false;
	}

	@Override
	public boolean onKeyUp (final KeyUpEvent keyboardEvent) {
		return false;
	}

	@Override
	public boolean onKeyTyped (final KeyTypedEvent keyboardEvent) {
		return false;
	}
}
