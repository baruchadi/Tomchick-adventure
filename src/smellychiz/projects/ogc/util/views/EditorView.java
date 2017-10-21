package smellychiz.projects.ogc.util.views;

import smellychiz.projects.ogc.Start;
import smellychiz.projects.ogc.util.base.GameRenderer;
import smellychiz.projects.ogc.util.base.GameView;
import smellychiz.projects.ogc.util.views.renderers.EditorRenderer;
import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLSurfaceView;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class EditorView extends GameView {
	public class ScaleListener extends
			ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			scaleFactor *= (detector.getScaleFactor());
			scaleFactor = Math.max(MIN_ZOOM, Math.min(scaleFactor, MAX_ZOOM));

			return true;

		}

	}

	public EditorRenderer c;

	int Width, Height;

	private static float MIN_ZOOM = .5f;
	private static float MAX_ZOOM = 1.5f;

	private float scaleFactor = 1f;
	private ScaleGestureDetector detector;

	private float mPosX;
	private float mPosY;

	private float mLastTouchX;
	private float mLastTouchY;
	private static final int INVALID_POINTER_ID = -1;

	// The ‘active pointer’ is the one currently moving our object.
	private int mActivePointerId = INVALID_POINTER_ID;

	public static SparseArray<PointF> mActivePointers;

	private static int NONE = 0;
	private static int DRAG = 1;
	private static int ZOOM = 2;

	private float sX = 0f;
	private float eX = 0f;
	private float sY = 0f;
	private float eY = 0f;

	private int mode;
	private View.OnTouchListener listener = new View.OnTouchListener() {

		@Override
		public boolean onTouch(View view, MotionEvent event) {

			// get pointer index from the event object
			int pointerIndex = event.getActionIndex();

			// get pointer ID
			int pointerId = event.getPointerId(pointerIndex);

			// get masked (not specific to a pointer) action
			int maskedAction = event.getActionMasked();

			switch (event.getAction() & MotionEvent.ACTION_MASK) {

			case MotionEvent.ACTION_DOWN:
				if (c.panning) {
					sX = event.getX();
					sY = event.getY();

					mode = DRAG;
				} else {

					c.touchDown(event.getX() / Width, (Height - event.getY())
							/ Height, 0);

				}
				break;
			case MotionEvent.ACTION_MOVE:

				if (c.panning) {
					eX = event.getX() - sX;
					eY = event.getY() - sY;
					eX /= Width;
					eY /= Height;
					mode = DRAG;
				} else {

					c.touch(event.getX() / Width, (Height - event.getY())
							/ Height, 0);

				}
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				mode = ZOOM;

				break;
			case MotionEvent.ACTION_UP:
				c.touchUp(event.getX() / Width, (Height - event.getY())
						/ Height, 0);
				mode = NONE;
				break;
			case MotionEvent.ACTION_POINTER_UP:
				mode = DRAG;
				break;

			}

			detector.onTouchEvent(event);

			c.camera.scale(scaleFactor, scaleFactor);

			c.camera.translate(eX, eY);
			sX = event.getX();
			sY = event.getY();
			eX = 0;
			eY = 0;
			invalidate();
			return true;
		}
	};

	public EditorView(Context context, int width, int height, Start parent,
			int LevelSize) {
		super(context, width, height, parent);

		// Set the Renderer for dra wing on the GLSurfaceView
		setRenderer(c);

		// Render the view only when there is a change in the drawing data
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		mActivePointers = new SparseArray<PointF>();
		this.setOnTouchListener(listener);
		detector = new ScaleGestureDetector(context, new ScaleListener());

	}

	@Override
	public GameRenderer getRend() {
		// TODO Auto-generated method stub
		return c;
	}

	@Override
	public void initRend(Context context) {
		c = new EditorRenderer(context, 0);
	}

	@Override
	public void touchDown(float x, float y, int pointerId, MotionEvent e,
			int pointerIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void touchMove(float x, float y, int pointerId, MotionEvent e,
			int pointerIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void touchUp(float x, float y, int pointerId, MotionEvent e,
			int pointerIndex) {
		// TODO Auto-generated method stub

	}
}
