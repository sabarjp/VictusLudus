package com.teamderpy.victusludus.gui.element;

import org.newdawn.slick.Color;
import org.newdawn.slick.SpriteSheet;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.engine.Actionable;
import com.teamderpy.victusludus.gui.element.imagebutton.ImageButtonImage;
import com.teamderpy.victusludus.gui.eventhandler.ButtonPressListener;
import com.teamderpy.victusludus.gui.eventhandler.HoverListener;
import com.teamderpy.victusludus.gui.eventhandler.MouseListener;
import com.teamderpy.victusludus.gui.eventhandler.SelectListener;
import com.teamderpy.victusludus.gui.eventhandler.event.ButtonPressEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.HoverEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.SelectEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.TooltipEvent;


// TODO: Auto-generated Javadoc
/**
 * The Class GUIImageButton.
 */
public class GUIImageButton extends GUIElement implements Actionable, Selectable, MouseListener, HoverListener, ButtonPressListener, SelectListener{

	/** The tooltip. */
	private String tooltip = "";

	/** The action. */
	private Actionable action;

	/** The img. */
	private ImageButtonImage img;

	/** The is centered. */
	private boolean isCentered = false;

	/** The is depressed. */
	private boolean isDepressed = false;

	/** The is selected. */
	protected boolean isSelected = false;

	/** The is hovered on. */
	protected boolean isHoveredOn = false;

	/** whether or not the tooltip is triggered when selected */
	private boolean isTriggerTooltipOnSelect = false;

	/** The original x. */
	private int originalX = -1;

	/** The sprite sheet. */
	private SpriteSheet spriteSheet;

