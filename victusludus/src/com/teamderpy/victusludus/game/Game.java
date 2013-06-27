
package com.teamderpy.victusludus.game;

import java.util.ArrayList;
import java.util.Vector;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.data.resources.EntityDefinition;
import com.teamderpy.victusludus.engine.GameException;
import com.teamderpy.victusludus.engine.ISettings;
import com.teamderpy.victusludus.engine.IView;
import com.teamderpy.victusludus.engine.MousePointer;
import com.teamderpy.victusludus.engine.graphics.GameCamera;
import com.teamderpy.victusludus.engine.graphics.GameDimensions;
import com.teamderpy.victusludus.game.entity.GameEntity;
import com.teamderpy.victusludus.game.map.Map;
import com.teamderpy.victusludus.game.tile.GameTile;
import com.teamderpy.victusludus.gui.GUI;
import com.teamderpy.victusludus.gui.GUIGameHUD;
import com.teamderpy.victusludus.gui.eventhandler.KeyboardListener;
import com.teamderpy.victusludus.gui.eventhandler.MouseListener;
import com.teamderpy.victusludus.gui.eventhandler.event.EnumRenderEventType;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyDownEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyTypedEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyUpEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.RenderEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.ScrollEvent;
import com.teamderpy.victusludus.renderer.game.GameRenderer;
import com.teamderpy.victusludus.renderer.game.RenderUtil;

/** The Class Game. */
public class Game implements IView, KeyboardListener, MouseListener {
	// size of the viewable field
	/** The current depth. */
	private int currentDepth = 0;

	// build mode variables
	/** The build mode object id. */
	private String buildModeObjectID;

	/** The build mode. */
	private EnumBuildMode buildMode;

	/** The build begin coord. */
	private WorldCoordSelection buildBeginCoord;

	// camera offsets
	/** The game camera. */
	private GameCamera gameCamera;

	/** The game dimensions. */
	private GameDimensions gameDimensions;

	// edit tiles or walls
	/** The interaction mode. */
	private byte interactionMode = EnumInteractionMode.MODE_QUERY_TILE;

	/** The map. */
	private Map map;

	/** The color of the ambient light from the sun */
	private Color ambientLightColor;

	/** The game renderer. */
	private GameRenderer gameRenderer;

	// right-click tracking for scrolling map
	/** The holding down right click. */
	private boolean holdingDownRightClick = false;

	/** The right click x. */
	private int rightClickX = 0;

	/** The right click y. */
	private int rightClickY = 0;

	// gui overlay
	/** The current gui. */
	private GUI currentGUI = null;

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

		GameSettings requestedSettings = (GameSettings)settings;

		this.ambientLightColor = Color.WHITE;

		this.gameDimensions = new GameDimensions();

		// setup game map
		this.gameDimensions.setWidth(VictusLudusGame.engine.X_RESOLUTION());
		this.gameDimensions.setHeight(VictusLudusGame.engine.Y_RESOLUTION());

		this.registerListeners();

		this.map = new Map(requestedSettings, this);
		this.currentDepth = this.map.getHighestPoint() - 1;

		// setup GUI
		this.changeGUI(new com.teamderpy.victusludus.gui.GUIGameHUD(this));
		((GUIGameHUD)this.currentGUI).setCurrentDepthText(Integer.toString(this.currentDepth));

		this.gameCamera = new GameCamera();

		// center camera
		this.gameCamera.setOffsetX(this.gameDimensions.getWidth() / 2);

		this.gameRenderer = new GameRenderer(this);

