package com.company;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by zlatkov on 6/26/15.
 */
public class Generator {
	public static void main(String[] args) throws IOException{
		try (PrintWriter writer = new PrintWriter("in.txt", "UTF-8")) {
			for (int i = 0; i < 1; i++) {
				writer.write("malemalelelezkodsaoiojlkjkdflsjkj34290");
			}
		}
	}
}
