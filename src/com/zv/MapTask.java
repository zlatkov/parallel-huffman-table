package com.zv;

/**
 * Created by zlatkov on 6/27/15.
 */
public class MapTask {
	private final FrequencyTable frequencyTable;
	private final Thread thread;

	public MapTask(String fileName, long blockStart, long bytesToRead, int charSetSize) {
		this.frequencyTable = new FrequencyTable(charSetSize);
		Runnable runnable = new FrequencyCalculatorRunnable(fileName, blockStart, bytesToRead, this.frequencyTable);
		this.thread = new Thread(runnable);
	}

	public void start() {
		this.thread.start();
	}

	public void join() throws InterruptedException{
		this.thread.join();
	}

	public FrequencyTable getFrequencyTable() { return this.frequencyTable; }
}
