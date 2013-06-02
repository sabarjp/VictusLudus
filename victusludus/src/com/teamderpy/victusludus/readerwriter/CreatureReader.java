package com.teamderpy.victusludus.readerwriter;

import java.util.Map;

import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.parts.BodyPart;
import com.teamderpy.victusludus.parts.Creature;



/**
 * The Class CreatureReader.
 */
public class CreatureReader implements IObjectReader {
	
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.readerwriter.IObjectReader#ReadAndLoad(java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public <T> void ReadAndLoad(String path, Map<String, T> hash) {
		JLDLSerialReader r = new JLDLSerialReader(path);

		ReadData rd;
		Creature c = null;
		BodyPart p = null;

		while ((rd = r.getNext()) != null) {
			boolean enteredData = false;
			if (rd.getNode().equalsIgnoreCase(JLDLSerialReader.BASE_LEVEL_NODE)) {
				if (rd.getId().equalsIgnoreCase("creature")) {
					c = new Creature();
					c.setId(rd.getValue());
					hash.put(c.getId(), (T) c);

					enteredData = true;
				}
			} else if (rd.getNode().equalsIgnoreCase("creature")) {
				if (rd.getId().equalsIgnoreCase("name")) {
					c.setName(rd.getValue());
					enteredData = true;
				}

				else if (rd.getId().equalsIgnoreCase("description")) {
					c.setDescription(rd.getValue());
					enteredData = true;
				}

				else if (rd.getId().equalsIgnoreCase("body")) {
					// do nothing
					enteredData = true;
				}
			} else if (rd.getNode().equalsIgnoreCase("body")) {
				if (rd.getId().equalsIgnoreCase("part")) {
					p = new BodyPart();
					p.setId(rd.getValue());
					c.getBodyParts().add(p);
					// lastItem.setObj(p);

					enteredData = true;
				}
			} else if (rd.getNode().equalsIgnoreCase("part")) {
				if (rd.getId().equalsIgnoreCase("description")) {
					p.setDescription(rd.getValue());

					enteredData = true;
				} else if (rd.getId().equalsIgnoreCase("name")) {
					p.setName(rd.getValue());

					enteredData = true;
				} else if (rd.getId().equalsIgnoreCase("material")) {
					p.setMaterial(VictusLudus.resources.getMaterialHash().get(rd.getValue()));

					enteredData = true;
				} else if (rd.getId().equalsIgnoreCase("flag") || rd.getId().equalsIgnoreCase("flags")) {
					String[] farray = rd.getValue().replaceAll(" ", "").split(",");

					if (farray.length == 0) {
						farray = new String[] { rd.getValue() };
					}

					for (String i : farray) {
						if (i.equalsIgnoreCase("vital")) {
							p.setVital(true);
						} else if (i.equalsIgnoreCase("cognition")) {
							p.setProvidesCognition(true);
						}
					}

					enteredData = true;
				} else if (rd.getId().equalsIgnoreCase("part")) {
					BodyPart old = p;
					p = new BodyPart();
					p.setId(rd.getValue());
					old.getAttachedParts().add(p);

					enteredData = true;
				}
			}

			if (!enteredData) {
				VictusLudus.LOGGER.warning("ERROR: creature in " + path + " on line " + r.getLineNumber()
						+ ": bad indentation or unknown keyword '" + rd.getId() + "'");
			}
		}

		r.close();
	}
}
