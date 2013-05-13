package com.teamderpy.victusludus.data;

import java.math.BigInteger;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.teamderpy.victusludus.engine.Engine;
import com.teamderpy.victusludus.game.starcluster.Planet;
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
		//Star sun = new Star(starDate, Cosmology.SOLAR_MASS.multiply(new BigDecimal("150"), Star.STELLAR_RND));


		Planet planet = new Planet(starDate);
		System.err.println(planet);

		//System.err.println(sun);

		for(int i=1; i<2000; i++){
			//sun.tick(BigDecimal.valueOf(100000000L));
			starDate.addYears(new BigInteger("100000000"));

			//System.err.println(sun);
		}

		//System.err.println(sun.getHistory());

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
