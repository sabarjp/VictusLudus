package com.teamderpy.victusludus.engine;

import com.badlogic.gdx.Input.Keys;
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
public class InputPoller implements InputProcessor{
	/** The last mouse x. */
	private int lastMouseX = -1;
	
	/** The last mouse y. */
	private int lastMouseY = -1;

	/**
	 * Force mouse move.
	 */
	public void forceMouseMove() {
		int x = this.lastMouseX;
		int y = this.lastMouseY;
		
		this.lastMouseX = -1;
		this.lastMouseY = -1;

		this.mouseMoved(x, y);
	}

	@Override
	public boolean keyDown (int keycode) {
		VictusLudusGame.engine.eventHandler.signal(new KeyDownEvent(VictusLudusGame.engine.eventHandler, keycode));
		return true;
	}

	@Override
	public boolean keyUp (int keycode) {
		VictusLudusGame.engine.eventHandler.signal(new KeyUpEvent(VictusLudusGame.engine.eventHandler, keycode));
		return true;
	}

	@Override
	public boolean keyTyped (char character) {
		VictusLudusGame.engine.eventHandler.signal(new KeyTypedEvent(VictusLudusGame.engine.eventHandler, character));
		return true;
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		VictusLudusGame.engine.eventHandler.signal(new MouseEvent(VictusLudusGame.engine.eventHandler, MouseEvent.EVENT_CLICK, screenX, screenY, button, true));
		return true;
	}

	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		VictusLudusGame.engine.eventHandler.signal(new MouseEvent(VictusLudusGame.engine.eventHandler, MouseEvent.EVENT_CLICK, screenX, screenY, button, false));
		return true;
	}

	@Override
	public boolean touchDragged (int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean mouseMoved (int screenX, int screenY) {
		if (screenX != this.lastMouseX || screenY != this.lastMouseY) {
			VictusLudusGame.engine.eventHandler.signal(new MouseEvent(VictusLudusGame.engine.eventHandler, MouseEvent.EVENT_MOVE, screenX, screenY, MouseEvent.BUTTON_NONE, false));
			this.lastMouseX = screenX;
			this.lastMouseY = screenY;
		}
		return true;
	}

	@Override
	public boolean scrolled (int amount) {
		VictusLudusGame.engine.eventHandler.signal(new ScrollEvent(VictusLudusGame.engine.eventHandler, amount));
		return true;
	}
}
