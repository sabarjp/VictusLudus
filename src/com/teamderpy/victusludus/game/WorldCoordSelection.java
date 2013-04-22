package com.teamderpy.victusludus.game;

// TODO: Auto-generated Javadoc
/**
 * The Class WorldCoordSelection.
 */
public class WorldCoordSelection {
	
	/** The x. */
	public int x;
	
	/** The y. */
	public int y;
	
	/* sub-section */
	/** The wall sub section facing. */
	public EnumWallFacing wallSubSectionFacing = EnumWallFacing.NONE;
	
	/**
	 * Instantiates a new world coord selection.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public WorldCoordSelection(int x, int y){
		this.x = x;
		this.y = y;
	}
}
