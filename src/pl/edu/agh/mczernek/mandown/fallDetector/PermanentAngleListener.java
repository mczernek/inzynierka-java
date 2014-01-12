package pl.edu.agh.mczernek.mandown.fallDetector;

public interface PermanentAngleListener {
	public void beginOfPermanentAngleDetected(long time, double value);

	public void endOfPermanentAngleDetected(long endTime, double valeue);
}
