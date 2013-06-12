package com.teamderpy.victusludus.gui;

import java.util.ArrayList;


import org.lwjgl.input.Keyboard;

import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.VictusLudusGame.enginengine.Actionable;
import com.teamderpy.victusludus.game.PlayerBackground;
import com.teamderpy.victusludus.gui.element.GUIElement;
import com.teamderpy.victusludus.gui.element.GUISelectFieldHorizontal;
import com.teamderpy.victusludus.gui.element.GUIText;
import com.teamderpy.victusludus.gui.element.GUITextButton;
import com.teamderpy.victusludus.gui.element.GUITextField;
import com.teamderpy.victusludus.gui.eventhandler.KeyboardListener;
import com.teamderpy.victusludus.gui.eventhandler.ResizeListener;
import com.teamderpy.victusludus.gui.eventhandler.TooltipListener;
import com.teamderpy.victusludus.gui.eventhandler.event.ButtonPressEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyDownEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.SelectEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.TooltipEvent;


/**
 * The Class GUINewGameAvatarMenu.
 */
public class GUINewGameAvatarMenu extends GUI implements KeyboardListener, ResizeListener, TooltipListener{
	
	/** The avatar background select. */
	private GUISelectFieldHorizontal avatarBackgroundSelect;
	
	/** The quit button. */
	private GUITextButton quitButton;
	
	/** The create button. */
	private GUITextButton createButton;
	
	/** The avatar first name label. */
	private GUIText avatarFirstNameLabel;
	
	/** The avatar last name label. */
	private GUIText avatarLastNameLabel;
	
	/** The avatar background label. */
	private GUIText avatarBackgroundLabel;
	
	/** The avatar first name field. */
	private GUITextField avatarFirstNameField;
	
	/** The avatar last name field. */
	private GUITextField avatarLastNameField;
	
	/** The tooltip text. */
	private GUIText tooltipText;
	
	/** The title text. */
	private GUIText titleText;
	
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.GUI#create()
	 */
	protected void create() {
		menuList = new ArrayList<GUIElement>();
		
		/************
		 * TITLE
		 */
		
		titleText = new GUIText(0, 0, "New Game", GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontL(GUI.TITLE_FONT_ID));
		titleText.setCentered(true);
		elementList.add(titleText);
		
		/************
		 * TOOLTIP
		 */
		
	   tooltipText = new GUIText(0, 0, "", GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontS(GUI.TOOLTIP_FONT_ID));
		tooltipText.setCentered(true);
	   elementList.add(tooltipText);
		
		/************
		 * AVATAR FIRST NAME LABEL
		 */
		
		avatarFirstNameLabel = new GUIText(0, 0, "", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PRIMARY_FONT_ID));
		avatarFirstNameLabel.setText("First name");
		avatarFirstNameLabel.setCentered(true);
		elementList.add(avatarFirstNameLabel);

		/************
		 * AVATAR FIRST NAME
		 */
		
