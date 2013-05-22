package com.teamderpy.victusludus.game.cosmos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import com.teamderpy.victusludus.data.VictusLudus;

/**
 * Has lots of stars
 * 
 * @author Josh
 */
public class Galaxy {
	private static int MAX_STAR_COUNT = 100;
	public static BigDecimal MIN_GALAXY_RADIUS = Cosmology.PARSEC.multiply(new BigDecimal("50"), Cosmology.COSMIC_RND);
	public static BigDecimal MAX_GALAXY_RADIUS =	Cosmology.PARSEC.multiply(new BigDecimal("25000"), Cosmology.COSMIC_RND);
	private static BigDecimal STAR_RATIO = new BigDecimal("10E6");

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
		//make stars
		if(this.stars.size() < Galaxy.MAX_STAR_COUNT){
			if(this.stars.size() < this.age.divideToIntegralValue(Galaxy.STAR_RATIO).doubleValue()){
				if(VictusLudus.rand.nextBoolean()){
					StarDate starBirthDate = this.getParentUniverse().getCosmicDate().clone();
					starBirthDate.addYears(this.age.toBigInteger());

					BigInteger years = Cosmology.linearInterpolation(Cosmology.NEGATIVE_ONE, BigDecimal.ONE, BigDecimal.ZERO, delta, new BigDecimal(VictusLudus.rand.nextFloat())).toBigInteger();

					starBirthDate.addYears(years);

					Star star = new Star(starBirthDate, this);

					boolean lookingForEmptySpace = true;
					int placementAttemptNum = 100;

					BigDecimal xPos = null;
					BigDecimal yPos = null;

					while(lookingForEmptySpace && placementAttemptNum > 0){
						placementAttemptNum--;

						//pick a spot with nothing else in the area
						xPos   = Cosmology.linearInterpolation(Cosmology.NEGATIVE_ONE, BigDecimal.ONE, this.xPosition.add(this.radius), this.xPosition.subtract(this.radius), new BigDecimal(VictusLudus.rand.nextFloat()));
						yPos   = Cosmology.linearInterpolation(Cosmology.NEGATIVE_ONE, BigDecimal.ONE, this.yPosition.add(this.radius), this.yPosition.subtract(this.radius), new BigDecimal(VictusLudus.rand.nextFloat()));

						for(Star s:this.stars){
							if (xPos.subtract(s.getxPosition()).pow(2).add(yPos.subtract(s.getyPosition()).pow(2)).compareTo(Star.SOLAR_SYSTEM_RADIUS.pow(2)) < 0){
								continue;
							}
						}

						lookingForEmptySpace = false;
					}

					if(placementAttemptNum > 0){
						star.setxPosition(xPos);
						star.setyPosition(yPos);

						this.stars.add(star);

						System.err.println("adding star to " + this.hashCode());
					}
				}
			}
		}

		//tick all children
		for(Star s:this.stars){
			s.tick(delta);
		}

		this.age = this.age.add(delta);
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

	public BigDecimal getxPosition() {
		return this.xPosition;
	}

	public BigDecimal getyPosition() {
		return this.yPosition;
	}

	public BigDecimal getRadius() {
		return this.radius;
	}

	public Universe getParentUniverse() {
		return this.parentUniverse;
	}

	public void setxPosition(final BigDecimal xPosition) {
		this.xPosition = xPosition;
	}

	public void setyPosition(final BigDecimal yPosition) {
		this.yPosition = yPosition;
	}

	public void setRadius(final BigDecimal radius) {
		this.radius = radius;
	}
}
