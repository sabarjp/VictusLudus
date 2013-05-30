package com.teamderpy.victusludus.game.cosmos;

public enum EnumStarType {
	DUST(0),
	BROWN_DWARF(1),
	DEAD_BROWN_DWARF(2),
	PROTOSTAR(3),
	MAIN_SEQUENCE(4),
	RED_DWARF(5),
	BLUE_DWARF(6),
	WHITE_DWARF(7),
	HELIUM_WHITE_DWARF(8),
	BLACK_DWARF(9),
	SUB_GIANT(10),
	GIANT(11),
	BRIGHT_GIANT(12),
	SUPER_GIANT(13),
	HYPER_GIANT(14),
	WOLF_RAYET_STAR(15),
	PULSAR(16),
	NEUTRON_STAR(17),
	BLACK_HOLE(18),
	PLANETARY_NEBULA(19),
	SUPER_NOVA_TYPE_Ib(20),
	SUPER_NOVA_TYPE_Ic(21),
	SUPER_NOVA_TYPE_IIP(22),
	SUPER_NOVA_TYPE_IIL(23);

	private int spriteIndex;

	EnumStarType(final int spriteIndex){
		this.spriteIndex = spriteIndex;
	}

	/**
	 * Returns the sprite index
	 * 
	 * @return the sprite index number of the star
	 */
	public int getSpriteIndex() {
		return this.spriteIndex;
	}
}
