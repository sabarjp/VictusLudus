
package com.teamderpy.victusludus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;
import com.badlogic.gdx.utils.Array;

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
		 * create directory listings
		 */
		if(Main.IS_DEVELOPMENT_MODE){
			File dir = new File("../victusludus-android/assets/");
			
			createFileList(dir);
		}

		/*
		 * start up the game
		 */
		final LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Victus Ludus";
		cfg.useGL20 = true;
		cfg.width = 800;
		cfg.height = 600;

		new LwjglApplication(new VictusLudusGame(), cfg);
	}
	
	private static void createFileList(File dir){
		Array<String> fileNameList = new Array<String>();
		
		//get list of the files
		for(File file:dir.listFiles()){
			if(file.isFile()){
				if(!file.getName().equals(".list") &&
					!file.getName().equals("Thumbs.db") &&
					!file.getName().equals("thumbs.db") &&
					!file.getName().equals(".DS_Store") &&
					!file.getName().equals(".DS_Store?")){
					fileNameList.add(file.getName());
				}
			} else if(file.isDirectory()){
				fileNameList.add(file.getName() + "/");
			}
		}
		
		//write out the files
		File listFile = new File(dir.getAbsolutePath() + "/.list");
	
		if(listFile.exists()){
			listFile.delete();
		}
		
		try {
			listFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			FileOutputStream fileStream = new FileOutputStream(listFile.getAbsolutePath());
			OutputStreamWriter fileWriter = new OutputStreamWriter(fileStream, "UTF-8");
			
			for(String fileName:fileNameList){
				fileWriter.write(fileName + "\n");
			}
			
			fileWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//do other directories
		for(File file:dir.listFiles()){
			if(file.isDirectory()){
				createFileList(file);
			}
		}
		
		//done
		fileNameList = null;
	}
}
