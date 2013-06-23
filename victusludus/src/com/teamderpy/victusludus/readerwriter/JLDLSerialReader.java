
package com.teamderpy.victusludus.readerwriter;

import java.io.Reader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.teamderpy.victusludus.VictusRuntimeException;

/** The Class JLDLSerialReader. */
public class JLDLSerialReader {

	/** The Constant BASE_LEVEL_NODE. */
	public static final String BASE_LEVEL_NODE = "__b_level_node_res__";

	/** The file path. */
	private final FileHandle filePath;

	/** The file reader. */
	private Reader fr;

	/** The node stack. */
	private Deque<JLDL> nodeStack;

	/** The scanner. */
	private PeekScanner s;

	/** The line. */
	private String line;

	/** The last item. */
	private JLDL lastItem;

	/** The string array. */
	private String[] sarray;

	/** The line number. */
	private int lineNumber;

	/** The cur indent. */
	private int curIndent;

	/** Count lead space.
	 * 
	 * @param s the s
	 * @return the int */
	private static int CountLeadSpace (final String s) {
		final Pattern p = Pattern.compile("^( )+.*");
		final Matcher m = p.matcher(s);

		if (m.matches()) {
			return m.end(1);
		}

		return 0;
	}

	/** Checks if is comment.
	 * 
	 * @param s the s
	 * @return true, if successful */
	private static boolean IsComment (final String s) {
		final Pattern p = Pattern.compile("^( )*#.*");
		final Matcher m = p.matcher(s);

		if (m.matches()) {
			return true;
		}

		return false;
	}

	/** Instantiates a new JLDL serial reader.
	 * 
	 * @param path the path */
	public JLDLSerialReader (final FileHandle path) {
		this.filePath = path;

		this.initialize();
	}

	/** Close. */
	public void close () {
		this.s.close();
	}

	/** Gets the line number.
	 * 
	 * @return the line number */
	public int getLineNumber () {
		return this.lineNumber;
	}

	// returns the next element without iterating over it
	/** Peek.
	 * 
	 * @return the read data */
	public ReadData peek () {
		while (this.s.hasNextLine()) {
			this.line = this.s.peek();

			if (JLDLSerialReader.IsComment(this.line)) {
				this.lineNumber++;
				this.s.nextLine();
				continue;
			}

			this.curIndent = JLDLSerialReader.CountLeadSpace(this.line);

			// strip leading whitespace and split string
			this.line = this.line.replaceFirst(" +", "");

			if (this.line.isEmpty()) {
				this.lineNumber++;
				this.s.nextLine();
				continue;
			}

			this.sarray = this.line.split(":", 2);

			if (this.sarray.length != 2) {
				Gdx.app.log("severe", "<ERROR> in " + this.filePath + " on line " + this.lineNumber + ": invalid format ("
					+ this.sarray.length + ")");
				throw new VictusRuntimeException("Improper formatting for file " + this.filePath + " on line " + this.lineNumber);
			}

			JLDL parent = this.nodeStack.peekFirst();

			// if the indent level changed, then update the to hold the parent
			if (this.curIndent > this.lastItem.getIndent()) {
				parent = this.lastItem;
			} else if (this.curIndent < this.lastItem.getIndent()) {
				Iterator<JLDL> iter = this.nodeStack.iterator();

				while (this.curIndent <= parent.getIndent() && iter.hasNext()) {
					parent = iter.next();
				}
			}

			return new ReadData(parent.getName(), this.sarray[0], this.sarray[1]);
		}

		return null;
	}

	// iterates over to the next element
	/** Gets the next.
	 * 
	 * @return the next */
	public ReadData getNext () {
		while (this.s.hasNextLine()) {
			this.line = this.s.nextLine();
			this.lineNumber++;

			if (JLDLSerialReader.IsComment(this.line)) {
				continue;
			}

			this.curIndent = JLDLSerialReader.CountLeadSpace(this.line);

			// strip leading whitespace and split string
			this.line = this.line.replaceFirst(" +", "");

			if (this.line.isEmpty()) {
				continue;
			}

			this.sarray = this.line.split(":", 2);

			if (this.sarray.length != 2) {
				Gdx.app.log("warning", "<ERROR> in " + this.filePath + " on line " + this.lineNumber + ": invalid format ("
					+ this.sarray.length + ")");
				return null;
			}

			// if the indent level changed, then update the to hold the parent
			if (this.curIndent > this.lastItem.getIndent()) {
				this.nodeStack.addFirst(this.lastItem);
			} else if (this.curIndent < this.lastItem.getIndent()) {
				while (this.curIndent <= this.nodeStack.peekFirst().getIndent()) {
					this.nodeStack.removeFirst();
				}
			}

			// actually do stuff with object creation now
			this.lastItem = new JLDL(this.sarray[0], this.curIndent);

			return new ReadData(this.nodeStack.peekFirst().getName(), this.sarray[0], this.sarray[1]);
		}

		return null;
	}

	/** Initialize. */
	private void initialize () {
		this.nodeStack = new ArrayDeque<JLDL>();

		if (!this.filePath.exists()) {
			Gdx.app.log("severe", "<ERROR> File does not exist [" + this.filePath + "]");
			throw new VictusRuntimeException("File does not exist, " + this.filePath);
		}

		this.fr = this.filePath.reader(256);
		this.s = new PeekScanner(this.fr);
		this.line = null;
		this.lastItem = new JLDL(JLDLSerialReader.BASE_LEVEL_NODE, -1);

		this.lineNumber = 0;
		this.curIndent = -1;
	}
}
