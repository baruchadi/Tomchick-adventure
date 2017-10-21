package smellychiz.projects.ogc.objects.projectiles;

import smellychiz.projects.ogc.objects.Projectile;
import smellychiz.projects.ogc.objects.mobs.agressive.Pig;
import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public class PigAttack extends Projectile {
	public static float width = Pig.width;
	public static float height = Pig.height;
	public static float speed1 = 0;

	public PigAttack(Context context, Vector3 v, Camera2D c, int var,
			boolean flip) {
		super(context, new Vector3(v.getX(), v.getY(), width, height), c,
				Assets.pig_power, speed1 * var, false, 2, 30);
		this.setAlpha(.75f);
		this.bound.setFlipped(flip);
		this.initVertices();
	}
}
