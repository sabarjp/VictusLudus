
package com.teamderpy.victusludus.renderer.cosmos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.VictusRuntimeException;
import com.teamderpy.victusludus.engine.graphics.GameDimensions;
import com.teamderpy.victusludus.game.cosmos.Star;

public class SolarSystemRenderer implements IUniverseRenderer {
	public static String STAR_5_IMAGE_PATH = "planet/sun_very_large";
	public static String STAR_4_IMAGE_PATH = "planet/sun_large";
	public static String STAR_3_IMAGE_PATH = "planet/sun_medium";
	public static String STAR_2_IMAGE_PATH = "planet/sun_small";
	public static String STAR_1_IMAGE_PATH = "planet/sun_tiny";
	public static String STAR_0_IMAGE_PATH = "planet/sun_point";

	public static String BACKGROUND_PATH = "planet/background_solar";

	private GameDimensions gameDimensions;
	private Array<PlanetImage> planetList;
	private Sprite starSprite;

	public SolarSystemRenderer (final GameDimensions gameDimensions, final Array<PlanetImage> planetList, final Star star) {
		this.gameDimensions = gameDimensions;
		this.planetList = planetList;

		PlanetImage.prepareShader();

		double scale = star.getRelativeScale();
		String imageToUse = "";

		if (scale < 0.10) {
			imageToUse = SolarSystemRenderer.STAR_0_IMAGE_PATH;
		} else if (scale < 0.40) {
			imageToUse = SolarSystemRenderer.STAR_1_IMAGE_PATH;
		} else if (scale < 0.80) {
			imageToUse = SolarSystemRenderer.STAR_2_IMAGE_PATH;
		} else if (scale < 1.50) {
			imageToUse = SolarSystemRenderer.STAR_3_IMAGE_PATH;
		} else if (scale < 3.00) {
			imageToUse = SolarSystemRenderer.STAR_4_IMAGE_PATH;
		} else {
			imageToUse = SolarSystemRenderer.STAR_5_IMAGE_PATH;
		}

		this.starSprite = VictusLudusGame.resources.getTextureAtlasCosmos().createSprite(imageToUse);
		if (this.starSprite == null) {
			throw new VictusRuntimeException("Failed to load sprite: " + imageToUse);
		}

		int spriteWidth = (int)(this.starSprite.getWidth());
		int spriteHeight = (int)(this.starSprite.getHeight());
		int offset = 0;

		offset = spriteWidth - (PlanetImage.RESERVED_STAR_AREA_WIDTH / 2);

		// System.err.println(imageToUse + " " + offset);

		this.starSprite.setColor(star.getStarColor());

		this.starSprite.setBounds(0 - offset, this.gameDimensions.getRenderHeight() / 2 - (spriteHeight / 2), spriteWidth,
			spriteHeight);
	}

	@Override
	public void render (final SpriteBatch batch, final float deltaT) {
		for (PlanetImage planetImage : this.planetList) {
			planetImage.render(batch, deltaT);
		}

		this.starSprite.draw(batch);
	}

	@Override
	public void finalize () {
		this.dispose();
	}

	/**
	 * Dispose of any resources associated with this object
	 */
	public void dispose () {
		PlanetImage.disposeShader();
	}
}
