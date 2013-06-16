
package com.teamderpy.victusludus.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.engine.SoundSystem;
import com.teamderpy.victusludus.gui.eventhandler.ResizeListener;
import com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent;

public class UI implements ResizeListener {
	protected Stage stage;
	protected Skin skin;

	protected Sound soundSelect;
	protected Sound soundType;

	protected static float CELL_PADDING = 4;

	public UI () {
		this.soundSelect = VictusLudusGame.engine.soundSystem.loadSound(SoundSystem.SOUND_SELECT_1);
		this.soundType = VictusLudusGame.engine.soundSystem.loadSound(SoundSystem.SOUND_TYPE_1);

		this.defineSkin();
		this.create();
	}

	public void defineSkin () {
		this.skin = new Skin();

		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();

		this.skin.add("white", new Texture(pixmap));
		pixmap.dispose();

		Pixmap pixmap2 = new Pixmap(24, 24, Format.RGBA8888);
		pixmap2.setColor(Color.WHITE);
		pixmap2.fill();

		this.skin.add("whiteLarge", new Texture(pixmap2));
		pixmap2.dispose();

		this.skin.add("default", GUI.fetchFontS(GUI.PRIMARY_FONT_ID));
		this.skin.add("medium", GUI.fetchFontM(GUI.PRIMARY_FONT_ID));

		TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.up = this.skin.newDrawable("white", Color.DARK_GRAY);
		buttonStyle.down = this.skin.newDrawable("white", Color.DARK_GRAY);
		buttonStyle.over = this.skin.newDrawable("white", Color.LIGHT_GRAY);
		buttonStyle.font = this.skin.getFont("default");
		buttonStyle.fontColor = Color.WHITE;
		this.skin.add("default", buttonStyle);

		LabelStyle labelStyle = new LabelStyle();
		labelStyle.fontColor = GUI.ELEMENT_COLOR_DEFAULT;
		labelStyle.font = this.skin.getFont("default");
		this.skin.add("default", labelStyle);

		LabelStyle titleStyle = new LabelStyle();
		titleStyle.fontColor = GUI.ELEMENT_COLOR_DEFAULT;
		titleStyle.font = this.skin.getFont("medium");
		this.skin.add("medium", titleStyle);

		SelectBoxStyle selectBoxStyle = new SelectBoxStyle();
		selectBoxStyle.background = this.skin.newDrawable("white", Color.LIGHT_GRAY);
		selectBoxStyle.listBackground = this.skin.newDrawable("white", Color.DARK_GRAY);
		selectBoxStyle.listSelection = this.skin.newDrawable("white", Color.YELLOW);
		selectBoxStyle.font = this.skin.getFont("default");
		selectBoxStyle.fontColor = Color.BLUE;
		this.skin.add("default", selectBoxStyle);

		CheckBoxStyle checkBoxStyle = new CheckBoxStyle();
		checkBoxStyle.checkboxOff = this.skin.newDrawable("whiteLarge", Color.DARK_GRAY);
		checkBoxStyle.checkboxOn = this.skin.newDrawable("whiteLarge", Color.BLUE);
		checkBoxStyle.font = this.skin.getFont("default");
		checkBoxStyle.fontColor = Color.WHITE;
		checkBoxStyle.overFontColor = Color.BLUE;
		checkBoxStyle.pressedOffsetX = -4.0F;
		this.skin.add("default", checkBoxStyle);

		TextFieldStyle textFieldStyle = new TextFieldStyle();
		textFieldStyle.background = this.skin.newDrawable("white", Color.LIGHT_GRAY);
		textFieldStyle.cursor = this.skin.newDrawable("white", Color.WHITE);
		textFieldStyle.selection = this.skin.newDrawable("white", Color.DARK_GRAY);
		textFieldStyle.font = this.skin.getFont("default");
		textFieldStyle.fontColor = Color.WHITE;
		this.skin.add("default", textFieldStyle);

		SliderStyle sliderStyle = new SliderStyle();
		sliderStyle.background = this.skin.newDrawable("whiteLarge", Color.LIGHT_GRAY);
		sliderStyle.knob = this.skin.newDrawable("whiteLarge", Color.WHITE);
		sliderStyle.knobAfter = this.skin.newDrawable("whiteLarge", Color.DARK_GRAY);
		sliderStyle.knobBefore = this.skin.newDrawable("whiteLarge", Color.DARK_GRAY);
		this.skin.add("default-horizontal", sliderStyle);

		ScrollPaneStyle scrollPaneStyle = new ScrollPaneStyle();
		scrollPaneStyle.background = this.skin.newDrawable("white", Color.DARK_GRAY);
		scrollPaneStyle.corner = this.skin.newDrawable("white", Color.LIGHT_GRAY);
		scrollPaneStyle.hScroll = this.skin.newDrawable("whiteLarge", Color.LIGHT_GRAY);
		scrollPaneStyle.hScrollKnob = this.skin.newDrawable("whiteLarge", Color.WHITE);
		scrollPaneStyle.vScroll = this.skin.newDrawable("whiteLarge", Color.LIGHT_GRAY);
		scrollPaneStyle.vScrollKnob = this.skin.newDrawable("whiteLarge", Color.WHITE);
		this.skin.add("default", scrollPaneStyle);

		WindowStyle dialogStyle = new WindowStyle();
		dialogStyle.background = this.skin.newDrawable("white", Color.YELLOW);
		dialogStyle.stageBackground = this.skin.newDrawable("white", Color.BLACK);
		dialogStyle.titleFont = this.skin.getFont("default");
		dialogStyle.titleFontColor = Color.LIGHT_GRAY;
		this.skin.add("dialog", dialogStyle);
	}

	public void create () {
		this.stage = new Stage();
		this.skin = new Skin();
		this.stage.setViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

		VictusLudusGame.engine.inputMultiplexer.addProcessor(0, this.stage);

		Table table = new Table();
		table.setFillParent(true);
		this.stage.addActor(table);

		// add more widgets to table

		// example
		// table.add(new Image(skin.newDrawable("white", Color.RED))).size(64);
	}

	public void resize (final int width, final int height) {
		this.stage.setViewport(width, height, true);
	}

	public void render (final SpriteBatch batch, final float deltaT) {
		this.stage.act(deltaT);
		this.stage.draw();

		// debug lines for tables
		Table.drawDebug(this.stage);
	}

	public void dispose () {
		this.stage.dispose();
		VictusLudusGame.engine.inputMultiplexer.removeProcessor(this.stage);
	}

	@Override
	public void onResize (final ResizeEvent resizeEvent) {
		this.resize(resizeEvent.getWidth(), resizeEvent.getHeight());
	}
}
