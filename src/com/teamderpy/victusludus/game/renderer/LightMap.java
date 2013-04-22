package com.teamderpy.victusludus.game.renderer;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.game.ScreenCoord;
import com.teamderpy.victusludus.gui.eventhandler.RenderListener;
import com.teamderpy.victusludus.gui.eventhandler.event.EnumRenderEventType;
import com.teamderpy.victusludus.gui.eventhandler.event.RenderEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class LightMap.
 */
public class LightMap implements RenderListener{

	/** The light list. */
	private ArrayList<Light> lightList;

	/** The static sight map. */
	private Image[] staticSightMap;

	/** The sight map buffer. */
	private ImageBuffer sightMapBuffer;

	/** The sight map resolution. */
	private int sightMapResolution = 128;

	/** The is dirty. */
	private boolean isDirty = true;

	/**
	 * Instantiates a new light map.
	 *
	 * @param depth the depth
	 */
	public LightMap(final int depth){
		this.lightList = new ArrayList<Light>();
		this.sightMapBuffer = new ImageBuffer(this.sightMapResolution, this.sightMapResolution);
		this.staticSightMap = new Image[depth];

		this.registerListeners();
	}

	/**
	 * Render light map.
	 *
	 * @param layer the layer
	 */
	public void renderLightMap(final int layer){
		if(this.isDirty){
			this.calculateLightMap(layer);
			this.isDirty = false;
		}

		if(this.staticSightMap[layer] != null){
			this.staticSightMap[layer].draw(0, 0, VictusLudus.e.currentGame.getGameDimensions().getWidth(), VictusLudus.e.currentGame.getGameDimensions().getHeight());
		}
	}

	/**
	 * Calculate light map.
	 *
	 * @param layer the layer
	 */
	public void calculateLightMap(final int layer){
		this.calculateFogOfWar(layer);
	}

	/**
	 * Calculate fog of war.
	 *
	 * @param layer the layer
	 */
	private void calculateFogOfWar(final int layer){
		byte[] sightMapByteArray = this.sightMapBuffer.getRGBA();

		//clear buffer
		for(int i=0; i<sightMapByteArray.length; i+=4){
			sightMapByteArray[i] = 0;
			sightMapByteArray[i+1] = 0;
			sightMapByteArray[i+2] = 0;
			sightMapByteArray[i+3] = (byte)0;
		}

		float xblock = (float)VictusLudus.e.currentGame.getGameDimensions().getWidth()/this.sightMapResolution;
		float yblock = (float)VictusLudus.e.currentGame.getGameDimensions().getHeight()/this.sightMapResolution;

		for(Light l:this.lightList){
			if(l.getPos().getZ() == layer){
				ScreenCoord lsc = RenderUtil.worldCoordToScreenCoord(l.getPos().getX(), l.getPos().getY(), l.getPos().getZ());
				lsc.x += VictusLudus.e.currentGame.getTileWidthS()/2;

				int realStrength = (int) (l.getStrength()*VictusLudus.e.currentGame.getGameCamera().getZoom());

				//the light map pixels to change, bounded to the screen
				int imin = (int) (Math.max(0, lsc.x - realStrength) / xblock);
				int imax = (int) (Math.min(VictusLudus.e.currentGame.getGameDimensions().getWidth(), lsc.x + realStrength) / xblock);
				int jmin = (int) (Math.max(0, lsc.y - realStrength) / yblock);
				int jmax = (int) (Math.min(VictusLudus.e.currentGame.getGameDimensions().getHeight(), lsc.y + realStrength) / yblock);

				for(int i=imin; i < imax; i++){
					for(int j=jmin; j < jmax; j++){
						if(sightMapByteArray[4 * (i + j*this.sightMapBuffer.getTexWidth())+3] == 0){
							continue;  //this fog can't get any brighter
						}

						int mapx = (int) (xblock*(i+0.5));
						int mapy = (int) (yblock*(j+0.5));

						int pos = 4 * (i + j*this.sightMapBuffer.getTexWidth());

						/*
						byte lastPassRed   = sightMapByteArray[pos+0];
						byte lastPassGreen = sightMapByteArray[pos+1];
						byte lastPassBlue  = sightMapByteArray[pos+2];
						 */
						byte lastPassAlpha = sightMapByteArray[pos+3];

						Float alpha = l.calculateLightTo(mapx, mapy);

						int newAlpha = (int) ((lastPassAlpha & 0xFF) - alpha*255);

						if(alpha > 0){
							/*
							float newRed = lastPassRed + l.getColor().getRed();
							float newGreen = lastPassGreen + l.getColor().getGreen();
							float newBlue = lastPassBlue + l.getColor().getBlue();

							float maxColor = Math.max(Math.max(newRed, newGreen), newBlue);

							if(maxColor > 255){
								float ratio = 255 / maxColor;
								newRed *= ratio;
								newGreen *= ratio;
								newBlue *= ratio;
							}

							sightMapByteArray[pos+0] = (byte) newRed;
							sightMapByteArray[pos+1] = (byte) newGreen;
							sightMapByteArray[pos+2] = (byte) newBlue;
							 */
							sightMapByteArray[pos+3] = (byte) Math.max(newAlpha,0);
						}
					}
				}
			}
		}

		this.staticSightMap[layer] = this.sightMapBuffer.getImage(Image.FILTER_NEAREST);
	}

	/**
	 * Gets the light map resolution.
	 *
	 * @return the light map resolution
	 */
	public int getLightMapResolution() {
		return this.sightMapResolution;
	}

	/**
	 * Gets the light list.
	 *
	 * @return the light list
	 */
	public ArrayList<Light> getLightList() {
		return this.lightList;
	}

	/**
	 * Adds the.
	 *
	 * @param light the light
	 */
	public void add(final Light light){
		this.lightList.add(light);
		this.isDirty = true;
		//this.calculateLightMap(light.getPos().getZ());
	}

	/**
	 * Register listeners.
	 */
	public void registerListeners() {
		VictusLudus.e.eventHandler.renderHandler.registerPlease(this);
	}

	/**
	 * Unregister listeners.
	 */
	public void unregisterListeners() {
		VictusLudus.e.eventHandler.renderHandler.unregisterPlease(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() {
		this.unregisterListeners();
	}

	/**
	 * Checks if is dirty.
	 *
	 * @return true, if is dirty
	 */
	public boolean isDirty() {
		return this.isDirty;
	}

	/**
	 * Sets the dirty.
	 *
	 * @param isDirty the new dirty
	 */
	public void setDirty(final boolean isDirty) {
		this.isDirty = isDirty;
	}

	@Override
	public void onRenderChangeEvent(final RenderEvent renderEvent) {
		if(renderEvent.getEventType() == EnumRenderEventType.CHANGE_DEPTH){
			this.calculateLightMap(VictusLudus.e.currentGame.getCurrentDepth());
		}
	}
}
