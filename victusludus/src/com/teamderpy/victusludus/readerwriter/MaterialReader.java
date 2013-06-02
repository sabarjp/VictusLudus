package com.teamderpy.victusludus.readerwriter;

import java.util.Map;

import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.parts.Material;




/**
 * The Class MaterialReader.
 */
public class MaterialReader implements IObjectReader {

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.readerwriter.IObjectReader#ReadAndLoad(java.lang.String, java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> void ReadAndLoad(final String path, final Map<String, T> hash) {
		JLDLSerialReader r = new JLDLSerialReader(path);

		ReadData rd;
		Material m = null;

		while ((rd = r.getNext()) != null) {
			boolean enteredData = false;

			if (rd.getNode().equalsIgnoreCase(JLDLSerialReader.BASE_LEVEL_NODE)) {
				if (rd.getId().equalsIgnoreCase("material")) {
					m = new Material();
					m.setId(rd.getValue());
					hash.put(m.getId(), (T) m);

					enteredData = true;
				}
			} else if (rd.getNode().equalsIgnoreCase("material")) {
				if (rd.getId().equalsIgnoreCase("name")) {
					m.setName(rd.getValue());
					enteredData = true;
				}

				else if (rd.getId().equalsIgnoreCase("description")) {
					m.setDescription(rd.getValue());
					enteredData = true;
				}

				else if (rd.getId().equalsIgnoreCase("density")) {
					m.setDensity(Integer.parseInt(rd.getValue()));
					enteredData = true;
				}

				else if (rd.getId().equalsIgnoreCase("mass")) {
					m.setMass(Integer.parseInt(rd.getValue()));
					enteredData = true;
				}

				else if (rd.getId().equalsIgnoreCase("hardness")) {
					m.setHardness(Integer.parseInt(rd.getValue()));
					enteredData = true;
				}

				else if (rd.getId().equalsIgnoreCase("elasticity")) {
					m.setElasticity(Integer.parseInt(rd.getValue()));
					enteredData = true;
				}

				else if (rd.getId().equalsIgnoreCase("rigidity")) {
					m.setRigidity(Integer.parseInt(rd.getValue()));
					enteredData = true;
				}

				else if (rd.getId().equalsIgnoreCase("compressibility")) {
					m.setCompressibility(Integer.parseInt(rd.getValue()));
					enteredData = true;
				}

				else if (rd.getId().equalsIgnoreCase("toughness")) {
					m.setToughness(Integer.parseInt(rd.getValue()));
					enteredData = true;
				}

				else if (rd.getId().equalsIgnoreCase("melting_point")) {
					m.setMeltingPoint(Integer.parseInt(rd.getValue()));
					enteredData = true;
				}

				else if (rd.getId().equalsIgnoreCase("boiling_point")) {
					m.setBoilingPoint(Integer.parseInt(rd.getValue()));
					enteredData = true;
				}
			}

			if (!enteredData) {
				VictusLudus.LOGGER.warning("ERROR: material in " + path + " on line " + r.getLineNumber()
						+ ": bad indentation or unknown keyword '" + rd.getId() + "'");
			}
		}

		r.close();
	}
}
