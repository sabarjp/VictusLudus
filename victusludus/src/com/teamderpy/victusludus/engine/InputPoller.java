
package com.teamderpy.victusludus.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyDownEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyTypedEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyUpEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.ScrollEvent;

/**
 * Listens for events and re-fires them for our purposes
 */
public class InputPoller implements InputProcessor {
	/** The last mouse x. */
	private int lastMouseX = -1;

	/** The last mouse y. */
	private int lastMouseY = -1;

	/**
	 * Force mouse move.
	 */
	public void forceMouseMove () {
		int x = this.lastMouseX;
		int y = this.lastMouseY;

		this.lastMouseX = -1;
		this.lastMouseY = -1;

		this.mouseMoved(x, y);
	}

	@Override
	public boolean keyDown (final int keycode) {
		VictusLudusGame.engine.eventHandler.signal(new KeyDownEvent(VictusLudusGame.engine.eventHandler, keycode));
		return false;
	}

	@Override
	public boolean keyUp (final int keycode) {
		VictusLudusGame.engine.eventHandler.signal(new KeyUpEvent(VictusLudusGame.engine.eventHandler, keycode));
		return false;
	}

	@Override
	public boolean keyTyped (final char character) {
		VictusLudusGame.engine.eventHandler.signal(new KeyTypedEvent(VictusLudusGame.engine.eventHandler, character));
		return false;
	}

	@Override
	public boolean touchDown (final int screenX, final int screenY, final int pointer, final int button) {
		VictusLudusGame.engine.eventHandler.signal(new MouseEvent(VictusLudusGame.engine.eventHandler, MouseEvent.EVENT_CLICK,
			screenX, screenY, button, true));
		return false;
	}

	@Override
	public boolean touchUp (final int screenX, final int screenY, final int pointer, final int button) {
		VictusLudusGame.engine.eventHandler.signal(new MouseEvent(VictusLudusGame.engine.eventHandler, MouseEvent.EVENT_CLICK,
			screenX, screenY, button, false));
		return false;
	}

	@Override
	public boolean touchDragged (final int screenX, final int screenY, final int pointer) {
		int button = -1;

		// pass the first button that is held
		if (Gdx.app.getInput().isButtonPressed(Buttons.LEFT)) {
			button = Buttons.LEFT;
		} else if (Gdx.app.getInput().isButtonPressed(Buttons.RIGHT)) {
			button = Buttons.RIGHT;
		} else if (Gdx.app.getInput().isButtonPressed(Buttons.MIDDLE)) {
			button = Buttons.MIDDLE;
		}

		if (screenX != this.lastMouseX || screenY != this.lastMouseY) {
			VictusLudusGame.engine.eventHandler.signal(new MouseEvent(VictusLudusGame.engine.eventHandler, MouseEvent.EVENT_DRAGGED,
				screenX, screenY, button, true));
			this.lastMouseX = screenX;
			this.lastMouseY = screenY;
		}
		return false;
	}

	@Override
	public boolean mouseMoved (final int screenX, final int screenY) {
		if (screenX != this.lastMouseX || screenY != this.lastMouseY) {
			VictusLudusGame.engine.eventHandler.signal(new MouseEvent(VictusLudusGame.engine.eventHandler, MouseEvent.EVENT_MOVE,
				screenX, screenY, -1, false));
			this.lastMouseX = screenX;
			this.lastMouseY = screenY;
		}
		return false;
	}

	@Override
	public boolean scrolled (final int amount) {
		VictusLudusGame.engine.eventHandler.signal(new ScrollEvent(VictusLudusGame.engine.eventHandler, amount));
		return false;
	}
}
