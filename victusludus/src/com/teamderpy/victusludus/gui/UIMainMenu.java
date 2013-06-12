package com.teamderpy.victusludus.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.teamderpy.victusludus.VictusLudusGame;

public class UIMainMenu extends UI{
	@Override
	public void create(){
		stage = new Stage();
		skin = new Skin();
		
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		
		skin.add("white", new Texture(pixmap));
		skin.add("default", GUI.fetchFontM(GUI.PRIMARY_FONT_ID));
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		buttonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		buttonStyle.checked = skin.newDrawable("white", Color.BLUE);
		buttonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		buttonStyle.font = skin.getFont("default");
		skin.add("default", buttonStyle);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.fontColor = GUI.ELEMENT_COLOR_DEFAULT;
		labelStyle.font = skin.getFont("default");
		skin.add("default", labelStyle);
		
		stage.setViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		
		VictusLudusGame.engine.inputMultiplexer.addProcessor(0, stage);
		
		Table tableContent = new Table();
		tableContent.setFillParent(true);
		tableContent.setBackground(skin.newDrawable("white", Color.BLACK));
		tableContent.debug();
		tableContent.center();
		stage.addActor(tableContent);
		
		Table tableTitle = new Table();
		tableTitle.setFillParent(true);
		tableTitle.debug();
		tableTitle.top();
		stage.addActor(tableTitle);
		
		Table tableFooter = new Table();
		tableFooter.setFillParent(true);
		tableFooter.debug();
		tableFooter.bottom();
		stage.addActor(tableFooter);
		
		/************
		 * TITLE
		 */
		
		final Label titleText = new Label("Victus Ludus", skin);
		tableTitle.add(titleText);
		tableTitle.row();
		
		/************
		 * TOOLTIP
		 */
		
		final Label tooltipText = new Label("Select an option to begin", skin);
		tableFooter.add(tooltipText);
		tableFooter.row();
		
		/************
		 * NEW GAME
		 */
		
		final TextButton newWorldButton = new TextButton("New universe", skin);
		tableContent.add(newWorldButton);
		tableContent.row();
		
		newWorldButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				newWorldButton.setText("Good job!");
			}
		});
		
		newWorldButton.addListener(new ClickListener() {
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActorr) {
				tooltipText.setText("Generates a new universe to play in");
			}
		});
		
		/************
		 * OPTIONS
		 */
		
		final TextButton optionsButton = new TextButton("Options", skin);
		tableContent.add(optionsButton);
		tableContent.row();
		
		optionsButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				optionsButton.setText("Good job!");
			}
		});
		
		optionsButton.addListener(new ClickListener() {
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActorr) {
				tooltipText.setText("Change game, audio, and video settings");
			}
		});
		
		/************
		 * QUIT
		 */
		
		final TextButton quitButton = new TextButton("Quit", skin);
		tableContent.add(quitButton);
		tableContent.row();
		
		quitButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				VictusLudusGame.engine.stop();
			}
		});
		
		quitButton.addListener(new ClickListener() {
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActorr) {
				tooltipText.setText("Exits the application");
			}
		});
	}
}

