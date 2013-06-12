package com.teamderpy.victusludus.engine.graphics;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Pool.Poolable;

public class PoolableTexture implements Poolable{
	public Texture texture;
	
	public PoolableTexture() {
		this.texture = new Texture(1, 1, Format.RGBA8888);
	}

	@Override	
	public void reset() {
		//nothing to do
	}
	
	@Override
	public void finalize(){
		texture.dispose();
	}
}
