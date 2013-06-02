package com.teamderpy.victusludus.gui.element;


import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;

import com.teamderpy.victusludus.gui.GUI;



/**
 * The Class GUIText.
 */
public class GUIText extends GUIElement{
	
	/** The text. */
	private String text = "";
	
	/** The color. */
	private Color color = GUI.ELEMENT_COLOR_DEFAULT;
	
	/** The font. */
	private UnicodeFont font;
	
	/** The is centered. */
	private boolean isCentered = false;
	
	/** The is wrapped. */
	private boolean isWrapped = false;

	/** Whether or not to render the text color inverted against the background */
	private boolean isRenderInverted = false;
	
	/** The width wrap. */
	private int widthWrap = 0;

	/** The original x. */
	private int originalX = -1;
	
	/**
	 * Instantiates a new gUI text.
	 *
	 * @param x the x
	 * @param y the y
	 * @param text the text
	 * @param color the color
	 * @param font the font
	 */
	public GUIText(int x, int y, String text, Color color, UnicodeFont font) {
		super(x, y, font.getWidth(text), font.getLineHeight());
		this.text = text;
		this.color = color;
		this.font = font;
		this.originalX = x;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#render()
	 */
	public void render() {
		if(isVisible()){
			if(this.isRenderInverted){
				//invert
				GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
			}
			
			if(!isWrapped){
				font.drawString(x, y, text, color);
			} else {
				String remainingText = text;
				int ymod = 0;
				while(!remainingText.isEmpty()){
					int charPos = findCharacterAtWidth(remainingText, this.widthWrap, true);
					String cutText = remainingText.substring(0, charPos);
					font.drawString(x+((this.widthWrap-font.getWidth(cutText)))/2, y + (ymod * font.getLineHeight()), cutText, color);
					remainingText = remainingText.substring(charPos, remainingText.length());
					ymod++;
				}
			}
			
			if(this.isRenderInverted){
				//normal
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			}
		}
	}
	
	/**
	 * Find character at width.
	 *
	 * @param text the text
	 * @param width the width
	 * @param breakAtWordBoundary the break at word boundary
	 * @return the int
	 */
	private int findCharacterAtWidth(String text, int width, boolean breakAtWordBoundary) {
		int slen, i;
		
		slen = 0;
		i = 0;
		
		//increment character position until we bypass length
		do{
			slen = font.getWidth(text.substring(0, i));		
		}while(slen < width && i++ < text.length());
		
		//set position 1 less
		if(i > 0){
			i--;
			slen = font.getWidth(text.substring(0, i));
		}
		
		//get word boundary if we need to
		if(breakAtWordBoundary && i < text.length()){
			int j = i;
			
			while(text.charAt(j) != ' ' && text.charAt(j) != '-' && j > 0)
				j--;
			
			if(j > 0)
				i = j;
		}

		return i;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(String text) {
		this.text = text;
		
		setCentered(this.isCentered);
		
		super.setWidth(font.getWidth(this.text));
	}

	/**
	 * Checks if is wrapped.
	 *
	 * @return true, if is wrapped
	 */
	@SuppressWarnings("unused")
	private boolean isWrapped() {
		return isWrapped;
	}

	/**
	 * Sets the wrapped.
	 *
	 * @param isWrapped the new wrapped
	 */
	private void setWrapped(boolean isWrapped) {
		this.isWrapped = isWrapped;
	}

	/**
	 * Gets the width wrap.
	 *
	 * @return the width wrap
	 */
	public int getWidthWrap() {
		return widthWrap;
	}

	/**
	 * Sets the width wrap.
	 *
	 * @param widthWrap the new width wrap
	 */
	public void setWidthWrap(int widthWrap) {
		this.widthWrap = widthWrap;
		if(widthWrap > 0){
			this.setWrapped(true);
		}else{
			this.setWrapped(false);
		}
		setText(this.text);
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color.
	 *
	 * @param color the new color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Gets the font.
	 *
	 * @return the font
	 */
	public UnicodeFont getFont() {
		return font;
	}

	/**
	 * Sets the font.
	 *
	 * @param font the new font
	 */
	public void setFont(UnicodeFont font) {
		this.font = font;
		
		GUI.InitializeUnicodeFont(font);
	}
	
	/**
	 * Checks if is centered.
	 *
	 * @return true, if is centered
	 */
	public boolean isCentered() {
		return isCentered;
	}

	/**
	 * Sets the centered.
	 *
	 * @param isCentered the new centered
	 */
	public void setCentered(boolean isCentered) {
		this.isCentered = isCentered;
		
		if(isCentered){
			if(!isWrapped){
				super.setX(originalX-(font.getWidth(text)/2));
			}else{
				super.setX(originalX-(widthWrap/2));
			}
		} else {
			super.setX(originalX);
		}
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#setX(int)
	 */
	@Override
	public void setX(int x) {
		this.originalX = x;
		this.x = x;
		
		setCentered(this.isCentered);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#tick()
	 */
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#unregisterListeners()
	 */
	@Override
	public void unregisterListeners() {
		//no listeners
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#registerListeners()
	 */
	@Override
	public void registerListeners() {
		//no listeners
	}

	public boolean isRenderInverted() {
		return isRenderInverted;
	}

	public void setRenderInverted(boolean isRenderInverted) {
		this.isRenderInverted = isRenderInverted;
	}
}
