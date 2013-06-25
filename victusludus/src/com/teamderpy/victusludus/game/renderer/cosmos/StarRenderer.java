
package com.teamderpy.victusludus.game.renderer.cosmos;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamderpy.victusludus.game.GameDimensions;

public class StarRenderer implements IUniverseRenderer {
	private GameDimensions gameDimensions;
	private ArrayList<StarImage> starList;

	public StarRenderer (final GameDimensions gameDimensions, final ArrayList<StarImage> starList) {
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
