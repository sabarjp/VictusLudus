
package com.teamderpy.victusludus.engine.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.teamderpy.victusludus.VictusRuntimeException;

/** The Class BitmapHandler. */
public class BitmapHandler {

	/** The default sprites on a sprite sheet */
	public static final int SPRITE_SHEET_SIZE = 16;

	/** The color of transparency */
	public static final Color TRANSPARENT_COLOR = new Color(1, 0, 1, 1);

	/**
	 * Load sprite sheet.
	 * 
	 * @param path the path
	 * @return the sprite sheet
	 */
	public static SpriteSheet LoadSpriteSheet (final TextureAtlas atlas, final String path) {
		return BitmapHandler.LoadSpriteSheet(atlas, path, BitmapHandler.SPRITE_SHEET_SIZE, BitmapHandler.SPRITE_SHEET_SIZE);
	}

	/**
	 * Load sprite sheet.
	 * 
	 * @param atlas the texture atlas
	 * @param path the path
	 * @param spritesPerRow the sprites per row
	 * @param rowsPerSheet the rows per sheet
	 * @return the sprite sheet
	 */
	public static SpriteSheet LoadSpriteSheet (final TextureAtlas atlas, final String path, final int spritesPerRow,
		final int rowsPerSheet) {
		SpriteSheet tileSheet = null;

		Gdx.app.log("info", "Loading sprite sheet on " + path);

		tileSheet = new SpriteSheet(atlas, path, spritesPerRow, rowsPerSheet);

		return tileSheet;
	}

	/**
	 * Load animation sheet.
	 * 
	 * @param atlas the texture atlas
	 * @param path the path
	 * @param spritesPerRow the sprites per row
	 * @param rowsPerSheet the rows per sheet
	 * @param firstFrame the first frame
	 * @param lastFrame the last frame
	 * @param speed the speed of the animation
	 * @return the animation object
	 */
	public static Animation LoadAnimationSheet (final TextureAtlas atlas, final String path, final int spritesPerRow,
		final int rowsPerSheet, final int firstFrame, final int lastFrame, final int speed) {

		TextureRegion ss = atlas.findRegion(path);
		if (ss == null) {
			throw new VictusRuntimeException("Failed to load sprite: " + path);
		}

		int spriteWidth = ss.getRegionWidth() / spritesPerRow;
		int spriteHeight = ss.getRegionHeight() / rowsPerSheet;

		Array<TextureRegion> frames = new Array<TextureRegion>();

		for (int i = firstFrame; i < lastFrame; i++) {
			int x = ((i - 1) % spritesPerRow) * spriteWidth;
			int y = ((i - 1) / spritesPerRow) * spriteHeight;

			frames.add(new TextureRegion(ss, x, y, spriteWidth, spriteHeight));
		}

		return new Animation(speed / 1000.0F, frames, Animation.LOOP);
	}

	/**
	 * Gets the sprite sheet col.
	 * 
	 * @return the sprite sheet col
	 */
	public static int getSpriteSheetCol (final int id, final SpriteSheet spriteSheet) {
		return id % spriteSheet.getHorizontalCount();
	}

	/**
	 * Gets the sprite sheet row.
	 * 
	 * @return the sprite sheet row
	 */
	public static int getSpriteSheetRow (final int id, final SpriteSheet spriteSheet) {
		return id / spriteSheet.getHorizontalCount();
	}
}
