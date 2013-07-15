
package com.teamderpy.victusludus.data;

import java.util.ArrayList;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.VictusRuntimeException;
import com.teamderpy.victusludus.data.resources.StarColorTuple;
import com.teamderpy.victusludus.readerwriter.EntityReader;
import com.teamderpy.victusludus.readerwriter.FontReader;
import com.teamderpy.victusludus.readerwriter.IObjectReader;
import com.teamderpy.victusludus.readerwriter.ISimpleReader;
import com.teamderpy.victusludus.readerwriter.LineReader;

public class DataLoader {
	public static final String PATH_MATERIALS = "material/";

	public static final String PATH_3D = "meshes/";

	public static final String PATH_CREATURES = "creatures/";

	public static final String PATH_FONTS = "fonts/";

	public static final String PATH_BACKGROUNDS = "backgrounds/";

	public static final String PATH_ENTITIES = "data/entities/";

	public static final String PATH_SOUNDS = "sounds/";
	public static final String PATH_MUSIC = "music/";

	public static final String STAR_NAMES = "data/star_names/";
	public static final String GALAXY_NAMES = "data/galaxy_names/";

	public static final String STAR_COLOR_CHART = "sprites/spectrum_starcolor.png";

	public static final String PATH_SPRITE_SHEETS_COSMOS = "sprites/spritesheets/cosmos_spritesheet.atlas";
	public static final String PATH_SPRITE_SHEETS_ENTITIES = "sprites/spritesheets/entities_spritesheet.atlas";
	public static final String PATH_SPRITE_SHEETS_GUI = "sprites/spritesheets/gui_spritesheet.atlas";
	public static final String PATH_SPRITE_SHEETS_TILES = "sprites/spritesheets/tiles_spritesheet.atlas";

	/** Pre-loads a bunch of data */
	public static void PreLoad () {
		DataLoader.ReadAndLoadAll(DataLoader.PATH_FONTS, VictusLudusGame.resources.getFontHash(), new FontReader());

		DataLoader.LoadSpriteSheets(DataLoader.PATH_SPRITE_SHEETS_COSMOS);
		DataLoader.LoadSpriteSheets(DataLoader.PATH_SPRITE_SHEETS_ENTITIES);
		DataLoader.LoadSpriteSheets(DataLoader.PATH_SPRITE_SHEETS_GUI);
		DataLoader.LoadSpriteSheets(DataLoader.PATH_SPRITE_SHEETS_TILES);

		DataLoader.LoadSounds(DataLoader.PATH_SOUNDS);
		DataLoader.LoadMusic(DataLoader.PATH_MUSIC);

		DataLoader.Load3DMeshes(DataLoader.PATH_3D);
	}

	/** Loads data that may be dependent on the pre-load */
	public static void PostLoad () {
		DataLoader.ReadAndLoadAll(DataLoader.PATH_ENTITIES, VictusLudusGame.resources.getEntityHash(), new EntityReader());

		DataLoader
			.SimpleReadAndLoad(DataLoader.STAR_NAMES, VictusLudusGame.resources.getCelestialStarNameArray(), new LineReader());
		DataLoader.SimpleReadAndLoad(DataLoader.GALAXY_NAMES, VictusLudusGame.resources.getCelestialGalaxyNameArray(),
			new LineReader());
		DataLoader.ReadStarColorChart(DataLoader.STAR_COLOR_CHART, VictusLudusGame.resources.getStarColorMap());
	}

	/**
	 * Loads the 3d meshes located at path
	 * 
	 * @param path
	 */
	private static void Load3DMeshes (final String path) {
		AssetManager assetManager = VictusLudusGame.engine.assetManager;

		FileHandle meshFolder = VFile.getFileHandle(path);
		FileHandle[] files = VFile.getFiles(meshFolder);

		for (FileHandle file : files) {
			if (file.exists()
				&& (file.extension().toLowerCase().equals("obj") || file.extension().toLowerCase().equals("g3db") || file.extension()
					.toLowerCase().equals("g3dj"))) {
				assetManager.load(file.path(), Model.class);
				System.out.println("queueing " + file.path());
			}
		}
	}

	/**
	 * Loads the sprite sheets located at path
	 * 
	 * @param path
	 */
	private static void LoadSpriteSheets (final String path) {
		AssetManager assetManager = VictusLudusGame.engine.assetManager;

		assetManager.load(path, TextureAtlas.class);
		System.out.println("queueing " + path);
	}

