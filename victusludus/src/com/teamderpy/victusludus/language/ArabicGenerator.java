
package com.teamderpy.victusludus.language;

import java.util.Random;

/**
 * Creates pseudo-greek words
 * @author Josh
 * 
 */
public class ArabicGenerator implements IWordGenerator {
	protected final static String[] PREFIX_LIST = {"Ac", "Ach", "Ad", "Al", "Adi", "Alt", "Ar", "Bat", "Ben", "Az", "Au", "As",
		"Sa'd", "Bo", "Ca", "Ce", "Cu", "Da", "Den", "Di", "Du", "Dz", "Ed", "El", "En", "Er", "Fu", "Gi", "Go", "Ha", "He", "Ho",
		"Iz", "Jab", "Ka", "Ke", "Ki", "Ko", "Ku", "Le", "Lu", "Ma", "Me", "Mi", "Mo", "Mu", "Na", "Ne", "Ni", "Nu", "Ok", "Ph",
		"Ra", "Ras", "Ra's", "Ri", "Ru", "Sa", "Se", "Sch", "She", "Sk", "Thu", "Un", "Ve", "Wa", "We", "Ye", "Za", "Zu"};
	protected final static String[] ROOT_LIST = {"--"};
	protected final static String[] SUFFIX_LIST = {"mar", "nar", "rab", "fera", "ib", "ali", "ibah", "ran", "baran", "ramin",
		"firk", "gedi", "nib", "bar", "gol", "na", "ioth", "kaid", "kes", "mak", "san", "nair", "air", "ais", "dra", "tenar",
		"kaa", "kab", "akis", "tik", "ha", "ham", "eid", "nash", "geuse", "tein", "zha", "ph", "lrai", "rsa", "bih", "neb", "eb",
		"bhe", "ban", "nin", "nif", "rai", "rud", "haut", "nah", "mal", "zar", "mam", "chab", "sath", "sym", "tar", "suta", "zar",
		"zim", "ira", "kar", "hal", "kan", "sed", "aban", "bik", "melik", "suud", "dr", "eat", "edir", "iak", "sat", "mali"};

	private Random rand;

	public ArabicGenerator () {
		this.rand = new Random();
	}

	public ArabicGenerator (final long seed) {
		this.rand = new Random(seed);
	}

	@Override
	public String getWord () {
		return LangUtil.capitalize(LangUtil.deduplicate(this.rootize(this.getRoot())));
	}

	@Override
	public String getWord (final long seed) {
		this.rand.setSeed(seed);
		return this.getWord();
	}

	private String getPrefix () {
		return ArabicGenerator.PREFIX_LIST[this.rand.nextInt(ArabicGenerator.PREFIX_LIST.length)];
	}

	private String getRoot () {
		return ArabicGenerator.ROOT_LIST[this.rand.nextInt(ArabicGenerator.ROOT_LIST.length)];
	}

	private String getSuffix () {
		return ArabicGenerator.SUFFIX_LIST[this.rand.nextInt(ArabicGenerator.SUFFIX_LIST.length)];
	}

	private String rootize (final String root) {
		String prefix = "";
		String suffix = "";

		if (root.charAt(0) == '-') {
			prefix = this.getPrefix();
		}

		if (root.charAt(root.length() - 1) == '-') {
			suffix = this.getSuffix();
		}

		return prefix + root.replace("-", "") + suffix;
	}

}
