package com.teamderpy.victusludus.game.starcluster;


/** A star in a solar system */
public class Star {
	public static double SOLAR_MASS = 1.9891E30;
	public static double SOLAR_LUMINOSITY = 3.827E26;
	public static double SOLAR_RADIUS = 6.960E8;
	public static double AU = 149.5978E6;
	public static double STEFAN_BOLTZMANN = 5.67E-8;
	public static double PROTOSTAR_START_TEMP = 1500D;

	private static double MAIN_SEQUENCE_START_MULT = 0.80;
	private static double MAIN_SEQUENCE_END_MULT = 1.60;

	/** the luminosity of a star in watts */
	private double luminosity;

	/** the luminosity of a star in kilograms */
	private double mass;

	/** the surface temperature of the star in kelvins */
	private double surfaceTemperature;

	/** the age of the star in years */
	private double age;

	/** the radius of the star in meters */
	private double radius;

	/** the type of star */
	private EnumStarType starType;

	public Star(final double startingMass){
		this.mass = startingMass;
		this.age = 0D;
		this.starType = EnumStarType.PROTOSTAR;
		this.surfaceTemperature = Star.PROTOSTAR_START_TEMP;
		this.luminosity = this.calcBirthLuminosity();
		this.radius = this.calcRadius();
	}

	/**
	 * A tick of time
	 * 
	 * @param delta the amount of stellar time that has passed since the last tick, in years
	 */
	public void tick(final double delta){
		switch (this.starType){
			case PROTOSTAR:
				this.tickProtostar(delta);
				break;
			case MAIN_SEQUENCE:
				this.tickMainSequence(delta);
				break;
			case RED_DWARF:
				this.tickMainSequence(delta);
				break;
			case BROWN_DWARF:
				this.tickMainSequence(delta);
				break;
		}
	}

	/**
	 * The idea is that the star slowly progresses towards the main sequence.
	 * Note that very massive and very tiny stars should fail.
	 * 
	 * @param delta the amount of stellar time that has passed since the last tick, in years
	 */
	private void tickProtostar(final double delta){
		double endOfLife = this.calcTimeAsProtostar();
		double startLuminosity = this.calcBirthLuminosity() ;
		double startTemperature = Star.PROTOSTAR_START_TEMP;
		double targetLuminosity = this.getMainSequenceLuminosityFromMass() * Star.MAIN_SEQUENCE_START_MULT;
		double targetTemperature = this.getMainSequenceTemperatureFromMass(targetLuminosity);
		double nextDate = this.age + delta;
		double rolloverDelta = 0;

		//is this a failure star?
		if(this.mass / Star.SOLAR_MASS < 0.08){
			this.starType = EnumStarType.BROWN_DWARF;
			this.tick(rolloverDelta);
			return;
		}

		//will we be in main sequence during this tick?
		if(nextDate > endOfLife){
			rolloverDelta = nextDate - endOfLife;
			nextDate = endOfLife;
		}

		//find our new luminosity
		this.luminosity = startLuminosity + nextDate / endOfLife * (targetLuminosity - startLuminosity );

		//find our new temperature
		this.surfaceTemperature = startTemperature + nextDate / endOfLife * (targetTemperature - startTemperature);

		//recalculate the radius
		this.radius = this.calcRadius();

		//proceed to main sequence
		if(nextDate >= endOfLife){
			if(this.mass/Star.SOLAR_MASS > 0.5){
				this.starType = EnumStarType.MAIN_SEQUENCE;
			}else if(this.mass/Star.SOLAR_MASS > 0.075){
				this.starType = EnumStarType.RED_DWARF;
			}else{
				this.starType = EnumStarType.BROWN_DWARF;
			}
		}

		//age the star
		this.age = nextDate;

		//do another tick as we finished this stage early
		if(rolloverDelta > 0){
			this.tick(rolloverDelta);
			return;
		}
	}

