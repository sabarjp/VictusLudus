package com.teamderpy.victusludus.gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;

import com.teamderpy.victusludus.data.VictusLudus;
import com.teamderpy.victusludus.data.resources.FontFile;
import com.teamderpy.victusludus.game.renderer.BitmapHandler;
import com.teamderpy.victusludus.gui.element.GUIElement;


// TODO: Auto-generated Javadoc
/**
 * The Class GUI.
 */
public abstract class GUI{
	
	/** The element color default. */
	public static Color ELEMENT_COLOR_DEFAULT = new Color(46,46,46);
	
	/** The selection color default. */
	public static Color SELECTION_COLOR_DEFAULT = new Color(67,55,255);
	
	/** The hover color default. */
	public static Color HOVER_COLOR_DEFAULT = new Color(255,0,0);
	
	/** The press color default. */
	public static Color PRESS_COLOR_DEFAULT = new Color(0,255,0);
	
	/** The subelement color default. */
	public static Color SUBELEMENT_COLOR_DEFAULT = new Color(111,111,111);
	
	/** The subselection color default. */
	public static Color SUBSELECTION_COLOR_DEFAULT = new Color(37,35,155);
	
	/** The subhover color default. */
	public static Color SUBHOVER_COLOR_DEFAULT = new Color(155,0,0);
	
	/** The focused color default. */
	public static Color FOCUSED_COLOR_DEFAULT = new Color(255,255,0);
	
	/** The tooltip text color default. */
	public static Color TOOLTIP_TEXT_COLOR_DEFAULT = new Color(122,122,122);

	/** The pmono font id. */
	public static String PMONO_FONT_ID = "EpilepsySansMono";
	
	/** The title font id. */
	public static String TITLE_FONT_ID = "EpilepsySansMono";
	
	/** The toolt font id. */
	public static String TOOLT_FONT_ID = "EpilepsySansMono";

	/** The Constant GUI_SHEET_PATH. */
	public static final String  GUI_SHEET_PATH = "res/sprites/gui/buttons.png";
	
	/** The Constant ID_BUILD_BTN. */
	public static final byte ID_BUILD_BTN   = 0x0;
	
	/** The Constant ID_QUERY_BTN. */
	public static final byte ID_QUERY_BTN   = 0x4;
	
	/** The Constant ID_PEOPLE_BTN. */
	public static final byte ID_PEOPLE_BTN  = 0x8;
	
	/** The Constant ID_QUIT_BTN. */
	public static final byte ID_QUIT_BTN    = 0xC;
	
	/** The Constant ID_BLANK_BTN. */
	public static final byte ID_BLANK_BTN   = 0x10;

	/** The gui button sheet. */
	public static SpriteSheet guiButtonSheet = null;

	/** The background color. */
	protected Color backgroundColor = new Color(255,255,255);

	/** The element list. */
	protected ArrayList<GUIElement> elementList = null;
	
	/** The menu list. */
	protected ArrayList<GUIElement> menuList = null;
	
	/** The current element. */
	protected int currentElement = -1;

	/** The next element y pos. */
	protected int nextElementYPos = 0;
	
	/** The element spacing. */
	protected int elementSpacing = 0;

	/** The is listening. */
	protected boolean isListening = false;
	
	/** The is disabled. */
	protected boolean isDisabled = false;

	/** The x. */
	protected int x = -1;
	
	/** The y. */
	protected int y = -1;
	
	/** The width. */
	protected int width = -1;
	
	/** The height. */
	protected int height = -1;

	/**
	 * Instantiates a new gui.
	 */
	protected GUI(){
		this.x = 0;
		this.y = 0;

		this.width = VictusLudus.e.X_RESOLUTION();
		this.height = VictusLudus.e.Y_RESOLUTION();

		this.elementList = new ArrayList<GUIElement>();

		this.create();
		this.registerListeners();
		this.positionElements();
	}

	/**
	 * Recreate.
	 */
	protected void recreate(){
		this.unregisterListeners();

		this.x = 0;
		this.y = 0;

		this.width = VictusLudus.e.X_RESOLUTION();
		this.height = VictusLudus.e.Y_RESOLUTION();

		this.elementList = new ArrayList<GUIElement>();

		this.create();
		this.registerListeners();
		this.positionElements();
	}

	/**
	 * Render.
	 */
	public void render() {
		if(this.backgroundColor != null){
			VictusLudus.e.graphics.setColor(this.backgroundColor);
			VictusLudus.e.graphics.fill(new Rectangle(this.x, this.y, this.width, this.height));
		}

		for(GUIElement i:this.elementList){
			i.render();
		}
	}

	/**
	 * Tick.
	 */
	public void tick(){
		for(GUIElement i:this.elementList){
			i.tick();
		}
	}

	/**
	 * Unregister listeners.
	 */
	public abstract void unregisterListeners();

	/**
	 * Register listeners.
	 */
	public abstract void registerListeners();

	/**
	 * Adds the element item.
	 *
	 * @param elem the elem
	 */
	public void addElementItem(final GUIElement elem){
		this.elementList.add(elem);
	}

	/**
	 * Adds the menu item.
	 *
	 * @param elem the elem
	 */
	public void addMenuItem(final GUIElement elem){
		this.menuList.add(elem);
	}

	/**
	 * Next element pos increment.
	 *
	 * @param elem the elem
	 */
	protected void nextElementPosIncrement(final GUIElement elem){
		this.nextElementYPos = this.nextElementYPos + elem.getHeight() + this.elementSpacing;
	}

