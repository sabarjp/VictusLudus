package com.teamderpy.victusludus.engine.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public final class EasyGL {
	private static final Pool<PoolablePixmapPixel> INTERNAL_PIXMAP_POOL  = new Pool<PoolablePixmapPixel>() {
      @Override
      protected PoolablePixmapPixel newObject() {
      	return new PoolablePixmapPixel();
      }
	};

	private static final Pool<PoolableTexture> INTERNAL_TEXTURE_POOL = new Pool<PoolableTexture>() {
      @Override
      protected PoolableTexture newObject() {
      	return new PoolableTexture();
      }
	};
	private static final Array<PoolableTexture> usedTextureArray = new Array<PoolableTexture>();
	
	/**
	 * Clears the frame buffer with one color overlaying it
	 * 
	 * @param red the red component
	 * @param green the green component
	 * @param blue the blue component
	 * @param alpha the transparancy component
	 */
	public static void clearScreen(float red, float green, float blue, float alpha){
		Gdx.graphics.getGL20().glClearColor( red, green, blue, alpha );
		Gdx.graphics.getGL20().glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
	}
	
	/**
	 * Clears the frame buffer with one color overlaying it
	 * 
	 * @param color the color to clear
	 * @param alpha the transparancy component
	 */
	public static void clearScreen(Color color){
		Gdx.graphics.getGL20().glClearColor( color.r, color.g, color.b, color.a );
		Gdx.graphics.getGL20().glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
	}
	
	/**
	 * Sets the open gl viewport
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void setViewport(int x, int y, int width, int height){
		Gdx.gl20.glViewport(x, y, width, height);
	}
	
	/**
	 * Sets the open gl viewport, which will cover the entire disply area
	 */
	public static void setViewport(){
		Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	/**
	 * Draws a filled rectangle as part of a batch
	 * 
	 * @param batch the SpriteBatch
	 * @param color the color of the rectangle
	 * @param x the x coordinate to begin to draw
	 * @param y the y coordinate to begin to draw
	 * @param width the width of the rectangle
	 * @param height the height of the rectangle
	 */
	public static void drawRect(SpriteBatch batch, Color color, int x, int y, int width, int height){
		PoolablePixmapPixel pixmap = INTERNAL_PIXMAP_POOL.obtain(); 
		PoolableTexture texture   = INTERNAL_TEXTURE_POOL.obtain(); 
		
		pixmap.pixmap.drawPixel(0, 0, Color.rgba8888(color));
		texture.texture.draw(pixmap.pixmap, 0, 0);
		
		batch.draw(texture.texture, x, y, width, height);
		
		INTERNAL_PIXMAP_POOL.free(pixmap);
		usedTextureArray.add(texture);
	}
	
	/**
	 * Frees all resources in use from pools
	 */
	public static void freePoolResources(){
		INTERNAL_TEXTURE_POOL.freeAll(usedTextureArray);
	}
}
