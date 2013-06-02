package com.teamderpy.victusludus.gui;

import java.util.ArrayList;


import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.VictusLudusGame.enginengine.Actionable;
import com.teamderpy.victusludus.gui.element.GUIElement;
import com.teamderpy.victusludus.gui.element.GUIImageButton;
import com.teamderpy.victusludus.gui.element.GUIText;
import com.teamderpy.victusludus.gui.element.GUITextWithBox;
import com.teamderpy.victusludus.gui.eventhandler.KeyboardListener;
import com.teamderpy.victusludus.gui.eventhandler.ResizeListener;
import com.teamderpy.victusludus.gui.eventhandler.TooltipListener;
import com.teamderpy.victusludus.gui.eventhandler.event.ButtonPressEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyDownEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.SelectEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.TooltipEvent;


public class GUICosmosUniverseHUD extends GUI implements KeyboardListener, ResizeListener, TooltipListener{
	/** The tooltip text. */
	private GUITextWithBox tooltipText;

	/** The current galaxy name. */
	private GUIText selectedGalaxyName;
	
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
				VictusLudusGame.engine.quitView();
			}
		});
		this.quitButton.setTooltip("Return to the main menu");
		this.elementList.add(this.quitButton);
		this.menuList.add(this.quitButton);

		if(this.currentElement < 0 && !this.elementList.isEmpty()) {
			this.currentElement = 0;
		}
		
		/************
		 * GALAXY NAME
		 */

		this.selectedGalaxyName = new GUIText(0, 0, "",
				GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontM(GUI.TOOLT_FONT_ID));
		this.selectedGalaxyName.setRenderInverted(true);
		this.selectedGalaxyName.setCentered(false);
		this.elementList.add(this.selectedGalaxyName);

		/************
		 * GALAXY TYPE
		 */

		this.selectedGalaxyType = new GUIText(0, 0, "",
				GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontM(GUI.TOOLT_FONT_ID));
		this.selectedGalaxyType.setCentered(false);
		this.selectedGalaxyType.setRenderInverted(true);
		this.elementList.add(this.selectedGalaxyType);
		
		/************
		 * GALAXY AGE
		 */

		this.selectedGalaxyAge = new GUIText(0, 0, "",
				GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontM(GUI.TOOLT_FONT_ID));
		this.selectedGalaxyAge.setCentered(false);
		this.selectedGalaxyAge.setRenderInverted(true);
		this.elementList.add(this.selectedGalaxyAge);
		
		/************
		 * GALAXY STAR COUNT
		 */

		this.selectedGalaxyStarCount = new GUIText(0, 0, "",
				GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontM(GUI.TOOLT_FONT_ID));
		this.selectedGalaxyStarCount.setCentered(false);
		this.selectedGalaxyStarCount.setRenderInverted(true);
		this.elementList.add(this.selectedGalaxyStarCount);


		if(!this.menuList.isEmpty()) {
			VictusLudusGame.engine.eventHandler.signal(new SelectEvent(this.menuList.get(this.currentElement), true));
		}

	}

	@Override
	protected void positionElements() {
		this.width = VictusLudusGame.engine.X_RESOLUTION();
		this.height = VictusLudusGame.engine.Y_RESOLUTION();

		this.setNextElementPos(5);
		this.setElementSpacing(5);
		
		this.quitButton.setX(5);
		this.quitButton.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.quitButton);
		
		this.selectedGalaxyName.setX(5);
		this.selectedGalaxyName.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.selectedGalaxyName);

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
			this.tooltipText.setY(VictusLudusGame.engine.Y_RESOLUTION() - Mouse.getY() + 5);
		}
	}

	@Override
	public void onResize(final ResizeEvent resizeEvent) {
		this.positionElements();
	}

	@Override
	public void onKeyDown(final KeyDownEvent keyboardEvent) {
		if(!this.isDisabled){
			if (keyboardEvent.getKey() == Keyboard.KEY_DOWN) {
				if (this.currentElement < this.menuList.size() - 1) {
					VictusLudusGame.engine.eventHandler.signal(new SelectEvent(this.menuList.get(this.currentElement), false));
					VictusLudusGame.engine.eventHandler.signal(new SelectEvent(this.menuList.get(++this.currentElement), true));
				}
			} else if (keyboardEvent.getKey() == Keyboard.KEY_UP) {
				if (this.currentElement > 0) {
					VictusLudusGame.engine.eventHandler.signal(new SelectEvent(this.menuList.get(this.currentElement), false));
					VictusLudusGame.engine.eventHandler.signal(new SelectEvent(this.menuList.get(--this.currentElement), true));
				}
			} else if (keyboardEvent.getKey() == Keyboard.KEY_RETURN) {
				VictusLudusGame.engine.eventHandler.signal(new ButtonPressEvent(this.menuList.get(this.currentElement), ""));
			} else if (keyboardEvent.getKey() == Keyboard.KEY_ESCAPE) {
				this.quitButton.act();
			}
		}
	}


	@Override
	public void registerListeners() {
		VictusLudusGame.engine.eventHandler.resizeHandler.registerPlease(this);
		VictusLudusGame.engine.eventHandler.tooltipHandler.registerPlease(this);
		VictusLudusGame.engine.eventHandler.keyboardHandler.registerPlease(this);
	}

	@Override
	public void unregisterListeners() {
		VictusLudusGame.engine.eventHandler.resizeHandler.unregisterPlease(this);
		VictusLudusGame.engine.eventHandler.tooltipHandler.unregisterPlease(this);
		VictusLudusGame.engine.eventHandler.keyboardHandler.unregisterPlease(this);
		this.unregisterListeningChildren();
	}

	@Override
	protected void finalize() {
		this.unregisterListeners();
	}
	
	/**
	 * Sets the selected galaxy name
	 *
	 * @param text the new selected galaxy name
	 */
	public void setSelectedGalaxyName(final String text){
		this.selectedGalaxyName.setText(text);
	}

	/**
	 * Sets the selected galaxy type
	 *
	 * @param text the new selected galaxy type
	 */
	public void setSelectedGalaxyType(final String text){
		this.selectedGalaxyType.setText(text);
	}
	
	/**
	 * Sets the selected galaxy age
	 *
	 * @param text the new selected galaxy age
	 */
	public void setSelectedGalaxyAge(final String text){
		this.selectedGalaxyAge.setText(text + " old");
	}

	
	/**
	 * Sets the selected galaxy star count
	 *
	 * @param text the new selected galaxy star count
	 */
	public void setSelectedGalaxyStarCount(final String text){
		this.selectedGalaxyStarCount.setText(text+ " stars detected");
	}
}
