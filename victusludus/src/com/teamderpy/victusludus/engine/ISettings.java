
package com.teamderpy.victusludus.engine;

public interface ISettings {
	/**
	 * Gets a value for a setting
	 * @param setting the setting to look for
	 * @return a value that goes with the setting, returned as a string
	 */
	public String getValue (String setting);

	/**
	 * Gets a value for a setting
	 * @param setting the setting to look for
	 * @return a value that goes with the setting, returned as a string
	 */
	public String getString (String setting);

	/**
	 * Gets a value for a setting
	 * @param setting the setting to look for
	 * @return a value that goes with the setting, returned as a float
	 */
	public float getFloat (String setting);

	/**
	 * Gets a value for a setting
	 * @param setting the setting to look for
	 * @return a value that goes with the setting, returned as a double
	 */
	public double getDouble (String setting);

	/**
	 * Gets a value for a setting
	 * @param setting the setting to look for
	 * @return a value that goes with the setting, returned as an int
	 */
	public int getInt (String setting);

	/**
	 * Gets a value for a setting
	 * @param setting the setting to look for
	 * @return a value that goes with the setting, returned as a boolean
	 */
	public boolean getBoolean (String setting);

	/**
	 * Gets a value for a setting
	 * @param setting the setting to look for
	 * @return a value that goes with the setting, returned as a byte
	 */
	public byte getByte (String setting);

	/**
	 * Gets a value for a setting
	 * @param setting the setting to look for
	 * @return a value that goes with the setting, returned as a long
	 */
	public long getLong (String setting);

	/**
	 * Adds a new setting and a value
	 * @param setting the setting to add
	 * @param value the value that the setting corresponds to
	 */
	public void addValue (String setting, String value);

	/**
	 * Adds a new setting and a value
	 * @param setting the setting to add
	 * @param value the value that the setting corresponds to
	 */
	public void addValue (String setting, float value);

	/**
	 * Adds a new setting and a value
	 * @param setting the setting to add
	 * @param value the value that the setting corresponds to
	 */
	public void addValue (String setting, double value);

	/**
	 * Adds a new setting and a value
	 * @param setting the setting to add
	 * @param value the value that the setting corresponds to
	 */
	public void addValue (String setting, int value);

	/**
	 * Adds a new setting and a value
	 * @param setting the setting to add
	 * @param value the value that the setting corresponds to
	 */
	public void addValue (String setting, boolean value);

	/**
	 * Adds a new setting and a value
	 * @param setting the setting to add
	 * @param value the value that the setting corresponds to
	 */
	public void addValue (String setting, byte value);

	/**
	 * Adds a new setting and a value
	 * @param setting the setting to add
	 * @param value the value that the setting corresponds to
	 */
	public void addValue (String setting, long value);
}
