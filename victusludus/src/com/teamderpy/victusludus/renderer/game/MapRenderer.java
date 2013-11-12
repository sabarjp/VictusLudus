
package com.teamderpy.victusludus.renderer.game;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.shaders.GLES10Shader;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.teamderpy.victusludus.game.EuclideanObject;
import com.teamderpy.victusludus.game.Game;
import com.teamderpy.victusludus.game.entity.GameEntityInstance;
import com.teamderpy.victusludus.game.map.Chunk;
import com.teamderpy.victusludus.game.map.Map;
import com.teamderpy.victusludus.gui.UIGameHUD;

public class MapRenderer implements RenderableProvider {
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

		modelBatch.begin(this.game.getGameCamera().getCamera());
		modelBatch.render(this, this.game.getLights());
		modelBatch.end();

		((UIGameHUD)this.game.getCurrentUI()).setCurrentChunksText(this.map.renderedChunks + "/" + this.map.numChunks);
	}

	@Override
	public void getRenderables (final Array<Renderable> renderables, final Pool<Renderable> pool) {
		/* get voxels to render */
		this.map.renderedChunks = 0;
		for (int i = 0; i < this.map.chunks.length; i++) {
			Chunk chunk = this.map.chunks[i];
			Mesh mesh = this.map.meshes[i];

			if (this.map.dirty[i]) {
				int numVerts = chunk.calculateVertices(this.map.vertices);
				this.map.numVertices[i] = numVerts / 4 * 6;
				mesh.setVertices(this.map.vertices, 0, numVerts * Chunk.VERTEX_SIZE);
				this.map.dirty[i] = false;
			}

			if (this.map.numVertices[i] == 0) {
				continue;
			}

			Renderable renderable = pool.obtain();
			renderable.material = this.map.materials[i];
			renderable.mesh = mesh;
			renderable.meshPartOffset = 0;
			renderable.meshPartSize = this.map.numVertices[i];
			renderable.primitiveType = GL20.GL_TRIANGLES;
			renderables.add(renderable);
			this.map.renderedChunks++;
		}

		/* get entities to render */
		for (GameEntityInstance entity : this.map.entityManager.getEntities()) {
			if (entity.getMesh() == null) {
				continue;
			}

			if (entity.isDirty()) {
				int numVerts = entity.calculateVertices();
				entity.setNumVerts(numVerts / 4 * 6);
				entity.getMesh().setVertices(entity.getVertices(), 0, numVerts * EuclideanObject.VERTEX_SIZE);

				if (entity.getEntity().isBillboard()) {
					entity.getMesh().transform(
						new Matrix4().rotate(new Vector3(0, 0, 1),
							entity.getPos().cpy().sub(this.game.getGameCamera().getCamera().position).scl(1, 0, 1).nor()).trn(
							entity.getPos()));
				} else {
					entity.getMesh().transform(
						new Matrix4().rotate(new Vector3(0, 1, 0), entity.getDirection().getDegreesRotation()).trn(entity.getPos()));
				}

				entity.setDirty(true);
			}

			Renderable renderable = pool.obtain();
			renderable.material = entity.getMaterial();
			renderable.mesh = entity.getMesh();
			renderable.meshPartOffset = 0;
			renderable.meshPartSize = entity.getNumVerts();
			renderable.primitiveType = GL20.GL_TRIANGLES;
			renderables.add(renderable);
		}
	}
}
