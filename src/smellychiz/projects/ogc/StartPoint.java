package smellychiz.projects.ogc;

import smellychiz.projects.ogc.util.base.GameRenderer;
import smellychiz.projects.ogc.util.base.GameView;
import smellychiz.projects.ogc.util.views.LevelView;
import android.os.Bundle;
import android.view.KeyEvent;

public class StartPoint extends Start {

	public static LevelView mGLView;

	public static boolean load = false;

	@Override
	public GameView getView() {
		// TODO Auto-generated method stub
		return mGLView;
	}

	@Override
	public String getName() {
		Bundle bundle = getIntent().getExtras();
		return bundle.getString("name");

	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		mGLView = new LevelView(this, width, height, this);

	}

	@Override
	public void Render() {

		mGLView.requestRender();

	}

	@Override
	public void setDeltaTime(float d) {
		GameRenderer.setDeltaTime(d);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			mGLView.c.pauseGame();
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public String getFolder() {
		// TODO Auto-generated method stub
		Bundle bundle = getIntent().getExtras();
		return bundle.getString("folder");
	}

}
