package pl.edu.agh.mczernek.mandown.fallDetector;

import pl.edu.agh.mczernek.mandown.sensor.SavedAccelerometerValueListener;
import pl.edu.agh.mczernek.mandown.utils.AccelerometerValue;

public class EventsDetector implements SavedAccelerometerValueListener {

	public EventsDetector() {

	}

	@Override
	public void newValue(AccelerometerValue val) {
		processValue(val);
	}

	@Override
	public void newValue(long time, float[] values) {
		newValue(new AccelerometerValue(time, values));
	}

	private void processValue(AccelerometerValue value) {

	}

}
