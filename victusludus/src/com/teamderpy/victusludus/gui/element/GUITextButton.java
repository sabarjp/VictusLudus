package com.teamderpy.victusludus.gui.element;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.engine.Actionable;
import com.teamderpy.victusludus.gui.GUI;
import com.teamderpy.victusludus.gui.eventhandler.ButtonPressListener;
import com.teamderpy.victusludus.gui.eventhandler.HoverListener;
import com.teamderpy.victusludus.gui.eventhandler.MouseListener;
import com.teamderpy.victusludus.gui.eventhandler.SelectListener;
import com.teamderpy.victusludus.gui.eventhandler.event.ButtonPressEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.HoverEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.MouseEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.ScrollEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.SelectEvent;
import com.teamderpy.victusludus.gui.eventhandler.event.TooltipEvent;



/**
 * The Class GUITextButton.
 */
public class GUITextButton extends GUIElement implements Actionable, Selectable, MouseListener, HoverListener, ButtonPressListener, SelectListener{
	
	/** The Constant BUTTON_1. */
	public static final String BUTTON_1 = "button";
	
	/** The Constant BORDER_SIZE. */
	public static final int BORDER_SIZE = 3;

	/** The button path. */
	private final String buttonPath;

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
	
	/** The press color. */
	private final Color pressColor = GUI.PRESS_COLOR_DEFAULT;
	
	/** The font. */
	private BitmapFont font;
	
	/** The action. */
	private Actionable action;
	
	/** The img. */
	private Patch3Image img;
	
	/** The custom size. */
	private final int customSize;
	
	/** The is centered. */
	private boolean isCentered = false;
	
	/** The is depressed. */
	private boolean isDepressed = false;

	/** The is selected. */
	protected boolean isSelected = false;
	
	/** The is hovered on. */
	protected boolean isHoveredOn = false;
	
	/** The original x. */
	private int originalX = -1;

	/**
	 * Instantiates a new gUI text button.
	 *
	 * @param x the x
	 * @param y the y
	 * @param text the text
	 * @param color the color
	 * @param font the font
	 */
	public GUITextButton(final int x, final int y, final String text, final Color color, final BitmapFont font) {
		super(x, y, (int)font.getBounds(text).width+GUITextButton.BORDER_SIZE+GUITextButton.BORDER_SIZE, (int)font.getLineHeight()+GUITextButton.BORDER_SIZE+GUITextButton.BORDER_SIZE);
		this.text = text;
		this.color = color;
		this.font = font;
		this.originalX = x;
		this.customSize = 0;

		this.buttonPath = GUITextButton.BUTTON_1;
		this.setImage(this.buttonPath);

		this.registerListeners();
	}

	/**
	 * Instantiates a new gUI text button.
	 *
	 * @param x the x
	 * @param y the y
	 * @param text the text
	 * @param color the color
	 * @param font the font
	 * @param buttonPath the button path
	 */
	public GUITextButton(final int x, final int y, final String text, final Color color, final BitmapFont font, final String buttonPath) {
		super(x, y, (int)font.getBounds(text).width+GUITextButton.BORDER_SIZE+GUITextButton.BORDER_SIZE, (int)font.getLineHeight()+GUITextButton.BORDER_SIZE+GUITextButton.BORDER_SIZE);
		this.text = text;
		this.color = color;
		this.font = font;
		this.originalX = x;
		this.customSize = 0;

		this.buttonPath = buttonPath;
		this.setImage(buttonPath);
	}

	/**
	 * Instantiates a new gUI text button.
	 *
	 * @param x the x
	 * @param y the y
	 * @param text the text
	 * @param color the color
	 * @param font the font
	 * @param characterSize the character size
	 */
	public GUITextButton(final int x, final int y, final String text, final Color color, final BitmapFont font, final int characterSize) {
		super(x, y, (int)font.getBounds(text).width+GUITextButton.BORDER_SIZE+GUITextButton.BORDER_SIZE, (int)font.getLineHeight()+GUITextButton.BORDER_SIZE+GUITextButton.BORDER_SIZE);
		this.text = text;
		this.color = color;
		this.font = font;
		this.originalX = x;
		this.customSize = characterSize;

		this.buttonPath = GUITextButton.BUTTON_1;
		this.setImage(this.buttonPath);
	}

