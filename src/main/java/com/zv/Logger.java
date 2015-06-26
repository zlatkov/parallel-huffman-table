package com.zv;

/**
 * Created by zlatkov on 6/27/15.
 */
public class Logger {
	private static boolean quietMode = false;

	public static void setQuietMode(boolean mode) {
		quietMode = mode;
	}

	public static void log(String data) {
		if (!quietMode) {
			System.out.println(data);
		}
	}

	public static void forceLog(String data) {
		System.out.println(data);
	}
}
