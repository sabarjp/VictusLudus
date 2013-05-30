package com.teamderpy.victusludus.gui.element;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.engine.Actionable;
import com.teamderpy.victusludus.gui.GUI;
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


// TODO: Auto-generated Javadoc
/**
 * The Class GUISliderHorizontal.
 */
public class GUISliderHorizontal extends GUIElement implements Actionable, Selectable, MouseListener, KeyboardListener, HoverListener, ButtonPressListener, SelectListener{

	/** The Constant SLIDER_RAIL_1. */
	public static final String SLIDER_RAIL_1 = "res/sprites/gui/sliderRail1.png";

	/** The Constant SLIDER_NUB_1. */
	public static final String SLIDER_NUB_1 = "res/sprites/gui/sliderNub1.png";

	/** The Constant BORDER_SIZE. */
	public static final int BORDER_SIZE = 2;

	/** The slider rail path. */
	private final String sliderRailPath;

	/** The slider nub path. */
	private final String sliderNubPath;

	/** The text. */
	private String text = "";

	/** The optional min text. */
	private String optionalMinText = "";

	/** The optional max text. */
	private String optionalMaxText = "";

	/** The tooltip. */
	private String tooltip = "";

	/** The color. */
	private Color color = GUI.ELEMENT_COLOR_DEFAULT;

	/** The select color. */
	private Color selectColor = GUI.SELECTION_COLOR_DEFAULT;

	/** The hover color. */
	private Color hoverColor = GUI.HOVER_COLOR_DEFAULT;

	/** The font. */
	private UnicodeFont font;

	/** The action. */
	private Actionable action;

	/** The is centered. */
	private boolean isCentered = false;

	/** The is selected. */
	protected boolean isSelected = false;

	/** The is hovered on. */
	protected boolean isHoveredOn = false;

	/** The original x. */
	private int originalX = -1;

	/** The slider rail width. */
	private int sliderRailWidth = 150;

	/** The slider rail height. */
	private int sliderRailHeight = 10;

	/** The slider nub width. */
	private int sliderNubWidth = 6;

	/** The slider nub height. */
	private int sliderNubHeight = 20;

	/** The slider rail img. */
	private SitchedGUIImage sliderRailImg;

	/** The slider nub img. */
	private SitchedGUIImage sliderNubImg;

	/** The min slider value. */
	private float minSliderValue = -1.0F * Float.MAX_VALUE;

	/** The max slider value. */
	private float maxSliderValue = Float.MAX_VALUE;

	/** The current slider value. */
	private float currentSliderValue = 0.0F;

	/** The stepping factor. */
	private float steppingFactor = 20.0F;

	/** The is slider nub held. */
	private boolean isSliderNubHeld = false;

	/** The mini tooltip for the value of the slider. */
	private GUITextWithBox miniTooltip;

