package com.teamderpy.victusludus.gui.element;

import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;

import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.gui.GUI;



/**
 * The Class GUITextWithBox.
 */
public class GUITextWithBox extends GUIElement{
	
	/** The Constant TOOLTIP_1. */
	public static final String TOOLTIP_1 = "res/sprites/gui/tooltip1.png";
	
	/** The Constant BORDER_SIZE. */
	public static final int BORDER_SIZE = 3;

	/** The button path. */
	private final String buttonPath;

	/** The text. */
	private String text = "";
	
	/** The color. */
	private Color color = GUI.ELEMENT_COLOR_DEFAULT;
	
	/** The font. */
	private UnicodeFont font;
	
	/** The img. */
	private Patch3Image img;
	
	/** The custom size. */
	private final int customSize;
	
	/** The is centered. */
	private boolean isCentered = false;

	/** The is selected. */
	protected boolean isSelected = false;
	
	/** The is hovered on. */
	protected boolean isHoveredOn = false;
	
	/** The original x. */
	private int originalX = -1;

	/**
	 * Instantiates a new gUI text with box.
	 *
	 * @param x the x
	 * @param y the y
	 * @param text the text
	 * @param color the color
	 * @param font the font
	 */
	public GUITextWithBox(final int x, final int y, final String text, final Color color, final UnicodeFont font) {
		super(x, y, font.getWidth(text)+GUITextWithBox.BORDER_SIZE+GUITextWithBox.BORDER_SIZE, font.getLineHeight()+GUITextWithBox.BORDER_SIZE+GUITextWithBox.BORDER_SIZE);
		this.text = text;
		this.color = color;
		this.font = font;
		this.originalX = x;
		this.customSize = 0;

		this.buttonPath = GUITextWithBox.TOOLTIP_1;
		this.setImage(this.buttonPath);

		this.registerListeners();
	}

	/**
	 * Instantiates a new gUI text with box.
	 *
	 * @param x the x
	 * @param y the y
	 * @param text the text
	 * @param color the color
	 * @param font the font
	 * @param buttonPath the button path
	 */
	public GUITextWithBox(final int x, final int y, final String text, final Color color, final UnicodeFont font, final String buttonPath) {
		super(x, y, font.getWidth(text)+GUITextWithBox.BORDER_SIZE+GUITextWithBox.BORDER_SIZE, font.getLineHeight()+GUITextWithBox.BORDER_SIZE+GUITextWithBox.BORDER_SIZE);
		this.text = text;
		this.color = color;
		this.font = font;
		this.originalX = x;
		this.customSize = 0;

		this.buttonPath = buttonPath;
		this.setImage(buttonPath);
	}

	/**
	 * Instantiates a new gUI text with box.
	 *
	 * @param x the x
	 * @param y the y
	 * @param text the text
	 * @param color the color
	 * @param font the font
	 * @param characterSize the character size
	 */
	public GUITextWithBox(final int x, final int y, final String text, final Color color, final UnicodeFont font, final int characterSize) {
		super(x, y, font.getWidth(text)+GUITextWithBox.BORDER_SIZE+GUITextWithBox.BORDER_SIZE, font.getLineHeight()+GUITextWithBox.BORDER_SIZE+GUITextWithBox.BORDER_SIZE);
		this.text = text;
		this.color = color;
		this.font = font;
		this.originalX = x;
		this.customSize = characterSize;

		this.buttonPath = GUITextWithBox.TOOLTIP_1;
		this.setImage(this.buttonPath);
	}

	/**
	 * Instantiates a new gUI text with box.
	 *
	 * @param x the x
	 * @param y the y
	 * @param text the text
	 * @param color the color
	 * @param font the font
	 * @param buttonPath the button path
	 * @param characterSize the character size
	 */
	public GUITextWithBox(final int x, final int y, final String text, final Color color, final UnicodeFont font, final String buttonPath, final int characterSize) {
		super(x, y, font.getWidth(text)+GUITextWithBox.BORDER_SIZE+GUITextWithBox.BORDER_SIZE, font.getLineHeight()+GUITextWithBox.BORDER_SIZE+GUITextWithBox.BORDER_SIZE);
		this.text = text;
		this.color = color;
		this.font = font;
		this.originalX = x;
		this.customSize = characterSize;

		this.buttonPath = buttonPath;
		this.setImage(buttonPath);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#render()
	 */
	@Override
	public void render() {
		if(this.text.isEmpty()){
			return;
		}

		if(this.isVisible()){
			//draw image box first
			int imageX;

			if(this.isCentered) {
				imageX = this.originalX - this.img.getWidth()/2;
			} else {
				imageX = this.originalX;
			}

			this.img.draw(imageX , this.y);

			if(this.isDisabled){
				this.font.drawString(this.x+GUITextWithBox.BORDER_SIZE, this.y+GUITextWithBox.BORDER_SIZE, this.text, this.color);
			}else{
				this.font.drawString(this.x+GUITextWithBox.BORDER_SIZE, this.y+GUITextWithBox.BORDER_SIZE, this.text, this.color);
			}

			if(VictusLudusGame.engine.IS_DEBUGGING){
				VictusLudusGame.engine.graphics.setColor(Color.red);
				VictusLudusGame.engine.graphics.draw(new org.newdawn.slick.geom.Rectangle(this.x, this.y, this.getWidth(), this.getHeight()));
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
		int width;

		if(this.font.getWidth("W")*this.customSize > this.font.getWidth(this.text)){
			width = this.font.getWidth("W")*this.customSize;
		} else {
			width = this.font.getWidth(this.text);
		}

		try {
			this.img = new Patch3Image(imagePath, width+GUITextWithBox.BORDER_SIZE+GUITextWithBox.BORDER_SIZE, this.font.getLineHeight()+GUITextWithBox.BORDER_SIZE+GUITextWithBox.BORDER_SIZE);
		} catch (final Exception e) {
			this.img = null;
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

		this.setImage(this.buttonPath);

		if(this.isCentered){
			super.setX(this.originalX-this.font.getWidth(text)/2);
		}

		super.setWidth(this.font.getWidth(this.text));
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
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#setX(int)
	 */
	@Override
	public void setX(final int x) {
		this.originalX = x;
		this.x = x;

		this.setCentered(this.isCentered);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#registerListeners()
	 */
	@Override
	public void registerListeners() {

	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#unregisterListeners()
	 */
	@Override
	public void unregisterListeners(){

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize(){
		this.unregisterListeners();
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#tick()
	 */
	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}
}
