package smellychiz.projects.ogc;

import smellychiz.projects.ogc.util.base.GameRenderer;
import smellychiz.projects.ogc.util.base.GameView;
import smellychiz.projects.ogc.util.views.SurvivalView;
import android.os.Bundle;
import android.view.KeyEvent;

public class StartSurviving extends Start {
	public static SurvivalView mGLView;

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
		mGLView = new SurvivalView(this, width, height, this);

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
		Bundle bundle = getIntent().getExtras();
		return bundle.getString("folder");
	}
}
