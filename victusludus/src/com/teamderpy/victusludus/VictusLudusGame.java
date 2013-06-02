package com.teamderpy.victusludus;

import java.util.Random;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamderpy.victusludus.data.ResourceBin;
import com.teamderpy.victusludus.engine.Engine;
import com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent;


public class VictusLudusGame implements ApplicationListener {	
	/** Global engine reference */
	public static Engine engine;
	
	/** Global random */
	public static Random rand;
	
	/** Global resource bin which holds hashes against dynamically loaded content */
	public static ResourceBin resources = new ResourceBin();
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	/** Whether or not the resources have been loaded */
	private boolean isLoaded;
	
	@Override
	public void create() {		
		this.isLoaded = false;
		
		VictusLudusGame.rand = new Random();
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(true, w, h);
		
		this.batch  = new SpriteBatch();
		
		VictusLudusGame.engine = new Engine();
		VictusLudusGame.engine.initializeBegin();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		batch.end();
		
		//check if our assets are loaded
		if(!this.isLoaded){
			if(VictusLudusGame.engine.assetManager.update()){
				this.isLoaded = true;
				System.out.println("load complete");
				VictusLudusGame.engine.initializeEnd();
				System.out.println("initialize complete");
			} else {
				float progress = VictusLudusGame.engine.assetManager.getProgress();
				int queued = VictusLudusGame.engine.assetManager.getQueuedAssets();
				int loaded = VictusLudusGame.engine.assetManager.getLoadedAssets();
				
				//display progress here
				System.out.println("loaded " + progress*100.0 + "%" + 
				"  (" + (loaded/(float)(queued+loaded))*100.0 + "%)" +
				"  queued: " + queued +
				"  loaded: " + loaded);
				
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		VictusLudusGame.engine.eventHandler.signal(new ResizeEvent(this, width, height));
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
