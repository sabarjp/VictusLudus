package com.teamderpy.victusludus.data;

import javax.swing.tree.DefaultTreeModel;

// TODO: Auto-generated Javadoc
/*
 * Lazy way to shorten the name of the swing tree
 */
/**
 * The Class JTree.
 */
public class JTree extends DefaultTreeModel{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1930549845469840635L;

	/**
	 * Instantiates a new j tree.
	 *
	 * @param name the name
	 */
	public JTree(JTreeNode name) {
		super(name);
	}
	
	/**
	 * Instantiates a new j tree.
	 *
	 * @param root the root
	 * @param asksAllowsChildren the asks allows children
	 */
	public JTree(JTreeNode root, boolean asksAllowsChildren) {
		super(root, asksAllowsChildren);
	}
}
