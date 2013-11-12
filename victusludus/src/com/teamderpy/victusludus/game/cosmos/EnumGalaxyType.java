
package com.teamderpy.victusludus.game.cosmos;

@Deprecated
public enum EnumGalaxyType {
	ELLIPTICAL_0("galaxy/galaxy_elliptical_e0", "Elliptical Galaxy", false),
	ELLIPTICAL_4("galaxy/galaxy_elliptical_e4", "Elliptical Galaxy", false),
	ELLIPTICAL_7("galaxy/galaxy_elliptical_e7", "Elliptical Galaxy", false),
	SPIRAL_a("galaxy/galaxy_spiral_sa", "Spiral Galaxy", true),
	SPIRAL_b("galaxy/galaxy_spiral_sb", "Spiral Galaxy", true),
	SPIRAL_c("galaxy/galaxy_spiral_sc", "Spiral Galaxy", true),
	BARRED_SPIRAL_a("galaxy/galaxy_barred_spiral_sba", "Barred Spiral Galaxy", true),
	BARRED_SPIRAL_b("galaxy/galaxy_barred_spiral_sbb", "Barred Spiral Galaxy", true),
	BARRED_SPIRAL_c("galaxy/galaxy_barred_spiral_sbc", "Barred Spiral Galaxy", true),
	RINGED("galaxy/galaxy_ring", "Ringed Galaxy", true);

	private String path;
	private String properName;
	private boolean isGalacticNursery; // determines if new stars are born

	EnumGalaxyType (final String path, final String properName, final boolean isGalacticNursery) {
		this.path = path;
		this.properName = properName;
		this.isGalacticNursery = isGalacticNursery;
	}

	/**
	 * Returns the sprite image path
	 * 
	 * @return the sprite path
	 */
	public String getPath () {
		return this.path;
	}

	public String getProperName () {
		return this.properName;
	}

	public boolean isGalacticNursery () {
		return this.isGalacticNursery;
	}
}
