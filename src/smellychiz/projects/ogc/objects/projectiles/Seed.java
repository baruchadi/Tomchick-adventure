package smellychiz.projects.ogc.objects.projectiles;

import smellychiz.projects.ogc.objects.Projectile;
import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public class Seed extends Projectile {

	public static float width = 1f;
	public static float height = .7f;
	public static float speed1 = 1;

	public Seed(Context context, Vector3 v, Camera2D c, int var, int dmg,
			int range) {
		super(context, new Vector3(v.getX() + (v.getWidth() / 2), v.getY()
				- (height / 2), width, height), c, Assets.fSeed, speed1 * var,
				true, dmg, range);

	}

	public Seed(Context context, Vector3 v, Camera2D c, int var, int dmg,
			int range, float f) {
		super(context, new Vector3(v.getX() + (v.getWidth() / 2), v.getY()
				+ (f) - (height / 2), width, height), c, Assets.seed, speed1
				* var, true, dmg, range);

	}

}
