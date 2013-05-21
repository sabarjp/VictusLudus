package com.teamderpy.victusludus.game.cosmos;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Has lots of stars
 * 
 * @author Josh
 */
public class Galaxy {
	/** list of stars in the galaxy */
	private ArrayList<Star> stars;

	/** the universe this galaxy belongs to */
	private Universe parentUniverse;

	/** the birth date of the star */
	private StarDate birthDate;

	/** the age of the galaxy in years */
	private BigDecimal age;

	/** the position of the galaxy in the universe */
	private BigDecimal xPosition;
	private BigDecimal yPosition;
	private BigDecimal radius;

	public Galaxy(final StarDate birthDate, final Universe universe){
		this.parentUniverse = universe;

		this.birthDate = birthDate;
		this.age = BigDecimal.ZERO;

		this.stars = new ArrayList<Star>();
	}

	/**
	 * A tick of time
	 * 
	 * @param delta the amount of stellar time that has passed since the last tick, in years
	 */
	public void tick(final BigDecimal delta){
		this.age = this.age.add(delta);

		//tick all children
		for(Star s:this.stars){
			s.tick(delta);
		}
	}

	public ArrayList<Star> getStars() {
		return this.stars;
	}

	public StarDate getBirthDate() {
		return this.birthDate;
	}

	public BigDecimal getAge() {
		return this.age;
	}
}
