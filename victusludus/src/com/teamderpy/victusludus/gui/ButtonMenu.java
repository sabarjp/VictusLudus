
package com.teamderpy.victusludus.gui;

import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.data.JTree;
import com.teamderpy.victusludus.data.JTreeNode;
import com.teamderpy.victusludus.gui.element.GUIImageButton;
import com.teamderpy.victusludus.gui.eventhandler.ButtonPressListener;
import com.teamderpy.victusludus.gui.eventhandler.event.ButtonPressEvent;

/*
 * A menu of buttons, which may have more sub-menus that open
 */
/**
 * The Class ButtonMenu.
 */
public class ButtonMenu implements ButtonPressListener {
	/*
	 * Static string symbolized the root of the tree
	 */
	/** The Constant ROOT_NODE. */
	public static final String ROOT_NODE = "__b__ROOT";

	/*
	 * This is the tree
	 */
	/** The button tree. */
	private final JTree buttonTree;

	/*
	 * The root node is never seen or used, it is merely the base
	 */
	/** The root. */
	private final JTreeNode root;

	/*
	 * The node that the tree is expanded up to, and whose direct children we can
	 * see
	 */
	/** The current expanded node. */
	private JTreeNode currentExpandedNode;

	/*
	 * Direction to grow this menu
	 */
	/** The growth direction. */
	private final EnumDirection growthDirection;

	/*
	 * Position
	 */
	/** The x. */
	private int x = -1;

	/** The y. */
	private int y = -1;

	/*
	 * Declare a new button menu
	 */
	/**
	 * Instantiates a new button menu.
	 * 
	 * @param growthDirection the growth direction
	 * @param x the x
	 * @param y the y
	 */
	public ButtonMenu (final EnumDirection growthDirection, final int x, final int y) {
		this.root = new JTreeNode(new ButtonMenuButton(null, null, ButtonMenu.ROOT_NODE, this));
		this.currentExpandedNode = this.root;
		this.buttonTree = new JTree(this.root);
		this.growthDirection = growthDirection;
		this.x = x;
		this.y = y;
	}

	/*
	 * Add a button to the tree
	 */
	/**
	 * Adds the.
	 * 
	 * @param button the button
	 * @param parentCategory the parent category
	 * @param category the category
	 */
	public void add (final GUIImageButton button, final String parentCategory, final String category) {
		this.add(new ButtonMenuButton(button, parentCategory, category, this));
	}

	/*
	 * Add a button to the tree
	 */
	/**
	 * Adds the.
	 * 
	 * @param button the button
	 * @param parentCategory the parent category
	 */
	public void add (final GUIImageButton button, final String parentCategory) {
		this.add(new ButtonMenuButton(button, parentCategory, null, this));
	}

	/*
	 * Add a button to the tree
	 */
	/**
	 * Adds the.
	 * 
	 * @param button the button
	 */
	public void add (final ButtonMenuButton button) {
		// locate the node to place this under
		@SuppressWarnings("unchecked")
		final Enumeration<JTreeNode> e = ((JTreeNode)this.buttonTree.getRoot()).breadthFirstEnumeration();

		JTreeNode n = null;
		boolean foundInTree = false;

		while (e.hasMoreElements()) {
			n = e.nextElement();

			if (((ButtonMenuButton)n.getUserObject()).getMyCategory().equals(button.getParentCategory())) {
				n.add(new JTreeNode(button));
				foundInTree = true;
				break;
			}
		}

		// add to root if we didn't find it
		if (!foundInTree) {
			this.root.add(new JTreeNode(button));
		}
	}

	/**
	 * Sets the current expanded node.
	 * 
	 * @param button the new current expanded node
	 */
	public void setCurrentExpandedNode (final ButtonMenuButton button) {
		@SuppressWarnings("unchecked")
		final Enumeration<JTreeNode> e = this.root.preorderEnumeration();

		JTreeNode n;
		while (e.hasMoreElements()) {
			n = e.nextElement();
			if (n.getUserObject().equals(button)) {
				if (this.currentExpandedNode.equals(n)) {
					this.currentExpandedNode = (JTreeNode)this.currentExpandedNode.getParent();
				} else {
					this.currentExpandedNode = n;
				}
				return;
			}
		}
	}

