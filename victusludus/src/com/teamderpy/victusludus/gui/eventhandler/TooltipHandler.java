package com.teamderpy.victusludus.gui.eventhandler;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EventListener;
import java.util.EventObject;

import com.badlogic.gdx.Gdx;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.gui.eventhandler.event.TooltipEvent;



/**
 * The Class TooltipHandler.
 */
public class TooltipHandler extends AbstractHandler{

	/** The name. */
	private static String name ="TOOLTIP_HANDLER";

	/** The listener queue. */
	private Deque<TooltipListener> listenerQueue;

	/** The register queue. */
	private Deque<TooltipListener> registerQueue;

	/** The unregister queue. */
	private Deque<TooltipListener> unregisterQueue;

	/**
	 * Instantiates a new tooltip handler.
	 */
	public TooltipHandler(){
		this.eventCounter = 0L;
		this.listenerCount = 0L;

		this.listenerQueue = new ArrayDeque<TooltipListener>();
		this.registerQueue = new ArrayDeque<TooltipListener>();
		this.unregisterQueue = new ArrayDeque<TooltipListener>();
	}

	/* signal all listeners with an event */
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#signalAllNow(java.util.EventObject)
	 */
	@Override
	public void signalAllNow(final EventObject e){
		TooltipEvent evt = (TooltipEvent) e;

		for(TooltipListener l: this.listenerQueue){
			if(VictusLudusGame.engine.IS_DEBUGGING){
				Gdx.app.log("info", "SIGNAL     " + TooltipHandler.name + ": " + evt + " -> " + l);
			}
			
			if(l.onChangeTooltip(evt)) break;
		}
	}

	/* registers listeners at the next opportunity */
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#registerPlease(java.util.EventListener)
	 */
	@Override
	public void registerPlease(final EventListener l){
		this.registerQueue.add((TooltipListener) l);
	}

	/* unregisters listeners at the next opportunity */
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#unregisterPlease(java.util.EventListener)
	 */
	@Override
	public void unregisterPlease(final EventListener l){
		this.unregisterQueue.add((TooltipListener) l);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.AbstractHandler#registerAllNow()
	 */
	@Override
	public void registerAllNow() {
		/* register new objects */
		while(!this.registerQueue.isEmpty()){
			TooltipListener l = this.registerQueue.removeFirst();

			if(!this.listenerQueue.contains(l)){
				if(this.listenerQueue.add(l)) {
					this.listenerCount++;
				}

				if(VictusLudusGame.engine.IS_DEBUGGING) {
					Gdx.app.log("info", "REGISTER   " + TooltipHandler.name + ": " + l);
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
			TooltipListener l = this.unregisterQueue.removeFirst();

			if(this.listenerQueue.contains(l)){
				if(this.listenerQueue.remove(l)) {
					this.listenerCount--;
				}

				if(VictusLudusGame.engine.IS_DEBUGGING) {
					Gdx.app.log("info", "UNREGISTER " + TooltipHandler.name + ": " + l);
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
