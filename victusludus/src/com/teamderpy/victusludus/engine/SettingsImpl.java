
package com.teamderpy.victusludus.engine;

import com.badlogic.gdx.utils.ArrayMap;

public class SettingsImpl implements ISettings {
	private ArrayMap<String, String> settings;

	public SettingsImpl () {
		this.settings = new ArrayMap<String, String>();
	}

	@Override
	public void addValue (final String setting, final String value) {
		this.settings.put(setting, value);
	}

	@Override
	public void addValue (final String setting, final float value) {
		this.settings.put(setting, Float.toString(value));
	}

	@Override
	public void addValue (final String setting, final double value) {
		this.settings.put(setting, Double.toString(value));
	}

	@Override
	public void addValue (final String setting, final int value) {
		this.settings.put(setting, Integer.toString(value));
	}

	@Override
	public void addValue (final String setting, final boolean value) {
		this.settings.put(setting, Boolean.toString(value));
	}

	@Override
	public void addValue (final String setting, final byte value) {
		this.settings.put(setting, Byte.toString(value));
	}

	@Override
	public void addValue (final String setting, final long value) {
		this.settings.put(setting, Long.toString(value));
	}

	@Override
	public String getValue (final String setting) {
		return this.settings.get(setting);
	}

	@Override
	public String getString (final String setting) {
		return this.settings.get(setting);
	}

	@Override
	public float getFloat (final String setting) {
		return Float.parseFloat(this.settings.get(setting));
	}

	@Override
	public double getDouble (final String setting) {
		return Double.parseDouble(this.settings.get(setting));
	}

	@Override
	public int getInt (final String setting) {
		return Integer.parseInt(this.settings.get(setting));
	}

	@Override
	public boolean getBoolean (final String setting) {
		return Boolean.parseBoolean(this.settings.get(setting));
	}

	@Override
	public byte getByte (final String setting) {
		return Byte.parseByte(this.settings.get(setting));
	}

	@Override
	public long getLong (final String setting) {
		return Long.parseLong(this.settings.get(setting));
	}
}
