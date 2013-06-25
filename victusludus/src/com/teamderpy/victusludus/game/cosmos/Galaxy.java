
package com.teamderpy.victusludus.game.cosmos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.language.GreekLatinGenerator;

/**
 * Has lots of stars
 * 
 * @author Josh
 */
public class Galaxy {
	private static int MIN_STAR_COUNT = 4;
	private static int MAX_STAR_COUNT = 20;
	public static BigDecimal MIN_GALAXY_RADIUS = Cosmology.PARSEC.multiply(new BigDecimal("50"), Cosmology.COSMIC_RND);
	public static BigDecimal MAX_GALAXY_RADIUS = Cosmology.PARSEC.multiply(new BigDecimal("25000"), Cosmology.COSMIC_RND);
	private static BigDecimal STAR_RATIO = new BigDecimal("80E6");

	/** list of stars in the galaxy */
	private ArrayList<Star> stars;

	/** the universe this galaxy belongs to */
	private Universe parentUniverse;

	/** the common name of this galaxy */
	private String name;

	/** the birth date of the galaxy */
	private StarDate birthDate;

	/** the age of the galaxy in years */
	private BigDecimal age;

	/** the type of galaxy */
	private EnumGalaxyType galaxyType;

	/** the random seed for this galaxy **/
	private long seed;

	/** the max number of stars for this galaxy */
	private int maxStars;

	/** degrees rotated */
	private double rotation;
	private double angularVelocity;

	/** the position of the galaxy in the universe */
	private double xPosition;
	private double yPosition;
	private double radius;

	public Galaxy (final StarDate birthDate, final Universe universe, final int id) {
		VictusLudusGame.sharedRand.setSeed(universe.getSeed() + 39082 + id * 479);
		this.seed = VictusLudusGame.sharedRand.nextLong();

		this.parentUniverse = universe;
		this.name = this.getRandomName();

		this.birthDate = birthDate;
		this.age = BigDecimal.ZERO;

		this.stars = new ArrayList<Star>();
		this.galaxyType = this.getRandomGalaxyType();
		this.rotation = this.getRandomGalaxyRotation();
		this.angularVelocity = this.getRandomAngularVelocity();
		this.maxStars = this.getRandomStarCount();
	}

	/**
	 * Creates a galaxy with a certain amount of time passed since its birth
	 * 
	 * @param timeYearsPassed
	 */
	public void create (final BigDecimal timeYearsPassed) {
		this.age = this.age.add(timeYearsPassed);
	}

	/**
	 * Creates the stars of the galaxy
	 */
	public void createStars () {
		int starsToCreateCount = (int)this.age.divideToIntegralValue(Galaxy.STAR_RATIO).doubleValue();
		starsToCreateCount = Math.max(Galaxy.MIN_STAR_COUNT, Math.min(this.maxStars, starsToCreateCount));

		BigDecimal earliestTime = new BigDecimal(this.getBirthDate().getYearsSinceBigBang()
			.max(Universe.MIN_AGE_FOR_STARS.toBigInteger()));

		BigDecimal latestTime;

		if (this.galaxyType.isGalacticNursery()) {
			latestTime = new BigDecimal(this.getBirthDate().getYearsSinceBigBang().add(this.age.toBigInteger())
				.min(Universe.MAX_AGE_FOR_STARS.toBigInteger()));
		} else {
			latestTime = new BigDecimal(this.getBirthDate().getYearsSinceBigBang()
				.add(this.age.multiply(BigDecimal.valueOf(0.10), Star.STELLAR_RND).toBigInteger())
				.min(Universe.MAX_AGE_FOR_STARS.toBigInteger()));
		}

		if (earliestTime.compareTo(latestTime) >= 0) {
			return; // no valid time period to make stars!
		}

		VictusLudusGame.sharedRand.setSeed(this.seed + 234234523);

		for (int i = 0; i < starsToCreateCount; i++) {
			// create the star
			BigInteger years = Cosmology.linearInterpolation(Cosmology.NEGATIVE_ONE, BigDecimal.ONE, earliestTime, latestTime,
				new BigDecimal(VictusLudusGame.sharedRand.nextFloat())).toBigInteger();

			StarDate starBirthDate = new StarDate();
			starBirthDate.addYears(years);

			Star star = new Star(starBirthDate, this, i);

			// place the star
			boolean lookingForEmptySpace = true;
			int placementAttemptNum = 100;

			double xPos = -1;
			double yPos = -1;

			VictusLudusGame.sharedRand.setSeed(star.getSeed() - 4508934058L);

			while (lookingForEmptySpace && placementAttemptNum > 0) {
				placementAttemptNum--;

				// pick a spot with nothing else in the area
				xPos = Cosmology.linearInterpolation(BigDecimal.ZERO, BigDecimal.ONE, new BigDecimal(this.xPosition - this.radius),
					new BigDecimal(this.xPosition + this.radius), new BigDecimal(VictusLudusGame.sharedRand.nextFloat()))
					.doubleValue();
				yPos = Cosmology.linearInterpolation(BigDecimal.ZERO, BigDecimal.ONE, new BigDecimal(this.yPosition - this.radius),
					new BigDecimal(this.yPosition + this.radius), new BigDecimal(VictusLudusGame.sharedRand.nextFloat()))
					.doubleValue();

				lookingForEmptySpace = false;

				for (Star s : this.stars) {
					if (Math.pow(xPos - s.getxPosition(), 2) + Math.pow(yPos - s.getyPosition(), 2) < Math.pow(
						Star.SOLAR_SYSTEM_RADIUS.doubleValue(), 2)) {
						lookingForEmptySpace = true;
						break;
					}
				}
			}

			if (placementAttemptNum > 0) {
				star.setxPosition(xPos);
				star.setyPosition(yPos);

				this.stars.add(star);

				// System.err.println("adding star to " + this.hashCode());
			}

			// simulate to current time
			star.create(new BigDecimal(this.birthDate.getYearsSinceBigBang().add(this.age.toBigInteger())
				.subtract(star.getBirthDate().getYearsSinceBigBang())));
		}
	}

