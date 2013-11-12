
package com.teamderpy.victusludus.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.teamderpy.victusludus.VictusLudusGame;

public class UIMainMenu extends UI {
	@Override
	public void create () {
		this.stage = new Stage();

		this.stage.setViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

		VictusLudusGame.engine.inputMultiplexer.addProcessor(0, this.stage);

		Table tableContent = new Table();
		tableContent.setFillParent(true);
		tableContent.setBackground(this.skin.newDrawable("white", Color.BLACK));
		// tableContent.debug();
		tableContent.center();
		this.stage.addActor(tableContent);

		Table tableTitle = new Table();
		tableTitle.setFillParent(true);
		// tableTitle.debug();
		tableTitle.top();
		this.stage.addActor(tableTitle);

		Table tableFooter = new Table();
		tableFooter.setFillParent(true);
		// tableFooter.debug();
		tableFooter.bottom();
		this.stage.addActor(tableFooter);

		/************ TITLE */

		final Label titleText = new Label("Victus Ludus", this.skin, "medium");
		tableTitle.add(titleText);
		tableTitle.row();

		/************ TOOLTIP */

		final Label tooltipText = new Label("Select an option to begin", this.skin);
		tableFooter.add(tooltipText);
		tableFooter.row();

		/************ NEW GAME */

		final TextButton newWorldButton = new TextButton("New organism", this.skin);
		tableContent.add(newWorldButton).pad(UI.CELL_PADDING);
		tableContent.row();

		newWorldButton.addListener(new ChangeListener() {
			@Override
			public void changed (final ChangeEvent event, final Actor actor) {
				UIMainMenu.this.soundSelect.play();
				VictusLudusGame.engine.changeUI(new UINewOrganismMenu());
			}
		});

		newWorldButton.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActorr) {
				tooltipText.setText("Creates a new organism to play with");
			}
		});

		/************ OPTIONS */

		final TextButton optionsButton = new TextButton("Options", this.skin);
		tableContent.add(optionsButton).pad(UI.CELL_PADDING);
		tableContent.row();

		optionsButton.addListener(new ChangeListener() {
			@Override
			public void changed (final ChangeEvent event, final Actor actor) {
				UIMainMenu.this.soundSelect.play();
				VictusLudusGame.engine.changeUI(new UIOptionsMenu());
			}
		});

		optionsButton.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActorr) {
				tooltipText.setText("Change game, audio, and video settings");
			}
		});

		/************ QUIT */

		final TextButton quitButton = new TextButton("Quit", this.skin);
		tableContent.add(quitButton).pad(UI.CELL_PADDING);
		tableContent.row();

		quitButton.addListener(new ChangeListener() {
			@Override
			public void changed (final ChangeEvent event, final Actor actor) {
				VictusLudusGame.engine.stop();
			}
		});

		quitButton.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActorr) {
				tooltipText.setText("Exits the application");
			}
		});
	}
}
