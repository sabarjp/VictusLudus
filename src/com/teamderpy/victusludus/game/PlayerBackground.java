package com.teamderpy.victusludus.game;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayerBackground.
 */
public class PlayerBackground {
	
	/** The id. */
	private String id;
	
	/** The name. */
	private String name;
	
	/** The description. */
	private String description;
	
	/** The degree. */
	private String degree;
	
	/** The respect. */
	private int respect;
	
	/** The capital. */
	private int capital;
	
	/** The credit. */
	private int credit;
	
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
	 * Gets the degree.
	 *
	 * @return the degree
	 */
	public String getDegree() {
		return degree;
	}
	
	/**
	 * Sets the degree.
	 *
	 * @param degree the new degree
	 */
	public void setDegree(String degree) {
		this.degree = degree;
	}
	
	/**
	 * Gets the respect.
	 *
	 * @return the respect
	 */
	public int getRespect() {
		return respect;
	}
	
	/**
	 * Sets the respect.
	 *
	 * @param respect the new respect
	 */
	public void setRespect(int respect) {
		this.respect = respect;
	}
	
	/**
	 * Gets the capital.
	 *
	 * @return the capital
	 */
	public int getCapital() {
		return capital;
	}
	
	/**
	 * Sets the capital.
	 *
	 * @param capital the new capital
	 */
	public void setCapital(int capital) {
		this.capital = capital;
	}
	
	/**
	 * Gets the credit.
	 *
	 * @return the credit
	 */
	public int getCredit() {
		return credit;
	}
	
	/**
	 * Sets the credit rating.
	 *
	 * @param credit the new credit rating
	 */
	public void setCreditRating(String credit) {
		if(credit.equals("C")){
			this.credit = 1;
		}else if(credit.equals("CC")){
			this.credit = 2;
		}else if(credit.equals("CCC")){
			this.credit = 3;
		}else if(credit.equals("B")){
			this.credit = 4;
		}else if(credit.equals("BB")){
			this.credit = 5;
		}else if(credit.equals("BBB")){
			this.credit = 6;
		}else if(credit.equals("A")){
			this.credit = 7;
		}else if(credit.equals("AA")){
			this.credit = 8;
		}else if(credit.equals("AAA")){
			this.credit = 9;
		}else{
			this.credit = 0;
		}
	}
	
	/**
	 * Gets the credit rating.
	 *
	 * @return the credit rating
	 */
	public String getCreditRating(){
		if(this.credit == 1){
			return "C";
		}else if(this.credit == 2){
			return "CC";
		}else if(this.credit == 3){
			return "CCC";
		}else if(this.credit == 4){
			return "B";
		}else if(this.credit == 5){
			return "BB";
		}else if(this.credit == 6){
			return "BBB";
		}else if(this.credit == 7){
			return "A";
		}else if(this.credit == 8){
			return "AA";
		}else if(this.credit == 9){
			return "AAA";
		}else{
			return "D";
		}
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
}
