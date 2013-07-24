
package com.teamderpy.victusludus.game;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.math.Vector3;

/**
 * The Class EuclideanObject represents an object that exists in Euclidean space
 * along with its model, if any.
 */
public abstract class EuclideanObject implements IPosition, IDirection {
	public static final int VERTEX_SIZE = 8;
	private Vector3 pos;
	private EnumDirection facingDirection;

	/**
	 * The model that renders this object, or null if there is none
	 */
	protected Mesh mesh;
	protected Material material;
	protected boolean isDirty = true;
	protected float[] vertices;
	protected int numVerts;

	/**
	 * Instantiates a new euclidean object.
	 */
	public EuclideanObject () {
		this(-1, -1, -1);
	}

	/**
	 * Instantiates a new euclidean object.
	 * 
	 * @param x the x position of the object
	 * @param y the y position of the object
	 * @param z the z position of the object
	 */
	public EuclideanObject (final int x, final int y, final int z) {
		this(x, y, z, EnumDirection.EAST);
	}

	/**
	 * Instantiates a new euclidean object.
	 * 
	 * @param x the x position of the object
	 * @param y the y position of the object
	 * @param z the z position of the object
	 * @param facingDirection the direction the object is facing
	 */
	public EuclideanObject (final int x, final int y, final int z, final EnumDirection facingDirection) {
		this.pos = new Vector3(x + 0.5f, y, z + 0.5f);
		this.facingDirection = facingDirection;
		this.initMesh();
	}

	/**
	 * Initializes the mesh objects
	 */
	public abstract void initMesh ();

	/**
	 * Calculates the vertices of the object and returns the count of them
	 * @return the number of vertices created
	 */
	public abstract int calculateVertices ();

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

		if (this.mesh != null) {
			this.isDirty = true;
		}
	}

	public float[] getVertices () {
		return this.vertices;
	}

	public Material getMaterial () {
		return this.material;
	}

	public Mesh getMesh () {
		return this.mesh;
	}

	public void setMesh (final Mesh modelInstance) {
		this.mesh = modelInstance;
	}

	public boolean isDirty () {
		return this.isDirty;
	}

	public void setDirty (final boolean isDirty) {
		this.isDirty = isDirty;
	}

	public int getNumVerts () {
		return this.numVerts;
	}

	public void setNumVerts (final int numVerts) {
		this.numVerts = numVerts;
	}

	@Override
	public EnumDirection getDirection () {
		return this.facingDirection;
	}

	@Override
	public void setDirection (final EnumDirection facingDirection) {
		this.facingDirection = facingDirection;
	}
}
