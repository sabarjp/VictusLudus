
package com.teamderpy.victusludus.game.cosmos;

@Deprecated
public enum EnumStarType {
	DUST("star/star_dust", "Dust Cloud"),
	BROWN_DWARF("star/star_brown_dwarf", "Brown Dwarf"),
	DEAD_BROWN_DWARF("star/star_brown_dwarf_dead", "Dead Brown Dwarf"),
	PROTOSTAR("star/star_proto_star", "Proto-star"),
	MAIN_SEQUENCE("star/star_main_sequence", "Main Sequence"),
	RED_DWARF("star/star_red_dwarf", "Red Dwarf"),
	BLUE_DWARF("star/star_blue_dwarf", "Blue Dwarf"),
	WHITE_DWARF("star/star_white_dwarf", "White Dwarf"),
	HELIUM_WHITE_DWARF("star/star_helium_white_dwarf", "Helium White Dwarf"),
	BLACK_DWARF("star/star_black_dwarf", "Black Dwarf"),
	SUB_GIANT("star/star_sub_giant", "Sub-Giant"),
	GIANT("star/star_giant", "Giant"),
	BRIGHT_GIANT("star/star_bright_giant", "Bright Giant"),
	SUPER_GIANT("star/star_super_giant", "Super Giant"),
	HYPER_GIANT("star/star_hyper_giant", "Hyper Giant"),
	WOLF_RAYET_STAR("star/star_wolf_rayet", "Wolf-Rayet Star"),
	PULSAR("star/star_pulsar", "Pulsar"),
	NEUTRON_STAR("star/star_neutron", "Neutron Star"),
	BLACK_HOLE("star/star_black_hole", "Black Hole"),
	PLANETARY_NEBULA("star/star_planetary_nebula", "Planetary Nebula"),
	SUPER_NOVA_TYPE_Ib("star/star_supernova_ib", "Super Nova type Ib"),
	SUPER_NOVA_TYPE_Ic("star/star_supernova_ic", "Super Nova type Ic"),
	SUPER_NOVA_TYPE_IIP("star/star_supernova_iip", "Super Nova type IIP"),
	SUPER_NOVA_TYPE_IIL("star/star_supernova_iil", "Super Nova type IIL");

	private String path;
	private String properName;

	EnumStarType (final String path, final String properName) {
		this.path = path;
		this.properName = properName;
	}

	/**
	 * Returns the sprite path
	 * 
	 * @return the sprite path
	 */
	public String getPath () {
		return this.path;
	}

	public String getProperName () {
		return this.properName;
	}
}
