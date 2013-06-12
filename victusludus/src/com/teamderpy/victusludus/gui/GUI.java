package com.teamderpy.victusludus.gui;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.data.DataReader;
import com.teamderpy.victusludus.data.VFile;
import com.teamderpy.victusludus.data.resources.FontFile;
import com.teamderpy.victusludus.engine.graphics.BitmapHandler;
import com.teamderpy.victusludus.engine.graphics.EasyGL;
import com.teamderpy.victusludus.gui.element.GUIElement;



/**
 * The Class GUI.
 */
public abstract class GUI{

	/** The element color default. */
	public static Color ELEMENT_COLOR_DEFAULT = new Color(0.18f, 0.18f, 0.18f, 1);

	/** The selection color default. */
	public static Color SELECTION_COLOR_DEFAULT = new Color(0.26f, 0.22f, 1, 1);

	/** The hover color default. */
	public static Color HOVER_COLOR_DEFAULT = new Color(1, 0, 0, 1);

	/** The press color default. */
	public static Color PRESS_COLOR_DEFAULT = new Color(0, 1, 0, 1);

	/** The subelement color default. */
	public static Color SUBELEMENT_COLOR_DEFAULT = new Color(0.44f ,0.44f ,0.44f ,1);

	/** The subselection color default. */
	public static Color SUBSELECTION_COLOR_DEFAULT = new Color(0.15f , 0.14f ,0.61f ,1);

	/** The subhover color default. */
	public static Color SUBHOVER_COLOR_DEFAULT = new Color(0.61f ,0 ,0 ,1);

	/** The focused color default. */
	public static Color FOCUSED_COLOR_DEFAULT = new Color(1, 1, 0, 1);

	/** The tooltip text color default. */
	public static Color TOOLTIP_TEXT_COLOR_DEFAULT = new Color(0.48f, 0.48f, 0.48f,1);

	/** The pmono font id. */
	public static String PRIMARY_FONT_ID = "EpilepsySansMono";

	/** The title font id. */
	public static String TITLE_FONT_ID = "EpilepsySansMono";

	/** The toolt font id. */
	public static String TOOLTIP_FONT_ID = "EpilepsySansMono";

	/** The Constant GUI_SHEET_PATH. */
	public static final String  GUI_SHEET_PATH = "sheet/buttons.png";

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
	public static Sprite guiButtonSheet = null;
	
	/** default font */
	public static BitmapFont defaultFont = new BitmapFont(true);

	/** The background color. */
	protected Color backgroundColor = new Color(0, 0, 0, 1);

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

		this.width = VictusLudusGame.engine.X_RESOLUTION();
		this.height = VictusLudusGame.engine.Y_RESOLUTION();

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

		this.width = VictusLudusGame.engine.X_RESOLUTION();
		this.height = VictusLudusGame.engine.Y_RESOLUTION();

		this.elementList = new ArrayList<GUIElement>();

		this.create();
		this.registerListeners();
		this.positionElements();
	}

	/**
	 * Render.
	 * @param deltaT 
	 * @param batch 
	 */
	public void render(SpriteBatch batch, float deltaT) {
		if(this.backgroundColor != null){
			EasyGL.drawRect(batch, this.backgroundColor, 0, 0, this.width, this.height);
		}

		for(GUIElement i:this.elementList){
			i.render(batch, deltaT);
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
		for(FontFile f:VictusLudusGame.resources.getFontHash().values()){

				FileHandle font = VFile.getFileHandle(f.getPath());
				FreeTypeFontGenerator ft = new FreeTypeFontGenerator(font);
				
				f.setFontNormal(ft.generateFont((int)(f.getDefaultSize() * VictusLudusGame.engine.scalingFactor), FreeTypeFontGenerator.DEFAULT_CHARS, false));
				f.setFontSmall(ft.generateFont((int)(f.getSmallSize() * VictusLudusGame.engine.scalingFactor), FreeTypeFontGenerator.DEFAULT_CHARS, false));
				f.setFontLarge(ft.generateFont((int)(f.getLargeSize() * VictusLudusGame.engine.scalingFactor), FreeTypeFontGenerator.DEFAULT_CHARS, false));
				
				ft.dispose();

				if(VictusLudusGame.engine.IS_DEBUGGING){
					Gdx.app.log("info", "using font " + f.getFontNormal() + " for " + f.getId());
					Gdx.app.log("info", "using font " + f.getFontSmall() + " for " + f.getId());
					Gdx.app.log("info", "using font " + f.getFontLarge() + " for " + f.getId());
				}
		}
	}

	/**
	 * Load gui sprite sheets.
	 */
	public static void loadGUISpriteSheets(){
		GUI.guiButtonSheet = VictusLudusGame.resources.getTextureAtlasGUI().createSprite(GUI.GUI_SHEET_PATH);
	}

	/**
	 * Fetch font m.
	 *
	 * @param fontId the font id
	 * @return the bitmap font
	 */
	public static BitmapFont fetchFontM(final String fontId){
		return VictusLudusGame.resources.getFontHash().get(fontId).getFontNormal();
	}

	/**
	 * Fetch font s.
	 *
	 * @param fontId the font id
	 * @return the bitmap font
	 */
	public static BitmapFont fetchFontS(final String fontId){
		return VictusLudusGame.resources.getFontHash().get(fontId).getFontSmall();
	}

	/**
	 * Fetch font l.
	 *
	 * @param fontId the font id
	 * @return the bitmap font
	 */
	public static BitmapFont fetchFontL(final String fontId){
		return VictusLudusGame.resources.getFontHash().get(fontId).getFontLarge();
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
		VictusLudusGame.engine.changeGUI(this);
	}

	/**
	 * Destroy.
	 */
	protected void destroy(){
		VictusLudusGame.engine.changeGUI(null);
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
