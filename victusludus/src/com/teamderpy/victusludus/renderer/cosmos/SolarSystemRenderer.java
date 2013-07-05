
package com.teamderpy.victusludus.renderer.cosmos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.VictusRuntimeException;
import com.teamderpy.victusludus.engine.graphics.GameDimensions;
import com.teamderpy.victusludus.game.cosmos.Star;

public class SolarSystemRenderer implements IUniverseRenderer {
	public static String BIG_STAR_IMAGE_PATH = "star/sun";
	public static String BACKGROUND_PATH = "background/background_solar";

	private GameDimensions gameDimensions;
	private Array<PlanetImage> planetList;
	private Sprite starSprite;

	public SolarSystemRenderer (final GameDimensions gameDimensions, final Array<PlanetImage> planetList, final Star star) {
		this.gameDimensions = gameDimensions;
		this.planetList = planetList;

		PlanetImage.prepareShader();

		this.starSprite = VictusLudusGame.resources.getTextureAtlasCosmos().createSprite(SolarSystemRenderer.BIG_STAR_IMAGE_PATH);
		if (this.starSprite == null) {
			throw new VictusRuntimeException("Failed to load sprite: " + SolarSystemRenderer.BIG_STAR_IMAGE_PATH);
		}

		double scale = star.getRelativeScale();

		int spriteWidth = (int)(this.starSprite.getWidth() * scale);
		int spriteHeight = (int)(this.starSprite.getHeight() * scale);
		int offset = Math.max(spriteWidth - (PlanetImage.RESERVED_STAR_AREA_WIDTH / 2), 0);

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
