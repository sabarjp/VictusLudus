package com.teamderpy.victusludus.gui;

import java.util.ArrayList;


import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.data.resources.EntityDefinition;
import com.teamderpy.victusludus.engine.Actionable;
import com.teamderpy.victusludus.game.EnumFlags;
import com.teamderpy.victusludus.game.Game;
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

// TODO: Auto-generated Javadoc
/**
 * The Class GUIGameHUD.
 */
public class GUIGameHUD extends GUI implements KeyboardListener, ResizeListener, TooltipListener{

	/** The tooltip text. */
	private GUITextWithBox tooltipText;

	/** The current tile text. */
	private GUIText currentTileText;

	/** The current depth text. */
	private GUIText currentDepthText;

	/** The debug text. */
	private GUIText debugText;

	/** The quit button. */
	private GUIImageButton quitButton;

	/** The query button. */
	private GUIImageButton queryButton;

	/** The build button. */
	private GUIImageButton buildButton;

	/** The people button. */
	private GUIImageButton peopleButton;

	/** The side menu. */
	private ButtonMenu sideMenu;

	/** The game this hud belongs to */
	private Game game;

	public GUIGameHUD(final Game game){
		super();

		this.game = game;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.GUI#create()
	 */
	@Override
	protected void create() {
		this.menuList = new ArrayList<GUIElement>();

		this.backgroundColor = null;

		/************
		 * DEBUG
		 */

		this.debugText = new GUIText(0, 0, "DEBUG\n",
				Color.yellow, GUI.fetchFontS(GUI.TOOLT_FONT_ID));
		this.debugText.setCentered(false);
		this.elementList.add(this.debugText);

		/************
		 * TOOLTIP
		 */

		this.tooltipText = new GUITextWithBox(0, 0, "",
				GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontS(GUI.TOOLT_FONT_ID));
		this.tooltipText.setCentered(false);

		/************
		 * CURRENT TILE
		 */

		this.currentTileText = new GUIText(0, 0, "Selected: ",
				GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontS(GUI.TOOLT_FONT_ID));
		this.currentTileText.setCentered(false);
		this.elementList.add(this.currentTileText);

		/************
		 * CURRENT DEPTH
		 */

		this.currentDepthText = new GUIText(0, 0, "Depth: ",
				GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontS(GUI.TOOLT_FONT_ID));
		this.currentDepthText.setCentered(false);
		this.elementList.add(this.currentDepthText);

		/************
		 * SIDE MENU AND BUTTONS
		 */
		this.sideMenu = new ButtonMenu(EnumDirection.RIGHT, 20, 80);

		/************
		 * QUERY
		 */

		this.queryButton = new GUIImageButton(0, 0, 32, 32, GUI.ID_QUERY_BTN, GUI.guiButtonSheet);
		this.queryButton.setCentered(false);
		this.queryButton.setPressedAction(new Actionable() {
			@Override
			public void act() {
				GUIGameHUD.this.game.enterQueryMode();
			}
		});
		this.queryButton.setTooltip("Get more information about something on the map");
		//elementList.add(queryButton);
		//menuList.add(queryButton);
		this.sideMenu.add(this.queryButton, ButtonMenu.ROOT_NODE, "query");

		/************
		 * BUILD
		 */

		this.buildButton = new GUIImageButton(0, 0, 32, 32, GUI.ID_BUILD_BTN, GUI.guiButtonSheet);
		this.buildButton.setCentered(false);
		this.buildButton.setPressedAction(new Actionable() {
			@Override
			public void act() {
				GUIGameHUD.this.game.enterBuildMode();
			}
		});
		this.buildButton.setTooltip("Build rooms and items");
		//elementList.add(buildButton);
		//menuList.add(buildButton);
		this.sideMenu.add(this.buildButton, ButtonMenu.ROOT_NODE, "build");

		/************
		 * PEOPLE
		 */

		this.peopleButton = new GUIImageButton(0, 0, 32, 32, GUI.ID_PEOPLE_BTN, GUI.guiButtonSheet);
		this.peopleButton.setCentered(false);
		this.peopleButton.setPressedAction(new Actionable() {
			@Override
			public void act() {
				GUIGameHUD.this.game.enterPeopleMode();
			}
		});
		this.peopleButton.setTooltip("Hire, fire, or manage people");
		this.peopleButton.setDisabled(true);
		//elementList.add(peopleButton);
		//menuList.add(peopleButton);
		this.sideMenu.add(this.peopleButton, ButtonMenu.ROOT_NODE, "people");


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
		this.quitButton.setTooltip("Exits the application");
		//elementList.add(quitButton);
		//menuList.add(quitButton);
		this.sideMenu.add(this.quitButton, ButtonMenu.ROOT_NODE, "quit");

		/************
		 * MENU
		 */
		for(final EntityDefinition entity:VictusLudus.resources.getEntityHash().values()){
			if(entity.getFlagSet().contains(EnumFlags.BUILDABLE) && entity.getButtonSpriteSheet() != null){
				GUIImageButton btn = new GUIImageButton(0, 0, 32, 32, (byte) 0x0, entity.getButtonSpriteSheet());
				btn.setPressedAction(new Actionable(){
					@Override
					public void act() {
						GUIGameHUD.this.game.setBuildModeObjectID(entity.getId());
						GUIGameHUD.this.game.setBuildMode(entity.getBuildMode());
					}
				});
				this.sideMenu.add(btn, entity.getParentButtonNode(), entity.getId());
			}
		}

		if(this.currentElement < 0 && !this.elementList.isEmpty()) {
			this.currentElement = 0;
		}

		if(!this.menuList.isEmpty()) {
			VictusLudus.e.eventHandler.signal(new SelectEvent(this.menuList.get(this.currentElement), true));
		}

	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.GUI#positionElements()
	 */
	@Override
	protected void positionElements() {
		this.width = VictusLudus.e.X_RESOLUTION();
		this.height = VictusLudus.e.Y_RESOLUTION();

		this.setNextElementPos(5);
		this.setElementSpacing(5);

		this.debugText.setX(5);
		this.debugText.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.debugText);

		this.currentTileText.setX(5);
		this.currentTileText.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.currentTileText);

		this.currentDepthText.setX(5);
		this.currentDepthText.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.currentDepthText);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.GUI#render()
	 */
	@Override
	public void render() {
		super.render();

		this.sideMenu.render();

		this.tooltipText.render();
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.TooltipListener#onChangeTooltip(com.teamderpy.victusludus.gui.eventhandler.event.TooltipEvent)
	 */
	@Override
	public void onChangeTooltip(final TooltipEvent tooltipEvent) {
		if(!this.isDisabled){
			this.tooltipText.setText(tooltipEvent.getTooltip());
			this.tooltipText.setX(Mouse.getX() + 32);
			this.tooltipText.setY(VictusLudus.e.Y_RESOLUTION() - Mouse.getY() + 5);
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.ResizeListener#onResize(com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent)
	 */
	@Override
	public void onResize(final ResizeEvent resizeEvent) {
		this.positionElements();
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.KeyboardListener#onKeyPress(com.teamderpy.victusludus.gui.eventhandler.event.KeyboardEvent)
	 */
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

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.GUI#registerListeners()
	 */
	@Override
	public void registerListeners() {
		VictusLudus.e.eventHandler.resizeHandler.registerPlease(this);
		VictusLudus.e.eventHandler.tooltipHandler.registerPlease(this);
		VictusLudus.e.eventHandler.keyboardHandler.registerPlease(this);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.GUI#unregisterListeners()
	 */
	@Override
	public void unregisterListeners() {
		VictusLudus.e.eventHandler.resizeHandler.unregisterPlease(this);
		VictusLudus.e.eventHandler.tooltipHandler.unregisterPlease(this);
		VictusLudus.e.eventHandler.keyboardHandler.unregisterPlease(this);
		this.unregisterListeningChildren();
		this.sideMenu.unregisterListeners();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() {
		this.unregisterListeners();
	}

	/**
	 * Sets the current tile text.
	 *
	 * @param text the new current tile text
	 */
	public void setCurrentTileText(final String text){
		this.currentTileText.setText("Selected: " + text);
	}

	/**
	 * Sets the current depth text.
	 *
	 * @param text the new current depth text
	 */
	public void setCurrentDepthText(final String text){
		this.currentDepthText.setText("Height: " + text);
	}

	/**
	 * Sets the debug text.
	 *
	 * @param text the new debug text
	 */
	public void setDebugText(final String text){
		this.debugText.setText("DEBUG   " + text);
	}
}
