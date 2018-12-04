package com.syx.sboot.common.utils.extend;

import org.springframework.util.Assert;

import java.util.*;

public class StringUtils {
	public static final String[] EMPTY_STRINGS = new String[0];

	public static String strTruncate(String source, int len, String delim) {
		if (source == null) {
			return null;
		}

		int alen = source.getBytes().length;
		if (len > 0) {
			if (alen <= len)
				return source;
			int byteLen;
			int stop;
			int start = stop = byteLen = 0;
			while (byteLen <= len) {
				if (source.substring(stop, stop + 1).getBytes().length == 1)
					byteLen++;
				else {
					byteLen += 2;
				}
				stop++;
			}
			if (alen > len) {
				StringBuilder sb = new StringBuilder(source.substring(start, stop - 1));
				sb.append(delim);
			}
			return source.substring(start, stop - 1);
		}
		return source;
	}

	public static String toUpperCase(String str, int index) {
		StringBuilder sb = new StringBuilder(str);
		char s = Character.toUpperCase(sb.charAt(index - 1));
		sb.setCharAt(index - 1, s);
		return sb.toString();
	}

	public static String toLowerCase(String str, int index) {
		StringBuilder sb = new StringBuilder(str);
		char s = Character.toLowerCase(sb.charAt(index - 1));
		sb.setCharAt(index - 1, s);
		return sb.toString();
	}

	public static String toString(Object param) {
		if (param == null) {
			return null;
		}
		return param.toString();
	}

	public static boolean isEmpty(String string) {
		return (string == null) || ("".equals(string.trim()));
	}

	public static boolean isNotEmpty(String string) {
		return (string != null) && (!"".equals(string.trim()));
	}

	public static String checkEmptyString(String string, String defaultValue) {
		if (isEmpty(string)) {
			return defaultValue;
		}
		return string;
	}

	public static boolean hasLength(CharSequence charSequence) {
		return (charSequence != null) && (charSequence.length() > 0);
	}

