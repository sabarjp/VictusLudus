
package com.teamderpy.victusludus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

public class Main {
	public static boolean IS_DEVELOPMENT_MODE = true;

	public static void main (String[] args) {
		/*
		 * dynamically pack textures
		 */
		if (Main.IS_DEVELOPMENT_MODE) {
			final Settings settings = new Settings();
			settings.maxWidth = 512;
			settings.maxHeight = 512;
			settings.edgePadding = false;
			settings.combineSubdirectories = false;
			settings.paddingX = 0;
			settings.paddingY = 0;
			TexturePacker2.process(settings, "../victusludus-sprites/cosmos", "../victusludus-android/assets/sprites/spritesheets", "cosmos_spritesheet");
			TexturePacker2.process(settings, "../victusludus-sprites/entities", "../victusludus-android/assets/sprites/spritesheets", "entities_spritesheet");
			TexturePacker2.process(settings, "../victusludus-sprites/gui", "../victusludus-android/assets/sprites/spritesheets", "gui_spritesheet");
			TexturePacker2.process(settings, "../victusludus-sprites/tiles", "../victusludus-android/assets/sprites/spritesheets", "tiles_spritesheet");
		}

		/*
		 * start up the game
		 */
		final LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Victus Ludus";
		cfg.useGL20 = false;
		cfg.width = 800;
		cfg.height = 600;

		new LwjglApplication(new VictusLudusGame(), cfg);
	}
}
