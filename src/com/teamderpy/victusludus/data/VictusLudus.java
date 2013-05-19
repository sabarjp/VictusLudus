package com.teamderpy.victusludus.data;

import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.teamderpy.victusludus.engine.Engine;

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
