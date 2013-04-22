package com.teamderpy.victusludus.engine;


import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyboardEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent;

// TODO: Auto-generated Javadoc
/* we pretty much just hand this off to the event handler, ought to combine the classes at some point */
/**
 * The Class InputPoller.
 */
public class InputPoller {
	
	/** The last mouse x. */
	private static int lastMouseX = -1;
	
	/** The last mouse y. */
	private static int lastMouseY = -1;

	/**
	 * Poll input.
	 */
	public static void pollInput() {
		InputPoller.pollKeyboardPress();
		InputPoller.pollMousePress();
		InputPoller.pollMouseMove();
	}

	/**
	 * Poll keyboard press.
	 */
	public static void pollKeyboardPress() {
		/* check keyboard input */
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				VictusLudus.e.eventHandler.signal(new KeyboardEvent(VictusLudus.e.eventHandler, Keyboard.getEventKey(), Keyboard.getEventCharacter()));
			}
		}
	}

	/**
	 * Poll mouse press.
	 */
	public static void pollMousePress() {
		/* check mouse clicks */
		while (Mouse.next()) {
			final int clickY = VictusLudus.e.Y_RESOLUTION() - Mouse.getEventY();

			if(Mouse.getEventButton() != -1){
				if (Mouse.getEventButton() == 0) {
					if(Mouse.getEventButtonState()){
						VictusLudus.e.eventHandler.signal(new MouseEvent(VictusLudus.e.eventHandler, MouseEvent.EVENT_CLICK, Mouse.getEventX(), clickY, MouseEvent.BUTTON_1, true));
					}else{
						VictusLudus.e.eventHandler.signal(new MouseEvent(VictusLudus.e.eventHandler, MouseEvent.EVENT_CLICK, Mouse.getEventX(), clickY, MouseEvent.BUTTON_1, false));
					}
				} else if (Mouse.getEventButton() == 1) {
					if(Mouse.getEventButtonState()){
						VictusLudus.e.eventHandler.signal(new MouseEvent(VictusLudus.e.eventHandler, MouseEvent.EVENT_CLICK, Mouse.getEventX(), clickY, MouseEvent.BUTTON_2, true));
					}else{
						VictusLudus.e.eventHandler.signal(new MouseEvent(VictusLudus.e.eventHandler, MouseEvent.EVENT_CLICK, Mouse.getEventX(), clickY, MouseEvent.BUTTON_2, false));
					}
				}
			}
		}
	}

	/**
	 * Poll mouse move.
	 */
	public static void pollMouseMove() {
		/* check mouse position */
		final int mouseY = VictusLudus.e.Y_RESOLUTION() - Mouse.getEventY();

		if (Mouse.getEventX() != InputPoller.lastMouseX || mouseY != InputPoller.lastMouseY) {
			VictusLudus.e.eventHandler.signal(new MouseEvent(VictusLudus.e.eventHandler, MouseEvent.EVENT_MOVE, Mouse.getEventX(), mouseY, MouseEvent.BUTTON_NONE, false));
			InputPoller.lastMouseX = Mouse.getEventX();
			InputPoller.lastMouseY = mouseY;
		}
	}

	/**
	 * Force mouse move.
	 */
	public static void forceMouseMove() {
		InputPoller.lastMouseX = -1;
		InputPoller.lastMouseY = -1;

		InputPoller.pollMouseMove();
	}
}
