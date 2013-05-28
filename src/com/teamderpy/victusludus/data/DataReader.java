package com.teamderpy.victusludus.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.teamderpy.victusludus.data.resources.StarColorTuple;
import com.teamderpy.victusludus.readerwriter.BackgroundReader;
import com.teamderpy.victusludus.readerwriter.CreatureReader;
import com.teamderpy.victusludus.readerwriter.EntityReader;
import com.teamderpy.victusludus.readerwriter.FontReader;
import com.teamderpy.victusludus.readerwriter.IObjectReader;
import com.teamderpy.victusludus.readerwriter.ISimpleReader;
import com.teamderpy.victusludus.readerwriter.LineReader;
import com.teamderpy.victusludus.readerwriter.MaterialReader;



// TODO: Auto-generated Javadoc
/**
 * The Class DataReader.
 */
public class DataReader {

	/** The Constant PATH_MATERIALS. */
	public static final String PATH_MATERIALS = "res/material/";

	/** The Constant PATH_CREATURES. */
	public static final String PATH_CREATURES = "res/creatures/";

	/** The Constant PATH_FONTS. */
	public static final String PATH_FONTS = "res/fonts/";

	/** The Constant PATH_BACKGROUNDS. */
	public static final String PATH_BACKGROUNDS = "res/backgrounds/";

	/** The Constant PATH_ENTITIES. */
	public static final String PATH_ENTITIES = "res/entities/";

	/** The Constant CELESITAL_NAMES. */
	public static final String CELESITAL_NAMES = "res/celestial/";

	/** The Constant STAR_COLOR_CHART. */
	public static final String STAR_COLOR_CHART = "res/sprites/starcolor.png";

	/**
	 * Read data.
	 */
	public static void ReadData() {
		DataReader.ReadAndLoadAll(DataReader.PATH_FONTS, VictusLudus.resources.getFontHash(), new FontReader());
		DataReader.ReadAndLoadAll(DataReader.PATH_CREATURES, VictusLudus.resources.getCreatureHash(), new CreatureReader());
		DataReader.ReadAndLoadAll(DataReader.PATH_MATERIALS, VictusLudus.resources.getMaterialHash(), new MaterialReader());
		DataReader.ReadAndLoadAll(DataReader.PATH_BACKGROUNDS, VictusLudus.resources.getBackgroundsHash(), new BackgroundReader());
		DataReader.ReadAndLoadAll(DataReader.PATH_ENTITIES, VictusLudus.resources.getEntityHash(), new EntityReader());

		DataReader.SimpleReadAndLoad(DataReader.CELESITAL_NAMES, VictusLudus.resources.getCelestialNameArray(), new LineReader());
		DataReader.ReadStarColorChart(DataReader.STAR_COLOR_CHART, VictusLudus.resources.getStarColorMap());
	}

	/**
	 * Reads the star color chart and loads them into a hash
	 * 
	 * @param starColorChart
	 * @param starColorMap
	 */
	private static void ReadStarColorChart(final String starColorChart, final ArrayList<StarColorTuple> starColorMap) {
		int minColor = 0;
		int maxColor = 33000;

		try {
			Image starColorImage = new Image(starColorChart, false, Image.FILTER_NEAREST, Color.magenta).getFlippedCopy(true, false);

			for(int i=0; i<starColorImage.getWidth(); i += 5){
				StarColorTuple tuple = new StarColorTuple((int) ((float)(starColorImage.getWidth()-i)/(float)starColorImage.getWidth()*(maxColor-minColor)), starColorImage.getColor(i, 0));
				starColorMap.add(tuple);
			}

			starColorMap.add(new StarColorTuple(Integer.MIN_VALUE, starColorImage.getFlippedCopy(true, false).getColor(0, 0)));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read and load all files into a resource hash
	 *
	 * @param <T> the generic type
	 * @param path the path
	 * @param hash the hash
	 * @param reader the reader
	 */
	private static <T> void ReadAndLoadAll(final String path, final Map<String, T> hash, final IObjectReader reader) {
		File folder = new File(path);

		if (folder.exists() && folder.canRead()) {
			File[] listOfFiles = folder.listFiles();

			for (File f : listOfFiles) {
				if (!f.isDirectory()) {
					if (f.exists() && f.canRead()) {
						reader.ReadAndLoad(f.getAbsolutePath(), hash);

						if (VictusLudus.e.IS_DEBUGGING) {
							VictusLudus.LOGGER.info("loaded " + f.getPath());
						}
					} else {
						VictusLudus.LOGGER.severe("ERROR: Cannot read file [" + f.getAbsolutePath() + "]");
					}
				}
			}
		} else {
			VictusLudus.LOGGER.severe("ERROR: Folder does not exist [" + path + "]");
		}
	}

	/**
	 * Read line items into an array list
	 * 
	 * @param path the path of the file
	 * @param array the arraylist to populate
	 */
	private static void SimpleReadAndLoad(final String path, final ArrayList<String> array, final ISimpleReader reader){
		File folder = new File(path);

		if (folder.exists() && folder.canRead()) {
			File[] listOfFiles = folder.listFiles();

			for (File f : listOfFiles) {
				if (!f.isDirectory()) {
					if (f.exists() && f.canRead()) {
						reader.ReadAndLoad(f.getAbsolutePath(), array);

						if (VictusLudus.e.IS_DEBUGGING) {
							VictusLudus.LOGGER.info("loaded " + f.getPath());
						}
					} else {
						VictusLudus.LOGGER.severe("ERROR: Cannot read file [" + f.getAbsolutePath() + "]");
					}
				}
			}
		} else {
			VictusLudus.LOGGER.severe("ERROR: Folder does not exist [" + path + "]");
		}
	}
}
