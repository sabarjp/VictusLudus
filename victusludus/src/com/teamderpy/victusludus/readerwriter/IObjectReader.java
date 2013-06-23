
package com.teamderpy.victusludus.readerwriter;

import java.util.Map;

import com.badlogic.gdx.files.FileHandle;

/** The Interface IObjectReader. */
public interface IObjectReader {

	/** Read and load.
	 * 
	 * @param <T> the generic type
	 * @param f the path
	 * @param hash the hash */
	public <T> void ReadAndLoad (FileHandle f, Map<String, T> hash);
}
