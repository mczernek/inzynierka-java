package pl.edu.agh.mczernek.mandown.fallDetector;

public class PermanentAngle {

	private float value;
	private float prevValue;
	private float timeStarted;
	private float timeFinished;

	public double getValue() {
		return value;
	}

	public double getPrevValue() {
		return prevValue;
	}

	public double getTimeStarted() {
		return timeStarted;
	}

	public double getTimeFinished() {
		return timeFinished;
	}

	public double getValueChange() {
		return value - prevValue;
	}

}
