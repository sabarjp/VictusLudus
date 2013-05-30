package com.teamderpy.victusludus.engine;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.CursorLoader;

// TODO: Auto-generated Javadoc
/**
 * The Class MousePointer.
 */
public class MousePointer {
	
	/** The Constant DEFAULT_CURSOR. */
	public static final String DEFAULT_CURSOR = "res/sprites/pointers/default_pointer.png";
	
	/** The Constant QUERY_CURSOR. */
	public static final String QUERY_CURSOR   = "res/sprites/pointers/query_pointer.png";
	
	/** The Constant BUILD_CURSOR. */
	public static final String BUILD_CURSOR   = "res/sprites/pointers/build_pointer.png";
	
	/** The cl. */
	private CursorLoader cl;
	
	/**
	 * Instantiates a new mouse pointer.
	 */
	public MousePointer(){
		cl = CursorLoader.get();
		
		loadPointer(DEFAULT_CURSOR);
	}
	
	/**
	 * Load pointer.
	 *
	 * @param path the path
	 */
	public void loadPointer(String path){
		try {
			Cursor cursor = cl.getCursor(path, 0, 0);
			Mouse.setNativeCursor(cursor);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (LWJGLException e1) {
			e1.printStackTrace();
		}
	}
}
