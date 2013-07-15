
package com.teamderpy.victusludus.game.map;

import java.util.ArrayList;
import java.util.Vector;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.graphics.g3d.materials.TextureAttribute;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.teamderpy.victusludus.game.GameSettings;
import com.teamderpy.victusludus.game.WorldCoord;
import com.teamderpy.victusludus.game.entity.GameEntity;
import com.teamderpy.victusludus.game.entity.GameEntityManager;

/**
 * The Class Map.
 */
public class Map implements RenderableProvider {

	/** The maximum height of a map */
	private static final int MAXIMUM_DEPTH = 128;

	/** Size of a chunk in the x direction */
	public static final int CHUNK_SIZE_X = 16;

	/** Size of a chunk in the y direction */

	public static final int CHUNK_SIZE_Y = 16;

	/** Size of a chunk in the z direction */
	public static final int CHUNK_SIZE_Z = 16;

	/** An array of chunks that make up the map */
	public final Chunk[] chunks;

	/** All of the meshes to render */
	public final Mesh[] meshes;

	/** All of the chunk materials */
	public final Material[] materials;

	/** The textures */
	private final TextureRegion tileTextures;

	/** Whether each chunk is dirty */
	public final boolean[] dirty;

	/** The vertices per chunk */
	public final int[] numVertices;

	/** The vertex data */
	public float[] vertices;

	/** The number of chunks in the X direction */
	public final int chunksX;

	/** The number of chunks in the Y direction */
	public final int chunksY;

	/** The number of chunks in the Z direction */
	public final int chunksZ;

	/** The number of voxels in the X direction */
	public final int voxelsX;

	/** The number of voxels in the Y direction */
	public final int voxelsY;

	/** The number of voxels in the Z direction */
	public final int voxelsZ;

	/** The number of rendered chunks */
	public int renderedChunks;

	/** The number of chunks */
	public int numChunks;

	/** old stuff below, can probably replace */

	/** The tiles. */
	// private GameTile[][][] tiles;

	/** The entity manager. */
	private GameEntityManager entityManager;

	/** The depth. */
	private int depth;

	/** The width. */
	private int width;

	/** The height. */
	private int height;

	/** The highest point. */
	private int highestPoint;

	public Map (final GameSettings requestedSettings, final TextureRegion tileTextures) {
		this.tileTextures = tileTextures;

		int chunksX = requestedSettings.getRequestedMapWidth();
		int chunksZ = requestedSettings.getRequestedMapHeight();
		int chunksY = Map.MAXIMUM_DEPTH / 16;

		this.chunks = new Chunk[chunksX * chunksY * chunksZ];
		this.chunksX = chunksX;
		this.chunksY = chunksY;
		this.chunksZ = chunksZ;

		this.numChunks = chunksX * chunksY * chunksZ;

		this.voxelsX = chunksX * Map.CHUNK_SIZE_X;
		this.voxelsY = chunksY * Map.CHUNK_SIZE_Y;
		this.voxelsZ = chunksZ * Map.CHUNK_SIZE_Z;

		System.out.println("Generating a map of size " + chunksX + "," + chunksY + "," + chunksZ);

		int i = 0;

		/* create the chunks */
		for (int y = 0; y < chunksY; y++) {
			for (int z = 0; z < chunksZ; z++) {
				for (int x = 0; x < chunksX; x++) {
					Chunk chunk = new Chunk(Map.CHUNK_SIZE_X, Map.CHUNK_SIZE_Y, Map.CHUNK_SIZE_Z);
					chunk.offset.set(x * Map.CHUNK_SIZE_X, y * Map.CHUNK_SIZE_Y, z * Map.CHUNK_SIZE_Z);
					this.chunks[i++] = chunk;
				}
			}
		}

		/* quads represented as two tris */
		int vertsPerQuad = 6;
		int vertsPerTri = 3;

		/* create the chunk meshes */
		int len = Map.CHUNK_SIZE_X * Map.CHUNK_SIZE_Y * Map.CHUNK_SIZE_Z * vertsPerQuad * vertsPerQuad / vertsPerTri;
		short[] indices = new short[len];
		short j = 0;

		/* indices for quads */
		for (i = 0; i < len; i += vertsPerQuad, j += 4) {
			indices[i + 0] = (short)(j + 0);
			indices[i + 1] = (short)(j + 1);
			indices[i + 2] = (short)(j + 2);
			indices[i + 3] = (short)(j + 2);
			indices[i + 4] = (short)(j + 3);
			indices[i + 5] = (short)(j + 0);
		}

		this.meshes = new Mesh[chunksX * chunksY * chunksZ];

		for (i = 0; i < this.meshes.length; i++) {
			this.meshes[i] = new Mesh(true, Map.CHUNK_SIZE_X * Map.CHUNK_SIZE_Y * Map.CHUNK_SIZE_Z * vertsPerQuad * 4,
				Map.CHUNK_SIZE_X * Map.CHUNK_SIZE_Y * Map.CHUNK_SIZE_Z * vertsPerQuad * vertsPerQuad / vertsPerTri,
				VertexAttribute.Position(), VertexAttribute.TexCoords(0), VertexAttribute.Normal());
			this.meshes[i].setIndices(indices);
		}

		this.dirty = new boolean[chunksX * chunksY * chunksZ];
		for (i = 0; i < this.dirty.length; i++) {
			this.dirty[i] = true;
		}

		this.numVertices = new int[chunksX * chunksY * chunksZ];
		for (i = 0; i < this.numVertices.length; i++) {
			this.numVertices[i] = 0;
		}

		this.vertices = new float[Chunk.VERTEX_SIZE * vertsPerQuad * Map.CHUNK_SIZE_X * Map.CHUNK_SIZE_Y * Map.CHUNK_SIZE_Z];
		this.materials = new Material[chunksX * chunksY * chunksZ];
		for (i = 0; i < this.materials.length; i++) {
			this.materials[i] = new Material(/*
														 * new
														 * ColorAttribute(ColorAttribute.Diffuse
														 * , MathUtils.random(0.5f, 1f),
														 * MathUtils.random(0.5f, 1f),
														 * MathUtils.random(0.5f, 1f), 1)
														 */);

			this.materials[i].set(new TextureAttribute(TextureAttribute.Diffuse, tileTextures.getTexture()));

		}

		/* other stuff */
		this.entityManager = new GameEntityManager(this);
		/* generate terrain */
		PerlinNoiseGenerator.generateVoxels(this, 0, 63, 10);
	}

