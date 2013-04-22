package com.teamderpy.victusludus.gui.eventhandler;

import java.util.EventListener;
import java.util.EventObject;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractHandler.
 */
public abstract class AbstractHandler {

	/** The event counter. */
	long eventCounter;

	/** The listener count. */
	long listenerCount;

	/* adds an event to the signal queue */
	/**
	 * Signal all now.
	 *
	 * @param e the e
	 */
	public abstract void signalAllNow(EventObject e);

	/* registers listeners at the next opportunity */
	/**
	 * Register please.
	 *
	 * @param l the l
	 */
	public abstract void registerPlease(EventListener l);

	/* unregisters listeners at the next opportunity */
	/**
	 * Unregister please.
	 *
	 * @param l the l
	 */
	public abstract void unregisterPlease(EventListener l);

	/* register all listeners in the queue */
	/**
	 * Register all now.
	 */
	public abstract void registerAllNow() ;

	/* unregister all listeners in the queue */
	/**
	 * Unregister all now.
	 */
	public abstract void unregisterAllNow() ;

	/**
	 * Gets the event counter.
	 *
	 * @return the event counter
	 */
	public abstract long getEventCounter() ;

	/**
	 * Gets the listener count.
	 *
	 * @return the listener count
	 */
	public abstract long getListenerCount();

	/**
	 * Gets a list of the listeners
	 *
	 * @return A string with all the listeners
	 */
	public abstract String getListenerList();
}