	/**
	 * Destroy the stars of the galaxy
	 */
	public void removeStars () {
		this.stars.clear();
	}

	/**
	 * A tick of time
	 * 
	 * @param delta the amount of stellar time that has passed since the last tick, in years
	 */
	public void tick (final BigDecimal delta) {
		// tick all children
		for (Star s : this.stars) {
			s.tick(delta);
		}

		this.age = this.age.add(delta);
	}

	private EnumGalaxyType getRandomGalaxyType () {
		VictusLudusGame.sharedRand.setSeed(this.seed + 234);
		return EnumGalaxyType.values()[VictusLudusGame.sharedRand.nextInt(EnumGalaxyType.values().length)];
	}

	private double getRandomGalaxyRotation () {
		VictusLudusGame.sharedRand.setSeed(this.seed + 102838);
		return VictusLudusGame.sharedRand.nextDouble() * 360;
	}

	private double getRandomAngularVelocity () {
		VictusLudusGame.sharedRand.setSeed(this.seed + 3556);
		return VictusLudusGame.sharedRand.nextDouble() * 10;
	}

	/**
	 * Gets a random galaxy name
	 * 
	 * @return a string with the random name
	 */
	public String getRandomName () {
		VictusLudusGame.sharedRand.setSeed(this.seed - 3982721);

		GreekLatinGenerator g = new GreekLatinGenerator(this.seed - 3982721);

		return g.getWord();
	}

	public ArrayList<Star> getStars () {
		return this.stars;
	}

	public StarDate getBirthDate () {
		return this.birthDate;
	}

	public BigDecimal getAge () {
		return this.age;
	}

	public double getxPosition () {
		return this.xPosition;
	}

	public double getyPosition () {
		return this.yPosition;
	}

	public double getRadius () {
		return this.radius;
	}

	public Universe getParentUniverse () {
		return this.parentUniverse;
	}

	public void setxPosition (final double xPosition) {
		this.xPosition = xPosition;
	}

	public void setyPosition (final double yPosition) {
		this.yPosition = yPosition;
	}

	public void setRadius (final double radius) {
		this.radius = radius;
	}

	public EnumGalaxyType getGalaxyType () {
		return this.galaxyType;
	}

	/**
	 * Get the rotation of the galaxy in degrees
	 * @return rotation
	 */
	public double getRotation () {
		return this.rotation;
	}

	public void setRotation (final double rotation) {
		if (rotation > 360) {
			this.rotation = rotation - (360 * ((int)rotation / 360));
		} else if (rotation < -360) {
			this.rotation = rotation + (360 * ((int)rotation / 360));
		} else {
			this.rotation = rotation;
		}
	}

	/**
	 * Gets the angular velocity of the galaxy in degrees per second
	 * @return the angular velocity
	 */
	public double getAngularVelocity () {
		return this.angularVelocity;
	}

	public long getSeed () {
		return this.seed;
	}

	public String getName () {
		return this.name;
	}

	public int getRandomStarCount () {
		double rand = Cosmology.randomNoise((int)this.seed, 12983);
		return Cosmology.linearInterpolation(Cosmology.NEGATIVE_ONE, BigDecimal.ONE, new BigDecimal("1"),
			new BigDecimal(Galaxy.MAX_STAR_COUNT), new BigDecimal(rand)).intValue();
	}

	public int getMaxStars () {
		return this.maxStars;
	}

	@Override
	public String toString () {
		return "type: " + this.galaxyType + "\n" + " age: " + Cosmology.getFormattedStellarAge(this.age);
	}
}
