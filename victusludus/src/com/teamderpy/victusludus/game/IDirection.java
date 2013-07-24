
package com.teamderpy.victusludus.game;

public interface IDirection {
	/**
	 * Gets the direction
	 * 
	 * @return The direction of this object
	 */
	public EnumDirection getDirection ();

	/**
	 * Sets the direction of this object
	 * @param direction an EnumDirection
	 */
	public void setDirection (EnumDirection direction);

	public enum EnumDirection {
		NORTH(270),
		NORTHEAST(315),
		EAST(0),
		SOUTHEAST(45),
		SOUTH(90),
		SOUTHWEST(135),
		WEST(180),
		NORTHWEST(225);

		private float degreesRotation;

		EnumDirection (final float degreesRotation) {
			this.degreesRotation = degreesRotation;
		}

		public float getDegreesRotation () {
			return this.degreesRotation;
		}
	}
}