	/**
	 * Instantiates a new GUI image button.
	 *
	 * @param x the x
	 * @param y the y
	 * @param width the width
	 * @param height the height
	 * @param buttonID the button id
	 * @param spriteSheet the sprite sheet
	 */
	public GUIImageButton(final int x, final int y, final int width, final int height, final byte buttonID, final SpriteSheet spriteSheet) {
		super(x, y, (int)(width* VictusLudus.e.scalingFactor), (int)(height* VictusLudus.e.scalingFactor));

		this.spriteSheet = spriteSheet;
		this.originalX = x;

		this.setImage(buttonID);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#render()
	 */
	@Override
	public void render() {
		if(this.isVisible()){
			//draw image box first
			int imageX;

			if(this.isCentered) {
				imageX = this.originalX - this.img.getWidth()/2;
			} else {
				imageX = this.originalX;
			}

			if(this.isDisabled){
				this.img.draw(imageX , this.y, ImageButtonImage.OFFSET_DISABLE);
			}else if(this.isDepressed){
				this.img.draw(imageX , this.y, ImageButtonImage.OFFSET_PRESS);
			}else if(this.isHoveredOn){
				this.img.draw(imageX , this.y, ImageButtonImage.OFFSET_HOVER);
			}else if(this.isSelected){
				this.img.draw(imageX , this.y, ImageButtonImage.OFFSET_SELECT);
			}else{
				this.img.draw(imageX , this.y, ImageButtonImage.OFFSET_NONE);
			}

			if(VictusLudus.e.IS_DEBUGGING){
				VictusLudus.e.graphics.setColor(Color.red);
				VictusLudus.e.graphics.draw(new org.newdawn.slick.geom.Rectangle(this.x, this.y, this.getWidth(), this.getHeight()));
			}
		}
	}

	/**
	 * Checks if is centered.
	 *
	 * @return true, if is centered
	 */
	public boolean isCentered() {
		return this.isCentered;
	}

	/**
	 * Sets the centered.
	 *
	 * @param isCentered the new centered
	 */
	public void setCentered(final boolean isCentered) {
		this.isCentered = isCentered;

		if(isCentered) {
			this.x = this.originalX - this.getWidth()/2;
		} else {
			this.x = this.originalX;
		}
	}

	/**
	 * Sets the image.
	 *
	 * @param buttonID the new image
	 */
	private void setImage(final byte buttonID) {
		this.img = new ImageButtonImage(buttonID, this.width, this.height, this.spriteSheet);
	}

	/**
	 * Sets the pressed action.
	 *
	 * @param act the new pressed action
	 */
	public void setPressedAction(final com.teamderpy.victusludus.engine.Actionable act){
		this.action = act;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.Selectable#getTooltip()
	 */
	@Override
	public String getTooltip() {
		return this.tooltip;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.Selectable#setTooltip(java.lang.String)
	 */
	@Override
	public void setTooltip(final String tooltip) {
		this.tooltip = tooltip;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.Selectable#isSelected()
	 */
	@Override
	public boolean isSelected() {
		return this.isSelected;
	}

	public boolean isTriggerTooltipOnSelect() {
		return this.isTriggerTooltipOnSelect;
	}

	public void setTriggerTooltipOnSelect(final boolean isTriggerTooltipOnSelect) {
		this.isTriggerTooltipOnSelect = isTriggerTooltipOnSelect;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#setX(int)
	 */
	@Override
	public void setX(final int x) {
		this.originalX = x;
		this.x = x;

		this.setCentered(this.isCentered);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.Selectable#setSelected(boolean)
	 */
	@Override
	public void setSelected(final boolean isSelected) {
		this.isSelected = isSelected;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.Selectable#isHoveredOn()
	 */
	@Override
	public boolean isHoveredOn() {
		return this.isHoveredOn;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.Selectable#setHoveredOn(boolean)
	 */
	@Override
	public void setHoveredOn(final boolean isHoveredOn) {
		this.isHoveredOn = isHoveredOn;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.MouseListener#onMouseClick(com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent)
	 */
	@Override
	public void onMouseClick(final MouseEvent mouseEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(mouseEvent.getButton() == MouseEvent.BUTTON_1){
				if(mouseEvent.isButtonPressed()){
					if(this.isInside(mouseEvent.getX(), mouseEvent.getY())){
						this.isDepressed = true;
					}
				}
				else{
					if(this.isInside(mouseEvent.getX(), mouseEvent.getY())){
						if(this.isDepressed){
							VictusLudus.e.eventHandler.signal(new ButtonPressEvent(this, ""));
							this.isDepressed = false;
						}
					} else {
						this.isDepressed = false;
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.MouseListener#onMouseMove(com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent)
	 */
	@Override
	public void onMouseMove(final MouseEvent mouseEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.isInside(mouseEvent.getX(), mouseEvent.getY())){
				if(!this.isHoveredOn){
					VictusLudus.e.eventHandler.signal(new HoverEvent(this, true));
				}
				VictusLudus.e.eventHandler.signal(new TooltipEvent(this, this.getTooltip()));
			}
			else{
				if(this.isHoveredOn){
					VictusLudus.e.eventHandler.signal(new HoverEvent(this, false));
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.SelectListener#onSelect(com.teamderpy.victusludus.gui.eventhandler.event.SelectEvent)
	 */
	@Override
	public void onSelect(final SelectEvent selectEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.equals(selectEvent.getSource())){
				this.isSelected = true;

				if(this.isTriggerTooltipOnSelect){
					VictusLudus.e.eventHandler.signal(new TooltipEvent(this, this.getTooltip()));
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.SelectListener#onUnselect(com.teamderpy.victusludus.gui.eventhandler.event.SelectEvent)
	 */
	@Override
	public void onUnselect(final SelectEvent selectEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.equals(selectEvent.getSource())){
				this.isSelected = false;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.ButtonPressListener#onButtonPress(com.teamderpy.victusludus.gui.eventhandler.event.ButtonPressEvent)
	 */
	@Override
	public void onButtonPress(final ButtonPressEvent buttonPressEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.equals(buttonPressEvent.getSource())){
				GUIElement.SOUND_SELECT.playAsSoundEffect(1.0F, 1.0F, false);
				this.act();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.HoverListener#onEnter(com.teamderpy.victusludus.gui.eventhandler.event.HoverEvent)
	 */
	@Override
	public void onEnter(final HoverEvent hoverEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.equals(hoverEvent.getSource())){
				this.isHoveredOn = true;
				VictusLudus.e.eventHandler.signal(new TooltipEvent(this, this.getTooltip()));
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.HoverListener#onLeave(com.teamderpy.victusludus.gui.eventhandler.event.HoverEvent)
	 */
	@Override
	public void onLeave(final HoverEvent hoverEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.equals(hoverEvent.getSource())){
				this.isHoveredOn = false;
				VictusLudus.e.eventHandler.signal(new TooltipEvent(this, ""));
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#registerListeners()
	 */
	@Override
	public void registerListeners() {
		VictusLudus.e.eventHandler.mouseHandler.registerPlease(this);
		VictusLudus.e.eventHandler.hoverHandler.registerPlease(this);
		VictusLudus.e.eventHandler.selectHandler.registerPlease(this);
		VictusLudus.e.eventHandler.buttonPressHandler.registerPlease(this);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#unregisterListeners()
	 */
	@Override
	public void unregisterListeners(){
		VictusLudus.e.eventHandler.mouseHandler.unregisterPlease(this);
		VictusLudus.e.eventHandler.hoverHandler.unregisterPlease(this);
		VictusLudus.e.eventHandler.selectHandler.unregisterPlease(this);
		VictusLudus.e.eventHandler.buttonPressHandler.unregisterPlease(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize(){
		this.unregisterListeners();
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.engine.Actionable#act()
	 */
	@Override
	public void act() {
		if(this.action != null) {
			this.action.act();
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#tick()
	 */
	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}
}
