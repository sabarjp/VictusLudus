package com.teamderpy.victusludus.data.resources;

import org.newdawn.slick.Color;

public class StarColorTuple {
	private int temperature;
	private Color color;

	public StarColorTuple(final int temperature, final Color color){
		this.temperature = temperature;
		this.color = color;
	}

	public int getTemperature() {
		return this.temperature;
	}

	public Color getColor() {
		return this.color;
	}
}
