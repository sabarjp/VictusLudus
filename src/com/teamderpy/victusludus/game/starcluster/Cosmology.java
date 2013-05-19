package com.teamderpy.victusludus.game.starcluster;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import com.teamderpy.victusludus.precision.Precision;

public class Cosmology {
	public static BigDecimal NEGATIVE_ONE = new BigDecimal("-1");

	public static BigDecimal SOLAR_MASS = new BigDecimal("1.98855E30");
	public static BigDecimal SOLAR_LUMINOSITY = new BigDecimal("3.846E26");
	public static BigDecimal SOLAR_RADIUS = new BigDecimal("6.955E8");
	public static BigDecimal SOLAR_TEMPERATURE = new BigDecimal("5800");
	public static BigDecimal AU = new BigDecimal("149.597870700E9");

	public static BigDecimal EARTH_ROTATIONAL_PERIOD = new BigDecimal("86400");
	public static BigDecimal EARTH_HOUR_LENGTH = new BigDecimal("3600");
	public static BigDecimal EARTH_MINUTE_LENGTH = new BigDecimal("60");
	public static BigDecimal EARTH_DAY_LENGTH = new BigDecimal(60 * 60 * 24);

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

	public static MathContext COSMIC_RND = MathContext.DECIMAL128;

	/**
	 * Turns a number into a string for stellar age.
	 * I.E, 6000000 returns "6 billion years"
	 * 
	 * @param age in years
	 * @return string formatted for stellar timescale
	 */
	static public String getFormattedStellarAge(final BigDecimal age){
		if(age.compareTo(BigDecimal.valueOf(1000000000000000L)) >= 0){
			return Precision.roundTwo(age.divide(BigDecimal.valueOf(1000000000000000L), Star.STELLAR_RND)) + " quadrillion years";
		}else if(age.compareTo(BigDecimal.valueOf(1000000000000L)) >= 0){
			return Precision.roundTwo(age.divide(BigDecimal.valueOf(1000000000000L), Star.STELLAR_RND)) + " trillion years";
		}else if(age.compareTo(BigDecimal.valueOf(1000000000)) >= 0){
			return Precision.roundTwo(age.divide(BigDecimal.valueOf(1000000000), Star.STELLAR_RND)) + " billion years";
		}else if(age.compareTo(BigDecimal.valueOf(1000000)) >= 0){
			return Precision.roundTwo(age.divide(BigDecimal.valueOf(1000000), Star.STELLAR_RND)) + " million years";
		}else if(age.compareTo(BigDecimal.valueOf(1000)) >= 0){
			return Precision.roundTwo(age.divide(BigDecimal.valueOf(1000), Star.STELLAR_RND)) + " thousand years";
		}

		return Precision.round(age) + " years";
	}

