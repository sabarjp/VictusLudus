
package com.teamderpy.victusludus.renderer.game;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.game.ScreenCoord;
import com.teamderpy.victusludus.game.light.LightEmitter;
import com.teamderpy.victusludus.game.map.Map;
import com.teamderpy.victusludus.gui.eventhandler.RenderListener;
import com.teamderpy.victusludus.gui.eventhandler.event.EnumRenderEventType;
import com.teamderpy.victusludus.gui.eventhandler.event.RenderEvent;

/**
 * The Class LightMap.
 */
public class LightMap implements RenderListener {

	/** The light list. */
	private ArrayList<LightEmitter> lightList;

	/** The static sight map. */
	private Texture[] staticSightMap;

	/** The sight map resolution. */
	private int sightMapResolution = 128;

	/**
	 * A dirty sight map needs to be recalculated on the next pass before
	 * rendering
	 */
	private boolean isDirty = true;

	/** The map this belongs to */
	private Map map;

	/**
	 * Instantiates a new light map.
	 * @param map
	 * 
	 * @param depth the depth
	 */
	public LightMap (final Map map, final int depth) {
		this.map = map;
		this.lightList = new ArrayList<LightEmitter>();
		this.staticSightMap = new Texture[depth];

		this.registerListeners();
	}

	/**
	 * Render light map.
	 * @param deltaT
	 * @param batch
	 * 
	 * @param layer the layer
	 */
	public void renderLightMap (final SpriteBatch batch, final float deltaT, final int layer) {
		if (this.isDirty) {
			this.calculateLightMap(layer);
			this.isDirty = false;
		}

		if (this.staticSightMap[layer] != null) {
			batch.draw(this.staticSightMap[layer], 0, 0, this.map.getGame().getGameDimensions().getWidth(), this.map.getGame()
				.getGameDimensions().getHeight());
		}
	}

	/**
	 * Calculate light map.
	 * 
	 * @param layer the layer
	 */
	public void calculateLightMap (final int layer) {
		this.calculateFogOfWar(layer);
	}

	/**
	 * Calculate fog of war.
	 * 
	 * @param layer the layer
	 */
	private void calculateFogOfWar (final int layer) {
		Pixmap sightMapBuffer = new Pixmap(this.sightMapResolution, this.sightMapResolution, Pixmap.Format.RGBA8888);
		ByteBuffer sightMapByteArray = sightMapBuffer.getPixels();

		// clear buffer
		for (int i = 0; i < sightMapByteArray.capacity(); i += 4) {
			sightMapByteArray.put(i, (byte)0);
			sightMapByteArray.put(i + 1, (byte)0);
			sightMapByteArray.put(i + 2, (byte)0);
			sightMapByteArray.put(i + 3, (byte)128);
		}

		float xblock = (float)this.map.getGame().getGameDimensions().getWidth() / this.sightMapResolution;
		float yblock = (float)this.map.getGame().getGameDimensions().getHeight() / this.sightMapResolution;

		for (LightEmitter l : this.lightList) {
			if (l.getPos().getZ() == layer) {
				ScreenCoord lsc = RenderUtil.worldCoordToScreenCoord(this.map.getGame(), l.getPos().getX(), l.getPos().getY(), l
					.getPos().getZ());
				lsc.x += this.map.getGame().getTileWidthScaled() / 2;

				int realStrength = (int)(l.getStrength() * this.map.getGame().getGameCamera().getZoom());

				// the light map pixels to change, bounded to the screen
				int imin = (int)(Math.max(0, lsc.x - realStrength) / xblock);
				int imax = (int)(Math.min(this.map.getGame().getGameDimensions().getWidth(), lsc.x + realStrength) / xblock);
				int jmin = (int)(Math.max(0, lsc.y - realStrength) / yblock);
				int jmax = (int)(Math.min(this.map.getGame().getGameDimensions().getHeight(), lsc.y + realStrength) / yblock);

				for (int i = imin; i < imax; i++) {
					for (int j = jmin; j < jmax; j++) {
						if (sightMapByteArray.get(4 * (i + j * sightMapBuffer.getWidth()) + 3) == 0) {
							continue; // this fog can't get any brighter
						}

						int mapx = (int)(xblock * (i + 0.5));
						int mapy = (int)(yblock * (j + 0.5));

						int pos = 4 * (i + j * sightMapBuffer.getWidth());

						/*
						 * byte lastPassRed = sightMapByteArray[pos+0]; byte
						 * lastPassGreen = sightMapByteArray[pos+1]; byte lastPassBlue
						 * = sightMapByteArray[pos+2];
						 */
						byte lastPassAlpha = sightMapByteArray.get(pos + 3);

						Float alpha = l.calculateLightTo(mapx, mapy, this.map);

						int newAlpha = (int)((lastPassAlpha & 0xFF) - alpha * 255);

						if (alpha > 0) {
							/*
							 * float newRed = lastPassRed + l.getColor().getRed();
							 * float newGreen = lastPassGreen +
							 * l.getColor().getGreen(); float newBlue = lastPassBlue +
							 * l.getColor().getBlue();
							 * 
							 * float maxColor = Math.max(Math.max(newRed, newGreen),
							 * newBlue);
							 * 
							 * if(maxColor > 255){ float ratio = 255 / maxColor; newRed
							 * *= ratio; newGreen *= ratio; newBlue *= ratio; }
							 * 
							 * sightMapByteArray[pos+0] = (byte) newRed;
							 * sightMapByteArray[pos+1] = (byte) newGreen;
							 * sightMapByteArray[pos+2] = (byte) newBlue;
							 */
							sightMapByteArray.put(pos + 3, (byte)Math.max(newAlpha, 0));
						}
					}
				}
			}
		}

		this.staticSightMap[layer] = new Texture(sightMapBuffer);
	}

	/**
	 * Gets the light map resolution.
	 * 
	 * @return the light map resolution
	 */
	public int getLightMapResolution () {
		return this.sightMapResolution;
	}

	/**
	 * Gets the light list.
	 * 
	 * @return the light list
	 */
	public ArrayList<LightEmitter> getLightList () {
		return this.lightList;
	}

	/**
	 * Adds the.
	 * 
	 * @param light the light
	 */
	public void add (final LightEmitter light) {
		this.lightList.add(light);
		this.isDirty = true;
		// this.calculateLightMap(light.getPos().getZ());
	}

	/**
	 * Register listeners.
	 */
	public void registerListeners () {
		VictusLudusGame.engine.eventHandler.renderHandler.registerPlease(this);
	}

	/**
	 * Unregister listeners.
	 */
	public void unregisterListeners () {
		VictusLudusGame.engine.eventHandler.renderHandler.unregisterPlease(this);
	}

	@Override
	protected void finalize () {
		this.unregisterListeners();
	}

	/**
	 * Checks if is dirty.
	 * 
	 * @return true, if is dirty
	 */
	public boolean isDirty () {
		return this.isDirty;
	}

	/**
	 * Sets the dirty.
	 * 
	 * @param isDirty the new dirty
	 */
	public void setDirty (final boolean isDirty) {
		this.isDirty = isDirty;
	}

	@Override
	public void onRenderChangeEvent (final RenderEvent renderEvent) {
		if (renderEvent.getEventType() == EnumRenderEventType.CHANGE_DEPTH
			|| renderEvent.getEventType() == EnumRenderEventType.ZOOM
			|| renderEvent.getEventType() == EnumRenderEventType.SCROLL_MAP) {
			this.calculateLightMap(this.map.getGame().getCurrentDepth());
		}
	}
}
