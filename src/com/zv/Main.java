package com.zv;

import java.io.File;

public class Main {

    private static final int CHARARSET_SIZE = 256;

    public static void main(String[] args) {
		CommandLineArguments arguments = new CommandLineArguments(args);

		Logger.setQuietMode(arguments.getIsQuietMode());

		String fileName = arguments.getFileName();
		int threadsCount = arguments.getThreadsCount();

		if (fileName == null) {
			Logger.log("Missing file name.");

			System.exit(1);
		}

		Logger.log("File name: " + fileName);
		Logger.log("Threads count: " + threadsCount);

		File file = new File(fileName);
		long fileSize = file.length();

		long blockSize = Math.max(fileSize / threadsCount, 1);

		Logger.log("Block size: " + blockSize);

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
				Logger.log("Waiting for thread number " + (i + 1) + " to finish");

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

		Logger.forceLog(String.format("Total time: %d milliseconds", totalTime));
	}
}
