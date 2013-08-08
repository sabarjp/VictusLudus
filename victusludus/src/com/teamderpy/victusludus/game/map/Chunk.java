
package com.teamderpy.victusludus.game.map;

import com.badlogic.gdx.math.Vector3;
import com.teamderpy.victusludus.game.tile.GameTile;

public class Chunk {
	public static final int VERTEX_SIZE = 8;

	/** the voxels in this chunk, using byte identifiers */
	public final byte[] voxels;

	/** the width of this chunk */
	public final int width;

	/** the height of this chunk */
	public final int height;

	/** the depth of this chunk */
	public final int depth;

	/** the offset of this chunk from the origin */
	public final Vector3 offset = new Vector3();

	private final int widthTimesHeight;
	private final int topOffset;
	private final int bottomOffset;
	private final int leftOffset;
	private final int rightOffset;
	private final int frontOffset;
	private final int backOffset;

	/**
	 * Instantiates a new chunk with specified dimensions
	 * 
	 * @param width the width of the chunk, in voxels
	 * @param height the height of the chunk, in voxels
	 * @param depth the depth of the chunk, in voxels
	 */
	public Chunk (final int width, final int height, final int depth) {
		this.voxels = new byte[width * height * depth];
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.topOffset = width * depth;
		this.bottomOffset = -width * depth;
		this.leftOffset = -1;
		this.rightOffset = 1;
		this.frontOffset = -width;
		this.backOffset = width;
		this.widthTimesHeight = width * height;
	}

	/**
	 * Gets the byte id of a voxel at a certain coordinate
	 * @param x the x-coordinate of the voxel to check
	 * @param y the y-coordinate of the voxel to check
	 * @param z the z-coordinate of the voxel to check
	 * @return the byte id of the voxel
	 */
	public byte get (final int x, final int y, final int z) {
		if (x < 0 || x >= this.width) {
			return 0;
		}
		if (y < 0 || y >= this.height) {
			return 0;
		}
		if (z < 0 || z >= this.depth) {
			return 0;
		}
		return this.getFast(x, y, z);
	}

	/**
	 * Gets the byte id of a voxel at a certain coordinate, but does not check
	 * that the coordinate is in-bounds. Dangerous!
	 * 
	 * @param x the x-coordinate of the voxel to check
	 * @param y the y-coordinate of the voxel to check
	 * @param z the z-coordinate of the voxel to check
	 * @return the byte id of the voxel
	 */
	public byte getFast (final int x, final int y, final int z) {
		return this.voxels[x + z * this.width + y * this.widthTimesHeight];
	}

	/**
	 * Sets the voxel at a certain coordinate to a specified byte id
	 * 
	 * @param x the x-coordinate of the voxel
	 * @param y the y-coordinate of the voxel
	 * @param z the z-coordinate of the voxel
	 * @param voxel the byte id of to set the voxel at (x,y,z) to
	 */
	public void set (final int x, final int y, final int z, final byte voxel) {
		if (x < 0 || x >= this.width) {
			return;
		}
		if (y < 0 || y >= this.height) {
			return;
		}
		if (z < 0 || z >= this.depth) {
			return;
		}
		this.setFast(x, y, z, voxel);
	}

	/**
	 * Sets the voxel at a certain coordinate to a specified byte id, but does
	 * not check that the coordinate is in-bounds. Dangerous!
	 * 
	 * @param x the x-coordinate of the voxel
	 * @param y the y-coordinate of the voxel
	 * @param z the z-coordinate of the voxel
	 * @param voxel the byte id of to set the voxel at (x,y,z) to
	 */
	public void setFast (final int x, final int y, final int z, final byte voxel) {
		this.voxels[x + z * this.width + y * this.widthTimesHeight] = voxel;
	}

