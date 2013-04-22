package com.teamderpy.victusludus.gui;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.engine.Actionable;
import com.teamderpy.victusludus.gui.element.GUIElement;
import com.teamderpy.victusludus.gui.element.GUIText;
import com.teamderpy.victusludus.gui.element.GUITextButton;
import com.teamderpy.victusludus.gui.eventhandler.KeyboardListener;
import com.teamderpy.victusludus.gui.eventhandler.ResizeListener;
import com.teamderpy.victusludus.gui.eventhandler.event.ButtonPressEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyboardEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.SelectEvent;


// TODO: Auto-generated Javadoc
/**
 * The Class DialogBox.
 */
public class DialogBox implements KeyboardListener, ResizeListener{
	
	/** The background color. */
	protected Color backgroundColor = Color.yellow;
	
	/** The border color. */
	protected Color borderColor = Color.black;
	
	/** The element list. */
	protected ArrayList<GUIElement> elementList = new ArrayList<GUIElement>();
	
	/** The menu list. */
	protected ArrayList<GUIElement> menuList = null;
	
	/** The current element. */
	protected int currentElement = -1;
	
	/** The next element y pos. */
	protected int nextElementYPos = 0;
	
	/** The element spacing. */
	protected int elementSpacing = 0;
	
	/** The is listening. */
	protected boolean isListening = false;
	
	/** The is disabled. */
	protected boolean isDisabled = false;
	
	/** The x. */
	protected int x = -1;
	
	/** The y. */
	protected int y = -1;
	
	/** The width. */
	protected int width = -1;
	
	/** The height. */
	protected int height = -1;
	
	/** The button1. */
	protected GUITextButton button1;
	
	/** The button2. */
	protected GUITextButton button2;
	
	/** The button3. */
	protected GUITextButton button3;
	
	/** The button4. */
	protected GUITextButton button4;
	
	/** The title text. */
	protected GUIText titleText;
	
	/** The message text. */
	protected GUIText messageText;
	
	/** The number of buttons. */
	protected int numberOfButtons = 0;
	
	/**
	 * Instantiates a new dialog box.
	 *
	 * @param numberOfButtons the number of buttons
	 */
	public DialogBox(int numberOfButtons){
		this.numberOfButtons = numberOfButtons;
		this.height = 200;
		
		create();
		positionElements();
	}

