package com.teamderpy.victusludus.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;


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
		return TimeUtils.millis();
	}
}
