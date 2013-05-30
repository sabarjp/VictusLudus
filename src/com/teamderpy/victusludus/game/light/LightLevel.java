package com.teamderpy.victusludus.game.light;

import org.newdawn.slick.Color;


// TODO: Auto-generated Javadoc
/**
 * The Class LightLevel which is the amount of light hitting an object.
 */
public class LightLevel{
	/** The strength. */
	private int strength;

	/** The color. */
	private Color color;

	/**
	 * Instantiates a new light level
	 * 
	 * @param strength the strength
	 * @param color the color
	 */
	public LightLevel(final int strength, final Color color){
		this.strength = strength;
		this.color = color;
	}

	/**
	 * Gets the strength.
	 *
	 * @return the strength
	 */
	public int getStrength() {
		return this.strength;
	}

	/**
	 * Sets the strength.
	 *
	 * @param strength the new strength
	 */
	public void setStrength(final int strength) {
		this.strength = strength;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Sets the color.
	 *
	 * @param color the new color
	 */
	public void setColor(final Color color) {
		this.color = color;
	}
}
