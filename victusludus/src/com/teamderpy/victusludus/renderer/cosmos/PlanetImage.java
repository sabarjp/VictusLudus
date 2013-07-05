
package com.teamderpy.victusludus.renderer.cosmos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.VictusRuntimeException;
import com.teamderpy.victusludus.data.VFile;
import com.teamderpy.victusludus.engine.Actionable;
import com.teamderpy.victusludus.engine.graphics.ActionArea2D;
import com.teamderpy.victusludus.game.cosmos.Cosmology;
import com.teamderpy.victusludus.game.cosmos.EnumCosmosMode;
import com.teamderpy.victusludus.game.cosmos.EnumPlanetType;
import com.teamderpy.victusludus.game.cosmos.Planet;
import com.teamderpy.victusludus.game.cosmos.Star;
import com.teamderpy.victusludus.gui.UIPlanetHUD;
import com.teamderpy.victusludus.gui.UIStellarSystemHUD;

/**
 * A planet and its corresponding image to render
 * @author Josh
 */
public class PlanetImage {
	public static String GAS_GIANT_SMALL_PATH = "planet/gas_giant_small";
	public static String GAS_GIANT_NORMAL_PATH = "planet/gas_giant_normal";
	public static String GAS_GIANT_LARGE_PATH = "planet/gas_giant_large";
	public static String ROCKY_SMALL_PATH = "planet/rocky_small";
	public static String ROCKY_NORMAL_PATH = "planet/rocky_normal";
	public static String ROCKY_LARGE_PATH = "planet/rocky_large";
	public static String ASTEROID_FIELD_PATH = "planet/asteroid_field";
	public static String ORBIT_LINE_PATH = "planet/orbital_line";
	public static int RESERVED_STAR_AREA_WIDTH = 256;

	private Planet planet;
	private ActionArea2D actionArea;
	private CosmosRenderer cosmosRenderer;
	private Sprite sprite;
	private Sprite orbit;

	private TextureRegion surfaceOverlay;

	private double rotation = 0;
	private double angularVelocity = 0;

	private static ShaderProgram shader = null;

