package smellychiz.projects.ogc.objects.world;

import smellychiz.projects.ogc.objects.GameObject;
import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public class Platform extends GameObject {

	GameObject[] mPlatform = new GameObject[] {};

	public int mWidth = 4, mHeight = 2;
	public Vector3 pos;

	int width = 0;

	Context mContext;

	public Platform(Context context, Vector3 v, Camera2D c) {
		super(context, v, c);
		this.type = PLATFORM;
		mPlatform = new GameObject[(int) ((v.getWidth() / mWidth) + 1)];
		mContext = context;
		pos = v;
		pos.setHeight(mHeight);

		mPlatform[0] = new GameObject(context, new Vector3(v.getX(), v.getY(),
				mWidth / 2, mHeight), c, (v.getY() == 0) ? Assets.baseRight
				: Assets.pside);
		width = mWidth / 2;
		int i = 1;
		while (width + mWidth < pos.getWidth()) {

			mPlatform[i] = new GameObject(context, new Vector3(
					v.getX() + width, v.getY(), mWidth, mHeight), c,
					(v.getY() == 0) ? Assets.baseMid : Assets.pLeft);
			width += mWidth;
			i++;
		}

		mPlatform[i] = new GameObject(context, new Vector3(v.getX()
				+ v.getWidth() - mWidth / 2, v.getY(), mWidth / 2, mHeight), c,
				(v.getY() == 0) ? Assets.baseRight : Assets.pside);

		mPlatform[0].bound.setFlipped(true);

		mPlatform[0].initVertices();
		//
		//
		// mPlatform[1].getTArea().getUvCoords()
	}

	public void draw(Camera2D c) {

		for (int i = 0; i < mPlatform.length; i++) {
			if (c.pos.getX() + c.pos.getWidth() >= mPlatform[i].bound.getX() - 4) {
				if (mPlatform[i] != null)
					mPlatform[i].draw();
			} else {
				break;
			}

		}
	}

	public void updateVert() {
		mPlatform[0].bound.setX(pos.getX());
		mPlatform[0].bound.setY(pos.getY());

		width = mWidth / 2;
		int i = 1;
		while (width + mWidth < pos.getWidth()) {

			mPlatform[i].bound.setX(pos.getX() + width);
			mPlatform[i].bound.setY(pos.getY());

			width += mWidth;
			i++;
		}

		mPlatform[i].bound.setX(pos.getX() + pos.getWidth() - mWidth / 2);
		mPlatform[i].bound.setY(pos.getY());
		mPlatform[0].bound.setFlipped(true);
		for (int l = 0; l < mPlatform.length; l++) {
			mPlatform[l].initVertices();

		}

	}
}
