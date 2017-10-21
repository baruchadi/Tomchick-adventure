package smellychiz.projects.ogc.objects;

import smellychiz.projects.ogc.objects.mobs.Player;
import smellychiz.projects.ogc.objects.world.Level;
import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.IO;
import smellychiz.projects.ogc.util.NumText;
import smellychiz.projects.ogc.util.graphics.Animation;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;
import android.content.SharedPreferences;

public class StatsMenu {

	int[] pricing = { 50, 100, 250, 500, 1000 };
	int[] levels = { 0, 0, 0, 0, 0, 0 };
	String[] ids = { "health", "range", "fRate", "tomLife", "burstAmmo", "dmg" };
	final int PAGE1 = 0;
	final int fRate = 2, dmg = 5, range = 1, burstAmmo = 4, health = 0,
			tomLife = 3;
	int page = 0;
	int oldPage = 0;
	final int pages = 2;

	GameObject statsbg, stats1;
	GameObject lArrow, rArrow;
	Button[] b;
	NumText[] prices, lvlNum;
	GameObject[] coins, lvl;

	public void fixStats(Player p) {
		for (int i = 0; i < levels[0]; i++) {
			p.upHealth();
		}
		for (int i = 0; i < levels[1]; i++) {
			p.upRange();
		}
		for (int i = 0; i < levels[2]; i++) {
			p.upFireRate();
		}
		for (int i = 0; i < levels[3]; i++) {
			p.upLife();
		}
		for (int i = 0; i < levels[4]; i++) {
			p.upAmmo();
		}
		for (int i = 0; i < levels[5]; i++) {
			p.upDmg();
		}
	}

	public StatsMenu(Context c, Camera2D cam, Player player) {

		statsbg = new GameObject(c, new Vector3(3f, 1, cam.pos.getWidth() - 6,
				cam.pos.getHeight() - 2), cam, Assets.BGstats);
		stats1 = new GameObject(c, new Vector3(3f, 1, cam.pos.getWidth() - 6,
				cam.pos.getHeight() - 2), cam, Assets.stats[page]);

		lArrow = new GameObject(c, new Vector3(stats1.bound.getX() - 2,
				stats1.bound.getY(), 2, 3), cam, Assets.arrow);
		rArrow = new GameObject(c, new Vector3(stats1.bound.getX()
				+ stats1.bound.getWidth(), stats1.bound.getY(), 2, 3), cam,
				Assets.arrow);
		lArrow.bound.setFlipped(true);
		lArrow.initVertices();
		b = new Button[3];
		coins = new GameObject[3];
		lvl = new GameObject[3];
		for (int i = 0; i < 3; i++) {
			b[i] = new Button(c,
					new Vector3(1.25f + stats1.bound.getX()
							+ ((stats1.bound.getWidth() / 4) * 3), .8f
							+ stats1.bound.getY()

							+ (i * ((stats1.bound.getHeight() - 1.2f) / 3)),
							.85f, .85f), cam, Assets.plus, Assets.pPlus);
			coins[i] = new GameObject(c,
					new Vector3(-2f + stats1.bound.getX()
							+ ((stats1.bound.getWidth() / 4) * 3), .8f + .8f
							+ stats1.bound.getY()

							+ (i * ((stats1.bound.getHeight() - 1.2f) / 3)),
							.75f, .75f), cam, Assets.coin.getKeyFrame(1,
							Animation.ANIMATION_LOOPING));
			lvl[i] = new GameObject(c, new Vector3(-2f + stats1.bound.getX()
					+ ((stats1.bound.getWidth() / 4) * 3), .8f
					+ stats1.bound.getY()

					+ (i * ((stats1.bound.getHeight() - 1.2f) / 3)), 1.25f,
					.75f), cam, Assets.lvlLabel);
		}
		SharedPreferences p = c.getSharedPreferences(IO.key,
				Context.MODE_PRIVATE);

		for (int i = 0; i < levels.length; i++) {
			levels[i] = p.getInt(ids[i], 0);
		}
		prices = new NumText[6];
		lvlNum = new NumText[6];
		for (int i = 0; i < ids.length; i++) {
			levels[i] = p.getInt(ids[i], 0);
			prices[i] = new NumText("" + pricing[levels[i]], pricing[levels[i]]
					+ "", c, new Vector3(.2f + coins[i % 3].bound.getX()
					+ coins[i % 3].bound.getWidth(), coins[i % 3].bound.getY(),
					b[i % 3].bound.getX()
							- (coins[i % 3].bound.getX() + coins[i % 3].bound
									.getWidth()), coins[0].bound.getHeight()),
					cam, false, 0.4f);
			lvlNum[i] = new NumText("" + (levels[i] + 1), (levels[i] + 1) + "",
					c,
					new Vector3(.2f + lvl[i % 3].bound.getX()
							+ lvl[i % 3].bound.getWidth(), lvl[i % 3].bound
							.getY(), b[i % 3].bound.getX()
							- (lvl[i % 3].bound.getX() + lvl[i % 3].bound
									.getWidth()), lvl[0].bound.getHeight()),
					cam, false, 0.4f);
		}

		fixStats(player);

	}

