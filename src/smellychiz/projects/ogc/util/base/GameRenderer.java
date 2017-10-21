package smellychiz.projects.ogc.util.base;

import javax.microedition.khronos.opengles.GL10;

import smellychiz.projects.ogc.util.Assets;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;

public abstract class GameRenderer implements Renderer {
	public boolean ready = false;
	public static float deltaTime;

	public static void setDeltaTime(float d) {
		deltaTime = d;

	}

	public boolean compCreated = false;
	Context context;
	public Assets assets;
	int countt = 0;
	public boolean paused = false;

	public float ratio;

	public GameRenderer(Context c) {
		context = c;

		assets = new Assets();
	}

	public abstract void CreateMainComponents();

	public abstract void draw();

	public abstract void drawBackground();

	public abstract void drawLoading();

	public abstract void drawObjects();

	public abstract void drawPaused();

	public Context getContext() {
		return context;
	}

	public abstract void initObjects();

	public boolean isPaused() {
		return paused;
	}

	public boolean isReady() {
		return ready;
	}

	public abstract void loadAssets();

	@Override
	public void onDrawFrame(GL10 gl) {
		if (isReady())
			this.update(deltaTime);
		GLES20.glClearColor(1, 0, 0, 1);
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

		if (this.isReady() && rr()) {
			draw();
			if (isPaused()) {
				drawPaused();
			}
		} else {
			drawLoading();
			if (countt == 1) {
				loadAssets();
				initObjects();
				setReady(true);
			} else
				countt++;
		}

	}

	public abstract boolean rr();

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {

		if (!compCreated) {
			CreateMainComponents();
			compCreated = true;
		}
		GLES20.glViewport(0, 0, width, height);
		ratio = (float) width / height;

	}

	@Override
	public void onSurfaceCreated(GL10 gl,
			javax.microedition.khronos.egl.EGLConfig config) {
		if (!compCreated) {
			CreateMainComponents();
			compCreated = true;
		}

	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public abstract boolean touchDown(float x, float y, int id);

	public abstract boolean touchUp(float x, float y, int id);

	public abstract void update(float deltaTime);

}
