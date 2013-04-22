package com.teamderpy.victusludus.game;

import java.util.ArrayList;
import java.util.Vector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.engine.InputPoller;
import com.teamderpy.victusludus.engine.MousePointer;
import com.teamderpy.victusludus.game.entity.GameEntity;
import com.teamderpy.victusludus.game.map.Map;
import com.teamderpy.victusludus.game.renderer.GameRenderer;
import com.teamderpy.victusludus.game.renderer.RenderUtil;
import com.teamderpy.victusludus.game.tile.GameTile;
import com.teamderpy.victusludus.gui.DialogBox;
import com.teamderpy.victusludus.gui.GUI;
import com.teamderpy.victusludus.gui.GUIGameHUD;
import com.teamderpy.victusludus.gui.eventhandler.KeyboardListener;
import com.teamderpy.victusludus.gui.eventhandler.MouseListener;
import com.teamderpy.victusludus.gui.eventhandler.event.EnumRenderEventType;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyboardEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.RenderEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent;
import com.teamderpy.victusludus.parts.Entity;
import com.teamderpy.victusludus.parts.EnumBuildMode;

// TODO: Auto-generated Javadoc
/**
 * The Class Game.
 */
public class Game implements KeyboardListener, MouseListener{
	//size of the viewable field
	/** The current depth. */
	private int currentDepth = 0;

	//build mode variables
	/** The build mode object id. */
	private String buildModeObjectID;

	/** The build mode. */
	private EnumBuildMode buildMode;

	/** The build begin coord. */
	private WorldCoordSelection buildBeginCoord;

	//camera offsets
	/** The game camera. */
	private GameCamera gameCamera;

	//edit tiles or walls
	/** The interaction mode. */
	private byte interactionMode = EnumInteractionMode.MODE_QUERY_TILE;

	//grid and tile sizes
	/** The game dimensions. */
	private GameDimensions gameDimensions;

	/** The map. */
	private Map map;

	/** The game renderer. */
	private GameRenderer gameRenderer;

	//right-click tracking for scrolling map
	/** The holding down right click. */
	private boolean holdingDownRightClick = false;

	/** The right click x. */
	private int rightClickX = 0;

	/** The right click y. */
	private int rightClickY = 0;

	//gui overlay
	/** The current gui. */
	private GUI currentGUI = null;

	/** The current dialog. */
	private DialogBox currentDialog = null;

	/** Whether or not the game is actually running yet */
	private boolean isRunning = false;

	/** Signal to terminate the game */
	private boolean quitSignal = false;

	/**
	 * Instantiates a new game.
	 */
	public Game(final GameSettings requestedSettings){
		this.gameDimensions = new GameDimensions();

		//setup game map
		this.gameDimensions.setWidth(VictusLudus.e.X_RESOLUTION());
		this.gameDimensions.setHeight(VictusLudus.e.Y_RESOLUTION());

		this.registerListeners();

		this.map = new Map(requestedSettings);
		this.currentDepth = this.map.getHighestPoint();

		//setup GUI
		this.changeGUI(new com.teamderpy.victusludus.gui.GUIGameHUD());
		((GUIGameHUD) this.currentGUI).setCurrentDepthText(Integer.toString(this.currentDepth));

		this.gameCamera = new GameCamera();

		//center camera
		this.gameCamera.setOffsetX(this.gameDimensions.getWidth()/2);

		this.gameRenderer = new GameRenderer(this);

		this.isRunning = true;
	}

	/**
	 * Render.
	 */
	public void render(){
		if(this.isRunning){
			this.gameRenderer.renderGameLayer(this.map, this.currentDepth);

			// GUI
			if(this.currentGUI != null) {
				this.currentGUI.render();
			}

			if(this.currentDialog != null) {
				this.currentDialog.render();
			}
		}
	}


