package smellychiz.projects.ogc.objects.mobs.agressive;

import smellychiz.projects.ogc.objects.mobs.Player;
import smellychiz.projects.ogc.objects.world.Level;
import smellychiz.projects.ogc.objects.world.OldLevel;
import smellychiz.projects.ogc.objects.world.Platform;
import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.graphics.Animation;
import android.content.Context;

public class EnemyTom extends Enemy {

	public static final float MHP = 5;

	public EnemyTom(Context context, Platform p, Camera2D c, float ratio,
			int pNum) {
		super(context, p, c, ratio, MHP, 3, 3, pNum);
		this.setTexture(Assets.tom1);
		this.setTextureArea(Assets.PlayerIdel);
		initVertices();
	}

	@Override
	public void attack(Level l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void charge(Player p, OldLevel lvl) {
		// TODO Auto-generated method stub

	}

	@Override
	public float HEIGHT() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public void midUpdate(float time) {
		// TODO Auto-generated method stub
		super.midUpdate(time);
		if (direction == IDLE)
			this.setTextureArea(Assets.tomIdle.getKeyFrame(stateTime,
					Animation.ANIMATION_LOOPING));
		else if (direction == LEFT || direction == RIGHT)
			this.setTextureArea(Assets.tomAnim.getKeyFrame(stateTime,
					Animation.ANIMATION_LOOPING));
	}

	@Override
	public float WIDTH() {
		// TODO Auto-generated method stub
		return 3;
	}

}
