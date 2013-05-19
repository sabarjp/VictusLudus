package com.teamderpy.victusludus.game.starcluster;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.precision.Precision;

/** A planet in a solar system */
public class Planet {
	public static MathContext PLANET_RND = MathContext.DECIMAL128;
	public static BigDecimal MIN_ROTATIONAL_PERIOD = BigDecimal.valueOf(86400 / 400);
	public static BigDecimal MAX_ROTATIONAL_PERIOD = BigDecimal.valueOf(86400 * 400);

	private static String[] PLANET_NAME_ARRAY = {"Altair",
		"Sol", "Orion", "Perseus", "Heracles", "Polaris", "Alpha Centauri", "Betelgeuse", "Ursa"};

	private static String[] PLANET_SUFFIX_ARRAY = {"Prime", "II", "III", "IV", "V", "VI", "VII",
		"VIII", "IX", "X", "XI", "XII"};

	/** the star that this planet orbits */
	private Star parentStar;

	/** the random seed for this planet **/
	private float seed;

	/** the name of the planet **/
	private String name;

	/** the birth date of the planet */
	private StarDate birthDate;

	/** the mass of a planet in kilograms */
	private BigDecimal mass;

	/** the age of the planet in years */
	private BigDecimal age;

	/** the radius of the planet in meters */
	private BigDecimal radius;

	/** the rotational period of the planet in seconds */
	private BigDecimal rotationalPeriod;

	/** the type of planet */
	private EnumPlanetType planetType;

	/** the tilt of the planet in degrees */
	private BigDecimal axialTilt;

	/** values for calculating an orbit follow */
	private BigDecimal orbitSemiMajorAxis;
	private BigDecimal orbitAnomaly;
	private double orbitEccentricity;


	public Planet(final StarDate birthDate, final Star parentStar){
		this.parentStar = parentStar;

		this.seed = VictusLudus.rand.nextInt()/2;

		this.planetType = this.calcPlanetType();
		this.mass = this.calcBirthMass();

		this.age = BigDecimal.ZERO;
		this.radius = this.calcRadius();
		this.birthDate = new StarDate(birthDate.getSecondsSinceBigBang());
		this.rotationalPeriod = this.getRandomRotation();
		this.axialTilt = this.getRandomAxialTilt();

		this.name = Planet.getRandomName();
		this.createRandomOrbit();
	}

	/**
	 * A tick of time
	 */
	public void tick(final BigDecimal delta){
		this.age = this.age.add(delta);
	}

	/**
	 * Gets a random planet type from EnumPlanetType
	 * 
	 * @return the planet type
	 */
	private EnumPlanetType calcPlanetType(){
		return EnumPlanetType.randomPlanetType();
	}

	/**
	 * Generates a random rotational period which is usually
	 * close to the rotation of the earth, but is rarely
	 * much longer or shorter.
	 * 
	 * @return rotational period in seconds
	 */
	private BigDecimal getRandomRotation(){
		double rand = Cosmology.triangleNoise((int) this.seed, 4392872);

		if(rand < 0){
			return Cosmology.EARTH_ROTATIONAL_PERIOD.multiply(BigDecimal.valueOf(1.1F - Math.abs(rand)), Planet.PLANET_RND);
		} else {
			return Cosmology.EARTH_ROTATIONAL_PERIOD.divide(BigDecimal.valueOf(1.1F - Math.abs(rand)), Planet.PLANET_RND);
		}
	}

	/**
	 * Generates a random axial tilt for the planet in degrees, with less tilt
	 * being more likely than extreme tilt.
	 * 
	 * @return axial tilt in degrees, greater than or equal to 0 but less than 360.0
	 */
	private BigDecimal getRandomAxialTilt(){
		double rand = Cosmology.gaussianNoise((int)this.seed, 27162233);

		return Cosmology.exponentialInterpolation(Cosmology.NEGATIVE_ONE, BigDecimal.ONE, BigDecimal.ZERO, new BigDecimal("359.99999"), BigDecimal.valueOf(rand), new BigDecimal("3"));
	}

