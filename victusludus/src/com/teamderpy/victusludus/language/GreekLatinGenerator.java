
package com.teamderpy.victusludus.language;

import java.util.Random;

/**
 * Creates psuedo-latin-greek words
 * @author Josh
 * 
 */
public class GreekLatinGenerator implements IWordGenerator {
	private Random rand;

	public GreekLatinGenerator () {
		this.rand = new Random();
	}

	public GreekLatinGenerator (final long seed) {
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
		if (this.rand.nextBoolean()) {
			return GreekGenerator.PREFIX_LIST[this.rand.nextInt(GreekGenerator.PREFIX_LIST.length)];
		}

		return LatinGenerator.PREFIX_LIST[this.rand.nextInt(LatinGenerator.PREFIX_LIST.length)];
	}

	private String getRoot () {
		if (this.rand.nextBoolean()) {
			return GreekGenerator.ROOT_LIST[this.rand.nextInt(GreekGenerator.ROOT_LIST.length)];
		}

		return LatinGenerator.ROOT_LIST[this.rand.nextInt(LatinGenerator.ROOT_LIST.length)];
	}

	private String getSuffix () {
		if (this.rand.nextBoolean()) {
			return GreekGenerator.SUFFIX_LIST[this.rand.nextInt(GreekGenerator.SUFFIX_LIST.length)];
		}

		return LatinGenerator.SUFFIX_LIST[this.rand.nextInt(LatinGenerator.SUFFIX_LIST.length)];
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