	/**
	 * Sets the id of a voxel at a coordinate
	 * 
	 * @param x the x-coordinate of the voxel
	 * @param y the y-coordinate of the voxel
	 * @param z the z-coordinate of the voxel
	 * @param voxel the byte id of the voxel to set the coordinate to
	 */
	public void set (final float x, final float y, final float z, final byte voxel) {
		int ix = (int)x;
		int iy = (int)y;
		int iz = (int)z;

		int chunkX = ix / Map.CHUNK_SIZE_X;

		if (chunkX < 0 || chunkX >= this.chunksX) {
			return;
		}

		int chunkY = iy / Map.CHUNK_SIZE_Y;

		if (chunkY < 0 || chunkY >= this.chunksY) {
			return;
		}

		int chunkZ = iz / Map.CHUNK_SIZE_Z;

		if (chunkZ < 0 || chunkZ >= this.chunksZ) {
			return;
		}

		this.chunks[chunkX + chunkZ * this.chunksX + chunkY * this.chunksX * this.chunksZ].set(ix % Map.CHUNK_SIZE_X, iy
			% Map.CHUNK_SIZE_Y, iz % Map.CHUNK_SIZE_Z, voxel);
	}

	/**
	 * Get the id of a voxel at a coordinate
	 * 
	 * @param x the x-coordinate of the voxel
	 * @param y the y-coordinate of the voxel
	 * @param z the z-coordinate of the voxel
	 * @return the byte id of the voxel
	 */
	public byte get (final float x, final float y, final float z) {
		int ix = (int)x;
		int iy = (int)y;
		int iz = (int)z;

		int chunkX = ix / Map.CHUNK_SIZE_X;

		if (chunkX < 0 || chunkX >= this.chunksX) {
			return 0;
		}

		int chunkY = iy / Map.CHUNK_SIZE_Y;

		if (chunkY < 0 || chunkY >= this.chunksY) {
			return 0;
		}

		int chunkZ = iz / Map.CHUNK_SIZE_Z;

		if (chunkZ < 0 || chunkZ >= this.chunksZ) {
			return 0;
		}

		return this.chunks[chunkX + chunkZ * this.chunksX + chunkY * this.chunksX * this.chunksZ].get(ix % Map.CHUNK_SIZE_X, iy
			% Map.CHUNK_SIZE_Y, iz % Map.CHUNK_SIZE_Z);
	}

	/**
	 * Get the highest Y value for a specified coordinate pair
	 * 
	 * @param x the x-coordinate
	 * @param z the y-coordinate
	 * @return a float value specifying the highest voxel in the Y axis
	 */
	public float getHighest (final float x, final float z) {
		int ix = (int)x;
		int iz = (int)z;

		if (ix < 0 || ix >= this.voxelsX) {
			return 0;
		}

		if (iz < 0 || iz >= this.voxelsZ) {
			return 0;
		}

		for (int y = this.voxelsY - 1; y > 0; y--) {
			if (this.get(ix, y, iz) > 0) {
				return y + 1;
			}
		}

		return 0;
	}

