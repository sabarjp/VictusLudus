package com.teamderpy.victusludus.gui.eventhandler;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EventListener;
import java.util.EventObject;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.gui.eventhandler.event.ButtonPressEvent;


// TODO: Auto-generated Javadoc
/**
 * The Class ButtonPressHandler.
 */
public class ButtonPressHandler extends AbstractHandler{

	/** The name. */
	private static String name ="BUTTONPRESS_HANDLER";

	/** The listener queue. */
	private Deque<ButtonPressListener> listenerQueue;

	/** The register queue. */
	private Deque<ButtonPressListener> registerQueue;

	/** The unregister queue. */
	private Deque<ButtonPressListener> unregisterQueue;

	/**
	 * Instantiates a new button press handler.
	 */
	public ButtonPressHandler(){
		this.eventCounter = 0L;
		this.listenerCount = 0L;

		this.listenerQueue = new ArrayDeque<ButtonPressListener>();
		this.registerQueue = new ArrayDeque<ButtonPressListener>();
		this.unregisterQueue = new ArrayDeque<ButtonPressListener>();
	}

	/* signal all listeners with an event */
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#signalAllNow(java.util.EventObject)
	 */
	@Override
	public void signalAllNow(final EventObject e){
		ButtonPressEvent evt = (ButtonPressEvent) e;

		for(ButtonPressListener l: this.listenerQueue){
			l.onButtonPress(evt);

			if(VictusLudus.e.IS_DEBUGGING){
				VictusLudus.LOGGER.info("SIGNAL     " + ButtonPressHandler.name + ": " + evt + " -> " + l);
			}
		}
	}

	/* registers listeners at the next opportunity */
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#registerPlease(java.util.EventListener)
	 */
	@Override
	public void registerPlease(final EventListener l){
		this.registerQueue.add((ButtonPressListener) l);
	}

	/* unregisters listeners at the next opportunity */
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#unregisterPlease(java.util.EventListener)
	 */
	@Override
	public void unregisterPlease(final EventListener l){
		this.unregisterQueue.add((ButtonPressListener) l);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#registerAllNow()
	 */
	@Override
	public void registerAllNow() {
		/* register new objects */
		while(!this.registerQueue.isEmpty()){
			ButtonPressListener l = this.registerQueue.removeFirst();

			if(!this.listenerQueue.contains(l)){
				if(this.listenerQueue.add(l)) {
					this.listenerCount++;
				}

				if(VictusLudus.e.IS_DEBUGGING) {
					VictusLudus.LOGGER.info("REGISTER   " + ButtonPressHandler.name + ": " + l);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#unregisterAllNow()
	 */
	@Override
	public void unregisterAllNow() {
		/* unregister old objects */
		while(!this.unregisterQueue.isEmpty()){
			ButtonPressListener l = this.unregisterQueue.removeFirst();

			if(this.listenerQueue.contains(l)){
				if(this.listenerQueue.remove(l)) {
					this.listenerCount--;
				}

				if(VictusLudus.e.IS_DEBUGGING) {
					VictusLudus.LOGGER.info("UNREGISTER " + ButtonPressHandler.name + ": " + l);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#getEventCounter()
	 */
	@Override
	public long getEventCounter() {
		return this.eventCounter;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#getListenerCount()
	 */
	@Override
	public long getListenerCount() {
		return this.listenerCount;
	}

	@Override
	public String getListenerList() {
		StringBuilder buf = new StringBuilder();

		for(EventListener l:this.listenerQueue){
			buf.append(this.getClass() + "   " + l.getClass() + ":" + l + "\n");
		}

		return buf.toString();
	}
}
