package smellychiz.projects.ogc.objects;

import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.graphics.Animation;
import smellychiz.projects.ogc.util.graphics.Texture;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public abstract class Mob extends GameObject {
	public float stateTime;
	Texture tex;

	Animation a;

	public boolean onGround = false;

	public int actX, actY;

	public float dX, dY;

	public double energyloss = 1, gravity = .2, gameDy = 2, dt = .3;

	public Mob(Context context, Vector3 v, Camera2D c) {
		super(context, v, c);
	}

	public abstract void afterUpdate(float time);

	public abstract void beforUpdate(float time);

	public abstract boolean isGravity();

	public void jump() {

		if (onGround) {
			System.out.println(onGround);
			this.bound.setY(this.bound.getY() + .25f);
			this.dY = (float) gameDy;
			onGround = false;
		}
	}

	public void jump(float aY) {

		if (onGround) {
			System.out.println(onGround);
			this.bound.setY(this.bound.getY() + .25f);
			this.dY = aY;
			onGround = false;
		}
	}

	public void update(float time) {
		beforUpdate(time);

		afterUpdate(time);

	}

	// soon to be - MOB CLASS!
}
