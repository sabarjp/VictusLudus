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

	/** the type of galaxy */
	private EnumGalaxyType galaxyType;

	/** degrees rotated */
	private double rotation;
	private double angularVelocity;

	/** the position of the galaxy in the universe */
	private double xPosition;
	private double yPosition;
	private double radius;

	public Galaxy(final StarDate birthDate, final Universe universe){
		this.parentUniverse = universe;

		this.birthDate = birthDate;
		this.age = BigDecimal.ZERO;

		this.stars = new ArrayList<Star>();
		this.galaxyType = this.getRandomGalaxyType();
		this.rotation = this.getRandomGalaxyRotation();
		this.angularVelocity = this.getRandomAngularVelocity();
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

					double xPos = -1;
					double yPos = -1;

					while(lookingForEmptySpace && placementAttemptNum > 0){
						placementAttemptNum--;

						//pick a spot with nothing else in the area
						xPos   = Cosmology.linearInterpolation(Cosmology.NEGATIVE_ONE, BigDecimal.ONE, new BigDecimal(this.xPosition - this.radius), new BigDecimal(this.xPosition + this.radius), new BigDecimal(VictusLudus.rand.nextFloat())).doubleValue();
						yPos   = Cosmology.linearInterpolation(Cosmology.NEGATIVE_ONE, BigDecimal.ONE, new BigDecimal(this.yPosition - this.radius), new BigDecimal(this.yPosition + this.radius), new BigDecimal(VictusLudus.rand.nextFloat())).doubleValue();

						for(Star s:this.stars){
							if (xPos - Math.pow(s.getxPosition(), 2) + yPos - Math.pow(s.getyPosition(), 2) < Math.pow(Star.SOLAR_SYSTEM_RADIUS.doubleValue(), 2)){
								continue;
							}
						}

						lookingForEmptySpace = false;
					}

					if(placementAttemptNum > 0){
						star.setxPosition(xPos);
						star.setyPosition(yPos);

						this.stars.add(star);

						//System.err.println("adding star to " + this.hashCode());
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

	private EnumGalaxyType getRandomGalaxyType(){
		return EnumGalaxyType.values()[VictusLudus.rand.nextInt(EnumGalaxyType.values().length)];
	}

	private double getRandomGalaxyRotation(){
		return Math.random()*360;
	}

	private double getRandomAngularVelocity(){
		return Math.random()*10;
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

	public double getxPosition() {
		return this.xPosition;
	}

	public double getyPosition() {
		return this.yPosition;
	}

	public double getRadius() {
		return this.radius;
	}

	public Universe getParentUniverse() {
		return this.parentUniverse;
	}

	public void setxPosition(final double xPosition) {
		this.xPosition = xPosition;
	}

	public void setyPosition(final double yPosition) {
		this.yPosition = yPosition;
	}

	public void setRadius(final double radius) {
		this.radius = radius;
	}

	public EnumGalaxyType getGalaxyType() {
		return this.galaxyType;
	}

	public double getRotation() {
		return this.rotation;
	}

	public void setRotation(final double rotation) {
		this.rotation = rotation;
	}

	public double getAngularVelocity() {
		return this.angularVelocity;
	}
}
