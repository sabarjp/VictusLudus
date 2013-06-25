
package com.teamderpy.victusludus.game.cosmos;

public enum EnumPlanetType {
	GAS_GIANT(true, "planet/gas_giant", "Gas Giant"),
	ICE_GIANT(true, "planet/ice_giant", "Ice Giant"),
	ROCKY(false, "planet/rocky", "Rocky"),
	OCEAN(false, "planet/oceanic", "Oceanic"),
	ICE(false, "planet/ice", "Frozen"),
	CARBON(false, "planet/carbon", "Carbon"),
	GAIA(false, "planet/gaia", "Gaia"),
	MOLTEN(false, "planet/molten", "Molten"),
	METAL(false, "planet/metal", "Metal"),
	BARREN(false, "planet/barren", "Barren"),
	ASTEROID_FIELD(false, "planet/asteroid_field", "Asteroid Field"),
	TOXIC(false, "planet/toxic", "Toxic");

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

	/**
	 * Returns a random planet type
	 * @return a random planet type
	 */
	public static EnumPlanetType randomPlanetType () {
		return EnumPlanetType.values()[(int)(Math.random() * EnumPlanetType.values().length)];
	}

	public String getProperName () {
		return this.properName;
	}
}
