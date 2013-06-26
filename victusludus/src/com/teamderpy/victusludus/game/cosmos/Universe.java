
package com.teamderpy.victusludus.game.cosmos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import com.teamderpy.victusludus.VictusLudusGame;

/**
 * Has lots of galaxies
 * 
 * @author Josh
 */
public class Universe {
	private static int MIN_GALAXY_COUNT = 8;
	private static int MAX_GALAXY_COUNT = 32;
	public static BigDecimal MIN_AGE_FOR_STARS = new BigDecimal("400E6");
	public static BigDecimal MAX_AGE_FOR_STARS = new BigDecimal("10E14");
	public static BigDecimal MAX_AGE_FOR_GALAXIES = new BigDecimal("2E9");
	private static BigDecimal GALAXY_RATIO = new BigDecimal("50E6");

	/** list of galaxies in the universe */
	private ArrayList<Galaxy> galaxies;

	/** the universal date */
	private StarDate cosmicDate;

	/** the random seed for this universe **/
	private long seed;

	/** the age of the universe in years */
	private BigDecimal age;

	/** the diameter of the universe in meters */
	private double diameter;

	/** the max number of galaxies for the universe */
	private int maxGalaxies;

	/** the star mass distribution for the universe */
	private float starMassDistrubution;

	public Universe (final long seed, final float starMassDistrubution) {
		this.seed = seed;
		this.cosmicDate = new StarDate();
		this.age = BigDecimal.ZERO;
		this.starMassDistrubution = starMassDistrubution;

		this.galaxies = new ArrayList<Galaxy>();
		this.diameter = Cosmology.LIGHT_YEAR.multiply(new BigDecimal("56000000000")).doubleValue();
		this.maxGalaxies = this.getRandomGalaxyCount();
	}

	/**
	 * Creates a new universe where a certain amount of time has passed since the big bang
	 * @param timePassed the amount of time that has passed
	 * @param f
	 */
	public void create (final BigDecimal timePassed) {
		// create galaxies to match the time passed and some other parameters
		int galaxiesToCreateCount = (int)timePassed.divideToIntegralValue(Universe.GALAXY_RATIO).doubleValue();
		galaxiesToCreateCount = Math.max(Universe.MIN_GALAXY_COUNT, Math.min(this.maxGalaxies, galaxiesToCreateCount));

		BigDecimal earliestTime = this.cosmicDate.getYearDecimal().max(Universe.MIN_AGE_FOR_STARS);
		BigDecimal latestTime = timePassed.min(Universe.MAX_AGE_FOR_GALAXIES);

		if (earliestTime.compareTo(latestTime) >= 0) {
			return; // no valid time period!
		}

		VictusLudusGame.sharedRand.setSeed(this.seed + 32487234);

		for (int i = 0; i < galaxiesToCreateCount; i++) {
			// create the galaxy
			BigInteger years = Cosmology.linearInterpolation(Cosmology.NEGATIVE_ONE, BigDecimal.ONE, earliestTime, latestTime,
				new BigDecimal(VictusLudusGame.sharedRand.nextFloat())).toBigInteger();

			StarDate galaxyBirthDate = new StarDate();
			galaxyBirthDate.addYears(years);

			Galaxy galaxy = new Galaxy(galaxyBirthDate, this, i);

			galaxy.create(timePassed.subtract(new BigDecimal(years)));

			// try to place the galaxy
			boolean lookingForEmptySpace = true;
			int placementAttemptNum = 100;

			double xPos = -1;
			double yPos = -1;
			double radius = -1;

			VictusLudusGame.sharedRand.setSeed(galaxy.getSeed() - 56464);

			while (lookingForEmptySpace && placementAttemptNum > 0) {
				placementAttemptNum--;

				// pick a spot with nothing else in the area, should be easy
				radius = Cosmology.linearInterpolation(BigDecimal.ZERO, BigDecimal.ONE, Galaxy.MIN_GALAXY_RADIUS,
					Galaxy.MAX_GALAXY_RADIUS, new BigDecimal(VictusLudusGame.sharedRand.nextFloat())).doubleValue();
				xPos = Cosmology.linearInterpolation(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO,
					new BigDecimal(this.diameter - radius), new BigDecimal(VictusLudusGame.sharedRand.nextFloat())).doubleValue();
				yPos = Cosmology.linearInterpolation(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO,
					new BigDecimal(this.diameter - radius), new BigDecimal(VictusLudusGame.sharedRand.nextFloat())).doubleValue();

				for (Galaxy g : this.galaxies) {
					if (xPos - Math.pow(g.getxPosition(), 2) + yPos - Math.pow(g.getyPosition(), 2) < Math.pow(g.getRadius(), 2)) {
						continue;
					}
				}

				lookingForEmptySpace = false;
			}

			if (placementAttemptNum > 0) {
				galaxy.setxPosition(xPos);
				galaxy.setyPosition(yPos);
				galaxy.setRadius(radius);

				this.galaxies.add(galaxy);
			}
		}

		this.cosmicDate.addYears(timePassed.toBigInteger());
		this.age = this.age.add(timePassed);
	}

	/**
	 * A tick of time in years
	 * 
	 * @param delta the amount of stellar time that has passed since the last tick, in years
	 */
	public void tick (final BigDecimal delta) {
		// tick all children
		for (Galaxy g : this.galaxies) {
			BigInteger yearDiff = g.getBirthDate().getYearsSinceBigBang().subtract(this.cosmicDate.getYearsSinceBigBang());

			if (yearDiff.compareTo(BigInteger.ZERO) > 0) {
				g.tick(delta.subtract(new BigDecimal(yearDiff)));
			} else {
				g.tick(delta);
			}
		}

		this.cosmicDate.addYears(delta.toBigInteger());
		this.age = this.age.add(delta);
	}

	public ArrayList<Galaxy> getGalaxies () {
		return this.galaxies;
	}

	public StarDate getCosmicDate () {
		return this.cosmicDate;
	}

	public BigDecimal getAge () {
		return this.age;
	}

	public double getDiameter () {
		return this.diameter;
	}

	public long getSeed () {
		return this.seed;
	}

	public int getRandomGalaxyCount () {
		double rand = Cosmology.randomNoise((int)this.seed, 3243233);
		return Cosmology.linearInterpolation(Cosmology.NEGATIVE_ONE, BigDecimal.ONE, new BigDecimal(Universe.MIN_GALAXY_COUNT),
			new BigDecimal(Universe.MAX_GALAXY_COUNT), new BigDecimal(rand)).intValue();
	}

	public float getStarMassDistrubution () {
		return this.starMassDistrubution;
	}
}
