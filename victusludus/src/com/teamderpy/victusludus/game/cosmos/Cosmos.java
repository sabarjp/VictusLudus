package com.teamderpy.victusludus.game.cosmos;

import java.math.BigDecimal;

import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.VictusLudusGame.enginengine.GameException;
import com.teamderpy.VictusLudusGame.enginengine.ISettings;
import com.teamderpy.VictusLudusGame.enginengine.IView;
import com.teamderpy.VictusLudusGame.enginengine.InputPoller;
import com.teamderpy.VictusLudusGame.enginengine.SoundSystem;
import com.teamderpy.victusludus.game.GameCamera;
import com.teamderpy.victusludus.game.GameDimensions;
import com.teamderpy.victusludus.game.renderer.cosmos.CosmosRenderer;
import com.teamderpy.victusludus.gui.DialogBox;
import com.teamderpy.victusludus.gui.GUI;
import com.teamderpy.victusludus.gui.eventhandler.MouseListener;
import com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent;

public class Cosmos implements IView, MouseListener{
	/** the universe */
	private Universe universe;

	/** the galaxy we drilled down to, if any */
	private Galaxy galaxy = null;

	/** the star we drilled down to, if any */
	private Star star = null;

	/** the planet we drilled down to, if any */
	private Planet planet = null;

	/** what view we are currently looking at */
	private EnumCosmosMode currentPerspective = EnumCosmosMode.UNIVERSE_PERSPECTIVE;

	/** Whether or not the game is actually running yet. */
	private boolean isRunning = false;

	/** Signal to terminate the game. */
	private boolean quitSignal = false;

	/** The current gui. */
	private GUI currentGUI = null;

	/** The current dialog. */
	private DialogBox currentDialog = null;

	/** The game camera. */
	private GameCamera gameCamera;

	/** The game dimensions. */
	private GameDimensions gameDimensions;

	/** The renderer */
	private CosmosRenderer cosmosRenderer;

	@Override
	public void init(final ISettings settings) throws GameException {
		if(settings == null){
			throw new GameException();
		}

		UniverseSettings requestedSettings = (UniverseSettings) settings;

		this.universe = new Universe();

		//tick away
		BigDecimal delta = BigDecimal.valueOf(10000000L);

		for(float i = 0F; i < requestedSettings.getRequestedUniAge()*100; i++){
			this.universe.tick(delta);
		}

		this.gameDimensions = new GameDimensions();

		this.gameDimensions.setWidth(VictusLudusGame.engine.X_RESOLUTION());
		this.gameDimensions.setHeight(VictusLudusGame.engine.Y_RESOLUTION());

		this.registerListeners();

		this.gameCamera = new GameCamera();

		this.gameCamera.setOffsetX(this.gameDimensions.getWidth()/2);

		this.cosmosRenderer = new CosmosRenderer(this);

		VictusLudusGame.engine.soundSystem.setCurrentMusicTrack(SoundSystem.MUSIC_TRACK_TENSE);

		this.isRunning = true;
	}

	@Override
	public void render(final float deltaT) {
		if(this.isRunning){
			this.cosmosRenderer.render(deltaT);

			// GUI
			if(this.currentGUI != null) {
				this.currentGUI.render();
			}

			if(this.currentDialog != null) {
				this.currentDialog.render();
			}
		}
	}

	@Override
	public void tick() {
		if(this.quitSignal){
			VictusLudusGame.engine.terminateView();
		} else if(this.isRunning){
			if(this.currentGUI != null) {
				this.currentGUI.tick();
			}

			if(this.currentDialog != null) {
				this.currentDialog.tick();
			}
		}
	}

	@Override
	public void registerListeners() {
		VictusLudusGame.engine.eventHandler.mouseHandler.registerPlease(this);
	}

	@Override
	public void unregisterListeners() {
		VictusLudusGame.engine.eventHandler.mouseHandler.unregisterPlease(this);
		this.gameDimensions.unregisterListeners();
		this.cosmosRenderer.unregisterListeners();

		this.changeGUI(null);
		this.displayDialog(null);
	}

	@Override
	public boolean isRunning() {
		return this.isRunning;
	}

	@Override
	public void setRunning(final boolean isRunning) {
		this.isRunning = isRunning;
	}

	@Override
	public boolean isQuitSignal() {
		return this.quitSignal;
	}

	@Override
	public void setQuitSignal(final boolean isQuitting) {
		this.quitSignal = isQuitting;
	}

	@Override
	public void onMouseClick(final MouseEvent mouseEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseMove(final MouseEvent mouseEvent) {
		// TODO Auto-generated method stub

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
		VictusLudusGame.engine.inputPoller.forceMouseMove();
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
		VictusLudusGame.engine.inputPoller.forceMouseMove();
	}

	/**
	 * Gets the game camera.
	 *
	 * @return the game camera
	 */
	public GameCamera getGameCamera() {
		return this.gameCamera;
	}

	/**
	 * Gets the game dimensions.
	 *
	 * @return the game dimensions
	 */
	public GameDimensions getGameDimensions() {
		return this.gameDimensions;
	}

	public Universe getUniverse() {
		return this.universe;
	}

	public Star getStar() {
		return this.star;
	}

	public void setStar(final Star star) {
		this.star = star;
	}

	public Planet getPlanet() {
		return this.planet;
	}

	public void setPlanet(final Planet planet) {
		this.planet = planet;
	}

	public void setUniverse(final Universe universe) {
		this.universe = universe;
	}

	public Galaxy getGalaxy() {
		return this.galaxy;
	}

	public void setGalaxy(final Galaxy galaxy) {
		this.galaxy = galaxy;
	}

	public EnumCosmosMode getCurrentPerspective() {
		return this.currentPerspective;
	}

	public void setCurrentPerspective(final EnumCosmosMode currentPerspective) {
		this.currentPerspective = currentPerspective;
	}

	public GUI getCurrentGUI() {
		return this.currentGUI;
	}
}