	/**
	 * Sets a column of voxels below the specified coordinate to a specified ID.
	 * 
	 * @param x the x-coordinate of the column
	 * @param y the y-coordinate of the column
	 * @param z the z-coordinate of the column
	 * @param voxel the byte value to set the voxels to
	 */
	public void setColumn (final float x, final float y, final float z, final byte voxel) {
		int ix = (int)x;
		int iy = (int)y;
		int iz = (int)z;

		if (ix < 0 || ix >= this.voxelsX) {
			return;
		}

		if (iy < 0 || iy >= this.voxelsY) {
			return;
		}

		if (iz < 0 || iz >= this.voxelsZ) {
			return;
		}

		for (; iy > 0; iy--) {
			this.set(ix, iy, iz, voxel);
		}
	}

	/**
	 * Sets a cube of voxels to the specified ID
	 * 
	 * @param x the x-coordinate of the cube
	 * @param y the y-coordinate of the cube
	 * @param z the z-coordinate of the cube
	 * @param width the width of the cube
	 * @param height the height of the cube
	 * @param depth the depth of the cube
	 * @param voxel the byte value to set the voxels to
	 */
	public void setCube (final float x, final float y, final float z, final float width, final float height, final float depth,
		final byte voxel) {
		int ix = (int)x;
		int iy = (int)y;
		int iz = (int)z;

		int iwidth = (int)width;
		int iheight = (int)height;
		int idepth = (int)depth;

		int startX = Math.max(ix, 0);
		int endX = Math.min(this.voxelsX, ix + iwidth);
		int startY = Math.max(iy, 0);
		int endY = Math.min(this.voxelsY, iy + iheight);
		int startZ = Math.max(iz, 0);
		int endZ = Math.min(this.voxelsZ, iz + idepth);

		for (iy = startY; iy < endY; iy++) {
			for (iz = startZ; iz < endZ; iz++) {
				for (ix = startX; ix < endX; ix++) {
					this.set(ix, iy, iz, voxel);
				}
			}
		}
	}

	@Override
	public void getRenderables (final Array<Renderable> renderables, final Pool<Renderable> pool) {
		this.renderedChunks = 0;
		for (int i = 0; i < this.chunks.length; i++) {
			Chunk chunk = this.chunks[i];
			Mesh mesh = this.meshes[i];

			if (this.dirty[i]) {
				int numVerts = chunk.calculateVertices(this.vertices);
				this.numVertices[i] = numVerts / 4 * 6;
				mesh.setVertices(this.vertices, 0, numVerts * Chunk.VERTEX_SIZE);
				this.dirty[i] = false;
			}

			if (this.numVertices[i] == 0) {
				continue;
			}

			Renderable renderable = pool.obtain();
			renderable.material = this.materials[i];
			renderable.mesh = mesh;
			renderable.meshPartOffset = 0;
			renderable.meshPartSize = this.numVertices[i];
			renderable.primitiveType = GL20.GL_TRIANGLES;
			renderables.add(renderable);
			this.renderedChunks++;
		}
	}

	/**
	 * Instantiates a new map.
	 * 
	 * @param width the width
	 * @param height the height
	 */

