package com.teamderpy.victusludus.game.map;

import java.util.ArrayList;
import java.util.Vector;

import com.teamderpy.victusludus.game.GameSettings;
import com.teamderpy.victusludus.game.WorldCoord;
import com.teamderpy.victusludus.game.entity.GameEntity;
import com.teamderpy.victusludus.game.entity.GameEntityManager;
import com.teamderpy.victusludus.game.renderer.LightMap;
import com.teamderpy.victusludus.game.tile.GameTile;

// TODO: Auto-generated Javadoc
/**
 * The Class Map.
 */
public class Map {

	/** The Constant MAXIMUM_DEPTH. */
	private static final int MAXIMUM_DEPTH = 128;

	/** The tiles. */
	private GameTile[][][] tiles;

	/** The entity manager. */
	private GameEntityManager entityManager;

	/** The tile overlay list. */
	private ArrayList<GameTile> tileOverlayList;

	/** The light map. */
	private LightMap lightMap;

	/** The depth. */
	private int depth;

	/** The width. */
	private int width;

	/** The height. */
	private int height;

	/** The highest point. */
	private int highestPoint;

	/**
	 * Instantiates a new map.
	 *
	 * @param width the width
	 * @param height the height
	 */
	public Map(final GameSettings requestedSettings){
		this.width = requestedSettings.getRequestedMapWidth();
		this.height = requestedSettings.getRequestedMapHeight();
		this.depth = Map.MAXIMUM_DEPTH;

		this.tiles = new GameTile[this.depth][this.width][this.height];

		LayeredGenerator gen = new LayeredGenerator((int) requestedSettings.getRequestedMapScale(), requestedSettings.getRequestedMapRandomness());
		int[][] mapArray = gen.generate(this.width, this.height);
		Smoother.smooth(mapArray, (int) requestedSettings.getRequestedMapSmoothness());
		HeightCapper.capMaxHeight(mapArray, this.depth-1);

		int highestPoint = 0;
		int lowestPoint = 9999;

		//find the highest point
		for (int[] row : mapArray) {
			for (int element : row) {
				if(element > highestPoint){
					highestPoint = element;
				}

				if(element < lowestPoint){
					lowestPoint = element;
				}
			}
		}

		//apply the plateau factor
		HeightCapper.capMaxHeight(mapArray, (int) (lowestPoint + (highestPoint-lowestPoint) * (1.0F - requestedSettings.getRequestedMapPlateauFactor())));
		HeightCapper.capMinHeight(mapArray, (int) (lowestPoint + (highestPoint-lowestPoint) * requestedSettings.getRequestedMapPlateauFactor()));

		//create actual terrain
		for (int i=0; i<mapArray.length; i++) {
			for (int j=0; j<mapArray[i].length; j++) {
				for(int k=0; k<mapArray[i][j]; k++){
					this.tiles[k][i][j] = new GameTile(GameTile.ID_AGAR, i, j, k);
					if(mapArray[i][j] > this.highestPoint){
						this.highestPoint = mapArray[i][j];
					}
				}
			}
		}

		//this.fillTilesInLayer(0, GameTile.ID_AGAR);

		this.entityManager = new GameEntityManager();

		this.tileOverlayList = new ArrayList<GameTile>();

		this.lightMap = new LightMap(this.depth);
	}

	/**
	 * Gets the entity manager.
	 *
	 * @return the entity manager
	 */
	public GameEntityManager getEntityManager(){
		return this.entityManager;
	}

	/**
	 * Fill tiles in layer.
	 *
	 * @param depth the depth
	 * @param tileID the tile id
	 */
	public void fillTilesInLayer(final int depth, final byte tileID){
		for(int i=0;i<this.tiles[depth].length;i++){
			for(int j=0;j<this.tiles[depth][i].length;j++){
				this.tiles[depth][i][j] = new GameTile(tileID, i, j, depth);
			}
		}
	}

	/**
	 * Adds the entity.
	 *
	 * @param entity the entity
	 */
	public void addEntity(final GameEntity entity){
		this.entityManager.add(entity);
	}

	/**
	 * Gets the layer.
	 *
	 * @param depth the depth
	 * @return the layer
	 */
	public GameTile[][] getLayer(final int depth){
		return this.tiles[depth];
	}

	/**
	 * Gets the map.
	 *
	 * @return the map
	 */
	public GameTile[][][] getMap(){
		return this.tiles;
	}

	/**
	 * Gets the entities.
	 *
	 * @return the entities
	 */
	public Vector<GameEntity> getEntities(){
		return this.entityManager.getEntities();
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Gets the depth.
	 *
	 * @return the depth
	 */
	public int getDepth() {
		return this.depth;
	}

	/**
	 * Gets the light map.
	 *
	 * @return the light map
	 */
	public LightMap getLightMap(){
		return this.lightMap;
	}

	/**
	 * Gets the tile overlay list.
	 *
	 * @return the tile overlay list
	 */
	public ArrayList<GameTile> getTileOverlayList() {
		return this.tileOverlayList;
	}

	/**
	 * Sets the tile overlay list.
	 *
	 * @param tileOverlayList the new tile overlay list
	 */
	public void setTileOverlayList(final ArrayList<GameTile> tileOverlayList) {
		this.tileOverlayList = tileOverlayList;
	}

	/**
	 * Gets the neighbors.
	 *
	 * @param coord the coord
	 * @return the neighbors
	 */
	public ArrayList<WorldCoord> getNeighbors(final WorldCoord coord){
		ArrayList<WorldCoord> coordList = new ArrayList<WorldCoord>();

		coordList.add(new WorldCoord(coord.getX()+1, coord.getY(), coord.getZ()));
		coordList.add(new WorldCoord(coord.getX()-1, coord.getY(), coord.getZ()));
		coordList.add(new WorldCoord(coord.getX(), coord.getY()+1, coord.getZ()));
		coordList.add(new WorldCoord(coord.getX(), coord.getY()-1, coord.getZ()));
		coordList.add(new WorldCoord(coord.getX(), coord.getY(), coord.getZ()+1));
		coordList.add(new WorldCoord(coord.getX(), coord.getY(), coord.getZ()-1));

		for(int i=0; i<coordList.size(); i++){
			WorldCoord c = coordList.get(i);

			if(c.getX() >= this.width ||
					c.getY() >= this.height ||
					c.getZ() >= this.depth ||
					c.getX() < 0 ||
					c.getY() < 0 ||
					c.getZ() < 0 ){
				coordList.remove(i);
			}
		}

		return coordList;
	}

	/**
	 * Gets the highest point.
	 *
	 * @return the highest point
	 */
	public int getHighestPoint() {
		return this.highestPoint;
	}

	/**
	 * Unregister listeners.
	 */
	public void unregisterListeners() {
		this.lightMap.unregisterListeners();
	}
}
