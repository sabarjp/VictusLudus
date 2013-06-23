
package com.teamderpy.victusludus.game.renderer.cosmos;

import java.nio.ByteBuffer;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.teamderpy.victusludus.game.map.MidpointGenerator;

/**
 * Generates a nebula image
 * @author Josh
 */
public class NebulaGenerator {

	/**
	 * Generates a nebula
	 * @param seed the seed to use when generating
	 * @param width the requested width of the nebula
	 * @param height the requested height of the nebula
	 * @return
	 */
	public static Texture createBackgroundNebula (final long seed, final int width, final int height) {
		int modifier = 8;

		MidpointGenerator noiseGenerator = new MidpointGenerator(0.56F, seed);
		float[][] noiseArrayRed = noiseGenerator.generateFloat(width / modifier, height / modifier, 0F, 255F, true);

		noiseGenerator.seed(seed + 234556);
		float[][] noiseArrayGreen = noiseGenerator.generateFloat(width / modifier, height / modifier, 0F, 255F, true);

		noiseGenerator.seed(seed + 4353452);
		float[][] noiseArrayBlue = noiseGenerator.generateFloat(width / modifier, height / modifier, 0F, 255F, true);

		noiseGenerator.seed(seed + 345345);
		float[][] noiseArrayAlpha = noiseGenerator.generateFloat(width / modifier, height / modifier, 0F, 255F, true);

		Pixmap imageBuffer = new Pixmap(width / modifier, height / modifier, Format.RGBA8888);
		ByteBuffer imageBufferByteArray = imageBuffer.getPixels();

		// clear buffer
		for (int i = 0; i < imageBufferByteArray.capacity(); i += 4) {
			imageBufferByteArray.put(i, (byte)0);
			imageBufferByteArray.put(i + 1, (byte)0);
			imageBufferByteArray.put(i + 2, (byte)0);
			imageBufferByteArray.put(i + 3, (byte)255);
		}

		for (int i = 0; i < noiseArrayRed.length; i++) {
			for (int j = 0; j < noiseArrayRed[i].length; j++) {
				int pos = 4 * (i + j * imageBuffer.getWidth());

				imageBufferByteArray.put(pos + 0, (byte)noiseArrayRed[i][j]);
				imageBufferByteArray.put(pos + 1, (byte)noiseArrayGreen[i][j]);
				imageBufferByteArray.put(pos + 2, (byte)noiseArrayBlue[i][j]);
				imageBufferByteArray.put(pos + 3, (byte)noiseArrayAlpha[i][j]);
			}
		}

		return new Texture(imageBuffer);
	}
}
