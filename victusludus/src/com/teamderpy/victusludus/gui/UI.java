package com.teamderpy.victusludus.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.physics.box2d.Shape.Type;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.gui.eventhandler.ResizeListener;
import com.teamderpy.victusludus.gui.eventhandler.event.ResizeEvent;

public class UI implements ResizeListener{
	protected Stage stage;
	protected Skin skin;

	public UI(){
		this.create();
	}
	
	public void create(){
		stage = new Stage();
		skin = new Skin();
		stage.setViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		
		VictusLudusGame.engine.inputMultiplexer.addProcessor(0, stage);
		
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		//add more widgets to table
		
		//example
		//table.add(new Image(skin.newDrawable("white", Color.RED))).size(64);
	}
	
	public void resize(int width, int height){
		stage.setViewport(width, height, true);
	}
	
	public void render(SpriteBatch batch, float deltaT){		
		stage.act(deltaT);
		stage.draw();
		
		//debug lines for tables
		Table.drawDebug(stage);
	}
	
	public void dispose(){
		stage.dispose();
		VictusLudusGame.engine.inputMultiplexer.removeProcessor(stage);
	}

	@Override
	public void onResize (ResizeEvent resizeEvent) {
		this.resize(resizeEvent.getWidth(), resizeEvent.getHeight());
	}
}
