package smellychiz.projects.ogc.util.helpers;

import java.util.ArrayList;
import java.util.List;

import smellychiz.projects.ogc.objects.GameObject;
import smellychiz.projects.ogc.objects.Line;

public class Quadtree {
	// call clear, then add, then retrieve and test.
	private int MAX_OBJECTS = 50;
	private int MAX_LEVELS = 5;

	private int level;
	private List<GameObject> objects;
	private Vector3 bounds;
	private Quadtree[] nodes;

	private Line[] lines;

	public Quadtree(int pLevel, Vector3 pBounds) {
		level = pLevel;
		objects = new ArrayList<GameObject>();
		bounds = pBounds;
		nodes = new Quadtree[4];

	}

	public void clear() {
		objects.clear();
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i] != null) {
				nodes[i].clear();
				nodes[i] = null;
			}
		}
	}

	public void draw(float[] mat) {
		for (int i = 0; i < lines.length; i++) {

			lines[i].draw(mat);

		}
		for (int l = 0; l < nodes.length; l++) {
			if (nodes[l] != null) {
				nodes[l].draw(mat);
			}
		}
	}

	private int getIndex(GameObject g) {

		int index = -1;
		double verticalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
		double horizontalMidpoint = bounds.getY() + (bounds.getHeight() / 2);

		boolean topQuadrant = (g.bound.getY() < horizontalMidpoint && g.bound
				.getY() + g.bound.getHeight() < horizontalMidpoint);
		boolean bottomQuadrant = (g.bound.getY() > horizontalMidpoint);

		if (g.bound.getX() < verticalMidpoint
				&& g.bound.getX() + g.bound.getWidth() < verticalMidpoint) {
			if (topQuadrant) {

				index = 1;

			} else if (bottomQuadrant) {

				index = 2;

			}
		} else if (g.bound.getX() > verticalMidpoint) {

			if (topQuadrant) {

				index = 0;

			} else if (bottomQuadrant) {

				index = 3;

			}

		}
		return index;

	}

	public void initLines() {
		lines = new Line[4];
		lines[0] = new Line(bounds.getX(), bounds.getY(), bounds.getX(),
				bounds.getY() + bounds.getHeight());
		lines[1] = new Line(bounds.getX(), bounds.getY() + bounds.getHeight(),
				bounds.getX() + bounds.getWidth(), bounds.getY()
						+ bounds.getHeight());
		lines[2] = new Line(bounds.getX() + bounds.getWidth(), bounds.getY()
				+ bounds.getHeight(), bounds.getX() + bounds.getWidth(),
				bounds.getY());
		lines[3] = new Line(bounds.getX(), bounds.getY(), bounds.getX()
				+ bounds.getWidth(), bounds.getY());
		for (Line l : lines) {
			l.SetColor(1, 0, 0, 1);

		}
	}

	public void insert(GameObject g) {

		if (nodes[0] != null) {
			int index = getIndex(g);
			if (index != -1) {

				nodes[index].insert(g);
				return;
			}
		}
		objects.add(g);
		if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
			if (nodes[0] == null) {
				split();
			}
			int i = 0;
			while (i < objects.size()) {

				int index = getIndex(objects.get(i));
				if (index != -1) {
					nodes[index].insert(objects.remove(i));
				} else {
					i++;
				}
			}

		}

	}

	public List<GameObject> retrieve(List<GameObject> returnObjects,
			GameObject g) {

		int index = getIndex(g);

		if (index != -1 && nodes[0] != null) {

			nodes[index].retrieve(returnObjects, g);

		}
		returnObjects.addAll(objects);
		return returnObjects;
	}

	private void split() {

		int subWidth = (int) (bounds.getWidth() / 2);
		int subHeight = (int) (bounds.getHeight() / 2);
		int x = (int) bounds.getX();
		int y = (int) bounds.getY();

		nodes[0] = new Quadtree(level + 1, new Vector3(x + subWidth, y,
				subWidth, subHeight));
		nodes[1] = new Quadtree(level + 1, new Vector3(x, y, subWidth,
				subHeight));
		nodes[2] = new Quadtree(level + 1, new Vector3(x, y + subHeight,
				subWidth, subHeight));
		nodes[3] = new Quadtree(level + 1, new Vector3(x + subWidth, y
				+ subHeight, subWidth, subHeight));

	}
}
