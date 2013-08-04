
package com.teamderpy.victusludus.game;

import java.util.ArrayList;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.lights.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.lights.Lights;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.OrderedMap;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.data.VFile;
import com.teamderpy.victusludus.engine.GameException;
import com.teamderpy.victusludus.engine.ISettings;
import com.teamderpy.victusludus.engine.IView;
import com.teamderpy.victusludus.engine.MousePointer;
import com.teamderpy.victusludus.engine.graphics.GameCamera;
import com.teamderpy.victusludus.engine.graphics.GameDimensions;
import com.teamderpy.victusludus.game.entity.GameEntity;
import com.teamderpy.victusludus.game.entity.GameEntityInstance;
import com.teamderpy.victusludus.game.map.Map;
import com.teamderpy.victusludus.gui.UI;
import com.teamderpy.victusludus.gui.UIGameHUD;
import com.teamderpy.victusludus.gui.eventhandler.KeyboardListener;
import com.teamderpy.victusludus.gui.eventhandler.MouseListener;
import com.teamderpy.victusludus.gui.eventhandler.event.EnumRenderEventType;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyDownEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyTypedEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyUpEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.RenderEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.ScrollEvent;
import com.teamderpy.victusludus.math.VMath;
import com.teamderpy.victusludus.renderer.game.GameRenderer;
import com.teamderpy.victusludus.renderer.game.RenderUtil;

/** The Class Game. */
public class Game implements IView, KeyboardListener, MouseListener {
	/** The current depth. */
	private int currentDepth = 0;

	/** The build mode object id. */
	private String buildModeObjectID;

	/** The build mode. */
	private EnumBuildMode buildMode;

	/** The build begin coord. */
	private Vector3 buildBeginCoord;

	/** The game camera which holds camera offsets */
	private GameCamera gameCamera;

	/** Inherited from the game engine */
	private Camera camera;

	/** The input controller to move around the camera */
	private RTSCameraController camController;

	/** The game dimensions. */
	private GameDimensions gameDimensions;

	/** The interaction mode for editing stuff */
	private byte interactionMode = EnumInteractionMode.MODE_QUERY_TILE;

	/** The map. */
	private Map map;

	/** The color of the ambient light from the sun */
	private Color ambientLightColor;

	/** The game renderer. */
	private GameRenderer gameRenderer;

	/** used with map scrolling */
	private boolean holdingDownRightClick = false;

	/** the time of the day, from 0 to 24 */
	private float hour;

	/** the tint of the light from the sun */
	private OrderedMap<Float, Color> solarTintMap = new OrderedMap<Float, Color>();

	/** the lights */
	private Lights lights;

	/** the sun light */
	private DirectionalLight sunLight;

	/** The right click x. */
	private int rightClickX = 0;

	/** The right click y. */
	private int rightClickY = 0;

	/** The current ui. */
	private UI currentUI = null;

	/** Whether or not the game is actually running yet. */
	private boolean isRunning = false;

	/** Signal to terminate the game. */
	private boolean quitSignal = false;