	/**
	 * Instantiates a new gUI text button.
	 *
	 * @param x the x
	 * @param y the y
	 * @param text the text
	 * @param color the color
	 * @param font the font
	 * @param buttonPath the button path
	 * @param characterSize the character size
	 */
	public GUITextButton(final int x, final int y, final String text, final Color color, final BitmapFont font, final String buttonPath, final int characterSize) {
		super(x, y, (int)font.getBounds(text).width+GUITextButton.BORDER_SIZE+GUITextButton.BORDER_SIZE, (int)font.getLineHeight()+GUITextButton.BORDER_SIZE+GUITextButton.BORDER_SIZE);
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
	public void render(SpriteBatch batch, float deltaT) {
		if(this.isVisible()){			
			//draw image box first
			int imageX;

			if(this.isCentered) {
				imageX = this.originalX - this.img.getWidth()/2;
			} else {
				imageX = this.originalX;
			}

			this.img.draw(batch, imageX , this.y);
			
			batch.enableBlending();

			if(this.isDisabled){
				this.font.setColor(this.color);
				this.font.draw(batch, this.text, this.x+GUITextButton.BORDER_SIZE, this.y+GUITextButton.BORDER_SIZE);
			}else if(this.isDepressed && this.pressColor != null){
				this.font.setColor(this.pressColor);
				this.font.draw(batch, this.text, this.x+GUITextButton.BORDER_SIZE, this.y+GUITextButton.BORDER_SIZE);
			}else if(this.isHoveredOn && this.hoverColor != null){
				this.font.setColor(this.hoverColor);
				this.font.draw(batch, this.text, this.x+GUITextButton.BORDER_SIZE, this.y+GUITextButton.BORDER_SIZE);
			}else if(this.isSelected && this.selectColor != null){
				this.font.setColor(this.selectColor);
				this.font.draw(batch, this.text, this.x+GUITextButton.BORDER_SIZE, this.y+GUITextButton.BORDER_SIZE);
			}else{
				this.font.setColor(this.color);
				this.font.draw(batch, this.text, this.x+GUITextButton.BORDER_SIZE, this.y+GUITextButton.BORDER_SIZE);
			}
			
			batch.disableBlending();

			if(VictusLudusGame.engine.IS_DEBUGGING){
				VictusLudusGame.engine.shapeRenderer.setColor(Color.RED);
				VictusLudusGame.engine.shapeRenderer.begin(ShapeType.Rectangle);
				VictusLudusGame.engine.shapeRenderer.rect(this.x, this.y, this.getWidth(), this.getHeight());
				VictusLudusGame.engine.shapeRenderer.end();
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

		if(this.font.getBounds("W").width*this.customSize > this.font.getBounds(this.text).width){
			width = (int)(this.font.getBounds("W").width*this.customSize);
		} else {
			width = (int)this.font.getBounds(this.text).width;
		}

		try {
			this.img = new Patch3Image(imagePath, width+GUITextButton.BORDER_SIZE+GUITextButton.BORDER_SIZE, (int)(this.font.getLineHeight()+GUITextButton.BORDER_SIZE+GUITextButton.BORDER_SIZE));
		} catch (InvalidDimensionsException e) {
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
			super.setX((this.originalX-(int)this.font.getBounds(text).width/2));
		}

		super.setWidth((int)this.font.getBounds(this.text).width);
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
	public BitmapFont getFont() {
		return this.font;
	}

	/**
	 * Sets the font.
	 *
	 * @param font the new font
	 */
	public void setFont(final BitmapFont font) {
		this.font = font;
	}

	/**
	 * Sets the pressed action.
	 *
	 * @param act the new pressed action
	 */
	public void setPressedAction(final Actionable act){
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
	public boolean onMouseClick(final MouseEvent mouseEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(mouseEvent.getButton() == MouseEvent.BUTTON_1){
				if(mouseEvent.isButtonPressed()){
					if(this.isInside(mouseEvent.getX(), mouseEvent.getY())){
						this.isDepressed = true;
						return true;
					}
				}
				else{
					if(this.isInside(mouseEvent.getX(), mouseEvent.getY())){
						if(this.isDepressed){
							VictusLudusGame.engine.eventHandler.signal(new ButtonPressEvent(this, ""));
							this.isDepressed = false;
							return true;
						}
					} else {
						this.isDepressed = false;
					}
				}
			}
		}
		
		return false;
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
	 * @see com.teamderpy.victusludus.gui.eventhandler.SelectListener#onSelect(com.teamderpy.victusludus.gui.eventhandler.event.SelectEvent)
	 */
	@Override
	public boolean onSelect(final SelectEvent selectEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.equals(selectEvent.getSource())){
				this.isSelected = true;
				VictusLudusGame.engine.eventHandler.signal(new TooltipEvent(this, this.getTooltip()));
				return true;
			}
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.SelectListener#onUnselect(com.teamderpy.victusludus.gui.eventhandler.event.SelectEvent)
	 */
	@Override
	public boolean onUnselect(final SelectEvent selectEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.equals(selectEvent.getSource())){
				this.isSelected = false;
				return true;
			}
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.ButtonPressListener#onButtonPress(com.teamderpy.victusludus.gui.eventhandler.event.ButtonPressEvent)
	 */
	@Override
	public boolean onButtonPress(final ButtonPressEvent buttonPressEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.equals(buttonPressEvent.getSource())){
				GUIElement.SOUND_SELECT.play();
				this.act();
				return true;
			}
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.HoverListener#onEnter(com.teamderpy.victusludus.gui.eventhandler.event.HoverEvent)
	 */
	@Override
	public boolean onEnter(final HoverEvent hoverEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.equals(hoverEvent.getSource())){
				this.isHoveredOn = true;
				VictusLudusGame.engine.eventHandler.signal(new TooltipEvent(this, this.getTooltip()));
				return true;
			}
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.eventhandler.HoverListener#onLeave(com.teamderpy.victusludus.gui.eventhandler.event.HoverEvent)
	 */
	@Override
	public boolean onLeave(final HoverEvent hoverEvent) {
		if(!this.isDisabled() && this.isVisible()){
			if(this.equals(hoverEvent.getSource())){
				this.isHoveredOn = false;
				return true;
			}else{
				if(this.isSelected){
					VictusLudusGame.engine.eventHandler.signal(new TooltipEvent(this, this.getTooltip()));
				}
			}
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#registerListeners()
	 */
	@Override
	public void registerListeners() {
		VictusLudusGame.engine.eventHandler.mouseHandler.registerPlease(this);
		VictusLudusGame.engine.eventHandler.hoverHandler.registerPlease(this);
		VictusLudusGame.engine.eventHandler.selectHandler.registerPlease(this);
		VictusLudusGame.engine.eventHandler.buttonPressHandler.registerPlease(this);
	}

	/* (non-Javadoc)
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#unregisterListeners()
	 */
	@Override
	public void unregisterListeners(){
		VictusLudusGame.engine.eventHandler.mouseHandler.unregisterPlease(this);
		VictusLudusGame.engine.eventHandler.hoverHandler.unregisterPlease(this);
		VictusLudusGame.engine.eventHandler.selectHandler.unregisterPlease(this);
		VictusLudusGame.engine.eventHandler.buttonPressHandler.unregisterPlease(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize(){
		this.unregisterListeners();
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
	 * @see com.teamderpy.victusludus.gui.element.GUIElement#tick()
	 */
	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll (ScrollEvent scrollEvent) {
		// TODO Auto-generated method stub
		return false;
	}
}
