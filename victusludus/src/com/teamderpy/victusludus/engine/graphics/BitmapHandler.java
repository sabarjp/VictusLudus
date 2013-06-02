package com.teamderpy.VictusLudusGame.enginengine.graphics;

import java.io.IOException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.teamderpy.victusludus.VictusLudusGame;


/**
 * The Class BitmapHandler.
 */
public class BitmapHandler {

	/** The Constant SPRITE_SHEET_SIZE. */
	public static final int SPRITE_SHEET_SIZE = 16;   //the number of sprites per row of the sheet

	/** The Constant TRANSPARENT_COLOR. */
	public static final Color TRANSPARENT_COLOR = new Color(255,0,255);

	/**
	 * Load sprite sheet.
	 *
	 * @param path the path
	 * @return the sprite sheet
	 */
	public static SpriteSheet LoadSpriteSheet(final String path) {
		return BitmapHandler.LoadSpriteSheet(path, BitmapHandler.SPRITE_SHEET_SIZE, BitmapHandler.SPRITE_SHEET_SIZE);
	}

	/**
	 * Load sprite sheet.
	 *
	 * @param path the path
	 * @param spritesPerRow the sprites per row
	 * @param rowsPerSheet the rows per sheet
	 * @return the sprite sheet
	 */
	public static SpriteSheet LoadSpriteSheet(final String path, final int spritesPerRow, final int rowsPerSheet) {
		SpriteSheet tileSheet = null;

		try {
			Texture texture = null;
			int tileHeight = 0;
			int tileWidth = 0;

			try {
				texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(path));
				tileWidth = texture.getImageWidth() / spritesPerRow;
				tileHeight = texture.getImageHeight() / rowsPerSheet;
				texture.release();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Gdx.app.log("info", "Loading " + path + " of " + tileWidth + "x" + tileHeight);

			tileSheet = new SpriteSheet(path, tileWidth, tileHeight, BitmapHandler.TRANSPARENT_COLOR);
			tileSheet.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		return tileSheet;
	}

	/**
	 * Load animation sheet.
	 *
	 * @param path the path
	 * @param spritesPerRow the sprites per row
	 * @param rowsPerSheet the rows per sheet
	 * @param firstFrame the first frame
	 * @param lastFrame the last frame
	 * @param speed the speed of the animation
	 * @return the animation object
	 */
	public static Animation LoadAnimationSheet(final String path, final int spritesPerRow, final int rowsPerSheet, final int firstFrame, final int lastFrame, final int speed){
		SpriteSheet ss = BitmapHandler.LoadSpriteSheet(path, spritesPerRow, rowsPerSheet);

		//calculate where the first and last frame are
		int firstX, firstY, lastX, lastY;

		firstX = (firstFrame-1) % spritesPerRow;
		firstY = (firstFrame-1) / spritesPerRow;
		lastX  = (lastFrame-1) % spritesPerRow;
		lastY  = (lastFrame-1) / spritesPerRow;

		Animation a = new Animation(ss, firstX, firstY, lastX, lastY, true, speed, true);

		return a;
	}

	/**
	 * Gets the sprite sheet col.
	 *
	 * @return the sprite sheet col
	 */
	public static int getSpriteSheetCol(final int id, final SpriteSheet spriteSheet){
		return id % spriteSheet.getHorizontalCount();
	}

	/**
	 * Gets the sprite sheet row.
	 *
	 * @return the sprite sheet row
	 */
	public static int getSpriteSheetRow(final int id, final SpriteSheet spriteSheet){
		return id / spriteSheet.getHorizontalCount();
	}
}
