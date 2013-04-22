package com.teamderpy.victusludus.gui.element;

// TODO: Auto-generated Javadoc
/**
 * The Interface Selectable.
 */
public interface Selectable {
	
	/**
	 * Checks if is selected.
	 *
	 * @return true, if is selected
	 */
	boolean isSelected();
	
	/**
	 * Sets the selected.
	 *
	 * @param isSelected the new selected
	 */
	void setSelected(boolean isSelected);
	
	/**
	 * Checks if is hovered on.
	 *
	 * @return true, if is hovered on
	 */
	boolean isHoveredOn();
	
	/**
	 * Sets the hovered on.
	 *
	 * @param isHoveredOn the new hovered on
	 */
	void setHoveredOn(boolean isHoveredOn);
	
	/**
	 * Gets the tooltip.
	 *
	 * @return the tooltip
	 */
	String getTooltip();
	
	/**
	 * Sets the tooltip.
	 *
	 * @param tooltip the new tooltip
	 */
	void setTooltip(String tooltip);
}
