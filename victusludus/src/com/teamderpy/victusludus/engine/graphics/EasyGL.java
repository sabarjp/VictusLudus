
package com.teamderpy.victusludus.engine.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;

public final class EasyGL {
	/**
	 * Clears the frame buffer with one color overlaying it
	 * 
	 * @param red the red component
	 * @param green the green component
	 * @param blue the blue component
	 * @param alpha the transparancy component
	 */
	public static void clearScreen (final float red, final float green, final float blue, final float alpha) {
		Gdx.graphics.getGL20().glClearColor(red, green, blue, alpha);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	}

	/**
	 * Clears the frame buffer with one color overlaying it
	 * 
	 * @param color the color to clear
	 * @param alpha the transparancy component
	 */
	public static void clearScreen (final Color color) {
		Gdx.graphics.getGL20().glClearColor(color.r, color.g, color.b, color.a);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	}

	/**
	 * Sets the open gl viewport
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void setViewport (final int x, final int y, final int width, final int height) {
		Gdx.gl20.glViewport(x, y, width, height);
	}

	/**
	 * Sets the open gl viewport, which will cover the entire disply area
	 */
	public static void setViewport () {
		Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	/**
	 * Create a rectangle of a certain color. Dispose when done!
	 * @param color the color of the pixel
	 * @param width the width of the texture
	 * @param height the height of the texture
	 * @return a texture with the specifications
	 */
	public static Texture getPixelTexture (final Color color, final int width, final int height) {
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fill();

		return new Texture(pixmap);
	}
}
