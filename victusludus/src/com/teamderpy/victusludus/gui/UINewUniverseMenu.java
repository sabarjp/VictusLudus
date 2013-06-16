
package com.teamderpy.victusludus.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.teamderpy.victusludus.VictusLudusGame;

public class UINewUniverseMenu extends UI {
	private TextField universeNameField;

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

		final Label titleText = new Label("New Universe", this.skin, "medium");
		tableTitle.add(titleText);
		tableTitle.row();

		/************ TOOLTIP */

		final Label tooltipText = new Label("Create a new universe to play in", this.skin);

		/************ UNIVERSE NAME */
		final Label universeNameLabel = new Label("Universe name", this.skin);
		this.universeNameField = new TextField("", this.skin);

		tableContent.add(universeNameLabel).pad(UI.CELL_PADDING).padRight(UI.CELL_PADDING * 10).left();
		tableContent.add(this.universeNameField).pad(UI.CELL_PADDING).colspan(3);
		tableContent.row();

		this.universeNameField.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActorr) {
				tooltipText.setText("The name of your universe");
			}
		});

		/************ UNIVERSE AGE */
		final Label universeAgeLabel = new Label("Universe age", this.skin);
		final Label universeAgeLessLabel = new Label("Young", this.skin);
		final Label universeAgeMoreLabel = new Label("Old", this.skin);
		final Slider universeAgeSlider = new Slider(3, 33, 3, false, this.skin);
		universeAgeSlider.setValue(12);

		tableContent.add(universeAgeLabel).pad(UI.CELL_PADDING).padRight(UI.CELL_PADDING * 10).left();
		tableContent.add(universeAgeLessLabel).pad(UI.CELL_PADDING).right();
		tableContent.add(universeAgeSlider).pad(UI.CELL_PADDING);
		tableContent.add(universeAgeMoreLabel).pad(UI.CELL_PADDING).left();
		tableContent.row();

		universeAgeSlider.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActorr) {
				tooltipText.setText("The age of the universe");
			}
		});

		/************ UNIVERSE DENSITY */
		final Label universeDensityLabel = new Label("Universe density", this.skin);
		final Label universeDensityLessLabel = new Label("Sparse", this.skin);
		final Label universeDensityMoreLabel = new Label("Dense", this.skin);
		final Slider universeDensitySlider = new Slider(0, 1.5F, 0.1F, false, this.skin);
		universeDensitySlider.setValue(0.25F);

		tableContent.add(universeDensityLabel).pad(UI.CELL_PADDING).padRight(UI.CELL_PADDING * 10).left();
		tableContent.add(universeDensityLessLabel).pad(UI.CELL_PADDING).right();
		tableContent.add(universeDensitySlider).pad(UI.CELL_PADDING);
		tableContent.add(universeDensityMoreLabel).pad(UI.CELL_PADDING).left();
		tableContent.row();

		universeDensitySlider.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActorr) {
				tooltipText.setText("The density of the universe");
			}
		});

		/************ CONTINUE */
		final TextButton continueButton = new TextButton("Continue", this.skin);
		tableFooter.add(continueButton).pad(UI.CELL_PADDING);
		tableFooter.row();

		continueButton.addListener(new ChangeListener() {
			@Override
			public void changed (final ChangeEvent event, final Actor actor) {
				UINewUniverseMenu.this.soundSelect.play();

				if (UINewUniverseMenu.this.universeNameField.getText().length() > 0) {

				} else {
					new Dialog("Universe name?", UINewUniverseMenu.this.skin, "dialog") {

					}.text("You forgot to enter a universe name!").button("OK!", true).key(Keys.ENTER, true).key(Keys.ESCAPE, true)
						.show(UINewUniverseMenu.this.stage);
				}
			}
		});

		continueButton.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActorr) {
				tooltipText.setText("Create the universe");
			}
		});

		/************ BACK */

		final TextButton backButton = new TextButton("Back", this.skin);
		tableFooter.add(backButton).pad(UI.CELL_PADDING);
		tableFooter.row();

		backButton.addListener(new ChangeListener() {
			@Override
			public void changed (final ChangeEvent event, final Actor actor) {
				UINewUniverseMenu.this.soundSelect.play();
				VictusLudusGame.engine.changeUI(new UIMainMenu());
			}
		});

		backButton.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActorr) {
				tooltipText.setText("Returns to the main menu");
			}
		});

		// add tooltip at end
		tableFooter.add(tooltipText);
		tableFooter.row();
	}
}
