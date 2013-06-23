
package com.teamderpy.victusludus.readerwriter;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.files.FileHandle;

public class LineReader implements ISimpleReader {

	@Override
	public void ReadAndLoad (final FileHandle f, final ArrayList<String> array) {
		Reader fr = f.reader(256);
		Scanner s = new Scanner(fr);

		while (s.hasNextLine()) {
			array.add(s.nextLine());
		}

		s.close();
	}
}
