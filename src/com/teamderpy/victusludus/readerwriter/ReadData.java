package com.teamderpy.victusludus.readerwriter;

// TODO: Auto-generated Javadoc
/**
 * The Class ReadData.
 */
public class ReadData {
	
	/** The node. */
	private String node;
	
	/** The id. */
	private String id;
	
	/** The value. */
	private String value;
	
	/**
	 * Instantiates a new read data.
	 *
	 * @param node the node
	 * @param id the id
	 * @param value the value
	 */
	public ReadData(String node, String id, String value){
		this.node = node;
		this.id = id;
		this.value = value;
	}
	
	/**
	 * Gets the node.
	 *
	 * @return the node
	 */
	public String getNode() {
		return node;
	}
	
	/**
	 * Sets the node.
	 *
	 * @param node the new node
	 */
	public void setNode(String node) {
		this.node = node;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
