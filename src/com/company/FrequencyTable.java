package com.company;

/**
 * Created by zlatkov on 6/26/15.
 */
public class FrequencyTable implements Iterable<FrequencyEntry> {
	private int[] frequencies;

	public FrequencyTable(int tableSize) {
		this.frequencies = new int[tableSize];
	}

	public void addItem(int key, int increment) {
		this.frequencies[key] += increment;
	}

	public FrequencyIterator iterator() {
		return new FrequencyIterator(this.frequencies);
	}
}
