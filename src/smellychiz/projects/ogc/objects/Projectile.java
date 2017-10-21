package smellychiz.projects.ogc.objects;

import java.util.List;

import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.graphics.Texture;
import smellychiz.projects.ogc.util.graphics.TextureArea;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public class Projectile extends GameObject {

	public int health;
	public int dmg;

	public float speed;
	static int oldID = 0;
	int id;

	public Projectile(Context context, Vector3 v, Camera2D c) {
		super(context, v, c, Assets.ball);

		this.health = 50;
		this.dmg = 1;
		this.good = true;
		this.speed = 0;
		this.type = PROJECTILE;
		id = oldID;
		oldID++;
	}

	public Projectile(Context context, Vector3 v, Camera2D c, Texture t,
			float speed, boolean good, int dmg, int health) {
		super(context, v, c, t);

		this.health = health;
		this.dmg = dmg;
		this.good = good;
		this.speed = speed;
		this.type = PROJECTILE;
		id = oldID;
		oldID++;
	}

	public Projectile(Context context, Vector3 v, Camera2D c, TextureArea t,
			float speed, boolean good, int dmg, int health) {
		super(context, v, c, t);

		this.health = health;
		this.dmg = dmg;
		this.good = good;
		this.speed = speed;
		this.type = PROJECTILE;
		id = oldID;
		oldID++;
	}

	public int getDamage() {

		return dmg;

	}

	public void move(float delta) {

		this.bound.addX(speed * delta);

		this.health -= 1;

		System.out.println(health);

	}

	public boolean update(float delta, List<Projectile> l) {
		move(delta);
		System.out.println("ID: " + id);
		if (health <= 0) {
			return true;
		}

		initVertices();
		return false;
	}

}
