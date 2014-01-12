package pl.edu.agh.mczernek.mandown.fallDetector;

import pl.edu.agh.mczernek.mandown.sensor.SavedAccelerometerValueListener;
import pl.edu.agh.mczernek.mandown.utils.AccelerometerValue;

public class ThreeAxisPermanentAngleDetector implements
		SavedAccelerometerValueListener {

	private PermanentAngleDetector xDetector;
	private PermanentAngleDetector yDetector;
	private PermanentAngleDetector zDetector;

	public ThreeAxisPermanentAngleDetector(long threshold, double tolerance,
			long period) {
		xDetector = new PermanentAngleDetector(threshold, tolerance, period);
		yDetector = new PermanentAngleDetector(threshold, tolerance, period);
		zDetector = new PermanentAngleDetector(threshold, tolerance, period);
	}

	@Override
	public void newValue(AccelerometerValue val) {
		xDetector.newValue(val.getTime(), val.getValues()[0]);
		yDetector.newValue(val.getTime(), val.getValues()[1]);
		zDetector.newValue(val.getTime(), val.getValues()[2]);
	}

	@Override
	public void newValue(long time, float[] values) {
		newValue(new AccelerometerValue(time, values));
	}

	public void registerXListener(PermanentAngleListener x) {
		xDetector.registerListener(x);
	}

	public void registerYListener(PermanentAngleListener y) {
		xDetector.registerListener(y);
	}

	public void registerZListener(PermanentAngleListener z) {
		xDetector.registerListener(z);
	}

	public void registerListeners(PermanentAngleListener x,
			PermanentAngleListener y, PermanentAngleListener z) {
		xDetector.registerListener(x);
		yDetector.registerListener(y);
		zDetector.registerListener(z);
	}

	public void unregisterListener(PermanentAngleListener listener) {
		xDetector.unregisterListener(listener);
		yDetector.unregisterListener(listener);
		zDetector.unregisterListener(listener);
	}

}
