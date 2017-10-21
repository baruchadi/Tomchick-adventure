package smellychiz.projects.ogc;

import smellychiz.projects.ogc.util.base.GameRenderer;
import smellychiz.projects.ogc.util.base.GameView;
import smellychiz.projects.ogc.util.views.EditorView;

public class StartWorldEditor extends Start {
	public static EditorView mGLView;

	public static int size;

	@Override
	public GameView getView() {
		// TODO Auto-generated method stub
		return mGLView;
	}

	@Override
	public void initView() {
		mGLView = new EditorView(this, width, height, this, size);
	}

	@Override
	public void Render() {
		StartWorldEditor.mGLView.requestRender();
	}

	@Override
	public void setDeltaTime(float d) {
		GameRenderer.setDeltaTime(d);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFolder() {
		return null;
	}
}
