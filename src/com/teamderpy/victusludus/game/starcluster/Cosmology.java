package com.teamderpy.victusludus.game.starcluster;

import java.math.BigDecimal;
import java.math.MathContext;

public class Cosmology {
	public static BigDecimal NEGATIVE_ONE = new BigDecimal("-1");

	public static BigDecimal SOLAR_MASS = new BigDecimal("1.98855E30");
	public static BigDecimal SOLAR_LUMINOSITY = new BigDecimal("3.846E26");
	public static BigDecimal SOLAR_RADIUS = new BigDecimal("6.955E8");
	public static BigDecimal SOLAR_TEMPERATURE = new BigDecimal("5800");
	public static BigDecimal AU = new BigDecimal("149.597870700E6");

	public static BigDecimal EARTH_ROTATIONAL_PERIOD = new BigDecimal("86400");
	public static BigDecimal EARTH_HOUR_LENGTH = new BigDecimal("3600");
	public static BigDecimal EARTH_MINUTE_LENGTH = new BigDecimal("60");

	public static BigDecimal EARTH_MASS = new BigDecimal("5.9736E24");
	public static BigDecimal JUPITER_MASS = new BigDecimal("1.8986E27");
	public static BigDecimal PLANET_MASS_ROCKY_MIN = new BigDecimal("1E23");
	public static BigDecimal PLANET_MASS_ROCKY_MAX = Cosmology.EARTH_MASS.multiply(new BigDecimal("10"), MathContext.DECIMAL32);
	public static BigDecimal PLANET_MASS_GAS_MIN = new BigDecimal("1E23");
	public static BigDecimal PLANET_MASS_GAS_MAX = Cosmology.JUPITER_MASS.multiply(new BigDecimal("13"), MathContext.DECIMAL32);

	public static BigDecimal EARTH_RADIUS = new BigDecimal("6378000");
	public static BigDecimal JUPITER_RADIUS = new BigDecimal("71492000");
	public static BigDecimal EARTH_GRAVITY = new BigDecimal("9.80665");
	public static BigDecimal PLANET_RADIUS_GAS_MIN = Cosmology.JUPITER_RADIUS.divide(new BigDecimal("2"), MathContext.DECIMAL32);
	public static BigDecimal PLANET_RADIUS_GAS_MAX = Cosmology.JUPITER_RADIUS.multiply(new BigDecimal("2"), MathContext.DECIMAL32);
	public static BigDecimal PLANET_RADIUS_ROCKY_MIN = Cosmology.EARTH_RADIUS.divide(new BigDecimal("4"), MathContext.DECIMAL32);
	public static BigDecimal PLANET_RADIUS_ROCKY_MAX = Cosmology.EARTH_RADIUS.multiply(new BigDecimal("4"), MathContext.DECIMAL32);

	public static BigDecimal STEFAN_BOLTZMANN = new BigDecimal("5.670373E-8");
	public static BigDecimal CHANDRASEKHAR_LIMIT = Cosmology.SOLAR_MASS.multiply(new BigDecimal("1.44"), MathContext.DECIMAL32);
	public static BigDecimal TOV_LIMIT = Cosmology.SOLAR_MASS.multiply(new BigDecimal("3.2"), MathContext.DECIMAL32);
	public static BigDecimal GRAVITATIONAL_CONST = new BigDecimal("6.67384E-11");
	public static BigDecimal LIGHT_SPEED = new BigDecimal("299792458");

	/**
	 * Turns a number into a string for stellar age.
	 * I.E, 6000000 returns "6 billion years"
	 * 
	 * @param age in years
	 * @return string formatted for stellar timescale
	 */
	static String getFormattedStellarAge(final BigDecimal age){
		if(age.compareTo(BigDecimal.valueOf(1000000000000000L)) >= 0){
			return age.divide(BigDecimal.valueOf(1000000000000000L), Star.LOWPREC_RND) + " quadrillion years";
		}else if(age.compareTo(BigDecimal.valueOf(1000000000000L)) >= 0){
			return age.divide(BigDecimal.valueOf(1000000000000L), Star.LOWPREC_RND) + " trillion years";
		}else if(age.compareTo(BigDecimal.valueOf(1000000000)) >= 0){
			return age.divide(BigDecimal.valueOf(1000000000), Star.LOWPREC_RND) + " billion years";
		}else if(age.compareTo(BigDecimal.valueOf(1000000)) >= 0){
			return age.divide(BigDecimal.valueOf(1000000), Star.LOWPREC_RND) + " million years";
		}else if(age.compareTo(BigDecimal.valueOf(1000)) >= 0){
			return age.divide(BigDecimal.valueOf(1000), Star.LOWPREC_RND) + " thousand years";
		}

		return age + " years";
	}

