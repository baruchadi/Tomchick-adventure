package smellychiz.projects.ogc.util.thread;

import smellychiz.projects.ogc.Start;
import smellychiz.projects.ogc.util.views.renderers.LevelRenderer;
import android.util.Log;

public class GameLoop extends Thread {

	private final static int maxFPS = 30;
	private final static int maxFrameSkips = 5;
	private final static double framePeriod = 1000 / maxFPS;

	public static boolean running;
	public final static String TAG = "Chiz";

	public LevelRenderer rend;

	Thread t1, t2;

	// FPSCounter fpsCounter;

	float deltaTime;
	private Start s;

	public GameLoop(Start s) {
		// this.fpsCounter = new FPSCounter();
		deltaTime = 1.0f;
		this.s = s;
	}

	int clean = 100, cc = 0;;

	@Override
	public void run() {
		running = s.isRunning();
		long beginTime;
		long timeDiff;
		int sleepTime;
		int framesSkipped;

		double last = 0;

		sleepTime = 0;
		while (running) {

			running = s.isRunning();

			beginTime = System.currentTimeMillis();
			final double now = beginTime;
			if (last != 0)
				deltaTime = (float) ((now - last) / framePeriod);
			framesSkipped = 0;
			s.setDeltaTime(deltaTime);
			s.Render();
			cc++;
			if (cc % clean == 0) {
				System.gc();

				System.out.println("CLEANING!");
			}
			timeDiff = System.currentTimeMillis() - beginTime;

			sleepTime = (int) (framePeriod - timeDiff);

			last = now;

			// fpsCounter.tick();
			// Log.d("FPS", "FPS: "+fpsCounter.getFPS());
			if (sleepTime > 0) {
				try {
					Thread.sleep(sleepTime);

				} catch (InterruptedException e) {
				}
			}

			while (sleepTime < 0 && framesSkipped < maxFrameSkips) {

				sleepTime += framePeriod;
				framesSkipped++;
				Log.d(TAG, "Frames Skipped");
			}
		}

	}

}
