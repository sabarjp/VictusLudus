package com.teamderpy.victusludus.engine;

import org.lwjgl.Sys;

// TODO: Auto-generated Javadoc
/**
 * The Class Time.
 */
public final class Time {
	
	/**
	 * Instantiates a new time.
	 */
	private Time(){};
	
	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public static long getTime(){
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
}
