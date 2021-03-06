
package com.teamderpy.victusludus.readerwriter;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.teamderpy.victusludus.data.DataLoader;
import com.teamderpy.victusludus.data.resources.FontFile;

/** The Class FontReader. */
public class FontReader implements IObjectReader {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teamderpy.victusludus.readerwriter.IObjectReader#ReadAndLoad(java.lang.String, java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> void ReadAndLoad (final FileHandle f, final Map<String, T> hash) {
		JLDLSerialReader r = new JLDLSerialReader(f);

		ReadData rd;
		FontFile fnt = null;

		while ((rd = r.getNext()) != null) {
			boolean enteredData = false;

			if (rd.getNode().equalsIgnoreCase(JLDLSerialReader.BASE_LEVEL_NODE)) {
				if (rd.getId().equalsIgnoreCase("font")) {
					fnt = new FontFile();
					fnt.setId(rd.getValue());
					hash.put(fnt.getId(), (T)fnt);

					enteredData = true;
				}
			} else if (rd.getNode().equalsIgnoreCase("font")) {
				if (rd.getId().equalsIgnoreCase("name")) {
					fnt.setName(rd.getValue());
					enteredData = true;
				}

				else if (rd.getId().equalsIgnoreCase("file")) {
					fnt.setPath(DataLoader.PATH_FONTS + rd.getValue());
					enteredData = true;
				}

				else if (rd.getId().equalsIgnoreCase("small_size")) {
					fnt.setSmallSize(Integer.parseInt(rd.getValue()));
					enteredData = true;
				}

				else if (rd.getId().equalsIgnoreCase("default_size")) {
					fnt.setDefaultSize(Integer.parseInt(rd.getValue()));
					enteredData = true;
				}

				else if (rd.getId().equalsIgnoreCase("large_size")) {
					fnt.setLargeSize(Integer.parseInt(rd.getValue()));
					enteredData = true;
				}
			}

			if (!enteredData) {
				Gdx.app.log("warning", "<ERROR> font in " + f.path() + " on line " + r.getLineNumber()
					+ ": bad indentation or unknown keyword '" + rd.getId() + "'");
			}
		}

		r.close();
	}
}