	/**
	 * The star basically just lives.  The luminosity and temperature will increase slightly
	 * as hydrogen turns into helium.
	 * 
	 * @param delta the amount of stellar time that has passed since the last tick, in years
	 */
	private void tickMainSequence(final double delta){
		double endOfLife = this.calcTimeAsMainSequence();
		double startLuminosity = this.getMainSequenceLuminosityFromMass() * Star.MAIN_SEQUENCE_START_MULT;
		double startTemperature = this.getMainSequenceTemperatureFromMass(startLuminosity);
		double targetLuminosity = this.getMainSequenceLuminosityFromMass() * Star.MAIN_SEQUENCE_END_MULT;
		double targetTemperature = this.getMainSequenceTemperatureFromMass(targetLuminosity);
		double nextDate = this.age + delta;
		double rolloverDelta = 0;

		//will we roll over this tick?
		if(nextDate > endOfLife){
			rolloverDelta = nextDate - endOfLife;
			nextDate = endOfLife;
		}

		//find our new luminosity
		this.luminosity = startLuminosity + nextDate / endOfLife * (targetLuminosity - startLuminosity);

		//find our new temperature
		this.surfaceTemperature = startTemperature + nextDate / endOfLife * (targetTemperature - startTemperature);

		//recalculate the radius
		this.radius = this.calcRadius();

		//proceed to next sequence
		if(nextDate >= endOfLife){
			if(this.mass/Star.SOLAR_MASS > 40){
				this.starType = EnumStarType.HYPER_GIANT;
			} else if(this.mass/Star.SOLAR_MASS > 10){
				this.starType = EnumStarType.SUPER_GIANT;
			} else if(this.mass/Star.SOLAR_MASS > 0.2){
				this.starType = EnumStarType.SUB_GIANT;
			} else {
				if(this.starType != EnumStarType.BROWN_DWARF){
					this.starType = EnumStarType.BLUE_DWARF;
				} else {
					this.starType = EnumStarType.DEAD_BROWN_DWARF;
				}
			}
		}

		//age the star
		this.age = nextDate;

		//do another tick as we finished this stage early
		if(rolloverDelta > 0){
			this.tick(rolloverDelta);
			return;
		}
	}

	/**
	 * Calculates the luminosity of a star at birth
	 * 
	 * @param mass the mass of the star in kilograms
	 * @return the luminosity of the star in watts
	 */
	public double calcBirthLuminosity(){
		return Star.SOLAR_LUMINOSITY * (46.0654687347D * Math.pow(this.mass/Star.SOLAR_MASS, 2.254768727D));
	}

	/**
	 * Calculates the radius of the star in meters
	 * 
	 * @return the radius of the star in meters
	 */
	public double calcRadius(){
		return Math.sqrt(this.luminosity / (4 * Math.PI * Star.STEFAN_BOLTZMANN * Math.pow(this.surfaceTemperature, 4)));
	}

	/**
	 * Calculates the luminosity of a main sequence star in watts from the mass
	 * 
	 * @return the mass in kilograms
	 */
	public double getMainSequenceLuminosityFromMass(){
		double solarMass = this.mass/Star.SOLAR_MASS;

		if(solarMass < 0.43){
			return 0.23 * Math.pow(this.mass / Star.SOLAR_MASS, 2.3) * Star.SOLAR_LUMINOSITY;
		}else if(solarMass < 2.00){
			return Math.pow(this.mass / Star.SOLAR_MASS, 4.0) * Star.SOLAR_LUMINOSITY;
		}else if(solarMass < 20.0){
			return 1.5 * Math.pow(this.mass / Star.SOLAR_MASS, 3.5) * Star.SOLAR_LUMINOSITY;
		}else{
			return 42 * Math.pow(this.mass / Star.SOLAR_MASS, 2.5) * Star.SOLAR_LUMINOSITY;
		}
	}

	/**
	 * Calculates the temperature of a main sequence star in kelvin from the mass
	 * 
	 * @return the temperature in kelvin
	 */
	public double getMainSequenceTemperatureFromMass(final double luminosity){
		return 6118.1162283755D * Math.pow(luminosity/Star.SOLAR_LUMINOSITY, 0.1349483096D);
	}

