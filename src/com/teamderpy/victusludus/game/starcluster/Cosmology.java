package com.teamderpy.victusludus.game.starcluster;

import java.math.BigDecimal;
import java.math.MathContext;

public class Cosmology {
	public static BigDecimal SOLAR_MASS = new BigDecimal("1.98855E30");
	public static BigDecimal SOLAR_LUMINOSITY = new BigDecimal("3.846E26");
	public static BigDecimal SOLAR_RADIUS = new BigDecimal("6.955E8");
	public static BigDecimal SOLAR_TEMPERATURE = new BigDecimal("5800");
	public static BigDecimal AU = new BigDecimal("149.597870700E6");

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
}