	/**
	 * Creates a mesh out of the chunk, returning the number of indices produced
	 * 
	 * @return the number of vertices produced
	 */
	public int calculateVertices (final float[] vertices) {
		int i = 0;
		int vertexOffset = 0;
		for (int y = 0; y < this.height; y++) {
			for (int z = 0; z < this.depth; z++) {
				for (int x = 0; x < this.width; x++, i++) {
					byte voxel = this.voxels[i];
					/* skip this type */
					if (voxel == GameTile.ID_AIR) {
						continue;
					}

					if (y < this.height - 1) {
						if (this.voxels[i + this.topOffset] == GameTile.ID_AIR) {
							vertexOffset = Chunk.createTop(voxel, this.offset, x, y, z, vertices, vertexOffset);
						}
					} else {
						vertexOffset = Chunk.createTop(voxel, this.offset, x, y, z, vertices, vertexOffset);
					}
					if (y > 0) {
						if (this.voxels[i + this.bottomOffset] == GameTile.ID_AIR) {
							vertexOffset = Chunk.createBottom(voxel, this.offset, x, y, z, vertices, vertexOffset);
						}
					} else {
						vertexOffset = Chunk.createBottom(voxel, this.offset, x, y, z, vertices, vertexOffset);
					}
					if (x > 0) {
						if (this.voxels[i + this.leftOffset] == GameTile.ID_AIR) {
							vertexOffset = Chunk.createLeft(voxel, this.offset, x, y, z, vertices, vertexOffset);
						}
					} else {
						vertexOffset = Chunk.createLeft(voxel, this.offset, x, y, z, vertices, vertexOffset);
					}
					if (x < this.width - 1) {
						if (this.voxels[i + this.rightOffset] == GameTile.ID_AIR) {
							vertexOffset = Chunk.createRight(voxel, this.offset, x, y, z, vertices, vertexOffset);
						}
					} else {
						vertexOffset = Chunk.createRight(voxel, this.offset, x, y, z, vertices, vertexOffset);
					}
					if (z > 0) {
						if (this.voxels[i + this.frontOffset] == GameTile.ID_AIR) {
							vertexOffset = Chunk.createFront(voxel, this.offset, x, y, z, vertices, vertexOffset);
						}
					} else {
						vertexOffset = Chunk.createFront(voxel, this.offset, x, y, z, vertices, vertexOffset);
					}
					if (z < this.depth - 1) {
						if (this.voxels[i + this.backOffset] == GameTile.ID_AIR) {
							vertexOffset = Chunk.createBack(voxel, this.offset, x, y, z, vertices, vertexOffset);
						}
					} else {
						vertexOffset = Chunk.createBack(voxel, this.offset, x, y, z, vertices, vertexOffset);
					}
				}
			}
		}
		return vertexOffset / Chunk.VERTEX_SIZE;
	}

	public static int createTop (final Byte voxel, final Vector3 offset, final int x, final int y, final int z,
		final float[] vertices, int vertexOffset) {

		int ssx = GameTile.getTileTopSpriteSheetColumn(voxel);
		int ssy = GameTile.getTileTopSpriteSheetRow(voxel);

		float u1 = GameTile.TILE_U_WIDTH * ssx;
		float v1 = GameTile.TILE_V_HEIGHT * ssy;
		float u2 = GameTile.TILE_U_WIDTH * (ssx + 1);
		float v2 = GameTile.TILE_V_HEIGHT * (ssy + 1);

		vertices[vertexOffset++] = offset.x + x;
		vertices[vertexOffset++] = offset.y + y + 1;
		vertices[vertexOffset++] = offset.z + z;
		vertices[vertexOffset++] = u2;
		vertices[vertexOffset++] = v2;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 1;
		vertices[vertexOffset++] = 0;

		vertices[vertexOffset++] = offset.x + x + 1;
		vertices[vertexOffset++] = offset.y + y + 1;
		vertices[vertexOffset++] = offset.z + z;
		vertices[vertexOffset++] = u1;
		vertices[vertexOffset++] = v2;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 1;
		vertices[vertexOffset++] = 0;

		vertices[vertexOffset++] = offset.x + x + 1;
		vertices[vertexOffset++] = offset.y + y + 1;
		vertices[vertexOffset++] = offset.z + z + 1;
		vertices[vertexOffset++] = u1;
		vertices[vertexOffset++] = v1;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 1;
		vertices[vertexOffset++] = 0;

		vertices[vertexOffset++] = offset.x + x;
		vertices[vertexOffset++] = offset.y + y + 1;
		vertices[vertexOffset++] = offset.z + z + 1;
		vertices[vertexOffset++] = u2;
		vertices[vertexOffset++] = v1;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 1;
		vertices[vertexOffset++] = 0;
		return vertexOffset;
	}

