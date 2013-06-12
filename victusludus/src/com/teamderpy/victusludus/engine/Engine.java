package com.teamderpy.victusludus.engine;


import java.awt.Toolkit;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.teamderpy.victusludus.data.DataReader;
import com.teamderpy.victusludus.engine.graphics.EasyGL;
import com.teamderpy.victusludus.game.cosmos.Universe;
import com.teamderpy.victusludus.gui.DialogBox;
import com.teamderpy.victusludus.gui.GUI;
import com.teamderpy.victusludus.gui.UI;
import com.teamderpy.victusludus.gui.eventhandler.ResizeListener;
import com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent;

/**
 * The Class Engine.
 */
public class Engine implements ResizeListener{
	/** The target tick rate, how often logic occurs*/
	private final int TARGET_TICKRATE = 60; 

	/** The is resizable. */
	private final boolean IS_RESIZABLE = false;

	/** The is debugging. */
	public boolean IS_DEBUGGING = false;

	/** The is fullscreen. */
	public boolean IS_FULLSCREEN = false;

	/** The is vsync. */
	public boolean IS_VSYNC = false;

	/** The scaling factor. */
	public double scalingFactor = 1.00;

	/** The target framerate. */
	public int targetFramerate = 60;

	/** The running. */
	public boolean running = false;
	
	/** Whether or not to skip frames if we are behind */
	private boolean isFrameSkipOn = true;

	/** The frames since reset. */
	private int framesSinceReset;

	/** The ticks since reset. */
	private int ticksSinceReset;

	/** The last frame reset time. */
	private long lastFPSResetTime;

	/** The last frame draw time. */
	private long lastFrameDrawTime;

	/** The last tick time. */
	private long lastTickTime;
	
	/** The last time the game title was updated */
	private long lastTitleUpdateTime;

	/** The tick count. */
	private long tickCount = 0;

	/** The player cash. */
	public int playerCash = 100000;

	/** The current gui. */
	public GUI currentGUI = null;
	
	public UI currentUI = null;

	/** The current dialog. */
	public DialogBox currentDialog = null;

	/** The current view. */
	public IView currentView = null;

	/** The current universe. */
	public Universe currentUniverse = null;

	/** The mouse pointer. */
	public MousePointer mousePointer = null;

	/** The sound system. */
	public SoundSystem soundSystem = null;

	/** The global listener. */
	private GlobalListener globalListener;

	/** The display modes. */
	public Array<DisplayMode> displayModes = new Array<DisplayMode>();

	/** The current display mode. */
	public FlexibleDisplayMode currentDisplayMode = null;

	/** The event handler. */
	public MasterEventHandler eventHandler = null;
	
	/** The input multiplexer */
	public InputMultiplexer inputMultiplexer = new InputMultiplexer();

	/** The preferences. */
	public com.teamderpy.victusludus.readerwriter.JLDLRandomReaderWriter preferences;
	
	/** The asset manager */
	public AssetManager assetManager;
	
	/** The input poller and signaller */
	public InputPoller inputPoller;
	
	/** shape renderer*/
	public ShapeRenderer shapeRenderer;
	
	/** the camera */
	public OrthographicCamera camera;
	
	/** frames per second */
	private int fps = 0;
	
	/** ticks per second */
	private int tps = 0;
	
	/**
	 * Initialize a new game engine
	 * 
	 * @param camera the orthographic camera
	 */
	public Engine(OrthographicCamera camera){
		this.camera = camera;
		
		this.shapeRenderer = new ShapeRenderer();
		this.shapeRenderer.setProjectionMatrix(camera.combined);
	}

	/**
	 * Starts to initialize the engine, up to queueing the data load 
	 */
	public void initializeBegin() {
		this.loadPreferences();
		
		this.eventHandler = new MasterEventHandler();

		//add displays with the same refresh rate as the desktop
		for(final DisplayMode d:Gdx.graphics.getDisplayModes()){
			if(d.refreshRate == Gdx.graphics.getDesktopDisplayMode().refreshRate){
				this.displayModes.add(d);
			}
		}

		//sets the current resolution
		this.setDisplay();
		
		this.loadResources();

	}
	
	/**
	 * Finished initializing the engine.
	 * DO NOT CALL UNTIL RESOURCES ARE LOADED
	 */
	public void initializeEnd(){		
		GUI.loadGUISpriteSheets();
		
		this.initInputPoller();

		this.createMousePointer();
		
		this.initSoundSystem();

		//this.changeGUI(new com.teamderpy.victusludus.gui.GUIMainMenu());
		
		this.changeUI(new com.teamderpy.victusludus.gui.UIMainMenu());

		//LoadLevel();

		this.globalListener = new GlobalListener();
		this.globalListener.registerListeners();
		
		this.eventHandler.resizeHandler.registerPlease(this);

		this.start();
	}
	
