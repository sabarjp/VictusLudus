package com.teamderpy.victusludus.gui;

import java.util.ArrayList;


import org.lwjgl.input.Keyboard;

import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.VictusLudusGame.enginengine.Actionable;
import com.teamderpy.victusludus.gui.element.GUIElement;
import com.teamderpy.victusludus.gui.element.GUIText;
import com.teamderpy.victusludus.gui.element.GUITextButton;
import com.teamderpy.victusludus.gui.eventhandler.KeyboardListener;
import com.teamderpy.victusludus.gui.eventhandler.ResizeListener;
import com.teamderpy.victusludus.gui.eventhandler.TooltipListener;
import com.teamderpy.victusludus.gui.eventhandler.event.ButtonPressEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyDownEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyTypedEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyUpEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.SelectEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.TooltipEvent;


/**
 * The Class GUIMainMenu.
 */
public class GUIMainMenu extends GUI implements KeyboardListener, ResizeListener, TooltipListener{

	/** The new world button. */
	private GUITextButton newWorldButton;

	/** The options button. */
	private GUITextButton optionsButton;

	/** The quit button. */
	private GUITextButton quitButton;

	/** The tooltip text. */
	private GUIText tooltipText;

	/** The title text. */
	private GUIText titleText;

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.GUI#create()
	 */
	@Override
	protected void create() {
		this.menuList = new ArrayList<GUIElement>();

		/************
		 * TITLE
		 */

		this.titleText = new GUIText(0, 0, "Victus Ludus",
				GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontL(GUI.TITLE_FONT_ID));
		this.titleText.setCentered(true);
		this.elementList.add(this.titleText);

		/************
		 * TOOLTIP
		 */

		this.tooltipText = new GUIText(0, 0, "",
				GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontS(GUI.TOOLT_FONT_ID));
		this.tooltipText.setCentered(true);
		this.elementList.add(this.tooltipText);

		/************
		 * NEW GAME
		 */

		this.newWorldButton = new GUITextButton(0, 0, "New universe", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID), 14);
		this.newWorldButton.setCentered(true);
		this.newWorldButton.setPressedAction(new Actionable() {
			@Override
			public void act() {
				new com.teamderpy.victusludus.gui.GUINewUniverseMenu().show();
			}
		});
		this.newWorldButton.setTooltip("Generates a new universe to play in");
		this.elementList.add(this.newWorldButton);
		this.menuList.add(this.newWorldButton);

		/************
		 * OPTIONS
		 */

		this.optionsButton = new GUITextButton(0, 0, "Options",
				GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID), 14);
		this.optionsButton.setCentered(true);
		this.optionsButton.setPressedAction(new Actionable() {
			@Override
			public void act() {
				new com.teamderpy.victusludus.gui.GUIOptionsMenu().show();
			}
		});
		this.optionsButton.setTooltip("Change game, audio, and video settings");
		this.elementList.add(this.optionsButton);
		this.menuList.add(this.optionsButton);

		/************
		 * QUIT
		 */

		this.quitButton = new GUITextButton(0, 0, "Quit",
				GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID), 14);
		this.quitButton.setCentered(true);
		this.quitButton.setPressedAction(new Actionable() {
			@Override
			public void act() {
				VictusLudusGame.engine.stop();
			}
		});
		this.quitButton.setTooltip("Exits the application");
		this.elementList.add(this.quitButton);
		this.menuList.add(this.quitButton);

		if(this.currentElement < 0 && !this.elementList.isEmpty()) {
			this.currentElement = 0;
		}

		if(!this.menuList.isEmpty()) {
			VictusLudusGame.engine.eventHandler.signal(new SelectEvent(this.menuList.get(this.currentElement), true));
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.GUI#positionElements()
	 */
	@Override
	protected void positionElements() {
		this.width = VictusLudusGame.engine.X_RESOLUTION();
		this.height = VictusLudusGame.engine.Y_RESOLUTION();

		this.titleText.setX(VictusLudusGame.engine.X_RESOLUTION() / 2);
		this.titleText.setY(5);

		this.tooltipText.setX(VictusLudusGame.engine.X_RESOLUTION() / 2);
		this.tooltipText.setY(VictusLudusGame.engine.Y_RESOLUTION() - GUI.fetchFontM(GUI.PMONO_FONT_ID).getLineHeight()-5);

		this.setNextElementPos(VictusLudusGame.engine.Y_RESOLUTION() / 2);
		this.setElementSpacing(5);

		this.newWorldButton.setX(VictusLudusGame.engine.X_RESOLUTION() / 2);
		this.newWorldButton.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.newWorldButton);

		this.optionsButton.setX(VictusLudusGame.engine.X_RESOLUTION() / 2);
		this.optionsButton.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.optionsButton);

		this.quitButton.setX(VictusLudusGame.engine.X_RESOLUTION() / 2);
		this.quitButton.setY(this.getNextElementPos());
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.TooltipListener#onChangeTooltip(com.teamderpy.victusludus.gui.eventhandler.event.TooltipEvent)
	 */
	@Override
	public void onChangeTooltip(final TooltipEvent tooltipEvent) {
		if(!this.isDisabled){
			this.tooltipText.setText(tooltipEvent.getTooltip());
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

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.GUI#registerListeners()
	 */
	@Override
	public void registerListeners() {
		VictusLudusGame.engine.eventHandler.resizeHandler.registerPlease(this);
		VictusLudusGame.engine.eventHandler.tooltipHandler.registerPlease(this);
		VictusLudusGame.engine.eventHandler.keyboardHandler.registerPlease(this);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.GUI#unregisterListeners()
	 */
	@Override
	public void unregisterListeners() {
		VictusLudusGame.engine.eventHandler.resizeHandler.unregisterPlease(this);
		VictusLudusGame.engine.eventHandler.tooltipHandler.unregisterPlease(this);
		VictusLudusGame.engine.eventHandler.keyboardHandler.unregisterPlease(this);
		this.unregisterListeningChildren();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() {
		this.unregisterListeners();
		this.unregisterListeningChildren();
	}

	@Override
	public void onKeyUp (KeyUpEvent keyboardEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyTyped (KeyTypedEvent keyboardEvent) {
		// TODO Auto-generated method stub
		
	}
}
