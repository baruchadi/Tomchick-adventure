package smellychiz.projects.ogc.objects;

import java.util.Random;

import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public abstract class MovingMob extends Mob {

	Random r;

	int BlocksToMove = 0;

	int blocksMoved = 0;

	int maxBlockMovement = 5;

	public static final int IDLE = 0, LEFT = 1, RIGHT = -1;

	public boolean attk = false;

	public int direction;

	float OriginalX;

	int counter = 0;

	int countTo = 0;

	boolean stuck = false;

	public MovingMob(Context context, Vector3 v, Camera2D c) {
		super(context, v, c);

		r = new Random();
		decideDirection(r.nextInt(120));
		OriginalX = v.getX();
	}

	public void decideDirection(int i) {
		// Log.d("direction", "RESETTING");
		if (!attk) {
			if (i <= 50) {
				direction = LEFT;
				BlocksToMove = r.nextInt(maxBlockMovement) + 3;
			} else if (i <= 100) {
				direction = RIGHT;
				BlocksToMove = r.nextInt(maxBlockMovement) + 3;
			} else {
				direction = IDLE;
				countTo = r.nextInt(100);
			}
		}
	}

	/* abstract vars */

	public abstract float getXSpeed(float delta);

	/* abstract voids */

	public abstract void midUpdate(float time);

}
