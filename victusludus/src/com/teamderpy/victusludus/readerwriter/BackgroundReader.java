package com.teamderpy.victusludus.readerwriter;

import java.util.Map;

import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.game.PlayerBackground;


/**
 * The Class BackgroundReader.
 */
public class BackgroundReader implements IObjectReader {
	
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.readerwriter.IObjectReader#ReadAndLoad(java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public <T> void ReadAndLoad(String path, Map<String, T> hash) {
		JLDLSerialReader r = new JLDLSerialReader(path);

		ReadData rd;
		PlayerBackground bg = null;

		while ((rd = r.getNext()) != null) {
			boolean enteredData = false;
			if (rd.getNode().equalsIgnoreCase(JLDLSerialReader.BASE_LEVEL_NODE)) {
				if (rd.getId().equalsIgnoreCase("background")) {
					bg = new PlayerBackground();
					bg.setId(rd.getValue());
					hash.put(bg.getId(), (T) bg);

					enteredData = true;
				}
			} else if (rd.getNode().equalsIgnoreCase("background")) {
				if (rd.getId().equalsIgnoreCase("name")) {
					bg.setName(rd.getValue());
					enteredData = true;
				}

				else if (rd.getId().equalsIgnoreCase("description")) {
					bg.setDescription(rd.getValue());
					enteredData = true;
				}

				else if (rd.getId().equalsIgnoreCase("degree")) {
					bg.setDegree(rd.getValue());
					enteredData = true;
				}

				else if (rd.getId().equalsIgnoreCase("respect")) {
					bg.setRespect(Integer.parseInt(rd.getValue()));
					enteredData = true;
				}

				else if (rd.getId().equalsIgnoreCase("capital")) {
					bg.setCapital(Integer.parseInt(rd.getValue()));
					enteredData = true;
				}

				else if (rd.getId().equalsIgnoreCase("credit")) {
					bg.setCreditRating(rd.getValue());
					enteredData = true;
				}
			}

			if (!enteredData) {
				VictusLudus.LOGGER.warning("ERROR: background in " + path + " on line " + r.getLineNumber()
						+ ": bad indentation or unknown keyword '" + rd.getId() + "'");
			}
		}

		r.close();
	}
}
