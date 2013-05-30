package com.teamderpy.victusludus.gui.element;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.engine.Actionable;
import com.teamderpy.victusludus.gui.GUI;
import com.teamderpy.victusludus.gui.GUISelection;
import com.teamderpy.victusludus.gui.eventhandler.ButtonPressListener;
import com.teamderpy.victusludus.gui.eventhandler.HoverListener;
import com.teamderpy.victusludus.gui.eventhandler.KeyboardListener;
import com.teamderpy.victusludus.gui.eventhandler.MouseListener;
import com.teamderpy.victusludus.gui.eventhandler.SelectListener;
import com.teamderpy.victusludus.gui.eventhandler.event.ButtonPressEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.HoverEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyboardEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.SelectEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.TooltipEvent;


// TODO: Auto-generated Javadoc
/**
 * The Class GUISelectFieldHorizontal.
 */
public class GUISelectFieldHorizontal extends GUIElement implements Actionable, Selectable, MouseListener, KeyboardListener, HoverListener, ButtonPressListener, SelectListener{
	
	/** The text. */
	private String text = "";
	
	/** The tooltip. */
	private String tooltip = "";
	
	/** The color. */
	private Color color = GUI.ELEMENT_COLOR_DEFAULT;
	
	/** The select color. */
	private Color selectColor = GUI.SELECTION_COLOR_DEFAULT;
	
	/** The hover color. */
	private Color hoverColor = GUI.HOVER_COLOR_DEFAULT;
	
	/** The subcolor. */
	private final Color subcolor = GUI.SUBELEMENT_COLOR_DEFAULT;
	
	/** The subselect color. */
	private final Color subselectColor = GUI.SUBSELECTION_COLOR_DEFAULT;
	
	/** The subhover color. */
	private final Color subhoverColor = GUI.SUBHOVER_COLOR_DEFAULT;
	
	/** The font. */
	private UnicodeFont font;
	
	/** The action. */
	private Actionable action;
	
	/** The selection list. */
	private final ArrayList<GUISelection> selectionList;
	
	/** The current selection ndx. */
	private int currentSelectionNdx = -1;
	
	/** The is centered. */
	private boolean isCentered = false;
	
	/** The is selected. */
	protected boolean isSelected = false;
	
	/** The is hovered on. */
	protected boolean isHoveredOn = false;

	/** The original x. */
	private int originalX = -1;
	
	/** The selection x. */
	private int selectionX = -1;
	
	/** The selection y. */
	private int selectionY = -1;
	
	/** The largest width. */
	private int largestWidth = -1;

	/** The left selector width. */
	private final int leftSelectorWidth;

	/** The left arrow button. */
	private final GUITextButton leftArrowButton;
	
	/** The right arrow button. */
	private final GUITextButton rightArrowButton;

