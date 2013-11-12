
package com.teamderpy.victusludus.game.cosmos;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.OrderedMap;

@Deprecated
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
	ASTEROID_FIELD(false, "planet/asteroids", "Asteroid Field"),
	TOXIC(false, "planet/toxic", "Toxic Oceans"),
	ARTIFICIAL(false, "planet/artificial", "Artificial World");

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
		case ASTEROID_FIELD:
			tintMap.put(1.00F, new Color(0.24F, 0.23F, 0.21F, 1));
			tintMap.put(0.80F, new Color(0.21F, 0.19F, 0.16F, 1));
			tintMap.put(0.55F, new Color(0.4F, 0.4F, 0.4F, 1));
			tintMap.put(0.50F, new Color(0.0F, 0.0F, 0.0F, 1));
			tintMap.put(0.00F, new Color(0.0F, 0.0F, 0.0F, 1));
			break;
		case BARREN:
			tintMap.put(1.00F, new Color(0.93F, 0.88F, 0.79F, 1));
			tintMap.put(0.75F, new Color(0.82F, 0.74F, 0.56F, 1));
			tintMap.put(0.50F, new Color(0.68F, 0.52F, 0.41F, 1));
			tintMap.put(0.25F, new Color(0.41F, 0.33F, 0.29F, 1));
			tintMap.put(0.05F, new Color(0.26F, 0.21F, 0.18F, 1));
			tintMap.put(0.00F, new Color(0.26F, 0.21F, 0.18F, 1));
			break;
		case CARBON:
			tintMap.put(1.00F, new Color(0.05F, 0.05F, 0.05F, 1));
			tintMap.put(0.75F, new Color(0.19F, 0.19F, 0.19F, 1));
			tintMap.put(0.50F, new Color(0.09F, 0.09F, 0.09F, 1));
			tintMap.put(0.25F, new Color(0.14F, 0.14F, 0.14F, 1));
			tintMap.put(0.20F, new Color(0.02F, 0.02F, 0.02F, 1));
			tintMap.put(0.00F, new Color(0.02F, 0.02F, 0.02F, 1));
			break;
		case GAIA:
			tintMap.put(1.00F, Color.WHITE);
			tintMap.put(0.92F, Color.GRAY);
			tintMap.put(0.86F, new Color(0.42F, 0.35F, 0.22F, 1));
			tintMap.put(0.75F, new Color(0, 0.4F, 0, 1));
			tintMap.put(0.53F, new Color(0, 0.6F, 0, 1));
			tintMap.put(0.50F, new Color(0.94F, 0.92F, 0.63F, 1));
			tintMap.put(0.47F, Color.CYAN);
			tintMap.put(0.24F, new Color(0, 0, 0.6F, 1));
			tintMap.put(0.00F, new Color(0, 0, 0.4F, 1));
			break;
		case GAS_GIANT:
			tintMap.put(1.00F, new Color(0.96F, 0.86F, 0.73F, 1));
			tintMap.put(0.90F, new Color(0.87F, 0.58F, 0.18F, 1));
			tintMap.put(0.80F, new Color(0.51F, 0.31F, 0.07F, 1));
			tintMap.put(0.70F, new Color(0.62F, 0.49F, 0.31F, 1));
			tintMap.put(0.60F, new Color(0.96F, 0.86F, 0.73F, 1));
			tintMap.put(0.50F, new Color(0.87F, 0.58F, 0.18F, 1));
			tintMap.put(0.40F, new Color(0.51F, 0.31F, 0.07F, 1));
			tintMap.put(0.30F, new Color(0.62F, 0.49F, 0.31F, 1));
			tintMap.put(0.20F, new Color(0.96F, 0.86F, 0.73F, 1));
			tintMap.put(0.10F, new Color(0.87F, 0.58F, 0.18F, 1));
			tintMap.put(0.00F, new Color(0.51F, 0.31F, 0.07F, 1));
			break;
		case ICE:
			tintMap.put(1.00F, new Color(0.98F, 0.98F, 0.98F, 1));
			tintMap.put(0.75F, new Color(0.74F, 0.77F, 0.79F, 1));
			tintMap.put(0.65F, new Color(0.71F, 0.80F, 0.85F, 1));
			tintMap.put(0.55F, new Color(0.48F, 0.63F, 0.68F, 1));
			tintMap.put(0.50F, new Color(0.48F, 0.63F, 0.68F, 1));
			tintMap.put(0.45F, new Color(0.21F, 0.52F, 0.70F, 1));
			tintMap.put(0.25F, new Color(0.11F, 0.41F, 0.51F, 1));
			tintMap.put(0.05F, new Color(0.15F, 0.47F, 0.66F, 1));
			tintMap.put(0.00F, new Color(0.05F, 0.09F, 0.26F, 1));
			break;
		case ICE_GIANT:
			tintMap.put(1.00F, new Color(1F, 1F, 1F, 1));
			tintMap.put(0.90F, new Color(0.20F, 0.41F, 0.69F, 1));
			tintMap.put(0.45F, new Color(0.03F, 0.28F, 0.61F, 1));
			tintMap.put(0.00F, new Color(0.09F, 0.22F, 0.40F, 1));
			break;
		case METAL:
			tintMap.put(1.00F, new Color(0.68F, 0.71F, 0.73F, 1));
			tintMap.put(0.50F, new Color(0.54F, 0.55F, 0.56F, 1));
			tintMap.put(0.25F, new Color(0.42F, 0.39F, 0.38F, 1));
			tintMap.put(0.00F, new Color(0.16F, 0.13F, 0.07F, 1));
			break;
		case MOLTEN:
			tintMap.put(1.00F, new Color(0.31F, 0.26F, 0.54F, 1));
			tintMap.put(0.75F, new Color(0.16F, 0.12F, 0.09F, 1));
			tintMap.put(0.65F, new Color(0.91F, 0.14F, 0.04F, 1));
			tintMap.put(0.50F, new Color(1.0F, 0.4F, 0.0F, 1));
			tintMap.put(0.25F, new Color(1.0F, 0.7F, 0.0F, 1));
			tintMap.put(0.05F, new Color(1.0F, 0.93F, 0.32F, 1));
			tintMap.put(0.00F, new Color(1.0F, 0.93F, 0.32F, 1));
			break;
		case OCEAN:
			tintMap.put(1.00F, Color.WHITE);
			tintMap.put(0.92F, Color.CYAN);
			tintMap.put(0.80F, new Color(0, 0, 0.6F, 1));
			tintMap.put(0.50F, new Color(0, 0, 0.4F, 1));
			tintMap.put(0.00F, new Color(0, 0, 0.2F, 1));
			break;
		case ROCKY:
			tintMap.put(1.00F, Color.WHITE);
			tintMap.put(0.75F, new Color(0.46F, 0.44F, 0.41F, 1));
			tintMap.put(0.50F, new Color(0.37F, 0.34F, 0.28F, 1));
			tintMap.put(0.25F, new Color(0.33F, 0.33F, 0.31F, 1));
			tintMap.put(0.00F, new Color(0.18F, 0.17F, 0.15F, 1));
			break;
		case TOXIC:
			tintMap.put(1.00F, new Color(0.5F, 1.0F, 0.3F, 1));
			tintMap.put(0.80F, new Color(0.5F, 0.8F, 0.3F, 1));
			tintMap.put(0.00F, new Color(0.2F, 0.4F, 0.3F, 1));
			break;
		case ARTIFICIAL:
			tintMap.put(1.00F, Color.WHITE);
			tintMap.put(0.92F, Color.GRAY);
			tintMap.put(0.86F, new Color(0.42F, 0.35F, 0.22F, 1));
			tintMap.put(0.75F, new Color(0.09F, 0.44F, 0.09F, 1));
			tintMap.put(0.44F, new Color(0.09F, 0.64F, 0.09F, 1));
			tintMap.put(0.40F, new Color(0.94F, 0.92F, 0.63F, 1));
			tintMap.put(0.36F, new Color(0.13F, 0.74F, 0.81F, 1));
			tintMap.put(0.24F, new Color(0, 0.50F, 0.60F, 1));
			tintMap.put(0.00F, new Color(0, 0.34F, 0.40F, 1));
			break;
		default:
			tintMap.put(1.00F, new Color(1F, 0F, 1F, 1));
			tintMap.put(0.00F, new Color(1F, 0F, 1F, 1));
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