	/*
	 * Render the button node and its direct children
	 */
	/**
	 * Render children.
	 * 
	 * @param node the node
	 * @param yOffset the y offset
	 */
	private void renderChildren (final JTreeNode node, final int yOffset) {
		final int thisLevel = 0;

		if (node.getParent() != null) {
			// thisLevel = node.getParent().getIndex(node);
		}

		@SuppressWarnings("unchecked")
		final Enumeration<JTreeNode> e = node.children();
		JTreeNode child;

		while (e.hasMoreElements()) {
			child = e.nextElement();
			this.renderSelf(child, yOffset + thisLevel + child.getParent().getIndex(child));
		}
	}

	/*
	 * Render the button node
	 */
	/**
	 * Render self.
	 * 
	 * @param node the node
	 * @param yLevel the y level
	 */
	private void renderSelf (final JTreeNode node, final int yLevel) {
		final GUIImageButton btn = ((ButtonMenuButton)node.getUserObject()).getButton();

		btn.setX(this.x + btn.getWidth() * (node.getLevel() - 1));
		btn.setY(this.y + btn.getHeight() * yLevel);

		btn.render();
	}

	/*
	 * Render the button menu up to the currently expanded node and its children
	 */
	/**
	 * Render.
	 */
	public void render () {
		final TreeNode[] nodePath = this.currentExpandedNode.getPath();
		int yOffset = 0;

		for (final TreeNode element : nodePath) {
			final JTreeNode castNode = (JTreeNode)element;

			if (castNode.getParent() != null) {
				yOffset += castNode.getParent().getIndex(castNode);
			}

			this.renderChildren(castNode, yOffset);
		}

		/*
		 * while(e.hasMoreElements()){ n = e.nextElement();
		 * 
		 * if(!n.isRoot()){ final GUIImageButton btn = ((ButtonMenuButton)
		 * n.getUserObject()).getButton();
		 * 
		 * if(n.getLevel() != lastLevel){ sideCount = 0; }
		 * 
		 * if(this.growthDirection == EnumDirection.RIGHT){ btn.setX(this.x +
		 * btn.getWidth()*(n.getLevel()-1)); btn.setY(this.y +
		 * btn.getHeight()*sideCount); } else if(this.growthDirection ==
		 * EnumDirection.LEFT){ btn.setX(this.x +
		 * btn.getWidth()*-1*(n.getLevel()-1)); btn.setY(this.y +
		 * btn.getHeight()*sideCount); } else if(this.growthDirection ==
		 * EnumDirection.UP){ btn.setX(this.x + btn.getWidth()*sideCount);
		 * btn.setY(this.x + btn.getHeight()*-1*(n.getLevel()-1)); } else
		 * if(this.growthDirection == EnumDirection.DOWN){ btn.setX(this.x +
		 * btn.getWidth()*sideCount); btn.setY(this.x +
		 * btn.getHeight()*(n.getLevel()-1)); }
		 * 
		 * btn.render(); }
		 * 
		 * lastLevel = n.getLevel(); sideCount++; }
		 */
	}

	/**
	 * Register listeners.
	 */
	public void registerListeners () {
		VictusLudusGame.engine.eventHandler.buttonPressHandler.registerPlease(this);
	}

	/**
	 * Unregister listeners.
	 */
	public void unregisterListeners () {
		VictusLudusGame.engine.eventHandler.buttonPressHandler.unregisterPlease(this);
		this.unregisterListeningChildren();
	}

	/**
	 * Unregister listening children.
	 */
	public void unregisterListeningChildren () {
		@SuppressWarnings("unchecked")
		final Enumeration<JTreeNode> e = this.root.preorderEnumeration();

		JTreeNode n;
		while (e.hasMoreElements()) {
			n = e.nextElement();
			((ButtonMenuButton)n.getUserObject()).unregisterListeners();
		}
	}

	@Override
	protected void finalize () {
		this.unregisterListeners();
	}

	@Override
	public boolean onButtonPress (final ButtonPressEvent buttonPressEvent) {
		return false;
	}
}