	private void setDisplay(){
		if(Gdx.graphics.supportsDisplayModeChange()){
			if(this.currentDisplayMode.displayMode != null && this.currentDisplayMode.isFullscreen){
				Gdx.graphics.setDisplayMode(this.currentDisplayMode.displayMode);
			} else {
				Gdx.graphics.setDisplayMode(this.currentDisplayMode.width, this.currentDisplayMode.height, this.currentDisplayMode.isFullscreen);
			}
			
			this.camera.setToOrtho(true, this.X_RESOLUTION(), this.Y_RESOLUTION());
			this.camera.update();
			
			this.shapeRenderer.setProjectionMatrix(camera.combined);
		}
	}
	
	/**
	 * Sets up the input poller and starts listening for events
	 */
	private void initInputPoller(){
		this.inputPoller = new InputPoller();
		this.inputMultiplexer.addProcessor(inputPoller);
		Gdx.input.setInputProcessor(this.inputMultiplexer);
	}

	/**
	 * Inits the sound system.
	 */
	private void initSoundSystem(){
		this.soundSystem = new SoundSystem();
	}

	/**
	 * Load preferences.
	 */
	private void loadPreferences(){
		String r = null;

		this.preferences = new com.teamderpy.victusludus.readerwriter.JLDLRandomReaderWriter("preferences.jldl");

		/************
		 * fonts
		 */
		r = this.preferences.read("settings->interface->fonts->main");

		if(r != null) {
			GUI.PRIMARY_FONT_ID = r;
		}

		r = this.preferences.read("settings->interface->fonts->big");

		if(r != null) {
			GUI.TITLE_FONT_ID= r;
		}

		r = this.preferences.read("settings->interface->fonts->small");

		if(r != null) {
			GUI.TOOLTIP_FONT_ID = r;
		}


		/************
		 * scaling factor
		 */
		r = this.preferences.read("settings->interface->dpi");

		if(r != null) {
			this.scalingFactor = Double.parseDouble(r);
		} else {
			this.initResolution();
		}


		/************
		 * max frame rate
		 */
		r = this.preferences.read("settings->interface->maximum framerate");

		if(r != null) {
			this.targetFramerate = Integer.parseInt(r);
		}


		/************
		 * resolution
		 */
		int xres = -1;
		int yres = -1;
		int bdepth = -1;
		int hfreq = -1;
		boolean fscreen = false;
		boolean vsync = false;

		r = this.preferences.read("settings->video->x resolution");
		if(r != null) {
			xres = Integer.parseInt(r);
		} else {
			xres = Gdx.graphics.getDesktopDisplayMode().width / 2;
		}

		r = this.preferences.read("settings->video->y resolution");
		if(r != null && xres != -1) {
			yres = Integer.parseInt(r);
		} else {
			yres = Gdx.graphics.getDesktopDisplayMode().height / 2;
		}

		r = this.preferences.read("settings->video->bit depth");
		if(r != null && yres != -1) {
			bdepth = Integer.parseInt(r);
		} else {
			bdepth = Gdx.graphics.getDesktopDisplayMode().bitsPerPixel;
		}

		r = this.preferences.read("settings->video->refresh rate");
		if(r != null && bdepth != -1) {
			hfreq = Integer.parseInt(r);
		} else {
			hfreq = Gdx.graphics.getDesktopDisplayMode().refreshRate;
		}

		r = this.preferences.read("settings->video->fullscreen");
		if(r != null && hfreq != -1) {
			fscreen = Boolean.parseBoolean(r);
		} else {
			fscreen = false;
		}

		this.IS_FULLSCREEN = fscreen;

		r = this.preferences.read("settings->video->vsync");
		if(r != null && hfreq != -1) {
			vsync = Boolean.parseBoolean(r);
		} else {
			vsync = false;
		}

		this.IS_VSYNC = vsync;

		if(hfreq != -1){

			//set the display mode to the stored one only if it is available
			for(final DisplayMode d:Gdx.graphics.getDisplayModes()){
				if(d.width == xres && d.height == yres && d.bitsPerPixel == bdepth && d.refreshRate == hfreq){
					this.currentDisplayMode = new FlexibleDisplayMode(d.width, d.height, fscreen);
					this.currentDisplayMode.displayMode = d;
					break;
				}
			}

			//couldn't find settings, so just make a new window with specified resolution
			if(this.currentDisplayMode == null){
				this.currentDisplayMode = new FlexibleDisplayMode(xres, yres, false);
			}
		}else{
			this.currentDisplayMode = new FlexibleDisplayMode(xres, yres, false);
		}
	}

