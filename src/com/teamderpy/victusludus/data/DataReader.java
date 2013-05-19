package com.teamderpy.victusludus.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

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
