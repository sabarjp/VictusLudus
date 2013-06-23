
package com.teamderpy.victusludus.engine;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.teamderpy.victusludus.VictusLudusGame;

/** The Class MousePointer. */
public class MousePointer {

	/** The Constant DEFAULT_CURSOR. */
	public static final String DEFAULT_CURSOR = "pointers/default_pointer.png";

	/** The Constant QUERY_CURSOR. */
	public static final String QUERY_CURSOR = "pointers/query_pointer.png";

	/** The Constant BUILD_CURSOR. */
	public static final String BUILD_CURSOR = "pointers/build_pointer.png";

	private Sprite currentMousePointer;

	/** Instantiates a new mouse pointer. */
	public MousePointer () {
		this.loadPointer(MousePointer.DEFAULT_CURSOR);
	}

	/** Load pointer.
	 * 
	 * @param path the path */
	public void loadPointer (final String path) {
		this.currentMousePointer = VictusLudusGame.resources.getTextureAtlasGUI().createSprite(MousePointer.DEFAULT_CURSOR);
	}
}
