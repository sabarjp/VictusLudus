package com.teamderpy.victusludus.engine;

import java.io.IOException;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.data.DataReader;


/**
 * The Class MousePointer.
 */
public class MousePointer {
	
	/** The Constant DEFAULT_CURSOR. */
	public static final String DEFAULT_CURSOR = "pointers/default_pointer.png";
	
	/** The Constant QUERY_CURSOR. */
	public static final String QUERY_CURSOR   = "pointers/query_pointer.png";
	
	/** The Constant BUILD_CURSOR. */
	public static final String BUILD_CURSOR   = "pointers/build_pointer.png";
	
	private Sprite currentMousePointer;
	
	/**
	 * Instantiates a new mouse pointer.
	 */
	public MousePointer(){
		loadPointer(DEFAULT_CURSOR);
	}
	
	/**
	 * Load pointer.
	 *
	 * @param path the path
	 */
	public void loadPointer(String path){
		this.currentMousePointer = VictusLudusGame.resources.getTextureAtlas().createSprite(DEFAULT_CURSOR);
	}
}
