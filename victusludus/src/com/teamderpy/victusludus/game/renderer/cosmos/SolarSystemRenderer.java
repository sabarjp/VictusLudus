
package com.teamderpy.victusludus.game.renderer.cosmos;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.VictusRuntimeException;
import com.teamderpy.victusludus.game.GameDimensions;
import com.teamderpy.victusludus.game.cosmos.Star;

public class SolarSystemRenderer implements IUniverseRenderer {
	public static String BIG_STAR_IMAGE_PATH = "star/sun";
	public static String BACKGROUND_PATH = "background/background_solar";

	private GameDimensions gameDimensions;
	private ArrayList<PlanetImage> planetList;
	private Sprite starSprite;

	public SolarSystemRenderer (final GameDimensions gameDimensions, final ArrayList<PlanetImage> planetList, final Star star) {
		this.gameDimensions = gameDimensions;
		this.planetList = planetList;

		this.starSprite = VictusLudusGame.resources.getTextureAtlasCosmos().createSprite(SolarSystemRenderer.BIG_STAR_IMAGE_PATH);
		if (this.starSprite == null) {
			throw new VictusRuntimeException("Failed to load sprite: " + SolarSystemRenderer.BIG_STAR_IMAGE_PATH);
		}

		double scale = star.getRelativeScale();

		int spriteWidth = (int)(this.starSprite.getWidth() * scale);
		int spriteHeight = (int)(this.starSprite.getHeight() * scale);
		int offset = Math.max(spriteWidth - 128, 0);

		this.starSprite.setColor(star.getStarColor());

		this.starSprite.setBounds(0 - offset, this.gameDimensions.getHeight() / 2 - (spriteHeight / 2), spriteWidth, spriteHeight);
	}

	@Override
	public void render (final SpriteBatch batch, final float deltaT) {
		for (PlanetImage planetImage : this.planetList) {
			planetImage.render(batch, deltaT);
		}

		this.starSprite.draw(batch);
	}
}
