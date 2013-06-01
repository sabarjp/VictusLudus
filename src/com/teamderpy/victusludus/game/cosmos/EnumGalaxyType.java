package com.teamderpy.victusludus.game.cosmos;

public enum EnumGalaxyType {
	ELLIPTICAL_0(0, "Elliptical Galaxy"),
	ELLIPTICAL_4(1, "Elliptical Galaxy"),
	ELLIPTICAL_7(2, "Elliptical Galaxy"),
	SPIRAL_a(3, "Spiral Galaxy"),
	SPIRAL_b(4, "Spiral Galaxy"),
	SPIRAL_c(5, "Spiral Galaxy"),
	BARRED_SPIRAL_a(6, "Barred Spiral Galaxy"),
	BARRED_SPIRAL_b(7, "Barred Spiral Galaxy"),
	BARRED_SPIRAL_c(8, "Barred Spiral Galaxy"),
	RINGED(9, "Ringed Galaxy");

	private int spriteIndex;
	private String properName;

	EnumGalaxyType(final int spriteIndex, String properName){
		this.spriteIndex = spriteIndex;
		this.properName = properName;
	}

	/**
	 * Returns the sprite index
	 * 
	 * @return the sprite index number of the galaxy
	 */
	public int getSpriteIndex() {
		return this.spriteIndex;
	}

	public String getProperName() {
		return properName;
	}
}
