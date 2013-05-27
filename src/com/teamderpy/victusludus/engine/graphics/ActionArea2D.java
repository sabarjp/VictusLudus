package com.teamderpy.victusludus.engine.graphics;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.engine.Actionable;
import com.teamderpy.victusludus.gui.eventhandler.HoverListener;
import com.teamderpy.victusludus.gui.eventhandler.MouseListener;
import com.teamderpy.victusludus.gui.eventhandler.event.HoverEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent;

/**
 * A 2D area that can have events attached to it
 * 
 * @author Josh
 */
public class ActionArea2D implements MouseListener, HoverListener{
	/** the x and y coordinates of the area */
	private int x;
	private int y;

	/** the height and width of the area */
	private int height;
	private int width;

	/** whether or not this sprite is disabled and ignoring events */
	private boolean isDisabled;

	/** whether or not this sprite is visible */
	private boolean isVisible;

	/** whether or not this sprite is being hovered on by the mouse */
	private boolean isHoveredOn;

	/** is the mouse depressed */
	private boolean isDepressed;

	/** the actions to perform */
	private Actionable mouseEnterAct;
	private Actionable mouseLeaveAct;
	private Actionable mouseClickAct;

	public ActionArea2D(final int x, final int y, final int width, final int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.isDisabled = false;
		this.isVisible = true;
		this.isHoveredOn = false;
		this.isDepressed = false;

		this.registerListeners();
	}

	@Override
	public void onEnter(final HoverEvent hoverEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.equals(hoverEvent.getSource())){
				this.isHoveredOn = true;
				this.mouseEnterAct.act();
			}
		}
	}

	@Override
	public void onLeave(final HoverEvent hoverEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.equals(hoverEvent.getSource())){
				this.isHoveredOn = false;
				this.mouseLeaveAct.act();
			}
		}
	}

	@Override
	public void onMouseClick(final MouseEvent mouseEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(mouseEvent.getButton() == MouseEvent.BUTTON_1){
				if(mouseEvent.isButtonPressed()){
					if(this.isInside(mouseEvent.getX(), mouseEvent.getY())){
						this.isDepressed = true;
					}
				}
				else{
					if(this.isInside(mouseEvent.getX(), mouseEvent.getY())){
						if(this.isDepressed){
							this.isDepressed = false;
							this.mouseClickAct.act();
						}
					} else {
						this.isDepressed = false;
					}
				}
			}
		}
	}

	@Override
	public void onMouseMove(final MouseEvent mouseEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.isInside(mouseEvent.getX(), mouseEvent.getY())){
				if(!this.isHoveredOn){
					VictusLudus.e.eventHandler.signal(new HoverEvent(this, true));
				}
			}
			else{
				if(this.isHoveredOn){
					VictusLudus.e.eventHandler.signal(new HoverEvent(this, false));
				}
			}
		}
	}

	/**
	 * Checks if the coordinates are inside.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the boolean
	 */
	public Boolean isInside(final int x, final int y){
		if(x >= this.x && x <= this.x + this.getWidth()){
			if(y >= this.y && y <= this.y + this.getHeight()){
				return true;
			}
		}

		return false;
	}

	public int getX() {
		return this.x;
	}

	public void setX(final int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(final int y) {
		this.y = y;
	}

	public boolean isDisabled() {
		return this.isDisabled;
	}

	public void setDisabled(final boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public boolean isVisible() {
		return this.isVisible;
	}

	public void setVisible(final boolean isVisible) {
		this.isVisible = isVisible;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Sets the height.
	 *
	 * @param height the new height
	 */
	public void setHeight(final int height) {
		this.height = height;
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Sets the width.
	 *
	 * @param width the new width
	 */
	public void setWidth(final int width) {
		this.width = width;
	}

	/**
	 * Unregister listeners.
	 */
	public void registerListeners() {
		VictusLudus.e.eventHandler.mouseHandler.registerPlease(this);
		VictusLudus.e.eventHandler.hoverHandler.registerPlease(this);
	}

	/**
	 * Register listeners.
	 */
	public void unregisterListeners(){
		VictusLudus.e.eventHandler.mouseHandler.unregisterPlease(this);
		VictusLudus.e.eventHandler.hoverHandler.unregisterPlease(this);
	}

	@Override
	public void finalize(){
		this.unregisterListeners();
	}

	public Actionable getMouseEnterAct() {
		return this.mouseEnterAct;
	}

	public void setMouseEnterAct(final Actionable mouseEnterAct) {
		this.mouseEnterAct = mouseEnterAct;
	}

	public Actionable getMouseLeaveAct() {
		return this.mouseLeaveAct;
	}

	public void setMouseLeaveAct(final Actionable mouseLeaveAct) {
		this.mouseLeaveAct = mouseLeaveAct;
	}

	public Actionable getMouseClickAct() {
		return this.mouseClickAct;
	}

	public void setMouseClickAct(final Actionable mouseClickAct) {
		this.mouseClickAct = mouseClickAct;
	}
}
