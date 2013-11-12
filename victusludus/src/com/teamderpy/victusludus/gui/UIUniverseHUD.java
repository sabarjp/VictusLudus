
package com.teamderpy.victusludus.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.teamderpy.victusludus.VictusLudusGame;

@Deprecated
public class UIUniverseHUD extends UI {
	private Label selectedGalaxyName;
	private Label selectedGalaxyType;
	private Label selectedGalaxyAge;
	private Label selectedGalaxyStarCount;

	@Override
	public void create () {
		this.stage = new Stage();

		this.stage.setViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

		VictusLudusGame.engine.inputMultiplexer.addProcessor(0, this.stage);

		Table tableContent = new Table();
		tableContent.setFillParent(true);
		// tableContent.debug();
		tableContent.top();
		tableContent.left();
		this.stage.addActor(tableContent);

		Table tableFooter = new Table();
		tableFooter.setFillParent(true);
		// tableFooter.debug();
		tableFooter.bottom();
		this.stage.addActor(tableFooter);

		/************ TOOLTIP */

		final Label tooltipText = new Label("", this.skin);
		tableFooter.add(tooltipText);
		tableFooter.row();

		/************ QUIT */

		final TextButton quitButton = new TextButton("Quit", this.skin);
		tableContent.add(quitButton).pad(UI.CELL_PADDING).left();
		tableContent.row();

		quitButton.addListener(new ChangeListener() {
			@Override
			public void changed (final ChangeEvent event, final Actor actor) {
				VictusLudusGame.engine.quitView();
			}
		});

		quitButton.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActor) {
				tooltipText.setText("Returns to the main menu");
			}

			@Override
			public void exit (final InputEvent event, final float x, final float y, final int pointer, final Actor toActor) {
				tooltipText.setText("");
			}
		});

		/************ SELECTED GALAXY NAME */

		this.selectedGalaxyName = new Label("", this.skin, "default");
		tableContent.add(this.selectedGalaxyName).left();
		tableContent.row();

		/************ SELECTED GALAXY NAME */

		this.selectedGalaxyType = new Label("", this.skin, "default");
		tableContent.add(this.selectedGalaxyType).left();
		tableContent.row();

		/************ SELECTED GALAXY NAME */

		this.selectedGalaxyAge = new Label("", this.skin, "default");
		tableContent.add(this.selectedGalaxyAge).left();
		tableContent.row();

		/************ SELECTED GALAXY NAME */

		this.selectedGalaxyStarCount = new Label("", this.skin, "default");
		tableContent.add(this.selectedGalaxyStarCount).left();
		tableContent.row();
	}

	/**
	 * Sets the selected galaxy name
	 * 
	 * @param text the new selected galaxy name
	 */
	public void setSelectedGalaxyName (final String text) {
		this.selectedGalaxyName.setText(text);
	}

	/**
	 * Sets the selected galaxy type
	 * 
	 * @param text the new selected galaxy type
	 */
	public void setSelectedGalaxyType (final String text) {
		this.selectedGalaxyType.setText(text);
	}

	/**
	 * Sets the selected galaxy age
	 * 
	 * @param text the new selected galaxy age
	 */
	public void setSelectedGalaxyAge (final String text) {
		this.selectedGalaxyAge.setText(text + " old");
	}

	/**
	 * Sets the selected galaxy star count
	 * 
	 * @param text the new selected galaxy star count
	 */
	public void setSelectedGalaxyStarCount (final String text) {
		this.selectedGalaxyStarCount.setText("~" + text + " stars detected");
	}
}
