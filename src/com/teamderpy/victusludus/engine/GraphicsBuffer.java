package com.teamderpy.victusludus.engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class GraphicsBuffer.
 */
public class GraphicsBuffer {
	
	/** The buffer. */
	private Image buffer;
	
	/** The size. */
	private int size;
	
	/**
	 * Instantiates a new graphics buffer.
	 */
	public GraphicsBuffer(){
		size = 8;
		
		buffer = createOffscreenImage(size);
	}
	
	/**
	 * Gets the buffer graphics context.
	 *
	 * @param width the width
	 * @param height the height
	 * @return the buffer graphics context
	 */
	public Graphics getBufferGraphicsContext(int width, int height){
		if(width <= buffer.getWidth() && height <= buffer.getHeight()){
			try {
				return buffer.getGraphics();
			} catch (SlickException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		//allocate a larger buffer
		buffer = createOffscreenImage(++size);
		
		return getBufferGraphicsContext(width, height);
	}
	
	/**
	 * Creates the offscreen image.
	 *
	 * @param size the size
	 * @return the image
	 */
	private Image createOffscreenImage(int size){
		Image b;
		
		try {
			b = Image.createOffscreenImage(0x1 << size, 0x1 << size, Image.FILTER_NEAREST);
		} catch (SlickException e) {
			b = null;
			e.printStackTrace();
		}
		
		return b;
	}
}
