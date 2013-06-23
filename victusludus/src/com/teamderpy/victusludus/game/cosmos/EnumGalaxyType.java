
package com.teamderpy.victusludus.game.cosmos;

public enum EnumGalaxyType {
	ELLIPTICAL_0("galaxy/galaxy_elliptical_e0", "Elliptical Galaxy"),
	ELLIPTICAL_4("galaxy/galaxy_elliptical_e4", "Elliptical Galaxy"),
	ELLIPTICAL_7("galaxy/galaxy_elliptical_e7", "Elliptical Galaxy"),
	SPIRAL_a("galaxy/galaxy_spiral_sa", "Spiral Galaxy"),
	SPIRAL_b("galaxy/galaxy_spiral_sb", "Spiral Galaxy"),
	SPIRAL_c("galaxy/galaxy_spiral_sc", "Spiral Galaxy"),
	BARRED_SPIRAL_a("galaxy/galaxy_barred_spiral_sba", "Barred Spiral Galaxy"),
	BARRED_SPIRAL_b("galaxy/galaxy_barred_spiral_sbb", "Barred Spiral Galaxy"),
	BARRED_SPIRAL_c("galaxy/galaxy_barred_spiral_sbc", "Barred Spiral Galaxy"),
	RINGED("galaxy/galaxy_ring", "Ringed Galaxy");

	private String path;
	private String properName;

	EnumGalaxyType (final String path, final String properName) {
		this.path = path;
		this.properName = properName;
	}

	/** Returns the sprite image path
	 * 
	 * @return the sprite path */
	public String getPath () {
		return this.path;
	}

	public String getProperName () {
		return this.properName;
	}
}
