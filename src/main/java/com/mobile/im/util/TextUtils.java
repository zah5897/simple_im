package com.mobile.im.util;

public final class TextUtils {

	public static boolean isEmpty(final CharSequence s) {
		if (s == null) {
			return true;
		}
		return s.length() == 0;
	}

	public static boolean isNotEmpty(final CharSequence s) {
		return !isEmpty(s);
	}

	public static boolean isBlank(final CharSequence s) {
		if (s == null) {
			return true;
		}
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isWhitespace(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(String userName) {
		return !isBlank(userName);
	}
}