	/**
	 * Instantiates a new galaxy image, which is basically the galaxy object
	 * combined with its rendered aspect
	 * 
	 * @param galaxy the galaxy
	 * @param cosmosRenderer the renderer
	 */
	public PlanetImage (final Planet planet, final Array<PlanetImage> collisionList, final CosmosRenderer cosmosRenderer) {
		this.planet = planet;
		this.cosmosRenderer = cosmosRenderer;

		Star star = cosmosRenderer.cosmos.getStar();

		this.sprite = VictusLudusGame.resources.getTextureAtlasCosmos().createSprite(this.getPlanetImagePath());
		if (this.sprite == null) {
			throw new VictusRuntimeException("Failed to load sprite: " + this.getPlanetImagePath());
		}

		String surfaceOverlayPath = this.planet.getPlanetType().getPath();
		this.surfaceOverlay = VictusLudusGame.resources.getTextureAtlasCosmos().findRegion(surfaceOverlayPath);
		if (this.surfaceOverlay == null) {
			throw new VictusRuntimeException("Failed to load sprite: " + surfaceOverlayPath);
		}
		this.surfaceOverlay.setRegionWidth((int)this.sprite.getWidth());
		this.surfaceOverlay.setRegionHeight((int)this.sprite.getHeight());

		this.orbit = VictusLudusGame.resources.getTextureAtlasCosmos().createSprite(PlanetImage.ORBIT_LINE_PATH);
		if (this.orbit == null) {
			throw new VictusRuntimeException("Failed to load sprite: " + PlanetImage.ORBIT_LINE_PATH);
		}

		if (this.planet.getPlanetType() == EnumPlanetType.ASTEROID_FIELD) {
			VictusLudusGame.sharedRand.setSeed(planet.getSeed() + 3241111);
			this.rotation = VictusLudusGame.sharedRand.nextDouble() * 360;
			this.angularVelocity = VictusLudusGame.sharedRand.nextDouble() * 10;
		}

		int spriteWidth = (int)(this.sprite.getWidth());
		int spriteHeight = (int)(this.sprite.getHeight());

		// find desired x coordinate
		int x = PlanetImage.RESERVED_STAR_AREA_WIDTH
			+ (int)(planet.getOrbitSemiMajorAxis().subtract(star.getRadius()).divide(Planet.MAX_ORBITAL_DISTANCE, Planet.PLANET_RND)
				.doubleValue() * (cosmosRenderer.cosmos.getGameDimensions().getRenderWidth() - PlanetImage.RESERVED_STAR_AREA_WIDTH));

		int y = cosmosRenderer.cosmos.getGameDimensions().getRenderHeight() / 2 - (spriteHeight / 2);

		boolean isPositionDetermined = false;
		int maxPlaceAttempts = 100;
		Rectangle desiredPosition = new Rectangle();
		float overlapAmount = 0;

		while (!isPositionDetermined && maxPlaceAttempts-- > 0) {
			boolean didCollisionOccur = false;
			int collisionAt = 0; // index where collision occured
			boolean moveSelf = false; // whether to shift this planet right or all
// prior planets to the left

			// if we collide with something on the list, shift until we fit
// somewhere
			for (PlanetImage potentialCollision : collisionList) {
				desiredPosition.set(x, y, spriteWidth, spriteHeight);

				if (potentialCollision.sprite.getBoundingRectangle().overlaps(desiredPosition)) {
					// shift and try again
					if (potentialCollision.sprite.getX() > x) {
						overlapAmount = 1 + (potentialCollision.sprite.getWidth()) + (potentialCollision.sprite.getX() - x);
					} else {
						overlapAmount = 1 + (potentialCollision.sprite.getWidth()) - (x - potentialCollision.sprite.getX());
					}

					if ((potentialCollision.sprite.getX() > x || x + spriteWidth + overlapAmount >= cosmosRenderer.cosmos
						.getGameDimensions().getRenderWidth())
						&& collisionList.first().sprite.getX() > PlanetImage.RESERVED_STAR_AREA_WIDTH / 2 + overlapAmount) {
						moveSelf = false;
					} else {
						moveSelf = true;
					}

					didCollisionOccur = true;
					break;
				}

				collisionAt++;
			}

			if (didCollisionOccur) {
				if (moveSelf) {
					x += overlapAmount;
					// System.err.println("moving " + planet.getName() + " to " + x +
// " with olap " + overlapAmount);
				} else {
					int i = 0;
					for (PlanetImage potentialCollision : collisionList) {
						potentialCollision.sprite.setX(potentialCollision.sprite.getX() - overlapAmount);
						potentialCollision.orbit.setX(potentialCollision.orbit.getX() - overlapAmount);
						potentialCollision.actionArea.setX((int)(potentialCollision.actionArea.getX() - overlapAmount));

						if (++i > collisionAt) {
							break;
						}
					}

					// System.err.println("moving others for " + planet.getName() +
// " with olap " + overlapAmount);

					// if we are out of bounds, try to move back a bit
					if (x + overlapAmount >= cosmosRenderer.cosmos.getGameDimensions().getRenderWidth() - spriteWidth) {
						x -= overlapAmount;
						// System.err.println("moving " + planet.getName() +
// " back in-bounds");
					}
				}

			} else {
				isPositionDetermined = true;
			}
		}

		this.actionArea = new ActionArea2D(x, y, spriteWidth, spriteHeight);
		this.actionArea.setMouseEnterAct(new EnterAction());
		this.actionArea.setMouseLeaveAct(new LeaveAction());
		this.actionArea.setMouseClickAct(new ClickAction());

		this.sprite.setOrigin(spriteWidth / 2, spriteHeight / 2);
		this.sprite.setBounds(x, y, spriteWidth, spriteHeight);
		this.orbit.setBounds(x + (spriteWidth / 2 - this.orbit.getWidth() / 2), 0, this.orbit.getWidth(), cosmosRenderer.cosmos
			.getGameDimensions().getRenderHeight());
	}

	public void setRotation (final double rotation) {
		if (rotation > 360) {
			this.rotation = rotation - (360 * ((int)rotation / 360));
		} else if (rotation < -360) {
			this.rotation = rotation + (360 * ((int)rotation / 360));
		} else {
			this.rotation = rotation;
		}
	}

	/**
	 * Returns the planet associated with this planetImage
	 * 
	 * @return the planet associated with this planetImage
	 */
	public Planet getPlanet () {
		return this.planet;
	}

	/**
	 * Returns the ActionArea2D associated with this planetImage
	 * 
	 * @return the ActionArea2D associated with this planetImage
	 */
	public ActionArea2D getActionArea () {
		return this.actionArea;
	}