	/**
	 * Random noise with a uniform distribution
	 *
	 * @param modifier an int to seed the random function
	 * @return a float between -1.0 and 1.0
	 */
	static double randomNoise(final int seed, final int modifier){
		int h = modifier * 257;
		h += seed;
		h = h << 13 ^ h;
		return 1.0F - (h * (h * h * 1597  + 39916801) + 1073807359 & 0x7fffffff) / 1073741824.0F;
	}

	/**
	 * Random noise with a gaussian distribution
	 * 
	 * @return a double between -1.0 and 1.0
	 */
	static double gaussianNoise(final int seed, final int modifier){
		return (Cosmology.randomNoise(seed, 33932+modifier)
				+ Cosmology.randomNoise(seed, 6923+modifier)
				+ Cosmology.randomNoise(seed, 44782+modifier)
				+ Cosmology.randomNoise(seed, 82+modifier)
				+ Cosmology.randomNoise(seed, 2948+modifier)
				+ Cosmology.randomNoise(seed, 43875+modifier)) / 6.0F;
	}

	/**
	 * Random noise with a triangle distribution
	 * 
	 * @return a double between -1.0 and 1.0
	 */
	static double triangleNoise(final int seed, final int modifier){
		return (Cosmology.randomNoise(seed, 28273+modifier)
				+ Cosmology.randomNoise(seed, 392392+modifier)) / 2.0F;
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
	static BigDecimal linearInterpolation(final BigDecimal x1, final BigDecimal x2, final BigDecimal y1, final BigDecimal y2, final BigDecimal desiredX){
		return y1.add(y2.subtract(y1).multiply(desiredX.subtract(x1).divide(x2.subtract(x1), Star.STELLAR_RND), Star.STELLAR_RND));
	}

	/**
	 * Exponential interpolation between two known points
	 * 
	 * @param x1
	 * @param x2
	 * @param y1
	 * @param y2
	 * @param desiredX the x at which we desire an interpolated Y
	 * @param slopeFactor a float which determines the growth curve.  A number
	 * less than 1 will cause rapid growth early that tapers off, with more
	 * aggressive grown the closer to zero the value is.  If the value is greater
	 * than one, then the opposite effect occurs, with slow growth early, but
	 * rapid growth rate.  Similar to a graph of Y=X^(slopeFactor)
	 * @return the interpolated Y
	 */
	static BigDecimal exponentialInterpolation(final BigDecimal x1, final BigDecimal x2, final BigDecimal y1, final BigDecimal y2, final BigDecimal desiredX, final BigDecimal slopeFactor){
		return Cosmology.pow(Cosmology.pow(y1, BigDecimal.ONE.divide(slopeFactor, Star.STELLAR_RND)).add(Cosmology.pow(y2, BigDecimal.ONE.divide(slopeFactor, Star.STELLAR_RND)).subtract(Cosmology.pow(y1, BigDecimal.ONE.divide(slopeFactor, Star.STELLAR_RND))).multiply(desiredX.subtract(x1).divide(x2.subtract(x1), Star.STELLAR_RND))), slopeFactor);
	}

	static BigDecimal pow(final BigDecimal n1, BigDecimal n2){
		int signOf2 = n2.signum();
		BigDecimal result;

		// Perform X^(A+B)=X^A*X^B (B = remainder)
		double dn1 = n1.doubleValue();

		n2 = n2.multiply(new BigDecimal(signOf2)); // n2 is now positive
		BigDecimal remainderOf2 = n2.remainder(BigDecimal.ONE);
		BigDecimal n2IntPart = n2.subtract(remainderOf2);

		// Calculate big part of the power using context -
		// bigger range and performance but lower accuracy
		BigDecimal intPow = n1.pow(n2IntPart.intValueExact());
		BigDecimal doublePow = new BigDecimal(Math.pow(dn1, remainderOf2.doubleValue()));
		result = intPow.multiply(doublePow);

		// Fix negative power
		if (signOf2 == -1) {
			result = BigDecimal.ONE.divide(result, Star.STELLAR_RND);
		}

		return result;
	}
}
