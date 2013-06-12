package com.teamderpy.victusludus.gui;

import java.util.ArrayList;


import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.engine.Actionable;
import com.teamderpy.victusludus.engine.graphics.EasyGL;
import com.teamderpy.victusludus.gui.element.GUIElement;
import com.teamderpy.victusludus.gui.element.GUIText;
import com.teamderpy.victusludus.gui.element.GUITextButton;
import com.teamderpy.victusludus.gui.eventhandler.KeyboardListener;
import com.teamderpy.victusludus.gui.eventhandler.ResizeListener;
import com.teamderpy.victusludus.gui.eventhandler.event.ButtonPressEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyDownEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyTypedEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyUpEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.SelectEvent;



/**
 * The Class DialogBox.
 */
public class DialogBox implements KeyboardListener, ResizeListener{
	
	/** The background color. */
	protected Color backgroundColor = Color.YELLOW;
	
	/** The border color. */
	protected Color borderColor = Color.BLACK;
	
	/** The fade color */
	protected Color fadeColor = new Color(0, 0, 0, 0.7F);
	
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
				button1 = new GUITextButton(0, 0, "button1", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PRIMARY_FONT_ID));
				elementList.add(button1);
				menuList.add(button1);
			}
			
			if(numberOfButtons >= 2){
				button2 = new GUITextButton(0, 0, "button2", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PRIMARY_FONT_ID));
				elementList.add(button2);
				menuList.add(button2);
			}
			
			if(numberOfButtons >= 3){
				button3 = new GUITextButton(0, 0, "button3", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PRIMARY_FONT_ID));
				elementList.add(button3);
				menuList.add(button3);
			}
			
			if(numberOfButtons == 4){
				button4 = new GUITextButton(0, 0, "button4", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PRIMARY_FONT_ID));
				elementList.add(button4);
				menuList.add(button4);			
			}
		}
		else{
			throw new IllegalArgumentException();
		}
		
		titleText = new GUIText(0, 0, "title", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PRIMARY_FONT_ID));
		titleText.setCentered(true);
		elementList.add(titleText);
		
		messageText = new GUIText(0, 0, "message", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PRIMARY_FONT_ID));
		messageText.setCentered(true);
		messageText.setWidthWrap(500);
		elementList.add(messageText);

		if(currentElement < 0 && !elementList.isEmpty())
			currentElement = 0;
		
		if(!menuList.isEmpty())
			VictusLudusGame.engine.eventHandler.signal(new SelectEvent(menuList.get(currentElement), true));
	}
	
	/**
	 * Position elements.
	 */
	protected void positionElements(){
		this.y = (VictusLudusGame.engine.Y_RESOLUTION() / 2) - (this.height / 2);
		this.width = VictusLudusGame.engine.X_RESOLUTION();
		
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
	 * @param deltaT 
	 * @param batch 
	 */
	public void render(SpriteBatch batch, float deltaT) {
		/* fader */
		batch.enableBlending();
		EasyGL.drawRect(batch, this.fadeColor, 0, 0, VictusLudusGame.engine.X_RESOLUTION(), VictusLudusGame.engine.Y_RESOLUTION());
		batch.disableBlending();
		
		/* box */
		EasyGL.drawRect(batch, this.backgroundColor, this.x, this.y, this.width, this.height);
		
		/* elements */
		for(GUIElement i:elementList){
			i.render(batch, deltaT);
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
	public boolean onKeyDown(KeyDownEvent keyboardEvent) {
		if(!isDisabled){
			if (keyboardEvent.getKey() == Keys.RIGHT) {
				if (currentElement < menuList.size() - 1) {
					VictusLudusGame.engine.eventHandler.signal(new SelectEvent(menuList.get(currentElement), false));
					VictusLudusGame.engine.eventHandler.signal(new SelectEvent(menuList.get(++currentElement), true));
					return true;
				}
			} else if (keyboardEvent.getKey() == Keys.LEFT) {
				if (currentElement > 0) {
					VictusLudusGame.engine.eventHandler.signal(new SelectEvent(menuList.get(currentElement), false));
					VictusLudusGame.engine.eventHandler.signal(new SelectEvent(menuList.get(--currentElement), true));
					return true;
				}
			} else if (keyboardEvent.getKey() == Keys.ENTER) {
				VictusLudusGame.engine.eventHandler.signal(new ButtonPressEvent(menuList.get(currentElement), ""));
				return true;
			} 
		}
		
		return false;
	}
	
	/**
	 * Register listeners.
	 */
	public void registerListeners() {
		VictusLudusGame.engine.eventHandler.resizeHandler.registerPlease(this);
		VictusLudusGame.engine.eventHandler.keyboardHandler.registerPlease(this);
	}

	/**
	 * Unregister listeners.
	 */
	public void unregisterListeners() {
		VictusLudusGame.engine.eventHandler.resizeHandler.unregisterPlease(this);
		VictusLudusGame.engine.eventHandler.keyboardHandler.unregisterPlease(this);
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
		VictusLudusGame.engine.currentGUI.setDisabled(true);
		VictusLudusGame.engine.displayDialog(this);
	}

	/**
	 * Destroy.
	 */
	protected void destroy(){
		VictusLudusGame.engine.currentGUI.setDisabled(false);
		VictusLudusGame.engine.displayDialog(null);
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

	@Override
	public boolean onKeyUp (KeyUpEvent keyboardEvent) {
		return false;
	}

	@Override
	public boolean onKeyTyped (KeyTypedEvent keyboardEvent) {
		return false;
	}
}
