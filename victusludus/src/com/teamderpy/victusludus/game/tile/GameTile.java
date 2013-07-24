
package com.teamderpy.victusludus.game.tile;

import com.badlogic.gdx.math.Vector3;
import com.teamderpy.victusludus.game.IPosition;
import com.teamderpy.victusludus.game.map.Map;

/** The Class GameTile. */
public class GameTile implements IPosition {
	public static final byte ID_GRASS = 0x1;
	public static final byte ID_GRID = 0x2;
	public static final byte ID_DIRT = 0x3;
	public static final byte ID_PATH_GOOD = 0x4;
	public static final byte ID_PATH_BAD = 0x5;
	public static final byte ID_AGAR = 0x6;
	public static final byte ID_HIDDEN = 0x8;
	public static final byte ID_AIR = (byte)0x0;

	/** the tiles per row in the tiles sprite sheet */
	public static final int TILES_PER_ROW = 16;

	/** the tiles per column in the tiles sprite sheet */
	public static final int TILES_PER_COL = 16;

	public static final float TILE_U_WIDTH = 1.0f / GameTile.TILES_PER_ROW;
	public static final float TILE_V_HEIGHT = 1.0f / GameTile.TILES_PER_COL;

	/** The id. */
	private int id;

	private Vector3 pos;

	/**
	 * Instantiates a new game tile.
	 * 
	 * @param id the id
	 * @param map the map the tile belongs to
	 */
	public GameTile (final int id, final Map map) {
		this.id = id;
		this.pos = new Vector3(-1, -1, -1);
	}

	/**
	 * Instantiates a new game tile.
	 * 
	 * @param id the id
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @param map the map the tile belongs to
	 */
	public GameTile (final int id, final int x, final int y, final int z, final Map map) {
		super();
		this.id = id;

		this.pos = new Vector3(x, y, z);
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public int getId () {
		return this.id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id the new id
	 */
	public void setId (final int id) {
		this.id = id;
	}

	@Override
	public Vector3 getPos () {
		return this.pos;
	}

	@Override
	public void setPos (final Vector3 pos) {
		this.pos = pos;
	}

	@Override
	public void setPos (final float x, final float y, final float z) {
		this.pos.x = x;
		this.pos.y = y;
		this.pos.z = z;
	}

	public int getTileTopSpriteSheetRow () {
		return ((this.id - 1) / GameTile.TILES_PER_ROW) * 2;
	}

	public int getTileTopSpriteSheetColumn () {
		return (this.id - 1) % GameTile.TILES_PER_ROW;
	}

	public int getTileSideSpriteSheetRow () {
		return this.getTileTopSpriteSheetRow() + 1;
	}

	public int getTileSideSpriteSheetColumn () {
		return (this.id - 1) % GameTile.TILES_PER_ROW;
	}

	public static int getTileTopSpriteSheetRow (final byte id) {
		return ((id - 1) / GameTile.TILES_PER_ROW) * 2;
	}

	public static int getTileTopSpriteSheetColumn (final byte id) {
		return (id - 1) % GameTile.TILES_PER_ROW;
	}

	public static int getTileSideSpriteSheetRow (final byte id) {
		return GameTile.getTileTopSpriteSheetRow(id) + 1;
	}

	public static int getTileSideSpriteSheetColumn (final byte id) {
		return (id - 1) % GameTile.TILES_PER_ROW;
	}

}
