
package com.teamderpy.victusludus.readerwriter;

import java.util.ArrayList;

import com.badlogic.gdx.files.FileHandle;

/** The Interface ISimpleReader. */
public interface ISimpleReader {

	/** Read and load.
	 * 
	 * @param f the path
	 * @param hash the hash */
	public void ReadAndLoad (FileHandle f, ArrayList<String> array);
}
