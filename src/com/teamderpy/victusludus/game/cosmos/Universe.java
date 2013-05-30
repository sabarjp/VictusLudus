package com.teamderpy.victusludus.game.cosmos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import com.teamderpy.victusludus.data.VictusLudus;

/**
 * Has lots of galaxies
 * 
 * @author Josh
 */
public class Universe{
	private static int MAX_GALAXY_COUNT = 10;
	private static BigDecimal MIN_AGE_FOR_STARS = new BigDecimal("400E6");
	private static BigDecimal MAX_AGE_FOR_STARS = new BigDecimal("10E14");
	private static BigDecimal GALAXY_RATIO = new BigDecimal("400E6");

	/** list of galaxies in the universe */
	private ArrayList<Galaxy> galaxies;

	/** the universal date */
	private StarDate cosmicDate;

	/** the random seed for this universe **/
	private float seed;

	/** the age of the universe in years */
	private BigDecimal age;

	/** the diameter of the universe in meters */
	private double diameter;

	public Universe(){
		this.cosmicDate = new StarDate();
		this.age = BigDecimal.ZERO;
		this.seed = VictusLudus.rand.nextInt()/2;

		this.galaxies = new ArrayList<Galaxy>();
		this.diameter = Cosmology.LIGHT_YEAR.multiply(new BigDecimal("56000000000")).doubleValue();
	}

	/**
	 * A tick of time in years
	 * 
	 * @param delta the amount of stellar time that has passed since the last tick, in years
	 */
	public void tick(final BigDecimal delta){
		//make some galaxies
		//roughly one galaxy every 150 million years should be good...
		if(this.galaxies.size() < Universe.MAX_GALAXY_COUNT && this.age.compareTo(Universe.MIN_AGE_FOR_STARS) > 0 && this.age.compareTo(Universe.MAX_AGE_FOR_STARS) < 0){
			if(this.galaxies.size() < this.age.divideToIntegralValue(Universe.GALAXY_RATIO).doubleValue()){
				if(VictusLudus.rand.nextInt(3) == 0){
					StarDate galaxyBirthDate = this.cosmicDate.clone();
					BigInteger years = Cosmology.linearInterpolation(Cosmology.NEGATIVE_ONE, BigDecimal.ONE, BigDecimal.ZERO, delta, new BigDecimal(VictusLudus.rand.nextFloat())).toBigInteger();

					galaxyBirthDate.addYears(years);

					Galaxy galaxy = new Galaxy(galaxyBirthDate, this);

					boolean lookingForEmptySpace = true;
					int placementAttemptNum = 100;

					double xPos = -1;
					double yPos = -1;
					double radius = -1;

					while(lookingForEmptySpace && placementAttemptNum > 0){
						placementAttemptNum--;

						//pick a spot with nothing else in the area, should be easy
						radius = Cosmology.linearInterpolation(BigDecimal.ZERO, BigDecimal.ONE, Galaxy.MIN_GALAXY_RADIUS, Galaxy.MAX_GALAXY_RADIUS, new BigDecimal(VictusLudus.rand.nextFloat())).doubleValue();
						xPos   = Cosmology.linearInterpolation(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO, new BigDecimal(this.diameter - radius), new BigDecimal(VictusLudus.rand.nextFloat())).doubleValue();
						yPos   = Cosmology.linearInterpolation(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO, new BigDecimal(this.diameter - radius), new BigDecimal(VictusLudus.rand.nextFloat())).doubleValue();

						for(Galaxy g:this.galaxies){
							if (xPos - Math.pow(g.getxPosition(), 2) + yPos - Math.pow(g.getyPosition(), 2) < Math.pow(g.getRadius(), 2)){
								continue;
							}
						}

						lookingForEmptySpace = false;
					}

					if(placementAttemptNum > 0){
						galaxy.setxPosition(xPos);
						galaxy.setyPosition(yPos);
						galaxy.setRadius(radius);

						this.galaxies.add(galaxy);

						//System.err.println("adding galaxy " + galaxy.getGalaxyType());
					}
				}
			}
		}

		//tick all children
		for(Galaxy g:this.galaxies){
			BigInteger yearDiff = g.getBirthDate().getYearsSinceBigBang().subtract(this.cosmicDate.getYearsSinceBigBang());

			if(yearDiff.compareTo(BigInteger.ZERO) > 0){
				g.tick(delta.subtract(new BigDecimal(yearDiff)));
			}else{
				g.tick(delta);
			}
		}

		this.cosmicDate.addYears(delta.toBigInteger());
		this.age = this.age.add(delta);
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

	public double getDiameter() {
		return this.diameter;
	}
}
