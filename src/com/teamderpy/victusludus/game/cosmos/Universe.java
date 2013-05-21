package com.teamderpy.victusludus.game.cosmos;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Has lots of galaxies
 * 
 * @author Josh
 */
public class Universe {
	/** list of galaxies in the universe */
	private ArrayList<Galaxy> galaxies;

	/** the universal date */
	private StarDate cosmicDate;

	/** the age of the universe in years */
	private BigDecimal age;

	/** the diameter of the universe in meters */
	private BigDecimal diameter;

	public Universe(){
		this.cosmicDate = new StarDate();
		this.age = BigDecimal.ZERO;

		this.galaxies = new ArrayList<Galaxy>();
		this.diameter = Cosmology.LIGHT_YEAR.multiply(new BigDecimal("56000000000"));
	}

	/**
	 * A tick of time
	 * 
	 * @param delta the amount of stellar time that has passed since the last tick, in years
	 */
	public void tick(final BigDecimal delta){
		this.age = this.age.add(delta);
		this.cosmicDate.addYears(delta.toBigInteger());

		//tick all children
		for(Galaxy g:this.galaxies){
			g.tick(delta);
		}
	}

	public ArrayList<Galaxy> getGalaxies() {
		return this.galaxies;
	}

	public StarDate getCosmicDate() {
		return this.cosmicDate;
	}

	public BigDecimal getAge() {
		return this.age;
	}
}
