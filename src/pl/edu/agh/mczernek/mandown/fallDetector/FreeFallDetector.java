package pl.edu.agh.mczernek.mandown.fallDetector;

import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.mczernek.mandown.sensor.SavedAccelerometerValueListener;
import pl.edu.agh.mczernek.mandown.utils.AccelerometerValue;

public class FreeFallDetector implements SavedAccelerometerValueListener {

	private static final int INVALID_TIME = -1;

	private List<FreeFallListener> listenersList;

	private int threshold;
	private double tolerance;

	private long period;

	private double lastValue;
	private long lastTime = INVALID_TIME;

	private int count;

	private long freeFallBeginTime;
	private double freeFallMinValue;

	public FreeFallDetector(int threshold, double tolerance, long period) {
		this.threshold = threshold;
		this.tolerance = tolerance;
		this.period = period;
		listenersList = new LinkedList<FreeFallListener>();
	}

	@Override
	public void newValue(AccelerometerValue val) {
		if (INVALID_TIME == lastTime) {
			setFirstValue(val);
		} else {
			processValue(val);
		}
	}

	@Override
	public void newValue(long time, float[] values) {
		newValue(new AccelerometerValue(time, values));
	}

	public void registerFreeFallListener(FreeFallListener listener) {
		listenersList.add(listener);
	}

	public void unregisterFreeFallListener(FreeFallListener listener) {
		listenersList.remove(listener);
	}

	private void notifyListeners(FreeFall fall) {
		for (FreeFallListener listener : listenersList) {
			listener.freeFallDetected(fall);
		}
	}

	private void setFirstValue(AccelerometerValue val) {
		lastTime = val.getTime();
		lastValue = getVectorLength(val.getValues());
	}

	private double getVectorLength(float[] values) {
		float sqrSum = 0.0f;
		for (float f : values) {
			sqrSum += f * f;
		}
		return Math.sqrt(sqrSum);
	}

	private void processValue(AccelerometerValue value) {
		long time = value.getTime();
		double currentValue = getVectorLength(value.getValues());
		long countInc = (time - lastTime) / period;
		if (count < threshold) {
			processDetectingFreeFall(currentValue, countInc);
		} else {
			processDetectedFall(time, currentValue, countInc);
		}

		replaceParameters(time, currentValue);
	}

	private void replaceParameters(long time, double currentValue) {
		lastValue = currentValue;
		lastTime = time;
	}

	private void processDetectedFall(long time, double currentValue,
			long countInc) {
		if (currentValue < lastValue) {
			freeFallContinues(currentValue, countInc);
		} else if (currentValue > freeFallMinValue + tolerance * 2) {
			freeFallFinished(time);
		}
	}

	private void processDetectingFreeFall(double currentValue, long countInc) {
		if (currentValue <= lastValue && currentValue < 12.0) {
			if (count == 0) {
				freeFallBeginTime = lastTime;
			}
			freeFallContinues(currentValue, countInc);
		} else if (currentValue < lastValue) {
			freeFallRegress(countInc);
		} else {
			freeFallBroken();
		}
	}

	private void freeFallBroken() {
		count = 0;
	}

	private void freeFallRegress(long countInc) {
		count -= 2 * countInc;
		if (count < 0) {
			freeFallBroken();
		}
	}

	private void freeFallFinished(long time) {
		notifyListeners(new FreeFall(freeFallBeginTime, time, freeFallMinValue));
		freeFallBroken();
	}

	private void freeFallContinues(double currentValue, long countInc) {
		count += countInc;
		freeFallMinValue = currentValue;
	}
}
