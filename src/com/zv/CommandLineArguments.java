package com.zv;

/**
 * Created by zlatkov on 6/27/15.
 */
public class CommandLineArguments {
	private String fileName;
	private int threadsCount = 1;
	private boolean isQuietMode = false;

	public CommandLineArguments(String[] args) {
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];

			switch (arg) {
				case "-q":
				case "-quiet":
					this.isQuietMode = true;
					break;
				case "-t":
				case "-tasks":
					this.threadsCount = Integer.parseInt(args[i + 1]);
					break;
				case "-f":
					this.fileName = args[i + 1];
					break;
			}
		}
	}

	public String getFileName() { return this.fileName; }

	public int getThreadsCount() { return this.threadsCount; }

	public boolean getIsQuietMode() { return this.isQuietMode; }
}
