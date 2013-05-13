package com.teamderpy.victusludus.game.starcluster;

public enum EnumPlanetType {
	GAS_GIANT(true),
	ICE_GIANT(true),
	ROCKY(false),
	OCEAN(false),
	ICE(false),
	CARBON(false),
	GAIA(false),
	MOLTEN(false),
	METAL(false),
	BARREN(false),
	ASTEROID_BELT(false);

	private boolean isGasGiant;

	EnumPlanetType(final boolean isGasGiant){
		this.isGasGiant = isGasGiant;
	}

	public boolean isGasGiant() {
		return this.isGasGiant;
	}

	/**
	 * Returns a random planet type
	 * @return a random planet type
	 */
	public static EnumPlanetType randomPlanetType()  {
		return EnumPlanetType.values()[(int)(Math.random()*EnumPlanetType.values().length)];
	}
}