	public static int createBottom (final Byte voxel, final Vector3 offset, final int x, final int y, final int z,
		final float[] vertices, int vertexOffset) {

		int ssx = GameTile.getTileSideSpriteSheetColumn(voxel);
		int ssy = GameTile.getTileSideSpriteSheetRow(voxel);

		float u1 = GameTile.TILE_U_WIDTH * ssx;
		float v1 = GameTile.TILE_V_HEIGHT * ssy;
		float u2 = GameTile.TILE_U_WIDTH * (ssx + 1);
		float v2 = GameTile.TILE_V_HEIGHT * (ssy + 1);

		vertices[vertexOffset++] = offset.x + x;
		vertices[vertexOffset++] = offset.y + y;
		vertices[vertexOffset++] = offset.z + z;
		vertices[vertexOffset++] = u2;
		vertices[vertexOffset++] = v2;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = -1;
		vertices[vertexOffset++] = 0;

		vertices[vertexOffset++] = offset.x + x;
		vertices[vertexOffset++] = offset.y + y;
		vertices[vertexOffset++] = offset.z + z + 1;
		vertices[vertexOffset++] = u1;
		vertices[vertexOffset++] = v2;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = -1;
		vertices[vertexOffset++] = 0;

		vertices[vertexOffset++] = offset.x + x + 1;
		vertices[vertexOffset++] = offset.y + y;
		vertices[vertexOffset++] = offset.z + z + 1;
		vertices[vertexOffset++] = u1;
		vertices[vertexOffset++] = v1;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = -1;
		vertices[vertexOffset++] = 0;

		vertices[vertexOffset++] = offset.x + x + 1;
		vertices[vertexOffset++] = offset.y + y;
		vertices[vertexOffset++] = offset.z + z;
		vertices[vertexOffset++] = u2;
		vertices[vertexOffset++] = v1;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = -1;
		vertices[vertexOffset++] = 0;
		return vertexOffset;
	}

	public static int createLeft (final Byte voxel, final Vector3 offset, final int x, final int y, final int z,
		final float[] vertices, int vertexOffset) {

		int ssx = GameTile.getTileSideSpriteSheetColumn(voxel);
		int ssy = GameTile.getTileSideSpriteSheetRow(voxel);

		float u1 = GameTile.TILE_U_WIDTH * ssx;
		float v1 = GameTile.TILE_V_HEIGHT * ssy;
		float u2 = GameTile.TILE_U_WIDTH * (ssx + 1);
		float v2 = GameTile.TILE_V_HEIGHT * (ssy + 1);

		vertices[vertexOffset++] = offset.x + x;
		vertices[vertexOffset++] = offset.y + y;
		vertices[vertexOffset++] = offset.z + z;
		vertices[vertexOffset++] = u1;
		vertices[vertexOffset++] = v2;
		vertices[vertexOffset++] = -1;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 0;

		vertices[vertexOffset++] = offset.x + x;
		vertices[vertexOffset++] = offset.y + y + 1;
		vertices[vertexOffset++] = offset.z + z;
		vertices[vertexOffset++] = u1;
		vertices[vertexOffset++] = v1;
		vertices[vertexOffset++] = -1;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 0;

		vertices[vertexOffset++] = offset.x + x;
		vertices[vertexOffset++] = offset.y + y + 1;
		vertices[vertexOffset++] = offset.z + z + 1;
		vertices[vertexOffset++] = u2;
		vertices[vertexOffset++] = v1;
		vertices[vertexOffset++] = -1;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 0;

		vertices[vertexOffset++] = offset.x + x;
		vertices[vertexOffset++] = offset.y + y;
		vertices[vertexOffset++] = offset.z + z + 1;
		vertices[vertexOffset++] = u2;
		vertices[vertexOffset++] = v2;
		vertices[vertexOffset++] = -1;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 0;
		return vertexOffset;
	}

	public static int createRight (final Byte voxel, final Vector3 offset, final int x, final int y, final int z,
		final float[] vertices, int vertexOffset) {

		int ssx = GameTile.getTileSideSpriteSheetColumn(voxel);
		int ssy = GameTile.getTileSideSpriteSheetRow(voxel);

		float u1 = GameTile.TILE_U_WIDTH * ssx;
		float v1 = GameTile.TILE_V_HEIGHT * ssy;
		float u2 = GameTile.TILE_U_WIDTH * (ssx + 1);
		float v2 = GameTile.TILE_V_HEIGHT * (ssy + 1);

		vertices[vertexOffset++] = offset.x + x + 1;
		vertices[vertexOffset++] = offset.y + y;
		vertices[vertexOffset++] = offset.z + z;
		vertices[vertexOffset++] = u2;
		vertices[vertexOffset++] = v2;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 1;
		vertices[vertexOffset++] = 0;

		vertices[vertexOffset++] = offset.x + x + 1;
		vertices[vertexOffset++] = offset.y + y;
		vertices[vertexOffset++] = offset.z + z + 1;
		vertices[vertexOffset++] = u1;
		vertices[vertexOffset++] = v2;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 1;
		vertices[vertexOffset++] = 0;

		vertices[vertexOffset++] = offset.x + x + 1;
		vertices[vertexOffset++] = offset.y + y + 1;
		vertices[vertexOffset++] = offset.z + z + 1;
		vertices[vertexOffset++] = u1;
		vertices[vertexOffset++] = v1;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 1;
		vertices[vertexOffset++] = 0;

		vertices[vertexOffset++] = offset.x + x + 1;
		vertices[vertexOffset++] = offset.y + y + 1;
		vertices[vertexOffset++] = offset.z + z;
		vertices[vertexOffset++] = u2;
		vertices[vertexOffset++] = v1;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 1;
		vertices[vertexOffset++] = 0;
		return vertexOffset;
	}

