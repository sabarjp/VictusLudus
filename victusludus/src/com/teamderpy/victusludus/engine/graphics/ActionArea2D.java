
package com.teamderpy.victusludus.engine.graphics;

import com.badlogic.gdx.Input.Buttons;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.engine.Actionable;
import com.teamderpy.victusludus.gui.eventhandler.HoverListener;
import com.teamderpy.victusludus.gui.eventhandler.MouseListener;
import com.teamderpy.victusludus.gui.eventhandler.event.HoverEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.ScrollEvent;

/**
 * A 2D area that can have events attached to it
 * 
 * @author Josh
 */
public class ActionArea2D implements MouseListener, HoverListener {
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

	public ActionArea2D (final int x, final int y, final int width, final int height) {
		this.x = (x);
		this.y = (y);
		this.width = (width);
		this.height = (height);

		this.isDisabled = false;
		this.isVisible = true;
		this.isHoveredOn = false;
		this.isDepressed = false;

		this.registerListeners();
	}

	@Override
	public boolean onEnter (final HoverEvent hoverEvent) {
		if (!this.isDisabled() && this.isVisible()) {
			if (this.equals(hoverEvent.getSource())) {
				this.isHoveredOn = true;
				this.mouseEnterAct.act();
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean onLeave (final HoverEvent hoverEvent) {
		if (!this.isDisabled() && this.isVisible()) {
			if (this.equals(hoverEvent.getSource())) {
				this.isHoveredOn = false;
				this.mouseLeaveAct.act();
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean onMouseClick (final MouseEvent mouseEvent) {
		if (!this.isDisabled() && this.isVisible()) {
			if (mouseEvent.getButton() == Buttons.LEFT) {
				if (mouseEvent.isButtonPressed()) {
					if (this.isInside(mouseEvent.getX(), mouseEvent.getY())) {
						this.isDepressed = true;
						return true;
					}
				} else {
					if (this.isInside(mouseEvent.getX(), mouseEvent.getY())) {
						if (this.isDepressed) {
							this.isDepressed = false;
							this.mouseClickAct.act();
							return true;
						}
					} else {
						this.isDepressed = false;
						return false;
					}
				}
			}
		}

		return false;
	}

	@Override
	public void onMouseMove (final MouseEvent mouseEvent) {
		if (!this.isDisabled() && this.isVisible()) {
			if (this.isInside(mouseEvent.getX(), mouseEvent.getY())) {
				if (!this.isHoveredOn) {
					VictusLudusGame.engine.eventHandler.signal(new HoverEvent(this, true));
				}
			} else {
				if (this.isHoveredOn) {
					VictusLudusGame.engine.eventHandler.signal(new HoverEvent(this, false));
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
	public Boolean isInside (final int x, final int y) {
		float xScale = VictusLudusGame.engine.getCameraXScale();
		float yScale = VictusLudusGame.engine.getCameraYScale();

		if (x >= this.x * xScale && x <= this.x * xScale + this.getWidth() * xScale) {
			if (y >= this.y * yScale && y <= this.y * yScale + this.getHeight() * yScale) {
				return true;
			}
		}

		return false;
	}

	public int getX () {
		return this.x;
	}

	public void setX (final int x) {
		this.x = (x);
	}

	public int getY () {
		return this.y;
	}

	public void setY (final int y) {
		this.y = (y);
	}

	public boolean isDisabled () {
		return this.isDisabled;
	}

	public void setDisabled (final boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public boolean isVisible () {
		return this.isVisible;
	}

	public void setVisible (final boolean isVisible) {
		this.isVisible = isVisible;
	}

	/**
	 * Gets the height.
	 * 
	 * @return the height
	 */
	public int getHeight () {
		return this.height;
	}

	/**
	 * Sets the height.
	 * 
	 * @param height the new height
	 */
	public void setHeight (final int height) {
		this.height = (height);
	}

	/**
	 * Gets the width.
	 * 
	 * @return the width
	 */
	public int getWidth () {
		return this.width;
	}

	/**
	 * Sets the width.
	 * 
	 * @param width the new width
	 */
	public void setWidth (final int width) {
		this.width = (width);
	}

	/** Unregister listeners. */
	public void registerListeners () {
		VictusLudusGame.engine.eventHandler.mouseHandler.registerPlease(this);
		VictusLudusGame.engine.eventHandler.hoverHandler.registerPlease(this);
	}

	/** Register listeners. */
	public void unregisterListeners () {
		VictusLudusGame.engine.eventHandler.mouseHandler.unregisterPlease(this);
		VictusLudusGame.engine.eventHandler.hoverHandler.unregisterPlease(this);
	}

	@Override
	public void finalize () {
		this.unregisterListeners();
	}

	public Actionable getMouseEnterAct () {
		return this.mouseEnterAct;
	}

	public void setMouseEnterAct (final Actionable mouseEnterAct) {
		this.mouseEnterAct = mouseEnterAct;
	}

	public Actionable getMouseLeaveAct () {
		return this.mouseLeaveAct;
	}

	public void setMouseLeaveAct (final Actionable mouseLeaveAct) {
		this.mouseLeaveAct = mouseLeaveAct;
	}

	public Actionable getMouseClickAct () {
		return this.mouseClickAct;
	}

	public void setMouseClickAct (final Actionable mouseClickAct) {
		this.mouseClickAct = mouseClickAct;
	}

	@Override
	public boolean onScroll (final ScrollEvent scrollEvent) {
		return false;
	}

	@Override
	public void onMouseDrag (final MouseEvent evt) {

	}
}
