package com.teamderpy.victusludus.readerwriter;

import java.io.Reader;
import java.util.Scanner;


/**
 * The Class PeekScanner.
 */
public class PeekScanner {
	
	/** The scanner. */
	private Scanner scanner;
	
	/** The next. */
	private String next;

	/**
	 * Instantiates a new peek scanner.
	 *
	 * @param fr the fr
	 */
	public PeekScanner(final Reader fr){
		this.scanner = new Scanner(fr);

		if(this.scanner.hasNextLine()){
			this.next = this.scanner.nextLine();
		} else {
			this.next = null;
		}
	}

	/**
	 * Checks for next line.
	 *
	 * @return true, if successful
	 */
	public boolean hasNextLine(){
		if(this.next != null){
			return true;
		}

		return false;
	}

	/**
	 * Next line.
	 *
	 * @return the string
	 */
	public String nextLine() {
		String ret = this.next;

		if(this.scanner.hasNextLine()){
			this.next = this.scanner.nextLine();
		} else {
			this.next = null;
		}

		return ret;
	}

	/**
	 * Peek.
	 *
	 * @return the string
	 */
	public String peek(){
		return this.next;
	}

	/**
	 * Close.
	 */
	public void close(){
		this.scanner.close();
	}
}
