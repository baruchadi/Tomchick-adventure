package smellychiz.projects.ogc.objects.mobs.agressive;

import java.util.List;

import smellychiz.projects.ogc.objects.GameObject;
import smellychiz.projects.ogc.objects.MovingMob;
import smellychiz.projects.ogc.objects.mobs.Player;
import smellychiz.projects.ogc.objects.world.Level;
import smellychiz.projects.ogc.objects.world.OldLevel;
import smellychiz.projects.ogc.objects.world.Platform;
import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public abstract class Enemy extends MovingMob {

	float speedVar = .14f;

	protected float health;
	float MAX_HEALTH = 5;

	static int edge = 3;
	Vector3 platform;

	GameObject bHealth, container;

	int platformNum;

	boolean close = false;
	int coins = 0;
	int d1;
	protected int stateTime = 0;

	float speed = .1f;

	float oSpeed = .1f;

	float dmg = 1;

	public Enemy(Context context, Platform p, Camera2D c, float ratio,
			float maxHP, float w, float h, int pNum) {
		super(context, new Vector3(
				(float) (p.pos.getX() + (Math.random() * p.pos.getWidth())),
				p.pos.getY() + p.pos.getHeight() - .2f, w, h), c);
		this.type = ENEMY;

		MAX_HEALTH = maxHP;

		health = maxHP;

		this.oSpeed = oSpeed + ((float) Math.random() * speedVar)
				- (speedVar / 2);

		container = new GameObject(mContext, new Vector3(bound.getRealX(),
				bound.getY() + bound.getHeight(), 5, 1), c, Assets.hContainer);
		bHealth = new GameObject(mContext, new Vector3(bound.getRealX(),
				bound.getY() + bound.getHeight(), 5, 1), c, Assets.red);

		platformNum = pNum;

		platform = p.pos;

	}

	@Override
	public void afterUpdate(float time) {

		midUpdate(time);
		dX = getXSpeed(time);
		if (direction == LEFT) {
			this.bound.setFlipped(true);
			if (this.bound.getX() + dX > platform.getX() + edge) {
				this.bound.addX(dX);
			} else {
				System.out.println("changeDirection");
				dX = 0;
				direction = -direction;
			}
		} else if (direction == RIGHT) {
			this.bound.setFlipped(false);
			if (this.bound.getX() + dX < platform.getX() + platform.getWidth()
					- edge) {
				this.bound.addX(dX);
			} else {
				System.out.println("changeDirection");
				dX = 0;
				direction = -direction;
			}
		}

		if (direction != d1) {
			stateTime = 0;
		}

		// stateTime += time;
		d1 = direction;
		// this.bound.addY(dY);
		this.initVertices();
		bHealth.initVertices();
		container.initVertices();
		this.stateTime += time;

	}

	public abstract void attack(Level l);

	public void Attack() {

	}

	@Override
	public void beforUpdate(float time) {
		this.container.bound.setX(this.bound.getRealX());
		this.bHealth.bound.setX(this.bound.getRealX());
		this.container.bound.setY(this.bound.getY() + bound.getHeight());
		this.bHealth.bound.setY(this.bound.getY() + bound.getHeight() - .25f);

	}

	public abstract void charge(Player p, OldLevel lvl);

	@Override
	public boolean contains(float x, float y) {
		// TODO Auto-generated method stub
		return super.contains(x, y);
	}

	public void damage(int dmg) {
		this.health -= dmg;
		updateHealth();
	}

	@Override
	public void draw() {
		super.draw();
		container.draw();
		bHealth.draw();
	}

	public void drawHealth() {
		container.draw();
		bHealth.draw();
	}

	@Override
	public float getDmg() {
		// TODO Auto-generated method stub
		return dmg;
	}

	@Override
	public float getXSpeed(float delta) {
		switch (direction) {
		case LEFT:

			return -speed * delta;

		case RIGHT:

			return speed * delta;
		default:
			return 0;
		}

	}

	public abstract float HEIGHT();

	@Override
	public boolean isGravity() {

		return true;
	}

	@Override
	public void midUpdate(float time) {

		// this.dX = .2f;
		// System.out.println(dX);

	}

	public int getCoins() {
		return coins;
	}

	public boolean update(float time, List<Enemy> enemies, int pNum, Player p,
			OldLevel lvl) {

		beforUpdate(time);
		if (platformNum == pNum) {
			attk = true;
			speed = oSpeed * 2;
			charge(p, lvl);
		} else {
			speed = oSpeed;
			attk = false;
		}

		if (close)
			attack(lvl);
		afterUpdate(time);
		if (health <= 0) {

			return false;
		} else
			return true;

	}

	public void updateHealth() {

		// bHealth.getTArea().setWidth(Math.min(WIDTH(), health * WIDTH()));

		bHealth.bound.setWidth(Math.min(5, (health / MAX_HEALTH) * 5));

	}

	public abstract float WIDTH();

}