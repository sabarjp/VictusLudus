
package com.teamderpy.victusludus.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

/**
 * A view is composed of a playing field and a GUI that renders over it. The
 * engine can render one view at a time, typically the game field with the HUD
 * on it.
 * 
 * @author Josh
 */
public interface IView {
	/**
	 * Initializes the view using some settings
	 * 
	 * @param settings the settings to pass to the view
	 * @throws GameException an exception if something goes wrong
	 */
	public void init (ISettings settings) throws GameException;

	/**
	 * Renders the view and the GUI over it
	 * @param spriteBatch
	 * @param modelBatch
	 * @param deltaTime
	 */
	public void render (SpriteBatch spriteBatch, ModelBatch modelBatch, float deltaTime);

	/**
	 * Does a tick
	 * @param deltaTime
	 */
	public void tick (float deltaTime);

	/** Registers all event listeners */
	public void registerListeners ();

	/** Unregisters all event listeners, including for the children */
	public void unregisterListeners ();

	/**
	 * Checks if is running.
	 * 
	 * @return true, if is running
	 */
	public boolean isRunning ();

	/**
	 * Sets whether or not the view is running
	 * 
	 * @param isRunning whether or not the view is running
	 */
	public void setRunning (boolean isRunning);

	/**
	 * Checks if is quit signal.
	 * 
	 * @return true, if is quit signal
	 */
	public boolean isQuitSignal ();

	/**
	 * Sets whether or not the view should quit as soon as possible
	 * 
	 * @param isQutting whether or not the view should quit as soon as possible
	 */
	public void setQuitSignal (boolean isQuitting);

	/**
	 * Dispose of any remaining resources before quitting
	 */
	public void dispose ();
}
