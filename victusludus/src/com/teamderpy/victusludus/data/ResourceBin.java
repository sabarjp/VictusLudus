
package com.teamderpy.victusludus.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.data.resources.EntityDefinition;
import com.teamderpy.victusludus.data.resources.FontFile;
import com.teamderpy.victusludus.data.resources.StarColorTuple;
import com.teamderpy.victusludus.game.PlayerBackground;
import com.teamderpy.victusludus.parts.Creature;
import com.teamderpy.victusludus.parts.Material;

/**
 * The resource bin holds a bunch of resources that we may need on demand.
 */
public class ResourceBin {

	/** The material hash. */
	private Map<String, Material> materialHash;

	/** The creature hash. */
	private Map<String, Creature> creatureHash;

	/** The font hash. */
	private Map<String, FontFile> fontHash;

	/** The backgrounds hash. */
	private Map<String, PlayerBackground> backgroundsHash;

	/** The entity hash. */
	private Map<String, EntityDefinition> entityHash;

	/** The celestial name array */
	private ArrayList<String> celestiaStarNameArray;
	private ArrayList<String> celestiaGalaxyNameArray;

	/** Mappings of color temperatures to colors */
	private ArrayList<StarColorTuple> starColorMap;

	/** TextureAtlas */
	private TextureAtlas textureAtlasCosmos;
	private TextureAtlas textureAtlasEntities;
	private TextureAtlas textureAtlasGUI;
	private TextureAtlas textureAtlasTiles;

	/**
	 * Instantiates a new resource bin.
	 */
	public ResourceBin () {
		this.materialHash = new HashMap<String, Material>();
		this.creatureHash = new HashMap<String, Creature>();
		this.fontHash = new HashMap<String, FontFile>();
		this.backgroundsHash = new HashMap<String, PlayerBackground>();
		this.entityHash = new HashMap<String, EntityDefinition>();

		this.celestiaStarNameArray = new ArrayList<String>();
		this.celestiaGalaxyNameArray = new ArrayList<String>();
		this.starColorMap = new ArrayList<StarColorTuple>();
	}

	/**
	 * Gets the material hash.
	 * 
	 * @return the material hash
	 */
	public Map<String, Material> getMaterialHash () {
		return this.materialHash;
	}

	/**
	 * Gets the creature hash.
	 * 
	 * @return the creature hash
	 */
	public Map<String, Creature> getCreatureHash () {
		return this.creatureHash;
	}

	/**
	 * Gets the font hash.
	 * 
	 * @return the font hash
	 */
	public Map<String, FontFile> getFontHash () {
		return this.fontHash;
	}

	/**
	 * Gets the backgrounds hash.
	 * 
	 * @return the backgrounds hash
	 */
	public Map<String, PlayerBackground> getBackgroundsHash () {
		return this.backgroundsHash;
	}

	/**
	 * Gets the entity hash.
	 * 
	 * @return the entity hash
	 */
	public Map<String, EntityDefinition> getEntityHash () {
		return this.entityHash;
	}

	/**
	 * Gets the celestial star name array
	 * 
	 * @return the name array
	 */
	public ArrayList<String> getCelestialStarNameArray () {
		return this.celestiaStarNameArray;
	}

	/**
	 * Gets the celestial galaxy name array
	 * 
	 * @return the name array
	 */
	public ArrayList<String> getCelestialGalaxyNameArray () {
		return this.celestiaGalaxyNameArray;
	}

	/**
	 * Gets the star color map
	 * 
	 * @return the color map
	 */
	public ArrayList<StarColorTuple> getStarColorMap () {
		return this.starColorMap;
	}

	/**
	 * Fetches the texture atlas for the cosmos
	 * 
	 * @return the texture atlas for the cosmos
	 */
	public TextureAtlas getTextureAtlasCosmos () {
		if (this.textureAtlasCosmos == null) {
			this.textureAtlasCosmos = VictusLudusGame.engine.assetManager.get(DataReader.PATH_SPRITE_SHEETS_COSMOS,
				TextureAtlas.class);
		}

		return this.textureAtlasCosmos;
	}

	/**
	 * Fetches the texture atlas for the gui
	 * 
	 * @return the texture atlas for the gui
	 */
	public TextureAtlas getTextureAtlasGUI () {
		if (this.textureAtlasGUI == null) {
			this.textureAtlasGUI = VictusLudusGame.engine.assetManager.get(DataReader.PATH_SPRITE_SHEETS_GUI, TextureAtlas.class);
		}

		return this.textureAtlasGUI;
	}

	/**
	 * Fetches the texture atlas for the tiles
	 * 
	 * @return the texture atlas for the tiles
	 */
	public TextureAtlas getTextureAtlasTiles () {
		if (this.textureAtlasTiles == null) {
			this.textureAtlasTiles = VictusLudusGame.engine.assetManager
				.get(DataReader.PATH_SPRITE_SHEETS_TILES, TextureAtlas.class);
		}

		return this.textureAtlasTiles;
	}

	/**
	 * Fetches the texture atlas for the entities
	 * 
	 * @return the texture atlas for the entities
	 */
	public TextureAtlas getTextureAtlasEntities () {
		if (this.textureAtlasEntities == null) {
			this.textureAtlasEntities = VictusLudusGame.engine.assetManager.get(DataReader.PATH_SPRITE_SHEETS_ENTITIES,
				TextureAtlas.class);
		}

		return this.textureAtlasEntities;
	}

	/**
	 * Fetches a sound
	 * 
	 * @param path the asset path to the sound
	 * @return the sound
	 */
	public Sound getSound (final String path) {
		return VictusLudusGame.engine.assetManager.get(path, Sound.class);
	}
}
