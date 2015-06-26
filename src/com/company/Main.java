package com.company;

import java.io.File;

public class Main {

	private static final int CHARARSET_SIZE = 256;

    public static void main(String[] args) {
		int threadsCount = 1;
		String fileName = null;

		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.equals("-t") || arg.equals("-task")) {
				threadsCount = Integer.valueOf(args[i + 1]);
			}
			else if (arg.equals("-f")) {
				fileName = args[i + 1];
			}
		}

		if (fileName == null) {
			fileName = "in.txt";
			//System.exit(1);
		}

		System.out.println("File name: " + fileName);
		System.out.println("Threads count: " + threadsCount);

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

			frequencyTables[i] = new FrequencyTable(CHARARSET_SIZE);
			Runnable runnable = new FrequencyCalculatorRunnable(fileName, blockStart, bytesToRead, frequencyTables[i]);
			threads[i] = new Thread(runnable);
			threads[i].start();
		}

		FrequencyTable resultTable = new FrequencyTable(CHARARSET_SIZE);
		for (int i = 0; i < threadsCount; i++) {
			try {
				threads[i].join();
				for (FrequencyEntry entry : frequencyTables[i]) {
					resultTable.addItem(entry.getKey(), entry.getValue());
				}

			}
			catch (InterruptedException e) {

			}
		}

		for (FrequencyEntry entry : resultTable) {
			char key = (char)entry.getKey();
			System.out.println(key + " " + entry.getValue());
		}

		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
	}
}
