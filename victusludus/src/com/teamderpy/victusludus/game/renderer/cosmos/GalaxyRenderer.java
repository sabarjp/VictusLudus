
package com.teamderpy.victusludus.game.renderer.cosmos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.teamderpy.victusludus.game.GameDimensions;

public class GalaxyRenderer implements IUniverseRenderer {
	public static String BACKGROUND_PATH = "background/background_universe";

	private GameDimensions gameDimensions;
	private Array<GalaxyImage> galaxyList;

	public GalaxyRenderer (final GameDimensions gameDimensions, final Array<GalaxyImage> galaxyList) {
		this.gameDimensions = gameDimensions;
		this.galaxyList = galaxyList;
	}

	@Override
	public void render (final SpriteBatch batch, final float deltaT) {
		for (GalaxyImage galaxyImage : this.galaxyList) {
			galaxyImage.render(batch, deltaT);
		}
	}
}
