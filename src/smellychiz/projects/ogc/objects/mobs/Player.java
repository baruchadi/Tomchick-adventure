package smellychiz.projects.ogc.objects.mobs;

import java.util.Random;

import smellychiz.projects.ogc.objects.GameObject;
import smellychiz.projects.ogc.objects.Mob;
import smellychiz.projects.ogc.objects.projectiles.Seed;
import smellychiz.projects.ogc.objects.world.Level;
import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.graphics.Animation;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public class Player extends Mob {

	public final static int IDEL = 0;
	public final static int MOVE_RIGHT = 1;
	public final static int MOVE_LEFT = 2;
	public final static int MOVE_UP = 3;
	public final static int MOVE_DOWN = 4;
	public final static float WIDTH = 3f;
	public final static float HEIGHT = 3f;

	private int axis = 0;

	public static int ATT_DELAY = 20;

	public int counter;

	public float xSpeed = .5f;
	public float ySpeed = .3f;
	boolean attacking = false;

	float health;
	static float MAX_HEALTH = 10;

	GameObject eyes;
	// , healthBar;
	// asdfasdf

	public GameObject legs, body;

	// asdfasdf

	float eyesTime = 0;

	int maxDy = -2;

	boolean fell = false;

	Vector3 mX = new Vector3(0, 0);

	float feedBack = 1.3f;

	boolean vulnerable = true;

	float vulnerableTime = 25;

	float vPast = 0;

	public Player(Context context, Vector3 v, Camera2D c) {
		super(context, new Vector3(v.getX(), v.getY(), WIDTH, HEIGHT), c);
		this.type = PLAYER;
		this.setTexture(Assets.tom1);
		this.setTextureArea(Assets.PlayerIdel);
		health = MAX_HEALTH;
		eyes = new GameObject(context, v, c, Assets.tom1);
		// beak = new GameObject(context, v, c, Assets.beak);
		// healthBar = new GameObject(mContext, new Vector3(bound.getX(),
		// bound.getY() + bound.getHeight(), WIDTH, 1), c, Assets.green);
		actX = IDEL;
		actY = IDEL;
		stateTime = 0;
		onGround = true;
		legs = new GameObject(context, bound, c, Assets.pigLegs.getKeyFrame(
				stateTime, Animation.ANIMATION_LOOPING));
		body = new GameObject(context, bound, c, Assets.pigIdle.getKeyFrame(
				stateTime, Animation.ANIMATION_LOOPING));
		r = new Random();
	}

	@Override
	public void afterUpdate(float time) {

		if (vulnerable()) {
			if (onGround) {
				if (this.actX == MOVE_RIGHT) {
					this.setTextureArea(Assets.tomAnim.getKeyFrame(stateTime,
							Animation.ANIMATION_LOOPING));
					// this.setTextureArea(Assets.PlayerIdel);

				} else if (this.actX == MOVE_LEFT) {
					this.setTextureArea(Assets.tomAnim.getKeyFrame(stateTime,
							Animation.ANIMATION_LOOPING));
					// this.setTextureArea(Assets.PlayerIdel);
				} else {
					this.setTextureArea(Assets.tomIdle.getKeyFrame(stateTime,
							Animation.ANIMATION_LOOPING));

				}
			}
		}
		//
		if (actX == IDEL)
			body.setTextureArea(Assets.pigIdle.getKeyFrame(stateTime,
					Animation.ANIMATION_LOOPING));
		else if (actX == MOVE_LEFT || actX == MOVE_RIGHT) {
			legs.setTextureArea(Assets.pigLegs.getKeyFrame(stateTime,
					Animation.ANIMATION_LOOPING));
			body.setTextureArea(Assets.pigAtt.getKeyFrame(stateTime,
					Animation.ANIMATION_LOOPING));

		}

		//
		if (!onGround) {
			// if(bound.getY()<=axis){
			// onGround=true;
			// dY=0;
			// bound.setY(0);
			// }else
			this.bound.addX(mX.getX() * time);
			if (dY > 0) {

				this.setTextureArea(Assets.PlayerJump);
				fell = true;
			} else {
				if (fell) {
					stateTime = 0;
					fell = false;
				}
				this.setTextureArea(Assets.tomFalling.getKeyFrame(stateTime,
						Animation.ANIMATION_NONLOOPING));
				// this.setTextureArea(Assets.PlayerLand);
			}
			if (dY > maxDy)
				dY -= gravity * time;

		} else {
			mX.setX(0);
			dY = 0;

		}

		if (!vulnerable()) {

			eyes.setTextureArea(Assets.eyeRollFull.getKeyFrame(eyesTime,
					Animation.ANIMATION_LOOPING));
			body.setTextureArea(Assets.pig_hit);
			actX = IDEL;

			if (vPast < vulnerableTime)
				this.vPast += time;
			else {
				vPast = 0;
				vulnerable = true;
				eyesTime = 0;
			}

		} else if (eyeCounter < maxeC) {
			eyes.setTextureArea(Assets.fireEyes.getKeyFrame(eyeCounter,
					Animation.ANIMATION_LOOPING));
		}

		this.bound.addX(dX);

		this.bound.addY(dY);

		// if (this.bound.isFlipped())
		// this.healthBar.bound
		// .setX(this.bound.getX() + this.bound.getWidth());
		// else
		// this.healthBar.bound.setX(this.bound.getX());
		// this.healthBar.bound.setY(this.bound.getY() + bound.getHeight());
		this.initVertices();

		this.eyes.initVertices(bound);
		// beak.initVertices(bound);
		// this.healthBar.bound.setFlipped(this.bound.isFlipped());
		// healthBar.initVertices();

		this.stateTime += time;
		this.eyesTime += time;
		counter += time * 1;
		if (RapidAmmo > 0)
			rdelayed += time;
		if (eyeCounter <= maxeC) {
			eyeCounter++;
		}
		legs.initVertices(bound);
		body.initVertices(bound);
		// System.out.println(bound.getX() + ", " + bound.getY());
	}

	int dmg = 1, range = 15;

	public void attack(Level l) {
		Assets.shoot.seekTo(0);
		Assets.shoot.start();
		counter = 0;
		if (this.bound.isFlipped()) {
			l.addProjectile(new Seed(this.mContext, new Vector3(this.bound
					.getX(), this.bound.getY() + (this.bound.getHeight() / 2)),
					this.camera, -1, dmg, range));
		} else {
			l.addProjectile(new Seed(this.mContext, new Vector3(this.bound
					.getX(), this.bound.getY() + (this.bound.getHeight() / 2)),
					this.camera, 1, dmg, range));

		}
	}

	int shot = 6;

	public int numRapid = 5;
	public int RapidAmmo = 10;
	public int eyeCounter = 50;
	int maxeC = 40;
	public int RAPID_DELAY = 200;
	public int rdelayed = 50;

	public boolean rapid = false;

	public void setRapid() {
		System.out.println("rapp");
		if (rdelayed >= RAPID_DELAY && vulnerable() && RapidAmmo > 0) {
			eyeCounter = 0;
			shot = 0;
			RapidAmmo--;
			rdelayed = 0;
			rapid = true;
		} else if (shot >= numRapid)
			rapid = false;
	}

	Random r;

	public void rapidAttack(Level l) {

		if (shot < numRapid) {
			Assets.shoot.seekTo(0);
			Assets.shoot.start();
			counter = 0;
			if (this.bound.isFlipped()) {
				l.addProjectile(new Seed(this.mContext, new Vector3(this.bound
						.getX(), this.bound.getY()
						+ (this.bound.getHeight() / 2)), this.camera, -1, dmg,
						range, r.nextFloat() * 2 - 1));
			} else {
				l.addProjectile(new Seed(this.mContext, new Vector3(this.bound
						.getX(), this.bound.getY()
						+ (this.bound.getHeight() / 2)), this.camera, 1, dmg,
						range, r.nextFloat() * 2 - 1));

			}
			shot++;

		} else {
			rapid = false;
		}
	}

	@Override
	public void beforUpdate(float time) {

		if (vulnerable()) {
			switch (actX) {

			case IDEL:
			default:
				dX = 0;
				break;
			case MOVE_RIGHT:

				dX = xSpeed;
				break;
			case MOVE_LEFT:

				dX = -xSpeed;
				break;

			}

		} else
			dX = 0;
		// switch (actY) {
		//
		// case IDEL:
		// default:
		// dY = 0;
		// break;
		// case MOVE_UP:
		//
		// dY = xSpeed;
		//
		// break;
		// case MOVE_DOWN:
		// dY = -xSpeed;
		//
		// break;
		// }

	}

	public boolean canAttack() {
		System.out.println("c" + counter);
		return (counter >= ATT_DELAY);

	}

	@Override
	public void draw() {
		super.draw();
		if (!vulnerable || (eyeCounter < maxeC))
			eyes.draw();
		// healthBar.draw();
		// if (actX != IDEL)
		// legs.draw();
		// body.draw();DEL)
		// legs.draw();
		// body.draw();DEL)
		// legs.draw();
		// body.draw();DEL)
		// legs.draw();
		// body.draw();DEL)
		// legs.draw();
		// body.draw();
	}

	public float getHealth() {

		return health / MAX_HEALTH;

	}

	public void hit(float dmg, boolean left) {

		this.jump(1f);
		if (left)
			this.mX.setX(-feedBack);
		else
			this.mX.setX(feedBack);
		vulnerable = false;

		this.health -= dmg;
		System.out.println("dmg " + dmg + ", " + health);
		Assets.player_hit.start();
		// healthBar.bound.setWidth((health / MAX_HEALTH) * WIDTH);

	}

	public boolean isDead() {

		return (health <= 0);

	}

	@Override
	public boolean isGravity() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void jump() {
		Assets.mp.start();
		// TODO: Implement this method
		super.jump();
	}

	public void restoreHealth() {
		this.mX.setX(0);
		this.health = MAX_HEALTH;
		// healthBar.bound.setWidth((health / MAX_HEALTH) * WIDTH);
	}

	public void setActionX(int action) {
		if (this.actX != action) {
			setAnimationProp(action);
			this.actX = action;
		}
	}

	public void setActionY(int action) {
		this.actY = action;
	}

	public void setAnimationProp(int action) {
		stateTime = 0;
		if (onGround) {
			if (action == MOVE_RIGHT) {
				this.setTextureArea(Assets.tomAnim.getKeyFrame(stateTime,
						Animation.ANIMATION_LOOPING));
				// this.setTextureArea(Assets.PlayerIdel);
				this.bound.setFlipped(false);

				stateTime = 0;
			} else if (action == MOVE_LEFT) {
				this.setTextureArea(Assets.tomAnim.getKeyFrame(stateTime,
						Animation.ANIMATION_LOOPING));
				// this.setTextureArea(Assets.PlayerIdel);
				this.bound.setFlipped(true);

				stateTime = 0;

			} else if (action == IDEL) {
				this.setTextureArea(Assets.tomIdle.getKeyFrame(stateTime,
						Animation.ANIMATION_LOOPING));
				stateTime = 0;
			}

		} else if (!onGround) {

			if (dY > 0) {

				this.setTextureArea(Assets.PlayerJump);

			} else {
				this.setTextureArea(Assets.PlayerLand);
			}

			if (action == MOVE_LEFT) {
				this.bound.setFlipped(true);
				stateTime = 0;
			} else if (action == MOVE_RIGHT) {
				this.bound.setFlipped(false);
				stateTime = 0;
			}
		}
	}

	public void setAttacking(boolean value) {

		if (value = true) {
			if (counter >= ATT_DELAY)
				this.attacking = value;
			else
				this.attacking = false; // remove these for awesome effects!
		} else
			this.attacking = false;

	}

	public void update(float time, Level l) {
		super.update(time);
		if (attacking) {
			if (vulnerable) {
				this.attack(l);
				counter = 0;
			}
			this.setAttacking(false);

		}
		if (rapid)
			rapidAttack(l);
	}

	public boolean vulnerable() {

		return vulnerable;
	}

	public void upFireRate() {
		if (ATT_DELAY > 5)
			ATT_DELAY -= .5f;
	}

	public void upDmg() {
		if (dmg < 10)
			dmg++;
	}

	public void upRange() {
		if (range < 50) {
			range += 2.5f;
		}
	}

	public void upAmmo() {
		RapidAmmo++;
	}

	public void upHealth() {
		if (MAX_HEALTH < 25) {
			MAX_HEALTH++;
			health++;
		}
	}

	public void upLife() {

	}
}
