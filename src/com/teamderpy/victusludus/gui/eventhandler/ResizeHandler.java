package com.teamderpy.victusludus.gui.eventhandler;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EventListener;
import java.util.EventObject;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent;


// TODO: Auto-generated Javadoc
/**
 * The Class ResizeHandler.
 */
public class ResizeHandler extends AbstractHandler{

	/** The name. */
	private static String name ="RESIZE_HANDLER";

	/** The listener queue. */
	private Deque<ResizeListener> listenerQueue;

	/** The register queue. */
	private Deque<ResizeListener> registerQueue;

	/** The unregister queue. */
	private Deque<ResizeListener> unregisterQueue;

	/**
	 * Instantiates a new resize handler.
	 */
	public ResizeHandler(){
		this.eventCounter = 0L;
		this.listenerCount = 0L;

		this.listenerQueue = new ArrayDeque<ResizeListener>();
		this.registerQueue = new ArrayDeque<ResizeListener>();
		this.unregisterQueue = new ArrayDeque<ResizeListener>();
	}

	/* signal all listeners with an event */
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#signalAllNow(java.util.EventObject)
	 */
	@Override
	public void signalAllNow(final EventObject e){
		ResizeEvent evt = (ResizeEvent) e;

		for(ResizeListener l: this.listenerQueue){
			l.onResize(evt);

			if(VictusLudus.e.IS_DEBUGGING){
				VictusLudus.LOGGER.info("SIGNAL     " + ResizeHandler.name + ": " + evt + " -> " + l);
			}
		}
	}

	/* registers listeners at the next opportunity */
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#registerPlease(java.util.EventListener)
	 */
	@Override
	public void registerPlease(final EventListener l){
		this.registerQueue.add((ResizeListener) l);
	}

	/* unregisters listeners at the next opportunity */
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#unregisterPlease(java.util.EventListener)
	 */
	@Override
	public void unregisterPlease(final EventListener l){
		this.unregisterQueue.add((ResizeListener) l);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#registerAllNow()
	 */
	@Override
	public void registerAllNow() {
		/* register new objects */
		while(!this.registerQueue.isEmpty()){
			ResizeListener l = this.registerQueue.removeFirst();

			if(!this.listenerQueue.contains(l)){
				if(this.listenerQueue.add(l)) {
					this.listenerCount++;
				}

				if(VictusLudus.e.IS_DEBUGGING) {
					VictusLudus.LOGGER.info("REGISTER   " + ResizeHandler.name + ": " + l);
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
			ResizeListener l = this.unregisterQueue.removeFirst();

			if(this.listenerQueue.contains(l)){
				if(this.listenerQueue.remove(l)) {
					this.listenerCount--;
				}

				if(VictusLudus.e.IS_DEBUGGING) {
					VictusLudus.LOGGER.info("UNREGISTER " + ResizeHandler.name + ": " + l);
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
