package com.teamderpy.victusludus.game.renderer.cosmos;

import com.teamderpy.victusludus.engine.Actionable;
import com.teamderpy.victusludus.engine.graphics.ActionArea2D;
import com.teamderpy.victusludus.engine.graphics.BitmapHandler;
import com.teamderpy.victusludus.game.cosmos.EnumCosmosMode;
import com.teamderpy.victusludus.game.cosmos.Galaxy;
import com.teamderpy.victusludus.game.cosmos.Star;

/**
 * A star and its corresponding image to render
 * @author Josh
 *
 */
public class StarImage {
	private Star star;
	private ActionArea2D actionArea;
	private CosmosRenderer cosmosRenderer;

	/**
	 * Instantiates a new galaxy image, which is basically the galaxy
	 * object combined with its rendered aspect
	 * 
	 * @param galaxy the galaxy
	 * @param cosmosRenderer the renderer
	 */
	public StarImage(final Star star, final CosmosRenderer cosmosRenderer){
		this.star = star;
		this.cosmosRenderer = cosmosRenderer;

		Galaxy galaxy = cosmosRenderer.cosmos.getGalaxy();

		float galaxyStartX = (float) (galaxy.getxPosition() - galaxy.getRadius());
		float galaxyEndX   = (float) (galaxy.getxPosition() + galaxy.getRadius());
		float galaxyStartY = (float) (galaxy.getyPosition() - galaxy.getRadius());
		float galaxyEndY   = (float) (galaxy.getyPosition() + galaxy.getRadius());

		int spriteWidth = cosmosRenderer.spriteSheetStar.getWidth() / cosmosRenderer.spriteSheetStar.getHorizontalCount() * 2;
		int spriteHeight = cosmosRenderer.spriteSheetStar.getHeight() / cosmosRenderer.spriteSheetStar.getVerticalCount() * 2;

		int x = (int) ((cosmosRenderer.cosmos.getGameDimensions().getWidth() - spriteWidth) / ((galaxyEndX - galaxyStartX) / (star.getxPosition() - star.getRadius().doubleValue() - galaxyStartX)));
		int y = (int) ((cosmosRenderer.cosmos.getGameDimensions().getHeight() - spriteHeight) / ((galaxyEndY - galaxyStartY) / (star.getyPosition() - star.getRadius().doubleValue() - galaxyStartY)));

		this.actionArea = new ActionArea2D(x, y, spriteWidth, spriteHeight);
		this.actionArea.setMouseEnterAct(new EnterAction());
		this.actionArea.setMouseLeaveAct(new LeaveAction());
		this.actionArea.setMouseClickAct(new ClickAction());
	}

	/**
	 * Returns the star associated with this galaxyImage
	 * 
	 * @return the star associated with this galaxyImage
	 */
	public Star getStar() {
		return this.star;
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
		this.cosmosRenderer.spriteSheetStar.startUse();

		//this.galaxy.setRotation(this.galaxy.getRotation() + deltaT * this.galaxy.getAngularVelocity());

		this.getStar().getStarColor().bind();

		this.cosmosRenderer.spriteSheetStar.renderInUse(this.getActionArea().getX(), this.getActionArea().getY(),
				this.getActionArea().getWidth(),
				this.getActionArea().getHeight(),
				0,
				BitmapHandler.getSpriteSheetCol(this.star.getStarType().getSpriteIndex(), this.cosmosRenderer.spriteSheetStar),
				BitmapHandler.getSpriteSheetRow(this.star.getStarType().getSpriteIndex(), this.cosmosRenderer.spriteSheetStar));

		this.cosmosRenderer.spriteSheetStar.endUse();
	}

	private class EnterAction implements Actionable{
		@Override
		public void act() {
			System.err.println("enter star " + StarImage.this.getStar());
		}
	}

	private class LeaveAction implements Actionable{
		@Override
		public void act() {
			System.err.println("leave " + StarImage.this.getStar());
		}
	}

	private class ClickAction implements Actionable{
		@Override
		public void act() {
			System.err.println("click " + StarImage.this.getStar());
			StarImage.this.cosmosRenderer.cosmos.setGalaxy(null);
			StarImage.this.cosmosRenderer.changePerspective(EnumCosmosMode.UNIVERSE_PERSPECTIVE);
		}
	}

	@Override
	public void finalize(){
		this.actionArea.unregisterListeners();
	}
}
