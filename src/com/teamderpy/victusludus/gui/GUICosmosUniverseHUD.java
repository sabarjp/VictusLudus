package com.teamderpy.victusludus.gui;

import java.util.ArrayList;


import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.engine.Actionable;
import com.teamderpy.victusludus.gui.element.GUIElement;
import com.teamderpy.victusludus.gui.element.GUIImageButton;
import com.teamderpy.victusludus.gui.element.GUIText;
import com.teamderpy.victusludus.gui.element.GUITextWithBox;
import com.teamderpy.victusludus.gui.eventhandler.KeyboardListener;
import com.teamderpy.victusludus.gui.eventhandler.ResizeListener;
import com.teamderpy.victusludus.gui.eventhandler.TooltipListener;
import com.teamderpy.victusludus.gui.eventhandler.event.ButtonPressEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyboardEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.SelectEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.TooltipEvent;


public class GUICosmosUniverseHUD extends GUI implements KeyboardListener, ResizeListener, TooltipListener{
	/** The tooltip text. */
	private GUITextWithBox tooltipText;

	/** The current galaxy type. */
	private GUIText selectedGalaxyType;
	
	/** The age of the selected galaxy */
	private GUIText selectedGalaxyAge;
	
	/** The number of stars in the selected galaxy */
	private GUIText selectedGalaxyStarCount;

	/** The quit button. */
	private GUIImageButton quitButton;

	/** The game this hud belongs to */
	//private Game game;

	public GUICosmosUniverseHUD(/*final Game game*/){
		super();

		//this.game = game;
	}

	@Override
	protected void create() {
		this.menuList = new ArrayList<GUIElement>();

		this.backgroundColor = null;

		/************
		 * TOOLTIP
		 */

		this.tooltipText = new GUITextWithBox(0, 0, "",
				GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontS(GUI.TOOLT_FONT_ID));
		this.tooltipText.setCentered(false);
		
		/************
		 * QUIT
		 */

		this.quitButton = new GUIImageButton(0, 0, 32, 32, GUI.ID_QUIT_BTN, GUI.guiButtonSheet);
		this.quitButton.setCentered(false);
		this.quitButton.setPressedAction(new Actionable() {
			@Override
			public void act() {
				VictusLudus.e.quitView();
			}
		});
		this.quitButton.setTooltip("Return to the main menu");
		this.elementList.add(this.quitButton);
		this.menuList.add(this.quitButton);

		if(this.currentElement < 0 && !this.elementList.isEmpty()) {
			this.currentElement = 0;
		}

		/************
		 * GALAXY TYPE
		 */

		this.selectedGalaxyType = new GUIText(0, 0, "",
				GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontM(GUI.TOOLT_FONT_ID));
		this.selectedGalaxyType.setCentered(false);
		this.elementList.add(this.selectedGalaxyType);
		
		/************
		 * GALAXY AGE
		 */

		this.selectedGalaxyAge = new GUIText(0, 0, "",
				GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontM(GUI.TOOLT_FONT_ID));
		this.selectedGalaxyAge.setCentered(false);
		this.elementList.add(this.selectedGalaxyAge);
		
		/************
		 * GALAXY STAR COUNT
		 */

		this.selectedGalaxyStarCount = new GUIText(0, 0, "",
				GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontM(GUI.TOOLT_FONT_ID));
		this.selectedGalaxyStarCount.setCentered(false);
		this.elementList.add(this.selectedGalaxyStarCount);


		if(!this.menuList.isEmpty()) {
			VictusLudus.e.eventHandler.signal(new SelectEvent(this.menuList.get(this.currentElement), true));
		}

	}

	@Override
	protected void positionElements() {
		this.width = VictusLudus.e.X_RESOLUTION();
		this.height = VictusLudus.e.Y_RESOLUTION();

		this.setNextElementPos(5);
		this.setElementSpacing(5);
		
		this.quitButton.setX(5);
		this.quitButton.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.quitButton);

		this.selectedGalaxyType.setX(5);
		this.selectedGalaxyType.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.selectedGalaxyType);
		
		this.selectedGalaxyAge.setX(5);
		this.selectedGalaxyAge.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.selectedGalaxyAge);
		
		this.selectedGalaxyStarCount.setX(5);
		this.selectedGalaxyStarCount.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.selectedGalaxyStarCount);
	}


	@Override
	public void render() {
		super.render();

		this.tooltipText.render();
	}

	@Override
	public void onChangeTooltip(final TooltipEvent tooltipEvent) {
		if(!this.isDisabled){
			this.tooltipText.setText(tooltipEvent.getTooltip());
			this.tooltipText.setX(Mouse.getX() + 32);
			this.tooltipText.setY(VictusLudus.e.Y_RESOLUTION() - Mouse.getY() + 5);
		}
	}

	@Override
	public void onResize(final ResizeEvent resizeEvent) {
		this.positionElements();
	}

	@Override
	public void onKeyPress(final KeyboardEvent keyboardEvent) {
		if(!this.isDisabled){
			if (keyboardEvent.getKey() == Keyboard.KEY_DOWN) {
				if (this.currentElement < this.menuList.size() - 1) {
					VictusLudus.e.eventHandler.signal(new SelectEvent(this.menuList.get(this.currentElement), false));
					VictusLudus.e.eventHandler.signal(new SelectEvent(this.menuList.get(++this.currentElement), true));
				}
			} else if (keyboardEvent.getKey() == Keyboard.KEY_UP) {
				if (this.currentElement > 0) {
					VictusLudus.e.eventHandler.signal(new SelectEvent(this.menuList.get(this.currentElement), false));
					VictusLudus.e.eventHandler.signal(new SelectEvent(this.menuList.get(--this.currentElement), true));
				}
			} else if (keyboardEvent.getKey() == Keyboard.KEY_RETURN) {
				VictusLudus.e.eventHandler.signal(new ButtonPressEvent(this.menuList.get(this.currentElement), ""));
			} else if (keyboardEvent.getKey() == Keyboard.KEY_ESCAPE) {
				this.quitButton.act();
			}
		}
	}


	@Override
	public void registerListeners() {
		VictusLudus.e.eventHandler.resizeHandler.registerPlease(this);
		VictusLudus.e.eventHandler.tooltipHandler.registerPlease(this);
		VictusLudus.e.eventHandler.keyboardHandler.registerPlease(this);
	}

	@Override
	public void unregisterListeners() {
		VictusLudus.e.eventHandler.resizeHandler.unregisterPlease(this);
		VictusLudus.e.eventHandler.tooltipHandler.unregisterPlease(this);
		VictusLudus.e.eventHandler.keyboardHandler.unregisterPlease(this);
		this.unregisterListeningChildren();
	}

	@Override
	protected void finalize() {
		this.unregisterListeners();
	}

	/**
	 * Sets the selected galaxy type
	 *
	 * @param text the new selected galaxy type
	 */
	public void setSelectedGalaxyType(final String text){
		this.selectedGalaxyType.setText("type: " + text);
	}
	
	/**
	 * Sets the selected galaxy age
	 *
	 * @param text the new selected galaxy age
	 */
	public void setSelectedGalaxyAge(final String text){
		this.selectedGalaxyAge.setText("age: " + text);
	}

	
	/**
	 * Sets the selected galaxy star count
	 *
	 * @param text the new selected galaxy star count
	 */
	public void setSelectedGalaxyStarCount(final String text){
		this.selectedGalaxyStarCount.setText("stars: " + text);
	}
}
