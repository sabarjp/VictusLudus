package com.teamderpy.victusludus.engine;


import java.awt.Toolkit;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.teamderpy.victusludus.data.DataReader;
import com.teamderpy.victusludus.game.cosmos.Universe;
import com.teamderpy.victusludus.gui.DialogBox;
import com.teamderpy.victusludus.gui.GUI;
import com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent;

/**
 * The Class Engine.
 */
public class Engine{
	/** The target tickrate. */
	private final int TARGET_TICKRATE = 60;       //logic occurs this many times per second

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
	private boolean running = false;

	/** The frames since reset. */
	private int framesSinceReset;

	/** The ticks since reset. */
	private int ticksSinceReset;

	/** The last frame reset time. */
	private long lastFrameResetTime;

	/** The last frame draw time. */
	private long lastFrameDrawTime;

	/** The last tick time. */
	private long lastTickTime;

	/** The tick count. */
	private long tickCount = 0;

	/** The player cash. */
	public int playerCash = 100000;

	/** The current gui. */
	public GUI currentGUI = null;

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

	/** The preferences. */
	public com.teamderpy.victusludus.readerwriter.JLDLRandomReaderWriter preferences;
	
	/** The asset manager */
	public AssetManager assetManager;
	
	/** The input poller and signaller */
	public InputPoller inputPoller;

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
		this.initInputPoller();

		this.createMousePointer();
		
		this.initSoundSystem();

		//this.changeGUI(new com.teamderpy.victusludus.gui.GUIMainMenu());

		//LoadLevel();

		this.globalListener = new GlobalListener();
		this.globalListener.registerListeners();

