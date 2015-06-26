package com.zv;

/**
 * Created by zlatkov on 6/26/15.
 */
public class FrequencyEntry {
	private final int key;
	private final int value;

	public FrequencyEntry(int key, int value) {
		this.key = key;
		this.value = value;
	}

	public int getKey() { return this.key; }

	public int getValue() { return this.value; }
}
