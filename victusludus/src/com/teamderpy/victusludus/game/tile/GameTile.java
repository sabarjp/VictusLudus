
package com.teamderpy.victusludus.game.tile;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.materials.IntAttribute;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.game.EuclideanObject;
import com.teamderpy.victusludus.game.WorldCoord;
import com.teamderpy.victusludus.game.map.Map;

/** The Class GameTile. */
public class GameTile extends EuclideanObject {
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

	/**
	 * Instantiates a new game tile.
	 * 
	 * @param id the id
	 * @param map the map the tile belongs to
	 */
	public GameTile (final int id, final Map map) {
		this.id = id;

		super.setTotallyBlockingLOS(true);

		Model model = VictusLudusGame.engine.assetManager.get("3d/cube.g3db", Model.class);
		this.setModelInstance(new ModelInstance(model));
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

		super.setWorldCoord(new WorldCoord(x, y, z));
		super.setTotallyBlockingLOS(true);

		Model model = VictusLudusGame.engine.assetManager.get("meshes/terrainCube.g3db", Model.class);
		for (Material m : model.materials) {
			m.set(new IntAttribute(IntAttribute.CullFace, GL20.GL_BACK));
		}
		this.setModelInstance(new ModelInstance(model, x, y, z));
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

	/**
	 * Gets the world position of the object
	 * 
	 * @return the pos
	 */
	public WorldCoord getPos () {
		return super.getWorldCoord();
	}

	/**
	 * Sets the world position of the object
	 * 
	 * @param pos the new pos
	 */
	public void setPos (final WorldCoord pos) {
		super.setWorldCoord(pos);
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
