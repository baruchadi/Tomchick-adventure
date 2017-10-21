package smellychiz.projects.ogc.objects;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.graphics.Texture;
import smellychiz.projects.ogc.util.graphics.TextureArea;
import smellychiz.projects.ogc.util.helpers.GLHelper;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

public class GameObject extends GLHelper implements java.io.Serializable {

	transient private ShortBuffer drawListBuffer;
	private static final int FLOAT_SIZE_BYTES = 4;
	private static final int TRIANGLE_VERTICES_DATA_STRIDE_BYTES = 5 * FLOAT_SIZE_BYTES;
	private static final int TRIANGLE_VERTICES_DATA_POS_OFFSET = 0;
	private static final int TRIANGLE_VERTICES_DATA_UV_OFFSET = 3;
	transient private FloatBuffer mTriangleVertices;
	private float[] mTriangleVerticesData = new float[20];
	private float[] UVcoord = { 0, 0, 0, 1, 1, 1, 1, 0 };
	private short drawOrder[] = { 0, 1, 2, 0, 2, 3 };
	public boolean good = true;
	public transient Camera2D camera;

	public Vector3 bound;

	Texture tex;
	TextureArea tArea;

	private int mProgram;
	private int muMVPMatrixHandle;
	private int maPositionHandle;
	private int maTextureHandle;

	public int type = -1;

	public static final int PLAYER = 0;
	public static final int COIN = 1;
	public static final int PLATFORM = 2;
	public static final int ENEMY = 3;
	public static final int PROJECTILE = 4;
	public static final int SIGN = 5;
	public static final int FINISH_SIGN = 6;

	private static String TAG = "GLES20TriangleRenderer";
	private final static String mVertexShader = "uniform mat4 uMVPMatrix;\n"
			+ "attribute vec4 aPosition;\n" + "attribute vec2 aTextureCoord;\n"
			+ "varying vec2 vTextureCoord;\n" + "void main() {\n"
			+ "  gl_Position = uMVPMatrix * aPosition;\n"
			+ "  vTextureCoord = aTextureCoord;\n" + "}\n";

	private final static String mFragmentShader = "precision mediump float;\n"
			+ "varying vec2 vTextureCoord;\n" + "uniform sampler2D sTexture;\n"
			+ "uniform float opacity;\n" + "void main() {\n"
			+ "  gl_FragColor = texture2D(sTexture, vTextureCoord);\n"
			+ "gl_FragColor.a *= opacity;\n " + "}\n";

	public Context mContext;

	public float alpha = 1;

	protected int ColorHandle;

	float color[] = { 1.0f, 1.0f, 1.0f, 1.0f };

	public GameObject(Context context, Vector3 v, Camera2D c) {
		super();
		mContext = context;
		this.camera = c;

		initVertices(v);

		initProgram();
	}

	public GameObject(Context context, Vector3 v, Camera2D c, Texture t) {

		mContext = context;
		this.camera = c;
		this.tex = t;
		initVertices(v);

		initProgram();

	}

	public GameObject(Context context, Vector3 v, Camera2D c, TextureArea t) {

		mContext = context;
		this.camera = c;
		setTexture(t.getTex());
		this.tArea = new TextureArea(t);

		UVcoord = t.getUvCoords();

		initVertices(v);

		initProgram();

	}

	public GameObject(int i) {
	}

	public boolean collidesWith(Vector3 v) {

		int r = -1;
		return (bound.getRealX() <= v.getRealX() + v.getRealWidth()
				&& bound.getRealX() + bound.getRealWidth() >= v.getRealX()
				&& bound.getY() <= v.getHeight() + v.getY() && bound.getY()
				+ bound.getHeight() >= v.getY());

		// if(bound.getX()+bound.getWidth()>=v.getX() &&
		// bound.getX()+bound.getWidth()<=v.getX()+v.getWidth() &&
		// bound.getY()+bound.getHeight()>=v.getY()&&){
		//
		//
		// }

	}

	public boolean contains(float x, float y) {
		if (!this.bound.isFlipped()) {
			if (this.bound.getX() < x
					&& this.bound.getX() + this.bound.getWidth() > x
					&& this.bound.getY() < y
					&& this.bound.getY() + this.bound.getHeight() > y) {

				System.out.println("Contains!");
				return true;
			} else
				return false;

		} else if (this.bound.getX() > x
				&& this.bound.getX() + this.bound.getWidth() < x
				&& this.bound.getY() < y
				&& this.bound.getY() + this.bound.getHeight() > y) {

			System.out.println("Contains!");
			return true;
		} else
			return false;

	}

	public void draw() {
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glEnable(GLES20.GL_TEXTURE_2D);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		GLES20.glUseProgram(mProgram);

		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tex.mTextureID);

