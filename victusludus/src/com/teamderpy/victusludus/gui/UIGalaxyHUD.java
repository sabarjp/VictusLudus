
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
import com.teamderpy.victusludus.game.cosmos.EnumCosmosMode;
import com.teamderpy.victusludus.game.renderer.cosmos.CosmosRenderer;

public class UIGalaxyHUD extends UI {
	private Label selectedStarName;
	private Label selectedStarType;
	private Label selectedStarAge;
	private Label selectedStarPlanetCount;

	/** The cosmos this hud belongs to */
	private CosmosRenderer cosmosRenderer;

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

		/************ SELECTED STAR NAME */

		this.selectedStarName = new Label("", this.skin, "default");
		tableContent.add(this.selectedStarName).left();
		tableContent.row();

		/************ SELECTED STAR TYPE */

		this.selectedStarType = new Label("", this.skin, "default");
		tableContent.add(this.selectedStarType).left();
		tableContent.row();

		/************ SELECTED STAR AGE */

		this.selectedStarAge = new Label("", this.skin, "default");
		tableContent.add(this.selectedStarAge).left();
		tableContent.row();

		/************ SELECTED STAR PLANET COUNT */

		this.selectedStarPlanetCount = new Label("", this.skin, "default");
		tableContent.add(this.selectedStarPlanetCount).left();
		tableContent.row();

		/************ TOOLTIP */

		final Label tooltipText = new Label("", this.skin);
		tableFooter.add(tooltipText);
		tableFooter.row();

		/************ BACK */

		final TextButton backButton = new TextButton("Back", this.skin);
		tableContent.add(backButton).pad(UI.CELL_PADDING).left();
		tableContent.row();

		backButton.addListener(new ChangeListener() {
			@Override
			public void changed (final ChangeEvent event, final Actor actor) {
				UIGalaxyHUD.this.cosmosRenderer.cosmos.setGalaxy(null);
				UIGalaxyHUD.this.cosmosRenderer.changePerspective(EnumCosmosMode.UNIVERSE_PERSPECTIVE);
			}
		});

		backButton.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActor) {
				tooltipText.setText("Returns to the galaxy select");
			}

			@Override
			public void exit (final InputEvent event, final float x, final float y, final int pointer, final Actor toActor) {
				tooltipText.setText("");
			}
		});
	}

	public void setCosmosRenderer (final CosmosRenderer cosmosRenderer) {
		this.cosmosRenderer = cosmosRenderer;
	}

	public CosmosRenderer getCosmosRenderer () {
		return this.cosmosRenderer;
	}

	/**
	 * Sets the selected star name
	 * 
	 * @param text the new selected star name
	 */
	public void setSelectedStarName (final String text) {
		this.selectedStarName.setText(text);
	}

	/**
	 * Sets the selected star type
	 * 
	 * @param text the new selected star type
	 */
	public void setSelectedStarType (final String text) {
		this.selectedStarType.setText(text);
	}

	/**
	 * Sets the selected star age
	 * 
	 * @param text the new selected star age
	 */
	public void setSelectedStarAge (final String text) {
		this.selectedStarAge.setText(text + " old");
	}

	/**
	 * Sets the selected star planet count
	 * 
	 * @param text the new selected star planet count
	 */
	public void setSelectedStarPlanetCount (final String text) {
		this.selectedStarPlanetCount.setText(text + " planets detected");
	}
}
