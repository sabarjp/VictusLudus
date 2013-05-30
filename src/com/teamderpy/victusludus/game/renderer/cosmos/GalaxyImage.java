package com.teamderpy.victusludus.game.renderer.cosmos;

import com.teamderpy.victusludus.engine.Actionable;
import com.teamderpy.victusludus.engine.graphics.ActionArea2D;
import com.teamderpy.victusludus.engine.graphics.BitmapHandler;
import com.teamderpy.victusludus.game.cosmos.EnumCosmosMode;
import com.teamderpy.victusludus.game.cosmos.Galaxy;
import com.teamderpy.victusludus.game.cosmos.Universe;
import com.teamderpy.victusludus.gui.GUICosmosHUD;

/**
 * A galaxy and its corresponding image to render
 * @author Josh
 *
 */
public class GalaxyImage {
	private Galaxy galaxy;
	private ActionArea2D actionArea;
	private CosmosRenderer cosmosRenderer;

	/**
	 * Instantiates a new galaxy image, which is basically the galaxy
	 * object combined with its rendered aspect
	 * 
	 * @param galaxy the galaxy
	 * @param cosmosRenderer the renderer
	 */
	public GalaxyImage(final Galaxy galaxy, final CosmosRenderer cosmosRenderer){
		this.galaxy = galaxy;
		this.cosmosRenderer = cosmosRenderer;

		Universe universe = cosmosRenderer.cosmos.getUniverse();

		int spriteWidth = cosmosRenderer.spriteSheetGalaxy.getWidth() / cosmosRenderer.spriteSheetGalaxy.getHorizontalCount() * 2;
		int spriteHeight = cosmosRenderer.spriteSheetGalaxy.getHeight() / cosmosRenderer.spriteSheetGalaxy.getVerticalCount() * 2;

		int x = (int) ((cosmosRenderer.cosmos.getGameDimensions().getWidth() - spriteWidth) / universe.getDiameter() * (galaxy.getxPosition() - galaxy.getRadius()));
		int y = (int) ((cosmosRenderer.cosmos.getGameDimensions().getHeight() - spriteHeight) / universe.getDiameter() * (galaxy.getyPosition() - galaxy.getRadius()));

		this.actionArea = new ActionArea2D(x, y, spriteWidth, spriteHeight);
		this.actionArea.setMouseEnterAct(new EnterAction());
		this.actionArea.setMouseLeaveAct(new LeaveAction());
		this.actionArea.setMouseClickAct(new ClickAction());
	}

	/**
	 * Returns the galaxy associated with this galaxyImage
	 * 
	 * @return the galaxy associated with this galaxyImage
	 */
	public Galaxy getGalaxy() {
		return this.galaxy;
	}

	/**
	 * Returns the ActionArea2D associated with this galaxyImage
	 * 
	 * @return the ActionArea2D associated with this galaxyImage
	 */
	public ActionArea2D getActionArea() {
		return this.actionArea;
	}

	/**
	 * Renders the galaxyImage
	 * 
	 * @param deltaT the amount of time since the last render
	 */
	public void render(final float deltaT){
		this.cosmosRenderer.spriteSheetGalaxy.startUse();

		this.galaxy.setRotation(this.galaxy.getRotation() + deltaT * this.galaxy.getAngularVelocity());

		this.cosmosRenderer.spriteSheetGalaxy.renderInUse(this.getActionArea().getX(), this.getActionArea().getY(),
				this.getActionArea().getWidth(),
				this.getActionArea().getHeight(),
				(float) this.galaxy.getRotation(),
				BitmapHandler.getSpriteSheetCol(this.galaxy.getGalaxyType().getSpriteIndex(), this.cosmosRenderer.spriteSheetGalaxy),
				BitmapHandler.getSpriteSheetRow(this.galaxy.getGalaxyType().getSpriteIndex(), this.cosmosRenderer.spriteSheetGalaxy));

		this.cosmosRenderer.spriteSheetGalaxy.endUse();
	}

	private class EnterAction implements Actionable{
		@Override
		public void act() {
			System.err.println("enter galaxy " + GalaxyImage.this.getGalaxy());
			System.err.println("num stars: " + GalaxyImage.this.getGalaxy().getStars().size());

			((GUICosmosHUD) GalaxyImage.this.cosmosRenderer.cosmos.getCurrentGUI()).setSelectedGalaxyType(GalaxyImage.this.getGalaxy().getGalaxyType().name());
		}
	}

	private class LeaveAction implements Actionable{
		@Override
		public void act() {
			System.err.println("leave " + GalaxyImage.this.getGalaxy());
		}
	}

	private class ClickAction implements Actionable{
		@Override
		public void act() {
			System.err.println("click " + GalaxyImage.this.getGalaxy());
			GalaxyImage.this.cosmosRenderer.cosmos.setGalaxy(GalaxyImage.this.galaxy);
			GalaxyImage.this.cosmosRenderer.changePerspective(EnumCosmosMode.GALAXY_PERSPECTIVE);
		}
	}

	@Override
	public void finalize(){
		this.actionArea.unregisterListeners();
	}
}
