
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
import com.teamderpy.victusludus.game.cosmos.Cosmos;
import com.teamderpy.victusludus.game.cosmos.UniverseSettings;

public class UINewUniverseMenu extends UI {
	private TextField universeNameField;
	private TextField universeSeedField;
	private Slider universeAgeSlider;
	private Slider universeDensitySlider;

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
		this.universeAgeSlider = new Slider(3, 66, 3, false, this.skin);
		this.universeAgeSlider.setValue(12);

		tableContent.add(universeAgeLabel).pad(UI.CELL_PADDING).padRight(UI.CELL_PADDING * 10).left();
		tableContent.add(universeAgeLessLabel).pad(UI.CELL_PADDING).right();
		tableContent.add(this.universeAgeSlider).pad(UI.CELL_PADDING);
		tableContent.add(universeAgeMoreLabel).pad(UI.CELL_PADDING).left();
		tableContent.row();

		this.universeAgeSlider.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActorr) {
				tooltipText.setText("The age of the universe");
			}
		});

		/************ UNIVERSE DENSITY */
		final Label universeDensityLabel = new Label("Universe density", this.skin);
		final Label universeDensityLessLabel = new Label("Sparse", this.skin);
		final Label universeDensityMoreLabel = new Label("Dense", this.skin);
		this.universeDensitySlider = new Slider(0, 1.5F, 0.1F, false, this.skin);
		this.universeDensitySlider.setValue(0.25F);

		tableContent.add(universeDensityLabel).pad(UI.CELL_PADDING).padRight(UI.CELL_PADDING * 10).left();
		tableContent.add(universeDensityLessLabel).pad(UI.CELL_PADDING).right();
		tableContent.add(this.universeDensitySlider).pad(UI.CELL_PADDING);
		tableContent.add(universeDensityMoreLabel).pad(UI.CELL_PADDING).left();
		tableContent.row();

		this.universeDensitySlider.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActorr) {
				tooltipText.setText("The density of the universe");
			}
		});

		/************ UNIVERSE SEED */
		final Label universeSeedLabel = new Label("Seed", this.skin);
		this.universeSeedField = new TextField("", this.skin);

		tableContent.add(universeSeedLabel).pad(UI.CELL_PADDING).padRight(UI.CELL_PADDING * 10).left();
		tableContent.add(this.universeSeedField).pad(UI.CELL_PADDING).colspan(3);
		tableContent.row();

		this.universeSeedField.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActorr) {
				tooltipText.setText("The random seed for the universe");
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
					UniverseSettings requestedSettings = new UniverseSettings();

					requestedSettings.setRequestedUniAge(UINewUniverseMenu.this.universeAgeSlider.getValue());
					requestedSettings.setRequestedUniDensity(UINewUniverseMenu.this.universeDensitySlider.getValue());
					requestedSettings.setRequestedSeed(UINewUniverseMenu.longHashString(UINewUniverseMenu.this.universeSeedField
						.getText()));

					VictusLudusGame.engine.changeUI(null);
					VictusLudusGame.engine.changeView(new Cosmos(), requestedSettings);
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

	/**
	 * Returns a hash code for this string as a long value
	 * 
	 * @param string to hash
	 * @return a long hash code
	 */
	private static long longHashString (final String string) {
		if (string.isEmpty()) {
			return VictusLudusGame.rand.nextLong();
		}

		long hash = 0L;

		for (int i = 0; i < string.length(); i++) {
			hash += Math.pow((string.charAt(i) * 63), string.length() - i);
		}

		return hash;
	}
}
