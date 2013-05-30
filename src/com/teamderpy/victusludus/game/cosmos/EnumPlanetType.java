package com.teamderpy.victusludus.game.cosmos;

public enum EnumPlanetType {
	GAS_GIANT(true, 0),
	ICE_GIANT(true, 1),
	ROCKY(false, 2),
	OCEAN(false, 3),
	ICE(false, 4),
	CARBON(false, 5),
	GAIA(false, 6),
	MOLTEN(false, 7),
	METAL(false, 8),
	BARREN(false, 9),
	ASTEROID_BELT(false, 10);

	private boolean isGasGiant;
	private int spriteIndex;

	EnumPlanetType(final boolean isGasGiant, final int spriteIndex){
		this.isGasGiant = isGasGiant;
		this.spriteIndex = spriteIndex;
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
}
