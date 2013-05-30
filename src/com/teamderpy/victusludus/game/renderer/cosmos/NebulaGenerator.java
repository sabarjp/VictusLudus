package com.teamderpy.victusludus.game.renderer.cosmos;

import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;

import com.teamderpy.victusludus.game.map.MidpointGenerator;

/**
 * Generates a nebula image
 * @author Josh
 *
 */
public class NebulaGenerator {

	/**
	 * Generates a nebula
	 * @param seed the seed to use when generating
	 * @param width the requested width of the nebula
	 * @param height the requested height of the nebula
	 * @return
	 */
	public static Image createBackgroundNebula(final float seed, final int width, final int height){
		int modifier = 8;

		MidpointGenerator noiseGenerator = new MidpointGenerator(0.56F, seed);
		float[][] noiseArrayRed   = noiseGenerator.generateFloat(width/modifier, height/modifier, 0F, 255F, true);

		noiseGenerator.seed(seed + 234556);
		float[][] noiseArrayGreen = noiseGenerator.generateFloat(width/modifier, height/modifier, 0F, 255F, true);

		noiseGenerator.seed(seed + 4353452);
		float[][] noiseArrayBlue  = noiseGenerator.generateFloat(width/modifier, height/modifier, 0F, 255F, true);

		noiseGenerator.seed(seed + 345345);
		float[][] noiseArrayAlpha  = noiseGenerator.generateFloat(width/modifier, height/modifier, 0F, 255F, true);

		ImageBuffer imageBuffer = new ImageBuffer(width/modifier, height/modifier);
		byte[] imageBufferByteArray = imageBuffer.getRGBA();

		//clear buffer
		for(int i=0; i<imageBufferByteArray.length; i+=4){
			imageBufferByteArray[i] = 0;
			imageBufferByteArray[i+1] = 0;
			imageBufferByteArray[i+2] = 0;
			imageBufferByteArray[i+3] = (byte) 255;
		}

		for(int i=0; i<noiseArrayRed.length; i++){
			for(int j=0; j<noiseArrayRed[i].length; j++){
				int pos = 4 * (i + j*imageBuffer.getTexWidth());

				imageBufferByteArray[pos+0] = (byte) noiseArrayRed[i][j];
				imageBufferByteArray[pos+1] = (byte) noiseArrayGreen[i][j];
				imageBufferByteArray[pos+2] = (byte) noiseArrayBlue[i][j];
				imageBufferByteArray[pos+3] = (byte) noiseArrayAlpha[i][j];
			}
		}

		return imageBuffer.getImage(Image.FILTER_NEAREST);
	}
}
