
package com.teamderpy.victusludus;

import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.teamderpy.victusludus.data.ResourceBin;
import com.teamderpy.victusludus.engine.Engine;
import com.teamderpy.victusludus.engine.graphics.EasyGL;
import com.teamderpy.victusludus.gui.GUI;
import com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent;

public class VictusLudusGame implements ApplicationListener {
	/** Global engine reference */
	public static Engine engine;

	/** Global random */
	public static Random rand;

	/** Shared random */
	public static Random sharedRand;

	/** Global resource bin which holds hashes against dynamically loaded content */
	public static ResourceBin resources = new ResourceBin();

	private OrthographicCamera ocamera;
	private PerspectiveCamera pcamera;

	private SpriteBatch spriteBatch;
	private ModelBatch modelBatch;

	/** Whether or not the resources have been loaded */
	private boolean isLoaded;

	@Override
	public void create () {
		this.isLoaded = false;

		VictusLudusGame.rand = new Random();
		VictusLudusGame.sharedRand = new Random(0L);

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		this.ocamera = new OrthographicCamera(w, h);
		this.ocamera.setToOrtho(true, w, h);
		this.ocamera.update();

		this.pcamera = new PerspectiveCamera(67, w, h);
		this.pcamera.near = 0.1f;
		this.pcamera.far = 1000f;
		this.pcamera.update();

		this.spriteBatch = new SpriteBatch();
		this.spriteBatch.disableBlending();
		this.modelBatch = new ModelBatch();

		VictusLudusGame.engine = new Engine(this.ocamera, this.pcamera);
		VictusLudusGame.engine.initializeBegin();
	}

	@Override
	public void dispose () {
		this.spriteBatch.dispose();
	}

	@Override
	public void render () {
		if (VictusLudusGame.engine.running) {
			this.spriteBatch.setProjectionMatrix(this.ocamera.combined);
			VictusLudusGame.engine.run(this.spriteBatch, this.modelBatch);
		}

		// check if our assets are loaded
		if (!this.isLoaded) {
			if (VictusLudusGame.engine.assetManager.update()) {
				this.isLoaded = true;
				System.out.println("load complete");
				VictusLudusGame.engine.initializeEnd();
				System.out.println("initialize complete");
			} else {
				EasyGL.setViewport();
				EasyGL.clearScreen(1, 1, 1, 1);

				float progress = VictusLudusGame.engine.assetManager.getProgress();
				int queued = VictusLudusGame.engine.assetManager.getQueuedAssets();
				int loaded = VictusLudusGame.engine.assetManager.getLoadedAssets();

				// display progress here
				System.out.println("loaded " + (int)(progress * 100.0) + "%" + "  queued: " + queued + "  loaded: " + loaded);

				final int loadingBarLength = VictusLudusGame.engine.X_RESOLUTION() / 3;
				final int loadingBarHeight = 20;
				final int loadingBarX = VictusLudusGame.engine.X_RESOLUTION() / 3;
				final int loadingBarY = VictusLudusGame.engine.Y_RESOLUTION() / 2 - loadingBarHeight / 2;

				this.spriteBatch.setProjectionMatrix(this.ocamera.combined);
				this.spriteBatch.begin();
				this.spriteBatch.enableBlending();

				GUI.defaultFont.setColor(Color.BLACK);
				GUI.defaultFont.draw(this.spriteBatch, "Loading...", loadingBarX, loadingBarY - GUI.defaultFont.getLineHeight());

				this.spriteBatch.disableBlending();

				Texture loadingBarBackground = EasyGL.getPixelTexture(Color.BLACK, 1, 1);
				Texture loadingBarForeground = EasyGL.getPixelTexture(Color.CYAN, 1, 1);

				this.spriteBatch.draw(loadingBarBackground, loadingBarX - 2, loadingBarY - 2, loadingBarLength + 4,
					loadingBarHeight + 4);
				this.spriteBatch.draw(loadingBarForeground, loadingBarX, loadingBarY, (int)(loadingBarLength * progress),
					loadingBarHeight);

				this.spriteBatch.end();

				loadingBarBackground.dispose();
				loadingBarForeground.dispose();
			}
		}
	}

	@Override
	public void resize (final int width, final int height) {
		VictusLudusGame.engine.eventHandler.signal(new ResizeEvent(this, width, height));
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}
}