		mTriangleVertices.position(TRIANGLE_VERTICES_DATA_POS_OFFSET);
		GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT,
				false, TRIANGLE_VERTICES_DATA_STRIDE_BYTES, mTriangleVertices);

		mTriangleVertices.position(TRIANGLE_VERTICES_DATA_UV_OFFSET);
		GLES20.glEnableVertexAttribArray(maPositionHandle);

		ColorHandle = GLES20.glGetUniformLocation(mProgram, "opacity");

		GLES20.glUniform1f(ColorHandle, alpha);

		GLES20.glVertexAttribPointer(maTextureHandle, 2, GLES20.GL_FLOAT,
				false, TRIANGLE_VERTICES_DATA_STRIDE_BYTES, mTriangleVertices);

		GLES20.glEnableVertexAttribArray(maTextureHandle);

		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false,
				camera.getProjMatrix(), 0);
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length,
				GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

		GLES20.glDisable(GLES20.GL_TEXTURE_2D);
		GLES20.glDisable(GLES20.GL_BLEND);

	}

	public void EchoUv() {
		for (int i = 0; i < UVcoord.length; i++) {
			Log.d("uv " + i, "" + UVcoord[i]);
		}
	}

	public float getDmg() {
		return 1.5f;
	}

	public TextureArea getTArea() {
		return this.tArea;
	}

	private void initProgram() {
		mProgram = createProgram(mVertexShader, mFragmentShader);
		if (mProgram == 0) {
			return;
		}
		maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");

		if (maPositionHandle == -1) {
			throw new RuntimeException(
					"Could not get attrib location for aPosition");
		}
		maTextureHandle = GLES20.glGetAttribLocation(mProgram, "aTextureCoord");

		if (maTextureHandle == -1) {
			throw new RuntimeException(
					"Could not get attrib location for aTextureCoord");
		}

		muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

		if (muMVPMatrixHandle == -1) {
			throw new RuntimeException(
					"Could not get attrib location for uMVPMatrix");
		}

	}

	private void initUVCoords(float[] uvCoords) {
		for (int i = 0; i < UVcoord.length; i++) {
			UVcoord[i] = uvCoords[i];

		}

	}

	public void initVertices() {

		mTriangleVerticesData[0] = bound.getX();
		mTriangleVerticesData[1] = bound.getY() + bound.getHeight();
		mTriangleVerticesData[2] = bound.getZ();

		mTriangleVerticesData[3] = UVcoord[0];
		mTriangleVerticesData[4] = UVcoord[1];

		mTriangleVerticesData[5] = bound.getX();
		mTriangleVerticesData[6] = bound.getY();
		mTriangleVerticesData[7] = bound.getZ();

		mTriangleVerticesData[8] = UVcoord[2];
		mTriangleVerticesData[9] = UVcoord[3];

		mTriangleVerticesData[10] = bound.getX() + bound.getWidth();
		mTriangleVerticesData[11] = bound.getY();
		mTriangleVerticesData[12] = bound.getZ();

		mTriangleVerticesData[13] = UVcoord[4];
		mTriangleVerticesData[14] = UVcoord[5];

		mTriangleVerticesData[15] = bound.getX() + bound.getWidth();
		mTriangleVerticesData[16] = bound.getY() + bound.getHeight();
		mTriangleVerticesData[17] = bound.getZ();

		mTriangleVerticesData[18] = UVcoord[6];
		mTriangleVerticesData[19] = UVcoord[7];
		putOrder();
	}

	public void initVertices(Vector3 v) {
		bound = v;
		initVertices();
	}

	public void putOrder() {
		mTriangleVertices = ByteBuffer
				.allocateDirect(mTriangleVerticesData.length * FLOAT_SIZE_BYTES)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		mTriangleVertices.put(mTriangleVerticesData).position(0);

		ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);

		dlb.order(ByteOrder.nativeOrder());

		drawListBuffer = dlb.asShortBuffer();
		drawListBuffer.put(drawOrder);
		drawListBuffer.position(0);

	}

	public void setAlpha(float a) {

		alpha = a;

	}

	public void setTexture(Texture t) {
		this.tex = t;
	}

	public void setTextureArea(TextureArea tArea) {

		this.tArea = tArea;

		this.setUVcoord(tArea.getUvCoords());

	}

	public void dispose() {
		mTriangleVertices = null;
		mTriangleVerticesData = null;
		tex = null;
		// tArea.dispose();
		maPositionHandle = 0;
		muMVPMatrixHandle = 0;
		maTextureHandle = 0;
		mProgram = 0;
		mContext = null;
		camera = null;
		tArea = null;
		drawListBuffer = null;
	}

	public void setUVcoord(float[] uVcoord) {
		UVcoord = uVcoord;
	}

}
