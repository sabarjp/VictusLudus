package com.teamderpy.victusludus.engine;


import java.awt.Toolkit;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.teamderpy.victusludus.data.DataReader;
import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.game.Game;
import com.teamderpy.victusludus.game.GameSettings;
import com.teamderpy.victusludus.game.cosmos.Universe;
import com.teamderpy.victusludus.gui.DialogBox;
import com.teamderpy.victusludus.gui.GUI;
import com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent;


// TODO: Auto-generated Javadoc
/**
 * The Class Engine.
 */
public class Engine{

	/** The Constant INSTANCE. */
	private static final Engine INSTANCE = new Engine();

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

	/** The current game. */
	public Game currentGame = null;

	/** The mouse pointer. */
	public MousePointer mousePointer = null;

	/** The sound system. */
	public SoundSystem soundSystem = null;

	/** The global listener. */
	private GlobalListener globalListener;

	/** The graphics. */
	public Graphics graphics = new Graphics();

	/** The display modes. */
	public ArrayList<DisplayMode> displayModes = new ArrayList<DisplayMode>();

	/** The current display mode. */
	public DisplayMode currentDisplayMode = null;

	/** The gbuf. */
	public GraphicsBuffer gbuf = null;

	/** The event handler. */
	public MasterEventHandler eventHandler = null;

	/** The preferences. */
	public com.teamderpy.victusludus.readerwriter.JLDLRandomReaderWriter preferences;

	/**
	 * Instantiates a new engine.
	 */
	private Engine(){};

	/**
	 * Gets the single instance of Engine.
	 *
	 * @return single instance of Engine
	 */
	public static Engine getInstance(){
		return Engine.INSTANCE;
	}

	/**
	 * Initialize.
	 *
	 * @param args the args
	 */
	public void initialize(final String[] args) {
		this.loadPreferences();

		try {
			for(final DisplayMode d:Display.getAvailableDisplayModes()){
				if(d.getFrequency() == Display.getDesktopDisplayMode().getFrequency()){
					this.displayModes.add(d);
				}
			}

			this.createDisplayMode(this.currentDisplayMode);
		} catch (final LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		this.initGL();
		this.setGLView();

		this.gbuf = new GraphicsBuffer();
		this.eventHandler = new MasterEventHandler();

		this.createMousePointer();
		this.loadResources();

		this.initSoundSystem();

		this.changeGUI(new com.teamderpy.victusludus.gui.GUIMainMenu());
		Keyboard.enableRepeatEvents(true);

		// LoadLevel();

		this.globalListener = new GlobalListener();
		this.globalListener.registerListeners();

		/********************************************************************************************/

		//StarDate starDate = new StarDate(new BigInteger("23463645346"));

		Universe universe = new Universe();

		//Galaxy galaxy = new Galaxy(universe.getCosmicDate(), universe);
		//Star sun = new Star(universe.getCosmicDate(), galaxy, Cosmology.SOLAR_MASS.multiply(new BigDecimal("1"), Star.STELLAR_RND));

		BigDecimal delta = BigDecimal.valueOf(100000000L);

		for(int i=1; i<20; i++){
			universe.tick(delta);
		}

		//		for(int i=1; i<20; i++){
		//			sun.tick(delta);
		//			starDate.addYears(delta.toBigInteger());
		//		}
		//
		//		Planet planet = new Planet(starDate, sun);
		//
		//		for(int i=1; i<20; i++){
		//			sun.tick(delta);
		//			planet.tick(delta);
		//			starDate.addYears(delta.toBigInteger());
		//		}

		//System.err.println(sun.getHistory());
		//System.err.println(planet);


		//System.err.println(Cosmology.calculateEccentricAnomaly(0.5, new BigDecimal(27)).toPlainString());
		//System.err.println(Cosmology.calculateTrueAnomaly(0.5, new BigDecimal("48.43417991487915")).toPlainString());
		//System.err.println(Cosmology.calculateKeplerDistanceAtTime(sun.getMass(), planet.getMass(), planet.getOrbitSemiMajorAxis(), new BigDecimal(222222), planet.getOrbitEccentricity()).toPlainString());
		//System.err.println(Cosmology.calculateKeplerCoordinateAtTime(sun.getMass(), planet.getMass(), planet.getOrbitSemiMajorAxis(), new BigDecimal(222222), planet.getOrbitEccentricity())[0]);
		//System.err.println(Cosmology.calculateKeplerCoordinateAtTime(sun.getMass(), planet.getMass(), planet.getOrbitSemiMajorAxis(), new BigDecimal(222222), planet.getOrbitEccentricity())[1]);

		//System.err.println(Cosmology.calculateOrbitalPeriod(sun.getMass(), planet.getMass(), planet.getOrbitSemiMajorAxis()));

		System.exit(0);

		/********************************************************************************************/

		this.start();
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
			xres = Display.getDesktopDisplayMode().getWidth() / 2;
		}

		r = this.preferences.read("settings->video->y resolution");
		if(r != null && xres != -1) {
			yres = Integer.parseInt(r);
		} else {
			yres = Display.getDesktopDisplayMode().getHeight() / 2;
		}

		r = this.preferences.read("settings->video->bit depth");
		if(r != null && yres != -1) {
			bdepth = Integer.parseInt(r);
		} else {
			bdepth = Display.getDesktopDisplayMode().getBitsPerPixel();
		}

		r = this.preferences.read("settings->video->refresh rate");
		if(r != null && bdepth != -1) {
			hfreq = Integer.parseInt(r);
		} else {
			hfreq = Display.getDesktopDisplayMode().getFrequency();
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
			try {
				for(final DisplayMode d:Display.getAvailableDisplayModes()){
					if(d.getWidth() == xres && d.getHeight() == yres && d.getBitsPerPixel() == bdepth && d.getFrequency() == hfreq){
						this.currentDisplayMode = d;
						break;
					}
				}
			} catch (final LWJGLException e) {
				e.printStackTrace();
			}

			//couldn't find settings, so just make a new window with specified resolution
			if(this.currentDisplayMode == null){
				this.currentDisplayMode = new DisplayMode(xres, yres);
			}
		}else{
			this.currentDisplayMode = new DisplayMode(xres, yres);
		}
	}

