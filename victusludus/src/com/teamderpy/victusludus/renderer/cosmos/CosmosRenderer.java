
package com.teamderpy.victusludus.renderer.cosmos;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.game.cosmos.Cosmos;
import com.teamderpy.victusludus.game.cosmos.EnumCosmosMode;
import com.teamderpy.victusludus.game.cosmos.Galaxy;
import com.teamderpy.victusludus.game.cosmos.Planet;
import com.teamderpy.victusludus.game.cosmos.Star;
import com.teamderpy.victusludus.gui.UIGalaxyHUD;
import com.teamderpy.victusludus.gui.UIPlanetHUD;
import com.teamderpy.victusludus.gui.UIStellarSystemHUD;
import com.teamderpy.victusludus.gui.UIUniverseHUD;
import com.teamderpy.victusludus.renderer.common.BackgroundRenderer;
import com.teamderpy.victusludus.renderer.common.DebugRenderer;

/** The Class CosmosRenderer. */
public class CosmosRenderer {
	/** The size of the render which gets scaled to the display */
	public static final int COSMOS_DISPLAY_WIDTH = 640;
	public static final int COSMOS_DISPLAY_HEIGHT = 480;

	/** The bg renderer. */
	private BackgroundRenderer bgRenderer;

	/** The debug overlay renderer. */
	private DebugRenderer debugRenderer;

	/** Renders aspects of the universe */
	private IUniverseRenderer iuniRenderer;

	/** A list of galaxies */
	protected Array<GalaxyImage> galaxyList;

	/** A list of stars */
	protected Array<StarImage> starList;

	/** A list of planets */
	protected Array<PlanetImage> planetList;

	/** The game. */
	public Cosmos cosmos;

	/**
	 * Instantiates a new game renderer.
	 * 
	 * @param game the game
	 */
	public CosmosRenderer (final Cosmos cosmos) {
		this.cosmos = cosmos;

		VictusLudusGame.engine.changeCamera(CosmosRenderer.COSMOS_DISPLAY_WIDTH, CosmosRenderer.COSMOS_DISPLAY_HEIGHT);

		this.bgRenderer = new BackgroundRenderer(this.cosmos.getGameDimensions(), Color.BLACK);
		// this.debugRenderer = new DebugRenderer(cosmos.getGameDimensions());

		this.changePerspective(EnumCosmosMode.UNIVERSE_PERSPECTIVE);
	}

	/** Render the cosmos */
	public void render (final SpriteBatch batch, final float deltaT) {
		batch.enableBlending();

		if (this.bgRenderer != null) {
			this.bgRenderer.render(batch, deltaT);
		}

		if (this.debugRenderer != null) {
			this.debugRenderer.render(batch, deltaT);
		}

		switch (this.cosmos.getCurrentPerspective()) {
		case UNIVERSE_PERSPECTIVE:
			this.iuniRenderer.render(batch, deltaT);
			break;
		case GALAXY_PERSPECTIVE:
			this.iuniRenderer.render(batch, deltaT);
			break;
		case STAR_PERSPECTIVE:
			this.iuniRenderer.render(batch, deltaT);
			break;
		case PLANET_PERSPECTIVE:
			this.iuniRenderer.render(batch, deltaT);
			break;
		}

		batch.disableBlending();
	}

	/**
	 * Changes the current perspective
	 * 
	 * @param newPerspective the new perspective to render
	 */
	public void changePerspective (final EnumCosmosMode newPerspective) {
		// clear out obsolete objects
		this.unregisterListeners();

		// change perspectives
		switch (newPerspective) {
		case UNIVERSE_PERSPECTIVE:
			this.galaxyList = new Array<GalaxyImage>();

			for (Galaxy g : this.cosmos.getUniverse().getGalaxies()) {
				this.galaxyList.add(new GalaxyImage(g, this.galaxyList, this));
			}

			this.bgRenderer.setBgImage(GalaxyRenderer.BACKGROUND_PATH, false);
			this.bgRenderer.setFlipTiling(false);
			this.bgRenderer.setStretchingImage(false);

			this.cosmos.changeUI(new UIUniverseHUD());

			this.iuniRenderer = new GalaxyRenderer(this.cosmos.getGameDimensions(), this.galaxyList);

			break;
		case GALAXY_PERSPECTIVE:
			this.starList = new Array<StarImage>();

			if (this.cosmos.getGalaxy().getStars().isEmpty()) {
				this.cosmos.getGalaxy().createStars();
			}

			for (Star s : this.cosmos.getGalaxy().getStars()) {
				this.starList.add(new StarImage(s, this.starList, this));
			}

			this.bgRenderer.setBgImage(NebulaGenerator.createBackgroundNebula(this.cosmos.getGalaxy().getSeed(), this.cosmos
				.getGameDimensions().getWidth(), this.cosmos.getGameDimensions().getHeight()), true);
			this.bgRenderer.setStretchingImage(true);

			UIGalaxyHUD galHUD = new UIGalaxyHUD();
			galHUD.setCosmosRenderer(this);

			this.cosmos.changeUI(galHUD);

			this.iuniRenderer = new StarRenderer(this.cosmos.getGameDimensions(), this.starList);

			break;
		case STAR_PERSPECTIVE:
			this.planetList = new Array<PlanetImage>();

			if (this.cosmos.getStar().getPlanets().isEmpty()) {
				this.cosmos.getStar().createPlanets();
			}

			for (Planet p : this.cosmos.getStar().getPlanets()) {
				this.planetList.add(new PlanetImage(p, this.planetList, this));
			}

			this.bgRenderer.setBgImage(SolarSystemRenderer.BACKGROUND_PATH, false);
			this.bgRenderer.setFlipTiling(false);
			this.bgRenderer.setStretchingImage(false);

			UIStellarSystemHUD starHUD = new UIStellarSystemHUD();
			starHUD.setCosmosRenderer(this);

			this.cosmos.changeUI(starHUD);

			this.iuniRenderer = new SolarSystemRenderer(this.cosmos.getGameDimensions(), this.planetList, this.cosmos.getStar());

			break;
		case PLANET_PERSPECTIVE:
			this.cosmos.getPlanet().createSurfaceWorld();

			this.bgRenderer.setBgImage(SolarSystemRenderer.BACKGROUND_PATH, false);
			this.bgRenderer.setFlipTiling(false);
			this.bgRenderer.setStretchingImage(false);

			UIPlanetHUD planetHUD = new UIPlanetHUD();
			planetHUD.setCosmosRenderer(this);

			this.cosmos.changeUI(planetHUD);

			this.iuniRenderer = new WorldRenderer(this.cosmos.getGameDimensions(), this.cosmos.getPlanet());

			break;
		}

		this.cosmos.setCurrentPerspective(newPerspective);
	}

	/** Unregister listeners. */
	public void unregisterListeners () {
		if (this.galaxyList != null) {
			for (GalaxyImage g : this.galaxyList) {
				g.dispose();
			}

			this.galaxyList = null;
		}

		if (this.starList != null) {
			for (StarImage s : this.starList) {
				s.dispose();
			}

			this.starList = null;
		}

		if (this.planetList != null) {
			for (PlanetImage p : this.planetList) {
				p.dispose();
			}

			this.planetList = null;
		}
	}

	@Override
	public void finalize () {
		this.unregisterListeners();
	}

	public BackgroundRenderer getBgRenderer () {
		return this.bgRenderer;
	}
}
