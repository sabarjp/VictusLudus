
package com.teamderpy.victusludus.game.cosmos;

import java.math.BigDecimal;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.engine.GameException;
import com.teamderpy.victusludus.engine.ISettings;
import com.teamderpy.victusludus.engine.IView;
import com.teamderpy.victusludus.engine.graphics.GameCamera;
import com.teamderpy.victusludus.engine.graphics.GameDimensions;
import com.teamderpy.victusludus.gui.UI;
import com.teamderpy.victusludus.gui.eventhandler.MouseListener;
import com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.ScrollEvent;
import com.teamderpy.victusludus.renderer.cosmos.CosmosRenderer;

public class Cosmos implements IView, MouseListener {
	/** universal seed */
	private long seed;

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

	/** The current ui. */
	private UI currentUI = null;

	/** The game camera. */
	private GameCamera gameCamera;

	/** The game dimensions. */
	private GameDimensions gameDimensions;

	/** The renderer */
	private CosmosRenderer cosmosRenderer;

	@Override
	public void init (final ISettings settings) throws GameException {
		if (settings == null) {
			throw new GameException();
		}

		ISettings requestedSettings = settings;
		this.seed = requestedSettings.getLong("seed");

		this.universe = new Universe(this.seed + 10523, requestedSettings.getFloat("starMassDistribution"));

		this.universe.create(new BigDecimal(requestedSettings.getFloat("universeAge") * 1000000000));

		this.gameDimensions = new GameDimensions();

		this.gameDimensions.setWidth(VictusLudusGame.engine.X_RESOLUTION());
		this.gameDimensions.setHeight(VictusLudusGame.engine.Y_RESOLUTION());
		this.gameDimensions.setRenderHeight(CosmosRenderer.COSMOS_DISPLAY_HEIGHT);
		this.gameDimensions.setRenderWidth(CosmosRenderer.COSMOS_DISPLAY_WIDTH);

		this.registerListeners();

		this.gameCamera = new GameCamera();

		this.gameCamera.setOffsetX(this.gameDimensions.getWidth() / 2);

		this.cosmosRenderer = new CosmosRenderer(this);

		// VictusLudusGame.engine.soundSystem.setCurrentMusicTrack(SoundSystem.MUSIC_TRACK_TENSE);

		this.isRunning = true;
	}

	@Override
	public void render (final SpriteBatch spriteBatch, final ModelBatch modelBatch, final float deltaT) {
		if (this.isRunning) {
			spriteBatch.begin();
			this.cosmosRenderer.render(spriteBatch, deltaT);
			if (this.currentUI != null) {
				this.currentUI.render(spriteBatch, deltaT);
			}
			spriteBatch.end();
		}
	}

	@Override
	public void tick (final float deltaTime) {
		if (this.quitSignal) {
			VictusLudusGame.engine.terminateView();
		}
	}

	@Override
	public void registerListeners () {
		VictusLudusGame.engine.eventHandler.mouseHandler.registerPlease(this);
	}

	@Override
	public void unregisterListeners () {
		VictusLudusGame.engine.eventHandler.mouseHandler.unregisterPlease(this);
		this.gameDimensions.unregisterListeners();
		this.cosmosRenderer.unregisterListeners();

		this.changeUI(null);
	}

	@Override
	public boolean isRunning () {
		return this.isRunning;
	}

	@Override
	public void setRunning (final boolean isRunning) {
		this.isRunning = isRunning;
	}

	@Override
	public boolean isQuitSignal () {
		return this.quitSignal;
	}

	@Override
	public void setQuitSignal (final boolean isQuitting) {
		this.quitSignal = isQuitting;
	}

	@Override
	public boolean onMouseClick (final MouseEvent mouseEvent) {
		return false;
	}

	@Override
	public void onMouseMove (final MouseEvent mouseEvent) {
		return;
	}

	/**
	 * Change gui.
	 * 
	 * @param newGUI the new gui
	 */
	public void changeUI (final UI newUI) {
		if (this.currentUI != null) {
			this.currentUI.dispose();
		}
		this.currentUI = newUI;
		VictusLudusGame.engine.inputPoller.forceMouseMove();
	}

	/**
	 * Gets the game camera.
	 * 
	 * @return the game camera
	 */
	public GameCamera getGameCamera () {
		return this.gameCamera;
	}

	/**
	 * Gets the game dimensions.
	 * 
	 * @return the game dimensions
	 */
	public GameDimensions getGameDimensions () {
		return this.gameDimensions;
	}

	public Universe getUniverse () {
		return this.universe;
	}

	public Star getStar () {
		return this.star;
	}

	public void setStar (final Star star) {
		this.star = star;
	}

	public Planet getPlanet () {
		return this.planet;
	}

	public void setPlanet (final Planet planet) {
		this.planet = planet;
	}

	public void setUniverse (final Universe universe) {
		this.universe = universe;
	}

	public Galaxy getGalaxy () {
		return this.galaxy;
	}

	public void setGalaxy (final Galaxy galaxy) {
		this.galaxy = galaxy;
	}

	public EnumCosmosMode getCurrentPerspective () {
		return this.currentPerspective;
	}

	public void setCurrentPerspective (final EnumCosmosMode currentPerspective) {
		this.currentPerspective = currentPerspective;
	}

	public UI getCurrentUI () {
		return this.currentUI;
	}

	@Override
	public boolean onScroll (final ScrollEvent scrollEvent) {
		return false;
	}

	@Override
	public void dispose () {
		return;
	}
}
