package com.teamderpy.victusludus.engine;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.EventObject;

import com.badlogic.gdx.Gdx;
import com.teamderpy.victusludus.gui.eventhandler.AbstractHandler;
import com.teamderpy.victusludus.gui.eventhandler.ButtonPressHandler;
import com.teamderpy.victusludus.gui.eventhandler.FocusHandler;
import com.teamderpy.victusludus.gui.eventhandler.HoverHandler;
import com.teamderpy.victusludus.gui.eventhandler.KeyboardHandler;
import com.teamderpy.victusludus.gui.eventhandler.MouseHandler;
import com.teamderpy.victusludus.gui.eventhandler.RenderHandler;
import com.teamderpy.victusludus.gui.eventhandler.ResizeHandler;
import com.teamderpy.victusludus.gui.eventhandler.SelectHandler;
import com.teamderpy.victusludus.gui.eventhandler.TooltipHandler;
import com.teamderpy.victusludus.gui.eventhandler.event.ButtonPressEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.FocusEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.HoverEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyDownEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyTypedEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyUpEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.RenderEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.ScrollEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.SelectEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.TooltipEvent;


/**
 * The Class MasterEventHandler.
 */
public class MasterEventHandler {

	/** The event counter. */
	private long eventCounter = 0L;

	/** The button press handler. */
	public ButtonPressHandler buttonPressHandler;

	/** The focus handler. */
	public FocusHandler focusHandler;

	/** The hover handler. */
	public HoverHandler hoverHandler;

	/** The keyboard handler. */
	public KeyboardHandler keyboardHandler;

	/** The mouse handler. */
	public MouseHandler mouseHandler;

	/** The resize handler. */
	public ResizeHandler resizeHandler;

	/** The render handler. */
	public RenderHandler renderHandler;

	/** The select handler. */
	public SelectHandler selectHandler;

	/** The tooltip handler. */
	public TooltipHandler tooltipHandler;

	/** The handler list. */
	private ArrayList<AbstractHandler> handlerList;

	/** The event queue. */
	private Deque<EventObject> eventQueue = new ArrayDeque<EventObject>();

	/**
	 * Instantiates a new master event handler.
	 */
	public MasterEventHandler(){
		this.handlerList = new ArrayList<AbstractHandler>();

		this.buttonPressHandler = new ButtonPressHandler();
		this.handlerList.add(this.buttonPressHandler);

		this.focusHandler = new FocusHandler();
		this.handlerList.add(this.focusHandler);

		this.hoverHandler = new HoverHandler();
		this.handlerList.add(this.hoverHandler);

		this.keyboardHandler = new KeyboardHandler();
		this.handlerList.add(this.keyboardHandler);

		this.mouseHandler = new MouseHandler();
		this.handlerList.add(this.mouseHandler);

		this.resizeHandler = new ResizeHandler();
		this.handlerList.add(this.resizeHandler);

		this.renderHandler = new RenderHandler();
		this.handlerList.add(this.renderHandler);

		this.selectHandler = new SelectHandler();
		this.handlerList.add(this.selectHandler);

		this.tooltipHandler = new TooltipHandler();
		this.handlerList.add(this.tooltipHandler);
	}

	/* adds an event to the signal queue */
	/**
	 * Signal.
	 *
	 * @param e the e
	 */
	public void signal(final EventObject e){
		this.eventQueue.add(e);
		this.eventCounter++;
	}

	/**
	 * Tick.
	 */
	public void tick(){
		/* unregister listeners */
		for(AbstractHandler h: this.handlerList) {
			h.unregisterAllNow();
		}

		/* register listeners */
		for(AbstractHandler h: this.handlerList) {
			h.registerAllNow();
		}

		/* send signals to listeners */
		int dequeueUntil = this.eventQueue.size();
		int dequeueCount = 0;
		//Triage.PrintError("Sending signals for " + eventQueue.size() + " events");
		while(dequeueCount < dequeueUntil){
			if(this.eventQueue.isEmpty()){
				Gdx.app.log("warning", "ERROR: Event queue modified while traversing!");
				return;
			}

			//Triage.PrintError("   Signal " + (dequeueCount+1) + " of " + dequeueUntil);

			EventObject e = this.eventQueue.removeFirst();

			if(e.getClass().equals(ButtonPressEvent.class)){
				this.buttonPressHandler.signalAllNow(e);
			} else if(e.getClass().equals(FocusEvent.class)){
				this.focusHandler.signalAllNow(e);
			} else if(e.getClass().equals(HoverEvent.class)){
				this.hoverHandler.signalAllNow(e);
			} else if(e.getClass().equals(KeyDownEvent.class)){
				this.keyboardHandler.signalAllNow(e);
			} else if(e.getClass().equals(KeyUpEvent.class)){
				this.keyboardHandler.signalAllNow(e);
			} else if(e.getClass().equals(KeyTypedEvent.class)){
				this.keyboardHandler.signalAllNow(e);
			} else if(e.getClass().equals(MouseEvent.class)){
				this.mouseHandler.signalAllNow(e);
			} else if(e.getClass().equals(ScrollEvent.class)){
				this.mouseHandler.signalAllNow(e);
			} else if(e.getClass().equals(ResizeEvent.class)){
				this.resizeHandler.signalAllNow(e);
			} else if(e.getClass().equals(RenderEvent.class)){
				this.renderHandler.signalAllNow(e);
			} else if(e.getClass().equals(SelectEvent.class)){
				this.selectHandler.signalAllNow(e);
			} else if(e.getClass().equals(TooltipEvent.class)){
				this.tooltipHandler.signalAllNow(e);
			}

			dequeueCount++;
		}
	}

	/**
	 * Gets the event counter.
	 *
	 * @return the event counter
	 */
	public long getEventCounter() {
		return this.eventCounter;
	}

	/**
	 * Gets the listener counter.
	 *
	 * @return the listener counter
	 */
	public long getListenerCounter(){
		long summation = 0L;

		for(AbstractHandler h: this.handlerList) {
			summation += h.getListenerCount();
		}

		return summation;
	}

	/**
	 * Returns a list of the listeners
	 *
	 * @return String containing all the listeners
	 */
	public String getListenerList(){
		StringBuilder buf = new StringBuilder();

		for(AbstractHandler h: this.handlerList) {
			buf.append(h.getListenerList());
		}

		return buf.toString();
	}
}
