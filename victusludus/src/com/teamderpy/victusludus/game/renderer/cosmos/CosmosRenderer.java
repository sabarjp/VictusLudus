
package com.teamderpy.victusludus.game.renderer.cosmos;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamderpy.victusludus.game.cosmos.Cosmos;
import com.teamderpy.victusludus.game.cosmos.EnumCosmosMode;
import com.teamderpy.victusludus.game.cosmos.Galaxy;
import com.teamderpy.victusludus.game.cosmos.Star;
import com.teamderpy.victusludus.game.renderer.BackgroundRenderer;
import com.teamderpy.victusludus.game.renderer.DebugRenderer;
import com.teamderpy.victusludus.gui.UIGalaxyHUD;
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
		this.universeRenderer = new UniverseRenderer();

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

			this.cosmos.getGalaxy().createStars();

			for (Star s : this.cosmos.getGalaxy().getStars()) {
				this.starList.add(new StarImage(s, this));
			}

			System.err.println(this.cosmos.getGalaxy().getSeed());

			this.bgRenderer.setBgImage(NebulaGenerator.createBackgroundNebula(this.cosmos.getGalaxy().getSeed(), this.cosmos
				.getGameDimensions().getWidth(), this.cosmos.getGameDimensions().getHeight()), true);
			this.bgRenderer.setStretchingImage(true);

			UIGalaxyHUD hud = new UIGalaxyHUD();
			hud.setCosmosRenderer(this);

			this.cosmos.changeUI(hud);
			break;
		case STAR_PERSPECTIVE:
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
				g.getActionArea().unregisterListeners();
			}

			this.galaxyList = null;
		}

		if (this.starList != null) {
			for (StarImage s : this.starList) {
				s.getActionArea().unregisterListeners();
			}

			this.starList = null;
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
