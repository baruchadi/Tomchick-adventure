package smellychiz.projects.ogc.objects.mobs.agressive;

import java.util.List;

import smellychiz.projects.ogc.objects.GameObject;
import smellychiz.projects.ogc.objects.mobs.Player;
import smellychiz.projects.ogc.objects.world.OldLevel;
import smellychiz.projects.ogc.objects.world.Platform;
import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.graphics.Animation;
import android.content.Context;

public class MinionPig extends Pig {

	GameObject poof;
	int poofTime = 0;

	public MinionPig(Context context, Platform p, Camera2D c, float ratio,
			int pNum) {
		super(context, p, c, ratio, pNum);
		poof = new GameObject(mContext, this.bound, camera,
				Assets.poof.getKeyFrame(0, Animation.ANIMATION_NONLOOPING));
		this.bound.setWidth(this.bound.getWidth() / 2);
		this.bound.setHeight(this.bound.getHeight() / 2);
		this.MAX_HEALTH = 2;
		this.health = 2;
		this.initVertices();
		poof.initVertices();

	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		super.draw();
		if (!Assets.poof.isAnimationOver(poofTime))
			poof.draw();
	}

	@Override
	public void midUpdate(float time) {
		// TODO Auto-generated method stub
		if (!Assets.poof.isAnimationOver(stateTime)) {
			poof.setTextureArea(Assets.poof.getKeyFrame(poofTime,
					Animation.ANIMATION_NONLOOPING));
			poof.initVertices();
			poofTime += time;
		}

		super.midUpdate(time);
	}

	@Override
	public boolean update(float time, List<Enemy> enemies, int pNum, Player p,
			OldLevel lvl) {

		return super.update(time, enemies, pNum, p, lvl);
	}

}