	/**
	 * Loads the sounds located at path
	 * 
	 * @param path
	 */
	private static void LoadSounds (final String path) {
		AssetManager assetManager = VictusLudusGame.engine.assetManager;

		FileHandle soundFolder = VFile.getFileHandle(path);
		FileHandle[] files = VFile.getFiles(soundFolder);

		for (FileHandle file : files) {
			if (file.exists() && file.extension().toLowerCase().equals("wav")) {
				assetManager.load(file.path(), Sound.class);
				System.out.println("queueing " + file.path());
			}
		}
	}

	/**
	 * Loads the music located at path
	 * 
	 * @param path
	 */
	private static void LoadMusic (final String path) {
		AssetManager assetManager = VictusLudusGame.engine.assetManager;

		FileHandle musicFolder = VFile.getFileHandle(path);
		FileHandle[] files = VFile.getFiles(musicFolder);

		for (FileHandle file : files) {
			if (file.exists() && file.extension().toLowerCase().equals("wav")) {
				assetManager.load(file.path(), Music.class);
				System.out.println("queueing " + file.path());
			}
		}
	}

	/**
	 * Reads the star color chart and loads them into a hash
	 * 
	 * @param starColorChart
	 * @param starColorMap
	 */
	private static void ReadStarColorChart (final String starColorChart, final ArrayList<StarColorTuple> starColorMap) {
		int minColor = 0;
		int maxColor = 33000;

		Texture starColorImage = new Texture(VFile.getFileHandle(starColorChart));

		starColorImage.getTextureData().prepare();
		Pixmap pixelData = starColorImage.getTextureData().consumePixmap();

		for (int i = 1; i < starColorImage.getWidth(); i += 5) {
			Color color = new Color();
			Color.rgba8888ToColor(color, pixelData.getPixel(starColorImage.getWidth() - i, 0));
			color.a = 1.0F;

			StarColorTuple tuple = new StarColorTuple(
				(int)((float)(starColorImage.getWidth() - i) / starColorImage.getWidth() * (maxColor - minColor)), color);
			// System.err.println("added tuple: " + tuple.getTemperature() + " " +
// tuple.getColor());
			starColorMap.add(tuple);
		}

		Color color = new Color();
		Color.rgba8888ToColor(color, pixelData.getPixel(starColorImage.getWidth(), 0));
		color.a = 1.0F;
		starColorMap.add(new StarColorTuple(Integer.MIN_VALUE, color));

		starColorImage.dispose();
	}

	/**
	 * Read and load all files into a resource hash
	 * 
	 * @param <T> the generic type
	 * @param path the path
	 * @param hash the hash
	 * @param reader the reader
	 */
	private static <T> void ReadAndLoadAll (final String path, final Map<String, T> hash, final IObjectReader reader) {
		FileHandle folder = VFile.getFileHandle(path);

		FileHandle[] listOfFiles = VFile.getFiles(folder);

		for (FileHandle f : listOfFiles) {
			if (f.exists()) {
				reader.ReadAndLoad(f, hash);

				if (VictusLudusGame.engine.IS_DEBUGGING) {
					Gdx.app.log("info", "loaded " + f.path());
				}
			} else {
				Gdx.app.log("severe", "<ERROR> Cannot read file [" + f.path() + "]");
				throw new VictusRuntimeException("Could not read resource folder for " + path);
			}
		}
	}

	/**
	 * Read line items into an array list
	 * 
	 * @param path the path of the file
	 * @param array the arraylist to populate
	 */
	private static void SimpleReadAndLoad (final String path, final ArrayList<String> array, final ISimpleReader reader) {
		FileHandle folder = VFile.getFileHandle(path);

		FileHandle[] listOfFiles = VFile.getFiles(folder);

		for (FileHandle f : listOfFiles) {
			if (f.exists()) {
				reader.ReadAndLoad(f, array);

				if (VictusLudusGame.engine.IS_DEBUGGING) {
					Gdx.app.log("info", "loaded " + f.path());
				}
			} else {
				Gdx.app.log("severe", "<ERROR> Cannot read file [" + f.path() + "]");
				throw new VictusRuntimeException("Could not read resource folder for " + path);
			}

		}

	}
}
