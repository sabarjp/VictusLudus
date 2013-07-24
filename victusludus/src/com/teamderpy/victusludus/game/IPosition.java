
package com.teamderpy.victusludus.game;

import com.badlogic.gdx.math.Vector3;

public interface IPosition {
	public void setPos (Vector3 pos);

	public void setPos (float x, float y, float z);

	public Vector3 getPos ();
}
