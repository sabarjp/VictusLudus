package com.teamderpy.victusludus.gui;

import java.util.ArrayList;


import org.lwjgl.input.Keyboard;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.engine.Actionable;
import com.teamderpy.victusludus.game.Game;
import com.teamderpy.victusludus.game.GameSettings;
import com.teamderpy.victusludus.gui.element.GUIElement;
import com.teamderpy.victusludus.gui.element.GUISelectFieldHorizontal;
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
 * The Class GUINewGameMenu.
 */
public class GUINewGameMenu extends GUI implements KeyboardListener, ResizeListener, TooltipListener{

	/** The world size select. */
	private GUISelectFieldHorizontal worldSizeSelect;

	/** The world variety. */
	private GUISelectFieldHorizontal biotaVariety;

	/** The world smoothness. */
	private GUISliderHorizontal worldSmoothness;

	/** The world randomness (spikey factor). */
	private GUISliderHorizontal worldRandomness;

	/** The world scaleness? */
	private GUISliderHorizontal worldScaleness;


	/** The world plateu factor */
	private GUISliderHorizontal worldPlateauFactor;

	/** The back button. */
	private GUITextButton backButton;

	/** The continue button. */
	private GUITextButton continueButton;

	/** The world name label. */
	private GUIText worldNameLabel;

	/** The world name field. */
	private GUITextField worldNameField;

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

		this.titleText = new GUIText(0, 0, "New Organism", GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontL(GUI.TITLE_FONT_ID));
		this.titleText.setCentered(true);
		this.elementList.add(this.titleText);

		/************
		 * TOOLTIP
		 */

		this.tooltipText = new GUIText(0, 0, "", GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontS(GUI.TOOLT_FONT_ID));
		this.tooltipText.setCentered(true);
		this.elementList.add(this.tooltipText);


		/************
		 * ORGANISM NAME LABEL
		 */