	/**
	 * Finds the distance from the star
	 * 
	 * @return distance from the star in meters
	 */
	private BigDecimal getDistanceFromStar(){
		return Cosmology.calculateKeplerDistance(this.orbitSemiMajorAxis, this.orbitEccentricity, this.orbitAnomaly);
	}

	/**
	 * Creates a random orbit for this planet
	 */
	private void createRandomOrbit(){
		double rand;

		/* set the long radius of the orbit */
		rand = Cosmology.randomNoise((int)this.seed, 2918);
		this.orbitSemiMajorAxis = Cosmology.exponentialInterpolation(Cosmology.NEGATIVE_ONE, BigDecimal.ONE, Cosmology.AU.multiply(new BigDecimal("0.10")), Cosmology.AU.multiply(new BigDecimal("80"), Planet.PLANET_RND), BigDecimal.valueOf(rand), new BigDecimal("5"));

		/* set the eccentricity of the orbit */
		this.orbitEccentricity = (1.0F + Cosmology.randomNoise((int)this.seed, 34998)) / 6.6F;

		/* set the orbit's angle */
		//this.orbitAnomaly = new BigDecimal((1.0F + Cosmology.randomNoise((int)this.seed, 48735)) * 180.0F);
		this.orbitAnomaly = BigDecimal.ZERO;
	}

	/**
	 * Returns the number of seconds in an hour
	 * 
	 * @return seconds in an hour
	 */
	public BigInteger getHourLength(){
		return this.getMinuteLength().multiply(new BigInteger("60"));
	}

	/**
	 * Returns the number of seconds in a minute
	 * 
	 * @return seconds in an minute
	 */
	public BigInteger getMinuteLength(){
		return this.rotationalPeriod.divide(new BigDecimal("1440"), Planet.PLANET_RND).toBigInteger();
	}

	/**
	 * Get the extra number of seconds per day
	 * 
	 * @return the extra seconds per day
	 */
	public BigDecimal getExtraSecondsPerDay(){
		return this.rotationalPeriod.subtract(new BigDecimal(this.getHourLength()).multiply(new BigDecimal("24"), Planet.PLANET_RND));
	}


	/**
	 * Calculates the birth mass of the planet
	 * 
	 * @return the birth mass
	 */
	private BigDecimal calcBirthMass(){
		if(this.planetType.isGasGiant()){
			return Cosmology.linearInterpolation(BigDecimal.ZERO, BigDecimal.ONE, Cosmology.PLANET_MASS_GAS_MIN, Cosmology.PLANET_MASS_GAS_MAX, new BigDecimal((1.0F + this.randomNoise(25498)) / 2.0F ));
		} else {
			return Cosmology.linearInterpolation(BigDecimal.ZERO, BigDecimal.ONE, Cosmology.PLANET_MASS_ROCKY_MIN, Cosmology.PLANET_MASS_ROCKY_MAX, new BigDecimal((1.0F + this.randomNoise(65486)) / 2.0F ));
		}
	}

