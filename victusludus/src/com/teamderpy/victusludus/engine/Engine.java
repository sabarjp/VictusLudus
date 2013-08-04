
package com.teamderpy.victusludus.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.teamderpy.victusludus.data.DataLoader;
import com.teamderpy.victusludus.engine.graphics.EasyGL;
import com.teamderpy.victusludus.game.cosmos.Universe;
import com.teamderpy.victusludus.gui.GUI;
import com.teamderpy.victusludus.gui.UI;
import com.teamderpy.victusludus.gui.UIMainMenu;
import com.teamderpy.victusludus.gui.eventhandler.ResizeListener;
import com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent;
import com.teamderpy.victusludus.readerwriter.JLDLRandomReaderWriter;

/** The Class Engine. */
public class Engine implements ResizeListener {
	/** The target tick rate, how often logic occurs */
	private final int TARGET_TICKRATE = 60;

	/** The is debugging. */
	public boolean IS_DEBUGGING = false;

	/** Whether shaders are enabled or not. */
	public boolean IS_SHADERS_ENABLED = true;

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

	public UI currentUI = null;

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
	public Array<FlexibleDisplayMode> displayModes = new Array<FlexibleDisplayMode>();

	/** The current display mode. */
	public FlexibleDisplayMode currentDisplayMode = null;

	/** The event handler. */
	public MasterEventHandler eventHandler = null;

	/** The input multiplexer */
	public InputMultiplexer inputMultiplexer = new InputMultiplexer();

	/** The preferences. */
	public JLDLRandomReaderWriter preferences;

	/** The asset manager */
	public AssetManager assetManager;

	/** The input poller and signaller */
	public InputPoller inputPoller;

	/** shape renderer */
	public ShapeRenderer shapeRenderer;

	/** the orthographic camera */
	public OrthographicCamera ocamera;

	/** frames per second */
	private int fps = 0;

	/** ticks per second */
	private int tps = 0;

	/**
	 * Initialize a new game engine
	 * 
	 * @param camera the orthographic camera
	 */
	public Engine (final OrthographicCamera camera) {
		this.ocamera = camera;

		this.shapeRenderer = new ShapeRenderer();
		this.shapeRenderer.setProjectionMatrix(camera.combined);
	}

	/** Starts to initialize the engine, up to queueing the data load */
	public void initializeBegin () {
		this.loadPreferences();

		this.eventHandler = new MasterEventHandler();

		// add displays with the same refresh rate as the desktop
		for (final DisplayMode d : Gdx.graphics.getDisplayModes()) {
			if (d.refreshRate == Gdx.graphics.getDesktopDisplayMode().refreshRate) {
				FlexibleDisplayMode fd = new FlexibleDisplayMode(d.width, d.height, true);
				fd.displayMode = d;
				this.displayModes.add(fd);
			}
		}

		this.displayModes.sort();
		this.displayModes.reverse();

		// sets the current resolution
		this.setDisplay();

		this.loadPreResources();

	}

	/** Finished initializing the engine. DO NOT CALL UNTIL RESOURCES ARE LOADED */
	public void initializeEnd () {
		this.loadPostResources();

		GUI.loadGUISpriteSheets();

		this.initInputPoller();

		this.createMousePointer();

		this.initSoundSystem();

		this.changeUI(new com.teamderpy.victusludus.gui.UIMainMenu());

		this.globalListener = new GlobalListener();
		this.globalListener.registerListeners();

		this.eventHandler.resizeHandler.registerPlease(this);

		this.start();
	}

	private void setDisplay () {
		if (Gdx.graphics.supportsDisplayModeChange()) {
			if (this.currentDisplayMode.displayMode != null && this.currentDisplayMode.isFullscreen) {
				Gdx.graphics.setDisplayMode(this.currentDisplayMode.displayMode);
			} else {
				Gdx.graphics.setDisplayMode(this.currentDisplayMode.width, this.currentDisplayMode.height,
					this.currentDisplayMode.isFullscreen);
			}

			this.ocamera.setToOrtho(true, this.X_RESOLUTION(), this.Y_RESOLUTION());
			this.ocamera.update();

			this.shapeRenderer.setProjectionMatrix(this.ocamera.combined);
		}
	}

