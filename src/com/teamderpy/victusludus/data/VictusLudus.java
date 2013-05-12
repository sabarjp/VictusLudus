package com.teamderpy.victusludus.data;

import java.math.BigDecimal;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.teamderpy.victusludus.engine.Engine;
import com.teamderpy.victusludus.game.starcluster.Star;

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


		Star sun = new Star(Star.SOLAR_MASS.multiply(new BigDecimal("14"), Star.STELLAR_RND));

		System.err.println(sun);

		for(int i=1; i<150000; i++){
			sun.tick(BigDecimal.valueOf(1000000000L));
			System.err.println(sun);
		}

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
