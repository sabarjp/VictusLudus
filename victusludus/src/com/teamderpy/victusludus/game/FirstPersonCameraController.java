
package com.teamderpy.victusludus.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntIntMap;

/**
 * Takes a {@link Camera} instance and controlls it via w,a,s,d and mouse
 * panning.
 * @author badlogic
 * 
 */
public class FirstPersonCameraController extends InputAdapter {
	private final Camera camera;
	private final IntIntMap keys = new IntIntMap();
	private int STRAFE_LEFT = Keys.A;
	private int STRAFE_RIGHT = Keys.D;
	private int FORWARD = Keys.W;
	private int BACKWARD = Keys.S;
	private int UP = Keys.Q;
	private int DOWN = Keys.E;
	private float velocity = 5;
	private float degreesPerPixel = 0.5f;
	private final Vector3 tmp = new Vector3();
	private final Vector3 tmp2 = new Vector3();

	public FirstPersonCameraController (final Camera camera) {
		this.camera = camera;
	}

	@Override
	public boolean keyDown (final int keycode) {
		this.keys.put(keycode, keycode);
		return true;
	}

	@Override
	public boolean keyUp (final int keycode) {
		this.keys.remove(keycode, 0);
		return true;
	}

	/**
	 * Sets the velocity in units per second for moving forward, backward and
	 * strafing left/right.
	 * @param velocity the velocity in units per second
	 */
	public void setVelocity (final float velocity) {
		this.velocity = velocity;
	}

	/**
	 * Sets how many degrees to rotate per pixel the mouse moved.
	 * @param degreesPerPixel
	 */
	public void setDegreesPerPixel (final float degreesPerPixel) {
		this.degreesPerPixel = degreesPerPixel;
	}

	@Override
	public boolean touchDragged (final int screenX, final int screenY, final int pointer) {
		float deltaX = -Gdx.input.getDeltaX() * this.degreesPerPixel;
		float deltaY = -Gdx.input.getDeltaY() * this.degreesPerPixel;
		this.camera.direction.rotate(this.camera.up, deltaX);
		this.tmp.set(this.camera.direction).crs(this.camera.up).nor();
		this.camera.direction.rotate(this.tmp, deltaY);
// camera.up.rotate(tmp, deltaY);
		return true;
	}

	public void update () {
		this.update(Gdx.graphics.getDeltaTime());
	}

	public void update (final float deltaTime) {
		if (this.keys.containsKey(this.FORWARD)) {
			this.tmp.set(this.camera.direction).nor().scl(deltaTime * this.velocity);
			this.camera.position.add(this.tmp);
		}
		if (this.keys.containsKey(this.BACKWARD)) {
			this.tmp.set(this.camera.direction).nor().scl(-deltaTime * this.velocity);
			this.camera.position.add(this.tmp);
		}
		if (this.keys.containsKey(this.STRAFE_LEFT)) {
			this.tmp.set(this.camera.direction).crs(this.camera.up).nor().scl(-deltaTime * this.velocity);
			this.camera.position.add(this.tmp);
		}
		if (this.keys.containsKey(this.STRAFE_RIGHT)) {
			this.tmp.set(this.camera.direction).crs(this.camera.up).nor().scl(deltaTime * this.velocity);
			this.camera.position.add(this.tmp);
		}
		if (this.keys.containsKey(this.UP)) {
			this.tmp.set(this.camera.up).nor().scl(deltaTime * this.velocity);
			this.camera.position.add(this.tmp);
		}
		if (this.keys.containsKey(this.DOWN)) {
			this.tmp.set(this.camera.up).nor().scl(-deltaTime * this.velocity);
			this.camera.position.add(this.tmp);
		}
		this.camera.update(true);
	}
}
