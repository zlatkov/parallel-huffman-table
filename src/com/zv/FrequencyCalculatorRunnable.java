package com.zv;

import java.io.*;

/**
 * Created by zlatkov on 6/26/15.
 */
public class FrequencyCalculatorRunnable implements Runnable {
	private String fileName;
	private long startPosition;
	private long bytesToRead;
	private FrequencyTable table;

	public FrequencyCalculatorRunnable(String fileName, long startPosition, long bytesToRead, FrequencyTable table) {
		this.fileName = fileName;
		this.startPosition = startPosition;
		this.bytesToRead = bytesToRead;
		this.table = table;
	}

	@Override
	public void run(){
		try (FileInputStream stream = new FileInputStream(this.fileName)) {
			stream.skip(this.startPosition);

			for (long bytesRead = 0; bytesRead < this.bytesToRead; bytesRead++) {
				int data = stream.read();
				if (data != -1) {
					this.table.addItem(data, 1);
				}
				else {
					// Reached EOF
					break;
				}
			}
		}
		catch (FileNotFoundException e) {

		}
		catch (IOException e) {

		}
	}
}