	/**
	 * Renders the planetImage
	 * 
	 * @param deltaT the amount of time since the last render
	 */
	public void render (final SpriteBatch batch, final float deltaT) {
		this.orbit.draw(batch);

		this.setRotation(this.rotation + deltaT * this.angularVelocity);
		this.sprite.setRotation((float)this.rotation);

		if (VictusLudusGame.engine.IS_SHADERS_ENABLED) {
			batch.flush();

			PlanetImage.getShader().begin();
			PlanetImage.getShader().setUniformi("u_texture1", 1);
			PlanetImage.getShader().setAttributef("a_overlayuv", this.surfaceOverlay.getU(), this.surfaceOverlay.getV(),
				this.surfaceOverlay.getU2(), this.surfaceOverlay.getV2());
			PlanetImage.getShader().setAttributef("a_regionsize", this.surfaceOverlay.getRegionWidth(),
				this.surfaceOverlay.getRegionHeight(), this.sprite.getWidth(), this.sprite.getHeight());
			PlanetImage.getShader().end();

			batch.setShader(PlanetImage.getShader());
		}

		this.surfaceOverlay.getTexture().bind(1);

		Gdx.graphics.getGLCommon().glActiveTexture(GL10.GL_TEXTURE0);

		this.sprite.draw(batch);

		if (VictusLudusGame.engine.IS_SHADERS_ENABLED) {
			batch.setShader(null);
			// PlanetImage.getShader().end();
		}
	}

	/*
	 * Get the planet's image depending on its type and size
	 */
	private String getPlanetImagePath () {
		if (this.planet.getPlanetType().isGasGiant()) {
			if (this.planet.getRelativeScale() >= 1.5) {
				return PlanetImage.GAS_GIANT_LARGE_PATH;
			} else if (this.planet.getRelativeScale() <= 0.5) {
				return PlanetImage.GAS_GIANT_SMALL_PATH;
			} else {
				return PlanetImage.GAS_GIANT_NORMAL_PATH;
			}
		} else {
			if (this.planet.getPlanetType() == EnumPlanetType.ASTEROID_FIELD) {
				return PlanetImage.ASTEROID_FIELD_PATH;
			} else if (this.planet.getRelativeScale() >= 1.5) {
				return PlanetImage.ROCKY_LARGE_PATH;
			} else if (this.planet.getRelativeScale() <= 0.5) {
				return PlanetImage.ROCKY_SMALL_PATH;
			} else {
				return PlanetImage.ROCKY_NORMAL_PATH;
			}
		}
	}

	private class EnterAction implements Actionable {
		@Override
		public void act () {
			UIStellarSystemHUD gui = ((UIStellarSystemHUD)PlanetImage.this.cosmosRenderer.cosmos.getCurrentUI());
			Planet planet = PlanetImage.this.getPlanet();

			gui.setSelectedPlanetName(planet.getName());
			gui.setSelectedPlanetType(planet.getPlanetType().getProperName());
			gui.setSelectedPlanetAge(Cosmology.getFormattedStellarAge(planet.getAge()));
		}
	}

	private class LeaveAction implements Actionable {
		@Override
		public void act () {

		}
	}

	private class ClickAction implements Actionable {
		@Override
		public void act () {
			PlanetImage.this.cosmosRenderer.cosmos.setPlanet(PlanetImage.this.getPlanet());
			PlanetImage.this.cosmosRenderer.changePerspective(EnumCosmosMode.PLANET_PERSPECTIVE);

			UIPlanetHUD gui = ((UIPlanetHUD)PlanetImage.this.cosmosRenderer.cosmos.getCurrentUI());
			gui.setSelectedPlanetName(PlanetImage.this.getPlanet().getName());
			gui.setSelectedPlanetType(PlanetImage.this.getPlanet().getPlanetType().getProperName());
			gui.setSelectedPlanetAge(Cosmology.getFormattedStellarAge(PlanetImage.this.getPlanet().getAge()));
		}
	}

	@Override
	public void finalize () {
		this.actionArea.unregisterListeners();
	}

	public void dispose () {
		this.actionArea.unregisterListeners();
	}

	/**
	 * Prepares the shader for use
	 */
	public static void prepareShader () {
		FileHandle vertexShader = VFile.getFileHandle("com/teamderpy/victusludus/engine/graphics/planetShader.vert");
		FileHandle fragmentShader = VFile.getFileHandle("com/teamderpy/victusludus/engine/graphics/planetShader.frag");
		ShaderProgram.pedantic = false;
		PlanetImage.shader = new ShaderProgram(vertexShader, fragmentShader);

		if (!PlanetImage.shader.isCompiled()) {
			throw new VictusRuntimeException("Failed to compile shader: \n" + PlanetImage.shader.getLog());
		} else {
			System.out.println("compiled shader\n" + PlanetImage.shader.getLog());
		}
	}

	/**
	 * Releases resources associated with the shader
	 */
	public static void disposeShader () {
		if (PlanetImage.shader != null) {
			PlanetImage.shader.dispose();
		}
	}

	public static ShaderProgram getShader () {
		return PlanetImage.shader;
	}
}
