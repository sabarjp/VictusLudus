package com.teamderpy.victusludus.game.starcluster;

import java.math.BigDecimal;
import java.math.MathContext;

import com.teamderpy.victusludus.data.VictusLudus;

/** A planet in a solar system */
public class Planet {
	public static MathContext PLANET_RND = MathContext.DECIMAL128;

	/** the random seed for this planet **/
	private float seed;

	/** the birth date of the planet */
	private StarDate birthDate;

	/** the mass of a planet in kilograms */
	private BigDecimal mass;

	/** the age of the planet in years */
	private BigDecimal age;

	/** the radius of the planet in meters */
	private BigDecimal radius;

	/** the type of planet */
	private EnumPlanetType planetType;

	public Planet(final StarDate birthDate){
		this.seed = VictusLudus.rand.nextInt()/2;

		this.planetType = this.calcPlanetType();
		this.mass = this.calcBirthMass();

		this.age = BigDecimal.ZERO;
		this.radius = this.calcRadius();
		this.birthDate = new StarDate(birthDate.getSecondsSinceBigBang());
	}

	/**
	 * Gets a random planet type
	 * @return the planet type
	 */
	private EnumPlanetType calcPlanetType(){
		return EnumPlanetType.randomPlanetType();
	}

	/**
	 * Calculates the birth mass of the planet
	 * 
	 * @return the birth mass
	 */
	private BigDecimal calcBirthMass(){
		if(this.planetType.isGasGiant()){
			return Planet.linearInterpolation(BigDecimal.ZERO, BigDecimal.ONE, Cosmology.PLANET_MASS_GAS_MIN, Cosmology.PLANET_MASS_GAS_MAX, new BigDecimal((1.0F + this.randomNoise(25498)) / 2.0F ));
		} else {
			return Planet.linearInterpolation(BigDecimal.ZERO, BigDecimal.ONE, Cosmology.PLANET_MASS_ROCKY_MIN, Cosmology.PLANET_MASS_ROCKY_MAX, new BigDecimal((1.0F + this.randomNoise(65486)) / 2.0F ));
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
				return Planet.linearInterpolation(Cosmology.PLANET_MASS_ROCKY_MIN, Cosmology.PLANET_MASS_ROCKY_MAX, Cosmology.PLANET_RADIUS_ROCKY_MIN,  Cosmology.PLANET_RADIUS_ROCKY_MAX, this.mass);
			case CARBON:
				return Planet.linearInterpolation(Cosmology.PLANET_MASS_ROCKY_MIN, Cosmology.PLANET_MASS_ROCKY_MAX, Cosmology.PLANET_RADIUS_ROCKY_MIN,  Cosmology.PLANET_RADIUS_ROCKY_MAX, this.mass);
			case GAIA:
				return Planet.linearInterpolation(Cosmology.PLANET_MASS_ROCKY_MIN, Cosmology.PLANET_MASS_ROCKY_MAX, Cosmology.PLANET_RADIUS_ROCKY_MIN,  Cosmology.PLANET_RADIUS_ROCKY_MAX, this.mass);
			case GAS_GIANT:
				return Planet.linearInterpolation(Cosmology.PLANET_MASS_GAS_MIN, Cosmology.PLANET_MASS_GAS_MAX, Cosmology.PLANET_RADIUS_GAS_MIN, Cosmology.PLANET_RADIUS_GAS_MAX, this.mass);
			case ICE:
				return Planet.linearInterpolation(Cosmology.PLANET_MASS_ROCKY_MIN, Cosmology.PLANET_MASS_ROCKY_MAX, Cosmology.PLANET_RADIUS_ROCKY_MIN,  Cosmology.PLANET_RADIUS_ROCKY_MAX, this.mass);
			case ICE_GIANT:
				return Planet.linearInterpolation(Cosmology.PLANET_MASS_GAS_MIN, Cosmology.PLANET_MASS_GAS_MAX, Cosmology.PLANET_RADIUS_GAS_MIN, Cosmology.PLANET_RADIUS_GAS_MAX, this.mass);
			case METAL:
				return Planet.linearInterpolation(Cosmology.PLANET_MASS_ROCKY_MIN, Cosmology.PLANET_MASS_ROCKY_MAX, Cosmology.PLANET_RADIUS_ROCKY_MIN,  Cosmology.PLANET_RADIUS_ROCKY_MAX, this.mass);
			case MOLTEN:
				return Planet.linearInterpolation(Cosmology.PLANET_MASS_ROCKY_MIN, Cosmology.PLANET_MASS_ROCKY_MAX, Cosmology.PLANET_RADIUS_ROCKY_MIN,  Cosmology.PLANET_RADIUS_ROCKY_MAX, this.mass);
			case OCEAN:
				return Planet.linearInterpolation(Cosmology.PLANET_MASS_ROCKY_MIN, Cosmology.PLANET_MASS_ROCKY_MAX, Cosmology.PLANET_RADIUS_ROCKY_MIN,  Cosmology.PLANET_RADIUS_ROCKY_MAX, this.mass);
			case ROCKY:
				return Planet.linearInterpolation(Cosmology.PLANET_MASS_ROCKY_MIN, Cosmology.PLANET_MASS_ROCKY_MAX, Cosmology.PLANET_RADIUS_ROCKY_MIN,  Cosmology.PLANET_RADIUS_ROCKY_MAX, this.mass);
			default:
				return Planet.linearInterpolation(Cosmology.PLANET_MASS_ROCKY_MIN, Cosmology.PLANET_MASS_ROCKY_MAX, Cosmology.PLANET_RADIUS_ROCKY_MIN,  Cosmology.PLANET_RADIUS_ROCKY_MAX, this.mass);
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
	 * Linear interpolation between two known points
	 * 
	 * @param x1
	 * @param x2
	 * @param y1
	 * @param y2
	 * @param desiredX the x at which we desire an interpolated Y
	 * @return the interpolated Y
	 */
	private static BigDecimal linearInterpolation(final BigDecimal x1, final BigDecimal x2, final BigDecimal y1, final BigDecimal y2, final BigDecimal desiredX){
		return y1.add(y2.subtract(y1).multiply(desiredX.subtract(x1).divide(x2.subtract(x1), Star.STELLAR_RND), Star.STELLAR_RND));
	}

	/*
	 * Calculates the gravitational force of the planet
	 */
	private BigDecimal calculateGravity(){
		return Cosmology.GRAVITATIONAL_CONST.multiply(this.mass, Star.STELLAR_RND).divide(this.radius.pow(2), Star.STELLAR_RND);
	}

	@Override
	public String toString(){
		return " Planet type: " + this.planetType + "\n"
				//+ "        age: " + Star.getFormattedStellarAge(this.age) + "\n"
				+ "       mass: " + this.mass + "  Earth " + this.mass.divide(Cosmology.EARTH_MASS, Star.STELLAR_RND) + "\n"
				//+ " luminosity: " + this.luminosity + "  Solar " + this.luminosity.divide(Cosmology.SOLAR_LUMINOSITY, Star.STELLAR_RND) + "\n"
				+ "     radius: " + this.radius + "  Earth " + this.radius.divide(Cosmology.EARTH_RADIUS, Star.STELLAR_RND) + "\n"
				//+ "       temp: " + Math.round(this.surfaceTemperature.doubleValue()) + " K   " + this.getStarColorName() + "\n"
				//+ "      class: " + this.getSpectralClass() + "\n"
				+ "    gravity: " + this.calculateGravity() + "  Earth " + this.calculateGravity().divide(Cosmology.EARTH_GRAVITY, Star.STELLAR_RND) + "\n";
	}
}
