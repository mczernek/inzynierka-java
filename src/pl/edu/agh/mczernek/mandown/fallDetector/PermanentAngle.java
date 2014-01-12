package pl.edu.agh.mczernek.mandown.fallDetector;

public class PermanentAngle {

	private double value;
	private long timeStarted;
	private long timeFinished;

	public PermanentAngle(long start, long end, double mean) {
		timeStarted = start;
		timeFinished = end;
		value = mean;
	}

	public double getValue() {
		return value;
	}

	public long getTimeStarted() {
		return timeStarted;
	}

	public long getTimeFinished() {
		return timeFinished;
	}

}
