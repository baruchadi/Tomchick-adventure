package smellychiz.projects.ogc.objects.world;

import smellychiz.projects.ogc.objects.Line;
import smellychiz.projects.ogc.util.Camera2D;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;

public class NewLevel extends Level {

	public static final int UNIT = 4;
	public static final int SMALL = 0;
	public static final int MEDIUM = 1;
	public static final int HUGE = 2;

	private int size;

	public Line[] lines;

	public float width;
	public float height;

	public int minX, minY, maxX, maxY;

	public NewLevel(Context context, Vector3 v, Camera2D c, float ratio,
			int size) {
		super(context, v, c, ratio);
		this.size = size;
		switch (size) {
		case SMALL:
			width = 40 * UNIT;
			height = 10 * UNIT;
			break;
		case MEDIUM:
			width = 70 * UNIT;
			height = 20 * UNIT;
			break;
		case HUGE:
			width = 100 * UNIT;
			height = 30 * UNIT;
		}

		minX = -1;
		minY = -1;
		maxX = (int) (width + 1);
		maxY = (int) (height + 1);

		lines = new Line[(int) (width + height)];

		createGrid();
	}

	public void createGrid() {
		lines[0] = new Line(0, minY, 0, maxY);
		lines[0].SetColor(1, 0, 0, 1);
		for (int x = 1; x < width; x++) {

			lines[0 + x] = new Line(x, 0, x, height);

		}

		lines[(int) width] = new Line(minX, 0, maxX, 0);
		lines[(int) width].SetColor(1, 0, 0, 1);
		for (int y = 1; y < height; y++) {

			lines[(int) (width + y)] = new Line(0, y, width, y);

		}

	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		super.draw();
		lines[0].draw(cam.getProjMatrix());
		for (int i = 1; i < width; i++) {

			lines[0 + i].draw(cam.getProjMatrix());

		}
		lines[(int) width].draw(cam.getProjMatrix());
		for (int i = 1; i < height; i++) {

			lines[(int) (width + i)].draw(cam.getProjMatrix());
		}
	}

	public boolean insideGrid(float x, float y) {

		return (x >= 0 && y >= 0 && y < height && x < width);

	}

	@Override
	public void update(float delta) {

	}

	@Override
	public String folder() {
		// TODO Auto-generated method stub
		return null;
	}

}
