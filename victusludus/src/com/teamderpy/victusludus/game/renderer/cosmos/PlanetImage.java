
package com.teamderpy.victusludus.game.renderer.cosmos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.VictusRuntimeException;
import com.teamderpy.victusludus.engine.Actionable;
import com.teamderpy.victusludus.engine.graphics.ActionArea2D;
import com.teamderpy.victusludus.game.cosmos.Cosmology;
import com.teamderpy.victusludus.game.cosmos.EnumCosmosMode;
import com.teamderpy.victusludus.game.cosmos.EnumPlanetType;
import com.teamderpy.victusludus.game.cosmos.Planet;
import com.teamderpy.victusludus.game.cosmos.Star;
import com.teamderpy.victusludus.gui.UIStarHUD;

/**
 * A planet and its corresponding image to render
 * @author Josh
 */
public class PlanetImage {
	public static String ORBIT_LINE_PATH = "planet/orbital_line";

	private Planet planet;
	private ActionArea2D actionArea;
	private CosmosRenderer cosmosRenderer;
	private Sprite sprite;
	private Sprite orbit;

	private double rotation = 0;
	private double angularVelocity = 0;

	/**
	 * Instantiates a new galaxy image, which is basically the galaxy object combined with its rendered aspect
	 * 
	 * @param galaxy the galaxy
	 * @param cosmosRenderer the renderer
	 */
	public PlanetImage (final Planet planet, final CosmosRenderer cosmosRenderer) {
		this.planet = planet;
		this.cosmosRenderer = cosmosRenderer;

		Star star = cosmosRenderer.cosmos.getStar();

		this.sprite = VictusLudusGame.resources.getTextureAtlasCosmos().createSprite(planet.getPlanetType().getPath());
		if (this.sprite == null) {
			throw new VictusRuntimeException("Failed to load sprite: " + planet.getPlanetType().getPath());
		}

		this.orbit = VictusLudusGame.resources.getTextureAtlasCosmos().createSprite(PlanetImage.ORBIT_LINE_PATH);
		if (this.orbit == null) {
			throw new VictusRuntimeException("Failed to load sprite: " + PlanetImage.ORBIT_LINE_PATH);
		}

		if (this.planet.getPlanetType() == EnumPlanetType.ASTEROID_FIELD) {
			VictusLudusGame.sharedRand.setSeed(planet.getSeed() + 3241111);
			this.rotation = VictusLudusGame.sharedRand.nextDouble() * 360;
			this.angularVelocity = VictusLudusGame.sharedRand.nextDouble() * 10;
		}

		double scale = planet.getRelativeScale();

		int spriteWidth = (int)(this.sprite.getWidth() * scale);
		int spriteHeight = (int)(this.sprite.getHeight() * scale);

		int x = 256 + (int)(planet.getOrbitSemiMajorAxis().subtract(star.getRadius())
			.divide(Planet.MAX_ORBITAL_DISTANCE, Planet.PLANET_RND).doubleValue() * (cosmosRenderer.cosmos.getGameDimensions()
			.getWidth() - 256));

		int y = cosmosRenderer.cosmos.getGameDimensions().getHeight() / 2 - (spriteHeight / 2);

		this.actionArea = new ActionArea2D(x, y, spriteWidth, spriteHeight);
		this.actionArea.setMouseEnterAct(new EnterAction());
		this.actionArea.setMouseLeaveAct(new LeaveAction());
		this.actionArea.setMouseClickAct(new ClickAction());

		this.sprite.setBounds(x, y, spriteWidth, spriteHeight);
		this.orbit.setBounds(x + (spriteWidth / 2 - this.orbit.getWidth() / 2), 0, this.orbit.getWidth(), cosmosRenderer.cosmos
			.getGameDimensions().getHeight());
	}

	public void setRotation (final double rotation) {
		if (rotation > 360) {
			this.rotation = rotation - (360 * ((int)rotation / 360));
		} else if (rotation < -360) {
			this.rotation = rotation + (360 * ((int)rotation / 360));
		} else {
			this.rotation = rotation;
		}
	}

	/**
	 * Returns the planet associated with this planetImage
	 * 
	 * @return the planet associated with this planetImage
	 */
	public Planet getPlanet () {
		return this.planet;
	}

	/**
	 * Returns the ActionArea2D associated with this planetImage
	 * 
	 * @return the ActionArea2D associated with this planetImage
	 */
	public ActionArea2D getActionArea () {
		return this.actionArea;
	}

	/**
	 * Renders the planetImage
	 * 
	 * @param deltaT the amount of time since the last render
	 */
	public void render (final SpriteBatch batch, final float deltaT) {
		this.orbit.draw(batch);

		this.setRotation(this.rotation + deltaT * this.angularVelocity);
		this.sprite.setRotation((float)this.rotation);

		this.sprite.draw(batch);
	}

	private class EnterAction implements Actionable {
		@Override
		public void act () {
			UIStarHUD gui = ((UIStarHUD)PlanetImage.this.cosmosRenderer.cosmos.getCurrentUI());
			Planet planet = PlanetImage.this.getPlanet();

			gui.setSelectedPlanetName(planet.getName());
			gui.setSelectedPlanetType(planet.getPlanetType().getProperName());
			gui.setSelectedPlanetAge(Cosmology.getFormattedStellarAge(planet.getAge()));
		}
	}

	private class LeaveAction implements Actionable {
		@Override
		public void act () {

		}
	}

	private class ClickAction implements Actionable {
		@Override
		public void act () {
			PlanetImage.this.cosmosRenderer.cosmos.getStar().removePlanets();
			PlanetImage.this.cosmosRenderer.cosmos.setStar(null);
			PlanetImage.this.cosmosRenderer.changePerspective(EnumCosmosMode.GALAXY_PERSPECTIVE);
		}
	}

	@Override
	public void finalize () {
		this.actionArea.unregisterListeners();
	}

	public void dispose () {
		this.actionArea.unregisterListeners();
	}
}
