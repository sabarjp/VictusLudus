
package com.teamderpy.victusludus.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.engine.FlexibleDisplayMode;
import com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent;

public class UIOptionsMenu extends UI {
	/* we hold some settings here to reflect changes without actually changing */

	/** The backup scaling factor. */
	private double BACKUP_SCALING_FACTOR = VictusLudusGame.engine.scalingFactor;

	/** The backup target framerate. */
	private int BACKUP_TARGET_FRAMERATE = VictusLudusGame.engine.targetFramerate;

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

		final Label titleText = new Label("Options", this.skin, "medium");
		tableTitle.add(titleText);
		tableTitle.row();

		/************ TOOLTIP */

		final Label tooltipText = new Label("Change video and game settings", this.skin);

		/************ RESOLUTION SELECT */
		Array<String> displayModes = new Array<String>(0);
		int currentDisplayIndex = VictusLudusGame.engine.displayModes.size - 1;
		int i = 0;

		for (FlexibleDisplayMode d : VictusLudusGame.engine.displayModes) {
			displayModes.add(d.width + "x" + d.height + "x" + d.displayMode.bitsPerPixel);

			if (d.softEquals(VictusLudusGame.engine.currentDisplayMode)) {
				currentDisplayIndex = i;
			}

			i++;
		}

		final Label resolutionSelectLabel = new Label("Resolution", this.skin);
		final SelectBox resolutionSelect = new SelectBox(displayModes.toArray(), this.skin);
		final ScrollPane resolutionScrollPane = new ScrollPane(resolutionSelect, this.skin);

		tableContent.add(resolutionSelectLabel).pad(UI.CELL_PADDING).left();
		tableContent.add(resolutionScrollPane).pad(UI.CELL_PADDING).right();
		tableContent.row();

		resolutionSelect.addListener(new ChangeListener() {
			@Override
			public void changed (final ChangeEvent event, final Actor actor) {
				tooltipText.setText(resolutionSelect.getSelection());
			}
		});

