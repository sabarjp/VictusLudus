
package com.teamderpy.victusludus.game.map;

import java.util.Random;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.teamderpy.victusludus.game.tile.GameTile;

/**
 * Adapted from <a
 * href="http://devmag.org.za/2009/04/25/perlin-noise/">http://devmag
 * .org.za/2009/04/25/perlin-noise/</a>
 * @author badlogic
 */
public class PerlinNoiseGenerator {
	private static Random rand;

	public static float[][] generateWhiteNoise (final int width, final int height) {
		float[][] noise = new float[width][height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				noise[x][y] = PerlinNoiseGenerator.rand.nextFloat();
			}
		}
		return noise;
	}

	public static float interpolate (final float x0, final float x1, final float alpha) {
		return x0 * (1 - alpha) + alpha * x1;
	}

	public static float[][] generateSmoothNoise (final float[][] baseNoise, final int octave) {
		int width = baseNoise.length;
		int height = baseNoise[0].length;
		float[][] smoothNoise = new float[width][height];

		int samplePeriod = 1 << octave; // calculates 2 ^ k
		float sampleFrequency = 1.0f / samplePeriod;
		for (int i = 0; i < width; i++) {
			int sample_i0 = (i / samplePeriod) * samplePeriod;
			int sample_i1 = (sample_i0 + samplePeriod) % width; // wrap around
			float horizontal_blend = (i - sample_i0) * sampleFrequency;

			for (int j = 0; j < height; j++) {
				int sample_j0 = (j / samplePeriod) * samplePeriod;
				int sample_j1 = (sample_j0 + samplePeriod) % height; // wrap around
				float vertical_blend = (j - sample_j0) * sampleFrequency;
				float top = PerlinNoiseGenerator.interpolate(baseNoise[sample_i0][sample_j0], baseNoise[sample_i1][sample_j0],
					horizontal_blend);
				float bottom = PerlinNoiseGenerator.interpolate(baseNoise[sample_i0][sample_j1], baseNoise[sample_i1][sample_j1],
					horizontal_blend);
				smoothNoise[i][j] = PerlinNoiseGenerator.interpolate(top, bottom, vertical_blend);
			}
		}

		return smoothNoise;
	}

	public static float[][] generatePerlinNoise (final float[][] baseNoise, final int octaveCount) {
		int width = baseNoise.length;
		int height = baseNoise[0].length;
		float[][][] smoothNoise = new float[octaveCount][][]; // an array of 2D
// arrays containing
		float persistance = 0.7f;

		for (int i = 0; i < octaveCount; i++) {
			smoothNoise[i] = PerlinNoiseGenerator.generateSmoothNoise(baseNoise, i);
		}

		float[][] perlinNoise = new float[width][height]; // an array of floats
// initialised to 0

		float amplitude = 1.0f;
		float totalAmplitude = 0.0f;

		for (int octave = octaveCount - 1; octave >= 0; octave--) {
			amplitude *= persistance;
			totalAmplitude += amplitude;

			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
				}
			}
		}

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				perlinNoise[i][j] /= totalAmplitude;
			}
		}

		return perlinNoise;
	}

	public static float[][] generatePerlinNoise (final int width, final int height, final int octaveCount) {
		float[][] baseNoise = PerlinNoiseGenerator.generateWhiteNoise(width, height);
		return PerlinNoiseGenerator.generatePerlinNoise(baseNoise, octaveCount);
	}

	public static byte[] generateHeightMap (final int width, final int height, final int min, final int max, final int octaveCount) {
		float[][] baseNoise = PerlinNoiseGenerator.generateWhiteNoise(width, height);
		float[][] noise = PerlinNoiseGenerator.generatePerlinNoise(baseNoise, octaveCount);
		byte[] bytes = new byte[baseNoise.length * baseNoise[0].length];
		int idx = 0;
		int range = max - min;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				bytes[idx++] = (byte)(noise[x][y] * range + min);
			}
		}
		return bytes;
	}

	public static Pixmap generatePixmap (final int width, final int height, final int min, final int max, final int octaveCount) {
		byte[] bytes = PerlinNoiseGenerator.generateHeightMap(width, height, min, max, octaveCount);
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		for (int i = 0, idx = 0; i < bytes.length; i++) {
			byte val = bytes[i];
			pixmap.getPixels().put(idx++, val);
			pixmap.getPixels().put(idx++, val);
			pixmap.getPixels().put(idx++, val);
			pixmap.getPixels().put(idx++, (byte)255);
		}
		return pixmap;
	}

	public static void generateVoxels (final Map map, final int min, final int max, final int octaveCount, final long seed) {
		PerlinNoiseGenerator.rand = new Random(seed);

		byte[] heightMap = PerlinNoiseGenerator.generateHeightMap(map.voxelsX, map.voxelsZ, min, max, octaveCount);
		int idx = 0;
		for (int z = 0; z < map.voxelsZ; z++) {
			for (int x = 0; x < map.voxelsX; x++) {
				map.setColumn(x, heightMap[idx++], z, GameTile.ID_GRASS);
				map.setHighestPointIfHigher((int)map.getHighest(x, z));
			}
		}
	}
}
