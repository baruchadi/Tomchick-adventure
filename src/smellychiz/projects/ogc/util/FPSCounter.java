package smellychiz.projects.ogc.util;

public class FPSCounter {
	 public int currentFPS = 0;
	    public int FPS = 0;
	    public long start = 0;
	    
	    public int getFPS() {
	        return FPS;
	    }
	    
	    public void tick() {
	        currentFPS++;
	        if(System.currentTimeMillis() - start >= 1000) {
	            FPS = currentFPS;
	            currentFPS = 0;
	            start = System.currentTimeMillis();
	        }
	    }
}