	/** Sets up the input poller and starts listening for events */
	private void initInputPoller () {
		this.inputPoller = new InputPoller();
		this.inputMultiplexer.addProcessor(this.inputPoller);
		Gdx.input.setInputProcessor(this.inputMultiplexer);
	}

	/** Inits the sound system. */
	private void initSoundSystem () {
		this.soundSystem = new SoundSystem();
	}

	/** Load preferences. */
	private void loadPreferences () {
		String r = null;

		this.preferences = new JLDLRandomReaderWriter("config/preferences.jldl");

		/************ fonts */
		r = this.preferences.read("settings->interface->fonts->main");

		if (r != null) {
			GUI.PRIMARY_FONT_ID = r;
		}

		r = this.preferences.read("settings->interface->fonts->big");

		if (r != null) {
			GUI.TITLE_FONT_ID = r;
		}

		r = this.preferences.read("settings->interface->fonts->small");

		if (r != null) {
			GUI.TOOLTIP_FONT_ID = r;
		}

		/************ scaling factor */
		r = this.preferences.read("settings->interface->dpi");

		if (r != null) {
			this.scalingFactor = Double.parseDouble(r);
		} else {
			this.initResolution();
		}

		/************ max frame rate */
		r = this.preferences.read("settings->interface->maximum framerate");

		if (r != null) {
			this.targetFramerate = Integer.parseInt(r);
		}

		/************ resolution */
		int xres = -1;
		int yres = -1;
		int bdepth = -1;
		int hfreq = -1;
		boolean fscreen = false;
		boolean vsync = false;

		r = this.preferences.read("settings->video->x resolution");
		if (r != null) {
			xres = Integer.parseInt(r);
		} else {
			xres = Gdx.graphics.getDesktopDisplayMode().width / 2;
		}

		r = this.preferences.read("settings->video->y resolution");
		if (r != null && xres != -1) {
			yres = Integer.parseInt(r);
		} else {
			yres = Gdx.graphics.getDesktopDisplayMode().height / 2;
		}

		r = this.preferences.read("settings->video->bit depth");
		if (r != null && yres != -1) {
			bdepth = Integer.parseInt(r);
		} else {
			bdepth = Gdx.graphics.getDesktopDisplayMode().bitsPerPixel;
		}

		r = this.preferences.read("settings->video->refresh rate");
		if (r != null && bdepth != -1) {
			hfreq = Integer.parseInt(r);
		} else {
			hfreq = Gdx.graphics.getDesktopDisplayMode().refreshRate;
		}

		r = this.preferences.read("settings->video->fullscreen");
		if (r != null && hfreq != -1) {
			fscreen = Boolean.parseBoolean(r);
		} else {
			fscreen = false;
		}

		this.IS_FULLSCREEN = fscreen;

		r = this.preferences.read("settings->video->vsync");
		if (r != null && hfreq != -1) {
			vsync = Boolean.parseBoolean(r);
		} else {
			vsync = false;
		}

		this.IS_VSYNC = vsync;

		if (hfreq != -1) {

			/* set the display mode to the stored one only if it is available */
			for (final DisplayMode d : Gdx.graphics.getDisplayModes()) {
				if (d.width == xres && d.height == yres && d.bitsPerPixel == bdepth && d.refreshRate == hfreq) {
					this.currentDisplayMode = new FlexibleDisplayMode(d.width, d.height, fscreen);
					this.currentDisplayMode.displayMode = d;
					break;
				}
			}

			/*
			 * couldn't find settings, so just make a new window with specified
			 * resolution
			 */
			if (this.currentDisplayMode == null) {
				this.currentDisplayMode = new FlexibleDisplayMode(xres, yres, false);
			}
		} else {
			this.currentDisplayMode = new FlexibleDisplayMode(xres, yres, false);
		}
	}

	private void loadPreResources () {
		this.assetManager = new AssetManager();

		/* external data */
		DataLoader.PreLoad();
		GUI.loadFonts();
	}

