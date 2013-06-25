
package com.teamderpy.victusludus.game.renderer.cosmos;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamderpy.victusludus.game.cosmos.Cosmos;
import com.teamderpy.victusludus.game.cosmos.EnumCosmosMode;
import com.teamderpy.victusludus.game.cosmos.Galaxy;
import com.teamderpy.victusludus.game.cosmos.Planet;
import com.teamderpy.victusludus.game.cosmos.Star;
import com.teamderpy.victusludus.game.renderer.BackgroundRenderer;
import com.teamderpy.victusludus.game.renderer.DebugRenderer;
import com.teamderpy.victusludus.gui.UIGalaxyHUD;
import com.teamderpy.victusludus.gui.UIStarHUD;
import com.teamderpy.victusludus.gui.UIUniverseHUD;

/** The Class CosmosRenderer. */
public class CosmosRenderer {
	/** The bg renderer. */
	private BackgroundRenderer bgRenderer;

	/** The debug overlay renderer. */
	private DebugRenderer debugRenderer;

	/** Renders aspects of the universe */
	private UniverseRenderer universeRenderer;

	/** A list of galaxies */
	protected ArrayList<GalaxyImage> galaxyList;

	/** A list of stars */
	protected ArrayList<StarImage> starList;

	/** A list of planets */
	protected ArrayList<PlanetImage> planetList;

	/** The game. */
	public Cosmos cosmos;

	/**
	 * Instantiates a new game renderer.
	 * 
	 * @param game the game
	 */
	public CosmosRenderer (final Cosmos cosmos) {
		this.cosmos = cosmos;

		this.bgRenderer = new BackgroundRenderer(this.cosmos.getGameDimensions(), Color.BLACK);
		// this.debugRenderer = new DebugRenderer(cosmos.getGameDimensions());
		this.universeRenderer = new UniverseRenderer(this.cosmos.getGameDimensions());

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
			this.universeRenderer.renderGalaxies(this.galaxyList, batch, deltaT);
			break;
		case GALAXY_PERSPECTIVE:
			this.universeRenderer.renderStars(this.starList, batch, deltaT);
			break;
		case STAR_PERSPECTIVE:
			this.universeRenderer.renderBigStar(this.cosmos.getStar(), batch, deltaT);
			this.universeRenderer.renderPlanets(this.planetList, batch, deltaT);
			break;
		case PLANET_PERSPECTIVE:
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
			this.galaxyList = new ArrayList<GalaxyImage>();

			for (Galaxy g : this.cosmos.getUniverse().getGalaxies()) {
				this.galaxyList.add(new GalaxyImage(g, this));
			}

			this.bgRenderer.setBgImage("background/background_universe", false);
			this.bgRenderer.setFlipTiling(false);
			this.bgRenderer.setStretchingImage(false);

			this.cosmos.changeUI(new UIUniverseHUD());

			break;
		case GALAXY_PERSPECTIVE:
			this.starList = new ArrayList<StarImage>();

			if (this.cosmos.getGalaxy().getStars().isEmpty()) {
				this.cosmos.getGalaxy().createStars();
			}

			for (Star s : this.cosmos.getGalaxy().getStars()) {
				this.starList.add(new StarImage(s, this));
			}

			this.bgRenderer.setBgImage(NebulaGenerator.createBackgroundNebula(this.cosmos.getGalaxy().getSeed(), this.cosmos
				.getGameDimensions().getWidth(), this.cosmos.getGameDimensions().getHeight()), true);
			this.bgRenderer.setStretchingImage(true);

			UIGalaxyHUD galHUD = new UIGalaxyHUD();
			galHUD.setCosmosRenderer(this);

			this.cosmos.changeUI(galHUD);
			break;
		case STAR_PERSPECTIVE:
			this.planetList = new ArrayList<PlanetImage>();

			if (this.cosmos.getStar().getPlanets().isEmpty()) {
				this.cosmos.getStar().createPlanets();
			}

			for (Planet p : this.cosmos.getStar().getPlanets()) {
				this.planetList.add(new PlanetImage(p, this));
			}

			this.bgRenderer.setBgImage("background/background_solar", false);
			this.bgRenderer.setFlipTiling(false);
			this.bgRenderer.setStretchingImage(false);

			UIStarHUD starHUD = new UIStarHUD();
			starHUD.setCosmosRenderer(this);

			this.cosmos.changeUI(starHUD);

			break;
		case PLANET_PERSPECTIVE:
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