	/**
	 * Initializes the game
	 * @throws GameException
	 */
	@Override
	public void init (final ISettings settings) throws GameException {
		if (settings == null) {
			throw new GameException();
		}

		/* instantiate the camera */

		Camera pcam = new PerspectiveCamera(67, VictusLudusGame.engine.X_RESOLUTION(), VictusLudusGame.engine.Y_RESOLUTION());
		pcam.near = 0.1f;
		pcam.far = 1000f;
		pcam.update();
		pcam.rotate(-35.264F, 1, 0, 0);
		pcam.rotate(45F, 0, 1, 0);

		if (settings.getBoolean("useOrtho")) {
			this.camera = new OrthographicCamera(10, 10 * (Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));
			this.camera.position.set(pcam.position);
			this.camera.direction.set(pcam.direction);
			this.camera.near = 0.0001f;
			this.camera.far = 10000;
			this.camera.update();
		} else {
			this.camera = pcam;
		}

		this.camController = new RTSCameraController(this.camera);
		VictusLudusGame.engine.inputMultiplexer.addProcessor(this.camController);

		ISettings requestedSettings = settings;

		/* light */
		this.solarTintMap.put(24.00F, new Color(0.13F, 0.04F, 0.27F, 1));
		this.solarTintMap.put(22.00F, new Color(0.18F, 0.11F, 0.47F, 1));
		this.solarTintMap.put(19.00F, new Color(0.83F, 0.41F, 0.14F, 1));
		this.solarTintMap.put(17.00F, new Color(0.91F, 0.89F, 0.67F, 1));
		this.solarTintMap.put(12.00F, new Color(1.00F, 0.99F, 0.99F, 1));
		this.solarTintMap.put(08.00F, new Color(0.91F, 0.89F, 0.67F, 1));
		this.solarTintMap.put(07.00F, new Color(0.95F, 0.74F, 0.24F, 1));
		this.solarTintMap.put(05.00F, new Color(0.18F, 0.11F, 0.47F, 1));
		this.solarTintMap.put(00.00F, new Color(0.13F, 0.04F, 0.27F, 1));

		this.hour = 0f;
		this.ambientLightColor = this.getSunlightColor();

		this.lights = new Lights();
		this.sunLight = new DirectionalLight().set(this.ambientLightColor, 0, -1, 0);
		this.lights.add(this.sunLight);

		this.setLights(this.ambientLightColor);

		this.gameDimensions = new GameDimensions();

		/* setup game dimensions */
		this.gameDimensions.setWidth(VictusLudusGame.engine.X_RESOLUTION());
		this.gameDimensions.setHeight(VictusLudusGame.engine.Y_RESOLUTION());
		this.gameDimensions.setRenderWidth(VictusLudusGame.engine.X_RESOLUTION());
		this.gameDimensions.setRenderHeight(VictusLudusGame.engine.Y_RESOLUTION());

		this.registerListeners();

		/* create map */

		/*
		 * TextureRegion texture =
		 * VictusLudusGame.resources.getTextureAtlasTiles().findRegion("tiles");
		 */
		TextureRegion texture = new TextureRegion(new Texture(VFile.getFileHandle("meshes/tiles.png")));

		this.map = new Map(this, requestedSettings, texture);
		this.currentDepth = this.map.getHighestPoint() - 1;

		/* setup GUI */
		this.changeUI(new UIGameHUD());
		((UIGameHUD)this.currentUI).setCurrentDepthText(Integer.toString(this.currentDepth));

		/* setup the camera */
		this.gameCamera = new GameCamera();

		/* centers the camera */
		this.gameCamera.setOffsetX(this.gameDimensions.getWidth() / 2);

		float camX = this.map.voxelsX / 2f;
		float camZ = this.map.voxelsZ / 2f;
		float camY = this.map.getHighest(camX, camZ) + 1.5f;
		this.camera.position.set(camX, camY, camZ);

		this.map.addEntity(new GameEntityInstance("rat", (int)camX, (int)this.map.getHighest(camX, camZ), (int)camZ, this.map));
		this.map.addEntity(new GameEntityInstance("nodule", (int)camX - 5, (int)this.map.getHighest(camX - 5, camZ - 5),
			(int)camZ - 5, this.map));
		this.map.addEntity(new GameEntityInstance("bone_marrow", (int)camX + 5, (int)this.map.getHighest(camX + 5, camZ + 5),
			(int)camZ + 5, this.map));
		this.map.addEntity(new GameEntityInstance("alveolus", (int)camX + 8, (int)this.map.getHighest(camX + 8, camZ + 8),
			(int)camZ + 8, this.map));
		this.map.addEntity(new GameEntityInstance("red_blood_cell", (int)camX + 14, (int)this.map.getHighest(camX + 14, camZ + 2),
			(int)camZ + 2, this.map));
		this.map.addEntity(new GameEntityInstance("red_blood_cell", 0, (int)this.map.getHighest(0, 0), 0, this.map));
		this.map.addEntity(new GameEntityInstance("desk", 6, (int)this.map.getHighest(6, 6), 6, this.map));

		/* the game renderer */
		this.gameRenderer = new GameRenderer(this);

		this.isRunning = true;
	}

