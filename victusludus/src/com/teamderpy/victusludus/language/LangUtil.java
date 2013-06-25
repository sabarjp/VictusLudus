
package com.teamderpy.victusludus.language;

public final class LangUtil {
	public static String deduplicate (final String word) {
		return word.replaceAll("(.)\1{2,}", "\1\1");
	}

	public static String capitalize (final String word) {
		char chars[] = word.toCharArray();

		if (chars.length >= 1) {
			chars[0] = Character.toUpperCase(chars[0]);
		}

		if (chars.length >= 2) {
			for (int i = 1; i < chars.length; i++) {
				if (chars[i - 1] == ' ' || chars[i - 1] == '\'' || chars[i - 1] == '-') {
					chars[i] = Character.toUpperCase(chars[i]);
				}
			}
		}

		return String.valueOf(chars);
	}
}
