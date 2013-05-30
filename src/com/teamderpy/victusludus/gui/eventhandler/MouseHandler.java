package com.teamderpy.victusludus.gui.eventhandler;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EventListener;
import java.util.EventObject;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent;


// TODO: Auto-generated Javadoc
/**
 * The Class MouseHandler.
 */
public class MouseHandler extends AbstractHandler{

	/** The name. */
	private static String name = "MOUSE_HANDLER";

	/** The listener queue. */
	private Deque<MouseListener> listenerQueue;

	/** The register queue. */
	private Deque<MouseListener> registerQueue;

	/** The unregister queue. */
	private Deque<MouseListener> unregisterQueue;

	/**
	 * Instantiates a new mouse handler.
	 */
	public MouseHandler(){
		this.eventCounter = 0L;
		this.listenerCount = 0L;

		this.listenerQueue = new ArrayDeque<MouseListener>();
		this.registerQueue = new ArrayDeque<MouseListener>();
		this.unregisterQueue = new ArrayDeque<MouseListener>();
	}

	/* signal all listeners with an event */
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#signalAllNow(java.util.EventObject)
	 */
	@Override
	public void signalAllNow(final EventObject e){
		MouseEvent evt = (MouseEvent) e;

		for(MouseListener l: this.listenerQueue){
			if(evt.getSpecificEvent() == MouseEvent.EVENT_CLICK){
				l.onMouseClick(evt);
			} else if (evt.getSpecificEvent() == MouseEvent.EVENT_MOVE){
				l.onMouseMove(evt);
			}

			if(VictusLudus.e.IS_DEBUGGING){
				VictusLudus.LOGGER.info("SIGNAL     " + MouseHandler.name + ": " + evt + " -> " + l);
			}
		}
	}

	/* registers listeners at the next opportunity */
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#registerPlease(java.util.EventListener)
	 */
	@Override
	public void registerPlease(final EventListener l){
		this.registerQueue.add((MouseListener) l);
	}

	/* unregisters listeners at the next opportunity */
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#unregisterPlease(java.util.EventListener)
	 */
	@Override
	public void unregisterPlease(final EventListener l){
		this.unregisterQueue.add((MouseListener) l);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#registerAllNow()
	 */
	@Override
	public void registerAllNow() {
		/* register new objects */
		while(!this.registerQueue.isEmpty()){
			MouseListener l = this.registerQueue.removeFirst();

			if(!this.listenerQueue.contains(l)){
				if(this.listenerQueue.add(l)) {
					this.listenerCount++;
				}

				if(VictusLudus.e.IS_DEBUGGING) {
					VictusLudus.LOGGER.info("REGISTER   " + MouseHandler.name + ": " + l);
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
			MouseListener l = this.unregisterQueue.removeFirst();

			if(this.listenerQueue.contains(l)){
				if(this.listenerQueue.remove(l)) {
					this.listenerCount--;
				}

				if(VictusLudus.e.IS_DEBUGGING) {
					VictusLudus.LOGGER.info("UNREGISTER " + MouseHandler.name + ": " + l);
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
