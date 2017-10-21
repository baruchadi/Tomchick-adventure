package smellychiz.projects.ogc.objects.mobs.agressive;

import smellychiz.projects.ogc.objects.GameObject;
import smellychiz.projects.ogc.objects.mobs.Player;
import smellychiz.projects.ogc.objects.world.Level;
import smellychiz.projects.ogc.objects.world.OldLevel;
import smellychiz.projects.ogc.objects.world.Platform;
import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.graphics.Animation;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public class Pig extends Enemy {

	public static float MHP = 5;

	public GameObject legs, body;

	public static final float width = 4, height = 4;

	float range = 1f;

	public Pig(Context context, Platform p, Camera2D c, float ratio, int pNum) {
		super(context, p, c, ratio, MHP, width, height, pNum);

		legs = new GameObject(context, bound, c, Assets.pigLegs.getKeyFrame(
				stateTime, Animation.ANIMATION_LOOPING));
		body = new GameObject(context, bound, c, Assets.pigIdle.getKeyFrame(
				stateTime, Animation.ANIMATION_LOOPING));

		this.setTexture(Assets.tPig);
		this.setTextureArea(Assets.pigIdle.getKeyFrame(0,
				Animation.ANIMATION_LOOPING));
		initVertices();
	}

	public Pig(Context context, Platform p, Camera2D c, float ratio, int pNum,
			int coins) {
		super(context, p, c, ratio, MHP, width, height, pNum);

		legs = new GameObject(context, bound, c, Assets.pigLegs.getKeyFrame(
				stateTime, Animation.ANIMATION_LOOPING));
		body = new GameObject(context, bound, c, Assets.pigIdle.getKeyFrame(
				stateTime, Animation.ANIMATION_LOOPING));

		this.coins = coins;

		this.setTexture(Assets.tPig);
		this.setTextureArea(Assets.pigIdle.getKeyFrame(0,
				Animation.ANIMATION_LOOPING));
		initVertices();
	}

	public Pig(Context context, Platform p, Camera2D c, float ratio, int pNum,
			int coins, int dmg, int health) {
		super(context, p, c, ratio, MHP, width, height, pNum);

		legs = new GameObject(context, bound, c, Assets.pigLegs.getKeyFrame(
				stateTime, Animation.ANIMATION_LOOPING));
		body = new GameObject(context, bound, c, Assets.pigIdle.getKeyFrame(
				stateTime, Animation.ANIMATION_LOOPING));

		this.coins = coins;
		this.dmg = dmg;
		this.MAX_HEALTH = health;
		this.health = health;
		this.setTexture(Assets.tPig);
		this.setTextureArea(Assets.pigIdle.getKeyFrame(0,
				Animation.ANIMATION_LOOPING));
		initVertices();
	}

	@Override
	public void attack(Level l) {
		body.setTextureArea(Assets.pigAtt.getKeyFrame(stateTime,
				Animation.ANIMATION_NONLOOPING));
		legs.setTextureArea(Assets.pigLegs.getKeyFrame(3,
				Animation.ANIMATION_LOOPING));
		stateTime++;
		if (Assets.pigAtt.isAnimationOver(stateTime)) {
			l.AddPigAttack(
					new Vector3(this.bound.getRealX(), this.bound.getY()),
					this.bound.isFlipped());
			close = false;
		}
	}

	@Override
	public void charge(Player p, OldLevel lvl) {
		if (!close) {
			if (p.bound.getRealX() > this.bound.getRealX() + width - range) {
				this.direction = RIGHT;
				close = false;
			} else if (p.bound.getRealX() + p.bound.getRealWidth() < this.bound
					.getRealX() + range) {
				this.direction = LEFT;
				close = false;
			} else {
				direction = IDLE;
				stateTime = 0;
				close = true;
			}
		} else {
			attack(lvl);
		}

	}

	@Override
	public void draw() {

		super.drawHealth();
		if (direction != IDLE || close)
			legs.draw();
		body.draw();
	}

	@Override
	public float HEIGHT() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public void midUpdate(float time) {
		super.midUpdate(time);

		if (!close) {
			if (direction == IDLE)
				body.setTextureArea(Assets.pigIdle.getKeyFrame(stateTime,
						Animation.ANIMATION_LOOPING));
			else if (direction == LEFT || direction == RIGHT) {
				legs.setTextureArea(Assets.pigLegs.getKeyFrame(stateTime,
						Animation.ANIMATION_LOOPING));
				body.setTextureArea(Assets.idle_body);

			}
		}

	}

	@Override
	public void afterUpdate(float time) {
		// TODO Auto-generated method stub
		super.afterUpdate(time);
		legs.initVertices(bound);
		body.initVertices(bound);
	}

	@Override
	public float WIDTH() {
		// TODO Auto-generated method stub
		return 4;
	}
}
