package com.teamderpy.victusludus.game.entity;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.game.Vec2i;
import com.teamderpy.victusludus.game.WorldCoord;
import com.teamderpy.victusludus.game.renderer.Light;
import com.teamderpy.victusludus.parts.Entity;

// TODO: Auto-generated Javadoc
/**
 * The Class GameEntity.
 */
public class GameEntity {
	
	/** The Constant ID_THING. */
	public static final byte ID_THING        = 0x0;
	
	/** The Constant ID_THING2. */
	public static final byte ID_THING2       = 0x1;

	/** The e. */
	private Entity e;
	
	/** The pos. */
	private WorldCoord pos = new WorldCoord(-1,-1,-1);
	
	/** The entity light. */
	private Light entityLight;
	
	/** The movement vector. */
	private Vec2i movementVector;
	
	/** The creation time. */
	private long creationTime;

	/**
	 * Instantiates a new game entity.
	 *
	 * @param entity the entity
	 * @param pos the pos
	 */
	public GameEntity(final Entity entity, final WorldCoord pos){
		this.e = entity;
		this.pos = pos;
		this.creationTime = VictusLudus.e.getTickCount();
	}

	/**
	 * Instantiates a new game entity.
	 *
	 * @param entityID the entity id
	 */
	public GameEntity(final String entityID){
		this.e = VictusLudus.resources.getEntityHash().get(entityID);
		this.creationTime = VictusLudus.e.getTickCount();
	}

	/**
	 * Instantiates a new game entity.
	 *
	 * @param entityID the entity id
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public GameEntity(final String entityID, final int x, final int y, final int z){
		this.e = VictusLudus.resources.getEntityHash().get(entityID);

		this.pos = new WorldCoord(x, y, z);

		if(this.e.getLight() != null){
			this.entityLight = new Light(this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.e.getLight().getStrength(), this.e.getLight().getColor());
			this.entityLight.setBrightness(this.e.getLight().getBrightness());
			VictusLudus.e.currentGame.getMap().getLightMap().add(this.entityLight);
		}

		this.movementVector = new Vec2i(0, 0);
		this.creationTime = VictusLudus.e.getTickCount();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return this.e.getId();
	}

	/**
	 * Gets the pos.
	 *
	 * @return the pos
	 */
	public WorldCoord getPos() {
		return this.pos;
	}

	/**
	 * Sets the pos.
	 *
	 * @param pos the new pos
	 */
	protected void setPos(final WorldCoord pos) {
		this.pos = pos;
	}

	/**
	 * Gets the entity.
	 *
	 * @return the entity
	 */
	public Entity getEntity(){
		return this.e;
	}

	/**
	 * Tick.
	 */
	public void tick(){
		this.getEntity().tick(this);
	}

	/**
	 * Gets the entity light.
	 *
	 * @return the entity light
	 */
	public Light getEntityLight() {
		return this.entityLight;
	}

	/**
	 * Gets the movement vector.
	 *
	 * @return the movement vector
	 */
	public Vec2i getMovementVector() {
		return this.movementVector;
	}

	/**
	 * Sets the movement vector.
	 *
	 * @param movementVector the new movement vector
	 */
	public void setMovementVector(final Vec2i movementVector) {
		this.movementVector = movementVector;
	}

	/**
	 * Gets the creation time.
	 *
	 * @return the creation time
	 */
	public long getCreationTime() {
		return this.creationTime;
	}
}
