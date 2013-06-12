package com.teamderpy.victusludus.engine;

import com.badlogic.gdx.utils.TimeUtils;


/**
 * The Class Time.
 */
public final class Time {
	/**
	 * Gets the time passed in milliseconds
	 *
	 * @return the time
	 */
	public static long getTime(){
		return getTimeMilli();
	}
	
	/**
	 * Gets the time passed in milliseconds
	 *
	 * @return the time
	 */
	public static long getTimeMilli(){
		return TimeUtils.millis();
	}
	
	/**
	 * Gets the time passed in nanoseconds
	 *
	 * @return the time
	 */
	public static long getTimeNano(){
		return TimeUtils.nanoTime();
	}
}
