package com.teamderpy.victusludus.data;

import javax.swing.tree.DefaultMutableTreeNode;

// TODO: Auto-generated Javadoc
/*
 * Lazy way to shorten the name of the swing tree node
 */
/**
 * The Class JTreeNode.
 */
public class JTreeNode extends DefaultMutableTreeNode{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8287575985339153534L;

	/**
	 * Instantiates a new j tree node.
	 */
	public JTreeNode(){
		super();
	}
	
	/**
	 * Instantiates a new j tree node.
	 *
	 * @param userObject the user object
	 */
	public JTreeNode(Object userObject){
		super(userObject);
	}
	
	/**
	 * Instantiates a new j tree node.
	 *
	 * @param userObject the user object
	 * @param allowsChildren the allows children
	 */
	public JTreeNode(Object userObject, boolean allowsChildren){
		super(userObject, allowsChildren);
	}
}
