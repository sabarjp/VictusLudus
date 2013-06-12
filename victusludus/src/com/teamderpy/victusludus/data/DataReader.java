package com.teamderpy.victusludus.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.VictusRuntimeException;
import com.teamderpy.victusludus.data.resources.StarColorTuple;
import com.teamderpy.victusludus.readerwriter.BackgroundReader;
import com.teamderpy.victusludus.readerwriter.CreatureReader;
import com.teamderpy.victusludus.readerwriter.EntityReader;
import com.teamderpy.victusludus.readerwriter.FontReader;
import com.teamderpy.victusludus.readerwriter.IObjectReader;
import com.teamderpy.victusludus.readerwriter.ISimpleReader;
import com.teamderpy.victusludus.readerwriter.LineReader;
import com.teamderpy.victusludus.readerwriter.MaterialReader;


public class DataReader {
	public static final String PATH_MATERIALS = "material/";

	public static final String PATH_CREATURES = "creatures/";

	public static final String PATH_FONTS = "fonts/";

	public static final String PATH_BACKGROUNDS = "backgrounds/";

	public static final String PATH_ENTITIES = "entities/";
	
	public static final String PATH_SOUNDS = "sounds/";

	public static final String STAR_NAMES = "celestial/star_names";
	public static final String GALAXY_NAMES = "celestial/galaxy_names";

	public static final String STAR_COLOR_CHART = "sprites/spectrum_starcolor.png";
	
	public static final String PATH_SPRITE_SHEETS_COSMOS = "sprites/spritesheets/cosmos_spritesheet.atlas";
	public static final String PATH_SPRITE_SHEETS_ENTITIES = "sprites/spritesheets/entities_spritesheet.atlas";
	public static final String PATH_SPRITE_SHEETS_GUI = "sprites/spritesheets/gui_spritesheet.atlas";
	public static final String PATH_SPRITE_SHEETS_TILES = "sprites/spritesheets/tiles_spritesheet.atlas";

	/**
	 * Read data.
	 */
	public static void ReadData() {
		DataReader.ReadAndLoadAll(DataReader.PATH_FONTS, VictusLudusGame.resources.getFontHash(), new FontReader());
		
		//DataReader.ReadAndLoadAll(DataReader.PATH_CREATURES, VictusLudus.resources.getCreatureHash(), new CreatureReader());
		//DataReader.ReadAndLoadAll(DataReader.PATH_MATERIALS, VictusLudus.resources.getMaterialHash(), new MaterialReader());
		//DataReader.ReadAndLoadAll(DataReader.PATH_BACKGROUNDS, VictusLudus.resources.getBackgroundsHash(), new BackgroundReader());
		//DataReader.ReadAndLoadAll(DataReader.PATH_ENTITIES, VictusLudus.resources.getEntityHash(), new EntityReader());

		//DataReader.SimpleReadAndLoad(DataReader.STAR_NAMES, VictusLudus.resources.getCelestialStarNameArray(), new LineReader());
		//DataReader.SimpleReadAndLoad(DataReader.GALAXY_NAMES, VictusLudus.resources.getCelestialGalaxyNameArray(), new LineReader());
		//DataReader.ReadStarColorChart(DataReader.STAR_COLOR_CHART, VictusLudus.resources.getStarColorMap());
		
		DataReader.LoadSpriteSheets(DataReader.PATH_SPRITE_SHEETS_COSMOS);
		DataReader.LoadSpriteSheets(DataReader.PATH_SPRITE_SHEETS_ENTITIES);
		DataReader.LoadSpriteSheets(DataReader.PATH_SPRITE_SHEETS_GUI);
		DataReader.LoadSpriteSheets(DataReader.PATH_SPRITE_SHEETS_TILES);
		
		DataReader.LoadSounds(DataReader.PATH_SOUNDS);
	}
	
	/**
	 * Loads the sprite sheets located at path
	 * 
	 * @param path
	 */
	private static void LoadSpriteSheets(String path){
		AssetManager assetManager = VictusLudusGame.engine.assetManager;
		
		assetManager.load(path, TextureAtlas.class);
	}
	
	/**
	 * Loads the sounds located at path
	 * 
	 * @param path
	 */
	private static void LoadSounds(String path){
		AssetManager assetManager = VictusLudusGame.engine.assetManager;
		
		FileHandle soundFolder = VFile.getFileHandle(path);
		FileHandle[] files = VFile.getFiles(soundFolder);
		
		for(FileHandle file: files) {
		   if(file.exists() && file.extension().toLowerCase().equals("wav")){
		   	assetManager.load(file.path(), Sound.class);
		   	System.err.println("loaded sound");
		   }
		}
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

		Sprite starColorImage = new Sprite(new Texture(VFile.getFileHandle(starColorChart)));
		starColorImage.flip(true, false);
		
		starColorImage.getTexture().getTextureData().prepare();
		Pixmap pixelData = starColorImage.getTexture().getTextureData().consumePixmap();

		Color color = new Color();
		
		for(int i=0; i<starColorImage.getWidth(); i += 5){
			Color.rgba8888ToColor(color, pixelData.getPixel(i, 0));
			StarColorTuple tuple = new StarColorTuple((int) ((float)(starColorImage.getWidth()-i)/(float)starColorImage.getWidth()*(maxColor-minColor)), color);
			starColorMap.add(tuple);
		}

		Color.rgba8888ToColor(color, pixelData.getPixel((int)starColorImage.getWidth(), 0));
		starColorMap.add(new StarColorTuple(Integer.MIN_VALUE, color));
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
		FileHandle folder = VFile.getFileHandle(path);

		FileHandle[] listOfFiles = VFile.getFiles(folder);

		for (FileHandle f : listOfFiles) {
			if (f.exists()) {
				reader.ReadAndLoad(f.path(), hash);

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
	private static void SimpleReadAndLoad(final String path, final ArrayList<String> array, final ISimpleReader reader){
		File folder = new File(path);

		if (folder.exists() && folder.canRead()) {
			File[] listOfFiles = folder.listFiles();

			for (File f : listOfFiles) {
				if (!f.isDirectory()) {
					if (f.exists() && f.canRead()) {
						reader.ReadAndLoad(f.getAbsolutePath(), array);

						if (VictusLudusGame.engine.IS_DEBUGGING) {
							Gdx.app.log("info", "loaded " + f.getPath());
						}
					} else {
						Gdx.app.log("severe", "<ERROR> Cannot read file [" + f.getAbsolutePath() + "]");
						throw new VictusRuntimeException("Could not read resource folder for " + path);
					}
				}
			}
		} else {
			Gdx.app.log("severe", "<ERROR> Folder does not exist [" + path + "]");
			throw new VictusRuntimeException("Could not locate resource folder for " + path);
		}
	}
}