	/**
	 * Calculates the radius of a planet
	 * 
	 * @return the new radius
	 */
	private BigDecimal calcRadius(){
		switch (this.planetType){
			case BARREN:
				return Cosmology.linearInterpolation(Cosmology.PLANET_MASS_ROCKY_MIN, Cosmology.PLANET_MASS_ROCKY_MAX, Cosmology.PLANET_RADIUS_ROCKY_MIN,  Cosmology.PLANET_RADIUS_ROCKY_MAX, this.mass);
			case CARBON:
				return Cosmology.linearInterpolation(Cosmology.PLANET_MASS_ROCKY_MIN, Cosmology.PLANET_MASS_ROCKY_MAX, Cosmology.PLANET_RADIUS_ROCKY_MIN,  Cosmology.PLANET_RADIUS_ROCKY_MAX, this.mass);
			case GAIA:
				return Cosmology.linearInterpolation(Cosmology.PLANET_MASS_ROCKY_MIN, Cosmology.PLANET_MASS_ROCKY_MAX, Cosmology.PLANET_RADIUS_ROCKY_MIN,  Cosmology.PLANET_RADIUS_ROCKY_MAX, this.mass);
			case GAS_GIANT:
				return Cosmology.linearInterpolation(Cosmology.PLANET_MASS_GAS_MIN, Cosmology.PLANET_MASS_GAS_MAX, Cosmology.PLANET_RADIUS_GAS_MIN, Cosmology.PLANET_RADIUS_GAS_MAX, this.mass);
			case ICE:
				return Cosmology.linearInterpolation(Cosmology.PLANET_MASS_ROCKY_MIN, Cosmology.PLANET_MASS_ROCKY_MAX, Cosmology.PLANET_RADIUS_ROCKY_MIN,  Cosmology.PLANET_RADIUS_ROCKY_MAX, this.mass);
			case ICE_GIANT:
				return Cosmology.linearInterpolation(Cosmology.PLANET_MASS_GAS_MIN, Cosmology.PLANET_MASS_GAS_MAX, Cosmology.PLANET_RADIUS_GAS_MIN, Cosmology.PLANET_RADIUS_GAS_MAX, this.mass);
			case METAL:
				return Cosmology.linearInterpolation(Cosmology.PLANET_MASS_ROCKY_MIN, Cosmology.PLANET_MASS_ROCKY_MAX, Cosmology.PLANET_RADIUS_ROCKY_MIN,  Cosmology.PLANET_RADIUS_ROCKY_MAX, this.mass);
			case MOLTEN:
				return Cosmology.linearInterpolation(Cosmology.PLANET_MASS_ROCKY_MIN, Cosmology.PLANET_MASS_ROCKY_MAX, Cosmology.PLANET_RADIUS_ROCKY_MIN,  Cosmology.PLANET_RADIUS_ROCKY_MAX, this.mass);
			case OCEAN:
				return Cosmology.linearInterpolation(Cosmology.PLANET_MASS_ROCKY_MIN, Cosmology.PLANET_MASS_ROCKY_MAX, Cosmology.PLANET_RADIUS_ROCKY_MIN,  Cosmology.PLANET_RADIUS_ROCKY_MAX, this.mass);
			case ROCKY:
				return Cosmology.linearInterpolation(Cosmology.PLANET_MASS_ROCKY_MIN, Cosmology.PLANET_MASS_ROCKY_MAX, Cosmology.PLANET_RADIUS_ROCKY_MIN,  Cosmology.PLANET_RADIUS_ROCKY_MAX, this.mass);
			default:
				return Cosmology.linearInterpolation(Cosmology.PLANET_MASS_ROCKY_MIN, Cosmology.PLANET_MASS_ROCKY_MAX, Cosmology.PLANET_RADIUS_ROCKY_MIN,  Cosmology.PLANET_RADIUS_ROCKY_MAX, this.mass);
		}
	}


	/**
	 * Random noise generator
	 *
	 * @param modifier an int to seed the random function
	 * @return a float between -1.0 and 1.0
	 */
	private float randomNoise(final int modifier){
		int h = modifier * 113;
		h += this.seed;
		h = h << 13 ^ h;
		return 1.0F - (h * (h * h * 4019  + 39916801) + 1073807359 & 0x7fffffff) / 1073741824.0F;
	}

	/**
	 * Gets a random planet name
	 * 
	 * @return a string with the random name
	 */
	public static String getRandomName(){
		return Planet.PLANET_NAME_ARRAY[VictusLudus.rand.nextInt(Planet.PLANET_NAME_ARRAY.length-1)]
				+ " "
				+ Planet.PLANET_SUFFIX_ARRAY[VictusLudus.rand.nextInt(Planet.PLANET_SUFFIX_ARRAY.length-1)];
	}