	/**
	 * Zoom out.
	 */
	public void zoomOut(){
		if(this.gameCamera.getZoom() > 0.25){
			this.gameCamera.setZoom(this.gameCamera.getZoom() / 2);
			VictusLudus.e.eventHandler.signal(new ResizeEvent(VictusLudus.e.currentDisplayMode, Display.getWidth(), Display.getHeight()));
		}
	}

	/**
	 * Zoom in.
	 */
	public void zoomIn(){
		if(this.gameCamera.getZoom() < 4){
			this.gameCamera.setZoom(this.gameCamera.getZoom() * 2);
			VictusLudus.e.eventHandler.signal(new ResizeEvent(VictusLudus.e.currentDisplayMode, Display.getWidth(), Display.getHeight()));
		}
	}

	/**
	 * Gets the current depth.
	 *
	 * @return the current depth
	 */
	public int getCurrentDepth() {
		return this.currentDepth;
	}

	/**
	 * Gets the tile height s.
	 *
	 * @return the tile height s
	 */
	public int getTileHeightS(){
		return (int) (this.getGameDimensions().getTileHeight() * this.gameCamera.getZoom());
	}

	/**
	 * Gets the tile width s.
	 *
	 * @return the tile width s
	 */
	public int getTileWidthS(){
		return (int) (this.getGameDimensions().getTileWidth() * this.gameCamera.getZoom());
	}

	/**
	 * Gets the wall height s.
	 *
	 * @return the wall height s
	 */
	public int getWallHeightS(){
		return (int) (this.getGameDimensions().getWallHeight() * this.gameCamera.getZoom());
	}

	/**
	 * Gets the wall width s.
	 *
	 * @return the wall width s
	 */
	public int getWallWidthS(){
		return (int) (this.getGameDimensions().getWallWidth() * this.gameCamera.getZoom());
	}

	/**
	 * Gets the layer height s.
	 *
	 * @return the layer height s
	 */
	public int getLayerHeightS(){
		return (int) (this.getGameDimensions().getLayerHeight() * this.gameCamera.getZoom());
	}

