
package com.teamderpy.victusludus.game.renderer.cosmos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.teamderpy.victusludus.game.GameDimensions;

public class StarRenderer implements IUniverseRenderer {
	private GameDimensions gameDimensions;
	private Array<StarImage> starList;

	public StarRenderer (final GameDimensions gameDimensions, final Array<StarImage> starList) {
		this.gameDimensions = gameDimensions;
		this.starList = starList;
	}

	@Override
	public void render (final SpriteBatch batch, final float deltaT) {
		for (StarImage starImage : this.starList) {
			starImage.render(batch, deltaT);
		}
	}
}
