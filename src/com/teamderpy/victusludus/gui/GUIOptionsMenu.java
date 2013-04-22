package com.teamderpy.victusludus.gui;

import java.util.ArrayList;


import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.DisplayMode;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.engine.Actionable;
import com.teamderpy.victusludus.gui.element.GUIElement;
import com.teamderpy.victusludus.gui.element.GUISelectFieldHorizontal;
import com.teamderpy.victusludus.gui.element.GUIText;
import com.teamderpy.victusludus.gui.element.GUITextButton;
import com.teamderpy.victusludus.gui.element.GUITimer;
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
 * The Class GUIOptionsMenu.
 */
public class GUIOptionsMenu extends GUI implements KeyboardListener, ResizeListener, TooltipListener{
	
	/** The dpi select. */
	private GUISelectFieldHorizontal dpiSelect;
	
	/** The font select main. */
	private GUISelectFieldHorizontal fontSelectMain;
	
	/** The font select big. */
	private GUISelectFieldHorizontal fontSelectBig;
	
	/** The font select small. */
	private GUISelectFieldHorizontal fontSelectSmall;
	
	/** The resolution select. */
	private GUISelectFieldHorizontal resolutionSelect;
	
	/** The full screen select. */
	private GUISelectFieldHorizontal fullScreenSelect;
	
	/** The vsync select. */
	private GUISelectFieldHorizontal vsyncSelect;
	
	/** The frame cap select. */
	private GUISelectFieldHorizontal frameCapSelect;
	
	/** The apply button. */
	private GUITextButton applyButton;
	
	/** The back button. */
	private GUITextButton backButton;
	
	/** The tooltip text. */
	private GUIText tooltipText;
	
	/** The title text. */
	private GUIText titleText;
	
	/* we hold some settings here to reflect changes without actually changing */
	/** The backup title font id. */
	private String BACKUP_TITLE_FONT_ID = GUI.TITLE_FONT_ID;
	
	/** The backup toolt font id. */
	private String BACKUP_TOOLT_FONT_ID = GUI.TOOLT_FONT_ID;
	
	/** The backup pmono font id. */
	private String BACKUP_PMONO_FONT_ID = GUI.PMONO_FONT_ID;
	
	/** The backup scaling factor. */
	private double BACKUP_SCALING_FACTOR = VictusLudus.e.scalingFactor;
	
