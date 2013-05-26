package com.teamderpy.victusludus.game.cosmos;

public enum EnumGalaxyType {
	ELLIPTICAL_0(0),
	ELLIPTICAL_4(1),
	ELLIPTICAL_7(2),
	SPIRAL_a(3),
	SPIRAL_b(4),
	SPIRAL_c(5),
	BARRED_SPIRAL_a(6),
	BARRED_SPIRAL_b(7),
	BARRED_SPIRAL_c(8),
	RINGED(9);

	private int spriteIndex;

	EnumGalaxyType(final int spriteIndex){
		this.spriteIndex = spriteIndex;
	}

	/**
	 * Returns the sprite index
	 * 
	 * @return the sprite index number of the galaxy
	 */
	public int getSpriteIndex() {
		return this.spriteIndex;
	}
}