		avatarFirstNameField = new GUITextField(0, 0, 200, GUI.SUBELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PRIMARY_FONT_ID));
		avatarFirstNameField.setText("");
		avatarFirstNameField.setMaximumSize(15);
		avatarFirstNameField.setTooltip("Your first name");
		avatarFirstNameField.setCentered(true);
		avatarFirstNameField.setTypingAction(new Actionable(){
			public void act(){
				genTitleText();
			}
		});
		elementList.add(avatarFirstNameField);
		menuList.add(avatarFirstNameField);

		/************
		 * AVATAR LAST NAME LABEL
		 */
		
		avatarLastNameLabel = new GUIText(0, 0, "", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PRIMARY_FONT_ID));
		avatarLastNameLabel.setText("Last name");
		avatarLastNameLabel.setCentered(true);
		elementList.add(avatarLastNameLabel);

		/************
		 * AVATAR LAST NAME
		 */
		
		avatarLastNameField = new GUITextField(0, 0, 200, GUI.SUBELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PRIMARY_FONT_ID));
		avatarLastNameField.setText("");
		avatarLastNameField.setMaximumSize(15);
		avatarLastNameField.setTooltip("Your last name");
		avatarLastNameField.setCentered(true);
		avatarLastNameField.setTypingAction(new Actionable(){
			public void act(){
				genTitleText();
			}
		});
		elementList.add(avatarLastNameField);
		menuList.add(avatarLastNameField);

		/************
		 * AVATAR BACKGROUND
		 */
		
		avatarBackgroundSelect = new GUISelectFieldHorizontal(0, 0, "Background", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PRIMARY_FONT_ID));
		avatarBackgroundSelect.setCentered(true);
		for(PlayerBackground pbg:VictusLudus.resources.getBackgroundsHash().values()){
			avatarBackgroundSelect.addItem(pbg.getName(), pbg.getDegree(), pbg.getDescription());
		}
		avatarBackgroundSelect.setCurrentSelectionIndex(0);
		avatarBackgroundSelect.setTooltip("Your background");
		avatarBackgroundSelect.setChangedAction(new Actionable(){
			public void act() {
				avatarBackgroundLabel.setText(avatarBackgroundSelect.getCurrentSelection().getTooltip());
				genTitleText();
			}
		});
		elementList.add(avatarBackgroundSelect);
		menuList.add(avatarBackgroundSelect);
		
		/************
		 * AVATAR BACKGROUND LABEL
		 */
		
		avatarBackgroundLabel = new GUIText(0, 0, "", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PRIMARY_FONT_ID));
		avatarBackgroundLabel.setText(avatarBackgroundSelect.getCurrentSelection().getTooltip());
		avatarBackgroundLabel.setCentered(true);
		avatarBackgroundLabel.setWidthWrap((int)(this.width * 0.75));
		elementList.add(avatarBackgroundLabel);
		
		
		/************
		 * CREATE
		 */
		
		createButton = new GUITextButton(0, 0, "Create!", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PRIMARY_FONT_ID), 8);
		createButton.setCentered(true);
		createButton.setPressedAction(new Actionable() {
			public void act() {
				if(avatarFirstNameField.getText().length() > 0 && avatarLastNameField.getText().length() > 0){
					VictusLudusGame.engine.changeGUI(null);
				} else {
					final DialogBox db = new DialogBox(1);
					db.setTitleText("Your name?");
					db.setMessageText("You need to enter a full name!");
					db.setButton1Text("OK!");
					db.setButton1Action(new Actionable(){
						public void act(){
							db.destroy();
						}
					});
					db.show();
				}
			}
				});
		createButton.setTooltip("Start the game!");
		elementList.add(createButton);
		menuList.add(createButton);

		/************
		 * BACK
		 */
		
		quitButton = new GUITextButton(0, 0, "Quit", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PRIMARY_FONT_ID), 8);
		quitButton.setCentered(true);
		quitButton.setPressedAction(new Actionable() {
					public void act() {
						new com.teamderpy.victusludus.gui.GUIMainMenu().show();
					}
				});
		quitButton.setTooltip("Go back to the menu");
		elementList.add(quitButton);
		menuList.add(quitButton);
		
		if(currentElement < 0 && !elementList.isEmpty())
			currentElement = 0;
		
		if(!menuList.isEmpty())
			VictusLudusGame.engine.eventHandler.signal(new SelectEvent(menuList.get(currentElement), true));
	}
	
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.GUI#positionElements()
	 */
	@Override
	protected void positionElements() {
		this.width = VictusLudusGame.engine.X_RESOLUTION();
		this.height = VictusLudusGame.engine.Y_RESOLUTION();
		
		titleText.setX(VictusLudusGame.engine.X_RESOLUTION() / 2);
		titleText.setY(5);
		
		tooltipText.setX(VictusLudusGame.engine.X_RESOLUTION() / 2);
		tooltipText.setY(VictusLudusGame.engine.Y_RESOLUTION() - GUI.fetchFontM(GUI.PRIMARY_FONT_ID).getLineHeight()-5);
		
		setNextElementPos(titleText.getHeight() + 50);
		setElementSpacing(5);
		
		avatarFirstNameLabel.setX(VictusLudusGame.engine.X_RESOLUTION() / 2);
		avatarFirstNameLabel.setY(getNextElementPos());
		nextElementPosIncrement(avatarFirstNameLabel);
		
		avatarFirstNameField.setX(VictusLudusGame.engine.X_RESOLUTION() / 2);
		avatarFirstNameField.setY(getNextElementPos());
		nextElementPosIncrement(avatarFirstNameField);
		
		avatarLastNameLabel.setX(VictusLudusGame.engine.X_RESOLUTION() / 2);
		avatarLastNameLabel.setY(getNextElementPos());
		nextElementPosIncrement(avatarLastNameLabel);
		
		avatarLastNameField.setX(VictusLudusGame.engine.X_RESOLUTION() / 2);
		avatarLastNameField.setY(getNextElementPos());
		nextElementPosIncrement(avatarLastNameField);
		
		avatarBackgroundSelect.setX(VictusLudusGame.engine.X_RESOLUTION() / 2);
		avatarBackgroundSelect.setY(getNextElementPos());
		nextElementPosIncrement(avatarBackgroundSelect);
		
		avatarBackgroundLabel.setX(VictusLudusGame.engine.X_RESOLUTION() / 2);
		avatarBackgroundLabel.setY(getNextElementPos());
		nextElementPosIncrement(avatarBackgroundLabel);
		nextElementPosIncrement(avatarBackgroundLabel.getHeight() * 3);
		
		createButton.setX(VictusLudusGame.engine.X_RESOLUTION() / 2);
		createButton.setY(getNextElementPos());
		nextElementPosIncrement(createButton);
		nextElementPosIncrement(10);
		
		quitButton.setX(VictusLudusGame.engine.X_RESOLUTION() / 2);
		quitButton.setY(getNextElementPos());
		nextElementPosIncrement(quitButton);
	}
	
	/**
	 * Gen title text.
	 */
	private void genTitleText(){
		if(!avatarFirstNameField.getText().isEmpty() || !avatarLastNameField.getText().isEmpty()){
			if(!avatarBackgroundSelect.getCurrentSelection().getValue().isEmpty())
				titleText.setText(avatarFirstNameField.getText() + " " + avatarLastNameField.getText() + ", " + avatarBackgroundSelect.getCurrentSelection().getValue());
			else
				titleText.setText(avatarFirstNameField.getText() + " " + avatarLastNameField.getText());
		}else{
			titleText.setText("New Game");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.TooltipListener#onChangeTooltip(com.teamderpy.victusludus.gui.eventhandler.event.TooltipEvent)
	 */
	@Override
	public void onChangeTooltip(TooltipEvent tooltipEvent) {
		if(!isDisabled){
			tooltipText.setText(tooltipEvent.getTooltip());
		}
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
	public void onKeyDown(KeyDownEvent keyboardEvent) {
		if(!isDisabled){
			if (keyboardEvent.getKey() == Keyboard.KEY_DOWN) {
				if (currentElement < menuList.size() - 1) {
					VictusLudusGame.engine.eventHandler.signal(new SelectEvent(menuList.get(currentElement), false));
					VictusLudusGame.engine.eventHandler.signal(new SelectEvent(menuList.get(++currentElement), true));
				}
			} else if (keyboardEvent.getKey() == Keyboard.KEY_UP) {
				if (currentElement > 0) {
					VictusLudusGame.engine.eventHandler.signal(new SelectEvent(menuList.get(currentElement), false));
					VictusLudusGame.engine.eventHandler.signal(new SelectEvent(menuList.get(--currentElement), true));
				}
			} else if (keyboardEvent.getKey() == Keyboard.KEY_RETURN) {
				VictusLudusGame.engine.eventHandler.signal(new ButtonPressEvent(menuList.get(currentElement), ""));
			} else if (keyboardEvent.getKey() == Keyboard.KEY_ESCAPE) {
				quitButton.act();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.GUI#registerListeners()
	 */
	public void registerListeners() {
		VictusLudusGame.engine.eventHandler.resizeHandler.registerPlease(this);
		VictusLudusGame.engine.eventHandler.tooltipHandler.registerPlease(this);
		VictusLudusGame.engine.eventHandler.keyboardHandler.registerPlease(this);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.GUI#unregisterListeners()
	 */
	public void unregisterListeners() {
		VictusLudusGame.engine.eventHandler.resizeHandler.unregisterPlease(this);
		VictusLudusGame.engine.eventHandler.tooltipHandler.unregisterPlease(this);
		VictusLudusGame.engine.eventHandler.keyboardHandler.unregisterPlease(this);
		unregisterListeningChildren();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize(){
		unregisterListeners();
		unregisterListeningChildren();
	}
}
