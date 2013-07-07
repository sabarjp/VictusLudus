
package com.teamderpy.victusludus.engine.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.teamderpy.victusludus.VictusRuntimeException;

/**
 * Temporary class until sprite sheets are converted over properly
 * 
 * @author Josh
 * 
 */
public class SpriteSheet {
	int spritesPerRow;
	int rowsPerSheet;
	int spriteWidth;
	int spriteHeight;

	TextureRegion[][] spriteSheet;

	public SpriteSheet (final TextureAtlas atlas, final String path, final int spritesPerRow, final int rowsPerSheet) {
		TextureRegion texture = atlas.findRegion(path);

		if (texture == null) {
			throw new VictusRuntimeException("Failed to load sprite: " + path);
		}

		this.spriteWidth = texture.getRegionWidth() / spritesPerRow;
		this.spriteHeight = texture.getRegionHeight() / rowsPerSheet;
		this.spritesPerRow = spritesPerRow;
		this.rowsPerSheet = rowsPerSheet;

		this.spriteSheet = texture.split(this.spriteWidth, this.spriteHeight);

		// flip the textures
		for (TextureRegion[] set : this.spriteSheet) {
			for (TextureRegion sprite : set) {
				sprite.flip(false, true);
			}
		}
	}

	public void render (final SpriteBatch batch, final int x, final int y, final int width, final int height, final int srcX,
		final int srcY) {
		this.render(batch, (float)x, (float)y, (float)width, (float)height, srcX, srcY);
	}

	public void render (final SpriteBatch batch, final float x, final float y, final float width, final float height,
		final int srcX, final int srcY) {
		batch.draw(this.spriteSheet[srcX][srcY], x, y, width, height);
	}

	public int getHeight () {
		return this.rowsPerSheet * this.spriteHeight;
	}

	public int getWidth () {
		return this.spritesPerRow * this.spriteWidth;
	}

	public int getSpriteSheetCol (final int id) {
		return id % this.spritesPerRow;
	}

	public int getHorizontalCount () {
		return this.spritesPerRow;
	}

	public int getSpriteSheetRow (final int id) {
		return id / this.spritesPerRow;
	}

	public int getVerticalCount () {
		return this.rowsPerSheet;
	}
}
