
package com.teamderpy.victusludus.game;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.materials.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

/**
 * The Class EuclideanObject represents an object that exists in Euclidean space
 * along with its model
 */
public class EuclideanObject {
	public static final int VERTEX_SIZE = 8;

	/** The world coordinates of the euclidean object. */
	public Vector3 pos;

	/**
	 * The model that renders this object, or null if there is none
	 */
	private Mesh mesh;
	private Material material;
	private boolean isDirty = true;
	private float[] vertices;
	private int numVerts;

	/**
	 * Instantiates a new euclidean object.
	 */
	public EuclideanObject () {
		this.pos = new Vector3(-1, -1, -1);
		this.initMesh();
	}

	/**
	 * Instantiates a new euclidean object.
	 * 
	 * @param x the x position of the object
	 * @param y the y position of the object
	 * @param z the z position of the object
	 */
	public EuclideanObject (final int x, final int y, final int z) {
		this.pos = new Vector3(x, y, z);
		this.initMesh();
	}

	public void initMesh () {
		this.vertices = new float[EuclideanObject.VERTEX_SIZE * 4];
		this.mesh = new Mesh(true, 4, 6, VertexAttribute.Position(), VertexAttribute.TexCoords(0), VertexAttribute.Normal());

		short[] indices = new short[6];
		indices[0] = (short)(0);
		indices[1] = (short)(1);
		indices[2] = (short)(2);
		indices[3] = (short)(2);
		indices[4] = (short)(3);
		indices[5] = (short)(0);

		this.mesh.setIndices(indices);
		this.material = new Material(new ColorAttribute(ColorAttribute.Diffuse, MathUtils.random(0.5f, 1f), MathUtils.random(0.5f,
			1f), MathUtils.random(0.5f, 1f), 1));
	}

	/**
	 * Sets the world coord of this object
	 * 
	 * @param worldCoord the new world coord of this object
	 */
	public void setWorldCoord (final int x, final int y, final int z) {
		this.pos.x = x;
		this.pos.y = y;
		this.pos.z = z;

		if (this.mesh != null) {
			this.isDirty = true;
		}
	}

	/**
	 * Calculates the vertices of the object and returns the count of them
	 * @return the number of vertices created
	 */
	public int calculateVertices () {
		int vertexOffset = 0;

		int yoffset = 1;

		this.vertices[vertexOffset++] = 0 /* + this.pos.x */;
		this.vertices[vertexOffset++] = 0 /* + this.pos.y */+ yoffset;
		this.vertices[vertexOffset++] = 0 /* + this.pos.z */;
		this.vertices[vertexOffset++] = 0;
		this.vertices[vertexOffset++] = 1;
		this.vertices[vertexOffset++] = 0;
		this.vertices[vertexOffset++] = 0;
		this.vertices[vertexOffset++] = 1;

		this.vertices[vertexOffset++] = 1 /* + this.pos.x */;
		this.vertices[vertexOffset++] = 0 /* + this.pos.y */+ yoffset;
		this.vertices[vertexOffset++] = 0 /* + this.pos.z */;
		this.vertices[vertexOffset++] = 0;
		this.vertices[vertexOffset++] = 0;
		this.vertices[vertexOffset++] = 0;
		this.vertices[vertexOffset++] = 0;
		this.vertices[vertexOffset++] = 1;

		this.vertices[vertexOffset++] = 1 /* + this.pos.x */;
		this.vertices[vertexOffset++] = 1 /* + this.pos.y */+ yoffset;
		this.vertices[vertexOffset++] = 0 /* + this.pos.z */;
		this.vertices[vertexOffset++] = 1;
		this.vertices[vertexOffset++] = 0;
		this.vertices[vertexOffset++] = 0;
		this.vertices[vertexOffset++] = 0;
		this.vertices[vertexOffset++] = 1;

		this.vertices[vertexOffset++] = 0 /* + this.pos.x */;
		this.vertices[vertexOffset++] = 1 /* + this.pos.y */+ yoffset;
		this.vertices[vertexOffset++] = 0 /* + this.pos.z */;
		this.vertices[vertexOffset++] = 1;
		this.vertices[vertexOffset++] = 1;
		this.vertices[vertexOffset++] = 0;
		this.vertices[vertexOffset++] = 0;
		this.vertices[vertexOffset++] = 1;

		return vertexOffset / EuclideanObject.VERTEX_SIZE;
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
}
