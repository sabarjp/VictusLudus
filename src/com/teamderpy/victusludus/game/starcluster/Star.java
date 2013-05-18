package com.teamderpy.victusludus.game.starcluster;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import com.teamderpy.victusludus.data.VictusLudus;


/** A star in a solar system */
public class Star {
	private static BigDecimal MAIN_SEQUENCE_START_MULT = new BigDecimal("0.80");
	private static BigDecimal MAIN_SEQUENCE_END_MULT = new BigDecimal("1.60");
	public static BigDecimal PROTOSTAR_START_TEMP = new BigDecimal("1500");

	public static MathContext STELLAR_RND = MathContext.DECIMAL128;
	public static MathContext LOWPREC_RND = MathContext.DECIMAL32;

	/** the random seed for this star **/
	private float seed;

	/** the birth date of the star */
	private StarDate birthDate;

	/** the luminosity of a star in watts */
	private BigDecimal luminosity;

	/** the mass of a star in kilograms */
	private BigDecimal mass;

	/** the surface temperature of the star in kelvins */
	private BigDecimal surfaceTemperature;

	/** the age of the star in years */
	private BigDecimal age;

	/** the radius of the star in meters */
	private BigDecimal radius;

	/** values that the start started its life phase with */
	private BigDecimal startedPhaseAge;
	private BigDecimal startedPhaseLuminosity;
	private BigDecimal startedPhaseMass;
	private BigDecimal startedPhaseTemperature;

	/** the type of star */
	private EnumStarType starType;

	/** star history log */
	private ArrayList<String> historyLog;

	public Star(final StarDate birthDate, final BigDecimal startingMass){
		this.mass = startingMass;

		this.startedPhaseMass = this.mass;
		this.age = BigDecimal.ZERO;
		this.startedPhaseAge = this.age;
		this.starType = EnumStarType.DUST;
		this.surfaceTemperature = Star.PROTOSTAR_START_TEMP;
		this.startedPhaseTemperature = this.surfaceTemperature;
		this.luminosity = this.calcBirthLuminosity();
		this.startedPhaseLuminosity = this.luminosity;
		this.radius = this.calcMainSequenceRadius();
		this.seed = VictusLudus.rand.nextInt()/2;
		this.historyLog = new ArrayList<String>();
		this.birthDate = new StarDate(birthDate.getSecondsSinceBigBang());
	}

	public Star(final StarDate birthDate, final String startingMass){
		this.mass = new BigDecimal(startingMass);

		this.startedPhaseMass = this.mass;
		this.age = BigDecimal.ZERO;
		this.startedPhaseAge = this.age;
		this.starType = EnumStarType.DUST;
		this.surfaceTemperature = Star.PROTOSTAR_START_TEMP;
		this.startedPhaseTemperature = this.surfaceTemperature;
		this.luminosity = this.calcBirthLuminosity();
		this.startedPhaseLuminosity = this.luminosity;
		this.radius = this.calcMainSequenceRadius();
		this.seed = VictusLudus.rand.nextInt()/2;
		this.historyLog = new ArrayList<String>();
		this.birthDate = new StarDate(birthDate.getSecondsSinceBigBang());
	}

	/**
	 * A tick of time
	 * 
	 * @param delta the amount of stellar time that has passed since the last tick, in years
	 */
	public void tick(final BigDecimal delta){
		switch (this.starType){
			case DUST:
				this.tickDust(delta);
				break;
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
			case SUB_GIANT:
				this.tickSubGiant(delta);
				break;
			case GIANT:
				this.tickGiant(delta);
				break;
			case PLANETARY_NEBULA:
				this.tickPlanetaryNebula(delta);
				break;
			case WHITE_DWARF:
				this.tickWhiteDwarf(delta);
				break;
			case HELIUM_WHITE_DWARF:
				this.tickWhiteDwarf(delta);
				break;
			case BLACK_DWARF:
				this.tickBlackDwarf(delta);
				break;
			case DEAD_BROWN_DWARF:
				this.tickBlackDwarf(delta);
				break;
			case SUPER_GIANT:
				this.tickSuperGiant(delta);
				break;
			case HYPER_GIANT:
				this.tickSuperGiant(delta);
				break;
			case WOLF_RAYET_STAR:
				this.tickSuperGiant(delta);
				break;
			case 	SUPER_NOVA_TYPE_Ib:
				this.tickSuperNova(delta);
				break;
			case 	SUPER_NOVA_TYPE_Ic:
				this.tickSuperNova(delta);
				break;
			case 	SUPER_NOVA_TYPE_IIP:
				this.tickSuperNova(delta);
				break;
			case 	SUPER_NOVA_TYPE_IIL:
				this.tickSuperNova(delta);
				break;
			case 	NEUTRON_STAR:
				this.tickNeutronStar(delta);
				break;
			case 	PULSAR:
				this.tickNeutronStar(delta);
				break;
			case  BLACK_HOLE:
				this.tickBlackHole(delta);
				break;
			case  BLUE_DWARF:
				this.tickBlueDwarf(delta);
				break;
			case BRIGHT_GIANT:
				break;
			default:
				break;
		}
	}

	/**
	 * Every star must start somewhere
	 * 
	 * @param delta the amount of stellar time that has passed since the last tick, in years
	 */
	private void tickDust(final BigDecimal delta){
		this.starType = EnumStarType.PROTOSTAR;

		this.addHistory(this.birthDate, BigDecimal.ZERO, this.age, "Coalesed into a protostar");

		this.tick(delta);

	}


	/**
	 * The idea is that the star slowly progresses towards the main sequence.
	 * Note that very massive and very tiny stars should fail.
	 * 
	 * @param delta the amount of stellar time that has passed since the last tick, in years
	 */
	private void tickProtostar(final BigDecimal delta){
		BigDecimal endOfLife = this.calcTimeAsProtostar();
		BigDecimal targetLuminosity = this.getMainSequenceLuminosityFromMass().multiply(Star.MAIN_SEQUENCE_START_MULT, Star.STELLAR_RND);
		BigDecimal targetTemperature = this.getMainSequenceTemperatureFromLuminosity(targetLuminosity);
		BigDecimal nextDate = this.age.add(delta);
		BigDecimal rolloverDelta = BigDecimal.ZERO;

		//is this a failure star?
		if(this.mass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND).compareTo(new BigDecimal("0.08")) < 0){
			this.starType = EnumStarType.BROWN_DWARF;

			this.addHistory(this.birthDate, BigDecimal.ZERO, this.age, "Born as a brown dwarf");

			this.surfaceTemperature = this.getBrownDwarfBirthTemperature();
			this.setStartingPhaseValues();
			this.tick(rolloverDelta);
			return;
		}

		//will we be in main sequence during this tick?
		if(nextDate.compareTo(endOfLife) > 0){
			rolloverDelta = nextDate.subtract(endOfLife);
			nextDate = endOfLife;
		}