	@Override
	public void render (final SpriteBatch spriteBatch, final ModelBatch modelBatch, final float deltaT) {
		if (this.isRunning) {
			this.gameRenderer.render(spriteBatch, modelBatch, deltaT);

			this.camController.update();
			// System.out.println(this.pcamera.position);
			// System.out.println(this.pcamera.direction);

			// UI
			if (this.currentUI != null) {
				this.currentUI.render(spriteBatch, deltaT);
			}
		}
	}

	@Override
	public void tick (final float deltaTime) {
		if (this.quitSignal) {
			VictusLudusGame.engine.terminateView();
		} else if (this.isRunning) {
			/* change the clock */
			this.hour += deltaTime;

			if (this.hour > 24f) {
				this.hour -= 24f;
			}

			((UIGameHUD)this.currentUI).setCurrentTimeText(Float.toString(this.hour));

			this.ambientLightColor = this.getSunlightColor();
			this.setLights(this.ambientLightColor);

			/* beware of concurrent modification! */
			for (int i = 0; i < this.map.getEntities().size(); i++) {
				this.map.getEntities().get(i).tick(deltaTime);
			}
		}
	}

	/** Zoom out. */
	public void zoomOut () {
		if (this.gameCamera.getZoom() > 0.25) {
			this.gameCamera.setZoom(this.gameCamera.getZoom() / 2);
			VictusLudusGame.engine.eventHandler.signal(new RenderEvent(this, EnumRenderEventType.ZOOM, this));
		}
	}

	/** Zoom in. */
	public void zoomIn () {
		if (this.gameCamera.getZoom() < 4) {
			this.gameCamera.setZoom(this.gameCamera.getZoom() * 2);
			VictusLudusGame.engine.eventHandler.signal(new RenderEvent(this, EnumRenderEventType.ZOOM, this));
		}
	}

	/**
	 * Gets the current depth.
	 * 
	 * @return the current depth
	 */
	public int getCurrentDepth () {
		return this.currentDepth;
	}

	/**
	 * Gets the tile height scaled.
	 * 
	 * @return the tile height scaled
	 */
	public int getTileHeightScaled () {
		return (int)(this.getGameDimensions().getTileHeight() * this.gameCamera.getZoom());
	}

	/**
	 * Gets the tile width scaled.
	 * 
	 * @return the tile width scaled
	 */
	public int getTileWidthScaled () {
		return (int)(this.getGameDimensions().getTileWidth() * this.gameCamera.getZoom());
	}

	/**
	 * Gets the layer height scaled.
	 * 
	 * @return the layer height scaled
	 */
	public int getLayerHeightScaled () {
		return (int)(this.getGameDimensions().getLayerHeight() * this.gameCamera.getZoom());
	}