	/**
	 * Load resources.
	 */
	private void loadResources(){
		final int loadingBarLength = this.X_RESOLUTION()/3;
		final int loadingBarHeight = 20;
		final int loadingBarX = this.X_RESOLUTION()/3;
		final int loadingBarY = this.Y_RESOLUTION()/2 - loadingBarHeight/2;
		final Color loadingBarColor = new Color(122, 214, 187);
		final Color borderColor = Color.black;

		this.clearGL();

		Image loadText = null;

		try {
			loadText = new Image("res/sprites/gui/loading_text.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}

		this.graphics.drawImage(loadText, loadingBarX, loadingBarY - loadText.getHeight());
		this.graphics.setColor(borderColor);
		this.graphics.fillRect(loadingBarX-2, loadingBarY-2, loadingBarLength+4, loadingBarHeight+4);
		this.graphics.setColor(loadingBarColor);
		this.graphics.fillRect(loadingBarX, loadingBarY, loadingBarLength/4, loadingBarHeight);
		Display.update();

		//external data
		DataReader.ReadData();

		this.clearGL();
		this.graphics.drawImage(loadText, loadingBarX, loadingBarY - loadText.getHeight());
		this.graphics.setColor(borderColor);
		this.graphics.fillRect(loadingBarX-2, loadingBarY-2, loadingBarLength+4, loadingBarHeight+4);
		this.graphics.setColor(loadingBarColor);
		this.graphics.fillRect(loadingBarX, loadingBarY, loadingBarLength/3, loadingBarHeight);
		Display.update();

		this.clearGL();
		this.graphics.drawImage(loadText, loadingBarX, loadingBarY - loadText.getHeight());
		this.graphics.setColor(borderColor);
		this.graphics.fillRect(loadingBarX-2, loadingBarY-2, loadingBarLength+4, loadingBarHeight+4);
		this.graphics.setColor(loadingBarColor);
		this.graphics.fillRect(loadingBarX, loadingBarY, loadingBarLength/2, loadingBarHeight);
		Display.update();

		//fonts and initializing, this is somewhat slow
		GUI.loadFonts();
		GUI.loadGUISpriteSheets();

		this.clearGL();
		this.graphics.drawImage(loadText, loadingBarX, loadingBarY - loadText.getHeight());
		this.graphics.setColor(borderColor);
		this.graphics.fillRect(loadingBarX-2, loadingBarY-2, loadingBarLength+4, loadingBarHeight+4);
		this.graphics.setColor(loadingBarColor);
		this.graphics.fillRect(loadingBarX, loadingBarY, loadingBarLength, loadingBarHeight);
		Display.update();
	}

	/**
	 * Inits the gl.
	 */
	private void initGL() {
		// OpenGL initialize
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	/**
	 * Sets the gl view.
	 */
	private void setGLView(){
		GL11.glViewport(0, 0, this.X_RESOLUTION(), this.Y_RESOLUTION());
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, this.X_RESOLUTION(), this.Y_RESOLUTION(), 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	/**
	 * Clear gl.
	 */
	private void clearGL(){
		GL11.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		GL11.glClearDepth(1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	/**
	 * Inits the resolution.
	 */
	private void initResolution() {
		this.scalingFactor = Toolkit.getDefaultToolkit().getScreenResolution() / 92.0;

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

			Display.setTitle("FPS: " + (int)(this.framesSinceReset*(1000.0/interval)) +
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
	 * Tick.
	 */
	public void tick(){
		if(this.IS_DEBUGGING){
			System.err.println(this.eventHandler.getListenerList());
		}

		if(this.currentGame != null) {
			this.currentGame.tick();
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
	 * Render.
	 */
	private void render() {
		this.clearGL();

		if(Display.wasResized()){
			this.eventHandler.signal(new ResizeEvent(this.currentDisplayMode, Display.getWidth(), Display.getHeight()));
			this.setGLView();
		}

		if(this.currentGame != null) {
			this.currentGame.render();
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

		if (this.IS_DEBUGGING) {
			VictusLudus.LOGGER.info("running");
		}

		while (this.running) {
			//this section constitutes a single frame
			this.framesSinceReset++;
			this.calculateFPS();

			//rate of logic needs to be carefully controlled
			this.tickIfPermitted();

			//draw as fast as possible
			this.render();

			//take input as fast as possible
			InputPoller.pollInput();

			//send events as fast as possible
			this.eventHandler.tick();

			//syncing will slow how often the frame code here is ran
			Display.update();
			Display.sync(this.targetFramerate);

			if (Display.isCloseRequested()) {
				this.running = false;
			}
		}

		if (this.IS_DEBUGGING) {
			VictusLudus.LOGGER.info("closing");
		}

		this.soundSystem.close();
		Display.destroy();
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
		InputPoller.forceMouseMove();
	}

	/**
	 * Creates the display mode.
	 *
	 * @param d the d
	 */
	public void createDisplayMode(final DisplayMode d){
		try {
			this.currentDisplayMode = d;

			Display.setDisplayMode(this.currentDisplayMode);
			Display.setFullscreen(this.IS_FULLSCREEN);
			Display.setResizable(this.IS_RESIZABLE);
			Display.create();
			Display.setVSyncEnabled(this.IS_VSYNC);
		} catch (final LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Change display mode.
	 *
	 * @param d the d
	 */
	public void changeDisplayMode(final DisplayMode d){
		try {
			Display.setDisplayMode(d);
			Display.setFullscreen(this.IS_FULLSCREEN);
			Display.setVSyncEnabled(this.IS_VSYNC);
			this.currentDisplayMode = d;
		} catch (final LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		this.setGLView();
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
		InputPoller.forceMouseMove();
	}

	/**
	 * Change the game, or remove the running one if passed a null value.
	 *
	 * @param game the game, or null to not run the current game
	 * @param requestedSettings the requested settings to start the game with (not used if game passed is null)
	 */
	public void changeGame(final Game game, final GameSettings requestedSettings){
		if (this.currentGame != null){
			this.currentGame.unregisterListeners();
		}
		this.currentGame = game;

		if(game != null){
			try {
				this.currentGame.init(requestedSettings);
			} catch (GameException e) {
				e.printStackTrace();
			}
		}

		InputPoller.forceMouseMove();
	}

	/**
	 * Actually terminate the game
	 */
	public void terminateGame(){
		VictusLudus.e.changeGUI(new com.teamderpy.victusludus.gui.GUIMainMenu());

		if (this.currentGame != null){
			this.currentGame.setRunning(false);
			this.changeGame(null, null);
		}

		VictusLudus.e.mousePointer.loadPointer(MousePointer.DEFAULT_CURSOR);
	}

	/**
	 * A request to stop the game.  Send signal to game.
	 */
	public void quitGame(){
		if (this.currentGame != null){
			this.currentGame.setQuitSignal(true);
		}
	}

	/**
	 * X resolution.
	 *
	 * @return the int
	 */
	public int X_RESOLUTION(){
		return Display.getWidth();
	}

	/**
	 * Y resolution.
	 *
	 * @return the int
	 */
	public int Y_RESOLUTION(){
		return Display.getHeight();
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

