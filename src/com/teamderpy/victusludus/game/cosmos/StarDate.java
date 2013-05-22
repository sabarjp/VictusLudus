package com.teamderpy.victusludus.game.cosmos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

/**
 * Represents a star date as the number of seconds since the big bang
 * 
 * @author Josh
 *
 */
public class StarDate {
	private static BigInteger SECONDS_PER_YEAR = BigInteger.valueOf(31557600);
	private static BigInteger YEARS_PER_CENTURY = BigInteger.valueOf(100);
	private static BigInteger CENT_PER_MILLENNIUM = BigInteger.valueOf(10);
	private static BigInteger MILLENNIUM_PER_EPOCH = BigInteger.valueOf(1000);

	public static MathContext STELLAR_RND = MathContext.DECIMAL128;

	private BigInteger secondsSinceBigBang;

	public StarDate(final BigInteger time){
		this.setSecondsSinceBigBang(time);
	}

	public StarDate(){
		this.setSecondsSinceBigBang(BigInteger.ZERO);
	}

	public BigInteger getSecondsSinceBigBang() {
		return this.secondsSinceBigBang;
	}

	public BigInteger getYearsSinceBigBang() {
		return this.secondsSinceBigBang.divide(StarDate.SECONDS_PER_YEAR);
	}

	public void setSecondsSinceBigBang(final BigInteger secondsSinceBigBang) {
		this.secondsSinceBigBang = secondsSinceBigBang;
	}

	public void addSeconds(final BigInteger seconds) {
		this.secondsSinceBigBang = this.secondsSinceBigBang.add(seconds);
	}

	public void addYears(final BigInteger years) {
		this.secondsSinceBigBang = this.secondsSinceBigBang.add(years.multiply(StarDate.SECONDS_PER_YEAR));
	}

	public void addCenturies(final BigInteger centuries) {
		this.secondsSinceBigBang = this.secondsSinceBigBang.add(centuries.multiply(StarDate.YEARS_PER_CENTURY).multiply(StarDate.SECONDS_PER_YEAR));
	}

	public void addMillennia(final BigInteger millennia) {
		this.secondsSinceBigBang = this.secondsSinceBigBang.add(millennia.multiply(StarDate.CENT_PER_MILLENNIUM).multiply(StarDate.YEARS_PER_CENTURY).multiply(StarDate.SECONDS_PER_YEAR));
	}

	public void addEpochs(final BigInteger epochs) {
		this.secondsSinceBigBang = this.secondsSinceBigBang.add(epochs.multiply(StarDate.MILLENNIUM_PER_EPOCH).multiply(StarDate.CENT_PER_MILLENNIUM).multiply(StarDate.YEARS_PER_CENTURY).multiply(StarDate.SECONDS_PER_YEAR));
	}

	@Override
	public String toString(){
		return StarDate.getFormattedStarDate(this);
	}

	/**
	 * Returns the epoch component of the star date
	 * @return
	 */
	public BigInteger getEpoch(){
		return this.getEpochDecimal().toBigInteger();
	}

	/**
	 * Returns the epoch component of the star date
	 * @return
	 */
	public BigDecimal getEpochDecimal(){
		return new BigDecimal(this.secondsSinceBigBang).divide(new BigDecimal(StarDate.SECONDS_PER_YEAR.multiply(StarDate.YEARS_PER_CENTURY).multiply(StarDate.CENT_PER_MILLENNIUM).multiply(StarDate.MILLENNIUM_PER_EPOCH)), StarDate.STELLAR_RND);
	}

	/**
	 * Returns the millennium component of the star date
	 * @return
	 */
	public BigInteger getMillennium(){
		return this.getMillenniumDecimal().toBigInteger();
	}

	/**
	 * Returns the millennium component of the star date
	 * @return
	 */
	public BigDecimal getMillenniumDecimal(){
		return this.getEpochDecimal().subtract(new BigDecimal(this.getEpoch())).multiply(new BigDecimal(StarDate.MILLENNIUM_PER_EPOCH), StarDate.STELLAR_RND);
	}

	/**
	 * Returns the century component of the star date
	 * @return
	 */
	public BigInteger getCentury(){
		return this.getCenturyDecimal().toBigInteger();
	}

	/**
	 * Returns the century component of the star date
	 * @return
	 */
	public BigDecimal getCenturyDecimal(){
		return this.getMillenniumDecimal().subtract(new BigDecimal(this.getMillennium())).multiply(new BigDecimal(StarDate.CENT_PER_MILLENNIUM), StarDate.STELLAR_RND);
	}

	/**
	 * Returns the year component of the star date
	 * @return
	 */
	public BigInteger getYear(){
		return this.getYearDecimal().toBigInteger();
	}

	/**
	 * Returns the year component of the star date
	 * @return
	 */
	public BigDecimal getYearDecimal(){
		return this.getCenturyDecimal().subtract(new BigDecimal(this.getCentury())).multiply(new BigDecimal(StarDate.YEARS_PER_CENTURY), StarDate.STELLAR_RND);
	}

	/**
	 * Returns the seconds component of the star date
	 * @return
	 */
	public BigInteger getSeconds(){
		return this.getYear().subtract(this.getYear()).multiply(StarDate.SECONDS_PER_YEAR);
	}

	/**
	 * Returns the seconds component of the star date
	 * @return
	 */
	public BigDecimal getSecondsDecimal(){
		return this.getYearDecimal().subtract(new BigDecimal(this.getYear())).multiply(new BigDecimal(StarDate.SECONDS_PER_YEAR), StarDate.STELLAR_RND);
	}

	/**
	 * Prints the star date in standard star date format
	 * 
	 * @param age in years
	 * @return string formatted for stellar timescale
	 */
	public static String getFormattedStarDate(final StarDate starDate){
		return starDate.getYear() + "." + starDate.getCentury() + "." + starDate.getMillennium() + ", " + starDate.getEpoch();
	}

	/**
	 * Returns a clone of this object
	 */
	@Override
	public StarDate clone(){
		return new StarDate(this.secondsSinceBigBang);
	}
}
