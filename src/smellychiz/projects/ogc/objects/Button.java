package smellychiz.projects.ogc.objects;

import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.graphics.TextureArea;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public class Button extends GameObject {

	TextureArea idle, pressed;
	public String data;
	boolean update = false;

	public Button(Context context, Vector3 v, Camera2D c, TextureArea idle,
			TextureArea pressed) {
		super(context, v, c, idle);

		this.idle = idle;
		this.pressed = pressed;

	}

	public Button(Context context, Vector3 v, Camera2D c, TextureArea idle,
			TextureArea pressed, String data) {
		super(context, v, c, idle);

		this.idle = idle;
		this.pressed = pressed;
		this.data = data;
	}

	public void press() {
		setTextureArea(pressed);
		update = true;
	}

	public void update() {
		if (update) {
			this.initVertices();
			update = false;
		}
	}

	public String release() {
		setTextureArea(idle);
		update = true;
		return data;
	}

}
