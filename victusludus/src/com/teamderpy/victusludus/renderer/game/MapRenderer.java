
package com.teamderpy.victusludus.renderer.game;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.shaders.GLES10Shader;
import com.teamderpy.victusludus.game.Game;
import com.teamderpy.victusludus.game.map.Map;
import com.teamderpy.victusludus.gui.UIGameHUD;

public class MapRenderer {
	private Game game;
	private Map map;

	public MapRenderer (final Game game, final Map map) {
		this.game = game;
		this.map = map;
	}

	/**
	 * Renders a map's terrain
	 * 
	 * @param modelBatch the model batch to use
	 * @param deltaT the amount of time that has elapsed since the last render
	 * @param camera the camera to render for
	 */
	public void render (final ModelBatch modelBatch, final float deltaT) {
		DefaultShader.defaultCullFace = GL20.GL_FRONT;
		GLES10Shader.defaultCullFace = GL20.GL_FRONT;

		modelBatch.begin(this.game.getPcamera());
		modelBatch.render(this.map, this.game.getLights());
		modelBatch.end();

		((UIGameHUD)this.game.getCurrentUI()).setCurrentChunksText(this.map.renderedChunks + "/" + this.map.numChunks);
	}
}
