package smellychiz.projects.ogc.util.views;

import smellychiz.projects.ogc.Start;
import smellychiz.projects.ogc.util.base.GameRenderer;
import smellychiz.projects.ogc.util.base.GameView;
import smellychiz.projects.ogc.util.views.renderers.LevelRenderer;
import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;

public class LevelView extends GameView {
	public LevelRenderer c;

	public LevelView(Context context, int width, int height, Start a) {
		super(context, width, height, a);

	}

	@Override
	public GameRenderer getRend() {
		// TODO Auto-generated method stub
		return c;
	}

	@Override
	public void initRend(Context context) {
		c = new LevelRenderer(context, parent.getName(), parent,
				parent.getFolder());
	}

	@Override
	public void touchDown(float x, float y, int pointerId, MotionEvent e,
			int pointerIndex) {
		PointF f = new PointF();
		f.x = ((e.getX(pointerIndex) / Width) * c.KeyCam.pos.getWidth())
				+ c.KeyCam.pos.getX();
		f.y = (Height - e.getY(pointerIndex)) / Height
				* c.KeyCam.pos.getHeight();
		mActivePointers.put(pointerId, f);
		c.touchDown(f.x, f.y, pointerId);
	}

	@Override
	public void touchMove(float x, float y, int pointerId, MotionEvent event,
			int pointerIndex) {
		for (int size = event.getPointerCount(), i = 0; i < size; i++) {
			PointF point = mActivePointers.get(event.getPointerId(i));
			if (point != null) {
				point.x = ((event.getX(pointerIndex) / Width) * c.KeyCam.pos
						.getWidth()) + c.KeyCam.pos.getX();
				point.y = (Height - event.getY(pointerIndex)) / Height
						* c.KeyCam.pos.getHeight();
				c.touchDown(point.x, point.y, pointerId);
			}
		}
	}

	@Override
	public void touchUp(float x, float y, int pointerId, MotionEvent e,
			int pointerIndex) {
		System.out.println("upTouch");
		c.touchUp(mActivePointers.get(pointerId).x,
				mActivePointers.get(pointerId).y, pointerId);
		mActivePointers.remove(pointerId);
	}
}
