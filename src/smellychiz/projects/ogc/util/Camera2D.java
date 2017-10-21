package smellychiz.projects.ogc.util;

import smellychiz.projects.ogc.util.helpers.Vector3;
import android.opengl.Matrix;

public class Camera2D implements java.io.Serializable {

	public Vector3 pos;
	private float ratio;
	private float[] mProjMatrix = new float[16];
	private float[] mProjMatrix2 = new float[16];
	private float[] Ratio = new float[16];
	private float[] Ortho = new float[16];
	public float mX = -1, mY = -1;

	public float oX, oY, oW, oH;

	public float minX, minY, maxX, maxY;

	public boolean boundries = false;

	float mW;

	float cW;

	float mH;

	float cH;

	int counter = 0;

	float speed = .1f;

	private final int MOVE_LEFT = 2;

	private final int MOVE_RIGHT = 0;

	private final int MOVE_BOTTOM = 1;

	private final int MOVE_TOP = 3;

	private int move = 0;

	public Camera2D(float x, float y, float height, float width) {
		pos = new Vector3(x, y, width, height);
		this.mX = mX;
		this.mY = mY;
		this.oX = x;
		this.oY = y;
		this.oW = width;
		this.oH = height;
		mW = oW;
		mH = oH;
		cW = 0;
		cH = 0;
	}

	public Camera2D(float x, float y, float height, float width, float mX,
			float mY) {
		pos = new Vector3(x, y, width, height);
		this.mX = mX;
		this.mY = mY;
		this.oX = x;
		this.oY = y;
		this.oW = width;
		this.oH = height;
		mW = oW;
		mH = oH;
		cW = 0;
		cH = 0;
	}

	public Camera2D(float x, float y, int height, int width) {
		pos = new Vector3(x, y, width, height);
		this.oX = x;
		this.oY = y;
		this.oW = width;
		this.oH = height;
		mW = oW;
		mH = oH;
		cW = 0;
		cH = 0;
	}

	public Vector3 botLeft() {
		return new Vector3(this.pos.getX(), this.pos.getY(), 1, 1);
	}

	public Vector3 botMid() {
		return new Vector3(this.pos.getX() + (pos.getWidth() / 2),
				this.pos.getY(), 1, 1);
	}

	public Vector3 botRight() {
		return new Vector3(this.pos.getX() + pos.getWidth(), this.pos.getY(),
				1, 1);
	}

	public Vector3 center() {
		// Log.d("CAM",
		// "" + pos.getX() + ", " + pos.getY() + ", " + pos.getWidth()
		// + ", " + pos.getHeight());
		return new Vector3(this.pos.getX() + (pos.getWidth() / 2),
				this.pos.getY() + (pos.getHeight() / 2), 1, 1);
	}

	public void centerCam(Vector3 v) {

		pos.setX(v.getX() - pos.getWidth() / 2 + v.getWidth() / 2);
		if (pos.getX() < 0)
			pos.setX(0);
		else if (mX != -1 && pos.getX() > mX - pos.getWidth())
			pos.setX(mX - pos.getWidth());

		pos.setY(v.getY() - pos.getHeight() / 2 + v.getHeight() / 2);
		if (pos.getY() < 0)
			pos.setY(0);

		init(pos);
	}

	public void cOrtho(int i, float ratio2, float ratio3, float j, float k,
			int l, int m) {
		Matrix.orthoM(Ratio, i, ratio2, ratio3, j, k, l, m);
	}

	public Vector3 getPos() {
		return pos;
	}

	public float[] getProjMatrix() {
		// Matrix.multiplyMM(Ortho, 0, mProjMatrix2, 0, mProjMatrix, 0);
		return mProjMatrix2;
	}

	public void init() {
		Matrix.orthoM(mProjMatrix2, 0, pos.getX(), pos.getX() + pos.getWidth(),
				pos.getY(), pos.getY() + pos.getHeight(), -5f, 5f);
		// Matrix.setLookAtM(mProjMatrix, 0, 0, 0, 3, 0, 0, 0, 0, 1, 0);
	}

