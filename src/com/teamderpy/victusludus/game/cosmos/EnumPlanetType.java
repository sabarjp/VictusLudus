package com.teamderpy.victusludus.game.cosmos;

public enum EnumPlanetType {
	GAS_GIANT(true, 0, "Gas Giant"),
	ICE_GIANT(true, 1, "Ice Giant"),
	ROCKY(false, 2, "Rocky"),
	OCEAN(false, 3, "Oceanic"),
	ICE(false, 4, "Frozen"),
	CARBON(false, 5, "Carbon"),
	GAIA(false, 6, "Gaia"),
	MOLTEN(false, 7, "Molten"),
	METAL(false, 8, "Metal"),
	BARREN(false, 9, "Barren"),
	ASTEROID_BELT(false, 10, "Asteroid Field");

	private boolean isGasGiant;
	private int spriteIndex;
	private String properName;

	EnumPlanetType(final boolean isGasGiant, final int spriteIndex, String properName){
		this.isGasGiant = isGasGiant;
		this.spriteIndex = spriteIndex;
		this.properName = properName;
	}

	public boolean isGasGiant() {
		return this.isGasGiant;
	}

	/**
	 * Returns the sprite index
	 * 
	 * @return the sprite index number of the galaxy
	 */
	public int getSpriteIndex() {
		return this.spriteIndex;
	}

	/**
	 * Returns a random planet type
	 * @return a random planet type
	 */
	public static EnumPlanetType randomPlanetType()  {
		return EnumPlanetType.values()[(int)(Math.random()*EnumPlanetType.values().length)];
	}

	public String getProperName() {
		return properName;
	}
}
