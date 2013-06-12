package com.teamderpy.victusludus.gui.element;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.*;

import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.VictusLudusGame.enginengine.Actionable;
import com.teamderpy.VictusLudusGame.enginengine.Time;
import com.teamderpy.victusludus.game.ScreenCoord;
import com.teamderpy.victusludus.gui.GUI;
import com.teamderpy.victusludus.gui.eventhandler.FocusListener;
import com.teamderpy.victusludus.gui.eventhandler.HoverListener;
import com.teamderpy.victusludus.gui.eventhandler.KeyboardListener;
import com.teamderpy.victusludus.gui.eventhandler.MouseListener;
import com.teamderpy.victusludus.gui.eventhandler.SelectListener;
import com.teamderpy.victusludus.gui.eventhandler.event.FocusEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.HoverEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.KeyDownEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.SelectEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.TooltipEvent;




/**
 * The Class GUITextField.
 */
public class GUITextField extends GUIElement implements Actionable, Selectable, MouseListener, KeyboardListener, HoverListener, SelectListener, FocusListener{

	/** The Constant FIELD_1. */
	public static final String FIELD_1 = "res/sprites/gui/field1.png";
	public static final String FIELD_1_HOVER = "res/sprites/gui/field1_hover.png";
	public static final String FIELD_1_SELECT = "res/sprites/gui/field1_select.png";

	/** The Constant BORDER_SIZE. */
	public static final int BORDER_SIZE = 3;

	/** The field path. */
	private final String fieldPath;
	private final String fieldHoverPath;
	private final String fieldSelectPath;

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

	/** The text color. */
	private Color textColor = GUI.SUBELEMENT_COLOR_DEFAULT;

	/** The focus color. */
	private Color focusColor = GUI.FOCUSED_COLOR_DEFAULT;

	/** The font. */
	private UnicodeFont font;

	/** The is centered. */
	private boolean isCentered = false;

	/** The original x. */
	private int originalX = -1;

	/** The action. */
	private Actionable action;

	/** The img. */
	private Patch3Image img;
	private Patch3Image imgHover;
	private Patch3Image imgSelect;

	/** The maximum size. */
	private int maximumSize = Integer.MAX_VALUE;

	/** The is selected. */
	protected boolean isSelected = false;

	/** The is hovered on. */
	protected boolean isHoveredOn = false;

	/** The is focused. */
	protected boolean isFocused = false;

	/** The is blink visible. */
	protected boolean isBlinkVisible = false;

	/** The blinky position. */
	private int blinkyPosition = 0;

	/** The last blink. */
	private long lastBlink = 0L;

	/** The blink interval. */
	private static int blinkInterval = 500;