	/**
	 * Calculates how long a star stays a protostar, in years
	 * 
	 * @return the time in years
	 */
	public double calcTimeAsProtostar(){
		return 37190503.1847951D * Math.pow(this.mass/Star.SOLAR_MASS, -2.3519309281D);
	}

	/**
	 * Calculates how long a star stays a main sequence star, in years
	 * 
	 * @return the time in years
	 */
	public double calcTimeAsMainSequence(){
		return 10000000000D * Math.pow(this.mass/Star.SOLAR_MASS, -2.5D);
	}

	/**
	 * The spectral class of this star
	 * 
	 * @return returns a string representing the spectral class of the star
	 */
	public String getSpectralClass(){
		StringBuilder sb = new StringBuilder();

		if(this.surfaceTemperature >= 33000){
			sb.append("O");
		}else if(this.surfaceTemperature >= 30700){
			sb.append("B0");
		}else if(this.surfaceTemperature >= 28400){
			sb.append("B1");
		}else if(this.surfaceTemperature >= 26100){
			sb.append("B2");
		}else if(this.surfaceTemperature >= 23800){
			sb.append("B3");
		}else if(this.surfaceTemperature >= 21500){
			sb.append("B4");
		}else if(this.surfaceTemperature >= 19200){
			sb.append("B5");
		}else if(this.surfaceTemperature >= 16900){
			sb.append("B6");
		}else if(this.surfaceTemperature >= 14600){
			sb.append("B7");
		}else if(this.surfaceTemperature >= 12300){
			sb.append("B8");
		}else if(this.surfaceTemperature >= 10000){
			sb.append("B9");
		}else if(this.surfaceTemperature >= 9750){
			sb.append("A0");
		}else if(this.surfaceTemperature >= 9500){
			sb.append("A1");
		}else if(this.surfaceTemperature >= 9250){
			sb.append("A2");
		}else if(this.surfaceTemperature >= 9000){
			sb.append("A3");
		}else if(this.surfaceTemperature >= 8750){
			sb.append("A4");
		}else if(this.surfaceTemperature >= 8500){
			sb.append("A5");
		}else if(this.surfaceTemperature >= 8250){
			sb.append("A6");
		}else if(this.surfaceTemperature >= 8000){
			sb.append("A7");
		}else if(this.surfaceTemperature >= 7750){
			sb.append("A8");
		}else if(this.surfaceTemperature >= 7500){
			sb.append("A9");
		}else if(this.surfaceTemperature >= 7350){
			sb.append("F0");
		}else if(this.surfaceTemperature >= 7200){
			sb.append("F1");
		}else if(this.surfaceTemperature >= 7050){
			sb.append("F2");
		}else if(this.surfaceTemperature >= 6900){
			sb.append("F3");
		}else if(this.surfaceTemperature >= 6750){
			sb.append("F4");
		}else if(this.surfaceTemperature >= 6600){
			sb.append("F5");
		}else if(this.surfaceTemperature >= 6450){
			sb.append("F6");
		}else if(this.surfaceTemperature >= 6300){
			sb.append("F7");
		}else if(this.surfaceTemperature >= 6150){
			sb.append("F8");
		}else if(this.surfaceTemperature >= 6000){
			sb.append("F9");
		}else if(this.surfaceTemperature >= 5920){
			sb.append("G0");
		}else if(this.surfaceTemperature >= 5840){
			sb.append("G1");
		}else if(this.surfaceTemperature >= 5760){
			sb.append("G2");
		}else if(this.surfaceTemperature >= 5680){
			sb.append("G3");
		}else if(this.surfaceTemperature >= 5600){
			sb.append("G4");
		}else if(this.surfaceTemperature >= 5520){
			sb.append("G5");
		}else if(this.surfaceTemperature >= 5440){
			sb.append("G6");
		}else if(this.surfaceTemperature >= 5360){
			sb.append("G7");
		}else if(this.surfaceTemperature >= 5280){
			sb.append("G8");
		}else if(this.surfaceTemperature >= 5200){
			sb.append("G9");
		}else if(this.surfaceTemperature >= 5050){
			sb.append("K0");
		}else if(this.surfaceTemperature >= 4900){
			sb.append("K1");
		}else if(this.surfaceTemperature >= 4750){
			sb.append("K2");
		}else if(this.surfaceTemperature >= 4600){
			sb.append("K3");
		}else if(this.surfaceTemperature >= 4450){
			sb.append("K4");
		}else if(this.surfaceTemperature >= 4300){
			sb.append("K5");
		}else if(this.surfaceTemperature >= 4150){
			sb.append("K6");
		}else if(this.surfaceTemperature >= 4000){
			sb.append("K7");
		}else if(this.surfaceTemperature >= 3850){
			sb.append("K8");
		}else if(this.surfaceTemperature >= 3700){
			sb.append("K9");
		}else if(this.surfaceTemperature >= 3530){
			sb.append("M0");
		}else if(this.surfaceTemperature >= 3360){
			sb.append("M1");
		}else if(this.surfaceTemperature >= 3190){
			sb.append("M2");
		}else if(this.surfaceTemperature >= 3020){
			sb.append("M3");
		}else if(this.surfaceTemperature >= 2850){
			sb.append("M4");
		}else if(this.surfaceTemperature >= 2680){
			sb.append("M5");
		}else if(this.surfaceTemperature >= 2510){
			sb.append("M6");
		}else if(this.surfaceTemperature >= 2340){
			sb.append("M7");
		}else if(this.surfaceTemperature >= 2170){
			sb.append("M8");
		}else if(this.surfaceTemperature >= 2000){
			sb.append("M9");
		}else if(this.surfaceTemperature >= 1300){
			sb.append("L");
		}else if(this.surfaceTemperature >= 700){
			sb.append("T");
		}else{
			sb.append("Y");
		}

		if(this.starType == EnumStarType.HYPER_GIANT){
			sb.append("0");
		}else if(this.starType == EnumStarType.SUPER_GIANT){
			sb.append("I");
		}else if(this.starType == EnumStarType.BRIGHT_GIANT){
			sb.append("II");
		}else if(this.starType == EnumStarType.GIANT){
			sb.append("III");
		}else if(this.starType == EnumStarType.SUB_GIANT){
			sb.append("IV");
		}else if(this.starType == EnumStarType.MAIN_SEQUENCE){
			sb.append("V");
		}else if(this.starType == EnumStarType.WHITE_DWARF){
			sb.append("VII");
		}else if(this.starType == EnumStarType.RED_DWARF || this.starType == EnumStarType.BROWN_DWARF){
			sb.append("VI");
		}

		return sb.toString();
	}