	/** The backup target framerate. */
	private int BACKUP_TARGET_FRAMERATE = VictusLudus.e.targetFramerate;

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.GUI#create()
	 */
	protected void create() {
		menuList = new ArrayList<GUIElement>();
		
		/************
		 * TITLE
		 */
		
		titleText = new GUIText(0, 0, "Options", GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontL(GUI.TITLE_FONT_ID));
		titleText.setCentered(true);
		elementList.add(titleText);
		
		/************
		 * TOOLTIP
		 */
		
	   tooltipText = new GUIText(0, 0, "", GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontS(GUI.TOOLT_FONT_ID));
		tooltipText.setCentered(true);
	   elementList.add(tooltipText);
		
		/************
		 * DPI SELECT
		 */
		
		dpiSelect = new GUISelectFieldHorizontal(0, 0, "Scaling factor", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
		dpiSelect.setCentered(true);
		dpiSelect.addItem("Tiny", "0.5", "50% scaling");
		if(VictusLudus.e.scalingFactor == 0.5)
			dpiSelect.setCurrentSelectionIndex(0);
		dpiSelect.addItem("Small", "0.75", "75% scaling");
		if(VictusLudus.e.scalingFactor == 0.75)
			dpiSelect.setCurrentSelectionIndex(1);
		dpiSelect.addItem("Normal", "1.0", "100% scaling");
		if(VictusLudus.e.scalingFactor == 1.0)
			dpiSelect.setCurrentSelectionIndex(2);
		dpiSelect.addItem("Large", "1.5", "150% scaling");
		if(VictusLudus.e.scalingFactor == 1.5)
			dpiSelect.setCurrentSelectionIndex(3);
		dpiSelect.addItem("Huge", "2.0", "200% scaling");
		if(VictusLudus.e.scalingFactor == 2.0)
			dpiSelect.setCurrentSelectionIndex(4);
		dpiSelect.setChangedAction(new Actionable(){
			public void act() {
				VictusLudus.e.scalingFactor = Double.parseDouble(dpiSelect.getCurrentSelection().getValue());
				GUI.reloadFonts();
				recreate();
			}
		});
		elementList.add(dpiSelect);
		menuList.add(dpiSelect);
		
		/************
		 * RESOLUTION SELECT
		 */
		
		resolutionSelect = new GUISelectFieldHorizontal(0, 0, "Display resolution", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
		resolutionSelect.setCentered(true);
		
		int i = 0;
		for(DisplayMode d:VictusLudus.e.displayModes){
			resolutionSelect.addItem(d.getWidth() + "x" + d.getHeight() + "x" + d.getBitsPerPixel(), Integer.toString(i++));
			
			if(d.equals(VictusLudus.e.currentDisplayMode))
				resolutionSelect.setCurrentSelectionIndex(resolutionSelect.getSize()-1);
		}

		elementList.add(resolutionSelect);
		menuList.add(resolutionSelect);
		
		/************
		 * FULLSCREEN SELECT
		 */
		
		fullScreenSelect = new GUISelectFieldHorizontal(0, 0, "Fullscreen?", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
		fullScreenSelect.setCentered(true);
		
		fullScreenSelect.addItem("Yes", "1", "Enable fullscreen");
		fullScreenSelect.addItem("No", "0", "Disable fullscreen");
		
		if(VictusLudus.e.IS_FULLSCREEN)
			fullScreenSelect.setCurrentSelectionIndex(0);
		else
			fullScreenSelect.setCurrentSelectionIndex(1);

		elementList.add(fullScreenSelect);
		menuList.add(fullScreenSelect);
		
		/************
		 * VSYNC SELECT
		 */
		
		vsyncSelect = new GUISelectFieldHorizontal(0, 0, "Vsync?", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
		vsyncSelect.setCentered(true);
		
		vsyncSelect.addItem("Yes", "1", "Enable vsync");
		vsyncSelect.addItem("No", "0", "Disable vsync");
		
		if(VictusLudus.e.IS_VSYNC)
			vsyncSelect.setCurrentSelectionIndex(0);
		else
			vsyncSelect.setCurrentSelectionIndex(1);
		
		elementList.add(vsyncSelect);
		menuList.add(vsyncSelect);
		
		/************
		 * FRAMERATE CAP SELECT
		 */
		
		frameCapSelect = new GUISelectFieldHorizontal(0, 0, "Framerate cap", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
		frameCapSelect.setCentered(true);
		frameCapSelect.addItem("15", "15", "Render no more than 15 frames per second");
		if(VictusLudus.e.targetFramerate == 15)
			frameCapSelect.setCurrentSelectionIndex(0);
		frameCapSelect.addItem("30", "30", "Render no more than 30 frames per second");
		if(VictusLudus.e.targetFramerate == 30)
			frameCapSelect.setCurrentSelectionIndex(1);
		frameCapSelect.addItem("60", "60", "Render no more than 60 frames per second");
		if(VictusLudus.e.targetFramerate == 60)
			frameCapSelect.setCurrentSelectionIndex(2);
		frameCapSelect.addItem("120", "120", "Render no more than 120 frames per second");
		if(VictusLudus.e.targetFramerate == 120)
			frameCapSelect.setCurrentSelectionIndex(3);
		frameCapSelect.addItem("Uncapped", Integer.toString(Integer.MAX_VALUE), "Render no more than " + Integer.MAX_VALUE + " frames per second");
		if(VictusLudus.e.targetFramerate > 120)
			frameCapSelect.setCurrentSelectionIndex(4);
		frameCapSelect.setChangedAction(new Actionable(){
			public void act() {
				VictusLudus.e.targetFramerate = Integer.parseInt(frameCapSelect.getCurrentSelection().getValue());
			}
		});
		elementList.add(frameCapSelect);
		menuList.add(frameCapSelect);
		
		/************
		 * MAIN FONT
		 */
		
		fontSelectMain = new GUISelectFieldHorizontal(0, 0, "Main font", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
		fontSelectMain.setCentered(true);
		for(FontFile f:VictusLudus.resources.getFontHash().values()){
			fontSelectMain.addItem(f.getName(), f.getId(), f.getPath());
			
			if(GUI.PMONO_FONT_ID.equals(f.getId()))
				fontSelectMain.setCurrentSelectionIndex(fontSelectMain.getSize()-1);
		}
		
		fontSelectMain.setChangedAction(new Actionable(){
			public void act() {
				GUI.PMONO_FONT_ID = fontSelectMain.getCurrentSelection().getValue();
				recreate();
			}
		});
		
		elementList.add(fontSelectMain);
		menuList.add(fontSelectMain);
		
		/************
		 * BIG FONT
		 */
		
		fontSelectBig = new GUISelectFieldHorizontal(0, 0, "Large font", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
		fontSelectBig.setCentered(true);
		for(FontFile f:VictusLudus.resources.getFontHash().values()){
			fontSelectBig.addItem(f.getName(), f.getId(), f.getPath());
			
			if(GUI.TITLE_FONT_ID.equals(f.getId()))
				fontSelectBig.setCurrentSelectionIndex(fontSelectBig.getSize()-1);
		}
		
		fontSelectBig.setChangedAction(new Actionable(){
			public void act() {
				GUI.TITLE_FONT_ID = fontSelectBig.getCurrentSelection().getValue();
				recreate();
			}
		});
		
		elementList.add(fontSelectBig);
		menuList.add(fontSelectBig);
		
		/************
		 * SMALL FONT
		 */
		
		fontSelectSmall = new GUISelectFieldHorizontal(0, 0, "Small font", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID));
		fontSelectSmall.setCentered(true);
		for(FontFile f:VictusLudus.resources.getFontHash().values()){
			fontSelectSmall.addItem(f.getName(), f.getId(), f.getPath());
			
			if(GUI.TOOLT_FONT_ID.equals(f.getId()))
				fontSelectSmall.setCurrentSelectionIndex(fontSelectSmall.getSize()-1);
		}
		
		fontSelectSmall.setChangedAction(new Actionable(){
			public void act() {
				GUI.TOOLT_FONT_ID = fontSelectSmall.getCurrentSelection().getValue();
				recreate();
			}
		});
		
		elementList.add(fontSelectSmall);
		menuList.add(fontSelectSmall);
		
		/************
		 * APPLY CHANGES
		 */
		
		applyButton = new GUITextButton(0, 0, "Apply & save changes", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID), 16);
		applyButton.setCentered(true);
		applyButton.setPressedAction(new Actionable() {
					public void act() {
						DisplayMode selectedDisplayMode = VictusLudus.e.displayModes.get(Integer.parseInt(resolutionSelect.getCurrentSelection().getValue()));
						boolean selectedFullscreen = false;
						boolean selectedVsync = false;
						
						if (fullScreenSelect.getCurrentSelection().getValue() == "1")
							selectedFullscreen = true;
						
						if (vsyncSelect.getCurrentSelection().getValue() == "1")
							selectedVsync = true;
						
						//overwrite backup settings
						BACKUP_TITLE_FONT_ID = GUI.TITLE_FONT_ID;
						BACKUP_TOOLT_FONT_ID = GUI.TOOLT_FONT_ID;
						BACKUP_PMONO_FONT_ID = GUI.PMONO_FONT_ID;
						BACKUP_SCALING_FACTOR = VictusLudus.e.scalingFactor;
						BACKUP_TARGET_FRAMERATE = VictusLudus.e.targetFramerate;
						
						//save to preferences
						VictusLudus.e.preferences.write("settings->interface->fonts->main:" + GUI.PMONO_FONT_ID);
						VictusLudus.e.preferences.write("settings->interface->fonts->big:" + GUI.TITLE_FONT_ID);
						VictusLudus.e.preferences.write("settings->interface->fonts->small:" + GUI.TOOLT_FONT_ID);
						VictusLudus.e.preferences.write("settings->interface->dpi:" + VictusLudus.e.scalingFactor);
						VictusLudus.e.preferences.write("settings->interface->maximum framerate:" + VictusLudus.e.targetFramerate);
						
						//only go through this if a display mode option changed
						if(!VictusLudus.e.currentDisplayMode.equals(selectedDisplayMode) || VictusLudus.e.IS_FULLSCREEN != selectedFullscreen || VictusLudus.e.IS_VSYNC != selectedVsync){
							final DisplayMode oldDisplayMode = VictusLudus.e.currentDisplayMode;
							final boolean oldIsFullscreen = VictusLudus.e.IS_FULLSCREEN;
							final boolean oldVsync = VictusLudus.e.IS_VSYNC;
							
							//change display mode
							VictusLudus.e.IS_FULLSCREEN = selectedFullscreen;
							VictusLudus.e.IS_VSYNC = selectedVsync;
							VictusLudus.e.changeDisplayMode(selectedDisplayMode);
							VictusLudus.e.eventHandler.resizeHandler.signalAllNow(new ResizeEvent(this, selectedDisplayMode.getWidth(), selectedDisplayMode.getHeight()));
							
							final DialogBox db = new DialogBox(2);
							db.setTitleText("Confirm changes");
							db.setMessageText("Would you like to keep these display settings?");
							db.setButton1Text("Keep");
							db.setButton2Text("Revert");
							
							GUITimer timer = new GUITimer();
							timer.setTimerTime(15000);
							timer.setTimerAction(new Actionable(){
								public void act(){
									db.triggerButton2Act();
								}
							});
							
							db.addElementItem(timer);
							db.setMessageText("Would you like to keep these display settings? Reverting automatically if no response.");
							
							db.setButton1Action(new Actionable(){
								public void act(){
									VictusLudus.e.changeGUI(new com.teamderpy.victusludus.gui.GUIOptionsMenu());
									VictusLudus.e.preferences.write("settings->video->x resolution:" + VictusLudus.e.currentDisplayMode.getWidth());
									VictusLudus.e.preferences.write("settings->video->y resolution:" + VictusLudus.e.currentDisplayMode.getHeight());
									VictusLudus.e.preferences.write("settings->video->refresh rate:" + VictusLudus.e.currentDisplayMode.getFrequency());
									VictusLudus.e.preferences.write("settings->video->bit depth:" + VictusLudus.e.currentDisplayMode.getBitsPerPixel());
									VictusLudus.e.preferences.write("settings->video->fullscreen:" + VictusLudus.e.IS_FULLSCREEN);
									VictusLudus.e.preferences.write("settings->video->vsync:" + VictusLudus.e.IS_VSYNC);
									
									db.destroy();
								}
							});
							
							db.setButton2Action(new Actionable(){
								public void act(){
									VictusLudus.e.IS_FULLSCREEN = oldIsFullscreen;
									VictusLudus.e.IS_VSYNC = oldVsync;
									VictusLudus.e.changeDisplayMode(oldDisplayMode);
									VictusLudus.e.eventHandler.resizeHandler.signalAllNow(new ResizeEvent(this, oldDisplayMode.getWidth(), oldDisplayMode.getHeight()));
									db.destroy();
									recreate();
								}
							});
							db.show();
						}
					}
				});
		applyButton.setTooltip("Apply and save changes");
		elementList.add(applyButton);
		menuList.add(applyButton);
		
		/************
		 * BACK
		 */
		
		backButton = new GUITextButton(0, 0, "Back", GUI.ELEMENT_COLOR_DEFAULT, GUI.fetchFontM(GUI.PMONO_FONT_ID), 16);
		backButton.setCentered(true);
		backButton.setPressedAction(new Actionable() {
					public void act() {
						/*reverse any changes*/
						GUI.TITLE_FONT_ID = BACKUP_TITLE_FONT_ID;
						GUI.TOOLT_FONT_ID = BACKUP_TOOLT_FONT_ID;
						GUI.PMONO_FONT_ID = BACKUP_PMONO_FONT_ID;
						VictusLudus.e.targetFramerate = BACKUP_TARGET_FRAMERATE;
						
						if(VictusLudus.e.scalingFactor != BACKUP_SCALING_FACTOR){
							VictusLudus.e.scalingFactor = BACKUP_SCALING_FACTOR;
							GUI.reloadFonts();
						}else{
							VictusLudus.e.scalingFactor = BACKUP_SCALING_FACTOR;
						}
						
						new com.teamderpy.victusludus.gui.GUIMainMenu().show();
					}
				});
		backButton.setTooltip("Return to the main menu");
		elementList.add(backButton);
		menuList.add(backButton);
		
		if(currentElement < 0 && !elementList.isEmpty())
			currentElement = 0;
		
		if(!menuList.isEmpty())
			VictusLudus.e.eventHandler.signal(new SelectEvent(menuList.get(currentElement), true));
	}
	
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.GUI#positionElements()
	 */
	@Override
	protected void positionElements() {
		this.width = VictusLudus.e.X_RESOLUTION();
		this.height = VictusLudus.e.Y_RESOLUTION();
		
		titleText.setX(VictusLudus.e.X_RESOLUTION() / 2);
		titleText.setY(5);
		
		tooltipText.setX(VictusLudus.e.X_RESOLUTION() / 2);
		tooltipText.setY(VictusLudus.e.Y_RESOLUTION() - GUI.fetchFontM(GUI.PMONO_FONT_ID).getLineHeight()-5);
		
		setNextElementPos(titleText.getHeight() + 20);
		setElementSpacing(5);
		
		dpiSelect.setX(VictusLudus.e.X_RESOLUTION() / 2);
		dpiSelect.setY(getNextElementPos());
		nextElementPosIncrement(dpiSelect);
		
		resolutionSelect.setX(VictusLudus.e.X_RESOLUTION() / 2);
		resolutionSelect.setY(getNextElementPos());
		nextElementPosIncrement(resolutionSelect);
		
		fullScreenSelect.setX(VictusLudus.e.X_RESOLUTION() / 2);
		fullScreenSelect.setY(getNextElementPos());
		nextElementPosIncrement(fullScreenSelect);
		
		vsyncSelect.setX(VictusLudus.e.X_RESOLUTION() / 2);
		vsyncSelect.setY(getNextElementPos());
		nextElementPosIncrement(vsyncSelect);
		
		frameCapSelect.setX(VictusLudus.e.X_RESOLUTION() / 2);
		frameCapSelect.setY(getNextElementPos());
		nextElementPosIncrement(frameCapSelect);
		
		fontSelectMain.setX(VictusLudus.e.X_RESOLUTION() / 2);
		fontSelectMain.setY(getNextElementPos());
		nextElementPosIncrement(fontSelectMain);
		
		fontSelectBig.setX(VictusLudus.e.X_RESOLUTION() / 2);
		fontSelectBig.setY(getNextElementPos());
		nextElementPosIncrement(fontSelectBig);
		
		fontSelectSmall.setX(VictusLudus.e.X_RESOLUTION() / 2);
		fontSelectSmall.setY(getNextElementPos());
		nextElementPosIncrement(fontSelectSmall);
		nextElementPosIncrement(15);
		
		applyButton.setX(VictusLudus.e.X_RESOLUTION() / 2);
		applyButton.setY(getNextElementPos());
		nextElementPosIncrement(applyButton);
		
		backButton.setX(VictusLudus.e.X_RESOLUTION() / 2);
		backButton.setY(getNextElementPos());
		nextElementPosIncrement(backButton);
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
	public void onKeyPress(KeyboardEvent keyboardEvent) {
		if(!isDisabled){
			if (keyboardEvent.getKey() == Keyboard.KEY_DOWN) {
				if (currentElement < menuList.size() - 1) {
					VictusLudus.e.eventHandler.signal(new SelectEvent(menuList.get(currentElement), false));
					VictusLudus.e.eventHandler.signal(new SelectEvent(menuList.get(++currentElement), true));
				}
			} else if (keyboardEvent.getKey() == Keyboard.KEY_UP) {
				if (currentElement > 0) {
					VictusLudus.e.eventHandler.signal(new SelectEvent(menuList.get(currentElement), false));
					VictusLudus.e.eventHandler.signal(new SelectEvent(menuList.get(--currentElement), true));
				}
			} else if (keyboardEvent.getKey() == Keyboard.KEY_RETURN) {
				VictusLudus.e.eventHandler.signal(new ButtonPressEvent(menuList.get(currentElement), ""));
			} else if (keyboardEvent.getKey() == Keyboard.KEY_ESCAPE) {
				backButton.act();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.GUI#registerListeners()
	 */
	public void registerListeners() {
		VictusLudus.e.eventHandler.resizeHandler.registerPlease(this);
		VictusLudus.e.eventHandler.tooltipHandler.registerPlease(this);
		VictusLudus.e.eventHandler.keyboardHandler.registerPlease(this);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.GUI#unregisterListeners()
	 */
	public void unregisterListeners() {
		VictusLudus.e.eventHandler.resizeHandler.unregisterPlease(this);
		VictusLudus.e.eventHandler.tooltipHandler.unregisterPlease(this);
		VictusLudus.e.eventHandler.keyboardHandler.unregisterPlease(this);
		unregisterListeningChildren();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() {
		unregisterListeners();
		unregisterListeningChildren();
	}
}
