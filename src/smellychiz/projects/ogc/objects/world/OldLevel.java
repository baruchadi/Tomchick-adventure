package smellychiz.projects.ogc.objects.world;

import java.text.DecimalFormat;

import smellychiz.projects.ogc.objects.FinishMenu;
import smellychiz.projects.ogc.objects.mobs.Player;
import smellychiz.projects.ogc.objects.mobs.agressive.Pig;
import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.IO;
import smellychiz.projects.ogc.util.NumText;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;
import android.content.SharedPreferences;

public class OldLevel extends Level {

	boolean demo = false;

	// Quadtree quad;
	public FinishMenu finish;
	int platformNum = -1;

	public NumText cCoins;

	Camera2D keyCam;

	// GameObject game;

	public int slain = 0, collected = 0;

	public OldLevel(Context context, Vector3 v, Camera2D c, float ratio,
			Camera2D keyCam) {
		super(context, v, c, ratio);

		if (demo) {
			initTest();
			ready = true;

		} else {

			if (IO.downloadLevel(context)) {

				IO.loadLevel(this, mContext);
				// p = new Seed(mContext, new Vector3(2, 2, 10, 10), cam);
				// projectiles.add(p);

				ready = true;
			} else {
				init();
				ready = true;
			}
		}
		this.keyCam = keyCam;
		cCoins = new NumText(Level.coinTotal + collected, 4, context,
				new Vector3(5, 8.5f, 1.2f, .7f), keyCam);
		// poof = new GameObject(mContext, player.bound, c,
		// Assets.poof.getKeyFrame(1, Animation.ANIMATION_LOOPING));
		// game = new GameObject(mContext, new Vector3(player.bound.getX(),
		// player.bound.getY(), 10, 10), c, Assets.poof.getKeyFrame(1,
		// Animation.ANIMATION_LOOPING));
		// quad = new Quadtree(0, new Vector3(0, 0, this.getWidth(),
		// this.getHeight()));
		if (fSign == null)
			fSign = new Sign(mContext, new Vector3(999, -999), cam, false);
	}

	// GameObject poof;

	public OldLevel(Context context, Vector3 v, Camera2D c, float ratio,
			String name, Camera2D keyCam, String folder) {
		super(context, v, c, ratio);
		System.out.println("***" + folder + "/" + name);
		if (IO.loadLevel(this, folder, name, mContext)) {
			ready = true;
		} else {
			init();
			ready = true;
		}
		this.keyCam = keyCam;
		cCoins = new NumText(Level.coinTotal + collected, 4, context,
				new Vector3(5, 8.5f, 1.2f, .7f), keyCam);
		if (fSign == null)
			fSign = new Sign(mContext, new Vector3(999, 999), cam, false);
		// poof = new GameObject(mContext, player.bound, c,
		// Assets.poof.getKeyFrame(1, Animation.ANIMATION_LOOPING));
		// game = new GameObject(mContext, new Vector3(player.bound.getX(),
		// player.bound.getY(), 10, 10), c, Assets.poof.getKeyFrame(1,
		// Animation.ANIMATION_LOOPING));
		// quad = new Quadtree(0, new Vector3(0, 0, this.getWidth(),
		// this.getHeight()));
	}

	public void createEnemies() {

		for (int i = 1; i < platforms.size(); i++) {
			enemies.add(new Pig(mContext, platforms.get(i), cam, r1, i));
		}
	}

	@Override
	public void draw() {
		super.draw();

		// poof.draw();
		// game.draw();
		// cCoins.draw();
	}

	public void init() {

		platforms.add(new Platform(mContext, new Vector3(0, 0, 30, 0), cam));
		platforms.add(new Platform(mContext, new Vector3(36, 0, 30, 0), cam));
		platforms.add(new Platform(mContext, new Vector3(70, 0, 30, 0), cam));
		platforms.add(new Platform(mContext, new Vector3(40, 10, 25, 0), cam));
		platforms.add(new Platform(mContext, new Vector3(60, 20, 30, 0), cam));
		platforms.add(new Platform(mContext, new Vector3(90, 30, 30, 0), cam));
		platforms.add(new Platform(mContext, new Vector3(100, 40, 30, 0), cam));
		platforms.add(new Platform(mContext, new Vector3(110, 50, 30, 0), cam));
		platforms.add(new Platform(mContext, new Vector3(130, 45, 30, 0), cam));
		platforms.add(new Platform(mContext, new Vector3(150, 40, 30, 0), cam));
		platforms.add(new Platform(mContext, new Vector3(170, 50, 60, 0), cam));
		platforms
				.add(new Platform(mContext, new Vector3(140, 0, 1200, 0), cam));
		addCoin(20, 2);
		addCoin(30, 6);
		addCoin(40, 10);
		createEnemies();
		spawn = new Vector3(2, 2);
		player = new Player(mContext, new Vector3(2, 8), cam);
		setWidth(9000);
		setHeight(9000);
	}

