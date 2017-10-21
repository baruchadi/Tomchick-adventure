package smellychiz.projects.ogc;

import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.base.GameView;
import smellychiz.projects.ogc.util.thread.GameLoop;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public abstract class Start extends Activity implements java.io.Serializable {
	private boolean running = true;
	public static GameLoop loop;
	public Thread mSplashThread;
	public static Dialog splash;
	static int level;
	int height;
	int width;

	public abstract GameView getView();

	public abstract void initView();

	public boolean isRunning() {
		return running;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		RelativeLayout rel = new RelativeLayout(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		DisplayMetrics metrics = this.getResources().getDisplayMetrics();
		width = metrics.widthPixels;
		height = metrics.heightPixels;
		splash = new Dialog(this,
				android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		initView();
		setContentView(getView());
		loop = new GameLoop(this);
		getView().setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.setRunning(false);
		try {
			loop.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		// The following call resumes a paused rendering thread.
		// If you de-allocated graphic objects for onPause()
		// this is a good place to re-allocate them.
		loop = new GameLoop(this);
		loop.start();
		// loop = new GameLoop(this);
		setRunning(true);
	}

	@Override
	protected void onStart() {
		super.onStart();
		setRunning(true);
	}

	public abstract void Render();

	public void restart() {
		finish();
		startActivity(getIntent().setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
		Assets.finish();
	}

	public abstract void setDeltaTime(float d);

	public void setRunning(boolean run) {
		running = run;
	}

	public abstract String getName();

	public abstract String getFolder();
}