	/**
	 * Load resources.
	 */
	private void loadResources(){
		this.assetManager = new AssetManager();
		
		//external data
		DataReader.ReadData();		
		GUI.loadFonts();
	}

	/**
	 * Initiates the resolution for pixel density purposes
	 */
	private void initResolution() {
		this.scalingFactor = Gdx.graphics.getDensity() * 160.0;

		if(this.scalingFactor <= 0.62){
			this.scalingFactor = 0.50;
		} else if(this.scalingFactor <= 0.87){
			this.scalingFactor = 0.75;
		} else if(this.scalingFactor <= 1.25){
			this.scalingFactor = 1.00;
		} else if(this.scalingFactor <= 1.75){
			this.scalingFactor = 1.50;
		} else {
			this.scalingFactor = 2.00;
		}
	}

	/**
	 * Creates the mouse pointer.
	 */
	public void createMousePointer(){
		this.mousePointer = new MousePointer();
	}

	/**
	 * Tick if permitted.
	 */
	public void tickIfPermitted() {
		final long interval = Time.getTimeNano() - this.lastTickTime;
		final long timePerTick = (long) (1.0/this.TARGET_TICKRATE*1000000000.0);

		//passed time must be at least the tick rate
		if(interval >= timePerTick){
			int ticksBehind = (int)(interval / timePerTick);

			while(ticksBehind-- > 0){
				this.tick();
				this.ticksSinceReset++;
			}
			
			this.lastTickTime = Time.getTimeNano();
		}
	}
	
	/**
	 * Render if permitted.
	 */
	public void renderIfPermitted(final SpriteBatch batch) {
		final long interval = Time.getTimeNano() - this.lastFrameDrawTime;
		final long timePerDraw = (long)(1.0/this.targetFramerate*1000000000.0);
		float deltaTime = (float)interval / 1000000000.0F;

		int framesBehind = Math.max(1, (int)(interval / timePerDraw));

		if(isFrameSkipOn){
			framesBehind = 1;
		}

		while(framesBehind-- > 0){
			deltaTime /= framesBehind;
			
			this.render(batch, deltaTime);
			this.framesSinceReset++;
		}
		
		this.lastFrameDrawTime = Time.getTimeNano();
	}
	
	/**
	 * Calculate fps
	 */
	private void calculateFPS(){
		final long interval = Time.getTimeMilli() - this.lastFPSResetTime;

		if (interval > 1000) {
			this.fps = (int)Math.round((this.framesSinceReset*(1000.0/interval)));
			this.tps = (int)Math.round((this.ticksSinceReset*(1000.0/interval)));
			
			System.err.println(this.framesSinceReset + " frames in " + interval + " time");
			System.err.println(this.ticksSinceReset + "  ticks in " + interval + " time");

			this.framesSinceReset = 0;
			this.ticksSinceReset = 0;
			this.lastFPSResetTime = Time.getTimeMilli();
		}
	}
	
	/**
	 * Set the title of the game window
	 */
	private void setTitle(){
		final long interval = Time.getTimeMilli() - this.lastTitleUpdateTime;
		
		if (interval > 1000) {
			Gdx.graphics.setTitle("FPS: " + this.fps +
				"   TPS: " + this.tps +
				"   JHeap:" + Gdx.app.getJavaHeap() / 1000000 +
				"M   NHeap: " + Gdx.app.getNativeHeap() / 1000000 +
				"M   EvtLsn: " + this.eventHandler.getListenerCounter() +
				"   EvtCnt: " + this.eventHandler.getEventCounter() +
				"   TCnt: " + this.getTickCount());
			
			this.lastTitleUpdateTime = Time.getTimeMilli();
		}
	}

	/**
	 * Tick
	 */
	public void tick(){
		if(this.currentView != null) {
			this.currentView.tick();
		}

		// GUI
		if(this.currentGUI != null) {
			this.currentGUI.tick();
		}

		if(this.currentDialog != null) {
			this.currentDialog.tick();
		}

		this.tickCount++;
	}