		resolutionSelect.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActorr) {
				tooltipText.setText("Sets the resolution of the game");
			}
		});

		resolutionSelect.setSelection(currentDisplayIndex);

		/************ FULLSCREEN SELECT */
		final CheckBox fullScreenSelect = new CheckBox(" Fullscreen?", this.skin);
		tableContent.add();
		tableContent.add(fullScreenSelect).pad(UI.CELL_PADDING).right();
		tableContent.row();

		fullScreenSelect.addListener(new ChangeListener() {
			@Override
			public void changed (final ChangeEvent event, final Actor actor) {
				UIOptionsMenu.this.soundSelect.play();
			}
		});

		fullScreenSelect.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActorr) {
				tooltipText.setText("Whether or not to be in full screen mode");
			}
		});

		if (VictusLudusGame.engine.IS_FULLSCREEN) {
			fullScreenSelect.setChecked(true);
		} else {
			fullScreenSelect.setChecked(false);
		}

		/************ VSYNC SELECT */
		final CheckBox vsyncSelect = new CheckBox(" Vsync?", this.skin);
		tableContent.add();
		tableContent.add(vsyncSelect).pad(UI.CELL_PADDING).right();
		tableContent.row();

		vsyncSelect.addListener(new ChangeListener() {
			@Override
			public void changed (final ChangeEvent event, final Actor actor) {
				UIOptionsMenu.this.soundSelect.play();
			}
		});

		vsyncSelect.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActorr) {
				tooltipText.setText("Whether or not to force the draw rate to match the refresh rate");
			}
		});

		if (VictusLudusGame.engine.IS_VSYNC) {
			vsyncSelect.setChecked(true);
		} else {
			vsyncSelect.setChecked(false);
		}

		/************ FRAMERATE CAP SELECT */
		final Label frameCapSelectLabel = new Label("Frame rate cap", this.skin);
		final SelectBox frameCapSelect = new SelectBox(new String[] {"30", "60", "120", "240", "Unlimited"}, this.skin);

		tableContent.add(frameCapSelectLabel).pad(UI.CELL_PADDING).left();
		tableContent.add(frameCapSelect).pad(UI.CELL_PADDING).right();
		tableContent.row();

		frameCapSelect.addListener(new ChangeListener() {
			@Override
			public void changed (final ChangeEvent event, final Actor actor) {
				if (frameCapSelect.getSelection().equals("30")) {
					VictusLudusGame.engine.targetFramerate = 30;
				} else if (frameCapSelect.getSelection().equals("60")) {
					VictusLudusGame.engine.targetFramerate = 60;
				} else if (frameCapSelect.getSelection().equals("120")) {
					VictusLudusGame.engine.targetFramerate = 120;
				} else if (frameCapSelect.getSelection().equals("240")) {
					VictusLudusGame.engine.targetFramerate = 240;
				} else if (frameCapSelect.getSelection().equals("Unlimited")) {
					VictusLudusGame.engine.targetFramerate = 999999999;
				}

				GUI.reloadFonts();
			}
		});

		frameCapSelect.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActorr) {
				tooltipText.setText("Sets the frame rate cap");
			}
		});

		if (VictusLudusGame.engine.targetFramerate == 30) {
			frameCapSelect.setSelection(0);
		} else if (VictusLudusGame.engine.targetFramerate == 60) {
			frameCapSelect.setSelection(1);
		} else if (VictusLudusGame.engine.targetFramerate == 120) {
			frameCapSelect.setSelection(2);
		} else if (VictusLudusGame.engine.targetFramerate == 240) {
			frameCapSelect.setSelection(3);
		} else {
			frameCapSelect.setSelection(4);
		}

		/************ DPI SELECT */
		final Label dpiSelectLabel = new Label("UI Scale", this.skin);
		final SelectBox dpiSelect = new SelectBox(new String[] {"Tiny", "Small", "Normal", "Large", "Huge"}, this.skin);

		tableContent.add(dpiSelectLabel).pad(UI.CELL_PADDING).left();
		tableContent.add(dpiSelect).pad(UI.CELL_PADDING).right();
		tableContent.row();

		dpiSelect.addListener(new ChangeListener() {
			@Override
			public void changed (final ChangeEvent event, final Actor actor) {
				if (dpiSelect.getSelection().equals("Tiny")) {
					VictusLudusGame.engine.scalingFactor = 0.5;
				} else if (dpiSelect.getSelection().equals("Small")) {
					VictusLudusGame.engine.scalingFactor = 0.75;
				} else if (dpiSelect.getSelection().equals("Normal")) {
					VictusLudusGame.engine.scalingFactor = 1.0;
				} else if (dpiSelect.getSelection().equals("Large")) {
					VictusLudusGame.engine.scalingFactor = 1.5;
				} else if (dpiSelect.getSelection().equals("Huge")) {
					VictusLudusGame.engine.scalingFactor = 2.0;
				}

				GUI.reloadFonts();
			}
		});

		dpiSelect.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActorr) {
				tooltipText.setText("Sets the scaling of the interface");
			}
		});

		if (VictusLudusGame.engine.scalingFactor == 0.5) {
			dpiSelect.setSelection(0);
		} else if (VictusLudusGame.engine.scalingFactor == 0.75) {
			dpiSelect.setSelection(1);
		} else if (VictusLudusGame.engine.scalingFactor == 1.0) {
			dpiSelect.setSelection(2);
		} else if (VictusLudusGame.engine.scalingFactor == 1.5) {
			dpiSelect.setSelection(3);
		} else if (VictusLudusGame.engine.scalingFactor == 2.0) {
			dpiSelect.setSelection(4);
		}

		/************ APPLY CHANGES */

		final TextButton applyButton = new TextButton("Apply changes", this.skin);
		tableFooter.add(applyButton).pad(UI.CELL_PADDING);
		tableFooter.row();

		applyButton.addListener(new ChangeListener() {
			@Override
			public void changed (final ChangeEvent event, final Actor actor) {
				UIOptionsMenu.this.soundSelect.play();

				boolean selectedFullscreen = fullScreenSelect.isChecked();
				boolean selectedVsync = vsyncSelect.isChecked();

				FlexibleDisplayMode selectedDisplayMode = VictusLudusGame.engine.displayModes.get(resolutionSelect
					.getSelectionIndex());
				FlexibleDisplayMode newDisplayMode = new FlexibleDisplayMode(selectedDisplayMode.width, selectedDisplayMode.height,
					selectedFullscreen);

				// overwrite backup settings
				UIOptionsMenu.this.BACKUP_SCALING_FACTOR = VictusLudusGame.engine.scalingFactor;
				UIOptionsMenu.this.BACKUP_TARGET_FRAMERATE = VictusLudusGame.engine.targetFramerate;

				// save to preferences
				VictusLudusGame.engine.preferences.write("settings->interface->dpi:" + VictusLudusGame.engine.scalingFactor);
				VictusLudusGame.engine.preferences.write("settings->interface->maximum framerate:"
					+ VictusLudusGame.engine.targetFramerate);

				// only go through this if a display mode option changed
				if (!VictusLudusGame.engine.currentDisplayMode.equals(newDisplayMode)
					|| VictusLudusGame.engine.IS_FULLSCREEN != selectedFullscreen || VictusLudusGame.engine.IS_VSYNC != selectedVsync) {
					final FlexibleDisplayMode oldDisplayMode = VictusLudusGame.engine.currentDisplayMode;
					final boolean oldIsFullscreen = VictusLudusGame.engine.IS_FULLSCREEN;
					final boolean oldVsync = VictusLudusGame.engine.IS_VSYNC;

					// change display mode
					VictusLudusGame.engine.IS_FULLSCREEN = selectedFullscreen;
					VictusLudusGame.engine.IS_VSYNC = selectedVsync;
					VictusLudusGame.engine.changeDisplayMode(newDisplayMode);
					VictusLudusGame.engine.eventHandler.resizeHandler.signalAllNow(new ResizeEvent(this, selectedDisplayMode.width,
						selectedDisplayMode.height));

					new Dialog("Confirm changes", UIOptionsMenu.this.skin, "dialog") {
						@Override
						protected void result (final Object object) {
							boolean answer = (Boolean)object;

							if (answer) {
								// write changes
								VictusLudusGame.engine.preferences.write("settings->video->x resolution:"
									+ VictusLudusGame.engine.currentDisplayMode.width);
								VictusLudusGame.engine.preferences.write("settings->video->y resolution:"
									+ VictusLudusGame.engine.currentDisplayMode.height);
								VictusLudusGame.engine.preferences.write("settings->video->fullscreen:"
									+ VictusLudusGame.engine.IS_FULLSCREEN);
								VictusLudusGame.engine.preferences.write("settings->video->vsync:" + VictusLudusGame.engine.IS_VSYNC);
							} else {
								// revert
								VictusLudusGame.engine.IS_FULLSCREEN = oldIsFullscreen;
								VictusLudusGame.engine.IS_VSYNC = oldVsync;
								VictusLudusGame.engine.changeDisplayMode(oldDisplayMode);
								VictusLudusGame.engine.eventHandler.resizeHandler.signalAllNow(new ResizeEvent(this,
									oldDisplayMode.width, oldDisplayMode.height));
							}
						}
					}.text("Would you like to keep these display settings?").button("Yes", true).button("Nope", false)
						.key(Keys.ENTER, true).key(Keys.ESCAPE, false).show(UIOptionsMenu.this.stage);

				}
			}
		});

		applyButton.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActorr) {
				tooltipText.setText("Apply your changes");
			}
		});

		/************ BACK */

		final TextButton backButton = new TextButton("Back", this.skin);
		tableFooter.add(backButton).pad(UI.CELL_PADDING);
		tableFooter.row();

		backButton.addListener(new ChangeListener() {
			@Override
			public void changed (final ChangeEvent event, final Actor actor) {
				UIOptionsMenu.this.soundSelect.play();

				// revert changes (does nothing if we applied settings)
				VictusLudusGame.engine.targetFramerate = UIOptionsMenu.this.BACKUP_TARGET_FRAMERATE;

				if (VictusLudusGame.engine.scalingFactor != UIOptionsMenu.this.BACKUP_SCALING_FACTOR) {
					VictusLudusGame.engine.scalingFactor = UIOptionsMenu.this.BACKUP_SCALING_FACTOR;
					GUI.reloadFonts();
				} else {
					VictusLudusGame.engine.scalingFactor = UIOptionsMenu.this.BACKUP_SCALING_FACTOR;
				}

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
