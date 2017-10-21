package smellychiz.projects.ogc.objects.mobs.agressive;

import java.util.List;

import smellychiz.projects.ogc.objects.mobs.Player;
import smellychiz.projects.ogc.objects.world.OldLevel;
import smellychiz.projects.ogc.objects.world.Platform;
import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.graphics.Animation;
import android.content.Context;

public class BossPig extends Pig {

	int h1 = 25;

	public BossPig(Context context, Platform p, Camera2D c, float ratio,
			int pNum, int coins, int dmg, int health) {

		super(context, p, c, ratio, pNum, coins, dmg, health);
		// TODO Auto-generated constructor stub

		this.bound.setWidth(this.bound.getWidth() * 2);
		this.bound.setHeight(this.bound.getHeight() * 2);
		this.MAX_HEALTH = h1;
		this.health = h1;
		updateHealth();
		this.initVertices();
	}

	public BossPig(Context context, Platform p, Camera2D c, float ratio,
			int pNum, int coins) {
		super(context, p, c, ratio, pNum, coins);
		this.bound.setWidth(this.bound.getWidth() * 2);
		this.bound.setHeight(this.bound.getHeight() * 2);
		this.MAX_HEALTH = h1;
		this.health = h1;
		updateHealth();
		this.initVertices();
	}

	public BossPig(Context mContext, Platform platform, Camera2D cam, float r1,
			int i) {
		super(mContext, platform, cam, r1, i);
		this.bound.setWidth(this.bound.getWidth() * 2);
		this.bound.setHeight(this.bound.getHeight() * 2);
		this.MAX_HEALTH = h1;
		this.health = h1;
		updateHealth();
		this.initVertices();
	}

	int summoned = 400;
	int wait = 500;

	float summonTime = 0;
	boolean s = false;

	@Override
	public boolean update(float time, List<Enemy> enemies, int pNum, Player p,
			OldLevel lvl) {
		super.update(time, enemies, pNum, p, lvl);
		// TODO Auto-generated method stub
		summoned += time;
		if (attk && summoned >= wait) {
			s = true;
			if (Assets.pigSummon.isAnimationOver(summonTime)) {

				lvl.addMinion(pNum, platformNum);
				lvl.addMinion(pNum, platformNum);
				lvl.addMinion(pNum, platformNum);
				summoned = 0;
				summonTime = 0;
				s = false;
			}

		}
		return (health > 0);
	}

	@Override
	public void midUpdate(float time) {
		// TODO Auto-generated method stub
		super.midUpdate(time);

		if (s) {
			body.setTextureArea(Assets.pigSummon.getKeyFrame(summonTime,
					Animation.ANIMATION_LOOPING));
			summonTime += time;
			// body.initVertices(bound);
		}
	}

}
