
package com.teamderpy.victusludus;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
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
			TexturePacker2.process(settings, "../victusludus-sprites", "../victusludus-android/assets/sprites/spritesheets", "spritesheet");
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
