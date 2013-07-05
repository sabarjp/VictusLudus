
package com.teamderpy.victusludus.renderer.cosmos;

import java.nio.ByteBuffer;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.teamderpy.victusludus.math.MatrixMath;
import com.teamderpy.victusludus.math.heightmap.MidpointGenerator;

/**
 * Generates a nebula image
 * @author Josh
 */
public class NebulaGenerator {
	/* the width and height of the nebula are downsized this many times */
	private static final int SIZE_MODIFIER = 8;

	/**
	 * Generates a nebula
	 * 
	 * @param seed the seed to use when generating
	 * @param width the requested width of the nebula
	 * @param height the requested height of the nebula
	 * @return
	 */
	public static Texture createBackgroundNebula (final long seed, final int width, final int height) {
		Pixmap imageBuffer = new Pixmap(width / NebulaGenerator.SIZE_MODIFIER, height / NebulaGenerator.SIZE_MODIFIER,
			Format.RGBA8888);
		ByteBuffer imageBufferByteArray = imageBuffer.getPixels();

		float[][][] rgbArray = NebulaGenerator.getIteratedNebula(seed, width, height, 1);

		// clear buffer
		for (int i = 0; i < imageBufferByteArray.capacity(); i += 4) {
			imageBufferByteArray.put(i, (byte)0);
			imageBufferByteArray.put(i + 1, (byte)0);
			imageBufferByteArray.put(i + 2, (byte)0);
			imageBufferByteArray.put(i + 3, (byte)255);
		}

		for (int i = 0; i < rgbArray[0].length; i++) {
			for (int j = 0; j < rgbArray[0][i].length; j++) {
				int pos = 4 * (i + j * imageBuffer.getWidth());

				imageBufferByteArray.put(pos + 0, (byte)(255 * rgbArray[0][i][j]));
				imageBufferByteArray.put(pos + 1, (byte)(255 * rgbArray[1][i][j]));
				imageBufferByteArray.put(pos + 2, (byte)(255 * rgbArray[2][i][j]));
				imageBufferByteArray.put(pos + 3, (byte)(255 * rgbArray[3][i][j]));
			}
		}

		return new Texture(imageBuffer);
	}

	/**
	 * Gets a nebula that has been iterated several times with multiple
	 * difference functions applied
	 * 
	 * @param seed the seed to use when generating
	 * @param width the requested width of the nebula
	 * @param height the requested height of the nebula
	 * @param iterations the number of times to iterate
	 * @return 3d array of floats, indexed as [rgba,x,y]
	 */
	private static float[][][] getIteratedNebula (final long seed, final int width, final int height, final int iterations) {
		float[][][] rgbArray = NebulaGenerator.getRandomNebula(seed + 2498723479L, width, height);

		for (int i = 0; i < iterations + 1; i++) {
			float[][][] subtract = NebulaGenerator.getRandomNebula(seed + (123987L * 601849), width, height);

			MatrixMath.difference(rgbArray[0], subtract[0]);
			MatrixMath.difference(rgbArray[1], subtract[1]);
			MatrixMath.difference(rgbArray[2], subtract[2]);
			MatrixMath.difference(rgbArray[3], subtract[3]);
		}

		MatrixMath.normalize(rgbArray[0], 0F, 1F);
		MatrixMath.normalize(rgbArray[1], 0F, 1F);
		MatrixMath.normalize(rgbArray[2], 0F, 1F);
		MatrixMath.normalize(rgbArray[3], 0F, 1F);

		return rgbArray;
	}

	/**
	 * Makes a random nebula and returns it as a 3d array, indexed as [rgba,x,y]
	 * 
	 * @param seed the seed to use when generating
	 * @param width the requested width of the nebula
	 * @param height the requested height of the nebula
	 * @return 3d array of floats, indexed as [rgba,x,y]
	 */
	private static float[][][] getRandomNebula (final long seed, final int width, final int height) {
		int arrayWidth = width / NebulaGenerator.SIZE_MODIFIER;
		int arrayHeight = height / NebulaGenerator.SIZE_MODIFIER;

		float[][][] rgbArray = new float[4][arrayWidth][arrayHeight];

		MidpointGenerator noiseGenerator = new MidpointGenerator(0.56F, seed);
		rgbArray[0] = noiseGenerator.generateFloat(arrayWidth, arrayHeight, 0F, 1F, false);

		noiseGenerator.seed(seed + 234556);
		rgbArray[1] = noiseGenerator.generateFloat(arrayWidth, arrayHeight, 0F, 1F, false);

		noiseGenerator.seed(seed + 4353452);
		rgbArray[2] = noiseGenerator.generateFloat(arrayWidth, arrayHeight, 0F, 1F, false);

		noiseGenerator.seed(seed + 345345);
		rgbArray[3] = noiseGenerator.generateFloat(arrayWidth, arrayHeight, 0F, 1F, false);

		return rgbArray;
	}
}