	/**
	 * Gets the map.
	 * 
	 * @return the map
	 */
	public Map getMap () {
		return this.map;
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

	public UI getCurrentUI () {
		return this.currentUI;
	}

	/** Increase depth. */
	private void increaseDepth () {
		if (this.currentDepth < this.map.getDepth()) {
			this.currentDepth++;
		}

		((UIGameHUD)this.currentUI).setCurrentDepthText(Integer.toString(this.currentDepth));
		VictusLudusGame.engine.inputPoller.forceMouseMove();

		VictusLudusGame.engine.eventHandler.signal(new RenderEvent(this, EnumRenderEventType.CHANGE_DEPTH, this));
	}

	/** Decrease depth. */
	private void decreaseDepth () {
		if (this.currentDepth > 0) {
			this.currentDepth--;
		}

		((UIGameHUD)this.currentUI).setCurrentDepthText(Integer.toString(this.currentDepth));
		VictusLudusGame.engine.inputPoller.forceMouseMove();

		VictusLudusGame.engine.eventHandler.signal(new RenderEvent(this, EnumRenderEventType.CHANGE_DEPTH, this));
	}

	@Override
	public boolean onMouseClick (final MouseEvent mouseEvent) {
		// left click
		if (mouseEvent.getButton() == Buttons.LEFT) {
			if (this.interactionMode == EnumInteractionMode.MODE_QUERY_TILE) {
				if (mouseEvent.isButtonPressed()) {
					return true;
				}
			} else if (this.interactionMode == EnumInteractionMode.MODE_BUILD) {
				// build mode
				if (this.buildModeObjectID == null) {
					return true;
				}

				if (mouseEvent.isButtonPressed()) {
					// depressed
					if (this.buildMode == EnumBuildMode.LINE) {
						// mark the starting position if it is in-bounds
						this.buildBeginCoord = null;
						Vector3 temp = RenderUtil.screenCoordToWorldCoord(this, mouseEvent.getX(), mouseEvent.getY(),
							this.currentDepth, false);

						// if (temp.x >= 0 && temp.y >= 0) {
						// if (temp.x < this.map.getLayer(this.currentDepth).length
						// && temp.y < this.map.getLayer(this.currentDepth)[0].length)
// {
						// if (this.map.getLayer(this.currentDepth)[temp.x][temp.y] !=
// null) {
						// this.buildBeginCoord = temp;
						// }
						// }
						// }

						return true;
					}
				} else {
					// let go

					if (this.buildMode == EnumBuildMode.LINE) {
						// complete the line
						if (this.buildBeginCoord != null) {
							Vector3 wcs = RenderUtil.screenCoordToWorldCoord(this, mouseEvent.getX(), mouseEvent.getY(),
								this.currentDepth, false);
							ArrayList<Vector3> temp = AStarSearch.search(this.map, new Vector3(this.buildBeginCoord.x,
								this.buildBeginCoord.y, this.currentDepth), new Vector3(wcs.x, wcs.y, this.currentDepth));

							if (temp != null) {
								for (Vector3 wc : temp) {
									this.build(this.getBuildModeObjectID(), (int)wc.x, (int)wc.y, this.currentDepth);
								}
							}

							this.buildBeginCoord = null;
							return true;
						}

					} else {
						Vector3 c = RenderUtil.screenCoordToWorldCoord(this, mouseEvent.getX(), mouseEvent.getY(), this.currentDepth,
							false);

						// if (c.x >= 0 && c.y >= 0) {
						// if (c.x < this.map.getLayer(this.currentDepth).length
						// && c.y < this.map.getLayer(this.currentDepth)[0].length) {
						// if (this.map.getLayer(this.currentDepth)[c.x][c.y] != null)
// {
						// this.build(this.getBuildModeObjectID(), c.x, c.y,
// this.currentDepth);
						// }
						// }
						// }

						return true;
					}
				}
			}
			// right click
		} else if (mouseEvent.getButton() == Buttons.RIGHT) {
			if (mouseEvent.isButtonPressed()) {
				this.holdingDownRightClick = true;
				this.rightClickX = mouseEvent.getX();
				this.rightClickY = mouseEvent.getY();
				return true;
			} else {
				this.holdingDownRightClick = false;
				return true;
			}
		}

		return false;
	}

	@Override
	public void onMouseMove (final MouseEvent mouseEvent) {
		// not moving the map
		if (!this.holdingDownRightClick) {
			Vector3 c = null;

			if (this.interactionMode == EnumInteractionMode.MODE_QUERY_TILE) {
				// highlight tile under cursor
				c = RenderUtil.screenCoordToWorldCoord(this, mouseEvent.getX(), mouseEvent.getY(), this.currentDepth, false);
			} else if (this.interactionMode == EnumInteractionMode.MODE_QUERY_WALL) {
				// highlight tile and wall under cursor
				c = RenderUtil.screenCoordToWorldCoord(this, mouseEvent.getX(), mouseEvent.getY(), this.currentDepth, true);
			} else if (this.interactionMode == EnumInteractionMode.MODE_BUILD) {
				// highlight tile under cursor
				c = RenderUtil.screenCoordToWorldCoord(this, mouseEvent.getX(), mouseEvent.getY(), this.currentDepth, false);

				// we are in the middle of a build so draw stuff
				if (this.buildMode == EnumBuildMode.LINE && this.buildBeginCoord != null) {
					ArrayList<Vector3> temp = AStarSearch.search(this.map, new Vector3(this.buildBeginCoord.x, this.buildBeginCoord.y,
						this.currentDepth), new Vector3(c.x, c.y, this.currentDepth));

					if (temp != null) {
						for (Vector3 wc : temp) {

						}
					}

					// this.map.getTileOverlayList().add(new
// GameTile(GameTile.ID_PATH_GOOD, this.buildBeginCoord.x,
// this.buildBeginCoord.y, this.currentDepth));
					// this.map.getTileOverlayList().add(new
// GameTile(GameTile.ID_PATH_GOOD, c.x, c.y, this.currentDepth));
				}
			} else {
				return;
			}

			if (c.x >= 0 && c.y >= 0) {
				// if (c.x < this.map.getLayer(this.currentDepth).length && c.y <
// this.map.getLayer(this.currentDepth)[0].length) {
				// // this.gameRenderer.setHighlightedTile(c);
				// this.map.getTileOverlayList().add(new GameTile(GameTile.ID_GRID,
// c.x, c.y, this.currentDepth, this.map));
				// ((UIGameHUD)this.currentUI).setCurrentTileText(c.x + "," + c.y);
				// } else {
				// this.map.getTileOverlayList().clear();
				// // this.gameRenderer.setHighlightedTile(null);
				// }
			}
			// map is being scrolled
		} else {
			this.gameCamera.setOffsetX(this.gameCamera.getOffsetX() - (this.rightClickX - mouseEvent.getX()));
			this.gameCamera.setOffsetY(this.gameCamera.getOffsetY() - (this.rightClickY - mouseEvent.getY()));

			VictusLudusGame.engine.eventHandler.signal(new RenderEvent(this, EnumRenderEventType.SCROLL_MAP, this));

			this.rightClickX = mouseEvent.getX();
			this.rightClickY = mouseEvent.getY();
		}
	}

	@Override
	public boolean onKeyDown (final KeyDownEvent keyboardEvent) {
		if (keyboardEvent.getKey() == Keys.I) {
			this.gameCamera.moveCameraUp();
			return true;
		} else if (keyboardEvent.getKey() == Keys.K) {
			this.gameCamera.moveCameraDown();
			return true;
		} else if (keyboardEvent.getKey() == Keys.L) {
			this.gameCamera.moveCameraRight();
			return true;
		} else if (keyboardEvent.getKey() == Keys.J) {
			this.gameCamera.moveCameraLeft();
			return true;
		} else if (keyboardEvent.getKey() == Keys.Z) {
			this.zoomOut();
			return true;
		} else if (keyboardEvent.getKey() == Keys.X) {
			this.zoomIn();
			return true;
		} else if (keyboardEvent.getKey() == Keys.PLUS) {
			this.increaseDepth();
			return true;
		} else if (keyboardEvent.getKey() == Keys.MINUS) {
			this.decreaseDepth();
			return true;
		}

		return false;
	}

	/** Register listeners. */
	@Override
	public void registerListeners () {
		VictusLudusGame.engine.eventHandler.mouseHandler.registerPlease(this);
		VictusLudusGame.engine.eventHandler.keyboardHandler.registerPlease(this);
	}

	/** Unregister listeners. */
	@Override
	public void unregisterListeners () {
		VictusLudusGame.engine.eventHandler.mouseHandler.unregisterPlease(this);
		VictusLudusGame.engine.eventHandler.keyboardHandler.unregisterPlease(this);

		this.map.unregisterListeners();
		this.gameDimensions.unregisterListeners();
		this.gameRenderer.unregisterListeners();

		this.changeUI(null);
	}

	/**
	 * Change ui.
	 * 
	 * @param newUI the new ui
	 */
	private void changeUI (final UI newUI) {
		this.currentUI = newUI;
		VictusLudusGame.engine.inputPoller.forceMouseMove();
	}

	/** Toggle mode. */
	public void toggleMode () {
		if (this.interactionMode == EnumInteractionMode.MODE_QUERY_TILE) {
			this.interactionMode = EnumInteractionMode.MODE_QUERY_WALL;
			// this.gameRenderer.setHighlightedTile(null);
		} else if (this.interactionMode == EnumInteractionMode.MODE_QUERY_WALL) {
			this.interactionMode = EnumInteractionMode.MODE_QUERY_TILE;
		}
	}

	/** Enter query mode. */
	public void enterQueryMode () {
		if (this.interactionMode == EnumInteractionMode.MODE_QUERY_TILE
			|| this.interactionMode == EnumInteractionMode.MODE_QUERY_WALL) {
			this.toggleMode();
		} else {
			this.interactionMode = EnumInteractionMode.MODE_QUERY_TILE;
			VictusLudusGame.engine.inputPoller.forceMouseMove();
			VictusLudusGame.engine.mousePointer.loadPointer(MousePointer.QUERY_CURSOR);
		}
	}

	/** Enter build mode. */
	public void enterBuildMode () {
		this.interactionMode = EnumInteractionMode.MODE_BUILD;
		// this.gameRenderer.setHighlightedTile(null);
		VictusLudusGame.engine.mousePointer.loadPointer(MousePointer.BUILD_CURSOR);
	}

	/** Enter people mode. */
	public void enterPeopleMode () {
		this.interactionMode = EnumInteractionMode.MODE_PEOPLE;
		// this.gameRenderer.setHighlightedTile(null);
		VictusLudusGame.engine.mousePointer.loadPointer(MousePointer.DEFAULT_CURSOR);
	}

	/**
	 * Gets the builds the mode object id.
	 * 
	 * @return the builds the mode object id
	 */
	public String getBuildModeObjectID () {
		return this.buildModeObjectID;
	}

	/**
	 * Sets the builds the mode object id.
	 * 
	 * @param buildModeObjectID the new builds the mode object id
	 */
	public void setBuildModeObjectID (final String buildModeObjectID) {
		this.buildModeObjectID = buildModeObjectID;
	}

	/**
	 * Gets the builds the mode.
	 * 
	 * @return the builds the mode
	 */
	public EnumBuildMode getBuildMode () {
		return this.buildMode;
	}

	/**
	 * Sets the builds the mode.
	 * 
	 * @param buildMode the new builds the mode
	 */
	public void setBuildMode (final EnumBuildMode buildMode) {
		this.buildMode = buildMode;
	}

	/**
	 * Attempts to build an object id at the indicated position.
	 * 
	 * @param objectID the object id
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	private void build (final String objectID, final int x, final int y, final int z) {
		GameEntity e = VictusLudusGame.resources.getEntityHash().get(objectID);
		Vector3 wc = new Vector3(x, y, z);

		// Check if there is any room
		for (int h = 1; h <= e.getHeight(); h++) {
			// if (this.map.getMap()[wc.getZ() + h][wc.getX()][wc.getY()] != null)
// {
			// return;
			// }
		}

		// Check if we can build this on other buildables
		if (e.getFlagSet().contains(EnumFlags.CANNOT_BUILD_ON_OTHERS)) {
			Vector<GameEntityInstance> gev = this.map.getEntityManager().getEntityListAtPos(wc);
			if (gev != null) {
				for (GameEntityInstance ge : gev) {
					if (ge.getEntity().getFlagSet().contains(EnumFlags.BUILDABLE)) {
						return;
					}
				}
			}
		}

		// Check if we can stack this item against itself
		if (e.getFlagSet().contains(EnumFlags.CANNOT_BUILD_STACKS)) {
			Vector<GameEntityInstance> gev = this.map.getEntityManager().getEntityListAtPos(wc);
			if (gev != null) {
				for (GameEntityInstance ge : gev) {
					if (ge.getEntity().equals(e)) {
						return;
					}
				}
			}
		}

		this.map.addEntity(new GameEntityInstance(objectID, x, y, z, this.map));
	}

	/**
	 * Checks if is running.
	 * 
	 * @return true, if is running
	 */
	@Override
	public boolean isRunning () {
		return this.isRunning;
	}

	/**
	 * Sets the running.
	 * 
	 * @param isRunning the new running
	 */
	@Override
	public void setRunning (final boolean isRunning) {
		this.isRunning = isRunning;
	}

	/**
	 * Checks if is quit signal.
	 * 
	 * @return true, if is quit signal
	 */
	@Override
	public boolean isQuitSignal () {
		return this.quitSignal;
	}

	@Override
	public void dispose () {
		VictusLudusGame.engine.inputMultiplexer.removeProcessor(this.camController);
	}

	/**
	 * Sets the quit signal. If true, we will quit at the first opportunity
	 * 
	 * @param quitSignal the new quit signal
	 */
	@Override
	public void setQuitSignal (final boolean quitSignal) {
		this.quitSignal = quitSignal;
	}

	public GameRenderer getGameRenderer () {
		return this.gameRenderer;
	}

	public Color getAmbientLightColor () {
		return this.ambientLightColor;
	}

	public void setAmbientLightColor (final Color ambientLightColor) {
		this.ambientLightColor = ambientLightColor;
	}

	@Override
	public boolean onScroll (final ScrollEvent scrollEvent) {
		if (scrollEvent.getScrollAmount() > 0) {
			this.zoomOut();
		} else {
			this.zoomIn();
		}

		return true;
	}

	@Override
	public boolean onKeyUp (final KeyUpEvent keyboardEvent) {
		return false;
	}

	@Override
	public boolean onKeyTyped (final KeyTypedEvent keyboardEvent) {
		return false;
	}

	public Camera getCamera () {
		return this.camera;
	}

	/**
	 * Sets the ambient light and the sun light to the specified color
	 * 
	 * @param color
	 */
	public void setLights (final Color color) {
		this.lights.ambientLight.set(color);
		this.sunLight.set(color, 0, -1, 0);
	}

	/**
	 * Gets the color of the light of the sun which is based off the current time
	 * of day
	 * @return the color of the sun light
	 */
	public Color getSunlightColor () {
		float data = this.hour;
		float upperRange = -1;
		Color upperValue = null;

		float lowerRange = -1;
		Color lowerValue = null;

		for (Entry<Float, Color> e : this.solarTintMap.entries()) {
			if (data <= upperRange && data >= e.key) {
				lowerRange = e.key;
				lowerValue = e.value;
				break;
			}

			upperRange = e.key;
			upperValue = e.value;
		}

		// normalize the gradient
		float red, green, blue;

		if (upperValue == null) {
			red = lowerValue.r;
			green = lowerValue.g;
			blue = lowerValue.b;
		} else {
			red = VMath.linearInterpolation(lowerRange, upperRange, lowerValue.r, upperValue.r, data);
			green = VMath.linearInterpolation(lowerRange, upperRange, lowerValue.g, upperValue.g, data);
			blue = VMath.linearInterpolation(lowerRange, upperRange, lowerValue.b, upperValue.b, data);
		}

		return new Color(red, green, blue, 1.0f);
	}

	public Lights getLights () {
		return this.lights;
	}
}