	private void loadPostResources () {
		DataLoader.PostLoad();
	}

	/** Initiates the resolution for pixel density purposes */
	private void initResolution () {
		this.scalingFactor = Gdx.graphics.getDensity() * 160.0;

		if (this.scalingFactor <= 0.62) {
			this.scalingFactor = 0.50;
		} else if (this.scalingFactor <= 0.87) {
			this.scalingFactor = 0.75;
		} else if (this.scalingFactor <= 1.25) {
			this.scalingFactor = 1.00;
		} else if (this.scalingFactor <= 1.75) {
			this.scalingFactor = 1.50;
		} else {
			this.scalingFactor = 2.00;
		}
	}

	/** Creates the mouse pointer. */
	public void createMousePointer () {
		this.mousePointer = new MousePointer();
	}

	/** Tick if permitted. */
	public void tickIfPermitted () {
		final long interval = Time.getTimeNano() - this.lastTickTime;
		final long timePerTick = (long)(1.0 / this.TARGET_TICKRATE * 1000000000.0);
		float deltaTime = interval / 1000000000.0F;

		/* passed time must be at least the tick rate */
		if (interval >= timePerTick) {
			int ticksBehind = (int)(interval / timePerTick);

			while (ticksBehind-- > 0) {
				if (ticksBehind > 1) {
					deltaTime /= ticksBehind;
				}

				this.tick(deltaTime);
				this.ticksSinceReset++;
				this.lastTickTime += timePerTick;
			}
		}
	}

	/** Render if permitted. */
	public void renderIfPermitted (final SpriteBatch spriteBatch, final ModelBatch modelBatch) {
		final long interval = Time.getTimeNano() - this.lastFrameDrawTime;
		final long timePerDraw = (long)(1.0 / this.targetFramerate * 1000000000.0);
		float deltaTime = interval / 1000000000.0F;

		int framesBehind = Math.max(1, (int)(interval / timePerDraw));

		if (this.isFrameSkipOn) {
			framesBehind = 1;
		}

		while (framesBehind > 0) {
			if (framesBehind > 1) {
				deltaTime /= framesBehind;
			}

			this.render(spriteBatch, modelBatch, deltaTime);
			this.framesSinceReset++;

			framesBehind--;
		}

		this.lastFrameDrawTime = Time.getTimeNano();
	}

	/** Calculate fps */
	private void calculateFPS () {
		final long interval = Time.getTimeMilli() - this.lastFPSResetTime;

		if (interval > 1000) {
			this.fps = (int)Math.round((this.framesSinceReset * (1000.0 / interval)));
			this.tps = (int)Math.round((this.ticksSinceReset * (1000.0 / interval)));

			this.framesSinceReset = 0;
			this.ticksSinceReset = 0;
			this.lastFPSResetTime = Time.getTimeMilli();
		}
	}

	/** Set the title of the game window */
	private void setTitle () {
		final long interval = Time.getTimeMilli() - this.lastTitleUpdateTime;

		if (interval > 1000) {
			Gdx.graphics.setTitle("FPS: " + this.fps + "   TPS: " + this.tps + "   JHeap:" + Gdx.app.getJavaHeap() / 1000000
				+ "M   NHeap: " + Gdx.app.getNativeHeap() / 1000000 + "M   EvtLsn: " + this.eventHandler.getListenerCounter()
				+ "   EvtCnt: " + this.eventHandler.getEventCounter() + "   TCnt: " + this.getTickCount());

			this.lastTitleUpdateTime = Time.getTimeMilli();
		}
	}

	/**
	 * Tick
	 * @param deltaTime
	 */
	public void tick (final float deltaTime) {
		if (this.currentView != null) {
			this.currentView.tick(deltaTime);
		}

		this.tickCount++;
	}

	/** Render */
	public void render (final SpriteBatch spriteBatch, final ModelBatch modelBatch, final float deltaTime) {
		EasyGL.setViewport();
		EasyGL.clearScreen(0, 0, 0, 1);

		spriteBatch.setProjectionMatrix(this.ocamera.combined);

		if (this.currentView != null) {
			this.currentView.render(spriteBatch, modelBatch, deltaTime);
		}

		if (this.currentUI != null) {
			this.currentUI.render(spriteBatch, deltaTime);
		}

		this.lastFrameDrawTime = Time.getTime();
	}

