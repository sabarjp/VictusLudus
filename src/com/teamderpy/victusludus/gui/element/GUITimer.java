package com.teamderpy.victusludus.gui.element;

import com.teamderpy.victusludus.engine.Actionable;
import com.teamderpy.victusludus.engine.Time;


// TODO: Auto-generated Javadoc
/**
 * The Class GUITimer.
 */
public class GUITimer extends GUIElement {
	
	/** The timer. */
	private long timer = 0;
	
	/** The timer action. */
	private Actionable timerAction;
	
	/**
	 * Instantiates a new gUI timer.
	 */
	public GUITimer (){
		super(0, 0, 0, 0);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#render()
	 */
	@Override
	public void render() {
		//this element is never visible
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#tick()
	 */
	@Override
	public void tick() {
		if(this.timer > 0){
			if(Time.getTime() >= this.timer){
				this.timer = 0;
				
				if(this.timerAction != null){
					this.timerAction.act();
				}
			}
		}
	}	
	
	/**
	 * Sets the timer time.
	 *
	 * @param milliseconds the new timer time
	 */
	public void setTimerTime(long milliseconds){
		this.timer = Time.getTime() + milliseconds;
	}
	
	/**
	 * Gets the timer time.
	 *
	 * @return the timer time
	 */
	public long getTimerTime(){
		if(this.timer - Time.getTime()> 0)
			return (this.timer - Time.getTime());
		else
			return 0;
	}
	
	/**
	 * Sets the timer action.
	 *
	 * @param act the new timer action
	 */
	public void setTimerAction(Actionable act){
		this.timerAction = act;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#unregisterListeners()
	 */
	@Override
	public void unregisterListeners() {
		//no listeners
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#registerListeners()
	 */
	@Override
	public void registerListeners() {
		//no listeners
	}
}
