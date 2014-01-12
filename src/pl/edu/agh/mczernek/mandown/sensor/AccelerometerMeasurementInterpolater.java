package pl.edu.agh.mczernek.mandown.sensor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.mczernek.mandown.filter.FilterFactory;
import pl.edu.agh.mczernek.mandown.utils.AccelerometerValue;

public class AccelerometerMeasurementInterpolater implements
		AccelerometerMeasurmentSolver {

	private long currTime;
	private long timeStamp;

	private List<AccelerometerValue> lastTwoValues;

	private List<SavedAccelerometerValueListener> valueListenersList;

	public AccelerometerMeasurementInterpolater(long timestamp) {
		this.timeStamp = timestamp;

		initiaieLists();
	}

	private void initiaieLists() {
		lastTwoValues = new ArrayList<AccelerometerValue>(2);
		valueListenersList = new LinkedList<SavedAccelerometerValueListener>();
	}

	@Override
	public void addValue(long time, float[] values) {
		addValue(new AccelerometerValue(time, values));
	}

	@Override
	public void addValue(AccelerometerValue val) {
		AccelerometerValue toAdd = FilterFactory.getFilter().getFilteredValue(
				val);

		refreshLastTwoValues(toAdd); // Must be in this order ass LastTwoValues
										// are used to interpolate All values
										// later.
		refreshValues(toAdd);
	}

	@Override
	public void registerSavedAccelerometerValueListener(
			SavedAccelerometerValueListener listener) {
		valueListenersList.add(listener);
	}

	@Override
	public void unregisterSavedAccelerometerValueListener(
			SavedAccelerometerValueListener listener) {
		valueListenersList.remove(listener);
	}

	private void refreshValues(AccelerometerValue val) {
		if (lastTwoValues.size() != 2) {
			saveValue(val);
		} else {
			if (val.getTime() >= currTime + timeStamp) {
				saveInterpolatedValue(val.getTime());
			}
		}
	}

	private void saveInterpolatedValue(long time) {
		while (currTime + timeStamp <= time) {
			currTime += timeStamp;
		}
		saveValue(interpolatedValues(currTime));
	}

	private AccelerometerValue interpolatedValues(long time) {
		AccelerometerValue firstValue = lastTwoValues.get(0);
		AccelerometerValue secondValue = lastTwoValues.get(1);

		long timeStamp = secondValue.getTime() - firstValue.getTime();

		float secondFactor = (float) (time - firstValue.getTime()) / timeStamp;
		float firstFactor = (float) (secondValue.getTime() - time) / timeStamp;

		float interpolatedX = firstFactor * firstValue.getValues()[0]
				+ secondFactor * secondValue.getValues()[0];
		float interpolatedY = firstFactor * firstValue.getValues()[1]
				+ secondFactor * secondValue.getValues()[1];
		float interpolatedZ = firstFactor * firstValue.getValues()[2]
				+ secondFactor * secondValue.getValues()[2];

		return new AccelerometerValue(time, new float[] { interpolatedX,
				interpolatedY, interpolatedZ });
	}

	private void saveValue(AccelerometerValue val) {
		refreshTime(val);

		notifyValueListeners(val);
	}

	private void refreshTime(AccelerometerValue val) {
		currTime = val.getTime();
	}

	private void notifyValueListeners(AccelerometerValue val) {
		if (null != valueListenersList) {
			for (SavedAccelerometerValueListener lis : valueListenersList) {
				lis.newValue(val);
			}
		}
	}

	private void refreshLastTwoValues(AccelerometerValue val) {
		if (lastTwoValues.size() == 2) {
			lastTwoValues.remove(0);
		}
		lastTwoValues.add(val);
	}

}
