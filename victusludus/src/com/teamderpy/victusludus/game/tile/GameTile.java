
package com.teamderpy.victusludus.game.tile;

import com.teamderpy.victusludus.game.EuclideanObject;
import com.teamderpy.victusludus.game.WorldCoord;
import com.teamderpy.victusludus.game.light.LightLevel;
import com.teamderpy.victusludus.game.map.Map;

/** The Class GameTile. */
public class GameTile extends EuclideanObject {

	/** The Constant ID_GRASS. */
	public static final byte ID_GRASS = 0x0;

	/** The Constant ID_GRID. */
	public static final byte ID_GRID = 0x1;

	/** The Constant ID_DIRT. */
	public static final byte ID_DIRT = 0x2;

	/** The Constant ID_PATH_GOOD. */
	public static final byte ID_PATH_GOOD = 0x3;

	/** The Constant ID_PATH_BAD. */
	public static final byte ID_PATH_BAD = 0x4;

	/** The Constant ID_AGAR. */
	public static final byte ID_AGAR = 0x5;

	/** The Constant ID_HIDDEN. */
	public static final byte ID_HIDDEN = 0x7;

	/** The id. */
	private int id;

	/** The current light of the tile */
	private LightLevel tileLight;

	/** Instantiates a new game tile.
	 * 
	 * @param id the id
	 * @param map the map the tile belongs to */
	public GameTile (final int id, final Map map) {
		this.id = id;

		this.setTileLight(new LightLevel(1, map.getGame().getAmbientLightColor()));

		super.setTotallyBlockingLOS(true);
	}

	/** Instantiates a new game tile.
	 * 
	 * @param id the id
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @param map the map the tile belongs to */
	public GameTile (final int id, final int x, final int y, final int z, final Map map) {
		super();
		this.id = id;

		this.setTileLight(new LightLevel(1, map.getGame().getAmbientLightColor()));

		super.setWorldCoord(new WorldCoord(x, y, z));
		super.setTotallyBlockingLOS(true);
	}

	/** Gets the id.
	 * 
	 * @return the id */
	public int getId () {
		return this.id;
	}

	/** Sets the id.
	 * 
	 * @param id the new id */
	public void setId (final int id) {
		this.id = id;
	}

	/** Gets the world position of the object
	 * 
	 * @return the pos */
	public WorldCoord getPos () {
		return super.getWorldCoord();
	}

	/** Sets the world position of the object
	 * 
	 * @param pos the new pos */
	public void setPos (final WorldCoord pos) {
		super.setWorldCoord(pos);
	}

	public LightLevel getTileLight () {
		return this.tileLight;
	}

	public void setTileLight (final LightLevel tileLight) {
		this.tileLight = tileLight;
	}
}
