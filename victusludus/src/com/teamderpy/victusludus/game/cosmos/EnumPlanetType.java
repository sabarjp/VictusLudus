
package com.teamderpy.victusludus.game.cosmos;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.OrderedMap;

public enum EnumPlanetType {
	GAS_GIANT(true, "planet/gas_giant", "Gas Giant"),
	ICE_GIANT(true, "planet/ice_giant", "Ice Giant"),
	ROCKY(false, "planet/rocky", "Rocky"),
	OCEAN(false, "planet/oceanic", "Oceanic"),
	ICE(false, "planet/ice", "Frozen"),
	CARBON(false, "planet/carbon", "Carbon"),
	GAIA(false, "planet/gaia", "Gaia"),
	MOLTEN(false, "planet/molten", "Molten"),
	METAL(false, "planet/metal", "Metallic"),
	BARREN(false, "planet/barren", "Barren"),
	ASTEROID_FIELD(false, "planet/asteroid_field", "Asteroid Field"),
	TOXIC(false, "planet/toxic", "Toxic Oceans");

	private boolean isGasGiant;
	private String properName;
	private String path;

	EnumPlanetType (final boolean isGasGiant, final String path, final String properName) {
		this.isGasGiant = isGasGiant;
		this.properName = properName;
		this.path = path;
	}

	public String getPath () {
		return this.path;
	}

	public boolean isGasGiant () {
		return this.isGasGiant;
	}

	public String getProperName () {
		return this.properName;
	}

	/**
	 * Gets a map used to tint the height map for a surface world of a planet
	 * @return
	 */
	public OrderedMap<Float, Color> getTintMap () {
		OrderedMap<Float, Color> tintMap = new OrderedMap<Float, Color>();

		switch (this) {
// case ASTEROID_FIELD:
// break;
// case BARREN:
// break;
// case CARBON:
// break;
// case GAIA:
// tintMap.put(255.0F, Color.WHITE);
// tintMap.put(235.0F, Color.GRAY);
// tintMap.put(220.0F, Color.ORANGE);
// tintMap.put(190.0F, new Color(0, 0.4F, 0, 1));
// tintMap.put(134.0F, new Color(0, 0.6F, 0, 1));
// tintMap.put(128.0F, Color.YELLOW);
// tintMap.put(120.0F, Color.CYAN);
// tintMap.put(60.0F, new Color(0, 0, 0.6F, 1));
// tintMap.put(0.0F, new Color(0, 0, 0.4F, 1));
// break;
// case GAS_GIANT:
// break;
// case ICE:
// break;
// case ICE_GIANT:
// break;
// case METAL:
// break;
// case MOLTEN:
// break;
// case OCEAN:
// break;
// case ROCKY:
// break;
// case TOXIC:
// tintMap.put(255.0F, new Color(0.5F, 1.0F, 0.3F, 1));
// tintMap.put(250.0F, new Color(0.5F, 0.8F, 0.3F, 1));
// tintMap.put(0.0F, new Color(0.2F, 0.4F, 0.3F, 1));
// break;
		default:
			tintMap.put(255.0F, new Color(0.5F, 1.0F, 0.3F, 1));
			tintMap.put(250.0F, new Color(0.5F, 0.8F, 0.3F, 1));
			tintMap.put(0.0F, new Color(0.2F, 0.4F, 0.3F, 1));
			break;
		}

		return tintMap;
	}

	/**
	 * Returns a random planet type
	 * @return a random planet type
	 */
	public static EnumPlanetType randomPlanetType () {
		return EnumPlanetType.values()[(int)(Math.random() * EnumPlanetType.values().length)];
	}
}