	/**
	 * Render
	 */
	public void render(final SpriteBatch batch, final float deltaT) {
		EasyGL.setViewport();
		EasyGL.clearScreen(1, 1, 1, 1);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		if(this.currentView != null) {
			this.currentView.render(batch, deltaT);
		}
		
		batch.end();

		// GUI
		//if(this.currentGUI != null) {
		//	this.currentGUI.render(batch, deltaT);
		//}
		
		if(this.currentUI != null) {
			this.currentUI.render(batch, deltaT);
		}

		//if(this.currentDialog != null) {
		//	this.currentDialog.render(batch, deltaT);
		//}
		
		
		EasyGL.freePoolResources();
		this.lastFrameDrawTime = Time.getTime();
	}

	/**
	 * Run.
	 */
	public void run(final SpriteBatch batch) {
		//rate of logic needs to be carefully controlled
		this.tickIfPermitted();

		//but draw as fast as possible
		this.renderIfPermitted(batch);

		//send events as fast as possible
		this.eventHandler.tick();
		
		//calc FPS
		this.calculateFPS();
		this.setTitle();
	}
	
	/**
	 * Start.
	 */
	public void start() {
		this.running = true;
		
		this.lastTickTime = Time.getTimeNano();
		this.lastFrameDrawTime = Time.getTimeNano();
		this.lastFPSResetTime = Time.getTimeMilli();
		this.lastTitleUpdateTime = Time.getTimeMilli();

		if (this.IS_DEBUGGING) {
			Gdx.app.log("info", "running");
		}
	}

	/**
	 * Stop.
	 */
	public void stop() {
		this.running = false;
		
		if (this.IS_DEBUGGING) {
			Gdx.app.log("info", "closing");
		}

		this.soundSystem.close();
		
		Gdx.app.exit();
	}

	/**
	 * Change gui.
	 *
	 * @param newGUI the new gui
	 */
	public void changeGUI(final GUI newGUI) {
		if (this.currentGUI != null) {
			this.currentGUI.unregisterListeners();
		}
		this.currentGUI = newGUI;
		this.inputPoller.forceMouseMove();
	}
	
	/**
	 * Change to a new UI
	 * 
	 * @param newUI the UI to switch to
	 */
	public void changeUI(final UI newUI) {
		if (this.currentUI != null) {
			//this.currentUI.unregisterListeners();
			this.currentUI.dispose();
		}
		this.currentUI = newUI;
		this.inputPoller.forceMouseMove();
	}

	/**
	 * Change display mode.
	 *
	 * @param d the display mode
	 */
	public void changeDisplayMode(final FlexibleDisplayMode d){
		this.currentDisplayMode = d;

		this.setDisplay();
	}

	/**
	 * Display dialog.
	 *
	 * @param dialogGUI the dialog gui
	 */
	public void displayDialog(final DialogBox dialogGUI){
		if (this.currentDialog != null){
			this.currentDialog.unregisterListeners();
		}
		this.currentDialog = dialogGUI;
		this.inputPoller.forceMouseMove();
	}

	/**
	 * Change the view, or remove the running one if passed a null value.
	 *
	 * @param view the view, or null to not run the current view
	 * @param requestedSettings the requested settings to start the view with (not used if view passed is null)
	 */
	public void changeView(final IView view, final ISettings requestedSettings){
		if (this.currentView != null){
			this.currentView.unregisterListeners();
		}
		this.currentView = view;

		if(view != null){
			try {
				this.currentView.init(requestedSettings);
			} catch (GameException e) {
				e.printStackTrace();
			}
		}

		this.inputPoller.forceMouseMove();
	}

	/**
	 * Actually terminate the game
	 */
	public void terminateView(){
		this.changeGUI(new com.teamderpy.victusludus.gui.GUIMainMenu());

		if (this.currentView != null){
			this.currentView.setRunning(false);
			this.changeView(null, null);
		}

		this.mousePointer.loadPointer(MousePointer.DEFAULT_CURSOR);
	}

	/**
	 * A request to stop the game.  Send signal to game.
	 */
	public void quitView(){
		if (this.currentView != null){
			this.currentView.setQuitSignal(true);
		}
	}

	/**
	 * X resolution.
	 *
	 * @return the int
	 */
	public int X_RESOLUTION(){
		return Gdx.graphics.getWidth();
	}

	/**
	 * Y resolution.
	 *
	 * @return the int
	 */
	public int Y_RESOLUTION(){
		return Gdx.graphics.getHeight();
	}

	/**
	 * Gets the tick count.
	 *
	 * @return the tick count
	 */
	public long getTickCount() {
		return this.tickCount;
	}

	@Override
	public void onResize (ResizeEvent resizeEvent) {
		this.currentDisplayMode = new FlexibleDisplayMode(resizeEvent.getWidth(), resizeEvent.getHeight(), false);
		this.setDisplay();
	}
}

