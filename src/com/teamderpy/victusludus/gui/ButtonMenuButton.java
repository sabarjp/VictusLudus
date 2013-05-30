package com.teamderpy.victusludus.gui;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.gui.element.GUIImageButton;
import com.teamderpy.victusludus.gui.eventhandler.ButtonPressListener;
import com.teamderpy.victusludus.gui.eventhandler.event.ButtonPressEvent;

// TODO: Auto-generated Javadoc
/*
 * This represents an individual button in a button menu
 */
/**
 * The Class ButtonMenuButton.
 */
public class ButtonMenuButton implements ButtonPressListener{
	/*
	 * The button
	 */
	/** The button. */
	private GUIImageButton button;

	/*
	 * An arbitrary category name, which determines where a button is placed
	 */
	/** The parent category. */
	private String parentCategory;

	/*
	 * Items under this button belong to this category.  Null indicates not to place anything below this.
	 */
	/** The my category. */
	private String myCategory;

	/*
	 * The button menu this belongs to
	 */
	/** The menu. */
	private final ButtonMenu menu;

	/**
	 * Instantiates a new button menu button.
	 *
	 * @param button the button
	 * @param parentCategory the parent category
	 * @param menu the menu
	 */
	public ButtonMenuButton(final GUIImageButton button, final String parentCategory, final ButtonMenu menu){
		this.button = button;
		this.myCategory = null;
		this.parentCategory = parentCategory;
		this.menu = menu;
		this.registerListeners();
	}

	/**
	 * Instantiates a new button menu button.
	 *
	 * @param button the button
	 * @param parentCategory the parent category
	 * @param myCategory the my category
	 * @param menu the menu
	 */
	public ButtonMenuButton(final GUIImageButton button, final String parentCategory, final String myCategory, final ButtonMenu menu){
		this.button = button;
		this.myCategory = myCategory;
		this.parentCategory = parentCategory;
		this.menu = menu;
		this.registerListeners();
	}

	/**
	 * Gets the button.
	 *
	 * @return the button
	 */
	public GUIImageButton getButton() {
		return this.button;
	}

	/**
	 * Sets the button.
	 *
	 * @param button the new button
	 */
	public void setButton(final GUIImageButton button) {
		this.button = button;
	}

	/**
	 * Gets the parent category.
	 *
	 * @return the parent category
	 */
	public String getParentCategory() {
		return this.parentCategory;
	}

	/**
	 * Sets the parent category.
	 *
	 * @param parentCategory the new parent category
	 */
	public void setParentCategory(final String parentCategory) {
		this.parentCategory = parentCategory;
	}

	/**
	 * Gets the my category.
	 *
	 * @return the my category
	 */
	public String getMyCategory() {
		return this.myCategory;
	}

	/**
	 * Sets the my category.
	 *
	 * @param myCategory the new my category
	 */
	public void setMyCategory(final String myCategory) {
		this.myCategory = myCategory;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.ButtonPressListener#onButtonPress(com.teamderpy.victusludus.gui.eventhandler.event.ButtonPressEvent)
	 */
	@Override
	public void onButtonPress(final ButtonPressEvent buttonPressEvent) {
		if(buttonPressEvent.getSource().equals(this.getButton())){
			System.err.println("our button was pressed: " + this.getMyCategory());
			this.menu.setCurrentExpandedNode(this);
		}
	}

	/**
	 * Register listeners.
	 */
	public void registerListeners() {
		VictusLudus.e.eventHandler.buttonPressHandler.registerPlease(this);
	}

	/**
	 * Unregister listeners.
	 */
	public void unregisterListeners() {
		VictusLudus.e.eventHandler.buttonPressHandler.unregisterPlease(this);
		this.unregisterListeningChildren();
	}

	/**
	 * Unregister listening children.
	 */
	public void unregisterListeningChildren(){
		if(this.getButton() != null){
			this.getButton().unregisterListeners();
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() {
		this.unregisterListeners();
	}
}
