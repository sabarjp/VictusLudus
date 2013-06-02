package com.teamderpy.victusludus.gui.element;

import org.newdawn.slick.openal.Audio;

import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.VictusLudusGame.enginengine.SoundSystem;



/**
 * The Class GUIElement.
 */
public abstract class GUIElement{
	
	/** The sound select. */
	protected static Audio SOUND_SELECT;
	
	/** The sound type. */
	protected static Audio SOUND_TYPE;
	
	/** The height. */
	protected int height = -1;
	
	/** The width. */
	protected int width = -1;
	
	/** The x. */
	protected int x = -1;
	
	/** The y. */
	protected int y = -1;
	
	/** The is disabled. */
	protected boolean isDisabled = false;
	
	/** The is visible. */
	protected boolean isVisible = true;

	/**
	 * Instantiates a new gUI element.
	 *
	 * @param x the x
	 * @param y the y
	 * @param width the width
	 * @param height the height
	 */
	protected GUIElement(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		SOUND_SELECT = VictusLudusGame.engine.soundSystem.loadWAV(SoundSystem.SOUND_SELECT_1);
		SOUND_TYPE   = VictusLudusGame.engine.soundSystem.loadWAV(SoundSystem.SOUND_TYPE_1);
		
		this.registerListeners();
	}

	/**
	 * Render.
	 */
	public abstract void render();
	
	/**
	 * Tick.
	 */
	public abstract void tick();
	
	/**
	 * Unregister listeners.
	 */
	public abstract void unregisterListeners();
	
	/**
	 * Register listeners.
	 */
	public abstract void registerListeners();

	/**
	 * Checks if is inside.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the boolean
	 */
	public Boolean isInside(int x, int y){
		if(x >= this.x && x <= this.x + getWidth()){
			if(y >= this.y && y <= this.y + getHeight()){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height.
	 *
	 * @param height the new height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width.
	 *
	 * @param width the new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x.
	 *
	 * @param x the new x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y.
	 *
	 * @param y the new y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Checks if is disabled.
	 *
	 * @return true, if is disabled
	 */
	public boolean isDisabled() {
		return isDisabled;
	}

	/**
	 * Sets the disabled.
	 *
	 * @param isDisabled the new disabled
	 */
	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}
	
	/**
	 * Checks if is visible.
	 *
	 * @return true, if is visible
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * Sets the visible.
	 *
	 * @param isVisible the new visible
	 */
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}
