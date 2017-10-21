package smellychiz.projects.ogc.util.views;

import smellychiz.projects.ogc.Start;
import smellychiz.projects.ogc.util.base.GameRenderer;
import smellychiz.projects.ogc.util.base.GameView;
import smellychiz.projects.ogc.util.views.renderers.PickerRenderer;
import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;

public class PickerView extends GameView {
	public PickerRenderer c;

	public PickerView(Context context, int width, int height, Start a) {
		super(context, width, height, a);
		parent = a;
	}

	@Override
	public GameRenderer getRend() {
		return c;
	}

	@Override
	public void initRend(Context context) {
		c = new PickerRenderer(context, parent);
	}

	@Override
	public void touchDown(float x, float y, int pointerId, MotionEvent e,
			int pointerIndex) {
		PointF f = new PointF();
		f.x = ((e.getX(pointerIndex) / Width) * c.camera.pos.getWidth())
				+ c.camera.pos.getX();
		f.y = (Height - e.getY(pointerIndex)) / Height
				* c.camera.pos.getHeight();
		mActivePointers.put(pointerId, f);
		c.touchDown(f.x, f.y, pointerId);
	}

	@Override
	public void touchMove(float x, float y, int pointerId, MotionEvent event,
			int pointerIndex) {
		for (int size = event.getPointerCount(), i = 0; i < size; i++) {
			PointF point = mActivePointers.get(event.getPointerId(i));
			if (point != null) {
				point.x = ((event.getX(pointerIndex) / Width) * c.camera.pos
						.getWidth()) + c.camera.pos.getX();
				point.y = (Height - event.getY(pointerIndex)) / Height
						* c.camera.pos.getHeight();
				c.touchDown(point.x, point.y, pointerId);
			}
		}
	}

	@Override
	public void touchUp(float x, float y, int pointerId, MotionEvent e,
			int pointerIndex) {
		c.touchUp(mActivePointers.get(pointerId).x,
				mActivePointers.get(pointerId).y, pointerId);
		mActivePointers.remove(pointerId);
	}

}
