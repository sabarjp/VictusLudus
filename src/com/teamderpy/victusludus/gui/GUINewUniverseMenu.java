package com.teamderpy.victusludus.gui;

import java.util.ArrayList;


import org.lwjgl.input.Keyboard;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.engine.Actionable;
import com.teamderpy.victusludus.game.cosmos.Cosmos;
import com.teamderpy.victusludus.game.cosmos.UniverseSettings;
import com.teamderpy.victusludus.gui.element.GUIElement;
import com.teamderpy.victusludus.gui.element.GUISliderHorizontal;
import com.teamderpy.victusludus.gui.element.GUIText;
import com.teamderpy.victusludus.gui.element.GUITextButton;
import com.teamderpy.victusludus.gui.element.GUITextField;
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
 * The Class GUINewUniverseMenu.
 */
public class GUINewUniverseMenu extends GUI implements KeyboardListener, ResizeListener, TooltipListener{
	/** How old the universe is */
	private GUISliderHorizontal universeAgeSlider;

	/** How dense the universe is */
	private GUISliderHorizontal universeDensitySlider;

	/** The back button. */
	private GUITextButton backButton;

	/** The continue button. */
	private GUITextButton continueButton;

	/** The universe name label. */
	private GUIText universeNameLabel;

	/** The universe name field. */
	private GUITextField universeNameField;

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

		this.titleText = new GUIText(0, 0, "New Universe", GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontL(GUI.TITLE_FONT_ID));
		this.titleText.setCentered(true);
		this.elementList.add(this.titleText);

		/************
		 * TOOLTIP
		 */

		this.tooltipText = new GUIText(0, 0, "", GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontS(GUI.TOOLT_FONT_ID));
		this.tooltipText.setCentered(true);
		this.elementList.add(this.tooltipText);


		/************
		 * UNIVERSE NAME LABEL
		 */

		this.universeNameLabel = new GUIText(0, 0, "", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
		this.universeNameLabel.setText("Universe name");
		this.universeNameLabel.setCentered(true);
		this.elementList.add(this.universeNameLabel);

		/************
		 * UNIVERSE NAME
		 */

		this.universeNameField = new GUITextField(0, 0, 200, GUI.SUBELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
		this.universeNameField.setText("");
		this.universeNameField.setMaximumSize(15);
		this.universeNameField.setTooltip("The name of your universe");
		this.universeNameField.setCentered(true);
		this.universeNameField.setTypingAction(new Actionable(){
			@Override
			public void act(){
				GUINewUniverseMenu.this.titleText.setText(GUINewUniverseMenu.this.universeNameField.getText());
			}
		});
		this.elementList.add(this.universeNameField);
		this.menuList.add(this.universeNameField);

		/************
		 * UNIVERSE AGE
		 */

		this.universeAgeSlider = new GUISliderHorizontal(0, 0, "Universe age", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
		this.universeAgeSlider.setCentered(true);
		this.universeAgeSlider.setMinSliderValue(3.0F);
		this.universeAgeSlider.setMaxSliderValue(33.0F);
		this.universeAgeSlider.setCurrentSliderValue(12.0F);
		this.universeAgeSlider.setSteppingFactor(10.0F);
		this.universeAgeSlider.setOptionalMinText("Young");
		this.universeAgeSlider.setOptionalMaxText("Old");
		this.elementList.add(this.universeAgeSlider);
		this.menuList.add(this.universeAgeSlider);

		/************
		 * UNIVERSE DENSITY
		 */

		this.universeDensitySlider = new GUISliderHorizontal(0, 0, "Universe density", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
		this.universeDensitySlider.setCentered(true);
		this.universeDensitySlider.setMinSliderValue(0.0F);
		this.universeDensitySlider.setMaxSliderValue(1.5F);
		this.universeDensitySlider.setCurrentSliderValue(0.25F);
		this.universeDensitySlider.setSteppingFactor(20.0F);
		this.universeDensitySlider.setOptionalMinText("Sparse");
		this.universeDensitySlider.setOptionalMaxText("Dense");
		this.elementList.add(this.universeDensitySlider);
		this.menuList.add(this.universeDensitySlider);

		/************
		 * CONTINUE
		 */

		this.continueButton = new GUITextButton(0, 0, "Generate", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID), 8);
		this.continueButton.setCentered(true);
		this.continueButton.setPressedAction(new Actionable() {
			@Override
			public void act() {
				if(GUINewUniverseMenu.this.universeNameField.getText().length() > 0){
					UniverseSettings requestedSettings = new UniverseSettings();

					requestedSettings.setRequestedUniAge(GUINewUniverseMenu.this.universeAgeSlider.getCurrentSliderValue());
					requestedSettings.setRequestedUniDensity(GUINewUniverseMenu.this.universeDensitySlider.getCurrentSliderValue());

					VictusLudus.e.changeGUI(null);
					VictusLudus.e.changeView(new Cosmos(), requestedSettings);
				} else {
					final DialogBox db = new DialogBox(1);
					db.setTitleText("Universe name?");
					db.setMessageText("You forgot to enter a universe name!");
					db.setButton1Text("OK!");
					db.setButton1Action(new Actionable(){
						@Override
						public void act(){
							db.destroy();
						}
					});
					db.show();
				}
			}
		});
		this.continueButton.setTooltip("Go to the next screen");
		this.elementList.add(this.continueButton);
		this.menuList.add(this.continueButton);

		/************
		 * BACK
		 */

		this.backButton = new GUITextButton(0, 0, "Back", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID), 8);
		this.backButton.setCentered(true);
		this.backButton.setPressedAction(new Actionable() {
			@Override
			public void act() {
				new com.teamderpy.victusludus.gui.GUIMainMenu().show();
			}
		});
		this.backButton.setTooltip("Go back to the menu");
		this.elementList.add(this.backButton);
		this.menuList.add(this.backButton);

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

		this.titleText.setX(VictusLudus.e.X_RESOLUTION() / 2);
		this.titleText.setY(5);

		this.tooltipText.setX(VictusLudus.e.X_RESOLUTION() / 2);
		this.tooltipText.setY(VictusLudus.e.Y_RESOLUTION() - GUI.fetchFontM(GUI.PMONO_FONT_ID).getLineHeight()-5);

		this.setNextElementPos(this.titleText.getHeight() + 50);
		this.setElementSpacing(5);

		this.universeNameLabel.setX(VictusLudus.e.X_RESOLUTION() / 2);
		this.universeNameLabel.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.universeNameLabel);

		this.universeNameField.setX(VictusLudus.e.X_RESOLUTION() / 2);
		this.universeNameField.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.universeNameField);

		this.universeAgeSlider.setX(VictusLudus.e.X_RESOLUTION() / 2);
		this.universeAgeSlider.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.universeAgeSlider);
		this.nextElementPosIncrement(5);

		this.universeDensitySlider.setX(VictusLudus.e.X_RESOLUTION() / 2);
		this.universeDensitySlider.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.universeDensitySlider);
		this.nextElementPosIncrement(5);

		this.continueButton.setX(VictusLudus.e.X_RESOLUTION() / 2);
		this.continueButton.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.continueButton);
		this.nextElementPosIncrement(5);

		this.backButton.setX(VictusLudus.e.X_RESOLUTION() / 2);
		this.backButton.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.backButton);
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
				this.backButton.act();
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
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize(){
		this.unregisterListeners();
		this.unregisterListeningChildren();
	}
}
