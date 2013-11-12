
package com.teamderpy.victusludus.engine.graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

/**
 * The Class GameCamera.
 */
public class GameCamera {
	private Camera camera;

	private int zoom = 0;
	private float moveVelocity = 40;
	private float zoomVelocity = 15;
	private final Vector3 tmp = new Vector3();

	private final Vector3 intersection = new Vector3();
	private Plane intersectionPlane;

	public GameCamera (final Camera camera) {
		this.camera = camera;
	}

	public Vector3 getGamePlaneIntersection (final int x, final int y) {
		Ray pickRay = this.camera.getPickRay(x, y);
		Intersector.intersectRayPlane(pickRay, this.intersectionPlane, this.intersection);
		return this.intersection;
	}

	public void setGamePlane (final int y) {
		this.intersectionPlane = new Plane(new Vector3(0, 1, 0), y);
	}

	/**
	 * Move camera up.
	 */
	public void moveCamera (final int offsetX, final int offsetY, final float deltaTime) {
		this.tmp.set(this.camera.direction).nor().scl(deltaTime * offsetY, 0, deltaTime * offsetY);
		this.camera.position.add(this.tmp);
		this.tmp.set(this.camera.direction).crs(this.camera.up).nor().scl(deltaTime * offsetX);
		this.camera.position.add(this.tmp);
		this.camera.update(true);
	}

	/**
	 * Move camera up.
	 */
	public void moveCameraUp (final float deltaTime) {
		this.tmp.set(this.camera.direction).nor().scl(deltaTime * this.moveVelocity, 0, deltaTime * this.moveVelocity);
		this.camera.position.add(this.tmp);
		this.camera.update(true);
	}

	/**
	 * Move camera down.
	 */
	public void moveCameraDown (final float deltaTime) {
		this.tmp.set(this.camera.direction).nor().scl(-deltaTime * this.moveVelocity, 0, -deltaTime * this.moveVelocity);
		this.camera.position.add(this.tmp);
		this.camera.update(true);
	}

	/**
	 * Move camera left.
	 */
	public void moveCameraLeft (final float deltaTime) {
		this.tmp.set(this.camera.direction).crs(this.camera.up).nor().scl(-deltaTime * this.moveVelocity);
		this.camera.position.add(this.tmp);
		this.camera.update(true);
	}

	/**
	 * Move camera right.
	 */
	public void moveCameraRight (final float deltaTime) {
		this.tmp.set(this.camera.direction).crs(this.camera.up).nor().scl(deltaTime * this.moveVelocity);
		this.camera.position.add(this.tmp);
		this.camera.update(true);
	}

	/**
	 * Zoom camera in.
	 */
	public void zoomCameraIn (final float deltaTime) {
		if (this.zoom > 0) {
			this.zoom--;
			this.tmp.set(this.camera.direction).nor().scl(deltaTime * this.zoomVelocity);
			this.camera.position.add(this.tmp);
			this.camera.update(true);
		}
	}

	/**
	 * Zoon camera out.
	 */
	public void zoomCameraOut (final float deltaTime) {
		if (this.zoom < 200) {
			this.zoom++;
			this.tmp.set(this.camera.direction).nor().scl(-deltaTime * this.zoomVelocity);
			this.camera.position.add(this.tmp);
			this.camera.update(true);
		}
	}

	public Camera getCamera () {
		return this.camera;
	}
}
