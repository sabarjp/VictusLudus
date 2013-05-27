package com.teamderpy.victusludus.game.cosmos;

import java.math.BigDecimal;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.engine.GameException;
import com.teamderpy.victusludus.engine.ISettings;
import com.teamderpy.victusludus.engine.IView;
import com.teamderpy.victusludus.engine.InputPoller;
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
		BigDecimal delta = BigDecimal.valueOf(100000000L);

		for(float i = 0F; i < requestedSettings.getRequestedUniAge()*10; i++){
			this.universe.tick(delta);
		}

		this.gameDimensions = new GameDimensions();

		this.gameDimensions.setWidth(VictusLudus.e.X_RESOLUTION());
		this.gameDimensions.setHeight(VictusLudus.e.Y_RESOLUTION());

		this.registerListeners();

		this.gameCamera = new GameCamera();

		this.gameCamera.setOffsetX(this.gameDimensions.getWidth()/2);

		this.cosmosRenderer = new CosmosRenderer(this);

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
			VictusLudus.e.terminateView();
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
		VictusLudus.e.eventHandler.mouseHandler.registerPlease(this);
	}

	@Override
	public void unregisterListeners() {
		VictusLudus.e.eventHandler.mouseHandler.unregisterPlease(this);
		this.gameDimensions.unregisterListeners();

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
	private void changeGUI(final GUI newGUI) {
		if (this.currentGUI != null) {
			this.currentGUI.unregisterListeners();
		}
		this.currentGUI = newGUI;
		InputPoller.forceMouseMove();
	}

	/**
	 * Display dialog.
	 *
	 * @param dialogGUI the dialog gui
	 */
	private void displayDialog(final DialogBox dialogGUI){
		if (this.currentDialog != null){
			this.currentDialog.unregisterListeners();
		}
		this.currentDialog = dialogGUI;
		InputPoller.forceMouseMove();
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
}
