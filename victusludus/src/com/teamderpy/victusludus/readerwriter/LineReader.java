package com.teamderpy.victusludus.readerwriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.teamderpy.victusludus.VictusLudusGame;

public class LineReader implements ISimpleReader{

	@Override
	public void ReadAndLoad(final String path, final ArrayList<String> array) {
		final File f = new File(path);

		if (!f.exists() || !f.isFile()) {
			Gdx.app.log("severe", "ERROR: File does not exist [" + path + "]");
			return;
		}

		try {
			FileReader fr = new FileReader(path);
			Scanner s = new Scanner(fr);

			while (s.hasNextLine()) {
				array.add(s.nextLine());
			}

			s.close();
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
