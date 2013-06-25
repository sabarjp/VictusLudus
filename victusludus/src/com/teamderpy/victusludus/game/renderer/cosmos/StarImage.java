
package com.teamderpy.victusludus.game.renderer.cosmos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.VictusRuntimeException;
import com.teamderpy.victusludus.engine.Actionable;
import com.teamderpy.victusludus.engine.graphics.ActionArea2D;
import com.teamderpy.victusludus.game.cosmos.Cosmology;
import com.teamderpy.victusludus.game.cosmos.EnumCosmosMode;
import com.teamderpy.victusludus.game.cosmos.Galaxy;
import com.teamderpy.victusludus.game.cosmos.Star;
import com.teamderpy.victusludus.gui.UIGalaxyHUD;

/**
 * A star and its corresponding image to render
 * @author Josh
 */
public class StarImage {
	private Star star;
	private ActionArea2D actionArea;
	private CosmosRenderer cosmosRenderer;
	private Sprite sprite;

	/**
	 * Instantiates a new galaxy image, which is basically the galaxy object combined with its rendered aspect
	 * 
	 * @param galaxy the galaxy
	 * @param cosmosRenderer the renderer
	 */
	public StarImage (final Star star, final CosmosRenderer cosmosRenderer) {
		this.star = star;
		this.cosmosRenderer = cosmosRenderer;

		Galaxy galaxy = cosmosRenderer.cosmos.getGalaxy();

		this.sprite = VictusLudusGame.resources.getTextureAtlasCosmos().createSprite(star.getStarType().getPath());
		if (this.sprite == null) {
			throw new VictusRuntimeException("Failed to load sprite: " + star.getStarType().getPath());
		}

		float galaxyStartX = (float)(galaxy.getxPosition() - galaxy.getRadius());
		float galaxyEndX = (float)(galaxy.getxPosition() + galaxy.getRadius());
		float galaxyStartY = (float)(galaxy.getyPosition() - galaxy.getRadius());
		float galaxyEndY = (float)(galaxy.getyPosition() + galaxy.getRadius());

		int spriteWidth = (int)(this.sprite.getWidth() * 2);
		int spriteHeight = (int)(this.sprite.getHeight() * 2);

		int x = (int)((cosmosRenderer.cosmos.getGameDimensions().getWidth() - spriteWidth) / ((galaxyEndX - galaxyStartX) / (star
			.getxPosition() - star.getRadius().doubleValue() - galaxyStartX)));
		int y = (int)((cosmosRenderer.cosmos.getGameDimensions().getHeight() - spriteHeight) / ((galaxyEndY - galaxyStartY) / (star
			.getyPosition() - star.getRadius().doubleValue() - galaxyStartY)));

		this.actionArea = new ActionArea2D(x, y, spriteWidth, spriteHeight);
		this.actionArea.setMouseEnterAct(new EnterAction());
		this.actionArea.setMouseLeaveAct(new LeaveAction());
		this.actionArea.setMouseClickAct(new ClickAction());

		// if (this.getStar().getStarType() != EnumStarType.BLACK_HOLE) {
		this.sprite.setColor(this.star.getStarColor());
		// }

		this.sprite.setBounds(x, y, spriteWidth, spriteHeight);
	}

	/**
	 * Returns the star associated with this galaxyImage
	 * 
	 * @return the star associated with this galaxyImage
	 */
	public Star getStar () {
		return this.star;
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
		this.sprite.draw(batch);
	}

	private class EnterAction implements Actionable {
		@Override
		public void act () {
			UIGalaxyHUD gui = ((UIGalaxyHUD)StarImage.this.cosmosRenderer.cosmos.getCurrentUI());
			Star star = StarImage.this.getStar();

			gui.setSelectedStarName(star.getName());
			gui.setSelectedStarType(star.getStarType().getProperName() + " - " + star.getSpectralClass());
			gui.setSelectedStarAge(Cosmology.getFormattedStellarAge(star.getAge()));
			gui.setSelectedStarPlanetCount(Integer.toString(star.getMaxPlanets()));

			System.err.println(star.getStarColor());
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
			StarImage.this.cosmosRenderer.cosmos.setStar(StarImage.this.star);
			StarImage.this.cosmosRenderer.changePerspective(EnumCosmosMode.STAR_PERSPECTIVE);
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