	/**
	 * Creates the.
	 */
	protected void create() {
		menuList = new ArrayList<GUIElement>();
		
		if(numberOfButtons > 0 && numberOfButtons < 5){
			if(numberOfButtons >= 1){
				button1 = new GUITextButton(0, 0, "button1", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
				elementList.add(button1);
				menuList.add(button1);
			}
			
			if(numberOfButtons >= 2){
				button2 = new GUITextButton(0, 0, "button2", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
				elementList.add(button2);
				menuList.add(button2);
			}
			
			if(numberOfButtons >= 3){
				button3 = new GUITextButton(0, 0, "button3", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
				elementList.add(button3);
				menuList.add(button3);
			}
			
			if(numberOfButtons == 4){
				button4 = new GUITextButton(0, 0, "button4", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
				elementList.add(button4);
				menuList.add(button4);			
			}
		}
		else{
			throw new IllegalArgumentException();
		}
		
		titleText = new GUIText(0, 0, "title", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
		titleText.setCentered(true);
		elementList.add(titleText);
		
		messageText = new GUIText(0, 0, "message", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
		messageText.setCentered(true);
		messageText.setWidthWrap(500);
		elementList.add(messageText);

		if(currentElement < 0 && !elementList.isEmpty())
			currentElement = 0;
		
		if(!menuList.isEmpty())
			VictusLudus.e.eventHandler.signal(new SelectEvent(menuList.get(currentElement), true));
	}
	
	/**
	 * Position elements.
	 */
	protected void positionElements(){
		this.y = (VictusLudus.e.Y_RESOLUTION() / 2) - (this.height / 2);
		this.width = VictusLudus.e.X_RESOLUTION();
		
		this.titleText.setX(this.width / 2);
		this.titleText.setY(this.y);
		
		this.messageText.setX(this.width / 2);
		this.messageText.setY(this.y + (this.height / 3));
		
		if(numberOfButtons == 1){
			this.button1.setX((this.width/2) - (button1.getWidth()/2));
			this.button1.setY(this.y + this.height - button1.getHeight() - 14);
		}
		else if(numberOfButtons == 2){
			this.button1.setX((this.width/2) - button1.getWidth() - 7);
			this.button1.setY(this.y + this.height - button1.getHeight() - 14);
			
			this.button2.setX(button1.getX() + button1.getWidth() + 14);
			this.button2.setY(this.y + this.height - button1.getHeight() - 14);
		}
		else if(numberOfButtons == 3){
			this.button1.setX((this.width/2) - button1.getWidth() - 7);
			this.button1.setY(this.y + this.height - button1.getHeight() - 14);
			
			this.button2.setX(button1.getX() + button1.getWidth() + 14);
			this.button2.setY(this.y + this.height - button1.getHeight() - 14);
			
			this.button3.setX(this.width - button3.getWidth() - 14);
			this.button3.setY(this.y + this.height - button3.getHeight() - 14);
		}
		else if(numberOfButtons == 4){
			this.button1.setX((this.width/2) - button1.getWidth() - 7);
			this.button1.setY(this.y + this.height - button1.getHeight() - 14);
			
			this.button2.setX(button1.getX() + button1.getWidth() + 14);
			this.button2.setY(this.y + this.height - button1.getHeight() - 14);
			
			this.button4.setX(this.width - button4.getWidth() - 14);
			this.button4.setY(this.y + this.height - button1.getHeight() - 14);
			
			this.button3.setX(button4.getX() - button3.getWidth() - 14);
			this.button3.setY(this.y + this.height - button1.getHeight() - 14);
		}
	}

	/**
	 * Render.
	 */
	public void render() {
		/* fader */
		VictusLudus.e.graphics.setColor(new Color(0,0,0,230));
		VictusLudus.e.graphics.fill(new Rectangle(0, 0, VictusLudus.e.X_RESOLUTION(), VictusLudus.e.Y_RESOLUTION()));
		
		/* box */
		VictusLudus.e.graphics.setColor(this.backgroundColor);
		VictusLudus.e.graphics.fill(new Rectangle(this.x, this.y, this.width, this.height));
		
		/* elements */
		for(GUIElement i:elementList){
			i.render();
		}
	}

	/**
	 * Adds the element item.
	 *
	 * @param elem the elem
	 */
	public void addElementItem(GUIElement elem){
		this.elementList.add(elem);
	}
	
	/**
	 * Adds the menu item.
	 *
	 * @param elem the elem
	 */
	public void addMenuItem(GUIElement elem){
		this.menuList.add(elem);
	}
	
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.ResizeListener#onResize(com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent)
	 */
	@Override
	public void onResize(ResizeEvent resizeEvent) {
		positionElements();
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.KeyboardListener#onKeyPress(com.teamderpy.victusludus.gui.eventhandler.event.KeyboardEvent)
	 */
	@Override
	public void onKeyPress(KeyboardEvent keyboardEvent) {
		if(!isDisabled){
			if (keyboardEvent.getKey() == Keyboard.KEY_RIGHT) {
				if (currentElement < menuList.size() - 1) {
					VictusLudus.e.eventHandler.signal(new SelectEvent(menuList.get(currentElement), false));
					VictusLudus.e.eventHandler.signal(new SelectEvent(menuList.get(++currentElement), true));
				}
			} else if (keyboardEvent.getKey() == Keyboard.KEY_LEFT) {
				if (currentElement > 0) {
					VictusLudus.e.eventHandler.signal(new SelectEvent(menuList.get(currentElement), false));
					VictusLudus.e.eventHandler.signal(new SelectEvent(menuList.get(--currentElement), true));
				}
			} else if (keyboardEvent.getKey() == Keyboard.KEY_RETURN) {
				VictusLudus.e.eventHandler.signal(new ButtonPressEvent(menuList.get(currentElement), ""));
			} 
		}
	}
	
	/**
	 * Register listeners.
	 */
	public void registerListeners() {
		VictusLudus.e.eventHandler.resizeHandler.registerPlease(this);
		VictusLudus.e.eventHandler.keyboardHandler.registerPlease(this);
	}

	/**
	 * Unregister listeners.
	 */
	public void unregisterListeners() {
		VictusLudus.e.eventHandler.resizeHandler.unregisterPlease(this);
		VictusLudus.e.eventHandler.keyboardHandler.unregisterPlease(this);
		unregisterListeningChildren();
	}

	/**
	 * Unregister listening children.
	 */
	protected void unregisterListeningChildren(){
		for(GUIElement e: this.elementList){
			e.unregisterListeners();
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() {
		unregisterListeners();
		unregisterListeningChildren();
	}
	
	/**
	 * Show.
	 */
	protected void show(){
		VictusLudus.e.currentGUI.setDisabled(true);
		VictusLudus.e.displayDialog(this);
	}

	/**
	 * Destroy.
	 */
	protected void destroy(){
		VictusLudus.e.currentGUI.setDisabled(false);
		VictusLudus.e.displayDialog(null);
	}

	/**
	 * Gets the message text.
	 *
	 * @return the message text
	 */
	public String getMessageText() {
		return messageText.getText();
	}

	/**
	 * Sets the message text.
	 *
	 * @param messageText the new message text
	 */
	public void setMessageText(String messageText) {
		this.messageText.setText(messageText);
	}

	/**
	 * Gets the title text.
	 *
	 * @return the title text
	 */
	public String getTitleText() {
		return titleText.getText();
	}

	/**
	 * Sets the title text.
	 *
	 * @param titleText the new title text
	 */
	public void setTitleText(String titleText) {
		this.titleText.setText(titleText);
	}
	
	/**
	 * Gets the button1 text.
	 *
	 * @return the button1 text
	 */
	public String getButton1Text() {
		return button1.getText();
	}

	/**
	 * Sets the button1 text.
	 *
	 * @param text the new button1 text
	 */
	public void setButton1Text(String text) {
		this.button1.setText(text);
		positionElements();
	}
	
	/**
	 * Gets the button2 text.
	 *
	 * @return the button2 text
	 */
	public String getButton2Text() {
		return button2.getText();
	}

	/**
	 * Sets the button2 text.
	 *
	 * @param text the new button2 text
	 */
	public void setButton2Text(String text) {
		this.button2.setText(text);
		positionElements();
	}
	
	/**
	 * Gets the button3 text.
	 *
	 * @return the button3 text
	 */
	public String getButton3Text() {
		return button3.getText();
	}

	/**
	 * Sets the button3 text.
	 *
	 * @param text the new button3 text
	 */
	public void setButton3Text(String text) {
		this.button3.setText(text);
		positionElements();
	}
	
	/**
	 * Gets the button4 text.
	 *
	 * @return the button4 text
	 */
	public String getButton4Text() {
		return button4.getText();
	}

	/**
	 * Sets the button4 text.
	 *
	 * @param text the new button4 text
	 */
	public void setButton4Text(String text) {
		this.button4.setText(text);
		positionElements();
	}
	
	/**
	 * Sets the button1 action.
	 *
	 * @param act the new button1 action
	 */
	public void setButton1Action(Actionable act){
		button1.setPressedAction(act);
	}
	
	/**
	 * Sets the button2 action.
	 *
	 * @param act the new button2 action
	 */
	public void setButton2Action(Actionable act){
		button2.setPressedAction(act);
	}
	
	/**
	 * Sets the button3 action.
	 *
	 * @param act the new button3 action
	 */
	public void setButton3Action(Actionable act){
		button3.setPressedAction(act);
	}
	
	/**
	 * Sets the button4 action.
	 *
	 * @param act the new button4 action
	 */
	public void setButton4Action(Actionable act){
		button4.setPressedAction(act);
	}
	
	/**
	 * Trigger button1 act.
	 */
	public void triggerButton1Act(){
		button1.act();
	}
	
	/**
	 * Trigger button2 act.
	 */
	public void triggerButton2Act(){
		button2.act();
	}
	
	/**
	 * Trigger button3 act.
	 */
	public void triggerButton3Act(){
		button3.act();
	}
	
	/**
	 * Trigger button4 act.
	 */
	public void triggerButton4Act(){
		button4.act();
	}

	/**
	 * Tick.
	 */
	public void tick(){
		for(GUIElement i:elementList){
			i.tick();
		}
	}
}
