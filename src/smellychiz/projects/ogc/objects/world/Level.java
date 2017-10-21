package smellychiz.projects.ogc.objects.world;

import java.util.LinkedList;
import java.util.List;

import smellychiz.projects.ogc.objects.Coin;
import smellychiz.projects.ogc.objects.Projectile;
import smellychiz.projects.ogc.objects.mobs.Player;
import smellychiz.projects.ogc.objects.mobs.agressive.BossPig;
import smellychiz.projects.ogc.objects.mobs.agressive.Enemy;
import smellychiz.projects.ogc.objects.mobs.agressive.MinionPig;
import smellychiz.projects.ogc.objects.mobs.agressive.Pig;
import smellychiz.projects.ogc.objects.projectiles.PigAttack;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.IO;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;
import android.content.SharedPreferences;

public abstract class Level {
	public Player player = null;
	public List<Platform> platforms;
	public List<Enemy> enemies;
	public List<Enemy> bosses;
	public List<Projectile> projectiles;
	public List<Coin> coins;
	public List<Sign> signs;
	public Sign fSign = null;
	Context mContext;

	Camera2D cam;

	float r1;

	Vector3 spawn;

	public boolean ready = false;

	float width, height;

	public static float indentY = .3f;

	public static int coinTotal;

	String coinKey = "coinKey";

	public Level(Context context, Vector3 v, Camera2D c, float ratio) {
		coinTotal = 0;
		SharedPreferences s = context.getSharedPreferences(IO.key,
				Context.MODE_PRIVATE);
		coinTotal = s.getInt(coinKey, 0);

		platforms = new LinkedList<Platform>();
		enemies = new LinkedList<Enemy>();
		projectiles = new LinkedList<Projectile>();
		coins = new LinkedList<Coin>();
		signs = new LinkedList<Sign>();
		bosses = new LinkedList<Enemy>();

		mContext = context;

		cam = c;
		r1 = ratio;

	}

	public void addCoin(int x, int y) {
		coins.add(new Coin(mContext, new Vector3(x, y), cam));
	}

	public void addMinion(int type, int i) {
		switch (type) {
		default:
			enemies.add(new MinionPig(mContext, platforms.get(i), cam, r1, i));
		}
	}

	public void addEnemy(int type, int i) {
		switch (type) {
		default:
			enemies.add(new Pig(mContext, platforms.get(i), cam, r1, i));
		}
	}

	public void addBoss(int type, int i) {
		switch (type) {
		default:
			bosses.add(new BossPig(mContext, platforms.get(i), cam, r1, i));
		}
	}

	public void AddPigAttack(Vector3 pos, boolean flip) {
		projectiles.add(new PigAttack(mContext, pos, cam, 0, flip));

	}

	public void addPlatform(float x, float y, int width) {
		platforms.add(new Platform(mContext, new Vector3(x, y, width, 0), cam));
	}

	public void addSign(float x, float y, int type) {
		System.out.println("Sign");
		signs.add(new Sign(mContext, new Vector3(x, y), cam, type));
	}

	public void addFSign(int x, int y) {
		System.out.println("fSign");
		fSign = new Sign(mContext, new Vector3(x, y), cam, true);

	}

	public void addPlatform(Platform p) {
		platforms.add(p);
	}

	public void addProjectile(Projectile p) {
		projectiles.add(p);
	}

	public void draw() {

		for (int i = 0; i < coins.size(); i++) {
			if (coins.get(i).collidesWith(cam.getPos()))
				coins.get(i).draw();
		}
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).collidesWith(cam.getPos()))
				projectiles.get(i).draw();
		}
		for (int i = 0; i < signs.size(); i++) {

			signs.get(i).draw();
		}
		for (int i = 0; i < platforms.size(); i++) {
			if (platforms.get(i).collidesWith(cam.getPos()))
				platforms.get(i).draw(cam);
		}
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).collidesWith(cam.getPos()))
				enemies.get(i).draw();
		}
		for (int i = 0; i < bosses.size(); i++) {
			if (bosses.get(i).collidesWith(cam.getPos()))
				bosses.get(i).draw();
		}

		if (player != null)
			player.draw();

	}

	public float getHeight() {
		return height;
	}

	public float getWidth() {
		return width;
	}

	public void setHeight(float h) {
		this.height = h;
	}

	public void setWidth(float w) {
		this.width = w;
	}

	public void spawnPlayer() {
		player.bound.setX(spawn.getX());
		player.bound.setY(spawn.getY());
	}

	public void spawnPlayer(float x, float y) {
		spawn = new Vector3(x, y, 0, 0);
		player = new Player(mContext, new Vector3(x, y), cam);
	}

	public abstract void update(float delta);

	public abstract String folder();

}
