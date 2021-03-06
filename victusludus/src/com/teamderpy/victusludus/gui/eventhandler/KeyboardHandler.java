
package com.teamderpy.victusludus.gui.eventhandler;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EventListener;
import java.util.EventObject;

import com.badlogic.gdx.Gdx;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyDownEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyTypedEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyUpEvent;

/** The Class KeyboardHandler. */
public class KeyboardHandler extends AbstractHandler {

	/** The name. */
	private static String name = "KEY_HANDLER";

	/** The listener queue. */
	private Deque<KeyboardListener> listenerQueue;

	/** The register queue. */
	private Deque<KeyboardListener> registerQueue;

	/** The unregister queue. */
	private Deque<KeyboardListener> unregisterQueue;

	/** Instantiates a new keyboard handler. */
	public KeyboardHandler () {
		this.eventCounter = 0L;
		this.listenerCount = 0L;

		this.listenerQueue = new ArrayDeque<KeyboardListener>();
		this.registerQueue = new ArrayDeque<KeyboardListener>();
		this.unregisterQueue = new ArrayDeque<KeyboardListener>();
	}

	/* signal all listeners with an event */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#signalAllNow(java.util.EventObject)
	 */
	@Override
	public void signalAllNow (final EventObject e) {
		if (e instanceof KeyDownEvent) {
			KeyDownEvent evt = (KeyDownEvent)e;

			for (KeyboardListener l : this.listenerQueue) {
				if (VictusLudusGame.engine.IS_DEBUGGING) {
					Gdx.app.log("info", "SIGNAL     " + KeyboardHandler.name + ": " + evt + " -> " + l);
				}

				if (l.onKeyDown(evt)) {
					break;
				}
			}
		} else if (e instanceof KeyUpEvent) {
			KeyUpEvent evt = (KeyUpEvent)e;

			for (KeyboardListener l : this.listenerQueue) {
				if (VictusLudusGame.engine.IS_DEBUGGING) {
					Gdx.app.log("info", "SIGNAL     " + KeyboardHandler.name + ": " + evt + " -> " + l);
				}

				if (l.onKeyUp(evt)) {
					break;
				}
			}
		} else if (e instanceof KeyTypedEvent) {
			KeyTypedEvent evt = (KeyTypedEvent)e;

			for (KeyboardListener l : this.listenerQueue) {
				if (VictusLudusGame.engine.IS_DEBUGGING) {
					Gdx.app.log("info", "SIGNAL     " + KeyboardHandler.name + ": " + evt + " -> " + l);
				}

				if (l.onKeyTyped(evt)) {
					break;
				}
			}
		}
	}

	/* registers listeners at the next opportunity */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#registerPlease(java.util.EventListener)
	 */
	@Override
	public void registerPlease (final EventListener l) {
		this.registerQueue.add((KeyboardListener)l);
	}

	/* unregisters listeners at the next opportunity */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#unregisterPlease(java.util.EventListener)
	 */
	@Override
	public void unregisterPlease (final EventListener l) {
		this.unregisterQueue.add((KeyboardListener)l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#registerAllNow()
	 */
	@Override
	public void registerAllNow () {
		/* register new objects */
		while (!this.registerQueue.isEmpty()) {
			KeyboardListener l = this.registerQueue.removeFirst();

			if (!this.listenerQueue.contains(l)) {
				if (this.listenerQueue.add(l)) {
					this.listenerCount++;
				}

				if (VictusLudusGame.engine.IS_DEBUGGING) {
					Gdx.app.log("info", "REGISTER   " + KeyboardHandler.name + ": " + l);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#unregisterAllNow()
	 */
	@Override
	public void unregisterAllNow () {
		/* unregister old objects */
		while (!this.unregisterQueue.isEmpty()) {
			KeyboardListener l = this.unregisterQueue.removeFirst();

			if (this.listenerQueue.contains(l)) {
				if (this.listenerQueue.remove(l)) {
					this.listenerCount--;
				}

				if (VictusLudusGame.engine.IS_DEBUGGING) {
					Gdx.app.log("info", "UNREGISTER " + KeyboardHandler.name + ": " + l);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#getEventCounter()
	 */
	@Override
	public long getEventCounter () {
		return this.eventCounter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#getListenerCount()
	 */
	@Override
	public long getListenerCount () {
		return this.listenerCount;
	}

	@Override
	public String getListenerList () {
		StringBuilder buf = new StringBuilder();

		for (EventListener l : this.listenerQueue) {
			buf.append(this.getClass() + "   " + l.getClass() + ":" + l + "\n");
		}

		return buf.toString();
	}
}