	/**
	 * Instantiates a new gUI text field.
	 *
	 * @param x the x
	 * @param y the y
	 * @param width the width
	 * @param color the color
	 * @param font the font
	 */
	public GUITextField(final int x, final int y, final int width, final Color color, final UnicodeFont font) {
		super(x, y, (int)(width * VictusLudusGame.engine.scalingFactor) + GUITextField.BORDER_SIZE + GUITextField.BORDER_SIZE, font.getLineHeight() + GUITextField.BORDER_SIZE + GUITextField.BORDER_SIZE);
		this.textColor = color;
		this.font = font;
		this.originalX = x;

		this.fieldPath = GUITextField.FIELD_1;
		this.setImage(this.fieldPath);

		this.fieldHoverPath = GUITextField.FIELD_1_HOVER;
		this.setHoverImage(this.fieldHoverPath);

		this.fieldSelectPath = GUITextField.FIELD_1_SELECT;
		this.setSelectImage(this.fieldSelectPath);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#render()
	 */
	@Override
	public void render() {
		if(this.isVisible()){
			final Rectangle rect = new Rectangle(this.x + GUITextField.BORDER_SIZE, this.y + GUITextField.BORDER_SIZE, this.width - GUITextField.BORDER_SIZE - GUITextField.BORDER_SIZE, this.height - GUITextField.BORDER_SIZE - GUITextField.BORDER_SIZE);

			//draw image box first
			int imageX;

			if(this.isCentered) {
				imageX = this.originalX - this.img.getWidth()/2;
			} else {
				imageX = this.originalX;
			}

			if(this.isHoveredOn){
				this.imgHover.draw(imageX , this.y);
			} else if (this.isSelected){
				this.imgSelect.draw(imageX , this.y);
			} else {
				this.img.draw(imageX , this.y);
			}

			if(this.isDisabled){
				VictusLudusGame.engine.graphics.setWorldClip(rect);
				this.font.drawString(this.x + GUITextField.BORDER_SIZE, this.y + GUITextField.BORDER_SIZE, this.text, this.textColor);
				VictusLudusGame.engine.graphics.clearWorldClip();
			}
			else if(this.isFocused && this.focusColor != null){
				VictusLudusGame.engine.graphics.setWorldClip(rect);
				this.font.drawString(this.x + GUITextField.BORDER_SIZE, this.y + GUITextField.BORDER_SIZE, this.text, this.textColor);
				VictusLudusGame.engine.graphics.clearWorldClip();

				/* blinky thingy */
				if(this.isBlinkVisible){
					final int xblink = this.font.getWidth(this.text.substring(0,this.blinkyPosition));
					VictusLudusGame.engine.graphics.setColor(this.color);
					VictusLudusGame.engine.graphics.fill(new Rectangle(this.x+xblink+GUITextField.BORDER_SIZE,this.y+GUITextField.BORDER_SIZE,2,this.getHeight() - GUITextField.BORDER_SIZE - GUITextField.BORDER_SIZE));

					if (Time.getTime() - this.lastBlink > GUITextField.blinkInterval) {
						this.isBlinkVisible = false;
						this.lastBlink = Time.getTime();
					}

				} else {
					if (Time.getTime() - this.lastBlink > GUITextField.blinkInterval) {
						this.isBlinkVisible = true;
						this.lastBlink = Time.getTime();
					}
				}
			}
			else if(this.isHoveredOn && this.hoverColor != null){
				VictusLudusGame.engine.graphics.setWorldClip(rect);
				this.font.drawString(this.x + GUITextField.BORDER_SIZE, this.y + GUITextField.BORDER_SIZE, this.text, this.hoverColor);
				VictusLudusGame.engine.graphics.clearWorldClip();
			}else if(this.isSelected && this.selectColor != null){
				VictusLudusGame.engine.graphics.setWorldClip(rect);
				this.font.drawString(this.x + GUITextField.BORDER_SIZE, this.y + GUITextField.BORDER_SIZE, this.text, this.textColor);
				VictusLudusGame.engine.graphics.clearWorldClip();
			}else{
				VictusLudusGame.engine.graphics.setWorldClip(rect);
				this.font.drawString(this.x + GUITextField.BORDER_SIZE, this.y + GUITextField.BORDER_SIZE, this.text, this.textColor);
				VictusLudusGame.engine.graphics.clearWorldClip();
			}

		}
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
	 * Sets the image.
	 *
	 * @param imagePath the new image
	 */
	private void setImage(final String imagePath) {
		final int width = this.getWidth();

		try {
			this.img = new Patch3Image(imagePath, width+GUITextField.BORDER_SIZE+GUITextField.BORDER_SIZE, this.font.getLineHeight()+GUITextField.BORDER_SIZE+GUITextField.BORDER_SIZE);
		} catch (final Exception e) {
			this.img = null;
			e.printStackTrace();
		}
	}

	/**
	 * Sets the hover image.
	 *
	 * @param imagePath the new image
	 */
	private void setHoverImage(final String imagePath) {
		final int width = this.getWidth();

		try {
			this.imgHover = new Patch3Image(imagePath, width+GUITextField.BORDER_SIZE+GUITextField.BORDER_SIZE, this.font.getLineHeight()+GUITextField.BORDER_SIZE+GUITextField.BORDER_SIZE);
		} catch (final Exception e) {
			this.imgHover = null;
			e.printStackTrace();
		}
	}

	/**
	 * Sets the select image.
	 *
	 * @param imagePath the new image
	 */
	private void setSelectImage(final String imagePath) {
		final int width = this.getWidth();

		try {
			this.imgSelect = new Patch3Image(imagePath, width+GUITextField.BORDER_SIZE+GUITextField.BORDER_SIZE, this.font.getLineHeight()+GUITextField.BORDER_SIZE+GUITextField.BORDER_SIZE);
		} catch (final Exception e) {
			this.imgSelect = null;
			e.printStackTrace();
		}
	}

	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(final String text) {
		this.text = text;
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

	/* Probably gets nasty with massive amounts of text, need a better solution.
	 * A binary search could handle a larger amount.
	 * Currently checks for position with a dumb linear search through the string. */
	/**
	 * Calc blinky position.
	 *
	 * @param coord the coord
	 */
	private void calcBlinkyPosition(final ScreenCoord coord) {
		int xadj, slen, i;

		xadj = coord.x - this.x;

		slen = 0;
		i = 0;

		do{
			slen = this.font.getWidth(this.text.substring(0, i));
			//System.err.println("string length: " + slen + " adj:" + xadj + "  index:" + i + "  text: " + this.text.substring(0,i));
		}while(slen < xadj && i++ < this.text.length());

		if(i > 0){
			i--;
			slen = this.font.getWidth(this.text.substring(0, i));
		}

		//System.err.println("string length: " + slen + " adj:" + xadj + "  index:" + i + "  text: " + this.text.substring(0,i));

		this.blinkyPosition = i;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.KeyboardListener#onKeyPress(com.teamderpy.victusludus.gui.eventhandler.event.KeyboardEvent)
	 */
	@Override
	public void onKeyDown(final KeyDownEvent keyboardEvent) {
		if(keyboardEvent.getKey() == Keyboard.KEY_RIGHT){
			if(this.isFocused && this.blinkyPosition < this.text.length()){
				this.blinkyPosition++;
				this.isBlinkVisible = true;
				this.lastBlink = Time.getTime();
			}
		}
		else if(keyboardEvent.getKey() == Keyboard.KEY_LEFT){
			if(this.isFocused && this.blinkyPosition > 0){
				this.blinkyPosition--;
				this.isBlinkVisible = true;
				this.lastBlink = Time.getTime();
			}
		}
		else if(keyboardEvent.getKey() == Keyboard.KEY_DOWN){
			if(this.isFocused){
				VictusLudusGame.engine.eventHandler.focusHandler.signalAllNow(new FocusEvent(this, false));
			}
		}
		else if(keyboardEvent.getKey() == Keyboard.KEY_UP){
			if(this.isFocused){
				VictusLudusGame.engine.eventHandler.focusHandler.signalAllNow(new FocusEvent(this, false));
			}
		}
		else if(keyboardEvent.getKey() == Keyboard.KEY_RETURN){
			if(this.isFocused){
				VictusLudusGame.engine.eventHandler.focusHandler.signalAllNow(new FocusEvent(this, false));
			}
			else if(this.isSelected){
				VictusLudusGame.engine.eventHandler.focusHandler.signalAllNow(new FocusEvent(this, true));
				this.blinkyPosition = this.text.length();
			}
		}
		else if(keyboardEvent.getKey() == Keyboard.KEY_BACK ||
				keyboardEvent.getKey() == Keyboard.KEY_DELETE){
			if(this.isFocused){
				if(this.blinkyPosition > 0){
					GUIElement.SOUND_TYPE.playAsSoundEffect(1.0F, 1.0F, false);
					this.text = this.text.substring(0, this.blinkyPosition-1) + this.text.substring(this.blinkyPosition, this.text.length());
					this.blinkyPosition--;
					this.act();
				}
			}
		}
		else if(keyboardEvent.getKey() == Keyboard.KEY_END){
			if(this.isFocused){
				this.blinkyPosition = this.text.length();
				this.act();
			}
		}
		else if(keyboardEvent.getKey() == Keyboard.KEY_HOME){
			if(this.isFocused){
				this.blinkyPosition = 0;
				this.act();
			}
		}
		else{
			if(this.isFocused){
				if(this.text.length() < this.maximumSize && GUI.isPrintableChar(keyboardEvent.getCharacter())){
					GUIElement.SOUND_TYPE.playAsSoundEffect(1.0F, 1.0F, false);
					this.text = this.text.substring(0, this.blinkyPosition) + keyboardEvent.getCharacter() + this.text.substring(this.blinkyPosition, this.text.length());
					this.blinkyPosition++;
					this.act();
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.FocusListener#onGainFocus(com.teamderpy.victusludus.gui.eventhandler.event.FocusEvent)
	 */
	@Override
	public void onGainFocus(final FocusEvent focusEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.equals(focusEvent.getSource())){
				this.isFocused = true;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.FocusListener#onLoseFocus(com.teamderpy.victusludus.gui.eventhandler.event.FocusEvent)
	 */
	@Override
	public void onLoseFocus(final FocusEvent focusEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.equals(focusEvent.getSource())){
				this.isFocused = false;
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
				VictusLudusGame.engine.eventHandler.tooltipHandler.signalAllNow(new TooltipEvent(this, this.getTooltip()));
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
	 * @see com.teamderpy.victusludus.gui.eventhandler.HoverListener#onEnter(com.teamderpy.victusludus.gui.eventhandler.event.HoverEvent)
	 */
	@Override
	public void onEnter(final HoverEvent hoverEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.equals(hoverEvent.getSource())){
				this.isHoveredOn = true;
				VictusLudusGame.engine.eventHandler.signal(new TooltipEvent(this, this.getTooltip()));
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
					VictusLudusGame.engine.eventHandler.signal(new TooltipEvent(this, this.getTooltip()));
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.MouseListener#onMouseClick(com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent)
	 */
	@Override
	public void onMouseClick(final MouseEvent mouseEvent) {
		if(mouseEvent.getButton() == MouseEvent.BUTTON_1){
			if(mouseEvent.isButtonPressed()){
				if(this.isInside(mouseEvent.getX(),mouseEvent.getY())){
					if(!this.isFocused){
						VictusLudusGame.engine.eventHandler.signal(new FocusEvent(this, true));
					}
					this.calcBlinkyPosition(new ScreenCoord(mouseEvent.getX(), mouseEvent.getY()));
					this.isBlinkVisible = true;
					this.lastBlink = Time.getTime();
				}else{
					if(this.isFocused){
						VictusLudusGame.engine.eventHandler.signal(new FocusEvent(this, false));
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
					VictusLudusGame.engine.eventHandler.signal(new HoverEvent(this, true));
				}
			}
			else{
				if(this.isHoveredOn){
					VictusLudusGame.engine.eventHandler.signal(new HoverEvent(this, false));
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#registerListeners()
	 */
	@Override
	public void registerListeners() {
		VictusLudusGame.engine.eventHandler.mouseHandler.registerPlease(this);
		VictusLudusGame.engine.eventHandler.hoverHandler.registerPlease(this);
		VictusLudusGame.engine.eventHandler.selectHandler.registerPlease(this);
		VictusLudusGame.engine.eventHandler.focusHandler.registerPlease(this);
		VictusLudusGame.engine.eventHandler.keyboardHandler.registerPlease(this);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#unregisterListeners()
	 */
	@Override
	public void unregisterListeners(){
		VictusLudusGame.engine.eventHandler.mouseHandler.unregisterPlease(this);
		VictusLudusGame.engine.eventHandler.hoverHandler.unregisterPlease(this);
		VictusLudusGame.engine.eventHandler.selectHandler.unregisterPlease(this);
		VictusLudusGame.engine.eventHandler.focusHandler.unregisterPlease(this);
		VictusLudusGame.engine.eventHandler.keyboardHandler.unregisterPlease(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize(){
		this.unregisterListeners();
	}

	/**
	 * Sets the typing action.
	 *
	 * @param act the new typing action
	 */
	public void setTypingAction(final com.teamderpy.VictusLudusGame.enginengine.Actionable act){
		this.action = act;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.VictusLudusGame.enginengine.Actionable#act()
	 */
	@Override
	public void act() {
		if(this.action != null) {
			this.action.act();
		}
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

	/**
	 * Gets the focus color.
	 *
	 * @return the focus color
	 */
	public Color getFocusColor() {
		return this.focusColor;
	}

	/**
	 * Sets the focus color.
	 *
	 * @param focusColor the new focus color
	 */
	public void setFocusColor(final Color focusColor) {
		this.focusColor = focusColor;
	}

	/**
	 * Gets the maximum size.
	 *
	 * @return the maximum size
	 */
	public int getMaximumSize() {
		return this.maximumSize;
	}

	/**
	 * Sets the maximum size.
	 *
	 * @param maximumSize the new maximum size
	 */
	public void setMaximumSize(final int maximumSize) {
		this.maximumSize = maximumSize;
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

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#tick()
	 */
	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}
}