		this.isRunning = true;
	}

	/** Render. */
	@Override
	public void render (final SpriteBatch batch, final float deltaT) {
		if (this.isRunning) {
			this.gameRenderer.renderGameLayer(this.map, this.currentDepth);

			// GUI
			if (this.currentGUI != null) {
				this.currentGUI.render(batch, deltaT);
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
	 * Gets the tile height s.
	 * 
	 * @return the tile height s
	 */
	public int getTileHeightS () {
		return (int)(this.getGameDimensions().getTileHeight() * this.gameCamera.getZoom());
	}

	/**
	 * Gets the tile width s.
	 * 
	 * @return the tile width s
	 */
	public int getTileWidthS () {
		return (int)(this.getGameDimensions().getTileWidth() * this.gameCamera.getZoom());
	}

	/**
	 * Gets the wall height s.
	 * 
	 * @return the wall height s
	 */
	public int getWallHeightS () {
		return (int)(this.getGameDimensions().getWallHeight() * this.gameCamera.getZoom());
	}

	/**
	 * Gets the wall width s.
	 * 
	 * @return the wall width s
	 */
	public int getWallWidthS () {
		return (int)(this.getGameDimensions().getWallWidth() * this.gameCamera.getZoom());
	}

	/**
	 * Gets the layer height s.
	 * 
	 * @return the layer height s
	 */
	public int getLayerHeightS () {
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

	/** Increase depth. */
	private void increaseDepth () {
		if (this.currentDepth < this.map.getDepth()) {
			this.currentDepth++;
		}

		((GUIGameHUD)this.currentGUI).setCurrentDepthText(Integer.toString(this.currentDepth));
		VictusLudusGame.engine.inputPoller.forceMouseMove();

		VictusLudusGame.engine.eventHandler.signal(new RenderEvent(this, EnumRenderEventType.CHANGE_DEPTH, this));
	}

	/** Decrease depth. */
	private void decreaseDepth () {
		if (this.currentDepth > 0) {
			this.currentDepth--;
		}

		((GUIGameHUD)this.currentGUI).setCurrentDepthText(Integer.toString(this.currentDepth));
		VictusLudusGame.engine.inputPoller.forceMouseMove();

		VictusLudusGame.engine.eventHandler.signal(new RenderEvent(this, EnumRenderEventType.CHANGE_DEPTH, this));
	}

	/** Tick. */
	@Override
	public void tick () {
		if (this.quitSignal) {
			VictusLudusGame.engine.terminateView();
		} else if (this.isRunning) {
			if (this.currentGUI != null) {
				this.currentGUI.tick();
			}

			// beware of concurrent modification!
			for (int i = 0; i < this.map.getEntities().size(); i++) {
				this.map.getEntities().get(i).tick();
			}

			// display some debug stuff
			((GUIGameHUD)this.currentGUI).setDebugText("Entities: " + this.map.getEntities().size() + "    GameH: "
				+ this.getGameDimensions().getHeight() + " GameW: " + this.getGameDimensions().getWidth());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teamderpy.victusludus.gui.eventhandler.MouseListener#onMouseClick(com.teamderpy.victusludus.gui.eventhandler.event.
	 * MouseEvent)
	 */
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
						WorldCoordSelection temp = RenderUtil.screenCoordToWorldCoord(this, mouseEvent.getX(), mouseEvent.getY(),
							this.currentDepth, false);

						if (temp.x >= 0 && temp.y >= 0) {
							if (temp.x < this.map.getLayer(this.currentDepth).length
								&& temp.y < this.map.getLayer(this.currentDepth)[0].length) {
								if (this.map.getLayer(this.currentDepth)[temp.x][temp.y] != null) {
									this.buildBeginCoord = temp;
								}
							}
						}

						return true;
					}
				} else {
					// let go

					if (this.buildMode == EnumBuildMode.LINE) {
						// complete the line
						if (this.buildBeginCoord != null) {
							WorldCoordSelection wcs = RenderUtil.screenCoordToWorldCoord(this, mouseEvent.getX(), mouseEvent.getY(),
								this.currentDepth, false);
							ArrayList<WorldCoord> temp = AStarSearch.search(this.map, new WorldCoord(this.buildBeginCoord.x,
								this.buildBeginCoord.y, this.currentDepth), new WorldCoord(wcs.x, wcs.y, this.currentDepth));

							if (temp != null) {
								for (WorldCoord wc : temp) {
									this.build(this.getBuildModeObjectID(), wc.getX(), wc.getY(), this.currentDepth);
								}
							}

							this.buildBeginCoord = null;
							this.map.getTileOverlayList().clear();
							return true;
						}

					} else {
						WorldCoordSelection c = RenderUtil.screenCoordToWorldCoord(this, mouseEvent.getX(), mouseEvent.getY(),
							this.currentDepth, false);

						if (c.x >= 0 && c.y >= 0) {
							if (c.x < this.map.getLayer(this.currentDepth).length
								&& c.y < this.map.getLayer(this.currentDepth)[0].length) {
								if (this.map.getLayer(this.currentDepth)[c.x][c.y] != null) {
									this.build(this.getBuildModeObjectID(), c.x, c.y, this.currentDepth);
								}
							}
						}

						return true;
					}
				}
			}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teamderpy.victusludus.gui.eventhandler.MouseListener#onMouseMove(com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent
	 * )
	 */
	@Override
	public void onMouseMove (final MouseEvent mouseEvent) {
		// not moving the map
		if (!this.holdingDownRightClick) {
			this.map.getTileOverlayList().clear();
			WorldCoordSelection c = null;

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
					ArrayList<WorldCoord> temp = AStarSearch.search(this.map, new WorldCoord(this.buildBeginCoord.x,
						this.buildBeginCoord.y, this.currentDepth), new WorldCoord(c.x, c.y, this.currentDepth));

					if (temp != null) {
						for (WorldCoord wc : temp) {
							this.map.getTileOverlayList().add(
								new GameTile(GameTile.ID_PATH_GOOD, wc.getX(), wc.getY(), wc.getZ(), this.map));
						}
					}

					// this.map.getTileOverlayList().add(new GameTile(GameTile.ID_PATH_GOOD, this.buildBeginCoord.x,
// this.buildBeginCoord.y, this.currentDepth));
					// this.map.getTileOverlayList().add(new GameTile(GameTile.ID_PATH_GOOD, c.x, c.y, this.currentDepth));
				}
			} else {
				return;
			}

			if (c.x >= 0 && c.y >= 0) {
				if (c.x < this.map.getLayer(this.currentDepth).length && c.y < this.map.getLayer(this.currentDepth)[0].length) {
					// this.gameRenderer.setHighlightedTile(c);
					this.map.getTileOverlayList().add(new GameTile(GameTile.ID_GRID, c.x, c.y, this.currentDepth, this.map));
					((GUIGameHUD)this.currentGUI).setCurrentTileText(c.x + "," + c.y);
				} else {
					this.map.getTileOverlayList().clear();
					// this.gameRenderer.setHighlightedTile(null);
				}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teamderpy.victusludus.gui.eventhandler.KeyboardListener#onKeyPress(com.teamderpy.victusludus.gui.eventhandler.event.
	 * KeyboardEvent)
	 */
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

		this.changeGUI(null);
	}

	/**
	 * Change gui.
	 * 
	 * @param newGUI the new gui
	 */
	private void changeGUI (final GUI newGUI) {
		if (this.currentGUI != null) {
			this.currentGUI.unregisterListeners();
		}
		this.currentGUI = newGUI;
		VictusLudusGame.engine.inputPoller.forceMouseMove();
	}

	/** Toggle mode. */
	public void toggleMode () {
		if (this.interactionMode == EnumInteractionMode.MODE_QUERY_TILE) {
			this.interactionMode = EnumInteractionMode.MODE_QUERY_WALL;
			this.map.getTileOverlayList().clear();
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
		this.map.getTileOverlayList().clear();
		// this.gameRenderer.setHighlightedTile(null);
		VictusLudusGame.engine.mousePointer.loadPointer(MousePointer.BUILD_CURSOR);
	}

	/** Enter people mode. */
	public void enterPeopleMode () {
		this.interactionMode = EnumInteractionMode.MODE_PEOPLE;
		this.map.getTileOverlayList().clear();
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
		EntityDefinition e = VictusLudusGame.resources.getEntityHash().get(objectID);
		WorldCoord wc = new WorldCoord(x, y, z);

		// Check if there is any room
		for (int h = 1; h <= e.getHeight(); h++) {
			if (this.map.getMap()[wc.getZ() + h][wc.getX()][wc.getY()] != null) {
				return;
			}
		}

		// Check if we can build this on other buildables
		if (e.getFlagSet().contains(EnumFlags.CANNOT_BUILD_ON_OTHERS)) {
			Vector<GameEntity> gev = this.map.getEntityManager().getEntityListAtPos(wc);
			if (gev != null) {
				for (GameEntity ge : gev) {
					if (ge.getEntity().getFlagSet().contains(EnumFlags.BUILDABLE)) {
						return;
					}
				}
			}
		}

		// Check if we can stack this item against itself
		if (e.getFlagSet().contains(EnumFlags.CANNOT_BUILD_STACKS)) {
			Vector<GameEntity> gev = this.map.getEntityManager().getEntityListAtPos(wc);
			if (gev != null) {
				for (GameEntity ge : gev) {
					if (ge.getEntity().equals(e)) {
						return;
					}
				}
			}
		}

		this.map.addEntity(new GameEntity(objectID, x, y, z, this.map));
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

	/**
	 * Sets the quit signal.
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
		return false;
	}

	@Override
	public boolean onKeyUp (final KeyUpEvent keyboardEvent) {
		return false;
	}

	@Override
	public boolean onKeyTyped (final KeyTypedEvent keyboardEvent) {
		return false;
	}
}