	@Override
	public String toString(){
		return "  Star type: " + this.starType + "\n"
				+ "        age: " + this.getFormattedStellarAge(this.age) + "\n"
				+ "       mass: " + this.mass + "  Solar " + this.mass / Star.SOLAR_MASS + "\n"
				+ " luminosity: " + this.luminosity + "  Solar " + this.luminosity / Star.SOLAR_LUMINOSITY + "\n"
				+ "     radius: " + this.radius + "  Solar " + this.radius / Star.SOLAR_RADIUS + "\n"
				+ "       temp: " + Math.round(this.surfaceTemperature) + " K" + "\n"
				+ "      class: " + this.getSpectralClass() + "\n"
				+ "     MS lum: " + this.getMainSequenceLuminosityFromMass() + "  Solar " + this.getMainSequenceLuminosityFromMass() / Star.SOLAR_LUMINOSITY + "\n";
	}

	/**
	 * Turns a number into a string for stellar age.
	 * I.E, 6000000 returns "6 billion years"
	 * 
	 * @param age in years
	 * @return string formatted for stellar timescale
	 */
	private String getFormattedStellarAge(final double age){
		if(age >= 1000000000000D){
			return age / 1000000000000D + " trillion years";
		}else if(age >= 1000000000D){
			return age / 1000000000D + " billion years";
		}else if(age >= 1000000D){
			return age / 1000000D + " million years";
		}else if(age >= 1000D){
			return age / 1000D + " thousand years";
		}

		return age + " years";
	}
}