	@Override
	public String toString(){
		return this.name.toUpperCase() + "\n"
				+ "Planet type: " + this.planetType + "\n"
				+ "        age: " + Cosmology.getFormattedStellarAge(this.age) + "\n"
				+ "     radius: " + Precision.roundTwo(this.radius) + " m  =  " + Precision.roundFive(this.radius.divide(Cosmology.EARTH_RADIUS, Star.STELLAR_RND)) + " Earth radius\n"
				+ "       mass: " + Precision.roundTwo(this.mass) + " kg  =  " + Precision.roundFive(this.mass.divide(Cosmology.EARTH_MASS, Star.STELLAR_RND)) + " Earth mass\n"
				+ "    gravity: " + Precision.roundTwo(Cosmology.calculateGravity(this.mass, this.radius)) + " m/s^2  =  " + Precision.roundFive(Cosmology.calculateGravity(this.mass, this.radius).divide(Cosmology.EARTH_GRAVITY, Star.STELLAR_RND)) + " Earth gravity\n\n"
				+ "   true day: " + Precision.roundTwo(this.rotationalPeriod) + " s  =  " + Precision.roundFive(this.rotationalPeriod.divide(Cosmology.EARTH_ROTATIONAL_PERIOD, Star.STELLAR_RND)) + " Earth days \n"
				+ " approx day: " + this.getHourLength().multiply(new BigInteger("24")) + "\n"
				+ "  leap time: " + "off by " + Precision.roundTwo(this.getExtraSecondsPerDay()) + " s per day\n"
				+ "             " + Precision.roundFive(this.rotationalPeriod.divide(this.getExtraSecondsPerDay(), Planet.PLANET_RND)) + " days until leap day is needed\n"
				+ "hour length: " + this.getHourLength() + " s\n"
				+ " min length: " + this.getMinuteLength() + " s\n\n"
				+ " axial tilt: " + Precision.roundTwo(this.axialTilt) + " degrees from ecliptic\n"
				+ "  orbit SMA: " + Precision.roundTwo(this.orbitSemiMajorAxis) + " m    " + Precision.roundFive(this.orbitSemiMajorAxis.divide(Cosmology.AU, Star.STELLAR_RND)) + " AU\n"
				+ "  orbit ecc: " + this.orbitEccentricity + "\n"
				+ "orbit angle: " + this.orbitAnomaly + " degrees\n"
				+ "       dist: " + Precision.roundTwo(this.getDistanceFromStar()) + " m    " + Precision.roundFive(this.getDistanceFromStar().divide(Cosmology.AU, Star.STELLAR_RND))+ " AU\n"
				+ " orbit time: " + Precision.roundTwo(Cosmology.calculateOrbitalPeriod(this.mass, this.parentStar.getMass(), this.orbitSemiMajorAxis)) + " s    " + Precision.roundFive(Cosmology.calculateOrbitalPeriod(this.mass, this.parentStar.getMass(), this.orbitSemiMajorAxis).divide(Cosmology.EARTH_DAY_LENGTH, Star.STELLAR_RND))+ " earth days\n";
	}

	public Star getParentStar() {
		return this.parentStar;
	}

	public String getName() {
		return this.name;
	}

	public StarDate getBirthDate() {
		return this.birthDate;
	}

	public BigDecimal getMass() {
		return this.mass;
	}

	public BigDecimal getAge() {
		return this.age;
	}

	public BigDecimal getRadius() {
		return this.radius;
	}

	public BigDecimal getRotationalPeriod() {
		return this.rotationalPeriod;
	}

	public EnumPlanetType getPlanetType() {
		return this.planetType;
	}

	public BigDecimal getAxialTilt() {
		return this.axialTilt;
	}

	public BigDecimal getOrbitSemiMajorAxis() {
		return this.orbitSemiMajorAxis;
	}

	public BigDecimal getOrbitAnomaly() {
		return this.orbitAnomaly;
	}

	public double getOrbitEccentricity() {
		return this.orbitEccentricity;
	}
}