package com.teamderpy.victusludus.precision;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.teamderpy.victusludus.game.cosmos.Star;

public class Precision {

	/**
	 * Calculates the power of two big decimals
	 * 
	 * @param n1 the first number
	 * @param n2 the number to raise the second number to
	 * @return the BigDecimal result
	 */
	public static BigDecimal pow(final BigDecimal n1, BigDecimal n2){
		int signOf2 = n2.signum();
		BigDecimal result;

		// Perform X^(A+B)=X^A*X^B (B = remainder)
		double dn1 = n1.doubleValue();

		n2 = n2.multiply(new BigDecimal(signOf2)); // n2 is now positive
		BigDecimal remainderOf2 = n2.remainder(BigDecimal.ONE);
		BigDecimal n2IntPart = n2.subtract(remainderOf2);

		// Calculate big part of the power using context -
		// bigger range and performance but lower accuracy
		BigDecimal intPow = n1.pow(n2IntPart.intValueExact());

		double remain = remainderOf2.doubleValue();
		double smallPow = Math.pow(dn1, remain);

		BigDecimal doublePow = new BigDecimal(smallPow);
		result = intPow.multiply(doublePow);

		// Fix negative power
		if (signOf2 == -1) {
			result = BigDecimal.ONE.divide(result, Star.STELLAR_RND);
		}

		return result;
	}

	/**
	 * Returns the floor of a big decimal
	 * 
	 * @param num the BigDecimal to round
	 * @return the floor of a big decimal
	 */
	public static BigDecimal floor(final BigDecimal num){
		return num.setScale(0, RoundingMode.FLOOR);
	}

	/**
	 * Returns the ceiling of a big decimal
	 * 
	 * @param num the BigDecimal to round
	 * @return the ceiling of a big decimal
	 */
	public static BigDecimal ceiling(final BigDecimal num){
		return num.setScale(0, RoundingMode.CEILING);
	}

	/**
	 * Returns the rounded result of a big decimal
	 * 
	 * @param num the BigDecimal to round
	 * @return the result of a big decimal rounded
	 */
	public static BigDecimal round(final BigDecimal num){
		return num.setScale(0, RoundingMode.HALF_UP);
	}

	/**
	 * Returns the rounded result of a big decimal to two decimal places
	 * 
	 * @param num the BigDecimal to round
	 * @return the result of a big decimal rounded to two decimal places
	 */
	public static BigDecimal roundTwo(final BigDecimal num){
		return num.setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * Returns the rounded result of a big decimal to three decimal places
	 * 
	 * @param num the BigDecimal to round
	 * @return the result of a big decimal rounded to three decimal places
	 */
	public static BigDecimal roundThree(final BigDecimal num){
		return num.setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * Returns the rounded result of a big decimal to five decimal places
	 * 
	 * @param num the BigDecimal to round
	 * @return the result of a big decimal rounded to five decimal places
	 */
	public static BigDecimal roundFive(final BigDecimal num){
		return num.setScale(5, RoundingMode.HALF_UP);
	}
}