	public void initTest() {

		platforms.add(new Platform(mContext, new Vector3(5, 0, 20, 0), cam));
		platforms.add(new Platform(mContext, new Vector3(25, 5, 20, 0), cam));
		spawn = new Vector3(10, 3.5f);
		player = new Player(mContext, new Vector3(10, 3.5f), cam);

		player.xSpeed = .05f;

		setWidth(9000);
		setHeight(9000);
	}

	public boolean bShop = false;

	@Override
	public void update(float delta) {
		platformNum = -1;
		// quad.clear();
		// quad.insert(player);
		// quad.insert(new GameObject(mContext, cam.getPos(), cam));

		//
		// for (int i = 0; i < projectiles.size(); i++) {
		// if (!projectiles.get(i).update(delta, projectiles)) {
		// quad.insert(projectiles.get(i));
		// }
		// }
		//
		// for (int i = 0; i < signs.size(); i++) {
		// quad.insert(signs.get(i));
		// }
		//
		// for(int i = 0; i <)

		// List<GameObject> returnObj = new ArrayList<GameObject>();
		//
		// quad.retrieve(returnObj, player);
		//
		// for (int x = 0; x < returnObj.size(); x++) {
		// switch (returnObj.get(x).type) {
		// case GameObject.COIN:
		// if (
		// // player.contains(
		// // returnObj.get(x).bound.getX()
		// // + (returnObj.get(x).bound.getWidth() / 2),
		// // returnObj.get(x).bound.getY()
		// // + (returnObj.get(x).bound.getHeight() / 2))
		// player.collidesWith(returnObj.get(x).bound)) {
		// Assets.sCoin.seekTo(0);
		// Assets.sCoin.start();
		// coins.remove(returnObj.get(x));
		// returnObj.remove(x);
		// cCoins.addCoin();
		//
		// }
		// break;
		// case GameObject.FINISH_SIGN:
		// if (player.collidesWith(returnObj.get(x).bound)) {
		// System.out.println("done");
		// }
		// break;
		// case GameObject.PROJECTILE:
		// if (!returnObj.get(x).good) {
		// if (player.collidesWith(returnObj.get(x).bound)
		// && player.vulnerable()) {
		// player.hit(returnObj.get(x).getDmg(),
		// returnObj.get(x).bound.isFlipped());
		//
		// }
		// }
		// }
		//
		// }

		boolean cUpdate = false;
		for (int i = 0; i < coins.size(); i++) {
			coins.get(i).update(delta);
			if (player.collidesWith(coins.get(i).bound)) {

				Assets.sCoin.seekTo(0);
				Assets.sCoin.start();
				coins.remove(i);
				cCoins.addCoin();
				collected++;
				coinTotal++;

				cUpdate = true;
			}
		}
		if (cUpdate) {
			cCoins.update(keyCam);
			cUpdate = false;
		}
		for (int i = 0; i < platforms.size(); i++) {

			if ((player.bound.getRealX() + (player.bound.getRealWidth() / 2) >= platforms
					.get(i).pos.getX()

			&&

			player.bound.getRealX() + (player.bound.getRealWidth() / 4) <= platforms
					.get(i).pos.getX() + platforms.get(i).pos.getWidth())

					&& (player.bound.getY() >= platforms.get(i).pos.getY() && (player.bound
							.getY() <= platforms.get(i).pos.getY()
							+ platforms.get(i).pos.getHeight() - indentY || player.bound
							.getY() + player.dY <= platforms.get(i).pos.getY()
							+ platforms.get(i).pos.getHeight() - indentY))) {
				platformNum = i;
				player.onGround = true;
				player.bound.setY(platforms.get(i).pos.getY()
						+ platforms.get(i).pos.getHeight() - indentY);
				break;
			} else
				player.onGround = false;

		}

		if (enemies.size() > 0) {
			for (int i = 0; i < enemies.size(); i++) {
				if (!enemies.get(i).update(delta, enemies, platformNum, player,
						this)) {
					for (int l = 0; l < enemies.get(i).getCoins(); l++) {
						addCoin((int) enemies.get(i).bound.getX() + l % 2,
								(int) enemies.get(i).bound.getY() + l % 5);
					}
					enemies.get(i).dispose();
					enemies.remove(i);
					slain++;
				}
				// if (enemies.get(i)
				// .update(delta, enemies, platformNum, player, this)) {
				// if (player.vulnerable()) {
				// if (enemies.get(i).collidesWith(player.bound)) {
				// System.out.println("hit");
				// player.hit(enemies.get(i).getDmg());
				//
				// }
				// }
				// }
			}
		}
		if (bosses.size() > 0) {
			for (int i = 0; i < bosses.size(); i++) {
				if (!bosses.get(i).update(delta, bosses, platformNum, player,
						this)) {
					for (int l = 0; l < bosses.get(i).getCoins(); l++) {
						addCoin((int) bosses.get(i).bound.getX() + l % 2,
								(int) bosses.get(i).bound.getY() + l % 5);
					}
					if (bosses.size() == 1) {
						fSign.bound.setX(bosses.get(i).bound.getX());
						fSign.bound.setY(bosses.get(i).bound.getY());
						fSign.initVertices();

					}
					bosses.get(i).dispose();
					bosses.remove(i);
					slain++;
				}
			}
		} else if (player.collidesWith(fSign.bound)) {
			endLevel();

		}

		for (int i = 0; i < projectiles.size(); i++) {

			if (!projectiles.get(i).update(delta, projectiles)) {
				if (projectiles.get(i).good) {
					boolean skip = false;
					for (int m = 0; m < enemies.size(); m++) {
						if (enemies.get(m).collidesWith(
								projectiles.get(i).bound)) {
							enemies.get(m).damage(
									projectiles.get(i).getDamage());
							projectiles.get(i).dispose();
							projectiles.set(i, null);
							projectiles.remove(i);
							skip = true;
							break;
						}
					}
					if (!skip) {
						for (int m = 0; m < bosses.size(); m++) {
							if (bosses.get(m).collidesWith(
									projectiles.get(i).bound)) {
								bosses.get(m).damage(
										projectiles.get(i).getDamage());
								projectiles.get(i).dispose();
								projectiles.set(i, null);
								projectiles.remove(i);

								break;
							}
						}
					}

				} else if (!projectiles.get(i).good) {
					if (player.collidesWith(projectiles.get(i).bound)
							&& player.vulnerable()) {
						player.hit(projectiles.get(i).getDmg(),
								projectiles.get(i).bound.isFlipped());

					}
				}
			} else {
				projectiles.get(i).dispose();
				projectiles.set(i, null);
				projectiles.remove(i);
			}
		}
		// System.out.println(projectiles.size() + " size");

		if (player.bound.getY() < -30 || player.isDead()) {
			player.restoreHealth();
			player.dY = 0;
			player.bound.setX(spawn.getX());
			player.bound.setY(spawn.getY());

		}

		player.update(delta, this);
	}

	public long start;
	public long sPause;
	public long ePause;
	public long end;
	public long result;
	double seconds;

	public void endLevel() {
		SharedPreferences s = mContext.getSharedPreferences(IO.key,
				Context.MODE_PRIVATE);
		end = System.nanoTime();
		result = end - start;
		seconds = (double) result / 1000000000.0;
		DecimalFormat df = new DecimalFormat("#.##");
		System.out.println(df.format(seconds) + " SECONDS");
		finish.create(Double.parseDouble(df.format(seconds)), collected, slain,
				mContext, keyCam);
		bShop = true;
		SharedPreferences.Editor editor = s.edit();
		editor.putInt(coinKey, coinTotal);
		editor.commit();
	}

	@Override
	public String folder() {
		// TODO Auto-generated method stub
		return "map1";
	}

}