	/**
	 * Instantiates a new GUI slider horizontal.
	 *
	 * @param x the x position
	 * @param y the y position
	 * @param text the text to draw
	 * @param color the color to draw the text
	 * @param font the font to use on the string
	 */
	public GUISliderHorizontal(final int x, final int y, final String text, final Color color, final UnicodeFont font) {
		super(x, y, font.getWidth(text), font.getLineHeight()+5);
		this.text = text;
		this.color = color;
		this.font = font;
		this.originalX = x;

		this.sliderRailPath = GUISliderHorizontal.SLIDER_RAIL_1;
		this.setSliderRailImage(this.sliderRailPath);

		this.sliderNubPath = GUISliderHorizontal.SLIDER_NUB_1;
		this.setSliderNubImage(this.sliderNubPath);

		this.miniTooltip = new GUITextWithBox(0, 0, "",
				GUI.TOOLTIP_TEXT_COLOR_DEFAULT, GUI.fetchFontS(GUI.TOOLT_FONT_ID));
		this.miniTooltip.setCentered(false);

		super.setHeight(font.getLineHeight()+this.sliderNubImg.getHeight()+5);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#render()
	 */
	/**
	 * Render.
	 */
	@Override
	public void render() {
		if(this.isVisible){
			Color mc;

			int imageX;
			int fontX;

			if(this.isDisabled){
				mc = this.color;
			}else if(this.isHoveredOn && this.hoverColor != null){
				mc = this.hoverColor;
			}else if(this.isSelected && this.selectColor != null){
				mc = this.selectColor;
			}else{
				mc = this.color;
			}

			if(this.isCentered){
				imageX = this.originalX - this.sliderRailImg.getWidth()/2;
				fontX = this.x;
			}
			else{
				imageX = this.x;
				fontX = this.x;
			}

			//main text
			this.font.drawString(fontX, this.y, this.text, mc);

			//render slider rail
			int sliderYOffset = this.y + this.font.getLineHeight() + 5;

			this.sliderRailImg.draw(imageX , sliderYOffset + this.sliderNubImg.getHeight()/4);

			//optional value text
			if(!this.optionalMinText.equals("")){
				this.font.drawString(imageX - this.font.getWidth(this.optionalMinText) - 10, sliderYOffset - this.sliderNubImg.getHeight()/8, this.optionalMinText, mc);
			}

			if(!this.optionalMaxText.equals("")){
				this.font.drawString(imageX + this.sliderRailImg.getWidth() + 10, sliderYOffset  - this.sliderNubImg.getHeight()/8, this.optionalMaxText, mc);
			}

			//render slider nub
			int sliderOffsetX = (int) (this.sliderRailImg.getWidth() * ((this.currentSliderValue-this.minSliderValue)/this.getSliderRange()));
			this.sliderNubImg.draw(imageX + sliderOffsetX - this.sliderNubImg.getWidth()/2, this.y + this.font.getLineHeight() + 5);

			this.miniTooltip.render();
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
	/**
	 * Gets the tooltip.
	 *
	 * @return the tooltip
	 */
	@Override
	public String getTooltip() {
		return this.tooltip;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.Selectable#setTooltip(java.lang.String)
	 */
	/**
	 * Sets the tooltip.
	 *
	 * @param tooltip the new tooltip
	 */
	@Override
	public void setTooltip(final String tooltip) {
		this.tooltip = tooltip;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.Selectable#isSelected()
	 */
	/**
	 * Checks if is selected.
	 *
	 * @return true, if is selected
	 */
	@Override
	public boolean isSelected() {
		return this.isSelected;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.Selectable#setSelected(boolean)
	 */
	/**
	 * Sets the selected.
	 *
	 * @param isSelected the new selected
	 */
	@Override
	public void setSelected(final boolean isSelected) {
		this.isSelected = isSelected;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.Selectable#isHoveredOn()
	 */
	/**
	 * Checks if is hovered on.
	 *
	 * @return true, if is hovered on
	 */
	@Override
	public boolean isHoveredOn() {
		return this.isHoveredOn;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.Selectable#setHoveredOn(boolean)
	 */
	/**
	 * Sets the hovered on.
	 *
	 * @param isHoveredOn the new hovered on
	 */
	@Override
	public void setHoveredOn(final boolean isHoveredOn) {
		this.isHoveredOn = isHoveredOn;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.SelectListener#onSelect(com.teamderpy.victusludus.gui.eventhandler.event.SelectEvent)
	 */
	/**
	 * On select.
	 *
	 * @param selectEvent the select event
	 */
	@Override
	public void onSelect(final SelectEvent selectEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.equals(selectEvent.getSource())){
				this.isSelected = true;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.SelectListener#onUnselect(com.teamderpy.victusludus.gui.eventhandler.event.SelectEvent)
	 */
	/**
	 * On unselect.
	 *
	 * @param selectEvent the select event
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
	/**
	 * On button press.
	 *
	 * @param buttonPressEvent the button press event
	 */
	@Override
	public void onButtonPress(final ButtonPressEvent buttonPressEvent) {
		//do nothing
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.HoverListener#onEnter(com.teamderpy.victusludus.gui.eventhandler.event.HoverEvent)
	 */
	/**
	 * On enter.
	 *
	 * @param hoverEvent the hover event
	 */
	@Override
	public void onEnter(final HoverEvent hoverEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.equals(hoverEvent.getSource())){
				this.isHoveredOn = true;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.HoverListener#onLeave(com.teamderpy.victusludus.gui.eventhandler.event.HoverEvent)
	 */
	/**
	 * On leave.
	 *
	 * @param hoverEvent the hover event
	 */
	@Override
	public void onLeave(final HoverEvent hoverEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.equals(hoverEvent.getSource())){
				this.isHoveredOn = false;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.KeyboardListener#onKeyPress(com.teamderpy.victusludus.gui.eventhandler.event.KeyboardEvent)
	 */
	/**
	 * On key press.
	 *
	 * @param keyboardEvent the keyboard event
	 */
	@Override
	public void onKeyPress(final KeyboardEvent keyboardEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.isSelected){
				if(keyboardEvent.getKey() == Keyboard.KEY_RIGHT){
					if(this.currentSliderValue < this.maxSliderValue - this.getSliderRange() / this.steppingFactor){
						this.currentSliderValue += this.getSliderRange() / this.steppingFactor;
					} else {
						this.currentSliderValue = this.maxSliderValue;
					}
				}
				else if(keyboardEvent.getKey() == Keyboard.KEY_LEFT){
					if(this.currentSliderValue > this.minSliderValue + this.getSliderRange() / this.steppingFactor){
						this.currentSliderValue -= this.getSliderRange() / this.steppingFactor;
					} else {
						this.currentSliderValue = this.minSliderValue;
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.MouseListener#onMouseClick(com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent)
	 */
	/**
	 * On mouse click.
	 *
	 * @param mouseEvent the mouse event
	 */
	@Override
	public void onMouseClick(final MouseEvent mouseEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.isInsideSliderNub(mouseEvent.getX(), mouseEvent.getY())){
				if(mouseEvent.isButtonPressed()){
					this.isSliderNubHeld = true;
				}
			}

			if(!mouseEvent.isButtonPressed()){
				this.isSliderNubHeld = false;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.MouseListener#onMouseMove(com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent)
	 */
	/**
	 * On mouse move.
	 *
	 * @param mouseEvent the mouse event
	 */
	@Override
	public void onMouseMove(final MouseEvent mouseEvent) {
		if(!this.isDisabled() && this.isVisible()){
			//determine hover event
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

			if(this.isSliderNubHeld || this.isInsideSliderNub(mouseEvent.getX(), mouseEvent.getY())){
				this.miniTooltip.setText("" + String.format("%.2f", this.getCurrentSliderValue()));
				this.miniTooltip.setX(Mouse.getX() + 32);
				this.miniTooltip.setY(VictusLudus.e.Y_RESOLUTION() - Mouse.getY() + 5);
			} else {
				this.miniTooltip.setText("");
			}

			//move the slider nub
			if(this.isSliderNubHeld){
				this.moveSliderNub(mouseEvent.getX());
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#registerListeners()
	 */
	/**
	 * Register listeners.
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
	/**
	 * Unregister listeners.
	 */
	@Override
	public void unregisterListeners(){
		VictusLudus.e.eventHandler.mouseHandler.unregisterPlease(this);
		VictusLudus.e.eventHandler.hoverHandler.unregisterPlease(this);
		VictusLudus.e.eventHandler.selectHandler.unregisterPlease(this);
		VictusLudus.e.eventHandler.buttonPressHandler.unregisterPlease(this);
		VictusLudus.e.eventHandler.keyboardHandler.unregisterPlease(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	/**
	 * Finalize.
	 */
	@Override
	protected void finalize(){
		this.unregisterListeners();
	}

	/**
	 * Move slider nub to the position indicated by the screen x position (usually mouse).
	 *
	 * @param x the x value to move to
	 */
	public void moveSliderNub(int x){
		int minSliderOffsetX;

		if(this.isCentered){
			minSliderOffsetX = this.originalX - this.sliderRailImg.getWidth()/2;
		}
		else{
			minSliderOffsetX = this.originalX;
		}

		int maxSliderOffsetX = minSliderOffsetX + this.sliderRailImg.getWidth();

		//force inside the range
		if (x < minSliderOffsetX){
			x = minSliderOffsetX;
		} else if (x > maxSliderOffsetX){
			x = maxSliderOffsetX;
		}

		//interpolate
		float valueToSet = (float) (x - minSliderOffsetX) / (maxSliderOffsetX - minSliderOffsetX) * this.getSliderRange() + this.getMinSliderValue();
		this.setCurrentSliderValue(valueToSet);
	}

	/**
	 * Checks if a point is inside slider nub.
	 *
	 * @param x the x coord to check
	 * @param y the y coord to check
	 * @return true, if the point is inside slider nub
	 */
	public boolean isInsideSliderNub(final int x, final int y){
		int imageX;

		if(this.isCentered){
			imageX = this.originalX - this.sliderRailImg.getWidth()/2;
		}
		else{
			imageX = this.originalX;
		}

		int sliderOffsetX = imageX + (int) (this.sliderRailImg.getWidth() * ((this.currentSliderValue-this.minSliderValue)/this.getSliderRange())) - this.sliderNubImg.getWidth()/2;
		int sliderOffsetY = this.y + this.font.getLineHeight() + 5;

		if(x >= sliderOffsetX && x <= sliderOffsetX + this.sliderNubImg.getWidth() ) {
			if(y >= sliderOffsetY && y <= sliderOffsetY + this.sliderNubImg.getHeight()){
				return true;
			}
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#isInside(int, int)
	 */
	/**
	 * Checks if is inside.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the boolean
	 */
	@Override
	public Boolean isInside(final int x, final int y){
		if(x >= this.x && x <= this.x + this.getWidth()){
			if(y >= this.y && y <= this.y + this.getHeight()){
				return true;
			}
		}

		return false;
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

		if(isCentered) {
			this.x = this.originalX - this.getWidth()/2;
		} else {
			this.x = this.originalX;
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#setDisabled(boolean)
	 */
	/**
	 * Sets the disabled.
	 *
	 * @param isDisabled the new disabled
	 */
	@Override
	public void setDisabled(final boolean isDisabled){
		this.isDisabled = isDisabled;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#setVisible(boolean)
	 */
	/**
	 * Sets the visible.
	 *
	 * @param isVisible the new visible
	 */
	@Override
	public void setVisible(final boolean isVisible){
		this.isDisabled = isVisible;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.engine.Actionable#act()
	 */
	/**
	 * Act.
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
	/**
	 * Tick.
	 */
	@Override
	public void tick() {
		// TODO Auto-generated method stub

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
	 * Gets the slider rail width.
	 *
	 * @return the slider rail width
	 */
	public int getSliderRailWidth() {
		return this.sliderRailWidth;
	}

	/**
	 * Sets the slider rail width.
	 *
	 * @param sliderRailWidth the new slider rail width
	 */
	public void setSliderRailWidth(final int sliderRailWidth) {
		this.sliderRailWidth = sliderRailWidth;
	}

	/**
	 * Gets the slider nub width.
	 *
	 * @return the slider nub width
	 */
	public int getSliderNubWidth() {
		return this.sliderNubWidth;
	}

	/**
	 * Sets the slider nub width.
	 *
	 * @param sliderNubWidth the new slider nub width
	 */
	public void setSliderNubWidth(final int sliderNubWidth) {
		this.sliderNubWidth = sliderNubWidth;
	}

	/**
	 * Gets the slider rail height.
	 *
	 * @return the slider rail height
	 */
	public int getSliderRailHeight() {
		return this.sliderRailHeight;
	}

	/**
	 * Sets the slider rail height.
	 *
	 * @param sliderRailHeight the new slider rail height
	 */
	public void setSliderRailHeight(final int sliderRailHeight) {
		this.sliderRailHeight = sliderRailHeight;
	}

	/**
	 * Gets the slider nub height.
	 *
	 * @return the slider nub height
	 */
	public int getSliderNubHeight() {
		return this.sliderNubHeight;
	}

	/**
	 * Sets the slider nub height.
	 *
	 * @param sliderNubHeight the new slider nub height
	 */
	public void setSliderNubHeight(final int sliderNubHeight) {
		this.sliderNubHeight = sliderNubHeight;
	}

	/**
	 * Sets the slider rail image.
	 *
	 * @param imagePath the new slider rail image
	 */
	private void setSliderRailImage(final String imagePath) {
		final int width = this.getSliderRailWidthS();
		final int height = this.getSliderRailHeightS();

		try {
			this.sliderRailImg = new SitchedGUIImage(imagePath, width+GUISliderHorizontal.BORDER_SIZE+GUISliderHorizontal.BORDER_SIZE, height+GUISliderHorizontal.BORDER_SIZE+GUISliderHorizontal.BORDER_SIZE, GUISliderHorizontal.BORDER_SIZE);
		} catch (final Exception e) {
			this.sliderRailImg = null;
			e.printStackTrace();
		}
	}

	/**
	 * Sets the slider nub image.
	 *
	 * @param imagePath the new slider nub image
	 */
	private void setSliderNubImage(final String imagePath) {
		final int width = this.getSliderNubWidthS();
		final int height = this.getSliderNubHeightS();

		try {
			this.sliderNubImg = new SitchedGUIImage(imagePath, width+GUISliderHorizontal.BORDER_SIZE+GUISliderHorizontal.BORDER_SIZE, height+GUISliderHorizontal.BORDER_SIZE+GUISliderHorizontal.BORDER_SIZE, GUISliderHorizontal.BORDER_SIZE);
		} catch (final Exception e) {
			this.sliderNubImg = null;
			e.printStackTrace();
		}
	}

	/**
	 * Gets the min slider value.
	 *
	 * @return the min slider value
	 */
	public float getMinSliderValue() {
		return this.minSliderValue;
	}

	/**
	 * Sets the min slider value.
	 *
	 * @param minSliderValue the new min slider value
	 */
	public void setMinSliderValue(final float minSliderValue) {
		if(minSliderValue <= this.maxSliderValue){
			this.minSliderValue = minSliderValue;
		}
	}

	/**
	 * Gets the max slider value.
	 *
	 * @return the max slider value
	 */
	public float getMaxSliderValue() {
		return this.maxSliderValue;
	}

	/**
	 * Sets the max slider value.
	 *
	 * @param maxSliderValue the new max slider value
	 */
	public void setMaxSliderValue(final float maxSliderValue) {
		if(maxSliderValue >= this.minSliderValue){
			this.maxSliderValue = maxSliderValue;
		}
	}

	/**
	 * Gets the current slider value.
	 *
	 * @return the current slider value
	 */
	public float getCurrentSliderValue() {
		return this.currentSliderValue;
	}

	/**
	 * Sets the current slider value.
	 * This will be restricted to the stepping factor!
	 *
	 * @param currentSliderValue the new current slider value
	 */
	public void setCurrentSliderValue(final float currentSliderValue) {
		if(currentSliderValue >= this.minSliderValue && currentSliderValue <= this.maxSliderValue){
			this.currentSliderValue = this.getSliderRange() / this.getSteppingFactor() * (int) (currentSliderValue / (this.getSliderRange() / this.getSteppingFactor()));
		}
	}

	/**
	 * Gets the slider value range.
	 *
	 * @return the slider range
	 */
	public float getSliderRange(){
		return this.maxSliderValue - this.minSliderValue;
	}

	/**
	 * Gets the stepping factor.
	 *
	 * @return the stepping factor
	 */
	public float getSteppingFactor() {
		return this.steppingFactor;
	}

	/**
	 * Sets the stepping factor.
	 *
	 * @param steppingFactor the new stepping factor
	 */
	public void setSteppingFactor(final float steppingFactor) {
		this.steppingFactor = steppingFactor;
	}

	/**
	 * Gets the slider rail width scaled.
	 *
	 * @return the slider rail width scaled
	 */
	public int getSliderRailWidthS() {
		return (int) (this.sliderRailWidth * VictusLudus.e.scalingFactor);
	}

	/**
	 * Gets the slider nub width scaled.
	 *
	 * @return the slider nub width scaled
	 */
	public int getSliderNubWidthS() {
		return (int) (this.sliderNubWidth * VictusLudus.e.scalingFactor);
	}

	/**
	 * Gets the slider rail height scaled.
	 *
	 * @return the slider rail height scaled
	 */
	public int getSliderRailHeightS() {
		return (int) (this.sliderRailHeight * VictusLudus.e.scalingFactor);
	}

	/**
	 * Gets the slider nub height scaled.
	 *
	 * @return the slider nub height scaled
	 */
	public int getSliderNubHeightS() {
		return (int) (this.sliderNubHeight * VictusLudus.e.scalingFactor);
	}

	/**
	 * Gets the optional min value text that is shown left of the slider
	 *
	 * @return the optional min text
	 */
	public String getOptionalMinText() {
		return this.optionalMinText;
	}

	/**
	 * Sets the optional min value text that is shown left of the slider
	 *
	 * @param optionalMinText the new optional min text
	 */
	public void setOptionalMinText(final String optionalMinText) {
		this.optionalMinText = optionalMinText;
	}

	/**
	 * Gets the optional max value text that is shown right of the slider
	 *
	 * @return the optional max text
	 */
	public String getOptionalMaxText() {
		return this.optionalMaxText;
	}

	/**
	 * Sets the optional max value text that is shown right of the slider
	 *
	 * @param optionalMaxText the new optional max text
	 */
	public void setOptionalMaxText(final String optionalMaxText) {
		this.optionalMaxText = optionalMaxText;
	}

}
