package smellychiz.projects.ogc.util;

import smellychiz.projects.ogc.objects.GameObject;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public class NumText {

	// String sTxt;

	int[] nums1;

	GameObject[] nums;

	Context mContext;

	Vector3 bounds;

	int valueNum;
	String value;

	public NumText(String s, String maxChars, Context c, Vector3 v,
			Camera2D cam, boolean d) {
		value = s;

		int txt = (int) (d ? Double.parseDouble(s) : Integer.parseInt(s));
		System.out.println(txt + "txt");
		nums1 = new int[maxChars.length()];
		formatNums(txt);
		nums = new GameObject[maxChars.length()];
		mContext = c;
		bounds = v;
		initArray(cam);
	}

	public NumText(String s, String maxChars, Context c, Vector3 v,
			Camera2D cam, boolean d, float maxWidth) {
		value = s;

		int txt = (int) (d ? Double.parseDouble(s) : Integer.parseInt(s));
		System.out.println(txt + "txt");
		nums1 = new int[maxChars.length()];
		formatNums(txt);
		nums = new GameObject[maxChars.length()];
		mContext = c;
		bounds = v;
		initArray(cam, maxWidth);
	}

	public NumText(int n, int maxChars, Context c, Vector3 v, Camera2D cam) {
		value = "" + n;
		nums1 = new int[maxChars];
		nums = new GameObject[maxChars];
		mContext = c;
		bounds = v;
		System.out.println(n + "txt");
		formatNums(n);
		initArray(cam);

	}

	public void initArray(Camera2D cam) {
		for (int i = 0; i < nums.length; i++) {
			nums[i] = new GameObject(mContext,
					new Vector3(
							bounds.getX()
									+ ((nums.length - 1 - i) * (bounds
											.getWidth() / value.length())),
							bounds.getY(), bounds.getWidth() / value.length(),
							bounds.getHeight()), cam, Assets.nums[0]);
		}
		update(cam);

	}

	public void initArray(Camera2D cam, float mW) {
		for (int i = 0; i < nums.length; i++) {
			nums[i] = new GameObject(mContext, new Vector3(bounds.getX()
					+ ((nums.length - 1 - i) * (Math.min(mW, bounds.getWidth()
							/ value.length()))), bounds.getY(), Math.min(mW,
					bounds.getWidth() / value.length()), bounds.getHeight()),
					cam, Assets.nums[0]);
		}
		update(cam);

	}

	public int getInt() {
		String str = "";
		for (int i = nums1.length - 1; i > -1; i--) {
			str += nums1[i];
		}
		return Integer.parseInt(str);
	}

	public void formatNums(int n) {
		int num = n;
		int i = 0;
		while (num > 0) {
			nums1[i] = num % 10;
			num = num / 10;
			i++;
		}
		for (int l = 0; l < nums1.length; l++) {
			System.out.println(nums1[l] + "xxndsfjk");
		}

	}

	public void updateText(int num, Camera2D cam) {
		formatNums(num);
		initArray(cam);
		update(cam);
	}

	public void addCoin() {
		int l = Integer.parseInt(value);
		l++;
		value = l + "";
		for (int i = 0; i < nums1.length; i++) {
			if (nums1[i] < 9) {
				nums1[i]++;

				break;
			} else {
				nums1[i] = 0;
			}
		}

	}

	public void update(Camera2D cam) {

		// System.out.println("length: " + sTxt.length());
		for (int i = 0; i < nums.length; i++) {
			// System.out.println(i + " ; " + nums1[i]);
			nums[i].setTextureArea(Assets.nums[nums1[i]]);
			nums[i].initVertices();
		}
	}

	public void draw() {

		// int num = (txt + "").length();

		for (int i = 0; i < nums.length; i++) {
			nums[i].draw();
		}

	}

}
