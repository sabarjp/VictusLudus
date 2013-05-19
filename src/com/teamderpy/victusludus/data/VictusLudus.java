package com.teamderpy.victusludus.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.teamderpy.victusludus.engine.Engine;
import com.teamderpy.victusludus.game.starcluster.Cosmology;
import com.teamderpy.victusludus.game.starcluster.Planet;
import com.teamderpy.victusludus.game.starcluster.Star;
import com.teamderpy.victusludus.game.starcluster.StarDate;

// TODO: Auto-generated Javadoc
/**
 * The Class VictusLudus.
 */
public class VictusLudus {

	/** The Constant LOGGER. */
	public final static Logger LOGGER = Logger.getLogger(VictusLudus.class.getName());

	/** The resource bin which holds hashes against dynamically loaded content */
	public static ResourceBin resources = new ResourceBin();

	/** The game engine */
	public static Engine e = Engine.getInstance();

	/** Global random */
	public static Random rand;

	/**
	 * This is where we start
	 *
	 * @param args the arguments (unused for now)
	 */
	public static void main(final String[] args) {
		VictusLudus.rand = new Random();

		StarDate starDate = new StarDate(new BigInteger("23463645346"));
		Star sun = new Star(starDate, Cosmology.SOLAR_MASS.multiply(new BigDecimal("1"), Star.STELLAR_RND));

		BigDecimal delta = BigDecimal.valueOf(100000000L);

		for(int i=1; i<20; i++){
			sun.tick(delta);
			starDate.addYears(delta.toBigInteger());
		}

		Planet planet = new Planet(starDate, sun);

		for(int i=1; i<20; i++){
			sun.tick(delta);
			planet.tick(delta);
			starDate.addYears(delta.toBigInteger());
		}

		System.err.println(sun.getHistory());
		System.err.println(planet);


		//System.err.println(Cosmology.calculateEccentricAnomaly(0.5, new BigDecimal(27)).toPlainString());
		//System.err.println(Cosmology.calculateTrueAnomaly(0.5, new BigDecimal("48.43417991487915")).toPlainString());
		//System.err.println(Cosmology.calculateKeplerDistanceAtTime(sun.getMass(), planet.getMass(), planet.getOrbitSemiMajorAxis(), new BigDecimal(222222), planet.getOrbitEccentricity()).toPlainString());
		//System.err.println(Cosmology.calculateKeplerCoordinateAtTime(sun.getMass(), planet.getMass(), planet.getOrbitSemiMajorAxis(), new BigDecimal(222222), planet.getOrbitEccentricity())[0]);
		//System.err.println(Cosmology.calculateKeplerCoordinateAtTime(sun.getMass(), planet.getMass(), planet.getOrbitSemiMajorAxis(), new BigDecimal(222222), planet.getOrbitEccentricity())[1]);

		System.err.println(Cosmology.calculateOrbitalPeriod(sun.getMass(), planet.getMass(), planet.getOrbitSemiMajorAxis()));

		System.exit(0);





		VictusLudus.LOGGER.setLevel(Level.FINEST);

		try {
			FileHandler fileTxt = new FileHandler("log.txt");
			VictusLudus.LOGGER.addHandler(fileTxt);
		} catch (Exception e) {
			e.printStackTrace();
		}

		VictusLudus.e.initialize(args);
	}
}
