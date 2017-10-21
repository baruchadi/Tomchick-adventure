package smellychiz.projects.ogc.objects.world;

import smellychiz.projects.ogc.objects.GameObject;
import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public class Sign extends GameObject {

	public Sign(Context context, Vector3 v, Camera2D c, boolean finish) {
		super(context, v, c);
		this.setTexture(Assets.signs);

		int n = 4;

		this.bound.setWidth(7);
		this.bound.setHeight(10);

		this.setTextureArea(Assets.sign[n]);
		this.type = finish ? GameObject.FINISH_SIGN : GameObject.SIGN;
		initVertices();
	}

	public Sign(Context context, Vector3 v, Camera2D c, int type) {
		super(context, v, c);
		this.setTexture(Assets.signs);

		this.bound.setWidth(7);
		this.bound.setHeight(11);

		this.setTextureArea(Assets.sign[type]);
		this.type = GameObject.SIGN;
		initVertices();
	}

}
