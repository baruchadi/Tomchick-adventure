package smellychiz.projects.ogc.util.base;

import smellychiz.projects.ogc.Start;
import smellychiz.projects.ogc.util.Assets;
import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLSurfaceView;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

public abstract class GameView extends GLSurfaceView {
	public int Width, Height;

	protected Start parent;

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Assets.stopMusic();
	}

	protected View.OnTouchListener listener = new View.OnTouchListener() {

		@Override
		public boolean onTouch(View view, MotionEvent event) {
			// get pointer index from the event object
			int pointerIndex = event.getActionIndex();

			// get pointer ID
			int pointerId = event.getPointerId(pointerIndex);

			// get masked (not specific to a pointer) action
			int maskedAction = event.getActionMasked();

			switch (maskedAction) {

			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN: {
				// We have a new pointer. Lets add it to the list of pointers
				touchDown(event.getX(), event.getY(), pointerId, event,
						pointerIndex);
				break;
			}
			case MotionEvent.ACTION_MOVE: { // a pointer was moved
				for (int size = event.getPointerCount(), i = 0; i < size; i++) {
					PointF point = mActivePointers.get(event.getPointerId(i));
					if (point != null) {
						touchMove(event.getX(), event.getY(),
								event.getPointerId(i), event, pointerIndex);
					}
				}
				break;
			}
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
			case MotionEvent.ACTION_CANCEL: {
				// System.out.println("CANCEL");
				touchUp(mActivePointers.get(pointerId).x,
						mActivePointers.get(pointerId).y, pointerId, event,
						pointerIndex);
				mActivePointers.remove(pointerId);

				break;
			}
			}

			invalidate();

			return true;
		}
	};

	public static SparseArray<PointF> mActivePointers;

	public GameView(Context context, int width, int height, Start a) {
		super(context);
		this.Width = width;
		this.Height = height;
		parent = a;
		setEGLContextClientVersion(2);

		initRend(context);

		setRenderer(getRend());

		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		mActivePointers = new SparseArray<PointF>();
		this.setOnTouchListener(listener);
	}

	public abstract GameRenderer getRend();

	public abstract void initRend(Context context);

	public abstract void touchDown(float x, float y, int pointerId,
			MotionEvent e, int pointerIndex);

	public abstract void touchMove(float x, float y, int pointerId,
			MotionEvent e, int pointerIndex);

	public abstract void touchUp(float x, float y, int pointerId,
			MotionEvent e, int pointerIndex);

}
