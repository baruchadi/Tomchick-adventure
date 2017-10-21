package smellychiz.projects.ogc.util.views.renderers;

import javax.microedition.khronos.opengles.GL10;

import smellychiz.projects.ogc.objects.GameObject;
import smellychiz.projects.ogc.objects.world.Background;
import smellychiz.projects.ogc.objects.world.NewLevel;
import smellychiz.projects.ogc.objects.world.Platform;
import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.IO;
import smellychiz.projects.ogc.util.base.GameRenderer;
import smellychiz.projects.ogc.util.graphics.Texture;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;
import android.widget.Toast;

public class EditorRenderer extends GameRenderer {

	public Camera2D camera, KeyCam, splashCam;
	GameObject pause, toolBar, t32, t64, item, place;

	NewLevel l;

	public static final int NONE = -1, PLAYER = 0, ENEMY = 1, PLATFORM = 2;

	int lSize;

	int countt = 0;
	public int equiped;

	boolean ttw = false;

	boolean xld = false;

	GameObject splash;

	public IO io;

	Platform p;

	Background farm;
	boolean lTouched = false, rTouched = false;

	int movement = -1, moveLeftID = -1, moveRightID = -1, jumpID = -1;

	boolean placed = false;

	public boolean panning = true;

	boolean dummy = false;

	int DownCounter = 0;

	boolean once = true;

	public EditorRenderer(Context c, int LevelSize) {
		super(c);

		lSize = LevelSize;
	}

	@Override
	public void CreateMainComponents() {
		io = new IO();
		splashCam = new Camera2D(0, 0, 10, 20);
		splashCam.init(splashCam.pos);
		splash = new GameObject(getContext(), new Vector3(0, 0, 20, 10),
				splashCam, new Texture(getContext(), "logo/logo.png"));
		splash.draw();
		float scale = 1.2f;
		camera = new Camera2D(0, 0, (20 * scale), 40 * scale);
		camera.init(camera.pos);
		KeyCam = new Camera2D(0, 0, 10, 20);

		KeyCam.init(KeyCam.pos);
	}

	@Override
	public void draw() {
		KeyCam.init();
		drawBackground();

		drawObjects();

		KeyCam.init();

		drawHUD();
	}

	@Override
	public void drawBackground() {

		farm.draw(0);
	}

	public void drawHUD() {
	}

	@Override
	public void drawLoading() {
		camera.init(camera.pos);
		splash.draw();
	}

	@Override
	public void drawObjects() {
		l.draw();

		p.draw(camera);

		item.draw();
		place.draw();
		// l.player.draw();

	}

	@Override
	public void drawPaused() {

	}

	public void exit() {
		Toast.makeText(getContext(), "saving...", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void initObjects() {
		l = new NewLevel(getContext(), null, camera, ratio, lSize);
		camera.mX = l.getWidth();
		camera.mY = l.getHeight();
		camera.SetBoundries(l.minX, l.minY, l.maxX, l.minY);
		farm = new Background(getContext(), new Vector3(0, 0, 30, 10), KeyCam,
				Assets.layers);
		item = new GameObject(getContext(), new Vector3(0, 10 - 4, 4, 4),
				KeyCam, Assets.ball);
		place = new GameObject(getContext(), new Vector3(20 - 4, 10 - 4, 4, 4),
				KeyCam, Assets.rArrow);
		place.setAlpha(.5f);
		item.setAlpha(.5f);
		ready = true;
		p = new Platform(getContext(), new Vector3(0, 0, 4, 4), camera);

	}

	@Override
	public void loadAssets() {
		assets.LoadAll(getContext());
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		camera.cOrtho(0, -ratio, ratio, -ratio, ratio, -5, 5);
	}

	public Vector3 scaleToBackground() {

		float x = l.player.bound.getX();
		float y = l.player.bound.getY();

		x /= l.getWidth();
		y /= l.getHeight();

		return new Vector3(x, y);

	}

	public boolean touch(float x, float y, int id) {
		DownCounter = 0;
		float x1 = x * KeyCam.pos.getWidth();
		float y1 = y * KeyCam.pos.getHeight();

		if (place.contains(x1, y1)) {

			// l.addPlatform(p);

		} else if (!panning) {
			p.pos.setX((float) Math.floor(camera.pos.getX()
					+ (x * camera.pos.getWidth()) - (p.pos.getWidth() / 2)));
			p.pos.setY((float) Math.floor(camera.pos.getY()
					+ (y * camera.pos.getHeight()) - (p.pos.getHeight() / 2)));
			p.updateVert();
			return true;
		}

		return false;

	}

	@Override
	public boolean touchDown(float x, float y, int id) {
		int x1 = (int) Math.floor(camera.pos.getX()
				+ (x * camera.pos.getWidth()));
		int y1 = (int) Math.floor(camera.pos.getY()
				+ (y * camera.pos.getHeight()));

		System.out.println(l.insideGrid(x1, y1));
		System.out.println(x1 + ", " + y1);

		return false;
	}

	@Override
	public boolean touchUp(float x, float y, int id) {
		DownCounter = 0;

		float x1 = x * KeyCam.pos.getWidth();
		float y1 = y * KeyCam.pos.getHeight();

		if (item.contains(x1, y1)) {
			panning = !panning;
			System.out.println("touched");
			return panning;
		} else if (place.contains(x1, y1)) {

			dummy = true;

		}

		return false;

	}

	@Override
	public void update(float deltaTime) {

		l.update(deltaTime);

		if (dummy) {

			l.addPlatform(p.pos.getX(), p.pos.getY(), (int) p.pos.getWidth());

			dummy = false;
		}

	}

	@Override
	public void onSurfaceCreated(GL10 gl,
			javax.microedition.khronos.egl.EGLConfig config) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean rr() {
		// TODO Auto-generated method stub
		return true;
	}

}