	/** Run. */
	public void run (final SpriteBatch spriteBatch, final ModelBatch modelBatch) {
		// rate of logic needs to be carefully controlled
		this.tickIfPermitted();

		// but draw as fast as possible
		this.renderIfPermitted(spriteBatch, modelBatch);

		// send events as fast as possible
		this.eventHandler.tick();

		// calc FPS
		this.calculateFPS();
		this.setTitle();
	}

	/** Start. */
	public void start () {
		this.running = true;

		this.lastTickTime = Time.getTimeNano();
		this.lastFrameDrawTime = Time.getTimeNano();
		this.lastFPSResetTime = Time.getTimeMilli();
		this.lastTitleUpdateTime = Time.getTimeMilli();

		if (this.IS_DEBUGGING) {
			Gdx.app.log("info", "running");
		}
	}

	/** Stop. */
	public void stop () {
		this.running = false;

		if (this.IS_DEBUGGING) {
			Gdx.app.log("info", "closing");
		}

		this.soundSystem.close();

		Gdx.app.exit();
	}

	/**
	 * Change to a new UI
	 * 
	 * @param newUI the UI to switch to
	 */
	public void changeUI (final UI newUI) {
		if (this.currentUI != null) {
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
	public void changeDisplayMode (final FlexibleDisplayMode d) {
		this.currentDisplayMode = d;

		this.setDisplay();
	}

	/**
	 * Change the view, or remove the running one if passed a null value.
	 * 
	 * @param view the view, or null to not run the current view
	 * @param requestedSettings the requested settings to start the view with
	 *           (not used if view passed is null)
	 */
	public void changeView (final IView view, final ISettings requestedSettings) {
		if (this.currentView != null) {
			this.currentView.unregisterListeners();
		}
		this.currentView = view;

		if (view != null) {
			try {
				this.currentView.init(requestedSettings);
			} catch (GameException e) {
				e.printStackTrace();
			}
		}

		this.inputPoller.forceMouseMove();
	}

	/** Actually terminate the game */
	public void terminateView () {
		this.changeUI(new UIMainMenu());

		if (this.currentView != null) {
			this.currentView.setRunning(false);
			this.currentView.dispose();
			this.changeView(null, null);
		}

		this.mousePointer.loadPointer(MousePointer.DEFAULT_CURSOR);
	}

	/** A request to stop the game. Send signal to game. */
	public void quitView () {
		if (this.currentView != null) {
			this.currentView.setQuitSignal(true);
		}
	}

	/**
	 * X resolution.
	 * 
	 * @return the int
	 */
	public int X_RESOLUTION () {
		return Gdx.graphics.getWidth();
	}

	/**
	 * Y resolution.
	 * 
	 * @return the int
	 */
	public int Y_RESOLUTION () {
		return Gdx.graphics.getHeight();
	}

	/**
	 * Gets the tick count.
	 * 
	 * @return the tick count
	 */
	public long getTickCount () {
		return this.tickCount;
	}

	/**
	 * Changes the camera viewport
	 * 
	 * @param width
	 * @param height
	 */
	public void changeCamera (final int width, final int height) {
		this.ocamera.setToOrtho(true, width, height);
		this.ocamera.update();
		EasyGL.setViewport();
	}

	/*
	 * Get the ratio of the display width to the camera width
	 */
	public float getCameraXScale () {
		return this.X_RESOLUTION() / this.ocamera.viewportWidth;
	}

	/*
	 * Get the ratio of the display height to the camera height
	 */
	public float getCameraYScale () {
		return this.Y_RESOLUTION() / this.ocamera.viewportHeight;
	}

	@Override
	public void onResize (final ResizeEvent resizeEvent) {
		this.currentDisplayMode = new FlexibleDisplayMode(resizeEvent.getWidth(), resizeEvent.getHeight(), false);
		this.setDisplay();
	}
}
