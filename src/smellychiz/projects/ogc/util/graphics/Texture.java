package smellychiz.projects.ogc.util.graphics;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class Texture implements java.io.Serializable {

	private static int TEXTURE_ID = 0;
	public int mTextureID;
	public int ID;
	private Context mContext;
	String source;
	InputStream is;
	float Width, Height;

	int[] textures = new int[1];

	public Texture(Context c, InputStream i) {
		ID = TEXTURE_ID;
		TEXTURE_ID++;
		mContext = c;
		this.is = i;
		initRaw();
	}

	public Texture(Context c, InputStream i, float h, float w) {
		ID = TEXTURE_ID;
		TEXTURE_ID++;
		mContext = c;
		this.is = i;
		initRaw();
		this.Height = h;
		this.Width = w;
	}

	public Texture(Context c, String s) {
		ID = TEXTURE_ID;
		TEXTURE_ID++;
		mContext = c;
		this.source = s;
		initString();
	}

	public Texture(Context c, String s, int h, int w) {
		ID = TEXTURE_ID;
		TEXTURE_ID++;
		mContext = c;
		this.source = s;
		initString();
		this.Height = h;
		this.Width = w;
	}

	public void dispose() {
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
		int[] tid = { mTextureID };
		GLES20.glDeleteTextures(1, tid, 0);
	}

	private Bitmap getBitmapFromAsset(String strName) {
		System.out.println("txt");
		AssetManager assetManager = mContext.getAssets();
		InputStream istr = null;
		try {
			istr = assetManager.open(strName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return BitmapFactory.decodeStream(istr);
	}

	public float getHeight() {
		return Height;
	}

	public int getTexture() {
		return mTextureID;
	}

	public float getWidth() {
		return Width;
	}

	public void initRaw() {
		System.out.println("txt");
		GLES20.glGenTextures(1, textures, 0);

		mTextureID = textures[0];
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID);

		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);

		Bitmap bitmap;
		try {
			bitmap = BitmapFactory.decodeStream(is);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// Ignore.
			}
		}

		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, bitmap, 0);
		bitmap.recycle();
		bitmap = null;
	}

	public void initString() {
		System.out.println("txt");

		GLES20.glGenTextures(1, textures, 0);

		mTextureID = textures[0];
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID);

		// InputStream is = mContext.getResources().openRawResource(R.raw.ch1);
		Bitmap bitmap;
		try {

			bitmap = getBitmapFromAsset(this.source);
			this.Width = bitmap.getWidth();
			this.Height = bitmap.getHeight();

		} finally {

		}

		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
		bitmap.recycle();
		bitmap = null;
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		// GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
		// GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		// GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
		// GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
				GLES20.GL_REPEAT);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
				GLES20.GL_REPEAT);
	}

}