	public static boolean hasText(CharSequence charSequence) {
		if (!hasLength(charSequence)) {
			return false;
		}
		int strLen = charSequence.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(charSequence.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static int occurrence(String string, String s, int beginIndex, int endIndex) {
		if ((string == null) || (isEmpty(s))) {
			return 0;
		}
		return occurrence0(string, s, beginIndex, endIndex);
	}

	public static int occurrence(String string, String s, int beginIndex) {
		if ((string == null) || (isEmpty(s))) {
			return 0;
		}
		return occurrence0(string, s, beginIndex, string.length());
	}

	public static int occurrence(String string, String s) {
		if ((string == null) || (isEmpty(s))) {
			return 0;
		}
		return occurrence0(string, s, 0, string.length());
	}

	private static int occurrence0(String string, String s, int beginIndex, int endIndex) {
		if ((endIndex == 0) && (beginIndex == 0)) {
			return 0;
		}
		Assert.isTrue((beginIndex >= 0) && (beginIndex < endIndex));
		Assert.isTrue(endIndex <= string.length());
		int i = 0;
		while (true) {
			beginIndex = string.indexOf(s, beginIndex);
			if ((beginIndex == -1) || (beginIndex >= endIndex)) {
				break;
			}
			i++;
			beginIndex += s.length();
		}
		return i;
	}

	public static int consecutive(String string, String s) {
		if ((string == null) || (isEmpty(s))) {
			return 0;
		}
		return consecutive0(string, s, 0, string.length());
	}

	public static int consecutive(String string, String s, int beginIndex) {
		if ((string == null) || (isEmpty(s))) {
			return 0;
		}
		return consecutive0(string, s, beginIndex, string.length());
	}

	public static int consecutive(String string, String s, int beginIndex, int endIndex) {
		if ((string == null) || (isEmpty(s))) {
			return 0;
		}
		return consecutive0(string, s, beginIndex, endIndex);
	}

	private static int consecutive0(String string, String s, int beginIndex, int endIndex) {
		if ((endIndex == 0) && (beginIndex == 0)) {
			return 0;
		}
		Assert.isTrue((beginIndex >= 0) && (beginIndex < endIndex));
		Assert.isTrue(endIndex <= string.length());
		int i = 0;
		while (true) {
			beginIndex = string.indexOf(s, beginIndex);
			if ((beginIndex == -1) || (beginIndex >= endIndex))
				break;
			if ((beginIndex != endIndex - 1) && (s.charAt(0) != string.charAt(beginIndex + 1))) {
				i++;
				break;
			}
			i++;
			beginIndex += s.length();
		}
		return i;
	}

	public static int lastConsecutive(String string, String s) {
		if ((string == null) || (isEmpty(s))) {
			return 0;
		}
		return lastConsecutive0(string, s, string.length(), 0);
	}

	public static int lastConsecutive(String string, String s, int lastBeginIndex) {
		if ((string == null) || (isEmpty(s))) {
			return 0;
		}
		return lastConsecutive0(string, s, lastBeginIndex, 0);
	}

	public static int lastConsecutive(String string, String s, int lastBeginIndex, int lastEndIndex) {
		if ((string == null) || (isEmpty(s))) {
			return 0;
		}
		return lastConsecutive0(string, s, lastBeginIndex, lastEndIndex);
	}

	private static int lastConsecutive0(String string, String s, int lastBeginIndex, int lastEndIndex) {
		if ((lastBeginIndex == 0) && (lastEndIndex == 0)) {
			return 0;
		}
		Assert.isTrue((lastBeginIndex <= string.length()) && (lastBeginIndex > lastEndIndex));
		Assert.isTrue(lastEndIndex >= 0);
		int i = 0;
		StringBuilder builder = new StringBuilder(string);
		builder.delete(0, lastEndIndex);
		char sEndChar = s.charAt(s.length() - 1);
		while (true) {
			lastBeginIndex = builder.lastIndexOf(s, --lastBeginIndex);
			if (lastBeginIndex == -1)
				break;
			if (lastBeginIndex == 0) {
				i++;
				break;
			}
			if (sEndChar != builder.charAt(lastBeginIndex - 1)) {
				i++;
				break;
			}
			i++;
			builder.delete(lastBeginIndex - s.length(), lastBeginIndex);
		}
		return i;
	}

	public static int indexOf(String string, String s, int count) {
		if (string == null) {
			return -1;
		}
		Assert.isTrue(count > 0);
		int index = -s.length();
		for (int i = 0; i < count; i++) {
			index = string.indexOf(s, index + s.length());
			if (index == -1) {
				return -1;
			}
		}
		return index;
	}

	public static int lastIndexOf(String string, String s, int count) {
		if (string == null) {
			return -1;
		}
		Assert.isTrue(count > 0);
		int index = string.length();
		for (int i = 0; i < count; i++) {
			index = string.lastIndexOf(s, index - 1);
			if (index == -1) {
				return -1;
			}
		}
		return index;
	}

	public static String substring(String string, int beginIndex) {
		if (string == null) {
			return null;
		}
		return new String(string.substring(beginIndex));
	}

	public static String substring(String string, int beginIndex, int endIndex) {
		if (string == null) {
			return null;
		}
		return new String(string.substring(beginIndex, endIndex));
	}

	public static Set<String> stringSplitToSet(String value, String regex) {
		return stringSplitToSet(value, regex, true);
	}

	public static Set<String> stringSplitToSet(String value, String regex, boolean ignoreEmptyTokens) {
		if (value == null) {
			return new LinkedHashSet(6);
		}
		Set set = new LinkedHashSet();
		stringSplitToCollection(value, regex, set, ignoreEmptyTokens);
		return set;
	}

	public static List<String> stringSplitToList(String value, String regex) {
		return stringSplitToList(value, regex, true);
	}

	public static List<String> stringSplitToList(String value, String regex, boolean ignoreEmptyTokens) {
		if (value == null) {
			return new ArrayList(5);
		}
		List list = new ArrayList();
		stringSplitToCollection(value, regex, list, ignoreEmptyTokens);
		return list;
	}

	private static void stringSplitToCollection(String value, String regex, Collection<String> set, boolean ignoreEmptyTokens) {
		String[] split = value.split(regex);
		for (String aSplit : split)
			if ((!ignoreEmptyTokens) || (aSplit.length() > 0))
				set.add(aSplit);
	}

	public static String[] stringSplitToArray(String value, String regex) {
		return stringSplitToArray(value, regex, true);
	}

	public static String[] stringSplitToArray(String value, String regex, boolean ignoreEmptyTokens) {
		if (value == null) {
			return EMPTY_STRINGS;
		}
		List list = stringSplitToList(value, regex, ignoreEmptyTokens);
		if (list.isEmpty()) {
			return EMPTY_STRINGS;
		}
		return (String[]) list.toArray(new String[list.size()]);
	}
}