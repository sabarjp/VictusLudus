
package com.teamderpy.victusludus.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.teamderpy.victusludus.VictusLudusGame;

public class UIGameHUD extends UI {
	private Label currentTileText;
	private Label currentDepthText;
	private Label currentChunksText;
	private Label currentTimeText;

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

		/************ CURRENT TILE TEXT */

		this.currentTileText = new Label("", this.skin, "default");
		tableContent.add(this.currentTileText).left();
		tableContent.row();

		/************ CURRENT DEPTH TEXT */

		this.currentDepthText = new Label("", this.skin, "default");
		tableContent.add(this.currentDepthText).left();
		tableContent.row();

		/************ CURRENT DEPTH TEXT */

		this.currentChunksText = new Label("", this.skin, "default");
		tableContent.add(this.currentChunksText).left();
		tableContent.row();

		/************ CURRENT TIME TEXT */

		this.currentTimeText = new Label("", this.skin, "default");
		tableContent.add(this.currentTimeText).left();
		tableContent.row();

	}

	/**
	 * Sets the current tile text.
	 * 
	 * @param text the new current tile text
	 */
	public void setCurrentTileText (final String text) {
		this.currentTileText.setText("Selected: " + text);
	}

	/**
	 * Sets the current depth text.
	 * 
	 * @param text the new current depth text
	 */
	public void setCurrentDepthText (final String text) {
		this.currentDepthText.setText("Height: " + text);
	}

	/**
	 * Sets the current chunk text.
	 * 
	 * @param text the new current chunk text
	 */
	public void setCurrentChunksText (final String text) {
		this.currentChunksText.setText("Chunks: " + text);
	}

	/**
	 * Sets the current time text.
	 * 
	 * @param text the new current time text
	 */
	public void setCurrentTimeText (final String text) {
		this.currentTimeText.setText("Clock: " + text);
	}
}