	/**
	 * Next element pos increment.
	 *
	 * @param height the height
	 */
	protected void nextElementPosIncrement(final int height){
		this.nextElementYPos = this.nextElementYPos + height + this.elementSpacing;
	}

	/**
	 * Gets the next element pos.
	 *
	 * @return the next element pos
	 */
	protected int getNextElementPos(){
		return this.nextElementYPos;
	}

	/**
	 * Sets the next element pos.
	 *
	 * @param pos the new next element pos
	 */
	protected void setNextElementPos(final int pos){
		this.nextElementYPos = pos;
	}

	/**
	 * Sets the element spacing.
	 *
	 * @param spacing the new element spacing
	 */
	protected void setElementSpacing(final int spacing){
		this.elementSpacing = spacing;
	}

	/**
	 * Creates the.
	 */
	protected abstract void create();

	/**
	 * Position elements.
	 */
	protected abstract void positionElements();

	/**
	 * Reload fonts.
	 */
	public static void reloadFonts(){
		GUI.loadFonts();
	}

	/**
	 * Load fonts.
	 */
	public static void loadFonts(){
		// load fonts in the hash table
		for(FontFile f:VictusLudus.resources.getFontHash().values()){
			try {
				f.setFontNormal(new UnicodeFont(f.getPath(), (int)(f.getDefaultSize() * VictusLudus.e.scalingFactor), false, false));
				f.setFontSmall(new UnicodeFont(f.getPath(), (int)(f.getSmallSize() * VictusLudus.e.scalingFactor), false, false));
				f.setFontLarge(new UnicodeFont(f.getPath(), (int)(f.getLargeSize() * VictusLudus.e.scalingFactor), false, false));
			} catch (SlickException e) {
				e.printStackTrace();
			} finally{
				GUI.InitializeUnicodeFont(f.getFontNormal());
				GUI.InitializeUnicodeFont(f.getFontSmall());
				GUI.InitializeUnicodeFont(f.getFontLarge());

				if(VictusLudus.e.IS_DEBUGGING){
					VictusLudus.LOGGER.info("using font " + f.getFontNormal().getFontFile() + " for " + f.getId());
					VictusLudus.LOGGER.info("using font " + f.getFontSmall().getFontFile() + " for " + f.getId());
					VictusLudus.LOGGER.info("using font " + f.getFontLarge().getFontFile() + " for " + f.getId());
				}
			}
		}
	}

	/**
	 * Load gui sprite sheets.
	 */
	public static void loadGUISpriteSheets(){
		GUI.guiButtonSheet = BitmapHandler.LoadSpriteSheet(GUI.GUI_SHEET_PATH);
	}

	/**
	 * Fetch font m.
	 *
	 * @param fontId the font id
	 * @return the unicode font
	 */
	public static UnicodeFont fetchFontM(final String fontId){
		return VictusLudus.resources.getFontHash().get(fontId).getFontNormal();
	}

	/**
	 * Fetch font s.
	 *
	 * @param fontId the font id
	 * @return the unicode font
	 */
	public static UnicodeFont fetchFontS(final String fontId){
		return VictusLudus.resources.getFontHash().get(fontId).getFontSmall();
	}

	/**
	 * Fetch font l.
	 *
	 * @param fontId the font id
	 * @return the unicode font
	 */
	public static UnicodeFont fetchFontL(final String fontId){
		return VictusLudus.resources.getFontHash().get(fontId).getFontLarge();
	}

	/**
	 * Initialize unicode font.
	 *
	 * @param font the font
	 */
	@SuppressWarnings("unchecked")
	public static void InitializeUnicodeFont(final UnicodeFont font){
		font.addAsciiGlyphs();
		font.addGlyphs(400, 600);
		font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));

		try {
			font.loadGlyphs();
		} catch (SlickException e) {
			VictusLudus.LOGGER.severe(e.toString());
		}
	}

	/**
	 * Unregister listening children.
	 */
	protected void unregisterListeningChildren(){
		for(GUIElement e: this.elementList){
			e.unregisterListeners();
		}
	}

	/**
	 * Show.
	 */
	protected void show(){
		VictusLudus.e.changeGUI(this);
	}

	/**
	 * Destroy.
	 */
	protected void destroy(){
		VictusLudus.e.changeGUI(null);
	}

	/**
	 * Checks if is disabled.
	 *
	 * @return true, if is disabled
	 */
	public boolean isDisabled() {
		return this.isDisabled;
	}

	/**
	 * Sets the disabled.
	 *
	 * @param isDisabled the new disabled
	 */
	public void setDisabled(final boolean isDisabled) {
		this.isDisabled = isDisabled;
		for(GUIElement e: this.elementList){
			e.setDisabled(isDisabled);
		}
	}

	/**
	 * Gets the background color.
	 *
	 * @return the background color
	 */
	public Color getBackgroundColor() {
		return this.backgroundColor;
	}

	/**
	 * Sets the background color.
	 *
	 * @param backgroundColor the new background color
	 */
	public void setBackgroundColor(final Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * Checks if is printable char.
	 *
	 * @param c the c
	 * @return true, if is printable char
	 */
	public static boolean isPrintableChar( final char c ) {
		Character.UnicodeBlock block = Character.UnicodeBlock.of( c );
		return !Character.isISOControl(c) &&
				block != null &&
				block != Character.UnicodeBlock.SPECIALS;
	}
}