		BigDecimal timeAdvance = nextDate.subtract(this.age);

		//find our new luminosity
		this.luminosity = Cosmology.linearInterpolation(this.startedPhaseAge, endOfLife, this.startedPhaseLuminosity, targetLuminosity, nextDate);

		//find our new temperature
		this.surfaceTemperature = Cosmology.linearInterpolation(this.startedPhaseAge, endOfLife, this.startedPhaseTemperature, targetTemperature, nextDate);

		//recalculate the radius
		this.radius = this.calcMainSequenceRadius();

		//age the star
		this.age = nextDate;

		//proceed to main sequence
		if(nextDate.compareTo(endOfLife) >= 0){
			this.setStartingPhaseValues();

			if(this.mass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND).compareTo(new BigDecimal("0.5")) > 0){
				this.starType = EnumStarType.MAIN_SEQUENCE;
				this.addHistory(this.birthDate, timeAdvance, this.age, "Born as a main sequence");
			}else if(this.mass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND).compareTo(new BigDecimal("0.075")) > 0){
				this.starType = EnumStarType.RED_DWARF;
				this.addHistory(this.birthDate, timeAdvance, this.age, "Born as a red dwarf");
			}
		}

		//do another tick as we finished this stage early
		if(rolloverDelta.compareTo(BigDecimal.ZERO) > 0){
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
	private void tickMainSequence(final BigDecimal delta){
		BigDecimal endOfLife = this.calcTimeAsMainSequence();
		BigDecimal targetLuminosity = this.getMainSequenceLuminosityFromMass().multiply(Star.MAIN_SEQUENCE_END_MULT, Star.STELLAR_RND);
		BigDecimal targetTemperature = this.getMainSequenceTemperatureFromLuminosity(targetLuminosity);
		BigDecimal nextDate = this.age.add(delta);
		BigDecimal rolloverDelta = BigDecimal.ZERO;

		//will we roll over this tick?
		if(nextDate.compareTo(endOfLife) > 0){
			rolloverDelta = nextDate.subtract(endOfLife);
			nextDate = endOfLife;
		}

		BigDecimal timeAdvance = nextDate.subtract(this.age);

		//find our new luminosity
		this.luminosity = Cosmology.linearInterpolation(this.startedPhaseAge, endOfLife, this.startedPhaseLuminosity, targetLuminosity, nextDate);

		//find our new temperature
		this.surfaceTemperature = Cosmology.linearInterpolation(this.startedPhaseAge, endOfLife, this.startedPhaseTemperature, targetTemperature, nextDate);

		//recalculate the radius
		this.radius = this.calcMainSequenceRadius();

		//age the star
		this.age = nextDate;

		//proceed to next sequence
		if(nextDate.compareTo(endOfLife) >= 0){
			this.setStartingPhaseValues();

			if(this.mass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND).compareTo(BigDecimal.valueOf(40)) > 0){
				this.starType = EnumStarType.HYPER_GIANT;
				this.addHistory(this.birthDate, timeAdvance, this.age, "Evolved into hyper giant");
			} else if(this.mass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND).compareTo(BigDecimal.valueOf(8)) > 0){
				this.starType = EnumStarType.SUPER_GIANT;
				this.addHistory(this.birthDate, timeAdvance, this.age, "Evolved into super giant");
			} else if(this.mass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND).compareTo(new BigDecimal("0.2")) > 0){
				this.starType = EnumStarType.SUB_GIANT;
				this.addHistory(this.birthDate, timeAdvance, this.age, "Evolved into sub-giant");
			} else {
				if(this.starType != EnumStarType.BROWN_DWARF){
					this.starType = EnumStarType.BLUE_DWARF;
					this.addHistory(this.birthDate, timeAdvance, this.age, "Evolved into blue dwarf");
				} else {
					this.starType = EnumStarType.DEAD_BROWN_DWARF;
					this.addHistory(this.birthDate, timeAdvance, this.age, "Evolved into dead brown dwarf");
				}
			}
		}

		//do another tick as we finished this stage early
		if(rolloverDelta.compareTo(BigDecimal.ZERO) > 0){
			this.tick(rolloverDelta);
			return;
		}
	}

	/**
	 * A sub giant is a hydrogen-shell burning star on its way to being a normal giant.
	 * This is a rather short phase and basically goes into the giant phase.
	 * The primary event here is a lowering of surface temperature but a massive
	 * increase in radius and luminosity.
	 * 
	 * @param delta the amount of stellar time that has passed since the last tick, in years
	 */
	private void tickSubGiant(final BigDecimal delta){
		BigDecimal endOfLife = this.startedPhaseAge.add(this.calcTimeAsSubGiant());
		BigDecimal targetLuminosity = this.getSubGiantLuminosityFromMass();
		BigDecimal targetTemperature = this.getSubgiantTemperatureFromLuminosity(targetLuminosity);
		BigDecimal nextDate = this.age.add(delta);
		BigDecimal rolloverDelta = BigDecimal.ZERO;

		//will we roll over this tick?
		if(nextDate.compareTo(endOfLife) > 0){
			rolloverDelta = nextDate.subtract(endOfLife);
			nextDate = endOfLife;
		}

		BigDecimal timeAdvance = nextDate.subtract(this.age);

		//find our new luminosity
		this.luminosity = Cosmology.exponentialInterpolation(this.startedPhaseAge, endOfLife, this.startedPhaseLuminosity, targetLuminosity, nextDate, new BigDecimal("0.3"));

		//find our new temperature
		this.surfaceTemperature = Cosmology.exponentialInterpolation(this.startedPhaseAge, endOfLife, this.startedPhaseTemperature, targetTemperature, nextDate, new BigDecimal("0.3"));

		//recalculate the radius
		this.radius = this.calcMainSequenceRadius();

		//age the star
		this.age = nextDate;

		//proceed to next sequence
		if(nextDate.compareTo(endOfLife) >= 0){
			this.setStartingPhaseValues();

			if(this.mass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND).compareTo(new BigDecimal("0.5")) > 0){
				this.setStartingPhaseValues();

				this.starType = EnumStarType.GIANT;

				this.addHistory(this.birthDate, timeAdvance, this.age, "Evolved into giant");
			} else {
				//shed outside layers
				this.surfaceTemperature = this.getWhiteDwarfTemperature();

				this.setStartingPhaseValues();

				this.starType = EnumStarType.HELIUM_WHITE_DWARF;

				this.addHistory(this.birthDate, timeAdvance, this.age, "Evolved into helium white dwarf");
			}
		}

		//do another tick as we finished this stage early
		if(rolloverDelta.compareTo(BigDecimal.ZERO) > 0){
			this.tick(rolloverDelta);
			return;
		}
	}

	/**
	 * A giant is a low or intermediate mass star that is burning helium
	 * at a very fast rate.  The star will become a white dwarf pretty fast.
	 * 
	 * @param delta the amount of stellar time that has passed since the last tick, in years
	 */
	private void tickGiant(final BigDecimal delta){
		BigDecimal endOfLife = this.startedPhaseAge.add(this.calcTimeAsGiant());
		BigDecimal targetMass = this.getGiantMassAfterLoss();
		BigDecimal nextDate = this.age.add(delta);
		BigDecimal rolloverDelta = BigDecimal.ZERO;

		//will we roll over this tick?
		if(nextDate.compareTo(endOfLife) > 0){
			rolloverDelta = nextDate.subtract(endOfLife);
			nextDate = endOfLife;
		}

		BigDecimal timeAdvance = nextDate.subtract(this.age);

		//age the star
		this.age = nextDate;

		//find the new mass
		this.mass = Cosmology.linearInterpolation(this.startedPhaseAge, endOfLife, this.startedPhaseMass, targetMass, nextDate);

		//calculate new temperature
		this.surfaceTemperature = this.calcTemperature();

		//recalculate the radius
		this.radius = this.calcMainSequenceRadius();

		//proceed to next sequence
		if(nextDate.compareTo(endOfLife) >= 0){
			this.setStartingPhaseValues();

			this.starType = EnumStarType.PLANETARY_NEBULA;

			this.addHistory(this.birthDate, timeAdvance, this.age, "Shed outer layers into planetary nebula");
		}

		//do another tick as we finished this stage early
		if(rolloverDelta.compareTo(BigDecimal.ZERO) > 0){
			this.tick(rolloverDelta);
			return;
		}
	}

	/**
	 * A super giant is a massive star that should burn out pretty fast
	 * 
	 * @param delta the amount of stellar time that has passed since the last tick, in years
	 */
	private void tickSuperGiant(final BigDecimal delta){
		BigDecimal endOfLife;
		BigDecimal targetMass;

		if(this.starType == EnumStarType.WOLF_RAYET_STAR){
			endOfLife = this.startedPhaseAge.add(this.calcTimeAsSuperGiant().divide(new BigDecimal("2"), Star.STELLAR_RND));
			targetMass = this.getSuperGiantMassAfterLoss();
		} else {
			endOfLife = this.startedPhaseAge.add(this.calcTimeAsSuperGiant());
			targetMass = this.getSuperGiantMassAfterLoss();
		}

		BigDecimal nextDate = this.age.add(delta);
		BigDecimal rolloverDelta = BigDecimal.ZERO;

		//will we roll over this tick?
		if(nextDate.compareTo(endOfLife) > 0){
			rolloverDelta = nextDate.subtract(endOfLife);
			nextDate = endOfLife;
		}

		BigDecimal timeAdvance = nextDate.subtract(this.age);

		//find the new mass
		this.mass = Cosmology.linearInterpolation(this.startedPhaseAge, endOfLife, this.startedPhaseMass, targetMass, nextDate);

		//determine new temperature
		this.surfaceTemperature = this.getSuperGiantLoopTemperature(nextDate.subtract(this.startedPhaseAge));

		//recalculate the radius
		this.radius = this.calcMainSequenceRadius();

		//age the star
		this.age = nextDate;

		//turn into wolf-rayet star
		if(this.starType != EnumStarType.WOLF_RAYET_STAR && this.mass.compareTo(this.startedPhaseMass.divide(new BigDecimal("2"), Star.STELLAR_RND)) < 0){
			this.surfaceTemperature = this.getWolfRayetTemperature();
			this.setStartingPhaseValues();
			this.starType = EnumStarType.WOLF_RAYET_STAR;

			this.addHistory(this.birthDate, timeAdvance, this.age, "Evolved into Wolf-Rayet star");
		} else if(nextDate.compareTo(endOfLife) >= 0){

			//next sequence
			this.setStartingPhaseValues();

			if(this.starType == EnumStarType.WOLF_RAYET_STAR){
				if(this.mass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND).compareTo(new BigDecimal("50")) > 0){
					this.starType = EnumStarType.SUPER_NOVA_TYPE_Ib;
					this.addHistory(this.birthDate, timeAdvance, this.age, "Exploded as a super nova Ib");
				} else {
					this.starType = EnumStarType.SUPER_NOVA_TYPE_Ic;
					this.addHistory(this.birthDate, timeAdvance, this.age, "Exploded as a super nova Ic");
				}
			} else if(this.mass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND).compareTo(new BigDecimal("20")) > 0){
				this.starType = EnumStarType.SUPER_NOVA_TYPE_IIL;
				this.addHistory(this.birthDate, timeAdvance, this.age, "Exploded as a super nova IIL");
			} else {
				this.starType = EnumStarType.SUPER_NOVA_TYPE_IIP;
				this.addHistory(this.birthDate, timeAdvance, this.age, "Exploded as a super nova IIP");
			}
		}

		//do another tick as we finished this stage early
		if(rolloverDelta.compareTo(BigDecimal.ZERO) > 0){
			this.tick(rolloverDelta);
			return;
		}
	}

	/**
	 * A planetary nebula is pretty much an an explosion where
	 * a giant rapidly transitions to a white dwarf.
	 * 
	 * @param delta the amount of stellar time that has passed since the last tick, in years
	 */
	private void tickPlanetaryNebula(final BigDecimal delta){
		BigDecimal endOfLife = this.startedPhaseAge.add(this.calcTimeAsPlanetaryNebula());
		BigDecimal targetMass = this.getWhiteDwarfBirthMass();
		BigDecimal targetTemperature = this.getWhiteDwarfTemperature();
		BigDecimal targetLuminosity = this.getWhiteDwarfLuminosity();
		BigDecimal nextDate = this.age.add(delta);
		BigDecimal rolloverDelta = BigDecimal.ZERO;

		//will we roll over this tick?
		if(nextDate.compareTo(endOfLife) > 0){
			rolloverDelta = nextDate.subtract(endOfLife);
			nextDate = endOfLife;
		}

		BigDecimal timeAdvance = nextDate.subtract(this.age);

		//age the star
		this.age = nextDate;

		//find the new mass
		this.mass = Cosmology.exponentialInterpolation(this.startedPhaseAge, endOfLife, this.startedPhaseMass, targetMass, nextDate, new BigDecimal("-0.3"));

		//find our new luminosity
		this.luminosity = Cosmology.exponentialInterpolation(this.startedPhaseAge, endOfLife, this.startedPhaseLuminosity, targetLuminosity, nextDate, new BigDecimal("-0.3"));

		//find our new temperature
		this.surfaceTemperature = Cosmology.exponentialInterpolation(this.startedPhaseAge, endOfLife, this.startedPhaseTemperature, targetTemperature, nextDate, new BigDecimal("-0.3"));

		//recalculate the radius
		this.radius = this.calcMainSequenceRadius();

		//proceed to next sequence
		if(nextDate.compareTo(endOfLife) >= 0){
			this.setStartingPhaseValues();

			this.starType = EnumStarType.WHITE_DWARF;

			this.addHistory(this.birthDate, timeAdvance, this.age, "Evolved into white dwarf");
		}

		//do another tick as we finished this stage early
		if(rolloverDelta.compareTo(BigDecimal.ZERO) > 0){
			this.tick(rolloverDelta);
			return;
		}
	}

	/**
	 * A super nova is a massive stellar explosion that will
	 * leave a neutron star or a black hole
	 * 
	 * @param delta the amount of stellar time that has passed since the last tick, in years
	 */
	private void tickSuperNova(final BigDecimal delta){
		BigDecimal endOfLife = this.startedPhaseAge.add(this.calcTimeAsSuperNova());
		BigDecimal targetMass = this.getNeutronStarBirthMass();
		BigDecimal targetTemperature = this.getNeutronStarTemperature();
		BigDecimal targetLuminosity = this.getNeutronStarLuminosity();
		BigDecimal nextDate = this.age.add(delta);
		BigDecimal rolloverDelta = BigDecimal.ZERO;

		//will we roll over this tick?
		if(nextDate.compareTo(endOfLife) > 0){
			rolloverDelta = nextDate.subtract(endOfLife);
			nextDate = endOfLife;
		}

		BigDecimal timeAdvance = nextDate.subtract(this.age);

		//find the new mass
		this.mass = Cosmology.exponentialInterpolation(this.startedPhaseAge, endOfLife, this.startedPhaseMass, targetMass, nextDate, new BigDecimal("-0.3"));

		//find our new luminosity
		this.luminosity = Cosmology.exponentialInterpolation(this.startedPhaseAge, endOfLife, this.startedPhaseLuminosity, targetLuminosity, nextDate, new BigDecimal("-0.3"));

		//find our new temperature
		this.surfaceTemperature = Cosmology.exponentialInterpolation(this.startedPhaseAge, endOfLife, this.startedPhaseTemperature, targetTemperature, nextDate, new BigDecimal("-0.3"));

		//recalculate the radius
		this.radius = this.calcMainSequenceRadius();

		//proceed to next sequence
		if(nextDate.compareTo(endOfLife) >= 0){
			this.setStartingPhaseValues();

			this.starType = EnumStarType.PULSAR;

			this.addHistory(this.birthDate, timeAdvance, this.age, "Evolved into pulsar");
		}

		//age the star
		this.age = nextDate;

		//do another tick as we finished this stage early
		if(rolloverDelta.compareTo(BigDecimal.ZERO) > 0){
			this.tick(rolloverDelta);
			return;
		}
	}

	/**
	 * A star made entirely of degenerate matter.  Can collapse into a black hole.
	 * 
	 * @param delta the amount of stellar time that has passed since the last tick, in years
	 */
	private void tickNeutronStar(final BigDecimal delta){
		BigDecimal endOfLife;
		BigDecimal targetTemperature = this.getBlackDwarfTemperature();
		BigDecimal targetLuminosity = this.getBlackDwarfLuminosity();


		if(this.starType == EnumStarType.PULSAR){
			endOfLife = this.startedPhaseAge.add(this.calcTimeAsPulsar());
		} else {
			endOfLife = this.startedPhaseAge.add(this.calcTimeAsNeutronStar());
		}

		BigDecimal nextDate = this.age.add(delta);
		BigDecimal rolloverDelta = BigDecimal.ZERO;

		//will we roll over this tick?
		if(nextDate.compareTo(endOfLife) > 0){
			nextDate = endOfLife;
		}

		BigDecimal timeAdvance = nextDate.subtract(this.age);

		//find our new luminosity
		this.luminosity = Cosmology.linearInterpolation(this.startedPhaseAge, endOfLife, this.startedPhaseLuminosity, targetLuminosity, nextDate);

		//find our new temperature
		this.surfaceTemperature = Cosmology.linearInterpolation(this.startedPhaseAge, endOfLife, this.startedPhaseTemperature, targetTemperature, nextDate);

		//recalculate the radius
		this.radius = this.calcNeutronDegenerateRadius();

		//age the star
		if(this.starType == EnumStarType.NEUTRON_STAR){
			this.age = this.age.add(delta);
		}else{
			this.age = nextDate;
		}

		//proceed to next sequence of black hole or neutron star
		if(this.mass.compareTo(Cosmology.TOV_LIMIT) > 0){
			this.radius = this.calcSchwarzschildRadius();
			this.luminosity = BigDecimal.ZERO;
			this.surfaceTemperature = BigDecimal.ZERO;

			this.setStartingPhaseValues();

			this.starType = EnumStarType.BLACK_HOLE;

			this.addHistory(this.birthDate, timeAdvance, this.age, "Collapsed into black hole");

			//roll over
			if(nextDate.compareTo(endOfLife) > 0){
				rolloverDelta = nextDate.subtract(endOfLife);
			}
		}else if(this.starType == EnumStarType.PULSAR && nextDate.compareTo(endOfLife) >= 0){
			this.starType = EnumStarType.NEUTRON_STAR;

			this.addHistory(this.birthDate, timeAdvance, this.age, "Evolved into neutron star");

			//roll over
			if(nextDate.compareTo(endOfLife) > 0){
				rolloverDelta = nextDate.subtract(endOfLife);
			}
		}

		//do another tick as we finished this stage early
		if(rolloverDelta.compareTo(BigDecimal.ZERO) > 0){
			this.tick(rolloverDelta);
			return;
		}
	}

	/**
	 * A black hole.
	 * 
	 * @param delta the amount of stellar time that has passed since the last tick, in years
	 */
	private void tickBlackHole(final BigDecimal delta){
		BigDecimal endOfLife = this.startedPhaseAge.add(this.calcTimeAsNeutronStar());
		BigDecimal nextDate = this.age.add(delta);

		//will we roll over this tick?
		if(nextDate.compareTo(endOfLife) > 0){
			nextDate = endOfLife;
		}

		//age the star
		this.age = this.age.add(delta);
	}

	/**
	 * A blue dwarf is a red dwarf that will increase its surface temperature
	 * a bunch on the way to being a white dwarf.
	 * 
	 * @param delta the amount of stellar time that has passed since the last tick, in years
	 */
	private void tickBlueDwarf(final BigDecimal delta){
		BigDecimal endOfLife = this.startedPhaseAge.add(this.calcTimeAsBlueDwarf());
		BigDecimal targetTemperature = this.getWhiteDwarfTemperature();
		BigDecimal nextDate = this.age.add(delta);
		BigDecimal rolloverDelta = BigDecimal.ZERO;

		//will we roll over this tick?
		if(nextDate.compareTo(endOfLife) > 0){
			rolloverDelta = nextDate.subtract(endOfLife);
			nextDate = endOfLife;
		}

		BigDecimal timeAdvance = nextDate.subtract(this.age);

		//find our new temperature
		this.surfaceTemperature = Cosmology.linearInterpolation(this.startedPhaseAge, endOfLife, this.startedPhaseTemperature, targetTemperature, nextDate);

		//recalculate the radius
		this.radius = this.calcElectronDegenerateRadius();

		//age the star
		this.age = nextDate;

		//proceed to next sequence
		if(nextDate.compareTo(endOfLife) >= 0){
			this.setStartingPhaseValues();

			this.starType = EnumStarType.WHITE_DWARF;

			this.addHistory(this.birthDate, timeAdvance, this.age, "Evolved into white dwarf");
		}

		//do another tick as we finished this stage early
		if(rolloverDelta.compareTo(BigDecimal.ZERO) > 0){
			this.tick(rolloverDelta);
			return;
		}
	}

	/**
	 * A white dwarf undergoes no fusion and slowly decays
	 * 
	 * @param delta the amount of stellar time that has passed since the last tick, in years
	 */
	private void tickWhiteDwarf(final BigDecimal delta){
		BigDecimal endOfLife = this.startedPhaseAge.add(this.calcTimeAsWhiteDwarf());
		BigDecimal targetTemperature = this.getBlackDwarfTemperature();
		BigDecimal targetLuminosity = this.getBlackDwarfLuminosity();
		BigDecimal nextDate = this.age.add(delta);
		BigDecimal rolloverDelta = BigDecimal.ZERO;

		//will we roll over this tick?
		if(nextDate.compareTo(endOfLife) > 0){
			rolloverDelta = nextDate.subtract(endOfLife);
			nextDate = endOfLife;
		}

		BigDecimal timeAdvance = nextDate.subtract(this.age);

		//find our new luminosity
		this.luminosity = Cosmology.exponentialInterpolation(this.startedPhaseAge, endOfLife, this.startedPhaseLuminosity, targetLuminosity, nextDate, new BigDecimal("0.5"));

		//find our new temperature
		this.surfaceTemperature = Cosmology.exponentialInterpolation(this.startedPhaseAge, endOfLife, this.startedPhaseTemperature, targetTemperature, nextDate, new BigDecimal("-0.5"));

		//re-calculate radius
		this.radius = this.calcElectronDegenerateRadius();

		//age the star
		this.age = nextDate;

		//proceed to next sequence
		if(nextDate.compareTo(endOfLife) >= 0){
			this.setStartingPhaseValues();

			this.starType = EnumStarType.BLACK_DWARF;

			this.addHistory(this.birthDate, timeAdvance, this.age, "Died as a black dwarf");
		}

		//do another tick as we finished this stage early
		if(rolloverDelta.compareTo(BigDecimal.ZERO) > 0){
			this.tick(rolloverDelta);
			return;
		}
	}

	/**
	 * A black dwarf is pretty much a dead star
	 * 
	 * @param delta the amount of stellar time that has passed since the last tick, in years
	 */
	private void tickBlackDwarf(final BigDecimal delta){
		BigDecimal endOfLife = this.startedPhaseAge.add(this.calcTimeAsBlackDwarf());
		BigDecimal targetTemperature = this.getSpaceTemperature();
		BigDecimal targetLuminosity = BigDecimal.ZERO;
		BigDecimal nextDate = this.age.add(delta);

		//will we roll over this tick?
		if(nextDate.compareTo(endOfLife) > 0){
			nextDate = endOfLife;
		}

		//find our new luminosity
		if(nextDate.compareTo(endOfLife) <= 0){
			this.luminosity = Cosmology.linearInterpolation(this.startedPhaseAge, endOfLife, this.startedPhaseLuminosity, targetLuminosity, nextDate);

			//find our new temperature
			this.surfaceTemperature = Cosmology.linearInterpolation(this.startedPhaseAge, endOfLife, this.startedPhaseTemperature, targetTemperature, nextDate);
		}

		//age the star
		this.age = this.age.add(delta);
	}

	/**
	 * Calculates the luminosity of a star at birth
	 * 
	 * @param mass the mass of the star in kilograms
	 * @return the luminosity of the star in watts
	 */
	public BigDecimal calcBirthLuminosity(){
		return Cosmology.SOLAR_LUMINOSITY.multiply(new BigDecimal("46.0654687347").multiply(Cosmology.pow(this.startedPhaseMass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND), new BigDecimal("2.254768727")), Star.STELLAR_RND), Star.STELLAR_RND);
	}

	/**
	 * Calculates the radius of the star in meters using main sequence as a model
	 * 
	 * @return the radius of the star in meters
	 */
	public BigDecimal calcMainSequenceRadius(){
		if(this.starType == EnumStarType.BROWN_DWARF){
			return Cosmology.pow(this.luminosity.divide(BigDecimal.valueOf(4).multiply(BigDecimal.valueOf(Math.PI), Star.STELLAR_RND).multiply(Cosmology.STEFAN_BOLTZMANN, Star.STELLAR_RND).multiply(this.surfaceTemperature.pow(4), Star.STELLAR_RND), Star.STELLAR_RND),new BigDecimal("0.45"));
		} else {
			return Cosmology.pow(this.luminosity.divide(BigDecimal.valueOf(4).multiply(BigDecimal.valueOf(Math.PI), Star.STELLAR_RND).multiply(Cosmology.STEFAN_BOLTZMANN, Star.STELLAR_RND).multiply(this.surfaceTemperature.pow(4), Star.STELLAR_RND), Star.STELLAR_RND),new BigDecimal("0.5"));
		}
	}

	/**
	 * Calculates the radius of the star in meters using electron degenerate matter as a model
	 * 
	 * @return the radius of the star in meters
	 */
	public BigDecimal calcElectronDegenerateRadius(){
		return new BigDecimal("0.010").multiply(Cosmology.pow(this.mass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND), new BigDecimal("-0.333333333"))).multiply(Cosmology.SOLAR_RADIUS, Star.STELLAR_RND);
	}

	/**
	 * Calculates the radius of the star in meters using electron degenerate matter as a model
	 * 
	 * @return the radius of the star in meters
	 */
	public BigDecimal calcNeutronDegenerateRadius(){
		return new BigDecimal("0.00010").multiply(Cosmology.pow(this.mass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND), new BigDecimal("-0.333333333"))).multiply(Cosmology.SOLAR_RADIUS, Star.STELLAR_RND);
	}

	/**
	 * Calculates the Schwarzschild radius
	 * 
	 * @return the radius of the star in meters
	 */
	public BigDecimal calcSchwarzschildRadius(){
		return new BigDecimal("2").multiply(Cosmology.GRAVITATIONAL_CONST, Star.STELLAR_RND).multiply(this.mass, Star.STELLAR_RND).divide(Cosmology.LIGHT_SPEED.pow(2), Star.STELLAR_RND);
	}

	/**
	 * Calculates the temperature of a star in kelvin
	 * 
	 * @return the temperature of the surface in kevlin
	 */
	public BigDecimal calcTemperature(){
		return Cosmology.pow(Cosmology.pow(this.startedPhaseMass.divide(this.mass, Star.STELLAR_RND), new BigDecimal("2.5")), new BigDecimal("0.25")).multiply(this.startedPhaseTemperature, Star.STELLAR_RND);
	}

	/**
	 * Calculates the luminosity of a main sequence star in watts from the mass
	 * 
	 * @return the mass in kilograms
	 */
	public BigDecimal getMainSequenceLuminosityFromMass(){
		BigDecimal solarMass = this.startedPhaseMass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND);

		if(solarMass.compareTo(new BigDecimal("0.08")) < 0){
			return new BigDecimal("0.05").multiply(Cosmology.pow(this.startedPhaseMass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND), new BigDecimal("1.2")).multiply(Cosmology.SOLAR_LUMINOSITY, Star.STELLAR_RND), Star.STELLAR_RND);
		}else if(solarMass.compareTo(new BigDecimal("0.43")) < 0){
			return new BigDecimal("0.23").multiply(Cosmology.pow(this.startedPhaseMass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND), new BigDecimal("2.3")).multiply(Cosmology.SOLAR_LUMINOSITY, Star.STELLAR_RND), Star.STELLAR_RND);
		}else if(solarMass.compareTo(new BigDecimal("2.00")) < 0){
			return Cosmology.pow(this.startedPhaseMass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND), new BigDecimal("4.0")).multiply(Cosmology.SOLAR_LUMINOSITY, Star.STELLAR_RND);
		}else if(solarMass.compareTo(new BigDecimal("20.0")) < 0){
			return new BigDecimal("1.5").multiply(Cosmology.pow(this.startedPhaseMass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND), new BigDecimal("3.5")).multiply(Cosmology.SOLAR_LUMINOSITY, Star.STELLAR_RND), Star.STELLAR_RND);
		}else{
			return BigDecimal.valueOf(42).multiply(Cosmology.pow(this.startedPhaseMass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND), new BigDecimal("2.5")).multiply(Cosmology.SOLAR_LUMINOSITY, Star.STELLAR_RND), Star.STELLAR_RND);
		}
	}

	/**
	 * Calculates the luminosity of a sub-giant star in watts from the mass
	 * 
	 * @return the mass in kilograms
	 */
	public BigDecimal getSubGiantLuminosityFromMass(){
		BigDecimal solarMass = this.startedPhaseMass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND);
		BigDecimal luminosity = BigDecimal.ZERO;

		if(solarMass.compareTo(new BigDecimal("0.43")) < 0){
			luminosity = new BigDecimal("0.23").multiply(Cosmology.pow(this.startedPhaseMass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND), new BigDecimal("2.3")).multiply(Cosmology.SOLAR_LUMINOSITY, Star.STELLAR_RND), Star.STELLAR_RND);
		}else if(solarMass.compareTo(new BigDecimal("2.00")) < 0){
			luminosity = Cosmology.pow(this.startedPhaseMass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND), new BigDecimal("4.0")).multiply(Cosmology.SOLAR_LUMINOSITY, Star.STELLAR_RND);
		}else if(solarMass.compareTo(new BigDecimal("20.0")) < 0){
			luminosity = new BigDecimal("1.5").multiply(Cosmology.pow(this.startedPhaseMass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND), new BigDecimal("3.5")).multiply(Cosmology.SOLAR_LUMINOSITY, Star.STELLAR_RND), Star.STELLAR_RND);
		}else{
			luminosity = BigDecimal.valueOf(42).multiply(Cosmology.pow(this.startedPhaseMass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND), new BigDecimal("2.5")).multiply(Cosmology.SOLAR_LUMINOSITY, Star.STELLAR_RND), Star.STELLAR_RND);
		}

		return luminosity.multiply(BigDecimal.valueOf(4500), Star.STELLAR_RND);
	}

	/**
	 * Calculates the luminosity of a white dwarf in watts from its current phase luminosity
	 * 
	 * @return the mass in kilograms
	 */
	public BigDecimal getWhiteDwarfLuminosity(){
		return this.startedPhaseLuminosity.multiply(new BigDecimal("0.01"), Star.STELLAR_RND);
	}

	/**
	 * Calculates the luminosity of a neutron star in watts from its current phase luminosity
	 * 
	 * @return the mass in kilograms
	 */
	public BigDecimal getNeutronStarLuminosity(){
		return this.startedPhaseLuminosity.multiply(new BigDecimal("0.0001"), Star.STELLAR_RND);
	}

	/**
	 * Calculates the luminosity of a black dwarf in watts
	 * 
	 * @return the mass in kilograms
	 */
	public BigDecimal getBlackDwarfLuminosity(){
		return new BigDecimal("1E-5");
	}

	/**
	 * Calculates the temperature of a main sequence star in kelvin from the mass
	 * 
	 * @return the temperature in kelvin
	 */
	public BigDecimal getMainSequenceTemperatureFromLuminosity(final BigDecimal luminosity){
		return new BigDecimal("6118.1162283755").multiply(Cosmology.pow(luminosity.divide(Cosmology.SOLAR_LUMINOSITY, Star.STELLAR_RND), new BigDecimal("0.1349483096")), Star.STELLAR_RND);
	}

	/**
	 * Calculates the temperature of a subgiant in kelvin from the mass
	 * 
	 * @return the temperature in kelvin
	 */
	public BigDecimal getSubgiantTemperatureFromLuminosity(final BigDecimal luminosity){
		return BigDecimal.valueOf(2500).multiply(Cosmology.pow(luminosity.divide(Cosmology.SOLAR_LUMINOSITY, Star.STELLAR_RND), new BigDecimal("0.0631514365")), Star.STELLAR_RND);
	}

	/**
	 * Returns the temperature of a super giant as a function of time
	 * 
	 * @return the temperature in kelvin
	 */
	public BigDecimal getSuperGiantLoopTemperature(final BigDecimal deltaT){
		return this.surfaceTemperature.add(BigDecimal.valueOf(4000).multiply(BigDecimal.valueOf(Math.cos(1/(32000/Math.PI) * deltaT.doubleValue()))), Star.STELLAR_RND);
	}

	/**
	 * The temperature of a new wolf-rayet star
	 * @return the temperature in kelvin
	 */
	public BigDecimal getWolfRayetTemperature(){
		return this.startedPhaseTemperature.multiply(BigDecimal.valueOf((1.0F + this.randomNoise(1234)) * 2));
	}

	/**
	 * Returns the temperature of a new white dwarf
	 * 
	 * @return the temperature in kelvin
	 */
	public BigDecimal getWhiteDwarfTemperature(){
		return BigDecimal.valueOf(28000 + (1.0F + this.randomNoise(32)) * 4000);
	}

	/**
	 * Returns the temperature of a new neutron star
	 * 
	 * @return the temperature in kelvin
	 */
	public BigDecimal getNeutronStarTemperature(){
		return BigDecimal.valueOf(22000 + (1.0F + this.randomNoise(2246)) * 4000);
	}

	/**
	 * Returns the temperature of a new brown dwarf
	 * 
	 * @return the temperature in kelvin
	 */
	public BigDecimal getBrownDwarfBirthTemperature(){
		return BigDecimal.valueOf(300 + (1.0F + this.randomNoise(942)) * 1000);
	}

	/**
	 * Returns the temperature of a new black dwarf
	 * 
	 * @return the temperature in kelvin
	 */
	public BigDecimal getBlackDwarfTemperature(){
		return BigDecimal.valueOf(600D +  1200D * (1.0F + this.randomNoise(698)));
	}

	/**
	 * Returns the temperature of space
	 * 
	 * @return the temperature in kelvin
	 */
	public BigDecimal getSpaceTemperature(){
		return new BigDecimal("2.725");
	}

	/**
	 * Calculates how long a star stays a protostar, in years
	 * 
	 * @return the time in years
	 */
	public BigDecimal calcTimeAsProtostar(){
		return new BigDecimal("37190503.1847951").multiply(Cosmology.pow(this.startedPhaseMass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND), new BigDecimal("-2.3519309281")), Star.STELLAR_RND);
	}

	/**
	 * Calculates how long a star stays a main sequence star, in years
	 * 
	 * @return the time in years
	 */
	public BigDecimal calcTimeAsMainSequence(){
		return BigDecimal.valueOf(10000000000L).multiply(Cosmology.pow(this.startedPhaseMass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND), new BigDecimal("-2.5")), Star.STELLAR_RND);
	}

	/**
	 * Calculates how long a star stays a sub giant, in years
	 * 
	 * @return the time in years
	 */
	public BigDecimal calcTimeAsSubGiant(){
		return BigDecimal.valueOf(1000000000).multiply(Cosmology.pow(this.startedPhaseMass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND), new BigDecimal("-1.1")), Star.STELLAR_RND);
	}

	/**
	 * Calculates how long a star stays a giant, in years
	 * 
	 * @return the time in years
	 */
	public BigDecimal calcTimeAsGiant(){
		return BigDecimal.valueOf(360000000).multiply(Cosmology.pow(this.startedPhaseMass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND), new BigDecimal("-1.3")), Star.STELLAR_RND);
	}

	/**
	 * Calculates how long a star stays a super giant, in years
	 * 
	 * @return the time in years
	 */
	public BigDecimal calcTimeAsSuperGiant(){
		return BigDecimal.valueOf(550000).multiply(Cosmology.pow(this.startedPhaseMass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND), new BigDecimal("-0.6")), Star.STELLAR_RND);
	}

	/**
	 * Calculates how long a star stays a planetary nebula, in years
	 * 
	 * @return the time in years
	 */
	public BigDecimal calcTimeAsPlanetaryNebula(){
		return BigDecimal.valueOf(50000D + 50000D * (1.0F + this.randomNoise(205)));
	}

	/**
	 * Calculates how long a star stays a super nova, in years
	 * 
	 * @return the time in years
	 */
	public BigDecimal calcTimeAsSuperNova(){
		return BigDecimal.valueOf(25000D + 25000D * (1.0F + this.randomNoise(9574)));
	}

	/**
	 * Calculates how long a star stays a white dwarf, in years
	 * 
	 * @return the time in years
	 */
	public BigDecimal calcTimeAsWhiteDwarf(){
		return BigDecimal.valueOf(1E12D + 1E12D * (1.0F + this.randomNoise(541)));
	}

	/**
	 * Calculates how long a star stays a blue dwarf, in years
	 * 
	 * @return the time in years
	 */
	public BigDecimal calcTimeAsBlueDwarf(){
		return BigDecimal.valueOf(1E12D + 1E12D * (1.0F + this.randomNoise(4210)));
	}

	/**
	 * Calculates how long a star stays a neutron star, in years
	 * 
	 * @return the time in years
	 */
	public BigDecimal calcTimeAsNeutronStar(){
		return BigDecimal.valueOf(1E15D + 1E15D * (1.0F + this.randomNoise(4384)));
	}

	/**
	 * Calculates how long a star stays a pulsar star, in years
	 * 
	 * @return the time in years
	 */
	public BigDecimal calcTimeAsPulsar(){
		return BigDecimal.valueOf(10000000 + 45000000 * (1.0F + this.randomNoise(56467)));
	}

	/**
	 * Calculates how long a star stays a black dwarf, in years
	 * 
	 * @return the time in years
	 */
	public BigDecimal calcTimeAsBlackDwarf(){
		return BigDecimal.valueOf(1E13D);
	}

	/**
	 * Returns the mass of the star after losing some to solar wind in
	 * the giant stage
	 * 
	 * @return the mass in kilograms
	 */
	public BigDecimal getGiantMassAfterLoss(){
		return this.startedPhaseMass.multiply(BigDecimal.valueOf(1 - (1.0F + this.randomNoise(13)) * 0.25), Star.STELLAR_RND);
	}

	/**
	 * Returns the mass of the star after losing some to solar wind in
	 * the super giant stage
	 * 
	 * @return the mass in kilograms
	 */
	public BigDecimal getSuperGiantMassAfterLoss(){
		return this.startedPhaseMass.multiply(BigDecimal.valueOf(1 - (1.0F + this.randomNoise(8432)) * 0.35), Star.STELLAR_RND);
	}

	/**
	 * Returns the mass of the star after it becomes a planetary nebula
	 * 
	 * @return the mass in kilograms
	 */
	public BigDecimal getWhiteDwarfBirthMass(){
		return this.startedPhaseMass.multiply(BigDecimal.valueOf((1.2F + this.randomNoise(54)) / 2.2F), Star.STELLAR_RND).min(Cosmology.CHANDRASEKHAR_LIMIT);
	}

	/**
	 * Returns the mass of the star after it becomes a super nova
	 * 
	 * @return the mass in kilograms
	 */
	public BigDecimal getNeutronStarBirthMass(){
		return this.startedPhaseMass.multiply(BigDecimal.valueOf((1.0F + this.randomNoise(14999)) / 2.0F), Star.STELLAR_RND).max(Cosmology.CHANDRASEKHAR_LIMIT);
	}

	/**
	 * The spectral class of this star
	 * 
	 * @return returns a string representing the spectral class of the star
	 */
	public String getSpectralClass(){
		StringBuilder sb = new StringBuilder();

		if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(33000)) >= 0){
			sb.append("O");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(30700)) >= 0){
			sb.append("B0");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(28400)) >= 0){
			sb.append("B1");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(26100)) >= 0){
			sb.append("B2");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(23800)) >= 0){
			sb.append("B3");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(21500)) >= 0){
			sb.append("B4");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(19200)) >= 0){
			sb.append("B5");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(16900)) >= 0){
			sb.append("B6");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(14600)) >= 0){
			sb.append("B7");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(12300)) >= 0){
			sb.append("B8");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(10000)) >= 0){
			sb.append("B9");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(9750)) >= 0){
			sb.append("A0");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(9500)) >= 0){
			sb.append("A1");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(9250)) >= 0){
			sb.append("A2");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(9000)) >= 0){
			sb.append("A3");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(8750)) >= 0){
			sb.append("A4");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(8500)) >= 0){
			sb.append("A5");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(8250)) >= 0){
			sb.append("A6");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(8000)) >= 0){
			sb.append("A7");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(7750)) >= 0){
			sb.append("A8");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(7500)) >= 0){
			sb.append("A9");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(7350)) >= 0){
			sb.append("F0");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(7200)) >= 0){
			sb.append("F1");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(7050)) >= 0){
			sb.append("F2");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(6900)) >= 0){
			sb.append("F3");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(6750)) >= 0){
			sb.append("F4");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(6600)) >= 0){
			sb.append("F5");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(6450)) >= 0){
			sb.append("F6");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(6300)) >= 0){
			sb.append("F7");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(6150)) >= 0){
			sb.append("F8");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(6000)) >= 0){
			sb.append("F9");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(5920)) >= 0){
			sb.append("G0");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(5840)) >= 0){
			sb.append("G1");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(5760)) >= 0){
			sb.append("G2");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(5680)) >= 0){
			sb.append("G3");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(5600)) >= 0){
			sb.append("G4");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(5520)) >= 0){
			sb.append("G5");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(5440)) >= 0){
			sb.append("G6");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(5360)) >= 0){
			sb.append("G7");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(5280)) >= 0){
			sb.append("G8");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(5200)) >= 0){
			sb.append("G9");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(5050)) >= 0){
			sb.append("K0");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(4900)) >= 0){
			sb.append("K1");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(4750)) >= 0){
			sb.append("K2");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(4600)) >= 0){
			sb.append("K3");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(4450)) >= 0){
			sb.append("K4");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(4300)) >= 0){
			sb.append("K5");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(4150)) >= 0){
			sb.append("K6");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(4000)) >= 0){
			sb.append("K7");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(3850)) >= 0){
			sb.append("K8");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(3700)) >= 0){
			sb.append("K9");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(3530)) >= 0){
			sb.append("M0");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(3360)) >= 0){
			sb.append("M1");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(3190)) >= 0){
			sb.append("M2");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(3020)) >= 0){
			sb.append("M3");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(2850)) >= 0){
			sb.append("M4");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(2680)) >= 0){
			sb.append("M5");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(2510)) >= 0){
			sb.append("M6");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(2340)) >= 0){
			sb.append("M7");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(2170)) >= 0){
			sb.append("M8");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(2000)) >= 0){
			sb.append("M9");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(1300)) >= 0){
			sb.append("L");
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(700)) >= 0){
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
				+ "        age: " + Cosmology.getFormattedStellarAge(this.age) + "\n"
				+ "       mass: " + this.mass + "  Solar " + this.mass.divide(Cosmology.SOLAR_MASS, Star.STELLAR_RND) + "\n"
				+ " luminosity: " + this.luminosity + "  Solar " + this.luminosity.divide(Cosmology.SOLAR_LUMINOSITY, Star.STELLAR_RND) + "\n"
				+ "     radius: " + this.radius + "  Solar " + this.radius.divide(Cosmology.SOLAR_RADIUS, Star.STELLAR_RND) + "\n"
				+ "       temp: " + Math.round(this.surfaceTemperature.doubleValue()) + " K   " + this.getStarColorName() + "\n"
				+ "      class: " + this.getSpectralClass() + "\n"
				+ "    gravity: " + this.calculateGravity() + "\n";
	}

	/**
	 * Prints out the history of the star
	 * 
	 * @return
	 */
	public String getHistory(){
		StringBuilder sbuf = new StringBuilder();

		sbuf.append("History for " + this.starType + " (" + this.getSpectralClass() + ")\n\n");

		for(String history:this.historyLog){
			sbuf.append(history + "\n");
		}

		return sbuf.toString();
	}


	/**
	 * Adds history to this star
	 * 
	 * @param starDate the date when the history is added
	 * @param delta the amount of time that has passed
	 * @param age the age of the star
	 * @param text the message to add
	 */
	private void addHistory(final StarDate starDate, final BigDecimal delta, final BigDecimal age, final String text){
		StarDate d = new StarDate(starDate.getSecondsSinceBigBang());
		d.addYears(age.toBigInteger());

		this.historyLog.add(StarDate.getFormattedStarDate(d)
				+ "\n    Age " + Cosmology.getFormattedStellarAge(age)
				+ "\n    " + text + "\n");
	}

	/**
	 * Sets the phase start values to the current ones
	 */
	private void setStartingPhaseValues(){
		this.startedPhaseAge = new BigDecimal(this.age.toString());
		this.startedPhaseLuminosity = new BigDecimal(this.luminosity.toString());
		this.startedPhaseMass = new BigDecimal(this.mass.toString());
		this.startedPhaseTemperature = new BigDecimal(this.surfaceTemperature.toString());
	}

	/**
	 * Random noise generator
	 *
	 * @param modifier an int to seed the random function
	 * @return a float between -1.0 and 1.0
	 */
	private float randomNoise(final int modifier){
		int h = modifier * 113;
		h += this.seed;
		h = h << 13 ^ h;
		return 1.0F - (h * (h * h * 19531 + 1046527) + 1073807359 & 0x7fffffff) / 1073741824.0F;
	}

	/*
	 * Calculates the gravitational force of the star
	 */
	private BigDecimal calculateGravity(){
		return Cosmology.GRAVITATIONAL_CONST.multiply(this.mass, Star.STELLAR_RND).divide(this.radius.pow(2), Star.STELLAR_RND);
	}

	/**
	 * Returns the name of the color of the star
	 * @return the name of the color of the star
	 */
	private String getStarColorName(){
		if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(33000)) >= 0){
			return "blue";
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(10000)) >= 0){
			return "blue-white";
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(7500)) >= 0){
			return "white";
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(6000)) >= 0){
			return "yellow-white";
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(5200)) >= 0){
			return "yellow";
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(3700)) >= 0){
			return "orange";
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(2000)) >= 0){
			return "red";
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(1300)) >= 0){
			return "red-brown";
		}else if(this.surfaceTemperature.compareTo(BigDecimal.valueOf(700)) >= 0){
			return "brown";
		}else{
			return "dark brown";
		}
	}
}
