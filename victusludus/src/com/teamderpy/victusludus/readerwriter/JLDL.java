package com.teamderpy.victusludus.readerwriter;


/**
 * The Class JLDL.
 */
public class JLDL {
	
	/** The node seperator. */
	public final String NODE_SEPERATOR = ":";
	
	/** The name. */
	private String name;
	
	/** The property. */
	private String property;
	
	/** The indent. */
	private int indent;
	
	/**
	 * Instantiates a new jldl.
	 *
	 * @param name the name
	 * @param property the property
	 * @param indent the indent
	 */
	public JLDL(String name, String property, int indent){
		this.name = name;
		this.property = property;
		this.indent = indent;
	}
	
	/**
	 * Instantiates a new jldl.
	 *
	 * @param name the name
	 * @param indent the indent
	 */
	public JLDL(String name, int indent){
		this.name = name;
		this.property = null;
		this.indent = indent;
	}
	
	/**
	 * Instantiates a new jldl.
	 *
	 * @param name the name
	 * @param property the property
	 */
	public JLDL(String name, String property){
		this.name = name;
		this.property = property;
		this.indent = -1;
	}
	
	/**
	 * Instantiates a new jldl.
	 *
	 * @param name the name
	 */
	public JLDL(String name){
		this.name = name;
		this.property = null;
		this.indent = -1;
	}

	/**
	 * Gets the indent.
	 *
	 * @return the indent
	 */
	public int getIndent() {
		return indent;
	}

	/**
	 * Sets the indent.
	 *
	 * @param indent the new indent
	 */
	public void setIndent(int indent) {
		this.indent = indent;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the property.
	 *
	 * @return the property
	 */
	public String getProperty() {
		return this.property;
	}

	/**
	 * Sets the property.
	 *
	 * @param property the new property
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		if(this.property == null)
			return this.name + this.NODE_SEPERATOR;
		
		return this.name + this.NODE_SEPERATOR + this.property;
	}
}
