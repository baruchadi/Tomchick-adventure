package smellychiz.projects.ogc.util;

public class Manifest {
	public int lines;
	public String[] levels;
	public boolean ready = false;;

	public Manifest(int lines, String[] lvls) {
		this.lines = lines;
		this.levels = lvls;
	}

	public String getLevel(int i) {
		return levels[i];
	}

	public int getLines() {
		return lines;
	}

	public boolean isReady() {
		return ready;
	}

}
