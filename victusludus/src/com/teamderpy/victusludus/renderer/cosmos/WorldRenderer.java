
package com.teamderpy.victusludus.renderer.cosmos;

import java.nio.ByteBuffer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.OrderedMap;
import com.teamderpy.victusludus.engine.graphics.GameDimensions;
import com.teamderpy.victusludus.game.cosmos.Planet;
import com.teamderpy.victusludus.math.VMath;

public class WorldRenderer implements IUniverseRenderer {
	// public static String BIG_STAR_IMAGE_PATH = "star/sun";
	// public static String BACKGROUND_PATH = "background/background_solar";

	private GameDimensions gameDimensions;
	private Sprite worldSprite;

	// private Array<PlanetImage> planetList;
	// private Sprite starSprite;

	public WorldRenderer (final GameDimensions gameDimensions, final Planet planet) {
		this.gameDimensions = gameDimensions;

		Texture tex = this.createWorldTexture(planet);
		this.worldSprite = new Sprite(tex);

		float width = (float)(gameDimensions.getRenderWidth() * 0.80);
		float height = (float)(gameDimensions.getRenderHeight() * 0.80);

		this.worldSprite.setBounds((this.gameDimensions.getRenderWidth() - width) / 2,
			(this.gameDimensions.getRenderHeight() - height) / 2, width, height);
	}

	@Override
	public void render (final SpriteBatch batch, final float deltaT) {
		this.worldSprite.draw(batch);
	}

	/**
	 * Creates a texture map of the world given height data
	 * @param planet the planet that we take height data from
	 * @return the texture map
	 */
	private Texture createWorldTexture (final Planet planet) {
		float[][] worldHeightData = planet.getSurfaceWorld().getWorldHeightData();
		Pixmap imageBuffer = new Pixmap(worldHeightData.length, worldHeightData[0].length, Format.RGBA8888);
		ByteBuffer imageBufferByteArray = imageBuffer.getPixels();

		OrderedMap<Float, Color> tintMap = planet.getPlanetType().getTintMap();

		// fill texture
		for (int i = 0; i < worldHeightData.length; i++) {
			for (int j = 0; j < worldHeightData[i].length; j++) {
				int pos = 4 * (i + j * imageBuffer.getWidth());

				float data = worldHeightData[i][j];
				float upperRange = -1;
				Color upperValue = null;

				float lowerRange = -1;
				Color lowerValue = null;

				for (Entry<Float, Color> e : tintMap.entries()) {
					if (data <= upperRange && data >= e.key) {
						lowerRange = e.key;
						lowerValue = e.value;
						break;
					}

					upperRange = e.key;
					upperValue = e.value;
				}

				// normalize
				float red, green, blue;

				if (upperValue == null) {
					red = lowerValue.r;
					green = lowerValue.g;
					blue = lowerValue.b;
				} else {
					red = VMath.linearInterpolation(lowerRange, upperRange, lowerValue.r, upperValue.r, data);
					green = VMath.linearInterpolation(lowerRange, upperRange, lowerValue.g, upperValue.g, data);
					blue = VMath.linearInterpolation(lowerRange, upperRange, lowerValue.b, upperValue.b, data);
				}

				imageBufferByteArray.put(pos + 0, (byte)(255 * red));
				imageBufferByteArray.put(pos + 1, (byte)(255 * green));
				imageBufferByteArray.put(pos + 2, (byte)(255 * blue));
				imageBufferByteArray.put(pos + 3, (byte)255);
			}
		}

		return new Texture(imageBuffer);
	}

	@Override
	public void finalize () {
		// dump the texture of the sprite since it was generated on the fly
		this.worldSprite.getTexture().dispose();
	}
}
