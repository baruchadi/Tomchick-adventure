package smellychiz.projects.ogc.objects.world;

import smellychiz.projects.ogc.objects.GameObject;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.graphics.Texture;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public class Background {

	public GameObject[] layers;
	public Vector3 pos;

	public float cX, cY;

	public Background(Context context, Vector3 v, Camera2D cam, Texture... t) {
		layers = new GameObject[t.length];

		cX = v.getWidth();
		cY = v.getHeight();

		for (int i = 0; i < t.length; i++) {
			layers[i] = new GameObject(context, v, cam, t[i]);
		}
	}

	public void draw() {
		for (int i = 0; i < layers.length; i++) {
			layers[i].draw();
		}
	}

	public void draw(int i) {
		if (i < layers.length) {

			layers[i].draw();

		}
	}

	public void update(Vector3 v) {

		for (int i = 0; i < layers.length; i++) {
			float x = (-cX / 4)
					+ ((v.getX() * -1) * layers[i].bound.getWidth() / (((layers.length - i) + 1) * 1.5f))
					+ layers[i].bound.getWidth()
					/ (((layers.length - i) + 1) * 2);
			layers[i].bound.setX(x);
			layers[i].initVertices();
		}
	}

}
