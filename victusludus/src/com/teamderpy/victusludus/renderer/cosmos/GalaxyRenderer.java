
package com.teamderpy.victusludus.renderer.cosmos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.engine.graphics.GameDimensions;

public class GalaxyRenderer implements IUniverseRenderer {
	public static String BACKGROUND_PATH = "galaxy/background_universe";

	private GameDimensions gameDimensions;
	private Array<GalaxyImage> galaxyList;

	public GalaxyRenderer (final GameDimensions gameDimensions, final Array<GalaxyImage> galaxyList) {
		this.gameDimensions = gameDimensions;
		this.galaxyList = galaxyList;

		GalaxyImage.prepareShader();
	}

	@Override
	public void render (final SpriteBatch batch, final float deltaT) {
		if (VictusLudusGame.engine.IS_SHADERS_ENABLED) {
			batch.flush();

			GalaxyImage.getShader().begin();
			GalaxyImage.getShader().end();

			batch.setShader(GalaxyImage.getShader());
		}

		for (GalaxyImage galaxyImage : this.galaxyList) {
			galaxyImage.render(batch, deltaT);
		}

		if (VictusLudusGame.engine.IS_SHADERS_ENABLED) {
			batch.setShader(null);
			// GalaxyImage.getShader().end();
		}
	}

	@Override
	public void finalize () {
		this.dispose();
	}

	/**
	 * Dispose of any resources associated with this object
	 */
	public void dispose () {
		GalaxyImage.disposeShader();
	}
}
