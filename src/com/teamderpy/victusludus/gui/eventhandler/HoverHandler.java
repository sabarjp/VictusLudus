package com.teamderpy.victusludus.gui.eventhandler;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EventListener;
import java.util.EventObject;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.gui.eventhandler.event.HoverEvent;


// TODO: Auto-generated Javadoc
/**
 * The Class HoverHandler.
 */
public class HoverHandler extends AbstractHandler{

	/** The name. */
	private static String name ="HOVER_HANDLER";

	/** The listener queue. */
	private Deque<HoverListener> listenerQueue;

	/** The register queue. */
	private Deque<HoverListener> registerQueue;

	/** The unregister queue. */
	private Deque<HoverListener> unregisterQueue;

	/**
	 * Instantiates a new hover handler.
	 */
	public HoverHandler(){
		this.eventCounter = 0L;
		this.listenerCount = 0L;

		this.listenerQueue = new ArrayDeque<HoverListener>();
		this.registerQueue = new ArrayDeque<HoverListener>();
		this.unregisterQueue = new ArrayDeque<HoverListener>();
	}

	/* signal all listeners with an event */
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#signalAllNow(java.util.EventObject)
	 */
	@Override
	public void signalAllNow(final EventObject e){
		HoverEvent evt = (HoverEvent) e;

		for(HoverListener l: this.listenerQueue){
			if(evt.isEntering()){
				l.onEnter(evt);
			} else {
				l.onLeave(evt);
			}

			if(VictusLudus.e.IS_DEBUGGING){
				VictusLudus.LOGGER.info("SIGNAL     " + HoverHandler.name + ": " + evt + " -> " + l);
			}
		}
	}

	/* registers listeners at the next opportunity */
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#registerPlease(java.util.EventListener)
	 */
	@Override
	public void registerPlease(final EventListener l){
		this.registerQueue.add((HoverListener) l);
	}

	/* unregisters listeners at the next opportunity */
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#unregisterPlease(java.util.EventListener)
	 */
	@Override
	public void unregisterPlease(final EventListener l){
		this.unregisterQueue.add((HoverListener) l);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#registerAllNow()
	 */
	@Override
	public void registerAllNow() {
		/* register new objects */
		while(!this.registerQueue.isEmpty()){
			HoverListener l = this.registerQueue.removeFirst();

			if(!this.listenerQueue.contains(l)){
				if(this.listenerQueue.add(l)) {
					this.listenerCount++;
				}

				if(VictusLudus.e.IS_DEBUGGING) {
					VictusLudus.LOGGER.info("REGISTER   " + HoverHandler.name + ": " + l);
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
			HoverListener l = this.unregisterQueue.removeFirst();

			if(this.listenerQueue.contains(l)){
				if(this.listenerQueue.remove(l)) {
					this.listenerCount--;
				}

				if(VictusLudus.e.IS_DEBUGGING) {
					VictusLudus.LOGGER.info("UNREGISTER " + HoverHandler.name + ": " + l);
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
