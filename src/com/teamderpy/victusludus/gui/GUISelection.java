package com.teamderpy.victusludus.gui;

// TODO: Auto-generated Javadoc
/**
 * The Class GUISelection.
 */
public class GUISelection {
	
	/** The text. */
	private String text;
	
	/** The value. */
	private String value;
	
	/** The tooltip. */
	private String tooltip;
	
	/** The width. */
	private int width = -1;
	
	/** The height. */
	private int height = -1;
	
	/**
	 * Instantiates a new gUI selection.
	 *
	 * @param text the text
	 * @param value the value
	 */
	public GUISelection(String text, String value){
		this.setText(text);
		this.setValue(value);
		this.setTooltip("");
	}
	
	/**
	 * Instantiates a new gUI selection.
	 *
	 * @param text the text
	 * @param value the value
	 * @param tooltip the tooltip
	 */
	public GUISelection(String text, String value, String tooltip){
		this.setText(text);
		this.setValue(value);
		this.setTooltip(tooltip);
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the tooltip.
	 *
	 * @return the tooltip
	 */
	public String getTooltip() {
		return tooltip;
	}

	/**
	 * Sets the tooltip.
	 *
	 * @param tooltip the new tooltip
	 */
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width.
	 *
	 * @param width the new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height.
	 *
	 * @param height the new height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
}
