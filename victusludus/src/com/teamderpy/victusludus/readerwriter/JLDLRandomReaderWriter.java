package com.teamderpy.victusludus.readerwriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.teamderpy.victusludus.data.JTree;
import com.teamderpy.victusludus.data.JTreeNode;
import com.teamderpy.victusludus.data.VFile;


/**
 * The Class JLDLRandomReaderWriter.
 */
public class JLDLRandomReaderWriter {
	
	/** The Constant BASE_LEVEL_NODE. */
	private static final String BASE_LEVEL_NODE = "__b_level_node_res__";
	
	/** The Constant STANDARD_INDENT. */
	private static final int STANDARD_INDENT = 3;
	
	/** The file path. */
	private String filePath;

	/** The fr. */
	private FileReader fr;

	/** The s. */
	private Scanner s;
	
	/** The scanned file. */
	private ArrayList<String> scannedFile;
	
	/** The jldltree. */
	private JTree jldltree;

	/**
	 * Instantiates a new jLDL random reader writer.
	 *
	 * @param path the path
	 */
	public JLDLRandomReaderWriter(String path) {
		this.filePath = path;

		initialize();
		treeify();
	}

	/**
	 * Count lead space.
	 *
	 * @param s the s
	 * @return the int
	 */
	private static int CountLeadSpace(String s) {
		Pattern p = Pattern.compile("^( )+.*");
		Matcher m = p.matcher(s);

		if (m.matches()) {
			return m.end(1);
		}

		return 0;
	}

	/**
	 * Checks if is comment.
	 *
	 * @param s the s
	 * @return true, if successful
	 */
	private static boolean IsComment(String s) {
		Pattern p = Pattern.compile("^( )*#.*");
		Matcher m = p.matcher(s);

		if (m.matches()) {
			return true;
		}

		return false;
	}

