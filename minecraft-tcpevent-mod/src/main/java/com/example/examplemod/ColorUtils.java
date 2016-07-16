package com.example.examplemod;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ericbottard on 13/07/16.
 */
public class ColorUtils {

	public static Map<String, Color> COLORS = new HashMap<>();

	static {
		COLORS.put("WHITE", parse("DDDDDD"));
		COLORS.put("ORANGE", parse("DB7D3E"));
		COLORS.put("MAGENTA", parse("B350BC"));
		COLORS.put("LIGHT_BLUE", parse("6B8AC9"));
		COLORS.put("YELLOW", parse("B1A627"));
		COLORS.put("LIME", parse("41AE38"));
		COLORS.put("PINK", parse("D08499"));
		COLORS.put("GRAY", parse("404040"));
		COLORS.put("SILVER", parse("9AA1A1"));
		COLORS.put("CYAN", parse("2E6E89"));
		COLORS.put("PURPLE", parse("7E3DB5"));
		COLORS.put("BLUE", parse("2E388D"));
		COLORS.put("BROWN", parse("4F321F"));
		COLORS.put("GREEN", parse("35461B"));
		COLORS.put("RED", parse("963430"));
		COLORS.put("BLACK", parse("191616"));
	}

	private static Color parse(String colorString) {
		int red = Integer.parseInt(colorString.substring(0, 2), 16);
		int green = Integer.parseInt(colorString.substring(2, 4), 16);
		int blue = Integer.parseInt(colorString.substring(4, 6), 16);
		return new Color(red, green, blue);
	}
}
