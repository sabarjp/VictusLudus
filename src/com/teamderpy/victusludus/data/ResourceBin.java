package com.teamderpy.victusludus.data;

import java.util.HashMap;
import java.util.Map;

import com.teamderpy.victusludus.data.resources.EntityDefinition;
import com.teamderpy.victusludus.data.resources.FontFile;
import com.teamderpy.victusludus.game.PlayerBackground;
import com.teamderpy.victusludus.parts.Creature;
import com.teamderpy.victusludus.parts.Material;

// TODO: Auto-generated Javadoc
/**
 * The Class ResourceBin.
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

	/**
	 * Instantiates a new resource bin.
	 */
	public ResourceBin(){
		this.materialHash 	  = new HashMap<String, Material>();
		this.creatureHash     = new HashMap<String, Creature>();
		this.fontHash         = new HashMap<String, FontFile>();
		this.backgroundsHash  = new HashMap<String, PlayerBackground>();
		this.entityHash       = new HashMap<String, EntityDefinition>();
	}

	/**
	 * Gets the material hash.
	 *
	 * @return the material hash
	 */
	public Map<String, Material> getMaterialHash() {
		return this.materialHash;
	}

	/**
	 * Gets the creature hash.
	 *
	 * @return the creature hash
	 */
	public Map<String, Creature> getCreatureHash() {
		return this.creatureHash;
	}

	/**
	 * Gets the font hash.
	 *
	 * @return the font hash
	 */
	public Map<String, FontFile> getFontHash() {
		return this.fontHash;
	}

	/**
	 * Gets the backgrounds hash.
	 *
	 * @return the backgrounds hash
	 */
	public Map<String, PlayerBackground> getBackgroundsHash() {
		return this.backgroundsHash;
	}

	/**
	 * Gets the entity hash.
	 *
	 * @return the entity hash
	 */
	public Map<String, EntityDefinition> getEntityHash() {
		return this.entityHash;
	}
}