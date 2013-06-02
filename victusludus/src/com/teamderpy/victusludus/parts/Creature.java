package com.teamderpy.victusludus.parts;

import java.util.ArrayList;

import com.teamderpy.victusludus.game.ScreenCoord;




/**
 * The Class Creature.
 */
public class Creature {
	
	/** The coord. */
	public ScreenCoord coord = new ScreenCoord(-1, -1);
	
	/** The symbol. */
	protected char symbol = '\0';
	
	/** The name. */
	protected String name = "";
	
	/** The description. */
	protected String description = "";
	
	/** The id. */
	protected String id = "";

	/** The body parts. */
	protected ArrayList<BodyPart> bodyParts = new ArrayList<BodyPart>();

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the symbol.
	 *
	 * @return the symbol
	 */
	public char getSymbol() {
		return symbol;
	}

	/**
	 * Sets the symbol.
	 *
	 * @param symbol the new symbol
	 */
	public void setSymbol(char symbol) {
		this.symbol = symbol;
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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
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
	 * Gets the body parts.
	 *
	 * @return the body parts
	 */
	public ArrayList<BodyPart> getBodyParts() {
		return bodyParts;
	}
	
	/**
	 * Prints the parts.
	 *
	 * @return the string
	 */
	public String printParts(){
		String buffer = "";
		
		for(BodyPart i:bodyParts){
			buffer = buffer + "\n" + i.printParts();
		}
		
		return buffer;
	}
}
