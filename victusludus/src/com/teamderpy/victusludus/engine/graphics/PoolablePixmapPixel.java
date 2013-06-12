package com.teamderpy.victusludus.engine.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.utils.Pool.Poolable;

public class PoolablePixmapPixel implements Poolable{
	public Pixmap pixmap;
	
	public PoolablePixmapPixel() {
		this.pixmap = new Pixmap(1, 1, Format.RGBA8888);
	}

	@Override	
	public void reset() {
		pixmap.drawPixel(0, 0, Color.rgba8888(1, 1, 1, 1));
	}
	
	@Override
	public void finalize(){
		pixmap.dispose();
	}
}
