
package com.teamderpy.victusludus.language;

/**
 * Generates a random word from a language
 * @author Josh
 * 
 */
public interface IWordGenerator {
	public String getWord ();

	public String getWord (long seed);
}