		//this.start();
	}
	
	private void setDisplay(){
		if(Gdx.graphics.supportsDisplayModeChange()){
			if(this.currentDisplayMode.displayMode != null && this.currentDisplayMode.isFullscreen){
				Gdx.graphics.setDisplayMode(this.currentDisplayMode.displayMode);
			} else {
				Gdx.graphics.setDisplayMode(this.currentDisplayMode.width, this.currentDisplayMode.height, this.currentDisplayMode.isFullscreen);
			}
		}
	}
	
	/**
	 * Sets up the input poller and starts listening for events
	 */
	private void initInputPoller(){
		this.inputPoller = new InputPoller();
		Gdx.input.setInputProcessor(inputPoller);
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
			GUI.PMONO_FONT_ID = r;
		}

		r = this.preferences.read("settings->interface->fonts->big");

		if(r != null) {
			GUI.TITLE_FONT_ID= r;
		}

		r = this.preferences.read("settings->interface->fonts->small");

		if(r != null) {
			GUI.TOOLT_FONT_ID = r;
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
		
		final int loadingBarLength = this.X_RESOLUTION()/3;
		final int loadingBarHeight = 20;
		final int loadingBarX = this.X_RESOLUTION()/3;
		final int loadingBarY = this.Y_RESOLUTION()/2 - loadingBarHeight/2;
		final Color loadingBarColor = new Color(0.44F, 0.86F, 0.61F, 1);
		final Color borderColor = Color.BLACK;

//		this.clearGL();
//		
//		TextureAtlas atlas;
//		atlas = new TextureAtlas(Gdx.files.internal("sprites/spritesheet"));
//		Sprite sprite = atlas.createSprite("gui/loading_text");
//
//		this.graphics.drawImage(loadText, loadingBarX, loadingBarY - loadText.getHeight());
//		this.graphics.setColor(borderColor);
//		this.graphics.fillRect(loadingBarX-2, loadingBarY-2, loadingBarLength+4, loadingBarHeight+4);
//		this.graphics.setColor(loadingBarColor);
//		this.graphics.fillRect(loadingBarX, loadingBarY, loadingBarLength/4, loadingBarHeight);
//		Display.update();

		//external data
		
		DataReader.ReadData();		
//		GUI.loadFonts();
//		GUI.loadGUISpriteSheets();

//		this.clearGL();
//		this.graphics.drawImage(loadText, loadingBarX, loadingBarY - loadText.getHeight());
//		this.graphics.setColor(borderColor);
//		this.graphics.fillRect(loadingBarX-2, loadingBarY-2, loadingBarLength+4, loadingBarHeight+4);
//		this.graphics.setColor(loadingBarColor);
//		this.graphics.fillRect(loadingBarX, loadingBarY, loadingBarLength/3, loadingBarHeight);
//		Display.update();

//		this.clearGL();
//		this.graphics.drawImage(loadText, loadingBarX, loadingBarY - loadText.getHeight());
//		this.graphics.setColor(borderColor);
//		this.graphics.fillRect(loadingBarX-2, loadingBarY-2, loadingBarLength+4, loadingBarHeight+4);
//		this.graphics.setColor(loadingBarColor);
//		this.graphics.fillRect(loadingBarX, loadingBarY, loadingBarLength/2, loadingBarHeight);
//		Display.update();

		//fonts and initializing, this is somewhat slow


//		this.clearGL();
//		this.graphics.drawImage(loadText, loadingBarX, loadingBarY - loadText.getHeight());
//		this.graphics.setColor(borderColor);
//		this.graphics.fillRect(loadingBarX-2, loadingBarY-2, loadingBarLength+4, loadingBarHeight+4);
//		this.graphics.setColor(loadingBarColor);
//		this.graphics.fillRect(loadingBarX, loadingBarY, loadingBarLength, loadingBarHeight);
//		Display.update();
	}

	/**
	 * Clear gl.
	 */
	private void clearGL(){
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
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
	 * Start.
	 */
	public void start() {
		this.running = true;
		this.run();
	}

	/**
	 * Stop.
	 */
	public void stop() {
		this.running = false;
	}

	/**
	 * Calculate fps.
	 */
	private void calculateFPS(){
		final long interval = Time.getTime() - this.lastFrameResetTime;

		if (interval > 1000) {
			final Runtime rt = Runtime.getRuntime();

			Gdx.graphics.setTitle("FPS: " + (int)(this.framesSinceReset*(1000.0/interval)) +
					"   TPS: " + (int)(this.ticksSinceReset*(1000.0/interval)) +
					"   Mem:" + rt.totalMemory() / 1000000 +
					"M   Free: " + rt.freeMemory() / 1000000 +
					"M   Use: " + (rt.totalMemory() - rt.freeMemory()) / 1000000 +
					"M   EvtLsn: " + this.eventHandler.getListenerCounter() +
					"   EvtCnt: " + this.eventHandler.getEventCounter() +
					"   TCnt: " + this.getTickCount());

			this.framesSinceReset = 0;
			this.ticksSinceReset = 0;
			this.lastFrameResetTime = Time.getTime();
		}
	}

	/**
	 * Tick if permitted.
	 */
	public void tickIfPermitted() {
		final long interval = Time.getTime() - this.lastTickTime;
		final long timePerTick = (long) (1.0/this.TARGET_TICKRATE*1000);

		//passed time must be at least the tick rate
		if(interval >= timePerTick){
			int ticks = (int)(interval / timePerTick);

			while(ticks-- > 0){
				this.tick();
				this.ticksSinceReset++;
				this.lastTickTime += timePerTick;
			}
		}
	}

	/**
	 * Tick
	 */
	public void tick(){
		if(this.IS_DEBUGGING){
			System.err.println(this.eventHandler.getListenerList());
		}

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
	private void render(final float deltaT) {
		this.clearGL();

		this.lastFrameDrawTime = Time.getTime();

		if(this.currentView != null) {
			this.currentView.render(deltaT);
		}

		// GUI
		if(this.currentGUI != null) {
			this.currentGUI.render();
		}

		if(this.currentDialog != null) {
			this.currentDialog.render();
		}
	}

	/**
	 * Run.
	 */
	public void run() {
		this.lastFrameResetTime = Time.getTime();
		this.lastTickTime = Time.getTime();
		this.lastFrameDrawTime = Time.getTime();

		if (this.IS_DEBUGGING) {
			Gdx.app.log("info", "running");
		}

		while (this.running) {
			//this section constitutes a single frame
			this.framesSinceReset++;
			this.calculateFPS();

			//rate of logic needs to be carefully controlled
			this.tickIfPermitted();

			//draw as fast as possible
			this.render((Time.getTime() - this.lastFrameDrawTime)/1000.0F);

			//take input as fast as possible
			//InputPoller.pollInput();

			//send events as fast as possible
			this.eventHandler.tick();

			//syncing will slow how often the frame code here is ran
			//Display.update();
			//Display.sync(this.targetFramerate);

			//if (Display.isCloseRequested()) {
			//	this.running = false;
			//}
		}

		if (this.IS_DEBUGGING) {
			Gdx.app.log("info", "closing");
		}

		this.soundSystem.close();
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
}

