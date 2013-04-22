package com.teamderpy.victusludus.gui.eventhandler;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EventListener;
import java.util.EventObject;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.gui.eventhandler.event.RenderEvent;


// TODO: Auto-generated Javadoc
/**
 * The Class ResizeHandler.
 */
public class RenderHandler extends AbstractHandler{

	/** The name. */
	private static String name ="RENDER_HANDLER";

	/** The listener queue. */
	private Deque<RenderListener> listenerQueue;

	/** The register queue. */
	private Deque<RenderListener> registerQueue;

	/** The unregister queue. */
	private Deque<RenderListener> unregisterQueue;

	/**
	 * Instantiates a new resize handler.
	 */
	public RenderHandler(){
		this.eventCounter = 0L;
		this.listenerCount = 0L;

		this.listenerQueue = new ArrayDeque<RenderListener>();
		this.registerQueue = new ArrayDeque<RenderListener>();
		this.unregisterQueue = new ArrayDeque<RenderListener>();
	}

	/* signal all listeners with an event */
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#signalAllNow(java.util.EventObject)
	 */
	@Override
	public void signalAllNow(final EventObject e){
		RenderEvent evt = (RenderEvent) e;

		for(RenderListener l: this.listenerQueue){
			l.onRenderChangeEvent(evt);

			if(VictusLudus.e.IS_DEBUGGING){
				VictusLudus.LOGGER.info("SIGNAL     " + RenderHandler.name + ": " + evt + " -> " + l);
			}
		}
	}

	/* registers listeners at the next opportunity */
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#registerPlease(java.util.EventListener)
	 */
	@Override
	public void registerPlease(final EventListener l){
		this.registerQueue.add((RenderListener) l);
	}

	/* unregisters listeners at the next opportunity */
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#unregisterPlease(java.util.EventListener)
	 */
	@Override
	public void unregisterPlease(final EventListener l){
		this.unregisterQueue.add((RenderListener) l);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#registerAllNow()
	 */
	@Override
	public void registerAllNow() {
		/* register new objects */
		while(!this.registerQueue.isEmpty()){
			RenderListener l = this.registerQueue.removeFirst();

			if(!this.listenerQueue.contains(l)){
				if(this.listenerQueue.add(l)) {
					this.listenerCount++;
				}

				if(VictusLudus.e.IS_DEBUGGING) {
					VictusLudus.LOGGER.info("REGISTER   " + RenderHandler.name + ": " + l);
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
			RenderListener l = this.unregisterQueue.removeFirst();

			if(this.listenerQueue.contains(l)){
				if(this.listenerQueue.remove(l)) {
					this.listenerCount--;
				}

				if(VictusLudus.e.IS_DEBUGGING) {
					VictusLudus.LOGGER.info("UNREGISTER " + RenderHandler.name + ": " + l);
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
