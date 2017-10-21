package smellychiz.projects.ogc.util.views.renderers;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import smellychiz.projects.ogc.Start;
import smellychiz.projects.ogc.objects.AlphaButton;
import smellychiz.projects.ogc.objects.Button;
import smellychiz.projects.ogc.objects.FinishMenu;
import smellychiz.projects.ogc.objects.GameObject;
import smellychiz.projects.ogc.objects.StatsMenu;
import smellychiz.projects.ogc.objects.mobs.Player;
import smellychiz.projects.ogc.objects.world.Background;
import smellychiz.projects.ogc.objects.world.SurviveLevel;
import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.base.GameRenderer;
import smellychiz.projects.ogc.util.graphics.Texture;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SurvivalRenderer extends GameRenderer {

	// public int lvlState = 0;

	// public static final int GAME = 0, SHOP = 1, CONT = 2;

	public Camera2D camera, KeyCam, splashCam;
	GameObject toolBar, t32, t64;
	AlphaButton attack, rapidFire, left, right, jump, pause;
	GameObject sign1, sign2, sign3;
	Button pauseTitle, bResume, bOptions, bQuit;
	GameObject HUD, health, icon;
	// stats
	StatsMenu stats;
	SurviveLevel l;

	GameObject overlayBlack;

	boolean ttw = false;

	boolean xld = false;

	GameObject splash;

	Background farm;

	boolean lTouched = false, rTouched = false;

	int movement = -1, moveLeftID = -1, moveRightID = -1, jumpID = -1,
			attID = -1, pauseID = -1, quitID = -1, contID = -1, rapidID = -1;

	int resumeID = -1;

	int dummy = 0;

	int DownCounter = 0;

	boolean once = true;

	float MaxHPWIDTH;

	int mhp = 7;

	String lvl = "";

	Start parent;

	public SurvivalRenderer(Context c, Start p) {
		super(c);
		this.parent = p;

	}

	String folder;

	public SurvivalRenderer(Context c, String lvl, Start p, String folder) {
		super(c);
		this.lvl = lvl;
		this.parent = p;
		this.folder = folder;
	}

	public void pauseGame() {

		this.paused = true;
	}

	public void resumeGame() {
		this.paused = false;
	}

	@Override
	public void CreateMainComponents() {

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

		camera.centerCam(l.player.bound);
		drawObjects();

		KeyCam.init();

		drawHUD();
		l.cCoins.draw();
	}

	@Override
	public void drawBackground() {

		farm.draw();
	}

	public void drawHUD() {
		left.draw();
		right.draw();
		jump.draw();
		attack.draw();
		rapidFire.draw();
		pause.draw();

		HUD.draw();
		health.draw();
		icon.draw();

	}

	@Override
	public void drawLoading() {
		camera.init(camera.pos);
		splash.draw();
	}

	@Override
	public void drawObjects() {
		// sign1.draw();
		// sign2.draw();
		// sign3.draw();
		l.draw();
		l.player.draw();

	}

	// FinishMenu finish;

	@Override
	public void drawPaused() {
		overlayBlack.draw();
		if (bShop) {
			l.finish.draw();
		} else if (bStats) {
			stats.draw();
		} else {
			bResume.draw();
			bOptions.draw();
			bQuit.draw();
		}
	}

	public void exit() {
		Toast.makeText(getContext(), "saving...", Toast.LENGTH_SHORT).show();
	}

	boolean bShop = false;
	boolean bStats = false;

	@Override
	public void initObjects() {

		HUD = new GameObject(getContext(), new Vector3(0,
				KeyCam.pos.getHeight() - 3.5f, 12, 3.5f), KeyCam,
				Assets.mainHud);
		health = new GameObject(getContext(), new Vector3(4.85f,
				KeyCam.pos.getHeight() - .85f, 7.03f, .73f), KeyCam,
				Assets.green);
		MaxHPWIDTH = health.getTArea().getWidth();
		icon = new GameObject(getContext(), new Vector3(2.5f - 1.7f,
				KeyCam.pos.getHeight() - 2.7f, 2.5f, 2.5f), KeyCam,
				Assets.PlayerIdel);
		HUD.setAlpha(.65f);
		health.setAlpha(.75f);
		icon.setAlpha(.75f);
		l = new SurviveLevel(getContext(), null, camera, ratio, lvl, KeyCam,
				folder);
		camera.mX = l.getWidth();
		camera.mY = l.getHeight();
		attack = new AlphaButton(getContext(), new Vector3(18, 2, 2, 1.8f),
				this.KeyCam, Assets.att, Assets.cAtt);
		rapidFire = new AlphaButton(getContext(), new Vector3(18, 4, 2, 1.8f),
				this.KeyCam, Assets.rapid, Assets.cRapid);
		jump = new AlphaButton(getContext(), new Vector3(17, 0, 2, 1.8f),
				this.KeyCam, Assets.jump, Assets.cjump);
		left = new AlphaButton(getContext(), new Vector3(0, 0, 2, 1.8f),
				this.KeyCam, Assets.lArrow, Assets.clArrow);
		right = new AlphaButton(getContext(), new Vector3(3, 0, 2, 2),
				this.KeyCam, Assets.rArrow, Assets.crArrow);
		bResume = new Button(getContext(), new Vector3(
				KeyCam.pos.getWidth() / 2 - 2, KeyCam.pos.getHeight() - 4, 4,
				1.5f), this.KeyCam, Assets.resume, Assets.cResume);
		bOptions = new Button(getContext(), new Vector3(
				KeyCam.pos.getWidth() / 2 - 2, KeyCam.pos.getHeight() - 5.5f,
				4, 1.5f), this.KeyCam, Assets.options, Assets.cOptions);
		bQuit = new Button(getContext(), new Vector3(
				KeyCam.pos.getWidth() / 2 - 2 + .67f,
				KeyCam.pos.getHeight() - 7f, 2.66f, 1.5f), this.KeyCam,
				Assets.quit, Assets.cQuit);
		l.finish = new FinishMenu(getContext(), KeyCam);
		pause = new AlphaButton(getContext(), new Vector3(
				KeyCam.pos.getWidth() - 2 - .2f,
				KeyCam.pos.getHeight() - 2 - .2f, 2, 1.8f), this.KeyCam,
				Assets.pause, Assets.cPause);

		stats = new StatsMenu(getContext(), KeyCam, l.player);
		attack.setAlpha(.75f);
		jump.setAlpha(.75f);
		right.setAlpha(.75f);
		left.setAlpha(.75f);

		farm = new Background(getContext(), new Vector3(0, 0, 30, 10), KeyCam,
				Assets.layers);
		overlayBlack = new GameObject(getContext(), KeyCam.getPos(), KeyCam,
				Assets.overlay);
		overlayBlack.setAlpha(.5f);

		ready = true;

	}

	@Override
	public void loadAssets() {
		assets.LoadAll(getContext());
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		super.onSurfaceChanged(gl, width, height);
		camera.cOrtho(0, -ratio, ratio, -ratio, ratio, -5, 5);
	}

	public Vector3 scaleToBackground() {

		float x = (l.player.bound.getRealX() > l.getWidth()) ? l.getWidth()
				: ((l.player.bound.getRealX() < 0) ? 0 : l.player.bound
						.getRealX());
		float y = l.player.bound.getY();

		x /= l.getWidth();
		y /= l.getHeight();

		return new Vector3(x, y);

	}

	@Override
	public boolean touchDown(float x, float y, int id) {
		DownCounter = 0;
		System.out.println(x + ", " + y);
		if (ready) {
			if (!paused) {

				if (attack.contains(x, y)) {

					attID = id;

					l.player.setAttacking(true);
					System.out.println("attack");
					attack.press();

					return true;
				} else if (rapidFire.contains(x, y)) {

					rapidID = id;

					l.player.setRapid();
					System.out.println("RAPID attack");
					rapidFire.press();

					return true;
				} else if (pause.contains(x, y) && pauseID == -1) {
					pauseID = id;

					pause.press();
					return true;
				} else if (jump.contains(x, y)) {

					System.out.println("jump");
					l.player.jump();
					jump.press();
					jumpID = id;
					Assets.finish();
					return true;
				}

				if (left.contains(x, y)) {
					movement = id;
					moveLeftID = id;
					left.press();
					right.release();
					l.player.setActionX(Player.MOVE_LEFT);
					System.out.println("left");
					lTouched = true;
					rTouched = false;
					return true;
				} else if (right.contains(x, y)) {

					movement = id;
					moveRightID = id;

					left.release();
					right.press();
					l.player.setActionX(Player.MOVE_RIGHT);
					System.out.println("right");
					rTouched = true;
					lTouched = false;
					return true;
				} else {

					lTouched = false;
					rTouched = false;
					return true;
				}

			} else if (!bShop) {
				if (bResume.contains(x, y)) {
					resumeID = id;

					bResume.press();
					return true;
				} else if (bQuit.contains(x, y)) {
					quitID = id;

					bQuit.press();
					return true;
				}
			} else {
				if (l.finish.bCont.contains(x, y)) {
					contID = id;

					l.finish.bCont.press();
					return true;
				}
			}
		}
		return false;

	}

	@Override
	public boolean touchUp(float x, float y, int id) {
		DownCounter = 0;
		if (ready) {
			if (!paused) {

				if (HUD.contains(x, y)) {
					pauseGame();
					bStats = true;
				}

				if (id == movement) {
					if (id == moveRightID) {
						right.release();
						moveRightID = -1;
					}
					if (id == moveLeftID) {
						left.release();
						moveLeftID = -1;
					}
					l.player.setActionX(Player.IDEL);
					movement = -1;
					right.release();
					moveRightID = -1;
					left.release();
					moveLeftID = -1;
				}
				if (id == jumpID) {
					jump.release();
					jumpID = -1;
				}
				if (id == rapidID) {

					rapidFire.release();
					rapidID = -1;
				}
				if (id == attID) {

					attack.release();
					attID = -1;
				}
				if (id == pauseID) {

					lTouched = false;
					rTouched = false;

					pause.release();
					pauseID = -1;
					pauseGame();
				}

			} else if (bShop) {
				if (id == contID) {
					contID = -1;

					l.finish.bCont.release();
					l.respawn();
					resumeGame();
					bShop = false;
				}

			} else if (bStats) {
				if (!stats.onTouch(x, y, l.player, l.coinTotal, getContext(),
						KeyCam)) {
					resumeGame();
					bStats = false;
				} else {
					l.cCoins.updateText(l.coinTotal, KeyCam);
				}
			} else {
				if (id == resumeID) {
					resumeID = -1;

					bResume.release();
					resumeGame();
				} else if (bQuit.contains(x, y)) {
					quitID = -1;

					bQuit.release();
					parent.startActivity(new Intent(
							"android.intent.action.PICKER"));
					Assets.finish();
					parent.finish();
				}
			}
		}
		return false;

	}

	@Override
	public void update(float deltaTime) {

		if (!paused) {

			if ((!lTouched && !rTouched) && l.player.onGround) {

				l.player.setActionX(Player.IDEL);

			}

			rapidFire.setAlpha((float) (l.player.rdelayed)
					/ (float) (l.player.RAPID_DELAY));
			l.update(deltaTime);
			l.cCoins.update(KeyCam);
			health.getTArea().setWidth(
					Math.min(MaxHPWIDTH, l.player.getHealth() * MaxHPWIDTH));
			health.bound.setWidth(Math.min(mhp, l.player.getHealth() * mhp));
			health.getTArea().setUV();
			health.initVertices();
			farm.update(scaleToBackground());

			if (paused) {

				if (bShop) {
					l.finish.update();
				} else if (bStats) {

				} else {
					bQuit.update();
					bResume.update();
				}
			} else {
				left.update();
				right.update();
				attack.update();
				jump.update();
			}

			if (l.enemies.size() == 0) {
				l.endLevel();
				bShop = true;
				pauseGame();

			}

		}

	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
	}

	@Override
	public boolean rr() {
		// TODO Auto-generated method stub
		return true;
	}

}