		this.worldNameLabel = new GUIText(0, 0, "", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
		this.worldNameLabel.setText("Organism name");
		this.worldNameLabel.setCentered(true);
		this.elementList.add(this.worldNameLabel);

		/************
		 * ORGANISM NAME
		 */

		this.worldNameField = new GUITextField(0, 0, 200, GUI.SUBELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
		this.worldNameField.setText("");
		this.worldNameField.setMaximumSize(15);
		this.worldNameField.setTooltip("The name of your organism");
		this.worldNameField.setCentered(true);
		this.worldNameField.setTypingAction(new Actionable(){
			@Override
			public void act(){
				GUINewGameMenu.this.titleText.setText(GUINewGameMenu.this.worldNameField.getText());
			}
		});
		this.elementList.add(this.worldNameField);
		this.menuList.add(this.worldNameField);

		/************
		 * WORLD SIZE
		 */

		this.worldSizeSelect = new GUISelectFieldHorizontal(0, 0, "World size", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
		this.worldSizeSelect.setCentered(true);
		this.worldSizeSelect.addItem("Tiny", "32", "A very small area");
		this.worldSizeSelect.addItem("Small", "64", "A small area");
		this.worldSizeSelect.addItem("Average", "128", "A normal area");
		this.worldSizeSelect.setCurrentSelectionIndex(2);
		this.worldSizeSelect.addItem("Large", "256", "A large area");
		this.worldSizeSelect.addItem("Huge", "512", "A huge area");
		this.worldSizeSelect.setTooltip("Pick the size of your starting area");
		this.elementList.add(this.worldSizeSelect);
		this.menuList.add(this.worldSizeSelect);

		/************
		 * BIOTA VARIETY
		 */

		this.biotaVariety = new GUISelectFieldHorizontal(0, 0, "Biota variety", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
		this.biotaVariety.setCentered(true);
		this.biotaVariety.addItem("Minor", "1", "Life forms are more or less the same");
		this.biotaVariety.addItem("Medium", "2", "Life forms have a good amount of variety");
		this.biotaVariety.setCurrentSelectionIndex(1);
		this.biotaVariety.addItem("Major", "3", "Life forms are chaotic and very random. ");

		this.biotaVariety.setTooltip("Pick the variety of the flora and fauna");
		this.elementList.add(this.biotaVariety);
		this.menuList.add(this.biotaVariety);

		/************
		 * WORLD SMOOTHNESS
		 */

		this.worldSmoothness = new GUISliderHorizontal(0, 0, "Terrain smoothness", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
		this.worldSmoothness.setCentered(true);
		this.worldSmoothness.setMinSliderValue(0.0F);
		this.worldSmoothness.setMaxSliderValue(10.0F);
		this.worldSmoothness.setCurrentSliderValue(2.0F);
		this.worldSmoothness.setSteppingFactor(10.0F);
		this.worldSmoothness.setOptionalMinText("Less");
		this.worldSmoothness.setOptionalMaxText("More");
		this.elementList.add(this.worldSmoothness);
		this.menuList.add(this.worldSmoothness);

		/************
		 * WORLD RANDOMNESS
		 */

		this.worldRandomness = new GUISliderHorizontal(0, 0, "Terrain type", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
		this.worldRandomness.setCentered(true);
		this.worldRandomness.setMinSliderValue(0.0F);
		this.worldRandomness.setMaxSliderValue(1.5F);
		this.worldRandomness.setCurrentSliderValue(0.25F);
		this.worldRandomness.setSteppingFactor(20.0F);
		this.worldRandomness.setOptionalMinText("Flat");
		this.worldRandomness.setOptionalMaxText("Hills");
		this.elementList.add(this.worldRandomness);
		this.menuList.add(this.worldRandomness);

		/************
		 * WORLD SCALENESS
		 */

		this.worldScaleness = new GUISliderHorizontal(0, 0, "Terrain scale", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
		this.worldScaleness.setCentered(true);
		this.worldScaleness.setMinSliderValue(1.0F);
		this.worldScaleness.setMaxSliderValue(12.0F);
		this.worldScaleness.setSteppingFactor(11.0F);
		this.worldScaleness.setCurrentSliderValue(8F);
		this.worldScaleness.setOptionalMinText("Small");
		this.worldScaleness.setOptionalMaxText("Large");
		this.elementList.add(this.worldScaleness);
		this.menuList.add(this.worldScaleness);

		/************
		 * WORLD PLATEAU FACTOR
		 */

		this.worldPlateauFactor = new GUISliderHorizontal(0, 0, "Hill & valley types", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
		this.worldPlateauFactor.setCentered(true);
		this.worldPlateauFactor.setMinSliderValue(0.0F);
		this.worldPlateauFactor.setMaxSliderValue(0.30F);
		this.worldPlateauFactor.setSteppingFactor(20.0F);
		this.worldPlateauFactor.setCurrentSliderValue(0.20F);
		this.worldPlateauFactor.setOptionalMinText("Peaks");
		this.worldPlateauFactor.setOptionalMaxText("Plains");
		this.elementList.add(this.worldPlateauFactor);
		this.menuList.add(this.worldPlateauFactor);


		/************
		 * CONTINUE
		 */

		this.continueButton = new GUITextButton(0, 0, "Generate", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID), 8);
		this.continueButton.setCentered(true);
		this.continueButton.setPressedAction(new Actionable() {
			@Override
			public void act() {
				if(GUINewGameMenu.this.worldNameField.getText().length() > 0){
					//new com.teamderpy.victusludus.gui.GUINewGameAvatarMenu().show();

					GameSettings requestedSettings = new GameSettings();

					requestedSettings.setRequestedMapHeight(Integer.valueOf(GUINewGameMenu.this.worldSizeSelect.getCurrentSelection().getValue()));
					requestedSettings.setRequestedMapWidth(Integer.valueOf(GUINewGameMenu.this.worldSizeSelect.getCurrentSelection().getValue()));
					requestedSettings.setRequestedMapSmoothness(GUINewGameMenu.this.worldSmoothness.getCurrentSliderValue());
					requestedSettings.setRequestedMapRandomness(GUINewGameMenu.this.worldRandomness.getCurrentSliderValue());
					requestedSettings.setRequestedMapScale(GUINewGameMenu.this.worldScaleness.getCurrentSliderValue());
					requestedSettings.setRequestedMapPlateauFactor(GUINewGameMenu.this.worldPlateauFactor.getCurrentSliderValue());

					VictusLudus.e.changeGUI(null);
					VictusLudus.e.changeGame(new Game(), requestedSettings);
				} else {
					final DialogBox db = new DialogBox(1);
					db.setTitleText("Organism name?");
					db.setMessageText("You forgot to enter an organism name!");
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

		this.worldNameLabel.setX(VictusLudus.e.X_RESOLUTION() / 2);
		this.worldNameLabel.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.worldNameLabel);

		this.worldNameField.setX(VictusLudus.e.X_RESOLUTION() / 2);
		this.worldNameField.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.worldNameField);

		this.worldSizeSelect.setX(VictusLudus.e.X_RESOLUTION() / 2);
		this.worldSizeSelect.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.worldSizeSelect);

		this.biotaVariety.setX(VictusLudus.e.X_RESOLUTION() / 2);
		this.biotaVariety.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.biotaVariety);
		this.nextElementPosIncrement(5);

		this.worldSmoothness.setX(VictusLudus.e.X_RESOLUTION() / 2);
		this.worldSmoothness.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.worldSmoothness);
		this.nextElementPosIncrement(5);

		this.worldRandomness.setX(VictusLudus.e.X_RESOLUTION() / 2);
		this.worldRandomness.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.worldRandomness);
		this.nextElementPosIncrement(5);

		this.worldScaleness.setX(VictusLudus.e.X_RESOLUTION() / 2);
		this.worldScaleness.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.worldScaleness);
		this.nextElementPosIncrement(5);

		this.worldPlateauFactor.setX(VictusLudus.e.X_RESOLUTION() / 2);
		this.worldPlateauFactor.setY(this.getNextElementPos());
		this.nextElementPosIncrement(this.worldPlateauFactor);
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