	public void saveStats(Context mContext) {
		SharedPreferences s = mContext.getSharedPreferences(IO.key,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = s.edit();
		for (int i = 0; i < ids.length; i++) {
			editor.putInt(ids[i], levels[i]);
		}
		editor.commit();

	}

	public void draw() {
		if (oldPage != page) {
			stats1.setTextureArea(Assets.stats[page]);
			stats1.initVertices();
			oldPage = page;
		}
		statsbg.draw();
		stats1.draw();
		for (int i = 0; i < b.length; i++) {
			b[i].update();
			b[i].draw();
			coins[i].draw();
			if ((i) + (3 * page) != tomLife && (i) + (3 * page) != burstAmmo)
				lvl[i].draw();
			prices[i + (page * 3)].draw();
			lvlNum[i + (page * 3)].draw();
		}
		if (page < pages - 1) {
			rArrow.draw();
		}
		if (page > 0) {
			lArrow.draw();
		}

	}

	public boolean onTouch(float x, float y, Player p, int money, Context c,
			Camera2D cam) {
		if (statsbg.contains(x, y)) {
			for (int i = 0; i < b.length; i++) {
				if (b[i].contains(x, y)) {
					System.out.println("*************");
					System.out.println(i);
					int n = i + (3 * page);
					System.out.println(n);
					switch (n) {
					case fRate:
						if (money > pricing[levels[fRate]]) {
							p.upFireRate();
							Level.coinTotal -= pricing[levels[fRate]];
							levels[fRate]++;
							lvlNum[fRate] = new NumText(
									"" + (levels[fRate] + 1),
									(levels[fRate] + 1) + "",
									c,
									new Vector3(
											.2f
													+ lvl[fRate % 3].bound
															.getX()
													+ lvl[fRate % 3].bound
															.getWidth(),
											lvl[fRate % 3].bound.getY(),
											b[fRate % 3].bound.getX()
													- (lvl[fRate % 3].bound
															.getX() + lvl[fRate % 3].bound
															.getWidth()),
											lvl[0].bound.getHeight()), cam,
									false, 0.4f);
							prices[fRate] = new NumText(
									"" + pricing[levels[fRate]],
									pricing[levels[fRate]] + "",
									c,
									new Vector3(
											.2f
													+ coins[fRate % 3].bound
															.getX()
													+ coins[fRate % 3].bound
															.getWidth(),
											coins[fRate % 3].bound.getY(),
											b[fRate % 3].bound.getX()
													- (coins[fRate % 3].bound
															.getX() + coins[fRate % 3].bound
															.getWidth()),
											coins[0].bound.getHeight()), cam,
									false, 0.4f);
						}

						System.out.println("fRate");
						break;
					case dmg:
						if (money > pricing[levels[dmg]]) {
							p.upDmg();
							Level.coinTotal -= pricing[levels[dmg]];
							levels[dmg]++;
							lvlNum[dmg] = new NumText(
									"" + (levels[dmg] + 1),
									(levels[dmg] + 1) + "",
									c,
									new Vector3(
											.2f
													+ lvl[dmg % 3].bound.getX()
													+ lvl[dmg % 3].bound
															.getWidth(),
											lvl[dmg % 3].bound.getY(),
											b[dmg % 3].bound.getX()
													- (lvl[dmg % 3].bound
															.getX() + lvl[dmg % 3].bound
															.getWidth()),
											lvl[0].bound.getHeight()), cam,
									false, 0.4f);
							prices[dmg] = new NumText(
									"" + pricing[levels[dmg]],
									pricing[levels[dmg]] + "",
									c,
									new Vector3(
											.2f
													+ coins[dmg % 3].bound
															.getX()
													+ coins[dmg % 3].bound
															.getWidth(),
											coins[dmg % 3].bound.getY(),
											b[dmg % 3].bound.getX()
													- (coins[i % 3].bound
															.getX() + coins[dmg % 3].bound
															.getWidth()),
											coins[0].bound.getHeight()), cam,
									false, 0.4f);
						}
						System.out.println("dmg");
						break;
					case range:
						if (money > pricing[levels[range]]) {
							p.upRange();
							Level.coinTotal -= pricing[levels[range]];
							levels[range]++;
							lvlNum[range] = new NumText(
									"" + (levels[range] + 1),
									(levels[range] + 1) + "",
									c,
									new Vector3(
											.2f
													+ lvl[range % 3].bound
															.getX()
													+ lvl[range % 3].bound
															.getWidth(),
											lvl[range % 3].bound.getY(),
											b[range % 3].bound.getX()
													- (lvl[range % 3].bound
															.getX() + lvl[range % 3].bound
															.getWidth()),
											lvl[0].bound.getHeight()), cam,
									false, 0.4f);
							prices[range] = new NumText(
									"" + pricing[levels[range]],
									pricing[levels[range]] + "",
									c,
									new Vector3(
											.2f
													+ coins[range % 3].bound
															.getX()
													+ coins[range % 3].bound
															.getWidth(),
											coins[range % 3].bound.getY(),
											b[range % 3].bound.getX()
													- (coins[range % 3].bound
															.getX() + coins[range % 3].bound
															.getWidth()),
											coins[0].bound.getHeight()), cam,
									false, 0.4f);
						}
						System.out.println("range");
						break;
					case burstAmmo:
						if (money > pricing[levels[burstAmmo]]) {
							p.upAmmo();
							Level.coinTotal -= pricing[levels[burstAmmo]];
							levels[burstAmmo]++;
							lvlNum[burstAmmo] = new NumText(
									"" + (levels[burstAmmo] + 1),
									(levels[burstAmmo] + 1) + "",
									c,
									new Vector3(
											.2f
													+ lvl[burstAmmo % 3].bound
															.getX()
													+ lvl[burstAmmo % 3].bound
															.getWidth(),
											lvl[burstAmmo % 3].bound.getY(),
											b[burstAmmo % 3].bound.getX()
													- (lvl[burstAmmo % 3].bound
															.getX() + lvl[burstAmmo % 3].bound
															.getWidth()),
											lvl[0].bound.getHeight()), cam,
									false, 0.4f);
							prices[burstAmmo] = new NumText(
									"" + pricing[levels[burstAmmo]],
									pricing[levels[burstAmmo]] + "",
									c,
									new Vector3(
											.2f
													+ coins[burstAmmo % 3].bound
															.getX()
													+ coins[burstAmmo % 3].bound
															.getWidth(),
											coins[burstAmmo % 3].bound.getY(),
											b[burstAmmo % 3].bound.getX()
													- (coins[burstAmmo % 3].bound
															.getX() + coins[burstAmmo % 3].bound
															.getWidth()),
											coins[0].bound.getHeight()), cam,
									false, 0.4f);
						}
						System.out.println("ammo");
						break;
					case health:
						if (money > pricing[levels[health]]) {
							p.upHealth();
							Level.coinTotal -= pricing[levels[health]];
							levels[health]++;
							lvlNum[health] = new NumText(
									"" + (levels[health] + 1),
									(levels[health] + 1) + "",
									c,
									new Vector3(
											.2f
													+ lvl[health % 3].bound
															.getX()
													+ lvl[health % 3].bound
															.getWidth(),
											lvl[health % 3].bound.getY(),
											b[health % 3].bound.getX()
													- (lvl[health % 3].bound
															.getX() + lvl[health % 3].bound
															.getWidth()),
											lvl[0].bound.getHeight()), cam,
									false, 0.4f);
							prices[health] = new NumText(
									"" + pricing[levels[health]],
									pricing[levels[health]] + "",
									c,
									new Vector3(
											.2f
													+ coins[health % 3].bound
															.getX()
													+ coins[health % 3].bound
															.getWidth(),
											coins[health % 3].bound.getY(),
											b[health % 3].bound.getX()
													- (coins[health % 3].bound
															.getX() + coins[health % 3].bound
															.getWidth()),
											coins[0].bound.getHeight()), cam,
									false, 0.4f);
						}
						System.out.println("health");
						break;
					case tomLife:
						if (money > pricing[levels[tomLife]]) {
							p.upLife();
							Level.coinTotal -= pricing[levels[tomLife]];
							levels[tomLife]++;
							lvlNum[tomLife] = new NumText(
									"" + (levels[tomLife] + 1),
									(levels[tomLife] + 1) + "",
									c,
									new Vector3(
											.2f
													+ lvl[tomLife % 3].bound
															.getX()
													+ lvl[tomLife % 3].bound
															.getWidth(),
											lvl[tomLife % 3].bound.getY(),
											b[tomLife % 3].bound.getX()
													- (lvl[tomLife % 3].bound
															.getX() + lvl[tomLife % 3].bound
															.getWidth()),
											lvl[0].bound.getHeight()), cam,
									false, 0.4f);
							prices[tomLife] = new NumText(
									"" + pricing[levels[tomLife]],
									pricing[levels[tomLife]] + "",
									c,
									new Vector3(
											.2f
													+ coins[tomLife % 3].bound
															.getX()
													+ coins[tomLife % 3].bound
															.getWidth(),
											coins[tomLife % 3].bound.getY(),
											b[tomLife % 3].bound.getX()
													- (coins[tomLife % 3].bound
															.getX() + coins[tomLife % 3].bound
															.getWidth()),
											coins[0].bound.getHeight()), cam,
									false, 0.4f);
						}
						System.out.println("life");
						break;
					}

					break;
				}
			}
			return true;
		}
		if (page < pages - 1) {
			if (rArrow.contains(x, y)) {
				page++;

				return true;
			}
		}
		if (page > 0) {
			if (lArrow.contains(x, y)) {
				page--;
				return true;
			}
		}
		return false;
	}
}
