package com.teamderpy.victusludus.game.cosmos;

public enum EnumStarType {
	DUST(0, "Dust Cloud"),
	BROWN_DWARF(1, "Brown Dwarf"),
	DEAD_BROWN_DWARF(2, "Dead Brown Dwarf"),
	PROTOSTAR(3, "Proto-star"),
	MAIN_SEQUENCE(4, "Main Sequence"),
	RED_DWARF(5, "Red Dwarf"),
	BLUE_DWARF(6, "Blue Dwarf"),
	WHITE_DWARF(7, "White Dwarf"),
	HELIUM_WHITE_DWARF(8, "Helium White Dwarf"),
	BLACK_DWARF(9, "Black Dwarf"),
	SUB_GIANT(10, "Sub-Giant"),
	GIANT(11, "Giant"),
	BRIGHT_GIANT(12, "Bright Giant"),
	SUPER_GIANT(13, "Super Giant"),
	HYPER_GIANT(14, "Hyper Giant"),
	WOLF_RAYET_STAR(15, "Wolf-Rayet Star"),
	PULSAR(16, "Pulsar"),
	NEUTRON_STAR(17, "Neutron Star"),
	BLACK_HOLE(18, "Black Hole"),
	PLANETARY_NEBULA(19, "Planetary Nebula"),
	SUPER_NOVA_TYPE_Ib(20, "Super Nova type Ib"),
	SUPER_NOVA_TYPE_Ic(21, "Super Nova type Ic"),
	SUPER_NOVA_TYPE_IIP(22, "Super Nova type IIP"),
	SUPER_NOVA_TYPE_IIL(23, "Super Nova type IIL");

	private int spriteIndex;
	private String properName;

	EnumStarType(final int spriteIndex, String properName){
		this.spriteIndex = spriteIndex;
		this.properName = properName;
	}

	/**
	 * Returns the sprite index
	 * 
	 * @return the sprite index number of the star
	 */
	public int getSpriteIndex() {
		return this.spriteIndex;
	}

	public String getProperName() {
		return properName;
	}
}
