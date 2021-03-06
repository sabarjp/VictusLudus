
package com.teamderpy.victusludus.game.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.teamderpy.victusludus.game.EnumBuildMode;
import com.teamderpy.victusludus.game.EnumFlags;
import com.teamderpy.victusludus.game.entity.behavior.EntityBehavior;
import com.teamderpy.victusludus.game.map.Map;

/**
 * The Class Entity which is loaded and populated based on mod files
 */
public class GameEntity {
	/** The id. */
	private String id;

	/** The name. */
	private String name;

	/** Whether this entity is a billboard or not */
	private boolean isBillboard;

	/** The parent button node. */
	private String parentButtonNode;

	/** The button sprite sheet. */
	// private SpriteSheet buttonSpriteSheet;

	/** The build mode. */
	private EnumBuildMode buildMode;

	/** The animation hash. */
	private HashMap<String, Animation> animationHash;

	/** The flag set. */
	private HashSet<EnumFlags> flagSet;

	/** The behavior list. */
	private ArrayList<EntityBehavior> behaviorList;

	/** Height in block units */
	private int height;

	/** width in block units */
	private int width;

	/**
	 * Instantiates a new entity.
	 */
	public GameEntity () {
		// this.buttonSpriteSheet = null;

		this.behaviorList = null;
		this.isBillboard = false;

		this.name = "";
		this.setBuildMode(EnumBuildMode.SINGLE);
		this.parentButtonNode = "root";

		this.setAnimationHash(new HashMap<String, Animation>());
		this.setFlagSet(new HashSet<EnumFlags>());
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId () {
		return this.id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id the new id
	 */
	public void setId (final String id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName () {
		return this.name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name the new name
	 */
	public void setName (final String name) {
		this.name = name;
	}

	// public void loadEntitySpriteSheet(final String path){
	// this.entitySpriteSheet = BitmapHandler.LoadSpriteSheet("res/" + path, 16,
// 1);
	// }

	/**
	 * Load button sprite sheet.
	 * 
	 * @param path the path
	 */
	public void loadButtonSpriteSheet (final String path) {
		// this.buttonSpriteSheet = BitmapHandler.LoadSpriteSheet("res/" + path,
// 4, 1);
	}

	/**
	 * Gets the parent button node.
	 * 
	 * @return the parent button node
	 */
	public String getParentButtonNode () {
		return this.parentButtonNode;
	}

	/**
	 * Sets the parent button node.
	 * 
	 * @param parentButtonNode the new parent button node
	 */
	public void setParentButtonNode (final String parentButtonNode) {
		this.parentButtonNode = parentButtonNode;
	}

	/**
	 * Gets the button sprite sheet.
	 * 
	 * @return the button sprite sheet
	 */
	// public SpriteSheet getButtonSpriteSheet () {
	// return this.buttonSpriteSheet;
	// }

	/**
	 * Gets the behavior list.
	 * 
	 * @return the behavior list
	 */
	public ArrayList<EntityBehavior> getBehaviorList () {
		return this.behaviorList;
	}

	/**
	 * Sets the behavior list.
	 * 
	 * @param behaviorList the new behavior list
	 */
	public void setBehaviorList (final ArrayList<EntityBehavior> behaviorList) {
		this.behaviorList = behaviorList;
	}

	/**
	 * Tick.
	 * 
	 * @param ge the GameEntity to tick
	 * @param map the map to tick against
	 */
	public void tick (final GameEntityInstance ge, final Map map) {
		if (this.behaviorList != null) {
			for (EntityBehavior eb : this.behaviorList) {
				eb.tick(ge, map);
			}
		}
	}

	/**
	 * Gets the animation hash.
	 * 
	 * @return the animation hash
	 */
	public HashMap<String, Animation> getAnimationHash () {
		return this.animationHash;
	}

	/**
	 * Sets the animation hash.
	 * 
	 * @param animationHash the animation hash
	 */
	public void setAnimationHash (final HashMap<String, Animation> animationHash) {
		this.animationHash = animationHash;
	}

	/**
	 * Gets the builds the mode.
	 * 
	 * @return the builds the mode
	 */
	public EnumBuildMode getBuildMode () {
		return this.buildMode;
	}

	/**
	 * Sets the builds the mode.
	 * 
	 * @param buildMode the new builds the mode
	 */
	public void setBuildMode (final EnumBuildMode buildMode) {
		this.buildMode = buildMode;
	}

	/**
	 * Sets the builds the mode.
	 * 
	 * @param buildMode the new builds the mode
	 */
	public void setBuildMode (final String buildMode) {
		if (buildMode.equalsIgnoreCase("line")) {
			this.buildMode = EnumBuildMode.LINE;
		} else if (buildMode.equalsIgnoreCase("single")) {
			this.buildMode = EnumBuildMode.SINGLE;
		} else {
			this.buildMode = EnumBuildMode.SINGLE;
		}
	}

	/**
	 * Gets the flag set.
	 * 
	 * @return the flag set
	 */
	public HashSet<EnumFlags> getFlagSet () {
		return this.flagSet;
	}

	/**
	 * Sets the flag set.
	 * 
	 * @param flagSet the new flag set
	 */
	public void setFlagSet (final HashSet<EnumFlags> flagSet) {
		this.flagSet = flagSet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals (final Object obj) {
		return this.getId().equals(((GameEntity)obj).getId());
	}

	public int getHeight () {
		return this.height;
	}

	public void setHeight (final int height) {
		this.height = height;
	}

	public int getWidth () {
		return this.width;
	}

	public void setWidth (final int width) {
		this.width = width;
	}

	public boolean isBillboard () {
		return this.isBillboard;
	}

	public void setBillboard (final boolean isBillboard) {
		this.isBillboard = isBillboard;
	}
}
