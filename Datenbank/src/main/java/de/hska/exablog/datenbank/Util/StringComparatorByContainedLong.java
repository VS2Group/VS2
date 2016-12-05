package de.hska.exablog.datenbank.Util;

import java.util.Comparator;

/**
 * Created by Angelo on 03.12.2016.
 */
public class StringComparatorByContainedLong implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		return this.mapLongToInt(Long.parseLong(o1) - Long.parseLong(o2));
	}

	private int mapLongToInt (long l) {
		if (l > Integer.MAX_VALUE) return Integer.MAX_VALUE;
		if (l < Integer.MIN_VALUE) return Integer.MIN_VALUE;
		return (int) l;
	}

}