	/**
	 * Gets the map.
	 *
	 * @return the map
	 */
	public Map getMap(){
		return this.map;
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

	/**
	 * Increase depth.
	 */
	private void increaseDepth(){
		if(this.currentDepth < this.map.getDepth()) {
			this.currentDepth++;
		}

		((GUIGameHUD) this.currentGUI).setCurrentDepthText(Integer.toString(this.currentDepth));
		InputPoller.forceMouseMove();

		VictusLudus.e.eventHandler.signal(new RenderEvent(this, EnumRenderEventType.CHANGE_DEPTH));
	}

	/**
	 * Decrease depth.
	 */
	private void decreaseDepth(){
		if(this.currentDepth > 0) {
			this.currentDepth--;
		}

		((GUIGameHUD) this.currentGUI).setCurrentDepthText(Integer.toString(this.currentDepth));
		InputPoller.forceMouseMove();

		VictusLudus.e.eventHandler.signal(new RenderEvent(this, EnumRenderEventType.CHANGE_DEPTH));
	}



	/**
	 * Tick.
	 */
	public void tick(){
		if(this.quitSignal){
			VictusLudus.e.terminateGame();
		} else if(this.isRunning){
			if(this.currentGUI != null) {
				this.currentGUI.tick();
			}

			if(this.currentDialog != null) {
				this.currentDialog.tick();
			}

			//beware of concurrent modification!
			for(int i=0; i<this.map.getEntities().size(); i++){
				this.map.getEntities().get(i).tick();
			}

			//display some debug stuff
			((GUIGameHUD) this.currentGUI).setDebugText("Entities: " + this.map.getEntities().size() + "    GameH: " + this.getGameDimensions().getHeight() + " GameW: " + this.getGameDimensions().getWidth());
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.MouseListener#onMouseClick(com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent)
	 */
	@Override
	public void onMouseClick(final MouseEvent mouseEvent) {
		//left click
		if (mouseEvent.getButton() == MouseEvent.BUTTON_1) {
			if(this.interactionMode == EnumInteractionMode.MODE_QUERY_TILE){
				if (mouseEvent.isButtonPressed()){
					//do something
				}
			} else if(this.interactionMode == EnumInteractionMode.MODE_BUILD){
				//build mode
				if (this.buildModeObjectID == null){
					return;
				}

				if (mouseEvent.isButtonPressed()){
					//depressed
					if (this.buildMode == EnumBuildMode.LINE){
						//mark the starting position if it is in-bounds
						this.buildBeginCoord = null;
						WorldCoordSelection temp = RenderUtil.screenCoordToWorldCoord(mouseEvent.getX(), mouseEvent.getY(), this.currentDepth, false);

						if(temp.x >= 0 && temp.y >= 0){
							if(temp.x < this.map.getLayer(this.currentDepth).length && temp.y <this.map.getLayer(this.currentDepth)[0].length){
								if(this.map.getLayer(this.currentDepth)[temp.x][temp.y] != null){
									this.buildBeginCoord = temp;
								}
							}
						}
					}
				} else {
					//let go

					if (this.buildMode == EnumBuildMode.LINE){
						//complete the line
						if(this.buildBeginCoord != null){
							WorldCoordSelection wcs = RenderUtil.screenCoordToWorldCoord(mouseEvent.getX(), mouseEvent.getY(), this.currentDepth, false);
							ArrayList<WorldCoord> temp = AStarSearch.search(this.map, new WorldCoord(this.buildBeginCoord.x, this.buildBeginCoord.y, this.currentDepth), new WorldCoord(wcs.x, wcs.y, this.currentDepth));

							if(temp != null){
								for(WorldCoord wc:temp){
									this.build(this.getBuildModeObjectID(), wc.getX(), wc.getY(), this.currentDepth);
								}
							}

							this.buildBeginCoord = null;
							this.map.getTileOverlayList().clear();
						}

					} else {
						WorldCoordSelection c = RenderUtil.screenCoordToWorldCoord(mouseEvent.getX(), mouseEvent.getY(), this.currentDepth, false);

						if(c.x >= 0 && c.y >= 0){
							if(c.x < this.map.getLayer(this.currentDepth).length && c.y <this.map.getLayer(this.currentDepth)[0].length){
								if(this.map.getLayer(this.currentDepth)[c.x][c.y] != null){
									this.build(this.getBuildModeObjectID(), c.x, c.y, this.currentDepth);
								}
							}
						}
					}
				}
			}
		} else if (mouseEvent.getButton() == MouseEvent.BUTTON_2) {
			if(mouseEvent.isButtonPressed()){
				this.holdingDownRightClick = true;
				this.rightClickX = mouseEvent.getX();
				this.rightClickY = mouseEvent.getY();
			} else {
				this.holdingDownRightClick = false;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.MouseListener#onMouseMove(com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent)
	 */
	@Override
	public void onMouseMove(final MouseEvent mouseEvent) {
		//not moving the map
		if(!this.holdingDownRightClick){
			this.map.getTileOverlayList().clear();
			WorldCoordSelection c = null;

			if(this.interactionMode == EnumInteractionMode.MODE_QUERY_TILE){
				//highlight tile under cursor
				c = RenderUtil.screenCoordToWorldCoord(mouseEvent.getX(), mouseEvent.getY(), this.currentDepth, false);
			} else if(this.interactionMode == EnumInteractionMode.MODE_QUERY_WALL) {
				//highlight tile and wall under cursor
				c = RenderUtil.screenCoordToWorldCoord(mouseEvent.getX(), mouseEvent.getY(), this.currentDepth, true);
			} else if(this.interactionMode == EnumInteractionMode.MODE_BUILD){
				//highlight tile under cursor
				c = RenderUtil.screenCoordToWorldCoord(mouseEvent.getX(), mouseEvent.getY(), this.currentDepth, false);

				//we are in the middle of a build so draw stuff
				if (this.buildMode == EnumBuildMode.LINE && this.buildBeginCoord != null){
					ArrayList<WorldCoord> temp = AStarSearch.search(this.map, new WorldCoord(this.buildBeginCoord.x, this.buildBeginCoord.y, this.currentDepth), new WorldCoord(c.x, c.y, this.currentDepth));

					if(temp != null){
						for(WorldCoord wc:temp){
							this.map.getTileOverlayList().add(new GameTile(GameTile.ID_PATH_GOOD, wc.getX(), wc.getY(), wc.getZ()));
						}
					}

					//this.map.getTileOverlayList().add(new GameTile(GameTile.ID_PATH_GOOD, this.buildBeginCoord.x, this.buildBeginCoord.y, this.currentDepth));
					//this.map.getTileOverlayList().add(new GameTile(GameTile.ID_PATH_GOOD, c.x, c.y, this.currentDepth));
				}
			} else {
				return;
			}

			if(c.x >= 0 && c.y >= 0){
				if(c.x < this.map.getLayer(this.currentDepth).length && c.y < this.map.getLayer(this.currentDepth)[0].length){
					//this.gameRenderer.setHighlightedTile(c);
					this.map.getTileOverlayList().add(new GameTile(GameTile.ID_GRID, c.x, c.y, this.currentDepth));
					((GUIGameHUD) this.currentGUI).setCurrentTileText(c.x + "," + c.y);
				} else {
					this.map.getTileOverlayList().clear();
					//this.gameRenderer.setHighlightedTile(null);
				}
			}
			//map is being scrolled
		} else {
			this.gameCamera.setOffsetX(this.gameCamera.getOffsetX() - (this.rightClickX - mouseEvent.getX()));
			this.gameCamera.setOffsetY(this.gameCamera.getOffsetY() - (this.rightClickY - mouseEvent.getY()));

			this.map.getLightMap().calculateLightMap(this.currentDepth);

			this.rightClickX = mouseEvent.getX();
			this.rightClickY = mouseEvent.getY();
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.KeyboardListener#onKeyPress(com.teamderpy.victusludus.gui.eventhandler.event.KeyboardEvent)
	 */
	@Override
	public void onKeyPress(final KeyboardEvent keyboardEvent) {
		if (keyboardEvent.getKey() == Keyboard.KEY_I) {
			this.gameCamera.moveCameraUp();
		} else if (keyboardEvent.getKey() == Keyboard.KEY_K) {
			this.gameCamera.moveCameraDown();
		} else if (keyboardEvent.getKey() == Keyboard.KEY_L) {
			this.gameCamera.moveCameraRight();
		} else if (keyboardEvent.getKey() == Keyboard.KEY_J) {
			this.gameCamera.moveCameraLeft();
		} else if (keyboardEvent.getKey() == Keyboard.KEY_Z) {
			this.zoomOut();
		} else if (keyboardEvent.getKey() == Keyboard.KEY_X) {
			this.zoomIn();
		} else if (keyboardEvent.getKey() == Keyboard.KEY_ADD) {
			this.increaseDepth();
		} else if (keyboardEvent.getKey() == Keyboard.KEY_SUBTRACT) {
			this.decreaseDepth();
		}
	}

	/**
	 * Register listeners.
	 */
	public void registerListeners() {
		VictusLudus.e.eventHandler.mouseHandler.registerPlease(this);
		VictusLudus.e.eventHandler.keyboardHandler.registerPlease(this);
	}

	/**
	 * Unregister listeners.
	 */
	public void unregisterListeners() {
		VictusLudus.e.eventHandler.mouseHandler.unregisterPlease(this);
		VictusLudus.e.eventHandler.keyboardHandler.unregisterPlease(this);

		this.map.unregisterListeners();
		this.gameDimensions.unregisterListeners();
		this.gameRenderer.unregisterListeners();

		this.changeGUI(null);
		this.displayDialog(null);
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
	 * Toggle mode.
	 */
	public void toggleMode(){
		if(this.interactionMode == EnumInteractionMode.MODE_QUERY_TILE){
			this.interactionMode = EnumInteractionMode.MODE_QUERY_WALL;
			this.map.getTileOverlayList().clear();
			//this.gameRenderer.setHighlightedTile(null);
		} else if(this.interactionMode == EnumInteractionMode.MODE_QUERY_WALL){
			this.interactionMode = EnumInteractionMode.MODE_QUERY_TILE;
		}
	}

	/**
	 * Enter query mode.
	 */
	public void enterQueryMode(){
		if(this.interactionMode == EnumInteractionMode.MODE_QUERY_TILE || this.interactionMode == EnumInteractionMode.MODE_QUERY_WALL){
			this.toggleMode();
		}
		else{
			this.interactionMode = EnumInteractionMode.MODE_QUERY_TILE;
			InputPoller.forceMouseMove();
			VictusLudus.e.mousePointer.loadPointer(MousePointer.QUERY_CURSOR);
		}
	}

	/**
	 * Enter build mode.
	 */
	public void enterBuildMode(){
		this.interactionMode = EnumInteractionMode.MODE_BUILD;
		this.map.getTileOverlayList().clear();
		//this.gameRenderer.setHighlightedTile(null);
		VictusLudus.e.mousePointer.loadPointer(MousePointer.BUILD_CURSOR);
	}

	/**
	 * Enter people mode.
	 */
	public void enterPeopleMode(){
		this.interactionMode = EnumInteractionMode.MODE_PEOPLE;
		this.map.getTileOverlayList().clear();
		//this.gameRenderer.setHighlightedTile(null);
		VictusLudus.e.mousePointer.loadPointer(MousePointer.DEFAULT_CURSOR);
	}

	/**
	 * Gets the builds the mode object id.
	 *
	 * @return the builds the mode object id
	 */
	public String getBuildModeObjectID() {
		return this.buildModeObjectID;
	}

	/**
	 * Sets the builds the mode object id.
	 *
	 * @param buildModeObjectID the new builds the mode object id
	 */
	public void setBuildModeObjectID(final String buildModeObjectID) {
		this.buildModeObjectID = buildModeObjectID;
	}

	/**
	 * Gets the builds the mode.
	 *
	 * @return the builds the mode
	 */
	public EnumBuildMode getBuildMode() {
		return this.buildMode;
	}

	/**
	 * Sets the builds the mode.
	 *
	 * @param buildMode the new builds the mode
	 */
	public void setBuildMode(final EnumBuildMode buildMode) {
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
	private void build(final String objectID, final int x, final int y, final int z){
		Entity e = VictusLudus.resources.getEntityHash().get(objectID);
		WorldCoord wc = new WorldCoord(x, y, z);

		//Check if there is any room
		for(int h=1; h<e.getHeight(); h++) {
			if(this.map.getMap()[wc.getZ()+h][wc.getX()][wc.getY()] != null){
				return;
			}
		}

		//Check if we can build this on other buildables
		if(e.getFlagSet().contains(EnumFlags.CANNOT_BUILD_ON_OTHERS)){
			Vector<GameEntity> gev = this.map.getEntityManager().getEntityListAtPos(wc);
			if(gev != null){
				for(GameEntity ge:gev){
					if(ge.getEntity().getFlagSet().contains(EnumFlags.BUILDABLE)){
						return;
					}
				}
			}
		}

		//Check if we can stack this item against itself
		if(e.getFlagSet().contains(EnumFlags.CANNOT_BUILD_STACKS)){
			Vector<GameEntity> gev = this.map.getEntityManager().getEntityListAtPos(wc);
			if(gev != null){
				for(GameEntity ge:gev){
					if(ge.getEntity().equals(e)){
						return;
					}
				}
			}
		}

		this.map.addEntity(new GameEntity(objectID, x, y, z));
	}

	public boolean isRunning() {
		return this.isRunning;
	}

	public void setRunning(final boolean isRunning) {
		this.isRunning = isRunning;
	}

	public boolean isQuitSignal() {
		return this.quitSignal;
	}

	public void setQuitSignal(final boolean quitSignal) {
		this.quitSignal = quitSignal;
	}
}
