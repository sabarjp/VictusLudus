package com.teamderpy.victusludus.game.light;

import org.newdawn.slick.Color;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.game.EuclideanObject;
import com.teamderpy.victusludus.game.ScreenCoord;
import com.teamderpy.victusludus.game.WorldCoord;
import com.teamderpy.victusludus.game.renderer.RenderUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class LightEmitter which is an object that emits light onto nearby objects.
 */
public class LightEmitter extends EuclideanObject{

	/** The strength. */
	private int strength;

	/** The brightness. */
	private float brightness;

	/** The color. */
	private Color color;

	/**
	 * Instantiates a new light.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @param strength the strength
	 * @param color the color
	 */
	public LightEmitter(final int x, final int y, final int z, final int strength, final Color color){
		this(x, y, z, strength);
		this.color = color;
	}

	/**
	 * Instantiates a new light.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @param strength the strength
	 */
	public LightEmitter(final int x, final int y, final int z, final int strength){
		this.setStrength(strength);
		this.setPos(new WorldCoord(x, y, z));
		this.color = Color.white;
		this.brightness = 0.25F;
	}

	/**
	 * Gets the strength.
	 *
	 * @return the strength
	 */
	public int getStrength() {
		return this.strength;
	}

	/**
	 * Sets the strength.
	 *
	 * @param strength the new strength
	 */
	public void setStrength(final int strength) {
		this.strength = strength;
	}

	/**
	 * Gets the pos.
	 *
	 * @return the pos
	 */
	public WorldCoord getPos() {
		return super.getWorldCoord();
	}

	/**
	 * Sets the pos.
	 *
	 * @param pos the new pos
	 */
	public void setPos(final WorldCoord pos) {
		super.setWorldCoord(pos);
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Sets the color.
	 *
	 * @param color the new color
	 */
	public void setColor(final Color color) {
		this.color = color;
	}

	/**
	 * Gets the brightness.
	 *
	 * @return the brightness
	 */
	public float getBrightness() {
		return this.brightness;
	}

	/**
	 * Sets the brightness.
	 *
	 * @param brightness the new brightness
	 */
	public void setBrightness(final float brightness) {
		this.brightness = brightness;
	}

	/**
	 * Calculate light to.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the float
	 */
	public float calculateLightTo(final double x, final double y){
		ScreenCoord sc = RenderUtil.worldCoordToScreenCoord(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());

		//center the light on the tile
		sc.x = sc.x + VictusLudus.e.currentGame.getTileWidthS()/2;

		//put the light at the correct height
		sc.y = sc.y + VictusLudus.e.currentGame.getTileHeightS()/2;

		double d1 = x-sc.x;
		double d2 = 2*(y-sc.y);

		double dist = Math.sqrt(d1*d1 + d2*d2) / VictusLudus.e.currentGame.getGameCamera().getZoom();

		float alpha = (float) Math.max(0.0F ,1.0F - dist/this.getStrength());

		return alpha;
	}
}
