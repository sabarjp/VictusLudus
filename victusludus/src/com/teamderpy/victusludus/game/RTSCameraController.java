
package com.teamderpy.victusludus.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntIntMap;

/**
 * Takes a {@link Camera} instance and controls it like an RTS. Modified from
 * LibGDX demo code for the FPS camera.
 */
public class RTSCameraController extends InputAdapter {
	private final Camera camera;
	private final IntIntMap keys = new IntIntMap();

	public int STRAFE_LEFT = Keys.A;
	public int STRAFE_RIGHT = Keys.D;
	public int FORWARD = Keys.W;
	public int BACKWARD = Keys.S;

	public int ZOOM_IN = Keys.X;
	public int ZOOM_OUT = Keys.Z;

	private float velocity = 5;
	private final Vector3 tmp = new Vector3();
	private float degreesPerPixel = 0.3F;

	public RTSCameraController (final Camera camera) {
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

	@Override
	public boolean touchDragged (final int screenX, final int screenY, final int pointer) {
		// points the camera as the mouse moves

		/*
		 * float deltaX = -Gdx.input.getDeltaX() * this.degreesPerPixel; float
		 * deltaY = -Gdx.input.getDeltaY() * this.degreesPerPixel;
		 * this.camera.direction.rotate(this.camera.up, deltaX);
		 * this.tmp.set(this.camera.direction).crs(this.camera.up).nor();
		 * this.camera.direction.rotate(this.tmp, deltaY);
		 */

		/* this.camera.up.rotate(this.tmp, deltaY); */

		return false;
	}

	public void update () {
		this.update(Gdx.graphics.getDeltaTime());
	}

	public void update (final float deltaTime) {
		if (this.keys.containsKey(this.FORWARD)) {
			this.tmp.set(this.camera.direction).nor().scl(deltaTime * this.velocity, 0, deltaTime * this.velocity);
			this.camera.position.add(this.tmp);
		}

		if (this.keys.containsKey(this.BACKWARD)) {
			this.tmp.set(this.camera.direction).nor().scl(-deltaTime * this.velocity, 0, -deltaTime * this.velocity);
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

		if (this.keys.containsKey(this.ZOOM_IN)) {
			this.tmp.set(this.camera.direction).nor().scl(deltaTime * this.velocity);
			this.camera.position.add(this.tmp);
		}

		if (this.keys.containsKey(this.ZOOM_OUT)) {
			this.tmp.set(this.camera.direction).nor().scl(-deltaTime * this.velocity);
			this.camera.position.add(this.tmp);
		}

		this.camera.update(true);
	}
}