	/**
	 * Random noise with a uniform distribution
	 *
	 * @param modifier an int to seed the random function
	 * @return a float between -1.0 and 1.0
	 */
	static public double randomNoise(final int seed, final int modifier){
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
	static public double gaussianNoise(final int seed, final int modifier){
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
	static public double triangleNoise(final int seed, final int modifier){
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
	static public BigDecimal linearInterpolation(final BigDecimal x1, final BigDecimal x2, final BigDecimal y1, final BigDecimal y2, final BigDecimal desiredX){
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
	static public BigDecimal exponentialInterpolation(final BigDecimal x1, final BigDecimal x2, final BigDecimal y1, final BigDecimal y2, final BigDecimal desiredX, final BigDecimal slopeFactor){
		return Precision.pow(Precision.pow(y1, BigDecimal.ONE.divide(slopeFactor, Star.STELLAR_RND)).add(Precision.pow(y2, BigDecimal.ONE.divide(slopeFactor, Star.STELLAR_RND)).subtract(Precision.pow(y1, BigDecimal.ONE.divide(slopeFactor, Star.STELLAR_RND))).multiply(desiredX.subtract(x1).divide(x2.subtract(x1), Star.STELLAR_RND))), slopeFactor);
	}

	/**
	 * Calculates the gravitational force of attraction of a massive object
	 * for another smaller object at its surface with negligible mass
	 * 
	 * @param mass in kilograms
	 * @param radius in meters
	 * @return the force of gravitational attraction
	 */
	static public BigDecimal calculateGravity(final BigDecimal mass, final BigDecimal radius){
		return Cosmology.GRAVITATIONAL_CONST.multiply(mass, Star.STELLAR_RND).divide(radius.pow(2), Star.STELLAR_RND);
	}

	/**
	 * Calculates the mean motion of two orbiting objects
	 * 
	 * @param mass1 the mass of the first object
	 * @param mass2 the mass of the second object
	 * @param semiMajorAxis the semi-major axis of the orbiting body
	 * @return the mean motion
	 */
	static public BigDecimal calculateMeanMotion(final BigDecimal mass1, final BigDecimal mass2, final BigDecimal semiMajorAxis){
		return Precision.pow(Cosmology.GRAVITATIONAL_CONST.multiply(mass1.add(mass2), Cosmology.COSMIC_RND).divide(semiMajorAxis.pow(3), Cosmology.COSMIC_RND), new BigDecimal("0.5"));
	}

	/**
	 * Calculates the orbital period of an orbiting object
	 * 
	 * @param mass1 the mass of the first object
	 * @param mass2 the mass of the second object
	 * @param semiMajorAxis the semi-major axis of the orbiting body
	 * @return the orbital period
	 */
	static public BigDecimal calculateOrbitalPeriod(final BigDecimal mass1, final BigDecimal mass2, final BigDecimal semiMajorAxis){
		final BigDecimal pi = new BigDecimal(Math.PI);
		final BigDecimal K = pi.multiply(new BigDecimal("2"), Cosmology.COSMIC_RND);

		return K.divide(Cosmology.calculateMeanMotion(mass1, mass2, semiMajorAxis), Cosmology.COSMIC_RND);
	}

	/**
	 * Calculates the mean anomaly of two orbiting objects
	 * 
	 * @param mass1 the mass of the first object
	 * @param mass2 the mass of the second object
	 * @param semiMajorAxis the semi-major axis of the orbiting body
	 * @param deltaTime the amount of time that has passed since periapsis
	 * @return the mean anomaly
	 */
	static public BigDecimal calculateMeanAnomaly(final BigDecimal mass1, final BigDecimal mass2, final BigDecimal semiMajorAxis, final BigDecimal deltaTime){
		return Cosmology.calculateMeanMotion(mass1, mass2, semiMajorAxis).multiply(deltaTime, Cosmology.COSMIC_RND);
	}

	/**
	 * Calculates the eccentric anomaly given eccentricity and mean anomaly
	 * 
	 * Derived from http://www.jgiesen.de/kepler/kepler.html
	 * 
	 * @param eccentricity the eccentricity
	 * @param meanAnomaly the mean anomaly
	 * @return the eccentric anomaly
	 */
	static public BigDecimal calculateEccentricAnomaly(final double eccentricity, final BigDecimal meanAnomaly){
		final int decimalPlaces = 15;

		final BigDecimal pi = new BigDecimal(Math.PI);
		final BigDecimal K = pi.divide(new BigDecimal("180"), Cosmology.COSMIC_RND);

		final double delta = Math.pow(10.0D, -1 * decimalPlaces);
		BigDecimal E;
		BigDecimal F;
		BigDecimal m;

		m = meanAnomaly.divide(new BigDecimal("360.0"), Cosmology.COSMIC_RND);
		m = new BigDecimal("2.0").multiply(pi, Cosmology.COSMIC_RND).multiply(m.subtract(Precision.floor(m)), Cosmology.COSMIC_RND);

		if(eccentricity < 0.8D){
			E = m;
		} else {
			E = new BigDecimal("180.0");
		}

		F = E.subtract(new BigDecimal(eccentricity).multiply(new BigDecimal(Math.sin(m.doubleValue())).subtract(m), Cosmology.COSMIC_RND));

		int i = 0;

		while(F.abs().compareTo(new BigDecimal(delta)) > 0 && i < 50){
			E = E.subtract(F.divide(BigDecimal.ONE.subtract(new BigDecimal(eccentricity * Math.cos(E.doubleValue()))), Cosmology.COSMIC_RND));
			F = E.subtract(new BigDecimal(eccentricity * Math.sin(E.doubleValue()))).subtract(m);

			i++;
		}

		E = E.divide(K, Cosmology.COSMIC_RND);

		return E.setScale(decimalPlaces, RoundingMode.HALF_UP);
	}

	/**
	 * Calculates the true anomaly given an eccentricity and eccentric anomaly
	 * 
	 * Derived from http://www.jgiesen.de/kepler/kepler.html
	 * 
	 * @param eccentricity the eccentricity
	 * @param eccentricAnomaly the eccentric anomaly
	 * @return the true anomaly
	 */
	static public BigDecimal calculateTrueAnomaly(final double eccentricity, final BigDecimal eccentricAnomaly){
		final BigDecimal pi = new BigDecimal(Math.PI);
		final BigDecimal K = pi.divide(new BigDecimal("180"), Cosmology.COSMIC_RND);

		double s = Math.sin(eccentricAnomaly.multiply(K, Cosmology.COSMIC_RND).doubleValue());
		double c = Math.cos(eccentricAnomaly.multiply(K, Cosmology.COSMIC_RND).doubleValue());

		double fak = Math.sqrt(1.0D - eccentricity*eccentricity);

		BigDecimal phi = new BigDecimal(Math.atan2(fak*s, c-eccentricity)).divide(K, Cosmology.COSMIC_RND);

		return phi;
	}

	/**
	 * Calculates the kepler orbital distance given the semi major axis, eccentricity, and
	 * true anomaly
	 * 
	 * Derived from http://www.jgiesen.de/kepler/kepler.html
	 * 
	 * @param semiMajorAxis
	 * @param eccentricity
	 * @param trueAnomaly
	 * @return the orbital distance
	 */
	static public BigDecimal calculateKeplerDistance(final BigDecimal semiMajorAxis, final double eccentricity, final BigDecimal trueAnomaly){
		final BigDecimal pi = new BigDecimal(Math.PI);
		final BigDecimal K = pi.divide(new BigDecimal("180"), Cosmology.COSMIC_RND);

		return semiMajorAxis.multiply(new BigDecimal(1.0D - Math.pow(eccentricity, 2)), Planet.PLANET_RND).divide(new BigDecimal(1.0D + eccentricity * Math.cos(trueAnomaly.multiply(K, Cosmology.COSMIC_RND).doubleValue())), Planet.PLANET_RND);
	}

	/**
	 * Calculates the kepler coordinates of an object given the semi major axis,
	 * eccentricity, and eccentric anomaly
	 * 
	 * Derived from http://www.jgiesen.de/kepler/kepler.html
	 * 
	 * @param semiMajorAxis
	 * @param eccentricity
	 * @param eccentricAnomaly
	 * @return the coordinates in an array, with 0 corresponding to X and 1 corresponding to Y
	 */
	static public BigDecimal[] calculateKeplerCoordinate(final BigDecimal semiMajorAxis, final double eccentricity, final BigDecimal eccentricAnomaly){
		final BigDecimal pi = new BigDecimal(Math.PI);
		final BigDecimal K = pi.divide(new BigDecimal("180"), Cosmology.COSMIC_RND);

		double s = Math.sin(eccentricAnomaly.multiply(K, Cosmology.COSMIC_RND).doubleValue());
		double c = Math.cos(eccentricAnomaly.multiply(K, Cosmology.COSMIC_RND).doubleValue());

		BigDecimal x = semiMajorAxis.multiply(new BigDecimal(c-eccentricity), Cosmology.COSMIC_RND);
		BigDecimal y = semiMajorAxis.multiply(new BigDecimal(s * Math.sqrt(1.0D - eccentricity * eccentricity)), Cosmology.COSMIC_RND);

		return new BigDecimal[]{x, y};
	}

	/**
	 * Calculates the kepler orbital distance given several parameters at a point in time
	 * past the periapsis
	 * 
	 * @param mass1
	 * @param mass2
	 * @param semiMajorAxis
	 * @param deltaTime
	 * @param eccentricity
	 * @return the distance in meters
	 */
	static public BigDecimal calculateKeplerDistanceAtTime(final BigDecimal mass1, final BigDecimal mass2, final BigDecimal semiMajorAxis, final BigDecimal deltaTime, final double eccentricity){
		BigDecimal meanAnomaly = Cosmology.calculateMeanAnomaly(mass1, mass2, semiMajorAxis, deltaTime);
		BigDecimal eccentricAnomaly = Cosmology.calculateEccentricAnomaly(eccentricity, meanAnomaly);
		BigDecimal trueAnomaly = Cosmology.calculateTrueAnomaly(eccentricity, eccentricAnomaly);
		BigDecimal keplerDistance = Cosmology.calculateKeplerDistance(semiMajorAxis, eccentricity, trueAnomaly);

		return keplerDistance;
	}

	/**
	 * Calculates the kepler coordinate given several parameters at a point in time past
	 * the periapsis
	 * 
	 * @param mass1
	 * @param mass2
	 * @param semiMajorAxis
	 * @param deltaTime
	 * @param eccentricity
	 * @return the coordinates in an array, with 0 corresponding to X and 1 corresponding to Y
	 */
	static public BigDecimal[] calculateKeplerCoordinateAtTime(final BigDecimal mass1, final BigDecimal mass2, final BigDecimal semiMajorAxis, final BigDecimal deltaTime, final double eccentricity){
		BigDecimal meanAnomaly = Cosmology.calculateMeanAnomaly(mass1, mass2, semiMajorAxis, deltaTime);
		BigDecimal eccentricAnomaly = Cosmology.calculateEccentricAnomaly(eccentricity, meanAnomaly);
		BigDecimal keplerCoordinate[] = Cosmology.calculateKeplerCoordinate(semiMajorAxis, eccentricity, eccentricAnomaly);

		return keplerCoordinate;
	}
}
