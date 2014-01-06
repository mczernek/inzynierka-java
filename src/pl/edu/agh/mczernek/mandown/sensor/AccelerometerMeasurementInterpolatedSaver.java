package pl.edu.agh.mczernek.mandown.sensor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.mczernek.mandown.filter.FilterFactory;
import pl.edu.agh.mczernek.mandown.utils.AccelerometerValue;
import android.util.Log;

public class AccelerometerMeasurementInterpolatedSaver implements
		AccelerometerMeasurmentSaver {

	private long currTime;
	private long timeStamp;

	private List<AccelerometerValue> allValues;
	private List<AccelerometerValue> lastTwoValues;

	public AccelerometerMeasurementInterpolatedSaver(long timestamp) {
		this.timeStamp = timestamp;

		initiaieLists();
	}

	private void initiaieLists() {
		allValues = new LinkedList<AccelerometerValue>();
		lastTwoValues = new ArrayList<AccelerometerValue>(2);
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
		refreshAllValues(toAdd);
	}

	private void refreshAllValues(AccelerometerValue val) {
		if (allValues.isEmpty()) {
			addToAllValues(val);
		} else {
			if (val.getTime() >= currTime + timeStamp) {
				addInterpolatedValueToAllValues(val.getTime());
			}
		}
	}

	private void addInterpolatedValueToAllValues(long time) {
		while (currTime + timeStamp <= time) {
			currTime += timeStamp;
		}
		addToAllValues(interpolatedValues(currTime));
	}

	private AccelerometerValue interpolatedValues(long time) {
		AccelerometerValue firstValue = lastTwoValues.get(0);
		AccelerometerValue secondValue = lastTwoValues.get(1);

		long timeStamp = secondValue.getTime() - firstValue.getTime();

		float secondFactor = (float) (time - firstValue.getTime()) / timeStamp;
		float firstFactor = (float) (secondValue.getTime() - time) / timeStamp;

		if (time == allValues.get(allValues.size() - 1).getTime()) {
			Log.e("TAG", "Sth bad happened!");
		}

		float interpolatedX = firstFactor * firstValue.getValues()[0]
				+ secondFactor * secondValue.getValues()[0];
		float interpolatedY = firstFactor * firstValue.getValues()[1]
				+ secondFactor * secondValue.getValues()[1];
		float interpolatedZ = firstFactor * firstValue.getValues()[2]
				+ secondFactor * secondValue.getValues()[2];

		return new AccelerometerValue(time, new float[] { interpolatedX,
				interpolatedY, interpolatedZ });
	}

	private void addToAllValues(AccelerometerValue val) {
		allValues.add(val);
		currTime = val.getTime();
	}

	private void refreshLastTwoValues(AccelerometerValue val) {
		if (lastTwoValues.size() == 2) {
			lastTwoValues.remove(0);
		}
		lastTwoValues.add(val);
	}

	@Override
	public void clearState() {
		lastTwoValues.clear();
		allValues.clear();
	}

	@Override
	public List<AccelerometerValue> getCurrentValues() {
		return allValues;
	}

}
