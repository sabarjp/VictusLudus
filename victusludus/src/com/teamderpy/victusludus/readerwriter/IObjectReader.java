package com.teamderpy.victusludus.readerwriter;

import java.util.Map;


/**
 * The Interface IObjectReader.
 */
public interface IObjectReader {
	
	/**
	 * Read and load.
	 *
	 * @param <T> the generic type
	 * @param path the path
	 * @param hash the hash
	 */
	public <T> void ReadAndLoad(String path, Map<String, T> hash);
}
