package com.teamderpy.victusludus.data.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

import com.teamderpy.victusludus.game.EnumBuildMode;
import com.teamderpy.victusludus.game.EnumFlags;
import com.teamderpy.victusludus.game.entity.GameEntity;
import com.teamderpy.victusludus.game.entity.behavior.EntityBehavior;
import com.teamderpy.victusludus.game.renderer.BitmapHandler;
import com.teamderpy.victusludus.game.renderer.Light;

// TODO: Auto-generated Javadoc
/**
 * The Class Entity which is loaded and populated based on mod files
 */
public class EntityDefinition {

	/** The id. */
	private String id;

	/** The name. */
	private String name;

	/** The parent button node. */
	private String parentButtonNode;

	/** The button sprite sheet. */
	private SpriteSheet buttonSpriteSheet;

	/** The build mode. */
	private EnumBuildMode buildMode;

	/** The animation hash. */
	private HashMap<String, Animation> animationHash;

	/** The flag set. */
	private HashSet<EnumFlags> flagSet;

	/** The behavior list. */
	private ArrayList<EntityBehavior> behaviorList;

	/** The light. */
	private Light light;

	/** Height in block units */
	private int height;

	/**
	 * Instantiates a new entity.
	 */
	public EntityDefinition(){
		this.buttonSpriteSheet = null;

		this.behaviorList = null;

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
	public String getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	//public void loadEntitySpriteSheet(final String path){
	//	this.entitySpriteSheet = BitmapHandler.LoadSpriteSheet("res/" + path, 16, 1);
	//}

	/**
	 * Load button sprite sheet.
	 *
	 * @param path the path
	 */
	public void loadButtonSpriteSheet(final String path){
		this.buttonSpriteSheet = BitmapHandler.LoadSpriteSheet("res/" + path, 4, 1);
	}

	/**
	 * Gets the parent button node.
	 *
	 * @return the parent button node
	 */
	public String getParentButtonNode() {
		return this.parentButtonNode;
	}

	/**
	 * Sets the parent button node.
	 *
	 * @param parentButtonNode the new parent button node
	 */
	public void setParentButtonNode(final String parentButtonNode) {
		this.parentButtonNode = parentButtonNode;
	}

	/**
	 * Gets the button sprite sheet.
	 *
	 * @return the button sprite sheet
	 */
	public SpriteSheet getButtonSpriteSheet() {
		return this.buttonSpriteSheet;
	}

	/**
	 * Gets the behavior list.
	 *
	 * @return the behavior list
	 */
	public ArrayList<EntityBehavior> getBehaviorList() {
		return this.behaviorList;
	}

	/**
	 * Sets the behavior list.
	 *
	 * @param behaviorList the new behavior list
	 */
	public void setBehaviorList(final ArrayList<EntityBehavior> behaviorList) {
		this.behaviorList = behaviorList;
	}

	/**
	 * Tick.
	 *
	 * @param ge the ge
	 */
	public void tick(final GameEntity ge){
		if(this.behaviorList != null){
			for(EntityBehavior eb:this.behaviorList){
				eb.tick(ge);
			}
		}
	}

	/**
	 * Gets the animation hash.
	 *
	 * @return the animation hash
	 */
	public HashMap<String, Animation> getAnimationHash() {
		return this.animationHash;
	}

	/**
	 * Sets the animation hash.
	 *
	 * @param animationHash the animation hash
	 */
	public void setAnimationHash(final HashMap<String, Animation> animationHash) {
		this.animationHash = animationHash;
	}

	/**
	 * Gets the builds the mode.
	 *
	 * @return the builds the mode
	 */
	public EnumBuildMode getBuildMode() {
		return this.buildMode;
	}

	/**
	 * Sets the builds the mode.
	 *
	 * @param buildMode the new builds the mode
	 */
	public void setBuildMode(final EnumBuildMode buildMode) {
		this.buildMode = buildMode;
	}

	/**
	 * Sets the builds the mode.
	 *
	 * @param buildMode the new builds the mode
	 */
	public void setBuildMode(final String buildMode){
		if(buildMode.equalsIgnoreCase("line")){
			this.buildMode = EnumBuildMode.LINE;
		} else if(buildMode.equalsIgnoreCase("single")){
			this.buildMode = EnumBuildMode.SINGLE;
		} else {
			this.buildMode = EnumBuildMode.SINGLE;
		}
	}

	/**
	 * Gets the light.
	 *
	 * @return the light
	 */
	public Light getLight() {
		return this.light;
	}

	/**
	 * Sets the light.
	 *
	 * @param light the new light
	 */
	public void setLight(final Light light) {
		this.light = light;
	}

	/**
	 * Gets the flag set.
	 *
	 * @return the flag set
	 */
	public HashSet<EnumFlags> getFlagSet() {
		return this.flagSet;
	}

	/**
	 * Sets the flag set.
	 *
	 * @param flagSet the new flag set
	 */
	public void setFlagSet(final HashSet<EnumFlags> flagSet) {
		this.flagSet = flagSet;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj){
		return this.getId().equals(((EntityDefinition)obj).getId());
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(final int height) {
		this.height = height;
	}
}
