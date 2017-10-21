package smellychiz.projects.ogc;

import smellychiz.projects.ogc.util.base.GameRenderer;
import smellychiz.projects.ogc.util.base.GameView;
import smellychiz.projects.ogc.util.views.PickerView;
import android.view.KeyEvent;

public class LevelSelector extends Start {

	public static PickerView mGLView;

	@Override
	public GameView getView() {
		// TODO Auto-generated method stub
		return mGLView;
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		mGLView = new PickerView(this, width, height, LevelSelector.this);

	}

	@Override
	public void Render() {
		mGLView.requestRender();
	}

	@Override
	public void setDeltaTime(float d) {
		mGLView.getRend();
		GameRenderer.setDeltaTime(d);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// startActivity(new Intent(this, Main_Menu.class));
			this.finish();
			System.exit(0);
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public String getFolder() {
		// TODO Auto-generated method stub
		return null;
	}

}