	public void init(Vector3 pos1) {
		this.pos = pos1;
		Matrix.orthoM(mProjMatrix2, 0, pos.getX(), pos.getX() + pos.getWidth(),
				pos.getY(), pos.getY() + pos.getHeight(), -5f, 5f);
		// Matrix.setLookAtM(mProjMatrix, 0, 0, 0, 3, 0, 0, 0, 0, 1, 0);
	}

	public Vector3 leftMid() {
		return new Vector3(this.pos.getX(), this.pos.getY()
				+ (pos.getHeight() / 2), 1, 1);
	}

	public void moveAround() {
		if (counter < 40) {
			switch (move) {
			case MOVE_LEFT:
				pos.addX(-speed);
				break;
			case MOVE_RIGHT:
				pos.addX(speed);
				break;
			case MOVE_BOTTOM:
				pos.addY(-speed);
				break;
			case MOVE_TOP:
				pos.addY(speed);

			}
			counter++;
		} else {
			counter = 0;
			if (move < 3) {
				move++;
			} else {
				move = 0;
			}
		}
	}

	public Vector3 qtr1() {
		return new Vector3(this.pos.getX() + ((pos.getWidth() / 4)),
				this.pos.getY() + ((pos.getHeight() / 4) * 3), 1, 1);
	}

	public Vector3 qtr2() {
		return new Vector3(this.pos.getX() + ((pos.getWidth() / 4) * 3),
				this.pos.getY() + ((pos.getHeight() / 4) * 3), 1, 1);
	}

	public Vector3 qtr3() {
		return new Vector3(this.pos.getX() + ((pos.getWidth() / 4)),
				this.pos.getY() + ((pos.getHeight() / 4)), 1, 1);
	}

	public Vector3 qtr4() {
		return new Vector3(this.pos.getX() + ((pos.getWidth() / 4) * 3),
				this.pos.getY() + (pos.getHeight() / 4), 1, 1);
	}

	public Vector3 rightMid() {
		return new Vector3(this.pos.getX() + pos.getWidth(), this.pos.getY()
				+ (pos.getHeight() / 2), 1, 1);
	}

	public void scale(float scaleX, float scaleY) {

		mW = (oW * scaleX);
		cW = (oW - mW) / 2;
		mH = (oH * scaleY);
		cH = (oH - mH) / 2;

		this.pos.setX(oX - cW);
		this.pos.setWidth(oW + (cW * 2));
		this.pos.setY(oX - cH);
		this.pos.setHeight(oH + (cH * 2));
		init(pos);
	}

	public void scaleTranslate(float scaleX, float scaleY, float translateX,
			float translateY) {
		oX -= ((translateX * oW) / 20);
		oY += ((translateY * oH) / 20);
		mW = (oW * scaleX);
		cW = (oW - mW) / 2;
		mH = (oH * scaleY);
		cH = (oH - mH) / 2;

		this.pos.setX(oX - cW);
		this.pos.setWidth(oW + (cW * 2));
		this.pos.setY(oY - cH);
		this.pos.setHeight(oH + (cH * 2));
		init(pos);
	}

	public void SetBoundries(float minX, float minY, float maxX, float maxY) {
		this.maxX = maxX;
		this.minX = minX;
		this.minY = minY;
		this.maxY = maxY;
		boundries = true;
	}

	public Vector3 topLeft() {
		return new Vector3(this.pos.getX(), this.pos.getY()
				+ this.pos.getHeight(), 1, 1);
	}

	public Vector3 topMid() {
		return new Vector3(this.pos.getX() + (pos.getWidth() / 2),
				this.pos.getY() + this.pos.getHeight(), 1, 1);
	}

	public Vector3 topRight() {
		return new Vector3(this.pos.getX() + pos.getWidth(), this.pos.getY()
				+ this.pos.getHeight(), 1, 1);
	}

	public void translate(float translateX, float translateY) {

		oX -= ((translateX * oW) * 2);
		oY += ((translateY * oH) * 2);
		float x, y;
		// if (boundries) {
		//
		// x = Math.max(minX, Math.min(maxX - pos.getWidth(), oX - cW));
		// y = Math.max(minY, Math.min(maxY + pos.getHeight(), oY - cH));
		// } else
		{
			x = oX - cW;
			y = oY - cH;
		}
		this.pos.setY(y);
		this.pos.setX(x);
		init(pos);
	}

}
