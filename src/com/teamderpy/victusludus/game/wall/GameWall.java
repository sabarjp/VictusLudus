package com.teamderpy.victusludus.game.wall;

import com.teamderpy.victusludus.engine.graphics.BitmapHandler;
import com.teamderpy.victusludus.game.EnumWallFacing;
import com.teamderpy.victusludus.game.WorldCoord;

// TODO: Auto-generated Javadoc
/**
 * The Class GameWall.
 */
public class GameWall {
	
	/** The Constant ID_GRID. */
	public static final byte ID_GRID           = 0x0;
	
	/** The Constant ID_DRYWALL. */
	public static final byte ID_DRYWALL        = 0x2;
	
	/** The Constant ID_BRICK. */
	public static final byte ID_BRICK          = 0x4;

	/** The id. */
	private int id;
	
	/** The facing. */
	private EnumWallFacing facing;

	/** The pos. */
	private WorldCoord pos = new WorldCoord(-1, -1, -1);

	/**
	 * Instantiates a new game wall.
	 *
	 * @param id the id
	 */
	public GameWall(final int id){
		this.id = id;
		this.setFacing(EnumWallFacing.NORTH_EAST);
	}

	/**
	 * Instantiates a new game wall.
	 *
	 * @param id the id
	 * @param facing the facing
	 */
	public GameWall(final int id, final EnumWallFacing facing){
		this.id = id;
		this.setFacing(facing);
	}

	/**
	 * Instantiates a new game wall.
	 *
	 * @param id the id
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public GameWall(final int id, final int x, final int y, final int z){
		this.id = id;
		this.setFacing(EnumWallFacing.NORTH_EAST);

		this.pos = new WorldCoord(x, y, z);
	}

	/**
	 * Instantiates a new game wall.
	 *
	 * @param id the id
	 * @param facing the facing
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public GameWall(final int id, final EnumWallFacing facing, final int x, final int y, final int z){
		this.id = id;
		this.setFacing(facing);

		this.pos = new WorldCoord(x, y, z);
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * Gets the facing.
	 *
	 * @return the facing
	 */
	public EnumWallFacing getFacing() {
		return this.facing;
	}

	/**
	 * Sets the facing.
	 *
	 * @param facing the new facing
	 */
	public void setFacing(final EnumWallFacing facing) {
		this.facing = facing;
	}

	/**
	 * Gets the sprite sheet col.
	 *
	 * @return the sprite sheet col
	 */
	public int getSpriteSheetCol(){
		int f = this.getFacing().getSpriteSheetMod();

		if(f >= EnumWallFacing.SOUTH_WEST.getSpriteSheetMod()) {
			f -= EnumWallFacing.SOUTH_WEST.getSpriteSheetMod();
		}

		return (this.getId() + f) % BitmapHandler.SPRITE_SHEET_SIZE;
	}

	/**
	 * Gets the sprite sheet row.
	 *
	 * @return the sprite sheet row
	 */
	public int getSpriteSheetRow(){
		int f = this.getFacing().getSpriteSheetMod();

		if(f >= EnumWallFacing.SOUTH_WEST.getSpriteSheetMod()) {
			f -= EnumWallFacing.SOUTH_WEST.getSpriteSheetMod();
		}

		return (this.getId() + f) / BitmapHandler.SPRITE_SHEET_SIZE;
	}

	/**
	 * Gets the pos.
	 *
	 * @return the pos
	 */
	public WorldCoord getPos() {
		return this.pos;
	}

	/**
	 * Sets the pos.
	 *
	 * @param pos the new pos
	 */
	public void setPos(final WorldCoord pos) {
		this.pos = pos;
	}
}
