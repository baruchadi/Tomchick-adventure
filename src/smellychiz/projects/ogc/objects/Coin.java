package smellychiz.projects.ogc.objects;

import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.graphics.Animation;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public class Coin extends GameObject {

	float max, min;
	float stateTime = 0;

	float aY = 0;

	float mY = .08f;
	boolean up = true;

	public Coin(Context context, Vector3 v, Camera2D c) {
		super(context, new Vector3(v.getX(), v.getY(), 2, 2), c);
		this.type = COIN;
		setTexture(Assets.coin.getKeyFrame(0, Animation.ANIMATION_LOOPING)
				.getTex());
		this.setTextureArea(Assets.coin.getKeyFrame(stateTime,
				Animation.ANIMATION_LOOPING));

		min = v.getY() - .5f;
		max = v.getY() + .5f;

	}

	public void update(float delta) {
		if (up) {
			if (bound.getY() < max) {
				if (aY < mY) {
					aY += .05f * delta;
				} else {
					aY = mY;
				}
			} else {
				up = false;
			}
		} else {
			if (bound.getY() > min) {
				if (aY > mY) {
					aY += -.05f * delta;
				} else {
					aY = -mY;
				}
			} else {
				up = true;
			}
		}
		this.setTextureArea(Assets.coin.getKeyFrame(stateTime,
				Animation.ANIMATION_LOOPING));

		stateTime += delta;

		this.bound.addY(aY);

		this.initVertices();

	}
}
