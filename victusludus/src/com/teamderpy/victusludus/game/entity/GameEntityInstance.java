
package com.teamderpy.victusludus.game.entity;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.materials.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.materials.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.graphics.g3d.materials.TextureAttribute;
import com.badlogic.gdx.math.Vector3;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.VictusRuntimeException;
import com.teamderpy.victusludus.game.EuclideanObject;
import com.teamderpy.victusludus.game.map.Map;

public class GameEntityInstance extends EuclideanObject {
	/** The entity definition that this bases its properties on. */
	private GameEntity e;

	/** The movement vector. */
	private Vector3 velocity;

	/** The creation time. */
	private long creationTime;

	/** The animation that is playing */
	private Animation currentAnimation;
	private float animationTimer = 0F;

	/** The map this entity belongs to */
	private Map map;

	/**
	 * Instantiates a new game entity.
	 * 
	 * @param entity the entity
	 * @param x
	 * @param y
	 * @param z
	 * @param map the map the entity is a part of
	 */
	public GameEntityInstance (final GameEntity entity, final int x, final int y, final int z, final Map map) {
		super(x, y, z);

		if (entity == null) {
			throw new VictusRuntimeException("could not locate entity, value is null: " + entity);
		}

		this.map = map;

		this.e = entity;

		this.creationTime = VictusLudusGame.engine.getTickCount();
		this.velocity = new Vector3(0, 0, 0);
		this.playAnimation("idle");
	}

	/**
	 * Instantiates a new game entity.
	 * 
	 * @param entityID the entity id
	 * @param map the map the entity is a part of
	 */
	public GameEntityInstance (final String entityID, final Map map) {
		this(entityID, -1, -1, -1, map);
	}

	/**
	 * Instantiates a new game entity.
	 * 
	 * @param entityID the entity id
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @param map the map the entity is a part of
	 */
	public GameEntityInstance (final String entityID, final int x, final int y, final int z, final Map map) {
		this(VictusLudusGame.resources.getEntityHash().get(entityID), x, y, z, map);
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId () {
		return this.e.getId();
	}

	/**
	 * Gets the entity.
	 * 
	 * @return the entity
	 */
	public GameEntity getEntity () {
		return this.e;
	}

	/** Tick. */
	public void tick (final float deltaT) {
		this.getEntity().tick(this, this.map);
		this.animationTimer += deltaT;
	}

	/**
	 * Gets the movement vector.
	 * 
	 * @return the movement vector
	 */
	public Vector3 getMovementVector () {
		return this.velocity;
	}

	/**
	 * Sets the movement vector.
	 * 
	 * @param movementVector the new movement vector
	 */
	public void setMovementVector (final Vector3 movementVector) {
		this.velocity = movementVector;
	}

	/**
	 * Gets the creation time.
	 * 
	 * @return the creation time
	 */
	public long getCreationTime () {
		return this.creationTime;
	}

	/**
	 * Changes the animation to the one specified and starts it from frame zero,
	 * along with changing the texture to the correct one
	 * 
	 * @param str the name of the animation to play
	 */
	public void playAnimation (final String str) {
		this.currentAnimation = this.e.getAnimationHash().get(str);
		if (this.currentAnimation == null) {
			throw new VictusRuntimeException("Failed to find animation: " + str + " for " + this);
		} else {
			this.loadTexture();
		}
	}

	/**
	 * Gets the current animation.
	 * 
	 * @return the current animation, or null if there is no animation to play
	 */
	public TextureRegion getCurrentAnimation () {
		if (this.currentAnimation != null) {
			return this.currentAnimation.getKeyFrame(this.animationTimer);
		}
		return null;
	}

	@Override
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
	}

	private void loadTexture () {
		TextureRegion tr = this.getCurrentAnimation();

		if (tr == null) {
			/* pink texture for failure to locate */
			this.material = new Material(new ColorAttribute(ColorAttribute.Diffuse, 255, 255, 0, 1));
		} else {
			this.material = new Material(new TextureAttribute(TextureAttribute.Diffuse, tr.getTexture()), new BlendingAttribute());
		}
	}

	@Override
	public int calculateVertices () {
		int vertexOffset = 0;

		float yoffset = 0;
		float xoffset = 0f;
		float zoffset = 0f;

		float u1, v1, u2, v2;

		TextureRegion tr = this.getCurrentAnimation();

		if (tr != null) {
			u1 = tr.getU();
			u2 = tr.getU2();
			v1 = tr.getV();
			v2 = tr.getV2();
		} else {
			u1 = 0;
			u2 = 1;
			v1 = 0;
			v2 = 1;
		}

		this.vertices[vertexOffset++] = -0.5f + xoffset;
		this.vertices[vertexOffset++] = 0 + yoffset;
		this.vertices[vertexOffset++] = 0 + zoffset;
		this.vertices[vertexOffset++] = u2;
		this.vertices[vertexOffset++] = v2;
		this.vertices[vertexOffset++] = 0;
		this.vertices[vertexOffset++] = 0;
		this.vertices[vertexOffset++] = 1;

		this.vertices[vertexOffset++] = 0.5f * this.e.getWidth() + xoffset;
		this.vertices[vertexOffset++] = 0 + yoffset;
		this.vertices[vertexOffset++] = 0 + zoffset;
		this.vertices[vertexOffset++] = u1;
		this.vertices[vertexOffset++] = v2;
		this.vertices[vertexOffset++] = 0;
		this.vertices[vertexOffset++] = 0;
		this.vertices[vertexOffset++] = 1;

		this.vertices[vertexOffset++] = 0.5f * this.e.getWidth() + xoffset;
		this.vertices[vertexOffset++] = 1f * this.e.getHeight() + yoffset;
		this.vertices[vertexOffset++] = 0 + zoffset;
		this.vertices[vertexOffset++] = u1;
		this.vertices[vertexOffset++] = v1;
		this.vertices[vertexOffset++] = 0;
		this.vertices[vertexOffset++] = 0;
		this.vertices[vertexOffset++] = 1;

		this.vertices[vertexOffset++] = -0.5f + xoffset;
		this.vertices[vertexOffset++] = 1f * this.e.getHeight() + yoffset;
		this.vertices[vertexOffset++] = 0 + zoffset;
		this.vertices[vertexOffset++] = u2;
		this.vertices[vertexOffset++] = v1;
		this.vertices[vertexOffset++] = 0;
		this.vertices[vertexOffset++] = 0;
		this.vertices[vertexOffset++] = 1;

		return vertexOffset / EuclideanObject.VERTEX_SIZE;
	}
}
