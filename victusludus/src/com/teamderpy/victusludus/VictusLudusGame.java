
package com.teamderpy.victusludus;

import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

	private OrthographicCamera camera;
	private SpriteBatch batch;

	/** Whether or not the resources have been loaded */
	private boolean isLoaded;

	@Override
	public void create () {
		this.isLoaded = false;

		VictusLudusGame.rand = new Random();
		VictusLudusGame.sharedRand = new Random(0L);

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(true, w, h);
		this.camera.update();

		this.batch = new SpriteBatch();
		this.batch.disableBlending();

		VictusLudusGame.engine = new Engine(this.camera);
		VictusLudusGame.engine.initializeBegin();
	}

	@Override
	public void dispose () {
		this.batch.dispose();
	}

	@Override
	public void render () {
		if (VictusLudusGame.engine.running) {
			VictusLudusGame.engine.run(this.batch);
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
				System.out.println("loaded " + progress * 100.0 + "%" + "  queued: " + queued + "  loaded: " + loaded);

				final int loadingBarLength = VictusLudusGame.engine.X_RESOLUTION() / 3;
				final int loadingBarHeight = 20;
				final int loadingBarX = VictusLudusGame.engine.X_RESOLUTION() / 3;
				final int loadingBarY = VictusLudusGame.engine.Y_RESOLUTION() / 2 - loadingBarHeight / 2;

				this.batch.setProjectionMatrix(this.camera.combined);
				this.batch.begin();
				this.batch.enableBlending();

				GUI.defaultFont.setColor(Color.BLACK);
				GUI.defaultFont.draw(this.batch, "Loading...", loadingBarX, loadingBarY - GUI.defaultFont.getLineHeight());

				this.batch.disableBlending();

				Texture loadingBarBackground = EasyGL.getPixelTexture(Color.BLACK, 1, 1);
				Texture loadingBarForeground = EasyGL.getPixelTexture(Color.CYAN, 1, 1);

				this.batch.draw(loadingBarBackground, loadingBarX - 2, loadingBarY - 2, loadingBarLength + 4, loadingBarHeight + 4);
				this.batch.draw(loadingBarForeground, loadingBarX, loadingBarY, (int)(loadingBarLength * progress), loadingBarHeight);

				this.batch.end();

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
