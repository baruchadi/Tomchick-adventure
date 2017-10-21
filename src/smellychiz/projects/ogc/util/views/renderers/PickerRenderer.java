package smellychiz.projects.ogc.util.views.renderers;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import smellychiz.projects.ogc.Start;
import smellychiz.projects.ogc.objects.Button;
import smellychiz.projects.ogc.objects.GameObject;
import smellychiz.projects.ogc.objects.world.Background;
import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.IO;
import smellychiz.projects.ogc.util.Manifest;
import smellychiz.projects.ogc.util.base.GameRenderer;
import smellychiz.projects.ogc.util.graphics.Texture;
import smellychiz.projects.ogc.util.graphics.TextureArea;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public class PickerRenderer extends GameRenderer {
	public Camera2D camera, KeyCam, splashCam;
	public Background background;
	Button[] buttons;

	Button survive, normal, pighunt, wave, back, exit, bCity, bFarm;

	GameObject splash;
	Start parent;

	Background bg;

	static final int PICK = 0, SURVIVE = 1, CLASSIC = 2, CITY = 5, FARM = 6,
			PIGHUNT = 3, WAVE = 4;
	public int state = PICK;

	GameObject logo, miniLogo, gear;
	AssetFileDescriptor afd;
	MediaPlayer click;
	IO io;

	public PickerRenderer(Context c, Start p) {

		super(c);
		io = new IO();
		this.parent = p;
		click = new MediaPlayer();

		try {
			afd = getContext().getAssets().openFd("sfx/shoot1.ogg");
			click.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
					afd.getLength());
			click.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Context contx;

	@Override
	public void CreateMainComponents() {
		contx = getContext();

		io.execute("lol");
		// io = new IO();
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
		Assets.loadBackground(5, getContext());

	}

	@Override
	public void draw() {
		camera.init();
		bg.draw();
		gear.draw();
		switch (state) {
		case PICK:

			survive.draw();
			normal.draw();
			logo.draw();
			exit.draw();
			break;
		case SURVIVE:
			pighunt.draw();
			wave.draw();
			back.draw();
			logo.draw();
			break;
		case CLASSIC:
			bCity.draw();
			bFarm.draw();
			back.draw();
			logo.draw();
			break;
		case PIGHUNT:
		case WAVE:
		case CITY:
		case FARM:
			back.draw();
			title.draw();
			miniLogo.draw();
			for (int i = 0; i < buttons.length; i++) {
				if (buttons[i] != null)
					buttons[i].draw();
			}

		}

	}

	@Override
	public void drawBackground() {

	}

	@Override
	public void drawLoading() {
		camera.init(camera.pos);
		splash.draw();
	}

	@Override
	public void drawObjects() {
	}

	@Override
	public void drawPaused() {
	}

	GameObject title;

	public static Manifest m;
	public static int lvlNum;

	float r1 = 1.15f;
	float shift = 1;

	@Override
	public void initObjects() {

		bg = new Background(getContext(), new Vector3(0, 0, 60, 24), camera,
				Assets.layers);

		title = new GameObject(getContext(), new Vector3(2,
				camera.pos.getHeight() - 2 - 6, 12, 6), camera,
				Assets.levelSelect);

		bCity = new Button(getContext(), new Vector3(27 - shift, 8 - 1,
				17 * r1, 7 * r1), camera, Assets.city, Assets.pCity);
		bFarm = new Button(getContext(), new Vector3(4 - shift, 8 - 1, 17 * r1,
				7 * r1), camera, Assets.farm, Assets.pFarm);
		survive = new Button(getContext(), new Vector3(4 - shift, 8 - 1,
				17 * r1, 7 * r1), camera, Assets.survival, Assets.psurvival);
		normal = new Button(getContext(), new Vector3(27 - shift, 8 - 1,
				17 * r1, 7 * r1), camera, Assets.normal, Assets.pnormal);
		wave = new Button(getContext(), new Vector3(27 - shift, 8 - 1, 17 * r1,
				7 * r1), camera, Assets.wave, Assets.pwave);
		pighunt = new Button(getContext(), new Vector3(4 - shift, 8 - 1,
				17 * r1, 7 * r1), camera, Assets.pighunt, Assets.ppighunt);
		back = new Button(getContext(), new Vector3(.2f, .2f, 4, 4), camera,
				Assets.back, Assets.pback);
		logo = new GameObject(getContext(), new Vector3(
				(camera.pos.getWidth() - 25) / 2,
				camera.topLeft().getY() - 5.5f, 25, 3.9f), camera, Assets.logo1);
		miniLogo = new GameObject(getContext(), new Vector3(
				camera.pos.getWidth() - 15, 0, 15, 3), camera, Assets.logo1);
		gear = new GameObject(getContext(), new Vector3(
				camera.pos.getWidth() - 4, camera.pos.getHeight() - 4, 3f, 3f),
				camera, Assets.gear);
		exit = new Button(getContext(), new Vector3(
				camera.pos.getWidth() / 2 - 2.5f, 1, 5, 2), camera,
				new TextureArea(Assets.exit), new TextureArea(Assets.exit));
		pighunt.setAlpha(.85f);
		wave.setAlpha(.85f);
		survive.setAlpha(.85f);
		normal.setAlpha(.85f);
		back.setAlpha(.75f);
	}

	@Override
	public void loadAssets() {

		assets.LoadPicker(getContext());
	}

	@Override
	public boolean touchDown(float x, float y, int id) {
		if (ready && IO.done) {

			switch (state) {
			case PICK:
				// if (back.contains(x, y)) {
				// back.press();
				// back.update();
				// }
				if (normal.contains(x, y)) {
					normal.press();
					normal.update();
				} else if (survive.contains(x, y)) {
					survive.press();
					survive.update();
				}
				break;
			case SURVIVE:
				if (wave.contains(x, y)) {
					wave.press();
					pighunt.release();
				} else if (pighunt.contains(x, y)) {
					wave.release();
					pighunt.press();
				}
				if (back.contains(x, y)) {
					back.press();
					back.update();
				}
				pighunt.update();
				wave.update();
				break;
			case CLASSIC:
				if (bCity.contains(x, y)) {
					bCity.press();
					bFarm.release();
				} else if (bFarm.contains(x, y)) {
					bCity.release();
					bFarm.press();
				}
				if (back.contains(x, y)) {
					back.press();
					back.update();
				}
				bFarm.update();
				bCity.update();
				break;
			case CITY:
			case FARM:
			case PIGHUNT:
			case WAVE:
				if (back.contains(x, y)) {
					back.press();
					back.update();
				}
				for (int i = 0; i < buttons.length; i++) {
					if (buttons[i].contains(x, y)) {
						buttons[i].press();
						buttons[i].update();
					} else {
						buttons[i].release();
						buttons[i].update();
					}
				}

			}
		}
		return false;
	}

	public void initButtons(int s) {
		IO io = new IO();
		switch (s) {
		// CHECK
		case FARM: {
			m = io.readManifest("farm", getContext());
			if (m != null) {
				buttons = new Button[Math.min(16, m.getLines())];
				int n = 0;
				int i = 2;
				for (int r = 0; r < 3; r++) {
					for (; i < 6; i++) {
						if (i + (r * 6) - 2 < buttons.length) {
							buttons[i + (r * 6) - 2] = new Button(getContext(),
									new Vector3((i * 7f) + 2, camera.pos
											.getHeight() - (r * 6) - 2 - 6, 6,
											6), camera,
									Assets.buttons[n], Assets.buttons[n + 16],
									m.getLevel(i + (r * 6) - 2));
							System.out.println(m.getLevel(i + (r * 6) - 2));
							n++;
						} else
							break;
					}
					i = 0;
				}
			} else
				s = PICK;
			break;
		}
		case CITY: {
			m = io.readManifest("city", getContext());
			if (m != null) {
				buttons = new Button[Math.min(16, m.getLines())];
				int n = 0;
				int i = 2;
				for (int r = 0; r < 3; r++) {
					for (; i < 6; i++) {
						if (i + (r * 6) - 2 < buttons.length) {
							buttons[i + (r * 6) - 2] = new Button(getContext(),
									new Vector3((i * 7f) + 2, camera.pos
											.getHeight() - (r * 6) - 2 - 6, 6,
											6), camera,
									Assets.buttons[n], Assets.buttons[n + 16],
									m.getLevel(i + (r * 6) - 2));
							System.out.println(m.getLevel(i + (r * 6) - 2));
							n++;
						} else
							break;
					}
					i = 0;
				}
			} else
				s = PICK;
			break;
		}
		case PIGHUNT: {

			m = io.readManifest("survival", getContext());
			if (m != null) {
				buttons = new Button[Math.min(16, m.getLines())];
				int n = 0;
				int i = 2;
				for (int r = 0; r < 3; r++) {
					for (; i < 6; i++) {
						if (i + (r * 6) - 2 < buttons.length) {
							buttons[i + (r * 6) - 2] = new Button(getContext(),
									new Vector3((i * 7f) + 2, camera.pos
											.getHeight() - (r * 8) - 2 - 6, 6,
											6), camera,
									Assets.buttons[n], Assets.buttons[n + 16],
									m.getLevel(i + (r * 6) - 2));
							System.out.println(m.getLevel(i + (r * 6) - 2));
							n++;
						} else
							break;
					}
					i = 0;
				}
			} else
				s = SURVIVE;
			break;

		}
		case WAVE: {
			m = io.readManifest("wave", getContext());
			if (m != null && m.isReady()) {
				buttons = new Button[Math.min(16, m.getLines())];
				int n = 0;
				int i = 2;
				for (int r = 0; r < 3; r++) {
					for (; i < 6; i++) {
						if (i + (r * 6) - 2 < buttons.length) {
							buttons[i + (r * 6) - 2] = new Button(getContext(),
									new Vector3((i * 7f) + 2, camera.pos
											.getHeight() - (r * 8) - 2 - 6, 6,
											6), camera,
									Assets.buttons[n], Assets.buttons[n + 16],
									m.getLevel(i + (r * 6) - 2));
							System.out.println(m.getLevel(i + (r * 6) - 2));
							n++;
						} else
							break;
					}
					i = 0;
				}
			} else
				s = SURVIVE;
			break;
		}
		}
		state = s;

	}

	@Override
	public boolean touchUp(float x, float y, int id) {
		if (ready && IO.done) {
			click.seekTo(0);
			click.start();
			switch (state) {
			case PICK:
				// if (back.contains(x, y)) {
				// back.release();
				// back.update();
				// this.parent.finish();
				//
				// }
				if (exit.contains(x, y)) {
					parent.finish();
					System.exit(0);
				}
				if (normal.contains(x, y)) {
					normal.release();
					survive.release();

					initButtons(CLASSIC);
				} else if (survive.contains(x, y)) {
					survive.release();
					normal.release();
					initButtons(SURVIVE);
				} else {
					normal.release();
					survive.release();

				}

				normal.update();
				survive.update();
				break;
			case SURVIVE:
				if (back.contains(x, y)) {
					back.release();
					back.update();
					state = PICK;

				}
				if (pighunt.contains(x, y)) {
					pighunt.release();
					wave.release();
					initButtons(PIGHUNT);
				} else if (wave.contains(x, y)) {
					wave.release();
					wave.release();
					initButtons(WAVE);
				} else {
					wave.release();
					pighunt.release();

				}

				wave.update();
				pighunt.update();
				break;
			case CLASSIC:
				if (back.contains(x, y)) {
					back.release();
					back.update();
					state = PICK;

				}
				if (bCity.contains(x, y)) {
					bCity.release();
					bFarm.release();
					initButtons(CITY);
					state = CITY;
				} else if (bFarm.contains(x, y)) {
					bFarm.release();
					bCity.release();
					initButtons(FARM);
					state = FARM;
				} else {
					wave.release();
					pighunt.release();

				}

				bCity.update();
				bFarm.update();
				break;
			case CITY:
			case FARM:
				if (back.contains(x, y)) {
					back.release();
					back.update();
					state = CLASSIC;

				}
				for (int i = 0; i < buttons.length; i++) {
					if (buttons[i].contains(x, y)) {
						startGame(buttons[i].release());
						buttons[i].update();
						lvlNum = i;
					}
				}
				break;
			case PIGHUNT:
			case WAVE:
				if (back.contains(x, y)) {
					back.release();
					back.update();
					state = SURVIVE;

				}
				for (int i = 0; i < buttons.length; i++) {
					if (buttons[i].contains(x, y)) {
						startGame(buttons[i].release());
						buttons[i].update();
					}
				}
				break;
			}
		}
		return false;
	}

	Vector3 move = new Vector3(0, 0);
	float MAX_X = 1;

	public Vector3 scaleToBackground() {

		float x = move.getX() / MAX_X;
		float y = move.getX() / MAX_X;

		// x /= 1;
		// y /= 1;
		if (x < 0) {
			x = 0;
		} else if (x > 1) {
			x = 1;
		}
		return new Vector3(x, y);

	}

	boolean dec = true;

	@Override
	public void update(float deltaTime) {

		if (dec) {
			if (move.getX() > -MAX_X / 3) {
				move.setX(move.getX() - (.001f * deltaTime));
			} else {
				dec = false;
			}
		} else {
			if (move.getX() < MAX_X / 3) {
				move.setX(move.getX() + (.001f * deltaTime));
			} else {
				dec = true;

			}
		}

		bg.update(scaleToBackground());
	}

	public void startGame(String s) {
		System.out.println(s);
		Intent i;
		switch (state) {
		case PIGHUNT:
			i = new Intent("android.intent.action.SURVIVE");
			i.putExtra("name", s);
			i.putExtra("folder", "survival");
			parent.startActivity(i);
			break;
		case WAVE:
			i = new Intent("android.intent.action.SURVIVE");
			i.putExtra("name", s);
			i.putExtra("folder", "wave");
			parent.startActivity(i);
			break;
		case FARM:
			i = new Intent("android.intent.action.GAME");
			i.putExtra("name", s);
			i.putExtra("folder", "farm");
			parent.startActivity(i);
			break;
		case CITY:
			i = new Intent("android.intent.action.GAME");
			i.putExtra("name", s);
			i.putExtra("folder", "city");
			parent.startActivity(i);
			break;
		}
		parent.finish();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean rr() {
		// TODO Auto-generated method stub
		return IO.done;
	}

}