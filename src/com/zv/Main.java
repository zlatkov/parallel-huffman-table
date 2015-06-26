package com.zv;

import java.io.File;

public class Main {

	private static final int CHARARSET_SIZE = 256;

    public static void main(String[] args) {
		boolean quietMode = false;
		int threadsCount = 1;
		String fileName = "in.txt";

		for (int i = 0; i < args.length; i++) {
			String arg = args[i];

			if (arg.equals("-q") || arg.equals("-quiet")) {
				quietMode = true;
			}
			else if (arg.equals("-t") || arg.equals("-tasks")) {
				threadsCount = Integer.parseInt(args[i + 1]);
			}
			else if (arg.equals("-f")) {
				fileName = args[i + 1];
			}
		}

		Logger.setQuietMode(quietMode);
		Logger.log("File name: " + fileName);
		Logger.log("Threads count: " + threadsCount);

		File file = new File(fileName);
		long fileSize = file.length();

		long blockSize = Math.max(fileSize / threadsCount, 1);

		long startTime = System.currentTimeMillis();

		Thread[] threads = new Thread[threadsCount];
		FrequencyTable[] frequencyTables = new FrequencyTable[threadsCount];

		for (int i = 0; i < threadsCount; i++) {
			long blockStart = i * blockSize;
			long bytesToRead;
			if (i == threadsCount - 1) {
				bytesToRead = fileSize - blockStart;
			}
			else {
				bytesToRead = blockSize;
			}

			Logger.log(String.format("Starting thread number %d from position %s", (i + 1), blockStart));

			frequencyTables[i] = new FrequencyTable(CHARARSET_SIZE);
			Runnable runnable = new FrequencyCalculatorRunnable(fileName, blockStart, bytesToRead, frequencyTables[i]);
			threads[i] = new Thread(runnable);
			threads[i].start();
		}

		FrequencyTable resultTable = new FrequencyTable(CHARARSET_SIZE);
		for (int i = 0; i < threadsCount; i++) {
			try {
				threads[i].join();
				Logger.log("Accumulating thread number " + (i + 1));
				for (FrequencyEntry entry : frequencyTables[i]) {
					resultTable.addItem(entry.getKey(), entry.getValue());
				}

			}
			catch (InterruptedException e) {
				Logger.log(e.getMessage());
			}
		}

		for (FrequencyEntry entry : resultTable) {
			char key = (char)entry.getKey();
			Logger.log(key + " " + entry.getValue());
		}

		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		Logger.forceLog("Total time: " + (totalTime / 1000));
	}
}