	public static int createFront (final Byte voxel, final Vector3 offset, final int x, final int y, final int z,
		final float[] vertices, int vertexOffset) {

		int ssx = GameTile.getTileSideSpriteSheetColumn(voxel);
		int ssy = GameTile.getTileSideSpriteSheetRow(voxel);

		float u1 = GameTile.TILE_U_WIDTH * ssx;
		float v1 = GameTile.TILE_V_HEIGHT * ssy;
		float u2 = GameTile.TILE_U_WIDTH * (ssx + 1);
		float v2 = GameTile.TILE_V_HEIGHT * (ssy + 1);

		vertices[vertexOffset++] = offset.x + x;
		vertices[vertexOffset++] = offset.y + y;
		vertices[vertexOffset++] = offset.z + z;
		vertices[vertexOffset++] = u2;
		vertices[vertexOffset++] = v2;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 1;

		vertices[vertexOffset++] = offset.x + x + 1;
		vertices[vertexOffset++] = offset.y + y;
		vertices[vertexOffset++] = offset.z + z;
		vertices[vertexOffset++] = u1;
		vertices[vertexOffset++] = v2;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 1;

		vertices[vertexOffset++] = offset.x + x + 1;
		vertices[vertexOffset++] = offset.y + y + 1;
		vertices[vertexOffset++] = offset.z + z;
		vertices[vertexOffset++] = u1;
		vertices[vertexOffset++] = v1;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 1;

		vertices[vertexOffset++] = offset.x + x;
		vertices[vertexOffset++] = offset.y + y + 1;
		vertices[vertexOffset++] = offset.z + z;
		vertices[vertexOffset++] = u2;
		vertices[vertexOffset++] = v1;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 1;
		return vertexOffset;
	}

	public static int createBack (final Byte voxel, final Vector3 offset, final int x, final int y, final int z,
		final float[] vertices, int vertexOffset) {

		int ssx = GameTile.getTileSideSpriteSheetColumn(voxel);
		int ssy = GameTile.getTileSideSpriteSheetRow(voxel);

		float u1 = GameTile.TILE_U_WIDTH * ssx;
		float v1 = GameTile.TILE_V_HEIGHT * ssy;
		float u2 = GameTile.TILE_U_WIDTH * (ssx + 1);
		float v2 = GameTile.TILE_V_HEIGHT * (ssy + 1);

		vertices[vertexOffset++] = offset.x + x;
		vertices[vertexOffset++] = offset.y + y;
		vertices[vertexOffset++] = offset.z + z + 1;
		vertices[vertexOffset++] = u1;
		vertices[vertexOffset++] = v2;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = -1;

		vertices[vertexOffset++] = offset.x + x;
		vertices[vertexOffset++] = offset.y + y + 1;
		vertices[vertexOffset++] = offset.z + z + 1;
		vertices[vertexOffset++] = u1;
		vertices[vertexOffset++] = v1;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = -1;

		vertices[vertexOffset++] = offset.x + x + 1;
		vertices[vertexOffset++] = offset.y + y + 1;
		vertices[vertexOffset++] = offset.z + z + 1;
		vertices[vertexOffset++] = u2;
		vertices[vertexOffset++] = v1;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = -1;

		vertices[vertexOffset++] = offset.x + x + 1;
		vertices[vertexOffset++] = offset.y + y;
		vertices[vertexOffset++] = offset.z + z + 1;
		vertices[vertexOffset++] = u2;
		vertices[vertexOffset++] = v2;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = 0;
		vertices[vertexOffset++] = -1;
		return vertexOffset;
	}
}
