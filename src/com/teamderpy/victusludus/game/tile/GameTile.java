package com.teamderpy.victusludus.game.tile;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.game.EuclideanObject;
import com.teamderpy.victusludus.game.WorldCoord;
import com.teamderpy.victusludus.game.light.LightLevel;
import com.teamderpy.victusludus.game.renderer.BitmapHandler;
import com.teamderpy.victusludus.game.wall.GameWall;

// TODO: Auto-generated Javadoc
/**
 * The Class GameTile.
 */
public class GameTile extends EuclideanObject{

	/** The Constant ID_GRASS. */
	public static final byte ID_GRASS       = 0x0;

	/** The Constant ID_GRID. */
	public static final byte ID_GRID        = 0x1;

	/** The Constant ID_DIRT. */
	public static final byte ID_DIRT        = 0x2;

	/** The Constant ID_PATH_GOOD. */
	public static final byte ID_PATH_GOOD   = 0x3;

	/** The Constant ID_PATH_BAD. */
	public static final byte ID_PATH_BAD    = 0x4;

	/** The Constant ID_AGAR. */
	public static final byte ID_AGAR        = 0x5;

	/** The Constant ID_HIDDEN. */
	public static final byte ID_HIDDEN      = 0x7;

	/** The id. */
	private int id;

	/** The current light of the tile */
	private LightLevel tileLight;

	/** The north east wall. */
	private GameWall northEastWall = null;

	/** The north west wall. */
	private GameWall northWestWall = null;

	/** The south east wall. */
	private GameWall southEastWall = null;

	/** The south west wall. */
	private GameWall southWestWall = null;

	/**
	 * Instantiates a new game tile.
	 *
	 * @param id the id
	 */
	public GameTile(final int id){
		this.id = id;

		this.setTileLight(new LightLevel(1, VictusLudus.e.currentGame.getAmbientLightColor()));

		super.setTotallyBlockingLOS(true);
	}

	/**
	 * Instantiates a new game tile.
	 *
	 * @param id the id
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public GameTile(final int id, final int x, final int y, final int z){
		super();
		this.id = id;

		this.setTileLight(new LightLevel(1, VictusLudus.e.currentGame.getAmbientLightColor()));

		super.setWorldCoord(new WorldCoord(x, y, z));
		super.setTotallyBlockingLOS(true);
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
	 * Gets the north east wall.
	 *
	 * @return the north east wall
	 */
	public GameWall getNorthEastWall() {
		return this.northEastWall;
	}

	/**
	 * Sets the north east wall.
	 *
	 * @param northEastWall the new north east wall
	 */
	public void setNorthEastWall(final GameWall northEastWall) {
		this.northEastWall = northEastWall;
	}

	/**
	 * Gets the north west wall.
	 *
	 * @return the north west wall
	 */
	public GameWall getNorthWestWall() {
		return this.northWestWall;
	}

	/**
	 * Sets the north west wall.
	 *
	 * @param northWestWall the new north west wall
	 */
	public void setNorthWestWall(final GameWall northWestWall) {
		this.northWestWall = northWestWall;
	}

	/**
	 * Gets the south east wall.
	 *
	 * @return the south east wall
	 */
	public GameWall getSouthEastWall() {
		return this.southEastWall;
	}

	/**
	 * Sets the south east wall.
	 *
	 * @param southEastWall the new south east wall
	 */
	public void setSouthEastWall(final GameWall southEastWall) {
		this.southEastWall = southEastWall;
	}

	/**
	 * Gets the south west wall.
	 *
	 * @return the south west wall
	 */
	public GameWall getSouthWestWall() {
		return this.southWestWall;
	}

	/**
	 * Sets the south west wall.
	 *
	 * @param southWestWall the new south west wall
	 */
	public void setSouthWestWall(final GameWall southWestWall) {
		this.southWestWall = southWestWall;
	}

	/**
	 * Gets the sprite sheet col.
	 *
	 * @return the sprite sheet col
	 */
	public int getSpriteSheetCol(){
		return this.getId() % BitmapHandler.SPRITE_SHEET_SIZE;
	}

	/**
	 * Gets the sprite sheet row.
	 *
	 * @return the sprite sheet row
	 */
	public int getSpriteSheetRow(){
		return this.getId() / BitmapHandler.SPRITE_SHEET_SIZE;
	}

	/**
	 * Gets the sprite sheet col.
	 *
	 * @return the sprite sheet col
	 */
	public static int getSpriteSheetCol(final int id){
		return id % BitmapHandler.SPRITE_SHEET_SIZE;
	}

	/**
	 * Gets the sprite sheet row.
	 *
	 * @return the sprite sheet row
	 */
	public static int getSpriteSheetRow(final int id){
		return id / BitmapHandler.SPRITE_SHEET_SIZE;
	}

	/**
	 * Gets the world position of the object
	 *
	 * @return the pos
	 */
	public WorldCoord getPos() {
		return super.getWorldCoord();
	}

	/**
	 * Sets the world position of the object
	 *
	 * @param pos the new pos
	 */
	public void setPos(final WorldCoord pos) {
		super.setWorldCoord(pos);
	}

	public LightLevel getTileLight() {
		return tileLight;
	}

	public void setTileLight(LightLevel tileLight) {
		this.tileLight = tileLight;
	}
}
