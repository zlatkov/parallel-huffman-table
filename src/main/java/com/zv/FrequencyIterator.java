package com.zv;

import java.util.Iterator;

/**
 * Created by zlatkov on 6/26/15.
 */
public class FrequencyIterator implements Iterator<FrequencyEntry> {

	private final int[] frequencies;
	private int position = 0;

	public FrequencyIterator(int[] frequencies) {
		this.frequencies = frequencies;
	}

	@Override
	public boolean hasNext() {
		while (this.position < this.frequencies.length && this.frequencies[this.position] == 0) {
			this.position++;
		}

		return this.position < this.frequencies.length;
	}

	@Override
	public FrequencyEntry next() {
		FrequencyEntry entry = new FrequencyEntry(this.position, this.frequencies[this.position]);
		this.position++;

		return entry;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
