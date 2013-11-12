
package com.teamderpy.victusludus.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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
import com.teamderpy.victusludus.engine.ISettings;
import com.teamderpy.victusludus.engine.SettingsImpl;
import com.teamderpy.victusludus.game.Game;

public class UINewOrganismMenu extends UI {
	private TextField organismNameField;
	private TextField organismSeedField;
	private Slider worldAgeSlider;

	/* whether a name has been typed in */
	private boolean typedInName = false;

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

		final Label titleText = new Label("New Organism", this.skin, "medium");
		tableTitle.add(titleText);
		tableTitle.row();

		/************ TOOLTIP */

		final Label tooltipText = new Label("Create a new organism to play with", this.skin);

		/************ ORGANISM NAME */
		final Label organismLabelField = new Label("Organism name", this.skin);
		this.organismNameField = new TextField("", this.skin);

		tableContent.add(organismLabelField).pad(UI.CELL_PADDING).padRight(UI.CELL_PADDING * 10).left();
		tableContent.add(this.organismNameField).pad(UI.CELL_PADDING).colspan(3);
		tableContent.row();

		this.organismNameField.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActor) {
				tooltipText.setText("The name of your organism");
			}
		});

		this.organismNameField.addListener(new InputListener() {
			@Override
			public boolean keyTyped (final InputEvent event, final char character) {
				if (!UINewOrganismMenu.this.organismNameField.getText().isEmpty()) {
					UINewOrganismMenu.this.typedInName = true;
				} else {
					UINewOrganismMenu.this.typedInName = false;
				}
				return true;
			}
		});

		/************ WORLD AGE */
		final Label worldAgeLabel = new Label("World age", this.skin);
		final Label worldAgeLessLabel = new Label("Young", this.skin);
		final Label worldAgeMoreLabel = new Label("Old", this.skin);
		this.worldAgeSlider = new Slider(0.5F, 60.5F, 0.5F, false, this.skin);
		this.worldAgeSlider.setValue(12);

		tableContent.add(worldAgeLabel).pad(UI.CELL_PADDING).padRight(UI.CELL_PADDING * 10).left();
		tableContent.add(worldAgeLessLabel).pad(UI.CELL_PADDING).right();
		tableContent.add(this.worldAgeSlider).pad(UI.CELL_PADDING);
		tableContent.add(worldAgeMoreLabel).pad(UI.CELL_PADDING).left();
		tableContent.row();

		this.worldAgeSlider.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActor) {
				tooltipText.setText("The age of the world");
			}
		});

		/************ SEED */
		final Label organismSeedLabel = new Label("Seed", this.skin);
		this.organismSeedField = new TextField("", this.skin);

		tableContent.add(organismSeedLabel).pad(UI.CELL_PADDING).padRight(UI.CELL_PADDING * 10).left();
		tableContent.add(this.organismSeedField).pad(UI.CELL_PADDING).colspan(3);
		tableContent.row();

		this.organismSeedField.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActorr) {
				tooltipText.setText("The random seed for the world");
			}
		});

		this.organismSeedField.addListener(new InputListener() {
			@Override
			public boolean keyTyped (final InputEvent event, final char character) {
				if (!UINewOrganismMenu.this.typedInName) {
					UINewOrganismMenu.this.organismNameField.setText(UINewOrganismMenu.this.organismSeedField.getText());
				}
				return true;
			}
		});

		/************ CONTINUE */
		final TextButton continueButton = new TextButton("Continue", this.skin);
		tableFooter.add(continueButton).pad(UI.CELL_PADDING);
		tableFooter.row();

		continueButton.addListener(new ChangeListener() {
			@Override
			public void changed (final ChangeEvent event, final Actor actor) {
				UINewOrganismMenu.this.soundSelect.play();

				if (UINewOrganismMenu.this.organismNameField.getText().length() > 0) {
					ISettings requestedSettings = new SettingsImpl();

					requestedSettings.addValue("organismAge", UINewOrganismMenu.this.worldAgeSlider.getValue());
					requestedSettings.addValue("seed",
						UINewOrganismMenu.longHashString(UINewOrganismMenu.this.organismSeedField.getText()));

					if (UINewOrganismMenu.this.organismNameField.getText().equals("ortho")) {
						requestedSettings.addValue("useOrtho", true);
					} else {
						requestedSettings.addValue("useOrtho", false);
					}
					requestedSettings.addValue("mapHeight", 5);
					requestedSettings.addValue("mapWidth", 5);
					requestedSettings.addValue("mapSmoothness", 2.0f); // 0-10
					requestedSettings.addValue("mapRandomness", 0.25f); // 0-1.5
					requestedSettings.addValue("mapScale", 8f); // 1-12
					requestedSettings.addValue("mapPlateauFactor", 0.2f); // 0-0.3

					VictusLudusGame.engine.changeUI(null);
					VictusLudusGame.engine.changeView(new Game(), requestedSettings);
				} else {
					new Dialog("Organism name?", UINewOrganismMenu.this.skin, "dialog") {

					}.text("You forgot to enter a name!").button("OK!", true).key(Keys.ENTER, true).key(Keys.ESCAPE, true)
						.show(UINewOrganismMenu.this.stage);
				}
			}
		});

		continueButton.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActorr) {
				tooltipText.setText("Create the organism!");
			}
		});

		/************ BACK */

		final TextButton backButton = new TextButton("Back", this.skin);
		tableFooter.add(backButton).pad(UI.CELL_PADDING);
		tableFooter.row();

		backButton.addListener(new ChangeListener() {
			@Override
			public void changed (final ChangeEvent event, final Actor actor) {
				UINewOrganismMenu.this.soundSelect.play();
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
