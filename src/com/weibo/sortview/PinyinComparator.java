package com.weibo.sortview;

import java.util.Comparator;

import com.weibo.card.WeatherCard;
public class PinyinComparator implements Comparator<WeatherCard> {
	public int compare(WeatherCard o1, WeatherCard o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
