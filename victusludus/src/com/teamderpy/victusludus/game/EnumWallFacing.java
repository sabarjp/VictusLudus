package com.teamderpy.victusludus.game;


/**
 * The Enum EnumWallFacing.
 */
public enum EnumWallFacing {
	
	/** The none. */
	NONE(-1),
	
	/** The north east. */
	NORTH_EAST(0),
	
	/** The north west. */
	NORTH_WEST(1),
	
	/** The south west. */
	SOUTH_WEST(2),
	
	/** The south east. */
	SOUTH_EAST(3);
	
	/** The sprite sheet mod. */
	private int spriteSheetMod;
	
	/**
	 * Instantiates a new enum wall facing.
	 *
	 * @param spriteSheetMod the sprite sheet mod
	 */
	private EnumWallFacing(int spriteSheetMod){
		this.spriteSheetMod = spriteSheetMod;
	}

	/**
	 * Gets the sprite sheet mod.
	 *
	 * @return the sprite sheet mod
	 */
	public int getSpriteSheetMod() {
		return spriteSheetMod;
	}
}