	/**
	 * Initialize.
	 */
	private void initialize() {
		FileHandle f = VFile.getFileHandle(this.filePath);
		
		if(!f.exists()){
			try {
				f.file().createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		//scan in file
		try {
			fr = new FileReader(this.filePath);
			s = new Scanner(fr);
			s.useDelimiter("\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		scannedFile = new ArrayList<String>();
		
		while(s.hasNext()){
			scannedFile.add(s.next());
		}
		
		s.close();
	}
	
	/**
	 * Treeify.
	 */
	private void treeify(){
	   Deque<JTreeNode> nodeStack = new ArrayDeque<JTreeNode>();
	   
	   JTreeNode lastItem = new JTreeNode(new JLDL(BASE_LEVEL_NODE));
		JTreeNode currentItem = new JTreeNode(new JLDL(BASE_LEVEL_NODE));

	   jldltree = new JTree(lastItem);
	   
		for(String line:scannedFile){
			int currentIndentSpaces = CountLeadSpace(line);
			
			line = line.replaceFirst(" +", "");
				
			String[] sarray = line.trim().split(":");
			
			if(sarray.length != 2){
				currentItem = new JTreeNode(new JLDL(sarray[0], currentIndentSpaces));
			} else {
				currentItem = new JTreeNode(new JLDL(sarray[0], sarray[1], currentIndentSpaces));
			}
			
			//if the indent level changed, then update the stack to correctly reflect the parent node
			if (((JLDL) currentItem.getUserObject()).getIndent() > ((JLDL) lastItem.getUserObject()).getIndent()) {
				nodeStack.addFirst(lastItem);
			} else if (((JLDL) currentItem.getUserObject()).getIndent() < ((JLDL) lastItem.getUserObject()).getIndent()) {
				while (((JLDL) currentItem.getUserObject()).getIndent() <= ((JLDL) nodeStack.peekFirst().getUserObject()).getIndent()) {
					nodeStack.removeFirst();	
				}
			}
			
			//set the parent of the current node
			nodeStack.peekFirst().add(currentItem);
			
			//set the last item if appropriate
			if(!IsComment(line) && !line.replaceFirst(" +", "").isEmpty()){
				lastItem = currentItem;
			}
		}
	}
	
	/**
	 * Prints the tree.
	 *
	 * @param n the n
	 * @param level the level
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	private void printTree(JTreeNode n, int level){
		if(level >= 0)
			System.out.println(repeat(' ', level) + n.getUserObject());
		
		Enumeration<JTreeNode> e = n.children();
		
		while(e.hasMoreElements())
			printTree(e.nextElement(), level+STANDARD_INDENT);
	}
	
	/**
	 * Write tree.
	 *
	 * @param wr the wr
	 * @param n the n
	 * @param level the level
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("unchecked")
	private void writeTree(BufferedWriter wr, JTreeNode n, int level) throws IOException{
		if(level >= 0)
			wr.write(repeat(' ', level) + n.getUserObject() + "\n");
		
		Enumeration<JTreeNode> e = n.children();
		
		while(e.hasMoreElements())
			writeTree(wr, e.nextElement(), level+STANDARD_INDENT);
	}

	//pass a node path, example "settings->video" then insert id:value under there
	/**
	 * Write.
	 *
	 * @param nodePath the node path
	 * @param id the id
	 * @param value the value
	 */
	public void write(String nodePath, String id, String value){
		write(nodePath + "->" + id + ":" + value);
	}
	
	/**
	 * Write.
	 *
	 * @param nodePath the node path
	 */
	public void write(String nodePath){
		String[] nodePathArray = nodePath.split("->");
		
		JTree tree = jldltree;
		
		int level = 0;
		int i = 0;
		
		//insert nodes into tree
		for(i=0; i<nodePathArray.length-1; i++){
			String nodeName = nodePathArray[i];
			level++;
			
			if(!nodeName.contains(":"))
				nodeName = nodeName + ":";
			
			String[] nodeSplit = nodeName.split(":");
			
			if(nodeSplit.length != 2){
				tree = insertIntoTree(tree, new JLDL(nodeSplit[0]), level, false);
			} else {
				tree = insertIntoTree(tree, new JLDL(nodeSplit[0], nodeSplit[1]), level, false);
			}
		}
		
		//modify or insert the final node
		level++;
		String nodeName = nodePathArray[i];
		
		String[] nodeSplit = nodeName.split(":");
		
		if(nodeSplit.length != 2){
			tree = insertIntoTree(tree, new JLDL(nodeSplit[0]), level, true);
		} else {
			tree = insertIntoTree(tree, new JLDL(nodeSplit[0], nodeSplit[1]), level, true);
		}
		
		
		//write out to a file now
		BufferedWriter out = null;
		
		try {
			out = new BufferedWriter(new FileWriter(filePath));
			writeTree(out, (JTreeNode) jldltree.getRoot(),STANDARD_INDENT * -1);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
	
	//pass a node path, example "settings->video" then read id:value there
	/**
	 * Read.
	 *
	 * @param nodePath the node path
	 * @param id the id
	 * @return the string
	 */
	public String read(String nodePath, String id){
		return read(nodePath + "->" + id);
	}
	
	//returns the property value of the node at the nodePath, or returns null 
	//if the node cannot be found
	/**
	 * Read.
	 *
	 * @param nodePath the node path
	 * @return the string
	 */
	public String read(String nodePath){
		String[] nodePathArray = nodePath.split("->");
		
		JTree tree = jldltree;
		
		int level = 0;
		int i = 0;
		
		//read nodes from tree
		for(i=0; i<nodePathArray.length-1; i++){
			String nodeName = nodePathArray[i];
			level++;
			
			if(!nodeName.contains(":"))
				nodeName = nodeName + ":";
			
			String[] nodeSplit = nodeName.split(":");
			
			if(nodeSplit.length != 2){
				tree = findInTree(tree, new JLDL(nodeSplit[0]), level, false);
			} else {
				tree = findInTree(tree, new JLDL(nodeSplit[0], nodeSplit[1]), level, false);
			}
		}
		
		//locate the final node
		level++;
		String nodeName = nodePathArray[i];
		
		String[] nodeSplit = nodeName.split(":");
		
		if(nodeSplit.length != 2){
			tree = findInTree(tree, new JLDL(nodeSplit[0]), level, true);
		} else {
			tree = findInTree(tree, new JLDL(nodeSplit[0], nodeSplit[1]), level, true);
		}

		//we should have the final node now
		if(tree != null)
			return ((JLDL) ((JTreeNode)tree.getRoot()).getUserObject()).getProperty();
		return null;
	}
	
	/*returns the subtree that contains our inserted node as the root*/
	/**
	 * Insert into tree.
	 *
	 * @param tree the tree
	 * @param j the j
	 * @param insertAtLevel the insert at level
	 * @param modifyValueFirst the modify value first
	 * @return the j tree
	 */
	@SuppressWarnings("unchecked")
	private JTree insertIntoTree(JTree tree, JLDL j, int insertAtLevel, boolean modifyValueFirst){
		if(tree == null)
			return null;
		
		Enumeration<JTreeNode> e = ((JTreeNode) tree.getRoot()).breadthFirstEnumeration();
		
		JTreeNode n = null;
		
		while(e.hasMoreElements()){
			n = e.nextElement();
			
			if(j.getProperty() == null){
				if(((JLDL)n.getUserObject()).getName().equals(j.getName())){
					return new JTree(n);
				}
			} else {
				if(modifyValueFirst){
					if(((JLDL)n.getUserObject()).getName().equals(j.getName())){
						((JLDL) n.getUserObject()).setProperty(j.getProperty());
						return new JTree(n);
					}
				} else {
					if(((JLDL)n.getUserObject()).getName().equals(j.getName()) && ((JLDL)n.getUserObject()).getProperty().equals(j.getProperty())){
						return new JTree(n);
					}
				}
			}
			
			if(n.getLevel() > insertAtLevel){
				JTreeNode temp = new JTreeNode(j);
				((JTreeNode) n.getParent().getParent()).add(temp);
				return new JTree(temp);
			}
		}
		
		//end of the tree!
		if(n.getLevel() == insertAtLevel){
			JTreeNode temp = new JTreeNode(j);
			((JTreeNode) n.getParent()).add(temp);
			return new JTree(temp);
		}
		else if(n.getLevel() == insertAtLevel - 1){
			JTreeNode temp = new JTreeNode(j);
			n.add(temp);
			return new JTree(temp);
		}
		
		//error
		return null;
	}
	
	/*returns the subtree that contains our inserted node as the root*/
	/**
	 * Find in tree.
	 *
	 * @param tree the tree
	 * @param j the j
	 * @param insertAtLevel the insert at level
	 * @param onlyMatchID the only match id
	 * @return the j tree
	 */
	@SuppressWarnings("unchecked")
	private JTree findInTree(JTree tree, JLDL j, int insertAtLevel, boolean onlyMatchID){
		if(tree == null)
			return null;
		
		Enumeration<JTreeNode> e = ((JTreeNode) tree.getRoot()).breadthFirstEnumeration();
		
		JTreeNode n = null;
		
		while(e.hasMoreElements()){
			n = e.nextElement();
			
			if(j.getProperty() == null){
				if(((JLDL)n.getUserObject()).getName().equals(j.getName())){
					return new JTree(n);
				}
			} else {
				if(onlyMatchID){
					if(((JLDL)n.getUserObject()).getName().equals(j.getName())){
						return new JTree(n);
					}
				} else {
					if(((JLDL)n.getUserObject()).getName().equals(j.getName()) && ((JLDL)n.getUserObject()).getProperty().equals(j.getProperty())){
						return new JTree(n);
					}
				}
			}
			
			if(n.getLevel() > insertAtLevel){
				JTreeNode temp = new JTreeNode(j);
				((JTreeNode) n.getParent().getParent()).add(temp);
				return new JTree(temp);
			}
		}
		
		//end of the tree!
		if(n.getLevel() == insertAtLevel){
			JTreeNode temp = new JTreeNode(j);
			((JTreeNode) n.getParent()).add(temp);
			return new JTree(temp);
		}
		else if(n.getLevel() == insertAtLevel - 1){
			JTreeNode temp = new JTreeNode(j);
			n.add(temp);
			return new JTree(temp);
		}
		
		//error
		return null;
	}
	
	/**
	 * Repeat.
	 *
	 * @param c the c
	 * @param times the times
	 * @return the string
	 */
	private String repeat(char c, int times){
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<times; i++)
			sb.append(c);
		
		return sb.toString();
	}
}
