
package com.teamderpy.victusludus.game.renderer.cosmos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.VictusRuntimeException;
import com.teamderpy.victusludus.engine.Actionable;
import com.teamderpy.victusludus.engine.graphics.ActionArea2D;
import com.teamderpy.victusludus.game.cosmos.Cosmology;
import com.teamderpy.victusludus.game.cosmos.EnumCosmosMode;
import com.teamderpy.victusludus.game.cosmos.Planet;
import com.teamderpy.victusludus.game.cosmos.Star;
import com.teamderpy.victusludus.gui.UIStarHUD;

/**
 * A planet and its corresponding image to render
 * @author Josh
 */
public class PlanetImage {
	private Planet planet;
	private ActionArea2D actionArea;
	private CosmosRenderer cosmosRenderer;
	private Sprite sprite;

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

		double scale = planet.getRelativeScale();

		int spriteWidth = (int)(this.sprite.getWidth() * scale);
		int spriteHeight = (int)(this.sprite.getHeight() * scale);

		int x = (int)(planet.getOrbitSemiMajorAxis().subtract(star.getRadius())
			.divide(Planet.MAX_ORBITAL_DISTANCE, Planet.PLANET_RND).doubleValue() * cosmosRenderer.cosmos.getGameDimensions()
			.getWidth());

		int y = cosmosRenderer.cosmos.getGameDimensions().getHeight() / 2 - (spriteHeight / 2);

		this.actionArea = new ActionArea2D(x, y, spriteWidth, spriteHeight);
		this.actionArea.setMouseEnterAct(new EnterAction());
		this.actionArea.setMouseLeaveAct(new LeaveAction());
		this.actionArea.setMouseClickAct(new ClickAction());

		this.sprite.setBounds(x, y, spriteWidth, spriteHeight);
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
