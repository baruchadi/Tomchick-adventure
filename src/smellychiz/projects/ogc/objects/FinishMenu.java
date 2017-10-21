package smellychiz.projects.ogc.objects;

import smellychiz.projects.ogc.util.Assets;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.NumText;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public class FinishMenu {
	GameObject contBackground;
	public Button bCont;

	NumText slain;
	NumText coins;
	NumText number1;

	public FinishMenu(Context mContext, Camera2D c) {
		contBackground = new GameObject(mContext, new Vector3(3f, 1,
				c.pos.getWidth() - 6, c.pos.getHeight() - 2), c,
				Assets.continueBg);
		bCont = new Button(mContext, new Vector3(
				3 + (.52f * contBackground.bound.getWidth()),
				.8f + (.15f * contBackground.bound.getHeight()),
				(.42f * contBackground.bound.getWidth()),
				(.34f * contBackground.bound.getHeight())), c, Assets.cont,
				Assets.cCont);
	}

	public void draw() {
		contBackground.draw();
		bCont.draw();
		// if (number1 != null)
		// number1.draw();
		if (slain != null) {
			slain.draw();
		}
		if (coins != null) {
			coins.draw();
		}
	}

	public void update() {
		bCont.update();
	}

	public void create(double time, int coin, int slainPigs, Context mContext,
			Camera2D cam) {

		number1 = new NumText("" + time, "" + time, mContext, new Vector3(4, 4,
				10, 4), cam, true);
		slain = new NumText(slainPigs + "", slainPigs + "", mContext,
				new Vector3(bCont.bound.getX() + (bCont.bound.getWidth() / 2)
						- .5f - 6f, bCont.bound.getY()
						+ (bCont.bound.getHeight() / 2) + 2f, 2, .5f), cam,
				false, .5f);
		System.out.println("coins: " + coin);
		coins = new NumText(coin + "", coin + "", mContext, new Vector3(
				bCont.bound.getX() + (bCont.bound.getWidth() / 2) - .5f - 6f,
				bCont.bound.getY() + (bCont.bound.getHeight() / 2) - 1.3f, 2,
				.5f), cam, false, .5f);
	}
}
