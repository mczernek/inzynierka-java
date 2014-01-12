package pl.edu.agh.mczernek.mandown.fallDetector;

public interface HumanFallListener {
	public void onFallDetected(FreeFall freeFall, double anglesChange);
}
