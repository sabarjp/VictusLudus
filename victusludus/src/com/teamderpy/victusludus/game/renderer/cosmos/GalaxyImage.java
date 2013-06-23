
package com.teamderpy.victusludus.game.renderer.cosmos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.engine.Actionable;
import com.teamderpy.victusludus.engine.graphics.ActionArea2D;
import com.teamderpy.victusludus.game.cosmos.Cosmology;
import com.teamderpy.victusludus.game.cosmos.EnumCosmosMode;
import com.teamderpy.victusludus.game.cosmos.Galaxy;
import com.teamderpy.victusludus.game.cosmos.Universe;
import com.teamderpy.victusludus.gui.UIUniverseHUD;

/**
 * A galaxy and its corresponding image to render
 * @author Josh
 */
public class GalaxyImage {
	private Galaxy galaxy;
	private ActionArea2D actionArea;
	private CosmosRenderer cosmosRenderer;
	private Sprite sprite;

	/**
	 * Instantiates a new galaxy image, which is basically the galaxy object combined with its rendered aspect
	 * 
	 * @param galaxy the galaxy
	 * @param cosmosRenderer the renderer
	 */
	public GalaxyImage (final Galaxy galaxy, final CosmosRenderer cosmosRenderer) {
		this.galaxy = galaxy;
		this.cosmosRenderer = cosmosRenderer;

		Universe universe = cosmosRenderer.cosmos.getUniverse();

		this.sprite = VictusLudusGame.resources.getTextureAtlasCosmos().createSprite(galaxy.getGalaxyType().getPath());

		int spriteWidth = (int)(this.sprite.getWidth() * 1);
		int spriteHeight = (int)(this.sprite.getHeight() * 1);

		int x = (int)((cosmosRenderer.cosmos.getGameDimensions().getWidth() - spriteWidth) / universe.getDiameter() * (galaxy
			.getxPosition() - galaxy.getRadius()));
		int y = (int)((cosmosRenderer.cosmos.getGameDimensions().getHeight() - spriteHeight) / universe.getDiameter() * (galaxy
			.getyPosition() - galaxy.getRadius()));

		this.actionArea = new ActionArea2D(x, y, spriteWidth, spriteHeight);
		this.actionArea.setMouseEnterAct(new EnterAction());
		this.actionArea.setMouseLeaveAct(new LeaveAction());
		this.actionArea.setMouseClickAct(new ClickAction());

		this.sprite.setBounds(x, y, spriteWidth, spriteHeight);
	}

	/**
	 * Returns the galaxy associated with this galaxyImage
	 * 
	 * @return the galaxy associated with this galaxyImage
	 */
	public Galaxy getGalaxy () {
		return this.galaxy;
	}

	/**
	 * Returns the ActionArea2D associated with this galaxyImage
	 * 
	 * @return the ActionArea2D associated with this galaxyImage
	 */
	public ActionArea2D getActionArea () {
		return this.actionArea;
	}

	/**
	 * Renders the galaxyImage
	 * 
	 * @param deltaT the amount of time since the last render
	 */
	public void render (final SpriteBatch batch, final float deltaT) {
		this.galaxy.setRotation(this.galaxy.getRotation() + deltaT * this.galaxy.getAngularVelocity());

		this.sprite.setRotation((float)this.galaxy.getRotation());

		this.sprite.draw(batch);
	}

	private class EnterAction implements Actionable {
		@Override
		public void act () {
			UIUniverseHUD gui = ((UIUniverseHUD)GalaxyImage.this.cosmosRenderer.cosmos.getCurrentUI());
			Galaxy galaxy = GalaxyImage.this.getGalaxy();

			gui.setSelectedGalaxyName(galaxy.getName());
			gui.setSelectedGalaxyType(galaxy.getGalaxyType().getProperName());
			gui.setSelectedGalaxyAge(Cosmology.getFormattedStellarAge(galaxy.getAge()));
			gui.setSelectedGalaxyStarCount(Integer.toString(galaxy.getMaxStars()));
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
			GalaxyImage.this.cosmosRenderer.cosmos.setGalaxy(GalaxyImage.this.galaxy);
			GalaxyImage.this.cosmosRenderer.changePerspective(EnumCosmosMode.GALAXY_PERSPECTIVE);
		}
	}

	@Override
	public void finalize () {
		this.actionArea.unregisterListeners();
	}
}
