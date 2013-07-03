
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
import com.teamderpy.victusludus.renderer.cosmos.CosmosRenderer;

public class UIStellarSystemHUD extends UI {
	private Label selectedPlanetName;
	private Label selectedPlanetType;
	private Label selectedPlanetAge;

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
				UIStellarSystemHUD.this.cosmosRenderer.cosmos.getStar().removePlanets();
				UIStellarSystemHUD.this.cosmosRenderer.cosmos.setStar(null);
				UIStellarSystemHUD.this.cosmosRenderer.changePerspective(EnumCosmosMode.GALAXY_PERSPECTIVE);
			}
		});

		backButton.addListener(new ClickListener() {
			@Override
			public void enter (final InputEvent event, final float x, final float y, final int pointer, final Actor fromActor) {
				tooltipText.setText("Returns to the star select");
			}

			@Override
			public void exit (final InputEvent event, final float x, final float y, final int pointer, final Actor toActor) {
				tooltipText.setText("");
			}
		});

		/************ SELECTED PLANET NAME */

		this.selectedPlanetName = new Label("", this.skin, "default");
		tableContent.add(this.selectedPlanetName).left();
		tableContent.row();

		/************ SELECTED PLANET TYPE */

		this.selectedPlanetType = new Label("", this.skin, "default");
		tableContent.add(this.selectedPlanetType).left();
		tableContent.row();

		/************ SELECTED PLANET AGE */

		this.selectedPlanetAge = new Label("", this.skin, "default");
		tableContent.add(this.selectedPlanetAge).left();
		tableContent.row();
	}

	public void setCosmosRenderer (final CosmosRenderer cosmosRenderer) {
		this.cosmosRenderer = cosmosRenderer;
	}

	public CosmosRenderer getCosmosRenderer () {
		return this.cosmosRenderer;
	}

	/**
	 * Sets the selected planet name
	 * 
	 * @param text the new selected planet name
	 */
	public void setSelectedPlanetName (final String text) {
		this.selectedPlanetName.setText(text);
	}

	/**
	 * Sets the selected planet type
	 * 
	 * @param text the new selected planet type
	 */
	public void setSelectedPlanetType (final String text) {
		this.selectedPlanetType.setText(text);
	}

	/**
	 * Sets the selected planet age
	 * 
	 * @param text the new selected planet age
	 */
	public void setSelectedPlanetAge (final String text) {
		this.selectedPlanetAge.setText(text + " old");
	}
}
