
package com.teamderpy.victusludus.game.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.data.resources.EntityDefinition;
import com.teamderpy.victusludus.game.EuclideanObject;
import com.teamderpy.victusludus.game.Vec2i;
import com.teamderpy.victusludus.game.WorldCoord;
import com.teamderpy.victusludus.game.light.LightEmitter;
import com.teamderpy.victusludus.game.map.Map;

/** The Class GameEntity. */
public class GameEntity extends EuclideanObject {
	/** The entity definition that this bases its properties on. */
	private EntityDefinition e;

	/** The entity light. */
	private LightEmitter entityLight;

	/** The movement vector. */
	private Vec2i movementVector;

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
	 * @param pos the pos
	 * @param map the map the entity is a part of
	 */
	public GameEntity (final EntityDefinition entity, final WorldCoord pos, final Map map) {
		super(pos);

		this.map = map;

		this.e = entity;

		this.creationTime = VictusLudusGame.engine.getTickCount();
		this.movementVector = new Vec2i(0, 0);
		this.playAnimation("idle");
	}

	/**
	 * Instantiates a new game entity.
	 * 
	 * @param entityID the entity id
	 * @param map the map the entity is a part of
	 */
	public GameEntity (final String entityID, final Map map) {
		super();

		this.map = map;

		this.e = VictusLudusGame.resources.getEntityHash().get(entityID);

		this.creationTime = VictusLudusGame.engine.getTickCount();
		this.movementVector = new Vec2i(0, 0);
		this.playAnimation("idle");
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
	public GameEntity (final String entityID, final int x, final int y, final int z, final Map map) {
		super(x, y, z);

		this.map = map;

		this.e = VictusLudusGame.resources.getEntityHash().get(entityID);

		this.movementVector = new Vec2i(0, 0);
		this.creationTime = VictusLudusGame.engine.getTickCount();
		this.playAnimation("idle");
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
	 * Gets the pos.
	 * 
	 * @return the pos
	 */
	public WorldCoord getPos () {
		return super.getWorldCoord();
	}

	/**
	 * Sets the pos.
	 * 
	 * @param pos the new pos
	 */
	protected void setPos (final WorldCoord pos) {
		super.setWorldCoord(pos);
	}

	/**
	 * Gets the entity.
	 * 
	 * @return the entity
	 */
	public EntityDefinition getEntity () {
		return this.e;
	}

	/** Tick. */
	public void tick () {
		this.getEntity().tick(this, this.map);
	}

	/**
	 * Gets the entity light.
	 * 
	 * @return the entity light
	 */
	public LightEmitter getEntityLight () {
		return this.entityLight;
	}

	/**
	 * Gets the movement vector.
	 * 
	 * @return the movement vector
	 */
	public Vec2i getMovementVector () {
		return this.movementVector;
	}

	/**
	 * Sets the movement vector.
	 * 
	 * @param movementVector the new movement vector
	 */
	public void setMovementVector (final Vec2i movementVector) {
		this.movementVector = movementVector;
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
	 * Changes the animation to the one specified and starts it from frame zero.
	 * 
	 * @param str the name of the animation to play
	 */
	public void playAnimation (final String str) {
		this.currentAnimation = this.e.getAnimationHash().get(str);
	}

	/**
	 * Gets the current animation.
	 * 
	 * @return the current animation
	 */
	public TextureRegion getCurrentAnimation (final float deltaT) {
		this.animationTimer += deltaT;
		return this.currentAnimation.getKeyFrame(this.animationTimer);
	}
}
