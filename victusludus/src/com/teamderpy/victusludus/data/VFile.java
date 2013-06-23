
package com.teamderpy.victusludus.data;

import java.io.BufferedReader;
import java.io.IOException;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public final class VFile {
	private static final String ASSETS_PATH = "assets/";

	/** Returns a Gdx FileHandle appropriate for the platform
	 * 
	 * @param path the path to the file
	 * @return a file handle for the file */
	public static FileHandle getFileHandle (final String path) {
		switch (Gdx.app.getType()) {
		case Android:
			// android specific code
			return Gdx.files.internal(path);
		case iOS:
			// HTML5 specific code
			return Gdx.files.internal(path);
		case Desktop:
			// desktop specific code
			if (Gdx.files.local(VFile.ASSETS_PATH + path).exists()) {
				return Gdx.files.local(VFile.ASSETS_PATH + path);
			} else {
				if (Gdx.files.classpath(path).exists()) {
					return Gdx.files.classpath(path);
				} else {
					return Gdx.files.local(path);
				}
			}
		case Applet:
			// java applet specific code
			return Gdx.files.classpath(path);
		case WebGL:
			// / HTML5 specific code
			return Gdx.files.internal(path);
		}

		return Gdx.files.internal(path);
	}

	/** Gets the children of a directory, trying to use list files where possible.
	 * 
	 * @param directory a file handle to the directory
	 * @param getFiles whether or not to return files in the list
	 * @param getDirectories whether or not to return directories in the list
	 * @return an array full of file handles of the children of the directory */
	private static FileHandle[] getChildrenOnCondition (final FileHandle directory, final boolean getFiles,
		final boolean getDirectories) {
		FileHandle listFile;

		if (directory.type() == FileType.Classpath) {
			listFile = Gdx.files.classpath(directory.path() + "/.list");

			if (listFile.exists()) {
				return VFile.createFileListFromListFile(listFile, getFiles, getDirectories);
			}
		} else if (directory.type() == FileType.Internal) {
			listFile = Gdx.files.internal(directory.path() + "/.list");

			if (listFile.exists()) {
				return VFile.createFileListFromListFile(listFile, getFiles, getDirectories);
			}
		}

		// fall back to ugly, broken function
		Array<FileHandle> files = new Array<FileHandle>(new FileHandle[0]);

		for (FileHandle file : directory.list()) {
			if (file.isDirectory() && getDirectories) {
				files.add(file);
			} else if (!file.isDirectory() && getFiles) {
				files.add(file);
			}
		}

		return files.toArray();
	}

	/** Gets the children of a directory, trying to use list files where possible.
	 * 
	 * @param directory a file handle to the directory
	 * @return an array full of file handles of the children of the directory */
	public static FileHandle[] getChildren (final FileHandle directory) {
		return VFile.getChildrenOnCondition(directory, true, true);
	}

	/** Gets only files of a directory, trying to use list files where possible.
	 * 
	 * @param directory a file handle to the directory
	 * @return an array full of file handles of the children of the directory */
	public static FileHandle[] getFiles (final FileHandle directory) {
		return VFile.getChildrenOnCondition(directory, true, false);
	}

	/** Gets the sub-dirs of a directory, trying to use list files where possible.
	 * 
	 * @param directory a file handle to the directory
	 * @return an array full of file handles of the children of the directory */
	public static FileHandle[] getSubDirectories (final FileHandle directory) {
		return VFile.getChildrenOnCondition(directory, false, true);
	}

	/** Creates an array list of file handles that a list file points to. A list file lists all the files and folders in the
	 * directory that it resides in.
	 * 
	 * @param listFile the list file which has a file or folder on each line
	 * @param getFiles whether or not to return files in the list
	 * @param getDirectories whether or not to return directories in the list
	 * @return an array of file handles */
	private static FileHandle[] createFileListFromListFile (final FileHandle listFile, final boolean getFiles,
		final boolean getDirectories) {
		Array<FileHandle> files = new Array<FileHandle>(new FileHandle[0]);
		BufferedReader listFileReader = listFile.reader(256);
		String line;

		// add files to file list
		try {
			while ((line = listFileReader.readLine()) != null) {
				if (line.endsWith("/") && getDirectories) {
					files.add(listFile.sibling(line));
				} else if (!line.endsWith("/") && getFiles) {
					files.add(listFile.sibling(line));
				}
			}

			listFileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return files.toArray();
	}
}