	/*
	 * public Map (final GameSettings requestedSettings, final Game game) {
	 * this.game = game; this.width = requestedSettings.getRequestedMapWidth();
	 * this.height = requestedSettings.getRequestedMapHeight(); this.depth =
	 * Map.MAXIMUM_DEPTH;
	 * 
	 * this.lights = new Lights(); this.lights.ambientLight.set(0.7f, 0.7f, 0.7f,
	 * 1);
	 * 
	 * this.tiles = new GameTile[this.depth][this.width][this.height];
	 * 
	 * LayeredGenerator gen = new
	 * LayeredGenerator((int)requestedSettings.getRequestedMapScale(),
	 * requestedSettings.getRequestedMapRandomness()); int[][] mapArray =
	 * gen.generateInt(this.width, this.height, true);
	 * MatrixMath.smooth(mapArray,
	 * (int)requestedSettings.getRequestedMapSmoothness());
	 * MatrixMath.capMax(mapArray, this.depth - 1);
	 * 
	 * int highestPoint = 0; int lowestPoint = 9999;
	 * 
	 * // find the highest point for (int[] row : mapArray) { for (int element :
	 * row) { if (element > highestPoint) { highestPoint = element; }
	 * 
	 * if (element < lowestPoint) { lowestPoint = element; } } }
	 * 
	 * // apply the plateau factor MatrixMath.capMax(mapArray, (int)(lowestPoint
	 * + (highestPoint - lowestPoint) * (1.0F -
	 * requestedSettings.getRequestedMapPlateauFactor())));
	 * MatrixMath.capMin(mapArray, (int)(lowestPoint + (highestPoint -
	 * lowestPoint) * requestedSettings.getRequestedMapPlateauFactor()));
	 * 
	 * // create actual terrain for (int i = 0; i < mapArray.length; i++) { for
	 * (int j = 0; j < mapArray[i].length; j++) { for (int k = 0; k <
	 * mapArray[i][j]; k++) { this.tiles[k][i][j] = new
	 * GameTile(GameTile.ID_GRASS, i, j, k, this); if (mapArray[i][j] >
	 * this.highestPoint) { this.highestPoint = mapArray[i][j]; } } } }
	 * 
	 * // this.fillTilesInLayer(0, GameTile.ID_AGAR);
	 * 
	 * this.entityManager = new GameEntityManager(this);
	 * 
	 * this.tileOverlayList = new Array<GameTile>();
	 * 
	 * this.lightMap = new LightMap(this, this.depth); }
	 */

	/**
	 * Gets the entity manager.
	 * 
	 * @return the entity manager
	 */
	public GameEntityManager getEntityManager () {
		return this.entityManager;
	}

	/**
	 * Fill tiles in layer.
	 * 
	 * @param depth the depth
	 * @param tileID the tile id
	 */
	/*
	 * public void fillTilesInLayer (final int depth, final byte tileID) { for
	 * (int i = 0; i < this.tiles[depth].length; i++) { for (int j = 0; j <
	 * this.tiles[depth][i].length; j++) { this.tiles[depth][i][j] = new
	 * GameTile(tileID, i, j, depth, this); } } }
	 */

	/**
	 * Adds the entity.
	 * 
	 * @param entity the entity
	 */
	public void addEntity (final GameEntity entity) {
		this.entityManager.add(entity);
	}

	/**
	 * Gets the layer.
	 * 
	 * @param depth the depth
	 * @return the layer
	 */
	/*
	 * public GameTile[][] getLayer (final int depth) { return this.tiles[depth];
	 * }
	 */

	/**
	 * Gets the map.
	 * 
	 * @return the map
	 */
	/*
	 * public GameTile[][][] getMap () { return this.tiles; }
	 */

	/**
	 * Gets the entities.
	 * 
	 * @return the entities
	 */
	public Vector<GameEntity> getEntities () {
		return this.entityManager.getEntities();
	}

	/**
	 * Gets the width.
	 * 
	 * @return the width
	 */
	public int getWidth () {
		return this.width;
	}

	/**
	 * Gets the height.
	 * 
	 * @return the height
	 */
	public int getHeight () {
		return this.height;
	}

	/**
	 * Gets the depth.
	 * 
	 * @return the depth
	 */
	public int getDepth () {
		return this.depth;
	}

	/**
	 * Gets the neighbors.
	 * 
	 * @param coord the coord
	 * @return the neighbors
	 */
	public ArrayList<WorldCoord> getNeighbors (final WorldCoord coord) {
		ArrayList<WorldCoord> coordList = new ArrayList<WorldCoord>();

		coordList.add(new WorldCoord(coord.getX() + 1, coord.getY(), coord.getZ()));
		coordList.add(new WorldCoord(coord.getX() - 1, coord.getY(), coord.getZ()));
		coordList.add(new WorldCoord(coord.getX(), coord.getY() + 1, coord.getZ()));
		coordList.add(new WorldCoord(coord.getX(), coord.getY() - 1, coord.getZ()));
		coordList.add(new WorldCoord(coord.getX(), coord.getY(), coord.getZ() + 1));
		coordList.add(new WorldCoord(coord.getX(), coord.getY(), coord.getZ() - 1));

		for (int i = 0; i < coordList.size(); i++) {
			WorldCoord c = coordList.get(i);

			if (c.getX() >= this.width || c.getY() >= this.height || c.getZ() >= this.depth || c.getX() < 0 || c.getY() < 0
				|| c.getZ() < 0) {
				coordList.remove(i);
			}
		}

		return coordList;
	}

	/**
	 * Gets the highest point.
	 * 
	 * @return the highest point
	 */
	public int getHighestPoint () {
		return this.highestPoint;
	}

	/**
	 * Unregister listeners.
	 */
	public void unregisterListeners () {
	}
}
