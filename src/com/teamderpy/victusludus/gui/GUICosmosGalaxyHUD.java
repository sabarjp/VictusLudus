package com.teamderpy.victusludus.gui;

import java.util.ArrayList;


import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.engine.Actionable;
import com.teamderpy.victusludus.game.cosmos.Cosmos;
import com.teamderpy.victusludus.game.cosmos.EnumCosmosMode;
import com.teamderpy.victusludus.game.renderer.cosmos.CosmosRenderer;
import com.teamderpy.victusludus.game.renderer.cosmos.StarImage;
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


public class GUICosmosGalaxyHUD extends GUI implements KeyboardListener, ResizeListener, TooltipListener{
	/** The tooltip text. */
	private GUITextWithBox tooltipText;

	/** The current star name. */
	private GUIText selectedStarName;
	
	
	/** The current star type. */
	private GUIText selectedStarType;
	
	/** The age of the selected star */
	private GUIText selectedStarAge;
	
	/** The number of planets in the selected star */
	private GUIText selectedStarPlanetCount;

	/** The quit button. */
	private GUIImageButton quitButton;

	/** The cosmos this hud belongs to */
	private CosmosRenderer cosmosRenderer;

	public GUICosmosGalaxyHUD(final CosmosRenderer cosmosRenderer){
		super();

		this.cosmosRenderer = cosmosRenderer;
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
				cosmosRenderer.cosmos.setGalaxy(null);
				cosmosRenderer.changePerspective(EnumCosmosMode.UNIVERSE_PERSPECTIVE);
			}
		});
		this.quitButton.setTooltip("Return to galaxy select");
		this.elementList.add(this.quitButton);
		this.menuList.add(this.quitButton);

		if(this.currentElement < 0 && !this.elementList.isEmpty()) {
			this.currentElement = 0;
		}
		
		/************
		 * STAR NAME
		 */

		this.selectedStarName = new GUIText(0, 0, "",
				GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontM(GUI.TOOLT_FONT_ID));
		this.selectedStarName.setCentered(false);
		this.elementList.add(this.selectedStarName);

		/************
		 * STAR TYPE
		 */

		this.selectedStarType = new GUIText(0, 0, "",
				GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontM(GUI.TOOLT_FONT_ID));
		this.selectedStarType.setCentered(false);
		this.elementList.add(this.selectedStarType);
		
		/************
		 * STAR AGE
		 */

		this.selectedStarAge = new GUIText(0, 0, "",
				GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontM(GUI.TOOLT_FONT_ID));
		this.selectedStarAge.setCentered(false);
		this.elementList.add(this.selectedStarAge);
		
		/************
		 * STAR PLANET COUNT
		 */

		this.selectedStarPlanetCount = new GUIText(0, 0, "",
				GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontM(GUI.TOOLT_FONT_ID));
		this.selectedStarPlanetCount.setCentered(false);
		this.elementList.add(this.selectedStarPlanetCount);


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

		this.selectedStarName.setX(5);
		this.selectedStarName.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.selectedStarName);
		
		this.selectedStarType.setX(5);
		this.selectedStarType.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.selectedStarType);
		
		this.selectedStarAge.setX(5);
		this.selectedStarAge.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.selectedStarAge);
		
		this.selectedStarPlanetCount.setX(5);
		this.selectedStarPlanetCount.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.selectedStarPlanetCount);
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
	 * Sets the selected star name
	 *
	 * @param text the new selected star name
	 */
	public void setSelectedStarName(final String text){
		this.selectedStarName.setText(text);
	}

	/**
	 * Sets the selected star type
	 *
	 * @param text the new selected star type
	 */
	public void setSelectedStarType(final String text){
		this.selectedStarType.setText("type: " + text);
	}
	
	/**
	 * Sets the selected star age
	 *
	 * @param text the new selected star age
	 */
	public void setSelectedStarAge(final String text){
		this.selectedStarAge.setText("age: " + text);
	}

	
	/**
	 * Sets the selected star planet count
	 *
	 * @param text the new selected star planet count
	 */
	public void setSelectedStarPlanetCount(final String text){
		this.selectedStarPlanetCount.setText("planets: " + text);
	}

	public CosmosRenderer getCosmosRenderer() {
		return cosmosRenderer;
	}
}
