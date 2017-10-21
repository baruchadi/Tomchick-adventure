package smellychiz.projects.ogc.objects;

import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.graphics.TextureArea;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public class AlphaButton extends Button {

	public AlphaButton(Context context, Vector3 v, Camera2D c,
			TextureArea idle, TextureArea pressed) {
		super(context, v, c, idle, pressed);
	}

	@Override
	public void press() {
		// TODO Auto-generated method stub
		super.press();
		setAlpha(.9f);
	}

	@Override
	public String release() {
		// TODO Auto-generated method stub
		super.release();
		setAlpha(.75f);
		return null;
	}

}
