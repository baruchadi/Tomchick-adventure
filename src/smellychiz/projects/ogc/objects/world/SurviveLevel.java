package smellychiz.projects.ogc.objects.world;

import java.util.LinkedList;

import smellychiz.projects.ogc.objects.mobs.agressive.Enemy;
import smellychiz.projects.ogc.objects.mobs.agressive.Pig;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public class SurviveLevel extends OldLevel {

	boolean wave = false;

	public SurviveLevel(Context context, Vector3 v, Camera2D c, float ratio,
			String name, Camera2D keyCam, String st) {
		super(context, v, c, ratio, name, keyCam, st);
		if (st == "wave") {
			wave = true;
		}
		respawn();
	}

	@Override
	public String folder() {
		// TODO Auto-generated method stub
		return "survival";
	}

	int p = 1, h = 1, n = 1, cc = 1;

	int l = 0;

	public void respawn() {
		enemies.clear();
		enemies = null;
		enemies = new LinkedList<Enemy>();
		for (int i = 0; i < n; i++) {
			if (wave)
				addWaveEnemy(p, h, cc);
			else
				addEnemy(p, h, cc);
		}
		spawnPlayer();

		switch (l % 3) {
		case 0:
			h++;
			break;
		case 1:
			n++;
			break;
		case 2:
			p++;
			break;
		}
		l++;
		cc = (p + h + n) / 3;

	}

	public void create(Camera2D camera, Context contx) {
		finish.create(this.seconds, collected, slain, contx, camera);
	}

	public void addEnemy(int power, int health, int coins) {
		int platform = (int) (Math.random() * (platforms.size() - 1)) + 1;
		enemies.add(new Pig(mContext, platforms.get(platform), cam, r1,
				platform, coins, power, health));
	}

	public void addWaveEnemy(int power, int health, int coins) {
		int platform = 0;
		enemies.add(new Pig(mContext, platforms.get(platform), cam, r1,
				platform, coins, power, health));
	}
}