	/**
	 * Instantiates a new gUI select field horizontal.
	 *
	 * @param x the x
	 * @param y the y
	 * @param text the text
	 * @param color the color
	 * @param font the font
	 */
	public GUISelectFieldHorizontal(final int x, final int y, final String text, final Color color, final UnicodeFont font) {
		super(x, y, font.getWidth(text), font.getLineHeight()*2);
		this.text = text;
		this.color = color;
		this.font = font;
		this.originalX = x;

		this.selectionList = new ArrayList<GUISelection>();
		this.currentSelectionNdx = -1;
		this.leftSelectorWidth = font.getWidth("<-") + GUITextButton.BORDER_SIZE + GUITextButton.BORDER_SIZE;

		this.leftArrowButton = new GUITextButton(x,y, "<-", color, font);
		this.rightArrowButton = new GUITextButton(x,y, "->", color, font);

		this.leftArrowButton.setPressedAction(new Actionable(){
			@Override
			public void act(){
				if(GUISelectFieldHorizontal.this.currentSelectionNdx > 0){
					GUISelectFieldHorizontal.this.currentSelectionNdx--;
					VictusLudus.e.eventHandler.signal(new TooltipEvent(this, GUISelectFieldHorizontal.this.getCurrentSelection().getTooltip()));
					GUISelectFieldHorizontal.this.pact();
				}
			}
		});

		this.rightArrowButton.setPressedAction(new Actionable(){
			@Override
			public void act(){
				if(GUISelectFieldHorizontal.this.currentSelectionNdx < GUISelectFieldHorizontal.this.selectionList.size()-1){
					GUISelectFieldHorizontal.this.currentSelectionNdx++;
					VictusLudus.e.eventHandler.signal(new TooltipEvent(this, GUISelectFieldHorizontal.this.getCurrentSelection().getTooltip()));
					GUISelectFieldHorizontal.this.pact();
				}
			}
		});

		this.leftArrowButton.setVisible(false);
		this.rightArrowButton.setVisible(false);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#render()
	 */
	@Override
	public void render() {
		if(this.isVisible){
			Color mc, sc;
			int xo;

			if(this.isDisabled){
				mc = this.color;
				sc = this.subcolor;
			}else if(this.isHoveredOn && this.hoverColor != null){
				mc = this.hoverColor;
				sc = this.subhoverColor;
			}else if(this.isSelected && this.selectColor != null){
				mc = this.selectColor;
				sc = this.subselectColor;
			}else{
				mc = this.color;
				sc = this.subcolor;
			}

			if(this.isCentered){
				xo = this.x-this.font.getWidth(this.text)/2;
			}
			else{
				xo = this.x;
			}

			//main text
			this.font.drawString(xo, this.y, this.text, mc);

			if (!this.selectionList.isEmpty()){
				//adjustment for centering selection
				if(this.isCentered){
					this.selectionX = this.x-this.getCurrentSelection().getWidth()/2;
				}else{
					this.selectionX = this.x;
				}

				this.selectionY = this.y+this.font.getLineHeight()+1;

				//draw selection item
				this.font.drawString(this.selectionX, this.selectionY, this.getCurrentSelection().getText(), sc);

				if(VictusLudus.e.IS_DEBUGGING){
					VictusLudus.e.graphics.setColor(Color.red);
					VictusLudus.e.graphics.draw(new org.newdawn.slick.geom.Rectangle(this.selectionX, this.selectionY, this.getCurrentSelection().getWidth(), this.getCurrentSelection().getHeight()));
				}

				//draw left and right arrows
				int arrowX;

				if(this.isCentered){
					arrowX = this.x-this.largestWidth/2;
				}else{
					arrowX = this.x;
				}

				if (this.currentSelectionNdx > 0){
					if (this.currentSelectionNdx < this.selectionList.size()-1){
						this.leftArrowButton.setX(arrowX - this.leftSelectorWidth - 5);
						this.leftArrowButton.setY(this.selectionY);
						this.leftArrowButton.setVisible(true);

						this.rightArrowButton.setX(arrowX + this.largestWidth + 5);
						this.rightArrowButton.setY(this.selectionY);
						this.rightArrowButton.setVisible(true);
					}else{
						this.leftArrowButton.setX(arrowX - this.leftSelectorWidth - 5);
						this.leftArrowButton.setY(this.selectionY);
						this.leftArrowButton.setVisible(true);

						this.rightArrowButton.setVisible(false);
					}
				} else {
					this.rightArrowButton.setX(arrowX + this.largestWidth + 5);
					this.rightArrowButton.setY(this.selectionY);
					this.rightArrowButton.setVisible(true);

					this.leftArrowButton.setVisible(false);
				}

				super.setHeight(this.font.getLineHeight() + this.getCurrentSelection().getHeight() + 1);

				if(this.font.getWidth(this.text) > this.getCurrentSelection().getWidth()) {
					super.setWidth(this.font.getWidth(this.text));
				} else {
					super.setWidth(this.getCurrentSelection().getWidth());
				}
			}else{
				super.setHeight(this.font.getLineHeight());
				super.setWidth(this.font.getWidth(this.text));
			}

			this.leftArrowButton.render();
			this.rightArrowButton.render();
		}
	}

	/**
	 * Adds the item.
	 *
	 * @param text the text
	 * @param value the value
	 */
	public void addItem(final String text, final String value){
		final GUISelection s = new GUISelection(text, value);
		s.setWidth(this.font.getWidth(text));
		s.setHeight(this.font.getLineHeight());

		if(s.getWidth() > this.largestWidth){
			this.largestWidth = s.getWidth();
		}

		this.selectionList.add(s);

		if(this.currentSelectionNdx == -1){
			this.currentSelectionNdx = 0;
			super.setHeight(this.font.getLineHeight() + this.getCurrentSelection().getHeight() + 1);

			if(this.font.getWidth(text) > this.getCurrentSelection().getWidth()) {
				super.setWidth(this.font.getWidth(text));
			} else {
				super.setWidth(this.getCurrentSelection().getWidth());
			}
		}
	}

	/**
	 * Adds the item.
	 *
	 * @param text the text
	 * @param value the value
	 * @param tooltip the tooltip
	 */
	public void addItem(final String text, final String value, final String tooltip){
		final GUISelection s = new GUISelection(text, value, tooltip);
		s.setWidth(this.font.getWidth(text));
		s.setHeight(this.font.getLineHeight());

		if(s.getWidth() > this.largestWidth){
			this.largestWidth = s.getWidth();
		}

		this.selectionList.add(s);

		if(this.currentSelectionNdx == -1){
			this.currentSelectionNdx = 0;
			super.setHeight(this.font.getLineHeight() + this.getCurrentSelection().getHeight() + 1);

			if(this.font.getWidth(text) > this.getCurrentSelection().getWidth()) {
				super.setWidth(this.font.getWidth(text));
			} else {
				super.setWidth(this.getCurrentSelection().getWidth());
			}
		}
	}

	/* not tested yet, possible error-paws */
	/**
	 * Removes the item.
	 *
	 * @param text the text
	 * @param value the value
	 */
	public void removeItem(final String text, final String value){
		this.selectionList.remove(new GUISelection(text, value));

		if(this.selectionList.size() == 0){
			super.setHeight(this.font.getLineHeight());
			super.setWidth(this.font.getWidth(text));
		}
	}

	/**
	 * Gets the selection by index.
	 *
	 * @param index the index
	 * @return the selection by index
	 */
	public GUISelection getSelectionByIndex(final int index){
		return this.selectionList.get(index);
	}

	/**
	 * Gets the current selection index.
	 *
	 * @return the current selection index
	 */
	public int getCurrentSelectionIndex(){
		return this.currentSelectionNdx;
	}

	/**
	 * Sets the current selection index.
	 *
	 * @param currentSelectionNdx the new current selection index
	 */
	public void setCurrentSelectionIndex(final int currentSelectionNdx){
		this.currentSelectionNdx = currentSelectionNdx;
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize(){
		return this.selectionList.size();
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(final String text) {
		this.text = text;

		if(this.isCentered){
			super.setX(this.originalX-this.font.getWidth(text)/2);
		}
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Sets the color.
	 *
	 * @param color the new color
	 */
	public void setColor(final Color color) {
		this.color = color;
	}

	/**
	 * Gets the select color.
	 *
	 * @return the select color
	 */
	public Color getSelectColor() {
		return this.selectColor;
	}

	/**
	 * Sets the select color.
	 *
	 * @param selectColor the new select color
	 */
	public void setSelectColor(final Color selectColor) {
		this.selectColor = selectColor;
	}

	/**
	 * Gets the hover color.
	 *
	 * @return the hover color
	 */
	public Color getHoverColor() {
		return this.hoverColor;
	}

	/**
	 * Sets the hover color.
	 *
	 * @param hoverColor the new hover color
	 */
	public void setHoverColor(final Color hoverColor) {
		this.hoverColor = hoverColor;
	}

	/**
	 * Gets the select hover color.
	 *
	 * @return the select hover color
	 */
	public Color getSelectHoverColor() {
		return this.selectColor;
	}

	/**
	 * Sets the select hover color.
	 *
	 * @param color the new select hover color
	 */
	public void setSelectHoverColor(final Color color) {
		this.selectColor = color;
		this.hoverColor = color;
	}

	/**
	 * Gets the font.
	 *
	 * @return the font
	 */
	public UnicodeFont getFont() {
		return this.font;
	}

	/**
	 * Sets the font.
	 *
	 * @param font the new font
	 */
	public void setFont(final UnicodeFont font) {
		this.font = font;
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
	 * @see com.teamderpy.victusludus.gui.eventhandler.SelectListener#onSelect(com.teamderpy.victusludus.gui.eventhandler.event.SelectEvent)
	 */
	@Override
	public void onSelect(final SelectEvent selectEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.equals(selectEvent.getSource())){
				this.isSelected = true;
				if(this.selectionList.isEmpty()){
					VictusLudus.e.eventHandler.tooltipHandler.signalAllNow(new TooltipEvent(this, this.getTooltip()));
				}
				else{
					VictusLudus.e.eventHandler.tooltipHandler.signalAllNow(new TooltipEvent(this, this.getCurrentSelection().getTooltip()));
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
		//do nothing
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.HoverListener#onEnter(com.teamderpy.victusludus.gui.eventhandler.event.HoverEvent)
	 */
	@Override
	public void onEnter(final HoverEvent hoverEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.equals(hoverEvent.getSource())){
				this.isHoveredOn = true;
				if(this.selectionList.isEmpty()){
					VictusLudus.e.eventHandler.signal(new TooltipEvent(this, this.getTooltip()));
				}
				else{
					VictusLudus.e.eventHandler.signal(new TooltipEvent(this, this.getCurrentSelection().getTooltip()));
				}
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
			}else{
				if(this.isSelected){
					if(this.selectionList.isEmpty()){
						VictusLudus.e.eventHandler.signal(new TooltipEvent(this, this.getTooltip()));
					}
					else{
						VictusLudus.e.eventHandler.signal(new TooltipEvent(this, this.getCurrentSelection().getTooltip()));
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.KeyboardListener#onKeyPress(com.teamderpy.victusludus.gui.eventhandler.event.KeyboardEvent)
	 */
	@Override
	public void onKeyPress(final KeyboardEvent keyboardEvent) {
		if(this.isSelected){
			if(keyboardEvent.getKey() == Keyboard.KEY_RIGHT){
				if(this.currentSelectionNdx < this.selectionList.size()-1){
					this.currentSelectionNdx++;
					VictusLudus.e.eventHandler.signal(new TooltipEvent(this, this.getCurrentSelection().getTooltip()));
					this.act();
				}
			}
			else if(keyboardEvent.getKey() == Keyboard.KEY_LEFT){
				if(this.currentSelectionNdx > 0){
					this.currentSelectionNdx--;
					VictusLudus.e.eventHandler.signal(new TooltipEvent(this, this.getCurrentSelection().getTooltip()));
					this.act();
				}
			}
			else if(keyboardEvent.getKey() == Keyboard.KEY_RETURN){
				VictusLudus.e.eventHandler.signal(new ButtonPressEvent(this, this.getCurrentSelection().getValue()));
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.MouseListener#onMouseClick(com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent)
	 */
	@Override
	public void onMouseClick(final MouseEvent mouseEvent) {
		//do nothing
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
			}
			else{
				if(this.isHoveredOn){
					VictusLudus.e.eventHandler.signal(new HoverEvent(this, false));
				}
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
		VictusLudus.e.eventHandler.keyboardHandler.registerPlease(this);
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
		VictusLudus.e.eventHandler.keyboardHandler.unregisterPlease(this);

		this.leftArrowButton.unregisterListeners();
		this.rightArrowButton.unregisterListeners();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize(){
		this.unregisterListeners();
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#isInside(int, int)
	 */
	@Override
	public Boolean isInside(final int x, final int y){
		if(x >= this.selectionX && x <= this.selectionX + this.getCurrentSelection().getWidth()){
			if(y >= this.selectionY && y <= this.selectionY + this.getCurrentSelection().getHeight()){
				return true;
			}
		}

		return false;
	}

	/**
	 * Gets the current selection.
	 *
	 * @return the current selection
	 */
	public GUISelection getCurrentSelection() {
		return this.getSelectionByIndex(this.currentSelectionNdx);
	}

	/**
	 * Sets the changed action.
	 *
	 * @param act the new changed action
	 */
	public void setChangedAction(final com.teamderpy.victusludus.engine.Actionable act){
		this.action = act;
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
	}

	/**
	 * Pact.
	 */
	private void pact(){
		this.act();
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#setX(int)
	 */
	@Override
	public void setX(final int x) {
		this.originalX = x;
		this.x = x;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#setDisabled(boolean)
	 */
	@Override
	public void setDisabled(final boolean isDisabled){
		this.isDisabled = isDisabled;
		this.leftArrowButton.setDisabled(isDisabled);
		this.rightArrowButton.setDisabled(isDisabled);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#setVisible(boolean)
	 */
	@Override
	public void setVisible(final boolean isVisible){
		this.isDisabled = isVisible;
		this.leftArrowButton.setVisible(isVisible);
		this.rightArrowButton.setVisible(isVisible);
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
